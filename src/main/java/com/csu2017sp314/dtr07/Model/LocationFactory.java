package com.csu2017sp314.dtr07.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by SummitDrift on 2/13/17.
 *
 * @author Scott Swensen
 *         Factory for creation of locations and pairs
 */

class LocationFactory {
    private ArrayList<Location> locations = new ArrayList<Location>();
    private ArrayList<Pair> pairs = new ArrayList<>();
    private ArrayList<Pair> bestPairs = new ArrayList<>();
    private boolean twoOpt;
    private boolean threeOpt;
    private int totalImprovements;
    private double[][] distTable;

    boolean readFile(String in) throws FileNotFoundException {
        Scanner scan = new Scanner(new File(in));
        int id = -1;
        int name = -1;
        int latitude = -1;
        int longitude = -1;
        if(scan.hasNext()) {
            String[] line = scan.nextLine().split(",");
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
            Location temp = new Location(line[id], line[name], line[latitude].replaceAll("\\s+", ""), line[longitude].replaceAll("\\s+", ""));
            locations.add(temp);
        }
        scan.close();
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
            if(twoOpt) {
                if(threeOpt) {

                }
                else {
                    betterTwoOpt();
                }
            }
            if(threeOpt) {
                betterThreeOpt();
            }
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

    ArrayList<Location> getLocations() {
        return locations;
    }

    ArrayList<Pair> getPairs() {
        return pairs;
    }

    public void setTwoOpt(boolean twoOpt) {
        this.twoOpt = twoOpt;
    }

    public void setThreeOpt(boolean threeOpt) {
        this.threeOpt = threeOpt;
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
        //double dist = 0.0;
        for(Pair p : pairs) {
            //    dist += p.getDistance();
            ret += p.getDistance();
        }
        //System.out.println(dist);
        return ret;
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

    private ArrayList<Pair> betterGeneratePairs(Location[] route, ArrayList<Pair> newPairs) {
        for(int a = 0; a < route.length - 1; a++) {
            newPairs.add(new Pair(Integer.toString(a), route[a], route[a + 1], route[a].distance(route[a + 1])));
        }
        //newPairs.add(new Pair(Integer.toString(route.length - 2), route[route.length - 2], route[0], route[route.length - 2].distance(route[0])));
        return newPairs;
    }

    private ArrayList<Location> generateRoute() {
        ArrayList<Location> route = new ArrayList<>();
        for(Pair pair : pairs) {
            route.add(pair.getOne());
        }
        return route;
    }

    private Location[] betterGenerateRoute() {
        Location[] route = new Location[pairs.size()+1];
        int i = 0;
        for(Pair pair : pairs) {
            route[i] = pair.getOne();
            i++;
        }
        route[route.length-1] = pairs.get(0).getOne();
        return route;
    }

    private double[][] generateDistanceTable(Location[] route) {
        double[][] distTable = new double[route.length][route.length];
        for(int i = 0; i < route.length; i++) {
            route[i].setTableIndex(i);
            for (int j = 0; j < route.length; j++) {
                distTable[i][j] = route[i].distance(route[j]);
            }
        }
        this.distTable = distTable;
        return distTable;
    }

    private double dist(double[][] distTable, int from, int to)
    {
        return (distTable[from][to]);
    }

    private double dist(Location[] route, int from, int to) {
        return route[from].distance(route[to]);
    }

    private double dist(double[][] distTable, Location from, Location to) {
        return distTable[from.getTableIndex()][to.getTableIndex()];
    }

    private double dist(Location from, Location to) {
        return this.distTable[from.getTableIndex()][to.getTableIndex()];
    }

    private void reverseSegment(Location[] route, int i, int j) {
        while(true) {
            Location temp = route[i];
            route[i] = route[j];
            route[j] = temp;
            i++;
            j--;
            if(i==j || j<i)
                break;
        }
    }

    private void reverseSegment(int[] route, int i, int j) {
        while(true) {
            int temp = route[i];
            route[i] = route[j];
            route[j] = temp;
            i++;
            j--;
            if(i==j || j<i)
                break;
        }
    }

    private Location[] swapSegments(Location[] route, int a, int b, int c, int d) {
        Location[] newRoute = new Location[route.length];
        //Copy up to a in order
        int index = 0;
        for(int i=0; i<a; i++) {
            newRoute[index] = route[i];
            index++;
        }
        //Add c->d to newRoute
        for(int i=c; i<=d; i++) {
            newRoute[index] = route[i];
            index++;
        }
        //Add b+1->c to newRoute
        for(int i=b+1; i<c; i++) {
            newRoute[index] = route[i];
            index++;
        }
        //Add a->b to newRoute
        for(int i=a; i<=b; i++) {
            newRoute[index] = route[i];
            index++;
        }
        //Add d->n to newRoute
        for(int i=d+1; i<route.length; i++) {
            newRoute[index] = route[i];
            index++;
        }
        return newRoute;
    }

    private int[] swapSegments(int[] route, int a, int b, int c, int d) {
        int[] newRoute = new int[route.length];
        //Copy up to a in order
        int index = 0;
        for(int i=0; i<a; i++) {
            newRoute[index] = route[i];
            index++;
        }
        //Add c->d to newRoute
        for(int i=c; i<=d; i++) {
            newRoute[index] = route[i];
            index++;
        }
        //Add b+1->c to newRoute
        for(int i=b+1; i<c; i++) {
            newRoute[index] = route[i];
            index++;
        }
        //Add a->b to newRoute
        for(int i=a; i<=b; i++) {
            newRoute[index] = route[i];
            index++;
        }
        //Add d->n to newRoute
        for(int i=d+1; i<route.length; i++) {
            newRoute[index] = route[i];
            index++;
        }
        return newRoute;
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
        int oldTripDistance;
        int newTripDistance;
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

    protected int betterTwoOpt() {
        Location[] route = betterGenerateRoute();
        double[][] distTable = generateDistanceTable(route);
        //System.out.println(route[18].distance(route[33]));
        //System.out.println(dist(distTable, 18, 33));
        //System.out.println(dist(route, 18, 33));
        //for(int i = 0; i<distTable.length; i++) {
        //    System.out.println(Arrays.toString(distTable[i]));
        //}
        ArrayList<Pair> newPairs = new ArrayList<>();
        //Start Debug
        betterGeneratePairs(route, newPairs);
        System.out.println(getTripDistance());
        //System.out.println(getTripDistance(newPairs));
        //boolean same = true;
        //for(int i=0; i<pairs.size(); i++) {
        //    if(!pairs.get(i).getOne().getName().equals(newPairs.get(i).getOne().getName())) {
        //        same = false;
        //    }
        //}
        //if(same)
        //    System.out.println("YESSSSSSSSSSSS");
        System.out.println(Arrays.toString(route));
        System.out.println(pairs);
        System.out.println(newPairs);
        //End Debug
        int totalImprovements = 0;
        this.totalImprovements = 0;
        int improvements = 1;
        int n = route.length-1;
        while(improvements > 0) {
            improvements = 0;
            for(int i=0; i<=n-3; i++) {
                for(int j=i+2; j<=n-1; j++) {
                    if((dist(route[i], route[i+1])+dist(route[j], route[j+1])) > (dist(route[i], route[j])+dist(route[i+1], route[j+1]))) {
                        //Start Debug
                        System.out.println("i: " + route[i].getName() + " i+1: " + route[i+1].getName() + " j: " + route[j].getName() + " j+1: " + route[j+1].getName());
                        System.out.println("i: " + route[i].getLat() + " " + route[i].getLon() + " i+1: " + route[i+1].getLat() + " " + route[i+1].getLon() + " j: " + route[j].getLat() + " " + route[j].getLon() + " j+1: " + route[j+1].getLat() + " " + route[j+1].getLon());
                        System.out.println(dist(distTable, i, i+1) + " " + dist(distTable, j, j+1) + " " + dist(distTable, i, j) + " " + dist(distTable, i+1, j+1));
                        System.out.println(dist(distTable, route[i], route[i+1]) + " " + dist(distTable, route[j], route[j+1]) + " " + dist(distTable, route[i], route[j]) + " " + dist(distTable, route[i+1], route[j+1]));
                        System.out.println(dist(route, i, i+1) + " " + dist(route, j, j+1) + " " + dist(route, i, j) + " " + dist(route, i+1, j+1));
                        //End Debug

                        reverseSegment(route, i+1, j);
                        improvements++;
                        totalImprovements++;

                        //Start Debug
                        newPairs = new ArrayList<>();
                        betterGeneratePairs(route, newPairs);
                        System.out.println(getTripDistance(newPairs));
                        //End Debug
                    }
                }
            }
            //System.out.println(improvements);
        }
        this.totalImprovements = totalImprovements;
        //Start Debug
        newPairs = new ArrayList<>();
        //End Debug
        pairs = betterGeneratePairs(route, newPairs);
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
        int oldTripDistance;
        int newTripDistance;
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

    private Location[] improve(Location[] route, int num, int i, int j, int k) {
        switch(num) {
            case 1:
                reverseSegment(route, j+1, k);
                return route;
            case 2:
                reverseSegment(route, i+1, j);
                return route;
            case 3:
                System.out.println("i: " + route[i].getName() + " i+1: " + route[i+1].getName() + " j: " + route[j].getName() + " j+1: " + route[j+1].getName() + " k: " + route[k].getName() + " k+1: " + route[k+1].getName());
                reverseSegment(route, i+1, k);
                System.out.println("i: " + route[i].getName() + " i+1: " + route[i+1].getName() + " j: " + route[j].getName() + " j+1: " + route[j+1].getName() + " k: " + route[k].getName() + " k+1: " + route[k+1].getName());
                return route;
            case 4:
                //System.out.println("i: " + route[i].getName() + " i+1: " + route[i+1].getName() + " j: " + route[j].getName() + " j+1: " + route[j+1].getName() + " k: " + route[k].getName() + " k+1: " + route[k+1].getName());
                route = swapSegments(route, i+1, j, j+1, k);
                //System.out.println("i: " + route[i].getName() + " i+1: " + route[i+1].getName() + " j: " + route[j].getName() + " j+1: " + route[j+1].getName() + " k: " + route[k].getName() + " k+1: " + route[k+1].getName());
                return route;
            case 5: //Change to final dist
                //System.out.println("i: " + route[i].getName() + " i+1: " + route[i+1].getName() + " j: " + route[j].getName() + " j+1: " + route[j+1].getName() + " k: " + route[k].getName() + " k+1: " + route[k+1].getName());
                reverseSegment(route, i+1, j);
                reverseSegment(route, j+1, k);
                //System.out.println("i: " + route[i].getName() + " i+1: " + route[i+1].getName() + " j: " + route[j].getName() + " j+1: " + route[j+1].getName() + " k: " + route[k].getName() + " k+1: " + route[k+1].getName());
                return route;
            case 6:
                //System.out.println("i: " + route[i].getName() + " i+1: " + route[i+1].getName() + " j: " + route[j].getName() + " j+1: " + route[j+1].getName() + " k: " + route[k].getName() + " k+1: " + route[k+1].getName());
                reverseSegment(route, j+1, k);
                route = swapSegments(route, i+1, j, j+1, k);
                //System.out.println("i: " + route[i].getName() + " i+1: " + route[i+1].getName() + " j: " + route[j].getName() + " j+1: " + route[j+1].getName() + " k: " + route[k].getName() + " k+1: " + route[k+1].getName());
                return route;
            case 7:
                //System.out.println("i: " + route[i].getName() + " i+1: " + route[i+1].getName() + " j: " + route[j].getName() + " j+1: " + route[j+1].getName() + " k: " + route[k].getName() + " k+1: " + route[k+1].getName());
                reverseSegment(route, i+1, j);
                route = swapSegments(route, i+1, j, j+1, k);
                //System.out.println("i: " + route[i].getName() + " i+1: " + route[i+1].getName() + " j: " + route[j].getName() + " j+1: " + route[j+1].getName() + " k: " + route[k].getName() + " k+1: " + route[k+1].getName());
                return route;
            default:
                return route;
        }
    }

    private int improved(Location[] route, int i, int j, int k) {
        double originalDist = dist(route[i], route[i+1]) + dist(route[j], route[j+1]) + dist(route[k], route[k+1]);
        if(originalDist > (dist(route[i], route[i+1]) + dist(route[j], route[k]) + dist(route[j+1], route[k+1]))) {
            //System.out.println("i: " + route[i].getName() + " i+1: " + route[i+1].getName() + " j: " + route[j].getName() + " j+1: " + route[j+1].getName() + " k: " + route[k].getName() + " k+1: " + route[k+1].getName());
            //reverseSegment(route, j+1, k);
            //System.out.println("i: " + route[i].getName() + " i+1: " + route[i+1].getName() + " j: " + route[j].getName() + " j+1: " + route[j+1].getName() + " k: " + route[k].getName() + " k+1: " + route[k+1].getName());
            return 1;
        }
        else if(originalDist > (dist(route[i], route[j]) + dist(route[i+1], route[j+1]) + dist(route[k], route[k+1]))) {
            //reverseSegment(route, i+1, j);
            return 2;
        }
        else if(originalDist > (dist(route[i], route[k]) + dist(route[j+1], route[j]) + dist(route[i+1], route[k+1]))) {
            System.out.println(originalDist + " " + (dist(route[i], route[k]) + dist(route[j+1], route[j]) + dist(route[i+1], route[k+1])));
            //reverseSegment(route, i+1, k);
            return 3;
        }
        else if(originalDist > (dist(route[i], route[j+1]) + dist(route[k], route[i+1]) + dist(route[j], route[k+1]))) {
            //System.out.println("i: " + route[i].getName() + " i+1: " + route[i+1].getName() + " j: " + route[j].getName() + " j+1: " + route[j+1].getName() + " k: " + route[k].getName() + " k+1: " + route[k+1].getName());
            //route = swapSegments(route, i+1, j, j+1, k);
            //System.out.println("i: " + route[i].getName() + " i+1: " + route[i+1].getName() + " j: " + route[j].getName() + " j+1: " + route[j+1].getName() + " k: " + route[k].getName() + " k+1: " + route[k+1].getName());
            return 4;
        }
        else if(originalDist > (dist(route[i], route[j]) + dist(route[i+1], route[k]) + dist(route[j+1], route[k+1]))) {
            //reverseSegment(route, i+1, j);
            //reverseSegment(route, j+1, k);
            return 5;
        }
        else if(originalDist > (dist(route[i], route[k]) + dist(route[j+1], route[i+1]) + dist(route[j], route[k+1]))) {
            //reverseSegment(route, j+1, k);
            //route = swapSegments(route, i+1, j, j+1, k);
            return 6;
        }
        else if(originalDist > (dist(route[i], route[j+1]) + dist(route[k], route[j]) + dist(route[i+1], route[k+1]))) {
            //Start Debug
            //System.out.println("i: " + route[i].getName() + " i+1: " + route[i+1].getName() + " j: " + route[j].getName() + " j+1: " + route[j+1].getName() + " k: " + route[k].getName() + " k+1: " + route[k+1].getName());
            //System.out.println("i: " + route[i].getLat() + " " + route[i].getLon() + " i+1: " + route[i+1].getLat() + " " + route[i+1].getLon() + " j: " + route[j].getLat() + " " + route[j].getLon() + " j+1: " + route[j+1].getLat() + " " + route[j+1].getLon() + " k: " + route[k].getLat() + " " + route[k].getLon() + " k+1: " + route[k+1].getLat() + " " + route[k+1].getLon());
            //System.out.println(originalDist + " " + dist(route[i], route[j+1]) + " " + dist(route[k], route[j]) + " " + dist(route[i+1], route[k+1]));
            //End Debug
            //reverseSegment(route, i+1, j);
            //route = swapSegments(route, i+1, j, j+1, k);
            //System.out.println("i: " + route[i].getName() + " i+1: " + route[i+1].getName() + " j: " + route[j].getName() + " j+1: " + route[j+1].getName() + " k: " + route[k].getName() + " k+1: " + route[k+1].getName());
            return 7;
        }
        else {
            return 0;
        }
    }

    protected int betterThreeOpt() {
        Location[] route = betterGenerateRoute();
        double[][] distTable = generateDistanceTable(route);
        ArrayList<Pair> newPairs = new ArrayList<>();
        //Start Debug
        betterGeneratePairs(route, newPairs);
        System.out.println(getTripDistance());
        //System.out.println(getTripDistance(newPairs));
        //boolean same = true;
        //for(int i=0; i<pairs.size(); i++) {
        //    if(!pairs.get(i).getOne().getName().equals(newPairs.get(i).getOne().getName())) {
        //        same = false;
        //    }
        //}
        //if(same)
        //    System.out.println("YESSSSSSSSSSSS");
        //System.out.println(Arrays.toString(route));
        //System.out.println(pairs);
        //System.out.println(newPairs);
        //End Debug
        int totalImprovements = 0;
        this.totalImprovements = 0;
        int improvements = 1;
        int n = route.length-1;
        while(improvements > 0) {
            improvements = 0;
            for(int i=0; i<=n-5; i++) {
                for(int j=i+2; j<=n-3; j++) {
                    for(int k=j+2; k<=n-1; k++) {
                        int improved = improved(route, i, j ,k);
                        if (improved>0) {
                            route = improve(route, improved, i, j, k);
                            improvements++;
                            totalImprovements++;

                            //Start Debug
                            newPairs = new ArrayList<>();
                            betterGeneratePairs(route, newPairs);
                            System.out.println(getTripDistance(newPairs));
                            //End Debug
                        }
                    }
                }
            }
        }
        this.totalImprovements = totalImprovements;
        //Start Debug
        newPairs = new ArrayList<>();
        //End Debug
        pairs = betterGeneratePairs(route, newPairs);
        return totalImprovements;
    }

    private void bothOpt()
    {
        while (threeOpt() > 0 || twoOpt() > 0);
    }
}