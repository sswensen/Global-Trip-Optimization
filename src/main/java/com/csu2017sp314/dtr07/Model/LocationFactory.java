package com.csu2017sp314.dtr07.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by SummitDrift on 2/13/17.
 *
 * @author Scott Swensen
 *         Factory for creation of locations and pairs
 */

class LocationFactory {
    private ArrayList<Location> locations = new ArrayList<>();
    private ArrayList<Pair> pairs = new ArrayList<>();
    private ArrayList<Pair> bestPairs = new ArrayList<>();
    private ArrayList<String> selectedAirports = new ArrayList<>();

    boolean readFile(String in) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(in));
        int id = -1;
        int name = -1;
        int latitude = -1;
        int longitude = -1;
        if(scan.hasNext()) {
            String[] line = scan.nextLine().replaceAll("\"", "").split(",");
            for(int x = 0; x < line.length; x++) {
                String temp = line[x];
                if(temp.equalsIgnoreCase("id")) {
                    id = x;
                }
                if(temp.equalsIgnoreCase("name")) {
                    name = x;
                }
                if(temp.equalsIgnoreCase("latitude")) {
                    latitude = x;
                }
                if(temp.equalsIgnoreCase("longitude")) {
                    longitude = x;
                }
            }
        }
        while(scan.hasNext()) {
            String[] line = scan.nextLine().split(",");
            if(selectedAirports.contains(line[id])) {
                Location temp = new Location(line[id], line[name], line[latitude].replaceAll("\\s+", ""), line[longitude].replaceAll("\\s+", ""));
                locations.add(temp);
            }
        }
        scan.close();
        //TODO Remove this
        locations.add(new Location("NZCH", "Christchurch International Airport", "-43.48939896", "172.5319977"));
        locations.add(new Location("00A", "Total Rf Heliport", "40.07080078", "-74.93360138"));
        locations.add(new Location("00IL", "Hammer Airport", "41.97840118", "-89.56040192"));
        locations.add(new Location("00LA", "Shell Chemical East Site Heliport", "30.191944", "-90.980833"));
        locations.add(new Location("00NK", "Cliche Cove Seaplane Base", "44.8118612", "-73.3698057"));
        locations.add(new Location("01CO", "St Vincent General Hospital Heliport", "39.24530029", "-106.2460022"));
        locations.add(new Location("02GA", "Doug Bolton Field", "34.20259857", "-83.42900085"));
        locations.add(new Location("CN24", "Flying R Airport", "38.28300095", "-121.2549973"));
        return locations.size() > 0;
    }

    boolean readUserLocations(ArrayList<Location> userLoc) {
        for(int i = 0; i < userLoc.size(); i++) {
            locations.add(userLoc.get(i));
        }
        if(locations.size() == userLoc.size()) {
            return true;
        }
        return false;
    }

    boolean thirdTry() {
        int bestDistance = 999999999;
        int sizer = locations.size();
        ArrayList<Location> originalLocations;
        for(int i = 0; i < sizer; i++) {
            originalLocations = new ArrayList<>(locations);
            for(int x = 0; x < locations.size() - 1; x++) {
                double distance = 999999999;
                int index = -1;
                for(int y = x + 1; y < locations.size(); y++) {
                    double temp = locations.get(x).distance(locations.get(y));
                    if(distance > temp) {
                        distance = temp;
                        index = y;
                    }
                }
                Location temploc = locations.get(x + 1);
                locations.set(x + 1, locations.get(index));
                locations.set(index, temploc);
                pairs.add(new Pair(Integer.toString(x), locations.get(x), locations.get(x + 1), locations.get(x).distance(locations.get(x + 1))));
            }
            pairs.add(new Pair(Integer.toString(locations.size() - 1), locations.get(locations.size() - 1), locations.get(0), locations.get(locations.size() - 1).distance(locations.get(0))));
            double total = 0;
            for(Pair p : pairs) {
                total += p.getDistance();
            }
            //System.out.println("[" + i + "]: " + total);
            if(total < bestDistance) {
                bestDistance = (int) Math.round(total);
                bestPairs = new ArrayList<>(pairs);
            }
            pairs.clear();
            locations = new ArrayList<>(originalLocations);
            Location temp = locations.remove(0);
            locations.add(temp);
        }
        pairs = new ArrayList<>(bestPairs);
        return true;
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    public void setSelectedAirports(ArrayList<String> selectedAirports) {
        this.selectedAirports = selectedAirports;
    }

    ArrayList<Location> getLocations() {
        return locations;
    }

    ArrayList<Pair> getPairs() {
        return pairs;
    }
}