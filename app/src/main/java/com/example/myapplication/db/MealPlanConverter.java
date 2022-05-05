package com.example.myapplication.db;

import androidx.room.TypeConverter;

import com.example.myapplication.db.entity.HealthLabels;
import com.example.myapplication.db.entity.Images;
import com.example.myapplication.db.entity.IngridientLines;
import com.example.myapplication.db.entity.Meals;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MealPlanConverter {

    @TypeConverter
    public static Date fromTimestamp(Long value){
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date){
        return date == null ? null : date.getTime();
    }


    @TypeConverter
    public Meals storedStringToMeals(String value){
        List<String> meals = Arrays.asList(value.split("\\s*,\\s*"));
        return new Meals(meals);
    }

    @TypeConverter
    public IngridientLines storedStringToIngridientLines(String value){
        List<String> ingridientLines = Arrays.asList(value.split("\\s*,\\s*"));
        return new IngridientLines(ingridientLines);
    }

    @TypeConverter
    public String ingridientLinesToStoredString(IngridientLines ingridientLines){
        String value = "";

        for(String ingr: ingridientLines.getIngridientLines()){
            value += ingr + ",";
        }
        return value;
    }





    @TypeConverter
    public HealthLabels storedStringToHealthLabels(String value){
        List<String> healthLabels = Arrays.asList(value.split("\\s*,\\s*"));
        return new HealthLabels(healthLabels);
    }

    @TypeConverter
    public String healthLabelsToStoredString(HealthLabels healthLabels){
        String value = "";

        for(String lab: healthLabels.getHealthLabels()){
            value += lab + ",";
        }
        return value;
    }
}
