package com.example.android.whatyouate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditProfile extends AppCompatActivity {

    private static final int REQUEST_PHOTO_GALLERY = 99;
    String name, goal, height , weight ;
    ImageView profilePic;
    EditText name_et, height_et, weight_et, goal_et;
    UserHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Profile");
        setContentView(R.layout.activity_edit_profile);

        name_et = findViewById(R.id.name_et);
        height_et = findViewById(R.id.height_et);
        weight_et = findViewById(R.id.weight_et);
        goal_et = findViewById(R.id.goal_et);
        profilePic = findViewById(R.id.profile_iv2);
        db = new UserHelper(getApplicationContext());

    }

    public void Done(View view) {
        name = name_et.getText().toString().trim();
        height = height_et.getText().toString();

        weight = weight_et.getText().toString();
        float heightInMeter = Float.parseFloat(height)/100;
        float weightvalue = Float.parseFloat(weight);

        float bmi = (weightvalue/(heightInMeter * heightInMeter));
        String bmifinal = String.format("%.2f", bmi);

       // finalBmi = String.format("%.2f", bmi);

        goal = goal_et.getText().toString().trim();
        imageViewToByte(profilePic);

        boolean success = db.insertDate(name, height, weight, bmifinal , goal, imageViewToByte(profilePic) );
        if(success){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }




    }

    private byte[] imageViewToByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public void changePicture(View view) {

        Intent selectPhotoIntent = new Intent(Intent.ACTION_PICK);
        selectPhotoIntent.setType("image/*");
        startActivityForResult(selectPhotoIntent, REQUEST_PHOTO_GALLERY);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_PHOTO_GALLERY) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                profilePic.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}