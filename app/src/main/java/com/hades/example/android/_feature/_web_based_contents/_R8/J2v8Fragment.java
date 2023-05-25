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
import com.eclipsesource.v8.V8Function;
import com.eclipsesource.v8.V8Object;
import com.eclipsesource.v8.utils.V8ObjectUtils;
import com.hades.example.android.R;
import com.hades.example.java.lib.FileUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        new Thread(() -> {
            try {
//                example_1();
//                example_1_backup();
//                example_2();
//                example_3();
//                example_4();
//                example_5();
                example_7();

            } catch (Exception ex) {
                Log.d(TAG, "V8, ex:" + ex);
            }
        }).start();

//        example_6();
    }

    // 标准做法：
    // 1 Release java resource
    // 2 Release native resource

    // Java 调用 Js - 方式1 ： 直接执行一个String，这个String是JS脚本。
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

    // Java 调用 Js - 方式2:    通过函数名。
    // Java to call Javascript function
    private void example_2() throws IOException {
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
        try (V8 runtime = V8.createV8Runtime(); V8Array parameters = new V8Array(runtime).push(100).push(200)) {
            runtime.executeVoidScript(getJs2("v8_example_js_4.js"));
            int result = runtime.executeIntegerFunction("sum", parameters); // 300
            Log.d(TAG, "example_4: " + result);
        }

        // case 3 :
        try (V8 runtime = V8.createV8Runtime()) {
            runtime.executeVoidScript(getJs2("v8_example_js_5.js"));
            try (V8Object v8Object = runtime.getObject("team");
                 V8Array parameters = new V8Array(runtime).push(10).push(20)) {
                int result = v8Object.executeIntegerFunction("addPlayer", parameters); // 30
                Log.d(TAG, "example_4: " + result);
            }
        }
    }

    // Java 调用 Js - 方式3 ：通过V8Function对象调用。
    private void example_3() throws IOException {
        try (V8 runtime = V8.createV8Runtime()) {
            runtime.executeVoidScript(getJs2("v8_example_js_4.js"));
            if (V8.V8_FUNCTION == runtime.getType("sum")) { // sum是一个函数
                try (V8Function sumFunction = (V8Function) runtime.getObject("sum");
                     V8Array parameters = new V8Array(runtime).push(1000).push(2000)) {
                    Object result = sumFunction.call(null, parameters);
                    Log.d(TAG, "example_3: " + result);
                    if (result instanceof Releasable) {
                        ((Releasable) result).close();
                    }
                }
            }
        }
    }

    // Java to access Javascript Objects
    private void example_4() throws IOException {
        try (V8 runtime = V8.createV8Runtime()) {
            runtime.executeVoidScript(getJs2("v8_example_js_2.js"));
            try (V8Object person = runtime.getObject("person");
                 V8Object team = person.getObject("team")) {
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
    private void example_5() throws IOException {
        try (V8 runtime = V8.createV8Runtime()) {
            runtime.executeVoidScript(getJs2("v8_example_js_3.js"));

            try (V8Object team = runtime.getObject("team");
                 V8Object name1 = new V8Object(runtime).add("name", "Chris");
                 V8Object name2 = new V8Object(runtime).add("name", "Jerry");
                 V8Array array = new V8Array(runtime).push(name1).push(name2);
                 V8Object newTeam = team.add("players", array)) {
                String[] key = newTeam.getKeys();
                Log.d(TAG, "example_3: " + Arrays.toString(key)); //  [players]
            }
        }
    }

    // Js 调用 Java
    // Register Java Callbacks with J2V8.
    // Java callback allow JavaScript to invoke Java methods.
    // With J2V8, JavaScript function can be mapped to a Java method. When the function is invoked, J2V8 will call the Java method instead, passing the JS arguments to Java.
    private void example_7() {
//        example_5_1();
//        example_7_2();
//        example_7_3();
//        example_7_4();
    }

    // Js 调用 Java，方式1 ： JavaVoidCallback
    private void example_7_1() {
        // Example : register Java method using JavaVoidCallback
        JavaVoidCallback callback = (receiver, parameters) -> {
            if (parameters.length() > 0) {
                Object arg1 = parameters.get(0);
                Log.d(TAG, "example_5,invoke: " + arg1);
                if (arg1 instanceof Releasable) {
                    ((Releasable) arg1).close();
                }
            }
        };
        try (V8 runtime = V8.createV8Runtime()) {
            runtime.registerJavaMethod(callback, "print");
            runtime.executeScript("print('hello world');");
        }
    }

    // Js 调用 Java，方式1 ： JavaCallback
    private void example_7_2() {
        // Example : register Java method using JavaCallback
        // This example was registered on the V8 runtime itself.
        JavaCallback callback = (receiver, parameters) -> {
            /**
             * Arguments:
             * Arguments is passed from Javascript to Java method. => V8Array parameters
             * parameters should be released because they were returned to you as a result of a method call.
             * The receiver : The Javascript object on which the function was called is passed as the first parameter.
             */
            if (parameters.length() > 1) {
                Object arg1 = parameters.get(0);
                if (arg1 instanceof Releasable) {
                    ((Releasable) arg1).close();
                }

                Object arg2 = parameters.get(1);
                if (arg2 instanceof Releasable) {
                    ((Releasable) arg2).close();
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
        };
        try (V8 runtime = V8.createV8Runtime()) {
            runtime.registerJavaMethod(callback, "sum");
            runtime.executeScript("sum(1,2)");
        }
    }

    // Js 调用 Java，方式2 ： 反射
    private void example_7_3() {
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
        try (V8 runtime = V8.createV8Runtime(); V8Object v8Console = new V8Object(runtime)) {
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


    // Multithreaded Javascript with J2V8
    // you can now interact with J2V8 from different threads, with one rule:All interactions with a V8Runtime must be from the thread that instantiates the Runtime.
    // This means that you can create a V8Runtime for each thread in the system, and each thread can interact with its own V8Runtime independent of each other
    private void example_6() {
        try {
            Log.d(TAG, "example_6: " + Thread.currentThread().getName());
            int threadSize = 4;
            final List<Thread> threads = new ArrayList<>();
            List<Object> mergeSortResults = new ArrayList<>();
            for (int i = 0; i < threadSize; i++) {
                Thread t = new Thread(() -> {
                    Log.d(TAG, "example_6,new thread: " + Thread.currentThread().getName());
                    V8 runtime = V8.createV8Runtime();      // 1 Closeable
                    runtime.registerJavaMethod(new Sort(), "_sort");
                    String sortAlgorithm = null;
                    try {
                        sortAlgorithm = getJs2("v8_example_js_6.js");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    runtime.executeScript(sortAlgorithm);

                    V8Array data = new V8Array(runtime); // 2 Closeable
                    int max = 50;
                    for (int j = 0; j < max; j++) {
                        data.push(max - j);
                    }
                    V8Array parameters = new V8Array(runtime).push(data);   // 3 Closeable
                    V8Array result = runtime.executeArrayFunction("sort", parameters);  // 4 Closeable
                    synchronized (threads) {
                        mergeSortResults.add(V8ObjectUtils.toList(result));
                    }
                    result.close();
                    parameters.close();
                    data.close();
                    runtime.close();

                });
                threads.add(t);
            }

            for (Thread thread : threads) {
                thread.start();
            }
            for (Thread thread : threads) {
                thread.join();
            }

            for (int i = 0; i < threadSize; i++) {
                List<Integer> result = (List<Integer>) mergeSortResults.get(i);
                System.out.print("example_6,result:" + Thread.currentThread().getName());
                for (int j = 0; j < result.size(); j++) {
                    System.out.print(result.get(j) + " ");
                }
                System.out.println();
            }
        } catch (Exception ex) {
            Log.d(TAG, "example_6: ex:" + ex);
        }
    }

    class Sort implements JavaCallback {
        List<Object> result = null;

        @Override
        public Object invoke(V8Object receiver, V8Array parameters) {
            final List<Object> data = V8ObjectUtils.toList(parameters);

            Thread t = new Thread(() -> {
                Log.d(TAG, "example_6,invoke[1]: " + Thread.currentThread().getName());
                V8 runtime = V8.createV8Runtime(); // 1 Closeable
                runtime.registerJavaMethod(new Sort(), "_sort");
                String sortAlgorithm;
                try {
                    sortAlgorithm = getJs2("v8_example_js_6.js");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                runtime.executeVoidScript(sortAlgorithm);
                V8Array _parameters = V8ObjectUtils.toV8Array(runtime, data);  // 2 Closeable
                V8Array _result = runtime.executeArrayFunction("sort", _parameters);  // 3 Closeable
                result = V8ObjectUtils.toList(_result);
                _result.close();
                _parameters.close();
                runtime.close();
            });
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Log.d(TAG, "example_6,invoke[2]: " + Thread.currentThread().getName());
            return V8ObjectUtils.toV8Array(parameters.getRuntime(), result);
        }
    }

    // Implementing WebWorkers with J2V8
}
