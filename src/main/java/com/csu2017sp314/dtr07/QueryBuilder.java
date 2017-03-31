package com.csu2017sp314.dtr07;/*
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
 */

public class QueryBuilder {
    private final static String myDriver = "com.mysql.jdbc.Driver";
    //private final static String myUrl = "jdbc:mysql://faure.cs.colostate.edu/cs314"; //Original
    private final static String myUrl = "jdbc:mysql://127.0.0.1:3306/cs314"; //Using tunneling
    private final static String count = "SELECT COUNT(1) ";
    private final static String columns = "SELECT airports.id,airports.name,latitude,longitude,municipality,regions.name,countries.name,continents.name ";
    private final static String continents = "FROM continents ";
    private static String where = "";
    private final static String join = "INNER JOIN countries ON countries.continent = continents.id " +
            "INNER JOIN regions ON regions.iso_country = countries.code " +
            "INNER JOIN airports ON airports.iso_region = regions.code ";
    private final static String limit = " LIMIT 10";

    public static void searchDatabase(String type, String continent, String country, String region, String municipality, String name) {
        ArrayList<String> w = new ArrayList<>();
        w.add(type);
        w.add(continent);
        w.add(country);
        w.add(region);
        w.add(municipality);
        w.add(name);
        setWhere(w);
    }

    public static void setWhere(ArrayList<String> wheres) { //TODO remove static
        ArrayList<String> q = new ArrayList<>();
        String type = wheres.get(0);
        String continent = wheres.get(1);
        String country = wheres.get(2);
        String region = wheres.get(3);
        String municipality = wheres.get(4);
        String airportName = wheres.get(5);
        if(!type.equals("")) {
            q.add("type like '%" + type + "%'");
        }
        if(!continent.equals("")) {
            q.add("continents.id like '%" + continent + "%'");
        }
        if(!country.equals("")) {
            q.add("countries.code like '%" + country + "%'");
        }
        if(!region.equals("")) {
            q.add("regions.code like '%" + region + "%'");
        }
        if(!municipality.equals("")) {
            q.add("municipality like '%" + municipality + "%'");
        }
        if(!airportName.equals("")) {
            q.add("airports.name like '%" + airportName + "%'");
        }
        String ret = "";
        for(int i = 0; i < q.size(); i++) {
            if(ret.equals("")) {
                ret = "WHERE ";
            } else {
                ret += " and ";
            }
            ret += q.get(i);
        }
        where = ret;
    }

    public static ResultSet query2() {
        ResultSet rs = null;
        try { // connect to the database
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "sswensen", "830534566");

            try { // create a statement
                Statement st = conn.createStatement();

                try { // submit a query to count the results
                    System.out.println(count + continents + join + where);
                    rs = st.executeQuery(count + continents + join + where);

                    try { // print the number of rows
                        rs.next();
                        int rows = rs.getInt(1);
                        System.out.printf("Selected rows = %d\n", rows);
                    } finally {
                        rs.close();
                    }

                    // submit a query to list all large airports
                    System.out.println(columns + continents + join + where + limit);
                    rs = st.executeQuery(columns + continents + join + where + limit);

                    try { // iterate through query results and print using column numbers
                        System.out.println("id,name,latitude,longitude,municipality,region,country,continent");
                        while(rs.next()) {
                            for(int i = 1; i <= 8; i++)
                                System.out.printf("%s,", rs.getString(i));
                            System.out.printf("%s\n", rs.getString(8));
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
        }
        return rs;
    }

    public static void main(String[] args) {
        searchDatabase("heliport", "AS", "AE", "AE-DU", "Dubai", "Schumacher Heliport");
        query2();
    }
}
