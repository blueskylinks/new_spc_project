package com.blueskylinks.spc_main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.blueskylinks.spc_main.Main2Activity.progressDialog;
import static com.blueskylinks.spc_main.Main2Activity.tv;

public class ON_OFFActivity extends AppCompatActivity {
    String phoneNumber = "9663261329";
    public static TextView textView1;
    public static TextView textView2;
    String phoneNumber1;
    String SMSBody1;

    public void getSmsDetails(String phoneNumber, String SMSBody) {
        phoneNumber1 = phoneNumber;
        SMSBody1 = SMSBody;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on__off);
        textView1=findViewById(R.id.textView7);
        textView2=findViewById(R.id.textView30);
    }

    public void turnsOn(View view){
        String message = "SPC,24";
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Log.i("Test", "SMS sent!");
        progress();
    }

    //Progress Dialog
    public void progress(){
        progressDialog = new ProgressDialog(ON_OFFActivity.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("ProgressDialog"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(15000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();
    }

    public void turnsOff(View view){

        String message = "SPC,26";
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Log.i("Test", "SMS sent!");
        progress();
    }

    public void settings(View view){
        //starting another activity..
        Intent it1 = new Intent(ON_OFFActivity.this, SettingsActivity.class);
        startActivity(it1);


    }

    //Hoome functions
    public void home(View view){
        Intent it1 = new Intent(ON_OFFActivity.this, Main2Activity.class);
        startActivity(it1);
    }

}
