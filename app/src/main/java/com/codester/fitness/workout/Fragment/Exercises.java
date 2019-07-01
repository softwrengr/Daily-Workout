package com.codester.fitness.workout.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codester.fitness.workout.Activity.ScrollingActivity;
import com.codester.fitness.workout.Adapter.ExerciseAdapter;
import com.codester.fitness.workout.R;
import com.codester.fitness.workout.Utils.DatabaseAccess;
import com.codester.fitness.workout.Utils.Model;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dream on 5/16/2018.
 *
 */

public class Exercises extends Fragment {

    View view;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
//    DataManager dataManager;
    List<Model> model_data;
    ExerciseAdapter exerciseAdapter;
    int MAINPOSITION = 0;
    boolean interstitialCanceled;
    ConnectionDetector cd;
    InterstitialAd mInterstitialAd;
    DatabaseAccess databaseAccess;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_exercise, container, false);
        init();

        databaseAccess = DatabaseAccess.getInstance(getContext());
        databaseAccess.open();
        model_data = databaseAccess.getalldata();
        databaseAccess.close();

//        model_data = dataManager.getalldata();
        exerciseAdapter = new ExerciseAdapter(getContext(), model_data, new ExerciseAdapter.OnClick() {
            @Override
            public void onClick(View view, int pos) {
                if (interstitialCanceled) {
                } else {
                    if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        passIntent();
                    }
                }
                MAINPOSITION = pos;
            }
        });
        recyclerView.setAdapter(exerciseAdapter);
        return view;
    }

    public void passIntent() {
        Intent intent = new Intent(getContext(), ScrollingActivity.class);
        intent.putExtra("id", model_data.get(MAINPOSITION).getId());
        startActivity(intent);
    }

    private void init() {
        model_data = new ArrayList<>();
//        dataManager = new DataManager(getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

    }


    @Override
    public void onPause() {
        mInterstitialAd = null;
        interstitialCanceled = true;
        super.onPause();
    }

    @Override
    public void onResume() {
        interstitialCanceled = false;
        if (getResources().getString(R.string.ADS_VISIBILITY).equals("YES")) {
            CallNewInsertial();
        } else {
        }
        super.onResume();
    }

    private void CallNewInsertial() {
        cd = new ConnectionDetector(getActivity());

        if (!cd.isConnectingToInternet()) {
           /* alert.showAlertDialog(Home.this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);*/
            return;
        } else {
            mInterstitialAd = new InterstitialAd(getActivity());
            mInterstitialAd.setAdUnitId(getString(R.string.AdmobFullScreenAdsID));
            requestNewInterstitial1();
            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdClosed() {
                    passIntent();
                }
            });
        }
    }

    private void requestNewInterstitial1() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }
}
