package com.hades.example.android._feature;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;

/**
 * 如何实现穿透点击
 */
public class FaqPenetrateClickActivity extends AppCompatActivity {
    private static final String TAG = FaqPenetrateClickActivity.class.getSimpleName();

    ViewGroup videoViewContainer;
    View videoView;

    ViewGroup slideViewContainer;
    View slideView;

    View playController;
    View play;
    View pause;

    private long lastSwipeTs = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_penetrate_click);

        videoViewContainer = findViewById(R.id.surfaceViewContainer);
        videoView = findViewById(R.id.surfaceView);
        videoViewContainer.setOnClickListener(v -> Log.d(TAG, "Click Video View")); // 不会捕捉到事件

        findViewById(R.id.userTap).setOnClickListener(v -> {
            togglePlayController();
        });

        slideViewContainer = findViewById(R.id.slideViewContainer);
        slideView = findViewById(R.id.slideView);
        slideViewContainer.setOnTouchListener((v, event) -> {
            if (event.getAction() != MotionEvent.ACTION_DOWN) {
                return true;
            }
            Log.d(TAG, "onCreate: Touch Slide View");
            long ts = System.currentTimeMillis();
            if (ts - lastSwipeTs < 1000) {
                return true;
            }
            switchSlideAndVideo();
            lastSwipeTs = ts;
            return true;
        });

        slideViewContainer.setOnClickListener(v -> {
            Log.d(TAG, "onCreate: Click Slide View");
            long ts = System.currentTimeMillis();
            if (ts - lastSwipeTs < 1000) {
                return;
            }
            switchSlideAndVideo();
            lastSwipeTs = ts;
        });

        playController = findViewById(R.id.playController);
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        play.setOnClickListener(v -> {
            Log.d(TAG, "Click  Play");
            play.setVisibility(View.GONE);
            pause.setVisibility(View.VISIBLE);
        });
        pause.setOnClickListener(v -> {
            {
                Log.d(TAG, "Click  Play");
                play.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);
            }
        });
    }

    private void switchSlideAndVideo() {
        if (isLargeSlide()) {
            // switch to large Video
            slideViewContainer.removeView(slideView);
            videoViewContainer.removeView(videoView);

            videoViewContainer.addView(slideView);
            slideViewContainer.addView(videoView);

            adjustViewLevel();
        } else {
            // switch to large Slide
            slideViewContainer.removeView(videoView);
            videoViewContainer.removeView(slideView);

            videoViewContainer.addView(videoView);
            slideViewContainer.addView(slideView);

            adjustViewLevel();
        }
    }

    private void adjustViewLevel() {
        slideViewContainer.bringToFront();
        slideViewContainer.invalidate();

        playController.bringToFront();
        playController.invalidate();
    }

    private boolean isLargeSlide() {
        return slideViewContainer.getChildAt(0).getId() == R.id.slideView;
    }

    private void togglePlayController() {
        if (playController.getVisibility() == View.VISIBLE) {
            playController.setVisibility(View.GONE);
        } else {
            playController.setVisibility(View.VISIBLE);
        }
    }
}
