package com.example.latteforever.wmap;

/**
 * Created by latteforever on 10/5/17.
 */


public interface WeatherCallback {
    public void resolved(WeatherParser data);
    public void rejected(WeatherParser data);
}
