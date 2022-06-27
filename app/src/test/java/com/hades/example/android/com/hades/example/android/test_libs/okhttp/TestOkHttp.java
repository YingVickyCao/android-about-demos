package com.hades.example.android.com.hades.example.android.test_libs.okhttp;

import org.junit.Test;

public class TestOkHttp {


    @Test
    public void test_sync_get(){

    }

    @Test
    public void test_async_get(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        thread.start();

        try {
            thread.join();
        }catch (InterruptedException exception){
            System.out.println(exception.getMessage());
        }
    }
}
