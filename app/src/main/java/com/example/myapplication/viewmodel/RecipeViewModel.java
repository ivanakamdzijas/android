package com.example.myapplication.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.myapplication.repository.DataRepository;
import com.example.myapplication.db.entity.MyRecepie;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private DataRepository mRepository;
    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    private final LiveData<List<MyRecepie>> mSelectedRecipes;

    public RecipeViewModel(Application application) {
        super(application);
        mRepository = new DataRepository(application);
        mSelectedRecipes = mRepository.getSelectedRecipes();
    }

    public LiveData<List<MyRecepie>> getSelectedRecipes() {
        return mSelectedRecipes;
    }

    public LiveData<List<MyRecepie>> getAll(){
        return mRepository.getAllRecepies();
    }

    public  void update(MyRecepie recipe, boolean checked){
       mRepository.update(recipe, checked);
    }

    public void unselectRecipes() {
        for (MyRecepie recipe : mSelectedRecipes.getValue()) {
            //recipe.setStatus(false);
            update(recipe, false);
        }
    }

    public void addMyRecipe(MyRecepie recipe) {
        mRepository.addMyRecipe(recipe);
    }



}
