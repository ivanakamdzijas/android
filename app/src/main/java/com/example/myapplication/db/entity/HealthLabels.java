package com.example.myapplication.db.entity;

import java.io.Serializable;
import java.util.List;

public class HealthLabels implements Serializable {

    @Override
    public String toString() {
        StringBuilder rez = new StringBuilder();
        for(int i = 0; i < healthLabels.size()-1; i++){
            rez.append(healthLabels.get(i)+ ", ");
        }
        rez.append(healthLabels.get(healthLabels.size()-1));
        return rez.toString();
    }

    private List<String> healthLabels;

    public HealthLabels(List<String> healthLabels){
        this.healthLabels = healthLabels;
    }

    public List<String> getHealthLabels(){
        return healthLabels;
    }

    public void setHealthLabels(List<String> healthLabels) {
        this.healthLabels = healthLabels;
    }
}
