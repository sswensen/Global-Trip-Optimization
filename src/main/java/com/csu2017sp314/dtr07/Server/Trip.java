package com.csu2017sp314.dtr07.Server;

import com.csu2017sp314.dtr07.Model.Location;

import java.util.ArrayList;

/**
 * Created by SummitDrift on 4/28/17.
 */

public class Trip {
    private String name = "";
    private double totalDistance = 0.0;
    private ArrayList<String> selectedIds;

    public Trip(String name, double totalDistance, ArrayList<String> locations) {
        this.name = name;
        this.totalDistance = totalDistance;
        this.selectedIds = locations;
    }

    public String getName() {
        return name;
    }

    public String toString(){
        return "Trip: " +"name " + name + " totalDistance " + totalDistance + " Locations " + selectedIds.toString();

    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }
  
    public ArrayList<String> getSelectedIds(){
        return selectedIds;

    }
}
