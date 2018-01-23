package com.silbytech.mundo.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silbytech.mundo.R;
import com.silbytech.mundo.UserListingFragment;
import com.silbytech.mundo.adapters.ViewPagerAdapter;

public class MyAccountFragment extends Fragment {
    private String TAG = "MyAccountFragment";

    private static final String USER_ID = "userId";
    private static final String USER_TOKEN = "token";
    private String userId;
    private String userToken;

    private OnFragmentInteractionListener mListener;

    public MyAccountFragment() {}


    public static MyAccountFragment newInstance(String userId, String userToken) {
        MyAccountFragment fragment = new MyAccountFragment();
        Bundle args = new Bundle();
        args.putString(USER_ID, userId);
        args.putString(USER_TOKEN, userToken);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            userId = getArguments().getString(USER_ID);
            userToken = getArguments().getString(USER_TOKEN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        ViewPager viewPager = view.findViewById(R.id.vpAccount);
        TabLayout tabLayout = view.findViewById(R.id.accountTabs);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        return view;
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
            Log.d(TAG, "Loaded MyAccountFragment");
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

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getFragmentManager());

        String tok = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1YTMyODFjNDU3ZjhjZTI1MWM3NGE4YzgiLCJhY2Nlc3MiOiJhdXRoIiwiaWF0IjoxNTE2NDY1NjI4fQ.kHitKHjAwSZCPVI2HRlkHPD8Djs-PdPCc59PcqMQ8_c";
        adapter.addFragment(UserListingFragment.newInstance(userId, tok), "My Listings");
        adapter.addFragment(UsersRentalsListFragment.newInstance(userId, tok), "My Rentals");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
    }
}