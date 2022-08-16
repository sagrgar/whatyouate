package com.example.android.whatyouate;

import androidx.annotation.Nullable;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;


public class Register extends AppCompatActivity {


    private static final int REQUEST_PHOTO_GALLERY = 99;
    String name, goal, height , weight ;
    ImageView profilePic;
    TextView selectpp;
    EditText name_et, height_et, weight_et, goal_et;
    UserHelper db;
    EditText username, password, rePassword;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.usernameR_editText);
        password = findViewById(R.id.passwordR_editText);
        rePassword = findViewById(R.id.rePasswordR_editText);
        selectpp = findViewById(R.id.textview_selectpp);
        DB = new DBHelper(this);
        name_et = findViewById(R.id.name_editText);
        height_et = findViewById(R.id.height_editText);
        weight_et = findViewById(R.id.weight_editText);
        goal_et = findViewById(R.id.goal_editText);
        profilePic = findViewById(R.id.displayPic);
        db = new UserHelper(this);
    }

    public void Register(View view) {
        String user = username.getText().toString();
        String pass = password.getText().toString();

        String rePass = rePassword.getText().toString();

        if (user.equals("") || pass.equals("") || rePass.equals(""))
            Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
        else {
            if(pass.equals(rePass)) {
                Boolean checkUser = DB.checkUsername(user);
                if (checkUser == false) {
                    byte[] inputBytes = pass.getBytes();
                    byte[] outputBytes = new byte[0];

                    try {
                        outputBytes = ShaEncryption.encryptSHA(inputBytes, "SHA-1");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    BigInteger shaData = new BigInteger(outputBytes);
                    String hashPass = shaData.toString();

                    Boolean insert = DB.insertDate(user, hashPass);
                    if (insert == true) {
                        Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    }else {
                        Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "User already exists! Please sign in", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        }

        userData();




    }

    public void selectDisplayPic(View view) {

        Intent selectPhotoIntent = new Intent(Intent.ACTION_PICK);
        selectPhotoIntent.setType("image/*");
        startActivityForResult(selectPhotoIntent, REQUEST_PHOTO_GALLERY);
    }

    private byte[] imageViewToByte(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }


    public void userData() {
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

            Boolean success = db.insertDate(name, height, weight, bmifinal , goal, imageViewToByte(profilePic) );


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_PHOTO_GALLERY) {
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                selectpp.setVisibility(View.INVISIBLE);
                profilePic.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}