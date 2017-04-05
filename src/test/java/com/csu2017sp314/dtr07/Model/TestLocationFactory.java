package com.csu2017sp314.dtr07.Model;

/*
 * Created by SummitDrift on 2/13/17.
 * Testing file for LocationFactory.java
 */

import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestLocationFactory {
    @Before
    public void initialize() {
    }

    private LocationFactory lf = new LocationFactory();

    @Test
    public void readTest() {
        LocationFactory lf = new LocationFactory();
        try {
            lf.readFile("./src/test/resources/Testing/ColoradoSkiResorts.csv");
            //lf.findNearest();
            lf.thirdTry();
            ArrayList<Location> result = lf.getLocations();
            //ArrayList<Pair> pairs = lf.getPairs();
            Location one = new Location("1","Wolf Creek", "37°28'20\" N", "106°47'35\" W");
            Assert.assertTrue(one.equals(result.get(0)));
        } catch (FileNotFoundException a) {
            System.out.println("Error eccountered: " + a);
        }
    }

    @Test
    public void setTotalImprovements() {
        lf.setTotalImprovements(20);
        Assert.assertEquals(20, lf.getTotalImprovements());
    }

    @Test
    public void setTwoOpt() {
        lf.setTwoOpt(true);
        Assert.assertEquals(true, lf.getTwoOpt());
    }

    @Test
    public void setThreeOpt() {
        lf.setThreeOpt(true);
        Assert.assertEquals(true, lf.getThreeOpt());
    }
}