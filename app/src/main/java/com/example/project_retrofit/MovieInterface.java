package com.example.project_retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieInterface {  // CRUD작업을 해주는 interface
    @GET("json/students/")
    Call<List<Movie>> doGetMovie();
}
