package com.example.myapplication.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.ui.adapters.MealAdapter;
import com.example.myapplication.db.entity.Meal;

import com.example.myapplication.db.entity.Plan;
import com.example.myapplication.ui.adapters.PlanDetailsAdapter;
import com.example.myapplication.viewmodel.MealViewModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static android.os.Build.VERSION.SDK_INT;

public class CameraActivity extends AppCompatActivity {

    private ImageView imageView;
    private ActivityResultLauncher<Intent> launcher;
    private MealViewModel mealViewModel;


    private String currentPhotoPath;
    private File photoFile;

    private Meal meal;
    private Plan plan;
    private final int PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        meal = (Meal)bundle.getSerializable("meal");
        plan = (Plan)bundle.getSerializable("plan");
        Log.i("RecipeActivity", "plan je ovo u camera activity"+ plan);
        Log.i("RecipeActivity", "meal je ovo u camera activity"+ meal);
        this.imageView = (ImageView)this.findViewById(R.id.imageView);

        mealViewModel =  new ViewModelProvider(this).get(MealViewModel.class);

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Uri outputUri = FileProvider.getUriForFile(CameraActivity.this,
                                    "com.example.myapplication.fileprovider", photoFile);

                            meal.setImage(outputUri.toString());
                            mealViewModel.update(meal);

                            Picasso.get()
                                    .load(outputUri)
                                    .placeholder((R.drawable.ic_baseline_image_24))
                                    .fit()
                                    .into(imageView);
                        }
                    }
                });
        if(SDK_INT <= 18){
            checkPermission();
        }else{
            dispatchTakePictureIntent();
        }

    }


   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);

            Uri outputUri = FileProvider.getUriForFile(this, "com.example.myapplication.fileprovider", photoFile);
            Log.i("RecipeActivity", "uri " + outputUri);

            meal.setImage(outputUri.toString()); //a kako vadim uy baze/; Uri uri = Uri.parse(urikaostring);
            //ovde sada moram da prosledim mealslist
            List<Meal> mealList = plan.getMeals();
            MealAdapter mealAdapter = new MealAdapter(this, mealList);
            mealAdapter.updateMeal(meal);
            mealAdapter.notifyDataSetChanged();

            Picasso.get().load(outputUri).fit().into(imageView);
        }
    }*/


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("RecipeActivity", "Error occured while creating the File ");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.myapplication.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoURI);
                //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                launcher.launch(takePictureIntent);
            }
        }
    }




    private File createImageFile() throws IOException {
        if(isExternalStorageWritable()) {
            // Create an image file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "JPEG_" + timeStamp + "_";
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

            File image = File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );

            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = image.getAbsolutePath();
            return image;
        }else{
            return  null;
        }
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Log.i("RecipeActivity", "External storage is avaliable");
            return true;
        }
        return false;
    }

    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            dispatchTakePictureIntent();

        } else {
            // You can directly ask for the permission.
            ActivityCompat.requestPermissions(this,   new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} ,
                    PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    dispatchTakePictureIntent();
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.

                    Toast.makeText(this,getString(R.string.permission_not_granted), Toast.LENGTH_LONG).show();
                }
                return;
        }
        // Other 'case' lines to check for other
        // permissions this app might request.
    }
}




