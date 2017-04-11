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
    private ArrayList<String> selectedLocations;
    private ArrayList<Location> databaseLocationsReturned;
    private int numberFromDatabase = 0;
    private LocationFactory dataBaseSearch = new LocationFactory(); //This is for populating the second GUI window and getting locations once the user has selected what he wnats
    private boolean twoOpt;
    private boolean threeOpt;
    private boolean tick = false;
    private int totalImprovements;
    private String unit;
    private double[][] distTable;
    private boolean readingFromXML = true;
    private boolean kilometers;

    public int planTrip(String units, boolean useDB) throws FileNotFoundException {
        this.unit = units;
        LocationFactory lf = new LocationFactory();
        lf.setUnit(units);
        databaseLocationsReturned = lf.setSelectedAirports(selectedLocations, "id", useDB); //THis also searches the database lol
        //lf.readFile(filename);
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

    public int planUserTrip(String filename, boolean readingFromXML) throws FileNotFoundException {
        this.readingFromXML = readingFromXML;
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
        lf.setLocations(userLocations);//TODOdone read from database
        //List locations is not the ids of the selected airports
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

    public ArrayList<String> searchDatabase(ArrayList<String> where, boolean read) {
        databaseLocationsReturned = dataBaseSearch.readFromDB(where, read);
        numberFromDatabase = dataBaseSearch.getNumberReturnedFromDatabase();
        ArrayList<String> ret = new ArrayList<>(); //Very inefficient, see begining of fireQuery for additional options
        for(Location loc : databaseLocationsReturned) {
            ret.add(loc.getName());
        }
        return ret;
    }

    public ArrayList<Object> copyDBLocationsToView(int index) {
        ArrayList<Object> ret = new ArrayList<>();
        ret.add(databaseLocationsReturned.get(index).getId());
        ret.add(databaseLocationsReturned.get(index).getName());
        ret.add(databaseLocationsReturned.get(index).getLat());
        ret.add(databaseLocationsReturned.get(index).getLon());
        ret.add(databaseLocationsReturned.get(index).getMunicipality());
        ret.add(databaseLocationsReturned.get(index).getRegion());
        ret.add(databaseLocationsReturned.get(index).getCountry());
        ret.add(databaseLocationsReturned.get(index).getContinent());
        ret.add(databaseLocationsReturned.get(index).getAirportUrl());
        ret.add(databaseLocationsReturned.get(index).getRegionUrl());
        ret.add(databaseLocationsReturned.get(index).getCountryUrl());
        return ret;
    }

    //This method finds the locations with the correspoinging ids after the user has selected what he wants from the database
    /*public void getLocationsFromIds(ArrayList<String> ids) {
        ArrayList<Location> locs = dataBaseSearch.getLocations();
        for(String id : ids) {
            for(int i = 0; i < locs.size(); i++) {
                if(Integer.parseInt(id) == i) {
                    //TODOnotneeded need to make those buttons and update the itinerary, maybe use planUserTrip or something
                }
            }
        }
    }*/

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

    public ArrayList<String> getLocationNames() {
        ArrayList<String> ret = new ArrayList<>();
        for(Location l : locations) {
            ret.add(l.getName());
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

    public void setReadingFromXML(boolean readingFromXML) {
        this.readingFromXML = readingFromXML;
    }

    public int toggleListLocations(ArrayList<String> ids, boolean useDB) {
        if(!ids.isEmpty()) {
            /*if(readingFromXML) {
                for(String id : ids) {
                    int f = searchLocations(id, "name");
                    if(f > -1) {
                        userLocations.add(locations.get(f));
                    } else {
                        System.err.println("Error searching for " + id);
                    }

                }
            } else {
                databaseLocationsReturned = dataBaseSearch.setSelectedAirports(ids, "name"); //Instead of searching the existing lcoations, maybe we should just do another query
                userLocations = dataBaseSearch.getLocations(); //Thats is what i am implementing here
            }*/
            userLocations = (searchDatabaseLocationsReturnedForName(ids, useDB));

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

    private ArrayList<Location> searchDatabaseLocationsReturnedForName(ArrayList<String> names, boolean useDB) {
        ArrayList<Location> ret = new ArrayList<>();
        for(String name : names)
            for(Location loc : databaseLocationsReturned) {
                if(loc.getName().equalsIgnoreCase(name)) {
                    ret.add(loc);
                } else {
                    //Search database for names instead of using the ones from the last query
                    if(name.length() > 60) {
                        databaseLocationsReturned = dataBaseSearch.setSelectedAirports(names, "name", useDB);
                    } else {
                        databaseLocationsReturned = dataBaseSearch.setSelectedAirports(names, "id", useDB);
                    }
                    return dataBaseSearch.getLocations();
                }
            }
        return ret;
    }

    public boolean getTwoOpt() {
        return twoOpt;
    }

    public void setTwoOpt(boolean twoOpt) {
        this.twoOpt = twoOpt;
    }

    public boolean getThreeOpt() {
        return threeOpt;
    }

    public void setThreeOpt(boolean threeOpt) {
        this.threeOpt = threeOpt;
    }

    public boolean isKilometers() {
        return this.kilometers;
    }

    public void setKilometers(boolean kilometers) {
        this.kilometers = kilometers;
    }

    public int getDatabaseLocationsReturnedSize() {
        return databaseLocationsReturned.size();
    }

    public int getTotalImprovements() {
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

    public String getFirstMunicipality(int i) {
        return pairs.get(i).getOne().getMunicipality();
    }

    public String getSecondMunicipality(int i) {
        return pairs.get(i).getTwo().getMunicipality();
    }

    public String getFirstRegion(int i) {
        return pairs.get(i).getOne().getRegion();
    }

    public String getSecondRegion(int i) {
        return pairs.get(i).getTwo().getRegion();
    }

    public String getFirstCountry(int i){
        return pairs.get(i).getOne().getCountry();
    }

    public String getSecondCountry(int i){
        return pairs.get(i).getTwo().getCountry();
    }

    public String getFirstContinent(int i){
        return pairs.get(i).getOne().getContinent();
    }

    public String getSecondContinent(int i){
        return pairs.get(i).getTwo().getContinent();
    }

    public String getFirstAirportURL(int i){
        return pairs.get(i).getOne().getAirportUrl();
    }

    public String getSecondAirportURL(int i){
        return pairs.get(i).getTwo().getAirportUrl();
    }

    public String getFirstRegionUrl(int i){
        return pairs.get(i).getOne().getRegionUrl();
    }

    public String getSecondRegionUrl(int i){
        return pairs.get(i).getTwo().getRegionUrl();
    }

    public String getFirstCountryURL(int i){
        return pairs.get(i).getOne().getCountryUrl();
    }

    public String getSecondCountryURl(int i){
        return pairs.get(i).getTwo().getCountryUrl();
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

    public int getNumLocs() {
        return locations.size();
    }

    public int getNumDatabaseLocationsReturned() {
        return databaseLocationsReturned.size();
    }

    public int getNumUserLocs() {
        return userLocations.size();
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
        if(!kilometers) {
            return ret;
        } else {
            //double ret2 = (double) ret;
            //ret2 *= 1.60934;
            //ret2 = Math.round(ret2);
            return convert(ret);
        }
    }

    private int convert(int in) {
        double out = (double) in;
        out *= 1.60934;
        out = Math.round(out);
        return (int) out;
    }

    public boolean isWraparound(int i) {
        return pairs.get(i).isUseWraparound();
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
        //if(!kilometers) {
            return (int) userPairs.get(i).getDistance();
        //} else {
        //    return convert((int) userPairs.get(i).getDistance());
        //}
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

    public int getNumberReturnedFromDatabase() {
        return dataBaseSearch.getNumberReturnedFromDatabase();
    }

    public ArrayList<String> getSelectedLocations() {
        return selectedLocations;
    }

    public void setSelectedLocations(ArrayList<String> selectedLocations) {
        this.selectedLocations = selectedLocations;
    }

    public void printUserLoc() {
        for(int i = 0; i < userLocations.size(); i++) {
            System.out.println("ID at index " + i + " = " + userLocations.get(i).getId());
        }
    }
}