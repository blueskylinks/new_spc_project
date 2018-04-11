package com.blueskylinks.spc_main;

import android.Manifest;
import android.app.ProgressDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
 public static  TextView tv;
    public static  TextView tv1;
    public static  TextView tv2;
    public static  TextView tv3;
    public static  TextView tv4;
    public static  TextView tv5;
    public static  TextView textView6;
    public static ProgressDialog progressDialog;
    String phoneNumber = "9663261329";
    public static TextView b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS}, 200);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_SMS}, 200);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECEIVE_SMS}, 200);
        tv = findViewById(R.id.textView11);
        tv1 = findViewById(R.id.textView12);
        tv2 = findViewById(R.id.textView13);
        tv3 = findViewById(R.id.textView17);
        tv4 = findViewById(R.id.textView18);
        String s = tv.getText().toString();

        //tv5=findViewById(R.id.tv5);
        //  textView6=findViewById(R.id.tv6);

/*        String phoneNumber = "9764005401";
        String message = "SPC,25";
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Log.i("Test", "SMS sent!");*/
        progress();
        b = findViewById(R.id.textView314);
    }

    public void refresh(View view){
        String s=tv.getText().toString();
        Log.i("test",s);

        if(s.matches(" "))
        {

            String message = "SPC,25";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.i("Test", "SMS sent!");
            progress();
        }
        else return;
    }

    //Progress Dialog
    public void progress(){
        progressDialog = new ProgressDialog(Main2Activity.this);
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

    public void power(View view){
        tv=findViewById(R.id.textView11);
        String s=tv.getText().toString();
        TextView b=findViewById(R.id.textView314);
        Log.i("Test",s);
        String s1=b.getText().toString();
        if(s1.equals("turn OFF")){
            String message = "SPC,26";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.i("Test", "SMS sent!");}

        else if(s1.equals("turn ON")){
            String message = "SPC,24";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.i("Test", "SMS sent!");}


    }
}
