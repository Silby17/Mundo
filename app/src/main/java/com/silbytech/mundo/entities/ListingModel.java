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

    @SerializedName("hourlyPrice")
    private double hourlyPrice;

    @SerializedName("imageUrl")
    private ArrayList<String> imageUrls;

    @SerializedName("location")
    private String location;

    @SerializedName("active")
    private boolean active;

    public ListingModel(String id, String creatorID, String title, String category,
                        String description, double dailyPrice, double weeklyPrice,
                        double hourlyPrice, ArrayList<String> imageUrls,
                        String location, boolean active) {
        this.id = id;
        this.creatorID = creatorID;
        this.title = title;
        this.category = category;
        this.description = description;
        this.dailyPrice = dailyPrice;
        this.weeklyPrice = weeklyPrice;
        this.hourlyPrice = hourlyPrice;
        this.imageUrls = imageUrls;
        this.location = location;
        this.active = active;
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

    public double getHourlyPrice() {
        return hourlyPrice;
    }

    public void setHourlyPrice(double hourlyPrice) {
        this.hourlyPrice = hourlyPrice;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
