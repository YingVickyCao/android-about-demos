package com.hades.example.android.resource._style_theme;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

public class TestDeclareStyleableInThemeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.res_theme_4_declare_styleable, container, false);
        LinearLayout MyViewContainer = view.findViewById(R.id.MyViewContainer);
        MyView myView = new MyView(getActivity(), null, R.attr.MyViewStyle);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) getResources().getDimension(R.dimen.size_80), (int) getResources().getDimension(R.dimen.size_80));
        myView.setLayoutParams(layoutParams);
        MyViewContainer.addView(myView);
        return view;
    }
}