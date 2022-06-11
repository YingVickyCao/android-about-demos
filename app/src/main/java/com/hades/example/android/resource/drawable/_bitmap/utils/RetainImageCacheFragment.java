package com.hades.example.android.resource.drawable._bitmap.utils;

import android.os.Bundle;

import com.hades.example.android.base.BaseFragment;


public class RetainImageCacheFragment extends BaseFragment {
    public static final String TAG = RetainImageCacheFragment.class.getSimpleName();

    private Object mStoredObject;

    public RetainImageCacheFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retainedFragmentOverConfigurationChange();
    }

    private void retainedFragmentOverConfigurationChange() {
        setRetainInstance(true);
    }

    public void setObject(Object object) {
        mStoredObject = object;
    }

    public Object getObject() {
        return mStoredObject;
    }
}
