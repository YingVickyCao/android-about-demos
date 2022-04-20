package com.hades.example.android.widget._edittext;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.hades.example.android.R;

public class TestKeyBoardActivity extends Activity {

    private EditText editText;
    private KeyboardView keyboardView;
    private Keyboard keyboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_edittext_add_keyboardview2);
        editText = (EditText) findViewById(R.id.editText);
        keyboardView = (KeyboardView) findViewById(R.id.keyboardView);
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!editText.hasFocus()) {
                    int inputType = editText.getInputType();
                    editText.setInputType(InputType.TYPE_NULL);
                    showKeyboard();
                    init();
                    editText.setInputType(inputType);
                } else {
                    hideKeyboard();
                }
                return false;
            }
        });
    }

    private void init() {
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
                        keyboardView.setVisibility(View.GONE);
                        editText.clearFocus();
                        break;

                    default:
                        editable.insert(start, Character.toString((char) primaryCode));
                        break;
                }
            }
        });
        keyboard = new Keyboard(editText.getContext(), R.xml.qwerty);
        keyboardView.setKeyboard(keyboard);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(false);
    }

    // Activity中获取焦点时调用，显示出键盘
    public void showKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            keyboardView.setVisibility(View.INVISIBLE);
        }
    }
}