package com.hades.example.android.libexamples.core

import org.junit.Assert
import org.junit.Test

class MyClassTest {
    @Test
    fun getData() {
        val myClass = MyClass()
        Assert.assertEquals(3, myClass.getData())
    }
}
