package com.codester.fitness.workout.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codester.fitness.workout.R;
import com.codester.fitness.workout.Utils.DataBaseHelper;
import com.codester.fitness.workout.Utils.DatabaseAccess;
import com.codester.fitness.workout.Utils.Model;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * Created by dream on 5/16/2018.
 */

public class Second_home extends AppCompatActivity {


    ImageView imageView;
    TextView txt_header, txt_done, txt_sec;
    RelativeLayout lv_done, lv_timer;
    //    DataManager dataManager;
    List<Model> model_data;
    String imgPath;
    String id;
    String ref_id;
    int ref_pos = 0, position = 0;
    CountDownTimer countDownTimer, restTimer;
    List<Model> get_ref_data;
    String skip, take_rest, done;
    boolean isClick = false;
    LinearLayout lv_View, final_View;
    TextView btn_home;
    LinearLayout indicator_Layout;
    TextView rowTextView;
    int rootPos;
    int miliseconds, work_type;
    TextView[] myTextViews;
    int sdk = android.os.Build.VERSION.SDK_INT;
    DataBaseHelper dataBaseHelper;
    SQLiteDatabase db;
    List<Model> pass_id;
    List<Model> history_data;
    List<Model> upDateData;
    List<Model> check_date;
    String current_date, exersice_type;
    String[] type_array;

    ConnectionDetector cd;
    int MAINPOSITION;
    boolean interstitialCanceled;
    InterstitialAd mInterstitialAd;
    AdView addview;
    DatabaseAccess databaseAccess;
    private View fragmentview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        miliseconds = pref.getInt("seconds", 10000);
        work_type = pref.getInt("no", 0);
        Log.e("array", "" + work_type);

        setArry();
        init();

//        admob();
        showbanner();
//        model_data = dataManager.getRandomData();
        databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        model_data = databaseAccess.getRandomData();
        databaseAccess.close();
        myTextViews = new TextView[model_data.size()];
        displayData(position);
        createTextPosition();


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


