package com.silbytech.mundo.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.silbytech.mundo.adapters.AllListingsRecyclerViewAdapter;
import com.silbytech.mundo.Communicator;
import com.silbytech.mundo.R;
import com.silbytech.mundo.entities.CategorySectionListings;
import com.silbytech.mundo.entities.CategorySectionListingsList;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class AllCategoriesFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private String TAG = "AllCategoriesFragment";
    private ProgressBar loadingProgress;

    private ArrayList<CategorySectionListings> categorySectionListings;

    public AllCategoriesFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View proView = inflater.inflate(R.layout.fragment_all_categories, container, false);
        //Loads the ProgressBar
        final TextView emptyText = proView.findViewById(R.id.emptyCategoriesList);
        //Loads the Default textView
        loadingProgress = proView.findViewById(R.id.load_progress);


        //Creates the server communicator
        final Call<CategorySectionListingsList> call = new Communicator().getListingsByCategory();

        loadingProgress.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<CategorySectionListingsList>() {
            @Override
            public void onResponse(Call<CategorySectionListingsList> call, Response<CategorySectionListingsList> response) {
                loadingProgress.setVisibility(View.GONE);
                if(response.body().getFullListings().size() > 0){
                    categorySectionListings = response.body().getFullListings();

                    //Init the Main (Vertical) RecyclerView
                    RecyclerView mainRecyclerView = proView.findViewById(R.id.my_recycler_view);
                    mainRecyclerView.setHasFixedSize(true);

                    //Init new Adapter
                    AllListingsRecyclerViewAdapter adapter = new AllListingsRecyclerViewAdapter(categorySectionListings, getContext());
                    mainRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

                    //Sets the Layout adapter
                    mainRecyclerView.setAdapter(adapter);
                    emptyText.setVisibility(View.GONE);
                }
                else {
                    emptyText.setText(R.string.nothingToShow);
                }
            }
            @Override
            public void onFailure(Call<CategorySectionListingsList> call, Throwable t) {
                Toast.makeText(getContext(), R.string.errorTryLater, Toast.LENGTH_SHORT).show();
                emptyText.setText(R.string.nothingToShow);
                loadingProgress.setVisibility(View.GONE);
            }
        });
        return proView;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            Log.d(TAG, "Attached All Categories Fragment");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}