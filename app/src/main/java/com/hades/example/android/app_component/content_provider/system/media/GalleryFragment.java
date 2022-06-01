package com.hades.example.android.app_component.content_provider.system.media;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hades.example.android.R;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {
    private static final String TAG = GalleryFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private GalleryAdapter adapter;
    private IGallerySelectedListener gallerySelectedListener;
    private List<GalleryItem> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_provider_gallery, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.recycle_view);

        adapter = new GalleryAdapter(getActivity(), list);
        adapter.setItemClickListener(new IItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(TAG, "onItemClick: " + position);
                Toast.makeText(getContext(), "Click position " + position, Toast.LENGTH_SHORT).show();
                if (null != gallerySelectedListener) {
                    gallerySelectedListener.select(list.get(position));
                }
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 5);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            browserPhoto_android29();
        } else {
            browserThumbnails();
        }
        return view;
    }

    public void setGallerySelectedListener(IGallerySelectedListener gallerySelectedListener) {
        this.gallerySelectedListener = gallerySelectedListener;
    }

    private void showLoading() {
        getActivity().runOnUiThread(() -> progressBar.setVisibility(View.VISIBLE));
    }

    private void hideLoading() {
        getActivity().runOnUiThread(() -> progressBar.setVisibility(View.GONE));
    }

    // Android API >= 29(Android 10)
    private void browserPhoto_android29() {
        showLoading();
        list.clear();
        Cursor cursor = null;
        try {
            // https://blog.csdn.net/weixin_39612023/article/details/117578376
            String[] attrs = {MediaStore.Images.ImageColumns._ID, MediaStore.Images.Media.DISPLAY_NAME, MediaStore.Images.ImageColumns.WIDTH, MediaStore.Images.ImageColumns.HEIGHT};

            ContentResolver contentResolver = getActivity().getContentResolver();
            cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, attrs, null, null, MediaStore.Images.ImageColumns._ID + " DESC");
            if (null == cursor) {
                return;
            }
            while (cursor.moveToNext()) {
                // 本地系统里面的相册太多，直接加载会造成OOM? Load more /将图片进行压缩 / 或者引入Picaso 图片加载框架
                int id = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID);
                String name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME));
                Uri uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getLong(id));
                String width = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.WIDTH));
                String height = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.HEIGHT));

                GalleryItem item = new GalleryItem(uri);
                item.name = name;
                item.id = id;
                // browserPhoto_android29: GalleryItem{id=0, name='20220529_170946.jpg', uri=content://media/external/images/media/4338, path='null'},width:4032,height:3024
                // browserPhoto_android29: GalleryItem{id=0, name='ic_pic.svg', uri=content://media/external/images/media/4028, path='null'},width:null,height:null
                /**
                 * W/MediaStore: Failed to obtain thumbnail for content://media/external/images/media/4028
                 *     java.io.FileNotFoundException: Failed to create image decoder with message 'unimplemented'Input contained an error.
                 *         at android.database.DatabaseUtils.readExceptionWithFileNotFoundExceptionFromParcel(DatabaseUtils.java:151)
                 *         at android.content.ContentProviderProxy.openTypedAssetFile(ContentProviderNative.java:781)
                 *         at android.content.ContentResolver.openTypedAssetFileDescriptor(ContentResolver.java:1993)
                 *         at android.content.ContentResolver.openTypedAssetFile(ContentResolver.java:1898)
                 *         at android.content.ContentResolver.lambda$loadThumbnail$0(ContentResolver.java:4078)
                 *         at android.content.-$$Lambda$ContentResolver$7ILY1SWNxC2xhk-fQUG6tAXW9Ik.call(Unknown Source:10)
                 *         at android.graphics.ImageDecoder$CallableSource.createImageDecoder(ImageDecoder.java:692)
                 *         at android.graphics.ImageDecoder.decodeBitmapImpl(ImageDecoder.java:2007)
                 *         at android.graphics.ImageDecoder.decodeBitmap(ImageDecoder.java:2000)
                 *         at android.content.ContentResolver.loadThumbnail(ContentResolver.java:4077)
                 *         at android.content.ContentResolver.loadThumbnail(ContentResolver.java:4061)
                 *         at android.provider.MediaStore$InternalThumbnails.getThumbnail(MediaStore.java:1911)
                 *         at android.provider.MediaStore$Images$Thumbnails.getThumbnail(MediaStore.java:2308)
                 *         at com.squareup.picasso.MediaStoreRequestHandler.load(MediaStoreRequestHandler.java:84)
                 *         at com.squareup.picasso.BitmapHunter.hunt(BitmapHunter.java:206)
                 *         at com.squareup.picasso.BitmapHunter.run(BitmapHunter.java:159)
                 *         at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:462)
                 *         at java.util.concurrent.FutureTask.run(FutureTask.java:266)
                 *         at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1167)
                 *         at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:641)
                 *         at java.lang.Thread.run(Thread.java:923)
                 *         at com.squareup.picasso.Utils$PicassoThread.run(Utils.java:411)
                 *
                 * D/skia: --- Failed to create image decoder with message 'unimplemented'
                 */
                Log.d(TAG, "browserPhoto_android29: " + item.toString() + ",width:" + width + ",height:" + height);
                list.add(item);

                adapter.notifyDataSetChanged();
                hideLoading();
            }
        } catch (Exception ex) {
            Log.d(TAG, "browserPhoto_android29:error: " + ex);
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }
    }

    // 缩略图
    // https://blog.csdn.net/java2009cgh/article/details/8364735
    private void browserThumbnails() {
        Cursor cursor = null;
        try {
            // https://blog.csdn.net/weixin_39612023/article/details/117578376
            // https://blog.csdn.net/dianziagen/article/details/55255401
            String[] attrs = {MediaStore.Images.Thumbnails.IMAGE_ID, MediaStore.Images.Thumbnails.DATA};

            ContentResolver contentResolver = getActivity().getContentResolver();
            cursor = contentResolver.query(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, attrs, null, null, MediaStore.Images.Thumbnails.IMAGE_ID + " DESC");
            if (null == cursor) {
                return;
            }
            while (cursor.moveToNext()) {
                int id = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.IMAGE_ID);
                String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails.DATA));

                GalleryItem item = new GalleryItem(path);
                item.id = id;
                list.add(item);

                adapter.notifyDataSetChanged();
            }
        } catch (Exception ex) {
            Log.d(TAG, "browserPhoto_android29:error: " + ex);
        } finally {
            if (null != cursor) {
                cursor.close();
            }
        }
    }
}
