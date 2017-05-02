package com.csu2017sp314.dtr07.Model;

/*
 * CS314 Sprint 3 Example 2
 * Sample Java code to query database and produce a CSV containing large airports
 * Take two arguments, your eID and eNumber for database access.
 * You will need to obtain the mysql jdbc library
 * Command line
 * javac -cp ".:./com.mysql.jdbc_5.1.5.jar" Sprint3Example2.java
 * java -cp ".:./com.mysql.jdbc_5.1.5.jar" Sprint3Example2 eID password
 */

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by SummitDrift on 3/28/17.
 * File for creating SQL queries to access the database
 *
 * @author SummitDrift
 */

public class QueryBuilder {
    private final String myDriver = "com.mysql.jdbc.Driver";
    //private final static String myUrl = "jdbc:mysql://faure.cs.colostate.edu/cs314"; //Original
    private final String myUrl = "jdbc:mysql://127.0.0.1:3306/cs314"; //Using tunneling
    private final String count = "SELECT COUNT(1) ";
    private final String columns = "SELECT airports.id,airports.name,latitude,longitude,municipality,"
            + "regions.name,countries.name,continents.name,airports.wikipedia_link,regions.wikipedia_link,"
            + "countries.wikipedia_link ";
    private final String continents = "FROM continents ";
    private final String join = "INNER JOIN countries ON countries.continent = continents.id "
            + "INNER JOIN regions ON regions.iso_country = countries.code "
            + "INNER JOIN airports ON airports.iso_region = regions.code ";
    String anotherbigstring = "SELECT airports.id,\n" +
            "  airports.name,\n" +
            "  latitude,\n" +
            "  longitude,\n" +
            "  municipality,\n" +
            "  regions.name,\n" +
            "  countries.name,\n" +
            "  continents.name,\n" +
            "  airports.wikipedia_link,\n" +
            "  regions.wikipedia_link,\n" +
            "  countries.wikipedia_link FROM continents INNER JOIN countries ON countries.continent = continents.id\n" +
            "  INNER JOIN regions ON countries.code = regions.iso_country\n" +
            "  INNER JOIN airports ON airports.iso_region =regions.code\n" +
            "WHERE airports.id IN ";
    private String limit = "";
    private boolean useDatabase;
    private String where = "";
    private int numberReturnedFromDatabase = 0;
    private ArrayList<Location> locations = new ArrayList<>();
    private String bigstring = "(SELECT\n" +
            "  airports.id,\n" +
            "  airports.name,\n" +
            "  latitude,\n" +
            "  longitude,\n" +
            "  municipality,\n" +
            "  regions.name,\n" +
            "  countries.name,\n" +
            "  continents.name,\n" +
            "  airports.wikipedia_link,\n" +
            "  regions.wikipedia_link,\n" +
            "  countries.wikipedia_link\n" +
            "FROM continents\n" +
            "  INNER JOIN countries ON countries.continent = continents.id\n" +
            "  INNER JOIN regions ON regions.iso_country = countries.code\n" +
            "  INNER JOIN airports ON airports.iso_region = regions.code\n" +
            "WHERE MATCH (airports.name) AGAINST (?)\n" +
            "LIMIT 50)\n" +
            "UNION\n" +
            "(SELECT\n" +
            "  airports.id,\n" +
            "  airports.name,\n" +
            "  latitude,\n" +
            "  longitude,\n" +
            "  municipality,\n" +
            "  regions.name,\n" +
            "  countries.name,\n" +
            "  continents.name,\n" +
            "  airports.wikipedia_link,\n" +
            "  regions.wikipedia_link,\n" +
            "  countries.wikipedia_link\n" +
            "FROM continents\n" +
            "  INNER JOIN countries ON countries.continent = continents.id\n" +
            "  INNER JOIN regions ON regions.iso_country = countries.code\n" +
            "  INNER JOIN airports ON airports.iso_region = regions.code\n" +
            "WHERE MATCH (airports.municipality) AGAINST (?)\n" +
            "LIMIT 50)\n" +
            "UNION\n" +
            "(SELECT\n" +
            "   airports.id,\n" +
            "   airports.name,\n" +
            "   latitude,\n" +
            "   longitude,\n" +
            "   municipality,\n" +
            "   regions.name,\n" +
            "   countries.name,\n" +
            "   continents.name,\n" +
            "   airports.wikipedia_link,\n" +
            "   regions.wikipedia_link,\n" +
            "   countries.wikipedia_link\n" +
            " FROM continents\n" +
            "   INNER JOIN countries ON countries.continent = continents.id\n" +
            "   INNER JOIN regions ON regions.iso_country = countries.code\n" +
            "   INNER JOIN airports ON airports.iso_region = regions.code\n" +
            " WHERE MATCH (regions.name) AGAINST (?)\n" +
            " LIMIT 50)\n" +
            "UNION\n" +
            "(SELECT\n" +
            "   airports.id,\n" +
            "   airports.name,\n" +
            "   latitude,\n" +
            "   longitude,\n" +
            "   municipality,\n" +
            "   regions.name,\n" +
            "   countries.name,\n" +
            "   continents.name,\n" +
            "   airports.wikipedia_link,\n" +
            "   regions.wikipedia_link,\n" +
            "   countries.wikipedia_link\n" +
            " FROM continents\n" +
            "   INNER JOIN countries ON countries.continent = continents.id\n" +
            "   INNER JOIN regions ON regions.iso_country = countries.code\n" +
            "   INNER JOIN airports ON airports.iso_region = regions.code\n" +
            " WHERE MATCH (countries.name) AGAINST (?)\n" +
            " LIMIT 50)";
    //TODO should probably be converted to an array, especially if there are hundreds of locations returning.

