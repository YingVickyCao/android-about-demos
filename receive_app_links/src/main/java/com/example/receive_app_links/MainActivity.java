package com.example.receive_app_links;

import android.content.Context;
import android.content.pm.verify.domain.DomainVerificationManager;
import android.content.pm.verify.domain.DomainVerificationUserState;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            try {
                Context context = MainActivity.this;
                DomainVerificationManager manager = context.getSystemService(DomainVerificationManager.class);
                DomainVerificationUserState userState =
                        manager.getDomainVerificationUserState(context.getPackageName());

                Map<String, Integer> hostToStateMap = userState.getHostToStateMap();
                List<String> verifiedDomains = new ArrayList<>();
                List<String> selectedDomains = new ArrayList<>();
                List<String> unapprovedDomains = new ArrayList<>();
                for (String key : hostToStateMap.keySet()) {
                    Integer stateValue = hostToStateMap.get(key);
                    if (stateValue == DomainVerificationUserState.DOMAIN_STATE_VERIFIED) {
                        // Domain has passed Android App Links verification.
                        verifiedDomains.add(key);
                    } else if (stateValue == DomainVerificationUserState.DOMAIN_STATE_SELECTED) {
                        // Domain hasn't passed Android App Links verification, but the user has
                        // associated it with an app.
                        selectedDomains.add(key);
                    } else {
                        // All other domains.
                        unapprovedDomains.add(key);
                    }
                }
            } catch (Exception ex) {
                Log.d(TAG, "onCreate: " + ex.getMessage());
            }

        }
    }
}