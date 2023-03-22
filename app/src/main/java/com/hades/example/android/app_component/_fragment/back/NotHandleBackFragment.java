package com.hades.example.android.app_component._fragment.back;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hades.example.android.R;
import com.hades.example.android.tools.FragmentUtils;

public class NotHandleBackFragment extends Fragment implements IBack {
    public static final String TAG = "NotHandleBackFragment";

    public NotHandleBackFragment() {
    }

    public static NotHandleBackFragment newInstance() {
        NotHandleBackFragment fragment = new NotHandleBackFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_back_not_handle, container, false);
        view.findViewById(R.id.back).setOnClickListener(v -> clickBack());
        return view;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        // Way 1 :
//        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                // Click back button or Press back of system os, then this method invoked
//                {// NotHandleBackFragment will handle completely
//                    // If consumed event, not need to setEnabled(false)
//                    FragmentUtils.popBackStack(requireActivity());
//                }
//                // or
////                {// Finally TestBackActivity will use OnBackPressedCallback to pop this fragment
////                    this.setEnabled(false);
////                    requireActivity().getOnBackPressedDispatcher().onBackPressed();
////                }
//            }
//        });
//    }


    //    private void clickBack() {
    // // requireActivity().onBackPressed() is depressed
//        requireActivity().onBackPressed();
//    }

    private void clickBack() {
        // requireActivity().onBackPressed() is depressed
        requireActivity().getOnBackPressedDispatcher().onBackPressed();
    }

    @Override
    public boolean handleBack() {
        return false;
    }
}