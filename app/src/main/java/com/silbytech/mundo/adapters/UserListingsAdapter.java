package com.silbytech.mundo.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import com.silbytech.mundo.R;
import com.silbytech.mundo.entities.ListingModel;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class UserListingsAdapter extends BaseAdapter {
    public String TAG = "UserListingsAdapter";
    private ArrayList<ListingModel> userListings;
    private Context context;

    public UserListingsAdapter(ArrayList<ListingModel> userListings, Context context) {
        this.userListings = userListings;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.userListings.size();
    }

    @Override
    public Object getItem(int i) {
        return userListings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.single_user_listings_frag, null);

        ImageView listingImg = v.findViewById(R.id.listingImg);
        TextView listingTitle = v.findViewById(R.id.tvTitle);
        TextView listingLocation = v.findViewById(R.id.tvListingLocation);
        TextView listingPrice = v.findViewById(R.id.tvListingPrice);

        Switch active = v.findViewById(R.id.swListingActive);

        Picasso.with(context.getApplicationContext())
                .load(userListings.get(i).getImageUrls().get(0))
                .into(listingImg);
        listingTitle.setText(userListings.get(i).getTitle());
        listingLocation.setText(userListings.get(i).getLocation());

        String price = "₪" + Double.toString(userListings.get(i).getDailyPrice());
        listingPrice.setText(price);

        active.setChecked(userListings.get(i).isActive());

        if(userListings.get(i).isActive()){
            active.setText(R.string.active);
        }
        else {
            active.setText(R.string.not_active);
        }
        return v;
    }
}
