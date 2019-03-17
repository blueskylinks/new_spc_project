package com.blueskylinks.spc_main;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String subId;
    TextView text;
    SharedPreferences prefs;
    boolean value1 = true;
    String phoneNumber;
    String Subno;
    Spinner SPINNER;
    Button ADD;
    EditText EDITTEXT;
    Button remove;
    String TEXT;
    EditText EDITTEXT1;
    String[] spinnerItems = new String[]{};
    String GETTEXT;
    List<String> stringlist;
    ArrayAdapter<String> arrayadapter;
    SharedPreferences mt_pref;

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            // Permission has already been granted
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

        myDb = new DatabaseHelper(this);

        prefs = getSharedPreferences("TAG", Context.MODE_PRIVATE);
        String SpinnerItems=prefs.getString("PLAYLISTS",null);
        if (SpinnerItems!=null){
        spinnerItems=SpinnerItems.split(",");}
        else{}

        SPINNER = (Spinner)findViewById(R.id.spinner1);
        ADD = (Button)findViewById(R.id.button1);
        EDITTEXT = (EditText)findViewById(R.id.editText1);
        remove = (Button)findViewById(R.id.button11);
        EDITTEXT1 = (EditText)findViewById(R.id.editText11);
        stringlist = new ArrayList<>(Arrays.asList(spinnerItems));

        arrayadapter = new ArrayAdapter<String>(MainActivity.this,R.layout.textview,stringlist);

        arrayadapter.setDropDownViewResource(R.layout.textview);
        SPINNER.setAdapter(arrayadapter);

        startService(new Intent(this, UpdatingService.class));

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
                phoneNumber=Subno;

                SharedPreferences SP;
                SP = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SP.edit().putInt("last index", SPINNER.getSelectedItemPosition()).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }

    public void display_editBox(View v){
        EDITTEXT.setVisibility(View.VISIBLE);
        ADD.setVisibility(View.VISIBLE);
    }

    public void display_removeBox(View v){
        EDITTEXT1.setVisibility(View.VISIBLE);
        remove.setVisibility(View.VISIBLE);
    }

    public void update_spinner(View v){
        GETTEXT = EDITTEXT.getText().toString();
        if(GETTEXT!="" && GETTEXT.length()==10) {
            if (stringlist.contains(GETTEXT)) {
            } else {
                stringlist.add(GETTEXT);
                arrayadapter.notifyDataSetChanged();
                prefs = this.getSharedPreferences("TAG", Context.MODE_PRIVATE);
                prefs.edit().putString("KEY", GETTEXT).commit();
                Log.i("updated string", stringlist.toString());
                //Storing list
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < stringlist.size(); i++) {
                    sb.append(stringlist.get(i)).append(",");
                }
                prefs.edit().putString("PLAYLISTS", sb.toString()).commit();
            }
        }
        else Toast.makeText(this, "please Enter 10 digit Number", Toast.LENGTH_SHORT).show();
        EDITTEXT.setVisibility(View.INVISIBLE);
        ADD.setVisibility(View.INVISIBLE);
        AddData();
    }

    public void remove_spinnerItems(View view){
        TEXT=EDITTEXT1.getText().toString();
            if(stringlist.contains(TEXT)) {
                stringlist.remove(TEXT);
                arrayadapter.notifyDataSetChanged();
            }
            else Toast.makeText(this, "please Enter 10 digit Number", Toast.LENGTH_SHORT).show();

        EDITTEXT1.setVisibility(View.INVISIBLE);
        remove.setVisibility(View.INVISIBLE);

    }



    public void set_activity(View view) {
            if(SPINNER != null && phoneNumber !=null ) {
                mt_pref=getSharedPreferences("login", 0);
                mt_pref.edit().putBoolean("logged", true).apply();
                mt_pref.edit().putString("subId", phoneNumber).apply();
                Log.i("phone_id", phoneNumber);
                Intent it = new Intent(MainActivity.this, splashActivity.class);
                startActivity(it);
            }
            else Toast.makeText(this, "Please Add numbers", Toast.LENGTH_SHORT).show();
    }


    public  void AddData() {
        Log.i("Data", GETTEXT);
        String s1=GETTEXT.toString();
        boolean isInserted = myDb.insertData(s1);
        boolean isInserted1 = myDb.insert_setting_tb(s1,"PH_ID");
        if(isInserted == true)
            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
    }
}

