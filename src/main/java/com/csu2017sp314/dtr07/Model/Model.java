package com.csu2017sp314.dtr07.Model;

import java.io.FileNotFoundException;
import java.util.ArrayList;

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
    private boolean testThreeOpt;
    private boolean tick = false;
    private int totalImprovements;

    public int planTrip(String filename) throws FileNotFoundException {
        LocationFactory lf = new LocationFactory();
        lf.readFile(filename);
        lf.thirdTry();
        locations = lf.getLocations();
        pairs = lf.getPairs();
        if(twoOpt) {
            previousLocations = new ArrayList<>(userLocations);
            twoOpt();
        }
        if(threeOpt) {
            previousLocations = new ArrayList<>(userLocations);
            bothOpt();
        }
        if(testThreeOpt)
        {
            previousLocations = new ArrayList<>(userLocations);
            threeOpt();
        }

        userPairs.clear();
        userPairs = new ArrayList<>(pairs);
        return 1;
    }

    public int planUserTrip(String filename) throws FileNotFoundException {
        LocationFactory lf = new LocationFactory();
        if(!twoOpt && !tick) {
            userLocations = new ArrayList<>(previousLocations);
        }
        lf.setLocations(userLocations);
        lf.thirdTry();
        userLocations = lf.getLocations();
        pairs = lf.getPairs();
        if(twoOpt) {
            previousLocations = new ArrayList<>(userLocations);
            twoOpt();
        }
        if(threeOpt){
            previousLocations = new ArrayList<>(userLocations);
            threeOpt();
        }
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

    public void setTestThreeOpt(boolean testThreeOpt)
    {
        this.testThreeOpt = testThreeOpt;
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

    public int getTripDistance(ArrayList<Pair> pairs) {
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

    private void copySegmentInOrder(ArrayList<Location> source, ArrayList<Location> dest, int from, int through) {
        for(int i = from; i <= through; i++) {
            dest.set(i, source.get(i));
        }
    }

    private void copySegmentReverse(ArrayList<Location> source, ArrayList<Location> dest, int from, int through) {
        int dec = 0;
        for(int i = from; i <= through; i++) {
            dest.set(i, source.get(through - dec));
            dec++;
        }
    }

    private void generatePairs(ArrayList<Location> newLocations, ArrayList<Pair> newPairs) {
        for(int a = 0; a < newLocations.size() - 1; a++) {
            newPairs.add(new Pair(Integer.toString(a), newLocations.get(a), newLocations.get(a + 1), newLocations.get(a).distance(newLocations.get(a + 1))));
        }
        newPairs.add(new Pair(Integer.toString(newLocations.size() - 1), newLocations.get(newLocations.size() - 1), newLocations.get(0), newLocations.get(newLocations.size() - 1).distance(newLocations.get(0))));
    }

    private ArrayList<Location> generateRoute() {
        ArrayList<Location> route = new ArrayList<>();
        for(Pair pair : pairs) {
            route.add(pair.getOne());
        }
        return route;
    }

    private ArrayList<Location> twoOptSwap(ArrayList<Location> route, ArrayList<Pair> newPairs, int i, int j) {
        ArrayList<Location> newLocations = new ArrayList<>();
        newLocations.addAll(route);
        //1
        copySegmentInOrder(route, newLocations, 0, i-1);
        //2
        copySegmentReverse(route, newLocations, i, j);
        //3
        copySegmentInOrder(route, newLocations, j+1, route.size()-1);

        generatePairs(newLocations, newPairs);
        return newLocations;
    }

    protected int twoOpt() {
        int oldTripDistance, newTripDistance;
        ArrayList<Location> newLocations;
        ArrayList<Pair> newPairs;
        ArrayList<Location> route = generateRoute();
        int totalImprovements = 0;
        this.totalImprovements = 0;
        int improvements = 1;
        while(improvements > 0) {
            improvements = 0;
            oldTripDistance = getTripDistance();
            for(int i = 0; i < route.size() - 1; i++) {
                for(int j = i + 1; j < route.size(); j++) {
                    newPairs = new ArrayList<>();
                    newLocations = twoOptSwap(route, newPairs, i, j);
                    newTripDistance = getTripDistance(newPairs);
                    if(newTripDistance < oldTripDistance) {
                        route = newLocations;
                        this.pairs = newPairs;
                        improvements++;
                        totalImprovements++;
                    }
                }
            }
        }
        this.totalImprovements = totalImprovements;
        return totalImprovements;
    }

    private ArrayList<Location> threeOptSwap(ArrayList<Location> route, ArrayList<Pair> newPairs, int i, int j, int k) {
        ArrayList<Location> newLocations = new ArrayList<>();
        newLocations.addAll(route);
        //1
        copySegmentInOrder(route, newLocations, 0, i-1);
        //2
        copySegmentReverse(route, newLocations, i, j);
        //3
        copySegmentReverse(route, newLocations, j+1, k);
        //4
        copySegmentInOrder(route, newLocations, k+1, route.size()-1);

        generatePairs(newLocations, newPairs);
        return newLocations;
    }

    protected int threeOpt() {
        int oldTripDistance, newTripDistance;
        ArrayList<Location> newLocations;
        ArrayList<Pair> newPairs;
        ArrayList<Location> route = generateRoute();
        int totalImprovements = 0;
        this.totalImprovements = 0;
        int improvements = 1;
        while(improvements > 0) {
            improvements = 0;
            oldTripDistance = getTripDistance();
            for(int i = 0; i < route.size() - 2; i++) {
                for(int j = i + 1; j < route.size() - 1; j++) {
                    for(int k = j + 1; k < route.size(); k++) {
                        newPairs = new ArrayList<>();
                        newLocations = threeOptSwap(route, newPairs, i, j, k);
                        newTripDistance = getTripDistance(newPairs);
                        if(newTripDistance < oldTripDistance) {
                            route = newLocations;
                            pairs = newPairs;
                            improvements++;
                            totalImprovements++;
                        }
                    }
                }
            }
        }
        this.totalImprovements = totalImprovements;
        return totalImprovements;
    }

    private void bothOpt()
    {
        while (threeOpt() > 0 || twoOpt() > 0);
    }

}
