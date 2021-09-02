package com.hades.example.android.network;

public interface RequestCallback<T, K> {
    void onSuccess(T response);

    void onError(K msg);
}
