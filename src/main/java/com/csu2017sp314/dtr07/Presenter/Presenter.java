package com.csu2017sp314.dtr07.Presenter;

import com.csu2017sp314.dtr07.Model.Model;
import com.csu2017sp314.dtr07.View.View;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by SummitDrift on 2/13/17.
 * Main class for Presenter Package
 * Handles all view and model data
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
    private boolean readingFromXML = true;
    private boolean kilometers;
    private boolean useDatabase = false;

    public Presenter(Model model, View view) {
        this.model = model;
        this.view = view;

        view.setCallback((String s) -> {
            this.eventUserAddLoc(s);
        });
        view.setCallback2((ArrayList<String> s) -> {
            this.eventUserAddLocList(s);
            for(String temp : s) {
                System.out.println("[Presenter] This is callback2:\t" + temp);
            }
        });
        view.setCallback3((String s) -> {
            if(s.equals("Names")) {
                toggleName();
            }
            if(s.equals("IDs")) {
                toggleIds();
            }
            if(s.equals("Distance")) {
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
                toggleKilometers();
                System.out.println("[Presenter] Units now M");
            }
            if(s.equals("K")) {
                //TODO put this somewhere
                toggleKilometers();
                System.out.println("[Presenter] Units now K");
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
            ArrayList<String> locationNames = model.searchDatabase(s, useDatabase);
            for(String temp : locationNames) {
                System.out.println("[Presenter] This is callback4:\t" + temp);
            }
            for(int i = 0; i < locationNames.size(); i++) {
                copyLocationsToView(model.copyDBLocationsToView(i)); //This gets the location data and pushes it into copyLoctaions
            }
            System.out.println("DONE MAKING LOCATIONS");
            readingFromXML = false;
        });
    }

    private void copyLocationsToView(ArrayList<Object> locs) {
        view.makeGUILocations(locs);
    }

    private void toggleName() {
        displayName = !displayName;
        System.out.println("[Presenter] Names now " + displayName);
    }

    private void toggleIds() {
        displayId = !displayId;
        System.out.println("[Presenter] IDs now " + displayId);
    }

    private void toggleMileage() {
        displayMileage = !displayMileage;
        System.out.println("[Presenter] Mileage now " + displayMileage);
    }

    private void toggleKilometers() {
        if(kilometers) {
            kilometers = false;
            model.setKilometers(false);
            view.setKilometers(false);
        } else {
            kilometers = true;
            model.setKilometers(true);
            view.setKilometers(true);
        }
        System.out.println("[Presenter] Kilometers now " + kilometers);
    }

    private void toggle2opt() {
        if(twoOpt) {
            twoOpt = false;
            model.setTwoOpt(false);
        } else {
            twoOpt = true;
            model.setTwoOpt(true);
        }
        System.out.println("[Presenter] 2-opt now " + twoOpt);
    }

    private void toggle3opt() {
        if(threeOpt) {
            threeOpt = false;
            model.setThreeOpt(false);
        } else {
            threeOpt = true;
            model.setThreeOpt(true);
        }
        System.out.println("[Presenter] 3-opt now " + threeOpt);
    }

    private int eventUserAddLoc(String id) {
        return model.toggleLocations(id);
    }

    private int eventUserAddLocList(ArrayList<String> ids) {
        currentIds = ids;
        model.setReadingFromXML(readingFromXML);
        model.toggleListLocations(ids, useDatabase);
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
            model.planUserTrip(fname, readingFromXML);
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
                view.addLeg(model.getPairId(i),model.getFirstId(i), model.getFirstName(i),Double.toString(model.getFirstLat(i)),Double.toString(model.getFirstLon(i))," ", model.getFirstMunicipality(i),model.getFirstRegion(i),model.getFirstCountry(i),model.getFirstContinent(i),model.getFirstAirportURL(i),model.getFirstRegionUrl(i),model.getFirstCountryURL(i),model.getSecondId(i),model.getSecondName(i),Double.toString(model.getSecondLat(i)),Double.toString(model.getSecondLon(i))," ",model.getSecondMunicipality(i),model.getSecondRegion(i),model.getSecondCountry(i),model.getSecondContinent(i),model.getSecondAirportURL(i),model.getSecondRegionUrl(i),model.getSecondCountry(i),model.getPairDistance(i),"miles");
                finalPairId++;
                view.addLine(model.getUserFirstLon(i), model.getUserFirstLat(i), model.getUserSecondLon(i), model.getUserSecondLat(i), pairId, model.isWraparound(i));
                if(displayName) {
                    view.addCityNameLabel(model.getUserFirstLon(i), model.getUserFirstLat(i), firstName);
                    view.addCityNameLabel(model.getUserSecondLon(i), model.getUserSecondLat(i), secondName);
                }
                if(displayMileage) {
                    view.addDistance(model.getUserFirstLon(i), model.getUserFirstLat(i), model.getUserSecondLon(i), model.getUserSecondLat(i), model.getUserPairDistance(i), pairId, model.isWraparound(i));
                }
                if(displayId) {
                    view.addIDLabel(model.getUserFirstLon(i), model.getUserFirstLat(i), firstId);
                    view.addIDLabel(model.getUserSecondLon(i), model.getUserSecondLat(i), secondId);
                }
            }
            view.addFooter(model.getTripDistance());
            view.addHeader("Long Live the Chief");
            //view.addFinalLeg(Integer.toString(finalPairId), model.getLegStartLocation(), model.getLegFinishLocation(), model.getTripDistance());
            view.finalizeTrip(fname);
            for(int i = 0; i < model.getNumDatabaseLocationsReturned(); i++) {
                copyLocationsToView(model.copyDBLocationsToView(i)); //This gets the location data and pushes it into copyLoctaions
            }
            makeItinerary();
            model.resetUserLoc();
            view.refresh();
        } catch(Exception e) {
            System.err.println("[Presenter] Exception encountered in Presenter.java");
            System.err.println(e);
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

    public boolean isKilometers() {
        return this.kilometers;
    }

    public void setKilometers(boolean kilometers) {
        this.kilometers = kilometers;
        view.setKilometers(kilometers);
        model.setKilometers(kilometers);
    }

    public boolean getDisplayMileage() {
        return displayMileage;
    } //done

    public void setDisplayMileage(boolean x) {
        displayMileage = x;
    }//done

    public boolean getDisplayId() {
        return displayId;
    } //done

    public void setDisplayId(boolean x) {
        displayId = x;
    } //done

    public boolean getDisplayName() {
        return displayName;
    }//done

    public void setDisplayName(boolean x) {
        displayName = x;
    } //done

    public boolean displayGui(boolean x) {
        return (displayGui = x);
    }

    public boolean getDisplayGui() {
        return displayGui;
    }

    public String getFname() {
        return fname;
    }

    private void makeItinerary() {
        int numUserPairs = model.getUserNumPairs();
        if(numUserPairs == 0) {
            numUserPairs = model.getNumPairs();
        }
        for(int i = 0; i < numUserPairs; i++) {
            //System.out.println("Adding something to index " + i);
            if(!kilometers) {
                view.addLegToItinerary(model.getPairId(i), model.getFirstName(i), model.getSecondName(i), model.getPairDistance(i));
            } else {
                view.addLegToItinerary(model.getPairId(i), model.getFirstName(i), model.getSecondName(i), convert(model.getPairDistance(i)));
            }
        }
    }

    public void setViewOptions(ArrayList<String> arguments){
        view.setOptions(arguments);
    }

    private int convert(int in) {
        double out = (double) in;
        out *= 1.60934;
        out = Math.round(out);
        return (int) out;
    }

    public void planTrip(String selectionXml, String svgMap) throws Exception {
        //fname = filename;
        this.selectionXml = selectionXml;
        this.svgMap = svgMap;
        String[] cut = selectionXml.split("/");
        fname = cut[cut.length - 1].substring(0, cut[cut.length - 1].length() - 4);


        /*ArrayList selectedAirports = new ArrayList();
        selectedAirports.add("NZCH");
        selectedAirports.add("EHAM");
        selectedAirports.add("EDDB");
        selectedAirports.add("VDPP");
        selectedAirports.add("BIKF");
        selectedAirports.add("AYPY");
        selectedAirports.add("ZYHB");
        selectedAirports.add("ZYTX");
        selectedAirports.add("BKPR");
        selectedAirports.add("CYEG");*/
        if(selectionXml.equals("")) {
            model.setSelectedLocations(new ArrayList<>());
            selectionXml = "untitled";
        } else {
            model.setSelectedLocations(readXML(selectionXml));
        }
        model.planTrip("M", useDatabase);
        //ArrayList<String> locationNames = model.searchDatabase(new ArrayList<>());
        //for(int i = 0; i < model.getNumLocs(); i++) {
        //    copyLocationsToView(model.copyDBLocationsToView(i)); //This gets the location data and pushes it into copyLoctaions
        //}
        int numPairs = model.getNumPairs();
        view.originalIds = model.getLocationNames();
        view.initializeTrip(svgMap);
        view.addFooter(model.getTripDistance());
        for(int i = 0; i < model.getDatabaseLocationsReturnedSize(); i++) {
            copyLocationsToView(model.copyDBLocationsToView(i)); //This gets the location data and pushes it into copyLoctaions
        }
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
            boolean wraparound = model.isWraparound(i);
            view.addLeg(model.getPairId(i),model.getFirstId(i), model.getFirstName(i),Double.toString(model.getFirstLat(i)),Double.toString(model.getFirstLon(i))," ", model.getFirstMunicipality(i),model.getFirstRegion(i),model.getFirstCountry(i),model.getFirstContinent(i),model.getFirstAirportURL(i),model.getFirstRegionUrl(i),model.getFirstCountryURL(i),model.getSecondId(i),model.getSecondName(i),Double.toString(model.getSecondLat(i)),Double.toString(model.getSecondLon(i))," ",model.getSecondMunicipality(i),model.getSecondRegion(i),model.getSecondCountry(i),model.getSecondContinent(i),model.getSecondAirportURL(i),model.getSecondRegionUrl(i),model.getSecondCountry(i),model.getPairDistance(i),"miles");
            finalPairId++;
            view.addLine(firstLon, firstLat, secondLon, secondLat, pairId, wraparound);
            if(displayName) {
                view.addCityNameLabel(firstLon, firstLat, firstName);
                view.addCityNameLabel(secondLon, secondLat, secondName);
            }
            if(displayMileage) {
                view.addDistance(firstLon, firstLat, secondLon, secondLat, pairDistance, pairId, wraparound);
            }
            if(displayId) {
                view.addIDLabel(firstLon, firstLat, firstId);
                view.addIDLabel(secondLon, secondLat, secondId);
            }
        }

        view.addFooter(model.getTripDistance());
        view.addHeader("Long Live the Chief");
        //view.addFinalLeg(Integer.toString(finalPairId), model.getLegStartLocation(), model.getLegFinishLocation(), model.getTripDistance());
        ArrayList<String> viewArguments = view.getCommandLineOptions();
        String fileArguments = "";
        for(int i = 0; i < viewArguments.size(); i++) {
            if(viewArguments.get(i).equals("-d") || viewArguments.get(i).equals("-n") || viewArguments.get(i).equals("-i")
                    || viewArguments.get(i).equals("-2") || viewArguments.get(i).equals("-3") || viewArguments.get(i).equals("-k")) {
                fileArguments += viewArguments.get(i);
            }
        }
        fname = fname + fileArguments + "-t07";
        view.finalizeTrip(fname);
        makeItinerary();
        if(displayGui) {
            view.gui();
        }
    }

    private ArrayList<String> readXML(String selectionXml) throws Exception {
        Document readXml;
        String name = "";
        File xmlFile = new File(selectionXml);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        readXml = dBuilder.parse(xmlFile);
        readXml.getDocumentElement().normalize();
        //System.out.println("*Testing*   Root element :" + readXml.getDocumentElement().getNodeName());
        ArrayList<String> ret = new ArrayList<>();
        NodeList nList = readXml.getElementsByTagName("destinations");
        NodeList nList2 = readXml.getElementsByTagName("title");
        for(int i = 0; i < nList2.getLength(); i++) {
            Node a = nList2.item(i);
            name = a.getTextContent();
        }
        for(int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            //System.out.println("\nCurrent Element :" + nNode.getNodeName());
            if(nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                int i = 0;
                while(eElement.getElementsByTagName("id").item(i) != null) {
                    ret.add(eElement.getElementsByTagName("id").item(i).getTextContent());
                    i++;
                }
            }
        }
        return ret;
    }

    public void setUseDatabase(boolean useDatabase) {
        this.useDatabase = useDatabase;
    }
}
