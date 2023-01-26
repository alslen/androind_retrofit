package com.example.myapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PhoneService {
    @GET("list")  // 전체 select
    Call<List<Phone>> findAll();

    @POST("insert")
    Call<Phone> save(@Body Phone phoneDto);  // insert한 값을 받아오기 위해 Phone으로 정의함.

    @PUT("update/{id}")
    Call<Phone> update(@Path("id")Long id, @Body Phone phoneDto);

    @DELETE("delete/{id}")
    Call<Void> deleteById(@Path("id")Long id);
}
