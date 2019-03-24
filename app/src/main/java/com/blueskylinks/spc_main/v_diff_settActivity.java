package com.blueskylinks.spc_main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Vibrator;
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
import android.widget.TextView;
import android.widget.Toast;


public class v_diff_settActivity extends AppCompatActivity {
    String phoneNumber ;
    ProgressBar pbar;
    TextView textView;
    TextView tv;
    EditText et;
    String SMSBody1;
    DatabaseHelper myDb;
    SharedPreferences sp1;
    Vibrator vibe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new DatabaseHelper(this);
        setContentView(R.layout.voltage_difference_setting);
        pbar = findViewById(R.id.set3);
        et = findViewById(R.id.vdiff_val);
        vibe = (Vibrator) getSystemService(VIBRATOR_SERVICE);

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
            String temp1 = res.getString(32);
            Log.i("EXT Value", String.valueOf(temp1));
            et.setText(temp1);
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        SMSBody1 = "";
    }

    //Over and Under Volts difference
    public void v_diff(View view) {
        Log.i("..", "voltage difference");
        String text = et.getText().toString();
        if (TextUtils.isEmpty(text)) {et.setError("Enter Volts difference!..");
        vibe.vibrate(200);
        }
        else {
            Log.i("..", text);
            String message = "SPC,16," + text;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            String mot_data[] = {"", "", ""};
            mot_data[1]=text;
            boolean isInserted = myDb.update_set(mot_data,phoneNumber,2735);
        }
    }

    public void settings(View view) {
        //starting another activity..
        Intent it1 = new Intent(v_diff_settActivity.this, SettingsActivity.class);
        startActivity(it1);
    }

    public void ON_OFF(View view) {
        //starting another activity..
        Intent it2 = new Intent(v_diff_settActivity.this, ON_OFFActivity.class);
        startActivity(it2);
    }

    public void Users(View view) {
        //starting another activity..
        Intent it3 = new Intent(v_diff_settActivity.this, UsersActivity.class);
        startActivity(it3);
    }

    public void home(View view) {
        Intent it5 = new Intent(v_diff_settActivity.this, Main2Activity.class);
        startActivity(it5);
    }


}
