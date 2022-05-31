package com.hades.example.android.app_component.content_provider.system.media;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.hades.example.android.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


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
        findViewById(R.id.chooseSystemGallery).setOnClickListener(v -> chooseGallery());

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
    private void chooseGallery() {
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
        FragmentManager fragmentManager = getSupportFragmentManager();
//        GalleryChooseResultFragment fragment = (GalleryChooseResultFragment) fragmentManager.findFragmentById(R.id.galleryChooseResultFragment);
//        Fragment fragment = new GalleryChooseResultFragment();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.fragmentRoot, fragment, GalleryChooseResultFragment.TAG).commit();

        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, 1000);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == 1000 && resultCode == Activity.RESULT_OK)
                showPic(resultCode, data);
        }
    }

    // 调用android自带图库，显示选中的图片
    private void showPic(int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                // uri = content://media/external/images/media/4338
                Log.d(TAG, "uri = " + uri);
                Fragment fragment = new GalleryChooseResultFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(GalleryChooseResultFragment.KEY_DATA, new GalleryItem(uri, null));
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragmentRoot, fragment, GalleryChooseResultFragment.TAG).commit();
            }
        }
    }
}
