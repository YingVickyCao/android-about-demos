package com.hades.example.android._case.unit_test;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Mock RxJava unit test
 */
public interface StuService {
    @GET("/getStuInfo")
    Observable<Stu> getStuInfo(@Query("ts") long ts);
}