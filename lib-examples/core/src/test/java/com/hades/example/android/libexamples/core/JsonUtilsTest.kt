package com.hades.example.android.libexamples.core

import org.junit.Assert
import org.junit.Test

class JsonUtilsTest {

    @Test
    fun toJson() {
        Assert.assertEquals("3", JsonUtils().toJson(3))
    }

    @Test
    fun fromJson() {
        Assert.assertEquals(3, JsonUtils().fromJson("3", Int::class.java))
    }
}