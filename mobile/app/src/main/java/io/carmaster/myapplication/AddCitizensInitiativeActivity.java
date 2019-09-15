package io.carmaster.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.carmaster.myapplication.services.ICSApiService;
import io.carmaster.myapplication.services.RetrofitClientInstance;

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
                service.addNotification(
                        citizenInitiativeDescriptionTextArea.getText().toString(),
                        0,
                        "sdfsd",
                        0,
                        0,
                        "Debica",
                        "Podkarpackie",
                        "Polnocny"
                );
            }
        });


    }
}
