package Model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/*
 * Created by SummitDrift on 2/16/17.
 * Testing file for Location.java
 */
public class TestLocation {
    @Before
    public void initialize() {
    }
    private Location one = new Location(0, "A", "37°28'20\"N","106°47'35\"W");

    @Test
    public void getId() throws Exception {
        assertEquals(0, one.getId());
    }

    @Test
    public void setId() throws Exception {
        one.setId(1);
        assertEquals(1, one.getId());
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
        assertEquals(37.47222222222222, one.getLat(), 0);
    }

    @Test
    public void setLat() throws Exception {
        //one.setLat("40°28'20\" N");
    }

    @Test
    public void getLon() throws Exception {
        assertEquals(-106.79305555555555, one.getLon(), 0);
    }

    @Test
    public void setLon() throws Exception {
        //one.setLat("3106°47'35\" W");
    }
}