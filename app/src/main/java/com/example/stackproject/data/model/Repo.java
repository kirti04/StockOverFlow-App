package com.example.stackproject.data.model;

import com.google.gson.annotations.SerializedName;

public class Repo{

    @SerializedName("user_id")
    private int id;
    @SerializedName("display_name")
    private String name;
    @SerializedName("reputation")
    private int reputation;
    @SerializedName("creation_date")
    private int creation_date;
    @SerializedName("profile_image")
    private String url_image;
    @SerializedName("location")
    private String location;
    @SerializedName("badge_counts")
    private BadgeCounts badgeCounts;


    public Repo(int id, String name, int reputation, int creation_date, String url_image, String location, BadgeCounts badgeCounts) {
        this.id = id;
        this.name = name;
        this.creation_date = creation_date;
        this.location = location;
        this.reputation = reputation;
        this.url_image = url_image;
        this.badgeCounts = badgeCounts;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCreation_date() {
        return creation_date;
    }

    public int getReputation() {
        return reputation;
    }

    public String getLocation() {
        return location;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreation_date(int creation_date) {
        this.creation_date = creation_date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public BadgeCounts getBadgeCounts() {
        return badgeCounts;
    }

    public void setBadgeCounts(BadgeCounts badgeCounts) {
        this.badgeCounts = badgeCounts;
    }

}

