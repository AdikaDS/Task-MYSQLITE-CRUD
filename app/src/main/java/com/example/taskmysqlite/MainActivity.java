package com.example.taskmysqlite;

import static com.example.taskmysqlite.DatabaseHelper.TABLE_NAME;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;
    DatabaseHelper mDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;
    FloatingActionButton fab;
    GameAdapter gameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseHelper = new DatabaseHelper(this);
        rv = findViewById(R.id.rv_game);
        fab = findViewById(R.id.add);

        displayData();
        showRecyclerList();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TambahActivity.class);
                startActivity(intent);
            }
        });

    }

    private void showRecyclerList(){
        rv.setLayoutManager(new LinearLayoutManager(this, rv.VERTICAL, false));
    }

    public void displayData() {
        sqLiteDatabase = mDatabaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME , null);
        ArrayList<Game> game = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            byte[] pictGame = cursor.getBlob(1);
            String nameGame = cursor.getString(2);
            String priceGame = cursor.getString(3);
            String typeGame = cursor.getString(4);
            game.add(new Game(id, pictGame, nameGame, priceGame, typeGame));
        }

        cursor.close();
        gameAdapter = new GameAdapter(this,R.layout.item_games, game, sqLiteDatabase);
        rv.setAdapter(gameAdapter);


    }

}