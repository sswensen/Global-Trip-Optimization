package Model;

/*
 * Created by SummitDrift on 2/13/17.
 */

import Model.Location;
import Model.LocationFactory;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestLocationFactory {
    @Before
    public void initialize() {
    }

    @Test
    public void readTest() {
        LocationFactory lf = new LocationFactory();
        String expected = "Cracker";
        try {
            lf.readFile("./src/Model/test/ColoradoSkiResorts.csv");
            //lf.findNearest();
            lf.secondTry();
            ArrayList<Location> result = lf.getLocations();
            ArrayList<Pair> pairs = lf.getPairs();
            Location one = new Location(1,"Wolf Creek", "37°28'20\" N", "106°47'35\" W");
            Assert.assertTrue(one.equals(result.get(0)));
        }
        catch (FileNotFoundException a) {
            System.out.println("Error eccountered: " + a);
        }
    }
}