package com.hades.example.android.resource._layout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;

public class LoadDiffLayoutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.res_activity_load_diff_layout_main);
        this.setTitle(R.string.layoutMain);  //设置标题栏名称
    }

    public void buttonClick(View v) {
        switch (v.getId()) {
            case R.id.buttonLayout1:
                setContentView(R.layout.res_activity_load_diff_layout_1);  //加载layout_1布局文件
                this.setTitle(R.string.layout1);  //设置标题栏名称
                //点击按钮，回到activity_main布局文件
                //并设置标题栏名称
                Button buttonBack1 = findViewById(R.id.back_to_layout_main);
                buttonBack1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setContentView(R.layout.res_activity_load_diff_layout_main);
                        setTitle(R.string.layoutMain);
                    }
                });
                break;
            case R.id.buttonLayout2: {
                setContentView(R.layout.res_activity_load_diff_layout_2);  //加载layout_2布局文件
                this.setTitle(R.string.layout2);  //设置标题栏名称
                Button back = findViewById(R.id.back_to_layout_main);
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setContentView(R.layout.res_activity_load_diff_layout_main);
                        setTitle(R.string.layoutMain);
                    }
                });
            }
            break;
            default:
                break;
        }
    }
}
