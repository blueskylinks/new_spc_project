package com.blueskylinks.spc_main;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.RotateDrawable;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.blueskylinks.spc_main.Main2Activity.progressDialog;
import static com.blueskylinks.spc_main.Main2Activity.tv;
import static com.blueskylinks.spc_main.MainActivity.phoneNumber;

public class ON_OFFActivity extends AppCompatActivity {

    public static TextView textView1;
    public static TextView textView2;
    public static TextView mot_st_text1;
    public ProgressBar onoffpg1;
    RadioButton onoff_rb1;
    RadioButton onoff_rb2;
    Long diffMin;
    int count=0;
    String on;
    String off;
    Spinner spinner;
    Spinner spinner1;
    String selectedItem;
    String SMSBody1;
    TextView text;
    LinearLayout mainLayout;
    TextView tv;
    TextView tv2;
    ProgressBar pbar;
    TextView et1;
    TextView et2;
    TextView et3;
    TextView et11;
    TextView et12;
    TextView et13;
   String ftime;
   String ntime;
   String ftime1;
   String ntime1;
   String ftime2;
   String ntime2;
  ImageButton image;
    SimpleDateFormat format;
    Date d1 = null;
    Date d2 = null;
    Date date1 = null;
    Date date2 = null;
    Date date_1=null;
    Date date_2=null;
    Date ontime;
    Date offtime;
    SharedPreferences sharedPreferences;
  SharedPreferences.Editor editor;


    @Override
    protected void onResume(){
        super.onResume();
        SMSBody1="";
        final IntentFilter MIntentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        registerReceiver(toastOrNotificationCatcherReceiver, MIntentFilter);
        registerReceiver(toastOrNotificationCatcherReceiver,MIntentFilter);
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
        sharedPreferences=getSharedPreferences("mypref",0);
        editor= sharedPreferences.edit();


        spinner=findViewById(R.id.spinner1);
       et1=findViewById(R.id.tv1);
       et2=findViewById(R.id.tv2);
       et3=findViewById(R.id.tv3);
        et11=findViewById(R.id.tv4);
        et12=findViewById(R.id.tv5);
        et13=findViewById(R.id.tv6);
        tv=findViewById(R.id.textView1);
        text=findViewById(R.id.onoff_status_text_1);
        tv2=findViewById(R.id.textView2);
        pbar=findViewById(R.id.pbar);
        mainLayout=findViewById(R.id.spinnerLayout);
        spinner1 = new Spinner(this);
        image=findViewById(R.id.image);
        format = new SimpleDateFormat("HH:mm");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // selectedItem = parent.getItemAtPosition(position).toString();
                Log.i("position ", String.valueOf(position));
                switch (position){
                    case 0:
                        mainLayout.removeView(spinner1);
                        setRTC();
                        break;
                    default:
                        mainLayout.removeView(spinner1);
                        break;
                }
            }
            // to close the onItemSelected
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItem = parent.getItemAtPosition(position).toString();
                Log.i("position ", selectedItem);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String text1=sharedPreferences.getString("RTC1",null);
        et1.setText(text1);
        String text2=sharedPreferences.getString("RTC_1",null);
        et11.setText(text2);

        String text3=sharedPreferences.getString("RTC2",null);
        et2.setText(text3);
        String text4=sharedPreferences.getString("RTC_2",null);
        et12.setText(text4);

        String text5=sharedPreferences.getString("RTC3",null);
        et3.setText(text5);
        String text6=sharedPreferences.getString("RTC_3",null);
        et13.setText(text6);
    }
