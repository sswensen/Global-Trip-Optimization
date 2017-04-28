package com.csu2017sp314.dtr07.Server;

import com.csu2017sp314.dtr07.Model.Location;
import com.csu2017sp314.dtr07.Model.QueryBuilder;
import com.google.gson.Gson;
import spark.Request;
import spark.Response;

import java.util.ArrayList;

import static spark.Spark.get;

/**
 * Created by SummitDrift on 4/20/17.
 */

public class Server {
    private ArrayList<Trip> trips = new ArrayList<>();

    public static void main(String[] args) {
        Server s = new Server();
        s.serve();
    }

    public void serve() {
        Gson g = new Gson();
        get("/locations", this::hello, g::toJson);
        get("/toOptimize", this::optimize, g::toJson);
        get("/saveTrips", this::saveTrip, g::toJson);
        get("/getTrips", this::getTrip, g::toJson);
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
        ArrayList<Location> locations = new ArrayList<>();
        for(int k = 0; k < jsonStrings.length; k++) {
            Location loc = gson.fromJson(jsonStrings[k], Location.class);
            locations.add(loc);
            System.out.println("Location " + k + " " + loc.toString());
        }
        /*Location temp = locations.remove(0);
        locations.add(temp);*/
        return locations;
    }

    public Object saveTrip(Request rec, Response res) {
        setHeaders(res);
        Gson gson = new Gson();
        String locs = rec.queryParams("trips");
        locs = locs.replace("[", "");
        locs = locs.replace("]", "");
        String[] jsonStrings = locs.split("}");
        jsonStrings[0] += "}";
        for(int j = 1; j < jsonStrings.length; j++) {
            StringBuilder sb = new StringBuilder(jsonStrings[j]);
            sb.deleteCharAt(0);
            sb.append("}");
            jsonStrings[j] = sb.toString();
        }
        ArrayList<Trip> newTrips = new ArrayList<>();
        for(int k = 0; k < jsonStrings.length; k++) {
            Trip trip = gson.fromJson(jsonStrings[k], Trip.class);
            newTrips.add(trip);
            System.out.println("Trip " + k + " " + trip.toString());
        }
        trips = newTrips;
        /*Location temp = locations.remove(0);
        locations.add(temp);*/
        return trips;
    }

    public Object getTrip(Request rec, Response res) {
        setHeaders(res);
        String locs = rec.queryParams("num");
        trips.add(new Trip("e", 666.666, new ArrayList<>()));
        return trips;
    }

    private void setHeaders(Response res) {
        res.header("Content-Type", "application/json"); //Says we are returning a json
        res.header("Access-Control-Allow-Origin", "*"); //Says its ok for browser to call even if diff host
    }
}