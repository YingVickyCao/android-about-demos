package com.hades.example.android.resource.drawable.bitmap.change_icon_render_color;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
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

    private ImageView imageview3;
    private ImageView imageview4;
    private ImageView imageview5;
    private ImageView imageview6;

    private ViewGroup mImageView_svg4Container;

    private boolean isSelected = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.res_drawable_bitmap_tint, container, false);
        imageview3 = view.findViewById(R.id.imageview3);
        imageview4 = view.findViewById(R.id.imageview4);
        imageview5 = view.findViewById(R.id.imageview5);
        imageview6 = view.findViewById(R.id.imageview6);

        view.findViewById(R.id.changeColorByTint).setOnClickListener(v -> changeColorByTint());

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

        mImageView_svg.setImageDrawable(AppCompatResources.getDrawable(getActivity(), R.drawable.ic_svg_add));
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
        Log.d(TAG, "enableSvg: svg4 enable=" + mImageView_svg4.isEnabled());
    }

    private void disableSvg() {
        mImageView_svg2.setEnabled(false);
        mImageView_svg21.setEnabled(false);
        mImageView_svg3.setEnabled(false);
        mImageView_svg4Container.setEnabled(false);
        Log.d(TAG, "disableSvg: svg4 enable=" + mImageView_svg4.isEnabled());
    }

    private void clickSvg4() {
        Toast.makeText(getActivity(), "Click svg", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "clickSvg4: svg4 enable=" + mImageView_svg4.isEnabled());
    }

    private void changeColorByTint() {
        // Android>=6.0
        Drawable drawable = getResources().getDrawable(R.drawable.ic_svg_adjust, getContext().getTheme());
        imageview3.setImageDrawable(drawable);
        // 设置单个颜色
        imageview3.getDrawable().setTint(getResources().getColor(R.color.red, getContext().getTheme()));
        // 假如imageview3 和 imageview4 的颜色变成了同一个，要mutate().
//        imageview3.getDrawable().mutate();

        // Android>=6.0
        Drawable drawable2 = getResources().getDrawable(R.drawable.ic_svg_adjust, getContext().getTheme());
        imageview4.setImageDrawable(drawable2);
        // 设置一组selector颜色
        imageview4.getDrawable().setTintList(getResources().getColorStateList(R.color.textview_color_enable, getContext().getTheme()));
        imageview4.setOnClickListener(v -> imageview4.setSelected(!imageview4.isSelected()));
//        imageview4.getDrawable().mutate();

        // 使用DrawableCompat兼容Android<6.0
        Drawable drawable3 = ContextCompat.getDrawable(getContext(), R.drawable.ic_svg_adjust);
        Drawable tintDrawable3 = DrawableCompat.wrap(drawable3).mutate();
        DrawableCompat.setTint(tintDrawable3, getResources().getColor(R.color.red, getContext().getTheme()));
        imageview5.setImageDrawable(tintDrawable3);

        // 使用DrawableCompat兼容Android<6.0
        Drawable drawable4 = ContextCompat.getDrawable(getContext(), R.drawable.ic_svg_adjust);
        Drawable tintDrawable4 = DrawableCompat.wrap(drawable4).mutate();
        DrawableCompat.setTintList(tintDrawable4, ContextCompat.getColorStateList(getContext(), R.color.textview_color_enable));
        imageview6.setImageDrawable(tintDrawable4);
        imageview6.setOnClickListener(v -> imageview6.setSelected(!imageview6.isSelected()));
    }

}