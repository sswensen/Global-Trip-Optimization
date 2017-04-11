package com.csu2017sp314.dtr07.View;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

/**
 * Created by SummitDrift on 4/10/17.
 */

public class TestSavedTrip {
    private SavedTrip trip = new SavedTrip();

    @Before
    public void initialize() throws Exception {
        ArrayList<Object> obj1 = new ArrayList<>();
        ArrayList<Object> obj2 = new ArrayList<>();
        obj1.add("KBKF");
        obj2.add("KEGE");
        obj1.add("Buckley Air Force Base");
        obj2.add("Eagle County Regional Airport");
        obj1.add("39.701698303200004");
        obj1.add("-104.751998901");
        obj2.add("39.64260101");
        obj2.add("-106.9179993");
        obj1.add("Aurora");
        obj2.add("Eagle");
        obj1.add("Colorado");
        obj2.add("Colorado");
        obj1.add("United States");
        obj2.add("United States");
        obj1.add("http://en.wikipedia.org/wiki/Buckley_Air_Force_Base");
        obj2.add("http://en.wikipedia.org/wiki/Eagle_County_Regional_Airport");
        obj1.add("http://en.wikipedia.org/wiki/Colorado");
        obj2.add("http://en.wikipedia.org/wiki/Colorado");
        obj1.add("http://en.wikipedia.org/wiki/United_States");
        obj2.add("http://en.wikipedia.org/wiki/United_States");
        ArrayList<GUILocation> locations = new ArrayList<>();
        locations.add(new GUILocation(obj1));
        locations.add(new GUILocation(obj2));
        trip.setLocations(locations);
    }

    @Test
    public void getName() throws Exception {
        trip.setName("E");
        assertEquals("E", trip.getName());
    }

    @Test
    public void setName() throws Exception {
        trip.setName("F");
        assertEquals("F", trip.getName());
    }

    @Test
    public void getNames() throws Exception {
        assertEquals("Buckley Air Force Base", trip.getNames().get(0));
    }

    @Test
    public void getIds() throws Exception {
        assertEquals("KBKF", trip.getIds().get(0));
    }

    @Test
    public void getLocations() throws Exception {

    }

    @Test
    public void setLocations() throws Exception {
    }

    @Test
    public void containsName() throws Exception {
    }

    @Test
    public void removeUsingName() throws Exception {
    }

    @Test
    public void indexOfName() throws Exception {
    }

    @Test
    public void addName() throws Exception {
    }

}