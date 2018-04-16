package com.blueskylinks.spc_main;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.RotateDrawable;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import static com.blueskylinks.spc_main.Main2Activity.progressDialog;
import static com.blueskylinks.spc_main.Main2Activity.tv;

public class ON_OFFActivity extends AppCompatActivity {
    String phoneNumber = "9663261329";
    public static TextView textView1;
    public static TextView textView2;
    public static TextView mot_st_text1;
    public ProgressBar onoffpg1;
    String phoneNumber1;
    String SMSBody1;
    RadioButton onoff_rb1;
    RadioButton onoff_rb2;

    public void getSmsDetails(String phoneNumber, String SMSBody) {
        phoneNumber1 = phoneNumber;
        SMSBody1 = SMSBody;
    }

    @Override
    protected void onResume(){
        super.onResume();
        SMSBody1="";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on__off);
        onoffpg1 = findViewById(R.id.onoff_pgbar1);
        onoffpg1.setVisibility(View.INVISIBLE);
        textView1=findViewById(R.id.onoff_status_text1);
        mot_st_text1 = findViewById(R.id.mot_st);
        onoff_rb1=findViewById(R.id.radioButton);
        onoff_rb2=findViewById(R.id.radioButton);
        final IntentFilter mIntentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(toastOrNotificationCatcherReceiver, mIntentFilter);
        registerReceiver(toastOrNotificationCatcherReceiver,mIntentFilter);
    }

    public void turnsOn(View view){
        String message = "";
        if(onoff_rb1.isChecked()){
            message = "SPC,24";
        }
        else{
            message = "SPC,26";
        }

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Log.i("Test", "SMS sent!");
        onoffpg1.setVisibility(View.VISIBLE);
        textView1.setText("Command Sent, Please Wait...For 2 minutes");
    }

    //Progress Dialog
    public void progress(){
        progressDialog = new ProgressDialog(ON_OFFActivity.this);
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

    public void turnsOff(View view){

        String message = "SPC,26";
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Log.i("Test", "SMS sent!");
        progress();
    }

    public void settings(View view){
        //starting another activity..
        Intent it1 = new Intent(ON_OFFActivity.this, SettingsActivity.class);
        startActivity(it1);


    }

    //Hoome functions
    public void home(View view){
        Intent it1 = new Intent(ON_OFFActivity.this, Main2Activity.class);
        startActivity(it1);
    }

    private final BroadcastReceiver toastOrNotificationCatcherReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    String senderNum = smsMessage.getDisplayOriginatingAddress();
                    Log.i("sender num", senderNum);
                    SMSBody1 += smsMessage.getMessageBody().toString();
                    String[] lines = SMSBody1.split("\\r?\\n");
                    int l=lines.length-1;
                    Log.i("test",String.valueOf(l));
                    Log.i("test",lines[l]);
                    for (int i=0;i<=l;i++){
                        Log.i("SMSText",lines[i]);
                    }
                    if(lines[2].toString().contains("on")){
                        onoffpg1.setVisibility(View.INVISIBLE);
                        textView1.setText(lines[4]);
                        mot_st_text1.setText("ON");
                    }
                    else {
                        onoffpg1.setVisibility(View.INVISIBLE);
                        textView1.setText(lines[2]+lines[3]);
                        mot_st_text1.setText("OFF");
                    }
                    SMSBody1 ="";

                }
            }
        }
    };

}
