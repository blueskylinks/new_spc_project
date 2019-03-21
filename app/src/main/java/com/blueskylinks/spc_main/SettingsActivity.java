package com.blueskylinks.spc_main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.provider.Telephony;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class SettingsActivity extends AppCompatActivity {
    Switch s1;
    Switch s2;
    Switch s4;
    Switch s5;
    Switch s6;
    Switch s7;
    Switch s8;
    TextView text;
    TextView tv;
    String SMSBody1;
    String phoneNumber;
    boolean val1 = false;
    boolean val2 = false;
    boolean val3 = false;
    boolean val4 = false;
    boolean val5 = false;
    Boolean val6=false;
    Boolean val7=false;
    boolean val8 = false;
    SharedPreferences sp1;
    DatabaseHelper myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        myDb = new DatabaseHelper(this);
        s1=findViewById(R.id.switch1);
        s2=findViewById(R.id.switch2);
        s4=findViewById(R.id.switch4);
        s5=findViewById(R.id.switch5);
        s6=findViewById(R.id.switch6);
        s7=findViewById(R.id.switchp2);
        s8=findViewById(R.id.switch7);
        //   tv=findViewById(R.id.textView1);
        // text=findViewById(R.id.onoff_status_text_1);
        sp1=getSharedPreferences("login",0);
        //logged=sp1.getBoolean("logged",false);
        phoneNumber=sp1.getString("subId","0");
        get_data(phoneNumber);
        get_set(phoneNumber);

    }

    @Override
    public void onResume(){
        super.onResume();
        SMSBody1="";
    }

    public void get_set(String ph_no){
        Cursor res = myDb.getAllData_set(ph_no);
        if(res.getCount() == 0) {
            Toast.makeText(this,"Nothing found",Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i("Rec_Count_settings",String.valueOf(res.getCount()));
        while (res.moveToNext()) {
            String temp_s=res.getString(8);
            Log.i("EXT Value",String.valueOf(temp_s));
            if(temp_s.contains("1"))
                s7.setChecked(true);
            else
                s7.setChecked(false);

            String temp1=res.getString(9);
            Log.i("Sinle ph",String.valueOf(temp1));
            if(temp1.contains("1"))
                s2.setChecked(true);
            else
                s2.setChecked(false);

            String temp2=res.getString(10);
            Log.i("Rev ph",String.valueOf(temp2));
            if(temp2.contains("1"))
                s4.setChecked(true);
            else
                s4.setChecked(false);

            String temp3=res.getString(11);
            Log.i("Feed SMS",String.valueOf(temp3));
            if(temp3.contains("1"))
                s5.setChecked(true);
            else
                s5.setChecked(false);

            String temp4=res.getString(12);
            Log.i("Feed CALL",String.valueOf(temp3));
            if(temp4.contains("1"))
                s6.setChecked(true);
            else
                s6.setChecked(false);

            String temp5=res.getString(13);
            Log.i("Feed CALL",String.valueOf(temp5));
            if(temp5.contains("1"))
                s8.setChecked(true);
            else
                s8.setChecked(false);

        }

    }

    public void get_data(String ph_no){
        Cursor res = myDb.getAllData(ph_no);
        if(res.getCount() == 0) {
            Toast.makeText(this,"Nothing found",Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i("Rec_Count",String.valueOf(res.getCount()));
        while (res.moveToNext()) {
            String temp_s=res.getString(9);
            if(temp_s.contains("3"))
                s1.setChecked(false);
            else
                s1.setChecked(true);
        }

    }

    public void submit_func(View view){
        switch(view.getId()){
            case R.id.switch1:
                if(s1.isChecked()){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage("Are you sure want to switch on!.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String message = "SPC,36,1";
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                                    Log.i("Test", message);
                                    String mot_data[] = {"", "", ""};
                                    mot_data[1]="2";
                                    boolean isInserted = myDb.updateData(mot_data,phoneNumber,1005);

                                }
                            });
                            builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    s1.setChecked(false);
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else{
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage("Are you sure want to switch off!.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String message = "SPC,36,0";
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    s1.setChecked(true);
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                    String mot_data[] = {"", "", ""};
                    mot_data[1]="3";
                    boolean isInserted = myDb.updateData(mot_data,phoneNumber,1005);
                    if(isInserted == true)
                        Toast.makeText(this,"Data Inserted",Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(this,"Data not Inserted",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.switch2:
                if(s2.isChecked()){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage("Are you sure want to switch on!.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String message = "SPC,42,1";
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                                    Log.i("Test", message);
                                    String mot_data[] = {"", "", ""};
                                    mot_data[1]="1";
                                    boolean isInserted = myDb.update_set(mot_data,phoneNumber,2712);
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    s2.setChecked(false);
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else{
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage("Are you sure want to switch off!.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String message = "SPC,42,0";
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                                    String mot_data[] = {"", "", ""};
                                    mot_data[1]="0";
                                    boolean isInserted = myDb.update_set(mot_data,phoneNumber,2712);
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    s2.setChecked(true);
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                break;

            case R.id.switch4:
                if(s4.isChecked()){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage("Are you sure want to switch on!.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String message = "SPC,18,1";
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                                    Log.i("Test", message);
                                    String mot_data[] = {"", "", ""};
                                    mot_data[1]="1";
                                    boolean isInserted = myDb.update_set(mot_data,phoneNumber,2713);

                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    s4.setChecked(false);
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else{
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage("Are you sure want to switch off!.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String message = "SPC,18,0";
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                                    Log.i("Test", message);
                                    String mot_data[] = {"", "", ""};
                                    mot_data[1]="0";
                                    boolean isInserted = myDb.update_set(mot_data,phoneNumber,2713);

                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    s4.setChecked(true);
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                break;
            case R.id.switch5:
                if(s5.isChecked()){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage("Are you sure want to switch on!.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String message = "SPC,1,1";
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                                    Log.i("Test", message);
                                    String mot_data[] = {"", "", ""};
                                    mot_data[1]="1";
                                    boolean isInserted = myDb.update_set(mot_data,phoneNumber,2714);

                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    s5.setChecked(false);
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else{
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage("Are you sure want to switch off!.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String message = "SPC,1,0";
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                                    Log.i("Test", message);
                                    String mot_data[] = {"", "", ""};
                                    mot_data[1]="0";
                                    boolean isInserted = myDb.update_set(mot_data,phoneNumber,2714);

                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    s5.setChecked(true);
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                break;

            case R.id.switch6:
                if(s6.isChecked()){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage("Are you sure want to switch on!.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String message = "SPC,0,1";
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                                    Log.i("Test", message);
                                    String mot_data[] = {"", "", ""};
                                    mot_data[1]="1";
                                    boolean isInserted = myDb.update_set(mot_data,phoneNumber,2715);

                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    s6.setChecked(false);
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else{
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage("Are you sure want to switch off!.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String message = "SPC,0,0";
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                                    Log.i("Test", message);
                                    String mot_data[] = {"", "", ""};
                                    mot_data[1]="0";
                                    boolean isInserted = myDb.update_set(mot_data,phoneNumber,2715);

                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    s6.setChecked(true);
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                break;
            case R.id.switchp2:
                if(s7.isChecked()){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage("Are you sure want to switch on!.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String message = "SPC,46,1";
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                                    Log.i("Test", message);
                                    String mot_data[] = {"", "", ""};
                                    mot_data[1]="1";
                                    boolean isInserted = myDb.update_set(mot_data,phoneNumber,2711);
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    s7.setChecked(false);
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else{
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage("Are you sure want to switch off!.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String message = "SPC,46,0";
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                                    Log.i("Test", message);
                                    String mot_data[] = {"", "", ""};
                                    mot_data[1]="0";
                                    boolean isInserted = myDb.update_set(mot_data,phoneNumber,2711);
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    s7.setChecked(true);
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                break;
            case R.id.switch7:
                if(s8.isChecked()){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage("Are you sure want to switch on!.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String message = "SPC,22,1";
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                                    Log.i("Test", message);
                                    String mot_data[] = {"", "", ""};
                                    mot_data[1]="1";
                                    boolean isInserted = myDb.update_set(mot_data,phoneNumber,2716);

                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    s8.setChecked(false);
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else{
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage("Are you sure want to switch off!.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String message = "SPC,22,0";
                                    SmsManager smsManager = SmsManager.getDefault();
                                    smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                                    Log.i("Test", message);
                                    String mot_data[] = {"", "", ""};
                                    mot_data[1]="0";
                                    boolean isInserted = myDb.update_set(mot_data,phoneNumber,2716);

                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    s8.setChecked(true);
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                break;

        }
    }

    public void overLoad(View view){
        Intent in1 = new Intent(SettingsActivity.this, OverLoadActivity.class);
        startActivity(in1);
    }

    public void overVoltage(View view){
        Intent in2 = new Intent(SettingsActivity.this, Over_voltage_funtion.class);
        startActivity(in2);
    }

    public void NoLoad(View view){
        Intent in3 = new Intent(SettingsActivity.this, No_load_activity.class);
        startActivity(in3);
    }

    public void Restart(View view){
        Intent in3 = new Intent(SettingsActivity.this, RestartDryrunActivity.class);
        startActivity(in3);
    }

    public void underVoltage(View view){
        Intent in2 = new Intent(SettingsActivity.this, UnderVoltageActivity.class);
        startActivity(in2);
    }

    public void v_diff(View view){
        Intent in2 = new Intent(SettingsActivity.this, v_diff_settActivity.class);
        startActivity(in2);
    }
    public void spp_v(View view){
        Intent in2 = new Intent(SettingsActivity.this, spp_voltsActivity.class);
        startActivity(in2);
    }


    //Home functions
    public void home(View view){
        Intent it1 = new Intent(SettingsActivity.this, Main2Activity.class);
        startActivity(it1);
    }

    public void ON_OFF(View view){
        //starting another activity..
        Intent it2 = new Intent(SettingsActivity.this, ON_OFFActivity.class);
        startActivity(it2);
    }

    public void Users(View view){
        //starting another activity..
        Intent it3 = new Intent(SettingsActivity.this, UsersActivity.class);
        startActivity(it3);
    }


    @Override
    public void onPause(){
        super.onPause();
        // unregisterReceiver(settingfunconoroffReceiver);
    }
}


