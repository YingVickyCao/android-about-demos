package com.hades.example.android.media.audio.exoplayer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class ExoPlayerMediaService extends Service implements IMediaService {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void setData() {

    }

    @Override
    public void pausePlay() {

    }

    @Override
    public void resumePlay() {

    }

    @Override
    public void setSpeed(float speed) {

    }

    @Override
    public void setTrack() {

    }

    @Override
    public void setVolume(float volume) {

    }

    @Override
    public void setScreenLight(float light) {

    }

    @Override
    public long getCurrentMs() {
        return 0;
    }

    @Override
    public long getTotalMS() {
        return 0;
    }

    @Override
    public long getCachedMs() {
        return 0;
    }
}
