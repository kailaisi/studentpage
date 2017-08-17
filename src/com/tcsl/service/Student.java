package com.tcsl.service;

import java.util.Map;

public class Student {
    private String name;
    private int  age;
    private int sex;

    public Student(Map<String, Object> map) {
        this.name = (String) map.get("name");
        this.age = (int) map.get("age");
        this.sex = (int) map.get("sex");
    }
    public Student(String name, int sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getSex(){
        return sex;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
