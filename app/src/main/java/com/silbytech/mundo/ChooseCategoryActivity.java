package com.silbytech.mundo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shuhart.stepview.StepView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class ChooseCategoryActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_category_layout);

        ListView categoriesList = findViewById(R.id.choose_categories_listView);
        final TextView tvType = findViewById(R.id.tvType);
        tvType.setVisibility(View.GONE);
        final ListView typeList = findViewById(R.id.choose_type);
        final StepView stepsView = findViewById(R.id.stepsView);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * .9), ((int)(height * .8)));


        List<String> stepsArray = Arrays.asList(getResources().getStringArray(R.array.steps_array));

        stepsView.getState()
                .selectedTextColor(getApplicationContext().getResources().getColor(R.color.black))
                .doneTextColor(getApplicationContext().getResources().getColor(R.color.white))
                .animationType(StepView.ANIMATION_LINE)
                .nextTextColor(getApplicationContext().getResources().getColor(R.color.black))
                .selectedCircleColor(getApplicationContext().getResources().getColor( R.color.colorAccent))
                .selectedStepNumberColor(getApplicationContext().getResources().getColor( R.color.colorPrimary))
                .steps(stepsArray)
                .animationDuration(getResources().getInteger(android.R.integer.config_shortAnimTime))
                // other state methods are equal to the corresponding xml attributes
                .commit();




        ArrayAdapter<CharSequence> categoriesAdapter = ArrayAdapter
                .createFromResource(this, R.array.categories, android.R.layout.simple_list_item_1);

        categoriesList.setAdapter(categoriesAdapter);

        categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String it = adapterView.getItemAtPosition(i).toString();
                tvType.setVisibility(View.VISIBLE);
                if(adapterView.getItemAtPosition(i).equals("Bicycle")){
                    ArrayAdapter<CharSequence> bicycleTypeAdapter = ArrayAdapter
                            .createFromResource(getApplicationContext(), R.array.bicycle_types,
                                    android.R.layout.simple_list_item_1);
                    typeList.setAdapter(bicycleTypeAdapter);

                }
                else if(adapterView.getItemAtPosition(i).equals("Photography")){
                    ArrayAdapter<CharSequence> photographyTypeAdapter = ArrayAdapter
                            .createFromResource(getApplicationContext(), R.array.photography_types,
                                    android.R.layout.simple_list_item_1);
                    typeList.setAdapter(photographyTypeAdapter);

                }

                Toast.makeText(getApplicationContext(), it, Toast.LENGTH_SHORT).show();
                stepsView.go(2, true);
            }
        });
    }
}
