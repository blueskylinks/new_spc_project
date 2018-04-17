package com.blueskylinks.spc_main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
    }

    public void call_fun(View view){
        Intent in1=new Intent(FeedbackActivity.this,FeedbackCallActivity.class);
        startActivity(in1);
    }
}
