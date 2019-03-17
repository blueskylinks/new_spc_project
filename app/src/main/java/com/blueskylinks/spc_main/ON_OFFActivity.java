package com.blueskylinks.spc_main;

import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
//import static com.blueskylinks.spc_main.Main2Activity.mot_st;


public class ON_OFFActivity extends AppCompatActivity {

    String[] lines;
    DatabaseHelper myDb;

    public static TextView textView1;
    public static TextView textView2;
    public static TextView mot_st_text1;
    public int mot_st;
    RadioButton onoff_rb1;
    RadioButton onoff_rb2;
    Long diffMin;
    Long diffMin1;
    String message1;
    String message2;
    String phoneNumber;
    String message3;
    String message4;

    String message5;
    String message6;
    String on;
    Spinner spinner;
    Spinner spinner1;
    String selectedItem;
    String SMSBody1;
    TextView text;
    LinearLayout mainLayout;
    TextView textv;
    TextView tv2;
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
    SharedPreferences sharedPreferences2;
    SharedPreferences.Editor editor2;
    TextView remove1;
    TextView remove2;
    TextView remove3;
    String Subno;
    boolean value1 = true;
    boolean value2 = true;
    String ctime;
    List<String> stringlist;
    ArrayAdapter<String> arrayadapter;
    Spinner SPINNER;
    String rtc_status;


