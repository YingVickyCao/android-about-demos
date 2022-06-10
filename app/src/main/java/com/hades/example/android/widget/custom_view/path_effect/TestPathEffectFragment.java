package com.hades.example.android.widget.custom_view.path_effect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;
import com.hades.example.android.widget.custom_view.TestCanvasPaintFragment;

public class TestPathEffectFragment extends Fragment {
    private static final String TAG = TestCanvasPaintFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.widget_custom_view_of_path_effect, container, false);
    }
}