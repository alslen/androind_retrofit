package com.example.app_member;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MemberService {
    @POST("insert")
    Call<Member> save(@Body Member member);

    @GET("list")
    Call<List<Member>> findAll();

    @PUT("update/{name}")
    Call<Member> update(@Path("name")String name, @Body Member member);

    @DELETE("delete/{name}")
    Call<Void> delete(@Path("name")String name);
}
