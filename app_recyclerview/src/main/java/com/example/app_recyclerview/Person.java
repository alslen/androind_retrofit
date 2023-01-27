package com.example.app_recyclerview;

public class Person {
    String name;
    String phone;

    // 생성자
    public Person(String name, String phone){
        this.name = name;
        this.phone = phone;
    }

    // Getter, Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
