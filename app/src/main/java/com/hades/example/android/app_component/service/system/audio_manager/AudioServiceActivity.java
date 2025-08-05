package com.hades.example.android.app_component.service.system.audio_manager;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioDeviceCallback;
import android.media.AudioDeviceInfo;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.AudioPlaybackConfiguration;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ToggleButton;

import com.hades.example.android.R;

import java.util.List;


public class AudioServiceActivity extends Activity {
    private static final String TAG = "AudioServiceActivity";

    private AudioManager audioManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_system_audio_service);

        audioManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);

        findViewById(R.id.play).setOnClickListener(source -> play());
        findViewById(R.id.requestAudioFocus).setOnClickListener(source -> requestAudioFocus());
        findViewById(R.id.up).setOnClickListener(source -> up());
        findViewById(R.id.down).setOnClickListener(source -> down());
        ((ToggleButton) findViewById(R.id.mute)).setOnCheckedChangeListener((source, isChecked) -> mute(isChecked));
    }

    /**
     * 获取焦点成功，则播放音频或视频
     * 监听其他app 播放正在播放，若重新获取焦点，则继续播放音频或视频，否则暂停。
     * 若停止播放，则通知失去焦点。
     */
    private void requestAudioFocus() {
        int result = audioManager.requestAudioFocus(audioFocusRequest);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            Log.e(TAG, "Audio focus requested successfully");
            // Start playing audio or movie
        } else {
            Log.e(TAG, "Failed to request audio focus");
        }
    }

    AudioAttributes audioAttributes = new AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE) // CONTENT_TYPE_MUSIC
            .build();
    AudioFocusRequest audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
            .setAudioAttributes(audioAttributes)
            .setAcceptsDelayedFocusGain(true)
            .setOnAudioFocusChangeListener(new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    switch (focusChange) {
                        case AudioManager.AUDIOFOCUS_GAIN:
                            // Audio focus gained, media might be playing or about to play
                            Log.e("MediaListener", "Audio focus gained. Media might be playing.");
                            // You can now check isMusicActive() if needed.
                            break;
                        case AudioManager.AUDIOFOCUS_LOSS:
                            // Audio focus lost, another app is playing media
                            Log.e("MediaListener", "Audio focus lost. Another app is playing media.");
                            // You might want to pause your own media here.
                            break;
                        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                            // Temporary audio focus loss, another app is playing media temporarily
                            Log.e("MediaListener", "Temporary audio focus loss. Media is playing temporarily.");
                            // You might want to pause your own media temporarily.
                            break;
                    }
                }
            })
            .build();

    private void play() {
        MediaPlayer mPlayer = MediaPlayer.create(AudioServiceActivity.this, R.raw.msg);
        mPlayer.setLooping(false);
        mPlayer.start();
    }

    private void up() {
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
    }

    private void down() {
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
    }

    private void mute(boolean isChecked) {
        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, isChecked);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}