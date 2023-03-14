package com.example.findmyparking;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.util.ArrayList;

public class DownloadDSAsyncTask extends AsyncTask<Void, Void, String> {

    private DownloadDataSetActivity activity;
    private AsyncDDsCallBack asyncCallBack;
    private ProgressBar progressBar;

    DownloadDSAsyncTask setInstance(Context context, ProgressBar progressBar){
        this.activity = (DownloadDataSetActivity) context;
        asyncCallBack = (AsyncDDsCallBack) context;
        this.progressBar = progressBar;
        return this;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(Void... voids) {
        ArrayList<Data> dataArrayList = new ArrayList<>();

        JSONObject jsonObject = null;
        String result = "";
        HttpURLConnection urlConnection = null;
        String string_url = "https://script.google.com/macros/s/AKfycbzCqyqIr9JFiwUzfZsOwMpTCKqVXoWNPAn6eUQXhpxtrgegS4UyXcLKgwvz6l-sIAtQ/exec?action=get_all&location=xyz_mall";
        try {
            URL url = new URL(string_url);
            urlConnection = (HttpURLConnection) url.openConnection();

            int statusCode = urlConnection.getResponseCode();
            if(statusCode != 200){
                throw new IOException("Invalid Response from Server Status Code : "+statusCode);
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String line;
            while((line = bufferedReader.readLine()) != null){
                result += line;
            }

            jsonObject = new JSONObject(result);
            Log.d("", "doInBackground: "+result);
            Log.d("", "doInBackground json Object: "+jsonObject);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            });
        }

        String csvString = "date,time,count\n";
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                String date = object.getString("date");
                String time = object.getString("time");
                String count = object.getString("count");
                csvString += date+ ","+time+ ","+count+"\n";
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("", "doInBackground: "+csvString);
        return csvString;
    }

    private void downloadDataSet(String csvString){
        File file = new File(activity.getFilesDir(), "text");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File gpxfile = new File(file, "dataset.csv");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(csvString);
            writer.flush();
            writer.close();
            Toast.makeText(activity, "File Stored Successfully!", Toast.LENGTH_LONG).show();
        } catch (Exception e) { }

    }


    @Override
    protected void onPostExecute(String result){
        super.onPostExecute(result);
        downloadDataSet(result);
        Log.d("TAG", "onPostExecute: "+result);
    }
}
