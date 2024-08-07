package com.hades.example.android.resource.drawable;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.hades.example.android.R;
import com.hades.example.android.base.BaseFragment;

public class TestLayerDrawableFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.res_drawable_layer, container, false);
        view.findViewById(R.id.btn).setOnClickListener(v -> btn());
        return view;
    }

    private void btn() {
//        View red = view.findViewById(R.id.red); // not work
        if (null != getActivity()) {
            LayerDrawable layerDrawable = (LayerDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.drawable_layer_list_4_normal_item);
            if (null != layerDrawable) {
                Drawable red = layerDrawable.findDrawableByLayerId(R.id.red);
            }
        }
//        red.setVisible(false,true);// not work
    }
}