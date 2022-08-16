package com.example.android.whatyouate;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {


    TextView mname, mheight, mgoal, mweight, mBMI;
    String name, goal, height , weight , bmi;
    UserHelper db;
    ImageView profilePic;
    Bitmap bitmap;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mname = view.findViewById(R.id.name_tv);
        mheight = view.findViewById(R.id.tv_height);
        mweight = view.findViewById(R.id.tv_weight);
        mgoal = view.findViewById(R.id.tv_goal);
        mBMI = view.findViewById(R.id.tv_bmi);
        profilePic = view.findViewById(R.id.profile_iv);
        db = new UserHelper(getActivity());

        Cursor cursor = db.readAllData();
        if (cursor.getCount() == 0) {

        }else {
            while (cursor.moveToNext()) {

                name = cursor.getString(1) ;
                height = cursor.getString(2) + " cm";
                weight = cursor.getString(3) + " kg";
                bmi = cursor.getString(4);
                goal = cursor.getString(5);
                byte[] photo = cursor.getBlob(6);
                bitmap = BitmapFactory.decodeByteArray(photo, 0 ,photo.length);




            }

        }

        mname.setText(name);
        mheight.setText(height);
        mweight.setText(weight);
        mBMI.setText(bmi);
        mgoal.setText(goal);

        profilePic.setImageBitmap(bitmap);


        return view;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.edit_button, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(getActivity(), EditProfile.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }
}