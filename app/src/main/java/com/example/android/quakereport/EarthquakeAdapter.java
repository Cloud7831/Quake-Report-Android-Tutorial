package com.example.android.quakereport;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    SimpleDateFormat dateFmt = new SimpleDateFormat("MMM dd, yyyy");
    SimpleDateFormat timeFmt = new SimpleDateFormat("hh:mm a");
    DecimalFormat magFmt = new DecimalFormat("0.0");

    public EarthquakeAdapter(Activity context, ArrayList<Earthquake> earthquakes){
        super(context, 0, earthquakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.earthquake_list_item, parent, false);
        }

        final Earthquake currentEarthquake = getItem(position);


        // Set the location data of the Earthquake -----------------------------------------------------------------------

        // We want to show the location as two different strings.

        String baseLocation = currentEarthquake.getLocation();
        String locationPrefix;
        String primaryLocation;

        String scoutingWord = " of "; // The character sequence we're using to determine if something reads "##km NW of ..."
        if(baseLocation.contains(scoutingWord)){
            // String goes something like "##km NW of "
            // Split the string after the " of ".

            // Find where " of " occurs
            int index = baseLocation.indexOf(scoutingWord);
            int offset = scoutingWord.length();
            locationPrefix = baseLocation.substring(0, index + offset);
            primaryLocation = baseLocation.substring(index + offset, baseLocation.length()); // the rest of the string.
        }
        else{
            // String does not contain something like "33km NW of ..."
            locationPrefix = getContext().getString(R.string.location_prefix); // "Near the"
            primaryLocation = baseLocation;
        }


        TextView locationTextView = (TextView) listItemView.findViewById(R.id.earthquake_location_primary);
        locationTextView.setText(primaryLocation);

        TextView locationPrefixTextView = (TextView) listItemView.findViewById(R.id.earthquake_location_prefix);
        locationPrefixTextView.setText(locationPrefix);



        // Set the Magnitude of the Earthquake and colour the circle ------------------------------------------------------

        TextView magView = (TextView) listItemView.findViewById(R.id.earthquake_magnitude);
        magView.setText(String.valueOf(magFmt.format(currentEarthquake.getMag())));

        GradientDrawable magnitudeCircle = (GradientDrawable) magView.getBackground();
        int magnitudeColour = getMagnitudeColour(currentEarthquake.getMag());
        magnitudeCircle.setColor(magnitudeColour);

        // Set the date and time of the Earthquake ------------------------------------------------------------------------

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.earthquake_date);
        dateTextView.setText(dateFmt.format(currentEarthquake.getTime()));

        TextView timeTextView = (TextView) listItemView.findViewById(R.id.earthquake_time);
        timeTextView.setText(timeFmt.format(currentEarthquake.getTime()));

        return listItemView;
    }

    private int getMagnitudeColour(double mag){
        int colour;
        int intMag = (int) Math.floor(mag);

        switch (intMag) {
            case 0:
            case 1:
                colour = R.color.magnitude1;
                break;
            case 2:
                colour = R.color.magnitude2;
                break;
            case 3:
                colour = R.color.magnitude3;
                break;
            case 4:
                colour = R.color.magnitude4;
                break;
            case 5:
                colour = R.color.magnitude5;
                break;
            case 6:
                colour = R.color.magnitude6;
                break;
            case 7:
                colour = R.color.magnitude7;
                break;
            case 8:
                colour = R.color.magnitude8;
                break;
            case 9:
                colour = R.color.magnitude9;
                break;
            default:
                colour = R.color.magnitude10plus;
                break;
        }

        return ContextCompat.getColor(getContext(), colour);
    }
}
