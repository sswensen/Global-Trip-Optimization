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
    Model m = new Model();

    @Test
    public void planTrip() throws Exception {
        m.planTrip("./src/Testing/ColoradoSkiResorts.csv");
        assertEquals(16, m.getNumPairs());
    }

    @Test
    public void getFirstLon() throws Exception {
        m.planTrip("./src/Testing/ColoradoSkiResorts.csv");
        assertEquals(-106.79305555555555, m.getFirstLon(0), 0);
    }

    @Test
    public void getFirstLat() throws Exception {
        m.planTrip("./src/Testing/ColoradoSkiResorts.csv");
        assertEquals(37.47222222222222, m.getFirstLat(0), 0);
    }

    @Test
    public void getSecondLon() throws Exception {
        m.planTrip("./src/Testing/ColoradoSkiResorts.csv");
        assertEquals(-107.66722222222222, m.getSecondLon(0), 0);
    }

    @Test
    public void getSecondLat() throws Exception {
        m.planTrip("./src/Testing/ColoradoSkiResorts.csv");
        assertEquals(37.88361111111111, m.getSecondLat(0), 0);
    }

    @Test
    public void getPairDistance() throws Exception {
        m.planTrip("./src/Testing/ColoradoSkiResorts.csv");
        assertEquals(55, m.getPairDistance(0));
    }

    @Test
    public void getPairId() throws Exception {
        m.planTrip("./src/Testing/ColoradoSkiResorts.csv");
        assertEquals(0, m.getPairId(0));
    }

    @Test
    public void getNumPairs() throws Exception {
        m.planTrip("./src/Testing/ColoradoSkiResorts.csv");
        assertEquals(16, m.getNumPairs());
    }
}
