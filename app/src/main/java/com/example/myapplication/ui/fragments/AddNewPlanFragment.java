package com.example.myapplication.ui.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.R;
import com.example.myapplication.ui.adapters.MealAdapter;
import com.example.myapplication.db.entity.Meal;
import com.example.myapplication.db.entity.MealPlan;
import com.example.myapplication.db.entity.Plan;
import com.example.myapplication.viewmodel.RecipeViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewPlanFragment extends Fragment  {

    static private final String TAG = "RecipeActivity";
    private DatePickerDialog datePicker;
    private TextView textViewDate;
    private TextView textViewSumCalories;
    private TextView kalorije;
    private RecyclerView recyclerView;
    private MealAdapter adapter;
    private MealPlan mealPlan;
    private List<Meal> mealList ;
    private OnAddToListListener callback;
    private OnSelectMealsListener listener;
    private OnCancelPlanListener cancelListener;
    private RecipeViewModel recipeViewModel;


    public AddNewPlanFragment() {
        // Required empty public constructor
    }


    public interface OnAddToListListener {
       void addToListFragment(Plan plan);
    }

    public interface UpdateMealListener {
        void updateMeal(Meal meal);
    }

    public interface OnCancelPlanListener {
        void cancelPlan();
    }

    public interface OnSelectMealsListener {
        void selectMealsListener(String sumKcal);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_add_new, container, false);
        textViewDate = (TextView) view.findViewById(R.id.textViewDate);
        kalorije = view.findViewById(R.id.textview_kcal);
        kalorije.setVisibility(View.INVISIBLE);
        textViewSumCalories = (TextView) view.findViewById(R.id.textViewSumCalories);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMeals);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        Button buttonDate = (Button) view.findViewById(R.id.buttonDate);
        Button buttonPickMeals = (Button) view.findViewById(R.id.buttonPickMeals);
        Button buttonAdd = (Button) view.findViewById(R.id.buttonAdd);
        Button buttonCancel = (Button) view.findViewById(R.id.buttonCancel);

        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        mealPlan = new MealPlan();

        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                //datePicker dialog
                datePicker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                c.set(Calendar.HOUR, 0);
                                c.set(Calendar.MINUTE, 0);
                                c.set(Calendar.SECOND, 0);
                                c.set(Calendar.MILLISECOND, 0);
                                c.set(Calendar.YEAR, year);
                                c.set(Calendar.MONTH, month);
                                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                long dateLong = c.getTimeInMillis();
                                Date date = new Date(dateLong);

                                Calendar calendarNow = Calendar.getInstance();
                                calendarNow.set(Calendar.HOUR, 0);
                                calendarNow.set(Calendar.MINUTE, 0);
                                calendarNow.set(Calendar.SECOND, 0);
                                calendarNow.set(Calendar.MILLISECOND, 0);
                                Date now = new Date(calendarNow.getTimeInMillis());
                                boolean isBefore = date.before(now);
                                if(isBefore){
                                    Toast toast = Toast.makeText(getContext(),getString(R.string.futureDate), Toast.LENGTH_LONG);
                                    toast.show();
                                }
                                else {
                                    String formattedDate = new SimpleDateFormat("dd.MM.yyyy").format(date);
                                    textViewDate.setText(formattedDate);
                                    mealPlan.setDate(date);
                                }
                            }
                        }, year, month, dayOfMonth);
                datePicker.show();
            }
        });
        buttonPickMeals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.selectMealsListener(String.valueOf(textViewSumCalories.getText()));
                kalorije.setVisibility(View.VISIBLE);
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textViewSumCalories.getText().equals("0") || textViewSumCalories.getText().equals("")){
                    Toast toast = Toast.makeText(getContext(),getString(R.string.pickMeals), Toast.LENGTH_LONG);
                    toast.show();
                } else if(textViewDate.getText().equals("...")){
                    Toast toast = Toast.makeText(getContext(),getString(R.string.pickDate), Toast.LENGTH_LONG);
                    toast.show();
                }else{
                    Plan p = new Plan();
                    p.setMealPlan(mealPlan);
                    p.setMeals(mealList);
                    callback.addToListFragment(p);
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cancelListener.cancelPlan();
                //unselektovanje recepta
                if(mealList != null) {
                    for (Meal m : mealList) {
                        recipeViewModel.update(m.getMyRecepie(), false);
                    }
                }

            }
        });
        return view;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                mealList = (List<Meal>) result.getSerializable("meals");
                int calories = result.getInt("calories");
                setMealsInfo(mealList, calories);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (OnAddToListListener) context;
        listener = (OnSelectMealsListener) context;
        cancelListener = (OnCancelPlanListener) context;
    }

    public void setMealsInfo(List<Meal> mealList, int sumCalories){
        adapter = new MealAdapter(getContext(),  mealList);
        recyclerView.setAdapter(adapter);
        textViewSumCalories.setText(String.valueOf(sumCalories));
        mealPlan.setSumCalories(sumCalories);
    }


}
