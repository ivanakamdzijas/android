package com.example.myapplication.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hit {
    @SerializedName("recipe")
    @Expose
    private Recipe recipe;

    @SerializedName("bookmarked")
    @Expose
    private boolean bookmarked;

    @SerializedName("bought")
    @Expose
    private boolean bought;

    public Hit() {
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public boolean getBookmarked() {
        return bookmarked;
    }

    public void setBookmarked(boolean bookmarked) {
        this.bookmarked = bookmarked;
    }

    public boolean getBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    @Override
    public String toString() {
        return "Hit{" +
                "recipe=" + recipe +
                ", bookmarked=" + bookmarked +
                ", bought=" + bought +
                '}';
    }

}