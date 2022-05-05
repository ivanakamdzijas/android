package com.example.myapplication.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient{

    @SerializedName("text")
    @Expose
    private String text;

    @SerializedName("weight")
    @Expose
    private double weight;

    @SerializedName("image")
    @Expose
    private String image;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

