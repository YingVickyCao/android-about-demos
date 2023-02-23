package com.hades.example.android.test.apk_upgrade;

import android.content.DialogInterface;
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
        versionUpdateBtn.setOnClickListener(v -> clickVersionUpdate((Button) v));
    }

    private boolean isMD5Valid(@NonNull File targetFile) {
        String fileMD5 = AppUtils.getFileMD5(targetFile);
        return fileMD5 != null && fileMD5.equals(mBean.getMd5());
    }

    private void clickVersionUpdate(Button versionUpdateBtn) {
        versionUpdateBtn.setEnabled(false);
        File targetFile = AppUtils.getApkFile(getActivity());
        if (targetFile.exists()) {
            if (isMD5Valid(targetFile)) {
                Log.d(TAG, "clickVersionUpdate: exist apk, install directly");
                dismiss();
                AppUtils.checkInstallApk(getActivity(), targetFile);
            } else {
                downloadApk(versionUpdateBtn, targetFile);
            }
        } else {
            downloadApk(versionUpdateBtn, targetFile);
        }
    }

    private void downloadApk(Button versionUpdateBtn, File targetFile) {
        AppVersionUpgrade.getInstance().getNetManager().download(mBean.getUrl(), targetFile, new INetDownloadCallBack() {
            @Override
            public void success(File apkFile) {
                versionUpdateBtn.setEnabled(true);
                dismiss();
                // check md5 : 不一致，说明被修改了，或 没有下载完全。大多数时，md5一样，说明文件一样
                if (isMD5Valid(apkFile)) {
                    Log.d(TAG, "success: download success");
                    // 安装代码
                    AppUtils.checkInstallApk(getActivity(), targetFile);
                } else {
                    Log.d(TAG, "success: download success but md5 is wrong");
                    Toast.makeText(getActivity(), "MD5 file is wrong", Toast.LENGTH_LONG).show();
                }
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
                // cancel后： Error:java.lang.NullPointerException: Attempt to invoke virtual method 'java.lang.String android.content.Context.getPackageName()' on a null object reference
                // Fix : check call.isCanceled()
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            }
        }, UpdateVersionDialog.this);
    }

    /**
     * Error:
     * FATAL EXCEPTION: main
     * Process: com.hades.example.android, PID: 13784
     * android.util.AndroidRuntimeException: requestFeature() must be called before adding content
     * at com.android.internal.policy.PhoneWindow.requestFeature(PhoneWindow.java:388)
     * at android.app.Dialog.requestWindowFeature(Dialog.java:1205)
     * at com.hades.example.android._case.apk_upgrade.UpdateVersionDialog.onViewCreated(UpdateVersionDialog.java:68)
     */
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 不需要有背景
//    }
    
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d(TAG, "onDismiss: ");
        AppVersionUpgrade.getInstance().getNetManager().cancel(UpdateVersionDialog.this);
    }
}
