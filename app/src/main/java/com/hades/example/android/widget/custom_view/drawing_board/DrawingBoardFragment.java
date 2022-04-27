package com.hades.example.android.widget.custom_view.drawing_board;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;

/**
 * 利用双缓冲实现画面板
 */
public class DrawingBoardFragment extends Fragment {
    DrawingBoardView drawingBoardView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_custom_view_double_buffer_for_drawing_board, container, false);
        view.findViewById(R.id.colorRed).setOnClickListener(v -> colorRed());
        view.findViewById(R.id.colorGreen).setOnClickListener(v -> colorGreen());
        view.findViewById(R.id.colorBlue).setOnClickListener(v -> colorBlue());
        view.findViewById(R.id.width1).setOnClickListener(v -> width1());
        view.findViewById(R.id.width3).setOnClickListener(v -> width3());
        view.findViewById(R.id.width5).setOnClickListener(v -> width5());
        view.findViewById(R.id.blur).setOnClickListener(v -> width5());
        view.findViewById(R.id.emboss).setOnClickListener(v -> emboss());

//        drawingBoardView = view.findViewById(R.id.drawingBoardView);
        return view;
    }

    private void colorRed() {
        drawingBoardView.setPaintColor(Color.RED);
    }

    private void colorGreen() {
        drawingBoardView.setPaintColor(Color.GREEN);
    }

    private void colorBlue() {
        drawingBoardView.setPaintColor(Color.BLUE);
    }

    private void width1() {
        drawingBoardView.setPaintStrokeWidth(getResources().getDimension(R.dimen.size_1));
    }

    private void width3() {
        drawingBoardView.setPaintStrokeWidth(getResources().getDimension(R.dimen.size_3));
    }

    private void width5() {
        drawingBoardView.setPaintStrokeWidth(getResources().getDimension(R.dimen.size_5));
    }

    private void blur() {
        drawingBoardView.useBlurMaskFilter();
    }

    private void emboss() {
        drawingBoardView.useEmbossMaskFilter();
    }
}
