package com.example.myapplication.ui.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.api.Hit;
import com.example.myapplication.R;

import com.example.myapplication.db.entity.Plan;
import com.example.myapplication.ui.fragments.RecipeSearchFragment;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ApiAdapter extends RecyclerView.Adapter<ApiAdapter.CustomViewHolder> {

    private  List<Hit> hits;
    private RecipeSearchFragment.OnSelectedRecipeDetailsListener listener;
    private final Context context;

    public ApiAdapter(Context context) {
        this.context = context;
        this.hits  = new ArrayList<>();
    }

    public void setHitList(List<Hit> hits){
        this.hits = hits;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recipe_item, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        int kcal = (int)Math.rint(hits.get(position).getRecipe().getCalories()/hits.get(position).getRecipe().getYield());
        holder.txtTitle.setText(hits.get(position).getRecipe().getLabel() + " ("+ kcal +" kcal)");
        String[] healthLab = hits.get(position).getRecipe().getHealthLabels();
        for(int i = 0; i <healthLab.length-1; i++){
            holder.healthLabels.append(healthLab[i]);
            holder.healthLabels.append(", ");
        }
        holder.healthLabels.append(healthLab[healthLab.length-1]);
        Picasso.get()
                .load(hits.get(position).getRecipe().getImage())
                .placeholder((R.drawable.ic_baseline_image_24))
                .fit()
                .into(holder.coverImage);


        holder.buttonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int kcal = (int)Math.rint(hits.get(position).getRecipe().getCalories()/hits.get(position).getRecipe().getYield());
                Bundle bundle = new Bundle();
                bundle.putString("label",hits.get(position).getRecipe().getLabel() );
                bundle.putStringArray("ingredients", hits.get(position).getRecipe().getIngredientLines());
                bundle.putStringArray("healthLabels", hits.get(position).getRecipe().getHealthLabels());
                bundle.putString("numPersons",String.valueOf((int)Math.rint(hits.get(position).getRecipe().getYield())));
                bundle.putString("time",String.valueOf((int)Math.round(hits.get(position).getRecipe().getTotalTime())));
                bundle.putString("recipeOnWeb",hits.get(position).getRecipe().getUrl() );
                bundle.putString("calories",String.valueOf(kcal));
                bundle.putString("coverImage",hits.get(position).getRecipe().getImage());
                listener.onSelectedRecipeDetails(bundle);
            }
        });
    }


    @Override
    public int getItemCount() {
        return hits.size();
    }



    class CustomViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private final TextView txtTitle;
        private final ImageView coverImage;
        private final TextView healthLabels;
        private final Button buttonDetails;
        private CheckBox selected;


        CustomViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            selected = mView.findViewById(R.id.checkBoxSelectRecipe);
            selected.setVisibility(View.INVISIBLE);
            txtTitle = mView.findViewById(R.id.textRec);
            coverImage = mView.findViewById(R.id.coverImage);
            healthLabels = mView.findViewById(R.id.healthLabels);
            buttonDetails = mView.findViewById(R.id.buttonDetails);
            listener = (RecipeSearchFragment.OnSelectedRecipeDetailsListener) context;
        }
    }

}