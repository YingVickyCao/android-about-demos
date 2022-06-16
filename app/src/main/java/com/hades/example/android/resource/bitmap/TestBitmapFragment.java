package com.hades.example.android.resource.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class TestBitmapFragment extends Fragment {
    private static final String TAG = TestBitmapFragment.class.getSimpleName();
    private final String IMAGE_FULL_PATH = "/sdcard/photo7.jpg";
    private final String IMAGE_NAME = "photo7.jpg";

    private ImageView img0;

    private ImageView img1_1;

    private ImageView img2_1;
    private ImageView img2_2;
    private ImageView img2_3;
    private ImageView img2_4;

    private ImageView img3_1;
    private ImageView img3_2;
    private ImageView img3_3;
    private ImageView img3_4;
    private ImageView img3_5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.res_bitmap, container, false);
        img0 = view.findViewById(R.id.img0);

        img1_1 = view.findViewById(R.id.img1_1);

        img2_1 = view.findViewById(R.id.img2_1);
        img2_2 = view.findViewById(R.id.img2_2);
        img2_3 = view.findViewById(R.id.img2_3);
        img2_4 = view.findViewById(R.id.img2_4);

        img3_1 = view.findViewById(R.id.img3_1);
        img3_2 = view.findViewById(R.id.img3_2);
        img3_3 = view.findViewById(R.id.img3_3);
        img3_4 = view.findViewById(R.id.img3_4);
        img3_5 = view.findViewById(R.id.img3_5);


        img1_1();

        // 2 使用静态方法创建Bitmap对象
        img2_1();
        img2_2();
        img2_3();
        img2_4();

        // 3 从不同的数据源来解析、创建Bitmap对象
        img3_1();
        img3_2();
        img3_3();
        img3_4();
        img3_5();
        return view;
    }

    private void img1_1() {
        if (img0.getDrawable() instanceof BitmapDrawable) {
            // 1 Png是一个Drawable - BitmapDrawable
//            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.photo7, getActivity().getTheme());
//            img1_1.setImageDrawable(drawable);

            // 获取BitmapDrawable包装的Bitmap对象
            BitmapDrawable bitmapDrawable = (BitmapDrawable) img0.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();
//            img1_1.setImageBitmap(bitmap);

            // 把一个Bitmap包装成一个BitmapDrawable对象
            BitmapDrawable bitmapDrawableTarget = new BitmapDrawable(getResources(), bitmap);
            img1_1.setImageDrawable(bitmapDrawableTarget);
        }

//        if (img0.getDrawable() instanceof BitmapDrawable) {
//            Bitmap bitmap = Bitmap.createBitmap(((BitmapDrawable) img0.getDrawable()).getBitmap());
//            img1_1.setImageBitmap(bitmap);
//        }
    }

    // 2 使用静态方法创建Bitmap对象

    /**
     * 按指定坐标(x,y)挖出:
     * public static Bitmap createBitmap(@NonNull Bitmap source, int x, int y, int width, int height)
     * 从源位图source指定的坐标点（给定x，y）开始，从中挖出宽width和高height的一块出来，创建新的Bitmap对象。
     */
    private void img2_1() {
        if (img0.getDrawable() instanceof BitmapDrawable) {
            /**
             * ERROR:java.lang.IllegalArgumentException: x + width must be <= bitmap.width() => 50+350>355
             * x : 要创建的Bitmap的x 坐标；
             * width ： 要创建的Bitmap的宽度。
             * Bitmap.width(): 原图的宽度
             *
             * 原因：要截取的图的位置不在或者超出了图的范围，然后就报错了
             * https://blog.csdn.net/qq_26439323/article/details/106883138
             *
             * ERROR: java.lang.IllegalArgumentException: y + height must be <= bitmap.height()
             * 原因：同上
             */
//            Bitmap bitmap = Bitmap.createBitmap(((BitmapDrawable) img0.getDrawable()).getBitmap(), 50, 50, getResources().getDimensionPixelSize(R.dimen.size_100), getResources().getDimensionPixelSize(R.dimen.size_50));

            // Solution:
            Bitmap bitmapSource = ((BitmapDrawable) img0.getDrawable()).getBitmap();
            int bitmapWidthOfSource = bitmapSource.getWidth();
            int bitmapHeightOfSource = bitmapSource.getHeight();
//            Log.d(TAG, "img1_2: x=" + 50);
//            Log.d(TAG, "img1_2: width=" + (bitmapWidth - 50));
//            Log.d(TAG, "img1_2: bit.width()=" + bitmapWidth);
            Bitmap bitmapTarget = Bitmap.createBitmap(bitmapSource, 50, 50, bitmapWidthOfSource - 50, bitmapHeightOfSource - 50);
//            Bitmap bitmapTarget = Bitmap.createBitmap(bitmapOrigin, 0, 0, bitmapWidth, bitmapHeight);
            img2_1.setImageBitmap(bitmapTarget);

        }
    }

    /**
     * 按宽、高缩放
     * public static Bitmap createScaledBitmap(@NonNull Bitmap src, int dstWidth, int dstHeight,boolean filter)
     * 对源位图src进行缩放，缩放成dstWidth和dstHeight的新位图。
     */
    private void img2_2() {
        if (img0.getDrawable() instanceof BitmapDrawable) {
            Bitmap bitmapSource = ((BitmapDrawable) img0.getDrawable()).getBitmap();
            // filter:true-放大图片，filter决定是否平滑，如果是缩小图片，filter无影响. false时，若放大，会模糊。
//            Bitmap bitmap = Bitmap.createScaledBitmap(bitmapSource, bitmapSource.getWidth(), bitmapSource.getHeight(), true);
            Bitmap bitmap = Bitmap.createScaledBitmap(bitmapSource, bitmapSource.getWidth() / 2, bitmapSource.getHeight() / 2, true);
            img2_2.setImageBitmap(bitmap);
        }
    }

    /**
     * 创建一个宽、高的新位图
     * Bitmap createBitmap(int width, int height, @NonNull Config config)
     */
    private void img2_3() {
        if (img0.getDrawable() instanceof BitmapDrawable) {
            Bitmap bitmapSource = ((BitmapDrawable) img0.getDrawable()).getBitmap();
            // 创建一个空位图，没有色彩，宽和高为源图的1/4
            // TODO: Bitmap=Bitmap.createBitmap(int width, int height, @NonNull Config config)  为什么得到的图片变得很小
            Bitmap bitmapTarget = Bitmap.createBitmap(bitmapSource.getWidth(), bitmapSource.getHeight(), Bitmap.Config.ARGB_8888);
            Log.d(TAG, "img2_3: source bitmap-with:" + bitmapSource.getWidth() + ",height:" + bitmapSource.getHeight());
            // 把源图的数据复制到buffer缓冲区
            Buffer buffer = ByteBuffer.allocate(bitmapSource.getByteCount());
            bitmapSource.copyPixelsToBuffer(buffer);
            buffer.position(0);
            // 从buffer缓冲区复制到空位图
            bitmapTarget.copyPixelsFromBuffer(buffer);
            Log.d(TAG, "img2_3: target bitmap-with:" + bitmapTarget.getWidth() + ",height:" + bitmapTarget.getHeight());
            img2_3.setImageBitmap(bitmapTarget);
        }
    }

    /**
     * Bitmap createBitmap(@NonNull Bitmap source, int x, int y, int width, int height,@Nullable Matrix m, boolean filter)
     * 从源位图source的指定作坐标（x，y）开始，从中挖取宽width和高height的一块出来，创建新的位图，并按Matrix指定的规则进行转换。
     */
    private void img2_4() {
        if (img0.getDrawable() instanceof BitmapDrawable) {
            Bitmap bitmapSource = ((BitmapDrawable) img0.getDrawable()).getBitmap();

            // Matrix ? https://blog.csdn.net/nupt123456789/article/details/24600055
            /**
             * Matrix sample on {@link com.hades.example.android.widget.custom_view.matrix.MatrixOnBitmapFragment}
             */
            Matrix matrix = new Matrix();
            // setTranslate-位移、setSkew-倾斜、setRotate-旋转、setScale-缩放
            matrix.setScale(2, 1);
            Bitmap bitmap = Bitmap.createBitmap(bitmapSource, 0, 0, bitmapSource.getWidth(), bitmapSource.getHeight(), matrix, true);
            img2_4.setImageBitmap(bitmap);
        }
    }

    // 3 从不同的数据源来解析、创建Bitmap对象

    /**
     * static Bitmap decodeByteArray(byte[] data, int offset, int length)
     * 从指定字节数组的offset位置开始，将长度为length的字节数据解析成Bitmap对象
     */
    private void img3_1() {
        try {
            InputStream inputStream = getContext().getAssets().open(IMAGE_NAME);
            byte[] bytes = getBytes(inputStream);
            // TODO: Bitmap = BitmapFactory.decodeByteArray(byte[] data, int offset, int length),为什么得到的图片变得很小
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length); // ERROR: not work
            img3_1.setImageBitmap(bitmap); // not shown
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = inputStream.read(b)) != -1) {
            outputStream.write(b, 0, len);
//            baos.flush();
        }
        outputStream.close();
        inputStream.close();
        byte[] bytes = outputStream.toByteArray();
        return bytes;
    }

    /**
     * BitmapFactory.decodeFile(String pathName)
     * 从指定文件文件解析，创建bitmap对象
     */
    private void img3_2() {
        Log.d(TAG, "img3_2: ");
        // TODO:Bitmap = BitmapFactory.decodeFile(String pathName),为什么得到的图片变得很小
        Bitmap bitmap = BitmapFactory.decodeFile(IMAGE_FULL_PATH);
        img3_2.setImageBitmap(bitmap);
    }

    /**
     * BitmapFactory.decodeStream(InputStream is)
     * 从指定输入流中解析，创建bitmap对象
     */
    private void img3_3() {
        try {
            InputStream inputStream = getContext().getAssets().open(IMAGE_NAME);
//            FileInputStream is = new FileInputStream(IMAGE_FULL_PATH);
            // TODO:Bitmap = BitmapFactory.decodeStream(InputStream is),为什么得到的图片变得很小
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            img3_3.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * BitmapFactory.decodeFileDescriptor(FileDescriptor fd)
     * 从FileDescriptor对应的文件，解析、创建Bitmap对象
     */
    private void img3_4() {
        // TODO:Bitmap = BitmapFactory.decodeFileDescriptor(FileDescriptor fd),为什么得到的图片变得很小
        try {
            FileInputStream is = new FileInputStream(IMAGE_FULL_PATH);
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(is.getFD());
            Log.d(TAG, "img3_4: " + bitmap);
            img3_4.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * BitmapFactory.decodeResource(Resources res, int id)
     * 从指定Id，解析、创建Bitmap对象
     */
    private void img3_5() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.photo7);
        img3_5.setImageBitmap(bitmap);
    }
}