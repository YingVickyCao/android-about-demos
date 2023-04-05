package com.hades.example.android._case.recognize_speech.google;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hades.example.android.R;

import java.util.List;
import java.util.Locale;

import ying.cao.simplepermissions.SimplePermissions;

/**
 * 使用原生Google 实现 语音识别
 * 如何测试？安装Voice Search /  Assistant of Google （前提是安装Google）, 同时使用VPN
 * https://www.cnblogs.com/xyzlmn/archive/2010/01/04/3168316.html
 */
public class TestRecognizerSpeechGoogleActivity2 extends AppCompatActivity {
    private static final String TAG = TestRecognizerSpeechGoogleActivity2.class.getSimpleName();
    private TextView mVoice;
    private TextView mText;
    SpeechRecognizer mSpeechRecognizer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recognizer_speech_google2);

        mVoice = findViewById(R.id.voice);
        mText = findViewById(R.id.text);

        mVoice.setEnabled(isEnableRecognizerSpeech());
        mVoice.setOnClickListener(v -> startVoice());
        findViewById(R.id.stopVoice).setOnClickListener(v -> stopVoice());
        SimplePermissions simplePermissions = new SimplePermissions(this);
        simplePermissions.request(new SimplePermissions.IPermissionCallback() {
            @Override
            public void granted() {
//                Toast.makeText(TestRecognizerSpeechGoogleActivity.this, "Granted", Toast.LENGTH_SHORT).show();
            }
        }, Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO, Manifest.permission.INTERNET);

        findViewById(R.id.checkIsEnableRecognizerSpeech).setOnClickListener(v -> isEnableRecognizerSpeech());
    }

    private boolean isEnableRecognizerSpeech() {
        boolean flag = SpeechRecognizer.isRecognitionAvailable(this);
        Log.d(TAG, "isEnableRecognizerSpeech:" + flag);
        return flag;
    }

    private void startVoice() {
        try {
            Log.d(TAG, "clickVoice: ");
            if (null == mSpeechRecognizer) {
                mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
                mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
                    @Override
                    public void onReadyForSpeech(Bundle params) {
                        Log.d(TAG, "onReadyForSpeech: ");
                    }

                    @Override
                    public void onBeginningOfSpeech() {
                        Log.d(TAG, "onBeginningOfSpeech: ");
                    }

                    @Override
                    public void onRmsChanged(float rmsdB) {
                        Log.d(TAG, "onRmsChanged: ");
                    }

                    @Override
                    public void onBufferReceived(byte[] buffer) {
                        Log.d(TAG, "onBufferReceived: ");
                    }

                    @Override
                    public void onEndOfSpeech() {
                        Log.d(TAG, "onEndOfSpeech: ");
                    }

                    @Override
                    public void onError(int error) {
                        Log.d(TAG, "onError: " + error + ":" + (recogError(error)));
                    }

                    public String recogError(int errorCode) {
                        String message;
                        switch (errorCode) {
                            case SpeechRecognizer.ERROR_AUDIO:
                                message = "音频问题";
                                break;
                            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                                message = "没有语音输入";
                                break;
                            case SpeechRecognizer.ERROR_CLIENT:
                                message = "其它客户端错误";
                                break;
                            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                                message = "权限不足";
                                break;
                            case SpeechRecognizer.ERROR_NETWORK:
                                message = "网络问题";
                                break;
                            case SpeechRecognizer.ERROR_NO_MATCH:
                                message = "没有匹配的识别结果";
                                break;
                            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                                message = "引擎忙";
                                break;
                            case SpeechRecognizer.ERROR_SERVER:
                                message = "服务端错误";
                                break;
                            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                                message = "连接超时";
                                break;
                            default:
                                message = "未知错误:" + errorCode;
                                break;
                        }
                        return message;
                    }

                    @Override
                    public void onResults(Bundle results) {
                        Log.d(TAG, "onResults: ");
                    }

                    @Override
                    public void onPartialResults(Bundle partialResults) {
                        Log.d(TAG, "onPartialResults: ");
                    }

                    @Override
                    public void onEvent(int eventType, Bundle params) {
                        Log.d(TAG, "onEvent: ");
                    }
                });

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, 3);
                intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH.toLanguageTag());
                intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 20000);
//            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
                intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 20000);


                // 自动监听是否结束，也可以手动停止
                mSpeechRecognizer.startListening(intent);
            }

        } catch (ActivityNotFoundException ex) {
            Log.d(TAG, "clickVoice: not find recognize speech:ex:" + ex.getMessage());
        }
    }

    private void stopVoice() {
        if (null != mSpeechRecognizer) {
            mSpeechRecognizer.stopListening();
            mSpeechRecognizer.cancel();
            mSpeechRecognizer.destroy();
            mSpeechRecognizer = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != mSpeechRecognizer) {
            mSpeechRecognizer.cancel();
            mSpeechRecognizer.destroy();
            mSpeechRecognizer = null;
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
