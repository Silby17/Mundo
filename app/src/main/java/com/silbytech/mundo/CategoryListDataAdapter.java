package com.silbytech.mundo;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.silbytech.mundo.entities.ListingModel;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class CategoryListDataAdapter extends RecyclerView.Adapter<CategoryListDataAdapter.SingleItemRowHolder> {
    private String itemId;
    private ArrayList<ListingModel> listingsList;
    private Context context;

    public CategoryListDataAdapter(ArrayList<ListingModel> listingsList, Context context) {
        this.listingsList = listingsList;
        this.context = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_listing_card, null);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int position) {
        ListingModel singleListing = listingsList.get(position);

        holder.tvTitle.setText(singleListing.getTitle());
        holder.tvPrice.setText(String.format("%.2f", singleListing.getDailyPrice()));
        holder.tvLocation.setText("Tel Aviv-Yaffo");

        Picasso.with(context).load(singleListing.getImageUrls().get(0))
                .into(holder.listingImg);

        itemId = listingsList.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return listingsList.size();
    }


    public class SingleItemRowHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvLocation;
        private TextView tvPrice;
        private ImageView listingImg;


        private SingleItemRowHolder(View view) {
            super(view);
            this.tvTitle = view.findViewById(R.id.tvListingTitle);
            this.tvLocation = view.findViewById(R.id.tvListingLocation);
            this.tvPrice = view.findViewById(R.id.tvListingPrice);
            this.listingImg = view.findViewById(R.id.listingImg);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), SingleListingActivity.class);
                    i.putExtra("itemId", itemId);
                    Toast.makeText(v.getContext(), "Touched " + itemId, Toast.LENGTH_SHORT).show();
                    v.getContext().startActivity(i);
                }
            });
        }
    }
}