package com.hades.example.android.resource.anim._activity_transition_animation;

import android.animation.Animator;
import android.os.Bundle;
import android.transition.Explode;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;

public class FirstPageActivity extends AppCompatActivity {
    private View pageRoot;
    private TextView tv_reveal1;
    private TextView tv_reveal2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS); // set or not set，没有影响
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
//        getWindow().setAllowEnterTransitionOverlap(false);
//        getWindow().setAllowReturnTransitionOverlap(false);

        getWindow().setExitTransition(new Explode());
//        getWindow().setExitTransition(new Slide());
//        getWindow().setExitTransition(new Fade());

        setContentView(R.layout.res_anim_activity_transition_animation_first_page);

        pageRoot = findViewById(R.id.pageRoot);
        tv_reveal1 = findViewById(R.id.tv_reveal);
        tv_reveal2 = findViewById(R.id.tv_reveal2);

        findViewById(R.id.button).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    changeView(view, motionEvent);
                }
                return false;
            }
        });
    }

    private void changeView(View view, MotionEvent motionEvent) {
        float finalRadius = (float) Math.hypot(pageRoot.getWidth(), pageRoot.getHeight());
        Animator anim = ViewAnimationUtils.createCircularReveal(tv_reveal2, (int) motionEvent.getRawX(), (int) motionEvent.getRawY(), 0, finalRadius);
        anim.setDuration(1000L);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                tv_reveal2.setVisibility(View.VISIBLE);
                tv_reveal1.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                tv_reveal1.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        anim.start();
    }
}
