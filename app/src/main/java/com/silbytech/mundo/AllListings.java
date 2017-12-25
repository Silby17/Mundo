package com.silbytech.mundo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.silbytech.mundo.entities.ListingsArray;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class AllListings extends AppCompatActivity {
    private ListingsArray listingsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Communicator communicator = new Communicator();

        Call<ListingsArray> call = communicator.getAllListings();

        call.enqueue(new Callback<ListingsArray>() {
            @Override
            public void onResponse(Call<ListingsArray> call, Response<ListingsArray> response) {
                System.out.println("Success");
                if(response.code() == 200){
                    listingsArray = response.body();
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            R.string.errorTryLater, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ListingsArray> call, Throwable t) {
                Toast.makeText(getApplicationContext(),
                        R.string.errorTryLater, Toast.LENGTH_SHORT).show();
            }
        });
    }
}