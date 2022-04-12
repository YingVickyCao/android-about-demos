package com.hades.example.android.widget.keyboard;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.hades.example.android.R;
import com.hades.example.android.lib.base.BaseFragment;

public class TestKeyBoardViewFragment extends BaseFragment {

    private KeyboardView mKeyBoardView;
    private Keyboard mKeyboard;

    private EditText editText;
    private KeyboardView.OnKeyboardActionListener onKeyboardActionListener;
    private InputMethodManager inputMethodManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_keyboardview, container, false);
        mKeyBoardView = view.findViewById(R.id.keyBoardView);
        editText = view.findViewById(R.id.editText);
        view.findViewById(R.id.show).setOnClickListener(view1 -> show());
        view.findViewById(R.id.hide).setOnClickListener(view1 -> hide());
        setKeyBoardView();
        return view;
    }

    private void show() {
        mKeyBoardView.setVisibility(View.VISIBLE);
    }

    private void hide() {
        mKeyBoardView.setVisibility(View.GONE);
    }

    private void setKeyBoardView() {
//        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        mKeyboard = new Keyboard(getActivity(), R.xml.xml_keyboard_numbers);
        for (Keyboard.Key key : mKeyboard.getKeys()) {
            if (Keyboard.KEYCODE_DELETE == key.codes[0]) {
                key.icon = getResources().getDrawable(R.drawable.delete);
            }
        }

        mKeyBoardView.setKeyboard(mKeyboard);
        mKeyBoardView.setEnabled(true);
        mKeyBoardView.setPreviewEnabled(true);
        mKeyBoardView.setOnKeyboardActionListener(onKeyboardActionListener);

//        editText.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (!editText.hasFocus()) {
//                    int type = editText.getInputType();
//                    editText.setShowSoftInputOnFocus(false);
//                    mKeyBoardView.setVisibility(View.VISIBLE);
//                    editText.setInputType(type);
//                }
//                return false;
//            }
//        });

//        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean flag) {
//                if (!flag) {
//                    mKeyBoardView.setVisibility(View.GONE);
//                } else {
//                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                }
//            }
//        });

        onKeyboardActionListener = new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int i) {

            }

            @Override
            public void onRelease(int i) {

            }

            @Override
            public void onKey(int i, int[] ints) {

            }

            @Override
            public void onText(CharSequence charSequence) {

            }

            @Override
            public void swipeLeft() {

            }

            @Override
            public void swipeRight() {

            }

            @Override
            public void swipeDown() {

            }

            @Override
            public void swipeUp() {

            }
        };
    }
}
