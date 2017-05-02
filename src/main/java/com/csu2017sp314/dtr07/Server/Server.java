package com.csu2017sp314.dtr07.Server;

import com.csu2017sp314.dtr07.Model.Location;
import com.csu2017sp314.dtr07.Model.QueryBuilder;
import com.google.gson.Gson;
import org.eclipse.jetty.util.ArrayUtil;
import spark.Request;
import spark.Response;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static spark.Spark.get;

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

    private Object selectIndividual(Request rec, Response res) {
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
        for(int k = 0; k < jsonStrings.length; k++) {
            Location loc = gson.fromJson(jsonStrings[k], Location.class);
            locations2[k] = loc;
            Location tempL = locations2[k];
            selectedLocations.add(tempL);
        }

        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(jsonStrings.length);
        return temp;
    }

    private Object fireOpt(Request rec, Response res) {
        setHeaders(res);
        String opt = rec.queryParams("opt");
        Location[] locations2 =
                selectedLocations.toArray(new Location[selectedLocations.size()]);
        System.out.println("running individual opt now");
        Optimization optimiziation = new Optimization(locations2, opt);
        locations2 = optimiziation.getOptimizedRoute();
        System.out.println("complete");
        tripDistance = optimiziation.getTripDistance();
        selectedLocations.clear();
        //TODO remove last locations from locations2
        System.out.println("individual opt done returning " +
                locations2.length + " locations");
        Location[] newLocations = ArrayUtil.removeFromArray(locations2,
                locations2[locations2.length - 1]);
        return newLocations;
    }

    private Object hello(Request rec, Response res) {
        setHeaders(res);
        QueryBuilder q = new QueryBuilder(true);
        q.fireQuery(rec.queryParams("q"));
        return q.getLocations();
    }

    private Object optimize(Request rec, Response res) {
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
        for(int k = 0; k < jsonStrings.length; k++) {
            Location loc = gson.fromJson(jsonStrings[k], Location.class);
            locations2[k] = loc;
        }
        System.out.println("running opt now");
        Optimization optimiziation = new Optimization(locations2, opt);
        locations2 = optimiziation.getOptimizedRoute();
        System.out.println("complete");
        tripDistance = optimiziation.getTripDistance();
        //TODO remove last locations from locations2
        Location[] newLocations = ArrayUtil.removeFromArray(locations2,
                locations2[locations2.length - 1]);
        return newLocations;
    }

    private Object saveTrip(Request rec, Response res) {
        setHeaders(res);
        Gson gson = new Gson();
        String locs = rec.queryParams("trips");
        locs = locs.substring(1, locs.length() - 1); // --> "ello World"
        System.out.println(locs);
        String index = ",\\{\"name";
        String[] jsonStrings = locs.split(index);
        System.out.println(jsonStrings.length);
        for(int i = 1; i < jsonStrings.length; i++) {
            jsonStrings[i] = index + jsonStrings[i];
            StringBuilder sb = new StringBuilder(jsonStrings[i]);
            sb.deleteCharAt(0);
            sb.deleteCharAt(0);
            jsonStrings[i] = sb.toString();
        }
        for(int i = 0; i < jsonStrings.length; i++) {
            System.out.println(jsonStrings[i]);
        }
        Trip trip = gson.fromJson(jsonStrings[0], Trip.class);
        int ret = searchSavedTrips(trip.getName());
        if(ret < 0) {
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

    private void createKMLFile(ArrayList<Location> locations, String tripName) {
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
            for(int i = 0; i < locations.size(); i++) {
                String kmlElement =
                        "    <Placemark>\n" +
                                "      <name>" +
                                locations.get(i).getName() + "</name>\n" +
                                "      <description>" +
                                locations.get(i).getMunicipality() + "</description>\n" +
                                "      <Point>\n" +
                                "        <coordinates>" +
                                locations.get(i).getLon() + "," +
                                locations.get(i).getLat() + "," + 0 + "</coordinates>\n" +
                                "      </Point>\n" +
                                "    </Placemark>\n";
                bw.write(kmlElement);
                coordinates += locations.get(i).getLon() + ", " +
                        locations.get(i).getLat() + ", " + 0 + ".\n" + "          ";
            }
            coordinates = coordinates.trim();
            String lineString =
                    "    <Placemark>\n" +
                            "      <LineString>\n" +
                            "        <coordinates>\n" +
                            "          " + coordinates + "\n" +
                            "        </coordinates>\n" +
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

    private Object getTrip(Request rec, Response res) {
        setHeaders(res);
        System.out.println("Returning saved trips");
        String locs = rec.queryParams("num");
        return trips;
    }

    private Object getDistance(Request rec, Response res) {
        setHeaders(res);
        String tf = rec.queryParams("dist");
        ArrayList<Double> temp = new ArrayList<>();
        temp.add(tripDistance);
        return temp;
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
        res.header("Content-Type", "application/json");
        //Says we are returning a json
        res.header("Access-Control-Allow-Origin", "*");
        //Says its ok for browser to call even if diff host
    }
}