package com.silbytech.mundo.responses;

import com.google.gson.annotations.SerializedName;
import com.silbytech.mundo.entities.ListingModel;

import java.io.Serializable;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class SingleListingServerResponse implements Serializable {

    @SerializedName("listing")
    private ListingModel listingModel;

    public SingleListingServerResponse(ListingModel listingModel) {
        this.listingModel = listingModel;
    }

    public ListingModel getListingModel() {
        return listingModel;
    }

    public void setListingModel(ListingModel listingModel) {
        this.listingModel = listingModel;
    }
}
