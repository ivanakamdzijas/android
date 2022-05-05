package com.example.myapplication.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.db.AppDatabase;
import com.example.myapplication.db.dao.MealDao;
import com.example.myapplication.db.dao.PlanDaoClass;
import com.example.myapplication.db.dao.RecipeDao;
import com.example.myapplication.db.entity.Meal;
import com.example.myapplication.db.entity.MealPlan;
import com.example.myapplication.db.entity.MyRecepie;
import com.example.myapplication.db.entity.Plan;

import java.util.List;

public class DataRepository{
    private PlanDaoClass mPlanDao;
    private RecipeDao mRecipeDao;
    private MealDao mMealDao;
    private LiveData<List<Plan>> mAllPlans;
    private LiveData<List<MealPlan>> mAllMealPlans;
    private LiveData<List<MyRecepie>> mSelectedRecipes;
    private LiveData<List<MyRecepie>> mAllRecipes;
    private MutableLiveData<Long> insertedId;
    private MutableLiveData<Plan> insertedPlan;
    private MutableLiveData<Plan> planForDetails;



    public DataRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mPlanDao = db.planDaoClass();
        mRecipeDao = db.recipeDao();
        mMealDao = db.mealDao();
        mAllPlans = mPlanDao.getAll();
        mSelectedRecipes = mRecipeDao.getSelectedRecipes();
        mAllRecipes = mRecipeDao.getAll();
        mAllMealPlans = mPlanDao.getAllMealPlans();
        insertedId = new MutableLiveData();
        insertedPlan = new MutableLiveData();
        planForDetails = new MutableLiveData();
    }


    public LiveData<Plan> getInsertedPlan() {
        return insertedPlan;
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Plan>> getAllPlans() {
        return mAllPlans;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void createPlan(Plan plan){
        AppDatabase.databaseWriteExecutor.execute(new Runnable(){
            @Override
            public void run() {
                Long mealPlanId =mPlanDao.insert(plan);
                insertedId.postValue(mealPlanId);
                getMealPlanById(mealPlanId);
                Log.i("RecipeActivity", "Ovo je value "+ insertedPlan);
            }
        });
    }

    public  void getMealPlanById(long id){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            Plan inserted = mPlanDao.getMealPlanById(id);
            insertedPlan.postValue(inserted);
        });
    }



    public LiveData<List<Meal>> getMeals(long id){
        return mPlanDao.getMeals(id);
    }

    public LiveData<List<MealPlan>> getAllMealPlans(){
        return mAllMealPlans;
    }

    public LiveData<List<MyRecepie>> getSelectedRecipes() {
        return mSelectedRecipes;
    }

    public  void update(MyRecepie recipe, boolean checked){
        recipe.setStatus(checked);
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mRecipeDao.update(recipe);
        });
    }

    public  void update(Meal meal){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mMealDao.update(meal);
        });
    }

    public LiveData<List<MyRecepie>> getAllRecepies(){
        return mAllRecipes;
    }

    public void addMyRecipe(MyRecepie recipe) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mRecipeDao.insert(recipe);
        });
    }

}
