package com.csu2017sp314.dtr07.Model;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/*
 * Created by SummitDrift on 2/16/17.
 * Testing file for Location.java
 */

public class TestLocation {
    @Before
    public void initialize() {
    }

    Location one = (new Location("0", "A", "39.701698303200004", "-104.751998901", "Aurora", "Colorado", "United States", "North America", "http://en.wikipedia.org/wiki/Buckley_Air_Force_Base", "http://en.wikipedia.org/wiki/Colorado", "http://en.wikipedia.org/wiki/United_States"));
    Location two = (new Location("1", "B", "-39.701698303200004", "104.751998901", "Fort Collins", "Colorado", "United States", "North America", "http://en.wikipedia.org/wiki/Buckley_Air_Force_Base", "http://en.wikipedia.org/wiki/Colorado", "http://en.wikipedia.org/wiki/United_States"));
    Location equal = (new Location("0", "A", "39.701698303200004", "-104.751998901", "Aurora", "Colorado", "United States", "North America", "http://en.wikipedia.org/wiki/Buckley_Air_Force_Base", "http://en.wikipedia.org/wiki/Colorado", "http://en.wikipedia.org/wiki/United_States"));


    @Test
    public void getId() throws Exception {
        assertEquals("0", one.getId());
    }

    @Test
    public void setId() throws Exception {
        one.setId("1");
        assertEquals("1", one.getId());
    }

    @Test
    public void getName() throws Exception {
        assertEquals("A", one.getName());
    }

    @Test
    public void setName() throws Exception {
        one.setName("a");
        one.setName("B");
        assertEquals("B", one.getName());
    }

    @Test
    public void getLat() throws Exception {
        assertEquals(39.701698303200004, one.getLat(), 0);
    }

    @Test
    public void getLon() throws Exception {
        assertEquals(-104.751998901, one.getLon(), 0);
    }

    @Test
    public void distance() throws Exception {
        assertEquals(10875.0, one.distance(two), 0);
    }

    @Test
    public void getTableIndex() {
        assertEquals(0, one.getTableIndex());
    }

    @Test
    public void setTableIndex() {
        one.setTableIndex(45);
        assertEquals(45, one.getTableIndex());
    }

    @Test
    public void getMunicipality() {
        assertEquals("Aurora", one.getMunicipality());
    }

    @Test
    public void getRegion() {
        assertEquals("Colorado", one.getRegion());
    }

    @Test public void getCountry() {
        assertEquals("United States", one.getCountry());
    }

    @Test
    public void equals() {
        assertEquals(true, one.equals(one));
        assertEquals(false, one.equals(two));
        assertEquals(true, one.equals(equal));
    }
}
