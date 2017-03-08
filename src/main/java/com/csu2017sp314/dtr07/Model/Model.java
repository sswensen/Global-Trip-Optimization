package com.csu2017sp314.dtr07.Model;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by SummitDrift on 2/13/17.
 * Main class for Model Package
 */
public class Model {
    //private ArrayList<Location> locations;
    private ArrayList<Pair> pairs;
    private ArrayList<Location> locations;

    public int planTrip(String filename) throws FileNotFoundException {
        LocationFactory lf = new LocationFactory();
        lf.readFile(filename);
        //lf.findNearest();
        lf.thirdTry();
        locations = lf.getLocations();
        pairs = lf.getPairs();
        return 1;
    }

    public String getLegStartLocation() {
        return pairs.get(0).getOne().getName();
    }

    public String getLegFinishLocation() {
        return pairs.get(pairs.size()-2).getTwo().getName();
    }

    /*public int getLegDistance(Object o) {
        return -1;
    }

    public int getLocationID(int index) {
        return locations.get(index).getId();
    }

    public double getLocationLattitude(int index) {
        return locations.get(index).getLat();
    }

    public double getLocationLongitude(int index) {
        return locations.get(index).getLon();
    }*/

    public double getFirstLon(final int i) {
        return pairs.get(i).getOne().getLon();
    }

    public double getFirstLat(final int i) {
        return pairs.get(i).getOne().getLat();
    }

    public double getSecondLon(final int i) {
        return pairs.get(i).getTwo().getLon();
    }

    public double getSecondLat(final int i) {
        return pairs.get(i).getTwo().getLat();
    }

    public int getPairDistance(final int i) {
        return (int) pairs.get(i).getDistance();
    }

    public String getPairId(final int i) {
        return pairs.get(i).getId();
    }

    public int getNumPairs() {
        return pairs.size();
    }

    public String getFirstName(final int i) {
        return pairs.get(i).getOne().getName();
    }

    public String getSecondName(final int i) {
        return pairs.get(i).getTwo().getName();
    }

    public int getTripDistance() {
        int ret = 0;
        for(Pair p : pairs) {
            ret += p.getDistance();
        }
        return ret;
    }

    public String getFirstId(final int i) {
        return pairs.get(i).getOne().getId();
    }

    public String getSecondId(final int i) {
        return pairs.get(i).getTwo().getId();
    }
}
