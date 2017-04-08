package com.csu2017sp314.dtr07.View;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;
//import org.w3c.dom.DOMImplementation;

/**
 * Created by SummitDrift on 2/13/17.
 * Main class for View Package
 */

public class View {
    private Consumer<String> callback;
    private Consumer<ArrayList<String>> callback2;
    private Consumer<String> callback3;
    private Consumer<ArrayList<String>> callback4;
    private ArrayList<String> xmlIds;
    private SVGBuilder svg;
    private XMLBuilder xml;
    private ArrayList<String> ids = new ArrayList<>();
    private String f;
    private MapGUI gui;
    private String svgMap;
    public ArrayList<String> originalIds;
    private double width;
    private double height;


    public void initializeTrip(String svgMap) throws SAXException, IOException, ParserConfigurationException {
        this.svgMap = svgMap;
        gui = new MapGUI();
        svg = new SVGBuilder(svgMap);
        xml = new XMLBuilder();
        ids = new ArrayList<>(originalIds);
        width = svg.getWidth();
        height = svg.getHeight();
        gui.setWidth((int) width);
        gui.setHeight((int) height + 20);
    }

    public void resetTrip() throws SAXException, IOException, ParserConfigurationException {
        svg = new SVGBuilder(svgMap);
        xml = new XMLBuilder();
    }

    private void readXML(String selectionXml) throws SAXException, IOException, ParserConfigurationException {
        Document readXml;
        File xmlFile = new File(selectionXml);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        readXml = dBuilder.parse(xmlFile);
        readXml.getDocumentElement().normalize();
        //System.out.println("*Testing*   Root element :" + readXml.getDocumentElement().getNodeName());
        NodeList nList = readXml.getElementsByTagName("destinations");
        for(int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            //System.out.println("\nCurrent Element :" + nNode.getNodeName());
            if(nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                int i = 0;
                while(eElement.getElementsByTagName("id").item(i) != null) {
                    ids.add(eElement.getElementsByTagName("id").item(i).getTextContent());
                    i++;
                }
            }
        }
        /*for(int i = 0; i < ids.size(); i++) {
            //System.out.println("id at index " + i + " = " + ids.get(i));
        }*/
    }

    Document getXMLdoc() {
        return xml.getXMLdoc();
    }

    Document getSVGdoc() {
        return svg.getSVGdoc();
    }

    public void setCallback(Consumer<String> callback) {
        this.callback = callback;
    }

    public void setCallback2(Consumer<ArrayList<String>> callback2) {
        this.callback2 = callback2;
    }

    public void setCallback3(Consumer<String> callback3) {
        this.callback3 = callback3;
    }

    public void setCallback4(Consumer<ArrayList<String>> callback4) {
        this.callback4 = callback4;
    }

    private void userAddLoc(String id) {
        callback.accept(id);
    }

    private void userAddLocList(ArrayList<String> ids) {
        callback2.accept(ids);
    }

    private void searchDatabase(ArrayList<String> wheres) {
        callback4.accept(wheres);
    }

    private void mapOptions(String option) {
        callback3.accept(option);
    }

    public void addLeg(String id, String start, String finish, int mileage) {
        xml.addLeg(id, start, finish, mileage);
    }

    public void addFinalLeg(String id, String s, String f, int t) {
        xml.addLeg(id, s, f, t);
    }

    public void addLine(double x1, double y1, double x2, double y2, String id, boolean wraparound) { //TODO implement gui wraparound
        if(wraparound) {
            double originalX1 = x1;
            double originalY1 = y1;
            double originalX2 = x2;
            double originalY2 = y2;
            System.out.println("Using wraparound for " + id);
            if(x1 > x2) {
                x1 -= 180;
                x2 += 180;
            } else {
                x1 += 180;
                x2 -= 180;
            }
            double m = (y2 - y1) / (x2 - x1);
            double b = y1 - (m * x1);
            double interX1;
            double interX2;
            if(originalX1 > originalX2) {
                interX1 = 180;
                interX2 = -180;
            } else {
                interX1 = -180;
                interX2 = 180;
            }
            double interY = m*interX1 + b;
            svg.addLine(originalX1, originalY1, interX1, interY, id);
            svg.addLine(originalX2, originalY2, interX2, interY, id);
        } else {
            svg.addLine(x1, y1, x2, y2, id);
        }
    }

    public void addDistance(double x1, double y1, double x2, double y2, int distance, String id) {
        svg.addDistance(x1, y1, x2, y2, distance, id);
    }

    public void addCityNameLabel(double lon, double lat, String city) {
        svg.addCityNameLabel(lon, lat, city);
    }

    public void addIDLabel(double lon, double lat, String id) {
        svg.addIDLabel(lon, lat, id);
    }

    public void addHeader(String title) {
        svg.addHeader(title);
    }

    public void addFooter(int totalDistance) {
        svg.addFooter(totalDistance);
    }

    public void addBorders() {
        svg.addBorders();
    }

    public void finalizeTrip(String filename) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        String[] cut = filename.split("/");
        f = cut[cut.length - 1].substring(0, cut[cut.length - 1].length() - 4);
        //XML document
        DOMSource source = new DOMSource(xml.getXMLdoc());
        StreamResult result = new StreamResult(new File(f + ".xml"));
        transformer.transform(source, result);

        //SVG document
        DOMSource source2 = new DOMSource(svg.getSVGdoc());
        StreamResult result2 = new StreamResult(new File(f + ".svg"));
        transformer.transform(source2, result2);
    }


    public void gui() throws Exception {
        gui.setCallback((String s) -> {
            this.userAddLoc(s);
        });

        gui.setCallback2((ArrayList<String> s) -> {
            this.userAddLocList(s);
        });

        gui.setCallback3((String s) -> {
            this.mapOptions(s);
        });

        gui.setCallback4((ArrayList<String> s) -> {
            this.searchDatabase(s);
        });
        try {
            gui.init(f);
        } catch(Exception e) {
            System.err.println(e);
            System.err.println("Error initilizing gui with filename " + f);
        }
        gui.displayXML(ids);
    }

    public void makeGUILocations(ArrayList<Object> locs) {
        gui.makeGUILocations(locs);
    }

    public void addLegToItinerary(String seqId, String name1, String name2, int mileage) {
        gui.addLegToItinerary(seqId, name1, name2, mileage);
    }

    public void refresh() throws Exception {
        gui.refresh();
    }

    public boolean cleanup() {
        return gui.cleanup();
    }

    public static void main(String[] argv) throws Exception {
        MapGUI gui = new MapGUI();
        gui.displayDatabaseWindow();
    }
}
