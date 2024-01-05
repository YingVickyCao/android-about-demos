package com.hades.example.android.widget.view_animator.adapterviewflipper.v3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;
import com.hades.example.android.tools.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class AdapterViewFlipper3Fragment extends Fragment {
    MyAdapterViewFlipper adapterViewFlipper1;
    MyAdapterViewFlipper adapterViewFlipper2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_adapterviewflipper_v3, container, false);
        adapterViewFlipper1 = (MyAdapterViewFlipper) view.findViewById(R.id.adapterViewFlipper1);
        adapterViewFlipper2 = (MyAdapterViewFlipper) view.findViewById(R.id.adapterViewFlipper2);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView1();
        initView2();
    }

    private void initView1() {
        ViewFlipperAdapter<FlipperItem> viewFlipperAdapter = new ViewFlipperAdapter<>(getActivity(), R.layout.adapterviewflipper_item1, buildData1()) {

            @Override
            protected void convert(ViewHolder baseViewHolder, FlipperItem flipperItem) {
                TextView textView = (TextView) baseViewHolder.getViewById(R.id.name);
                textView.setText(flipperItem.name);
                ImageView imageView = (ImageView) baseViewHolder.getViewById(R.id.image);
                imageView.setImageResource(flipperItem.getResId());
            }
        };
        adapterViewFlipper1.setAdapter(viewFlipperAdapter);
        adapterViewFlipper1.setFlipInterval(1000);
        adapterViewFlipper1.setAutoStart(false);
        adapterViewFlipper1.startFlipping();
    }

    private void initView2() {
        ViewFlipperAdapter<Integer> viewFlipperAdapter = new ViewFlipperAdapter<>(getActivity(), R.layout.adapterviewflipper_item2, buildData2()) {

            @Override
            protected void convert(ViewHolder baseViewHolder, @ColorRes Integer item) {
                TextView textView = (TextView) baseViewHolder.getViewById(R.id.name);
                textView.setText(String.valueOf(item));
                baseViewHolder.getConvertView().setBackgroundColor(getResources().getColor(item, getContext().getTheme()));
            }
        };
        adapterViewFlipper2.setAdapter(viewFlipperAdapter);
        adapterViewFlipper2.setFlipInterval(1000);
        adapterViewFlipper2.setAutoStart(false);
        adapterViewFlipper2.setAutoFlip(false);
        adapterViewFlipper2.setLoopViews(false);
    }

    private List<FlipperItem> buildData1() {
        List<FlipperItem> items = new ArrayList<>();
        items.add(new FlipperItem(R.drawable.image_1, "Image 1"));
        items.add(new FlipperItem(R.drawable.image_2, "Image 2"));
        items.add(new FlipperItem(R.drawable.image_3, "Image 3"));
        items.add(new FlipperItem(R.drawable.image_4, "Image 4"));
        items.add(new FlipperItem(R.drawable.image_5, "Image 5"));
        items.add(new FlipperItem(R.drawable.image_6, "Image 6"));
        return items;
    }

    private List<Integer> buildData2() {
        List<Integer> items = new ArrayList<>();
        items.add(R.color.blue);
        items.add(R.color.green);
        items.add(R.color.s_0_e_red_light);
        items.add(R.color.btn_default_normal_holo_light);
        items.add(R.color.btn_default_disabled_holo_light);
        items.add(R.color.btn_default_pressed_holo_light);
        return items;
    }
}
