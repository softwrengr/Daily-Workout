package com.codester.fitness.workout.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codester.fitness.workout.Activity.ActivityCompose;
import com.codester.fitness.workout.Activity.ReminderActivity;
import com.codester.fitness.workout.R;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by dream on 5/16/2018.
 */

public class Setting extends Fragment {

    public static int milisecond = 10000;
    public static int workout_type = 80;
    public static String[] type_array;
    View view;
    ImageView rest_img, work_img, reminder_img;
    LinearLayout lv_rest_time, lv_workout_type, lv_rate, lv_contact_us, lv_calender, lv_remider;
    TextView tc_set_time, tv_type;
    String title, type;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_setting, container, false);

        SharedPreferences pref = getContext().getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        title = pref.getString("title", null);
        type = pref.getString("type", null);
        init();
        buttonClick();
        return view;
    }

    private void buttonClick() {

        lv_rest_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDialog();
            }
        });

        lv_workout_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectType();
            }
        });

        lv_rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getContext().getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        lv_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActivityCompose.class);
                startActivity(intent);
            }
        });

        lv_remider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ReminderActivity.class);
                startActivity(intent);
            }
        });

        lv_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                more();
            }
        });
    }

    private void init() {
        tv_type = (TextView) view.findViewById(R.id.type);
        tc_set_time = (TextView) view.findViewById(R.id.seconds);
        rest_img = (ImageView) view.findViewById(R.id.rest_img);
        work_img = (ImageView) view.findViewById(R.id.work_img);
        reminder_img = (ImageView) view.findViewById(R.id.reminder_img);
        lv_rest_time = (LinearLayout) view.findViewById(R.id.rest_time);
        lv_workout_type = (LinearLayout) view.findViewById(R.id.workout);
        lv_rate = (LinearLayout) view.findViewById(R.id.rate);
        lv_contact_us = (LinearLayout) view.findViewById(R.id.contactUs);
        lv_calender = (LinearLayout) view.findViewById(R.id.calender);
        lv_remider = (LinearLayout) view.findViewById(R.id.reminder);
        lv_calender.setVisibility(View.GONE);

        Drawable drawable = getContext().getResources().getDrawable(R.drawable.settime);
        drawable.setColorFilter(Color.parseColor("#08b45d"), PorterDuff.Mode.SRC_IN);
        rest_img.setImageDrawable(drawable);

        Drawable drawable1 = getContext().getResources().getDrawable(R.drawable.difficulty);
        drawable1.setColorFilter(Color.parseColor("#08b45d"), PorterDuff.Mode.SRC_IN);
        work_img.setImageDrawable(drawable1);

        Drawable drawable2 = getContext().getResources().getDrawable(R.drawable.reminder);
        drawable2.setColorFilter(Color.parseColor("#08b45d"), PorterDuff.Mode.SRC_IN);
        reminder_img.setImageDrawable(drawable2);


        if (type != null) {
            tv_type.setText(type);
        }

        if (title != null) {
            tc_set_time.setText(title);
        }


    }


    public void timeDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.timer_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);

        final int[] inArray = {10000, 20000, 30000, 60000, 90000, 120000};

        LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.lin);
        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        String[] array = getContext().getResources().getStringArray(R.array.strin_array);

        final TextView[] myTextViews = new TextView[array.length];
        for (int i = 0; i < array.length; i++) {
            final TextView rowTextView = new TextView(getContext());


            rowTextView.setText("" + array[i]);
            rowTextView.setTextColor(Color.BLACK);
            rowTextView.setTextSize(18f);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 30, 10, 20);
            rowTextView.setLayoutParams(params);

            final int finalI = i;
            rowTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tc_set_time.setText("" + rowTextView.getText().toString());
                    milisecond = inArray[finalI];
                    SharedPreferences pref = getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putInt("seconds", milisecond);
                    edit.putString("title", rowTextView.getText().toString());
                    edit.commit();
                    edit.apply();
                    dialog.dismiss();
                }
            });
            linearLayout.addView(rowTextView);
            myTextViews[i] = rowTextView;
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void selectType() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.timer_dialog);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);


        LinearLayout linearLayout = (LinearLayout) dialog.findViewById(R.id.lin);
        Button cancel = (Button) dialog.findViewById(R.id.cancel);
        TextView title = (TextView) dialog.findViewById(R.id.txt_title);
        String[] array = getContext().getResources().getStringArray(R.array.type_array);
        cancel.setVisibility(View.GONE);

        title.setText("Select Workout Type");

        final TextView[] myTextViews = new TextView[array.length];
        for (int i = 0; i < array.length; i++) {
            final TextView rowTextView = new TextView(getContext());
            rowTextView.setText("" + array[i]);
            rowTextView.setTextColor(Color.BLACK);
            rowTextView.setTextSize(18f);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 30, 10, 20);
            rowTextView.setLayoutParams(params);

            final int finalI = i;
            rowTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv_type.setText("" + rowTextView.getText().toString());

                    workout_type = finalI;
                    if (finalI == 0) {
                        type_array = getContext().getResources().getStringArray(R.array.easy_array);
                    } else if (finalI == 1) {
                        type_array = getContext().getResources().getStringArray(R.array.medium_array);
                    } else {
                        type_array = getContext().getResources().getStringArray(R.array.difficult);
                    }

                    SharedPreferences pref = getContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor edit = pref.edit();
                    edit.putString("type", rowTextView.getText().toString());
                    edit.putInt("no", workout_type);
                    edit.putString("array", String.valueOf(type_array));
                    edit.commit();
                    edit.apply();
                    dialog.dismiss();
                }
            });
            linearLayout.addView(rowTextView);
            myTextViews[i] = rowTextView;
        }
        dialog.show();
    }


    public void more() {
        String E_MAIL = getContext().getResources().getString(R.string.E_MAIL);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        PackageManager manager = getContext().getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(getContext().getPackageName(), 0);
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
//        i.setType(Intent.EXTRA_EMAIL + E_MAIL);
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{E_MAIL});
        i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name) + version);
        i.putExtra(Intent.EXTRA_TEXT, "Have a problem? Please share it with us and we will do our best to solve it!" + "\n");
        startActivity(Intent.createChooser(i, "Send Email"));
    }
}
