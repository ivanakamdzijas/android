package com.example.myapplication.db.entity;

import java.io.Serializable;
import java.util.List;

public class Images implements Serializable {

    private List<String> images;

    public Images(List<String> images){
        this.images = images;
    }

    public List<String> getImages(){
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
