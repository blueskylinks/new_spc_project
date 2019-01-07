package com.blueskylinks.spc_main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
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
    String SMSBody1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restart_dryrun);
        rbutton2=findViewById(R.id.radioButton2);
        rbutton1=findViewById(R.id.radioButton);
        pbar=findViewById(R.id.onoff_pgbar1);
        tv=findViewById(R.id.mot_st);
        textView1=findViewById(R.id.onoff_status_text1);
        textView2=findViewById(R.id.onoff_status_text3);
        tv1=findViewById(R.id.time);
        pbar1=findViewById(R.id.set);
        et2=findViewById(R.id.et11);
        //get Destination address
        SharedPreferences sp1=getSharedPreferences("login",0);
        phoneNumber=sp1.getString("subId","0");

    }


    @Override
    public void onResume() {
        super.onResume();
        SMSBody1 = "";

        final IntentFilter resIntentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(RestartReceiver, resIntentFilter);
        registerReceiver(RestartReceiver, resIntentFilter);
    }

    public void turnsOn(View view) {
        String message = "";
        if (rbutton1.isChecked()) {
            message = "SPC,10,1";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.i("message", message);
            Log.i("Test", "SMS sent!");
            pbar.setVisibility(View.VISIBLE);
            textView1.setText("Command Sent, Please Wait...For 2 minutes");
        }
        else if (rbutton2.isChecked()) {
            message = "SPC,10,0";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.i("message", message);
            Log.i("Test", "SMS sent!");
            pbar.setVisibility(View.VISIBLE);
            textView1.setText("Command Sent, Please Wait...For 2 minutes");
        } else return;
    }

    public void set_maxvoltage(View view) {

        String text = et2.getText().toString();
        Log.i("..", text);
        String message = " ";
        if (TextUtils.isEmpty(text)) {
           et2.setError("please provide 3phase trip current!..");
        }
        else {
            message = "SPC,11" + "," + text;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            pbar1.setVisibility(View.VISIBLE);
            Log.i("message", message);
            Log.i("Test", "SMS sent!");
            textView2.setText("Command Sent, Please Wait...For 2 minutes");

            et2.getText().clear();
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

    private final BroadcastReceiver RestartReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent4) {
            if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent4.getAction())) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent4)) {
                    String senderNum = smsMessage.getDisplayOriginatingAddress();
                    SMSBody1 += smsMessage.getMessageBody().toString();
                    Log.i("length", String.valueOf(SMSBody1.length()));
                    Log.i("Received SMS:", SMSBody1);
                    String[] lines = SMSBody1.split("\\r?\\n");
                    if(lines[2].toString().contains("function is on")){
                        tv.setText("ON");
                        pbar.setVisibility(View.INVISIBLE);
                    }
                    else if(lines[2].toString().contains("function is off")){
                        tv.setText("OFF");
                        pbar.setVisibility(View.INVISIBLE);
                    }
                    else if(lines[2].toString().contains("noload restart")){
                        tv1.setText(lines[2]);
                        pbar1.setVisibility(View.INVISIBLE);
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
        unregisterReceiver(RestartReceiver);
    }

}
