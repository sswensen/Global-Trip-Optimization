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
    private String limit = "";
    private boolean useDatabase;
    private String where = "";
    private int numberReturnedFromDatabase = 0;
    private ArrayList<Location> locations = new ArrayList<>();
    private String bigassfuckingstring = "(SELECT\n" +
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

    public void searchDatabase(String type, String continent, String country,
                        String region, String municipality, String name) {
        ArrayList<String> w = new ArrayList<>();
        w.add(type);
        w.add(continent);
        w.add(country);
        w.add(region);
        w.add(municipality);
        w.add(name);
        //setWhere(w);
    }

    public void search4IDinDatabase(ArrayList<String> ids, String idOrName) {
        if(!ids.isEmpty()) {
            String w = "WHERE airports." + idOrName + " in (";
            for(int i = 0; i < ids.size() - 1; i++) {
                /*if(!ids.get(i).contains("'")) {
                    w += "'" + ids.get(i) + "', "; //TODO replace this with StringBuilder.append
                } else {
                    String[] splitonsinglequote = ids.get(i).split("'");
                    w += "'";
                    for(int j = 0; j < splitonsinglequote.length; j++) {
                        if(j != 0) {
                            w += "'" + splitonsinglequote[j];
                        } else {
                            w += splitonsinglequote[j];
                        }
                    }
                    w += "', ";
                }*/
                w += "\"" + ids.get(i) + "\", ";
            }
            w += "\"" + ids.get(ids.size() - 1) + "\")";
            where = w;
        } else {
            where = "";
        }
    }

    //TODO add function for changing limit, this is very optional as we will lkely limit to 500

    //TODO add function that returns the number of items found, actually this can be accomplished
    // by calling the size function on the ids returned to the db selection window

    private void setWhere(String where) {
        this.where = where;
    }

    public void fireQuery() {
        this.fireQuery("Denver");
    }

    public void fireQuery(String query) { //String whatDoYouWantBack) {
        /*boolean makeLocationsQuestionMark = false;
        if(whatDoYouWantBack.equals("locations")) {
            makeLocationsQuestionMark = true;
        }*/
        if(useDatabase) {
            ResultSet rs = null;
            try { // connect to the database
                Class.forName(myDriver);
                Connection conn = DriverManager.getConnection(myUrl, "sswensen", "830534566");

                try { // create a statement
                    PreparedStatement st = conn.prepareStatement(bigassfuckingstring);
                    for(int i = 1; i < 5; i++) {
                        st.setString(i, query);
                    }

                    try { // submit a query to count the results
                        //System.out.println(columns + continents + join + where + limit);
                        //rs = st.executeQuery(columns + continents + join + where + limit);

                        //System.out.println(st);
                        System.out.println("Querying with \"" + query + "\"");
                        rs = st.executeQuery();

                        try { // iterate through query results and print using column numbers
                            //System.out.println("id,name,latitude,longitude,municipality,region,country,continent");
                            while(rs.next()) {
                            /*for(int i = 1; i <= 7; i++)
                                System.out.printf("%s,", rs.getString(i));
                            System.out.printf("%s\n", rs.getString(8));*/
                                //System.out.println("Creating location with id [" + rs.getString(1) + "]");
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

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public int getNumberReturnedFromDatabase() {
        return numberReturnedFromDatabase;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public ArrayList<String> getLocationNames() { //Highly ineffient, see beginning of fireQuery
        ArrayList<String> ret = new ArrayList<>();
        for(Location loc : locations) {
            ret.add(loc.getName());
        }
        return ret;
    }
}
