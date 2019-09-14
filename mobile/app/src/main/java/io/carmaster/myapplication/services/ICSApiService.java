package io.carmaster.myapplication.services;
import java.util.List;

import io.carmaster.myapplication.services.retrofitModels.Notification;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface  ICSApiService {
    //@POST("Accounting")
    //Call<List<User>> addUser(@Path("user") User user);

    @GET("Notification/")
    Call<Notification> getNotification(@Query("notificationId") int notificationId);

    @POST("Notification/")
    Call<Notification> addNotification(@Body String description,
                                       @Body int notificationTypeId,
                                       @Body String submittedByUserId,
                                       @Body int latitude,
                                       @Body int longitude,
                                       @Body String city,
                                       @Body String province,
                                       @Body String region);
    @POST("Notification/all")
    Call<List<Notification>> getNotifications(@Query("description") String description);
}