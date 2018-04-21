package com.blueskylinks.spc_main;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
 public static  TextView tv;
    public static  TextView tv1;
    public static  TextView tv2;
    public static  TextView tv3;
    public static  TextView tv4;
    public static  TextView tv5;
    public static  TextView textView6;
    TextView tv9;
    public static ProgressDialog progressDialog;
    //String phoneNumber = "9764005401";
    String phoneNumber = "9663261329";
    public static TextView b;
    String SMSBody1;

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
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_PHONE_STATE}, 200);
        tv = findViewById(R.id.textView11);
        tv1 = findViewById(R.id.textView12);
        tv2 = findViewById(R.id.textView13);
        tv3 = findViewById(R.id.textView17);
        tv4 = findViewById(R.id.textView18);
        tv9=findViewById(R.id.textView29);

        /*String message = "Statue sms by 7337619279\n" +
                "Motor is on in 3 phase mode\n" +
                "RY:-389.40\n" +
                "YB:-393.20\n" +
                "BR:-392.20\n"; */
        String message = "SPC,25";
        SmsManager smsManager = SmsManager.getDefault();
       // smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Log.i("Test", "SMS sent!");
        progress();

    }

    @Override
    protected void onResume(){
        super.onResume();
        SMSBody1="";

        final IntentFilter mIntentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(sms_notify_reciver, mIntentFilter);
        registerReceiver(sms_notify_reciver,mIntentFilter);
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
                    Thread.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();
    }


    public void settings(View view){
            //starting another activity..
            Intent it1 = new Intent(Main2Activity.this, SettingsActivity.class);
            startActivity(it1);
    }

    public void ON_OFF(View view){
        //starting another activity..
        Intent it2 = new Intent(Main2Activity.this, ON_OFFActivity.class);
        startActivity(it2);
    }

    public void Users(View view){
        //starting another activity..
        Intent it3 = new Intent(Main2Activity.this, UsersActivity.class);
        startActivity(it3);
    }

    public void manual(View view){
        //starting another activity..
        Intent it4 = new Intent(Main2Activity.this, ManualActivity.class);
        startActivity(it4);
    }


    private final BroadcastReceiver sms_notify_reciver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent2) {
            if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent2.getAction())) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent2)) {
                    String senderNum = smsMessage.getDisplayOriginatingAddress();
                    //  Log.i("sender num", senderNum);
                    SMSBody1 += smsMessage.getMessageBody().toString();
                    Log.i("length", String.valueOf(SMSBody1.length()));
                    Log.i("Received SMS:", SMSBody1);
                    String[] lines = SMSBody1.split("\\r?\\n");
                    int l = lines.length - 1;
                    Log.i("lines length", String.valueOf(l));
                    String s4 = lines[l].toString();
                    String sms = SMSBody1;

                    if (sms.length() > 153) {
                        if (lines[1].toString().contains("on")) {
                            tv.setText("ON");
                            if (lines[1].toString().contains("3")) tv1.setText("3 Phase Mode");
                            else tv1.setText("2 Phase Mode");
                            String s1 = lines[2].toString();
                            String s2 = lines[3].toString();
                            String s3 = lines[4].toString();
                            tv2.setText(s1.substring(4));
                            tv3.setText(s2.substring(4));
                            tv4.setText(s3.substring(4));
                            tv9.setText(s4.substring(6));
                        } else if (lines[1].toString().contains("off")) {
                            tv.setText("OFF");
                            tv9.setText(s4.substring(10));
                        } else return;
                        SMSBody1 = "";

                    }
                }
            }
        }
    };

    @Override
    public void onPause(){
        super.onPause();
        unregisterReceiver(sms_notify_reciver);
    }
}
