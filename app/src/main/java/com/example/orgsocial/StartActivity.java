package com.example.orgsocial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.orgsocial.authentication.LoginActivity;

public class StartActivity extends AppCompatActivity {

    SharedPreferences prefs = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_container);
        prefs = getSharedPreferences("com.example.orgsocial",MODE_PRIVATE);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new splashScreen()).commit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(prefs.getBoolean("firstrun",true)){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,new MainActivity.Welcome()).commit();
                }else{
                    startActivity(new Intent(StartActivity.this, LoginActivity.class));
                    finish();
                }

            }
        },1500);



    }
}