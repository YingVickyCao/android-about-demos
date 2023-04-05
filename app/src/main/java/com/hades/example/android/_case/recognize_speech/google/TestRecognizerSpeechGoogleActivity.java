package com.hades.example.android._case.recognize_speech.google;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;

import java.util.List;

import ying.cao.simplepermissions.SimplePermissions;

/**
 * 使用原生Google 实现 语音识别
 * 如何测试？安装Voice Search /  Assistant of Google （前提是安装Google）, 同时使用VPN
 * https://www.cnblogs.com/xyzlmn/archive/2010/01/04/3168316.html
 * Android <=10
 */
public class TestRecognizerSpeechGoogleActivity extends AppCompatActivity {
    private static final String TAG = TestRecognizerSpeechGoogleActivity.class.getSimpleName();
    private TextView mVoice;
    private TextView mText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognizer_speech_google);

        mVoice = findViewById(R.id.voice);
        mText = findViewById(R.id.text);

        mVoice.setEnabled(isEnableRecognizerSpeech());
        mVoice.setOnClickListener(v -> clickVoice());
        SimplePermissions simplePermissions = new SimplePermissions(this);
        simplePermissions.request(new SimplePermissions.IPermissionCallback() {
            @Override
            public void granted() {
//                Toast.makeText(TestRecognizerSpeechGoogleActivity.this, "Granted", Toast.LENGTH_SHORT).show();
            }
        }, Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO,Manifest.permission.INTERNET);

        findViewById(R.id.checkIsEnableRecognizerSpeech).setOnClickListener(v -> isEnableRecognizerSpeech());
    }

    private boolean isEnableRecognizerSpeech() {
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), PackageManager.MATCH_DEFAULT_ONLY);
        Log.d(TAG, "isEnableRecognizerSpeech: size=" + activities.size());
        return !activities.isEmpty();
    }

    private void clickVoice() {
        try {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "");
            startActivityForResult(intent, 1000);
        } catch (ActivityNotFoundException ex) {
            Log.d(TAG, "clickVoice: not find recognize speech:ex:" + ex.getMessage());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode != RESULT_OK || data == null) {
                return;
            }
            List<String> texts = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            mText.setText(texts.get(0));
        }
    }
}
