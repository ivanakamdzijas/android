package com.example.myapplication.ui.adapters;



import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.db.entity.Meal;

import com.example.myapplication.db.entity.Plan;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PlanDetailsAdapter extends RecyclerView.Adapter<PlanDetailsAdapter.PlanDetailsAdapterViewHolder> {
    private  List<Meal> mealList;
    private Context context;



    public PlanDetailsAdapter(final Context context, Plan plan) {
        this.context = context;
        this.mealList = plan.getMeals();
    }





    @NonNull
    @Override
    public PlanDetailsAdapter.PlanDetailsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_plan_details_item, parent, false);
        return  new PlanDetailsAdapter.PlanDetailsAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanDetailsAdapter.PlanDetailsAdapterViewHolder holder, int position) {
        String formattedTime = new SimpleDateFormat("HH:mm").format(mealList.get(position).getTime());
        String text = formattedTime +" h";
        holder.textViewMealTime.setText(text);
        holder.textViewMealTitle.setText(String.valueOf(mealList.get(position).getMyRecepie().getTitle()));

        if(mealList.get(position).getImage() == null){
            Log.i("RecipeActivity", "NULL");
            holder.coverImage.setVisibility(View.GONE);
        }else {
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(context));
            builder.build()
                    .load(mealList.get(position).getImage())
                    .placeholder(R.drawable.ic_baseline_image_24)
                    .fit()
                    .into(holder.coverImage);
        }
    }

    @Override
    public int getItemCount() {
        return mealList == null ? 0 : mealList.size();
    }

    public static class PlanDetailsAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewMealTitle;
        private  TextView textViewMealTime;
        private  ImageView coverImage;


        public PlanDetailsAdapterViewHolder(View v) {
            super(v);
            textViewMealTitle = v.findViewById(R.id.textViewTitle);
            textViewMealTime = v.findViewById(R.id.textViewTime);
            coverImage = v.findViewById(R.id.coverImageInMeal);
        }
    }
}