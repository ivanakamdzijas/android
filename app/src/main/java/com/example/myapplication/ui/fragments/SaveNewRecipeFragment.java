package com.example.myapplication.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.ui.adapters.MyRecipeAdapter;
import com.example.myapplication.db.entity.HealthLabels;
import com.example.myapplication.db.entity.IngridientLines;
import com.example.myapplication.db.entity.MyRecepie;
import com.example.myapplication.viewmodel.RecipeViewModel;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class SaveNewRecipeFragment extends Fragment {

    private TextView textRec2;
    private ImageView coverImage2;
    private TextView recipeIngredients;
    private TextView numPersons;
    private TextView time;
    private TextView recipeOnWeb;
    private TextView calories;
    private Button buttonSave;
    private OnSaveRecipeListener onSaveRecipeListener;
    private RecipeViewModel recipeViewModel;

    public SaveNewRecipeFragment() {
        // Required empty public constructor
    }

    public interface OnSaveRecipeListener {
        void onSaveRecipe();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recipe_detail, container, false);
        textRec2 = view.findViewById(R.id.textRec2);
        coverImage2 = view.findViewById(R.id.coverImage2);
        recipeIngredients = view.findViewById(R.id.recipeIngredients);
        numPersons = view.findViewById(R.id.numPersons);
        time = view.findViewById(R.id.time);
        recipeOnWeb = view.findViewById(R.id.recipeOnWeb);
        calories = view.findViewById(R.id.calories);
        buttonSave = view.findViewById(R.id.buttonSave);
        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
        Bundle bundle = getArguments().getBundle("bundle");
        if(bundle.getBoolean("showSavedRecipeDetails")){
            buttonSave.setVisibility(View.GONE);
        }
        textRec2.setText(bundle.getString("label"));
        String[] ingr = bundle.getStringArray("ingredients");
        for(int i = 0; i <ingr.length; i++){
            recipeIngredients.append(ingr[i]);
            recipeIngredients.append("\n");
        }
        numPersons.setText(bundle.getString("numPersons"));
        time.setText(bundle.getString("time"));
        recipeOnWeb.setText(bundle.getString("recipeOnWeb"));
        calories.setText(bundle.getString("calories"));
        Picasso.Builder builder = new Picasso.Builder(getContext());
        builder.downloader(new OkHttp3Downloader(getContext()));
        builder.build().load(bundle.getString("coverImage")).fit()
                    .placeholder((R.drawable.ic_baseline_image_24))
                    .error(R.drawable.ic_launcher_background)
                    .into(coverImage2);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyRecepie myRecepie = new MyRecepie();
                myRecepie.setTitle(bundle.getString("label"));
                List<String> ingridients = Arrays.asList(bundle.getStringArray("ingredients"));
                IngridientLines ingridientLines = new IngridientLines(ingridients);
                myRecepie.setIngridientLines(ingridientLines);
                List<String> labels = Arrays.asList(bundle.getStringArray("healthLabels"));
                HealthLabels healthLabels = new HealthLabels(labels);
                myRecepie.setHealthLabels(healthLabels);
                myRecepie.setNumPersons(Integer.parseInt(bundle.getString("numPersons")));
                myRecepie.setPrepTime(Integer.parseInt(bundle.getString("time")));
                myRecepie.setUrl(bundle.getString("recipeOnWeb"));
                myRecepie.setStatus(false);
                myRecepie.setRecipeImage(bundle.getString("coverImage"));
                myRecepie.setCalories(Integer.parseInt(bundle.getString("calories")));
                recipeViewModel.addMyRecipe(myRecepie);
                onSaveRecipeListener.onSaveRecipe();
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        onSaveRecipeListener = (OnSaveRecipeListener) context;
    }
}
