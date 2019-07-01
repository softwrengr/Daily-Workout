package com.codester.fitness.workout.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;


import com.codester.fitness.workout.R;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by dream on 5/19/2018.
 */

public class ReminderActivity extends AppCompatActivity {

    TimePicker timePicker;
    int hour, min;
    public static String set_12_hour_view,set_12_hour_view1, set_24_hour_view,set_24_hour_view1;
    public static SwitchCompat switchCompat;
    public static boolean isBoolean = true;
    LinearLayout lv_cancel_time, lv_save_time;
    int set_hour, set_min;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        init();
        SharedPreferences pref = getSharedPreferences("myTime", Context.MODE_PRIVATE);
        set_12_hour_view = pref.getString("time", null);
        set_12_hour_view1 = pref.getString("time1", null);
        set_24_hour_view = pref.getString("time_24", null);
        set_24_hour_view1 = pref.getString("time_241", null);
        set_hour = pref.getInt("hour", 0);
        set_min = pref.getInt("min", 0);


        if (set_12_hour_view == null || set_24_hour_view == null) {

            final Calendar c= Calendar.getInstance();
            int hourOfDay = c.get(c.HOUR_OF_DAY); //Current Hour
            int minute = c.get(c.MINUTE); //Current Minute

            onTimeSet(hourOfDay, minute);
            onTimeSet12_hour(hourOfDay, minute);

            timePicker.setCurrentHour(hourOfDay);
            timePicker.setCurrentMinute(minute);

        }else {
            timePicker.setCurrentHour(set_hour);
            timePicker.setCurrentMinute(set_min);
        }




        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Activity.isBoolean = isChecked;
                if (isChecked) {
                    switchCompat.setChecked(true);
                } else {
                    switchCompat.setChecked(false);
                }
                Log.e("isBoolean=", "" + Activity.isBoolean);
                SharedPreferences pref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putBoolean("name", Activity.isBoolean);
                edit.commit();
                edit.apply();
            }
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {

                hour = hourOfDay;
                min = minute;

            }
        });

        lv_save_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onTimeSet(hour, min);
                onTimeSet12_hour(hour, min);
                SharedPreferences pref = getSharedPreferences("myTime", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("time", set_12_hour_view);
                edit.putString("time_24", set_24_hour_view);
                edit.putString("time_241", set_24_hour_view1);
                edit.putString("time1", set_12_hour_view1);
                edit.putInt("hour", hour);
                edit.putInt("min", min);
                edit.commit();
                edit.apply();

                Intent intent = new Intent(ReminderActivity.this , Activity.class);
                startActivity(intent);
            }
        });

        lv_cancel_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void onTimeSet12_hour(int hourOfDay, int minute) {
        // TODO Auto-generated method stub
        hour = hourOfDay;
        min = minute;
        String am_pm = (hourOfDay < 12) ? "am" : "pm";
        String am_pm1 = (hourOfDay < 12) ? "AM" : "PM";
        set_24_hour_view1 = hour + ":" + min + am_pm1;
        set_24_hour_view = hour + ":" + min + am_pm;
        set_24_hour_view = String.format(Locale.US, "%02d:%02d %s", hour, minute, am_pm);
        set_24_hour_view1 = String.format(Locale.US, "%02d:%02d %s", hour, minute, am_pm1);

    }

    public void onTimeSet(int hourOfDay, int minute) {
        int hour = hourOfDay % 12;
        if (hour == 0) hour = 12;
        String _AM_PM = (hourOfDay > 24) ? "am" : "pm";
        String _AM_PM1 = (hourOfDay > 24) ? "AM" : "PM";
        System.out.println(String.format(Locale.getDefault(), "%02d:%02d %s", hour, minute, _AM_PM));
        set_12_hour_view = String.format(Locale.getDefault(), "%02d:%02d %s", hour, minute, _AM_PM);
        set_12_hour_view1 = String.format(Locale.getDefault(), "%02d:%02d %s", hour, minute, _AM_PM1);
    }


    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.reminder));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        switchCompat = (SwitchCompat) findViewById(R.id.swich);
        lv_cancel_time = (LinearLayout) findViewById(R.id.lv_cancel);
        lv_save_time = (LinearLayout) findViewById(R.id.lv_save);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        isBoolean = pref.getBoolean("name", true);

        if (isBoolean == true) {
            switchCompat.setChecked(true);
        } else {
            switchCompat.setChecked(false);
        }

    }




}
