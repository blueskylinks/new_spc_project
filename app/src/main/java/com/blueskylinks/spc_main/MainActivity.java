package com.blueskylinks.spc_main;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText et1;
    EditText et2;
    public static String subId;
    String pass;
    TextView text;
    CheckBox c1;
    SharedPreferences settings;
    SharedPreferences sharedPreferences1;
    boolean value1 = true;
    public static String phoneNumber;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS}, 200);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_SMS}, 200);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECEIVE_SMS}, 200);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_PHONE_STATE}, 200);
        et1 = findViewById(R.id.Et1);
        et2 = findViewById(R.id.Et2);
        text = findViewById(R.id.textView7);
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        c1 = findViewById(R.id.checkBox);
        textView=findViewById(R.id.textView30);
        textView.setPaintFlags(p.getColor());
        textView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        sharedPreferences1 = getSharedPreferences("isChecked1", 0);
        value1 = sharedPreferences1.getBoolean("isChecked1", value1); // retrieve the value of your key

        c1.setChecked(value1);
        c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sharedPreferences1.edit().putBoolean("isChecked1", true).apply();
                // update your model (or other business logic) based on isChecked
              settings = getSharedPreferences("PREFS_NAME", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = settings.edit();
                    subId = et1.getText().toString();
                    pass = et2.getText().toString();
                // Edit and commit
                System.out.println("onPause save name: " + subId);
                System.out.println("onPause save password: " + pass);
                editor.putString("PREF_UNAME", subId);
                editor.putString("PREF_PASSWORD", pass);
                editor.commit();

                }
                else {   sharedPreferences1.edit().putBoolean("isChecked1", false).apply();}
            }
        });

        et1.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==10){text.setText(" ");}
                //Your query to fetch Data
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    //Your query to fetch Data

            }
        });
        et2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("test",s.toString());
                if(s.toString().equals("1234")){text.setText(" ");}
                //Your query to fetch Data
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //Your query to fetch Data
            }
        });

        if(c1.isChecked()){
            settings = getSharedPreferences("PREFS_NAME",Context.MODE_PRIVATE);
            // Get value
            subId = settings.getString("PREF_UNAME", " ");
            pass = settings.getString("PREF_PASSWORD", " ");
            et1.setText(subId);
            et2.setText(pass);
            System.out.println("onResume load name: " + subId);
            System.out.println("onResume load password: " + pass);
        }
            else return;
    }

    public void set_activity(View view) {
        subId = et1.getText().toString();
        pass = et2.getText().toString();
        Log.i("sub ID:",subId);
        Log.i("password",pass);
        if (TextUtils.isEmpty(subId) || TextUtils.isEmpty(pass)) {
            if (TextUtils.isEmpty(subId)) et1.setError("please Enter subscriber Id");
            else et2.setError("please Enter password");
        } else if (!subId.equals("9880760642")) {
            text.setText("Incorrect subscribe ID!!..");
        } else if (!pass.equals("1234")) {
            text.setText("Incorrect  password!!..");
        } else {
            phoneNumber=subId;
            //starting another activity..
            Intent it = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(it);
        }
    }
}

