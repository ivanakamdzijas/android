package com.example.myapplication.ui.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.AlarmReceiver;
import com.example.myapplication.R;
import com.example.myapplication.db.entity.MealPlan;
import com.example.myapplication.ui.adapters.PlanListAdapter;
import com.example.myapplication.ui.adapters.MyRecipeAdapter;
import com.example.myapplication.db.entity.Meal;
import com.example.myapplication.db.entity.Plan;
import com.example.myapplication.viewmodel.PlanViewModel;
import com.example.myapplication.viewmodel.RecipeViewModel;


import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


import static android.content.Context.ALARM_SERVICE;

public class PlanListFragment extends Fragment {
    
    private RecyclerView recyclerView;
    private PlanListAdapter planListAdapter;
    private MyRecipeAdapter myRecipeAdapter;
    private PlanViewModel planViewModel;
    private RecipeViewModel recipeViewModel;


    public PlanListFragment() {
        // Required empty public constructor
    }


    public interface  OnItemSelectedListener{
        void onItemSelected(Plan plan);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(planListAdapter);
        return view;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        planListAdapter = new PlanListAdapter(getContext());
        myRecipeAdapter = new MyRecipeAdapter(getContext());
        planViewModel = new ViewModelProvider(this).get(PlanViewModel.class);
        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        recipeViewModel.getSelectedRecipes().observe(this, selected -> {
            // Update the cached copy of the selectd recepies in the adapter.
            myRecipeAdapter.setSelectedRecipesList(selected);
        });
        // Add an observer on the LiveData returned by getAlAllPlans.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        planViewModel.getAllPlans().observe(this, plans -> {
            // Update the cached copy of the plans in the adapter.
            planListAdapter.setPlanList(plans);
        });

        planViewModel.getInsertedPlan().observe(this, insertedPlan -> {
            recipeViewModel.unselectRecipes();
            Log.i("RecipeActivity", "inserted plan  "+ insertedPlan);
            Log.i("RecipeActivity", "meals from inserted plan "+ insertedPlan.getMeals());
            for(Meal m : insertedPlan.getMeals() ){
                notification(m, insertedPlan);
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public void addItem(Plan plan){
        planViewModel.insert(plan);
        Toast.makeText(getContext(), getString(R.string.added), Toast.LENGTH_SHORT).show();
    }


    private void notification(Meal meal, Plan plan){
        //Date date = plan.getDate();
        Date date = plan.getMealPlan().getDate();
        long mealTime = meal.getTime();
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("meal",meal);
        bundle.putSerializable("plan",plan);
        intent.putExtra("bundle",bundle);
        intent.putExtra("notificationId", meal.getId());//notificationId is actually meal id
        intent.putExtra("message","Would you like to take a picture of your meal " +meal.getMyRecepie().getTitle() +"?");
        // AlarmManager
        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(ALARM_SERVICE);
        long alarmStartTime = createNotificationStartTime(date, mealTime);
        // PendingIntent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getContext(), meal.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        // Set Alarm
        alarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, pendingIntent);
    }

    private long createNotificationStartTime(Date date, long mealTime) {
        // Create time.
        Calendar calTime = Calendar.getInstance();
        calTime.setTimeInMillis(mealTime);
        int hour = calTime.get(Calendar.HOUR);
        int minute = calTime.get(Calendar.MINUTE);
        int second = calTime.get(Calendar.SECOND);
        //Create date
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(date);
        int year = calDate.get(Calendar.YEAR);
        int month = calDate.get(Calendar.MONTH);
        int day = calDate.get(Calendar.DAY_OF_MONTH);
        //Combine date and time
        Calendar a = Calendar.getInstance();
        a.set(Calendar.HOUR, hour);
        a.set(Calendar.MINUTE, minute);
        a.set(Calendar.SECOND, second);
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month);
        a.set(Calendar.DAY_OF_MONTH,day);
        return  a.getTimeInMillis();
    }


}

