package com.hades.example.android.app_component._activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import android.widget.Toast;

import com.hades.example.android.R;

import java.util.Locale;

public class GetViewSizeActivity extends Activity {
    private static final String TAG = GetViewSizeActivity.class.getSimpleName();

    private TextView mTextView;

    /**
     * GetViewSizeInOncreateActivity: onCreate:
     * GetViewSizeInOncreateActivity: onCreate: width=0,height0
     * GetViewSizeInOncreateActivity: onStart:
     * GetViewSizeInOncreateActivity: onResume:
     * MyTextView: onMeasure:
     * MyTextView: onMeasure:
     * MyTextView: onLayout:
     * MyTextView: layout:
     * GetViewSizeInOncreateActivity: onCreate2: width=1440,height66
     * MyTextView: onDraw:
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_view_size);

        mTextView = findViewById(R.id.my_text_view);

        // width=0,height=0,width=0,height=0
        Log.d(TAG, "onCreate: 1 width=" + mTextView.getWidth() + ",height=" + mTextView.getHeight() + ",width=" + mTextView.getMeasuredWidth() + ",height=" + mTextView.getMeasuredHeight());
        //布局绘制后获取View的大小
        mTextView.post(() -> {
            requestSize_post();
            requestViewSize_measure();
        });

        mTextView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                requestViewSize_onPreDraw(this);
                return true;
            }
        });

        mTextView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                requestViewSize_onGlobalLayout(this);
            }
        });

        mTextView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                requestViewSize_onLayoutChange(this);
            }
        });
    }

    private void requestSize_post() {
        //得到正确的宽和高
        // width=1080,height=275,width=1080,height=275
        Log.d(TAG, "requestSize_post: width=" + mTextView.getWidth() + ",height=" + mTextView.getHeight() + ",width=" + mTextView.getMeasuredWidth() + ",height=" + mTextView.getMeasuredHeight());
        String size = String.format(Locale.getDefault(), "TextView's width: %d, height: %d", mTextView.getWidth(), mTextView.getHeight());
        Toast.makeText(GetViewSizeActivity.this, size, Toast.LENGTH_SHORT).show();
        mTextView.setText(size);
    }

    private void requestViewSize_measure() {
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mTextView.measure(width, height);
        // width=1080,height=275,width=604,height=53
        Log.d(TAG, "requestViewSize_measure: width=" + mTextView.getWidth() + ",height=" + mTextView.getHeight() + ",width=" + mTextView.getMeasuredWidth() + ",height=" + mTextView.getMeasuredHeight());
    }

    private void requestViewSize_onPreDraw(ViewTreeObserver.OnPreDrawListener listener) {
        mTextView.getViewTreeObserver().removeOnPreDrawListener(listener);
        // width=1080,height=275,width=1080,height=275
        Log.d(TAG, "requestViewSize_onPreDraw: width=" + mTextView.getWidth() + ",height=" + mTextView.getHeight() + ",width=" + mTextView.getMeasuredWidth() + ",height=" + mTextView.getMeasuredHeight());
    }

    private void requestViewSize_onGlobalLayout(ViewTreeObserver.OnGlobalLayoutListener listener) {
        mTextView.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        // width=1080,height=275,width=1080,height=275
        Log.d(TAG, "requestViewSize_onGlobalLayout: width=" + mTextView.getWidth() + ",height=" + mTextView.getHeight() + ",width=" + mTextView.getMeasuredWidth() + ",height=" + mTextView.getMeasuredHeight());
    }

    private void requestViewSize_onLayoutChange(View.OnLayoutChangeListener listener) {
        mTextView.removeOnLayoutChangeListener(listener);
        Log.d(TAG, "requestViewSize_onLayoutChange: width=" + mTextView.getWidth() + ",height=" + mTextView.getHeight() + ",width=" + mTextView.getMeasuredWidth() + ",height=" + mTextView.getMeasuredHeight());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }
}

