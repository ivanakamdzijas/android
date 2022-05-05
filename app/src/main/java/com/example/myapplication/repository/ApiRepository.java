package com.example.myapplication.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.RetrofitClient;
import com.example.myapplication.api.Hit;
import com.example.myapplication.api.HitClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepository {

    private final MutableLiveData<List<Hit>> allHits;
    static final String apikey = "a674ae7f68675fbf1f0c590239a55073";
    static final String apiid = "3c265af6";


    public ApiRepository(Application application) { //application is subclass of context
        //cant call abstract func but since instance is there we can do this
        allHits = new MutableLiveData<>();
    }

    public LiveData<List<Hit>> getHits() {
        return allHits;
    }

    public void callAPI(String searchText){
        Call<HitClass> call = RetrofitClient.getInstance().getApi().getRecepies(searchText, apiid, apikey);
        call.enqueue(new Callback<HitClass>() {
            @Override
            public void onResponse(Call<HitClass> call, Response<HitClass> response) {
                if (response.code() == 200) {
                    HitClass recipesResponse = response.body();
                    allHits.setValue(recipesResponse.getHits());
                }
            }

            @Override
            public void onFailure(Call<HitClass> call, Throwable t) {

            }
        });
    }

}



