package io.carmaster.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import io.carmaster.myapplication.services.ICSApiService;
import io.carmaster.myapplication.services.RetrofitClientInstance;
import io.carmaster.myapplication.services.retrofitModels.User;
import io.carmaster.myapplication.ui.login.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        final Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // loadingProgressBar.setVisibility(View.VISIBLE);
                // loginViewModel.login(usernameEditText.getText().toString(),
                //         passwordEditText.getText().toString());
                // Now we triggering login action
                RetrofitClientInstance retrofitClient = new RetrofitClientInstance();
                ICSApiService service = retrofitClient.getRetrofitInstance().create(ICSApiService.class);

                SharedPreferences sharedPref = MenuActivity.this.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("isLogged", false);
                editor.commit();

                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        final Button addInitiativeButton = findViewById(R.id.button_add_initiative);
        addInitiativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, AddCitizensInitiativeActivity.class);
                startActivity(intent);
            }
        });

        final Button voteForInitiatovesButton = findViewById(R.id.vote_for_initiatives);
        voteForInitiatovesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SweepActivity.class);
                startActivity(intent);
            }
        });
    }
}
