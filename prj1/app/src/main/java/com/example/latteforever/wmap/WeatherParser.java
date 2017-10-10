package com.example.latteforever.wmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by latteforever on 10/5/17.
 */

public class WeatherParser {

    private String location;
    private String temp;
    private String description;
    private String error;

    public String getLocation() {
        return location;
    }
    public String getTemp() {
        return temp;
    }
    public String getDescription() {
        return description;
    }
    public String getError() {return error;}


    public void parseData(JSONObject obj) throws JSONException {

        JSONObject coord = obj.optJSONObject("coord");
        location = obj.optString("name");               // Location
        JSONObject tempO = obj.optJSONObject("main");    // Templature

        int aValue = tempO.optInt("temp");
        int aaValue = aValue * 9 / 5 - 459;
        temp = new Integer(aaValue).toString() + " F";

        JSONArray jArray = obj.optJSONArray("weather");  // Description
        JSONObject object = jArray.getJSONObject(0);
        description = object.optString("description");

        try {
        } catch(Exception e) {
            error = "Error occured: " + e.toString();
        }
    }
}
