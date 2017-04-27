package com.konekthing.intravest.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.konekthing.intravest.R;

/**
 * Created by Semmy on 12/1/2016.
 */

public class MainMenuActivity extends BaseActivity {
    private Button btn_login, btn_regis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.front_home);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_regis = (Button) findViewById(R.id.btn_regis);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenuActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });

        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainMenuActivity.this, RegisterActivity_1.class);
                startActivity(i);
            }
        });

    }
}
