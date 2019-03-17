package com.blueskylinks.spc_main;

import android.Manifest;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.provider.Telephony;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Main2Activity extends AppCompatActivity {

    DatabaseHelper myDb;

    TextView ph_id_tx;
    TextView tv_var_v1;
    TextView tv_var_v2;
    TextView tv_var_v3;
    TextView tv_var_c1;
    TextView tv_var_c2;
    TextView tv_var_c3;
    TextView on_off_text;
    TextView Dates;
    TextView time;
    TextView tc3;
    TextView tv_var_phase;
    TextView electricity;
    String[] lines;
    String msgData = "";
    String cur_col[];
    int col_index=0;
    int col_date=0;
    int msg_no=0;
    String senderNum;
    String smsDate;

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
    public String phoneNumber;
    Switch s1;
    Boolean val1=false;
    TextView sync_date_time;
    SharedPreferences sp1;
    GregorianCalendar calendar;
    Boolean sms_per;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sms_per=false;
        smsDate="";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_SMS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_SMS},
                        101);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

        } else {
            sms_per=true;
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        102);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }

        } else {
            // Permission has already been granted
        }

        Typeface typeface = Typeface.createFromAsset(getAssets(), "digital7.ttf");

        myDb = new DatabaseHelper(this);
        mySwipeRefreshLayout=findViewById(R.id.swiperefresh);


        ph_id_tx=findViewById(R.id.ph_idtext);
        tv8=findViewById(R.id.textView8);
        tv_var_v1 = findViewById(R.id.tv_v1);
        tv_var_v2 = findViewById(R.id.tv_v2);
        tv_var_v3 = findViewById(R.id.tv_v3);

        s1=findViewById(R.id.switch7);
        sync_date_time=findViewById(R.id.tv_sync_date);

        tv_var_c1=findViewById(R.id.tv_c1);
        tv_var_c2=findViewById(R.id.tv_c2);
        tv_var_c3=findViewById(R.id.tv_c3);
        tv_var_phase=findViewById(R.id.tx_phase);
        
        tv_var_v1.setTypeface(typeface);
        tv_var_v2.setTypeface(typeface);
        tv_var_v3.setTypeface(typeface);
        tv_var_c1.setTypeface(typeface);
        tv_var_c2.setTypeface(typeface);
        tv_var_c3.setTypeface(typeface);

        Dates=findViewById(R.id.date);
        time=findViewById(R.id.time);
        on_off_text=findViewById(R.id.on_off_text);
        myimage=findViewById(R.id.img1);
        tc3=findViewById(R.id.tv_motoron);
        electricity=findViewById(R.id.tv_electricity);
        sp1=getSharedPreferences("login",0);
        logged=sp1.getBoolean("logged",false);
        phoneNumber=sp1.getString("subId","0");
        ph_id_tx.setText(phoneNumber);

        calendar = (GregorianCalendar) Calendar.getInstance();
        Intent myIntent = new Intent(Main2Activity.this, UpdatingService.class);
        if(!isMyServiceRunning(UpdatingService.class))
            startService(myIntent);
        else
            Log.i("T::","service is already running...");

        get_lines();

        if(logged){
            Log.i("","logged in");
        }
        else{
            Log.i("","logged out");
            Intent it=new Intent(Main2Activity.this,MainActivity.class);
            startActivity(it);
        }

    }

    private boolean isMyServiceRunning(Class <?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if(serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("TTT", "isMyServiceRunning? " + true + "");
                return true;
            }
        }
        Log.i("TTT", "isMyServiceRunning? " + false + "");
        return false;
    }



    @Override
    protected void onResume(){
        super.onResume();
        final IntentFilter mIntentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(sms_notify_reciver, mIntentFilter);
        get_lines();
        get_data(phoneNumber);
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
                    Thread.sleep(2000);
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


    private final BroadcastReceiver sms_notify_reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent2) {
            if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent2.getAction())) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent2)) {
                    senderNum = smsMessage.getDisplayOriginatingAddress();
                    Log.i("sender num...",senderNum.substring(3));
                    SMSBody1 += smsMessage.getMessageBody().toString();
                    lines = SMSBody1.split("\\r?\\n");
                    //setData();
                    //get_data(senderNum);
                }
            }
        }
    };

    public void get_data(String ph_no){
        Cursor res = myDb.getAllData(ph_no);
        if(res.getCount() == 0) {
            Toast.makeText(Main2Activity.this,"Nothing found",Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i("Rec_Count",String.valueOf(res.getCount()));
        while (res.moveToNext()) {
            tv_var_v1.setText(res.getString(2)+" v");
            tv_var_v2.setText(res.getString(3)+" v");
            tv_var_v3.setText(res.getString(4)+" v");
            tv_var_c1.setText(res.getString(5)+" a");
            tv_var_c2.setText(res.getString(6)+" a");
            tv_var_c3.setText(res.getString(7)+" a");
            on_off_text.setText(res.getString(8));
            String temp_s=res.getString(9);
            if(temp_s.contains("3"))
                tv_var_phase.setText("3 Phase");
            else
                tv_var_phase.setText("2 Phase");
            if(!smsDate.isEmpty())
            sync_date_time.setText(smsDate.substring(0, 16)+"  ");
            Toast.makeText(Main2Activity.this, "Phase...."+ temp_s, Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onStop(){
        super.onStop();
        unregisterReceiver(sms_notify_reciver);
    }

    public void get_lines(){
        if(sms_per){
            long date = new Date(System.currentTimeMillis() - 2L * 24 * 3600 * 1000).getTime();
            Log.i("D",String.valueOf(phoneNumber));
            Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null,"date" + ">?"+"AND address="+"'+91"+ phoneNumber+"'",new String[]{""+date},"date ASC");
            //Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null, null, null, null);
            msg_no=cursor.getCount();
            col_index=cursor.getColumnIndex("body");
            col_date=cursor.getColumnIndex("date");
            String col_date1="";
            String col_date2="";
            Cursor res_getdate = myDb.get_date(phoneNumber);
            Log.i("Length",String.valueOf(cursor.getCount()) + "     "+String.valueOf(res_getdate.getCount()));

            while(res_getdate.moveToNext()) { // must check the result to prevent exception
                col_date2 = res_getdate.getString(0);
                Log.i("DDDDD",col_date1);
            }

            Log.i("Length",String.valueOf(col_date));

            while(cursor.moveToNext()) { // must check the result to prevent exception
                col_date1 = cursor.getString(col_date);
                Log.i("TTTTT",col_date1);
                Log.i("DD",cursor.getString(col_index));
                if(col_date2.isEmpty()){
                    col_date2="1552066206941";
                }
                set_d(col_date1,col_date2,cursor.getString(col_index));

            }

        }

    }


    public void set_d(String msg_date, String rec_date, String body_data){
        Long timestamp_msg;
        Long timestamp_rec;
        Calendar calendar = Calendar.getInstance();
        lines = body_data.split("\\r?\\n");
        timestamp_msg=Long.valueOf(msg_date);
        timestamp_rec=Long.valueOf(rec_date);
        calendar.setTimeInMillis(timestamp_msg);
        Date finaldate = calendar.getTime();
        smsDate = finaldate.toString();
        if(lines.length==13) {
            if (timestamp_rec < timestamp_msg) {
                Log.i("Msg Date", msg_date);
                Log.i("Rec Date", rec_date);
                String mot_data[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
                if (lines[1].toString().contains("on ")) {
                    on_off_text.setText("ON");
                    mot_data[8] = "ON";
                }
                if (lines[1].toString().contains("3 phase")) {
                    mot_data[9] = "3";
                } else {
                    mot_data[9] = "2";
                }
                mot_data[15] = smsDate.substring(0, 16);

                mot_data[0] = phoneNumber;
                mot_data[1] = "";
                mot_data[2] = lines[2].toString().substring(4, 7);
                mot_data[3] = lines[3].toString().substring(4, 7);
                mot_data[4] = lines[4].toString().substring(4, 7);
                mot_data[5] = lines[6].toString().substring(3, 6);
                mot_data[6] = lines[7].toString().substring(3, 6);
                mot_data[7] = lines[8].toString().substring(3, 6);
                boolean isUpdated = myDb.updateData(mot_data, phoneNumber, 1025);
                if (isUpdated == true)
                    Toast.makeText(Main2Activity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(Main2Activity.this, "Data not Updated", Toast.LENGTH_SHORT).show();
            }
        }

        if(lines.length==6) {
                if (timestamp_rec < timestamp_msg) {
                    Log.i("Msg Date", msg_date);
                    Log.i("Rec Date", rec_date);
                    String mot_data[] = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
                    if (lines[1].toString().contains("off ")) {
                        on_off_text.setText("OFF");
                        mot_data[8] = "OFF";
                    }
                    if (lines[1].toString().contains("3 phase")) {
                        mot_data[9] = "3";
                    } else {
                        mot_data[9] = "2";
                    }

                    mot_data[0] = phoneNumber;
                    boolean isUpdated = myDb.updateData(mot_data, phoneNumber, 1028);
                    if (isUpdated == true)
                        Toast.makeText(Main2Activity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(Main2Activity.this, "Data not Updated", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    sms_per=true;

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    /*

    //Extra Code.......

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

            DateFormat df = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
            String date = df.format(Calendar.getInstance().getTime());
            Log.i("Date &TIME", date.toString());
            d = date.split(",");
            time.setText("ON @ " + d[1] + "/n" + d[0]);
            mot_data[15]="@" + d[1] + "  " + d[0];

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
                Toast.makeText(Main2Activity.this, "Data Updated", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(Main2Activity.this, "Data not Updated", Toast.LENGTH_SHORT).show();

        }

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

    */
}



