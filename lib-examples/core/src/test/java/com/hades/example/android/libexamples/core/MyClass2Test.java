package com.hades.example.android.libexamples.core;

import org.junit.Assert;
import org.junit.Test;

public class MyClass2Test {

    @Test
    public void getData() {
        MyClass2 myClass2 = new MyClass2();
        Assert.assertEquals(3, myClass2.getData());
    }
}