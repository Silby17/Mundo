package com.silbytech.mundo.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class CategoriesList implements Serializable {

    @SerializedName("categories")
    private List<Category> categoryList;

    public CategoriesList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
