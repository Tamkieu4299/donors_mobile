package com.example.as2_blood_donation.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "blood_donation.db";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_USER = "CREATE TABLE Users (id INTEGER PRIMARY KEY, name TEXT, email TEXT, password TEXT, role TEXT)";
    private static final String CREATE_TABLE_SITE = "CREATE TABLE DonationSites (siteId INTEGER PRIMARY KEY, name TEXT, latitude REAL, longitude REAL, address TEXT, requiredBloodTypes TEXT, eventDate TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_SITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS DonationSites");
        onCreate(db);
    }
}
