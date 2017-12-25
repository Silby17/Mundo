package com.silbytech.mundo.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class ListingsArray implements Serializable {

    @SerializedName("listings")
    private List<Listing> listings;

    public ListingsArray(List<Listing> listings) {
        this.listings = listings;
    }

    public List<Listing> getListings() {
        return listings;
    }

    public void setListings(List<Listing> listings) {
        this.listings = listings;
    }
}
