package com.squaresdevelopers.fitness.Activity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squaresdevelopers.fitness.R;
import com.squaresdevelopers.fitness.Utils.DatabaseAccess;
import com.squaresdevelopers.fitness.Utils.Model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    String id, name, description, image, ref_id;
    List<Model> models;
    List<Model> get_ref_data;
//    DataManager dataManager;
    int position, ref_pos;
    TextView txt_deatil;
    ImageView imageView;
    CountDownTimer countDownTimer;
    DatabaseAccess databaseAccess;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        id = getIntent().getStringExtra("id");

//        models = dataManager.getIdData(id);
        databaseAccess = DatabaseAccess.getInstance(this);
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
                databaseAccess = DatabaseAccess.getInstance(DetailActivity.this);
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

    private void init() {
        models = new ArrayList<>();
        get_ref_data = new ArrayList<>();
//        dataManager = new DataManager(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txt_deatil = (TextView) findViewById(R.id.txt);
        imageView = (ImageView) findViewById(R.id.image);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
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
