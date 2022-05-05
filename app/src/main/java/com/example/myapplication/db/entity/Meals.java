package com.example.myapplication.db.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Meals implements Serializable {
    private List<String> meals;



    public Meals(List<String> meals){
        this.meals = meals;
    }

    public List<String> getMeals(){
        return meals;
    }

    public void setMeals(List<String> meals) {
        this.meals = meals;
    }




}
