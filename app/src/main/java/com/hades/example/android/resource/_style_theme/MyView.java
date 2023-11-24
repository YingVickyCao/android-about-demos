package com.hades.example.android.resource._style_theme;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.hades.example.android.R;

@SuppressLint("AppCompatCustomView")
public class MyView extends TextView {
    private static final String TAG = MyView.class.getSimpleName();

    private @ColorInt
    int colorId;
    Paint paint = new Paint();

    public MyView(Context context) { // 用于直接new view
        super(context);
        Log.d(TAG, "MyView: 1");
    }

    public MyView(Context context, @Nullable AttributeSet attrs) { // 在xml中设置一个view
        this(context, attrs, R.attr.MyViewStyle);
        Log.d(TAG, "MyView: 2");
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) { // style
        super(context, attrs, defStyleAttr, defStyleAttr);
        Log.d(TAG, "MyView: 3-------->");

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyView, defStyleAttr, R.style.MyView_Default);
        if (null == typedArray) {
            return;
        }

        CustomViewTools tools = new CustomViewTools();
        tools.printAttributeSet(TAG, attrs);
        tools.printTypedArray(TAG, typedArray);

        colorId = typedArray.getColor(R.styleable.MyView_bgColor, Color.RED);
        Log.d(TAG, "MyView: colorId=" + colorId);
        typedArray.recycle();
        Log.d(TAG, "MyView: 3<--------");
    }

    /**
     * 优先级：xml 直接定义 > xml中的style引用 > defStyleAttr > defStyleRes > theme 直接定义
     * https://blog.csdn.net/tianjf0514/article/details/125822783
     *
     * @param context
     * @param attrs        从xml中定义的参数
     * @param defStyleAttr 主题中优先级较高的属性
     * @param defStyleRes  优先级次之的View的内置style
     */

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) { // theme style
        super(context, attrs, defStyleAttr, defStyleRes);
        Log.d(TAG, "MyView: 4");
    }


    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(colorId); //设置画笔颜色
//        paint.setAlpha(77);// colorId已经包含了透明度，再setAlpha()没有影响

        paint.setStyle(Paint.Style.FILL);   //设置画笔为空心 如果将这里改为Style.STROKE  这个图中的实线圆柱体就变成了空心的圆柱体
        canvas.drawRect(0, 0, getSize(), getSize(), paint); //绘制矩形
    }

    private int getSize() {
        return (int) getResources().getDimension(R.dimen.size_80);
    }
}