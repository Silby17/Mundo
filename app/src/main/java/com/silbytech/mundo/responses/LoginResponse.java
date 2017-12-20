package com.silbytech.mundo.responses;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class LoginResponse implements Serializable {

    @SerializedName("_id")
    private String id;

    @SerializedName("email")
    private String email;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("surname")
    private String surname;

    @SerializedName("token")
    private String token;

    public LoginResponse(String id, String email, String firstName, String surname, String token) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.surname = surname;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}