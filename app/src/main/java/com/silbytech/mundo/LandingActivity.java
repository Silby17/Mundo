package com.silbytech.mundo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class LandingActivity extends Activity {
    private String TAG = "LandingActivity";
    Button btnSignUp;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Show Window in full screen without status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.landing_layout);

        this.btnSignUp = findViewById(R.id.btnSignUp);
        this.btnLogin = findViewById(R.id.btnLogin);

        this.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LandingActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });

        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LandingActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
}