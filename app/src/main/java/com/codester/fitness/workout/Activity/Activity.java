package com.codester.fitness.workout.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codester.fitness.workout.Fragment.Calender;
import com.codester.fitness.workout.Fragment.Exercises;
import com.codester.fitness.workout.Fragment.Home;
import com.codester.fitness.workout.Fragment.Setting;
import com.codester.fitness.workout.R;

public class Activity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ImageView img_home,img_ex,img_cal,img_setting;
    LinearLayout lv_home,lv_ex,lv_cal,lv_setting;
    TextView txt_home,txt_ex,txt_cal,txt_setting;
    public static boolean isBoolean;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        SharedPreferences pref = getSharedPreferences("myTime", Context.MODE_PRIVATE);
        ReminderActivity.set_12_hour_view = pref.getString("time", null);
        ReminderActivity.set_24_hour_view = pref.getString("time_24", null);
        ReminderActivity.set_24_hour_view1= pref.getString("time_241", null);
        ReminderActivity.set_12_hour_view1= pref.getString("time1", null);
        init();
        setCheckedNotification(isBoolean);

        setFragment(new Home());
        buttonClick();
    }

    private void buttonClick() {

        lv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorFilter(R.drawable.ic_home_black_24dp,img_home,txt_home);
                setResetColor(R.drawable.ic_directions_walk_black_24dp,R.drawable.ic_perm_contact_calendar_black_24dp,R.drawable.ic_settings_black_24dp,
                        img_ex,img_cal,img_setting,txt_ex,txt_cal,txt_setting);

                setFragment(new Home());

            }
        });

        lv_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorFilter(R.drawable.ic_directions_walk_black_24dp,img_ex,txt_ex);
                setResetColor(R.drawable.ic_home_black_24dp,R.drawable.ic_perm_contact_calendar_black_24dp,R.drawable.ic_settings_black_24dp,
                        img_home,img_cal,img_setting,txt_home,txt_cal,txt_setting);
                setFragment(new Exercises());
            }
        });

        lv_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorFilter(R.drawable.ic_perm_contact_calendar_black_24dp,img_cal,txt_cal);
                setResetColor(R.drawable.ic_home_black_24dp,R.drawable.ic_directions_walk_black_24dp,R.drawable.ic_settings_black_24dp,
                        img_home,img_ex,img_setting,txt_home,txt_ex,txt_setting);
                setFragment(new Calender());
            }
        });

        lv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setColorFilter(R.drawable.ic_settings_black_24dp,img_setting,txt_setting);
                setResetColor(R.drawable.ic_home_black_24dp,R.drawable.ic_directions_walk_black_24dp,R.drawable.ic_perm_contact_calendar_black_24dp,
                        img_home,img_ex,img_cal,txt_home,txt_ex,txt_cal);
                setFragment(new Setting());
            }
        });
    }


    public void setCheckedNotification(Boolean isBoolean) {
        if (isBoolean == true) {
            handleNotification();

            PackageManager pm = getPackageManager();
            ComponentName componentName = new ComponentName(Activity.this, MyReceiver.class);
            pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                    PackageManager.DONT_KILL_APP);

        } else if (isBoolean == false) {
            PackageManager pm = getPackageManager();
            ComponentName componentName = new ComponentName(Activity.this, MyReceiver.class);
            pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
        }
    }

    private void handleNotification() {

        Intent alarmIntent = new Intent(this, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        int time = 60000;
        Log.e("time==", "" + time);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), time, pendingIntent);
    }

    private void init() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        isBoolean = pref.getBoolean("name", true);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");
        TextView title = (TextView) findViewById(R.id.toolbar_title);
        title.setText(getResources().getString(R.string.app_name));

        img_home = (ImageView)findViewById(R.id.img_home);
        img_ex = (ImageView)findViewById(R.id.img_ex);
        img_cal = (ImageView)findViewById(R.id.img_cal);
        img_setting = (ImageView)findViewById(R.id.img_setting);

        txt_home = (TextView)findViewById(R.id.txt_home);
        txt_ex = (TextView)findViewById(R.id.txt_ex);
        txt_cal = (TextView)findViewById(R.id.txt_cal);
        txt_setting = (TextView)findViewById(R.id.txt_setting);

        lv_home = (LinearLayout)findViewById(R.id.lv_home);
        lv_ex = (LinearLayout)findViewById(R.id.lv_ex);
        lv_cal = (LinearLayout)findViewById(R.id.lv_cal);
        lv_setting = (LinearLayout)findViewById(R.id.lv_setting);

        setColorFilter(R.drawable.ic_home_black_24dp,img_home,txt_home);
        setResetColor(R.drawable.ic_directions_walk_black_24dp,R.drawable.ic_perm_contact_calendar_black_24dp,R.drawable.ic_settings_black_24dp,
                img_ex,img_cal,img_setting,txt_ex,txt_cal,txt_setting);

    }

    public void setFragment(android.support.v4.app.Fragment fragment){
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment).commit();

    }

    public void setColorFilter(int drawable,ImageView imageView,TextView textView){
        Drawable drawable1 = getResources().getDrawable(drawable);
        drawable1.setColorFilter(Color.parseColor("#08b45d"), PorterDuff.Mode.SRC_IN);
        imageView.setImageDrawable(drawable1);
        textView.setTextColor(Color.parseColor("#08b45d"));
    }

    public void setResetColor(int drawable,int drawable1,int drawable2,ImageView imageView,ImageView imageView1,ImageView imageView2,TextView textView,TextView textView1,TextView textView2){
        Drawable setcolor = getResources().getDrawable(drawable);
        Drawable setcolor1 = getResources().getDrawable(drawable1);
        Drawable setcolor2 = getResources().getDrawable(drawable2);
        setcolor.setColorFilter(Color.parseColor("#797777"), PorterDuff.Mode.SRC_IN);
        setcolor1.setColorFilter(Color.parseColor("#797777"), PorterDuff.Mode.SRC_IN);
        setcolor2.setColorFilter(Color.parseColor("#797777"), PorterDuff.Mode.SRC_IN);
        imageView.setImageDrawable(setcolor);
        imageView1.setImageDrawable(setcolor1);
        imageView2.setImageDrawable(setcolor2);
        textView.setTextColor(Color.parseColor("#797777"));
        textView1.setTextColor(Color.parseColor("#797777"));
        textView2.setTextColor(Color.parseColor("#797777"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_share) {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            share.putExtra(Intent.EXTRA_SUBJECT, "Xyz");
            share.putExtra(Intent.EXTRA_TEXT, getString(R.string.SHARE_APP_LINK)
                    + Activity.this.getPackageName());
            startActivity(Intent.createChooser(share, "Share Link!"));

        } else if (id == R.id.action_rate) {
            final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        } else if (id == R.id.action_feedback) {
            String E_MAIL = getResources().getString(R.string.E_MAIL);
            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            int height = displaymetrics.heightPixels;
            int width = displaymetrics.widthPixels;
            PackageManager manager = getApplicationContext().getPackageManager();
            PackageInfo info = null;
            try {
                info = manager.getPackageInfo(getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String version = null;
            if (info != null) {
                version = info.versionName;
            }
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("sage/rfc822mes" + E_MAIL);
            i.putExtra(Intent.EXTRA_SUBJECT, "Physics Formula" + version);
            i.putExtra(Intent.EXTRA_TEXT,
                    "\n" + " System Version:" + Build.VERSION.SDK_INT +
                            "\n" + " Display Height  :" + height + "px" +
                            "\n" + " Display Width  :" + width + "px" +
                            "\n\n" + "Have a problem? Please share it with us and we will do our best to solve it!" +
                            "\n");
            startActivity(Intent.createChooser(i, "Send Email"));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Activity.this.finishAffinity();
    }
}
