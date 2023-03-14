package com.example.findmyparking;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetAsyncTask extends AsyncTask<Void, Void, Data> {

    private ShowParkingDetails activity;
    private AsyncCallBack asyncCallBack;
    private ProgressBar progressBar;

    GetAsyncTask setInstance(Context context, ProgressBar progressBar){
        this.activity = (ShowParkingDetails) context;
        asyncCallBack = (AsyncCallBack) context;
        this.progressBar = progressBar;
        return this;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Data doInBackground(Void... voids) {
        Data data = null;
        JSONObject jsonObject = null;
        String result = "";
        HttpURLConnection urlConnection = null;
        String string_url = "https://script.google.com/macros/s/AKfycbzCqyqIr9JFiwUzfZsOwMpTCKqVXoWNPAn6eUQXhpxtrgegS4UyXcLKgwvz6l-sIAtQ/exec?action=get_current&location=xyz_mall";
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

        try {
            String date = jsonObject.getString("date");
            String time = jsonObject.getString("time");
            String count = jsonObject.getString("count");
            String status = jsonObject.getString("status");

            data = new Data(date, time, count);
            data.setStatus(status);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return data;
    }

    @Override
    protected void onPostExecute(Data result){
        super.onPostExecute(result);
        Log.d("TAG", "onPostExecute: "+result);
        asyncCallBack.setResult(result);
    }
}
