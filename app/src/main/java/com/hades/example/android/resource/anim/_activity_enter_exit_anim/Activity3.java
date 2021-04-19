package com.hades.example.android.resource.anim._activity_enter_exit_anim;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;

public class Activity3 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mock_green_page);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
//        overridePendingTransition(R.anim.activity_open_enter, android.R.anim.activity_open_exit);
    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(R.anim.activity_close_enter, R.anim.activity_close_exit);
//        overridePendingTransition(R.anim.out_to_right_abit, R.anim.out_to_right);

//        overridePendingTransition(0, R.anim.up_2_down);
        overridePendingTransition(R.anim.slide_out_down_abit, R.anim.slide_out_down);  // Recommend
    }
}