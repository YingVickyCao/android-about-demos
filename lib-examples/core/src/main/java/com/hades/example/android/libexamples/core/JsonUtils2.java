package com.hades.example.android.libexamples.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils2 {
    private Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();

    public String toJson(Object src) {
        return gson.toJson(src);
    }

    public <T> T fromJson(String value, Class<T> classOfT) {
        return gson.fromJson(value, classOfT);
    }
}
