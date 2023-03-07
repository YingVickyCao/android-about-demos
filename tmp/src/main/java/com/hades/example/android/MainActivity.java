package com.hades.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    View mMediaBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        setContentView(R.layout.activity_main);

        findViewById(R.id.open_one_video).setOnClickListener(v -> openOneVideo());
        findViewById(R.id.open_one_audio).setOnClickListener(v -> openOneAudio());
        findViewById(R.id.expand).setOnClickListener(v -> clickExpand());
        findViewById(R.id.close).setOnClickListener(v -> clickClose());

        mMediaBar = findViewById(R.id.media_bar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCollapseMediaEvent(CollapseVideoEvent event) {
//        EventBus.getDefault().cancelEventDelivery(event);
        showMediaBar();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCloseMediaEvent(CloseVideoEvent event) {
//        EventBus.getDefault().cancelEventDelivery(event);
        hideMediaBar();
    }

    private void showMediaBar() {
        mMediaBar.setVisibility(View.VISIBLE);

        mMediaBar.clearAnimation();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.media_bar_enter_from_bottom_to_top);
        mMediaBar.startAnimation(animation);
    }

    private void hideMediaBar() {
        mMediaBar.setVisibility(View.GONE);

        mMediaBar.clearAnimation();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.media_bar_exit_from_top_to_bottom);
        mMediaBar.startAnimation(animation);
    }

    private void openOneVideo() {
        startActivity(new Intent(this, SecondActivity.class));
    }

    private void openOneAudio() {
        showMediaBar();
    }

    private void clickExpand() {
        startActivity(new Intent(this, SecondActivity.class));
    }

    private void clickClose() {
        hideMediaBar();
    }
}