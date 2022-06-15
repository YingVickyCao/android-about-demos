package com.hades.example.android.resource.animator;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

/**
 * https://developer.android.google.cn/guide/topics/resources/animation-resource
 * https://developer.android.google.cn/guide/topics/graphics/prop-animation#choreography
 * https://developer.android.google.cn/reference/android/animation/ValueAnimator
 */

// AnimatedVectorDrawable 结合ObjectAnimator 使用：TestAnimatedVectorDrawableFragment
public class TestObjectAnimationFragment extends Fragment {
    private static final String TAG = TestObjectAnimationFragment.class.getSimpleName();

    private Button btn;

    private Button btn2;
    private ImageView imageview2;
    private ImageView imageview3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.res_anim_object_animator, container, false);

        btn = view.findViewById(R.id.btn);
        view.findViewById(R.id.change_bg_color_xml).setOnClickListener(v -> change_bg_color_xml());
        view.findViewById(R.id.change_bg_color_java).setOnClickListener(v -> change_bg_color_java());

        // 平移
        btn2 = view.findViewById(R.id.btn2);
        view.findViewById(R.id.AnimatorSet_translationXY_by_XML).setOnClickListener(v -> AnimatorSet_translationXY_by_XML());
        view.findViewById(R.id.AnimatorSet_translationXY_by_java).setOnClickListener(v -> AnimatorSet_translationXY_by_java());
        view.findViewById(R.id.PropertyValuesHolder_translationXY_by_java).setOnClickListener(v -> PropertyValuesHolder_translationXY_by_java());

        // 旋转
        imageview2 = view.findViewById(R.id.imageview2);
        view.findViewById(R.id.rotate).setOnClickListener(v -> rotate());

        // 旋转
        imageview3 = view.findViewById(R.id.imageview3);
        view.findViewById(R.id.scale).setOnClickListener(v -> scale());
        return view;
    }

    private void change_bg_color_xml() {
        ObjectAnimator objectAnimator4Bg = (ObjectAnimator) AnimatorInflater.loadAnimator(getContext(), R.animator.property_animator_4_object_animation_4_bg_color);
        objectAnimator4Bg.setEvaluator(new ArgbEvaluator()); // 渐变，否则 一闪一闪
        objectAnimator4Bg.setTarget(btn);                       // 目标View
        setAnimatorListener(objectAnimator4Bg);
        objectAnimator4Bg.start();
    }

    private void change_bg_color_java() {
        ObjectAnimator objectAnimator4Bg = ObjectAnimator.ofInt(btn, "backgroundColor", 0xffff8800, 0xffcc0000); // 目标View，View set 属性名称，开始值（int），结束值（int）

        objectAnimator4Bg.setDuration(3000);                    // 动画持续时间
        // 设置动画重复播放次数 = 重放次数+1
        // 动画播放次数 = infinite时,动画无限重复
        objectAnimator4Bg.setRepeatCount(0);                    // 动画重复的次数
        // ValueAnimator.RESTART(默认):正序重放
        // ValueAnimator.REVERSE:倒序回放
        objectAnimator4Bg.setRepeatMode(ValueAnimator.REVERSE); // 设置重复播放动画模式
        objectAnimator4Bg.setEvaluator(new ArgbEvaluator());    // 渐变，否则 一闪一闪
//        objectAnimator4Bg.setStartDelay(1000);                // 动画延迟播放
        setAnimatorListener(objectAnimator4Bg);

        objectAnimator4Bg.start();                              // 开始运行动画
    }


    private void AnimatorSet_translationXY_by_XML() {
        Animator animator = AnimatorInflater.loadAnimator(getContext(), R.animator.property_animator_4_set_4_translation); // AnimatorSet
        animator.setTarget(btn2);
        setAnimatorListener(animator);
        animator.start();
    }

    private void AnimatorSet_translationXY_by_java() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(btn2, "translationX", 0f, 500f).setDuration(1000);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(btn2, "translationY", 0f, 200f).setDuration(1000);
        ObjectAnimator color = ObjectAnimator.ofInt(btn, "backgroundColor", 0xf00, 0x0f0).setDuration(1000);


        AnimatorSet set = new AnimatorSet(); // 相对于父容器， X、Y轴 先后移动
//        set.playSequentially(animatorX, animatorY);                                            // play x ->  play y
        set.play(color);               // TODO:
//        set.play(animatorX).with(color);               // TODO:
//        set.play(animatorY).before(animatorX);                                                  // play x -> play y
//        set.play(animatorY).after(animatorX);                                                 // play x, play y
//        set.playTogether(animatorX, animatorY);                                                // play (x,y)
        setAnimatorListener(set);
//        set.setDuration(500);
//        set.setInterpolator(new BounceInterpolator());
        set.start();
    }

    private void PropertyValuesHolder_translationXY_by_java() {
        PropertyValuesHolder holder1 = PropertyValuesHolder.ofFloat("translationX", 0f, 500f);
        PropertyValuesHolder holder2 = PropertyValuesHolder.ofFloat("translationY", 0f, 200f);
        ObjectAnimator.ofPropertyValuesHolder(btn2, holder1, holder2).setDuration(1000)
                .start(); // play (x,y)
    }

    //  旋转
    private void rotate() {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(imageview2, "rotation", 0f, 360f).setDuration(1000);
//        rotation.setDuration(1000);
        rotation.start();
    }

    // 缩放
    private void scale() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageview3, "scaleX", 1f, 3f, 1f);
        objectAnimator.setDuration(1000);
        objectAnimator.start();
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