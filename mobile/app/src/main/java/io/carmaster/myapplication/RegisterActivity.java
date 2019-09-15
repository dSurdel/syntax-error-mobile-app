package io.carmaster.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import io.carmaster.myapplication.services.ICSApiService;
import io.carmaster.myapplication.services.RetrofitClientInstance;
import io.carmaster.myapplication.services.retrofitModels.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // loadingProgressBar.setVisibility(View.VISIBLE);
                // loginViewModel.login(usernameEditText.getText().toString(),
                //         passwordEditText.getText().toString());
                // Now we triggering login action
                RetrofitClientInstance retrofitClient = new RetrofitClientInstance();
                ICSApiService service = retrofitClient.getRetrofitInstance().create(ICSApiService.class);

                final EditText emailEditText = findViewById(R.id.email);
                final EditText passwordEditText = findViewById(R.id.password);

                User user = new User();
                user.setEmail(emailEditText.getText().toString());
                user.setPassword(passwordEditText.getText().toString());
                Call<User> registerCall = service.registerUser(user);
                registerCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );
                        Toast.makeText(
                                getApplicationContext(),
                                "Item "+response.body(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Error ", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

}
