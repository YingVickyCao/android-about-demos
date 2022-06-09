package com.hades.example.android.widget.custom_view.matrix;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

/**
 * Matrix 处理Bitmap ：缩放(setScale)、旋转(setRotate)、平移(setTranslate)、倾斜(setSkew)
 */
public class MatrixOnBitmapFragment extends Fragment {
    private static final String TAG = MatrixOnBitmapFragment.class.getSimpleName();

    private Button btn_scale, btn_rotate, btn_translate, btn_skew;
    private ImageView image_origin, image_after;
    private Bitmap bitmap;
    private Paint paint;

    private MatrixOnBitmapView image_after2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_custom_view_of_maxtrix_on_bitmap, container, false);
        btn_scale = view.findViewById(R.id.btn_scale);
        btn_rotate = view.findViewById(R.id.btn_rotate);
        btn_translate = view.findViewById(R.id.btn_translate);
        btn_skew = view.findViewById(R.id.btn_skew);

        btn_scale.setOnClickListener(v -> {
            bitmapScale(2.0f, 2.0f);
            image_after2.bitmapScale();
        });
        btn_rotate.setOnClickListener(v -> {
            bitmapRotate(90);
            image_after2.bitmapRotate();
        });
        btn_translate.setOnClickListener(v -> {
            bitmapTranslate(200f, 200f);
            image_after2.bitmapTranslate();
        });
        btn_skew.setOnClickListener(v -> {
            bitmapSkew(0.2f, 0.4f);
            image_after2.bitmapSkew();
        });

        image_origin = view.findViewById(R.id.image_origin);
//        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img_2);
//        image_origin.setImageBitmap(bitmap);
        bitmap = ((BitmapDrawable) image_origin.getDrawable()).getBitmap();

        image_after = view.findViewById(R.id.image_after);
        image_after2 = view.findViewById(R.id.image_after2);
//        Log.d(TAG, "onCreateView:[1] width:" + image_after.getWidth() + ",height:" + image_after.getHeight());
        view.post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "onCreateView:[2] width:" + image_after.getWidth() + ",height:" + image_after.getHeight());
                Log.d(TAG, "onCreateView:[2] top margin:" + ((LinearLayout.LayoutParams) image_after.getLayoutParams()).topMargin);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(image_after.getWidth(), image_after.getHeight());
                layoutParams.topMargin = ((LinearLayout.LayoutParams) image_after.getLayoutParams()).topMargin;
                image_after2.setLayoutParams(layoutParams);
            }
        });

        // 设置画笔，消除锯齿
        paint = new Paint();
        paint.setAntiAlias(true);
        return view;
    }

    /**
     * 缩放图片
     */
    protected void bitmapScale(float x, float y) {
        // 因为要将图片放大，所以要根据放大的尺寸重新创建Bitmap
        Bitmap afterBitmap = Bitmap.createBitmap((int) (bitmap.getWidth()), (int) (bitmap.getHeight()), bitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        // 初始化Matrix对象
        Matrix matrix = new Matrix();
        // 根据传入的参数设置缩放比例
        matrix.setScale(x, y);
        // 根据缩放比例，把图片draw到Canvas上
        canvas.drawBitmap(bitmap, matrix, paint);

        image_after.setImageBitmap(afterBitmap);
    }

    /**
     * 倾斜图片
     */
    protected void bitmapSkew(float dx, float dy) {
        // 根据图片的倾斜比例，计算变换后图片的大小，
        Bitmap afterBitmap = Bitmap.createBitmap(bitmap.getWidth()
                + (int) (bitmap.getWidth() * dx), bitmap.getHeight()
                + (int) (bitmap.getHeight() * dy), bitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        Matrix matrix = new Matrix();
        // 设置图片倾斜的比例
        matrix.setSkew(dx, dy);
        canvas.drawBitmap(bitmap, matrix, paint);
        image_after.setImageBitmap(afterBitmap);
    }

    /**
     * 图片移动
     */
    protected void bitmapTranslate(float dx, float dy) {
        // 需要根据移动的距离来创建图片的拷贝图大小
        Bitmap afterBitmap = Bitmap.createBitmap(
                (int) (bitmap.getWidth() + dx),
                (int) (bitmap.getHeight() + dy), bitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        Matrix matrix = new Matrix();
        // 设置移动的距离
        matrix.setTranslate(dx, dy);
        canvas.drawBitmap(bitmap, matrix, paint);
        image_after.setImageBitmap(afterBitmap);
    }

    /**
     * 图片旋转
     */
    protected void bitmapRotate(float degrees) {
        // 创建一个和原图一样大小的图片
        Bitmap afterBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(afterBitmap);
        Matrix matrix = new Matrix();
        // 根据原图的中心位置旋转
        matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, matrix, paint);

        image_after.setImageBitmap(afterBitmap);
    }
}