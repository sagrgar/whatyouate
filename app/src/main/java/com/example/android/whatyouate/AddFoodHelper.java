package com.example.android.whatyouate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class AddFoodHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Food.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "FOOD_TABLE";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_MEALTYPE = "MEALTYPE";
    private static final String COLUMN_DATE = "DATE";
    private static final String COLUMN_TIME = "TIME";
    private static final String COLUMN_MEAL = "MEAL";
    private static final String COLUMN_WATER = "WATER";
    private static final String COLUMN_COMMENT = "COMMENT";

    public AddFoodHelper(@Nullable  Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MEALTYPE + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_TIME + " TEXT, " +
                COLUMN_MEAL + " TEXT, " +
                COLUMN_WATER + " TEXT, " +
                COLUMN_COMMENT + " TEXT);";
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Boolean insertDate (String mealtype, String date, String time, String meal, String water, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MEALTYPE, mealtype);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_TIME, time);
        contentValues.put(COLUMN_MEAL, meal);
        contentValues.put(COLUMN_WATER, water);
        contentValues.put(COLUMN_COMMENT, comment);
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();
        if(result == -1)

            return false;

        else
            return true;

    }

    Cursor readAllData() {

        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateData(String row_id, String mealtype, String date, String time, String meal, String water, String comment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MEALTYPE, mealtype);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_TIME, time);
        contentValues.put(COLUMN_MEAL, meal);
        contentValues.put(COLUMN_WATER, water);
        contentValues.put(COLUMN_COMMENT, comment);
        long result = db.update(TABLE_NAME, contentValues, "_id=?", new String[] {row_id});
        if(result == -1)
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();

        else
            Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show();

    }
    void deleteItem(String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[] {row_id});
        if(result == -1) {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }



}
