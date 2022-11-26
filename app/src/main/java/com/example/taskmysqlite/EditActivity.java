package com.example.taskmysqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;

public class EditActivity extends AppCompatActivity {

    DatabaseHelper mDatabaseHelper;
    ImageView gamePicture;
    EditText name, price, type;
    Button edit;
    int id = 0;

    public static final int CAMERA_REQUEST = 100;
    public static final int STORAGE_REQUEST = 101;
    String[] cameraPermission;
    String[] storagePermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        gamePicture = findViewById(R.id.gambar_edit);
        name = findViewById(R.id.et_name_edit);
        price = findViewById(R.id.et_price_edit);
        type = findViewById(R.id.et_type_edit);
        edit = findViewById(R.id.btn_update);

        mDatabaseHelper = new DatabaseHelper(this);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

//    private void editData() {
//        if (getIntent().getBundleExtra("userdata")!=null){
//            Bundle bundle=getIntent().getBundleExtra("userdata");
//            id = bundle.getInt(GA);
//            // Untuk Gambar
//            byte[]bytes = bundle.getByteArray("avatar");
//            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
//            avatar.setImageBitmap(bitmap);
//            // Untuk mengatur nama
//            name.setText(bundle.getString("name"));
//            //visible edit button and hide submit button
//            submit.setVisibility(View.GONE);
//            edit.setVisibility(View.VISIBLE);
//        }
//    }

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