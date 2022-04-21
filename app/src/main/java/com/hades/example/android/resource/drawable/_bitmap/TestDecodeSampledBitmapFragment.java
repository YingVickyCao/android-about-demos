package com.hades.example.android.resource.drawable._bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.hades.example.android.lib.utils.ImageUtil;
import com.hades.example.android.R;
import com.hades.example.android.lib.base.BaseFragment;

public class TestDecodeSampledBitmapFragment extends BaseFragment {
    private static final String TAG = TestDecodeSampledBitmapFragment.class.getSimpleName();
    private final String IMAGE_FULL_PATH = "/sdcard/wallpaper.jpg";

    private ImageView mImageView;
    private ImageUtil bitmapUtil = new ImageUtil();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.res_bitmap_decode, container, false);
        mImageView = view.findViewById(R.id.imageView);
        view.findViewById(R.id.clear).setOnClickListener(v -> clear());
        view.findViewById(R.id.setImageResource).setOnClickListener(v -> setImageResource());
        view.findViewById(R.id.decodeResource).setOnClickListener(v -> decodeResource());
        view.findViewById(R.id.decodeResource_Sampled).setOnClickListener(v -> decodeResource_Sampled());
        view.findViewById(R.id.decodeFile).setOnClickListener(v -> decodeFile());
        return view;
    }

    private void clear() {
        mImageView.getLayoutParams().height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        mImageView.getLayoutParams().width = RelativeLayout.LayoutParams.MATCH_PARENT;
        mImageView.requestLayout();

        mImageView.setImageResource(android.R.color.holo_orange_light);
    }

    /**
     * checkDrawableMemory: [Bitmap]: Width=4480,Height=2520,ByteCount=45158400,[ImageView]: width=1440, height=350
     */
    private void setImageResource() {
        Log.d(TAG, "setImageResource: ");
        mImageView.setImageResource(R.drawable.wallpaper);
        checkDrawableMemory();
    }

    /**
     * checkDrawableMemory: [Bitmap]: Width=5120,Height=2880,ByteCount=58982400,[ImageView]: width=1440, height=350
     */
    private void decodeFile() {
        Log.d(TAG, "decodeFile: ");
        Bitmap bitmap = BitmapFactory.decodeFile(IMAGE_FULL_PATH);
        mImageView.setImageBitmap(bitmap);
        checkDrawableMemory();
    }

    /**
     * checkDrawableMemory: [Bitmap]: Width=4480,Height=2520,ByteCount=45158400,[ImageView]: width=1440, height=350
     */
    private void decodeResource() {
        Log.d(TAG, "decodeResource: ");
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper);
        mImageView.setImageBitmap(bitmap);
        checkDrawableMemory();
    }

    /**
     * ImageUtil: scaleSize: viewWidth:1440,viewHeight:350，scaleSize=2
     * checkDrawableMemory: [Bitmap]: Width=2240,Height=1260,ByteCount=11289600,[ImageView]: width=1440, height=350
     */
    private void decodeResource_Sampled() {
        Log.d(TAG, "decodeResource_Sampled: ");
        Bitmap target = bitmapUtil.decodeResource(getResources(), R.drawable.wallpaper, mImageView.getWidth(), mImageView.getHeight());
        mImageView.setImageBitmap(target);
        checkDrawableMemory();
    }

    private void checkDrawableMemory() {
        BitmapDrawable drawable = (BitmapDrawable) mImageView.getDrawable();
        if (null != drawable) {
            Bitmap bitmap = drawable.getBitmap();
            int bitmapWidth = bitmap.getWidth();
            int bitmapHeight = bitmap.getHeight();
            int bitmapByteCount = bitmap.getByteCount();

            int width = mImageView.getWidth();
            int height = mImageView.getHeight();
            Log.d(TAG, "checkDrawableMemory: [Bitmap]: Width=" + bitmapWidth + ",Height=" + bitmapHeight + ",ByteCount=" + bitmapByteCount + ",[ImageView]: width=" + width + ", height=" + height);
        }
    }
}