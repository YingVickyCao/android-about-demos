package com.hades.example.android.app_component.content_provider.system.media;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.hades.example.android.R;
import com.tbruyelle.rxpermissions2.RxPermissions;


public class GalleryActivity extends AppCompatActivity {
    private static final String TAG = "GalleryActivity";
    RxPermissions rxPermissions = null;
    private View fragmentRoot;

    private String pic_path;
//    private DisplayImageOptions options;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_provider_gallery_acitivity);

        fragmentRoot = findViewById(R.id.fragmentRoot);
        findViewById(R.id.openSystemGallery).setOnClickListener(v -> openSystemGallery());
        findViewById(R.id.chooseSystemGallery).setOnClickListener(v -> chooseSystemGallery());
        findViewById(R.id.browserSystemGallery).setOnClickListener(v -> browserSystemGallery());

        rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);
    }

    // 直接打开系统相册查看照片 不是选择图片
    private void openSystemGallery() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_APP_GALLERY);
        startActivity(intent);
    }

    @SuppressLint("CheckResult")
    private void chooseSystemGallery() {
        // Android <=6.0 不需要request 权限
        rxPermissions.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(shouldShowRequestPermissionRationale -> {
                    if (shouldShowRequestPermissionRationale) {
                        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                                .subscribe(granted -> {
                                    if (granted) {
                                        doChooseGallery();
                                    } else {
                                        Toast.makeText(GalleryActivity.this, "permission not granted", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        doChooseGallery();
                    }
                });

    }

    private void doChooseGallery() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, 1000);
    }

    // 调用android自带图库，显示选中的图片
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 1000 && resultCode == Activity.RESULT_OK) {
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
        // Android <=6.0 不需要request 权限
        rxPermissions.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(shouldShowRequestPermissionRationale -> {
                    if (shouldShowRequestPermissionRationale) {
                        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                                .subscribe(granted -> {
                                    if (granted) {
                                        doBrowserSystemGallery();
                                    } else {
                                        Toast.makeText(GalleryActivity.this, "permission not granted", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        doBrowserSystemGallery();
                    }
                });
    }

    private void doBrowserSystemGallery() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        GalleryFragment galleryFragment = new GalleryFragment();
        galleryFragment.setGallerySelectedListener(this::showSelectedPicture);
        fragmentManager.beginTransaction().replace(R.id.fragmentRoot, galleryFragment, GalleryChooseResultFragment.TAG).commit();
    }
}
