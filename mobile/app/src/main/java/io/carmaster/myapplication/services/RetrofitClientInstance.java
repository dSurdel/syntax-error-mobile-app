package io.carmaster.myapplication.services;


import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.Path;


public class RetrofitClientInstance {
    public interface  ICSApiService {
       // @POST("Accounting")
       // Call<List<Repo>> addUser(@Path("user") String user);
    }

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://51.38.132.82:8000";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ICSApiService service = retrofit.create(ICSApiService.class);
        }
        return retrofit;
    }
}
