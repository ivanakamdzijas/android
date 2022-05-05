package com.example.myapplication.db.dao;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.myapplication.db.entity.Meal;
import com.example.myapplication.db.entity.MealPlan;
import com.example.myapplication.db.entity.Plan;

import java.util.List;

@Dao
public abstract class PlanDaoClass implements PlanDao{

    @Update
    abstract void update(Meal meal);

    //kada se dodaje plan sa datumom koji vec postoji, radi se zamena
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long insertMealPlan(MealPlan mealPlan);

    @Insert
    public abstract void insert(List<Meal> meals);

    //crucial method
    //return meal plan id
    @Transaction
    public long insert(Plan plan) {
        long id = insertMealPlan(plan.getMealPlan());
        Log.i("RecipeActivity", "plan id     "+id);
        for(Meal m: plan.getMeals()){
            //m.setMealPlanId((int)id);
            m.setMealPlanId((int)id);
            update(m);
        }
        insert(plan.getMeals());
        return id;

    }

    @Transaction
    @Query("SELECT * FROM meal_plan ORDER BY date DESC")
    public abstract LiveData<List<Plan>> getAll();

    @Transaction
    @Query("SELECT * FROM meal WHERE meal.mealPlanId = :id")
    public abstract LiveData<List<Meal>> getMeals(long id);

    @Query("SELECT * FROM meal_plan")
    public abstract  LiveData<List<MealPlan>> getAllMealPlans();

    @Transaction
    @Query("SELECT * FROM meal_plan WHERE planId = :id")
    public abstract  Plan getMealPlanById(long id);



}
