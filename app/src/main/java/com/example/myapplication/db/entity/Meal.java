package com.example.myapplication.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.RoomWarnings;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.NO_ACTION;

@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
@Entity(tableName = "meal",
        foreignKeys = {
        @ForeignKey(
                entity = MealPlan.class,
                parentColumns = "planId",
                childColumns = "mealPlanId",
                onDelete = ForeignKey.CASCADE
        ),

},indices = {@Index("mealPlanId")}/*, @Index(value = "selected")}*/)
public class Meal  implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "mealPlanId")
    private Integer mealPlanId;

    @ColumnInfo(name = "time")
    private long time;


    /*@ColumnInfo(name="selected")
    private boolean selected;*/

    @Embedded
    private MyRecepie myRecepie;

    @ColumnInfo(name = "image")
    private String image;

    @Override
    public String toString() {
        return myRecepie.getTitle()+"-"+time+"-"+image + "-"+id;
    }

    public Integer getMealPlanId() {
        return mealPlanId;
    }

    public void setMealPlanId(Integer mealPlanId) {
        this.mealPlanId = mealPlanId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MyRecepie getMyRecepie() {
        return myRecepie;
    }

    public void setMyRecepie(MyRecepie myRecepie) {
        this.myRecepie = myRecepie;
    }




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

}

