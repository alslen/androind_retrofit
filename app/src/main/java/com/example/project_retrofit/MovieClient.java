package com.example.project_retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieClient {  // 접속할 서버 주소를 선언한 클래스
    private static Retrofit zretrofit = null;

    static Retrofit getClient() {
        // 접속할 URL
        zretrofit = new Retrofit.Builder().baseUrl("http://mellowcode.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return zretrofit;
    }
}
