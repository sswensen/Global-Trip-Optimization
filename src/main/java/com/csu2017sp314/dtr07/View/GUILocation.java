package com.csu2017sp314.dtr07.View;

import java.util.ArrayList;

/**
 * Created by SummitDrift on 4/5/17.
 */

public class GUILocation {
    private String id;
    private String name;
    private double lat;
    private double lon;
    private String municipality;
    private String region;
    private String country;
    private String continent;
    private String airportUrl;
    private String regionUrl;
    private String countryUrl;

    GUILocation(ArrayList<Object> in) {
        this.id = (String)in.get(0);
        this.name = (String)in.get(1);
        this.lat = (double)in.get(2);
        this.lon = (double)in.get(3);
        this.municipality = (String)in.get(4);
        this.region = (String)in.get(5);
        this.country = (String)in.get(6);
        this.continent = (String)in.get(7);
        this.airportUrl = (String)in.get(8);
        this.regionUrl = (String)in.get(9);
        this.countryUrl = (String)in.get(10);
    }
}
