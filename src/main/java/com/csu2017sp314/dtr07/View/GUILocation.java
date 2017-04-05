package com.csu2017sp314.dtr07.View;

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
    private int nearest = -1;
    private int nearestDistance = 9999999;
    private int tableIndex;

    public GUILocation(String id, String name, double lat, double lon, String municipality, String region, String country, String continent, String airportUrl, String regionUrl, String countryUrl) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.municipality = municipality;
        this.region = region;
        this.country = country;
        this.continent = continent;
        this.airportUrl = airportUrl;
        this.regionUrl = regionUrl;
        this.countryUrl = countryUrl;
    }
}
