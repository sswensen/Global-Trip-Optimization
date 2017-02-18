package Model;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by SummitDrift on 2/13/17.
 */
public class Model {
    private ArrayList<Location> locations;
    public int planTrip(String filename) throws FileNotFoundException {
        LocationFactory lf = new LocationFactory();
        lf.readFile(filename);
        lf.findNearest();

        return 1;
    }

    public int getLegStartLocation(int a) {
        return -1;
    }

    public int getLegFinishLocation(int a) {
        return -1;
    }

    public int getLegDistance(Object o) {
        return -1;
    }

    public String getLocationID(int index) {
        return locations.get(index).getId();
    }

    public double getLocationLattitude(int index) {
        return -1.0;
    }

    public double getLocationLongitude(int index) {
        return -1.0;
    }
}
