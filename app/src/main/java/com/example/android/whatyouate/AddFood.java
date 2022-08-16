package com.example.android.whatyouate;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddFood extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    EditText date_et, time_et, water_et, comment_et, meal_et;
    Spinner spinner;
    AddFoodHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add food");

        water_et = findViewById(R.id.water_editText);
        meal_et = findViewById(R.id.meal_editText);
        comment_et = findViewById(R.id.comment_editText);
        DB = new AddFoodHelper(this);



        spinner = findViewById(R.id.mealType_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.meal_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


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

        date_et = findViewById(R.id.date_editText);
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
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000);
        datePickerDialog.show();
    }

    public void timePicker(View view) {

        time_et = findViewById(R.id.time_editText);
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


    public void save(View view) {
        String date = date_et.getText().toString();
        String time = time_et.getText().toString();
        String mealType = spinner.getSelectedItem().toString();
        String water = water_et.getText().toString();
        String meal = meal_et.getText().toString().trim();
        String comment = comment_et.getText().toString().trim();


        Boolean insert = DB.insertDate(mealType, date, time, meal, water, comment);
        if(insert == true) {
            Toast.makeText(this, "Food added successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "Failed to add food", Toast.LENGTH_SHORT).show();
        }


    }
}