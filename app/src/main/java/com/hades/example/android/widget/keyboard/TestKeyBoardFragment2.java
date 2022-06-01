package com.hades.example.android.widget.keyboard;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;
import com.hades.example.android.lib.utils.BlockQuickTap;

public class TestKeyBoardFragment2 extends Fragment {
    private static final String TAG = "TestKeyBoardFragment2";

    private EditText editText;
    private ViewGroup keyboardView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_edittext_keyboardview_2, container, false);
        editText = (EditText) view.findViewById(R.id.editText);
        keyboardView = view.findViewById(R.id.keyboardView);

        hideKeyboard();
        forbidSystemKeyBoard();
        initKeyboard();

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch: ");
                // Fix：反复点击EditText，点击的太快，键盘会闪。
                // 解决：增加200ms的重复事件的过滤
                if (BlockQuickTap.isRepeatShowKeyboard()) {
                    return false;
                }
                toggleKeyboardViewVisible();
                return false;
            }
        });

        // Fix:点击其他区域时，键盘应该消失
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 解决：增加200ms的重复事件的过滤
                if (BlockQuickTap.isRepeatShowKeyboard()) {
                    return;
                }
                hideKeyboard();
            }
        });

        return view;
    }

    private void toggleKeyboardViewVisible() {
        if (!editText.hasFocus()) {
            Log.d(TAG, "onTouch: !editText.hasFocus():go here");
            // setInputType 调用或不调用，显现一样，因此注释掉该代码
//                    int inputType = editText.getInputType();
//                    editText.setInputType(InputType.TYPE_NULL);
            showKeyboard();
//                    editText.setInputType(inputType);
        } else {
            if (keyboardView.getVisibility() != View.VISIBLE) { // Fix：显示和不可见自定义键盘多次后，再次点击EditText，自定义键盘不显示。
                Log.d(TAG, "onTouch: else keyboard is not visible, show it");
                showKeyboard();
            } else {
                Log.d(TAG, "onTouch: else keyboard is visible, hide it");
                hideKeyboard();
            }
        }
    }

    // Fix：点击EditText时，系统软键盘覆盖了KeyboardView
    // 禁止EditText弹出系统软键盘
    private void forbidSystemKeyBoard() {
        // 禁用软键盘
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // 在需要打开的Activity取消禁用软键盘
        //getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    // 取消禁用软键盘
    private void cancelForbidSystemKeyBoard() {
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancelForbidSystemKeyBoard();
    }

    private void initKeyboard() {
        for (int i = 0; i < keyboardView.getChildCount(); i++) {
            ViewGroup view = (ViewGroup) keyboardView.getChildAt(i);
            for (int j = 0; j < view.getChildCount(); j++) {
                Button btn = (Button) view.getChildAt(j);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onKeyPressed(v);
                    }
                });
            }
        }
    }

    private void onKeyPressed(View v) {
        Log.d(TAG, "onClick: " + ((Button) v).getText());
        String label = ((Button) v).getText().toString();
        Editable editable = editText.getText();
        int start = editText.getSelectionStart();
        switch (label) {
            case "Delete":
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
                break;
            case "Done":
                hideKeyboard();
                break;

            default:
                editable.insert(start, label);
                break;

        }
    }

    // Activity中获取焦点时调用，显示出键盘
    public void showKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            // 显示键盘时，添加动画，增加流畅度
            anim_slide_in_up();
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            // 隐藏键盘时，添加动画，增加流畅度
            anim_slide_out_down();
            keyboardView.setVisibility(View.INVISIBLE);
        }
    }

    private void anim_slide_in_up() {
        final Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_up2);
        // 设置动画结束后保留结束状态
        anim.setFillAfter(false);
        Log.d(TAG, "alpha: ");
        keyboardView.startAnimation(anim);
    }

    private void anim_slide_out_down() {
        final Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_down2);
        // 设置动画结束后保留结束状态
        anim.setFillAfter(false);
        Log.d(TAG, "alpha: ");
        keyboardView.startAnimation(anim);
    }
}