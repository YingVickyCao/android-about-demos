package com.hades.example.android._process_and_thread.threadPoolExecutor;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.hades.example.android.R;
import com.github.yingvickycao.autils.base.BaseFragment;

public class ThreadPoolExecutorFragment extends BaseFragment implements IProgressListener {
    private static final String TAG = ThreadPoolExecutorFragment.class.getSimpleName();

    private int mCounter = 1;
    private TextView mResultView;
    UpdateProgressMgr mUpdateProgressMgr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.process_thread_threadpoolexecutor, container, false);

        mUpdateProgressMgr = new UpdateProgressMgr();
        mResultView = view.findViewById(R.id.result);

        view.findViewById(R.id.init).setOnClickListener(v -> init());
        view.findViewById(R.id.start).setOnClickListener(v -> start());
        view.findViewById(R.id.stop).setOnClickListener(v -> stop());
        view.findViewById(R.id.destroy).setOnClickListener(v -> destroy());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if (null != mUpdateProgressMgr) {
            mUpdateProgressMgr.destroy();
            mUpdateProgressMgr = null;
        }
    }

    private void init() {
        mUpdateProgressMgr.init(this);
    }

    private void start() {
        mUpdateProgressMgr.start();
    }

    private void stop() {
        mUpdateProgressMgr.stop();
    }

    private void destroy() {
        mUpdateProgressMgr.destroy();
    }

    @Override
    public void update() {
        mCounter++;
        Log.d(TAG, "update: mCounter=" + mCounter);
        mResultView.post(() -> mResultView.setText(String.valueOf(mCounter)));
    }
}