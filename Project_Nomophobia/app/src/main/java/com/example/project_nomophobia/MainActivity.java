package com.example.project_nomophobia;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.widget.RelativeLayout;
import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private Resources mResources;
    private RelativeLayout mRelativeLayout;
    private Button mButton2;
    private Button mButton3;
    private UsageStatsManager usageStatsManager;
    private TextView textView;
    private PhonePickupActivity temp;
    private String message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final long currentTime = System.currentTimeMillis();
        setContentView(R.layout.activity_main);
        mButton2 = (Button)findViewById(R.id.button2);
        mButton3 = (Button)findViewById(R.id.button3);
        textView = (TextView)findViewById(R.id.textView);
        SharedPreferences preferences = getSharedPreferences("key", MODE_PRIVATE);
        //getSharedPreferences("key", 0).edit().clear().commit();

        if(preferences.contains("text")){
            TextView textView = findViewById(R.id.textView);
            message = preferences.getString("text"," ");
            textView.setText(message);
        }

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final long rightnow = System.currentTimeMillis();
                textView.setText(message + " " + (rightnow-currentTime)/1000);
                //Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                //startActivity(intent);
            }
        });
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(" ");
                getSharedPreferences("key", 0).edit().clear().commit();
            }
        });


    }
    /*
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("test", "onRestoreInstanceState");
        CharSequence userText = savedInstanceState.getCharSequence("savedText");
        textView.setText(userText);
    }
    */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i("test", "onSaveInstanceState");

        CharSequence userText = textView.getText();
        outState.putCharSequence("savedText", userText);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences preferences = getSharedPreferences("key", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();



        textView = (TextView)findViewById(R.id.textView);
        String message = textView.getText().toString();

        editor.putString("text", message);
        editor.commit();
        editor.apply();
    }

/*
    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences preferences = getSharedPreferences("key", MODE_PRIVATE);
        TextView textView = findViewById(R.id.textView);
        String message = preferences.getString("text","");
        textView.setText(message);
    }
    */

}