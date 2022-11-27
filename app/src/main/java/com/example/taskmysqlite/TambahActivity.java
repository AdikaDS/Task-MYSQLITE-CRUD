package com.example.taskmysqlite;

import static com.example.taskmysqlite.DatabaseHelper.TABLE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;

public class TambahActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    SQLiteDatabase sqLiteDatabase;
    ImageView gamePicture;
    EditText name, price, type;
    Button add;

    public static final int CAMERA_REQUEST = 100;
    public static final int STORAGE_REQUEST = 101;
    String[] cameraPermission;
    String[] storagePermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        gamePicture = findViewById(R.id.gambar_add);
        name = findViewById(R.id.et_name_add);
        price = findViewById(R.id.et_price_add);
        type = findViewById(R.id.et_type_add);
        add = findViewById(R.id.btn_save);

        mDatabaseHelper = new DatabaseHelper(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });

        gamePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int gamePicture = 0;
                if (gamePicture == 0) {
                    if (!checkCameraPermission()) {
                        requestCameraPermission();
                    } else {
                        pickFromGallery();
                    }
                } else if (gamePicture == 1) {
                    if (!checkStoragePermission()) {
                        requestStoragePermission();
                    } else {
                        pickFromGallery();
                    }
                }
            }
        });
    }

    private void insertData() {
        String valNama = name.getText().toString().trim();
        String valPrice = price.getText().toString().trim();
        String valType = type.getText().toString().trim();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.GAMES_IMAGE, ImageViewToByte(gamePicture));
        values.put(DatabaseHelper.GAMES_NAME, valNama);
        values.put(DatabaseHelper.GAMES_PRICE, valPrice);
        values.put(DatabaseHelper.GAMES_TYPE, valType);

        sqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        Long recinsert = sqLiteDatabase.insert(TABLE_NAME,null, values);

        if (valNama.isEmpty() || valPrice.isEmpty() || valType.isEmpty()) {
            Toast.makeText(TambahActivity.this, "Data Tidak Boleh Kosong !!!", Toast.LENGTH_SHORT).show();
        } else if (recinsert != null){
            Toast.makeText(TambahActivity.this, "Data Berhasil Ditambah", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(TambahActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private byte[] ImageViewToByte (ImageView gamePicture) {
        Bitmap bitmap = ((BitmapDrawable) gamePicture.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }

    private void pickFromGallery() {
        CropImage.activity().start(this);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        boolean result2 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        return result && result2;
    }

    private void requestCameraPermission() {
        requestPermissions(cameraPermission, CAMERA_REQUEST);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragePermission() {
        requestPermissions(storagePermission, STORAGE_REQUEST);
    }

}