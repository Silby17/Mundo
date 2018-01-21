package com.silbytech.mundo;

import android.os.Message;

import com.google.gson.Gson;
import com.silbytech.mundo.communication.Interface;
import com.silbytech.mundo.entities.CategoriesList;
import com.silbytech.mundo.entities.CategorySectionListingsList;
import com.silbytech.mundo.entities.ListingsList;
import com.silbytech.mundo.responses.LoginResponse;
import com.silbytech.mundo.responses.MessageResponse;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class Communicator {
    private static final String TAG = "Communicator";
    private static final String HURL = "https://mundo-84767.herokuapp.com/";
    //private static final String HURL = "http://192.168.1.14:3000";

    Call<LoginResponse> userLogin(String email, String password){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(HURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        Interface comInter = retrofit.create(Interface.class);
        return comInter.userLogin(email, password);
    }

    Call<LoginResponse> userSignUp(String firstName, String surname, String email,
                                   String password){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(HURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        Interface comInterface = retrofit.create(Interface.class);
        return comInterface.userSignUp(firstName, surname, email, password);
    }

    Call<MessageResponse> uploadFIle(String desc, Map<String, RequestBody> map) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(HURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        Interface comInterface = retrofit.create(Interface.class);
        return comInterface.upload(desc, map);
    }


    /**********************************************************************************
     * This Call will get all the categories from the server and return the call
     * @return the call to me made
     **********************************************************************************/
    public Call<CategoriesList> getAllCategories() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(HURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        Interface comInterface = retrofit.create(Interface.class);
        return comInterface.getAllCategories();
    }


    public Call<ListingsList> getListingsByCategory(String categoryName){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(HURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        Interface comInterface = retrofit.create(Interface.class);
        return comInterface.getListingsByCategory(categoryName);
    }

    public Call<CategorySectionListingsList> getListingsByCategory(){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(HURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        Interface comInterface = retrofit.create(Interface.class);
        return comInterface.getListingsByCategory();
    }


    public Call<ListingsList> getUsersListings(String userToken){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(HURL)
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        Interface comInterface = retrofit.create(Interface.class);

        return comInterface.getUsersListings(userToken);
    }
}
