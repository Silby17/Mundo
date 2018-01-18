package com.silbytech.mundo.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class CategorySectionListings implements Serializable {

    @SerializedName("category_name")
    private String categoryName;

    @SerializedName("listing")
    private ArrayList<ListingModel> listingModels;

    public CategorySectionListings(String categoryName, ArrayList<ListingModel> listingModels) {
        this.categoryName = categoryName;
        this.listingModels = listingModels;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ArrayList<ListingModel> getListingModels() {
        return listingModels;
    }

    public void setListingModels(ArrayList<ListingModel> listingModels) {
        this.listingModels = listingModels;
    }
}
