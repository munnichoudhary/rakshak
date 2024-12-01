package com.iit.rakshak;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


               /* if (SharedPrefManager.getInstance(SplashActivity.this).isLoggedIn()) {
                    finish();
                    startActivity(new Intent(SplashActivity.this, Primary.class));
                    return;
                }
                else
                    startActivity(new Intent(SplashActivity.this, SigninActivity.class));
                finish();*/
                startActivity(new Intent(SplashActivity.this, TabOnOffline.class));
                finish();

            }
        }, 2000);
    }
}
