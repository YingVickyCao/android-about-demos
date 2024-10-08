package com.hades.example.android.widget.view_animator.adapterviewflipper.v1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterViewFlipper1Fragment extends Fragment {
    AdapterViewFlipper adapterViewFlipper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_adapterviewflipper, container, false);
        adapterViewFlipper = (AdapterViewFlipper) view.findViewById(R.id.adapterViewFlipper1);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    private void initView() {
        ViewFlipperAdapter viewFlipperAdapter = new ViewFlipperAdapter(getActivity(), buildData());
        adapterViewFlipper.setAdapter(viewFlipperAdapter);
        adapterViewFlipper.setFlipInterval(1000);
        adapterViewFlipper.setAutoStart(false);
        adapterViewFlipper.startFlipping();
    }

    private List<FlipperItem> buildData() {
        List<FlipperItem> items = new ArrayList<>();
        items.add(new FlipperItem(R.drawable.image_1, "Image 1"));
        items.add(new FlipperItem(R.drawable.image_2, "Image 2"));
        items.add(new FlipperItem(R.drawable.image_3, "Image 3"));
        items.add(new FlipperItem(R.drawable.image_4, "Image 4"));
        items.add(new FlipperItem(R.drawable.image_5, "Image 5"));
        items.add(new FlipperItem(R.drawable.image_6, "Image 6"));
        return items;
    }
}
