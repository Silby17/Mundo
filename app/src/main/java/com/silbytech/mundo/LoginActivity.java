package com.silbytech.mundo;

import android.app.Activity;
import com.silbytech.mundo.responses.LoginResponse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        this.btnLogin = findViewById(R.id.btnLogin);

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

                    Communicator communicator = new Communicator();
                    Call<LoginResponse> call = communicator.userLogin(email, password);

                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if(response.code() == 200){
                                LoginResponse loginResponse = response.body();
                                String fullName = loginResponse.getFirstName() + " " + loginResponse.getSurname();
                                preferences = getSharedPreferences(PREFS, 0);
                                preferences.edit().putString("userid", loginResponse.getId()).apply();
                                preferences.edit().putString("fullName", fullName).apply();
                                preferences.edit().putString("token", response.headers().get("x-auth")).apply();
                                Toast.makeText(getApplicationContext(),
                                        R.string.welcomeBack, Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                            }
                            else if(response.code() == 400){
                                Toast.makeText(getApplicationContext(),
                                        R.string.detailsError, Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),
                                    R.string.errorTryLater, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}