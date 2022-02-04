package com.hades.example.android.other_ui._dialog.depressed;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hades.example.android.R;

public class CustomDialog extends Dialog {
    private static final String TAG = "CustomDialog";

    /**
     * 显示的图片
     */
    private ImageView imageIv;

    private TextView title;
    private TextView message;
    private Button negtive, positive;
    private View negtiveContainer, positiveContainer;
    private LinearLayout customView;

    CustomDialogBean bean;

    public CustomDialog(@NonNull Context context) {
        super(context);
        this.bean = bean;
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.bean = bean;
    }

    protected CustomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setBean(CustomDialogBean bean) {
        this.bean = bean;
    }

    private void init() {
        setContentView(R.layout.other_ui_alertdialog_custom_dialog);
        setCanceledOnTouchOutside(true);
        findViews();
        setViews();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void setViews() {
        if (null == bean) {
            return;
        }
        //如果用户自定了title和message
        if (!TextUtils.isEmpty(bean.getTitle())) {
            title.setText(bean.getTitle());
            title.setVisibility(View.VISIBLE);
        } else {
            title.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(bean.getMessage())) {
            message.setVisibility(View.VISIBLE);
            message.setText(bean.getMessage());
        } else {
            message.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(bean.getPositiveText())) {
            positiveContainer.setVisibility(View.VISIBLE);
            positive.setText(bean.getPositiveText());
            positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: Positive");
                    dismiss();
                    if (null != bean.getPositiveClickListener()) {
                        bean.getPositiveClickListener().onClick(view);
                    }
                }
            });
        } else {
            positiveContainer.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(bean.getNegativeText())) {
            negtiveContainer.setVisibility(View.VISIBLE);
            negtive.setText(bean.getNegativeText());
            negtive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: Negative");
                    dismiss();
                    if (null != bean.getNegativeClickListener()) {
                        bean.getNegativeClickListener().onClick(view);
                    }
                }
            });
        } else {
            negtiveContainer.setVisibility(View.GONE);
        }

        if (bean.getCustomView() != null) {
            customView.addView(bean.getCustomView());
            customView.setVisibility(View.VISIBLE);
        } else {
            customView.setVisibility(View.GONE);
        }
    }

    private void findViews() {
        negtiveContainer = findViewById(R.id.negativeContainer);
        positiveContainer = findViewById(R.id.positiveContainer);
        negtive = findViewById(R.id.negative);
        positive = findViewById(R.id.positive);
        title = findViewById(R.id.title);
        message = findViewById(R.id.message);
        imageIv = findViewById(R.id.image);
        customView = findViewById(R.id.custom_view);
    }
}