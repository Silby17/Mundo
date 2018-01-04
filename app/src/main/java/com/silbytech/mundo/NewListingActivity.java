package com.silbytech.mundo;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.gson.Gson;
import com.silbytech.mundo.communication.Interface;
import com.silbytech.mundo.responses.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/************************************
 * Created by Yosef Silberhaft
 ************************************/
public class NewListingActivity extends AppCompatActivity {
    public static final String TAG = "NewListingActivity";
    public static final String URL = "http://192.168.1.88:3000";
    private ProgressBar mProgressBar;
    Integer REQUEST_CAMERA = 1, REQUEST_GALLERY = 2;
    List<Uri> imageList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_main);
        initViews();
    }

    private void initViews() {
        this.imageList = new ArrayList<>();
        Button mBtImageSelect = findViewById(R.id.btn_select_image);
        mProgressBar = findViewById(R.id.progress);

        mBtImageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }


    private void selectImage(){
        //Sets the options for choosing an image
        final CharSequence[] items = {"Gallery", "Camera", "Cancel"};
        //Build a dialog to choose image
        AlertDialog.Builder builder = new AlertDialog.Builder(NewListingActivity.this);
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
                    uploadImage(getBytes(is));
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



    private void uploadImage(byte[] imageBytes) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Interface retrofitInterface = retrofit.create(Interface.class);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageBytes);

        MultipartBody.Part body = MultipartBody.Part.createFormData("myFile", "image.jpg", requestFile);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "myFile");

        Call<Response> call = retrofitInterface.uploadImage(description, body);

        mProgressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                mProgressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    Response responseBody = response.body();
                    Toast.makeText(getApplicationContext(),
                            responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    ResponseBody errorBody = response.errorBody();
                    Gson gson = new Gson();
                    try {
                        Response errorResponse = gson.fromJson(errorBody.string(), Response.class);
                        Log.d("Error Response", errorResponse.getMessage());
                        Toast.makeText(getApplicationContext(),
                                errorResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: "+t.getLocalizedMessage());
                Toast.makeText(getApplicationContext(),
                        "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }




}
