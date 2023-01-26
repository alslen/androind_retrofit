package com.example.myapplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit2Client {
    private static Retrofit2Client instance;
    private PhoneService phoneService;

    public Retrofit2Client() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.100.102.22:8899/")  // 내가 실행하고 있는 스프링의 서버
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        phoneService = retrofit.create(PhoneService.class);  // PhoneService객체를 통해서 Retrofit을 생성할 것임.
    }
    public static Retrofit2Client getInstance(){
        if(instance == null) {
            instance = new Retrofit2Client();
        }
        return instance;
    }

    public PhoneService getPhoneService() {
        return phoneService;
    }
}
