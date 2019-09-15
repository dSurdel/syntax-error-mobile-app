package io.carmaster.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import io.carmaster.myapplication.services.ICSApiService;
import io.carmaster.myapplication.services.RetrofitClientInstance;
import io.carmaster.myapplication.services.retrofitModels.Notification;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCitizensInitiativeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_citizens_initiative);

        RetrofitClientInstance retrofitClient = new RetrofitClientInstance();
        ICSApiService service = retrofitClient.getRetrofitInstance().create(ICSApiService.class);


        final Button button = (Button) findViewById(R.id.triggerInitiativeAddButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                TextView citizenInitiativeDescriptionTextArea = (TextView)findViewById(R.id.initiative_description);
                Notification notification = new Notification();
                notification.setDescription(citizenInitiativeDescriptionTextArea.getText().toString());

              /*  Toast.makeText(
                        getApplicationContext(),
                        "Called api", Toast.LENGTH_LONG).show();*/

                Call<Notification> notificationCall = service.getNotification(0);

                Call<List<Notification>> notificationsCall = service.getNotifications();




            /*    Toast.makeText(
                        getApplicationContext(),
                        "Called api 2", Toast.LENGTH_LONG).show();*/

                notificationsCall.enqueue(new Callback<List<Notification>>() {
                    @Override
                    public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {

                        Toast.makeText(
                                getApplicationContext(),
                                "Item "+response.body(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<List<Notification>> call, Throwable t) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Error ", Toast.LENGTH_LONG).show();
                    }
                });

                notificationCall.enqueue(new Callback<Notification>() {
                    @Override
                    public void onResponse(Call<Notification> call, Response<Notification> response) {

                        Toast.makeText(
                                getApplicationContext(),
                                "Item "+response.body(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Notification> call, Throwable t) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Error ", Toast.LENGTH_LONG).show();
                    }
                });




                Call<Notification> addCall = service.addNotification(
                      notification
                );
                addCall.enqueue(new Callback<Notification>() {
                    @Override
                    public void onResponse(Call<Notification> call, Response<Notification> response) {

                        Toast.makeText(
                                getApplicationContext(),
                                "Item "+response.body(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Notification> call, Throwable t) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Error ", Toast.LENGTH_LONG).show();
                    }
                });


            }
        });


    }
}


