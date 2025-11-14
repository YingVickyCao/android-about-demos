package com.hades.example.android.app_component.content_provider.dict.common;

public interface DicListener {
    void onUpdate(int position, String word, long key);

    void onDelete(int position, long key);
}
