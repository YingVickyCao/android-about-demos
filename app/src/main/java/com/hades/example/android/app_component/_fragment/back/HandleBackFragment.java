package com.hades.example.android.app_component._fragment.back;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hades.example.android.R;
import com.hades.example.android.tools.FragmentUtils;

public class HandleBackFragment extends Fragment implements IBack {
    public static final String TAG = "HandleBackFragment";

    public HandleBackFragment() {
    }

    public static HandleBackFragment newInstance() {
        HandleBackFragment fragment = new HandleBackFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_back_handle, container, false);
        view.findViewById(R.id.back).setOnClickListener(v -> clickBack());
        return view;
    }

    private void clickBack() {
        requireActivity().getOnBackPressedDispatcher().onBackPressed();
//        Toast.makeText(requireActivity(), "Click Back", Toast.LENGTH_SHORT).show();
//        FragmentUtils.removedFromParent(this);
    }

    @Override
    public boolean handleBack() {
        Toast.makeText(requireActivity(), "Green page Backd", Toast.LENGTH_SHORT).show();
        FragmentUtils.removedFromParent(this);
        return true;
    }
}