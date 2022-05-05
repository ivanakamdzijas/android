package com.example.myapplication.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "my_recepie")
public class MyRecepie implements  Serializable {

    @ColumnInfo(name="recipe_id")
    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "title")
    private String title;

    private String url;

    @ColumnInfo(name = "status")
    private boolean status;

    private IngridientLines ingridientLines;
    private HealthLabels healthLabels;
    private int numPersons;
    private int prepTime;
    //private Images images;
    private String recipeImage;
    private int calories;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public IngridientLines getIngridientLines() {
        return ingridientLines;
    }

    public void setIngridientLines(IngridientLines ingridientLines) {
        this.ingridientLines = ingridientLines;
    }

    public HealthLabels getHealthLabels() {
        return healthLabels;
    }

    public void setHealthLabels(HealthLabels healthLabels) {
        this.healthLabels = healthLabels;
    }

    public int getNumPersons() {
        return numPersons;
    }

    public void setNumPersons(int numPersons) {
        this.numPersons = numPersons;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    /*public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }*/

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String image) {
        this.recipeImage = image;
    }
    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "MyRecepie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", status=" + status +
                ", ingridientLines=" + ingridientLines +
                ", healthLabels=" + healthLabels +
                ", numPersons=" + numPersons +
                ", prepTime=" + prepTime +
                ", image=" + recipeImage +
                ", calories=" + calories +
                '}';
    }
}

