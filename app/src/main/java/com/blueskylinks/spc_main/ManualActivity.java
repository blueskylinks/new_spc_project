package com.blueskylinks.spc_main;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class ManualActivity extends AppCompatActivity {
    int pos;
    Spinner spinner;
    SharedPreferences sharedPreferences1;
    LinearLayout layout;
    Button myButton1;
    Button myButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
       spinner = (Spinner)findViewById(R.id.spinner1);
      layout = (LinearLayout)findViewById(R.id.buttonlayout);
         myButton1= new Button(this);
         myButton2= new Button(this);
        sharedPreferences1 = getSharedPreferences("key", 0);
        pos= sharedPreferences1.getInt("key",pos);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                pos = spinner.getSelectedItemPosition();
                Log.i("position ", String.valueOf(pos));
                sharedPreferences1.edit().putInt("key", pos).apply();
                if(pos==0) return;
                else {layout.removeView(myButton1);
                layout.removeView(myButton2);}
            }
            // to close the onItemSelected
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    public void move_nextlayout(View view){
        switch (pos){
            case 0:
                    Call_SMS();
                    break;
            case 2:
                    settings(view);
                    break;
            default:
                return;
        }
    }

    public void settings(View view){
        //starting another activity..
        Intent it1 = new Intent(ManualActivity.this, SettingsActivity.class);
        startActivity(it1);
    }

    public void ON_OFF(View view){
        //starting another activity..
        Intent it2 = new Intent(ManualActivity.this, ON_OFFActivity.class);
        startActivity(it2);
    }

    public void Users(View view){
        //starting another activity..
        Intent it3 = new Intent(ManualActivity.this, UsersActivity.class);
        startActivity(it3);
    }

    //Hoome functions
    public void home(View view){
        Intent it = new Intent(ManualActivity.this, Main2Activity.class);
        startActivity(it);
    }

    public void Call_SMS(){

        myButton1.setText("CALL");
        myButton2.setText("SMS");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.addView(myButton1, lp);
        layout.addView(myButton2,lp);

        myButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
             Intent in1=new Intent(ManualActivity.this,FeedbackCallActivity.class);
             startActivity(in1);
             layout.removeView(myButton1);
             layout.removeView(myButton2);
            }
        });

    }

}
