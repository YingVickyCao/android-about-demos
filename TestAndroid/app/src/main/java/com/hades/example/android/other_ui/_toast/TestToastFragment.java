package com.hades.example.android.other_ui._toast;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

/*
  https://www.jianshu.com/p/ca8d7dd6172e
 */
public class TestToastFragment extends Fragment {
    private View root;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.toast_layout, container, false);
        view.findViewById(R.id.showNormalToast).setOnClickListener(v -> toast());
        view.findViewById(R.id.androidToast).setOnClickListener(v -> androidToast());
        view.findViewById(R.id.showCustomToast).setOnClickListener(v -> showCustomToast());
        view.findViewById(R.id.toast_without_icon_in_dark_theme).setOnClickListener(v -> toastWithoutIconInDarkTheme());
        view.findViewById(R.id.toast_without_icon_in_light_theme).setOnClickListener(v -> toastWithoutIconInLightTheme());
        view.findViewById(R.id.toast_with_icon_in_dark_theme).setOnClickListener(v -> toastWithIconInDarkTheme());
        view.findViewById(R.id.toast_with_icon_in_light_theme).setOnClickListener(v -> toastWithIconInLightTheme());
        view.findViewById(R.id.toast_with_icon_in_light_theme2).setOnClickListener(v -> toastWithIconInLightTheme2());
        view.findViewById(R.id.toast_with_icon_in_light_theme3).setOnClickListener(v -> toastWithIconInLightTheme3());
        root = view.findViewById(R.id.root);
        return view;
    }

    private Context getUsedContext() {
        return getActivity();
    }

    private void toast() {
        Toast.makeText(getUsedContext(), "简单的提示信息", Toast.LENGTH_SHORT).show();
    }

    private void androidToast() {
        Toast toast = new Toast(getUsedContext());
//        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(LayoutInflater.from(getUsedContext()).inflate(R.layout.toast_custom_android, null));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    private void showCustomToast() {
        Toast toast = new Toast(getUsedContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(getToastView());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    private View getToastView() {
        ImageView image = new ImageView(getUsedContext());
        image.setImageResource(R.drawable.ic_launcher);

        LinearLayout ll = new LinearLayout(getUsedContext());
        ll.addView(image);
        TextView textView = new TextView(getUsedContext());
        textView.setText("带图片的提示信息");
        textView.setTextSize(24);
        textView.setTextColor(Color.MAGENTA);
        ll.addView(textView);

        return ll;
    }

    private void toastWithoutIconInDarkTheme() {
        root.setBackgroundColor(getResources().getColor(R.color.dark_bg_0, null));
        Toast toast = new Toast(getUsedContext());
        toast.setGravity(Gravity.CENTER, 0, 0);

        toast.setView(LayoutInflater.from(getUsedContext()).inflate(R.layout.toast_custom_without_icon_dark, null));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }


    private void toastWithoutIconInLightTheme() {
        root.setBackgroundColor(getResources().getColor(R.color.light_bg_0, null));

        Toast toast = new Toast(getUsedContext());
        toast.setGravity(Gravity.CENTER, 0, 0);

        toast.setView(LayoutInflater.from(getUsedContext()).inflate(R.layout.toast_custom_without_icon_light, null));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    private void toastWithIconInDarkTheme() {
        root.setBackgroundColor(getResources().getColor(R.color.dark_bg_0, null));
        Toast toast = new Toast(getUsedContext());
        toast.setGravity(Gravity.CENTER, 0, 0);

        toast.setView(LayoutInflater.from(getUsedContext()).inflate(R.layout.toast_custom_with_icon_dark, null));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    private void toastWithIconInLightTheme() {
        root.setBackgroundColor(getResources().getColor(R.color.light_bg_0, null));

        Toast toast = new Toast(getUsedContext());
        toast.setGravity(Gravity.CENTER, 0, 0);

        toast.setView(LayoutInflater.from(getUsedContext()).inflate(R.layout.toast_custom_with_icon_light, null));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    private void toastWithIconInLightTheme2() {
        root.setBackgroundColor(getResources().getColor(R.color.light_bg_0, null));

        Toast toast = new Toast(getUsedContext());
        toast.setGravity(Gravity.CENTER, 0, 0);

        toast.setView(LayoutInflater.from(getUsedContext()).inflate(R.layout.toast_custom_with_icon_light2, null));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    private void toastWithIconInLightTheme3() {
        root.setBackgroundColor(getResources().getColor(R.color.light_bg_0, null));

        Toast toast = new Toast(getUsedContext());
        toast.setGravity(Gravity.CENTER, 0, 0);

        toast.setView(LayoutInflater.from(getUsedContext()).inflate(R.layout.toast_custom_with_icon_light3, null));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
