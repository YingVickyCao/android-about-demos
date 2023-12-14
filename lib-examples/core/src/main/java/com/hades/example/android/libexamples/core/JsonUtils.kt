package com.hades.example.android.libexamples.core

import com.google.gson.GsonBuilder


class JsonUtils {
    private var gson = GsonBuilder().enableComplexMapKeySerialization().create()

    fun toJson(src: Any):String {
        return gson.toJson(src)
    }

    fun <T> fromJson(value: String, classOfT: Class<T>): T {
        return gson.fromJson(value, classOfT)
    }
}