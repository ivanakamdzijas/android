package com.example.myapplication.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.myapplication.R;
import com.example.myapplication.ui.adapters.MyRecipeAdapter;
import com.example.myapplication.db.entity.Meal;
import com.example.myapplication.db.entity.MyRecepie;
import com.example.myapplication.viewmodel.PlanViewModel;
import com.example.myapplication.viewmodel.RecipeViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MyRecepiesFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyRecipeAdapter adapter;
    private Button buttonCreatePlan;
    private OnMealsSelectedListener onMealsSelectedListener;
    private RecipeViewModel recipeViewModel;
    private ProgressBar progressBar;


    public MyRecepiesFragment() {
        // Required empty public constructor
    }

    public interface OnMealsSelectedListener {
        void onMealsSelectedListener(ArrayList<Meal> meals, int sum);
    }

    public interface CheckedChangedListener {
        void onCheckedChangedListener(MyRecepie recepie, boolean checked);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_my_recipe, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMyRecipes);
        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(null);
        buttonCreatePlan = view.findViewById(R.id.buttonCreatePlan);
        progressBar = view.findViewById(R.id.indeterminateBar);
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setAdapter(adapter);

        buttonCreatePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Meal> meals = new ArrayList<>();
                for(MyRecepie recepie: adapter.getSelected()){
                    Meal m = new Meal();
                    m.setMyRecepie(recepie);
                    meals.add(m);
                }
                onMealsSelectedListener.onMealsSelectedListener(meals, adapter.getSum());
            }
        });
        return view;
    }
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get a new or existing ViewModel from the ViewModelProvider.
        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        recipeViewModel.getAll().observe(getViewLifecycleOwner(), recipes -> {
            adapter.setRecipesList(recipes);
            progressBar.setVisibility(View.INVISIBLE);
        });
        recipeViewModel.getSelectedRecipes().observe(getViewLifecycleOwner(), recipes -> {
            // Update the cached copy of the recipes in the adapter.
            adapter.setSelectedRecipesList(recipes);
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MyRecipeAdapter(getContext());
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onMealsSelectedListener = (OnMealsSelectedListener) context;
    }

}
