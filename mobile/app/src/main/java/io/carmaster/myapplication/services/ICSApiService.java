package io.carmaster.myapplication.services;
import java.util.List;

import io.carmaster.myapplication.services.retrofitModels.Initiative;
import io.carmaster.myapplication.services.retrofitModels.Notification;
import io.carmaster.myapplication.services.retrofitModels.User;
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
    Call<Notification> getNotification(@Query("notificationId") String notificationId);

    @POST("Notification")
    Call<Notification> addNotification(@Body Notification notification);

    @GET("Notification/all")
    Call<List<Notification>> getNotifications();

    @POST("SocialInitiatives/votePositive")
    Call<Initiative> votePositive(@Query("socialInitiativesId") String initiativeId);

    @POST("SocialInitiatives/voteNegative")
    Call<Initiative> voteNegative(@Query("socialInitiativesId") String initiativeId);

    @POST("SocialInitiatives/voteNeutral")
    Call<Initiative> voteNeutral(@Query("socialInitiativesId") String initiativeId);

    @POST("SocialInitiatives/")
    Call<Initiative> addSocialInitiative(@Body Initiative initiative);

    @GET("SocialInitiatives/")
    Call<Initiative> getSocialInitiative(@Body Initiative initiative);

    @GET("SocialInitiatives/all")
    Call<List<Initiative>> getSocialInitiatives();

    @GET("SocialInitiatives/closestSocialInitiatives")
    Call<List<Initiative>> getClosestSocialInitiatives();

    @POST("Accounting/register")
    Call<User> registerUser(@Body User user);

    @POST("Accounting/login")
    Call<User> loginUser(@Body User user);
}