package com.hades.example.android._feature._web_based_contents._R8;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.eclipsesource.v8.V8;
import com.hades.example.android.R;

public class J2v8Fragment extends Fragment {
    public static final String TAG = J2v8Fragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: " + hashCode());
        View view = inflater.inflate(R.layout.j2v8_layout, container, false);
        view.findViewById(R.id.test).setOnClickListener(v -> test());
        return view;
    }

    private void test() {
        example_1();
    }

    private void example_1() {
        String js = "var hello = \"hello world\";\n"
                + "hello.length;";

        try (V8 runtime = V8.createV8Runtime()) {
            int result = runtime.executeIntegerScript(js);
            runtime.release(true);
            Log.i(TAG, "V8,result:length is " + result);
        } catch (Exception ex) {
            Log.e(TAG, "V8: ex:" + ex);
        }
    }

    private void example_1_backup() {
        String js = "var hello = \"hello world\";\n"
                + "hello.length;";

        V8 runtime = null;
        try {
            runtime = V8.createV8Runtime();
            int result = runtime.executeIntegerScript(js);
            Log.i(TAG, "V8,result:length is " + result);
        } finally {
            if (null != runtime) {
                try {
                    runtime.close();
                    runtime.release(true);
                } catch (Exception exception) {
                    Log.e(TAG, "V8: ex:" + runtime);
                }
            }
        }
    }
}
