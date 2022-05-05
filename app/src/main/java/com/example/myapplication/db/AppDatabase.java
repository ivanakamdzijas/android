package com.example.myapplication.db;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.myapplication.db.dao.MealDao;

import com.example.myapplication.db.dao.PlanDao;
import com.example.myapplication.db.dao.PlanDaoClass;
import com.example.myapplication.db.dao.RecipeDao;
import com.example.myapplication.db.entity.Meal;
import com.example.myapplication.db.entity.MealPlan;
import com.example.myapplication.db.entity.MyRecepie;


@Database(entities = {MealPlan.class, Meal.class, MyRecepie.class}, version = 18, exportSchema = false)
@TypeConverters(MealPlanConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract PlanDao planDao();
    public abstract PlanDaoClass planDaoClass();
    public abstract RecipeDao recipeDao();
    public abstract MealDao mealDao();

    public static AppDatabase getInstance(Context context) {
        if (appDatabase == null) {
            synchronized (AppDatabase.class) {
                appDatabase = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        "MealPlans.db").

                         //allowMainThreadQueries(). //SHOULD NOT BE USED IN PRODUCTION
                fallbackToDestructiveMigration().build();
            }
        }
        return appDatabase;
    }
}
