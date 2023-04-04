package com.hades.example.android.test_libs.annotation;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class AnnotationTest {

    @Test
    public void test_annotation_of_interface() {
        Annotation[] list = IStu.class.getAnnotations();

        for (Annotation  item: list){
            System.out.println(item.toString());
        }
    }
}