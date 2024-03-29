package com.hades.example.android.resource.drawable.alpha;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.hades.example.android.Constant;
import com.hades.example.android.R;

import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("AppCompatCustomView")
public class AlphaImageView extends ImageView {

    // 图像透明度每次改变的大小
    private int alphaDelta = 0;

    // 记录图片当前的透明度。
    private int curAlpha = 0;

    // 每隔多少毫秒透明度改变一次
    private final int SPEED = 300;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Constant.KEY_UPDATE_UI) {
                // 每次增加curAlpha的值
                curAlpha += alphaDelta;
                if (curAlpha > 255) curAlpha = 255;
                // 修改该ImageView的透明度
                AlphaImageView.this.setImageAlpha(curAlpha);
            }
        }
    };

    public AlphaImageView(Context context) {
        super(context);
    }

    public AlphaImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AlphaImageView);
        // 获取duration参数
        int duration = typedArray.getInt(R.styleable.AlphaImageView_duration, 0);
        // 计算图像透明度每次改变的大小
        alphaDelta = 255 * SPEED / duration;
    }

    public AlphaImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AlphaImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.setImageAlpha(curAlpha);
        super.onDraw(canvas);

        final Timer timer = new Timer();
        // 按固定间隔发送消息，通知系统改变图片的透明度
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = Constant.KEY_UPDATE_UI;
                if (curAlpha >= 255) {
                    timer.cancel();
                } else {
                    handler.sendMessage(msg);
                }
            }
        }, 0, SPEED);
    }
}
