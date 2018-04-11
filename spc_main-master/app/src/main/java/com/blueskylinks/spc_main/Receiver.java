package com.blueskylinks.spc_main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import static com.blueskylinks.spc_main.Main2Activity.tv;
import static com.blueskylinks.spc_main.Main2Activity.tv1;
import static com.blueskylinks.spc_main.Main2Activity.tv2;
import static com.blueskylinks.spc_main.Main2Activity.tv3;
import static com.blueskylinks.spc_main.Main2Activity.tv4;

/**
 * Created by omsai on 4/3/2018.
 */

public class Receiver extends BroadcastReceiver {
    public String messageBody;
    public static String newline = System.getProperty("line.separator");
    @Override


    public void onReceive(Context context, Intent intent) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                String senderNum = smsMessage.getDisplayOriginatingAddress();
                Log.i("sender num", senderNum);
                messageBody += smsMessage.getMessageBody().toString();
                String[] lines = messageBody.split("\\r?\\n");
                String status=lines[1];
                if(status.equals("Motor is on in 3 phase mode")) {
                    tv.setText("ON");
                    tv1.setText("3 phase");
                }
               String value1=lines[2];
                tv2.setText(value1.substring(4));
                String value2=lines[3];
                tv3.setText(value2.substring(4));
               String value3=lines[4];
                tv4.setText(value3.substring(4));
               /* String current=messageBody.substring(98,106);
                Log.i("current",current);
              // tv5.setText(current);
                String signal=messageBody.substring(122,125);
                Log.i("signal",signal);*/
              // textView6.setText(signal);
                //messageBody = smsMessage.getMessageBody();
                Log.i("Message received:", messageBody);
                if (senderNum.equals("+919663261329")) {
                    if (Main2Activity.progressDialog.isShowing()) {
                        Log.i("Test", "progress is runing");
                        Main2Activity.progressDialog.dismiss();
                    }
                    Toast.makeText(context, "Yes! SMS Receved.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}



