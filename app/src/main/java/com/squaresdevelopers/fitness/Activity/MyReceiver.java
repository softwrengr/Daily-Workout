package com.squaresdevelopers.fitness.Activity;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.squaresdevelopers.fitness.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String set_24_hour, set_12_hour,set_12_hour1,set_24_hour1;
        SharedPreferences pref = context.getSharedPreferences("myTime", Context.MODE_PRIVATE);


        Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm aa");
        String current_24_hour_time = simpleDateFormat.format(calander.getTime());
        set_24_hour = pref.getString("time", null);
        set_24_hour1 = pref.getString("time1", null);

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");
        String currentTime_12__hour_time = dateFormat.format(new Date()).toString();
        set_12_hour = pref.getString("time_24", null);
        set_12_hour1 = pref.getString("time_241", null);


        if (set_12_hour == null) {

            set_12_hour = "12:00 pm";
            set_24_hour = "12:00 pm";
        }

        Log.e("setTime=", "" + currentTime_12__hour_time);
        Log.e("setTime====", "" + current_24_hour_time);
        Log.e("setTime========", "" + set_12_hour);
        Log.e("setTime============", "" + set_24_hour);


        if (currentTime_12__hour_time.equals(set_24_hour) || current_24_hour_time.equals(set_12_hour) || currentTime_12__hour_time.equals(set_24_hour1)||
                current_24_hour_time.equals(set_12_hour1)) {


            String app_name = context.getResources().getString(R.string.app_name);
            RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
            contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
            contentView.setTextViewText(R.id.app_name, app_name);
            contentView.setTextViewText(R.id.title, app_name);

            Intent notificationIntent = new Intent(context, Activity.class);
            notificationIntent.setAction(Intent.ACTION_MAIN);
            notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            NotificationManager nMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            @SuppressLint("WrongConstant") PendingIntent intent2 = PendingIntent.getActivity(context, 0, notificationIntent,
                    Intent.FLAG_ACTIVITY_NEW_TASK);

            NotificationCompat.Builder nBuilder;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String cid = "w01", name = "Daily Excersice";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;

                NotificationChannel channel = new NotificationChannel(cid, name, importance);
                nMgr.createNotificationChannel(channel);
                nBuilder = new NotificationCompat.Builder(context, cid);
            } else {
                nBuilder = new NotificationCompat.Builder(context);
            }
            nBuilder.setSmallIcon(R.drawable.ic_notifications_none_black_24dp);
            nBuilder.setContentIntent(intent2);
            nBuilder.setAutoCancel(true);
            nBuilder.setContent(contentView);
            nMgr.notify(0, nBuilder.build());
        }
    }
}

