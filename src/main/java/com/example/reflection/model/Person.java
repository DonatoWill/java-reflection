package com.example.reflection.model;

import com.example.reflection.annotation.Column;
import com.example.reflection.annotation.PrimaryKey;

public class Person {

    @PrimaryKey
    private long id;
    @Column
    private String name;
    @Column
    private int age;

    public Person(String name, int age){
        this.name =  name;
        this.age = age;
    }

    public static Person of(String name, int age){
        return new Person(name, age);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return "Person{ id=" + id +", name='" + name +", age=" + age +'}';
    }

    public void setAge(int age) {
        this.age = age;
    }
}
