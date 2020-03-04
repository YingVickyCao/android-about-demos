package com.hades.example.android.widget._surfaceview;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.yingvickycao.autils.timer.ITimerView;
import com.github.yingvickycao.autils.timer.TimerHandler;
import com.hades.example.android.R;
import com.hades.example.android.media.audio.media_player.IMediaPlayer;
import com.hades.example.android.media.audio.media_player.MediaController;

import java.io.IOException;

public class TestSurfaceViewPlayVideoFragment extends Fragment implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, IMediaPlayer, ITimerView {
    private static final String TAG = TestSurfaceViewPlayVideoFragment.class.getSimpleName();
    private SurfaceView surfaceView;
    private SeekBar mProgress;
    private TextView mCurrentTime;
    private TextView mEndTime;
    private View mView;

    private MediaPlayer mPlayer;
    private int currentPosition;

    private TimerHandler mHandler;
    private MediaController mMediaController;
    private boolean mDragging = false;
    private int mCurrentBufferPercentage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.media_video_surface_view, container, false);

        view.findViewById(R.id.play).setOnClickListener(v -> onClickPlay());
        view.findViewById(R.id.pause).setOnClickListener(v -> onClickPause());
        view.findViewById(R.id.stopRecord).setOnClickListener(v -> onClickStop());
        mProgress = view.findViewById(R.id.playProgress);
        mCurrentTime = view.findViewById(R.id.currentTime);
        mEndTime = view.findViewById(R.id.totalTime);
        surfaceView = view.findViewById(R.id.surfaceView);
        mView = view;

        mMediaController = new MediaController();

        mProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //  FIXED_ERROR:TBD: seekbar -> MediaPlayer.seekTo(int)没有效果,直接回调old position
                if (!fromUser) {
                    return;
                }
                long duration = mPlayer.getDuration();
                long newposition = (duration * progress) / 100L;
                seekTo((int) newposition);
                if (mCurrentTime != null) {
                    mCurrentTime.setText(mMediaController.stringForTime((int) newposition));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mDragging = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStopTrackingTouch: ");
                mDragging = false;
            }
        });

        enableBtns(false);

        mPlayer = new MediaPlayer();
        mPlayer.setOnPreparedListener(this);
        onClickPlay();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (null == mHandler) {
            mHandler = new TimerHandler();
        }
        startUpdateProgress();
    }

    @Override
    public void onPause() {
        if (mPlayer.isPlaying()) {
            currentPosition = mPlayer.getCurrentPosition();
            mPlayer.pause();
        }

        if (null != mHandler) {
            mHandler.clear();
            mHandler.setITimerView(null);
        }
        stopUpdateProgress();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        releasePlayer();
    }

    private void onClickPlay() {
        try {
            play();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onClickPause() {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
        } else {
            mPlayer.start();
        }
    }

    private void onClickStop() {
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
        }
    }

    private void play() throws IOException {
        mPlayer.reset();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        // 设置需要播放的视频
        mPlayer.setDataSource(getActivity(), Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.mp4_1));
//        mPlayer.setDataSource(getActivity(), Uri.parse("/sdcard/mp4_3.mp4"));
        mPlayer.prepareAsync();
    }

    private void adjustSurfaceViewSize() {
        // 获取窗口管理器
        WindowManager wManager = getActivity().getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        // 获取屏幕大小
        wManager.getDefaultDisplay().getMetrics(metrics);
        // 设置视频保持纵横比缩放到占满整个屏幕
        surfaceView.setLayoutParams(new RelativeLayout.LayoutParams(metrics.widthPixels, mPlayer.getVideoHeight() * metrics.widthPixels / mPlayer.getVideoWidth()));
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (null == mPlayer) {
            return;
        }

        mPlayer.start();
        if (null != mHandler) {
            mHandler.setITimerView(this);
            mHandler.sendMessage4Progress();
        }
        setEndTime(mPlayer.getDuration());

        enableBtns(true);
        adjustSurfaceViewSize();
        // 把视频画面输出到SurfaceView
        mPlayer.setDisplay(surfaceView.getHolder());  // ①
    }

    private void startUpdateProgress() {
        if (null != mHandler) {
            mHandler.sendMessage4Progress();
        }
    }

    private void stopUpdateProgress() {
        if (null != mHandler) {
            mHandler.clear();
            mHandler.setITimerView(null);
        }
    }

    private void enableBtns(boolean enabled) {
        mView.findViewById(R.id.play).setEnabled(enabled);
        mView.findViewById(R.id.pause).setEnabled(enabled);
        mView.findViewById(R.id.stopRecord).setEnabled(enabled);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (null == holder) {
            return;
        }
        holder.setKeepScreenOn(true);
        holder.addCallback(this);

        if (currentPosition > 0) {
            try {
                // 开始播放
                // 并直接从指定位置开始播放
                mPlayer.seekTo(currentPosition);
                currentPosition = 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (null == holder) {
            return;
        }
        holder.setKeepScreenOn(false);
        holder.addCallback(null);
    }


    private void seekTo(int seekToProgress) {
        if (null == mPlayer) {
            return;
        }
        mPlayer.seekTo(seekToProgress);
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        mCurrentBufferPercentage = percent;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        releasePlayer();
    }

    private void releasePlayer() {
        if (null != mPlayer) {
            if (mPlayer.isPlaying()) {
                mPlayer.stop();
            }

            mPlayer.release();
            mPlayer = null;
        }

        if (null != mMediaController) {
            mMediaController = null;
        }

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        Log.d(TAG, "onInfo: what=" + what + ",extra=" + extra);
        return false;
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        Log.d(TAG, "onSeekComplete: ");
    }

    @Override
    public void onTimedText(MediaPlayer mp, TimedText text) {
        Log.d(TAG, "onTimedText: ");
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        Log.d(TAG, "onVideoSizeChanged: width=" + width + ",height=" + height);
    }

    @Override
    public void updateView() {
        /**
         * ERROR:
         *  E/MediaPlayerNative: Attempt to call getDuration in wrong state: mPlayer=0xd3ee1d40, mCurrentState=0
         *  E/MediaPlayerNative: error (-38, 0)
         */
        if (null == mPlayer || mDragging || getActivity() == null) {
            return;
        }

        getActivity().runOnUiThread(() -> {
            int position = mPlayer.getCurrentPosition();
            int duration = mPlayer.getDuration();
            if (mProgress != null) {
                if (duration > 0) {
                    // use long to avoid overflow
                    long pos = 100L * position / duration;
                    mProgress.setProgress((int) pos);
                    Log.d(TAG, "updateView: ,[" + mPlayer.getCurrentPosition() + "," + mCurrentBufferPercentage + "," + mPlayer.getDuration() + "]," + "progress=" + pos + ",bufferPercentage=" + mCurrentBufferPercentage);
                }
                mProgress.setSecondaryProgress(mCurrentBufferPercentage);
            }

            if (mEndTime != null)
                mEndTime.setText(mMediaController.stringForTime(duration));
            if (mCurrentTime != null)
                mCurrentTime.setText(mMediaController.stringForTime(position));
        });
    }

    private void setEndTime(int duration) {
        if (null == getActivity()) {
            return;
        }
        getActivity().runOnUiThread(() -> {
            mProgress.setMax(100);
            mEndTime.setText(mMediaController.stringForTime(duration));
        });
    }

}