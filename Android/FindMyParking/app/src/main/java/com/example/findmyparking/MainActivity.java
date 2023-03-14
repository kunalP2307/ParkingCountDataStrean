package com.example.findmyparking;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    View viewLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(getApplicationContext(), DownloadDataSetActivity.class));

        File file = new File(MainActivity.this.getFilesDir(), "text");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File gpxfile = new File(file, "sample.txt");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append("Helllllllllllo");
            writer.flush();
            writer.close();
      //     Toast.makeText(MainActivity.this, "Saved your text", Toast.LENGTH_LONG).show();
        } catch (Exception e) { }

    }

    private void bindComponents(){
        viewLocation = findViewById(R.id.view_location);
        viewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ShowParkingDetails.class));
            }
        });
    }
}