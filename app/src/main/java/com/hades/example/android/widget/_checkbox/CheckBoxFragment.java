package com.hades.example.android.widget._checkbox;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;

import com.github.yingvickycao.autils.base.BaseFragment;
import com.hades.example.android.R;

public class CheckBoxFragment extends BaseFragment {
    private static final String TAG = CheckBoxFragment.class.getSimpleName();

    private CheckBox checkBox;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_checkbox, container, false);

        view.findViewById(R.id.toggle).setOnClickListener(v -> toggleCheckboxCheckedStatus());
        checkBox = view.findViewById(R.id.checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {  // setChecked(bool) or Clicked
                // isChecked=false,text=Is Friday
                // isChecked=true,text=Is Friday
                Log.d(TAG, "onCheckedChanged: isChecked=" + isChecked + ",text=" + buttonView.getText());
            }
        });
        return view;
    }

    private void toggleCheckboxCheckedStatus() {
        if (null != checkBox) {
            checkBox.setChecked(!checkBox.isChecked());
        }
    }
}