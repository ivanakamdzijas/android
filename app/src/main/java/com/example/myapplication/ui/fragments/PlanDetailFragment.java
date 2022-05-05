package com.example.myapplication.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.db.entity.Meal;
import com.example.myapplication.ui.adapters.PlanDetailsAdapter;
import com.example.myapplication.db.entity.Plan;
import com.example.myapplication.viewmodel.PlanViewModel;

import java.util.ArrayList;
import java.util.List;


public class PlanDetailFragment extends Fragment {

    private RecyclerView recyclerView;
    private PlanDetailsAdapter adapter;
    private Plan plan;


    public PlanDetailFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_plan_details, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewPlanDetails);
        // use a linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.plan = (Plan) getArguments().getSerializable("plan");
        }
        adapter = new PlanDetailsAdapter(getContext(), plan);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

}

