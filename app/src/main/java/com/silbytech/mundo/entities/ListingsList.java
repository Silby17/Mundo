package com.silbytech.mundo.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class ListingsList implements Serializable{

    @SerializedName("listings")
    private ArrayList<ListingModel> listingsList;

    public ListingsList(ArrayList<ListingModel> listingsList) {
        this.listingsList = listingsList;
    }

    public ArrayList<ListingModel> getListingsList() {
        return listingsList;
    }

    public void setListingsList(ArrayList<ListingModel> listingsList) {
        this.listingsList = listingsList;
    }
}
