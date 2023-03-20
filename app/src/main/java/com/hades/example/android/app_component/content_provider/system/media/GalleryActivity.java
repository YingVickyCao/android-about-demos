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
import com.hades.example.android.tools.FragmentUtils;
import com.hades.example.android.tools.permission.IRequestPermissionsCallback;
import com.hades.example.android.tools.permission.PermissionTools;
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
        PermissionTools permissionTools = new PermissionTools(this);
        permissionTools.request(new IRequestPermissionsCallback() {
            @Override
            public void granted() {
                doChooseGallery();
            }

            @Override
            public void denied() {
                Toast.makeText(GalleryActivity.this, "READ_MEDIA_IMAGES not granted", Toast.LENGTH_SHORT).show();
            }
        }, Manifest.permission.READ_MEDIA_IMAGES);
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
        PermissionTools permissionTools = new PermissionTools(this);
        permissionTools.request(new IRequestPermissionsCallback() {
            @Override
            public void granted() {
                doBrowserSystemGallery();
            }

            @Override
            public void denied() {
                Toast.makeText(GalleryActivity.this, "READ_MEDIA_IMAGES not granted", Toast.LENGTH_SHORT).show();
            }
        }, Manifest.permission.READ_MEDIA_IMAGES);
    }

    private void doBrowserSystemGallery() {
        GalleryFragment galleryFragment = new GalleryFragment();
        galleryFragment.setGallerySelectedListener(this::showSelectedPicture);
        FragmentUtils.replaceFragment(this, R.id.fragmentRoot, galleryFragment, GalleryChooseResultFragment.TAG);
    }
}
