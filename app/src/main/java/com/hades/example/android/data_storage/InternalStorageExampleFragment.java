package com.hades.example.android.data_storage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class InternalStorageExampleFragment extends Fragment {
    private static final String TAG = InternalStorageExampleFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View page = inflater.inflate(R.layout.fragment_data_storage_4_internal_storage, container, false);
        page.findViewById(R.id.access_persistent_files_using_filesdir).setOnClickListener(v -> testFilesDir());
        page.findViewById(R.id.access_persistent_files_using_fileOutput).setOnClickListener(v -> testFileOutput());
        return page;
    }

    // Internal Storage - Access persistent files, START
    private void testFilesDir() {
        new Thread(() -> {
            if (null == getContext()) {
                return;
            }
            // getContext().getFilesDir() : /data/data/com.hades.example.android/files
            File file = new File(getContext().getFilesDir(), "1");
        }).start();
    }

    // /data/data/com.hades.example.android/files/2.txt
    private void testFileOutput() {
        new Thread(() -> {
            if (null == getContext()) {
                return;
            }
            final String fileName = "2.txt";
//            final String fileContents = "12";
            final String fileContents = "hello world 2023";
            // Write to a file under files dir
            try (FileOutputStream fos = getContext().openFileOutput(fileName, Context.MODE_PRIVATE | Context.MODE_APPEND)) {
                fos.write(fileContents.getBytes());
            } catch (FileNotFoundException ex) {
                Log.d(TAG, "testFileOutput:FileNotFoundException:" + ex);
            } catch (IOException ex) {
                Log.d(TAG, "testFileOutput:IOException:" + ex);
            }
        }).start();
    }
    // Internal Storage - Access persistent files, END

    // Internal Storage - Access cache files, START
    // Internal Storage - Access cache files, END
}
