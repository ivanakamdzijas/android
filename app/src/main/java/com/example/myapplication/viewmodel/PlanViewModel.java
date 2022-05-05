package com.example.myapplication.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.repository.DataRepository;
import com.example.myapplication.db.entity.Meal;
import com.example.myapplication.db.entity.MealPlan;
import com.example.myapplication.db.entity.Plan;

import java.util.List;



public class PlanViewModel extends AndroidViewModel {

    private DataRepository mRepository;
    // Using LiveData and caching what getAllPlans returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private final LiveData<List<Plan>> mAllPlans;

    public PlanViewModel(Application application) {
        super(application);
        mRepository = new DataRepository(application);
        mAllPlans = mRepository.getAllPlans();
    }

    public LiveData<List<Plan>> getAllPlans() {
        return mAllPlans;
    }

    public void insert(Plan plan) {
          mRepository.createPlan(plan);
    }

    public LiveData<Plan> getInsertedPlan(){
        return mRepository.getInsertedPlan();
    }
    public LiveData<List<Meal>> getMeals(long mealPlanId){
        return mRepository.getMeals(mealPlanId);
    }
    
    public LiveData<List<MealPlan>> getPlanList(){
        return mRepository.getAllMealPlans();
    }


}
