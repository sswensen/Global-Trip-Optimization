package com.csu2017sp314.dtr07.Model;

import java.util.ArrayList;

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
    private String unit = "";
    private boolean twoOpt;
    private boolean threeOpt;
    private double[][] distTable;
    private int numberReturnedFromDatabase = 0;

    ArrayList<Location> readFromDB(ArrayList<String> where, boolean read) {
        QueryBuilder qb = new QueryBuilder(read);
        //qb.setWhere(where);
        //qb.searchDatabase("heliport", "AS", "AE", "AE-DU", "Dubai", "Schumacher Heliport");
        //qb.searchDatabase("large_airport", "North America", "United States", "Colorado", "", "");
        if(where.size() == 0) {
            qb.searchDatabase("", "", "", "", "", "");
        } else {
            qb.searchDatabase(where.get(0), where.get(1), where.get(2), where.get(3), where.get(4), where.get(5));
        }
        qb.fireQuery();
        locations = qb.getLocations();
        numberReturnedFromDatabase = qb.getNumberReturnedFromDatabase();
        return locations;
    }

    boolean readUserLocations(ArrayList<Location> userLoc) {
        for(int i = 0; i < userLoc.size(); i++) {
            locations.add(userLoc.get(i));
        }
        return locations.size() == userLoc.size();
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
                    double temp = locations.get(x).distance(locations.get(y), unit);
                    if(distance > temp) {
                        distance = temp;
                        index = y;
                    }
                }
                Location temploc = locations.get(x + 1);
                locations.set(x + 1, locations.get(index));
                locations.set(index, temploc);
                pairs.add(new Pair(Integer.toString(x), locations.get(x), locations.get(x + 1), locations.get(x).distance(locations.get(x + 1), unit)));
            }

            pairs.add(new Pair(Integer.toString(locations.size() - 1), locations.get(locations.size() - 1), locations.get(0), locations.get(locations.size() - 1).distance(locations.get(0), unit)));
            if(twoOpt) {
                if(threeOpt) {
                    threeOpt();
                } else {
                    twoOpt();
                }
            }
            if(threeOpt) {
                threeOpt();
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

    public int getTotalImprovements() {
        return this.totalImprovements;
    }

    public void setTotalImprovements(int totalImprovements) {
        this.totalImprovements = totalImprovements;
    }
  
    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    ArrayList<Location> setSelectedAirports(ArrayList<String> selectedAirportIds, String idOrName, boolean useDB) {
        this.selectedAirports = selectedAirportIds;
        QueryBuilder qb = new QueryBuilder(useDB);
        qb.search4IDinDatabase(selectedAirports, idOrName);
        qb.fireQuery(); //Searches db with selectedAirports as where and sets qb's local locations
        locations = qb.getLocations(); //Need to call this to update lf's locations
        return locations;
    }

    void setUnit(String unit) {
        this.unit = unit;
    }

    ArrayList<Location> getLocations() {
        return new ArrayList<>(locations);
    }

    public void setLocations(ArrayList<Location> locations) {
        this.locations = locations;
    }

    ArrayList<Pair> getPairs() {
        return new ArrayList<>(pairs);
    }

    public void setTwoOpt(boolean twoOpt) {
        this.twoOpt = twoOpt;
    }

    public void setThreeOpt(boolean threeOpt) {
        this.threeOpt = threeOpt;
    }

    public boolean getTwoOpt() {
        return this.twoOpt;
    }

    void setTwoOpt(boolean twoOpt) {
        this.twoOpt = twoOpt;
    }

    public boolean getThreeOpt() {
        return this.threeOpt;
    }

    public void setThreeOpt(boolean threeOpt) {
        this.threeOpt = threeOpt;
    }

    private ArrayList<Pair> generatePairs(Location[] route, ArrayList<Pair> newPairs) {
        for(int a = 0; a < route.length - 1; a++) {
            newPairs.add(new Pair(Integer.toString(a), route[a], route[a + 1], route[a].distance(route[a + 1], unit)));
        }
        return newPairs;
    }

    private Location[] generateRoute() {
        Location[] route = new Location[pairs.size()+1];
        int i = 0;
        for(Pair pair : pairs) {
            route[i] = pair.getOne();
            i++;
        }
        route[route.length - 1] = pairs.get(0).getOne();
        return route;
    }

    private double[][] generateDistanceTable(Location[] route) {
        double[][] distTable = new double[route.length][route.length];
        for(int i = 0; i < route.length; i++) {
            route[i].setTableIndex(i);
            for(int j = 0; j < route.length; j++) {
                distTable[i][j] = route[i].distance(route[j], unit);
            }
        }
        this.distTable = distTable;
        return distTable;
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
            if(i == j || j < i) {
                break;
            }
        }
    }

    private Location[] swapSegments(Location[] route, int a, int b, int c, int d) {
        Location[] newRoute = new Location[route.length];
        //Copy up to a in order
        int index = 0;
        for(int i = 0; i < a; i++) {
            newRoute[index] = route[i];
            index++;
        }
        //Add c->d to newRoute
        for(int i = c; i <= d; i++) {
            newRoute[index] = route[i];
            index++;
        }
        //Add b+1->c to newRoute
        for(int i = b + 1; i < c; i++) {
            newRoute[index] = route[i];
            index++;
        }
        //Add a->b to newRoute
        for(int i = a; i <= b; i++) {
            newRoute[index] = route[i];
            index++;
        }
        //Add d->n to newRoute
        for(int i = d + 1; i < route.length; i++) {
            newRoute[index] = route[i];
            index++;
        }
        return newRoute;
    }

    protected int twoOpt() {
        Location[] route = generateRoute();
        generateDistanceTable(route);
        int totalImprovements = 0;
        int improvements = 1;
        int n = route.length - 1;
        ArrayList<Pair> newPairs = new ArrayList<>();
        while(improvements > 0) {
            improvements = 0;
            for(int i=0; i<=n-3; i++) {
                for(int j=i+2; j<=n-1; j++) {
                    if((dist(route[i], route[i+1])+dist(route[j], route[j+1]))
                            > (dist(route[i], route[j])+dist(route[i+1], route[j+1]))) {
                        reverseSegment(route, i+1, j);
                        improvements++;
                        totalImprovements++;
                    }
                }
            }
        }
        pairs = generatePairs(route, newPairs);
        return totalImprovements;
    }

    private Location[] improve(Location[] route, int num, int i, int j, int k) {
        switch(num) {
            case 1:
                reverseSegment(route, j + 1, k);
                return route;
            case 2:
                reverseSegment(route, i + 1, j);
                return route;
            case 3:
                reverseSegment(route, i + 1, k);
                return route;
            case 4:
                route = swapSegments(route, i + 1, j, j + 1, k);
                return route;
            case 5:
                reverseSegment(route, i + 1, j);
                reverseSegment(route, j + 1, k);
                return route;
            case 6:
                reverseSegment(route, j + 1, k);
                route = swapSegments(route, i + 1, j, j + 1, k);
                return route;
            case 7:
                reverseSegment(route, i + 1, j);
                route = swapSegments(route, i + 1, j, j + 1, k);
                return route;
            default:
                return route;
        }
    }

    private int improved(Location[] route, int i, int j, int k) {
        double originalDist = dist(route[i], route[i+1]) + dist(route[j], route[j+1])
                + dist(route[k], route[k+1]);
        double distOne = (dist(route[i], route[i+1]) + dist(route[j], route[k])
                + dist(route[j+1], route[k+1]));
        double distTwo = (dist(route[i], route[j]) + dist(route[i+1], route[j+1])
                + dist(route[k], route[k+1]));
        double distThree = (dist(route[i], route[k]) + dist(route[j+1], route[j])
                + dist(route[i+1], route[k+1]));
        double distFour = (dist(route[i], route[j+1]) + dist(route[k], route[i+1])
                + dist(route[j], route[k+1]));
        double distFive = (dist(route[i], route[j]) + dist(route[i+1], route[k])
                + dist(route[j+1], route[k+1]));
        double distSix = (dist(route[i], route[k]) + dist(route[j+1], route[i+1])
                + dist(route[j], route[k+1]));
        double distSeven = (dist(route[i], route[j+1]) + dist(route[k], route[j])
                + dist(route[i+1], route[k+1]));
        if(originalDist > distOne) {
            return 1;
        } else if(originalDist > distTwo) {
            return 2;
        } else if(originalDist > distThree) {
            return 3;
        } else if(originalDist > distFour) {
            return 4;
        } else if(originalDist > distFive) {
            return 5;
        } else if(originalDist > distSix) {
            return 6;
        } else if(originalDist > distSeven) {
            return 7;
        } else {
            return 0;
        }
    }

    protected int threeOpt() {
        Location[] route = generateRoute();
        generateDistanceTable(route);
        int totalImprovements = 0;
        int improvements = 1;
        int n = route.length - 1;
        ArrayList<Pair> newPairs = new ArrayList<>();
        while(improvements > 0) {
            improvements = 0;
            for(int i = 0; i <= n - 5; i++) {
                for(int j = i + 2; j <= n - 3; j++) {
                    for(int k = j + 2; k <= n - 1; k++) {
                        int improved = improved(route, i, j, k);
                        if(improved > 0) {
                            route = improve(route, improved, i, j, k);
                            improvements++;
                            totalImprovements++;
                        }
                    }
                }
            }
        }
        pairs = generatePairs(route, newPairs);
        return totalImprovements;
    }

    public int getNumberReturnedFromDatabase() {
        return numberReturnedFromDatabase;
    }
}