package com.hades.example.android.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

public class TestImageButtonFragment extends Fragment {
    ImageButton btn1;
    ImageButton btn2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_image_button_page, container, false);
        btn1 = view.findViewById(R.id.btn1);
        view.findViewById(R.id.enable_true).setOnClickListener(v -> {
            btn1.setEnabled(true);
            btn1.setClickable(true);
        });

        view.findViewById(R.id.enable_false).setOnClickListener(v -> {
            btn1.setEnabled(false);
            btn1.setClickable(false);
        });

        btn2 = view.findViewById(R.id.btn2);
        view.findViewById(R.id.selected_true).setOnClickListener(v -> {
            btn2.setSelected(true);
            btn2.setClickable(true);
        });

        view.findViewById(R.id.selected_false).setOnClickListener(v -> {
            btn2.setSelected(false);
            btn2.setClickable(false);
        });
        return view;
    }
}
