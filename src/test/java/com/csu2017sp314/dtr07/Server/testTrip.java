package com.csu2017sp314.dtr07.Server;

import com.csu2017sp314.dtr07.Model.Location;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Sandeep on 5/1/2017.
 */
public class testTrip {
    @Test
    public void getName() throws Exception {
        String name = "fuc";
        double dis = 1000;
        ArrayList<String> locations = new ArrayList<>();
        locations.add("1");
        locations.add("2");
        locations.add("3");
        locations.add("4");
        locations.add("5");
        Trip trip = new Trip(name, dis, locations);
        assertEquals("fuc", trip.getName());
    }

    @Test
    public void getTotalDistance() throws Exception {
        String name = "fuc";
        double distance = 1000;
        ArrayList<String> locations = new ArrayList<>();
        locations.add("3");
        locations.add("4");
        locations.add("5");
        locations.add("1");
        locations.add("2");
        Trip trip = new Trip(name, distance, locations);
        double dis = trip.getTotalDistance();
        assertEquals(distance, dis, 0);
    }

    @Test
    public void setTotalDistance() throws Exception {
        String name = "fuc";
        double distance = 1000;
        ArrayList<String> locations = new ArrayList<>();
        locations.add("4");
        locations.add("5");
        locations.add("1");
        locations.add("3");
        locations.add("2");
        Trip trip = new Trip(name, distance, locations);
        trip.setTotalDistance(1200);
        double dis = trip.getTotalDistance();
        assertEquals(1200, dis, 0);
    }

    @Test
    public void getSelectedIds() throws Exception {
        String name = "fuc";
        double distance = 1000;
        ArrayList<String> locations = new ArrayList<>();
        locations.add("4");
        locations.add("5");
        locations.add("3");
        locations.add("2");
        locations.add("1");
        Trip trip = new Trip(name, distance, locations);
        trip.setTotalDistance(1200);
        assertEquals(locations, trip.getSelectedIds());
    }

}