//-----------------------------------------------------------------------------------------|
    //RTC1 Settings
    public void ontime(View v) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(ON_OFFActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String Hour;
                String Minute;
                if(selectedHour>=10) Hour=String.valueOf(selectedHour);
                else {
                   Hour= ("0"+String.valueOf(selectedHour));
                    Log.i("hour",Hour);
                }
                if(selectedMinute>=10) Minute=String.valueOf(selectedMinute);
                else{
                  Minute=("0"+String.valueOf(selectedMinute));
                    Log.i("minute",Minute);
                }

                et1.setText(Hour + ":" + Minute);
                ntime = et1.getText().toString();
                editor.putString("RTC1",ntime);
                editor.commit();
                String h=et1.getText().toString().substring(0,2);
                String m=et1.getText().toString().substring(3);
                String message="SPC,28,"+h+","+m;
                Log.i("message",message);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }


    public void offtime(View v) {
        if (!et1.getText().toString().equals("")) {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(ON_OFFActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String Hour;
                    String Minute;
                    if(selectedHour>=10) Hour=String.valueOf(selectedHour);
                    else {
                        Hour= ("0"+String.valueOf(selectedHour));
                        Log.i("hour",Hour);
                    }
                    if(selectedMinute>=10) Minute=String.valueOf(selectedMinute);
                    else{
                        Minute=("0"+String.valueOf(selectedMinute));
                        Log.i("minute",Minute);
                    }
                    et11.setText(Hour + ":" + Minute);
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");

                    ftime = et11.getText().toString();
                    ntime = et1.getText().toString();
                    try {
                        d1 = format.parse(ntime);
                        d2 = format.parse(ftime);
                        long diff = d2.getTime() - d1.getTime();
                        diffMin = diff / (60 * 1000);
                        Log.i("difference", String.valueOf(diffMin));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (diffMin >= 30.0) {
                        String h = ftime.substring(0, 2);
                        String m = ftime.substring(3);
                        String message = "SPC,29," + h + "," + m;
                        editor.putString("RTC_1",ftime);
                        editor.commit();
                        Log.i("message", message);
                    } else {
                        Toast.makeText(ON_OFFActivity.this, "off time must be 30 mins greater than ontime", Toast.LENGTH_SHORT).show();
                        et11.setText("");
                        et11.setError("please choose off time!..");
                    }
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }
        else Toast.makeText(this, "Please set onTime..", Toast.LENGTH_SHORT).show();
    }
    //------------------------------------------------------------------------------------|
    //RTC2 Settings
    public void ontime1(View v) {
        if (!et11.getText().toString().equals("")) {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(ON_OFFActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String Hour;
                    String Minute;
                    if (selectedHour >= 10) Hour = String.valueOf(selectedHour);
                    else {
                        Hour = ("0" + String.valueOf(selectedHour));
                        Log.i("hour", Hour);
                    }
                    if (selectedMinute >= 10) Minute = String.valueOf(selectedMinute);
                    else {
                        Minute = ("0" + String.valueOf(selectedMinute));
                        Log.i("minute", Minute);
                    }
                    et2.setText(Hour + ":" + Minute);
                    try {
                        ontime = format.parse(et2.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (ontime.after(d2)) {
                        ntime1 = et2.getText().toString();
                        String h = et2.getText().toString().substring(0, 2);
                        String m = et2.getText().toString().substring(3);
                        String message = "SPC,30," + h + "," + m;
                        editor.putString("RTC2", ntime1);
                        editor.commit();
                        Log.i("message", message);
                    } else {
                        et2.setText("");
                        Toast.makeText(ON_OFFActivity.this, "RTC2 must be out of RTC1 range..", Toast.LENGTH_LONG).show();
                    }
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }
        else Toast.makeText(this, "complete RTC1 Settings..", Toast.LENGTH_SHORT).show();
    }

    public void offtime1(View v) {
        if (!et2.getText().toString().equals("")) {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(ON_OFFActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String Hour;
                    String Minute;
                    if(selectedHour>=10) Hour=String.valueOf(selectedHour);
                    else {
                        Hour= ("0"+String.valueOf(selectedHour));
                        Log.i("hour",Hour);
                    }
                    if(selectedMinute>=10) Minute=String.valueOf(selectedMinute);
                    else{
                        Minute=("0"+String.valueOf(selectedMinute));
                        Log.i("minute",Minute);
                    }
                    et12.setText(Hour + ":" + Minute);
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");

                    ftime1 = et12.getText().toString();
                    ntime1 = et2.getText().toString();
                    try {
                        date1 = format.parse(ntime1);
                        date2 = format.parse(ftime1);
                        offtime = format.parse(et12.getText().toString());
                        long diff = date2.getTime() - date1.getTime();
                        diffMin = diff / (60 * 1000);
                        Log.i("difference", String.valueOf(diffMin));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (diffMin >= 30.0) {
                        if(offtime.after(d2)){
                            String h = ftime1.substring(0, 2);
                            String m = ftime1.substring(3);
                            String message = "SPC,31," + h + "," + m;
                            editor.putString("RTC_2",ftime1);
                            editor.commit();
                            Log.i("message", message);
                        } else {
                            et12.setText("");
                            Toast.makeText(ON_OFFActivity.this, "Unable to set off time..", Toast.LENGTH_LONG).show();
                        }
                    }
                 else {
                        Toast.makeText(ON_OFFActivity.this, "off time must be 30 mins greater than ontime", Toast.LENGTH_SHORT).show();
                        et12.setText("");
                    }
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }
        else Toast.makeText(this, "Please set onTime...", Toast.LENGTH_SHORT).show();
    }

    //------------------------------------------------------------------------------------|
    //RTC3 Settings
    public void ontime2(View v) {
        if (!et12.getText().toString().equals("")) {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(ON_OFFActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String Hour;
                    String Minute;
                    if (selectedHour >= 10) Hour = String.valueOf(selectedHour);
                    else {
                        Hour = ("0" + String.valueOf(selectedHour));
                        Log.i("hour", Hour);
                    }
                    if (selectedMinute >= 10) Minute = String.valueOf(selectedMinute);
                    else {
                        Minute = ("0" + String.valueOf(selectedMinute));
                        Log.i("minute", Minute);
                    }
                    et3.setText(Hour + ":" + Minute);
                    try {
                        ontime = format.parse(et3.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (ontime.after(date2)) {
                        ntime2 = et3.getText().toString();
                        String h = et3.getText().toString().substring(0, 2);
                        String m = et3.getText().toString().substring(3);
                        String message = "SPC,32," + h + "," + m;
                        editor.putString("RTC3", ntime2);
                        editor.commit();
                        Log.i("message", message);
                    } else {
                        et3.setText("");
                        Toast.makeText(ON_OFFActivity.this, "RTC2 must be out of RTC1 range..", Toast.LENGTH_LONG).show();
                    }
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }
        else Toast.makeText(this, "complete RTC3 Settings..", Toast.LENGTH_SHORT).show();
    }

    public void offtime2(View v) {
        if (!et3.getText().toString().equals("")) {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(ON_OFFActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String Hour;
                    String Minute;
                    if(selectedHour>=10) Hour=String.valueOf(selectedHour);
                    else {
                        Hour= ("0"+String.valueOf(selectedHour));
                        Log.i("hour",Hour);
                    }
                    if(selectedMinute>=10) Minute=String.valueOf(selectedMinute);
                    else{
                        Minute=("0"+String.valueOf(selectedMinute));
                        Log.i("minute",Minute);
                    }
                    et13.setText(Hour + ":" + Minute);
                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");

                    ftime2 = et13.getText().toString();
                    ntime2 = et3.getText().toString();
                    try {
                        date_1= format.parse(ntime2);
                        date_2 = format.parse(ftime2);
                        offtime = format.parse(et13.getText().toString());
                        long diff = date_2.getTime() - date_1.getTime();
                        diffMin = diff / (60 * 1000);
                        Log.i("difference", String.valueOf(diffMin));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (diffMin >= 30.0) {
                        if(offtime.after(date2)){
                            String h = ftime2.substring(0, 2);
                            String m = ftime2.substring(3);
                            String message = "SPC,33," + h + "," + m;
                            editor.putString("RTC_3",ftime2);
                            editor.commit();
                            Log.i("message", message);
                        } else {
                            et13.setText("");
                            Toast.makeText(ON_OFFActivity.this, "Unable to set off time..", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(ON_OFFActivity.this, "off time must be 30 mins greater than ontime", Toast.LENGTH_SHORT).show();
                        et13.setText("");
                    }

                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }
        else Toast.makeText(this, "Please set onTime...", Toast.LENGTH_SHORT).show();
    }


    public void set_function(View view){
        if(selectedItem.equals("RTC function on")){
            String message = "SPC,27,1";
            SmsManager smsManager = SmsManager.getDefault();
            //smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.i("Test", message);
            pbar.setVisibility(View.VISIBLE);
            text.setText("Command Sent, Please Wait...For 2 minutes");
        }
        else if(selectedItem.equals("RTC function off")){
            String message = "SPC,27,0";
            SmsManager smsManager = SmsManager.getDefault();
            // smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.i("Test", message);
            pbar.setVisibility(View.VISIBLE);
            text.setText("Command Sent, Please Wait...For 2 minutes");
        }
    }

    public void setRTC(){
        ArrayList<String> RTC = new ArrayList<String>();
        RTC.add("RTC function on");
        RTC.add("RTC function off");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,RTC);
        spinner1.setAdapter(spinnerArrayAdapter);
        mainLayout.addView(spinner1);
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

    public void Users(View view){
        //starting another activity..
        Intent it3 = new Intent(ON_OFFActivity.this, UsersActivity.class);
        startActivity(it3);
    }

    public void manual(View view){
        //starting another activity..
        Intent it4 = new Intent(ON_OFFActivity.this, ManualActivity.class);
        startActivity(it4);
    }


    private final BroadcastReceiver toastOrNotificationCatcherReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION.equals(intent.getAction())) {
                for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                    String senderNum = smsMessage.getDisplayOriginatingAddress();
                    Log.i("sender num", senderNum);
                    SMSBody1 = smsMessage.getMessageBody().toString();
                    String[] lines = SMSBody1.split("\\r?\\n");
                    Log.i("received sms",SMSBody1);
                    if(lines[2].toString().contains("on")){
                        onoffpg1.setVisibility(View.INVISIBLE);
                        textView1.setText(lines[4]);
                        mot_st_text1.setText("ON");
                    }
                    else if(lines[2].toString().contains("off")) {
                        onoffpg1.setVisibility(View.INVISIBLE);
                        textView1.setText(lines[2]+lines[3]);
                        mot_st_text1.setText("OFF");
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
        unregisterReceiver(toastOrNotificationCatcherReceiver);
    }
}
