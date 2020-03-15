package com.hyeung.sendapp;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitService {
    @GET("addNum")
    Call<RetrofitRepo> addNum(
            @Query("addNum") Integer addNum
    );
    @GET("delNum")
    Call<RetrofitRepo> delNum(
            @Query("delNum") Integer delNum
    );
    @GET("getNumList")
    Call<RetrofitRepo> getNumList(

    );
}