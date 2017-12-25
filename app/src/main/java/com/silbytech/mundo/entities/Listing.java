package com.silbytech.mundo.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class Listing implements Serializable {

    @SerializedName("_id")
    private String id;

    @SerializedName("imageUrl")
    private String imgUrl;

    @SerializedName("_creator")
    private String creatorId;

    @SerializedName("category")
    private String category;

    @SerializedName("description")
    private String description;

    @SerializedName("price")
    private double price;

    public Listing(String id, String imgUrl, String creatorId, String category, String description, double price) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.creatorId = creatorId;
        this.category = category;
        this.description = description;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
