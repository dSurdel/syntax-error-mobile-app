package io.carmaster.myapplication.services;
import java.util.List;

import io.carmaster.myapplication.services.retrofitModels.Initiative;
import io.carmaster.myapplication.services.retrofitModels.Notification;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface  ICSApiService {
    //@POST("Accounting")
    //Call<List<User>> addUser(@Path("user") User user);

    @GET("Notification/")
    Call<Notification> getNotification(@Query("notificationId") int notificationId);

    @POST("Notification/")
    Call<Notification> addNotification(@Body Notification notification);

    @GET("Notification/all")
    Call<List<Notification>> getNotifications();

    @POST("Initiative/{initiativeId}/votePositive")
    Call<Initiative> votePositive(@Path("initiativeId") int initiativeId);

    @POST("Initiative/{initiativeId}/voteNegative")
    Call<Initiative> voteNegative(@Path("initiativeId") int initiativeId);

    @POST("Initiative/{initiativeId}/voteNeutral")
    Call<Initiative> voteNeutral(@Path("initiativeId") int initiativeId);
}