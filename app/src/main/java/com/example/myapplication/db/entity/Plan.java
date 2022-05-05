package com.example.myapplication.db.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

public class Plan implements Serializable {
    @Embedded
    private MealPlan mealPlan;
    @Relation(parentColumn = "planId", entityColumn = "mealPlanId")
    private List<Meal> meals;

    @Override
    public String toString() {
        return "Plan{" +
                "mealPlan=" + mealPlan +
                ", meals=" + meals +
                '}';
    }

    public MealPlan getMealPlan() {
        return mealPlan;
    }

    public void setMealPlan(MealPlan mealPlan) {
        this.mealPlan = mealPlan;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}