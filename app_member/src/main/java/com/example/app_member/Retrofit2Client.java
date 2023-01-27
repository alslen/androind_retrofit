package com.example.app_member;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit2Client {
    private static Retrofit2Client instance;
    private MemberService memberService;

    public Retrofit2Client(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.100.102.22:8855/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        memberService = retrofit.create(MemberService.class);
    }
    public static Retrofit2Client getInstance(){
        if(instance == null) instance = new Retrofit2Client();

        return instance;
    }
    public MemberService getMemberService(){
        return memberService;
    }
}
