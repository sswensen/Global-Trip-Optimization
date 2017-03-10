package com.csu2017sp314.dtr07.View;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

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

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
//import org.w3c.dom.DOMImplementation;

public class View {
    public Consumer<String> callback;
    private ArrayList<String> xmlIds;
    private SVGBuilder svg;
    private XMLBuilder xml;
    private Document readXml;
    private ArrayList<Integer> ids = new ArrayList<>();
    private String f;
    MapGUI gui;

    public void initializeTrip(String selectionXml) throws SAXException, IOException, ParserConfigurationException {
        svg = new SVGBuilder();
        xml = new XMLBuilder();
        readXML(selectionXml);
    }
    
    public void readXML(String selectionXml) throws SAXException, IOException, ParserConfigurationException {
        File xmlFile = new File(selectionXml);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        readXml = dBuilder.parse(xmlFile);
        readXml.getDocumentElement().normalize();
        System.out.println("*Testing*   Root element :" + readXml.getDocumentElement().getNodeName());
        NodeList nList = readXml.getElementsByTagName("destinations");
        for(int temp = 0; temp < nList.getLength(); temp++){
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());
            if(nNode.getNodeType() == Node.ELEMENT_NODE){
                Element eElement = (Element) nNode;
                int i = 0;
                while(eElement.getElementsByTagName("id").item(i) != null){
                    ids.add(Integer.parseInt(eElement.getElementsByTagName("id").item(i).getTextContent()));
                    i++;
                }
            }
        }
        for(int i  = 0; i < ids.size();i++){
            System.out.println("id at index " + i + " = " + ids.get(i));
        }
    }

    public void setCallback(Consumer<String> callback) {
        this.callback = callback;
    }

    public void userAddLoc(String id) {
        callback.accept(id);
    }

    public void addLeg(String id, String start, String finish, int mileage) {
        xml.addLeg(id, start, finish, mileage);
    }

    public void addFinalLeg(String id, String s, String f, int t) {
        xml.addLeg(id, s, f, t);
    }

    public void addLine(double x1, double y1, double x2, double y2, String id) {
        svg.addLine(x1, y1, x2, y2, id);
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

    public void gui() {
        gui = new MapGUI(f);
        try {
            gui.init();
        } catch(Exception e) {
            System.err.println(e);
        }
    }

    public void refresh() throws Exception {
        gui.refresh();
    }

    Document getXMLdoc() {
        return xml.getXMLdoc();
    }

    Document getSVGdoc() {
        return svg.getSVGdoc();
    }

    ArrayList<Integer> getIdArrayList(){
        return ids;
    }

    public static void main(String[] argv) throws ParserConfigurationException, TransformerException {

    }
}
