package com.konekthing.intravest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.konekthing.intravest.R;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 2/20/17.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */

public class RegisterActivity_1 extends AppCompatActivity {
    Button btn_next;
    Toolbar mToolbar;
    ImageView btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_register_1);

        btn_next = (Button) findViewById(R.id.btn_next_regis1);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity_1.this, RegisterActivity_2.class);
                startActivity(i);
            }
        });

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        TextView tv = (TextView) findViewById(R.id.TextToolbar);
        btn = (ImageView) findViewById(R.id.back_btn);
        tv.setText("Individual Detail");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(mToolbar);


    }
}
