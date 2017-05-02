package com.csu2017sp314.dtr07.Server;

import com.csu2017sp314.dtr07.Model.Location;
import com.csu2017sp314.dtr07.Model.QueryBuilder;
import com.google.gson.Gson;
import org.eclipse.jetty.util.ArrayUtil;
import org.eclipse.jetty.util.IO;
import spark.Request;
import spark.Response;
import sun.util.resources.cldr.id.LocaleNames_id;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static spark.Spark.*;

/**
 * Created by SummitDrift on 4/20/17.
 */

public class Server {
    private ArrayList<Trip> trips = new ArrayList<>();
    private double tripDistance;
    private ArrayList<Location> selectedLocations = new ArrayList<>();

    public static void main(String[] args) {
        Server s = new Server();
        s.serve();
    }

    public void serve() {
        ArrayList<String> temp = new ArrayList<>();
        temp.add("KCOS");
        temp.add("MRBC");
        temp.add("0CD1");
        trips.add(new Trip("Colorado", 50, temp));
        Gson g = new Gson();
        get("/locations", this::hello, g::toJson);
        get("/toOptimize", this::optimize, g::toJson);
        get("/saveTrips", this::saveTrip, g::toJson);
        get("/getTrips", this::getTrip, g::toJson);
        get("/getDistance", this::getDistance, g::toJson);
        get("/setSelectedIndividual", this::selectIndividual, g::toJson);
        get("/fireOpt", this::fireOpt, g::toJson);
        get("/database", this::searchDatabase, g::toJson);
    }

    private Object searchDatabase(Request rec, Response res) {
        setHeaders(res);
        Gson gson = new Gson();
        String id = rec.queryParams("id");
        QueryBuilder qb = new QueryBuilder(true);
        id = id.replace(",", "\",\"");
        return qb.fireSearchQuery(id);
    }

    public Object selectIndividual(Request rec, Response res) {
        setHeaders(res);
        Gson gson = new Gson();
        String i = rec.queryParams("locs");
        i = i.replace("[", "");
        i = i.replace("]", "");
        String[] jsonStrings = i.split("}");
        jsonStrings[0] += "}";
        for(int j = 1; j < jsonStrings.length; j++) {
            StringBuilder sb = new StringBuilder(jsonStrings[j]);
            sb.deleteCharAt(0);
            sb.append("}");
            jsonStrings[j] = sb.toString();
        }
        Location[] locations2 = new Location[jsonStrings.length];
        //ArrayList<Location> locations = new ArrayList<>();
        for(int k = 0; k < jsonStrings.length; k++) {
            Location loc = gson.fromJson(jsonStrings[k], Location.class);
            //locations.add(loc);
            locations2[k] = loc;
            //System.out.println("Location " + k + " " + loc.toString());
            Location tempL = locations2[k];
            selectedLocations.add(tempL);
        }

        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(jsonStrings.length);
        return temp;
    }

    public Object fireOpt(Request rec, Response res) {
        setHeaders(res);
        String opt = rec.queryParams("opt");
        Location[] locations2 = selectedLocations.toArray(new Location[selectedLocations.size()]);
        System.out.println("running individual opt now");
        Optimization optimiziation = new Optimization(locations2, opt);
        locations2 = optimiziation.getOptimizedRoute();
        System.out.println("complete");
        tripDistance = optimiziation.getTripDistance();
        selectedLocations.clear();
        //TODO remove last locations from locations2
        System.out.println("individual opt done returning " + locations2.length + " locations");
        Location[] newLocations = ArrayUtil.removeFromArray(locations2, locations2[locations2.length - 1]);
        return newLocations;
    }

    public Object hello(Request rec, Response res) {
        setHeaders(res);
        QueryBuilder q = new QueryBuilder(true);
        //System.out.println(rec.queryParams("q"));
        q.fireQuery(rec.queryParams("q"));
        return q.getLocations();
    }

    public Object optimize(Request rec, Response res) {
        setHeaders(res);
        Gson gson = new Gson();
        String i = rec.queryParams("locs");
        String opt = rec.queryParams("opt");
        System.out.println("Opt is " + opt + "\n");
        i = i.replace("[", "");
        i = i.replace("]", "");
        String[] jsonStrings = i.split("}");
        jsonStrings[0] += "}";
        for(int j = 1; j < jsonStrings.length; j++) {
            StringBuilder sb = new StringBuilder(jsonStrings[j]);
            sb.deleteCharAt(0);
            sb.append("}");
            jsonStrings[j] = sb.toString();
        }
        Location[] locations2 = new Location[jsonStrings.length];
        //ArrayList<Location> locations = new ArrayList<>();
        for(int k = 0; k < jsonStrings.length; k++) {
            Location loc = gson.fromJson(jsonStrings[k], Location.class);
            //locations.add(loc);
            locations2[k] = loc;
            //System.out.println("Location " + k + " " + loc.toString());
        }
        /*Location temp = locations.remove(0);
        locations.add(temp);*/
        System.out.println("running opt now");
        Optimization optimiziation = new Optimization(locations2, opt);
        locations2 = optimiziation.getOptimizedRoute();
        System.out.println("complete");
        tripDistance = optimiziation.getTripDistance();
        //TODO remove last locations from locations2
        Location[] newLocations = ArrayUtil.removeFromArray(locations2, locations2[locations2.length - 1]);
        return newLocations;
    }

