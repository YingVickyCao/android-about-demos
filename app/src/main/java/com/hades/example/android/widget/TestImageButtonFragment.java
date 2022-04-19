package com.hades.example.android.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

public class TestImageButtonFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_image_button_page, container, false);
        view.findViewById(R.id.testEnable).setEnabled(true);
        view.findViewById(R.id.testEnable).setClickable(true);
        view.findViewById(R.id.testEnable2).setEnabled(true);
        view.findViewById(R.id.testEnable2).setClickable(true);
        return view;
    }
}
