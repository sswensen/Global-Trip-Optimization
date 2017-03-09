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
    private ArrayList<Pair> userPairs;
    private ArrayList<Location> locations;
    private ArrayList<Location> userLocations = new ArrayList<>();

    public int planTrip(String filename) throws FileNotFoundException {
        LocationFactory lf = new LocationFactory();
        lf.readFile(filename);
        //lf.findNearest();
        lf.thirdTry();
        locations = lf.getLocations();
        pairs = lf.getPairs();
        return 1;
    }

    public ArrayList<Pair> getUserPairs() {
        LocationFactory lf = new LocationFactory();
        lf.readUserLocations(userLocations);
        lf.thirdTry();
        userPairs = lf.getPairs();
        return userPairs;
    }

    public void resetUserLoc() {
        userLocations.clear();
    }

    public int toggleLocations(String id) {
        int index = searchLocations(id, "id");
        if(index >= 0) {
            userLocations.add(locations.get(index));
            return 1;
        } else {
            return -1;
        }
    }

    public int searchLocations(String identifier, String field) {
        if(field.equalsIgnoreCase("name")) {
            for(int x = 0; x < locations.size(); x++) {
                if(locations.get(x).getName().equals(identifier)) {
                    return x;
                }
            }
        } else if(field.equalsIgnoreCase("id")) {
            for(int x = 0; x < locations.size(); x++) {
                if(locations.get(x).getId().equals(identifier)) {
                    return x;
                }
            }
        }
        return -1;
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

    public String getFirstId(int i) {
        return pairs.get(i).getOne().getId();
    }

    public String getSecondId(int i) {
        return pairs.get(i).getTwo().getId();
    }


    //--------------------------------- User Events ----------------------------------//

    public double getUserFirstLon(int i) {
        return userPairs.get(i).getOne().getLon();
    }

    public double getUserFirstLat(int i) {
        return userPairs.get(i).getOne().getLat();
    }

    public double getUserSecondLon(int i) {
        return userPairs.get(i).getTwo().getLon();
    }

    public double getUserSecondLat(int i) {
        return userPairs.get(i).getTwo().getLat();
    }

    public int getUserPairDistance(int i) {
        return (int) userPairs.get(i).getDistance();
    }

    public String getUserPairId(int i) {
        return userPairs.get(i).getId();
    }

    public int getUserNumPairs() {
        return userPairs.size();
    }

    public String getUserFirstName(int i) {
        return userPairs.get(i).getOne().getName();
    }

    public String getUserSecondName(int i) {
        return userPairs.get(i).getTwo().getName();
    }

    public int getUserTripDistance() {
        int ret = 0;
        for(Pair p : userPairs) {
            ret += p.getDistance();
        }
        return ret;
    }

    public String getUserFirstId(int i) {
        return userPairs.get(i).getOne().getId();
    }

    public String getUserSecondId(int i) {
        return userPairs.get(i).getTwo().getId();
    }
}
