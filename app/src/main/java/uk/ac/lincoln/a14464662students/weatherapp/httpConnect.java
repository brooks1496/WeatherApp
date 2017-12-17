package uk.ac.lincoln.a14464662students.weatherapp;

/**
 * Created by brook on 06/12/2017.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class httpConnect extends AsyncTask<String,Void,String> {





    @Override
    protected String doInBackground(String... urls) {
        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(urls[0]);

            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(in);

            int data = reader.read();

            while (data != -1){
                char current = (char) data;

                result += current;

                data = reader.read();
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {
            JSONObject jsonObject = new JSONObject(result);

            JSONObject weatherData = new JSONObject(jsonObject.getString("main"));

            Double temperature = Double.parseDouble(weatherData.getString("temp"));

            int tempInt = (int) (temperature - 273.15);

            String placeName = jsonObject.getString("name");

            MainActivity.temperature.setText(String.valueOf(tempInt));
            MainActivity.placeTextView.setText(placeName);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

