package com.csu2017sp314.dtr07.Model;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by SummitDrift on 5/1/17.
 */
public class TestQueryBuilder {
    @Test
    public void fireSearchQuery() throws Exception {
        QueryBuilder qb = new QueryBuilder(false);
        ArrayList<Location> tempLocations = new ArrayList<>();
        tempLocations.add(new Location("KBKF", "Buckley Air Force Base",
                "39.701698303200004", "-104.751998901", "Aurora",
                "Colorado", "United States", "North America",
                "http://en.wikipedia.org/wiki/Buckley_Air_Force_Base",
                "http://en.wikipedia.org/wiki/Colorado",
                "http://en.wikipedia.org/wiki/United_States"));
        tempLocations.add(new Location("KEGE", "Eagle County Regional Airport",
                "39.64260101", "-106.9179993", "Eagle",
                "Colorado", "United States", "North America",
                "http://en.wikipedia.org/wiki/Eagle_County_Regional_Airport",
                "http://en.wikipedia.org/wiki/Colorado",
                "http://en.wikipedia.org/wiki/United_States"));
    }

    @Test
    public void fireQuery() throws Exception {
        QueryBuilder qb = new QueryBuilder(false);

    }

    @Test
    public void getLocations() throws Exception {
        QueryBuilder qb = new QueryBuilder(false);

    }

}