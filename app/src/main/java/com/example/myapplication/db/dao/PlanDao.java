package com.example.myapplication.db.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.myapplication.db.entity.Meal;
import com.example.myapplication.db.entity.MealPlan;
import com.example.myapplication.db.entity.Plan;


@Dao
public interface PlanDao {

    @Insert
    long insertMealPlan(MealPlan mealPlan);

    @Insert
    void insert(List<Meal> meals);

    @Transaction
    @Query("SELECT * FROM meal_plan")
    LiveData<List<Plan>> getAll();

    @Update
    void update(MealPlan plan);

    @Delete
    void delete(MealPlan plan);


}
