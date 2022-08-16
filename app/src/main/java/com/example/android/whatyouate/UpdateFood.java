package com.example.android.whatyouate;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateFood extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    EditText date_et, time_et, water_et, comment_et, meal_et;
    String date, time, water, mealtype, meal, comment, id;
    Spinner spinner;
    AddFoodHelper DB;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_food);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Update food");

        water_et = findViewById(R.id.water_editText2);
        meal_et = findViewById(R.id.meal_editText2);
        comment_et = findViewById(R.id.comment_editText2);

        date_et = findViewById(R.id.date_editText2);

        time_et = findViewById(R.id.time_editText2);

        update = findViewById(R.id.update_button);


        spinner = findViewById(R.id.mealType_spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.meal_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        getIntentData();

//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick (View view) {
//                DB = new AddFoodHelper(UpdateFood.this);
//                DB.updateData(id, mealtype, date, time, meal, water, comment);
//            }
//        });


    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        String text = parent.getItemAtPosition(pos).toString();
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


    public void datePicker(View view) {

        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        // date picker dialog

        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date_et.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    public void timePicker(View view) {
        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        // time picker dialog
        timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        time_et.setText(sHour + ":" + sMinute);
                    }
                }, hour, minutes, false);
        timePickerDialog.show();
    }


    public void update(View view) {


        DB = new AddFoodHelper(this);

        String date = date_et.getText().toString();
        String time = time_et.getText().toString();
        String mealtype = spinner.getSelectedItem().toString();
        String water = water_et.getText().toString();
        String meal = meal_et.getText().toString();
        String comment = comment_et.getText().toString();

        DB.updateData(id, mealtype, date, time, meal, water, comment);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);




    }

    void getIntentData() {
        if (getIntent().hasExtra("mealtype") &&
                getIntent().hasExtra("date") &&
                getIntent().hasExtra("time") &&
                getIntent().hasExtra("meal") &&
                getIntent().hasExtra("water") &&
                getIntent().hasExtra("id") &&
                getIntent().hasExtra("comment")) {

            mealtype = getIntent().getStringExtra("mealtype");
            date = getIntent().getStringExtra("date");
            time = getIntent().getStringExtra("time");
            meal = getIntent().getStringExtra("meal");
            water = getIntent().getStringExtra("water");
            comment = getIntent().getStringExtra("comment");
            id = getIntent().getStringExtra("id");

            water_et.setText(water);
            comment_et.setText(comment);
            meal_et.setText(meal);
            date_et.setText(date);
            time_et.setText(time);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.meal_type, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            if (mealtype != null) {
                int spinnerPosition = adapter.getPosition(mealtype);
                spinner.setSelection(spinnerPosition);
            }


        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();

        }
    }
}