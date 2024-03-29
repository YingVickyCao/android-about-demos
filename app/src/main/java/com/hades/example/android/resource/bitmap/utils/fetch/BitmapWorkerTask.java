package com.hades.example.android.resource.bitmap.utils.fetch;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.hades.utility.android.utils.VersionUtil;

import java.lang.ref.WeakReference;

// TODO: AsyncTask
class BitmapWorkerTask extends AsyncTask<Void, Void, BitmapDrawable> {
    private static final String TAG = BitmapWorkerTask.class.getSimpleName();

    private ImageWorker imageWorker;
    public Object mData;
    private final WeakReference<ImageView> imageViewReference;
    private final OnImageLoadedListener mOnImageLoadedListener;

    public BitmapWorkerTask(ImageWorker imageWorker, Object data, ImageView imageView) {
        this.imageWorker = imageWorker;
        mData = data;
        imageViewReference = new WeakReference<>(imageView);
        mOnImageLoadedListener = null;
    }

    public BitmapWorkerTask(ImageWorker imageWorker, Object data, ImageView imageView, OnImageLoadedListener listener) {
        this.imageWorker = imageWorker;
        mData = data;
        imageViewReference = new WeakReference<>(imageView);
        mOnImageLoadedListener = listener;
    }

    @Override
    protected BitmapDrawable doInBackground(Void... params) {
        return loadBitmapInBackground(params);
    }

    private BitmapDrawable loadBitmapInBackground(Void... params) {
        final String url = String.valueOf(mData);
        Bitmap bitmap = null;
        BitmapDrawable drawable = null;

        // Wait here if work is paused and the task is not cancelled
        synchronized (imageWorker.mPauseWorkLock) {
            while (imageWorker.mPauseWork && !isCancelled()) {
                try {
                    imageWorker.mPauseWorkLock.wait();
                } catch (InterruptedException e) {
                }
            }
        }

        bitmap = findBitmapInDishCache(url);
        bitmap = processBitmap(bitmap);

        if (null == bitmap) {
            return null;
        }

        if (VersionUtil.isNoLessThanV3()) {
            // PO:[Bitmap] BitmapDrawable
            drawable = new BitmapDrawable(imageWorker.mResources, bitmap);
        }
        if (imageWorker.mImageCache != null) {
            imageWorker.mImageCache.cacheBitmap(url, drawable);
        }
        return drawable;
    }

    private Bitmap findBitmapInDishCache(String url) {
        if (imageWorker.mImageCache != null && !isCancelled() && getAttachedImageView() != null && !imageWorker.mExitTasksEarly) {
            Bitmap bitmap = imageWorker.mImageCache.getBitmapFromDiskCache(url);
            return bitmap;
        }
        Log.d(TAG, "findBitmapInDishCache: [3]url=" + url + ",bitmap = null");
        return null;
    }

    private Bitmap processBitmap(Bitmap bitmap) {
        if (bitmap == null && !isCancelled() && getAttachedImageView() != null && !imageWorker.mExitTasksEarly) {
            bitmap = imageWorker.processBitmap(mData);
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(BitmapDrawable value) {
        if (isCancelled() || imageWorker.mExitTasksEarly) {
            value = null;
        }

        final ImageView imageView = getAttachedImageView();
        if (isSuccess(value, imageView)) {
            imageWorker.setImageDrawable(imageView, value);
        }
        if (mOnImageLoadedListener != null) {
            mOnImageLoadedListener.onImageLoaded(isSuccess(value, imageView));
        }
    }

    boolean isSuccess(BitmapDrawable value, ImageView imageView) {
        return value != null && imageView != null;
    }

    @Override
    protected void onCancelled(BitmapDrawable value) {
        super.onCancelled(value);
        synchronized (imageWorker.mPauseWorkLock) {
            imageWorker.mPauseWorkLock.notifyAll();
        }
    }

    private ImageView getAttachedImageView() {
        final ImageView imageView = imageViewReference.get();
        final BitmapWorkerTask bitmapWorkerTask = imageWorker.getBitmapWorkerTask(imageView);

        if (this == bitmapWorkerTask) {
            return imageView;
        }

        return null;
    }
}