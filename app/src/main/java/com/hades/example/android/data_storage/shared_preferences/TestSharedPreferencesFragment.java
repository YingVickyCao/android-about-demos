package com.hades.example.android.data_storage.shared_preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hades.example.android.R;
import com.hades.example.android.data_storage.DataStorageActivity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class TestSharedPreferencesFragment extends Fragment {
    private static final String TAG = TestSharedPreferencesFragment.class.getSimpleName();

    private final String FILE_NAME = "test_sf";
    private final String KEY_INT = "key_int";
    private final String KEY_BOOL = "key_bool";
    private final String KEY_FLOAT = "key_float";
    private final String KEY_STRING = "key_string";
    private final String KEY_STRING_SET = "key_string_set";

    private TextView mSavedText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.io_shared_preferences_layout, container, false);
        mSavedText = view.findViewById(R.id.saved_text);

        view.findViewById(R.id.write).setOnClickListener(arg0 -> write());
        view.findViewById(R.id.read).setOnClickListener(arg0 -> read());
        view.findViewById(R.id.checkSharedPreferencesIsSameInstance).setOnClickListener(v -> checkSharedPreferencesIsSameInstance());
        view.findViewById(R.id.runnableInMainThread).setOnClickListener(v -> runnableInMainThread());
        view.findViewById(R.id.testApis).setOnClickListener(v -> testApis());
        view.findViewById(R.id.testProcessSafe).setOnClickListener(v -> testProcessSafe());

        view.findViewById(R.id.testProcessSafe).setVisibility(getActivity() instanceof DataStorageActivity ? View.VISIBLE : View.GONE);
        return view;
    }

    private void read() {
        // 获取只能被本应用程序读、写的SharedPreferences对象
        // MODE_PRIVATE,MODE_WORLD_READABLE,MODE_WORLD_WRITEABLE,MODE_MULTI_PROCESS
        SharedPreferences preferences = getActivity().getSharedPreferences(FILE_NAME, MODE_PRIVATE);

        // 读取字符串数据
        String time = preferences.getString(KEY_STRING, null);
        // 读取int类型的数据
        int randNum = preferences.getInt(KEY_INT, 0);
        String result = time == null ? "您暂时还未写入数据" : "写入时间为：" + time + "\n上次生成的随机数为：" + randNum;
        mSavedText.setText(result);
    }

    private void write_PO_before() {
        // 获取只能被本应用程序读、写的SharedPreferences对象
        SharedPreferences preferences = getActivity().getSharedPreferences(FILE_NAME, MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(KEY_STRING, new Date().toString());
        editor.commit();

        SharedPreferences.Editor editor2 = preferences.edit();
        editor2.putInt(KEY_INT, (int) (Math.random() * 100));
        // Default in main  thread
        editor2.commit();
        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
        read();
    }

    private void write() {
        // 获取只能被本应用程序读、写的SharedPreferences对象
        if (null == getActivity()) {
            return;
        }
        SharedPreferences preferences = getActivity().getSharedPreferences(FILE_NAME, MODE_PRIVATE);

        Set<String> setOfString = new HashSet<>();
        setOfString.add("abc");
        setOfString.add("xyz");

        SharedPreferences.Editor editor = preferences.edit();
        Log.d(TAG, "write,Editor hashCode=" + editor.hashCode());
//        editor.putBoolean(KEY_BOOL, true);
//        editor.putInt(KEY_INT, (int) (Math.random() * 100));
        editor.putString(KEY_STRING, new Date().toString());
//        editor.putFloat(KEY_FLOAT, 1.5F);
//        editor.putStringSet(KEY_STRING_SET, setOfString);
        editor.apply();

        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
        read();
//        PreferenceManager.getDefaultSharedPreferences()
    }

    private void checkSharedPreferencesIsSameInstance() {
        Log.d(TAG, "checkSharedPreferencesIsSameInstance,context=" + getContext().hashCode());
        Log.d(TAG, "checkSharedPreferencesIsSameInstance,Activity context=" + getActivity().hashCode());
        Log.d(TAG, "checkSharedPreferencesIsSameInstance,Application context=" + getActivity().getApplication().hashCode());

        Log.d(TAG, "checkSharedPreferencesIsSameInstance: sf1 START");
        testSharedPreferences("sf1");
        Log.d(TAG, "checkSharedPreferencesIsSameInstance: sf1 END");

        Log.d(TAG, "checkSharedPreferencesIsSameInstance: sf2 START");
        testSharedPreferences("sf2");
        Log.d(TAG, "checkSharedPreferencesIsSameInstance: sf2 END");
    }

    private void testSharedPreferences(String fileName) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Log.d(TAG, "testSharedPreferences,Activity Context=" + getActivity().hashCode() + ",SF hasCode=" + sharedPreferences.hashCode());

        SharedPreferences sharedPreferences2 = getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Log.d(TAG, "testSharedPreferences,Activity Context=" + getActivity().hashCode() + ",SF hasCode=" + sharedPreferences2.hashCode());

        SharedPreferences sharedPreferences3 = getActivity().getApplication().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Log.d(TAG, "testSharedPreferences,Application Context=" + getActivity().getApplication().hashCode() + ",SF hasCode=" + sharedPreferences3.hashCode());

        SharedPreferences sharedPreferences4 = getActivity().getApplication().getSharedPreferences(fileName, Context.MODE_PRIVATE);
        Log.d(TAG, "testSharedPreferences,Application Context=" + getActivity().getApplication().hashCode() + ",SF hasCode=" + sharedPreferences4.hashCode());
    }

    private void runnableInMainThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // thread id =559,thread name=Thread-3
                Log.d(TAG, "runnableInMainThread,thread id =" + Thread.currentThread().getId() + ",thread name=" + Thread.currentThread().getName());
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_STRING, new Date().toString());
                editor.commit();
            }
        }).start();
    }

    private void testApis() {

    }

    private void testProcessSafe() {
    }
}