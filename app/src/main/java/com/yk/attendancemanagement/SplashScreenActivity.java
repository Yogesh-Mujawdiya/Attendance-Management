package com.yk.attendancemanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.yk.attendancemanagement.Controller.StoreData;

public class SplashScreenActivity extends Activity {

    StoreData controller;
    String Host;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        Host = getString(R.string.localhost);
        controller = new StoreData(this);
        controller.setHost(Host);
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i;
                if(controller.isLogin()) {
                    if(controller.isFaculty())
                        i = new Intent(SplashScreenActivity.this, FacultyActivity.class);
                    else
                        i = new Intent(SplashScreenActivity.this, StudentActivity.class);
                }
                else
                    i = new Intent(SplashScreenActivity.this, LoginSignupActivity.class);
                startActivity(i);
                finish();
            }
        }, 2000);
    }
}