package com.codester.fitness.workout.Activity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codester.fitness.workout.R;
import com.codester.fitness.workout.Utils.DatabaseAccess;
import com.codester.fitness.workout.Utils.Model;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class ScrollingActivity extends AppCompatActivity {
    String id, name, description, image, ref_id;
    List<Model> models;
    List<Model> get_ref_data;
//    DataManager dataManager;
    int position, ref_pos;
    TextView txt_deatil, toolbar_txt;
    ImageView imageView;
    CountDownTimer countDownTimer;
    AdView addview;
    DatabaseAccess databaseAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        init();
        showbanner();

        id = getIntent().getStringExtra("id");

//        models = dataManager.getIdData(id);
        databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        models = databaseAccess.getIdData(id);
        databaseAccess.close();
        Log.e("models", "" + models.size());

        displayData(position);

        Log.e("models", "" + name);
        Log.e("models", "" + description);
        Log.e("models", "" + image);


        countDownTimer = new CountDownTimer(300, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
                databaseAccess.open();
                get_ref_data = databaseAccess.getRef_id(id);
                databaseAccess.close();
//                get_ref_data = dataManager.getRef_id(id);

                if (get_ref_data.size() == 0) {
                } else if (ref_pos < get_ref_data.size() - 1) {

                    ref_pos = ref_pos + 1;
                    setRefData(ref_pos);
                    Log.e("ref_pos", "" + ref_pos);
                    countDownTimer.start();

                } else {
                    ref_pos = 0;
                    countDownTimer.start();
                }


            }
        }.start();

    }

    private void showbanner() {

        addview = (AdView) findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        addview.loadAd(adRequest);
    }


    private void init() {
        models = new ArrayList<>();
//        dataManager = new DataManager(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        toolbar_txt = (TextView) findViewById(R.id.toolbar_title);
        txt_deatil = (TextView) findViewById(R.id.txt);
        imageView = (ImageView) findViewById(R.id.image);
        toolbar_txt.setText("Workout");
    }

    public void displayData(int position) {
        name = models.get(position).getName() + ".png";
        image = models.get(position).getImage();
        description = models.get(position).getDesc();

        txt_deatil.setText(description);
        imageView.setImageBitmap(getBitmapFromAsset(image));

    }

    public void setRefData(int pos) {
        image = get_ref_data.get(pos).getRef_img() + ".png";
        ref_id = get_ref_data.get(pos).getRef_id();
        Log.e("path", "" + image);
        imageView.setImageBitmap(getBitmapFromAsset(image));
    }


    private Bitmap getBitmapFromAsset(String strName) {
        // TODO Auto-generated method stub
        AssetManager assetManager = getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(strName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }
}
