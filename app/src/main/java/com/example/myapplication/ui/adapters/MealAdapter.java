package com.example.myapplication.ui.adapters;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.myapplication.R;
import com.example.myapplication.db.AppDatabase;
import com.example.myapplication.db.entity.Meal;
import com.example.myapplication.db.entity.MyRecepie;
import com.example.myapplication.ui.fragments.AddNewPlanFragment;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.MealAdapterViewHolder> {
    private final List<Meal> mealList;
    private final Context context;
    private TimePickerDialog timePicker;
    private AddNewPlanFragment.UpdateMealListener listener;


    public MealAdapter(final Context context, List<Meal> meals) {
        this.context = context;
        this.mealList = meals;
    }



    @NonNull
    @Override
    public MealAdapter.MealAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_meal_item, parent, false);
        listener = (AddNewPlanFragment.UpdateMealListener)context;
        return new MealAdapter.MealAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MealAdapter.MealAdapterViewHolder holder, int position) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String time = sdf.format(mealList.get(position).getTime());
        holder.textViewMealTime.setText(time);
        holder.textViewMealTitle.setText(mealList.get(position).getMyRecepie().getTitle());
        holder.buttonPickTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int hourOfDay = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                // time picker dialog
                timePicker = new TimePickerDialog(context,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                c.set(Calendar.HOUR, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                c.set(Calendar.AM_PM,0);
                                long time = c.getTimeInMillis();
                                mealList.get(position).setTime(time);
                                //update(mealList.get(position));
                                listener.updateMeal(mealList.get(position));
                                String formattedTime = new SimpleDateFormat("HH:mm").format(time);
                                holder.textViewMealTime.setText(formattedTime);
                            }
                        }, hourOfDay, minute,true);
                timePicker.show();
                //androidx.fragment.app.DialogFragment newFragment = new MealAdapter.MealAdapterViewHolder.TimePickerFragment(holder,position);
                //newFragment.show(((AppCompatActivity)context).getSupportFragmentManager(), "timePicker");

            }
        });

    }

    @Override
    public int getItemCount() {
        return mealList == null ? 0 : mealList.size();
    }

    public static class MealAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewMealTitle;
        private TextView textViewMealTime;
        private Button buttonPickTime;



        public MealAdapterViewHolder(View v) {
            super(v);
            textViewMealTitle = v.findViewById(R.id.textViewMealTitle);
            textViewMealTime = v.findViewById(R.id.textViewMealTime);
            buttonPickTime = v.findViewById(R.id.buttonPickTime);
        }
    }



}