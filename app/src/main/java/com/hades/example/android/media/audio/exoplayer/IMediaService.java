package com.hades.example.android.media.audio.exoplayer;

public interface IMediaService {

    void init();

    void destroy();

    void setData();

    void pausePlay();

    void resumePlay();

    void setSpeed(final float speed);

    void setTrack();

    void setVolume(final float volume);

    void setScreenLight(final float light);

    long getCurrentMs();

    long getTotalMS();

    long getCachedMs();
}
