package com.hades.example.android.resource.anim._activity_transition_animation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;

public class TransitionMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        getWindow().setAllowEnterTransitionOverlap(false);
//        getWindow().setAllowReturnTransitionOverlap(false);

        // Exit
        getWindow().setExitTransition(new Explode());
//        getWindow().setExitTransition(new Slide());
//        getWindow().setExitTransition(new Fade());

        setContentView(R.layout.res_anim_activity_transition_animation_main);
        findViewById(R.id.view3).setOnClickListener(v -> openFirstPage());
    }

    private void openFirstPage() {
//        startActivity(new Intent(this, FirstPageActivity.class));

        // Exit/Exit
        startActivity(new Intent(this, FirstPageActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

        // Shared
//        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, findViewById(R.id.view3), "share_ImageView");
//        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, Pair.create(findViewById(R.id.view3), "share_ImageView"));
//        startActivity(new Intent(this, FirstPageActivity.class), options.toBundle());

//        getFragmentManager().beginTransaction()
//                .replace(R.id.framelayout_container, sharedElementFragment2)
//                .addToBackStack(null)
//                .addSharedElement(sharedView, "share_ImageView")
//                .commit();
    }
}
