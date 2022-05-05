package com.example.myapplication.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;


import com.example.myapplication.R;
import com.example.myapplication.db.entity.Meal;

import com.example.myapplication.db.entity.MyRecepie;
import com.example.myapplication.db.entity.Plan;
import com.example.myapplication.ui.fragments.AddNewPlanFragment;
import com.example.myapplication.ui.fragments.MyRecepiesFragment;
import com.example.myapplication.ui.fragments.PlanDetailFragment;
import com.example.myapplication.ui.fragments.PlanDiagramFragment;
import com.example.myapplication.ui.fragments.PlanListFragment;
import com.example.myapplication.ui.fragments.RecipeSearchFragment;
import com.example.myapplication.ui.fragments.SaveNewRecipeFragment;
import com.example.myapplication.viewmodel.MealViewModel;
import com.example.myapplication.viewmodel.RecipeViewModel;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements
        AddNewPlanFragment.OnAddToListListener,
        PlanListFragment.OnItemSelectedListener, RecipeSearchFragment.OnSelectedRecipeDetailsListener,
        AddNewPlanFragment.OnSelectMealsListener, MyRecepiesFragment.OnMealsSelectedListener,
        SaveNewRecipeFragment.OnSaveRecipeListener, AddNewPlanFragment.OnCancelPlanListener,
        MyRecepiesFragment.CheckedChangedListener, AddNewPlanFragment.UpdateMealListener {



    private AddNewPlanFragment addNewFragment;
    private PlanListFragment listFragment;
    private PlanDetailFragment detailFragment;
    private RecipeSearchFragment recipeSearchFragment;
    private SaveNewRecipeFragment saveNewRecipeFragment;
    private PlanDiagramFragment planDiagramFragment;
    private MyRecepiesFragment myRecepiesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNewFragment = new AddNewPlanFragment();
        listFragment = new PlanListFragment();
        recipeSearchFragment = new RecipeSearchFragment();
        planDiagramFragment = new PlanDiagramFragment();
        myRecepiesFragment = new MyRecepiesFragment();
        detailFragment = new PlanDetailFragment();
        saveNewRecipeFragment = new SaveNewRecipeFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!isTwoPaneView()){
            transaction.add(R.id.frame, listFragment);
        } else {
            transaction.add(R.id.frameLeft, listFragment);
            transaction.add(R.id.frameRight, new Fragment());
        }
        transaction.commit();
    }

    public void showAddNewFragment(){
        if (!isTwoPaneView()){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame, addNewFragment);
            transaction.setReorderingAllowed(true);
            transaction.addToBackStack(null);
            transaction.commit();
            getSupportFragmentManager().executePendingTransactions();
        }
    }



    public void addToListFragment(Plan plan){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!isTwoPaneView()){
            transaction.replace(R.id.frame, listFragment);
        }else{
            transaction.remove(addNewFragment);
        }
        listFragment.addItem(plan);
        transaction.addToBackStack(null);
        transaction.commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    private boolean isTwoPaneView(){
        return findViewById(R.id.frame) == null;
    }


    @Override
    public void onItemSelected(Plan plan) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("plan", plan);
        detailFragment = new PlanDetailFragment();
        detailFragment.setArguments(bundle);
        Log.i("RecipeActivity", "PLAN ARG "+detailFragment.getArguments());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setReorderingAllowed(true);
        if (!isTwoPaneView()){
            transaction.replace(R.id.frame, detailFragment);
        }else{
            transaction.replace(R.id.frameRight, detailFragment);
        }
        transaction.addToBackStack(null);
        transaction.commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    public void onSelectedRecipeDetails(Bundle bundle){
        Bundle b = new Bundle();
        b.putBundle("bundle", bundle);
        saveNewRecipeFragment.setArguments(b);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setReorderingAllowed(true);
        if (!isTwoPaneView()){
            transaction.replace(R.id.frame, saveNewRecipeFragment);
        }else{
            transaction.replace(R.id.frameRight, saveNewRecipeFragment);
        }
        transaction.addToBackStack(null);
        transaction.commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    public void selectMealsListener(String sumKcal){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.hide(addNewFragment);
        if (!isTwoPaneView()){
            transaction.add(R.id.frame, myRecepiesFragment);
        }else{
            transaction.add(R.id.frameRight, myRecepiesFragment);
        }
        transaction.addToBackStack(null);
        transaction.commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    public void onMealsSelectedListener(ArrayList<Meal> meals, int sum) {
        Bundle b = new Bundle();
        b.putSerializable("meals",(ArrayList)meals);
        b.putInt("calories",sum);
        getSupportFragmentManager().setFragmentResult("requestKey", b);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.remove(myRecepiesFragment);
        transaction.show(addNewFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        getSupportFragmentManager().executePendingTransactions();
    }


    @Override
    public void onSaveRecipe(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setReorderingAllowed(true);
        if (!isTwoPaneView()){
            transaction.replace(R.id.frame, recipeSearchFragment);
        }else {
            transaction.replace(R.id.frameRight, recipeSearchFragment);
        }
        transaction.commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    public void cancelPlan() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setReorderingAllowed(true);
        if (!isTwoPaneView()){
            transaction.replace(R.id.frame, listFragment);
        }else{
            transaction.remove(addNewFragment);
        }
        transaction.addToBackStack(null);
        transaction.commit();
        getSupportFragmentManager().executePendingTransactions();
    }


    @Override
    public void onCheckedChangedListener(MyRecepie recepie, boolean checked) {
        RecipeViewModel recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        recipeViewModel.update(recepie, checked);
    }

    @Override
    public void updateMeal(Meal meal) {
        MealViewModel mealViewModel =  new ViewModelProvider(this).get(MealViewModel.class);
        mealViewModel.update(meal);
    }


    //MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        if(isTwoPaneView()) {
            MenuItem item = menu.findItem(R.id.my_meal_plans);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setReorderingAllowed(true);

        switch (item.getItemId()) {
            case R.id.recepies_search:
                if (!isTwoPaneView()){
                   transaction.replace(R.id.frame, recipeSearchFragment);
                }else{

                    transaction.replace(R.id.frameRight,recipeSearchFragment);
                }
                break;
            case R.id.my_meal_plans:
                if (!isTwoPaneView()) {
                    transaction.replace(R.id.frame, listFragment);
                }
                break;
            case R.id.new_plan:
                if (!isTwoPaneView()){
                    transaction.replace(R.id.frame, addNewFragment);
                }else{
                    transaction.replace(R.id.frameRight,addNewFragment);
                }
                break;
            case R.id.plan_history:
                if (!isTwoPaneView()){
                    transaction.replace(R.id.frame, planDiagramFragment);
                }else{
                    transaction.replace(R.id.frameRight, planDiagramFragment);
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        transaction.addToBackStack(null);
        transaction.commit();
        getSupportFragmentManager().executePendingTransactions();
        return true;
    }


}
