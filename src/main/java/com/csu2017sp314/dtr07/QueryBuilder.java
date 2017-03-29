package com.csu2017sp314.dtr07;/*
 * CS314 Sprint 3 Example 2
 * Sample Java code to query database and produce a CSV containing large airports
 * Take two arguments, your eID and eNumber for database access.
 * You will need to obtain the mysql jdbc library
 * Command line
 * javac -cp ".:./com.mysql.jdbc_5.1.5.jar" Sprint3Example2.java
 * java -cp ".:./com.mysql.jdbc_5.1.5.jar" Sprint3Example2 eID password
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * Created by SummitDrift on 3/28/17.
 */

public class QueryBuilder {
    private final static String myDriver = "com.mysql.jdbc.Driver";
    //private final static String myUrl = "jdbc:mysql://faure.cs.colostate.edu/cs314";
    private final static String myUrl = "jdbc:mysql://127.0.0.1:3306/cs314";
    private final static String count = "SELECT COUNT(1) ";
    private final static String columns = "SELECT airports.id,airports.name,latitude,longitude,municipality,regions.name,countries.name,continents.name ";
    private final static String airports = "FROM airports ";
    private final static String continents = "FROM continents ";
    private final static String where = "WHERE type = 'large_airport'";
    private final static String join = "INNER JOIN countries ON countries.continent = continents.id " +
            "INNER JOIN regions ON regions.iso_country = countries.code " +
            "INNER JOIN airports ON airports.iso_region = regions.code ";
    private final static String limit = "LIMIT 1000";

    public static void query1() {
        String myDriver = "com.mysql.jdbc.Driver";
        String myUrl = "jdbc:mysql://127.0.0.1:3306/cs314";

        try { // connect to the database
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "sswensen", "830534566");

            try { // create a statement
                Statement st = conn.createStatement();

                try { // submit a query
                    String query = "SELECT * FROM continents LIMIT 10";
                    ResultSet rs = st.executeQuery(query);

                    try { // iterate through the query results and print
                        while(rs.next()) {
                            String id = rs.getString("Id");
                            String name = rs.getString("name");
                            System.out.printf("%s,%s\n", id, name);
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
    }

    public static ResultSet query2() {
        ResultSet rs = null;
        try { // connect to the database
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(myUrl, "sswensen", "830534566");

            try { // create a statement
                Statement st = conn.createStatement();

                try { // submit a query to count the results
                    rs = st.executeQuery(count + airports + where);

                    try { // print the number of rows
                        rs.next();
                        int rows = rs.getInt(1);
                        System.out.printf("Selected rows = %d\n", rows);
                    } finally {
                        rs.close();
                    }

                    // submit a query to list all large airports
                    rs = st.executeQuery(columns + continents + join + where);

                    try { // iterate through query results and print using column numbers
                        System.out.println("id,name,latitude,longitude,municipality,region,country,continent");
                        while(rs.next()) {
                            for(int i = 1; i <= 7; i++)
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
        query1();
        System.out.println("--------------------");
        query2();
    }
}
