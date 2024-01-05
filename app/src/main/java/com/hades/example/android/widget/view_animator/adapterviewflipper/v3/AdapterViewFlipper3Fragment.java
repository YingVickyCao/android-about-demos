package com.hades.example.android.widget.view_animator.adapterviewflipper.v3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterViewFlipper3Fragment extends Fragment {
    MyAdapterViewFlipper adapterViewFlipper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_adapterviewflipper_custom, container, false);
        adapterViewFlipper = (MyAdapterViewFlipper) view.findViewById(R.id.adapterViewFlipper);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    private void initView() {
        ViewFlipperAdapter<FlipperItem> viewFlipperAdapter = new ViewFlipperAdapter<>(getActivity(), R.layout.custom_adapter_layout, buildData()) {

            @Override
            protected void convert(ViewHolder baseViewHolder, FlipperItem flipperItem) {
                TextView textView = (TextView) baseViewHolder.getViewById(R.id.name);
                textView.setText(flipperItem.name);
                ImageView imageView = (ImageView) baseViewHolder.getViewById(R.id.image);
                imageView.setImageResource(flipperItem.getResId());
            }
        };
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
