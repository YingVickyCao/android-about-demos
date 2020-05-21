package com.hades.example.android.widget.custom_view.shadow;

import android.graphics.Outline;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

public class ShadowViewFragment extends Fragment {
    private ImageView imageView2;
    private Button button1;
    private Button button2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_view_with_shadow, container, false);
        imageView2 = view.findViewById(R.id.imageView2);
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);

        imageView2.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                int margin = Math.min(view.getWidth(), view.getHeight()) / 10;
                outline.setRoundRect(margin, margin, view.getWidth() - margin, view.getHeight(), margin / 2);
            }
        });

        button1.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
//                outline.setRect(0, 0, view.getWidth(), view.getHeight());
                // 缩小留白
                outline.setRect(button1.getPaddingLeft() / 8, 0, view.getWidth() - button1.getPaddingRight() / 8, view.getHeight() - button1.getPaddingBottom() / 2 - 2);
            }
        });


        button2.setOutlineProvider(new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                outline.setRect(0, 0, view.getWidth(), view.getHeight());
            }
        });
        return view;
    }
}