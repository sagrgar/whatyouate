package com.example.android.whatyouate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {



    RecyclerView recyclerView;
    AddFoodHelper myDB;
    ArrayList<String> meal_id, mealType, date, time, meal, water, comment;
    CustomAdapter customAdapter;
    TextView nodata;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        Log.v("history", "Create");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_history, container, false);
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        // Add the following lines to create RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        nodata = view.findViewById(R.id.nodata_tv);
        myDB = new AddFoodHelper(getActivity());
        mealType = new ArrayList<>();
        date = new ArrayList<>();
        time = new ArrayList<>();
        meal = new ArrayList<>();
        water = new ArrayList<>();
        comment = new ArrayList<>();
        meal_id = new ArrayList<>();

        storeDataInArrays();
        customAdapter = new CustomAdapter(getActivity(), meal_id, mealType, date, time, meal, water, comment);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Log.v("history", "CreateView");


        return view;

    }

    void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            nodata.setVisibility(View.VISIBLE);

        }else {
            while (cursor.moveToNext()) {

                meal_id.add(cursor.getString(0));
                mealType.add(cursor.getString(1));
                date.add(cursor.getString(2));
                time.add(cursor.getString(3));
                meal.add(cursor.getString(4));
                water.add(cursor.getString(5));
                comment.add(cursor.getString(6));

            }
            nodata.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v("history", "Resume");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v("history", "start");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v("history", "stop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v("history", "pause");
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.my_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete_all:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Delete all?");
                builder.setMessage("Are you sure you want to delete all data?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        AddFoodHelper myDb = new AddFoodHelper(getActivity());
                        myDb.deleteAllData();
                        getFragmentManager().beginTransaction().detach(HistoryFragment.this).attach(HistoryFragment.this).commit();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.create().show();
                return true;


            case R.id.share:
                StringBuffer data = new StringBuffer();
                data.append("ID,MealType,Date,Time,Meal,Water,Comment");
                Cursor cursor = myDB.readAllData();
                if (cursor.getCount() == 0) {
                    Toast.makeText(getActivity(), "No data to share", Toast.LENGTH_SHORT).show();
                }else {
                    while (cursor.moveToNext()) {
                            data.append("\n" + cursor.getString(0)+ ",");
                            data.append(cursor.getString(1)+ ",");
                            data.append("     " + cursor.getString(2)+ ",");
                            data.append(cursor.getString(3)+ ",");
                            data.append(cursor.getString(4)+ ",");
                            data.append(cursor.getString(5)+ ",");
                            data.append(cursor.getString(6));
                    }
                    try {
                        FileOutputStream out = getContext().openFileOutput("data.csv", Context.MODE_PRIVATE);
                        out.write(data.toString().getBytes());
                        out.close();

                        Context context = getContext();
                        File fileLocation = new File(context.getFilesDir(), "data.csv");
                        Uri path = FileProvider.getUriForFile(getContext(), "com.example.whatyouate.fileprovider", fileLocation);

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/csv");
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Data.csv");
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        intent.putExtra(Intent.EXTRA_STREAM, path);
                        startActivity(Intent.createChooser(intent, "Send mail..."));
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }



                return true;
        }
        return super.onOptionsItemSelected(item);




//            AddFoodHelper myDb = new AddFoodHelper(getActivity());
//            myDb.deleteAllData();
//            getFragmentManager().beginTransaction().detach(HistoryFragment.this).attach(HistoryFragment.this).commit();


    }


}