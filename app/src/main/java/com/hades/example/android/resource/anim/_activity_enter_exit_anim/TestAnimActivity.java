package com.hades.example.android.resource.anim._activity_enter_exit_anim;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;

public class TestAnimActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.res_anim_when_activity_in_out);

        findViewById(R.id.activityAnim_Default).setOnClickListener(v -> activityAnim_Default());
        findViewById(R.id.activityAnim_theme).setOnClickListener(v -> activityAnim_theme());
        findViewById(R.id.activityAnim_override_func).setOnClickListener(v -> activityAnim_override_func());
    }

    private void activityAnim_Default() {
        startActivity(new Intent(this, Activity1.class));
    }

    private void activityAnim_theme() {
        startActivity(new Intent(this, Activity2.class));
    }

    // 一定要先startActivity，或者 先finish，再调用overridePendingTransition
    private void activityAnim_override_func() {
        startActivity(new Intent(this, Activity3.class));
//        overridePendingTransition(R.anim.in_from_right, R.anim.in_from_right_abit);

//        overridePendingTransition(R.anim.slide_in_up, 0);
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_in_up_abit);  // Recommend
    }
}
