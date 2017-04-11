package com.csu2017sp314.dtr07.Model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/*
 * Created by SummitDrift on 2/13/17.
 * Testing file for Model.java
 */
public class TestModel {

    @Before
    public void initialize() {
    }

    private Model m = new Model();
    private String units = "M";

    @Test
    public void planTrip() throws Exception {
        m.setSelectedLocations(new ArrayList<>());
        m.planTrip(units, false);
        assertEquals(2, m.getNumPairs());
    }

    @Test
    public void getFirstLon() throws Exception {
        m.setSelectedLocations(new ArrayList<>());
        m.planTrip(units, false);
        assertEquals(-104.751998901, m.getFirstLon(0), 0);
    }

    @Test
    public void getFirstLat() throws Exception {
        m.setSelectedLocations(new ArrayList<>());
        m.planTrip(units, false);
        assertEquals(39.701698303200004, m.getFirstLat(0), 0);
    }

    @Test
    public void getSecondLon() throws Exception {
    m.setSelectedLocations(new ArrayList<>());
        m.planTrip(units, false);
        assertEquals(-106.9179993, m.getSecondLon(0), 0);
    }

    @Test
    public void getSecondLat() throws Exception {
        m.setSelectedLocations(new ArrayList<>());
        m.planTrip(units, false);
        assertEquals(39.64260101, m.getSecondLat(0), 0);
    }

    @Test
    public void getPairDistance() throws Exception {
        m.setSelectedLocations(new ArrayList<>());
        m.planTrip(units, false);
        assertEquals(115, m.getPairDistance(0));
    }

    @Test
    public void getPairId() throws Exception {
        m.setSelectedLocations(new ArrayList<>());
        m.planTrip(units, false);
        assertEquals("0", m.getPairId(0));
    }

    @Test
    public void getNumPairs() throws Exception {
        m.setSelectedLocations(new ArrayList<>());
        m.planTrip(units, false);
        assertEquals(2, m.getNumPairs());
    }

    @Test
    public void getFirstName() throws Exception {
        m.setSelectedLocations(new ArrayList<>());
        m.planTrip(units, false);
        assertEquals("Buckley Air Force Base", m.getFirstName(0));
    }

    @Test
    public void getSecondName() throws Exception {
        m.setSelectedLocations(new ArrayList<>());
        m.planTrip(units, false);
        assertEquals("Eagle County Regional Airport", m.getSecondName(0));
    }

    @Test
    public void getTripDistance() throws Exception {
        m.setSelectedLocations(new ArrayList<>());
        m.planTrip(units, false);
        assertEquals(230, m.getTripDistance());
        m.setKilometers(true);
        m.planTrip(units, false);
        assertEquals(370, m.getTripDistance());
    }

    @Test
    public void setTwoOpt() {
        m.setSelectedLocations(new ArrayList<>());
        m.setTwoOpt(true);
        assertEquals(true, m.getTwoOpt());
    }

    @Test
    public void setThreeOpt() {
        m.setSelectedLocations(new ArrayList<>());
        m.setThreeOpt(true);
        assertEquals(true, m.getThreeOpt());
    }

    @Test
    public void twoOpt() throws Exception {
        m.setSelectedLocations(new ArrayList<>());
        m.planTrip(units, false);
        int origDist = m.getTripDistance();
        m.setTwoOpt(true);
        m.planTrip(units, false);
        int newDist = m.getTripDistance();
        assertEquals(true, origDist>=newDist);
    }

   @Test
    public void threeOpt() throws Exception {
        m.setSelectedLocations(new ArrayList<>());
        m.planTrip(units, false);
        int origDist = m.getTripDistance();
        m.setThreeOpt(true);
        m.planTrip(units, false);
        int newDist = m.getTripDistance();
        assertEquals(true, origDist>=newDist);
    }

    @Test
    public void setKilometers() {
        m.setKilometers(true);
        assertEquals(true, m.isKilometers());
    }
}
