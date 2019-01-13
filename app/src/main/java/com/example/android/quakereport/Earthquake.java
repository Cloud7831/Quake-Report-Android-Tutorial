package com.example.android.quakereport;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Earthquake {

    private String location;
    private float mag;
    private Date time;

    public Earthquake(String loc, Date t, float m){
        location = loc;
        time = t;
        mag = m;
    }

    public String getLocation(){
        return location;
    }

    public float getMag(){
        return mag;
    }

    public Date getTime(){
        return time;
    }
}
