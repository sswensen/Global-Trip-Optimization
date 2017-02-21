package Model;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by SummitDrift on 2/13/17.
 * Main class for Model Package
 */
public class Model {
    //private ArrayList<Location> locations;
    private ArrayList<Pair> pairs;
    public int planTrip(String filename) throws FileNotFoundException {
        LocationFactory lf = new LocationFactory();
        lf.readFile(filename);
        //lf.findNearest();
        lf.secondTry();
        //locations = lf.getLocations();
        pairs = lf.getPairs();
        return 1;
    }

    /*public int getLegStartLocation(int a) {
        return pairs.get(a).getOne().getId();
    }

    public int getLegFinishLocation(int a) {
        return pairs.get(a).getTwo().getId();
    }

    public int getLegDistance(Object o) {
        return -1;
    }

    public int getLocationID(int index) {
        return locations.get(index).getId();
    }

    public double getLocationLattitude(int index) {
        return locations.get(index).getLat();
    }

    public double getLocationLongitude(int index) {
        return locations.get(index).getLon();
    }*/

    public double getFirstLon(int i) {
        return pairs.get(i).getOne().getLon();
    }

    public double getFirstLat(int i) {
        return pairs.get(i).getOne().getLat();
    }

    public double getSecondLon(int i) {
        return pairs.get(i).getTwo().getLon();
    }

    public double getSecondLat(int i) {
        return pairs.get(i).getTwo().getLat();
    }

    public int getPairDistance(int i) {
        return (int) pairs.get(i).getDistance();
    }

    public int getPairId(int i) {
        return pairs.get(i).getId();
    }

    public int getNumPairs() {
        return pairs.size();
    }

    public String getFirstName(int i) {
        return pairs.get(i).getOne().getName();
    }

    public String getSecondName(int i) {
        return pairs.get(i).getTwo().getName();
    }

    public int getTripDistance() {
        int ret = 0;
        for(Pair p : pairs) {
            ret += p.getDistance();
        }
        return ret;
    }

    public int getFirstId(int i) {
        return pairs.get(i).getOne().getId();
    }

    public int getSecondId(int i) {
        return pairs.get(i).getTwo().getId();
    }
}
