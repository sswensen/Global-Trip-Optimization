package Model;

/**
 * Created by SummitDrift on 2/17/17.
 */
public class Pair {
    private Location one;
    private Location two;
    private double distance;
    private int id;

    public Pair(int id, Location one, Location two, double distance) {
        this.id = id;
        this.one = one;
        this.two = two;
        this.distance = distance;
    }

    public Location getOne() {
        return one;
    }

    public void setOne(Location one) {
        this.one = one;
    }

    public Location getTwo() {
        return two;
    }

    public void setTwo(Location two) {
        this.two = two;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "one=" + one +
                ", two=" + two +
                ", distance=" + distance +
                '}';
    }
}
