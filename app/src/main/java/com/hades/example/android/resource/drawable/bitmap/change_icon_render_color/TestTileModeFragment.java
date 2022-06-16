package com.hades.example.android.resource.drawable.bitmap.change_icon_render_color;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

public class TestTileModeFragment extends Fragment {
    private static final String TAG = TestTileModeFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.res_drawable_bitmap_tile_mode, container, false);
    }
}