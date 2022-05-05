package com.example.myapplication.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.db.entity.Meal;


import java.util.List;

@Dao
public interface MealDao {

    @Query("SELECT * FROM meal")
    List<Meal> getAll();

    @Insert()
    void insert(Meal meal);

    @Update
    void update(Meal meal);

    @Delete
    void delete(Meal meal);

}

