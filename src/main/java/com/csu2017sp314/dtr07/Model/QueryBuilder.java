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

class QueryBuilder {
    private final String myDriver = "com.mysql.jdbc.Driver";
    //private final static String myUrl = "jdbc:mysql://faure.cs.colostate.edu/cs314"; //Original
    private final String myUrl = "jdbc:mysql://127.0.0.1:3306/cs314"; //Using tunneling
    private final String count = "SELECT COUNT(1) ";
    private final String columns = "SELECT airports.id,airports.name,latitude,longitude,municipality,regions.name,countries.name,continents.name ";
    private final String continents = "FROM continents ";
    private String where = "";
    private final String join = "INNER JOIN countries ON countries.continent = continents.id "
            + "INNER JOIN regions ON regions.iso_country = countries.code "
            + "INNER JOIN airports ON airports.iso_region = regions.code ";
    private final String limit = " LIMIT 100";
    private ArrayList<Location> locations = new ArrayList<>(); //TODO should probably be converted to an array, especially if there are hundreds of locations returning.

    void searchDatabase(String type, String continent, String country, String region, String municipality, String name) {
        ArrayList<String> w = new ArrayList<>();
        w.add(type);
        w.add(continent);
        w.add(country);
        w.add(region);
        w.add(municipality);
        w.add(name);
        setWhere(w);
    }

    void search4IDinDatabase(ArrayList<String> ids) {
        String w = "WHERE airports.id in (";
        for(int i = 0; i < ids.size() - 1; i++) {
            w += "'" + ids.get(i) + "', "; //TODO replace this with StringBuilder.append
        }
        w += "'" + ids.get(ids.size() - 1) + "')";
        where = w;
    }

    //TODO add function for changing limit, this is very optional as we will lkely limit to 500

    //TODO add function that returns the number of items found, actually this can be accomplished by calling the size function on the ids returned to the db selection window

    private void setWhere(ArrayList<String> wheres) {
        ArrayList<String> q = new ArrayList<>();
        String type = wheres.get(0);
        if(!type.equals("")) {
            q.add("type like '%" + type + "%'");
        }
        String continent = wheres.get(1);
        if(!continent.equals("")) {
            q.add("continents.id like '%" + continent + "%'");
        }
        String country = wheres.get(2);
        if(!country.equals("")) {
            q.add("countries.code like '%" + country + "%'");
        }
        String region = wheres.get(3);
        if(!region.equals("")) {
            q.add("regions.code like '%" + region + "%'");
        }
        String municipality = wheres.get(4);
        if(!municipality.equals("")) {
            q.add("municipality like '%" + municipality + "%'");
        }
        String airportName = wheres.get(5);
        if(!airportName.equals("")) {
            q.add("airports.name like '%" + airportName + "%'");
        }
        String ret = "";
        for(int i = 0; i < q.size(); i++) {
            if(ret.equals("")) {
                ret = "WHERE ";
            } else {
                ret += " and "; //TODO replace with StringBuilder.append
            }
            ret += q.get(i);
        }
        where = ret;
    }

    ResultSet fireQuery() { //String whatDoYouWantBack) { //if "locations" is passed in as parameter, make locations with db stuff, else just return the names
        /*boolean makeLocationsQuestionMark = false;
        if(whatDoYouWantBack.equals("locations")) {
            makeLocationsQuestionMark = true;
        }*/
        ResultSet rs = null;
        try { // connect to the database
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "chundrus", "830424750");

            try { // create a statement
                Statement st = conn.createStatement();

                try { // submit a query to count the results
                    //System.out.println(count + continents + join + where);
                    rs = st.executeQuery(count + continents + join + where);

                    try { // print the number of rows
                        rs.next();
                        int rows = rs.getInt(1);
                        System.out.printf("[QueryBuilder] Selected rows = %d\n", rows);
                    } finally {
                        rs.close();
                    }

                    // submit a query to list all large airports
                    //System.out.println(columns + continents + join + where + limit);
                    rs = st.executeQuery(columns + continents + join + where + limit);

                    try { // iterate through query results and print using column numbers
                        //System.out.println("id,name,latitude,longitude,municipality,region,country,continent");
                        while(rs.next()) {
                            /*for(int i = 1; i <= 7; i++)
                                System.out.printf("%s,", rs.getString(i));
                            System.out.printf("%s\n", rs.getString(8));*/
                            //System.out.println("Creating location with id [" + rs.getString(1) + "]");
                            locations.add(new Location(rs.getString(1), rs.getString(2), rs.getString(3),
                                    rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
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
        return rs;
    }

    ArrayList<Location> getLocations() {
        return locations;
    }

    ArrayList<String> getLocationNames() { //Highly ineffient, see todo at beginning of fireQuery
        ArrayList<String> ret = new ArrayList<>();
        for(Location loc : locations) {
            ret.add(loc.getName());
        }
        return ret;
    }
}