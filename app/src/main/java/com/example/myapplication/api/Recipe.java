package com.example.myapplication.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class Recipe {

    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("label")
    @Expose
    private String label;

    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("source")
    @Expose
    private String source;

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("shareAs")
    @Expose
    private String shareAs;
    @SerializedName("yield")
    @Expose
    private double yield;

    @SerializedName("dietLabels")
    @Expose
    private String[] dietLabels;

    @SerializedName("healthLabels")
    @Expose
    private String[] healthLabels;

    @SerializedName("cautions")
    @Expose
    private String[] cautions;

    public String[] getCautions() {
        return cautions;
    }

    public void setCautions(String[] cautions) {
        this.cautions = cautions;
    }

    public String[] getIngredientLines() {
        return ingredientLines;


    }

    public void setIngredientLines(String[] ingredientLines) {
        this.ingredientLines = ingredientLines;
    }

    @SerializedName("ingredientLines")
    @Expose
    private String[] ingredientLines;

    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> ingredients = null;

    @SerializedName("calories")
    @Expose
    private double calories;

    @SerializedName("totalWeight")
    @Expose
    private double totalWeight;
    @SerializedName("totalTime")
    @Expose
    private double totalTime;

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public String[] getDietLabels() {
        return dietLabels;
    }

    public void setDietLabels(String[] dietLabels) {
        this.dietLabels = dietLabels;
    }

    public String[] getHealthLabels() {
        return healthLabels;
    }

    public void setHealthLabels(String[] healthLabels) {
        this.healthLabels = healthLabels;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShareAs() {
        return shareAs;
    }

    public void setShareAs(String shareAs) {
        this.shareAs = shareAs;
    }

    public double getYield() {
        return yield;
    }

    public void setYield(double yield) {
        this.yield = yield;
    }




    @Override
    public String toString() {
        return "Recipe{" +
                "uri='" + uri + '\'' +
                ", label='" + label + '\'' +
                ", image='" + image + '\'' +
                ", source='" + source + '\'' +
                ", url='" + url + '\'' +
                ", shareAs='" + shareAs + '\'' +
                ", yield=" + yield +
                ", dietLabels=" + Arrays.toString(dietLabels) +
                ", healthLabels=" + Arrays.toString(healthLabels) +
                ", cautions=" + Arrays.toString(cautions) +
                ", ingredientLines=" + Arrays.toString(ingredientLines) +
                ", ingredients=" + ingredients +
                ", calories=" + calories +
                ", totalWeight=" + totalWeight +
                ", totalTime=" + totalTime +
                '}';
    }
}
