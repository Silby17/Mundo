package com.silbytech.mundo.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.silbytech.mundo.Communicator;
import com.silbytech.mundo.R;
import com.silbytech.mundo.adapters.AllCategoriesAdapter;
import com.silbytech.mundo.entities.CategoriesList;
import com.silbytech.mundo.entities.Category;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class AllCategoriesFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private String TAG = "AllCategoriesFragment";
    private AllCategoriesAdapter adapter;
    private ListView categoriesListView;
    private List<Category> categoryList;
    private ProgressBar loadingProgress;


    public AllCategoriesFragment() {}


    public static AllCategoriesFragment newInstance(String param1, String param2) {
        AllCategoriesFragment fragment = new AllCategoriesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View proView = inflater.inflate(R.layout.fragment_all_categories, container, false);
        //Loads the ProgressBar
        loadingProgress = proView.findViewById(R.id.load_progress);

        //Creates the server communicator
        Communicator communicator = new Communicator();
        Call<CategoriesList> call = communicator.getAllCategories();
        loadingProgress.setVisibility(View.VISIBLE);
        //Places the call
        call.enqueue(new Callback<CategoriesList>() {
            @Override
            public void onResponse(Call<CategoriesList> call, Response<CategoriesList> response) {
                loadingProgress.setVisibility(View.GONE);
                if(response.body().getCategoryList() != null){
                    categoryList = new ArrayList<>(response.body().getCategoryList());
                }
                categoriesListView =  proView.findViewById(R.id.categoriesListView);
                categoriesListView.setEmptyView(proView.findViewById(R.id.emptyCategoriesList));
                adapter = new AllCategoriesAdapter(categoryList, proView.getContext());
                categoriesListView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<CategoriesList> call, Throwable t) {
                loadingProgress.setVisibility(View.GONE);
                Toast.makeText(getContext(),
                        R.string.errorTryLater, Toast.LENGTH_SHORT).show();
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
