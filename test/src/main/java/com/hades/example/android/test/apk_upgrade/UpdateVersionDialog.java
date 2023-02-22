package com.hades.example.android.test.apk_upgrade;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.hades.example.android.test.R;

import java.io.File;

public class UpdateVersionDialog extends DialogFragment {
    private static final String TAG = "UpdateVersionDialog";
    public static final String KEY_APP_VERSION_UPDATE = "APP_VERSION_UPDATE";

    private AppVersionBean mBean;

    public static void show(AppCompatActivity activity, AppVersionBean bean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_APP_VERSION_UPDATE, bean);

        UpdateVersionDialog dialog = new UpdateVersionDialog();
        dialog.setArguments(bundle);
        dialog.show(activity.getSupportFragmentManager(), dialog.getClass().getSimpleName());
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        mBean = (AppVersionBean) bundle.getSerializable(KEY_APP_VERSION_UPDATE);

    }

    // onCreateView / onCreateDialog, 二选一
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE); // 不要有标题。
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 不需要有背景。此处的背景为透明

        View view = inflater.inflate(R.layout.app_version_upgrade_dialog, container, false);
        bindViews(view);
        return view;
    }

    private void bindViews(View view) {
        TextView title = view.findViewById(R.id.title);
        TextView content = view.findViewById(R.id.content);
        Button versionUpdateBtn = view.findViewById(R.id.versionUpdate);

        versionUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                versionUpdateBtn.setEnabled(false);
                File targetFile = AppUtils.getApkFile(getActivity());
                AppVersionUpgrade.getInstance().getNetManager().download(mBean.getUrl(), targetFile, new INetDownloadCallBack() {
                    @Override
                    public void success(File apkFile) {
                        versionUpdateBtn.setEnabled(true);
                        dismiss();
                        // 安装代码
                        AppUtils.checkInstallApk(getActivity(), targetFile);
                    }

                    @Override
                    public void progress(int progress) {
                        // 更新界面的代码
                        Log.d(TAG, "progress: " + progress);
                        versionUpdateBtn.setText(progress + " %");
                    }

                    @Override
                    public void fail() {
                        versionUpdateBtn.setEnabled(true);
                        Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /**
     * Error:
     * FATAL EXCEPTION: main
     *   Process: com.hades.example.android, PID: 13784
     *   android.util.AndroidRuntimeException: requestFeature() must be called before adding content
     *     at com.android.internal.policy.PhoneWindow.requestFeature(PhoneWindow.java:388)
     *     at android.app.Dialog.requestWindowFeature(Dialog.java:1205)
     *     at com.hades.example.android._case.apk_upgrade.UpdateVersionDialog.onViewCreated(UpdateVersionDialog.java:68)
     */
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 不需要有背景
//    }
}
