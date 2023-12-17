package com.hades.example.android.libexamples.core;

import org.junit.Assert;
import org.junit.Test;

public class JsonUtils2Test {

    @Test
    public void toJson() {
        JsonUtils2 jsonUtils2 = new JsonUtils2();
        Assert.assertEquals("3", jsonUtils2.toJson(3));
    }

    @Test
    public void fromJson() {
        JsonUtils2 jsonUtils2 = new JsonUtils2();
        Assert.assertEquals(Integer.valueOf(3), jsonUtils2.fromJson("3", Integer.class));
    }
}