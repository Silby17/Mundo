package com.silbytech.mundo.addListings;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shuhart.stepview.StepView;
import com.silbytech.mundo.R;
import com.silbytech.mundo.entities.ListingModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class AddListingScreenThreeActivity extends FragmentActivity implements OnMapReadyCallback {
    private String TAG = "AddListingScreenThreeActivity";
    ListingModel newListing;
    StepView stepsView;
    private GoogleMap mMap;
    public int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private Button btnNext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_listing_screen_three);

        //Display the screen in a pop-up overlay of the screen
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //Gets the size of the screen
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        //Sets the minimized screen
        getWindow().setLayout((int)(width * .9), ((int)(height * .8)));

        //Initialize components
        this.btnNext = findViewById(R.id.btnNextThree);
        btnNext.setVisibility(View.GONE);
        stepsView = findViewById(R.id.stepsViewThree);
        newListing = (ListingModel)getIntent().getSerializableExtra("NewListing");
        initStepView();

        //Set onclick Listener for the Next button
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddListingScreenThreeActivity.this, AddListingScreenFourActivity.class);
                i.putExtra("NewListing", newListing);
            }
        });


        //Init the Support Fragment For the Map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Init the PlaceAutoCompleteFragment for the autocomplete GoogleMaps search
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        //The Google-Maps auto complete handler
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                Log.i(TAG, "Place: " + place.getName());
                LatLng pickedPlace = place.getLatLng();
                changeLocation(pickedPlace);
                btnNext.setVisibility(View.VISIBLE);
            }
            @Override
            public void onError(Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }


    public void changeLocation(LatLng currentPosition){
        mMap.addMarker(new MarkerOptions().position(currentPosition)
                .title("Marker Picked"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentPosition));
        mMap.setMinZoomPreference(13.0f);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        Geocoder gc = new Geocoder(getApplicationContext());
        List<Address> addresses = new ArrayList<>();
        try {
            addresses = gc.getFromLocationName("Tel Aviv Israel", 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Add marker to Tel Aviv israel
        // and move the map's camera to the same location.
        mMap = googleMap;
        LatLng listingLocation = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
        mMap.setMinZoomPreference(13.5f);
        mMap.addMarker(new MarkerOptions().position(listingLocation)
                .title("Listing"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(listingLocation));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }



    /*********************************************************************
     * This function will initialize the step view
     *********************************************************************/
    public void initStepView(){
        List<String> stepsArray = Arrays.asList(getResources().getStringArray(R.array.steps_array));
        stepsView.getState()
                .selectedTextColor(getApplicationContext().getResources().getColor(R.color.white))
                .doneTextColor(getApplicationContext().getResources().getColor(R.color.white))
                .animationType(StepView.ANIMATION_LINE)
                .nextTextColor(getApplicationContext().getResources().getColor(R.color.black))
                .selectedCircleColor(getApplicationContext().getResources().getColor( R.color.colorAccent))
                .selectedStepNumberColor(getApplicationContext().getResources().getColor( R.color.colorPrimary))
                .steps(stepsArray)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .commit();
        stepsView.go(2, false);
    }
}
