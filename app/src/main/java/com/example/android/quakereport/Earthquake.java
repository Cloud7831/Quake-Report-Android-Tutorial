package com.example.android.quakereport;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Earthquake {

    private String location;
    private double mag;
    private Date time;

    public Earthquake(String loc, Date t, double m){
        location = loc;
        time = t;
        mag = m;
    }

    public String getLocation(){
        return location;
    }

    public double getMag(){
        return mag;
    }

    public Date getTime(){
        return time;
    }
}
