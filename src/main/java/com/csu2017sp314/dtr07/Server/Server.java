package com.csu2017sp314.dtr07.Server;

import com.csu2017sp314.dtr07.Model.Location;
import com.csu2017sp314.dtr07.Model.QueryBuilder;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;

import java.util.Arrays;

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
        String str = rec.queryParams("locs");
        //System.out.println(str);
        //Location[] locArray = locParser(str);
        return null;
    }

    /*
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
    */

    private void setHeaders(Response res) {
        res.header("Content-Type", "application/json"); //Says we are returning a json
        res.header("Access-Control-Allow-Origin", "*"); //Says its ok for browser to call even if diff host
    }
}