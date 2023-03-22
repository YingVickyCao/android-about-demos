package com.hades.example.android._case;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;

/**
 * Video播放控制层
 */
public class FaqPlayerControllerPlayerActivity extends AppCompatActivity {
    private static final String TAG = FaqPlayerControllerPlayerActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_player_controller_layer);
    }
}
