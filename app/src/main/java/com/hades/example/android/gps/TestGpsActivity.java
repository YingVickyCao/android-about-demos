package com.hades.example.android.gps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;
import com.hades.example.android.R;
import com.hades.utility.permission.OnContextUIListener;
import com.hades.utility.permission.OnPermissionResultCallback;
import com.hades.utility.permission.PermissionsTool;

import java.util.List;

/**
 * Samsung s10  = Samsung SM-G9730
 * 位置服务
 * GPS（美国）,Glonass（俄罗斯）,Beidou（中国）,Galileo(欧盟/欧洲)
 * https://www.samsung.com/cn/smartphones/galaxy-s10/specs/
 */

public class TestGpsActivity extends AppCompatActivity {
    private static final String TAG = TestGpsActivity.class.getSimpleName();

    LocationManager mLocationManager;
    private LocationListener mLocationListener;

    private ProximityAlertReceiver mProximityAlertReceiver;
    public final static String PROXIMITY_ALERT_ACTION = "PROXIMITY_ALERT_ACTION";

    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;

    private final String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private PermissionsTool permissionTools;

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); // 获取系统的LocationManager对象
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this); // Google play store Location
        lastLocation();
        lastLocation_4_GooglePlayServices();
        requestPermission();
    }

    protected void requestPermission() {
        permissionTools = new PermissionsTool(this);
        permissionTools.request(permissions, new OnPermissionResultCallback() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(TestGpsActivity.this, "ACCESS_FINE_LOCATION/ACCESS_COARSE_LOCATION granted", Toast.LENGTH_SHORT).show();
                listAllFreeLocationProviders();
                listAllLocationProviders();

                doResume();
                proximityAlert();
                initLocationUpdates();
                initLocationUpdates_4_GooglePlayServices();
            }

            @Override
            public void onPermissionDenied() {

            }

            @Override
            public void onPermissionError(String message) {

            }

            @Override
            public void showInContextUI(OnContextUIListener callback) {
                Snackbar.make(findViewById(R.id.root), "Request Location permission", Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.ok), view -> callback.ok())
                        .setAction(getString(R.string.cancel), view -> callback.cancel())
                        .show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (permissionTools.isGranted(permissions)) {
            doResume();
        }
    }

    private void doResume() {
        if (null == mProximityAlertReceiver) {
            mProximityAlertReceiver = new ProximityAlertReceiver();
            ContextCompat.registerReceiver(this, mProximityAlertReceiver, new IntentFilter(PROXIMITY_ALERT_ACTION), Context.RECEIVER_NOT_EXPORTED);
            startLocationUpdates();
            startLocationUpdates_4_GooglePlayServices();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (permissionTools.isGranted(permissions)) {
            doPause();
        }
    }

    private void doPause() {
        if (null != mProximityAlertReceiver) {
            unregisterReceiver(mProximityAlertReceiver);
            mProximityAlertReceiver = null;
            stopLocationUpdates();
            stopLocationUpdates_4_GooglePlayServices();
        }
    }

    private void listAllLocationProviders() {
//        List<String> providerNames = mLocationManager.getAllProviders(); // Samsung SM-G9730 passive, gps, network
        List<String> providerNames = mLocationManager.getProviders(true); // Samsung SM-G9730 passive

        TextView textView = findViewById(R.id.allProviders);
        textView.setText(providerNames.toString());
    }

    private void listAllFreeLocationProviders() {
        Criteria cri = new Criteria();
        cri.setCostAllowed(false);
        cri.setAltitudeRequired(true);
        cri.setBearingRequired(true);

        List<String> providerNames = mLocationManager.getProviders(cri, false); // Samsung SM-G9730: gps

//        List<String> providerNames = mLocationManager.getProviders(cri, true); // Samsung SM-G9730: null

        TextView textView = findViewById(R.id.allFreeProviders);
        textView.setText(providerNames.toString());
    }

    @SuppressLint("MissingPermission")
    private void proximityAlert() {
        /**
         * Samsung SM-G9730: Not Produced
         * Android emulator：send mock data， Not Produced
         */
        double longitude = 30;
        double latitude = 50;
        float radius = 5000; // 定义半径（5公里）

        Intent intent = new Intent(PROXIMITY_ALERT_ACTION);
        // 定义Intent
        PendingIntent pi;
        // 将Intent包装成PendingIntent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pi = PendingIntent.getBroadcast(this, -1, intent, PendingIntent.FLAG_MUTABLE);
        } else {
            pi = PendingIntent.getBroadcast(this, -1, intent, 0);
        }
        mLocationManager.addProximityAlert(latitude, longitude, radius, -1, pi); // 添加临近警告
    }

    @SuppressLint("MissingPermission")
    private void lastLocation() {
        /**
         * Samsung SM-G9730: Not Produced
         * Android emulator：send mock data，Produced
         */
        Location location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER); // Request Runtime permission Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        String info = parseLocation(location);
        Log.d(TAG, "lastLocation: " + info);
        setLocationInfo(location);
    }

    @SuppressLint("MissingPermission")
    private void lastLocation_4_GooglePlayServices() {
        /**
         * Samsung SM-G9730: Not Produced
         * Android emulator：send mock data，Produced
         */
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (null == location) {
                return;
            }
            String info = parseLocation(location);
            Log.d(TAG, "lastLocation 4 GooglePlayServices: " + info);
        });
    }

    private void initLocationUpdates() {
        if (null == mLocationListener) {
            mLocationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    if (null == location) {
                        return;
                    }
                    String info = parseLocation(location);
                    Log.d(TAG, "onLocationChanged: " + info);
                    setLocationInfo(location);
                }

                @Override
                public void onProviderDisabled(String provider) {
                    setLocationInfo(null);
                }

                @Override
                public void onProviderEnabled(String provider) {
                }
            };
        }
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        /**
         * Samsung SM-G9730: Not Produced
         * Android emulator：send mock data，Produced
         */
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, mLocationListener);
    }

    private void stopLocationUpdates() {
        mLocationManager.removeUpdates(mLocationListener);
    }

    public void setLocationInfo(Location newLocation) {
        TextView textView = findViewById(R.id.lastLocation);
        String info = parseLocation(newLocation);
        textView.setText(info);
    }

    public String parseLocation(Location newLocation) {
        if (newLocation == null) {
            return "";
        }
        return "经度：" + newLocation.getLongitude() + "," +
                "纬度：" + newLocation.getLatitude() + "," +
                "高度：" + newLocation.getAltitude() + "," +
                "速度：" + newLocation.getSpeed() + "," +
                "方向：" + newLocation.getBearing();
    }

    private void initLocationUpdates_4_GooglePlayServices() {
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (null == location) {
                        continue;
                    }
                    String info = parseLocation(location);
                    Log.d(TAG, "onLocationResult: " + info);
                }
            }
        };

        mLocationRequest = new LocationRequest();
        // setInterval(),setFastestInterval(),setPriority(), 必须设置，否则只有一次数据
        mLocationRequest.setInterval(1000);          // 应用接收位置更新的速率（毫秒） => 1s.
        mLocationRequest.setFastestInterval(5000);   // 应用处理位置更新的最快速率（毫秒）=> 5s
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // 设置请求的优先级
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates_4_GooglePlayServices() {
        /**
         * Samsung SM-G9730: Not Produced
         * Android emulator：send mock data，Produced
         */
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.getMainLooper());
    }

    private void stopLocationUpdates_4_GooglePlayServices() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }
}