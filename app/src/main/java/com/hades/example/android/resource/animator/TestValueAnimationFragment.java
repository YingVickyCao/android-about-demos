package com.hades.example.android.resource.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

/**
 * https://developer.android.google.cn/guide/topics/resources/animation-resource
 * https://developer.android.google.cn/guide/topics/graphics/prop-animation#choreography
 * https://developer.android.google.cn/reference/android/animation/ValueAnimator
 */
public class TestValueAnimationFragment extends Fragment {
    private static final String TAG = TestValueAnimationFragment.class.getSimpleName();

    private Button btn;
    private Button btn2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.res_anim_value_animator, container, false);

        btn = view.findViewById(R.id.btn);
        view.findViewById(R.id.changeBgColor_by_valueAnimator_Java).setOnClickListener(v -> changeBgColor_by_valueAnimator_Java());

        btn2 = view.findViewById(R.id.btn2);
        view.findViewById(R.id.moveOver).setOnClickListener(v -> moveOver());
        view.findViewById(R.id.moveBack).setOnClickListener(v -> moveBack());
        return view;
    }

    private void changeBgColor_by_valueAnimator_Java() {
        ValueAnimator animator = ValueAnimator.ofInt(0xffff8800, 0xffcc0000); // 产生一个从0到100变化的整数的动画
        animator.setDuration(3000);
        animator.setRepeatCount(0);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setEvaluator(new ArgbEvaluator());                         // 渐变，否则 一闪一闪
        setAnimatorListener(animator);
        animator.addUpdateListener(animation -> {
            Integer value = (Integer) animation.getAnimatedValue();         // 动态的获取当前运行到的属性值
            btn.setBackgroundColor(value);
        });
        animator.start(); // 开始播放动画
    }

    private void moveOver() {
        btn2.animate().x(500f).y(200f); // // play (x,y)
    }

    private void moveBack() {
        btn2.animate().x(0).y(0); // 按钮放回到它在容器中原来的位置 (0, 0)
    }

    private void setAnimatorListener(Animator animator) {
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d(TAG, " 动画开始");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, " 动画结束");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.d(TAG, " 动画取消");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.d(TAG, " 动画重复");
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.d(TAG, " 动画开始");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d(TAG, " 动画结束");
            }

            // 其他两个事件可以选择不实现
        });

    }
}