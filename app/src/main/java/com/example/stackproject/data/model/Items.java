package com.example.stackproject.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Items {

    @SerializedName("items")
    @Expose
    private List<Repo> items = null;

    public Items(List<Repo> items) {
        this.items = items;
    }

    public List<Repo> getItems() {
        return items;
    }

    public void setItems(List<Repo> items) {
        this.items = items;
    }
}
