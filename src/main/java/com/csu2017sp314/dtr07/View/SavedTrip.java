package com.csu2017sp314.dtr07.View;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by SummitDrift on 4/8/17.
 */

public class SavedTrip {
    private final String myDriver = "com.mysql.jdbc.Driver";
    //private final static String myUrl = "jdbc:mysql://faure.cs.colostate.edu/cs314"; //Original
    private final String myUrl = "jdbc:mysql://127.0.0.1:3306/cs314"; //Using tunneling
    private final String count = "SELECT COUNT(1) ";
    private final String columns = "SELECT airports.id,airports.name,latitude,longitude,municipality,regions.name,countries.name,continents.name,airports.wikipedia_link,regions.wikipedia_link,countries.wikipedia_link ";
    private final String continents = "FROM continents ";
    private final String join = "INNER JOIN countries ON countries.continent = continents.id "
            + "INNER JOIN regions ON regions.iso_country = countries.code "
            + "INNER JOIN airports ON airports.iso_region = regions.code ";
    private final String limit = " LIMIT 333";
    private String where = "";
    private String name;
    private ArrayList<GUILocation> locations = new ArrayList<>();

    public SavedTrip(SavedTrip t1) {
        name = t1.name;
        locations = new ArrayList<>(t1.getLocations());
    }

    public SavedTrip(String name, ArrayList<String> ids) {
        this.name = name;
        findNamesOrIDs(ids);
    }

    public SavedTrip() {
        this.name = "untitiled";
        locations = new ArrayList<>();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getNames() {
        ArrayList<String> retNames = new ArrayList<>();
        for(int i = 0; i < locations.size(); i++) {
            retNames.add(locations.get(i).getName());
        }
        return retNames;
    }

    public void setNames(ArrayList<String> newNames) {
        locations.clear();
        for(String n : newNames) {
            addName(n);
        }
    }

    public ArrayList<String> getIds() {
        ArrayList<String> retIds = new ArrayList<>();
        //for(GUILocation loc : locations) {
        for(int i = 0; i < locations.size(); i++) {
            retIds.add(locations.get(i).getId());
        }
        return retIds;
    }

    public ArrayList<GUILocation> getLocations() {
        return locations;
    }

    public void setLocations(ArrayList<GUILocation> locations) {
        this.locations = locations;
    }

    public boolean containsName(String name) {
        for(GUILocation n : locations) {
            if(n.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void removeUsingName(String name) {
        for(int i = 0; i < locations.size(); i++) {
            if(name.equals(locations.get(i).getName())) {
                locations.remove(i);
            }
        }
    }

    public int indexOfName(String name) {
        for(int i = 0; i < locations.size(); i++) {
            if(name.equals(locations.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }

    public void addName(String name) {
        where = "WHERE airports.name = \"" + name + "\"";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cs314", "sswensen", "830534566");
            Statement st = conn.createStatement();
            System.out.println(columns + continents + join + where + limit);
            ResultSet rs = st.executeQuery(columns + continents + join + where + limit);

            while(rs.next()) {
                ArrayList<Object> tempLoc = new ArrayList<>();
                tempLoc.add(rs.getString(1));
                tempLoc.add(rs.getString(2));
                tempLoc.add("");
                tempLoc.add("");
                tempLoc.add(rs.getString(5));
                tempLoc.add(rs.getString(6));
                tempLoc.add(rs.getString(7));
                tempLoc.add(rs.getString(8));
                tempLoc.add(rs.getString(9));
                tempLoc.add(rs.getString(10));
                tempLoc.add(rs.getString(11));
                locations.add(new GUILocation(tempLoc));
            }
        } catch(Exception e) {
            System.err.println("Exception: " + e);
            System.err.println("Problem in MapGUI with database");
        }
    }

    private void findNamesOrIDs(ArrayList<String> in) {
        if(in.size() == 0) {
            return;
        }
        String whatYouWant = "name ";
        if(in.get(0).length() < 7) {
            whatYouWant = "id ";
        }
        where = "WHERE airports." + whatYouWant + "in" + "(";
        for(int k = 0; k < in.size(); k++) {
            where += "\"" + in.get(k) + "\", ";
        }
        where += "'" + in.get(in.size()-1) + "')";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cs314", "sswensen", "830534566");
            Statement st = conn.createStatement();
            System.out.println(columns + continents + join + where + limit);
            ResultSet rs = st.executeQuery(columns + continents + join + where + limit);

            while(rs.next()) {
                ArrayList<Object> tempLoc = new ArrayList<>();
                tempLoc.add(rs.getString(1));
                tempLoc.add(rs.getString(2));
                tempLoc.add("");
                tempLoc.add("");
                tempLoc.add(rs.getString(5));
                tempLoc.add(rs.getString(6));
                tempLoc.add(rs.getString(7));
                tempLoc.add(rs.getString(8));
                tempLoc.add(rs.getString(9));
                tempLoc.add(rs.getString(10));
                tempLoc.add(rs.getString(11));
                locations.add(new GUILocation(tempLoc));
            }
        } catch(Exception e) {
            System.err.println("Exception: " + e);
            System.err.println("[SavedTrip] Problem in SavedTrip with database");
        }
    }

    @Override
    public String toString() {
        return "SavedTrip{" +
                "name='" + name + '\'' +
                "number of locations: " + locations.size() +
                '}';
    }
}
