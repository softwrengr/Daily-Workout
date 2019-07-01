package com.squaresdevelopers.fitness.Adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squaresdevelopers.fitness.R;
import com.squaresdevelopers.fitness.Utils.Model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by dream on 5/18/2018.
 *
 */

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.MyViewHolder> {

    View view;
    Context context;
    List<Model> alldata;
    List<Model> record_data;
    List<String> stringList;

    public RecordAdapter(Context context, List<Model> alldata,List<Model> record_data) {
        this.context = context;
        this.alldata = alldata;
        this.record_data = record_data;

    }


    @NonNull
    @Override
    public RecordAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.record_data, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordAdapter.MyViewHolder holder, int position) {

        String path = alldata.get(position).getImage() + ".png";
        holder.textView.setText("" + alldata.get(position).getName());
        holder.imageView.setImageBitmap(getBitmapFromAsset(path));
        Log.e("path==", "" + path);
        stringList = new ArrayList<>();

        holder.checkBox.setImageResource(R.drawable.ic_uncheck_black_24dp);


            String name  = alldata.get(position).getName();
            for (int j = 0; j<record_data.size();j++){

                if(record_data.get(j).getName().equals(name)){
                    holder.checkBox.setImageResource(R.drawable.ic_check_black_24dp);
                }

            }




    }

    @Override
    public int getItemCount() {
        return alldata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        ImageView checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.title);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            checkBox = (ImageView) itemView.findViewById(R.id.check);
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
