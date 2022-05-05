package com.example.myapplication;

import com.example.myapplication.api.RecepiesInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String base_url = "https://api.edamam.com/";//base url
    private static RetrofitClient instance;
    private Retrofit retrofit; //retrofit object

    private RetrofitClient() { //constructor
        retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;

    }

    public RecepiesInterface getApi() { //defining api function
        return retrofit.create(RecepiesInterface.class);
    }
}

