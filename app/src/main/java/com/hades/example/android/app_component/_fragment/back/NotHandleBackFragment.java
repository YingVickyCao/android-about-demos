package com.hades.example.android.app_component._fragment.back;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hades.example.android.R;

public class NotHandleBackFragment extends Fragment implements IBack {
    public static final String TAG = "NotHandleBackFragment";

    public NotHandleBackFragment() {
    }

    public static NotHandleBackFragment newInstance() {
        NotHandleBackFragment fragment = new NotHandleBackFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_back_not_handle, container, false);
        view.findViewById(R.id.back).setOnClickListener(v -> clickBack());
        return view;
    }

    private void clickBack() {
        requireActivity().onBackPressed();
    }

    @Override
    public boolean handleBack() {
        return false;
    }
}