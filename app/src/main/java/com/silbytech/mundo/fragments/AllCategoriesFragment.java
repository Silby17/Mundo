package com.silbytech.mundo.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.silbytech.mundo.R;
import com.silbytech.mundo.adapters.AllCategoriesAdapter;

import java.util.ArrayList;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class AllCategoriesFragment extends Fragment {
    private ArrayList<String> arrList;
    private AllCategoriesAdapter adapter;
    private ListView categoriesListView;


    private OnFragmentInteractionListener mListener;

    public AllCategoriesFragment() {}


    public static AllCategoriesFragment newInstance(String param1, String param2) {
        AllCategoriesFragment fragment = new AllCategoriesFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View proView = inflater.inflate(R.layout.fragment_all_categories, container, false);

        this.categoriesListView = proView.findViewById(R.id.categoriesListView);
        arrList= new ArrayList<>();
        for(int i = 0; i < 10; i++){
            arrList.add("New title# " + i);
        }
        adapter = new AllCategoriesAdapter(arrList, proView.getContext());
        categoriesListView.setAdapter(adapter);
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
            Toast.makeText(context, "All Categories Attached", Toast.LENGTH_SHORT).show();
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
