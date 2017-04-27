package com.konekthing.intravest.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;

import com.konekthing.intravest.R;

/**
 * Created by Semmy on 12/1/2016.
 */

public class SplashScreen extends Activity {
    private static int Splash_Time_out = 8000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SplashScreen.this);
                if (sp.getBoolean(LoginFragment.PREF_IS_LOGIN, false)) {
                    startActivity(new Intent(SplashScreen.this , MainActivity.class));
                    finish();
                    return;
                }else{
                    Intent i = new Intent(SplashScreen.this, MainMenuActivity.class);
                startActivity(i);
                finish();
                }

            }
        }, Splash_Time_out);
    }
}
