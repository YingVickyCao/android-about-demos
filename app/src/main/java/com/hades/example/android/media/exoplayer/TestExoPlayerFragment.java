package com.hades.example.android.media.exoplayer;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.yingvickycao.autils.timer.ITimerView;
import com.github.yingvickycao.autils.timer.TimerHandler;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.MappingTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.FileDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.ErrorMessageProvider;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;
import com.hades.example.android.R;
import com.hades.example.android.media.audio.media_player.MediaController;

import java.io.File;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.List;

import static com.hades.example.android.media.exoplayer.TestExoPlayerActivity.KEY_URI_STRING;

public class TestExoPlayerFragment extends Fragment implements SurfaceHolder.Callback, ITimerView {
    private static final String TAG = TestExoPlayerFragment.class.getSimpleName();
    private SurfaceView surfaceView;
    private View mPauseBtn;
    private View mResumeBtn;
    private SeekBar mProgress;
    private TextView mCurrentTime;
    private TextView mEndTime;
    private View mView;

    private SimpleExoPlayer mPlayer;
    private int currentPosition;

    private TimerHandler mHandler;
    private MediaController mMediaController;
    private boolean mDragging = false;

    private String mUriString;
    private MediaSource mediaSource;
    private int startWindow;
    private long startPosition;

    private LinearLayout debugRootView;
    private TextView debugTextView;
    private Button mAudioOfDebug;
    private Button mVideoOfDebug;
    private Button mTextOfDebug;
    private TextView errorMessageView;
    private DefaultTrackSelector trackSelector;
    private DefaultTrackSelector.Parameters trackSelectorParameters;
    private DebugTextViewHelper debugViewHelper;
    @Nullable
    private ErrorMessageProvider<? super ExoPlaybackException> errorMessageProvider;
    @Nullable
    private CharSequence customErrorMessage;

    private static final CookieManager DEFAULT_COOKIE_MANAGER;
    private com.google.android.exoplayer2.ControlDispatcher controlDispatcher;
    private PlaybackPreparer playbackPreparer;
    private boolean startAutoPlay;
    private ComponentListener componentListener;
    private DataSource.Factory dataSourceFactory;
    private TrackGroupArray lastSeenTrackGroupArray;
    private static final String DOWNLOAD_CONTENT_DIRECTORY = "downloads";
    private File downloadDirectory; // Recommend: global
    private Cache downloadCache;    // Recommend: global

    static {
        DEFAULT_COOKIE_MANAGER = new CookieManager();
        DEFAULT_COOKIE_MANAGER.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
    }

    private static final String KEY_TRACK_SELECTOR_PARAMETERS = "track_selector_parameters";
    private static final String KEY_WINDOW = "window";
    private static final String KEY_POSITION = "position";
    private static final String KEY_AUTO_PLAY = "auto_play";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCookie();

        if (getArguments() != null) {
            mUriString = getArguments().getString(KEY_URI_STRING);
        }

        if (savedInstanceState != null) {
            trackSelectorParameters = savedInstanceState.getParcelable(KEY_TRACK_SELECTOR_PARAMETERS);
            startAutoPlay = savedInstanceState.getBoolean(KEY_AUTO_PLAY);
            startPosition = savedInstanceState.getLong(KEY_POSITION);
        } else {
            trackSelectorParameters = new DefaultTrackSelector.ParametersBuilder().build();
            clearStartPosition();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        updateTrackSelectorParameters();
        updateStartPosition();
        outState.putParcelable(KEY_TRACK_SELECTOR_PARAMETERS, trackSelectorParameters);
        outState.putBoolean(KEY_AUTO_PLAY, startAutoPlay);
        outState.putInt(KEY_WINDOW, startWindow);
        outState.putLong(KEY_POSITION, startPosition);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.media_video_exopalyer_surfaceview, container, false);

        mPauseBtn = view.findViewById(R.id.pause);
        mResumeBtn = view.findViewById(R.id.resume);
        mProgress = view.findViewById(R.id.playProgress);
        mCurrentTime = view.findViewById(R.id.currentTime);
        mEndTime = view.findViewById(R.id.totalTime);
        surfaceView = view.findViewById(R.id.surfaceView);
        mView = view;

