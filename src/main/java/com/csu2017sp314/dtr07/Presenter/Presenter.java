package com.csu2017sp314.dtr07.Presenter;

import com.csu2017sp314.dtr07.Model.*;
import com.csu2017sp314.dtr07.View.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;

import org.xml.sax.SAXException;

/**
 * Created by SummitDrift on 2/13/17.
 * Main class for Presenter Package
 */

public class Presenter {
    private Model model;
    private View view;
    private boolean displayMileage = false;
    private boolean displayId = false;
    private boolean displayName = false;
    private boolean twoOpt = false;
    private boolean threeOpt = false;
    private String fname;
    private String selectionXml;
    private ArrayList<String> currentIds = new ArrayList<>();
    private boolean displayGui;
    private String svgMap;

    public Presenter(Model model, View view) {
        this.model = model;
        this.view = view;

        view.setCallback((String s) -> {
            this.eventUserAddLoc(s);
        });
        view.setCallback2((ArrayList<String> s) -> {
            this.eventUserAddLocList(s);
        });
        view.setCallback3((String s) -> {
            if(s.equals("Names")) {
                toggleName();
            }
            if(s.equals("IDs")) {
                toggleIds();
            }
            if(s.equals("Mileage")) {
                toggleMileage();
            }
            if(s.equals("2-opt")) {
                toggle2opt();
            }
            if(s.equals("3-opt")) {
                toggle3opt();
            }
            if(s.equals("M")) {
                //TODO put this somewhere
            }
            if(s.equals("K")) {
                //TODO put this somewhere
            }
            /*if(currentIds.isEmpty()) {
                try {
                    System.out.println("Using original map");
                    planTrip(fname, selectionXml);
                } catch(Exception e) {
                    System.err.printf("Error encountered in callback 3");
                    System.err.println(e);
                }
            } else {
                eventUserAddLocList(currentIds);
                System.out.println("Using new map");
            }*/
        });
        view.setCallback4((ArrayList<String> s) -> {
            this.eventUserAddLocList(s); //TODO update this
        });
    }

    private void toggleName() {
        if(displayName) {
            displayName = false;
        } else {
            displayName = true;
        }
        System.out.println("Names now " + displayName);
    }

    private void toggleIds() {
        if(displayId) {
            displayId = false;
        } else {
            displayId = true;
        }
        System.out.println("IDs now " + displayId);
    }

    private void toggleMileage() {
        if(displayMileage) {
            displayMileage = false;
        } else {
            displayMileage = true;
        }
        System.out.println("Mileage now " + displayMileage);
    }

    private void toggle2opt() {
        if(twoOpt) {
            twoOpt = false;
            model.setTwoOpt(false);
        } else {
            twoOpt = true;
            model.setTwoOpt(true);
        }
        System.out.println("2-opt now " + twoOpt);
    }

    private void toggle3opt() {
        if(threeOpt) {
            threeOpt = false;
            model.setThreeOpt(false);
        } else {
            threeOpt = true;
            model.setThreeOpt(true);
        }
        System.out.println("3-opt now " + threeOpt);
    }

    private int eventUserAddLoc(String id) {
        return model.toggleLocations(id);
    }

    private int eventUserAddLocList(ArrayList<String> ids) {
        currentIds = ids;
        model.toggleListLocations(ids);
        if(twoOpt)
            model.setTwoOpt(true);
        else
            model.setTwoOpt(false);
        if(threeOpt)
            model.setThreeOpt(true);
        else
            model.setThreeOpt(false);
        //model.printUserLoc();
        try {
            model.planUserTrip(fname);//TODO add arraylist to planUserTrip, might need to make another method like eventUserAddLocList
            view.resetTrip();
            int numPairs = model.getUserPairs().size();

            view.addFooter(model.getTripDistance());
            int finalPairId = 0;
            for(int i = 0; i < numPairs; i++) {
                String pairId = model.getUserPairId(i);
                String firstId = model.getUserFirstId(i);
                String secondId = model.getUserSecondId(i);
                String firstName = model.getUserFirstName(i);
                String secondName = model.getUserSecondName(i);
                view.addLeg(pairId, firstName, secondName, model.getUserPairDistance(i));
                finalPairId++;
                view.addLine(model.getUserFirstLon(i), model.getUserFirstLat(i), model.getUserSecondLon(i), model.getUserSecondLat(i), pairId);
                if(displayName) {
                    view.addCityNameLabel(model.getUserFirstLon(i), model.getUserFirstLat(i), firstName);
                    view.addCityNameLabel(model.getUserSecondLon(i), model.getUserSecondLat(i), secondName);
                }
                if(displayMileage) {
                    view.addDistance(model.getUserFirstLon(i), model.getUserFirstLat(i), model.getUserSecondLon(i), model.getUserSecondLat(i), model.getUserPairDistance(i), pairId);
                }
                if(displayId) {
                    view.addIDLabel(model.getUserFirstLon(i), model.getUserFirstLat(i), firstId);
                    view.addIDLabel(model.getUserSecondLon(i), model.getUserSecondLat(i), secondId);
                }
            }
            view.addFooter(model.getTripDistance());
            view.addHeader("Colorado");
            view.addFinalLeg(Integer.toString(finalPairId), model.getLegStartLocation(), model.getLegFinishLocation(), model.getTripDistance());
            view.finalizeTrip(fname);
            makeItinerary();
            model.resetUserLoc();
            view.refresh();
        } catch(Exception e) {
            //System.out.println("Exception encountered in Presenter.java");
            //System.err.println(e);
            return -1;
        }
        return 1;
    }