    public Object saveTrip(Request rec, Response res) {
        setHeaders(res);
        Gson gson = new Gson();
        String locs = rec.queryParams("trips");
        //System.out.println("we are here " + locs);
        locs = locs.substring(1, locs.length() - 1); // --> "ello World"
        System.out.println(locs);
        String index = ",\\{\"name";
        //int splitIndex = locs.indexOf(index);
        //System.out.println(locs.indexOf(index));
        //System.out.println(locs.substring(0,splitIndex));
        //System.out.println(locs.substring(splitIndex));
        String[] jsonStrings = locs.split(index);
        System.out.println(jsonStrings.length);
        for(int i = 1; i < jsonStrings.length;i++){
            jsonStrings[i] = index + jsonStrings[i];
            StringBuilder sb = new StringBuilder(jsonStrings[i]);
            sb.deleteCharAt(0);
            sb.deleteCharAt(0);
            jsonStrings[i]= sb.toString();
        }
        for(int i = 0; i < jsonStrings.length;i++){
            System.out.println(jsonStrings[i]);
        }
        //ArrayList<Trip> newTrips = new ArrayList<>();
        Trip trip = gson.fromJson(jsonStrings[0], Trip.class);
        int ret = searchSavedTrips(trip.getName());
        if(ret < 0) {
            //newTrips.add(trip); //Dont need this
            trips.add(trip);
        } else {
            trips.remove(ret);
            trips.add(ret, trip);
        }
        //Make KML file here
        ArrayList<Location> locations = getAllLocationsFromDatabase(trip.getSelectedIds());
        createKMLFile(locations, trip.getName());
        System.out.println("Trip " + trip.toString());


        return trips;
    }

    public void createKMLFile(ArrayList<Location> locations, String tripName) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(tripName + ".kml");
            bw = new BufferedWriter(fw);
            String kmlStart =
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n";
            String documentStart = "  <Document>\n";
            bw.write(kmlStart);
            bw.write(documentStart);
            String coordinates = "";
            for(int i = 0; i < locations.size();i++){
                String kmlElement =
                        "    <Placemark>\n" +
                        "      <name>" + locations.get(i).getName() + "</name>\n" +
                        "      <description>" + locations.get(i).getMunicipality() + "</description>\n" +
                        "      <Point>\n" +
                        "        <coordinates>" + locations.get(i).getLon() + "," + locations.get(i).getLat() + "," + 0 + "</coordinates>\n" +
                        "      </Point>\n" +
                        "    </Placemark>\n";
                bw.write(kmlElement);
                coordinates += locations.get(i).getLon() + ", " + locations.get(i).getLat() + ", " + 0 + ".\n" + "          ";
            }
            coordinates  = coordinates.trim();
            String lineString =
                "    <Placemark>\n" +
                "      <LineString>\n" +
                "        <coordinates>\n" +
                "          " + coordinates + "\n" +
                "        </coordinates>\n"+
                "      </LineString>\n" +
                "    </Placemark>\n";
            bw.write(lineString);
            String documentEnd = "  </Document>\n";
            String kmlend = "</kml>";
            bw.write(documentEnd);
            bw.write(kmlend);
            System.out.println("Done");
        } catch(IOException io) {
            io.printStackTrace();
        } finally {
            try {
                if(bw != null)
                    bw.close();
                if(fw != null)
                    fw.close();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public Object getTrip(Request rec, Response res) {
        setHeaders(res);
        System.out.println("Returning saved trips");
        String locs = rec.queryParams("num");
        //trips.add(new Trip("e", 666.666, new ArrayList<>()));
        return trips;
    }

    public Object getDistance(Request rec, Response res) {
        setHeaders(res);
        String tf = rec.queryParams("dist");
        ArrayList<Double> temp = new ArrayList<>();
        temp.add(tripDistance);
        return temp;
    }

    //TODO remove this shit
    public Location[] locParser(String str) {
        str = str.substring(1);
        System.out.println(str);
        while(true) {
            String id;
            String name;
            double lat;
            double lon;
            String municipality;
            String region;
            String country;
            String continent;
            String airportUrl;
            String regionUrl;
            String countryUrl;
            int nearest;
            int nearestDistance;
            int tableIndex;
            boolean pairUsesWraparound;
            String[] variables = parseVariables(str);
            System.out.println(Arrays.toString(variables));
            //TODO remove } from end
            break;
        }
        return null;
    }

    public String parseVariable(String str) {
        int quote = str.indexOf("\"");
        int comma = str.indexOf(",");
        int end = Math.min(quote, comma);
        return str.substring(0, end);
    }

    public String[] parseVariables(String str) {
        String[] variables = new String[15];
        for(int i=0; i<variables.length; i++) {
            if(i!=2 && i!=3 && i!=12 && i!=13 && i!=14) {
                str = str.substring(str.indexOf(":"));
                if(str.charAt(1)!='"') {
                    str = str.substring(nthIndexOf(str,":", 2)+2);
                } else {
                    str = str.substring(2);
                }
            } else {
                str = str.substring(str.indexOf(":")+1);
            }
            variables[i] = parseVariable(str);
        }
        return variables;
    }

    //Finds index of nth occurrence
    public static int nthIndexOf(String str, String character, int n) {
        int index = str.indexOf(character);
        while (--n > 0 && index != -1)
            index = str.indexOf(character, index+1);
        return index;
    }

    private ArrayList<Location> getAllLocationsFromDatabase(ArrayList<String> ids) {
        ArrayList<Location> locs = new ArrayList<>();
        QueryBuilder qb = new QueryBuilder(true);
        for(String id : ids) {
            locs.add(qb.fireSearchQuery(id).get(0));
        }
        return locs;
    }

    private int searchSavedTrips(String name) {
        for(int i = 0; i < trips.size(); i++) {
            if(name.equals(trips.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }

    private void setHeaders(Response res) {
        res.header("Content-Type", "application/json"); //Says we are returning a json
        res.header("Access-Control-Allow-Origin", "*"); //Says its ok for browser to call even if diff host
    }
}