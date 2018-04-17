package com.blueskylinks.spc_main;

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
import android.telephony.SmsMessage;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UsersActivity extends AppCompatActivity {
    int count=0;
    SharedPreferences sharedPreferences1;
    String ms="00";
    String SMSBody1;
    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        final IntentFilter mIntentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(addorremovereceiver, mIntentFilter);
        registerReceiver(addorremovereceiver,mIntentFilter);

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

    //Add users
    public void Add_users(View view) {
        text1=findViewById(R.id.text1);
        Log.i("count",String.valueOf(count));
        if(count<4) {
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            final EditText et = new EditText(this);
            et.setInputType(InputType.TYPE_CLASS_NUMBER);
            et.setHint("mobile no:");

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(et);
            int maxLength = 10;
            et.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
            // set dialog message

            alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    ms = et.getText().toString();
                 //   String msg = "spc," + finalMss + ",1," + ms;
                   // Log.i("Test", msg);
                    ms = et.getText().toString();
                    //smsManager.sendTextMessage(phoneNumber, null, msg, null, null);
                }
            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }
        else {
            final AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(this);
            alertDialogBuilder1.setMessage(" You can Add only 5 numbers!");
            alertDialogBuilder1.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });
            AlertDialog alertDialog = alertDialogBuilder1.create();
            alertDialog.show();

        }
    }


    private final BroadcastReceiver addorremovereceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent1) {
            if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent1.getAction())) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent1)) {
                    String senderNum = smsMessage.getDisplayOriginatingAddress();
                   // Log.i("sender num", senderNum);
                    SMSBody1 += smsMessage.getMessageBody().toString();
                   String sms=SMSBody1;
                   if(sms.length()>=153) {
                       String[] lines = SMSBody1.split("\\r?\\n");
                       Log.i("test", lines[1].toString());

                    if(lines[2].toString().contains("Spcmno")) {
                        for (int i = 3; i < lines.length - 2; i++) {
                            count += 1;

                        }
                        Log.i("Count",String.valueOf(count));
                    }

                   }
                    SMSBody1 ="";

                }
            }
        }
    };


}
