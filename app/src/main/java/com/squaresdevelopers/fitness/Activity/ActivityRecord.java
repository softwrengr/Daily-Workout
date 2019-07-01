package com.squaresdevelopers.fitness.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.squaresdevelopers.fitness.Adapter.RecordAdapter;
import com.squaresdevelopers.fitness.R;
import com.squaresdevelopers.fitness.Utils.DatabaseAccess;
import com.squaresdevelopers.fitness.Utils.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dream on 5/18/2018.
 */

public class ActivityRecord extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    List<Model> modelList;
    List<Model> recod_data;
//    DataManager dataManager;
    RecordAdapter recordAdapter;
    String date;
    DatabaseAccess databaseAccess;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        init();
        date = getIntent().getStringExtra("date");
        databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        recod_data = databaseAccess.getDateData(date);
        databaseAccess.close();


        databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        modelList = databaseAccess.getRandomData();
        databaseAccess.close();

//        recod_data = dataManager.getDateData(date);
//        modelList = dataManager.getRandomData();
        Log.e("record_data",""+recod_data.size());
        Log.e("date====",""+date);

        recordAdapter = new RecordAdapter(getApplicationContext(),modelList,recod_data);
        recyclerView.setAdapter(recordAdapter);

    }

    private void init() {
        modelList = new ArrayList<>();
        recod_data = new ArrayList<>();
//        dataManager = new DataManager(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.record));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView = (RecyclerView)findViewById(R.id.recylcer);
        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
    }
}
