package com.silbytech.mundo.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.silbytech.mundo.R;

import java.util.ArrayList;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class AllCategoriesAdapter extends BaseAdapter {
    private ArrayList<String> recList;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<String> list;
    private String URL = "https://images.pexels.com/photos/2242/wall-sport-green-bike.jpg?w=1260&h=750&auto=compress&cs=tinysrgb";
    private Context context;

    public AllCategoriesAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.single_category_fragment, null);
        TextView title = v.findViewById(R.id.categoryTitle);
        title.setText(list.get(i));

        recList = new ArrayList<>();
        for(int j = 0; j < 10; j++){
            recList.add("Recycle Title#  " + i);
        }


        mRecyclerView = v.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MainAdapter(recList);
        mRecyclerView.setAdapter(mAdapter);


        return v;
    }
}
