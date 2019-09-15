package io.carmaster.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class CustomMarkerDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no, not_sure;

    public CustomMarkerDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                // Confirmed report

                c.finish();
                break;
            case R.id.btn_no:
                // Reported at fake news
                dismiss();
                break;
            case R.id.btn_notsure:
                // Reported at not sure
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}