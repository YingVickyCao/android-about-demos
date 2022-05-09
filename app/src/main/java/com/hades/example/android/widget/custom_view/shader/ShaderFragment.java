package com.hades.example.android.widget.custom_view.shader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

public class ShaderFragment extends Fragment {
    private static final String TAG = ShaderFragment.class.getSimpleName();
private RectWithShader rectWithShader;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_custom_view_of_shader, container, false);
        rectWithShader = view.findViewById(R.id.rectWithShader);
        view.findViewById(R.id.bitmapShader).setOnClickListener(v -> bitmapShader());
        view.findViewById(R.id.linearGradient).setOnClickListener(v -> linearGradient());
        view.findViewById(R.id.radialGradient).setOnClickListener(v -> radialGradient());
        view.findViewById(R.id.sweepGradient).setOnClickListener(v -> sweepGradient());
        view.findViewById(R.id.composeShader).setOnClickListener(v -> composeShader());
        return view;
    }

    private void bitmapShader() {
        rectWithShader.useBitmapShader();
    }

    private void linearGradient() {
        rectWithShader.useLinearGradient();
    }

    private void radialGradient() {
        rectWithShader.useRadialGradient();
    }

    private void sweepGradient() {
        rectWithShader.useSweepGradient();
    }

    private void composeShader() {
        rectWithShader.useComposeShader();
    }
}