package com.hades.example.android.app_component.content_provider.system.media;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

public class GalleryChooseResultFragment extends Fragment {
    public static final String TAG = GalleryChooseResultFragment.class.getSimpleName();
    public static final String KEY_DATA = "DATA";

    private GalleryItem bean;

    private ImageView image;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bean = (GalleryItem) getArguments().getSerializable(KEY_DATA);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_provider_gallery_choose_result, container, false);
        image = view.findViewById(R.id.image);
        progressBar = view.findViewById(R.id.progressBar);
        Log.d(TAG, "onCreateView: ");
        update();
        return view;
    }

    public void update() {
        if (null == bean) {
            return;
        }
        Toast.makeText(getContext(), "Updated", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.VISIBLE);
        new Thread(() -> {
            try {
                Bitmap bitmap = null;
                if (null != bean.uri) {
                    //显示图片所使用的uri：
                    //String imageUri = "http://site.com/image.png"; // from Web
                    //String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
                    //String imageUri = "content://media/external/audio/albumart/13"; // from content provider
                    //String imageUri = "assets://image.png"; // from assets
                    //String imageUri = "drawable://" + R.drawable.image; // from drawables (only images, non-9patch)
                    bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(bean.uri));
                } else {
                    bitmap = BitmapFactory.decodeFile(bean.path);
                }
                if (null != bitmap) {
                    setImage(bitmap);
                }
            } catch (Exception exception) {
                Log.d(TAG, "run: " + exception.getMessage());
            }
        }).start();
    }

    private void setImage(final Bitmap bitmap) {
        getActivity().runOnUiThread(() -> {
            image.setImageBitmap(bitmap);
            progressBar.setVisibility(View.GONE);
        });
    }
}
