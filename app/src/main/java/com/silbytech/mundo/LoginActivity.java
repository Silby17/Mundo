package com.silbytech.mundo;

import android.app.Activity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.silbytech.mundo.responses.LoginResponse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

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
    CallbackManager callbackManager;
    LoginButton fbLoginButton;
    Button btnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Show Window in full screen without status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_layout);
        callbackManager = CallbackManager.Factory.create();

        ImageView backImgButton = findViewById(R.id.imgBackArrow);
        //Will close the activity when the back arrow is clicked
        backImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivity.this.finish();
            }
        });

        TextView forgotPassword = findViewById(R.id.tvForgotPassword);
        //Will open a new activity which will recover a users password
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), R.string.forgot_password, Toast.LENGTH_SHORT).show();
            }
        });

        this.btnLogin = findViewById(R.id.btnLogin);
        //init the Facebook login button
        this.fbLoginButton = findViewById(R.id.fb_login_button);
        fbLoginButton.setReadPermissions("email");

        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = ((EditText)findViewById(R.id.edtEmail)).getText().toString();
                final String password = ((EditText)findViewById(R.id.edtPassword)).getText().toString();

                //Checks that the user has entered in all their details
                if(email.equals("") || password.equals("")){
                    Toast.makeText(getApplicationContext(),
                            R.string.errorMissingInfo, Toast.LENGTH_SHORT).show();
                }
                else {
                    //Create a new server communicator
                    Communicator communicator = new Communicator();
                    //Log the user into the server
                    Call<LoginResponse> call = communicator.userLogin(email, password);

                    //Make the call to the Server
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            if(response.code() == 200){
                                LoginResponse loginResponse = response.body();
                                //Build the full name of the Logged in user
                                String fullName = loginResponse.getFirstName() + " " + loginResponse.getSurname();
                                //Saves the below info to shared preferences
                                preferences = getSharedPreferences(PREFS, 0);
                                preferences.edit().putString("userId", loginResponse.getId()).apply();
                                preferences.edit().putString("fullName", fullName).apply();
                                preferences.edit().putString("email", loginResponse.getEmail()).apply();
                                preferences.edit().putString("userToken", response.headers().get("x-auth")).apply();
                                preferences.edit().putBoolean("logged-in", true).apply();
                                Toast.makeText(getApplicationContext(),
                                        R.string.welcomeBack, Toast.LENGTH_SHORT).show();
                                //Starts the main Activity
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                //Finishes the login activity
                                LoginActivity.this.finish();
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

        //Facebook login callback Registration
        this.fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        //Gets all the Users Data from the object response
                        Bundle userFacebookData = getFacebookData(object);
                        preferences = getSharedPreferences(PREFS, 0);
                        preferences.edit().putString("profile_pic_url",
                                userFacebookData.get("profile_pic").toString()).apply();

                        //Creates a new Server communicator
                        Communicator communicator = new Communicator();
                        if(userFacebookData.get("email").toString() != null
                                && userFacebookData.get("id").toString() != null){

                            //Creates a login call to the server
                            Call<LoginResponse> call = communicator.userLogin(
                                    userFacebookData.get("email").toString(),
                                    userFacebookData.get("id").toString());

                            //Makes the Login request to the server
                            call.enqueue(new Callback<LoginResponse>() {
                            @Override
                            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                if(response.code() == 200){
                                    LoginResponse loginResponse = response.body();
                                    assert loginResponse != null;
                                    //Makes the full name of the user String
                                    String fullName = loginResponse.getFirstName() + " " + loginResponse.getSurname();
                                    //Saves the below info into shared preferences
                                    preferences = getSharedPreferences(PREFS, 0);
                                    preferences.edit().putString("userId", loginResponse.getId()).apply();
                                    preferences.edit().putString("fullName", fullName).apply();
                                    preferences.edit().putString("email", loginResponse.getEmail()).apply();
                                    //Gets the x-auth token from the header
                                    preferences.edit().putString("userToken", response.headers().get("x-auth")).apply();
                                    preferences.edit().putBoolean("logged-in", true).apply();
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
                        else {
                            Toast.makeText(getApplicationContext(), R.string.missingFBData,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.errorTryLater,Toast.LENGTH_SHORT).show();
            }
        });
    }


    /***********************************************************************
     * This function will extract all the data from the Facebook object
     * and return a bundle
     * @param object - from Facebook response
     * @return - Bundle with all the users info
     ***********************************************************************/
    private Bundle getFacebookData(JSONObject object) {
        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");
            bundle.putString("id", id);
            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
            bundle.putString("idFacebook", id);
            if (object.has("first_name"))
                bundle.putString("first_name", object.getString("first_name"));
            if (object.has("last_name"))
                bundle.putString("last_name", object.getString("last_name"));
            if (object.has("email"))
                bundle.putString("email", object.getString("email"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            return bundle;
        }
        catch(JSONException e) {
            Log.d(TAG,"Error parsing JSON");
        }
        return null;
    }


    /************************************************************************
     * This function will handel the result from the Facebook Login
     * @param requestCode - request Code
     * @param resultCode - result code
     * @param data - data from Intent
     ***********************************************************************/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}