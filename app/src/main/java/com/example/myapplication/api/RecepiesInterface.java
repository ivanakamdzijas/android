package com.example.myapplication.api;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface RecepiesInterface {


    @GET("/search")
    Call<HitClass> getRecepies(@Query("q") String q, @Query("app_id") String app_id, @Query("app_key") String app_key);
}
