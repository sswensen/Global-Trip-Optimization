package com.csu2017sp314.dtr07;

import com.csu2017sp314.dtr07.Server.Server;

import java.util.ArrayList;


public class TripCo {
    private static ArrayList<String> arguments = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        Server s = new Server();
        s.serve();
    }
}
