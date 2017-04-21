package com.csu2017sp314.dtr07.Server;

import com.csu2017sp314.dtr07.Model.Location;
import com.csu2017sp314.dtr07.Model.QueryBuilder;
import spark.Request;
import spark.Response;
import com.google.gson.Gson;

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
    }

    public Object hello(Request rec, Response res) {
        setHeaders(res);
        QueryBuilder q = new QueryBuilder(true);
        //System.out.println(rec.queryParams("q"));
        q.fireQuery(rec.queryParams("q"));
        return q.getLocations();
    }

    private void setHeaders(Response res) {
        res.header("Content-Type", "application/json"); //Says we are returning a json
        res.header("Access-Control-Allow-Origin", "*"); //Says its ok for browser to call even if diff host
    }
}