    public boolean getTwoOpt() {
        return twoOpt;
    } //done

    public void setTwoOpt(boolean twoOpt) {
        this.twoOpt = twoOpt;
        model.setTwoOpt(twoOpt);
    } //done

    public boolean getThreeOpt() {
        return threeOpt;
    } //done

    public void setThreeOpt(boolean threeOpt) {
        this.threeOpt = threeOpt;
        model.setThreeOpt(threeOpt);
    } //done

    public void setDisplayMileage(boolean x) {
        displayMileage = x;
    }//done

    public void setDisplayId(boolean x) {
        displayId = x;
    } //done

    public void setDisplayName(boolean x) {
        displayName = x;
    } //done

    public boolean getDisplayMileage() {
        return displayMileage;
    } //done

    public boolean getDisplayId() {
        return displayId;
    } //done

    public boolean getDisplayName() {
        return displayName;
    }//done

    public boolean displayGui(boolean x) {
        return (displayGui = x);
    }

    public boolean getDisplayGui() {
        return displayGui;
    }

    public String getFname(){
        return fname;
    }

    private void makeItinerary() {
        int numUserPairs = model.getUserNumPairs();
        if(numUserPairs == 0) {
            numUserPairs = model.getNumPairs();
        }
        for(int i = 0; i < numUserPairs; i++) {
            //System.out.println("Adding something to index " + i);
            view.addLegToItinerary(model.getPairId(i), model.getFirstName(i), model.getSecondName(i), model.getPairDistance(i));
        }
    }

    public void planTrip(String filename, String selectionXml, String svgMap) throws Exception {
        fname = filename;
        this.selectionXml = selectionXml;
        this.svgMap = svgMap;
        //TODO populate selectedAirports arraylist with xml
        ArrayList selectedAirports = new ArrayList();
        selectedAirports.add("NZCH");
        selectedAirports.add("EHAM");
        selectedAirports.add("EDDB");
        selectedAirports.add("VDPP");
        selectedAirports.add("BIKF");
        selectedAirports.add("AYPY");
        selectedAirports.add("ZYHB");
        selectedAirports.add("ZYTX");
        selectedAirports.add("BKPR");
        selectedAirports.add("CYEG");
        model.setSelectedLocations(selectedAirports);
        model.planTrip(filename, "M");
        int numPairs = model.getNumPairs();
        view.originalIds = model.getLocationIds();
        view.initializeTrip(selectionXml, svgMap);
        //view.addBorders();
        //view.addHeader("Colorado");
        view.addFooter(model.getTripDistance());
        int finalPairId = 0;
        for(int i = 0; i < numPairs; i++) {
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
            if(displayName) {
                view.addCityNameLabel(firstLon, firstLat, firstName);
                view.addCityNameLabel(secondLon, secondLat, secondName);
            }
            if(displayMileage) {
                view.addDistance(firstLon, firstLat, secondLon, secondLat, pairDistance, pairId);
            }
            if(displayId) {
                view.addIDLabel(firstLon, firstLat, firstId);
                view.addIDLabel(secondLon, secondLat, secondId);
            }
        }

        view.addFooter(model.getTripDistance());
        view.addHeader("Long Live the Chief");
        view.addFinalLeg(Integer.toString(finalPairId), model.getLegStartLocation(), model.getLegFinishLocation(), model.getTripDistance());
        view.finalizeTrip(filename);
        makeItinerary();
        if(displayGui) {
            view.gui();
        }
    }
}
