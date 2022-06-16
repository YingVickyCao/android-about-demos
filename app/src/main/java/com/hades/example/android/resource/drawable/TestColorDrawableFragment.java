package com.hades.example.android.resource.drawable;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

public class TestColorDrawableFragment extends Fragment {
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.res_drawable_color, container, false);
        textView = view.findViewById(R.id.tv);

        ColorDrawable drawable = new ColorDrawable();
        drawable.setColor(0xFFFF0000);

        // 错误使用：
//        drawable.setColor(0x00FF0000);  // 最终color 是 0x00FF0000 -> 透明色

        // 错误使用：
//        drawable.setColor(0x00FF0000);
//        drawable.setAlpha(255);       // 最终color 是 0x00FF0000 -> 透明色
        textView.setBackground(drawable);
        return view;
    }
}
