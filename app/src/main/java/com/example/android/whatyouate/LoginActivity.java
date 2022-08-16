package com.example.android.whatyouate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;

public class LoginActivity extends AppCompatActivity {

    EditText username, password;
    DBHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username_editText);
        password = findViewById(R.id.password_editText);
        DB = new DBHelper(this);
    }

    public void logIn(View view) {

        String user = username.getText().toString();
        String pass = password.getText().toString();
        
        if (user.equals("") || pass.equals(""))
            Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show();

        else {
            Boolean checkUser = DB.checkUsername(user);
            if (checkUser == false) {
                Toast.makeText(this, "User does not exist", Toast.LENGTH_SHORT).show();
            }else {
                byte[] inputBytes = pass.getBytes();
                byte[] outputBytes = new byte[0];

                try {
                    outputBytes = ShaEncryption.encryptSHA(inputBytes, "SHA-1");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                BigInteger shaData = new BigInteger(outputBytes);
                String hashPass = shaData.toString();
                Boolean checkUserPass = DB.checkUsernamePassword(user, hashPass);
                if (checkUserPass == true) {
                    Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                }else {
                    Toast.makeText(this, "Username and password do not match", Toast.LENGTH_SHORT).show();
                }

            }

        }

    }

    public void register(View view) {
        Intent intent = new Intent(getApplicationContext(), Register.class);
        startActivity(intent);
    }

    public void skip(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}