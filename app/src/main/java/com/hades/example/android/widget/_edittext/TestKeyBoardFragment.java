package com.hades.example.android.widget._edittext;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
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
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

public class TestKeyBoardFragment extends Fragment {
    private static final String TAG = "TestKeyBoardFragment";

    private EditText editText;
    private KeyboardView keyboardView;
    private Keyboard keyboard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_edittext_keyboardview, container, false);
        editText = (EditText) view.findViewById(R.id.editText);
        keyboardView = (KeyboardView) view.findViewById(R.id.keyboardView);

        hideKeyboard();
        forbidSystemKeyBoard();
        initKeyboard();

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Fix：反复点击EditText，点击的太快，键盘会闪。
                // 解决：增加200ms的重复事件的过滤
                if (ButtonUtils.isFastClick()) {
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
                if (ButtonUtils.isFastClick()) {
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
//        keyboard = new Keyboard(editText.getContext(), R.xml.keyboard_characters);
        keyboard = new Keyboard(editText.getContext(), R.xml.keyboard_numbers);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(false); // 开启和关闭点击Key的预览效果
        keyboardView.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void swipeUp() {
            }

            @Override
            public void swipeRight() {
            }

            @Override
            public void swipeLeft() {
            }

            @Override
            public void swipeDown() {
            }

            @Override
            public void onText(CharSequence text) {
            }

            @Override
            public void onRelease(int primaryCode) {
            }

            @Override
            public void onPress(int primaryCode) {
            }

            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                Editable editable = editText.getText();
                int start = editText.getSelectionStart();
                switch (primaryCode) {
                    case Keyboard.KEYCODE_DELETE:
                        if (editable != null && editable.length() > 0) {
                            if (start > 0) {
                                editable.delete(start - 1, start);
                            }
                        }
                        break;
                    case Keyboard.KEYCODE_CANCEL:
                        hideKeyboard();
                        break;

                    default:
                        editable.insert(start, Character.toString((char) primaryCode));
                        break;
                }
            }
        });
    }

    // Activity中获取焦点时调用，显示出键盘
    public void showKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            // 隐藏键盘时，添加动画，增加流畅度
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            // 隐藏键盘时，添加动画，增加流畅度
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