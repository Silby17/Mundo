package com.silbytech.mundo.addListings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.shuhart.stepview.StepView;
import com.silbytech.mundo.R;
import com.silbytech.mundo.entities.ListingModel;
import java.util.Arrays;
import java.util.List;


/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class AddListingScreenOneActivity extends Activity {
    ListingModel newListing;
    Button btnNextStep;
    boolean typeFlag = false;
    private TextView tvType;
    private ListView typeList;
    private StepView stepsView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_listing_screen_one);

        ListView categoriesList = findViewById(R.id.choose_categories_listView);
        tvType = findViewById(R.id.tvType);
        tvType.setVisibility(View.GONE);
        typeList = findViewById(R.id.choose_type_list);
        stepsView = findViewById(R.id.stepsView);
        btnNextStep = findViewById(R.id.btnNextStepOne);
        btnNextStep.setVisibility(View.GONE);

        //Creates a new Listing Model
        newListing = new ListingModel();

        //Sets the size of the overlay screen
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width * .9), ((int)(height * .8)));

        //Init the step view
        initStepView();

        ArrayAdapter<CharSequence> categoriesAdapter = ArrayAdapter
                .createFromResource(this, R.array.categories, android.R.layout.simple_list_item_1);
        categoriesList.setAdapter(categoriesAdapter);

        //Listener for the Category item List
        categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Gets the string of the clicked item
                String item = adapterView.getItemAtPosition(i).toString();
                tvType.setVisibility(View.VISIBLE);
                if(adapterView.getItemAtPosition(i).equals("Bicycle")){
                    //Changed FlagStatus to false
                    changeFlagStatus(false);
                    setTypeListAdapter("Bicycle");
                    newListing.setCategory(item);
                }
                else if(adapterView.getItemAtPosition(i).equals("Photography")){
                    //Changed FlagStatus to false
                    changeFlagStatus(false);
                    setTypeListAdapter("Photography");
                    newListing.setCategory(item);
                }
                else if(adapterView.getItemAtPosition(i).equals("Surfing")){
                    changeFlagStatus(false);
                    setTypeListAdapter("Surfing");
                    newListing.setCategory(item);
                }
            }
        });

        //Listener for the Type items list
        typeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Gets the string of the clicked item
                String item = adapterView.getItemAtPosition(i).toString();
                //Change FlagStatus to True as the user has selected both Category & type
                changeFlagStatus(true);
                newListing.setType(item);

            }
        });

        btnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddListingScreenOneActivity.this, AddListingScreenTwoActivity.class);
                i.putExtra("NewListing", newListing);
                startActivity(i);
            }
        });
    }

    /**********************************************************************
     * This function will set and display the correct strings in
     * the type list
     * @param category - category that needs to be displayed
     **********************************************************************/
    public void setTypeListAdapter(String category){
        tvType.setVisibility(View.VISIBLE);
        switch (category) {
            case "Bicycle":
                ArrayAdapter<CharSequence> bicycleTypeAdapter = ArrayAdapter
                        .createFromResource(getApplicationContext(), R.array.bicycle_types,
                                android.R.layout.simple_list_item_1);
                typeList.setAdapter(bicycleTypeAdapter);
                break;

            case "Photography":
                ArrayAdapter<CharSequence> photographyTypeAdapter = ArrayAdapter
                        .createFromResource(getApplicationContext(), R.array.photography_types,
                                android.R.layout.simple_list_item_1);
                typeList.setAdapter(photographyTypeAdapter);
                break;

            case "Surfing":
                ArrayAdapter<CharSequence> surfingTypeAdapter = ArrayAdapter
                        .createFromResource(getApplicationContext(), R.array.surfboard_types,
                                android.R.layout.simple_list_item_1);
                typeList.setAdapter(surfingTypeAdapter);
                break;
        }
    }


    /*********************************************************************
     * This function will change the status of the FlagStatus variable
     * to the given status
     * @param status - new status to be changed
     *********************************************************************/
    public void changeFlagStatus(boolean status){
        typeFlag = status;
        if(typeFlag){
            btnNextStep.setVisibility(View.VISIBLE);
        }
        else {
            btnNextStep.setVisibility(View.GONE);
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
                .doneStepLineColor(getApplicationContext().getResources().getColor(R.color.white))
                .selectedStepNumberColor(getApplicationContext().getResources().getColor( R.color.colorPrimary))
                .steps(stepsArray)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                .commit();
    }
}