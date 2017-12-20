package com.silbytech.mundo.communication;

import com.silbytech.mundo.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public interface Interface {

    @FormUrlEncoded
    @POST("/users/login/")
    Call<LoginResponse> userLogin(@Field("email") String email,
                                  @Field("password") String Password);
}
