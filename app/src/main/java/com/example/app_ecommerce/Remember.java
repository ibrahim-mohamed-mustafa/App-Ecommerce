package com.example.app_ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

public class Remember extends AppCompatActivity   {
    Timer timer;
    Animation top, bottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strart);
        top= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottom = AnimationUtils.loadAnimation(this,R.anim.butom_animation);
        ImageView img=(ImageView)findViewById(R.id.imageBackGround);
        img.setAnimation(top);
        TextView tx=(TextView)findViewById(R.id.textEcommerceApp);
        tx.setAnimation(bottom);
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    boolean check=preferences.getBoolean("remember",false);
                    if(check) {
                        Intent intent = new Intent(Remember.this, Main.class);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(Remember.this, signup_login.class);
                        startActivity(intent);
                    }
            }
        },100);
    }
}