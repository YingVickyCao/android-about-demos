package com.hades.example.android._case.unit_test;

import com.google.gson.Gson;

import org.junit.Assert;
import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Mock RxJava unit test
 */
public class StuServiceTest {
    StuService dummyService() {
        return new StuService() {
            @Override
            public Observable<Stu> getStuInfo(long ts) {
//                System.out.println("thread getStuInfo:" + Thread.currentThread().getId() + "," + Thread.currentThread().getName());
                String stu = "{\"num\":10,\"name\":\"Li Ming\"}";
                Gson gson = new Gson();
                return Observable.just(gson.fromJson(stu, Stu.class));
            }
        };
    }

    @Test
    public void test_getStuInfo_way1() {
        // test and Observable Consumer is running on same thread "Test worker"
        // 顺序执行：getStuInfo -> subscribe
        System.out.println("thread test_getStuInfo:" + Thread.currentThread().getId() + "," + Thread.currentThread().getName());
        StuService stuService = dummyService();
        Disposable disposable = stuService.getStuInfo(System.currentTimeMillis())
                .subscribe(new Consumer<Stu>() {
                    @Override
                    public void accept(Stu stu) throws Exception {
                        Assert.assertEquals(10, stu.num);
                        Assert.assertNotEquals(5, stu.num);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Assert.assertNotNull(throwable);
                    }
                });
        disposable.dispose();
    }

    @Test
    public void test_getStuInfo_way2() {
        // test and Observable Consumer is running on same thread "Test worker"
        // 顺序执行：getStuInfo -> subscribe -> assertXXX
        Stu[] list = new Stu[1];
        StuService stuService = dummyService();
        Disposable disposable = stuService.getStuInfo(System.currentTimeMillis())
                .subscribe(stu -> list[0] = stu, throwable -> Assert.assertNotNull(throwable));
        Assert.assertEquals(10, list[0].num);
        Assert.assertNotEquals(5, list[0].num);
        disposable.dispose();
    }
}