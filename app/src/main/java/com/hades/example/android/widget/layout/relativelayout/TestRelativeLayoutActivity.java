package com.hades.example.android.widget.layout.relativelayout;

import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;

public class TestRelativeLayoutActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_relativelayout);

        findViewById(R.id.changeSurfaceViewSize).setOnClickListener(v -> changeSurfaceViewSize());
        surfaceView = findViewById(R.id.surfaceView);
    }

    private void changeSurfaceViewSize() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) surfaceView.getLayoutParams();
        layoutParams.height = getResources().getDimensionPixelSize(R.dimen.big_size);
        surfaceView.setLayoutParams(layoutParams);
    }
}
