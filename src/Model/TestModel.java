package Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by SummitDrift on 2/13/17.
 */
public class TestModel {
    @Before
    public void initialize() {
    }

    @Test
    public void planTrip() throws Exception {
        Model m = new Model();
        m.planTrip("./src/Testing/ColoradoSkiResorts.csv");
        assertEquals(16, m.getNumPairs());
    }

    @Test
    public void getLocationID() throws Exception {

    }

    @Test
    public void getLocationLattitude() throws Exception {

    }

    @Test
    public void getLocationLongitude() throws Exception {

    }

    @Test
    public void getFirstLon() throws Exception {

    }

    @Test
    public void getFirstLat() throws Exception {

    }

    @Test
    public void getSecondLon() throws Exception {

    }

    @Test
    public void getSecondLat() throws Exception {

    }

    @Test
    public void getPairDistance() throws Exception {

    }

    @Test
    public void getNumPairs() throws Exception {

    }
}
