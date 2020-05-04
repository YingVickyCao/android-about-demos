package com.hades.example.android.resource._style_theme;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import com.hades.example.android.Constant;
import com.hades.example.android.R;
import com.squareup.picasso.Picasso;

public class TestTintFragment extends Fragment {
    private static final String TAG = TestTintFragment.class.getSimpleName();

    private ImageView mImageView_svg;
    private ImageView mImageView_png_url;
    private ImageView mImageView_png_url_2;

    private ImageView mImageView_svg2;
    private ImageView mImageView_svg21;
    private ImageView mImageView_svg3;
    private ImageView mImageView_svg4;
    private ViewGroup mImageView_svg4Container;

    private boolean isSelected = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.res_tint, container, false);
        test_selected(view);
        test_enable_pressed_state(view);
        return view;
    }

    private void test_selected(View view) {
        /**
         * android:tint="@color/menu_icon_color" 改变menu color?
         * API 21    Android 5.0      ✗
         * API 22    Android 5.1      ✗
         * API 23    Android 6.0      ✓
         * API 24    Android 7.0      ✓
         * API 25    Android 7.1      ✓
         * API 26    Android 8.0      ✓
         * API 27    Android 8.1      ✓
         * API 28    Android 9.0      ✓
         */
        mImageView_svg = view.findViewById(R.id.svg);
        mImageView_png_url = view.findViewById(R.id.png_url);
        mImageView_png_url_2 = view.findViewById(R.id.png_url2);

        mImageView_svg.setImageDrawable(AppCompatResources.getDrawable(getActivity(), R.drawable.drawable_vector_add));
        mImageView_svg.getDrawable().mutate();

        //非透明，not ok
        Picasso.with(getActivity()).load(Constant.IMAGE_URL).placeholder(R.drawable.ic_launcher).fit().into(mImageView_png_url);
        // 透明, ok
        Picasso.with(getActivity()).load(Constant.IMAGE_URL2).placeholder(R.drawable.ic_launcher).fit().into(mImageView_png_url_2);

        view.findViewById(R.id.switchMenuColor).setOnClickListener(v -> toggleSelected());
    }

    private void toggleSelected() {
        mImageView_svg.setSelected(isSelected);
        mImageView_png_url.setSelected(isSelected);
        mImageView_png_url_2.setSelected(isSelected);

        isSelected = !isSelected;
    }

    private void test_enable_pressed_state(View view) {
        mImageView_svg2 = view.findViewById(R.id.svg2);
        mImageView_svg21 = view.findViewById(R.id.svg21);
        mImageView_svg3 = view.findViewById(R.id.svg3);
        mImageView_svg4 = view.findViewById(R.id.svg4);
        mImageView_svg4Container = view.findViewById(R.id.svg4Container);

        view.findViewById(R.id.enableSvg).setOnClickListener(v -> enableSvg());
        view.findViewById(R.id.disableSvg).setOnClickListener(v -> disableSvg());
        view.findViewById(R.id.svg4Container).setOnClickListener(v -> clickSvg4());
    }

    private void enableSvg() {
        mImageView_svg2.setEnabled(true);
        mImageView_svg21.setEnabled(true);
        mImageView_svg3.setEnabled(true);
        mImageView_svg4Container.setEnabled(true);
        Log.d(TAG, "enableSvg: svg4 enable="+ mImageView_svg4.isEnabled());
    }

    private void disableSvg() {
        mImageView_svg2.setEnabled(false);
        mImageView_svg21.setEnabled(false);
        mImageView_svg3.setEnabled(false);
        mImageView_svg4Container.setEnabled(false);
        Log.d(TAG, "disableSvg: svg4 enable="+ mImageView_svg4.isEnabled());
    }

    private void clickSvg4() {
        Toast.makeText(getActivity(), "Click svg", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "clickSvg4: svg4 enable="+ mImageView_svg4.isEnabled());
    }

}