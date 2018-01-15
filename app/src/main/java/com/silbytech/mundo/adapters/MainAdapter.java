package com.silbytech.mundo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.silbytech.mundo.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> list;

    public MainAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        this.context = parent.getContext();
        ViewHolder vh = new ViewHolder(v, context);

        return vh;

    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {
        holder.title.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView itemImage;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTitle);
            itemImage = itemView.findViewById(R.id.itemImage);
            Picasso.with(context)
                    .load("https://images.pexels.com/photos/2242/wall-sport-green-bike.jpg?w=1260&h=750&auto=compress&cs=tinysrgb")
                    .resize(140, 130)
                    .centerCrop()
                    .into(itemImage);
        }
    }
}
