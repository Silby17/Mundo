package com.silbytech.mundo;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.silbytech.mundo.entities.ListingModel;
import com.silbytech.mundo.responses.SingleListingServerResponse;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class SingleListingActivity extends AppCompatActivity implements OnMapReadyCallback {
    public String TAG = "SingleListingActivity";
    private GoogleMap mMap;
    public int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private ListingModel listingModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_listing_info_layout);
        Intent i = getIntent();
        //Gets item ID from the intent passed through
        String itemId = i.getStringExtra("itemId");

        //Init all the layout items
        final TextView tvTitle = findViewById(R.id.tvListingTitle);
        final TextView tvDesc = findViewById(R.id.tvDescription);
        final ImageView imgListing = findViewById(R.id.listingImg);
        final TextView tvHourPrice = findViewById(R.id.tvPriceHour);
        final TextView tvDayPrice = findViewById(R.id.tvPriceDay);
        final TextView tvDayWeek = findViewById(R.id.tvPriceWeek);


        Communicator communicator = new Communicator();
        Call<SingleListingServerResponse> call = communicator.getListingByID(itemId);

        call.enqueue(new Callback<SingleListingServerResponse>() {
            @Override
            public void onResponse(Call<SingleListingServerResponse> call, Response<SingleListingServerResponse> response) {
                if(response.code() == 200){
                    listingModel = response.body().getListingModel();
                    tvTitle.setText(listingModel.getTitle());
                    tvDesc.setText(listingModel.getDescription());
                    Picasso.with(getApplicationContext()).load(listingModel.getImageUrls().get(0)).into(imgListing);

                    String hr = "₪" + Double.toString(listingModel.getHourlyPrice());
                    String daily = "₪" + Double.toString(listingModel.getDailyPrice());
                    String weekly = "₪" + Double.toString(listingModel.getWeeklyPrice());

                    tvHourPrice.setText(hr);
                    tvDayPrice.setText(daily);
                    tvDayWeek.setText(weekly);
                }
                //The user has no authorized token
                else if(response.code() == 401){
                    Toast.makeText(getApplicationContext(), R.string.unauthorized, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), R.string.errorTryLater, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SingleListingServerResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.errorTryLater, Toast.LENGTH_SHORT).show();
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
            addresses = gc.getFromLocationName("Yoni Netanyahu 20 Givat Shmuel", 5);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(addresses.size());
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        mMap = googleMap;
        LatLng listingLocation = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
        mMap.setMinZoomPreference(13.5f);
        mMap.addMarker(new MarkerOptions().position(listingLocation)
                .title("Marker in Sydney"));
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

    public void onBackClicked(){
        this.finish();
    }
}
