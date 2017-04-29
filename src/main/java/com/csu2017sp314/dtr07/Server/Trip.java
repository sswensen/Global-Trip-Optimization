package com.csu2017sp314.dtr07.Server;

import com.csu2017sp314.dtr07.Model.Location;

import java.util.ArrayList;

/**
 * Created by SummitDrift on 4/28/17.
 */

public class Trip {
    private String name = "";
    private double distance = 0.0;
    private ArrayList<String> locations;

    public Trip(String name, double distance,ArrayList<String> locations) {
        this.name = name;
        this.distance = distance;
        this.locations = locations;
    }

    public String toString(){
        return "Trip: " +"name " + name + " distance " + distance + " Locations " + locations.toString();

    }
}
