package com.hades.example.android.media.exoplayer;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.yingvickycao.autils.base.PermissionActivity;
import com.hades.example.android.R;
import com.hades.example.android.media.audio.audio_effect.TestAudioEffectActivity;
import com.hades.example.android.media.audio.media_player.TestMediaPlayer4AudioFragment;
import com.hades.example.android.media.audio.sound_pool.TestSoundPoolFragment;
import com.hades.example.android.media.camera.TestCameraActivity;
import com.hades.example.android.media.record.audio.TestRecordAudioFragment;
import com.hades.example.android.widget._surfaceview.TestSurfaceViewPlayVideoFragment;
import com.hades.example.android.widget._videoview.VideoViewRotateScreenTipActivity;

public class TestExoPlayerActivity extends PermissionActivity {
    private String mUriString;
    public final static String KEY_URI_STRING = "KEY_URI_STRING";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_exoplayer);
        initViews();

        setUriString(3);

        findViewById(R.id.clear).setOnClickListener(v -> clear());
        findViewById(R.id.play).setOnClickListener(v -> play());
        ((Spinner) findViewById(R.id.mediaFrom)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setUriString(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @Override
    protected void requestPermission() {
        checkPermission("Request permission for operate storage", Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private void setUriString(int position) {
        mUriString = getResources().getStringArray(R.array.media_data)[position];
    }

    private void clear() {
        removeDetailFragment();
    }

    private void play() {
        Fragment fragment = new TestExoPlayerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_URI_STRING, mUriString);
        fragment.setArguments(bundle);
        showFragment(fragment);
    }
}