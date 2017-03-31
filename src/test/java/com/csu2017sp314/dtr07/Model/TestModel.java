package com.csu2017sp314.dtr07.Model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/*
 * Created by SummitDrift on 2/13/17.
 * Testing file for Model.java
 */
public class TestModel {

    @Before
    public void initialize() {
    }

    private Model m = new Model();

    @Test
    public void planTrip() throws Exception {
        m.planTrip("./src/test/resources/Testing/ColoradoSkiResorts.csv");
        assertEquals(16, m.getNumPairs());
    }

    @Test
    public void getFirstLon() throws Exception {
        m.planTrip("./src/test/resources/Testing/ColoradoSkiResorts.csv");
        assertEquals(-107.8461111111111, m.getFirstLon(0), 0);
    }

    @Test
    public void getFirstLat() throws Exception {
        m.planTrip("./src/test/resources/Testing/ColoradoSkiResorts.csv");
        assertEquals(37.936388888888885, m.getFirstLat(0), 0);
    }

    @Test
    public void getSecondLon() throws Exception {
        m.planTrip("./src/test/resources/Testing/ColoradoSkiResorts.csv");
        assertEquals(-107.66722222222222, m.getSecondLon(0), 0);
    }

    @Test
    public void getSecondLat() throws Exception {
        m.planTrip("./src/test/resources/Testing/ColoradoSkiResorts.csv");
        assertEquals(37.88361111111111, m.getSecondLat(0), 0);
    }

    @Test
    public void getPairDistance() throws Exception {
        m.planTrip("./src/test/resources/Testing/ColoradoSkiResorts.csv");
        assertEquals(10, m.getPairDistance(0));
    }

    @Test
    public void getPairId() throws Exception {
        m.planTrip("./src/test/resources/Testing/ColoradoSkiResorts.csv");
        assertEquals("0", m.getPairId(0));
    }

    @Test
    public void getNumPairs() throws Exception {
        m.planTrip("./src/test/resources/Testing/ColoradoSkiResorts.csv");
        assertEquals(16, m.getNumPairs());
    }

    @Test
    public void getFirstName() throws Exception {
        m.planTrip("./src/test/resources/Testing/ColoradoSkiResorts.csv");
        assertEquals("Telluride", m.getFirstName(0));
    }

    @Test
    public void getSecondName() throws Exception {
        m.planTrip("./src/test/resources/Testing/ColoradoSkiResorts.csv");
        assertEquals("Silverton", m.getSecondName(0));
    }

    @Test
    public void getTripDistance() throws Exception {
        m.planTrip("./src/test/resources/Testing/ColoradoSkiResorts.csv");
        assertEquals(668, m.getTripDistance());
    }

    @Test
    public void setTwoOpt()
    {
        m.setTwoOpt(true);
        assertEquals(true, m.getTwoOpt());
    }

    @Test
    public void setThreeOpt()
    {
        m.setThreeOpt(true);
        assertEquals(true, m.getThreeOpt());
    }
}
