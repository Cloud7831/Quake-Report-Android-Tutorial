package com.example.android.quakereport;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    SimpleDateFormat fmt = new SimpleDateFormat("MMM dd, yyyy");

    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes){
        super(context, 0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
        }

        Earthquake currentEarthquake = getItem(position);

        TextView locationTextView = (TextView) listItemView.findViewById(R.id.earthquake_location);
        locationTextView.setText(currentEarthquake.getLocation());

        TextView magTextView = (TextView) listItemView.findViewById(R.id.earthquake_magnitude);
        magTextView.setText(String.valueOf(currentEarthquake.getMag()));

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.earthquake_time);
        timeTextView.setText(fmt.format(currentEarthquake.getTime()));

        return listItemView;
    }
}
