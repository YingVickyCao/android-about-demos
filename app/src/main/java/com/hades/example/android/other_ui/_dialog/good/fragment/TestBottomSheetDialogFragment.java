package com.hades.example.android.other_ui._dialog.good.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hades.example.android.R;

public class TestBottomSheetDialogFragment extends BottomSheetDialogFragment {
    public static final String TAG = "TestBottomSheetDialogFragment";
    private String uniqueKey;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: " + printDialog()); // onCreate: dialog is null
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: " + printDialog()); // onCreateDialog: dialog is null
        return inflater.inflate(R.layout.fragment_bottom_sheet_dialog_fragment, container, false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog: " + printDialog()); // onCreateView:  dialog is hide
        return new BottomSheetDialog(getContext(), R.style.CustomBottomSheetDialogFragmentStyle);
    }

    public void setUniqueKey(String uniqueKey) {
        Log.d(TAG, "setUniqueKey: " + uniqueKey + "," + printDialog());
        this.uniqueKey = uniqueKey;
    }

    public String getUniqueKey() {
        Log.d(TAG, "getUniqueKey: " + uniqueKey + "," + printDialog());
        return uniqueKey;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: " + printDialog());// onStart:  dialog is show
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " + printDialog()); // onResume:  dialog is show
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: " + printDialog()); // dialog is hide
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: " + printDialog()); // dialog is hide
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: " + printDialog());  // dialog is null
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: " + printDialog());  // dialog is null
    }

    private String printDialog() {
        if (getDialog() == null) {
            return "dialog is null";
        }
        return getDialog().isShowing() ? " dialog is show" : " dialog is hide";
    }
}