package com.hades.example.android.app_component._fragment;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

import com.hades.example.android.R;

public class Prefs2Fragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.display_prefs, rootKey);
    }
}
