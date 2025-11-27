package com.hades.example.android.app_component.ipc.common.content_provider;

public interface DicListener {
    void onUpdate(int position, String word, long key);

    void onDelete(int position, long key);
}
