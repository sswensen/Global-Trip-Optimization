package com.csu2017sp314.dtr07.Model;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by SummitDrift on 2/13/17.
 * @author Scott Swensen
 * Main class for Model Package
 */

public class Model {
    //private ArrayList<Location> locations;
    private ArrayList<Pair> pairs;
    private ArrayList<Pair> userPairs = new ArrayList<>();
    private ArrayList<Location> locations;
    private ArrayList<Location> userLocations = new ArrayList<>();
    private boolean twoOpt;

    public int planTrip(String filename) throws FileNotFoundException {
        LocationFactory lf = new LocationFactory();
        lf.readFile(filename);
        lf.thirdTry();
        locations = lf.getLocations();
        pairs = lf.getPairs();
        if(twoOpt)
            twoOpt();
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
            //System.out.println("Adding " + id + " to locations");
            return 1;
        } else {
            return -1;
        }
    }

    public int toggleListLocations(ArrayList<String> ids) {
        if(!ids.isEmpty()) {
            for(String id : ids) {
                userLocations.add(locations.get(searchLocations(id, "id")));
            }
            if(userLocations.size() > 0) {
                return 1;
            }
            for(Location loc : userLocations) {
                //System.out.println("Array: " + loc.getId());
            }
        } else {
            userLocations = new ArrayList<>(locations);
        }
        return 1;
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

    public void setTwoOpt(boolean twoOpt)
    {
        this.twoOpt = twoOpt;
    }

    public String getLegStartLocation() {
        return pairs.get(0).getOne().getName();
    }

    public String getLegFinishLocation() {
        return pairs.get(pairs.size()-2).getTwo().getName();
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

    public int getTripDistance(ArrayList<Pair> pairs)
    {
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
            System.out.println("ID at index " + i + " = "+ userLocations.get(i).getId());
        }
    }

    private ArrayList<Location> twoOptSwap(ArrayList<Location> route, ArrayList<Pair> newPairs, int i, int k)
    {
        /*
        1. take route[1] to route[i-1] and add them in order to new_route
        2. take route[i] to route[k] and add them in reverse order to new_route
        3. take route[k+1] to end and add them in order to new_route
        return new_route;
        example route: A ==> B ==> C ==> D ==> E ==> F ==> G ==> H ==> A
        example i = 4, example k = 7
        new_route:
        1. (A ==> B ==> C)
        2. A ==> B ==> C ==> (G ==> F ==> E ==> D)
        3. A ==> B ==> C ==> G ==> F ==> E ==> D (==> H ==> A)
        */
        ArrayList<Location> newLocations = new ArrayList<>();
        newLocations.addAll(route);
        //1
        for(int j=0; j<=i-1; j++)
        {
            newLocations.set(j, route.get(j));
            //Scott?
            //newPairs.add(new Pair(Integer.toString(j), locations.get(j), locations.get(j + 1), locations.get(j).distance(locations.get(j + 1))));
        }
        //2
        int dec = 0;
        for(int j=i; j<=k; j++)
        {
            newLocations.set(j, route.get(k-dec));
            dec++;
        }
        //3
        for(int j=k+1; j<route.size(); j++)
        {
            newLocations.set(j, route.get(j));
        }
        for(int j=0; j<newLocations.size()-1; j++)
        {
            newPairs.add(new Pair(Integer.toString(j), newLocations.get(j), newLocations.get(j + 1), newLocations.get(j).distance(newLocations.get(j + 1))));
        }
        newPairs.add(new Pair(Integer.toString(newLocations.size() - 1), newLocations.get(newLocations.size() - 1), newLocations.get(0), newLocations.get(newLocations.size() - 1).distance(newLocations.get(0))));
        return newLocations;
    }

    private void twoOpt()
    {
        /*
        repeat until no improvement is made {
            start_again:
            best_distance = calculateTotalDistance(existing_route)
            for (i = 0; i < number of nodes eligible to be swapped - 1; i++) {
                for (k = i + 1; k < number of nodes eligible to be swapped; k++) {
                    new_route = 2optSwap(existing_route, i, k)
                    new_distance = calculateTotalDistance(new_route)
                    if (new_distance < best_distance) {
                        existing_route = new_route
                        goto start_again
                    }
                }
            }
        }
        */
        int oldTripDistance;
        int newTripDistance;
        ArrayList<Location> newLocations;
        ArrayList<Pair> newPairs;
        ArrayList<Location> route = new ArrayList<>();
        for(Pair pair : pairs)
        {
            route.add(pair.getOne());
        }
        int totalImprovements = 0;
        int improvements = 1;
        while(improvements > 0)
        {
            improvements = 0;
            oldTripDistance = getTripDistance();
            for (int i = 0; i < route.size() - 1; i++)
            {
                for (int k = i + 1; k < route.size(); k++)
                {
                    newPairs = new ArrayList<>();
                    newLocations = twoOptSwap(route, newPairs, i, k);
                    newTripDistance = getTripDistance(newPairs);
                    if(newTripDistance<oldTripDistance)
                    {
                        route = newLocations;
                        pairs = newPairs;
                        improvements++;
                        totalImprovements++;
                    }
                }
            }
        }
        //System.out.println(totalImprovements);
    }
}
