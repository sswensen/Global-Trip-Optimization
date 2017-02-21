package Model;

/*
 * Created by SummitDrift on 2/17/17.
 */
public class Pair {
    private Location one;
    private Location two;
    private double distance;
    private int id;

    Pair(int id, Location one, Location two, double distance) {
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
