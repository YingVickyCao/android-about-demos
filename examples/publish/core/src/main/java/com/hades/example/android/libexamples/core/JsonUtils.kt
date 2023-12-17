package com.hades.example.android.libexamples.core

import com.google.gson.GsonBuilder

/**
 * This is json util written by kotlin
 */
class JsonUtils {
    private var gson = GsonBuilder().enableComplexMapKeySerialization().create()

    /**
     * generate a json string
     * @param src target class
     * @return the json string
     */
    fun toJson(src: Any): String {
        return gson.toJson(src)
    }

    /**
     * Generate the object of given class from a json string
     * @param value the json string
     * @param classOfT class type
     * @return the object of this class
     */
    fun <T> fromJson(value: String, classOfT: Class<T>): T {
        return gson.fromJson(value, classOfT)
    }
}