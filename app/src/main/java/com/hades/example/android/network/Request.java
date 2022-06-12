package com.hades.example.android.network;

public class Request {
    public void test() {

        doRequest(new RequestCallback<Stu, String>() {
            @Override
            public void onSuccess(Stu response) {

            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    public void doRequest(RequestCallback<Stu, String> requestCallback) {
        requestCallback.onSuccess(new Stu());
        requestCallback.onError("error");
    }
}
