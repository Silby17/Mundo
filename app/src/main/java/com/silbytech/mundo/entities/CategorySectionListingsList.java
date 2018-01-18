package com.silbytech.mundo.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class CategorySectionListingsList implements Serializable {

    @SerializedName("category_listings")
    private ArrayList<CategorySectionListings> fullListings;

    public CategorySectionListingsList(ArrayList<CategorySectionListings> fullListings) {
        this.fullListings = fullListings;
    }

    public ArrayList<CategorySectionListings> getFullListings() {
        return fullListings;
    }

    public void setFullListings(ArrayList<CategorySectionListings> fullListings) {
        this.fullListings = fullListings;
    }
}
