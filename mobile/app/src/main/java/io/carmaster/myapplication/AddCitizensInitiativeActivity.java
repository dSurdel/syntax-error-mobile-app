package io.carmaster.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import io.carmaster.myapplication.services.ICSApiService;
import io.carmaster.myapplication.services.RetrofitClientInstance;
import io.carmaster.myapplication.services.retrofitModels.Initiative;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCitizensInitiativeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_citizens_initiative);


        final Button button = (Button) findViewById(R.id.logoutButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                EditText citizenInitiativeDescriptionTextArea = (EditText)findViewById(R.id.initiative_description);
                EditText citizenInitiativeNameTextEdit = (EditText)findViewById(R.id.initiative_name);
                //Notification notification = new Notification();
                //notification.setDescription(citizenInitiativeDescriptionTextArea.getText().toString());

                Initiative initiative = new Initiative();
                initiative.setDescription(citizenInitiativeDescriptionTextArea.getText().toString());
                initiative.setName(citizenInitiativeNameTextEdit.getText().toString());

              /*  Toast.makeText(
                        getApplicationContext(),
                        "Called api", Toast.LENGTH_LONG).show();*/

               // Call<Notification> notificationCall = service.getNotification("29149013-63a4-47b7-8b97-471a9d9d817a");






            /*    Toast.makeText(
                        getApplicationContext(),
                        "Called api 2", Toast.LENGTH_LONG).show();*/


/*
                notificationCall.enqueue(new Callback<Notification>() {
                    @Override
                    public void onResponse(Call<Notification> call, Response<Notification> response) {
                      //  JsonObject post = new JsonObject().get().getAsJsonObject();
                        Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );
                        String gson1 = new Gson().toJson(response.body());
                        try {
                            JSONObject obj = new JSONObject(gson1);
                            String pageName = obj.getString("City");
                            Log.e("TAG", "response 33: "+pageName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                      /*  Toast.makeText(
                                getApplicationContext(),
                                "Item "+response.body().string(), Toast.LENGTH_LONG).show();*/
                  /*  }

                    @Override
                    public void onFailure(Call<Notification> call, Throwable t) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Error ", Toast.LENGTH_LONG).show();
                    }
                });


*/
                RetrofitClientInstance retrofitClient = new RetrofitClientInstance();
                ICSApiService service = retrofitClient.getRetrofitInstance().create(ICSApiService.class);

                Call<Initiative> addInitiativeCall = service.addSocialInitiative(
                      initiative
                );
                addInitiativeCall.enqueue(new Callback<Initiative>() {
                    @Override
                    public void onResponse(Call<Initiative> call, Response<Initiative> response) {
                        Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );
                        Toast.makeText(
                                getApplicationContext(),
                                "Item "+response.body(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Initiative> call, Throwable t) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Error ", Toast.LENGTH_LONG).show();
                    }
                });



            }
        });


    }
}


