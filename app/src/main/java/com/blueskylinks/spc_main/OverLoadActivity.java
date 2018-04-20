package com.blueskylinks.spc_main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

public class OverLoadActivity extends AppCompatActivity {
    RadioButton rbutton1;
    RadioButton rbutyon2;
    String phoneNumber="9663261329";
    ProgressBar pbar;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    EditText et1;
    EditText et2;
    EditText et3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over_load);
        rbutton1 = findViewById(R.id.radioButton);
        rbutyon2 = findViewById(R.id.radioButton2);
        pbar = findViewById(R.id.onoff_pgbar1);
        textView1 = findViewById(R.id.onoff_status_text1);
        et1 = findViewById(R.id.et1);
        textView2 = findViewById(R.id.onoff_status_text2);
        et2 = findViewById(R.id.et11);
        et3 = findViewById(R.id.et12);
        textView3 = findViewById(R.id.onoff_status_text3);
    }

    public void turnsOn(View view){
        String message = "";
        if(rbutton1.isChecked()){
            message = "SPC,2,1";
            SmsManager smsManager = SmsManager.getDefault();
            // smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.i("message",message);
            Log.i("Test", "SMS sent!");
            //  pbar.setVisibility(View.VISIBLE);
            textView1.setText("Command Sent, Please Wait...For 2 minutes");
        }
        else if(rbutyon2.isChecked()){
            message = "SPC,2,0";
            SmsManager smsManager = SmsManager.getDefault();
            // smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.i("message",message);
            Log.i("Test", "SMS sent!");
            //  pbar.setVisibility(View.VISIBLE);
            textView1.setText("Command Sent, Please Wait...For 2 minutes");
        }
        else return;

    }

    public void set_tripTime(View view){
        Log.i("..","trip time event");
        String text=et1.getText().toString();
        if(TextUtils.isEmpty(text)) et1.setError("please enter trip time!..");
        else {
            Log.i("..", text);
            String message = "SPC,3," + text;
            SmsManager smsManager = SmsManager.getDefault();
            // smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.i("message", message);
            Log.i("Test", "SMS sent!");
            textView2.setText("Command Sent, Please Wait...For 2 minutes");
        }
    }

    public void set_tripCurrent(View view){

        String text=et2.getText().toString();
        Log.i("..",text);

        String text2=et3.getText().toString();
        Log.i("..",text2);
        String message=" ";
        if(TextUtils.isEmpty(text)|| TextUtils.isEmpty(text2)){
            if(TextUtils.isEmpty(text)) et2.setError("please provide 3phase trip current!..");
            else et3.setError("please provide 2phase trip current!..");
        }
        message="SPC,5" + ","+text+","+text2;
        SmsManager smsManager = SmsManager.getDefault();
        // smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Log.i("message",message);
        Log.i("Test", "SMS sent!");
        textView3.setText("Command Sent, Please Wait...For 2 minutes");
    }

    public void settings(View view){
        //starting another activity..
        Intent it1 = new Intent(OverLoadActivity.this, SettingsActivity.class);
        startActivity(it1);
    }

    public void ON_OFF(View view){
        //starting another activity..
        Intent it2 = new Intent(OverLoadActivity.this, ON_OFFActivity.class);
        startActivity(it2);
    }

    public void Users(View view){
        //starting another activity..
        Intent it3 = new Intent(OverLoadActivity.this, UsersActivity.class);
        startActivity(it3);
    }

    public void manual(View view){
        //starting another activity..
        Intent it4 = new Intent(OverLoadActivity.this, ManualActivity.class);
        startActivity(it4);
    }

    public void home(View view){
        Intent it5 = new Intent(OverLoadActivity.this, Main2Activity.class);
        startActivity(it5);
    }
}
