package com.hades.example.android.app_component.content_provider.system.media;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.snackbar.Snackbar;
import com.hades.example.android.R;
import com.hades.example.android.tools.FragmentUtils;
import com.hades.utility.permission.OnContextUIListener;
import com.hades.utility.permission.OnPermissionResultCallback;
import com.hades.utility.permission.OnSimplePermissionCallback;
import com.hades.utility.permission.PermissionsTool;


public class GalleryActivity extends AppCompatActivity {
    private static final String TAG = "GalleryActivity";

    private String pic_path;

    private ActivityResultLauncher<Intent> mLauncher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                GalleryActivity.this.onActivityResult(result.getResultCode(), result.getData());
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_provider_gallery_acitivity);

        findViewById(R.id.openSystemGallery).setOnClickListener(v -> openSystemGallery());
        findViewById(R.id.chooseSystemGallery).setOnClickListener(v -> chooseSystemGallery());
        findViewById(R.id.browserSystemGallery).setOnClickListener(v -> browserSystemGallery());
    }

    // 直接打开系统相册查看照片 不是选择图片
    private void openSystemGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_APP_GALLERY);
        startActivity(intent);
    }

    private String[] getGalleryPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            return new String[]{Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED, Manifest.permission.READ_MEDIA_IMAGES};
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return new String[]{Manifest.permission.READ_MEDIA_IMAGES};
        } else {
            return new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        }
    }

    @SuppressLint("CheckResult")
    private void chooseSystemGallery() {
        PermissionsTool permissionTools = new PermissionsTool(this);
        permissionTools.request(
                getGalleryPermission(),
                new OnPermissionResultCallback() {
                    @Override
                    public void onPermissionGranted() {
                        doChooseGallery();
                    }

                    @Override
                    public void onPermissionDenied() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE && (permissionTools.isGranted(new String[]{Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED}) || permissionTools.isGranted(new String[]{Manifest.permission.READ_MEDIA_IMAGES}))) {
                            doChooseGallery();
                        } else {
                            Toast.makeText(GalleryActivity.this, "READ_MEDIA_IMAGES not granted", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionError(String message) {

                    }

                    @Override
                    public void showInContextUI(OnContextUIListener callback) {
                        Snackbar.make(findViewById(R.id.root), "Request READ_MEDIA_IMAGES permission", Snackbar.LENGTH_INDEFINITE)
                                .setAction(getString(R.string.ok), view -> callback.ok())
                                .setAction(getString(R.string.cancel), view -> callback.cancel())
                                .show();
                    }
                });
    }

    private void doChooseGallery() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        // startActivityForResult is depressed
//        startActivityForResult(pickIntent, 1000);
        mLauncher.launch(pickIntent);
    }

    // 调用android自带图库，显示选中的图片
    // onActivityResult is depressed
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (data != null && requestCode == 1000 && resultCode == Activity.RESULT_OK) {
//            Uri uri = data.getData();
//            // uri = content://media/external/images/media/4338
//            Log.d(TAG, "uri = " + uri);
//            showSelectedPicture(new GalleryItem(uri));
//        }
//    }

    public void onActivityResult(int resultCode, Intent data) {
        if (data != null && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            // uri = content://media/external/images/media/4338
            Log.d(TAG, "uri = " + uri);
            showSelectedPicture(new GalleryItem(uri));
        }
    }


    private void showSelectedPicture(GalleryItem bean) {
        Fragment fragment = new GalleryChooseResultFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(GalleryChooseResultFragment.KEY_DATA, bean);
        fragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentRoot, fragment, GalleryChooseResultFragment.TAG).commit();
    }

    private void browserSystemGallery() {
        PermissionsTool permissionTools = new PermissionsTool(this);
        permissionTools.request(new String[]{(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ? Manifest.permission.READ_MEDIA_IMAGES : Manifest.permission.READ_EXTERNAL_STORAGE)},
                new OnSimplePermissionCallback() {
                    @Override
                    public void onPermissionGranted() {
                        doBrowserSystemGallery();
                    }

                    @Override
                    public void onPermissionDenied() {
                        Toast.makeText(GalleryActivity.this, "READ_MEDIA_IMAGES not granted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionError(String message) {

                    }
                });
    }

    private void doBrowserSystemGallery() {
        GalleryFragment galleryFragment = new GalleryFragment();
        galleryFragment.setGallerySelectedListener(this::showSelectedPicture);
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, galleryFragment, GalleryChooseResultFragment.TAG);
    }
}
