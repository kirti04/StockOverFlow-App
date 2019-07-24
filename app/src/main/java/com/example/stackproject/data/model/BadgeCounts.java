package com.example.stackproject.data.model;

import com.google.gson.annotations.SerializedName;

public class BadgeCounts {
    @SerializedName("bronze")
    private int bronze;
    @SerializedName("silver")
    private int silver;
    @SerializedName("gold")
    private int gold;

    public BadgeCounts(int gold, int silver, int bronze){
        this.gold = gold;
        this.silver = silver;
        this.bronze = bronze;
    }

    public int getBronze() {
        return bronze;
    }

    public int getGold() {
        return gold;
    }

    public int getSilver() {
        return silver;
    }

    public void setBronze(int bronze) {
        this.bronze = bronze;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public void setSilver(int silver) {
        this.silver = silver;
    }
}
