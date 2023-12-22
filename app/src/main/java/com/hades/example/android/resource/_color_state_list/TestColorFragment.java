package com.hades.example.android.resource._color_state_list;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hades.example.android.R;
import com.hades.example.android.base.BaseFragment;
import com.hades.utility.android.utils.ThemeUtils;

public class TestColorFragment extends BaseFragment {
    private static final String TAG = "TestColorFragment";

    private int color_red_1 = 0xFFFF0000;
    private int color_red_2 = 0xFF0000;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.res_color, container, false);

        TextView color1 = view.findViewById(R.id.color1);
        TextView color2 = view.findViewById(R.id.color2);
        TextView color3 = view.findViewById(R.id.color3);
        TextView color4 = view.findViewById(R.id.color4);
        TextView color5 = view.findViewById(R.id.color5);
        TextView color6 = view.findViewById(R.id.color6);
        TextView color7 = view.findViewById(R.id.color7);

        color1.setBackgroundColor(color_red_1);
        color2.setBackgroundColor(color_red_2); // Not working. must 0xAARRGGBB
        color3.setBackgroundColor(Color.GREEN);

        // Get color id from attr
        int colorResId2 = ThemeUtils.getDataInValueIdByAttrId(getContext(), R.attr.color1);
        color4.setBackgroundColor(colorResId2);

        int colorResId = getResources().getColor(R.color.text_color_convert_1, getResources().newTheme());
        color5.setBackgroundColor(colorResId);

        // color string to int;
        color6.setBackgroundColor(ThemeUtils.convertStringColorToInt("#80ff0000"));

        // color int to string
        int color_1 = getResources().getColor(R.color.text_color_convert_1, getResources().newTheme());
        Log.d(TAG, "onClick: " + color_1 + "," + ThemeUtils.convertIntColorToSting_ARGB(color_1)); // #80ff0000
        int color_2 = getResources().getColor(R.color.text_color_convert_2, getResources().newTheme());
        Log.d(TAG, "onClick: " + color_2 + "," + ThemeUtils.convertIntColorToSting_ARGB(color_2)); // #ff00ff00
        color7.setText(Html.fromHtml(getEmailWithColorLink(ThemeUtils.convertIntColorToSting_RGB(colorResId))));

        view.findViewById(R.id.testColorConvert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        return view;
    }

    private String getEmailWithColorLink(String color) {
//        https://www.w3school.com.cn/tags/att_font_color.asp
//        <font> 不支持ARGB，只支持RGB
        return "A email. <font color=" + color + ">" + "abc@outlook.com" + "</font>" + " .Press to send email.";
//        return "A email. <font color=" + "#ff0000" + ">" + "abc@outlook.com" + "</font>" + " .Press to send email.";
    }
}