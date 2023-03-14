package com.example.findmyparking;

public class Data {

    public final static int XYZ_MALL_MAX_COUNT = 10;

    private String date,time,count,status;

    public Data(){}

    public Data(String date, String time, String count) {
        this.date = date;
        this.time = time;
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Data{" +
                "date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", count='" + count + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
