package com.hades.example.android;

import org.junit.Test;

public class InstanceOfCheck {
    @Test
    public void isNeedNUll() {
        Integer integer = null;

        if (integer instanceof Integer) {
            System.out.printf("not need null");
        }
    }
}
