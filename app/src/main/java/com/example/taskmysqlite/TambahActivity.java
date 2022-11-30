package com.example.taskmysqlite;

import static com.example.taskmysqlite.DatabaseHelper.TABLE_NAME;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
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

        cameraPermission = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_REQUEST:{
                if (grantResults.length>0){
                    boolean camera_accept = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storage_accept = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (camera_accept && storage_accept){
                        pickFromGallery();
                    }else{
                        Toast.makeText(this, "Enable camera and storage permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST:{
                if (grantResults.length>0){
                    boolean storage_accept = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storage_accept){
                        pickFromGallery();
                    }else{
                        Toast.makeText(this, "Please enable storage permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                Picasso.with(this).load(resultUri).into(gamePicture);
            }
        }
    }

}