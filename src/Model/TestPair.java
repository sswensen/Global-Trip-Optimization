package Model;

import org.junit.Test;

import static org.junit.Assert.*;

/*
 * Created by SummitDrift on 2/21/17.
 * Testing file for Pair
 */
public class TestPair {
    Pair p = new Pair(0, new Location(0, "A", "37°28'20\"N","106°47'35\"W"), new Location(1, "B", "37°28'20\"N","106°47'35\"W"), 777);
    @Test
    public void getOne() throws Exception {
        Location one = new Location(0, "A", "37°28'20\"N","106°47'35\"W");
        assertEquals(one, p.getOne());
    }

    @Test
    public void getTwo() throws Exception {
        Location two = new Location(1, "B", "37°28'20\"N","106°47'35\"W");
        assertEquals(two, p.getTwo());
    }

    @Test
    public void getDistance() throws Exception {
        assertEquals(777, p.getDistance(), 0);
    }

    @Test
    public void getId() throws Exception {
        assertEquals(0, p.getId());
    }

    @Test
    public void setId() throws Exception {
        p.setId(970);
        assertEquals(970, p.getId());
    }

}