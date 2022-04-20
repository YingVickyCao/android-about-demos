package com.hades.example.android.widget._edittext;

import android.app.Activity;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.hades.example.android.R;

public class TestKeyBoardActivity extends Activity {

    private EditText editText;
    private KeyboardView keyboardView;

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
                    int inputback = editText.getInputType();
                    editText.setInputType(InputType.TYPE_NULL);
                    showKeyboard();
                    new KeyboardUtil(keyboardView, editText).showKeyboard();
                    editText.setInputType(inputback);
                } else {
                    hideKeyboard();
                }
                return false;
            }
        });
    }

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