package com.example.myapplication.ui.fragments;

import android.Manifest;


import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.ui.adapters.ApiAdapter;
import com.example.myapplication.api.Hit;
import com.example.myapplication.api.HitClass;
import com.example.myapplication.api.RecepiesInterface;
import com.example.myapplication.viewmodel.ApiViewModel;
import com.example.myapplication.viewmodel.PlanViewModel;
import com.example.myapplication.viewmodel.RecipeViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeSearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private ApiAdapter apiAdapter;
    private ProgressBar progressBar;
    private EditText editTextSearch;
    private ApiViewModel apiViewModel;


    public RecipeSearchFragment() {
        // Required empty public constructor
    }
    public interface  OnSelectedRecipeDetailsListener{
        void onSelectedRecipeDetails(Bundle bundle);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_recipe, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewRecipes);
        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(apiAdapter);
        progressBar = view.findViewById(R.id.indeterminateBar);
        progressBar.setVisibility(View.INVISIBLE);
        editTextSearch = view.findViewById(R.id.editTextSearch);

        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String searchText = editTextSearch.getText().toString();
                    if (!searchText.equals("") && isOnline()) {
                        progressBar.setVisibility(View.VISIBLE);
                        apiViewModel.loadData(searchText);
                    }
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get a new or existing ViewModel from the ViewModelProvider.
        apiViewModel = new ViewModelProvider(this).get(ApiViewModel.class);
        apiViewModel.getHits().observe(getViewLifecycleOwner(), hits -> {
            // Update the cached copy of the plans in the adapter.
            apiAdapter.setHitList(hits);
            progressBar.setVisibility(View.INVISIBLE);
        });

    }

    private boolean isOnline(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
    

    



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiAdapter = new ApiAdapter(getContext());
    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
