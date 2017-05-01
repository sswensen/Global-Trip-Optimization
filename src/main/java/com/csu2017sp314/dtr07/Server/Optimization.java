package com.csu2017sp314.dtr07.Server;

import com.csu2017sp314.dtr07.Model.Location;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by jesseL1on on 4/28/2017.
 */
public class Optimization {
    private boolean twoOpt;
    private boolean threeOpt;
    private double[][] distTable;
    private Location[] route;
    private Location[] locations;
    private Location[] bestRoute;

    public Optimization(Location[] locations, String opt) {
        this.locations = locations;
        if(opt.equals("0")) {
            setTwoOpt(false);
            setThreeOpt(false);
        } else if(opt.equals("2")) {
            setTwoOpt(true);
            setThreeOpt(false);
        } else if(opt.equals("3")) {
            setTwoOpt(false);
            setThreeOpt(true);
        } else {
            System.out.println("No opt chosen!");
        }
        Location[] routeForTable = generateLocs(locations);
        distTable = generateDistanceTable(routeForTable);
    }

    public Location[] getOptimizedRoute() {
        nearestNeighbor();
        return route;
    }

    private void nearestNeighbor() {
        route = new Location[locations.length+1];
        int bestDistance = 999999999;
        int sizer = locations.length;
        Location[] originalLocations;
        int addIndex;
        for(int i = 0; i < sizer; i++) {
            addIndex = 0;
            originalLocations = Arrays.copyOf(locations, locations.length);
            for(int x = 0; x < locations.length - 1; x++) {
                double distance = 999999999;
                int index = -1;
                for(int y = x + 1; y < locations.length; y++) {
                    double temp = dist(locations[x], locations[y]);
                    if(distance > temp) {
                        distance = temp;
                        index = y;
                    }
                }
                Location temploc = locations[x+1];
                locations[x+1] = locations[index];
                locations[index] = temploc;
                route[addIndex] = locations[x];
                addIndex++;
            }

            route[route.length-2] = locations[locations.length-1];
            route[route.length-1] = locations[0]; //???
            if(twoOpt) {
                twoOpt();
            } else if(threeOpt) {
                threeOpt();
            }
            double total = getTripDistance();
            if(total < bestDistance) {
                bestDistance = (int) Math.round(total);
                bestRoute = Arrays.copyOf(route, route.length);
            }
            route = new Location[locations.length+1];
            locations = Arrays.copyOf(originalLocations, originalLocations.length);
            locations = shift(locations);
        }
        route = Arrays.copyOf(bestRoute, bestRoute.length);
    }

    private Location[] shift(Location[] array) {
        Location first = array[0];
        for(int i=0;i<array.length-1;i++) {
            array[i] = array[i+1];
        }
        array[array.length-1] = first;
        return array;
    }

    public boolean getTwoOpt() {
        return this.twoOpt;
    }

    public void setTwoOpt(boolean twoOpt) {
        this.twoOpt = twoOpt;
    }

    public boolean getThreeOpt() {
        return this.threeOpt;
    }

    public void setThreeOpt(boolean threeOpt) {
        this.threeOpt = threeOpt;
    }

    double getTripDistance() {
        double total = 0;
        for(int i=0; i<route.length-1; i++) {
            total += dist(route[i], route[i+1]);
        }
        return total;
    }

    private Location[] generateLocs(Location[] locations) {
        Location[] newLocs = new Location[locations.length+1];
        for(int i=0; i<locations.length; i++) {
            newLocs[i] = locations[i];
        }
        newLocs[newLocs.length-1] = locations[0];
        return newLocs;
    }

    private double[][] generateDistanceTable(Location[] route) {
        double[][] distTable = new double[route.length][route.length];
        for(int i = 0; i < route.length; i++) {
            route[i].setTableIndex(i);
            for(int j = 0; j < route.length; j++) {
                distTable[i][j] = route[i].distance(route[j]);
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

    private int twoOpt() {
        int totalImprovements = 0;
        int improvements = 1;
        int n = route.length - 1;
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

    private int threeOpt() {
        generateDistanceTable(route);
        int totalImprovements = 0;
        int improvements = 1;
        int n = route.length - 1;
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
        return totalImprovements;
    }
}
