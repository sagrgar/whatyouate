package com.example.android.whatyouate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UserHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "User.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "USER_TABLE";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_WEIGHT = "WEIGHT";
    private static final String COLUMN_HEIGHT = "HEIGHT";
    private static final String COLUMN_GOAL = "GOAL";
    private static final String COLUMN_BMI = "BMI";
    private static final String COLUMN_IMAGE = "IMAGE";
    public UserHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_HEIGHT + " TEXT, " +
                COLUMN_WEIGHT + " TEXT, " +
                COLUMN_BMI + " TEXT, " +
                COLUMN_GOAL + " TEXT, " +
                COLUMN_IMAGE + " BLOB);";
        sqLiteDatabase.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Boolean insertDate (String name, String height, String weight, String bmi, String goal, byte[] image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_HEIGHT, height);
        contentValues.put(COLUMN_WEIGHT, weight);
        contentValues.put(COLUMN_BMI, bmi);
        contentValues.put(COLUMN_GOAL, goal);
        contentValues.put(COLUMN_IMAGE, image);
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
}
