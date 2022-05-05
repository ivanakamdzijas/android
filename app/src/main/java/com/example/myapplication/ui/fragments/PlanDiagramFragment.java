package com.example.myapplication.ui.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.ui.adapters.PlanListAdapter;
import com.example.myapplication.db.entity.MealPlan;
import com.example.myapplication.viewmodel.PlanViewModel;
import com.example.myapplication.viewmodel.RecipeViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlanDiagramFragment extends Fragment {

    private LineChart lineChart;
    private List<MealPlan> planList = new ArrayList<>();
    private final ArrayList<Long> xAxisLabel = new ArrayList<>();
    private PlanViewModel planViewModel;
    private XAxis xAxis;

    public PlanDiagramFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_line_chart, container, false);
        lineChart = view.findViewById(R.id.lineChart);
        // set text if data are are not available
        lineChart.setNoDataText(getString(R.string.dataNotAvaliable));
        lineChart.getDescription().setEnabled(false);
        lineChart.setDragEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setDoubleTapToZoomEnabled(true);
        lineChart.setHorizontalScrollBarEnabled(true);
        lineChart.getAxisLeft().setGranularity(1f);

        xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1f);// only intervals of 1 day
        xAxis.setDrawLabels(true);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get a new or existing ViewModel from the ViewModelProvider.
        planViewModel = new ViewModelProvider(this).get(PlanViewModel.class);
        planViewModel.getPlanList().observe(getViewLifecycleOwner(), plans -> {
            // Update the planList
            this.planList = plans;
            createDataSet();
            formatXAxis(xAxis);
            lineChart.notifyDataSetChanged();
            lineChart.invalidate();
        });
    }

    private void createDataSet(){
        LineDataSet lineDataSet = new LineDataSet(lineChartDataSet(),getString(R.string.history));
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);

        LineData lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);

        lineDataSet.setColor(Color.LTGRAY);
        lineDataSet.setCircleColor(Color.BLUE);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setLineWidth(5);
        lineDataSet.setCircleRadius(10);
        lineDataSet.setCircleHoleRadius(10);
        lineDataSet.setValueTextSize(10);
        lineDataSet.setValueTextColor(Color.BLACK);

    }

    private void formatXAxis(XAxis xAxis){
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                long dateLong = xAxisLabel.get((int) value);
                Date date = new Date(dateLong);
                SimpleDateFormat sdf = new SimpleDateFormat("dd. MMM");
                return sdf.format(date);
            }
        });
    }

    private ArrayList<Entry> lineChartDataSet(){
        ArrayList<Entry> dataSet = new ArrayList<>();
        for(int i = 0; i < planList.size(); i++){
            xAxisLabel.add(planList.get(i).getDate().getTime());
            dataSet.add(new Entry(i, planList.get(i).getSumCalories()));
        }
        return  dataSet;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

}
