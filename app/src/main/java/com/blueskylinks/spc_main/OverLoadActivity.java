package com.blueskylinks.spc_main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class OverLoadActivity extends AppCompatActivity {
    RadioButton rbutton1;
    RadioButton rbutyon2;
    String phoneNumber ;
    ProgressBar pbar;
    ProgressBar pbar1;
    ProgressBar pbar2;
    ProgressBar pbar3;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView tv;
    TextView tv1;
    TextView tv2;
    EditText et1;
    EditText et2;
    String SMSBody1;
    EditText et3;
    EditText et4;
    SharedPreferences sp1;
    DatabaseHelper myDb;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new DatabaseHelper(this);
        setContentView(R.layout.activity_over_load);
        rbutton1 = findViewById(R.id.radioButton);
        submit = findViewById(R.id.button3);
        rbutyon2 = findViewById(R.id.radioButton2);
        pbar = findViewById(R.id.onoff_pgbar1);
        textView1 = findViewById(R.id.onoff_status_text1);
        et1 = findViewById(R.id.et1);
        textView2 = findViewById(R.id.onoff_status_text2);
        et2 = findViewById(R.id.et11);
        et3 = findViewById(R.id.et12);
        et4 = findViewById(R.id.et_over_per);
        textView3 = findViewById(R.id.onoff_status_text3);
        textView4 = findViewById(R.id.onoff_status_text4);
        tv=findViewById(R.id.mot_st);
        pbar1=findViewById(R.id.set1);
        pbar2=findViewById(R.id.set2);
        pbar3=findViewById(R.id.set3);
        tv1=findViewById(R.id.time);
        tv2=findViewById(R.id.current);

        //get Destination address
        sp1=getSharedPreferences("login",0);
        phoneNumber=sp1.getString("subId","0");
        get_set(phoneNumber);
    }

    public void get_set(String ph_no){
        Cursor res = myDb.getAllData_set(ph_no);
        if(res.getCount() == 0) {
            Toast.makeText(this,"Nothing found",Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i("Rec_Count_settings",String.valueOf(res.getCount()));
        while (res.moveToNext()) {
            String temp1 = res.getString(14);
            Log.i("EXT Value", String.valueOf(temp1));
            if(temp1!=null){
                if (temp1.contains("1")){
                    Log.i("...Value", String.valueOf(temp1));
                    rbutton1.setChecked(true);
                }
                else{
                    Log.i("...Value", String.valueOf(temp1));
                    rbutyon2.setChecked(true);
                }

            }
            String temp2 = res.getString(15);
            et1.setText(temp2);

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        SMSBody1 = "";
    }

    public void turnsOn(View view) {
        String message = "";
        if (rbutton1.isChecked()) {
            message = "SPC,2,1";
            SmsManager smsManager = SmsManager.getDefault();
             smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            String mot_data[] = {"", "", ""};
            mot_data[1]="1";
            boolean isInserted = myDb.update_set(mot_data,phoneNumber,2717);

        }
        else if (rbutyon2.isChecked()) {
            message = "SPC,2,0";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            String mot_data[] = {"", "", ""};
            mot_data[1]="0";
            boolean isInserted = myDb.update_set(mot_data,phoneNumber,2717);
        } else return;
    }

    public void set_tripTime(View view) {
        Log.i("..", "trip time event");
        String text = et1.getText().toString();
        if (TextUtils.isEmpty(text)) et1.setError("please enter trip time!..");
        else {
            Log.i("..", text);
            String message = "SPC,3," + text;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.i("message", message);
            Log.i("Test", "SMS sent!");
            textView2.setText("Command Sent, Please Wait...For 2 minutes");
            String mot_data[] = {"", "", ""};
            mot_data[1]=text;
            boolean isInserted = myDb.update_set(mot_data,phoneNumber,2718);
        }
    }

    public void set_tripCurrent(View view) {

        String text = et2.getText().toString();
        Log.i("..", text);

        String text2 = et3.getText().toString();
        Log.i("..", text2);
        String message = " ";
        if (TextUtils.isEmpty(text) || TextUtils.isEmpty(text2)) {
            if (TextUtils.isEmpty(text)) et2.setError("please provide 3phase trip current!..");
            else et3.setError("please provide 2phase trip current!..");
        }
        else {
            message = "SPC,5" + "," + text + "," + text2;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            pbar2.setVisibility(View.VISIBLE);
            Log.i("message", message);
            Log.i("Test", "SMS sent!");
            textView3.setText("Command Sent, Please Wait...For 2 minutes");

            et2.getText().clear();
            et3.getText().clear();
        }
    }
    //Overload Trip Percentage
    public void set_trip_percentage(View view) {
        Log.i("..", "trip percentage event");
        String text = et4.getText().toString();
        if (TextUtils.isEmpty(text)) et4.setError("Please enter Overload %!..");
        else {
            Log.i("..", text);
            String message = "SPC,4," + text;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            pbar3.setVisibility(View.VISIBLE);
            Log.i("message", message);
            Log.i("Test", "SMS sent!");
            textView4.setText("Command Sent, Please Wait...For 2 minutes");

            et4.getText().clear();
        }
    }

    public void settings(View view) {
        //starting another activity..
        Intent it1 = new Intent(OverLoadActivity.this, SettingsActivity.class);
        startActivity(it1);
    }

    public void ON_OFF(View view) {
        //starting another activity..
        Intent it2 = new Intent(OverLoadActivity.this, ON_OFFActivity.class);
        startActivity(it2);
    }

    public void Users(View view) {
        //starting another activity..
        Intent it3 = new Intent(OverLoadActivity.this, UsersActivity.class);
        startActivity(it3);
    }

    public void manual(View view) {
        //starting another activity..
        Intent it4 = new Intent(OverLoadActivity.this, ManualActivity.class);
        startActivity(it4);
    }

    public void home(View view) {
        Intent it5 = new Intent(OverLoadActivity.this, Main2Activity.class);
        startActivity(it5);
    }



}