        view.findViewById(R.id.play).setOnClickListener(v -> onClickPlay());
        mPauseBtn.setOnClickListener(v -> onClickPause());
        mResumeBtn.setOnClickListener(v -> onClickResume());
        view.findViewById(R.id.stop).setOnClickListener(v -> onClickStop());

        debugRootView = view.findViewById(R.id.controls_root);
        debugTextView = view.findViewById(R.id.debug_text_view);
        mAudioOfDebug = view.findViewById(R.id.audioOfDebug);
        mVideoOfDebug = view.findViewById(R.id.videoOfDebug);
        mTextOfDebug = view.findViewById(R.id.textOfDebug);
        errorMessageView = view.findViewById(R.id.errorMessageView);
        mAudioOfDebug.setOnClickListener(this::setOnTrackSelectionBtnClickListener);
        mVideoOfDebug.setOnClickListener(this::setOnTrackSelectionBtnClickListener);
        mTextOfDebug.setOnClickListener(this::setOnTrackSelectionBtnClickListener);
        componentListener = new ComponentListener();

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

        initializePlayer();
        onClickPlay();
        return view;
    }

    public String buildUserAgent() {
        return Util.getUserAgent(getContext(), getResources().getString(R.string.app_name));
    }


    public DataSource.Factory buildDataSourceFactory() {
        DefaultDataSourceFactory upstreamFactory = new DefaultDataSourceFactory(getActivity().getApplicationContext(), buildHttpDataSourceFactory());
        return buildReadOnlyCacheDataSource(upstreamFactory, getDownloadCache());
    }

    private HttpDataSource.Factory buildHttpDataSourceFactory() {
        return new DefaultHttpDataSourceFactory(buildUserAgent());
    }

    private static CacheDataSourceFactory buildReadOnlyCacheDataSource(DefaultDataSourceFactory upstreamFactory, Cache cache) {
        return new CacheDataSourceFactory(cache, upstreamFactory, new FileDataSourceFactory(), null, CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR, null);
    }

    private synchronized Cache getDownloadCache() {
        if (downloadCache == null) {
            File downloadContentDirectory = new File(getDownloadDirectory(), DOWNLOAD_CONTENT_DIRECTORY);
            downloadCache = new SimpleCache(downloadContentDirectory, new NoOpCacheEvictor());
        }
        return downloadCache;
    }

    private File getDownloadDirectory() {
        if (downloadDirectory == null) {
            downloadDirectory = getActivity().getExternalFilesDir(null);
            if (downloadDirectory == null) {
                downloadDirectory = getActivity().getFilesDir();
            }
        }
        return downloadDirectory;
    }

    private void setCookie() {
        if (CookieHandler.getDefault() != DEFAULT_COOKIE_MANAGER) {
            CookieHandler.setDefault(DEFAULT_COOKIE_MANAGER);
        }
    }

    private void initializePlayer() {
        if (null == mPlayer) {

            dataSourceFactory = buildDataSourceFactory();
            TrackSelection.Factory trackSelectionFactory = buildTrackSelectionFactory();
            setTrackSelector(trackSelectionFactory);

            Uri[] uris = parseUris();
            if (!checkUris(uris)) {
                return;
            }

            DefaultRenderersFactory renderersFactory = buildRenderersFactory();
            mPlayer = ExoPlayerFactory.newSimpleInstance(getActivity().getApplicationContext(), renderersFactory, trackSelector);
            mPlayer.addListener(new PlayerEventListener());
            mPlayer.setPlayWhenReady(startAutoPlay);
            mPlayer.addAnalyticsListener(new EventLogger(trackSelector));
            mPlayer.addVideoListener(new MyVideoListener());

            setControlDispatcher();
            setDebugViewHelper();

            setMediaSource(uris);

            initPlayerView();
        }

        boolean haveStartPosition = startWindow != C.INDEX_UNSET;
        if (haveStartPosition) {
            mPlayer.seekTo(startWindow, startPosition);
        }
        mPlayer.prepare(mediaSource, !haveStartPosition, false);
        updateTrackSelectionBtnsVisibilities();

    }

    private boolean checkUris(Uri[] uris) {
        if (!Util.checkCleartextTrafficPermitted(uris)) {
            showToast(R.string.error_cleartext_not_permitted);
            return false;
        }
        // The mPlayer will be reinitialized if the permission is granted.
        return !Util.maybeRequestReadExternalStoragePermission(/* activity= */ getActivity(), uris);
    }

    private Uri[] parseUris() {
        return new Uri[]{Uri.parse(mUriString)};
    }

    private void initPlayerView() {
        surfaceView.requestFocus();
        errorMessageProvider = new PlayerErrorMessageProvider();
        if (null != mPlayer) {
            this.mPlayer.removeListener(componentListener);
            Player.VideoComponent oldVideoComponent = this.mPlayer.getVideoComponent();

            if (oldVideoComponent != null) {
                oldVideoComponent.removeVideoListener(componentListener);
                oldVideoComponent.clearVideoSurfaceView(surfaceView);
            }

            Player.TextComponent oldTextComponent = this.mPlayer.getTextComponent();
            if (oldTextComponent != null) {
                oldTextComponent.removeTextOutput(componentListener);
            }

            Player.VideoComponent newVideoComponent = mPlayer.getVideoComponent();
            newVideoComponent.setVideoSurfaceView(surfaceView);
            newVideoComponent.addVideoListener(componentListener);
        }
        playbackPreparer = this::initPlayerView;
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
        super.onPause();
        if (Util.SDK_INT <= 23) {
            stopUpdateProgress();
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            stopUpdateProgress();
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (null != mMediaController) {
            mMediaController = null;
        }

        if (mPlayer != null) {
            updateTrackSelectorParameters();
            updateStartPosition();
            debugViewHelper.stop();
            debugViewHelper = null;
            mPlayer.release();
            mPlayer = null;
            mediaSource = null;
            trackSelector = null;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        releasePlayer();
    }

    private void onClickPlay() {
        initializePlayer();
    }

    private void onClickResume() {
        if (mPlayer.getPlaybackState() == Player.STATE_IDLE) {
            if (playbackPreparer != null) {
                playbackPreparer.preparePlayback();
            }
        } else if (mPlayer.getPlaybackState() == Player.STATE_ENDED) {
            controlDispatcher.dispatchSeekTo(mPlayer, mPlayer.getCurrentWindowIndex(), C.TIME_UNSET);
        }
        controlDispatcher.dispatchSetPlayWhenReady(mPlayer, true);
    }

    private void onClickPause() {
        controlDispatcher.dispatchSetPlayWhenReady(mPlayer, false);
    }

    private void onClickStop() {
        mPlayer.stop();
    }

    private boolean isPlaying() {
        return mPlayer != null
                && mPlayer.getPlaybackState() != Player.STATE_ENDED
                && mPlayer.getPlaybackState() != Player.STATE_IDLE
                && mPlayer.getPlayWhenReady();
    }

    private void updatePlayPauseButton() {
        boolean requestPlayPauseFocus = false;
        boolean playing = isPlaying();
        if (mResumeBtn != null) {
            requestPlayPauseFocus = playing && mResumeBtn.isFocused();
            Log.d(TAG, "updatePlayPauseButton: mResumeBtn " + (playing ? "View.GONE" : "View.VISIBLE"));
            mResumeBtn.setVisibility(playing ? View.GONE : View.VISIBLE);
        }
        if (mPauseBtn != null) {
            requestPlayPauseFocus |= !playing && mPauseBtn.isFocused();
            Log.d(TAG, "updatePlayPauseButton: mPauseBtn " + (!playing ? "View.GONE" : "View.VISIBLE"));
            mPauseBtn.setVisibility(!playing ? View.GONE : View.VISIBLE);
        }
        if (requestPlayPauseFocus) {
            requestPlayPauseFocus();
        }
    }

    private void requestPlayPauseFocus() {
        boolean playing = isPlaying();
        if (!playing && mResumeBtn != null) {
            mResumeBtn.requestFocus();
        } else if (playing && mPauseBtn != null) {
            mPauseBtn.requestFocus();
        }
    }

    private void adjustSurfaceViewSize() {
        // 获取窗口管理器
        WindowManager wManager = getActivity().getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        // 获取屏幕大小
        wManager.getDefaultDisplay().getMetrics(metrics);
        // 设置视频保持纵横比缩放到占满整个屏幕

        Format videoFormart = mPlayer.getVideoFormat();
        if (null != videoFormart) {
            surfaceView.setLayoutParams(new RelativeLayout.LayoutParams(metrics.widthPixels, videoFormart.height * metrics.widthPixels / videoFormart.width));
        }
    }

    private boolean isMp3() {
        return mUriString.endsWith(".mp3");
    }

    private void startUpdateProgress() {
        if (null != mHandler) {
            mHandler.sendMessage4UpdateView();
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
        mView.findViewById(R.id.stop).setEnabled(enabled);
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
            updateProgress();
            setEndTime();
            setCurrentTime();
        });
    }

    private void setCurrentTime() {
        if (null == mCurrentTime) {
            return;
        }
        long position = mPlayer.getCurrentPosition();
        mCurrentTime.setText(mMediaController.stringForTime(position));
    }

    private void setEndTime() {
        if (null == getActivity()) {
            return;
        }

        long duration = mPlayer.getDuration();
        getActivity().runOnUiThread(() -> {
            mProgress.setMax(100);
            mEndTime.setText(mMediaController.stringForTime(duration));
        });
    }

    private void updateProgress() {
        if (null == mPlayer) {
            return;
        }
        long position = mPlayer.getCurrentPosition();
        long duration = mPlayer.getDuration();
        if (mProgress != null) {
            if (duration > 0) {
                // use long to avoid overflow
                long pos = 100L * position / duration;
                mProgress.setProgress((int) pos);
            }
            int cachePercentage = mPlayer.getBufferedPercentage();
            Log.d(TAG, "updateProgress: cachePercentage=" + cachePercentage);
            mProgress.setSecondaryProgress(cachePercentage);
        }
    }


    private void updateTrackSelectionBtnsVisibilities() {
        debugRootView.setVisibility(View.GONE);
        mAudioOfDebug.setVisibility(View.GONE);
        mVideoOfDebug.setVisibility(View.GONE);
        mTextOfDebug.setVisibility(View.GONE);
        if (mPlayer == null) {
            return;
        }

        debugRootView.setVisibility(View.VISIBLE);
        MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
        if (mappedTrackInfo == null) {
            return;
        }

        for (int i = 0; i < mappedTrackInfo.getRendererCount(); i++) {
            TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i);
            if (trackGroups.length != 0) {
                switch (mPlayer.getRendererType(i)) {
                    case C.TRACK_TYPE_AUDIO:
                        mAudioOfDebug.setVisibility(View.VISIBLE);
                        mAudioOfDebug.setTag(i);
                        debugRootView.setVisibility(View.VISIBLE);
                        break;
                    case C.TRACK_TYPE_VIDEO:
                        mVideoOfDebug.setVisibility(View.VISIBLE);
                        mVideoOfDebug.setTag(i);
                        debugRootView.setVisibility(View.VISIBLE);
                        break;
                    case C.TRACK_TYPE_TEXT:
                        mTextOfDebug.setVisibility(View.VISIBLE);
                        mTextOfDebug.setTag(i);
                        debugRootView.setVisibility(View.VISIBLE);
                        break;
                    default:
                }
            }
        }
    }

    private void setOnTrackSelectionBtnClickListener(View view) {
        MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
        if (mappedTrackInfo != null) {
            CharSequence title = ((Button) view).getText();
            int rendererIndex = (int) view.getTag();
            int rendererType = mappedTrackInfo.getRendererType(rendererIndex);
            boolean allowAdaptiveSelections = rendererType == C.TRACK_TYPE_VIDEO || (rendererType == C.TRACK_TYPE_AUDIO && mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO) == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_NO_TRACKS);
            Pair<AlertDialog, TrackSelectionView> dialogPair = TrackSelectionView.getDialog(getActivity(), title, trackSelector, rendererIndex);
            dialogPair.second.setShowDisableOption(true);
            dialogPair.second.setAllowAdaptiveSelections(allowAdaptiveSelections);
            dialogPair.first.show();
        }
    }


    private void setDebugViewHelper() {
        debugViewHelper = new DebugTextViewHelper(mPlayer, debugTextView);
        debugViewHelper.start();
    }

    public void setErrorMessageProvider(
            @Nullable ErrorMessageProvider<? super ExoPlaybackException> errorMessageProvider) {
        if (this.errorMessageProvider != errorMessageProvider) {
            this.errorMessageProvider = errorMessageProvider;
            updateErrorMessage();
        }
    }

    private void updateErrorMessage() {
        if (errorMessageView != null) {
            if (customErrorMessage != null) {
                errorMessageView.setText(customErrorMessage);
                errorMessageView.setVisibility(View.VISIBLE);
                return;
            }
            ExoPlaybackException error = null;
            if (mPlayer != null
                    && mPlayer.getPlaybackState() == Player.STATE_IDLE
                    && errorMessageProvider != null) {
                error = mPlayer.getPlaybackError();
            }
            if (error != null) {
                CharSequence errorMessage = errorMessageProvider.getErrorMessage(error).second;
                errorMessageView.setText(errorMessage);
                errorMessageView.setVisibility(View.VISIBLE);
            } else {
                errorMessageView.setVisibility(View.GONE);
            }
        }
    }


    private void updateTrackSelectorParameters() {
        if (trackSelector != null) {
            trackSelectorParameters = trackSelector.getParameters();
        }
    }

    private void updateStartPosition() {
        if (mPlayer != null) {
            startAutoPlay = mPlayer.getPlayWhenReady();
            startWindow = mPlayer.getCurrentWindowIndex();
            startPosition = Math.max(0, mPlayer.getContentPosition());
        }
    }

    private void clearStartPosition() {
        startAutoPlay = true;
        startWindow = C.INDEX_UNSET;
        startPosition = C.TIME_UNSET;
    }


    private final class ComponentListener implements Player.EventListener,
            TextOutput,
            VideoListener,
            View.OnLayoutChangeListener {

        // TextOutput implementation

        @Override
        public void onCues(List<Cue> cues) {
//            if (subtitleView != null) {
//                subtitleView.onCues(cues);
//            }
        }

        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            updatePlayPauseButton();
            updateProgress();
        }

    }

    private class PlayerErrorMessageProvider implements ErrorMessageProvider<ExoPlaybackException> {
        @Override
        public Pair<Integer, String> getErrorMessage(ExoPlaybackException e) {
            String errorString = getString(R.string.error_generic);
            if (e.type == ExoPlaybackException.TYPE_RENDERER) {
                Exception cause = e.getRendererException();
                if (cause instanceof MediaCodecRenderer.DecoderInitializationException) {
                    // Special case for decoder initialization failures.
                    MediaCodecRenderer.DecoderInitializationException decoderInitializationException = (MediaCodecRenderer.DecoderInitializationException) cause;

                    if (decoderInitializationException.decoderName == null) {
                        if (decoderInitializationException.getCause() instanceof MediaCodecUtil.DecoderQueryException) {
                            errorString = getString(R.string.error_querying_decoders);
                        } else if (decoderInitializationException.secureDecoderRequired) {
                            errorString = getString(R.string.error_no_secure_decoder, decoderInitializationException.mimeType);
                        } else {
                            errorString = getString(R.string.error_no_decoder, decoderInitializationException.mimeType);
                        }
                    } else {
                        errorString = getString(R.string.error_instantiating_decoder, decoderInitializationException.decoderName);
                    }
                }
            }
            return Pair.create(0, errorString);
        }
    }

    private class MyVideoListener implements VideoListener {
        @Override
        public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
            Log.d(TAG, "onVideoSizeChanged: width=" + width + ",height=" + height);
        }

        @Override
        public void onSurfaceSizeChanged(int width, int height) {
            Log.d(TAG, "onSurfaceSizeChanged: width=" + width + ",height=" + height);
        }

        @Override
        public void onRenderedFirstFrame() {
            Log.d(TAG, "onRenderedFirstFrame: ");
        }
    }

    private class PlayerEventListener implements Player.EventListener {

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            Log.d(TAG, "onPlayerStateChanged: playWhenReady=" + playWhenReady + ",playbackState=" + playbackState);
            if (Player.STATE_READY == playbackState) {
                if (!isMp3()) {
                    adjustSurfaceViewSize();
                }

                showDebugControls();
                setEndTime();
                enableBtns(true);
            }

            if (Player.STATE_ENDED == playbackState) {
//                releasePlayer();
            }
            updatePlayPauseButton();
            updateProgress();
            updateTrackSelectionBtnsVisibilities();
        }

        @Override
        public void onPositionDiscontinuity(@Player.DiscontinuityReason int reason) {
            if (mPlayer.getPlaybackError() != null) {
                // The user has performed a seek whilst in the error state. Update the resume position so
                // that if the user then retries, playback resumes from the position to which they seeked.
                updateStartPosition();
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException e) {
            if (isBehindLiveWindow(e)) {
                clearStartPosition();
                initializePlayer();
            } else {
                updateStartPosition();
                updateTrackSelectionBtnsVisibilities();
                showDebugControls();
            }
        }

        @Override
        @SuppressWarnings("ReferenceEquality")
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            updateTrackSelectionBtnsVisibilities();
            if (trackGroups != lastSeenTrackGroupArray) {
                MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
                if (mappedTrackInfo != null) {
                    if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO) == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                        showToast(R.string.error_unsupported_video);
                    }
                    if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_AUDIO) == MappingTrackSelector.MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
                        showToast(R.string.error_unsupported_audio);
                    }
                }
                lastSeenTrackGroupArray = trackGroups;
            }
        }
    }

    private boolean isBehindLiveWindow(ExoPlaybackException e) {
        if (e.type != ExoPlaybackException.TYPE_SOURCE) {
            return false;
        }
        Throwable cause = e.getSourceException();
        while (cause != null) {
            if (cause instanceof BehindLiveWindowException) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }

    private void showDebugControls() {
        debugRootView.setVisibility(View.VISIBLE);
    }

    private void showToast(int messageId) {
        showToast(getString(messageId));
    }

    private void showToast(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    private TrackSelection.Factory buildTrackSelectionFactory() {
        return new AdaptiveTrackSelection.Factory();
    }

    private void setTrackSelector(TrackSelection.Factory trackSelectionFactory) {
        trackSelector = new DefaultTrackSelector(trackSelectionFactory);
        trackSelector.setParameters(trackSelectorParameters);
    }

    private DefaultRenderersFactory buildRenderersFactory() {
        return new DefaultRenderersFactory(getActivity().getApplicationContext()).setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF);
    }

    public void setControlDispatcher() {
        if (null == controlDispatcher) {
            controlDispatcher = new com.google.android.exoplayer2.DefaultControlDispatcher();
        }
    }

    private void setMediaSource(Uri[] uris) {
        MediaSource[] mediaSources = buildMediaSource(uris);
        mediaSource = mediaSources.length == 1 ? mediaSources[0] : new ConcatenatingMediaSource(mediaSources);
    }

    private MediaSource[] buildMediaSource(Uri[] uris) {
        MediaSource[] mediaSources = new MediaSource[uris.length];
        for (int i = 0; i < uris.length; i++) {
            mediaSources[i] = buildMediaSource(uris[i]);
        }
        return mediaSources;
    }

    private MediaSource buildMediaSource(Uri uri) {
        @C.ContentType int type = Util.inferContentType(uri, null);
        switch (type) {
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            case C.TYPE_OTHER:
                return new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }
}