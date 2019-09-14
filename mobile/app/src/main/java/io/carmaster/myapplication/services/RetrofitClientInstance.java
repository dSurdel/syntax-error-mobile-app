package io.carmaster.myapplication.services;


import java.util.List;

import io.carmaster.myapplication.services.retrofitModels.Notification;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public class RetrofitClientInstance {
    public interface  ICSApiService {
        //@POST("Accounting")
        //Call<List<User>> addUser(@Path("user") User user);

        @GET("Notification/")
        Call<List<Notification>> getNotification(@Query("notificationId") int notificationId);

        //@POST("Notification/")
        //Call<List<Notification>> addNotification(@Query("description") String description, );
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
