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

    public DatabaseHelper (Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_PRODUCT = "CREATE TABLE " + TABLE_NAME +
                "(" + GAMES_ID + " INTEGER PRIMARY KEY, " +
                GAMES_IMAGE +" BLOB, " +
                GAMES_NAME + " TEXT, " +
                GAMES_PRICE + " INTEGER, " +
                GAMES_TYPE + " TEXT)"
                ;

        sqLiteDatabase.execSQL(CREATE_TABLE_PRODUCT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
    }

    // Mengget semua data SQLITE
//    public Cursor allData() {
//        SQLiteDatabase db = getWritableDatabase();
//        Cursor cur = db.rawQuery("SELECT * FROM " + TABLE_NAME , null);
//        return cur;
//    }

    // Mengget 1 Data SQLITE berdasarkan ID
//    public Cursor oneData(Long id) {
//        SQLiteDatabase db = getWritableDatabase();
//        Cursor cur = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + GAMES_ID + "=" + id, null);
//        return cur;
//    }

    // Tambah Data Games
//    public void insertGame (ContentValues values) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.insert(TABLE_NAME, null, values);
//
//    }

    // Update Data Games
//    public void updateGame(ContentValues values, long id) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.update(TABLE_NAME, values, GAMES_ID + "=" + id, null);
//    }

    // Delete Data Games
//    public void deleteGame(long id) {
//        SQLiteDatabase db = getWritableDatabase();
//        db.delete(TABLE_NAME, GAMES_ID + "=" + id, null);
//        db.close();
//    }
}
