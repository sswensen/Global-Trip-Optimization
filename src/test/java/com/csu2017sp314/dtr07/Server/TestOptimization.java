package com.csu2017sp314.dtr07.Server;

import com.csu2017sp314.dtr07.Model.Location;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jesseL1on on 5/1/2017.
 */
public class TestOptimization {
    @Before
    public void initialize() {

    }

    Location one = (new Location("0", "A", "39.701698303200004", "-104.751998901", "Aurora", "Colorado", "United States", "North America", "http://en.wikipedia.org/wiki/Buckley_Air_Force_Base", "http://en.wikipedia.org/wiki/Colorado", "http://en.wikipedia.org/wiki/United_States"));
    Location two = (new Location("1", "B", "-39.701698303200004", "104.751998901", "Fort Collins", "Colorado", "United States", "North America", "http://en.wikipedia.org/wiki/Buckley_Air_Force_Base", "http://en.wikipedia.org/wiki/Colorado", "http://en.wikipedia.org/wiki/United_States"));
    Location[] locs = {one, two};
    Optimization opt = new Optimization(locs, "0");

    @Test
    public void getTwoOpt() {
        assertEquals(false, opt.getTwoOpt());
    }

    @Test
    public void setTwoOpt() {
        opt.setTwoOpt(true);
        assertEquals(true, opt.getTwoOpt());
    }

    @Test
    public void getThreeOpt() {
        assertEquals(false, opt.getThreeOpt());
    }

    @Test
    public void setThreeOpt() {
        opt.setThreeOpt(true);
        assertEquals(true, opt.getThreeOpt());
    }

    @Test
    public void setOpts() {
        opt.setOpts(false, false);
        assertEquals(false, opt.getTwoOpt());
        assertEquals(false, opt.getThreeOpt());
        opt.setOpts(false, true);
        assertEquals(false, opt.getTwoOpt());
        assertEquals(true, opt.getThreeOpt());
        opt.setOpts(true, false);
        assertEquals(true, opt.getTwoOpt());
        assertEquals(false, opt.getThreeOpt());
        opt.setOpts(true, true);
        assertEquals(true, opt.getTwoOpt());
        assertEquals(true, opt.getThreeOpt());
    }

    @Test
    public void getOptimizedRoute() {
        Location[] route = opt.getOptimizedRoute();
        assertEquals(locs[0], route[0]);
        assertEquals(locs[1], route[1]);
    }

    @Test
    public void getTripDistance() {
        opt.getOptimizedRoute();
        assertEquals(21750.0, opt.getTripDistance(), 0);
    }
}
