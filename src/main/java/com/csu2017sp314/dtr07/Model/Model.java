package com.csu2017sp314.dtr07.Model;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by SummitDrift on 2/13/17.
 *
 * @author Scott Swensen
 *         Main class for Model Package
 */

public class Model {
    private ArrayList<Pair> pairs;
    private ArrayList<Pair> userPairs = new ArrayList<>();
    private ArrayList<Location> locations;
    private ArrayList<Location> userLocations = new ArrayList<>();
    private ArrayList<Location> previousLocations = new ArrayList<>();
    private boolean twoOpt;
    private boolean threeOpt;
    private boolean tick = false;
    private int totalImprovements;

    public int planTrip(String filename) throws FileNotFoundException {
        LocationFactory lf = new LocationFactory();
        lf.readFile(filename);
        if(twoOpt) {
            lf.setTwoOpt(true);
        }
        if(threeOpt) {
            lf.setThreeOpt(true);
        }
        lf.thirdTry();
        locations = lf.getLocations();
        pairs = lf.getPairs();
        userPairs.clear();
        userPairs = new ArrayList<>(pairs);
        return 1;
    }

    public int planUserTrip(String filename) throws FileNotFoundException {
        LocationFactory lf = new LocationFactory();
        if(twoOpt) {
            lf.setTwoOpt(true);
        }
        if(!twoOpt && !tick) {
            userLocations = new ArrayList<>(previousLocations);
        }
        if(threeOpt) {
            lf.setThreeOpt(true);
        }
        if(!threeOpt && !tick) {
            userLocations = new ArrayList<>(previousLocations);
        }
        lf.setLocations(userLocations);
        lf.thirdTry();
        userLocations = lf.getLocations();
        pairs = lf.getPairs();
        /*
        if(twoOpt) {
            previousLocations = new ArrayList<>(userLocations);
            twoOpt();
        }
        if(threeOpt){
            previousLocations = new ArrayList<>(userLocations);
            threeOpt();
        }
        */
        userPairs.clear();
        userPairs = new ArrayList<>(pairs);
        return 1;
    }

    public ArrayList<Pair> getUserPairs() {
        /*LocationFactory lf = new LocationFactory();
        lf.readUserLocations(userLocations);
        lf.thirdTry();
        userPairs = lf.getPairs();
        return userPairs;*/
        return userPairs;
    }

    public ArrayList<String> getLocationIds() {
        ArrayList<String> ret = new ArrayList<>();
        for(Location l : locations) {
            ret.add(l.getId());
        }
        return ret;
    }

    public void resetUserLoc() {
        userLocations.clear();
    }

    public int toggleLocations(String id) {
        int index = searchLocations(id, "id");
        if(index >= 0) {
            userLocations.add(locations.get(index));
            //System.out.println("Adding " + id + " to locations");
            return 1;
        } else {
            return -1;
        }
    }

    public int toggleListLocations(ArrayList<String> ids) {
        if(!ids.isEmpty()) {
            for(String id : ids) {
                int f = searchLocations(id, "id");
                if(f > -1) {
                    userLocations.add(locations.get(f));
                } else {
                    System.err.println("Error searching for " + id);
                }
            }
        } else {
            userLocations = new ArrayList<>(locations);
        }
        if(userLocations.size() > 0) {
            tick = true;
            return 1;
        }
        return -1;
    }

    private int searchLocations(String identifier, String field) {
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

    public void setTwoOpt(boolean twoOpt) {
        this.twoOpt = twoOpt;
    }

    public boolean getTwoOpt() {
        return twoOpt;
    }

    public void setThreeOpt(boolean threeOpt) {
        this.threeOpt = threeOpt;
    }

    public boolean getThreeOpt() {
        return threeOpt;
    }

    public int getTotalImprovements()
    {
        return totalImprovements;
    }

    public String getLegStartLocation() {
        return pairs.get(0).getOne().getName();
    }

    public String getLegFinishLocation() {
        return pairs.get(pairs.size() - 2).getTwo().getName();
    }

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

    public void printUserLoc() {
        for(int i = 0; i < userLocations.size(); i++) {
            System.out.println("ID at index " + i + " = " + userLocations.get(i).getId());
        }
    }
}
