package com.example.findmyparking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;

public class DownloadDataSetActivity extends AppCompatActivity  implements AsyncDDsCallBack {

    TextView textViewStartDate, textViewEndDate;
    Button buttonDownload;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_data_set);
        bindComponents();
    }

    private void bindComponents(){
        this.textViewStartDate = findViewById(R.id.text_view_start);
        this.textViewEndDate = findViewById(R.id.text_view_end);
        this.buttonDownload = findViewById(R.id.button_download);
        this.progressBar = findViewById(R.id.progress_bar_down_ds);
        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownloadDSAsyncTask().setInstance(DownloadDataSetActivity.this, progressBar)
                        .execute();
            }
        });

        textViewStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        DownloadDataSetActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                textViewStartDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });

        textViewEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        DownloadDataSetActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                textViewEndDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        },
                        year, month, day);
                datePickerDialog.show();
            }
        });
    }

    @Override
    public void setResult(String csvString) {
        Log.d("", "setResult: "+csvString);
        Toast.makeText(this, "callback", Toast.LENGTH_SHORT).show();
    }
}