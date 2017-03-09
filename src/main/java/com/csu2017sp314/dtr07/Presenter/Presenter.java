package com.csu2017sp314.dtr07.Presenter;


import com.csu2017sp314.dtr07.Model.*;
import com.csu2017sp314.dtr07.View.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.function.Function;
import java.util.concurrent.TimeUnit;


import org.xml.sax.SAXException;

public class Presenter {
    private Model model;
    private View view;
    private boolean displayMileage;
    private boolean displayId;
    private boolean displayName;
    private String fname;
    private String selectionXml;

    public Presenter(Model model, View view) {
        this.model = model;
        this.view = view;
        this.displayMileage = false;
        this.displayId = false;
        this.displayName = false;
        view.setCallback((String s) -> {
            this.eventUserAddLoc(s);
        });
    }

    public int eventUserAddLoc(String id) {
        return model.toggleLocations(id);
    }

    public int eventLoadLoc() throws SAXException, IOException, ParserConfigurationException, TransformerException {
        view.initializeTrip(selectionXml);

        int numPairs = model.getUserPairs().size();

        view.addFooter(model.getTripDistance());
        int finalPairId = 0;
        for (int i = 0; i < numPairs; i++) {
            double firstLon = model.getUserFirstLon(i);
            double firstLat = model.getUserFirstLat(i);
            double secondLon = model.getUserSecondLon(i);
            double secondLat = model.getUserSecondLat(i);
            int pairDistance = model.getUserPairDistance(i);
            String pairId = model.getUserPairId(i);
            String firstId = model.getUserFirstId(i);
            String secondId = model.getUserSecondId(i);
            String firstName = model.getUserFirstName(i);
            String secondName = model.getUserSecondName(i);
            view.addLeg(pairId, firstName, secondName, pairDistance);
            finalPairId++;
            view.addLine(firstLon, firstLat, secondLon, secondLat, pairId);
            if (displayName) {
                view.addCityNameLabel(firstLon, firstLat, firstName);
                view.addCityNameLabel(secondLon, secondLat, secondName);
            }
            if (displayMileage) {
                view.addDistance(firstLon, firstLat, secondLon, secondLat, pairDistance, pairId);
            }
            if (displayId) {
                view.addIDLabel(firstLon, firstLat, firstId);
                view.addIDLabel(secondLon, secondLat, secondId);
            }
        }
        view.addFooter(model.getTripDistance());
        view.addHeader("Colorado");
        view.addFinalLeg(Integer.toString(finalPairId), model.getLegStartLocation(), model.getLegFinishLocation(), model.getTripDistance());
        view.finalizeTrip(fname);
        return -1;
    }


    public void clearUserLoc() {
        model.resetUserLoc();
    }

    public void setDisplayMileage(boolean x) {
        displayMileage = x;
    }

    public void setDisplayId(boolean x) {
        displayId = x;
    }

    public void setDisplayName(boolean x) {
        displayName = x;
    }

    public boolean getDisplayMileage() {
        return displayMileage;
    }

    public boolean getDisplayId() {
        return displayId;
    }

    public boolean getDisplayName() {
        return displayName;
    }

    public void planTrip(String filename, String selectionXml) throws Exception {
        fname = filename;
        this.selectionXml = selectionXml;
        view.initializeTrip(selectionXml);
        model.planTrip(filename);
        int numPairs = model.getNumPairs();
        //view.addBorders();
        //view.addHeader("Colorado");
        view.addFooter(model.getTripDistance());
        int finalPairId = 0;
        for (int i = 0; i < numPairs; i++) {
            double firstLon = model.getFirstLon(i);
            double firstLat = model.getFirstLat(i);
            double secondLon = model.getSecondLon(i);
            double secondLat = model.getSecondLat(i);
            int pairDistance = model.getPairDistance(i);
            String pairId = model.getPairId(i);
            String firstId = model.getFirstId(i);
            String secondId = model.getSecondId(i);
            String firstName = model.getFirstName(i);
            String secondName = model.getSecondName(i);
            view.addLeg(pairId, firstName, secondName, pairDistance);
            finalPairId++;
            view.addLine(firstLon, firstLat, secondLon, secondLat, pairId);
            if (displayName) {
                view.addCityNameLabel(firstLon, firstLat, firstName);
                view.addCityNameLabel(secondLon, secondLat, secondName);
            }
            if (displayMileage) {
                view.addDistance(firstLon, firstLat, secondLon, secondLat, pairDistance, pairId);
            }
            if (displayId) {
                view.addIDLabel(firstLon, firstLat, firstId);
                view.addIDLabel(secondLon, secondLat, secondId);
            }
        }

        view.addFooter(model.getTripDistance());
        view.addHeader("Colorado");
        view.addFinalLeg(Integer.toString(finalPairId), model.getLegStartLocation(), model.getLegFinishLocation(), model.getTripDistance());
        view.finalizeTrip(filename);

        view.gui();
        //TimeUnit.SECONDS.sleep(3);
        eventUserAddLoc("3");
        eventUserAddLoc("10");
        eventUserAddLoc("17");
        eventLoadLoc();
        view.refresh();
    }
}
