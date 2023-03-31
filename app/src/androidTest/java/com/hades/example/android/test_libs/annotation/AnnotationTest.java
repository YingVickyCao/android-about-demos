package com.hades.example.android.test_libs.annotation;

import org.junit.Test;

import java.lang.annotation.Annotation;

public class AnnotationTest {

    @Test
    public void test_class() {
        Annotation[] annotations = IStu.class.getAnnotations();
        System.out.println(annotations);

    }
}