package com.hades.example.android.widget.textview;

import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

public class LinkInTextViewFragment extends Fragment {
    private static final String TAG = "LinkInTextViewFragment";
    private final String FULL_TEXT = "VisitPage www.baidu.com";
    private final String URL_TEXT = "www.baidu.com";
    private final int start = 10;
    private final int end = 23;

    private TextView link2;
    private TextView link3;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_textview_4_link_in_text, container, false);
        link2 = view.findViewById(R.id.link2);
        link3 = view.findViewById(R.id.link3);
        setLink2();
        setLink3();
        return view;
    }

    private void setLink2() {
//        link2.setText(Html.fromHtml("VisitPage www.baidu.com", Html.FROM_HTML_MODE_COMPACT)); // ok
        link2.setText(Html.fromHtml("VisitPage <a href='http://www.baidu.com'>www.baidu.com</a>", Html.FROM_HTML_MODE_COMPACT)); // ok
    }


    private void setLink3() {
        SpannableString spannableString = new SpannableString(FULL_TEXT);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Log.d(TAG, "onClick: ");
                Toast.makeText(getContext(), "Clicked http://www.baidu.com", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setUnderlineText(true); //true : 超链接有下划线
                // 设置超链接的文字颜色
//                int color = getResources().getColor(android.R.color.holo_blue_light, getContext().getTheme());
//                ds.setColor(color);
            }
        }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        // 设置超链接的文字颜色
        int color = getResources().getColor(android.R.color.holo_red_light, getContext().getTheme());
        /**
         * flags : 
         * Spannable. SPAN_INCLUSIVE_EXCLUSIVE：前面包括，后面不包括，即在文本前插入新的文本会应用该样式，而在文本后插入新文本不会应用该样式
         * Spannable. SPAN_INCLUSIVE_INCLUSIVE：前面包括，后面包括，即在文本前插入新的文本会应用该样式，而在文本后插入新文本也会应用该样式
         * Spannable. SPAN_EXCLUSIVE_EXCLUSIVE：前面不包括，后面不包括
         * Spannable. SPAN_EXCLUSIVE_INCLUSIVE：前面不包括，后面包括
         */
        spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        link3.setText(spannableString);
        // 设置文字可点击
        link3.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
