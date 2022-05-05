package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.myapplication.repository.DataRepository;
import com.example.myapplication.db.entity.Meal;

public class MealViewModel extends AndroidViewModel {

    private DataRepository mRepository;

    public MealViewModel(Application application) {
        super(application);
        mRepository = new DataRepository(application);

    }

    public void update(Meal meal){
        mRepository.update(meal);
    }


}
