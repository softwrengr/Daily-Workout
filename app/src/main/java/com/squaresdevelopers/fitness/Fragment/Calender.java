package com.squaresdevelopers.fitness.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.squaresdevelopers.fitness.Activity.ActivityRecord;
import com.squaresdevelopers.fitness.R;
import com.squaresdevelopers.fitness.Utils.DataManager;
import com.squaresdevelopers.fitness.Utils.Model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Calender extends Fragment {

    View view;
    CalendarView calendarView;
    List<Model> recod_data;
    List<Model> insert_data;
    List<Model> model_data;
    TextView image, tv_date, tv_task;
    String date,inCompleteTask,workoutDone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_calender,container,false);
        init();
        final DataManager dataManager = new DataManager(getContext());

        model_data = dataManager.getalldata();

//
        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        date = day + "/" + (month+1) + "/" + year;

        tv_date.setText("" + date);


        insert_data = dataManager.getDateData(date);
        Log.e("insert_date==", "" + model_data.size());
        Log.e("size==", "" + insert_data.size());
        if (insert_data.size() == model_data.size()) {
            image.setTextColor(Color.GREEN);
            tv_task.setText(workoutDone);
        } else {
            image.setTextColor(Color.RED);
            tv_task.setText(inCompleteTask);
        }


        calendarView.setFirstDayOfWeek(2);
        calendarView.setSelectedWeekBackgroundColor(getResources().getColor(R.color.transparent));
        calendarView.setSelectedDateVerticalBar(R.color.colorPrimary);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {

                date = day + "/" + (month + 1) + "/" + year;
                Log.e("current_date==", "" + date);
                tv_date.setText("" + date);
                insert_data = dataManager.getDateData(date);
                Log.e("insert_date==", "" + model_data.size());
                Log.e("size==", "" + insert_data.size());
                if (insert_data.size() == model_data.size()) {
                    image.setTextColor(Color.GREEN);
                    tv_task.setText(workoutDone);
                } else {
                    image.setTextColor(Color.RED);
                    tv_task.setText(inCompleteTask);
                }
//                Toast.makeText(getContext(), "" + date, Toast.LENGTH_SHORT).show();
            }
        });


        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recod_data = dataManager.getDateData(date);
                if (recod_data.size() != 0) {
                    Intent intent = new Intent(getContext(), ActivityRecord.class);
                    intent.putExtra("date", date);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Not data found", Toast.LENGTH_SHORT).show();
                }


                Log.e("current_date==", "" + date);
            }
        });
        return view;
    }

    private void init() {
        recod_data = new ArrayList<>();
        insert_data = new ArrayList<>();
        model_data = new ArrayList<>();
        calendarView = (CalendarView) view.findViewById(R.id.calender_view);
        tv_date = (TextView) view.findViewById(R.id.date);
        image = (TextView) view.findViewById(R.id.image);
        tv_task = (TextView) view.findViewById(R.id.task);
        workoutDone = getContext().getResources().getString(R.string.workoutdone);
        inCompleteTask = getContext().getResources().getString(R.string.InComplete);
    }
}
