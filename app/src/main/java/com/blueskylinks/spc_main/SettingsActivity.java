package com.blueskylinks.spc_main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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

public class SettingsActivity extends AppCompatActivity {
    Switch s1;
    Switch s2;
    Switch s3;
    Switch s4;
    Switch s5;
    String phoneNumber = "9663261329";
    ProgressBar pbar;
    TextView text;
    TextView tv;
    String SMSBody1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        s1=findViewById(R.id.switch1);
        s2=findViewById(R.id.switch2);
        s3=findViewById(R.id.switch3);
        s4=findViewById(R.id.switch4);
        s5=findViewById(R.id.switch5);
        pbar=findViewById(R.id.progress);
        tv=findViewById(R.id.textView1);
        text=findViewById(R.id.onoff_status_text_1);
    }

    @Override
    public void onResume(){
        super.onResume();
        SMSBody1="";
        final IntentFilter SintentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(settingfunconoroffReceiver, SintentFilter);
        registerReceiver(settingfunconoroffReceiver,SintentFilter);
    }

public void submit_func(View view){
        switch(view.getId()){
            case R.id.switch1:
                if(s1.isChecked()){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage("Are you sure want to switch on!.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String message = "SPC,36,1";
                                    SmsManager smsManager = SmsManager.getDefault();
                                    //   smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                                    Log.i("Test", message);
                                    pbar.setVisibility(View.VISIBLE);
                                    text.setText("Command Sent, Please Wait...For 2 minutes");
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
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
                    //   smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                    Log.i("Test", message);
                    pbar.setVisibility(View.VISIBLE);
                    text.setText("Command Sent, Please Wait...For 2 minutes");
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
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
                    //   smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                    Log.i("Test", message);
                    pbar.setVisibility(View.VISIBLE);
                    text.setText("Command Sent, Please Wait...For 2 minutes");
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
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
                    //   smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                    Log.i("Test", message);
                    pbar.setVisibility(View.VISIBLE);
                    text.setText("Command Sent, Please Wait...For 2 minutes");
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                break;
            case R.id.switch3:
                if(s3.isChecked()){
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage("Are you sure want to switch on!.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                    String message = "SPC,17,1";
                    SmsManager smsManager = SmsManager.getDefault();
                    //   smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                    Log.i("Test", message);
                    pbar.setVisibility(View.VISIBLE);
                    text.setText("Command Sent, Please Wait...For 2 minutes");
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
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
                    String message = "SPC,17,0";
                    SmsManager smsManager = SmsManager.getDefault();
                    //   smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                    Log.i("Test", message);
                    pbar.setVisibility(View.VISIBLE);
                    text.setText("Command Sent, Please Wait...For 2 minutes");
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
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
                    //   smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                    Log.i("Test", message);
                    pbar.setVisibility(View.VISIBLE);
                    text.setText("Command Sent, Please Wait...For 2 minutes");
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
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
                    //   smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                    Log.i("Test", message);
                    pbar.setVisibility(View.VISIBLE);
                    text.setText("Command Sent, Please Wait...For 2 minutes");
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
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
                    //   smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                    Log.i("Test", message);
                    pbar.setVisibility(View.VISIBLE);
                    text.setText("Command Sent, Please Wait...For 2 minutes");
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
                else{  AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                    builder1.setMessage("Are you sure want to switch off!.");
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                    String message = "SPC,1,0";
                    SmsManager smsManager = SmsManager.getDefault();
                    //   smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                    Log.i("Test", message);
                    pbar.setVisibility(View.VISIBLE);
                    text.setText("Command Sent, Please Wait...For 2 minutes");
                   }
                });
                 builder1.setNegativeButton(
                          "No",
                      new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface dialog, int id) {
                      dialog.cancel();
                     }
               });
                 AlertDialog alert11 = builder1.create();
                 alert11.show();
                }
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

    //Hoome functions
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

    public void manual(View view) {
        //starting another activity..
        Intent it4 = new Intent(SettingsActivity.this, ManualActivity.class);
        startActivity(it4);
    }

    private final BroadcastReceiver settingfunconoroffReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent7) {
            if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent7.getAction())) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent7)) {
                    SMSBody1 = smsMessage.getMessageBody().toString();
                    Log.i("Received sms", SMSBody1);
                    String sms = SMSBody1;
                    Log.i("length", String.valueOf(SMSBody1.length()));
                    String[] lines = SMSBody1.split("\\r?\\n");
                    if(lines[2].toString().contains("on")){
                        tv.setText("ON");
                        pbar.setVisibility(View.INVISIBLE);
                        text.setText(lines[2]);
                    }
                    else if(lines[2].toString().contains("off")){
                        tv.setText("OFF");
                        text.setText(lines[2]);
                        pbar.setVisibility(View.INVISIBLE);
                    }
                    else return;
                    SMSBody1 = "";
                }
            }
        }
    };

    @Override
    public void onPause(){
        super.onPause();
        unregisterReceiver(settingfunconoroffReceiver);
    }
}


