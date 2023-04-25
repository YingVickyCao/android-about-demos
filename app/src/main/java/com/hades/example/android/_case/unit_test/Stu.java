package com.hades.example.android._case.unit_test;

public class Stu {
    int num;
    String name;

    public Stu(int num, String name) {
        this.num = num;
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Stu{" +
                "num=" + num +
                ", name='" + name + '\'' +
                '}';
    }
}
