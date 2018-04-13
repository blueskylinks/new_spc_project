package com.blueskylinks.spc_main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import static com.blueskylinks.spc_main.Main2Activity.b;
import static com.blueskylinks.spc_main.Main2Activity.tv;
import static com.blueskylinks.spc_main.Main2Activity.tv1;
import static com.blueskylinks.spc_main.Main2Activity.tv2;
import static com.blueskylinks.spc_main.Main2Activity.tv3;
import static com.blueskylinks.spc_main.Main2Activity.tv4;
import static com.blueskylinks.spc_main.ON_OFFActivity.textView1;
import static com.blueskylinks.spc_main.ON_OFFActivity.textView2;

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
                int l=lines.length-1;
                Log.i("test",String.valueOf(l));
                Log.i("test",lines[l]);
                if(lines[0].equals("Power supply is off")){
                    textView1.setText("Power supply is OFF");
                }
                else textView1.setText("Power supply is ON");
                textView2.setText(lines[l]);
            }
        }
    }
}



