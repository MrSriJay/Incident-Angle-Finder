package com.example.incidentanglefinder;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import org.opencv.android.OpenCVLoader;


public class MainActivity extends AppCompatActivity {
    private Button camBtn;
    private Button galBtn;
    public static String TAG ="MainActivity";
    ImageView imgView;



    static {
        if(OpenCVLoader.initDebug()){
            Log.d(TAG,"OpenCV is Configured Successfull");

        }
        else{
            Log.d(TAG,"OpenCV is Not Configured properly");

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView=(ImageView) findViewById(R.id.imageView);

        camBtn = (Button) findViewById(R.id.camBtn);
        galBtn = (Button) findViewById(R.id.galleryBtn);

        camBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        galBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent myIntent = new Intent(MainActivity.this, Calculation.class);
              MainActivity.this.startActivity(myIntent);
            }
        });

    }

    public  void openGallery(View v){
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent,101);
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == RESULT_OK && data != null){
          Uri imageUri=data.getData();

          String path=getPath(imageUri);


        }
    }

    private String getPath(Uri imageUri) {

        if(imageUri == null){
            return null;
        }
        else {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(imageUri,projection,null,null,null);
            if(cursor!=null){
                int col_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(col_index);

            }

        }
        return imageUri.getPath();
    }
}