package com.silbytech.mundo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.silbytech.mundo.responses.LoginResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.MalformedURLException;
import java.net.URL;


/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class SignUpActivity extends Activity {
    private static String TAG = "SignUpActivity";
    public static final String PREFS = "prefs";
    CallbackManager callbackManager;
    private SharedPreferences preferences;
    Button btnSignUp;
    LoginButton fbLoginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Show Window in full screen without status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.sign_up_layout);
        callbackManager = CallbackManager.Factory.create();

        this.btnSignUp = findViewById(R.id.btnSignUp);
        this.fbLoginButton = findViewById(R.id.fb_login_button);
        this.fbLoginButton.setReadPermissions("email");

        this.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Extracts the data from the users inputs
                String firstName = ((EditText)findViewById(R.id.edtFirstName)).getText().toString();
                String lastName = ((EditText)findViewById(R.id.edtLastName)).getText().toString();
                String email = ((EditText)findViewById(R.id.edtEmail)).getText().toString();
                String password = ((EditText)findViewById(R.id.edtPassword)).getText().toString();


                if(firstName.equals("") || lastName.equals("")
                        ||email.equals("") || password.equals("")){
                    Toast.makeText(getApplicationContext(),
                            R.string.errorMissingInfo, Toast.LENGTH_SHORT).show();
                }
                else {
                    Communicator communicator = new Communicator();
                    Call<LoginResponse> call = communicator.userSignUp(firstName, lastName, email, password);
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                            //Response OK From Server
                            if(response.code() == 200){
                                LoginResponse loginResponse = response.body();
                                String fullName = loginResponse.getFirstName() + " " + loginResponse.getSurname();
                                preferences = getSharedPreferences(PREFS, 0);
                                preferences.edit().putBoolean("logged-in", true).apply();
                                preferences.edit().putString("userId", loginResponse.getId()).apply();
                                preferences.edit().putString("fullName", fullName).apply();
                                preferences.edit().putString("token", response.headers().get("x-auth")).apply();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                            //Conflict in the Server
                            else if(response.code() == 409){
                                Toast.makeText(getApplicationContext(), R.string.userExists, Toast.LENGTH_SHORT).show();
                                LoginManager.getInstance().logOut();
                            }
                            //Bad Request
                            else if(response.code() == 400){
                                Toast.makeText(getApplicationContext(), R.string.detailsError, Toast.LENGTH_SHORT).show();
                                LoginManager.getInstance().logOut();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), R.string.errorTryLater, Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), R.string.errorTryLater,
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        this.fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Bundle userFacebookData = getFacebookData(object);
                        Communicator communicator = new Communicator();
                        assert userFacebookData != null;
                        Call<LoginResponse> call = communicator.userSignUp(
                                userFacebookData.get("first_name").toString(),
                                userFacebookData.get("last_name").toString(),
                                userFacebookData.get("email").toString(),
                                userFacebookData.get("id").toString());

                        call.enqueue(new Callback<LoginResponse>() {
                            @Override
                            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                if(response.code() == 200){
                                    LoginResponse loginResponse = response.body();
                                    String fullName = loginResponse.getFirstName() + " " + loginResponse.getSurname();
                                    preferences = getSharedPreferences(PREFS, 0);
                                    preferences.edit().putBoolean("logged-in", true).apply();
                                    preferences.edit().putString("userId", loginResponse.getId()).apply();
                                    preferences.edit().putString("fullName", fullName).apply();
                                    preferences.edit().putString("token", response.headers().get("x-auth")).apply();
                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                                else if(response.code() == 409){
                                    Toast.makeText(getApplicationContext(), R.string.userExists, Toast.LENGTH_SHORT).show();
                                    LoginManager.getInstance().logOut();
                                }

                                else if(response.code() == 400){
                                    Toast.makeText(getApplicationContext(), R.string.detailsError, Toast.LENGTH_SHORT).show();
                                    LoginManager.getInstance().logOut();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), R.string.errorTryLater, Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<LoginResponse> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), R.string.errorTryLater,Toast.LENGTH_SHORT).show();
                            }
                        });
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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}