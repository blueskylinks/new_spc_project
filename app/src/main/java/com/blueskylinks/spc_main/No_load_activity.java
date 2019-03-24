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
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


public class No_load_activity extends AppCompatActivity {
    RadioButton rbutton1;
    RadioButton rbutyon2;
    String phoneNumber ;
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
    SharedPreferences sp1;
    EditText et3;
    DatabaseHelper myDb;
    EditText et4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new DatabaseHelper(this);
        setContentView(R.layout.activity_no_load_activity);
        rbutton1=findViewById(R.id.radioButton);
        rbutyon2=findViewById(R.id.radioButton2);
        tv=findViewById(R.id.mot_st);
        tv1=findViewById(R.id.dryrun1);
        tv2=findViewById(R.id.dryrun2);
        textView1=findViewById(R.id.onoff_status_text1);
        textView2=findViewById(R.id.onoff_status_text2);
        textView3=findViewById(R.id.onoff_status_text3);
        textView4=findViewById(R.id.onoff_status_text4);
        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        et3=findViewById(R.id.et3);
        et4=findViewById(R.id.no_lod_et);
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
            String temp1 = res.getString(19);
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
            String temp2 = res.getString(20);
            et1.setText(temp2);

            String temp3 = res.getString(21);
            et2.setText(temp3);

            String temp4 = res.getString(22);
            et3.setText(temp4);

            String temp5 = res.getString(23);
            et4.setText(temp5);


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
            message = "SPC,6,1";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            String mot_data[] = {"", "", ""};
            mot_data[1]="1";
            boolean isInserted = myDb.update_set(mot_data,phoneNumber,2722);
        }
        else if (rbutyon2.isChecked()) {
            message = "SPC,6,0";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            String mot_data[] = {"", "", ""};
            mot_data[1]="0";
            boolean isInserted = myDb.update_set(mot_data,phoneNumber,2722);
        } else return;

    }

    public void set_tripTime(View view) {
        Log.i("..", "trip time event");
        String text = et1.getText().toString();
        if (TextUtils.isEmpty(text)) et1.setError("please enter trip time!..");
        else {
            Log.i("..", text);
            String message = "SPC,7," + text;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.i("Test", "SMS sent!");
            textView2.setText("Command Sent, Please Wait...For 2 minutes");
            String mot_data[] = {"", "", ""};
            mot_data[1]=text;
            boolean isInserted = myDb.update_set(mot_data,phoneNumber,2723);


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
            message = "SPC,8" + "," + text + "," + text2;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            String mot_data[] = {"", "", ""};
            mot_data[1]=text;
            mot_data[2]=text2;
            boolean isInserted = myDb.update_set(mot_data,phoneNumber,2724);
        }
    }

    //Overload Trip Percentage
    public void set_nltrip_percentage(View view) {
        Log.i("..", "trip percentage event");
        String text = et4.getText().toString();
        if (TextUtils.isEmpty(text)) et4.setError("Please enter Overload %!..");
        else {
            Log.i("..", text);
            String message = "SPC,9," + text;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.i("message", message);
            textView4.setText("Command Sent, Please Wait...For 2 minutes");
            String mot_data[] = {"", "", ""};
            mot_data[1]=text;
            boolean isInserted = myDb.update_set(mot_data,phoneNumber,2726);
        }
    }

    public void settings(View view) {
        //starting another activity..
        Intent it1 = new Intent(No_load_activity.this, SettingsActivity.class);
        startActivity(it1);
    }

    public void ON_OFF(View view) {
        //starting another activity..
        Intent it2 = new Intent(No_load_activity.this, ON_OFFActivity.class);
        startActivity(it2);
    }

    public void Users(View view) {
        //starting another activity..
        Intent it3 = new Intent(No_load_activity.this, UsersActivity.class);
        startActivity(it3);
    }

    public void manual(View view) {
        //starting another activity..
        Intent it4 = new Intent(No_load_activity.this, ManualActivity.class);
        startActivity(it4);
    }

    public void home(View view) {
        Intent it5 = new Intent(No_load_activity.this, Main2Activity.class);
        startActivity(it5);
    }



    @Override
   public void onPause(){
        super.onPause();
    }
}
