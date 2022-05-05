package com.example.myapplication.ui.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.db.entity.Meal;
import com.example.myapplication.db.entity.Plan;
import com.example.myapplication.ui.fragments.PlanListFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PlanListAdapter extends RecyclerView.Adapter<PlanListAdapter.DatabaseAdapterViewHolder> {
    private  List<Plan> plans ;
    private final Context context;
    private PlanListFragment.OnItemSelectedListener listener;


    public PlanListAdapter(Context context) {
        this.context = context;
        this.plans  = new ArrayList<>();
    }



    public void setPlanList(List<Plan> plans){
        this.plans = plans;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public DatabaseAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        DatabaseAdapterViewHolder vh = new DatabaseAdapterViewHolder(v);
        listener = (PlanListFragment.OnItemSelectedListener) context;
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull DatabaseAdapterViewHolder holder, int position) {
        String formattedDate = new SimpleDateFormat("dd.MM.yyyy").format(plans.get(position).getMealPlan().getDate());
        holder.textViewDate.setText(formattedDate);
        holder.caloriesPlan.setText(String.valueOf(plans.get(position).getMealPlan().getSumCalories()));
        List<Meal> meals = new ArrayList<>(plans.get(position).getMeals());
        String text = "";
        for(Meal m:meals){
            text += m.getMyRecepie().getTitle()+ "\n"   ;
        }
        holder.planMeals.setText(text);
        holder.buttonPlanDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemSelected(plans.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return plans == null ? 0 : plans.size();
    }

    public static class DatabaseAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewDate;
        private TextView caloriesPlan;
        private TextView planMeals;
        private Button buttonPlanDetails;


        public DatabaseAdapterViewHolder(View v) {
            super(v);
            textViewDate = v.findViewById(R.id.textViewDate);
            caloriesPlan = v.findViewById(R.id.caloriesPlan);
            planMeals = v.findViewById(R.id.planMeals);
            buttonPlanDetails = v.findViewById(R.id.buttonPlanDetails);
        }
    }



}
