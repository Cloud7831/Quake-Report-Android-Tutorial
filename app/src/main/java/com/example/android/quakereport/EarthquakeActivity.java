/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
        ArrayList<Earthquake> earthquakes = new ArrayList<>();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy MM dd");
        try{
            earthquakes.add(new Earthquake("San Francisco", fmt.parse("2016 02 02"), (float) 7.2));
            earthquakes.add(new Earthquake("London", fmt.parse("2015 07 20"), (float) 6.1));
            earthquakes.add(new Earthquake("Tokyo", fmt.parse("2014 10 20"), (float) 3.9));
            earthquakes.add(new Earthquake("Mexico City", fmt.parse("2014 05 03"), (float) 5.4));
            earthquakes.add(new Earthquake("Moscow", fmt.parse("2013 01 31"), (float) 7.2));
            earthquakes.add(new Earthquake("Rio", fmt.parse("2012 08 19"), (float) 7.2));
            earthquakes.add(new Earthquake("Paris", fmt.parse("2011 10 30"), (float) 7.2));
        }
        catch(ParseException e){

        }

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        EarthquakeAdapter adapter = new EarthquakeAdapter(
                this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
    }
}
