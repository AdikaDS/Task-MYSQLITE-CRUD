package com.example.taskmysqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "games-db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "games";
    public static final String GAMES_ID = "id";
    public static final String GAMES_NAME = "nama";
    public static final String GAMES_PRICE = "harga";
    public static final String GAMES_TYPE = "tipe";
    public static final String GAMES_IMAGE = "gambar";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_PRODUCT = "CREATE TABLE " + TABLE_NAME +
                "(" + GAMES_ID + " INTEGER PRIMARY KEY, " +
                GAMES_IMAGE + " BLOB, " +
                GAMES_NAME + " TEXT, " +
                GAMES_PRICE + " INTEGER, " +
                GAMES_TYPE + " TEXT)";

        sqLiteDatabase.execSQL(CREATE_TABLE_PRODUCT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
    }
}