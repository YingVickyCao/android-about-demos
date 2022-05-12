package com.hades.example.android.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

public class TextClockFragment extends Fragment {
    private TextClock textClock_default;
    private TextView textClockInfo_default;

    private TextClock textClock_custom;
    private TextView textClockInfo_custom;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_text_clock, container, false);
        textClock_default = view.findViewById(R.id.textClock_default);
        textClockInfo_default = view.findViewById(R.id.textClockInfo_default);

        textClock_custom = view.findViewById(R.id.textClock_custom);
        textClockInfo_custom = view.findViewById(R.id.textClockInfo_custom);
        return view;
    }
}
