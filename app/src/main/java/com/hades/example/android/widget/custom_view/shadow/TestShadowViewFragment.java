package com.hades.example.android.widget.custom_view.shadow;

import android.graphics.Outline;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.shape.MaterialShapeDrawable;
import com.hades.example.android.R;

import static com.google.android.material.shape.MaterialShapeDrawable.*;

public class TestShadowViewFragment extends Fragment {
    private ImageView imageView2;
    private ImageView imageView3;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private TextView elevationNum;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_view_with_shadow, container, false);
        imageView2 = view.findViewById(R.id.imageView2);
        imageView3 = view.findViewById(R.id.imageView3);
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);
        elevationNum = view.findViewById(R.id.elevationNum);

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
        // TODO:not work
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
        materialShapeDrawable.setElevation(getResources().getDimension(R.dimen.size_10));
        materialShapeDrawable.setShadowColor(getResources().getColor(R.color.red));
        materialShapeDrawable.setShadowCompatibilityMode(SHADOW_COMPAT_MODE_ALWAYS);
        imageView3.setBackground(materialShapeDrawable);
        button3.setBackground(materialShapeDrawable);


        elevationNum.setText(String.valueOf(button4.getElevation()));
        view.findViewById(R.id.elevationPlus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button4.setElevation(button4.getElevation() + 1);
                elevationNum.setText(String.valueOf(button4.getElevation()));
            }
        });
        view.findViewById(R.id.elevationMinus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button4.setElevation(button4.getElevation() - 1);
                elevationNum.setText(String.valueOf(button4.getElevation()));
            }
        });
        return view;
    }
}