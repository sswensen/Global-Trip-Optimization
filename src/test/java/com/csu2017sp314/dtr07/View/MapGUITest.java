package com.csu2017sp314.dtr07.View;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by SummitDrift on 3/20/17.
 */
public class MapGUITest {
    @Before
    public void initialize() {
    }

    MapGUI g = new MapGUI();

    @Test
    public void init() throws Exception {
        assertEquals(1, g.init("brews.csv"));
    }

    @Test
    public void displayXML() throws Exception {
        ArrayList<String> a = new ArrayList<>();
        a.add("HAY");
        assertEquals(1, g.displayXML(a));
    }

    @Test
    public void addLegToItinerary() throws Exception {
        assertEquals(1, g.addLegToItinerary("1", "a", "b", 100));
    }
}