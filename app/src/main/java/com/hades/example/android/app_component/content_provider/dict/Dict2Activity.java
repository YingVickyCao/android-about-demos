package com.hades.example.android.app_component.content_provider.dict;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;
import com.hades.example.android.app_component.content_provider.dict.common.Dict2;

public class Dict2Activity extends AppCompatActivity {
    private static final String TAG = "Dict2Activity";
    SharedPreferences sharedPreferences;
    private TextView result;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_provider_dict2);

        result = findViewById(R.id.result);
        findViewById(R.id.query).setOnClickListener(v -> query());

        sharedPreferences = getSharedPreferences(Dict2.FILE_NAME, 0);
    }

    private void query() {
        long timestamp = sharedPreferences.getLong(Dict2.DictColumns._ID, -1L);
        result.setText(String.valueOf(timestamp));
        Log.e(TAG, "query: " + timestamp);
    }
}