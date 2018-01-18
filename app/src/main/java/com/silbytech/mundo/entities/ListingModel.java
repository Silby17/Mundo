package com.silbytech.mundo.entities;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class ListingModel implements Serializable {

    @SerializedName("_id")
    private String id;

    @SerializedName("_creator")
    private String creatorID;

    @SerializedName("title")
    private String title;

    @SerializedName("category")
    private String category;

    @SerializedName("description")
    private String description;

    @SerializedName("dailyPrice")
    private double dailyPrice;

    @SerializedName("weeklyPrice")
    private double weeklyPrice;

    @SerializedName("monthlyPrice")
    private double monthlyPrice;

    @SerializedName("imageUrl")
    private ArrayList<String> imageUrls;

    public ListingModel(String id, String creatorID, String title, String category,
                        String description, double dailyPrice, double weeklyPrice,
                        double monthlyPrice, ArrayList<String> imageUrls) {
        this.id = id;
        this.creatorID = creatorID;
        this.title = title;
        this.category = category;
        this.description = description;
        this.dailyPrice = dailyPrice;
        this.weeklyPrice = weeklyPrice;
        this.monthlyPrice = monthlyPrice;
        this.imageUrls = imageUrls;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public double getWeeklyPrice() {
        return weeklyPrice;
    }

    public void setWeeklyPrice(double weeklyPrice) {
        this.weeklyPrice = weeklyPrice;
    }

    public double getMonthlyPrice() {
        return monthlyPrice;
    }

    public void setMonthlyPrice(double monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
