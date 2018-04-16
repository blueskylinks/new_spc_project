package com.blueskylinks.spc_main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {
    Switch s1;
    Switch s2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        s1=findViewById(R.id.switch1);
        s2=findViewById(R.id.switch2);
    }


    //Hoome functions
    public void home(View view){

        Intent it1 = new Intent(SettingsActivity.this, Main2Activity.class);
        startActivity(it1);
    }


    public void ON_OFF(View view){
        //starting another activity..
        Intent it2 = new Intent(SettingsActivity.this, ON_OFFActivity.class);
        startActivity(it2);
    }
    public void Users(View view){
        //starting another activity..
        Intent it3 = new Intent(SettingsActivity.this, UsersActivity.class);
        startActivity(it3);
    }


}


