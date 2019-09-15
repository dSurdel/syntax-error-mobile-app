package io.carmaster.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import io.carmaster.myapplication.services.ICSApiService;
import io.carmaster.myapplication.services.RetrofitClientInstance;
import io.carmaster.myapplication.services.retrofitModels.Initiative;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomMarkerDialog extends Dialog implements android.view.View.OnClickListener {

    private final io.carmaster.myapplication.services.ICSApiService ICSApiService;
    private final String itemId;
    private final String initiativeName;
    private final String initiativeDescription;
    public Activity c;
    public Dialog d;
    public Button yes, no, not_sure;
    

    public CustomMarkerDialog(Activity a, String itemId, String initiativeName, String initiativeDescription) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.itemId = itemId;
        RetrofitClientInstance retrofitClient = new RetrofitClientInstance();
        ICSApiService service = retrofitClient.getRetrofitInstance().create(ICSApiService.class);
        
        this.ICSApiService = service;
        this.initiativeName = initiativeName;
        this.initiativeDescription = initiativeDescription;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_marker_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        not_sure = (Button) findViewById(R.id.btn_notsure);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        not_sure.setOnClickListener(this);

        TextView initiativeMarkerItemName = (TextView)findViewById(R.id.txt_dia);
        TextView initiativeMarkerItemDescription = (TextView)findViewById(R.id.txt_dia_opis);
        initiativeMarkerItemName.setText(this.initiativeName);
        initiativeMarkerItemDescription.setText(this.initiativeDescription);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                // Confirmed report
                Call<Initiative>  voteCall = this.ICSApiService.votePositive(this.itemId);
                voteCall.enqueue(new Callback<Initiative>() {
                    @Override
                    public void onResponse(Call<Initiative> call, Response<Initiative> response) {
                        Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );

                    }

                    @Override
                    public void onFailure(Call<Initiative> call, Throwable t) {

                    }
                });
                //c.finish();
                break;
            case R.id.btn_no:
                // Reported at fake news
                Call<Initiative>  voteCallNegative = this.ICSApiService.voteNegative(this.itemId);
                voteCallNegative.enqueue(new Callback<Initiative>() {
                    @Override
                    public void onResponse(Call<Initiative> call, Response<Initiative> response) {
                        Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );

                    }

                    @Override
                    public void onFailure(Call<Initiative> call, Throwable t) {

                    }
                });
               // dismiss();
                break;
            case R.id.btn_notsure:
                // Reported at not sure
                Call<Initiative>  voteCallNeutral = this.ICSApiService.voteNeutral(this.itemId);
                voteCallNeutral.enqueue(new Callback<Initiative>() {
                    @Override
                    public void onResponse(Call<Initiative> call, Response<Initiative> response) {
                        Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );

                    }

                    @Override
                    public void onFailure(Call<Initiative> call, Throwable t) {

                    }
                });
               // dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}