package com.hades.example.android.app_component.service.boundservice;

import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.hades.example.android.BConstant;
import com.hades.example.android.R;
import com.hades.example.android.b.IRemoteService;
import com.hades.utility.jvm.ThreadUtils;

/**
 * 测试 bound service - .aidl
 *
 * bound service using aidl failed:
 * https://blog.csdn.net/qq_37506816/article/details/128643540
 *
 */
public class TestRemoteBoundServiceActivity3 extends Activity {
    private static final String TAG = TestRemoteBoundServiceActivity3.class.getSimpleName();

    // 保持所启动的Service的IBinder对象
    IRemoteService mBinder;
    // 定义一个ServiceConnection对象
    private ServiceConnection mConnection;
    boolean bound;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_bounded_service_test);

        ((TextView) findViewById(R.id.topic)).setText("Remote BoundService");

        setServiceConnection();

        findViewById(R.id.bind).setOnClickListener(v -> bindService());
        findViewById(R.id.bindAutoCreate).setOnClickListener(v -> bindAutoCreate());
        findViewById(R.id.bindAutoCreateInThread).setOnClickListener(v -> bindAutoCreateInThread());
        findViewById(R.id.unbind).setOnClickListener(v -> unbindService());
//        findViewById(R.id.getServiceStatus).setOnClickListener(v -> getServiceStatus());

        findViewById(R.id.start).setOnClickListener(v -> startService());
        findViewById(R.id.stopRecord).setOnClickListener(v -> stopService());
        findViewById(R.id.check).setOnClickListener(v -> check());
    }

    private Intent buildIntent() {
        Intent intent = new Intent();
        intent.setAction(BConstant.B_REMOTEBOUNDEDSERVICE2_CLASS);
        intent.setPackage(BConstant.B_PACKAGE);
//        intent.setComponent(new ComponentName(BConstant.B_PACKAGE, BConstant.B_REMOTEBOUNDEDSERVICE2_CLASS));
        return intent;
    }

    private void bindService() {
        Log.d(TAG, "bindService: ");
        Intent intent = buildIntent();
        bindService(intent, mConnection, 0);
    }

    private void bindAutoCreate() {
        Log.d(TAG, "bindService: ");
        Intent intent = buildIntent();
        bindService(intent, mConnection, Service.BIND_AUTO_CREATE);
    }

    private void bindAutoCreateInThread() {
        new Thread(() -> {
            Log.d(TAG, "bindAutoCreateInThread->run: " + ThreadUtils.getThreadInfo());
            Intent intent = buildIntent();
            bindService(intent, mConnection, Service.BIND_AUTO_CREATE);
        }).start();
    }

    private void unbindService() {
        // Unbind from the service
        if (bound) {
            unbindService(mConnection);
            bound = false;
        }
    }

    // TODO: android.app.BackgroundServiceStartNotAllowedException: Not allowed to start service Intent { act=com.hades.example.android.b.bound_service.RemoteBoundedService2 pkg=com.hades.example.android.b cmp=com.hades.example.android.b/.bound_service.RemoteBoundedService2 }: app is in background
    //  uid null
    private void startService() {
        Log.d(TAG, "startService: ");
        Intent intent = buildIntent();
        startService(intent);
    }

    private void stopService() {
        Log.d(TAG, "stopService: ");
        Intent intent = buildIntent();
        stopService(intent);
    }

    private void check() {
        if (mBinder != null) {
            try {
                // main thread
                int value = mBinder.getPid(); // getPid() 在bound service 运行在子线程中
                Toast.makeText(this, value + "", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                Log.e(TAG, "check: " + ex.getMessage());
            }
        }
    }

    private void setServiceConnection() {
        /**
         * ServiceConnection 用于监听访问者与Service之间的连接情况
         */
        mConnection = new ServiceConnection() {
            // 当该Activity与Service连接成功时回调该方法
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG, "onServiceConnected: ");
                // 获取Service的onBind()方法所返回的IBinder - MyBinder对象 ,访问者通过IBinder与Service进行通信。
                mBinder = IRemoteService.Stub.asInterface(service);  // ①
                bound = true;
            }

            // 当Service所在当宿主进程由于异常终止或者其他原因终止，导致该Service与访问者之间断开连接时，回调该方法
            // 当调用者主动使用 unbindService()时，不回调该方法。
            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG, "onServiceDisconnected: ");
                bound = false;
                mBinder = null;
            }
        };
    }

}