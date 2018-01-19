package com.silbytech.mundo.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.silbytech.mundo.CategoryListDataAdapter;
import com.silbytech.mundo.R;
import com.silbytech.mundo.entities.CategorySectionListings;
import com.silbytech.mundo.entities.ListingModel;
import java.util.ArrayList;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class AllListingsRecyclerViewAdapter extends RecyclerView.Adapter<AllListingsRecyclerViewAdapter.ItemRowHolder> {
    private String TAG = "AllListingsRecyclerViewAdapter";
    private ArrayList<CategorySectionListings> categorySectionListings;
    private Context context;


    /*********************************************************
     * Constructor Method
     * @param categorySectionListings - the Array of listings to be used
     * @param context - Application Context
     *********************************************************/
    public AllListingsRecyclerViewAdapter(ArrayList<CategorySectionListings> categorySectionListings, Context context) {
        this.categorySectionListings = categorySectionListings;
        this.context = context;
    }


    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.all_categories_listing_layout, null);
        return new ItemRowHolder(v);
    }


    @Override
    public void onBindViewHolder(AllListingsRecyclerViewAdapter.ItemRowHolder holder, int position) {
        final String categoryName = categorySectionListings.get(position).getCategoryName();
        ArrayList<ListingModel> singleListingsItems = categorySectionListings.get(position).getListingModels();
        holder.itemTitle.setText(categoryName);

        //Creates new adapter that will display all the Listings of each category
        CategoryListDataAdapter itemListDataAdapter = new CategoryListDataAdapter(singleListingsItems, context);

        holder.recycler_view_list.setHasFixedSize(true);
        holder.recycler_view_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recycler_view_list.setAdapter(itemListDataAdapter);
    }

    @Override
    public int getItemCount() {
        return categorySectionListings.size();
    }


    /********************************************************************
     * New Inner class that will handle the holder layout
     ********************************************************************/
    public class ItemRowHolder extends RecyclerView.ViewHolder {
        private RecyclerView recycler_view_list;
        private TextView itemTitle;

        private ItemRowHolder(View view) {
            super(view);
            this.itemTitle = view.findViewById(R.id.itemTitle);
            this.recycler_view_list = view.findViewById(R.id.main_recycler_view_list);
        }
    }
}