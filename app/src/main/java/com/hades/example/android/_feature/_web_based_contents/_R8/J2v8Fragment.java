package com.hades.example.android._feature._web_based_contents._R8;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.Releasable;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.hades.example.android.R;
import com.hades.example.android.app_component._activity._life_cycle.C;
import com.hades.example.java.lib.FileUtils;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;

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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    example_1();
//                    example_2();
//                    example_3();
//                    example_4();
                    example_5();
                } catch (Exception ex) {
                    Log.d(TAG, "V8, ex:" + ex);
                }
            }
        }).start();
    }

    // 标准做法：
    // 1 Release java resource
    // 2 Release native resource
    private void example_1() {
        // V8 is Closeable
        try (V8 runtime = V8.createV8Runtime()) {
            //        String js = getJs();
            String js = getJs2();
            int result = runtime.executeIntegerScript(js);
            Log.i(TAG, "V8,result:length is " + result);
        } catch (Exception ex) {
            Log.e(TAG, "V8: ex:" + ex);
        }
    }

    private String getJs() {
        return "var hello = \"hello world\";\n" + "hello.length;";
    }

    private String getJs2() throws IOException {
        return getJs2("v8_example_js_1.js");
    }

    private String getJs2(String fileName) throws IOException {
        InputStream inputStream = getContext().getResources().getAssets().open(fileName);
        return new FileUtils().convertStreamToStr(inputStream);
    }

    private void example_1_backup() {
        String js = "var hello = \"hello world\";\n" + "hello.length;";

        V8 runtime = null;
        try {
            runtime = V8.createV8Runtime();
            int result = runtime.executeIntegerScript(js);
            Log.i(TAG, "V8,result:length is " + result);
        } finally {
            if (null != runtime) {
                try {
                    runtime.close();
                } catch (Exception exception) {
                    Log.e(TAG, "V8: ex:" + runtime);
                }
            }
        }
    }

    // Java to access Javascript Objects
    private void example_2() throws IOException {
        try (V8 runtime = V8.createV8Runtime();) {
            runtime.executeVoidScript(getJs2("v8_example_js_2.js"));
            try (V8Object person = runtime.getObject("person"); V8Object team = person.getObject("team")) {
                String firstName = person.getString("firstName");
                int id = team.getInteger("id");
                Log.d(TAG, "example_2:firstName=" + firstName + ",id=" + id);
                try (V8Object newTeam = team.add("id", 10)) {
                    Log.d(TAG, "example_2:,updated id =" + team.getInteger("id"));
                }
            }
        }
    }


    // Java to access V8 Arrays
    private void example_3() throws IOException {
        try (V8 runtime = V8.createV8Runtime()) {
            runtime.executeVoidScript(getJs2("v8_example_js_3.js"));

            try (V8Object team = runtime.getObject("team"); V8Object name1 = new V8Object(runtime).add("name", "Chris");
                 V8Object name2 = new V8Object(runtime).add("name", "Jerry");
                 V8Array array = new V8Array(runtime).push(name1).push(name2);
                 V8Object newTeam = team.add("players", array)) {
                String[] key = newTeam.getKeys();
                Log.d(TAG, "example_3: " + Arrays.toString(key)); //  [players]
            }
        }
    }

    // Java to call Javascript function
    private void example_4() throws IOException {
        // case 1 :
        try (V8 runtime = V8.createV8Runtime()) {
            runtime.executeVoidScript(getJs2("v8_example_js_4.js"));
            Object result = runtime.executeJSFunction("sum", 1, 5); // 6
            if (result instanceof Integer) {
                int sumResult = (int) result;
                Log.d(TAG, "example_4: " + sumResult);
            }
        }

        // case 2 :
        try (V8 runtime = V8.createV8Runtime();
             V8Array parameters = new V8Array(runtime).push(100).push(200)) {
            runtime.executeVoidScript(getJs2("v8_example_js_4.js"));
            int result = runtime.executeIntegerFunction("sum", parameters); // 300
            Log.d(TAG, "example_4: " + result);
        }

        // case 3 :
        try (V8 runtime = V8.createV8Runtime()) {
            runtime.executeVoidScript(getJs2("v8_example_js_5.js"));
            try (V8Object v8Object = runtime.getObject("team"); V8Array parameters = new V8Array(runtime).push(10).push(20)) {
                int result = v8Object.executeIntegerFunction("addPlayer", parameters); // 30
                Log.d(TAG, "example_4: " + result);
            }
        }
    }

    // Register Java Callbacks with J2V8.
    // Java callback allow JavaScript to invoke Java methods.
    // With J2V8, JavaScript function can be mapped to a Java method. When the function is invoked, J2V8 will call the Java method instead, passing the JS arguments to Java.
    private void example_5() throws IOException {
//        example_5_1();
        example_5_2();
        example_5_3();
    }

    private void example_5_1() {
        // Example : register Java method using JavaVoidCallback
        JavaVoidCallback callback = new JavaVoidCallback() {
            @Override
            public void invoke(V8Object receiver, V8Array parameters) {
                if (parameters.length() > 0) {
                    Object arg1 = parameters.get(0);
                    Log.d(TAG, "example_5,invoke: " + arg1);
                    if (arg1 instanceof Releasable) {
                        ((Releasable) arg1).release();
                    }
                }
            }
        };
        try (V8 runtime = V8.createV8Runtime();
             V8Object v8Object = runtime.registerJavaMethod(callback, "print")) {
            runtime.executeScript("print('hello world');");
        }
    }

    private void example_5_2() {
        // Example : register Java method using JavaCallback
        // This example was registered on the V8 runtime itself.
        JavaCallback callback = new JavaCallback() {
            @Override
            public Object invoke(V8Object receiver, V8Array parameters) {
                /**
                 * Arguments:
                 * Arguments is passed from Javascript to Java method. => V8Array parameters
                 * parameters should be released because they were returned to you as a result of a method call.
                 * The receiver : The Javascript object on which the function was called is passed as the first parameter.
                 */
                if (parameters.length() > 1) {
                    Object arg1 = parameters.get(0);
                    if (arg1 instanceof Releasable) {
                        ((Releasable) arg1).release();
                    }

                    Object arg2 = parameters.get(1);
                    if (arg2 instanceof Releasable) {
                        ((Releasable) arg2).release();
                    }
                    int sum = Integer.parseInt(arg1.toString()) + Integer.parseInt(arg2.toString());
                    Log.d(TAG, "example_5,sum=: " + sum);

                    if (!parameters.isReleased()) {
                        parameters.close();
                    }

                    if (!receiver.isReleased()) {
                        receiver.close();
                    }
                    return sum;
                }

                if (!parameters.isReleased()) {
                    parameters.close();
                }
                if (!receiver.isReleased()) {
                    receiver.close();
                }
                return 0;
            }
        };
        try (V8 runtime = V8.createV8Runtime()) {
            runtime.registerJavaMethod(callback, "sum");
            runtime.executeScript("sum(1,2)");
        }
    }

    private void example_5_3() {
        // Register method reflectively
        // This example were registered on an existing JavaScript object (console)
        Console console = new Console();

//        try (V8 runtime = V8.createV8Runtime();
//             V8Object v8Console = new V8Object(runtime)) {
//            runtime.add("console", v8Console);
//            v8Console.registerJavaMethod(console, "log", "log", new Class[]{String.class});
//            v8Console.registerJavaMethod(console, "error", "error", new Class[]{String.class});
//            runtime.executeScript("console.log('China');");
//        }


        // This example were registered on an not-existing JavaScript object (console)
        try (V8 runtime = V8.createV8Runtime();
             V8Object v8Console = new V8Object(runtime)) {
            runtime.add("CustomConsole", v8Console);
            v8Console.registerJavaMethod(console, "log", "log", new Class[]{String.class});
            v8Console.registerJavaMethod(console, "error", "error", new Class[]{String.class});
            runtime.executeScript("CustomConsole.log('China');");
        }
    }

    final class Console {
        public void log(final String message) {
            Log.i(TAG, "log: " + message);
        }

        public void error(final String message) {
            Log.e(TAG, "error: " + message);
        }
    }


    // Implement WebWorkers with J2V8.
    // Multithreaded Javascript with J2V8
    private void example_6() {

    }

}
