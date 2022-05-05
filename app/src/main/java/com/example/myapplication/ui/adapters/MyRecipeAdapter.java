package com.example.myapplication.ui.adapters;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.db.AppDatabase;
import com.example.myapplication.db.entity.HealthLabels;
import com.example.myapplication.db.entity.MyRecepie;

import com.example.myapplication.ui.fragments.MyRecepiesFragment;
import com.example.myapplication.ui.fragments.RecipeSearchFragment;
import com.example.myapplication.viewmodel.RecipeViewModel;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyRecipeAdapter extends RecyclerView.Adapter<MyRecipeAdapter.MyRecipeAdapterViewHolder> {
    private  static List<MyRecepie> myRecepies;
    private static  Context context;
    private RecipeSearchFragment.OnSelectedRecipeDetailsListener listener;
    private static MyRecepiesFragment.CheckedChangedListener changedListener;
    private int sum;
    private List<MyRecepie> selectedRecepies ;

    public MyRecipeAdapter(final Context context) {
        this.context = context;
        this.myRecepies = new ArrayList<>();
        this.selectedRecepies = new ArrayList<>();
    }




    public void setSelectedRecipesList(List<MyRecepie> selectedRecipes){
        this.selectedRecepies= selectedRecipes;
        setSum(selectedRecipes);
        notifyDataSetChanged();
    }

    public void setSum(List<MyRecepie> selectedRecepies ){
        this.sum =0;
        if(selectedRecepies!=null) {
            for (MyRecepie recepie : selectedRecepies) {
                this.sum += recepie.getCalories() / recepie.getNumPersons();
            }
        }
        listener = (RecipeSearchFragment.OnSelectedRecipeDetailsListener) context;
    }

    public void setRecipesList(List<MyRecepie> recipes){
        this.myRecepies= recipes;
        notifyDataSetChanged();
    }

    public List<MyRecepie> getSelected(){
        return this.selectedRecepies;
    }

    public int getSum(){ return this.sum; }


    @NonNull
    @Override
    public MyRecipeAdapter.MyRecipeAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false);
        changedListener = (MyRecepiesFragment.CheckedChangedListener)context;
        return new MyRecipeAdapter.MyRecipeAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecipeAdapter.MyRecipeAdapterViewHolder holder, int position) {
        int kcal = (int)Math.rint(myRecepies.get(position).getCalories()/myRecepies.get(position).getNumPersons());
        holder.txtTitle.setText(myRecepies.get(position).getTitle() + " ("+ kcal +" kcal)");
        HealthLabels healthLabs = myRecepies.get(position).getHealthLabels();
        holder.healthLabels.setText(healthLabs.toString());

        Picasso.get()
                .load(myRecepies.get(position).getRecipeImage())
                .placeholder((R.drawable.ic_baseline_image_24))
                .fit()
                .into(holder.coverImage);

        holder.buttonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int kcal = (int)Math.rint(myRecepies.get(position).getCalories()/myRecepies.get(position).getNumPersons());
                Bundle bundle = new Bundle();
                bundle.putString("label",myRecepies.get(position).getTitle() );
                bundle.putStringArray("ingredients", (String[]) myRecepies.get(position).getIngridientLines().getIngridientLines().toArray());
                bundle.putStringArray("healthLabels", (String[]) myRecepies.get(position).getHealthLabels().getHealthLabels().toArray());
                bundle.putString("numPersons",String.valueOf((int)Math.rint(myRecepies.get(position).getNumPersons())));
                bundle.putString("time",String.valueOf((int)Math.round(myRecepies.get(position).getPrepTime())));
                bundle.putString("recipeOnWeb",myRecepies.get(position).getUrl() );
                bundle.putString("calories",String.valueOf(kcal));

                bundle.putString("coverImage",myRecepies.get(position).getRecipeImage());
                bundle.putBoolean("showSavedRecipeDetails", true);
                listener.onSelectedRecipeDetails(bundle);
            }
        });
        //checkbox
        holder.selectedRecipe.setTag(position);
        holder.selectedRecipe.setChecked(myRecepies.get(position).isStatus());
    }

    @Override
    public int getItemCount() {
        return myRecepies== null ? 0 : myRecepies.size();
    }



    public static class MyRecipeAdapterViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtTitle;
        private final ImageView coverImage;
        private final TextView healthLabels;
        private final Button buttonDetails;
        private final CheckBox selectedRecipe;

        public MyRecipeAdapterViewHolder(View v) {
            super(v);
            txtTitle = v.findViewById(R.id.textRec);
            coverImage = v.findViewById(R.id.coverImage);
            healthLabels = v.findViewById(R.id.healthLabels);
            buttonDetails = v.findViewById(R.id.buttonDetails);
            selectedRecipe = v.findViewById(R.id.checkBoxSelectRecipe);

            selectedRecipe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = (Integer) buttonView.getTag();
                    changedListener.onCheckedChangedListener(myRecepies.get(position), isChecked);
                    /*myRecepies.get(position).setStatus(isChecked);
                    update(myRecepies.get(position));*/
                }
            });



        }
    }



}


