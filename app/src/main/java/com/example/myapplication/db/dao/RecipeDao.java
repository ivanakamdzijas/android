package com.example.myapplication.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.myapplication.db.entity.MyRecepie;

import java.util.List;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM my_recepie ORDER BY recipe_id DESC")
    LiveData<List<MyRecepie>> getAll();

    @Query("SELECT * FROM my_recepie r WHERE r.status = 1 ")
    LiveData<List<MyRecepie>> getSelectedRecipes();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(MyRecepie myRecepie);

    @Update
    void update(MyRecepie myRecepie);

    @Delete
    void delete(MyRecepie myRecepie);




}
