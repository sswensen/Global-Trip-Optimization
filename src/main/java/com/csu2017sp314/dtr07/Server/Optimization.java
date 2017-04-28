package com.csu2017sp314.dtr07.Server;

import com.csu2017sp314.dtr07.Model.Location;
import com.csu2017sp314.dtr07.Model.Pair;

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

    public Optimization(Location[] route, String opt) {
        this.route = route;
        if(opt.equals("two")) {
            setTwoOpt(true);
        } else {
            setThreeOpt(true);
        }
        distTable = generateDistanceTable(this.route);
    }

    public Location[] getOptimizedRoute() {
        if(twoOpt) {
            twoOpt();
        } else {
            threeOpt();
        }
        return route;
    }

    private boolean nearestNeighbor() {
        Location[] locArray = locations.toArray(new Location[locations.size()]);
        generateDistanceTable(locArray);
        int bestDistance = 999999999;
        int sizer = locArray.length;
        Location[] originalLocations;
        for(int i = 0; i < sizer; i++) {
            originalLocations = Arrays.copyOf(locArray, locArray.length);
            for(int x = 0; x < locations.size() - 1; x++) {
                double distance = 999999999;
                int index = -1;
                for(int y = x + 1; y < locations.size(); y++) {
                    double temp = dist(locArray[x], locArray[y]);
                    if(distance > temp) {
                        distance = temp;
                        index = y;
                    }
                }
                Location temploc = locArray[x+1];
                locArray[x+1] = locArray[index];
                locArray[index] = temploc;
                pairs.add(new Pair(Integer.toString(x), locArray[x], locArray[x + 1], dist(locArray[x], locArray[x+1])));
            }

            pairs.add(new Pair(Integer.toString(locArray.length - 1), locArray[locArray.length - 1], locArray[0], dist(locArray[locArray.length-1], locArray[0])));
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
            if(total < bestDistance) {
                bestDistance = (int) Math.round(total);
                bestPairs = new ArrayList<>(pairs);
            }
            pairs.clear();
            locArray = Arrays.copyOf(originalLocations, originalLocations.length);
            locArray = shift(locArray);
        }
        locations = new ArrayList<>(Arrays.asList(locArray));
        pairs = new ArrayList<>(bestPairs);
        return true;
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
        return totalImprovements;
    }
}
