package com.codester.fitness.workout.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.codester.fitness.workout.Activity.Second_home;
import com.codester.fitness.workout.R;


/**
 * Created by dream on 5/16/2018.
 *
 */

public class Home extends Fragment {

    View view;
    RelativeLayout lv_start;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home,container,false);
        init();

        lv_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lv_start.startAnimation(fab_open);
                Intent intent = new Intent(getActivity(), Second_home.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void init() {
        lv_start = (RelativeLayout)view.findViewById(R.id.lv_start);
        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_backward);
    }
}
