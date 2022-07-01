package com.hades.example.android.widget.progressbar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.hades.example.android.R;
import com.hades.example.android.base.BaseFragment;
import com.hades.example.android.widget.custom_view.CircleProgressBar;

// progress_medium
public class TestProgressBarFragment extends BaseFragment {
    ProgressBar mProgressBar;
    ProgressBar circleProgressBar;
    CircleProgressBar circleProgressBar2_1;
    CircleProgressBar circleProgressBar2_2;
    CircleProgressBar circleProgressBar2_3;
    CircleProgressBar circleProgressBar2_4;
    CircleProgressBar circleProgressBar2_5;
    CircleProgressBar circleProgressBar2_6;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_progressbar, container, false);

        mProgressBar = view.findViewById(R.id.progressBar);

        circleProgressBar = view.findViewById(R.id.circleProgressBar);

        circleProgressBar2_1 = view.findViewById(R.id.circleProgressBar2_1);
        circleProgressBar2_2 = view.findViewById(R.id.circleProgressBar2_2);
        circleProgressBar2_3 = view.findViewById(R.id.circleProgressBar2_3);
        circleProgressBar2_4 = view.findViewById(R.id.circleProgressBar2_4);
        circleProgressBar2_5 = view.findViewById(R.id.circleProgressBar2_5);
        circleProgressBar2_6 = view.findViewById(R.id.circleProgressBar2_6);

        view.findViewById(R.id.add).setOnClickListener(v -> add());
        view.findViewById(R.id.reduce).setOnClickListener(v -> reduce());

        return view;
    }

    private void add() {
      add(mProgressBar);
        if (circleProgressBar.getProgress() ==0) {
            circleProgressBar.setProgress(10);
        }
        else {
            circleProgressBar.setProgress(circleProgressBar.getProgress()+10);
        }
        add(circleProgressBar2_1);
        add(circleProgressBar2_2);
        add(circleProgressBar2_3);
        add(circleProgressBar2_4);
        add(circleProgressBar2_5);
        add(circleProgressBar2_6);
    }

    private void add(ProgressBar progressBar){
        if (progressBar.getProgress() <= 90) {
//            progressBar.setProgress((int) (mProgressBar.getProgress() * 1.2));
            progressBar.incrementProgressBy(10);
        }
        if (progressBar.getSecondaryProgress() < 90) {
//            progressBar.setSecondaryProgress((int) (mProgressBar.getSecondaryProgress() * 1.2));
            progressBar.incrementSecondaryProgressBy(10);
        }
    }

    private void add(CircleProgressBar progressBar){
        if (progressBar.getProgress() ==0) {
            progressBar.setProgress(10);
        }
        else {
            progressBar.setProgress(progressBar.getProgress()+10);
        }
    }

    private void reduce() {
       reduce(mProgressBar);

        if (circleProgressBar.getProgress() ==0) {
            circleProgressBar.setProgress(0);
        }
        else {
            circleProgressBar.setProgress(circleProgressBar.getProgress()-10);
        }
        reduce(circleProgressBar2_1);
        reduce(circleProgressBar2_2);
        reduce(circleProgressBar2_3);
        reduce(circleProgressBar2_4);
        reduce(circleProgressBar2_5);
        reduce(circleProgressBar2_6);
    }
    private void reduce(ProgressBar progressBar) {
        if (progressBar.getProgress() > 10) {
//            progressBar.setProgress(-10);
            progressBar.incrementProgressBy(-10);
        }
        if (progressBar.getSecondaryProgress() > 20) {
            progressBar.incrementSecondaryProgressBy(-10);
        }
    }

    private void reduce(CircleProgressBar progressBar) {
        if (progressBar.getProgress() ==0) {
            progressBar.setProgress(0);
        }
        else {
            progressBar.setProgress(progressBar.getProgress()-10);
        }
    }
}