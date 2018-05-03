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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.blueskylinks.spc_main.Main2Activity.progressDialog;
import static com.blueskylinks.spc_main.Main2Activity.tv;
import static com.blueskylinks.spc_main.MainActivity.phoneNumber;

public class ON_OFFActivity extends AppCompatActivity {

    public static TextView textView1;
    public static TextView textView2;
    public static TextView mot_st_text1;
    public ProgressBar onoffpg1;
    String phoneNumber1;
    RadioButton onoff_rb1;
    RadioButton onoff_rb2;
  //  String phoneNumber = "9880760642";
    Spinner spinner;
    Spinner spinner1;
    String selectedItem;
    String SMSBody1;
    TextView text;
    LinearLayout mainLayout;
    TextView tv;
    TextView tv2;
    ProgressBar pbar;
    EditText et1;
    EditText et2;
    String s1;
    String s2;
    int number;

    public void getSmsDetails(String phoneNumber, String SMSBody) {
        phoneNumber1 = phoneNumber;
        SMSBody1 = SMSBody;
    }

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

        spinner=findViewById(R.id.spinner1);
        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        tv=findViewById(R.id.textView1);
        text=findViewById(R.id.onoff_status_text_1);
        tv2=findViewById(R.id.textView2);
        pbar=findViewById(R.id.pbar);
        mainLayout=findViewById(R.id.spinnerLayout);
        spinner1 = new Spinner(this);

        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

