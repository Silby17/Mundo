package com.silbytech.mundo.addListings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.shuhart.stepview.StepView;
import com.silbytech.mundo.R;
import com.silbytech.mundo.entities.ListingModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class AddListingScreenTwoActivity extends Activity {
    public String TAG = "AddListingScreenTwoActivity";
    ListingModel newListing;
    private StepView stepsView;
    Integer REQUEST_CAMERA = 1, REQUEST_GALLERY = 2;
    List<Uri> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_listing_screen_two);
        Button btnNext = findViewById(R.id.btnNext);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        final EditText listingTitle = findViewById(R.id.edtTitle);
        final EditText listingDesc = findViewById(R.id.edtDescription);
        ImageView listingImg = findViewById(R.id.imgUpload);
        Button btnChooseImg = findViewById(R.id.btnSelectImage);

        stepsView = findViewById(R.id.stepsViewTwo);
        initStepView();
        newListing = (ListingModel)getIntent().getSerializableExtra("NewListing");

        //Display the screen in a pop-up overlay of the screen
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //Gets the size of the screen
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        //Sets the minimized screen
        getWindow().setLayout((int)(width * .9), ((int)(height * .8)));

        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listingTitle.getText().toString().equals("") || listingDesc.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), R.string.please_fill_all_boxes, Toast.LENGTH_SHORT).show();
                }
                else{
                    newListing.setTitle(listingTitle.getText().toString());
                    newListing.setDescription(listingDesc.getText().toString());
                    Intent i = new Intent(AddListingScreenTwoActivity.this, AddListingScreenThreeActivity.class);
                    i.putExtra("NewListing", newListing);
                    startActivity(i);
                }
            }
        });
    }


    private void selectImage(){
        //Sets the options for choosing an image
        final CharSequence[] items = {"Gallery", "Camera", "Cancel"};
        //Build a dialog to choose image
        AlertDialog.Builder builder = new AlertDialog.Builder(AddListingScreenTwoActivity.this);
        builder.setTitle("Choose an image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //User clicks Camera
                if(items[i].equals("Camera")){
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, REQUEST_CAMERA);
                }
                //User clicks Gallery
                else if(items[i].equals("Gallery")){
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/jpeg");
                    try {
                        startActivityForResult(intent, REQUEST_GALLERY);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                    //User clicks cancel
                } else if(items[i].equals("Cancel")){
                    dialogInterface.dismiss();
                }
            }
        });
        //Shows the image chooser dialog
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                ImageView imgItem = findViewById(R.id.imgItem);
                imgItem.setImageBitmap(bitmap);
                this.imageList.add(data.getData());
                Log.d("Size of Image List is", Integer.toString(imageList.size()));
                InputStream is = getContentResolver().openInputStream(data.getData());
                //uploadImage(getBytes(is));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode == REQUEST_CAMERA && resultCode == RESULT_OK){
        }
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }
        return byteBuff.toByteArray();
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

        stepsView.go(1, false);
    }
}