package com.hades.example.android.media;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.snackbar.Snackbar;
import com.hades.example.android.R;
import com.hades.example.android.base.BaseActivity;
import com.hades.example.android.media.audio.audio_effect.TestAudioEffectActivity;
import com.hades.example.android.media.audio.media_player.TestMediaPlayer4AudioFragment;
import com.hades.example.android.media.audio.sound_pool.TestSoundPoolFragment;
import com.hades.example.android.media.camera.TestCameraActivity;
import com.hades.example.android.media.record.audio.TestRecordAudioFragment;
import com.hades.example.android.tools.permission.IRationaleOnClickListener;
import com.hades.example.android.tools.permission.IRequestPermissionsCallback;
import com.hades.example.android.tools.permission.PermissionTools;
import com.hades.example.android.widget.surfaceview.TestSurfaceViewPlayVideoFragment;
import com.hades.example.android.widget.videoview.VideoViewRotateScreenTipActivity;

public class TestMediaActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_layout);

        findViewById(R.id.pageMediaPlayerPlayAudio).setOnClickListener(v -> pageMediaPlayerPlayAudio());
        findViewById(R.id.pageAudioEffect).setOnClickListener(v -> pageAudioEffect());
        findViewById(R.id.pageSoundPoolPlayAudio).setOnClickListener(v -> pageSoundPool4Audio());

        findViewById(R.id.pageVideoViewPlayVideo).setOnClickListener(v -> pageVideoViewPlayVideo());
        findViewById(R.id.pageSurfaceViewPlayVideo).setOnClickListener(v -> pageSurfaceViewPlayVideo());

        findViewById(R.id.pageRecordAudio).setOnClickListener(v -> pageRecordAudio());

        findViewById(R.id.pageCamera).setOnClickListener(v -> pageCamera());
        requestPermission();
    }

    private void requestPermission() {
        PermissionTools permissionTools = new PermissionTools(this);
        permissionTools.request(new IRequestPermissionsCallback() {
            @Override
            public void showRationaleContextUI(IRationaleOnClickListener callback) {
                Snackbar.make(findViewById(R.id.root), "Request SD card permission", Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.ok), view -> callback.clickOK())
                        .setAction(getString(R.string.cancel), view -> callback.clickCancel())
                        .show();
            }

            @Override
            public void granted() {
                Toast.makeText(TestMediaActivity.this, "SD card permission granted", Toast.LENGTH_SHORT).show();
            }
        }, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void showCurrentTest() {
        pageMediaPlayerPlayAudio();
    }

    private void pageMediaPlayerPlayAudio() {
        showFragment(new TestMediaPlayer4AudioFragment());
    }

    private void pageAudioEffect() {
        showActivity(TestAudioEffectActivity.class);
    }

    private void pageSoundPool4Audio() {
        showFragment(new TestSoundPoolFragment());
    }

    private void pageVideoViewPlayVideo() {
        showActivity(VideoViewRotateScreenTipActivity.class);
    }

    private void pageSurfaceViewPlayVideo() {
        showFragment(new TestSurfaceViewPlayVideoFragment());
    }


    private void pageRecordAudio() {
        showFragment(new TestRecordAudioFragment());
    }

    private void pageCamera() {
        showActivity(TestCameraActivity.class);
    }
}