    @Override
    protected void onResume(){
        super.onResume();
        SMSBody1="";
        if(mot_st==1){
            onoff_rb1.setChecked(true);
            mot_st_text1.setText("ON");
        }
        else{
            onoff_rb2.setChecked(true);
            mot_st_text1.setText("OFF");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on__off);
        myDb = new DatabaseHelper(this);
        rtc_status="";

        textView1=findViewById(R.id.onoff_status_text1);
        mot_st_text1 = findViewById(R.id.on_off_status);
        onoff_rb1=findViewById(R.id.radioButton);
        onoff_rb2=findViewById(R.id.radioButton2);
        sharedPreferences=getSharedPreferences("mypref",0);
        editor= sharedPreferences.edit();
        sharedPreferences2=getSharedPreferences("pref",0);
        editor2= sharedPreferences2.edit();
        remove1=findViewById(R.id.textView11);
        remove2=findViewById(R.id.textView12);
        remove3=findViewById(R.id.textView13);


        spinner=findViewById(R.id.spinner1);
       et1=findViewById(R.id.tv1);
       et2=findViewById(R.id.tv2);
       et3=findViewById(R.id.tv3);
        et11=findViewById(R.id.tv4);
        et12=findViewById(R.id.tv5);
        et13=findViewById(R.id.tv6);
        textv=findViewById(R.id.textview1);
        text=findViewById(R.id.onoff_status_text_1);
        tv2=findViewById(R.id.textView2);
        mainLayout=findViewById(R.id.spinnerLayout);
        spinner1 = new Spinner(this);
        image=findViewById(R.id.image);
        format = new SimpleDateFormat("HH:mm");
        ctime=format.format(Calendar.getInstance().getTime());
        Log.i("current time",ctime);

        //Main Activity Spinner
         SharedPreferences prefs = getSharedPreferences("TAG", Context.MODE_PRIVATE);
        String SpinnerItems=prefs.getString("PLAYLISTS",null);

        String[]  spinnerItems=SpinnerItems.split(",");

        SPINNER = findViewById(R.id.spinner111);

        stringlist = new ArrayList<>(Arrays.asList(spinnerItems));

        arrayadapter = new ArrayAdapter<String>(ON_OFFActivity.this,R.layout.textview,stringlist);

        arrayadapter.setDropDownViewResource(R.layout.textview);
        SPINNER.setAdapter(arrayadapter);

        //get spinner last postion
        SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(SP!=null){
            int pos = SP.getInt("last index", 0);
            SPINNER.setSelection(pos);
        }

        SPINNER.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Subno = SPINNER.getItemAtPosition(i).toString();
                Log.i("Selected item",Subno);
                //store spinner position
                SharedPreferences SP;
                SP = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SP.edit().putInt("last index", SPINNER.getSelectedItemPosition()).commit();
                phoneNumber=Subno;
               SharedPreferences mt_pref = getSharedPreferences("login", 0);
                mt_pref.edit().putString("subId",phoneNumber).apply();
                String ph_s1 = SPINNER.getSelectedItem().toString();

                get_data(ph_s1);
                get_data1(ph_s1);
                if(rtc_status.contains("ON"))
                    spinner1.setSelection(0);
                else
                    spinner1.setSelection(1);
                textv.setText(rtc_status);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        String ph_s1 = SPINNER.getSelectedItem().toString();
        get_data1(ph_s1);


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

        sharedPreferences = getSharedPreferences("isChecked1", 0);
        value1 = sharedPreferences.getBoolean("isChecked1", value1); // retrieve the value of your key
        onoff_rb1.setChecked(value1);

        sharedPreferences = getSharedPreferences("isChecked2", 0);
        value2 = sharedPreferences.getBoolean("isChecked2", value2); // retrieve the value of your key
        onoff_rb2.setChecked(value2);
        String text9=sharedPreferences2.getString("Motor",null);
        Log.i("pStored",""+text9);
        mot_st_text1.setText(text9);

        String text1=sharedPreferences2.getString("RTC1",null);
        et1.setText(text1);
        String text2=sharedPreferences2.getString("RTC_1",null);
        et11.setText(text2);

        String text3=sharedPreferences2.getString("RTC2",null);
        et2.setText(text3);
        String text4=sharedPreferences2.getString("RTC_2",null);
        et12.setText(text4);

        String text5=sharedPreferences2.getString("RTC3",null);
        et3.setText(text5);
        String text6=sharedPreferences2.getString("RTC_3",null);
        et13.setText(text6);

        String text7=sharedPreferences2.getString("RTC_func",null);
        Log.i("pstored",""+text7);
        textv.setText(rtc_status);

       message1=sharedPreferences2.getString("msg1",null);
       message2=sharedPreferences2.getString("msg2",null);
       message3=sharedPreferences2.getString("msg3",null);
       message4=sharedPreferences2.getString("msg4",null);
       message5=sharedPreferences2.getString("msg5",null);
       message6=sharedPreferences2.getString("msg6",null);

        if(!et11.getText().toString().equals("")) remove1.setVisibility(View.VISIBLE);
        if(!et12.getText().toString().equals("")) remove2.setVisibility(View.VISIBLE);
        if(!et13.getText().toString().equals("")) remove3.setVisibility(View.VISIBLE);
    }


//-----------------------------------------------------------------------------------------|
    //RTC1 Settings
    public void ontime(View v) {
        if (textv.getText().toString().equals("ON")) {
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
                    else Hour = ("0" + String.valueOf(selectedHour));

                    if (selectedMinute >= 10) Minute = String.valueOf(selectedMinute);
                    else Minute = ("0" + String.valueOf(selectedMinute));

                    et1.setText(Hour + ":" + Minute);
                    ntime = et1.getText().toString();
                    editor2.putString("RTC1", ntime);
                    editor2.commit();
                    String h = et1.getText().toString().substring(0, 2);
                    String m = et1.getText().toString().substring(3);
                    message1 = "SPC,28," + h + "," + m;
                    editor2.putString("msg1", message1);
                    editor2.commit();
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();
        }else Toast.makeText(this, "RTC function is off", Toast.LENGTH_SHORT).show();
    }

