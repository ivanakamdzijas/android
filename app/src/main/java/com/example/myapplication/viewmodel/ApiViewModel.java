package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.repository.ApiRepository;
import com.example.myapplication.api.Hit;

import java.util.List;

public class ApiViewModel extends AndroidViewModel {

    private final ApiRepository repository ;


    public ApiViewModel(@NonNull Application application) {
        super(application);
        repository = new ApiRepository(application);
    }
    public void loadData(String searchText) {
       repository.callAPI(searchText);
    }

    public LiveData<List<Hit>> getHits() {
        return repository.getHits();
    }



}
