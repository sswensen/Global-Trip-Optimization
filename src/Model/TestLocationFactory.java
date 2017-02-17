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
        String expected = "Negro";
        try {
            ArrayList<Location> result = lf.readFile("./src/Model/test/test.csv");
            Location one = new Location("anthonysuper", "Equinox Brewing Co", "Fort Collins",
                    "40\u00b035'10.68\"N", "105\u00b04'32.51\"W", "1518");
            Assert.assertTrue(one.equals(result.get(0)));
        }
        catch (FileNotFoundException a) {
            System.out.println("Error eccountered: " + a);
        }
    }
}