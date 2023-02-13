package com.hades.example.android._case.apk_upgrade;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.hades.example.android.R;

public class UpdateVersionDialog extends DialogFragment {
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
        View view = inflater.inflate(R.layout.app_version_upgrade_dialog, container, false);
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
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // 不需要有背景
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
}
