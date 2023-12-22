package com.hades.example.android.data_storage.io;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.hades.example.android.R;
import com.hades.example.android.base.BaseFragment;
import com.hades.utility.jvm.FileUtils;

import java.io.File;

public class TestZipFragment extends BaseFragment {
    private static final String TAG = TestZipFragment.class.getSimpleName();

    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_storage_zip_layout, container, false);
        view.findViewById(R.id.unzip).setOnClickListener(v -> unzip());
        progressBar = view.findViewById(R.id.progressBar);
        return view;
    }

    private void unzip() {
        if (progressBar.getVisibility() == View.VISIBLE) {
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                showLoading();
                try {
                    //        File sdCardDir = Environment.getExternalStorageDirectory();
                    // /storage/emulated/0
//                    File targetFile = new File(sdCardDir.getCanonicalPath()
//                    + File.separator + EXTERNAL_STORAGE_FILE_NAME); // /storage/emulated/0/sd_test_IO.txt   => sdcard/sd_test_IO.txt
                    // /storage/emulated/0
                    String path = Environment.getExternalStorageDirectory().getPath();          // /storage/emulated/0
                    String path2 = Environment.getExternalStorageDirectory().getCanonicalPath();// /storage/emulated/0
                    Log.d(TAG, "unzip: " + path);
                    Log.d(TAG, "unzip: " + path2);

                    String newFilePath = path + File.separator + "yc"+File.separator+"abc";
                    new FileUtils().unzip(newFilePath + ".zip", newFilePath, false);
                    hideLoading();
                } catch (Exception exception) {
                    Log.d(TAG, "unzip: " + exception.getMessage());
                    hideLoading();
                }
            }
        }).start();
    }

    private void showLoading() {
        getActivity().runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));
    }

    private void hideLoading() {
        getActivity().runOnUiThread(() -> progressBar.setVisibility(View.GONE));
    }
}