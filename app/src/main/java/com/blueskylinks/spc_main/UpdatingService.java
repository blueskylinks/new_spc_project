package com.blueskylinks.spc_main;

/**
 * Created by omsai on 8/30/2018.
 */

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class UpdatingService extends Service  {
    Handler handler;
    String[] lines;
    String msgData = "";
    String cur_col[];
    int col_index=0;
    int col_date=0;
    int msg_no=0;
    String senderNum;
    String[] d;

    DatabaseHelper myDb;

    String SMSBody1;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        IntentFilter mIntentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(sms_notify_reciver, mIntentFilter);
        Toast.makeText(UpdatingService.this, "SPC Service!", Toast.LENGTH_LONG).show();
        myDb = new DatabaseHelper(this);

        handler = new Handler();

        Runnable r = new Runnable() {
            int n=0;
            public void run() {
                handler.postDelayed(this, 30000);
                n=n+1;
                if(n==50000){
                    n=0;
                }
            }
        };
        handler.postDelayed(r, 30000);

        return START_STICKY;


    }

    public class MyReceiver1 extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            String s1 = arg1.getStringExtra("D1");
            Log.i("BLE,,,,,,,", s1);
        }
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(sms_notify_reciver);
        Toast.makeText(UpdatingService.this, "service Destroyed......!", Toast.LENGTH_LONG).show();
        super.onDestroy();

    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    private final BroadcastReceiver sms_notify_reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent2) {
            if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent2.getAction())) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent2)) {
                    int end_index=smsMessage.getDisplayOriginatingAddress().length();
                    senderNum = smsMessage.getDisplayOriginatingAddress().substring(3,end_index);
                    Log.i("sender num...",senderNum);
                    SMSBody1 += smsMessage.getMessageBody().toString();
                    lines = SMSBody1.split("\\r?\\n");
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    r.play();
                    //setData();
                }
            }
        }
    };


    public void setData(){
        int l = lines.length;
        if(l>=13) {
            //String mot_data[]= new String[] {};
            String mot_data[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
            if (lines[1].toString().contains("on ")) {
                //on_off_text.setText("ON");
                mot_data[8]="ON";
            }
            if (lines[1].toString().contains("3 phase")) {
                mot_data[9]="3";
            } else {
                mot_data[9]="2";
            }

            Date d1 = new Date();
            Log.i("Date-D1",String.valueOf(d1.getTime()));
            mot_data[15]=String.valueOf(d1.getTime());

            mot_data[0] = senderNum;
            mot_data[1] = "";
            mot_data[2] = lines[2].toString().substring(4, 7);
            mot_data[3] = lines[3].toString().substring(4, 7);
            mot_data[4] = lines[4].toString().substring(4, 7);
            mot_data[5] = lines[6].toString().substring(3, 6);
            mot_data[6] = lines[7].toString().substring(3, 6);
            mot_data[7] = lines[8].toString().substring(3, 6);
            boolean isUpdated = myDb.updateData(mot_data, senderNum,1025);
            if (isUpdated == true)
                Toast.makeText(UpdatingService.this, "Data Updated", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(UpdatingService.this, "Data not Updated", Toast.LENGTH_SHORT).show();

        }

    }


}

