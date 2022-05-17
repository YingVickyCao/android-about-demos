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

        //  代码设置font：Typeface （>=26）
        TextView fontText = view.findViewById(R.id.fontText);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Use single font
            Typeface typeface = getResources().getFont(R.font.consolas_bold_italic);
            fontText.setTypeface(typeface);

            // Use font family
//            fontText.setTypeface(getResources().getFont(R.font.consolas_fonts_since_api_26), Typeface.BOLD_ITALIC);

            // 设置monospace字体
//            fontText.setTypeface(Typeface.MONOSPACE);
        }

        // 代码设置font：Typeface + Support library 支持库. （>=16）
        TextView fontText2 = view.findViewById(R.id.fontText2);
        // Use single font
        Typeface typeface2 = ResourcesCompat.getFont(getActivity(), R.font.consolas_bold_italic);
        fontText2.setTypeface(typeface2);

        // Use font family
//        fontText2.setTypeface(ResourcesCompat.getFont(getActivity(), R.font.consolas_fonts_since_api_16), Typeface.BOLD_ITALIC);
        return view;
    }
}