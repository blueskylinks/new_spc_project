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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class RestartDryrunActivity extends AppCompatActivity {
    RadioButton rbutton1;
    RadioButton rbutton2;
    String phoneNumber ;
    ProgressBar pbar;
    ProgressBar pbar1;
    TextView textView1;
    TextView textView2;;
    TextView tv;
    TextView tv1;
    EditText et2;
    DatabaseHelper myDb;
    SharedPreferences sp1;
    String SMSBody1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new DatabaseHelper(this);
        setContentView(R.layout.activity_restart_dryrun);
        rbutton2 = findViewById(R.id.radioButton2);
        rbutton1 = findViewById(R.id.radioButton);
        pbar = findViewById(R.id.onoff_pgbar1);
        tv = findViewById(R.id.mot_st);
        textView1 = findViewById(R.id.onoff_status_text1);
        textView2 = findViewById(R.id.onoff_status_text3);
        tv1 = findViewById(R.id.time);
        pbar1 = findViewById(R.id.set);
        et2 = findViewById(R.id.et11);
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
            String temp1 = res.getString(24);
            Log.i("EXT Value", String.valueOf(temp1));
            if(temp1!=null){
                if (temp1.contains("1")){
                    Log.i("...Value", String.valueOf(temp1));
                    rbutton1.setChecked(true);
                }
                else{
                    Log.i("...Value", String.valueOf(temp1));
                    rbutton2.setChecked(true);
                }
            }
            String temp2 = res.getString(25);
            et2.setText(temp2);
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
            message = "SPC,10,1";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            textView1.setText("Command Sent, Please Wait...For 2 minutes");
            String mot_data[] = {"", "", ""};
            mot_data[1]="1";
            boolean isInserted = myDb.update_set(mot_data,phoneNumber,2727);
        }
        else if (rbutton2.isChecked()) {
            message = "SPC,10,0";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            textView1.setText("Command Sent, Please Wait...For 2 minutes");
            String mot_data[] = {"", "", ""};
            mot_data[1]="0";
            boolean isInserted = myDb.update_set(mot_data,phoneNumber,2727);
        } else return;
    }

    public void set_restart_time(View view) {

        String text = et2.getText().toString();
        Log.i("..", text);
        String message = " ";
        if (TextUtils.isEmpty(text)) {
           et2.setError("please provide restart time in seconds!..");
        }
        else {
            message = "SPC,11" + "," + text;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.i("message", message);
            textView2.setText("Command Sent, Please Wait...For 2 minutes");
            String mot_data[] = {"", "", ""};
            mot_data[1]=text;
            boolean isInserted = myDb.update_set(mot_data,phoneNumber,2728);
        }
    }

    public void settings(View view) {
        //starting another activity..
        Intent it1 = new Intent(RestartDryrunActivity.this, SettingsActivity.class);
        startActivity(it1);
    }

    public void ON_OFF(View view) {
        //starting another activity..
        Intent it2 = new Intent(RestartDryrunActivity.this, ON_OFFActivity.class);
        startActivity(it2);
    }

    public void Users(View view) {
        //starting another activity..
        Intent it3 = new Intent(RestartDryrunActivity.this, UsersActivity.class);
        startActivity(it3);
    }

    public void manual(View view) {
        //starting another activity..
        Intent it4 = new Intent(RestartDryrunActivity.this, ManualActivity.class);
        startActivity(it4);
    }

    public void home(View view) {
        Intent it5 = new Intent(RestartDryrunActivity.this, Main2Activity.class);
        startActivity(it5);
    }

}