                String added_number = et2.getText().toString();
                if (added_number.length() != 0) {
                    number  = Integer.parseInt(added_number);

                    if (number > 59)
                        Toast.makeText(ON_OFFActivity.this, "Not more than 59", Toast.LENGTH_SHORT).show();
                    // et2.setError("Enter mins less than 60");
                    et2.setMaxEms(1);
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // selectedItem = parent.getItemAtPosition(position).toString();
                Log.i("position ", String.valueOf(position));
                switch (position){
                    case 0:
                        mainLayout.removeView(spinner1);
                        et1.setVisibility(View.INVISIBLE);
                        et2.setVisibility(View.INVISIBLE);
                        setRTC();
                        break;
                    case 1:
                        mainLayout.removeView(spinner1);
                        et1.setVisibility(View.VISIBLE);
                        et2.setVisibility(View.VISIBLE);
                        setRTC1();
                        break;
                    case 2:
                        mainLayout.removeView(spinner1);
                        et1.setVisibility(View.VISIBLE);
                        et2.setVisibility(View.VISIBLE);
                        setRTC2();
                        break;
                    case 3:
                        mainLayout.removeView(spinner1);
                        et1.setVisibility(View.VISIBLE);
                        et2.setVisibility(View.VISIBLE);
                        setRTC3();
                        break;
                    default:
                        mainLayout.removeView(spinner1);
                        et1.setVisibility(View.INVISIBLE);
                        et2.setVisibility(View.INVISIBLE);
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
        else if(selectedItem.equals("RTC_1 on")){
            s1=et1.getText().toString();
            s2=et2.getText().toString();
            if(TextUtils.isEmpty(s1)||TextUtils.isEmpty(s2)|| number>59 ){
                if(TextUtils.isEmpty(s1)) et1.setError("please enter time!!");
                else et2.setError("please enter time!!");
            }else{
                String message = "SPC,28,"+s1+","+s2;
                SmsManager smsManager = SmsManager.getDefault();
                // smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                Log.i("Test", message);
                pbar.setVisibility(View.VISIBLE);
                text.setText("Command Sent, Please Wait...For 2 minutes");
            }
            et1.getText().clear();
            et2.getText().clear();
        }
        else if(selectedItem.equals("RTC_1 off")){
            s1=et1.getText().toString();
            s2=et2.getText().toString();
            if(TextUtils.isEmpty(s1)||TextUtils.isEmpty(s2) || number>59){
                if(TextUtils.isEmpty(s1)) et1.setError("please enter time!!");
                else et2.setError("please enter time!!");
            }else{
                String message = "SPC,29,"+s1+","+s2;
                SmsManager smsManager = SmsManager.getDefault();
                // smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                Log.i("Test", message);
                pbar.setVisibility(View.VISIBLE);
                text.setText("Command Sent, Please Wait...For 2 minutes");
            } et1.getText().clear();
            et2.getText().clear();
        }
        else if(selectedItem.equals("RTC_2 on")){
            s1=et1.getText().toString();
            s2=et2.getText().toString();
            if(TextUtils.isEmpty(s1)||TextUtils.isEmpty(s2)||number>59){
                if(TextUtils.isEmpty(s1)) et1.setError("please enter time!!");
                else et2.setError("please enter time!!");
            }else{
                String message = "SPC,30,"+s1+","+s2;
                SmsManager smsManager = SmsManager.getDefault();
                // smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                Log.i("Test", message);
                pbar.setVisibility(View.VISIBLE);
                text.setText("Command Sent, Please Wait...For 2 minutes");
            }  et1.getText().clear();
            et2.getText().clear();
        }
        else if(selectedItem.equals("RTC_2 off")){
            s1=et1.getText().toString();
            s2=et2.getText().toString();
            if(TextUtils.isEmpty(s1)||TextUtils.isEmpty(s2)||number>59){
                if(TextUtils.isEmpty(s1)) et1.setError("please enter time!!");
                else et2.setError("please enter time!!");
            }else{
                String message = "SPC,31,"+s1+","+s2;
                SmsManager smsManager = SmsManager.getDefault();
                // smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                Log.i("Test", message);
                pbar.setVisibility(View.VISIBLE);
                text.setText("Command Sent, Please Wait...For 2 minutes");
            }et1.getText().clear();
            et2.getText().clear();
        }
        else if(selectedItem.equals("RTC_3 on")){
            s1=et1.getText().toString();
            s2=et2.getText().toString();
            if(TextUtils.isEmpty(s1)||TextUtils.isEmpty(s2)||number>59){
                if(TextUtils.isEmpty(s1)) et1.setError("please enter time!!");
                else et2.setError("please enter time!!");
            }else{
                String message = "SPC,32,"+s1+","+s2;
                SmsManager smsManager = SmsManager.getDefault();
                // smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                Log.i("Test", message);
                pbar.setVisibility(View.VISIBLE);
                text.setText("Command Sent, Please Wait...For 2 minutes");
            }et1.getText().clear();
            et2.getText().clear();
        }
        else if(selectedItem.equals("RTC_3 off")){
            s1=et1.getText().toString();
            s2=et2.getText().toString();
            if(TextUtils.isEmpty(s1)||TextUtils.isEmpty(s2)||number>59){
                if(TextUtils.isEmpty(s1)) et1.setError("please enter time!!");
                else et2.setError("please enter time!!");
            }else{
                String message = "SPC,33,"+s1+","+s2;
                SmsManager smsManager = SmsManager.getDefault();
                // smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                Log.i("Test", message);
                pbar.setVisibility(View.VISIBLE);
                text.setText("Command Sent, Please Wait...For 2 minutes");
            } et1.getText().clear();
            et2.getText().clear();
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

    public void setRTC1(){
        ArrayList<String> RTC_1 = new ArrayList<String>();
        RTC_1.add("RTC_1 on");
        RTC_1.add("RTC_1 off");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,RTC_1);
        spinner1.setAdapter(spinnerArrayAdapter);
        mainLayout.addView(spinner1);
    }
    public void setRTC2(){
        ArrayList<String> RTC_2 = new ArrayList<String>();
        RTC_2.add("RTC_2 on");
        RTC_2.add("RTC_2 off");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,RTC_2);
        spinner1.setAdapter(spinnerArrayAdapter);
        mainLayout.addView(spinner1);
    }
    public void setRTC3(){
        ArrayList<String> RTC_3 = new ArrayList<String>();
        RTC_3.add("RTC_3 on");
        RTC_3.add("RTC_3 off");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,RTC_3);
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
