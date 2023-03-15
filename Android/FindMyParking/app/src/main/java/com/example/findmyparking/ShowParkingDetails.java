package com.example.findmyparking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class ShowParkingDetails extends AppCompatActivity implements AsyncCallBack{

    PieChart pieChart;
    ProgressBar progressBar;
    TextView textViewLoading;
    TextView textViewCountOutOff;
    ImageView imageViewRefresh;
    Button buttonGetDataSet;
    TextView textViewLastUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_parking_details);

        this.progressBar = findViewById(R.id.progressBar);
        pieChart = findViewById(R.id.piechart);
        bindComponents();
        new GetAsyncTask().setInstance(ShowParkingDetails.this, progressBar)
                .execute();

    }

    private void bindComponents(){
        textViewLoading = findViewById(R.id.text_view_loading);
        textViewCountOutOff = findViewById(R.id.text_view_count_out_off);
        imageViewRefresh = findViewById(R.id.image_view_refresh);
        textViewLastUpdated = findViewById(R.id.text_view_last_updated);
        buttonGetDataSet = findViewById(R.id.button_goto_down_act);
        buttonGetDataSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), DownloadDataSetActivity.class));
            }
        });
        imageViewRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });
    }
    private void setData(int occupied){

        pieChart.addPieSlice(
                new PieModel(
                        "Occupied Parking",
                        occupied * 10,
                        Color.parseColor("#FFFF0000")));
        pieChart.addPieSlice(
                new PieModel(
                        "Available Parking",
                        (Data.XYZ_MALL_MAX_COUNT - occupied) * 10,
                        Color.parseColor("#FF00FF0A")));
        pieChart.startAnimation();
    }

    private void makeVisible(){
        TextView textView6 = findViewById(R.id.textView6);
        textView6.setVisibility(View.VISIBLE);
        TextView textView8 = findViewById(R.id.textView8);
        textView8.setVisibility(View.VISIBLE);
        View view5 = findViewById(R.id.view5);
        view5.setVisibility(View.VISIBLE);
        View view4 = findViewById(R.id.view4);
        view4.setVisibility(View.VISIBLE);
        textViewCountOutOff.setVisibility(View.VISIBLE);
        pieChart.setVisibility(View.VISIBLE);
        textViewLastUpdated.setVisibility(View.VISIBLE);
        imageViewRefresh.setVisibility(View.VISIBLE);
        buttonGetDataSet.setVisibility(View.VISIBLE);
    }

    private void createDataSet() {

    }

    private void setData(Data data){
        textViewCountOutOff.setText(data.getCount()+"/"+Data.XYZ_MALL_MAX_COUNT);
        textViewLastUpdated.setText("No change since "+data.getDate()+" "+data.getTime());
    }

    @Override
    public void setResult(Data result) {
        Log.d("", "setResult: "+result);
        setData(Integer.parseInt(result.getCount()));
        textViewLoading.setVisibility(View.GONE);
        makeVisible();
        setData(result);
    }
}