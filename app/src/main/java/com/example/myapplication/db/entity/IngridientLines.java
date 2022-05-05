package com.example.myapplication.db.entity;

import java.io.Serializable;
import java.util.List;

public class IngridientLines implements Serializable {


    private List<String> ingridientLines;

    public IngridientLines(List<String> ingridientLines){
        this.ingridientLines = ingridientLines;
    }

    public List<String> getIngridientLines(){
        return ingridientLines;
    }

    public void setIngridientLines(List<String> ingridientLines) {
        this.ingridientLines = ingridientLines;
    }
}
