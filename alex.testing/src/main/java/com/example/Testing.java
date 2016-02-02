package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.JOptionPane;

public class Testing {


    public static void main(String args []){
        String json = other();
        JSONObject nj = new JSONObject(json);
        JSONObject ja = nj.getJSONArray("list").getJSONObject(0);
        JSONObject date = ja.getJSONObject("temp");
        double maxd = Double.parseDouble(date.get("max").toString());

        System.out.print(maxd);
    }

    public static String other(){

    // so that they can be closed in the finally block.
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;

    // Will contain the raw JSON response as a string.
    String forecastJsonStr = null;

    String format = "json";
    String units = "metric";
    String appid = "APP_ID";

    try {
        // Construct the URL for the OpenWeatherMap query
        // Possible parameters are avaiable at OWM's forecast API page, at
        // http://openweathermap.org/API#forecast

        URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?zip=64119&mode=json&units=metric&appid="+appid);


        // Create the request to OpenWeatherMap, and open the connection
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        // Read the input stream into a String
        InputStream inputStream = urlConnection.getInputStream();
        StringBuffer buffer = new StringBuffer();
        if (inputStream == null) {
            // Nothing to do.
            return null;
        }
        reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = reader.readLine()) != null) {
            // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
            // But it does make debugging a *lot* easier if you print out the completed
            // buffer for debugging.
            buffer.append(line + "\n");
        }

        if (buffer.length() == 0) {
            // Stream was empty.  No point in parsing.
            return null;
        }
        forecastJsonStr = buffer.toString();



    } catch (IOException e) {

        // If the code didn't successfully get the weather data, there's no point in attemping
        // to parse it.
        return null;
    } finally{
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
        if (reader != null) {
            try {
                reader.close();
            } catch (final IOException e) {

            }
        }
    }

        return forecastJsonStr;
    }

}
