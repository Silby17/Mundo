package com.silbytech.mundo.communication;

import com.silbytech.mundo.entities.CategoriesList;
import com.silbytech.mundo.entities.CategorySectionListingsList;
import com.silbytech.mundo.entities.ListingsList;
import com.silbytech.mundo.responses.LoginResponse;
import com.silbytech.mundo.responses.MessageResponse;
import com.silbytech.mundo.responses.Response;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public interface Interface {

    @FormUrlEncoded
    @POST("/users/login/")
    Call<LoginResponse> userLogin(@Field("email") String email,
                                  @Field("password") String Password);

    @FormUrlEncoded
    @POST("/users/")
    Call<LoginResponse> userSignUp(@Field("firstName") String firstName,
                                   @Field("surname") String lastName,
                                   @Field("email") String email,
                                   @Field("password") String password);

    @Multipart
    @POST("/listing/")
    Call<ResponseBody> uploadPhoto(@Part("desc") RequestBody description,
                                   @Part("myFile") MultipartBody.Part myFile);

    @Multipart
    @POST("/listing/")
    Call<MessageResponse> upload(
            @Part("desc") String description,
            @PartMap Map<String, RequestBody> map
    );

    @GET("/testing")
    Call<Response> getTesting();

    @Multipart
    @POST("/upload")
    Call<ResponseBody> upload(
            @Part("description") RequestBody description,
            @Part MultipartBody.Part file);

    @Multipart
    @POST("/upload")
    Call<Response> uploadImage(@Part("description") RequestBody description,
                               @Part MultipartBody.Part image);

    @GET("/categories")
    Call<CategoriesList> getAllCategories();


    @GET("/listings/{category}/category")
    Call<ListingsList> getListingsByCategory(@Path("category") String categoryName);


    @GET("/categories/listings")
    Call<CategorySectionListingsList> getListingsByCategory();
}
