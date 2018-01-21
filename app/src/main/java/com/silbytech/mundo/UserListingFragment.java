package com.silbytech.mundo;

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
import com.silbytech.mundo.adapters.UserListingsAdapter;
import com.silbytech.mundo.entities.ListingModel;
import com.silbytech.mundo.entities.ListingsList;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class UserListingFragment extends Fragment {
    private String TAG = "UserListingsFragment";
    private OnFragmentInteractionListener mListener;
    private static final String USER_ID = "userId";
    private static final String USER_TOKEN = "userToken";
    private ArrayList<ListingModel> userListings;
    private String userToken;
    private String userId;


    public UserListingFragment() {}


    public static UserListingFragment newInstance(String userId, String userToken) {
        UserListingFragment fragment = new UserListingFragment();
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View proView =  inflater.inflate(R.layout.fragment_user_listing, container, false);
        final ProgressBar progressBar = proView.findViewById(R.id.load_progress);
        final ListView userListingListView = proView.findViewById(R.id.userListingListView);

        Communicator communicator = new Communicator();
        Call<ListingsList> call = communicator.getUsersListings(userToken);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<ListingsList>() {
            @Override
            public void onResponse(Call<ListingsList> call, Response<ListingsList> response) {
                progressBar.setVisibility(View.GONE);
                if(response.code() == 200){
                    userListings = response.body().getListingsList();
                    UserListingsAdapter adapter = new UserListingsAdapter(userListings, getContext());
                    userListingListView.setAdapter(adapter);
                }
                else if(response.code() == 401){
                    Toast.makeText(getContext(), R.string.unauthorized, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), R.string.errorTryLater, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ListingsList> call, Throwable t) {
                Toast.makeText(getContext(), R.string.errorTryLater, Toast.LENGTH_SHORT).show();
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
            Log.d(TAG, "Loaded Fragment");
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