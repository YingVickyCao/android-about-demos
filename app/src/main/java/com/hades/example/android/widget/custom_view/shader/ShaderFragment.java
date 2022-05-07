package com.hades.example.android.widget.custom_view.shader;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;
import com.hades.example.android.widget.custom_view.mesh.BitmapMeshView;

public class ShaderFragment extends Fragment {
    private static final String TAG = ShaderFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_custom_view_of_shader, container, false);
        return view;
    }
}