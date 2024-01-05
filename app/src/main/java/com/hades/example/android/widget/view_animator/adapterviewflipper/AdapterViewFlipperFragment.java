package com.hades.example.android.widget.view_animator.adapterviewflipper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;
import com.hades.example.android.widget.view_animator.CustomAdapter;

public class AdapterViewFlipperFragment extends Fragment {
    AdapterViewFlipper adapterViewFlipper;

    int[] IMAGES = {
            R.drawable.image_1,
            R.drawable.image_2,
            R.drawable.image_3,
            R.drawable.image_4
    };

    String[] NAMES = {
            "Image 1",
            "Image 1",
            "Image 3",
            "Image 4"
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_adapterviewflipper, container, false);
        adapterViewFlipper = (AdapterViewFlipper) view.findViewById(R.id.adapterViewFlipper);
        CustomAdapter customAdapter = new CustomAdapter(getActivity(), NAMES, IMAGES);
        adapterViewFlipper.setAdapter(customAdapter);
        adapterViewFlipper.setFlipInterval(2600);
        adapterViewFlipper.setAutoStart(true);
        return view;
    }
}
