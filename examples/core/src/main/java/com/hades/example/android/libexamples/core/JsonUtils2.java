package com.hades.example.android.libexamples.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * This is json util written by java
 */
public class JsonUtils2 {
    private Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();

    /**
     * generate a json string
     *
     * @param src target class
     * @return the json string
     */
    public String toJson(Object src) {
        return gson.toJson(src);
    }

    /**
     * Generate the object of given class from a json string
     *
     * @param value    the json string
     * @param classOfT class type
     * @return the object of this class
     */
    public <T> T fromJson(String value, Class<T> classOfT) {
        return gson.fromJson(value, classOfT);
    }
}
