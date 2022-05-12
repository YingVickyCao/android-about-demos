package com.hades.example.android.resource.font;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;


public class TestFontFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.res_font, container, false);

//        //  通过代码设置font
//        //  直接使用font
        TextView fontText = view.findViewById(R.id.fontText);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Typeface typeface = getResources().getFont(R.font.consolas_bold);
            fontText.setTypeface(typeface);
        }

        //  使用Support library 支持库
        TextView fontText2 = view.findViewById(R.id.fontText2);
        Typeface typeface2 = ResourcesCompat.getFont(getActivity(), R.font.consolas_bold);
        fontText2.setTypeface(typeface2);
        return view;
    }
}