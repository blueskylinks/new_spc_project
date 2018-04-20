package com.blueskylinks.spc_main;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.provider.Telephony;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import static com.blueskylinks.spc_main.Main2Activity.progressDialog;

public class UsersActivity extends AppCompatActivity {
    int count=0;
    SharedPreferences sharedPreferences1;
    String ms="00";
    String SMSBody1;
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    TextView text5;
    String user2;
    String user3;
    String user4;
    String user5;
    String phoneNumber = "9663261329";
    EditText et1;
    EditText et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        String message = "SPC,92";
        SmsManager smsManager = SmsManager.getDefault();
       // smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Log.i("Test", "SMS sent!");
        progress();
        text1 = findViewById(R.id.u_text11);
        text2 = findViewById(R.id.u_text22);
        text3 = findViewById(R.id.u_text33);
        text4 = findViewById(R.id.u_text44);
        text5 = findViewById(R.id.u_text55);
    }
@Override
public void onResume(){
        super.onResume();
        SMSBody1="";
    final IntentFilter IntentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
    registerReceiver(addorremovereceiver, IntentFilter);
    registerReceiver(addorremovereceiver,IntentFilter);

}

    public void settings(View view){
        //starting another activity..
        Intent it1 = new Intent(UsersActivity.this, SettingsActivity.class);
        startActivity(it1);
    }

    //Hoome functions
    public void home(View view){
        Intent it1 = new Intent(UsersActivity.this, Main2Activity.class);
        startActivity(it1);
    }

    public void ON_OFF(View view){
        //starting another activity..
        Intent it2 = new Intent(UsersActivity.this, ON_OFFActivity.class);
        startActivity(it2);
    }

    public void manual(View view){
        //starting another activity..
        Intent it4 = new Intent(UsersActivity.this, ManualActivity.class);
        startActivity(it4);
    }

    //Progress Dialog
    public void progress(){
        progressDialog = new ProgressDialog(UsersActivity.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle(" "); // Setting Title
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

    //Add users
    public void Add_users(View view) {
        String no ;
        TextView textView1=findViewById(R.id.onoff_status_text_2);
        ProgressBar onoffpg1=findViewById(R.id.onoff_pgbar_1);
        et1=findViewById(R.id.add_us_t3);
        no=et1.getText().toString();
        user2=text2.getText().toString();
        user3=text3.getText().toString();
        user4=text4.getText().toString();
        user5=text5.getText().toString();

      if(TextUtils.isEmpty(user2)){
          String message = "SPC,38,1,"+no;
          Log.i("added no",message);
          SmsManager smsManager = SmsManager.getDefault();
           smsManager.sendTextMessage(phoneNumber, null, message, null, null);
          Log.i("Test", "SMS sent!");
          onoffpg1.setVisibility(View.VISIBLE);
          textView1.setText("Command Sent, Please Wait...For 2 minutes");
      }
       else if(TextUtils.isEmpty(user3)){
            String message = "SPC,39,1,"+no;
            Log.i("added no",message);
            SmsManager smsManager = SmsManager.getDefault();
             smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.i("Test", "SMS sent!");
          onoffpg1.setVisibility(View.VISIBLE);
          textView1.setText("Command Sent, Please Wait...For 2 minutes");
        }
       else if(TextUtils.isEmpty(user4)){
            String message = "SPC,40,1,"+no;
            Log.i("added no",message);
            SmsManager smsManager = SmsManager.getDefault();
             smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.i("Test", "SMS sent!");
          onoffpg1.setVisibility(View.VISIBLE);
          textView1.setText("Command Sent, Please Wait...For 2 minutes");
        }
       else if(TextUtils.isEmpty(user5)){
            String message = "SPC,41,1,"+no;
            Log.i("added no",message);
            SmsManager smsManager = SmsManager.getDefault();
             smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.i("Test", "SMS sent!");
          onoffpg1.setVisibility(View.VISIBLE);
          textView1.setText("Command Sent, Please Wait...For 2 minutes");
        }
        else et1.setError("you reached maximum limit!!...");

       et1.getText().clear();
    }

    public void refresh(View view){
        String message = "SPC,92";
        SmsManager smsManager = SmsManager.getDefault();
         smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        Log.i("Test", "SMS sent!");
    }


    public void remove(View view){
        et2=findViewById(R.id.add_us_t1);
        user2=text2.getText().toString();
        user3=text3.getText().toString();
        user4=text4.getText().toString();
        user5=text5.getText().toString();
        String Rno=et2.getText().toString();
        ProgressBar onoffpg1=findViewById(R.id.onoff_pgbar1);
        TextView textView1=findViewById(R.id.onoff_status_text1);

       if(Rno.equals(user2)){
           String message = "SPC,38,0";
           SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
           Log.i("Test", "SMS sent!");
           onoffpg1.setVisibility(View.VISIBLE);
           textView1.setText("Command Sent, Please Wait...For 2 minutes");
       }
       else if(Rno.equals(user3)){
           String message = "SPC,39,0";
           SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
           Log.i("Test", "SMS sent!");
           onoffpg1.setVisibility(View.VISIBLE);
           textView1.setText("Command Sent, Please Wait...For 2 minutes");
       }
       else if(Rno.equals(user4)){
           String message = "SPC,40,0";
           SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
           Log.i("Test", "SMS sent!");
           onoffpg1.setVisibility(View.VISIBLE);
           textView1.setText("Command Sent, Please Wait...For 2 minutes");
       }
        else if(Rno.equals(user5)){
           String message = "SPC,41,0";
           SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
           Log.i("Test", "SMS sent!");
           onoffpg1.setVisibility(View.VISIBLE);
           textView1.setText("Command Sent, Please Wait...For 2 minutes");
       }
       else{
            et2.setError("please Enter valid number");
       }

       et2.getText().clear();
    }

    private final BroadcastReceiver addorremovereceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent1) {
            if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent1.getAction())) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent1)) {
                    String senderNum = smsMessage.getDisplayOriginatingAddress();
                   // Log.i("sender num", senderNum);
                    SMSBody1 += smsMessage.getMessageBody().toString();
                    Log.i("Received sms",SMSBody1);
                   String sms=SMSBody1;
                    String[] lines = SMSBody1.split("\\r?\\n");
                    if(sms.length()>100) {
                        if (lines[2].toString().contains("Spcmno")) {
                            text1.setText(lines[2].toString().substring(9));
                            for (int i = 3; i <= 6; i++) {
                                if (lines[i].toString().contains("Spcbno")) {
                                    text2.setText(lines[i].toString().substring(9));
                                } else if (lines[i].toString().contains("Spccno")) {
                                    text3.setText(lines[i].toString().substring(9));
                                } else if (lines[i].toString().contains("Spcdno")) {
                                    text4.setText(lines[i].toString().substring(9));
                                } else if (lines[i].toString().contains("Spceno")) {
                                    text5.setText(lines[i].toString().substring(9));
                                } else return;
                            }
                        }
                        else return;
                    }
                   else return;
                    SMSBody1 ="";

                }
            }
        }
    };

    @Override
   public void onPause(){
        super.onPause();
        unregisterReceiver(addorremovereceiver);
    }
}
