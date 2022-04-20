package com.hades.example.android.widget._edittext;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.hades.example.android.R;
import com.hades.example.android.lib.base.BaseFragment;

public class TestKeyBoardViewFragment extends BaseFragment {
    private static final String TAG = "TestKeyBoardViewFragment";

    private KeyboardView mKeyBoardView;
    private Keyboard mKeyboard;

    private EditText editText;
    private KeyboardView.OnKeyboardActionListener onKeyboardActionListener;
    private InputMethodManager inputMethodManager;
    private int KEY_ENTER_CODE = 13;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_edittext_add_keyboardview, container, false);
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
                key.icon = getResources().getDrawable(R.drawable.ic_delete);
            }
        }

        mKeyBoardView.setKeyboard(mKeyboard);
        mKeyBoardView.setVisibility(View.VISIBLE);
        mKeyBoardView.setEnabled(true);
        mKeyBoardView.setPreviewEnabled(false);
        mKeyBoardView.setOnKeyboardActionListener(new KeyboardView.OnKeyboardActionListener() {
            @Override
            public void onPress(int primaryCode) {
                Log.d(TAG, "onPress: ");
            }

            @Override
            public void onRelease(int primaryCode) {
                Log.d(TAG, "onRelease: ");
                if (KEY_ENTER_CODE == primaryCode) {
//                    if (null != onEnterListener) {
//                        onEnterListener.enter();
//                    }
                    hideKeyboard();
                }
            }

            @Override
            public void onKey(int primaryCode, int[] keyCodes) {
                Log.d(TAG, "onKey: primaryCode=" + primaryCode);
            }

            @Override
            public void onText(CharSequence text) {
                Log.d(TAG, "onText: ");
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
        });
    }

    public void showKeyboard() {
        int visibility = mKeyBoardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            mKeyBoardView.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard() {
        int visibility = mKeyBoardView.getVisibility();
        if (visibility == View.VISIBLE) {
            mKeyBoardView.setVisibility(View.INVISIBLE);
        }
    }

}
