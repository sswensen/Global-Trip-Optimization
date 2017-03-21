package com.csu2017sp314.dtr07.Model;

/**
 * Created by SummitDrift on 2/17/17.
 *
 * @author Scott Swensen
 */

public class Pair {
    private Location one;
    private Location two;
    private double distance;
    private String id;

    Pair(String id, Location one, Location two, double distance) {
        this.id = id;
        this.one = one;
        this.two = two;
        this.distance = distance;
    }

    Location getOne() {
        return one;
    }

    Location getTwo() {
        return two;
    }

    double getDistance() {
        return distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pair{"
                + "one=" + one
                + ", two=" + two
                + ", distance=" + distance
                + '}';
    }
}
