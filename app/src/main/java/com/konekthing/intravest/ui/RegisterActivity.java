package com.konekthing.intravest.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.konekthing.intravest.R;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 5/8/17.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class RegisterActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    public static ImageView iv;
    public static TextView tv;
    public static AppCompatActivity activity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_activity);

        activity = RegisterActivity.this;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_bro, new RegisterActivity_1());
        fragmentTransaction.commit();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");

        setSupportActionBar(mToolbar);

        iv = (ImageView) findViewById(R.id.back_btn);
        tv = (TextView) findViewById(R.id.TextToolbar);
    }
}
