package com.silbytech.mundo;

import com.silbytech.mundo.communication.Interface;
import com.silbytech.mundo.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class Communicator {
    private static final String TAG = "Communicator";
    private static final String HURL = "https://mundo-84767.herokuapp.com/";

    Call<LoginResponse> userLogin(String email, String password){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(HURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        Interface comInter = retrofit.create(Interface.class);
        return comInter.userLogin(email, password);
    }
}
