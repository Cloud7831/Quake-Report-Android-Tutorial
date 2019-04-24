package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private String url;

    public EarthquakeLoader(Context context, String url){
        super(context);
        this.url = url;

    }

    @Override
    public List<Earthquake> loadInBackground() {
        if(url == null){
            return null;
        }

        List<Earthquake> result = QueryUtils.fetchEarthquakeData(url);
        return result;
    }

    protected void onStartLoading(){
        forceLoad();
    }
}
