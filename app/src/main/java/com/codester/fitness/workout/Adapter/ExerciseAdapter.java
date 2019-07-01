package com.codester.fitness.workout.Adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.codester.fitness.workout.R;
import com.codester.fitness.workout.Utils.Model;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by dream on 5/16/2018.
 *
 */

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.MyViewHolder> {

    List<Model> models;
    Context context;
    View view;
    OnClick click;

    public interface OnClick{
        void onClick(View view,int pos);
    }

    public ExerciseAdapter(Context context,List<Model> models,OnClick onClick){
        this.context = context;
        this.models = models;
        this.click = onClick;
    }

    @NonNull
    @Override
    public ExerciseAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.excersice_data,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseAdapter.MyViewHolder holder, int position) {

        String path = models.get(position).getImage()+".png";
        holder.textView.setText(""+models.get(position).getName());
        holder.imageView.setImageBitmap(getBitmapFromAsset(path));
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.text);
            imageView = (ImageView)itemView.findViewById(R.id.image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(click != null){
                        click.onClick(v,getPosition());
                    }
                }
            });
        }
    }


    private Bitmap getBitmapFromAsset(String strName) {
        // TODO Auto-generated method stub
        AssetManager assetManager = context.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(strName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }
}
