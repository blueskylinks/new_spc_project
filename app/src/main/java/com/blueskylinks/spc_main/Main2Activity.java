package com.blueskylinks.spc_main;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main2Activity extends AppCompatActivity {
    TextView tv;
    TextView tv1;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    TextView on_off_text;
    TextView Dates;
    TextView time;
    TextView tc3;
    TextView electricity;

    String s;
    String s2;
    String s3;
    String s4;
    String s5;
    String s6;
    String[] d;

    ImageView myimage;
    TextView textt1;
    TextView tv8;
    TextView text;
    SwipeRefreshLayout mySwipeRefreshLayout;
    ProgressDialog progressDialog;
    SharedPreferences Preferences1;
    //changes made
    String SMSBody1;
    boolean logged;
    String phoneNumber;
    Switch s1;
    public static SharedPreferences mt_status_pref;
    SharedPreferences v1_pref;
    SharedPreferences v2_pref;
    SharedPreferences v3_pref;
    SharedPreferences r_pref;
    SharedPreferences y_pref;
    SharedPreferences b_pref;
    SharedPreferences d_pref;
    SharedPreferences t_pref;
    SharedPreferences d_on_pref;
    SharedPreferences t_on_pref;
    SharedPreferences status_pref;
    public static SharedPreferences ele_d_pref;
    public static SharedPreferences ele_t_pref;
    public static SharedPreferences.Editor editor;
    public static int mot_st;
    public static int app_status;
    Boolean val1=false;
    TextView sync_date_time;
    SharedPreferences sp1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mt_status_pref = getSharedPreferences("status1", 0);
        v1_pref=getSharedPreferences("voltage1",0);
        v2_pref=getSharedPreferences("voltage2",0);
        v3_pref=getSharedPreferences("voltage3",0);
        r_pref=getSharedPreferences("R",0);
        y_pref=getSharedPreferences("Y",0);
        b_pref=getSharedPreferences("B",0);
        d_pref=getSharedPreferences("sync_date",0);
        t_pref=getSharedPreferences("sync_time",0);
        d_on_pref=getSharedPreferences("sync_date_on",0);
        t_on_pref=getSharedPreferences("sync_time_off",0);
        status_pref=getSharedPreferences("motor_status",0);
        ele_d_pref=getSharedPreferences("power ondate",0);
        ele_t_pref=getSharedPreferences("power ontime",0);
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
        Typeface typeface = Typeface.createFromAsset(getAssets(), "digital7.ttf");
        tv8=findViewById(R.id.textView8);
     tv = findViewById(R.id.textView11);
      tv1 = findViewById(R.id.textView12);
        tv2 = findViewById(R.id.textView31);

        s1=findViewById(R.id.switch7);
        sync_date_time=findViewById(R.id.textView);

        tv3 = findViewById(R.id.textView32);
        tv4 = findViewById(R.id.textView21);
        tv5 = findViewById(R.id.textView22);
        tv2.setTypeface(typeface);
        tv3.setTypeface(typeface);
        tv4.setTypeface(typeface);
        tv1.setTypeface(typeface);
        tv5.setTypeface(typeface);
        tv.setTypeface(typeface);
        Dates=findViewById(R.id.date);
        time=findViewById(R.id.time);
        mySwipeRefreshLayout=findViewById(R.id.swiperefresh);
        on_off_text=findViewById(R.id.on_off_text);
        myimage=findViewById(R.id.img1);
        tc3=findViewById(R.id.tv_motoron);
        electricity=findViewById(R.id.tv_electricity);
       sp1 =getSharedPreferences("login",0);
        logged=sp1.getBoolean("logged",false);
        phoneNumber=sp1.getString("subId","0");

        Preferences1 = getSharedPreferences("Checked1", 0);
        val1 = Preferences1.getBoolean("Checked1", val1); // retrieve the value of your key
        s1.setChecked(val1);

         if(app_status!=1){
            String message = "SPC,25";
            SmsManager smsManager = SmsManager.getDefault();
           smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.i("Test", "SMS sent!");
            progress();
            app_status =1;
        }

        if(logged){
            Log.i("","logged in");
        }
        else{
            Log.i("","logged out");
            Intent it=new Intent(Main2Activity.this,MainActivity.class);
            startActivity(it);
        }

     //page reload
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mySwipeRefreshLayout.setRefreshing(true);
                        Log.i("", "onRefresh called from SwipeRefreshLayout");
                        String message = "SPC,25";
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                        Log.i("Test", "SMS sent!");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mySwipeRefreshLayout.setRefreshing(false);
                            }
                        }, 5000);
                    }
                }
        );

        //laod voltage and Current on dashboard
        String status=status_pref.getString("motor_status","");
        on_off_text.setText(status);
        String volt1=v1_pref.getString("voltage1","0");
        tv.setText(volt1+"V");
        String volt2=v2_pref.getString("voltage2","0");
        tv4.setText(volt2+"V");
        String volt3=v3_pref.getString("voltage3","0");
        tv2.setText(volt3+"V");
        String c1=r_pref.getString("R","0");
        tv1.setText(c1+"A");
        String c2=y_pref.getString("Y","0");
        tv5.setText(c2+"A");
        String c3=b_pref.getString("B","0");
        tv3.setText(c3+"A");

        //last sync time and date
        String sync_date=d_pref.getString("sync_date","");
        String sync_time=t_pref.getString("sync_time","");
        String sync_on_date=d_pref.getString("sync_on_date","");
        String sync_on_time=t_pref.getString("sync_on_time","");
        sync_date_time.setText(sync_time+"  "+sync_date);

        tc3.setText(sync_on_time+"  "+sync_on_date);

        //suply on time
        String ele_date=ele_d_pref.getString("power ondate","");
        String ele_time=ele_t_pref.getString("power ontime","");
        electricity.setText(ele_date+"\t"+ele_time);

        //current date and time
        DateFormat df = new SimpleDateFormat("dd MM yyyy, HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        Log.i("Date &TIME",date.toString());
        String[] d = date.split(",");
        if(status=="ON"){time.setText("ON @ "+sync_on_date+"/n"+sync_on_time);}
        else {
            time.setText("ON @ "+sync_on_time+"\n"+sync_on_date);
            Dates.setText("OFF @ "+sync_time+"\n"+sync_date);}

        tv8.setText("Date:"+d[0]);
    }


    @Override
    protected void onResume(){
        super.onResume();
        SMSBody1="";
        editor = mt_status_pref.edit();
        app_status = mt_status_pref.getInt("a1",0);
        Log.i("aaaaa", Integer.toString(app_status));
        if(mot_st != mt_status_pref.getInt("m1",0)){
            editor.putInt("m1",mot_st);
            editor.apply();
        }

        final IntentFilter mIntentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(sms_notify_reciver, mIntentFilter);
        registerReceiver(sms_notify_reciver,mIntentFilter);
    }

    public void refresh(View view){
            String message = "SPC,25";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.i("Test", "SMS sent!");
            progress();
    }

    //Progress Dialog
    public void progress(){
        progressDialog = new ProgressDialog(Main2Activity.this);
        progressDialog.setMessage("Please Wait......"); // Setting Message
        progressDialog.setTitle("Loading!!!"); // Setting Title
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


    public void submit_func(View view){
        if(s1.isChecked()){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Are you sure want to switch on!.");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    String message = "SPC,36,1";
                    SmsManager smsManager = SmsManager.getDefault();
                       smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                    Log.i("Test", message);
                    Preferences1.edit().putBoolean("Checked1",true).apply();
                }
            });

            builder1.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            s1.setChecked(false);
                            Preferences1.edit().putBoolean("Checked1",false).apply();
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
        else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Are you sure want to switch off!.");
            builder1.setCancelable(true);
            builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    String message = "SPC,36,0";
                    SmsManager smsManager = SmsManager.getDefault();
                       smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                    Log.i("Test", message);
                    Preferences1.edit().putBoolean("Checked1", false).apply();
                }
            });

            builder1.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            s1.setChecked(true);
                            Preferences1.edit().putBoolean("Checked1", true).apply();
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
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
        Intent it3 = new Intent(Main2Activity.this, UsersActivity.class);
        //starting another activity..
        startActivity(it3);
    }

    public void manual(View view){
        //starting another activity..
        Intent it4 = new Intent(Main2Activity.this, ManualActivity.class);
        startActivity(it4);
    }
    public void logout(View view){
        logged=false;
        sp1.edit().putBoolean("logged", false).apply();
        Intent it=new Intent(Main2Activity.this,MainActivity.class);
        startActivity(it);

    }

    public void motor_on(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure,want to start Motor");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
         String message = "SPC,24";
         SmsManager smsManager = SmsManager.getDefault();
         smsManager.sendTextMessage(phoneNumber, null, message, null, null);
         myimage.setImageResource(R.drawable.display_green_circle);
            textt1.setText("ON");
            }
        });
        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
    }

    public void motor_off(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure,want to Turn Off Motor");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
       String message = "SPC,26";
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, null, null);
        myimage.setImageResource(R.drawable.display_red_circle);
        textt1.setText("OFF");
         }
         });
        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
    }

    //shared pref update
    public void motor_status_track(){
        v1_pref.edit().putString("voltage1",s).apply();
        v2_pref.edit().putString("voltage2",s2).apply();
        v3_pref.edit().putString("voltage3",s3).apply();
        r_pref.edit().putString("R",s4).apply();
        y_pref.edit().putString("Y",s5).apply();
        b_pref.edit().putString("B",s6).apply();
        d_on_pref.edit().putString("sync_date_on",d[0]).apply();
        t_on_pref.edit().putString("sync_time_off",d[1]).apply();

    }

    private final BroadcastReceiver sms_notify_reciver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent2) {
            if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent2.getAction())) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent2)) {
                    String senderNum = smsMessage.getDisplayOriginatingAddress();
                    Log.i("sender num",senderNum.substring(3));
                    Log.i("sender num",phoneNumber);
                    SMSBody1 += smsMessage.getMessageBody().toString();
                    if(phoneNumber.equals(senderNum.substring(3))) {
                        Log.i("length", String.valueOf(SMSBody1.length()));
                        Log.i("Received SMS:", SMSBody1);
                        String[] lines = SMSBody1.split("\\r?\\n");
                        int l = lines.length - 1;
                        if (SMSBody1.length() < 160) {
                            if (lines[1].toString().contains("on ")) {
                                on_off_text.setText("ON");
                                editor = mt_status_pref.edit();
                                editor.putInt("m1", 1);
                                editor.apply();
                                mot_st = 1;
                                if (lines[1].toString().contains("2 phase")) {
                                    s1.setChecked(false);
                                } else {
                                    s1.setChecked(true);
                                }
                                s = lines[2].toString().substring(4, 7);
                                s2 = lines[3].toString().substring(4, 7);
                                s3 = lines[4].toString().substring(4, 7);
                                tv.setText(s + "V");
                                tv4.setText(s2 + "V");
                                tv2.setText(s3 + "V");
                                s4 = lines[6].toString().substring(3);
                                s5 = lines[7].toString().substring(3);
                                s6 = lines[8].toString().substring(3);
                                tv1.setText(s4 + "A");
                                tv3.setText(s5 + "A");
                                tv5.setText(s6 + "A");
                                DateFormat df = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
                                String date = df.format(Calendar.getInstance().getTime());
                                Log.i("Date &TIME", date.toString());
                                d = date.split(",");
                                time.setText("ON @ " + d[1] + "/n" + d[0]);
                                sync_date_time.setText("@" + d[1] + "  " + d[0]);
                                tv8.setText("Date:" + d[0]);
                                status_pref.edit().putString("motor_status", "ON").apply();
                                motor_status_track();

                            } else if (lines[1].toString().contains("off")) {
                                on_off_text.setText("OFF");
                                editor = mt_status_pref.edit();
                                editor.putInt("m1", 0);
                                editor.apply();
                                mot_st = 0;
                                DateFormat df = new SimpleDateFormat("dd MM yyyy, HH:mm:ss");
                                String date = df.format(Calendar.getInstance().getTime());
                                Log.i("Date &TIME", date.toString());
                                d = date.split(",");
                                String sync_on_date = d_pref.getString("sync_on_date", "");
                                String sync_on_time = t_pref.getString("sync_on_time", "");
                                time.setText("ON @" + sync_on_date + "\n" + sync_on_time);
                                Dates.setText("OFF @" + d[1] + "\n" + d[0]);
                                sync_date_time.setText("@" + d[1] + "  " + d[0]);
                                tv8.setText("Date:" + d[0]);
                                s = "0.00";
                                s2 = "0.00";
                                s3 = "0.00";
                                tv.setText(s + "V");
                                tv4.setText(s2 + "V");
                                tv2.setText(s3 + "V");
                                s4 = lines[2].substring(8, 11);
                                s5 = lines[2].substring(8, 11);
                                s6 = lines[2].substring(8, 11);
                                tv1.setText(s4 + "A");
                                tv3.setText(s5 + "A");
                                tv5.setText(s6 + "A");
                                status_pref.edit().putString("motor_status", "OFF").apply();
                            }
                            else return;
                        }
                        //power Supply on and off
                        else if(l==2){
                            if(lines[0].toString().contains("on")){
                                electricity.setText(lines[1].substring(6)+"\t"+lines[2].substring(6));
                                ele_d_pref.edit().putString("power ondate",lines[1].substring(6));
                                ele_t_pref.edit().putString("power ontime",lines[2].substring(6));
                            }
                        }
                    }
                        SMSBody1 = "";
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
