package io.carmaster.myapplication.services;


import java.util.List;

import io.carmaster.myapplication.services.retrofitModels.Notification;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class RetrofitClientInstance {


    private static Retrofit retrofit;
    private static final String BASE_URL = "http://51.38.132.82:8000";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }

    public static RetrofitClientInstance getRetrofitClient() {
       int a=1;
        return null;
    }
}
