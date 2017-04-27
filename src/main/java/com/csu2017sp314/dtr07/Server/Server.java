package com.csu2017sp314.dtr07.Server;

import com.csu2017sp314.dtr07.Model.Location;
import com.csu2017sp314.dtr07.Model.QueryBuilder;
import com.google.gson.JsonElement;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;

import java.util.ArrayList;

import static spark.Spark.*;

/**
 * Created by SummitDrift on 4/20/17.
 */

public class Server {

    public static void main(String[] args) {
        Server s = new Server();
        s.serve();
    }

    public void serve() {
        Gson g = new Gson();
        get("/locations", this::hello, g::toJson);
        get("/toOptimize", this::optimize, g::toJson);
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
        i = i.replace("[", "");
        i = i.replace("]", "");
        String[] jsonStrings = i.split("}");
        jsonStrings[0] += "}";
        for(int j = 1; j < jsonStrings.length;j++){
            StringBuilder sb = new StringBuilder(jsonStrings[j]);
            sb.deleteCharAt(0);
            sb.append("}");
            jsonStrings[j] = sb.toString();
        }
        ArrayList<Location> locations = new ArrayList<>();
        for(int k = 0; k < jsonStrings.length;k++){
            Location loc = gson.fromJson(jsonStrings[k], Location.class);
            locations.add(loc);
        }

        for(int y = 0; y < jsonStrings.length;y++){
            System.out.println(jsonStrings[y]);
        }
        System.out.println();
        System.out.println(locations.get(2).getId());
        System.out.println(locations.get(2).getName());
        System.out.println(locations.get(2).getLat());
        System.out.println(locations.get(2).getLon());
        System.out.println(locations.get(2).getMunicipality());
        System.out.println(locations.get(2).getRegion());
        System.out.println(locations.get(2).getCountry());
        System.out.println(locations.get(2).getContinent());
        System.out.println(locations.get(2).getAirportUrl());
        System.out.println(locations.get(2).getRegionUrl());
        System.out.println(locations.get(2).getCountryUrl());
        System.out.println(locations.get(2).getNearest());
        System.out.println(locations.get(2).getNearestDistance());
        System.out.println(locations.get(2).getTableIndex());
        System.out.println(locations.get(2).isPairUsesWraparound());
        return null;
    }

    private void setHeaders(Response res) {
        res.header("Content-Type", "application/json"); //Says we are returning a json
        res.header("Access-Control-Allow-Origin", "*"); //Says its ok for browser to call even if diff host
    }
}