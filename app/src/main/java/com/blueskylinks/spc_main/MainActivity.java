package com.blueskylinks.spc_main;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText et1;
    EditText et2;
    String subId;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS},200);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_SMS},200);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECEIVE_SMS},200);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_PHONE_STATE}, 200);
        et1=findViewById(R.id.Et1);
        et2=findViewById(R.id.Et2);

    }

    public void set_activity(View view){
        subId= et1.getText().toString();
        pass=et2.getText().toString();
        Log.i("sub ID:",subId);
        Log.i("password",pass);

       if(TextUtils.isEmpty(subId) || TextUtils.isEmpty(pass))
       {
           if(TextUtils.isEmpty(subId)) et1.setError("please Enter subscriber Id");

            else et2.setError("please Enter password");
        }

        else {
           //starting another activity..
           Intent it = new Intent(MainActivity.this, Main2Activity.class);
           startActivity(it);
       }
    }
}
