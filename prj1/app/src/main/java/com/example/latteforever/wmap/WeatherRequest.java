package com.example.latteforever.wmap;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by latteforever on 10/5/17.
 */

public class WeatherRequest {

    private WeatherCallback wCallback;
    private WeatherParser parser;

    public WeatherRequest(WeatherCallback wListener) {
        this.wCallback = wListener;
    }

    public void refresh(String[] geo) {

        new AsyncTask<String, Void, WeatherParser >() {

            @Override
            protected WeatherParser doInBackground(String[] geo) {

                final String APPID = "5f3ce42a59b36b00128114a9d330fe52";

                // api.openweathermap.org/data/2.5/weather?q={city name}
                // api.openweathermap.org/data/2.5/weather?q={city name},{country code}
                // Get by City name
                String endpoint = "http://api.openweathermap.org/data/2.5/weather?" + "q=" +  geo[0]  + "&APPID=" + APPID;

                //String endpoint = "http://api.openweathermap.org/data/2.5/weather?" + "lat=" + geo[0] + "&lon=" + geo[1]  + "&APPID=" + APPID;

                try {
                    parser = new WeatherParser();

                    URL url = new URL(endpoint);
                    URLConnection connection = url.openConnection();
                    connection.setUseCaches(false);

                    InputStream iStream = connection.getInputStream();
                    BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));
                    StringBuffer result = new StringBuffer();

                    String in;
                    while ((in = bReader.readLine()) != null) {
                        result.append(in);
                    }

                    bReader.close();

                    JSONObject data = new JSONObject(result.toString());
                    parser.parseData(data);

                    return parser;

                } catch (Exception e) {
                    Log.d("Exception thrown : ", e.toString());
                }
                return null;
            }

            @Override
            protected void onPostExecute(WeatherParser parser) {
                super.onPostExecute(parser);

                if (parser != null)
                    wCallback.resolved(parser);
                else
                    wCallback.rejected(parser);
            }

        }.execute(geo);
    }
}
