package com.hades.example.android.other_ui._dialog.depressed;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hades.example.android.R;

public class CustomThemeProgressDialog extends ProgressDialog {
    private static final String TAG = CustomThemeProgressDialog.class.getSimpleName();

    View mView;
    private ProgressBar mProgress;
    private TextView mMessageView;
    private CharSequence mMessage;

    public CustomThemeProgressDialog(Context context) {
        super(context, R.style.progress_dialog);
//        super(context);
    }

    // When set theme, width of dialog is shorter.
    public CustomThemeProgressDialog(Context context, int theme) {
        super(context, theme);
        Log.d(TAG, "MyProgressDialog2: ");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
//        super.onCreate(savedInstanceState); // In some device, invorked, first opened with dark flash.

        mView = LayoutInflater.from(getContext()).inflate(R.layout.other_ui_progress_dialog, null);
        mMessageView = mView.findViewById(R.id.message);
        mProgress = mView.findViewById(R.id.progress);

        mMessageView.setText(mMessage);
        setContentView(mView);
    }

    @Override
    public void setMessage(CharSequence message) {
        if (mProgress != null) {
            Log.d(TAG, "setMessage: mMessageView,message=" + message);
            mMessageView.setText(message);
        } else {
            Log.d(TAG, "setMessage: message=" + message);
            mMessage = message;
        }
    }
}