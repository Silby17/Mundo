package com.silbytech.mundo.entities;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class CategoriesList implements Serializable {

    @SerializedName("categories")
    private ArrayList<Category> categoryList;

    public CategoriesList(ArrayList<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public ArrayList<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(ArrayList<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
