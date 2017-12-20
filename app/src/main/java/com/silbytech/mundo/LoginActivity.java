package com.silbytech.mundo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class LoginActivity extends Activity {
    private static String TAG = "LoginActivity";
    public static final String PREFS = "prefs";
    private SharedPreferences preferences;
    Button btnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Show Window in full screen without status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_layout);

        this.btnLogin = (Button)findViewById(R.id.btnLogin);

        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = ((EditText)findViewById(R.id.edtEmail)).getText().toString();
                final String password = ((EditText)findViewById(R.id.edtPassword)).getText().toString();

                if(email.equals("") || password.equals("")){
                    Toast.makeText(getApplicationContext(),
                            R.string.errorMissingInfo, Toast.LENGTH_SHORT).show();
                }
                else {

                }
            }
        });
    }
}