    public QueryBuilder(boolean useDB) {
        useDatabase = useDB;
    }

    public ArrayList<Location> fireSearchQuery(String id) {
        ArrayList<Location> tempLocations = new ArrayList<>();
        if(useDatabase) {
            ResultSet rs = null;
            try { // connect to the database
                Class.forName(myDriver);
                Connection conn = DriverManager.getConnection(myUrl, "sswensen", "830534566");

                try { // create a statement
                    Statement st = conn.createStatement();

                    try { // submit a query to count the results
                        System.out.println("Querying individual with \"" + id + "\"");
                        rs = st.executeQuery(anotherbigstring + "(\"" + id + "\")");

                        try { // iterate through query results and print using column numbers
                            while(rs.next()) {
                                tempLocations.add(new Location(rs.getString(1),
                                        rs.getString(2), rs.getString(3),
                                        rs.getString(4), rs.getString(5),
                                        rs.getString(6), rs.getString(7),
                                        rs.getString(8), rs.getString(9),
                                        rs.getString(10), rs.getString(11)));
                            }
                        } finally {
                            rs.close();
                        }
                    } finally {
                        st.close();
                    }
                } finally {
                    conn.close();
                }
            } catch(Exception e) {
                System.err.printf("Exception: ");
                System.err.println(e.getMessage());
                System.err.println("-------------EXITING!!!------------");
                System.exit(33); //Something broke in the database :/
            }
        } else {
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

        return tempLocations;
    }

    public void fireQuery(String query) {
        if(useDatabase) {
            ResultSet rs = null;
            try { // connect to the database
                Class.forName(myDriver);
                Connection conn = DriverManager.getConnection(myUrl, "sswensen", "830534566");

                try { // create a statement
                    PreparedStatement st = conn.prepareStatement(bigstring);
                    for(int i = 1; i < 5; i++) {
                        st.setString(i, query);
                    }

                    try { // submit a query to count the results
                        System.out.println("Querying with \"" + query + "\"");
                        rs = st.executeQuery();

                        try { // iterate through query results and print using column numbers
                            while(rs.next()) {
                                locations.add(new Location(rs.getString(1),
                                        rs.getString(2), rs.getString(3),
                                        rs.getString(4), rs.getString(5),
                                        rs.getString(6), rs.getString(7),
                                        rs.getString(8), rs.getString(9),
                                        rs.getString(10), rs.getString(11)));
                            }
                        } finally {
                            rs.close();
                        }
                    } finally {
                        st.close();
                    }
                } finally {
                    conn.close();
                }
            } catch(Exception e) {
                System.err.printf("Exception: ");
                System.err.println(e.getMessage());
                System.err.println("-------------EXITING!!!------------");
                System.exit(33); //Something broke in the database :/
            }
        } else {
            locations.add(new Location("KBKF", "Buckley Air Force Base",
                    "39.701698303200004", "-104.751998901", "Aurora",
                    "Colorado", "United States", "North America",
                    "http://en.wikipedia.org/wiki/Buckley_Air_Force_Base",
                    "http://en.wikipedia.org/wiki/Colorado",
                    "http://en.wikipedia.org/wiki/United_States"));
            locations.add(new Location("KEGE", "Eagle County Regional Airport",
                    "39.64260101", "-106.9179993", "Eagle",
                    "Colorado", "United States", "North America",
                    "http://en.wikipedia.org/wiki/Eagle_County_Regional_Airport",
                    "http://en.wikipedia.org/wiki/Colorado",
                    "http://en.wikipedia.org/wiki/United_States"));
        }
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }
}