        lv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < model_data.size() - 1) {
                    if (isClick == false) {
                        isClick = true;
                        setLayout();
                        Log.e("id==", "" + id);
                        skipClick();
                    } else {
                        isClick = false;
                        doneClick();
                    }
                } else {

                    lv_View.setVisibility(View.GONE);
                    final_View.setVisibility(View.VISIBLE);
                    insertData();
                    btn_home.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (interstitialCanceled) {
                            } else {
                                if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                                    mInterstitialAd.show();
                                } else {
                                    ContinueIntent();
                                }
                            }

                        }
                    });

                }
            }

        });

    }

    private void ContinueIntent() {
        Intent intent = new Intent(Second_home.this, Activity.class);
        startActivity(intent);
    }

    private void showbanner() {
        addview = (AdView) findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        addview.loadAd(adRequest);
//
    }

    public void skipClick() {
        insertData();
        restTimer = new CountDownTimer(miliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txt_sec.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                Log.e("timer_sec", "" + get_ref_data.size());
                isClick = false;
                restTimer.cancel();
                imageView.setVisibility(View.VISIBLE);
//                txt_header.setText(type_array[position]);
                txt_done.setText(done);
                lv_timer.setVisibility(View.GONE);


                Log.e("id1==", "" + (Integer.parseInt(id) + 1));
                if (position < model_data.size()) {
                    position = position + 1;
                    displayData(position);
                    for (int i = 0; i < myTextViews.length; i++) {
                        if (position == i) {
                            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                myTextViews[position].setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_circlebackground_blue));
                            } else {
                                myTextViews[position].setBackground(getResources().getDrawable(R.drawable.ic_circlebackground_blue));

                            }
                            myTextViews[i].setTextColor(Color.WHITE);

                        }
                    }
                    countDownTimer.start();
                    ref_pos = 0;
                    restTimer.cancel();
                } else {

                }

            }
        }.start();
    }

    public void doneClick() {

        restTimer.cancel();
        imageView.setVisibility(View.VISIBLE);
//        txt_header.setText(type_array[position]);
        txt_done.setText(done);

        lv_timer.setVisibility(View.GONE);
        Log.e("id1==", "" + (Integer.parseInt(id) + 1));

        if (position < model_data.size()) {

            position = position + 1;
            displayData(position);
            for (int i = 0; i < myTextViews.length; i++) {
                if (position == i) {
                    if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        myTextViews[position].setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_circlebackground_blue));
                    } else {
                        myTextViews[position].setBackground(getResources().getDrawable(R.drawable.ic_circlebackground_blue));

                    }
                    myTextViews[i].setTextColor(Color.WHITE);

                }
            }
            Log.e("pos====", "" + rootPos);
            countDownTimer.start();
            ref_pos = 0;
            restTimer.cancel();
        } else {

        }


    }


    public void insertData() {
        databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        pass_id = databaseAccess.getIdData(id);
        databaseAccess.close();
//        pass_id = dataManager.getIdData(id);
        if (checkedInsert(current_date, pass_id.get(0).getName())) {

        } else {
            insertData(current_date, pass_id.get(0).getId(), pass_id.get(0).getName(), pass_id.get(0).getImage());
        }
    }

    public boolean checkedInsert(String id, String name) {
        databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        history_data = databaseAccess.getInsertData();
        databaseAccess.close();
//        history_data = dataManager.getInsertData();
        if (history_data.size() > 0) {
            for (int i = 0; i < history_data.size(); i++) {

                if (id.equals(history_data.get(i).getDate()) && name.equals(history_data.get(i).getName())) {
//                    Log.e("id====", "" + id.equals(history_data.get(i).getDate()));
//                    Log.e("name====", "" + name.equals(history_data.get(i).getName()));
                    return true;
                }

            }
        }
        return false;
    }

    private void createTextPosition() {


        for (int i = 0; i < model_data.size(); i++) {
            rowTextView = new TextView(this);

            rootPos = i;
            rowTextView.setText("" + (i + 1));
            rowTextView.setGravity(Gravity.CENTER);
            rowTextView.setTextColor(Color.BLACK);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, 50);
            lp.weight = 1;
            rowTextView.setLayoutParams(lp);
            rowTextView.setPadding(0, 0, 15, 0);
            rowTextView.setTextSize(12f);
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                rowTextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_circlebackground));
            } else {
                rowTextView.setBackground(getResources().getDrawable(R.drawable.ic_circlebackground));
            }
            indicator_Layout.addView(rowTextView);
            myTextViews[i] = rowTextView;
        }

        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            myTextViews[position].setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_circlebackground_blue));
        } else {
            myTextViews[position].setBackground(getResources().getDrawable(R.drawable.ic_circlebackground_blue));
        }
        myTextViews[position].setTextColor(Color.WHITE);
    }

    private void init() {
        model_data = new ArrayList<>();
        get_ref_data = new ArrayList<>();
        pass_id = new ArrayList<>();
        history_data = new ArrayList<>();
        upDateData = new ArrayList<>();
        check_date = new ArrayList<>();
//        dataManager = new DataManager(getApplicationContext());
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imageView = findViewById(R.id.imaegview);
        txt_header = findViewById(R.id.header);
        txt_done = findViewById(R.id.text_done);
        txt_sec = findViewById(R.id.sec);
        lv_done = findViewById(R.id.done);
        lv_timer = findViewById(R.id.timer);
        lv_View = findViewById(R.id.lv_view);
        final_View = findViewById(R.id.fianl_view);
        indicator_Layout = findViewById(R.id.indiactor);
        btn_home = findViewById(R.id.btn_home);
//        txt_header.setText(type_array[position]);

        skip = getResources().getString(R.string.Skip);
        take_rest = getResources().getString(R.string.take_rest);
        done = getResources().getString(R.string.Done);
        txt_done.setText(done);

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        current_date = day + "/" + (month + 1) + "/" + year;


    }

    public void displayData(int position) {

        id = model_data.get(position).getId();
        imgPath = model_data.get(position).getImage() + ".png";
        ref_id = model_data.get(position).getRef_id();
        imageView.setImageBitmap(getBitmapFromAsset(imgPath));
        if (work_type == 0) {
            exersice_type = model_data.get(position).getEasy();
        } else if (work_type == 1) {
            exersice_type = model_data.get(position).getMedium();
        } else {
            exersice_type = model_data.get(position).getHard();
        }
        txt_header.setText(exersice_type);

    }

    public void setRefData(int pos) {
        imgPath = get_ref_data.get(pos).getRef_img() + ".png";
        ref_id = get_ref_data.get(pos).getRef_id();
        Log.e("path", "" + imgPath);
        imageView.setImageBitmap(getBitmapFromAsset(imgPath));
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

    public void setLayout() {
        txt_header.setText(take_rest);
        txt_done.setText(skip);
        countDownTimer.cancel();
        imageView.setVisibility(View.GONE);
        lv_timer.setVisibility(View.VISIBLE);
    }


    public void insertData(String s, String id, String name, String image) {
        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        dataBaseHelper.createDataBase();
        dataBaseHelper.openDataBase();
        db = dataBaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", s);
        values.put("ref_id", id);
        values.put("name", name);
        values.put("image", image);
        db.insert("insert_task", "", values);
        dataBaseHelper.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }


    public void setArry() {
        if (work_type == 0) {
            type_array = getResources().getStringArray(R.array.easy_array);
        } else if (work_type == 1) {
            type_array = getResources().getStringArray(R.array.medium_array);
        } else {
            type_array = getResources().getStringArray(R.array.difficult);
        }
    }

    @Override
    protected void onResume() {
        interstitialCanceled = false;
        if (getResources().getString(R.string.ADS_VISIBILITY).equals("YES")) {
            CallNewInsertial1();

        } else {
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        mInterstitialAd = null;
        interstitialCanceled = true;
        super.onPause();
    }

    private void CallNewInsertial1() {
        cd = new ConnectionDetector(Second_home.this);

        if (!cd.isConnectingToInternet()) {
           /* alert.showAlertDialog(Home.this, "Internet Connection Error",
                    "Please connect to working Internet connection", false);*/
            return;
        } else {
            mInterstitialAd = new InterstitialAd(Second_home.this);
            mInterstitialAd.setAdUnitId(getString(R.string.AdmobFullScreenAdsID));
            requestNewInterstitial1();
            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdClosed() {
                    ContinueIntent();
                }
            });
        }
    }

    private void requestNewInterstitial1() {
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }
}
