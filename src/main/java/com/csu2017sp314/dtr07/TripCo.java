package com.csu2017sp314.dtr07;

import com.csu2017sp314.dtr07.Model.*;

import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

import org.xml.sax.SAXException;

import com.csu2017sp314.dtr07.Server.Server;


public class TripCo {
    private static ArrayList<String> arguments = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        Server s = new Server();
        s.serve();
    }
}
