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
    private boolean useWraparound = false;

    public Pair(String id, Location one, Location two, double distance) {
        this.id = id;
        this.one = one;
        this.two = two;
        this.distance = distance;
        checkForWraparound();
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

    public boolean isUseWraparound() {
        return useWraparound;
    }

    public void setUseWraparound(boolean useWraparound) {
        this.useWraparound = useWraparound;
    }

    public boolean checkForWraparound() {
        boolean ret = one.isPairUsesWraparound() && two.isPairUsesWraparound();
        useWraparound = ret;
        return ret;
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