    public void offtime(View v) {
        if (textv.getText().toString().equals("ON")) {
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
                        if (selectedHour >= 10) Hour = String.valueOf(selectedHour);
                        else Hour = ("0" + String.valueOf(selectedHour));

                        if (selectedMinute >= 10) Minute = String.valueOf(selectedMinute);
                        else Minute = ("0" + String.valueOf(selectedMinute));

                        et11.setText(Hour + ":" + Minute);
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

                        ftime = et11.getText().toString();
                        ntime = et1.getText().toString();

                        try {
                            d1 = format.parse(ntime);
                            d2 = format.parse(ftime);
                            long diff = d2.getTime() - d1.getTime();
                            diffMin = diff / (60 * 1000);
                            diffMin1=diff/(60);
                            Toast.makeText(ON_OFFActivity.this, "difference betwee times"+diffMin1, Toast.LENGTH_SHORT).show();
                            Log.i("difference", String.valueOf(diffMin));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (diffMin >= 5.0) {
                            String h = ftime.substring(0, 2);
                            String m = ftime.substring(3);
                            message2 = "SPC,29," + h + "," + m;
                            editor2.putString("RTC_1", ftime);
                            editor2.commit();
                            editor2.putString("msg2", message2);
                            editor2.commit();
                            remove1.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(ON_OFFActivity.this, "off time must be 5 mins greater than ontime", Toast.LENGTH_SHORT).show();
                            et11.setText(" ");

                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            } else Toast.makeText(this, "Please set onTime..", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(this, "RTC function is off", Toast.LENGTH_SHORT).show();
    }
    //------------------------------------------------------------------------------------|
    //RTC2 Settings
    public void ontime1(View v) {
        if (textv.getText().toString().equals("ON")) {
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
                        else Hour = ("0" + String.valueOf(selectedHour));

                        if (selectedMinute >= 10) Minute = String.valueOf(selectedMinute);
                        else Minute = ("0" + String.valueOf(selectedMinute));

                        et2.setText(Hour + ":" + Minute);
                        ntime1 = et2.getText().toString();

                        try {
                            ontime = format.parse(et2.getText().toString());
                            d1 = format.parse(et1.getText().toString());
                            d2 = format.parse(et11.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        if (ontime.after(d2)) {
                            String h = et2.getText().toString().substring(0, 2);
                            String m = et2.getText().toString().substring(3);
                            editor2.putString("RTC2", ntime1);
                            editor2.commit();
                            message3 = "SPC,30," + h + "," + m;
                            editor2.putString("msg3", message3);
                            editor2.commit();
                        } else {
                            et2.setText("");
                            Toast.makeText(ON_OFFActivity.this, "RTC2 must be out of RTC1 range..", Toast.LENGTH_LONG).show();
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            } else Toast.makeText(this, "complete RTC1 Settings..", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(this, "RTC function is off", Toast.LENGTH_SHORT).show();
    }
    public void offtime1(View v) {
        if (textv.getText().toString().equals("ON")) {
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
                        if (selectedHour >= 10) Hour = String.valueOf(selectedHour);
                        else Hour = ("0" + String.valueOf(selectedHour));
                        if (selectedMinute >= 10) Minute = String.valueOf(selectedMinute);
                        else Minute = ("0" + String.valueOf(selectedMinute));

                        et12.setText(Hour + ":" + Minute);
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

                        ftime1 = et12.getText().toString();
                        ntime1 = et2.getText().toString();

                        try {
                            d1 = format.parse(et1.getText().toString());
                            d2 = format.parse(et11.getText().toString());
                            date1 = format.parse(ntime1);
                            date2 = format.parse(ftime1);
                            offtime = format.parse(et12.getText().toString());
                            long diff = date2.getTime() - date1.getTime();
                            diffMin = diff / (60 * 1000);
                            diffMin1=diff/(60);
                            Toast.makeText(ON_OFFActivity.this, "difference betwee times"+diffMin1, Toast.LENGTH_SHORT).show();
                            Log.i("difference", String.valueOf(diffMin));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (diffMin >= 5.0) {
                            String h = ftime1.substring(0, 2);
                            String m = ftime1.substring(3);
                            message4 = "SPC,31," + h + "," + m;
                            editor2.putString("RTC_2", ftime1);
                            editor2.commit();
                            editor2.putString("msg4", message4);
                            editor2.commit();
                            remove2.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(ON_OFFActivity.this, "off time must be 30 mins greater than ontime", Toast.LENGTH_SHORT).show();
                            et12.setText("");

                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            } else Toast.makeText(this, "Please set onTime...", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(this, "RTC function is off", Toast.LENGTH_SHORT).show();
    }

    //------------------------------------------------------------------------------------|
    //RTC3 Settings
    public void ontime2(View v) {
        if (textv.getText().toString().equals("ON")) {
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
                        else Hour = ("0" + String.valueOf(selectedHour));
                        if (selectedMinute >= 10) Minute = String.valueOf(selectedMinute);
                        else Minute = ("0" + String.valueOf(selectedMinute));

                        et3.setText(Hour + ":" + Minute);
                        ntime2 = et3.getText().toString();

                        try {
                            ontime = format.parse(et3.getText().toString());
                            date1 = format.parse(et2.getText().toString());
                            date2 = format.parse(et12.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (ontime.after(date2)) {
                            String h = et3.getText().toString().substring(0, 2);
                            String m = et3.getText().toString().substring(3);
                            message5 = "SPC,32," + h + "," + m;
                            editor2.putString("msg5", message5);
                            editor2.commit();
                            editor2.putString("RTC3", ntime2);
                            editor2.commit();
                        } else {
                            et3.setText("");
                            Toast.makeText(ON_OFFActivity.this, "RTC2 must be out of RTC1 range..", Toast.LENGTH_LONG).show();
                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            } else Toast.makeText(this, "complete RTC3 Settings..", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(this, "RTC function is off", Toast.LENGTH_SHORT).show();
    }

    public void offtime2(View v) {
        if (textv.getText().toString().equals("ON")) {
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
                        if (selectedHour >= 10) Hour = String.valueOf(selectedHour);
                        else Hour = ("0" + String.valueOf(selectedHour));
                        if (selectedMinute >= 10) Minute = String.valueOf(selectedMinute);
                        else Minute = ("0" + String.valueOf(selectedMinute));

                        et13.setText(Hour + ":" + Minute);
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

                        ftime2 = et13.getText().toString();
                        ntime2 = et3.getText().toString();

                        try {
                            date_1 = format.parse(ntime2);
                            date_2 = format.parse(ftime2);
                            offtime = format.parse(et13.getText().toString());
                            long diff = date_2.getTime() - date_1.getTime();
                            diffMin = diff / (60 * 1000);
                            diffMin1=diff/(60);
                            Toast.makeText(ON_OFFActivity.this, "difference betwee times"+diffMin1, Toast.LENGTH_SHORT).show();
                            Log.i("difference", String.valueOf(diffMin));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (diffMin >= 5.0) {
                            String h = ftime2.substring(0, 2);
                            String m = ftime2.substring(3);
                            message6 = "SPC,33," + h + "," + m;
                            editor2.putString("RTC_3", ftime2);
                            editor2.commit();
                            editor2.putString("msg6", message6);
                            editor2.commit();
                            remove3.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(ON_OFFActivity.this, "off time must be 30 mins greater than ontime", Toast.LENGTH_SHORT).show();
                            et13.setText("");

                        }
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            } else Toast.makeText(this, "Please set onTime...", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(this, "RTC function is off", Toast.LENGTH_SHORT).show();
    }

// setting RTC functions
    public void set_RTCfunction(View view) {
        if (textv.getText().toString().equals("ON")) {
            SmsManager smsManager = SmsManager.getDefault();
            if(message1!=null)
                if(!message1.isEmpty())
                    smsManager.sendTextMessage(phoneNumber, null, message1, null, null);
            if(message2!=null)
                if(!message2.isEmpty())
                    smsManager.sendTextMessage(phoneNumber, null, message2, null, null);
            if(message3!=null)
                if(!message3.isEmpty())
                    smsManager.sendTextMessage(phoneNumber, null, message3, null, null);
            if(message4!=null)
                if(!message4.isEmpty())
                    smsManager.sendTextMessage(phoneNumber, null, message4, null, null);
            if(message5!=null)
                if(!message4.isEmpty())
                    smsManager.sendTextMessage(phoneNumber, null, message5, null, null);
            if(message6!=null)
                if(!message4.isEmpty())
                    smsManager.sendTextMessage(phoneNumber, null, message6, null, null);
        }
        else
            Toast.makeText(this, "RTC function is off", Toast.LENGTH_SHORT).show();

        setData_set2();
    }

public void remove1_fun(View v) {
    if (textv.getText().toString().equals("ON")) {
        if (remove2.getVisibility() == View.INVISIBLE && remove3.getVisibility() == View.INVISIBLE) {
            et1.setText("");
            et11.setText("");
            message1 = "SPC,28,00,00";
            message2 = "SPC,29,00,00";
            ntime = et1.getText().toString();
            editor2.putString("RTC1", ntime);
            editor2.commit();
            ftime = et11.getText().toString();
            editor2.putString("RTC_1", ftime);
            editor2.commit();
            editor2.putString("msg1", message1);
            editor2.commit();
            editor2.putString("msg2", message2);
            editor2.commit();
            remove1.setVisibility(View.INVISIBLE);
        } else Toast.makeText(this, "remove RTC2 and RTC3..", Toast.LENGTH_SHORT).show();
    }else Toast.makeText(this, "RTC function is off", Toast.LENGTH_SHORT).show();
}

    public void remove2_fun(View v) {
        if (textv.getText().toString().equals("ON")) {
            if (remove3.getVisibility() == View.INVISIBLE) {
                et2.setText("");
                et12.setText("");
                message3 = "SPC,30,00,00";
                message4 = "SPC,31,00,00";
                ntime1 = et2.getText().toString();
                editor2.putString("RTC2", ntime1);
                editor2.commit();
                ftime1 = et12.getText().toString();
                editor2.putString("RTC_2", ftime1);
                editor2.commit();
                editor2.putString("msg3", message3);
                editor2.commit();
                editor2.putString("msg4", message4);
                editor2.commit();
                remove2.setVisibility(View.INVISIBLE);
            } else Toast.makeText(this, "remove RTC3..", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(this, "RTC function is off", Toast.LENGTH_SHORT).show();
    }

    public void remove3_fun(View v) {
        if (textv.getText().toString().equals("ON")) {
            et3.setText("");
            et13.setText("");
            message5 = "SPC,32,00,00";
            message6 = "SPC,33,00,00";
            ntime2 = et3.getText().toString();
            editor2.putString("RTC3", ntime2);
            editor2.commit();
            ftime2 = et13.getText().toString();
            editor2.putString("RTC_3", ftime2);
            editor2.commit();
            editor2.putString("msg5", message5);
            editor2.commit();
            editor2.putString("msg6", message6);
            editor2.commit();
            remove3.setVisibility(View.INVISIBLE);
        }else Toast.makeText(this, "RTC function is off", Toast.LENGTH_SHORT).show();
    }

    public void set_function(View view){
        if(selectedItem.equals("ON")){
            String message = "SPC,27,1";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            textv.setText("ON");
            setData_set();
            Log.i("Test", message);
            //text.setText("Command Sent, Please Wait...For 2 minutes");
        }
        else if(selectedItem.equals("OFF")){
            String message = "SPC,27,0";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            textv.setText("OFF");
            setData_set1();
            Log.i("Test", message);
            //text.setText("Command Sent, Please Wait...For 2 minutes");
        }
        editor2.putString("RTC_func",textv.getText().toString());
        editor2.commit();
    }

    public void setRTC(){
        ArrayList<String> RTC = new ArrayList<String>();
        RTC.add("ON");
        RTC.add("OFF");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,RTC);
        spinner1.setAdapter(spinnerArrayAdapter);
        mainLayout.addView(spinner1);
        if(rtc_status.contains("ON"))
            spinner1.setSelection(0);
        else
            spinner1.setSelection(1);
    }


    public void turnsOn(View view) {
            String message = "";
            if (onoff_rb1.isChecked()) {
            message = "SPC,24";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            mot_st_text1.setText("ON");
            setData();
            //textView1.setText("Command Sent...");
            }
            else {
            message = "SPC,26";
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            mot_st_text1.setText("OFF");
            setData();
            //textView1.setText("Command Sent...");
            }
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


    @Override
    public void onPause(){
        super.onPause();
    }

    public void setData(){
            String mot_data[] = {"",""};
            mot_data[0] = phoneNumber;
            mot_data[1] = mot_st_text1.getText().toString();
            boolean isUpdated = myDb.updateData(mot_data, phoneNumber,1024);
            if (isUpdated == true)
                Toast.makeText(ON_OFFActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(ON_OFFActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
    }

    public void get_data(String ph_no){
        Cursor res = myDb.getAllData_set(ph_no);
        Cursor res1 = myDb.getAllData(ph_no);
        if(res.getCount() == 0) {
            Toast.makeText(ON_OFFActivity.this,"Nothing found",Toast.LENGTH_LONG).show();
            return;
        }
        Log.i("Rec_Count",String.valueOf(res.getCount()));
        while (res.moveToNext()) {
            Log.i("Rec_Count",res.getString(0));
            Log.i("Rec_Count",res.getString(1));
            Log.i("Rec_Count",res.getString(2));
        }
        res1.moveToFirst();
            Log.i("Rec_Count",res1.getString(9));
            Log.i("Rec_Count",res1.getString(10));
            Log.i("Rec_Count",res1.getString(15));

    }


    public void get_data1(String ph_no){
        Cursor res = myDb.getAllData(ph_no);
        Cursor res1 = myDb.getAllData_set(ph_no);
        if(res.getCount() == 0) {
            Toast.makeText(ON_OFFActivity.this,"Nothing found",Toast.LENGTH_LONG).show();
            return;
        }
        Log.i("Rec_Count",String.valueOf(res.getCount()));
        while (res.moveToNext()) {
            Log.i("tt",res.getString(8));
            String s=res.getString(8);
            if(s.contains("ON")){
                onoff_rb1.setChecked(true);
                mot_st_text1.setText("ON");
                mot_st=1;
            }
            else{
                onoff_rb2.setChecked(true);
                mot_st_text1.setText("OFF");
                mot_st=0;
            }

        }

        while (res1.moveToNext()){
            Log.i("setting data:",res1.getString(0));
            Log.i("setting data:",res1.getString(1));
            Log.i("setting data:",res1.getString(2));
            Log.i("setting data:",res1.getString(3));
            Log.i("setting data:",res1.getString(4));
            Log.i("setting data:",res1.getString(5));
            Log.i("setting data:",res1.getString(6));
            Log.i("setting data:",res1.getString(7));
            rtc_status=res1.getString(1);
            et1.setText(res1.getString(2));
            et11.setText(res1.getString(3));
            et2.setText(res1.getString(4));
            et12.setText(res1.getString(5));

        }

    }


    public void setData_set(){
        String mot_data[] = {"",""};
        mot_data[0] = phoneNumber;
        mot_data[1] = "ON";
        boolean isUpdated = myDb.update_set(mot_data, phoneNumber,2701);
        if (isUpdated == true)
            Toast.makeText(ON_OFFActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(ON_OFFActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
    }

    public void setData_set1(){
        String mot_data[] = {"","","","","","","",""};
        mot_data[0] = phoneNumber;
        mot_data[1] = "OFF";
        mot_data[2] = "";
        mot_data[3] = "";
        mot_data[4] = "";
        mot_data[5] = "";
        mot_data[6] = "";
        mot_data[7] = "";
        boolean isUpdated = myDb.update_set(mot_data, phoneNumber,2702);
        if (isUpdated == true)
            Toast.makeText(ON_OFFActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(ON_OFFActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
    }

    public void setData_set2(){
        String mot_data[] = {"","","","","","","",""};
        mot_data[0] = phoneNumber;
        mot_data[2] = et1.getText().toString();
        mot_data[3] = et11.getText().toString();
        mot_data[4] = et2.getText().toString();
        mot_data[5] = et12.getText().toString();
        mot_data[6] = et3.getText().toString();
        mot_data[7] = et13.getText().toString();
        boolean isUpdated = myDb.update_set(mot_data, phoneNumber,2703);
        if (isUpdated == true)
            Toast.makeText(ON_OFFActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(ON_OFFActivity.this, "Data not Updated", Toast.LENGTH_LONG).show();
    }






}
