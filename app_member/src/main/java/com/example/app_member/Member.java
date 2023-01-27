package com.example.app_member;

public class Member {
    String name;
    Long age;
    String phone;
    String addr;
    String email;

    public Member() {

    }
    public Member(String name, Long age, String phone, String addr, String email) {
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.addr = addr;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
