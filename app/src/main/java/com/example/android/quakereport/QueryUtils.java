package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Earthquake> extractEarthquakes(String earthquakeJSON) {

        if(TextUtils.isEmpty(earthquakeJSON)){
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject baseJSONresponse = new JSONObject(earthquakeJSON);//new JSONObject(SAMPLE_JSON_RESPONSE);
            JSONArray features = baseJSONresponse.getJSONArray("features");

            for (int i = 0; i < features.length(); i++){
                // Loop through all the features and extract the data to create earthquake objects.
                if (features.get(i) instanceof JSONObject){
                    // Want to access the feature's details required to build an earthquake object.

                    JSONObject properties = ((JSONObject) features.get(i)).getJSONObject("properties");

                    Date date = new Date(properties.getLong("time"));
                    earthquakes.add(new Earthquake(properties.getString("place"), date, properties.getDouble("mag"), properties.getString("url")));
                }
                else{
                    Log.d("QueryUtils", "Feature element was not a JSONObject as expected");
                }
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return earthquakes;
    }

    public static List<Earthquake> fetchEarthquakeData(String requestURL){


//        try{
//            Thread.sleep(2000);
//        }
//        catch(InterruptedException e) {
//            e.printStackTrace();
//        }


        URL url = createURL(requestURL);

        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }
        catch (IOException e){
            Log.e("fetchEarthquakeData", "Problem making the HTTP request.", e);
        }

        List<Earthquake> earthquakes = extractEarthquakes(jsonResponse);

        return earthquakes;
    }

    private static JSONObject getUSGSData(String query){
        URL url = createURL(query);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            //TODO: Handle the IOException
        }


        if(TextUtils.isEmpty(jsonResponse)){
            // Returns if input string is null or empty.
            return null;
        }

        JSONObject baseJsonResponse = null;
        try {
            baseJsonResponse = new JSONObject(jsonResponse);
        } catch (JSONException e) {
            Log.e("Extract JSON", "Problem parsing the earthquake JSON results", e);
        }

        return baseJsonResponse;
    }

    private static URL createURL(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("Create URL", "Error with creating URL", e);
            return null;
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if(url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else {
                Log.e("makeHttpRequest", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("makeHttpRequest", "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse; // If something went wrong, empty string returned.
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

}