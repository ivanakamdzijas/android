package com.example.myapplication.db.entity;

import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(tableName = "meal_plan", indices ={@Index(value = "date", unique = true)})
public class MealPlan implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer planId;

    @ColumnInfo(name = "date")
    @NonNull
    private Date date;//ovo kasnije Date?

    //@ColumnInfo(name = "meals list")
    //private Meals meals;

    @ColumnInfo(name = "sum_calories")
    private Integer sumCalories;


    @Override
    public String toString() {
        return "MealPlan{" +
                "id=" + planId +
                ", date=" + date +
                ", sumCalories=" + sumCalories +
                '}';
    }

    public Integer getSumCalories() {
        return sumCalories;
    }

    public void setSumCalories(Integer sumCalories) {
        this.sumCalories = sumCalories;
    }

    /*public Meals getMeals() {
        return meals;
    }*/

    /*public void setMeals(Meals meals) {
        this.meals = meals;
    }*/

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer id) {
        this.planId = id;
    }



}

