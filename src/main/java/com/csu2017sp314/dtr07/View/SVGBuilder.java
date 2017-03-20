package com.csu2017sp314.dtr07.View;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by SummitDrift on 3/6/17.
 * @author Scott Swensen
 * All code for creating SVG doc
 */

class SVGBuilder {
    private Document SVGdoc;
    private int labelID = 1;

    SVGBuilder(String svgMap) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();

        //Creating the SVG document
        String filepath = "";
        if(!svgMap.isEmpty()) {
             filepath = svgMap;
        } else {
            filepath = "src/test/resources/coloradoMap.svg";
        }
        SVGdoc = docBuilder.parse(filepath);
        /*String svgNS = "http://www.w3.org/2000/svg";
		DOMImplementation impl = docBuilder.getDOMImplementation();
		SVGdoc = impl.createDocument(svgNS, "svg", null);
		Element svgRoot = SVGdoc.getDocumentElement();
		svgRoot.setAttribute("width", "1280");
		svgRoot.setAttribute("height", "1024");
		svgRoot.setAttribute("xmlns", "http://www.w3.org/2000/svg");
		svgRoot.setAttribute("xmlns:svg", "http://www.w3.org/2000/svg");*/
    }

    private double convertLongitudeCoordinates(double x) {
        double xPixels = 993.54946; //Width of colorado map
        double startX = -109;
        double endX = -102;
        //Convert to SVG 'x' coordinate
        double strideX = endX - startX;
        double relativeX = (x - startX);
        double realX = relativeX * (xPixels / strideX);
        return (realX + 34.72952);
    }

    private double convertLatitudeCoordinates(double y) {
        double yPixels = 709.98902; //Height of colorado map
        double startY = 41;
        double endY = 37;
        //Convert to SVG 'y' coordinate
        double strideY = endY - startY;
        double relativeY = (y - startY);
        double realY = relativeY * (yPixels / strideY);
        return (realY + 34.76269);
    }

    void addLine(double x1, double y1, double x2, double y2, String id) {
        Element line = SVGdoc.createElement("line");
        line.setAttribute("id", ("leg" + id));
        line.setAttribute("x1", Double.toString(convertLongitudeCoordinates(x1)));
        line.setAttribute("y1", Double.toString(convertLatitudeCoordinates(y1)));
        line.setAttribute("x2", Double.toString(convertLongitudeCoordinates(x2)));
        line.setAttribute("y2", Double.toString(convertLatitudeCoordinates(y2)));
        line.setAttribute("stroke-width", "3");
        line.setAttribute("stroke", "#999999");
        SVGdoc.getDocumentElement().appendChild(line);
    }

    void addDistance(double x1, double y1, double x2, double y2, int distanceBetween, String id) {
        Element distance = SVGdoc.createElement("text");
        distance.setAttribute("font-family", "Sans-serif");
        distance.setAttribute("font-size", "16");
        distance.setAttribute("id", ("leg" + id));
        distance.setAttribute("x", Double.toString((convertLongitudeCoordinates(x1) + convertLongitudeCoordinates(x2)) / 2));
        distance.setAttribute("y", Double.toString((convertLatitudeCoordinates(y1) + convertLatitudeCoordinates(y2)) / 2));
        distance.setTextContent(Integer.toString(distanceBetween));
        SVGdoc.getDocumentElement().appendChild(distance);
    }

    void addCityNameLabel(double longitude, double latitude, String city) {
        Element label = SVGdoc.createElement("text");
        label.setAttribute("font-family", "Sans-serif");
        label.setAttribute("font-size", "16");
        label.setAttribute("id", "id" + labelID);
        labelID++;
        label.setAttribute("x", Double.toString(convertLongitudeCoordinates(longitude)));
        label.setAttribute("y", Double.toString(convertLatitudeCoordinates(latitude)));
        label.setTextContent(city);
        SVGdoc.getDocumentElement().appendChild(label);
    }

    void addIDLabel(double longitude, double latitude, String id) {
        Element IDLabel = SVGdoc.createElement("text");
        IDLabel.setAttribute("font-family", "Sans-serif");
        IDLabel.setAttribute("font-size", "16");
        IDLabel.setAttribute("id", "id" + labelID);
        labelID++;
        IDLabel.setAttribute("x", Double.toString(convertLongitudeCoordinates(longitude)));
        IDLabel.setAttribute("y", Double.toString(convertLatitudeCoordinates(latitude)));
        IDLabel.setTextContent(id);
        SVGdoc.getDocumentElement().appendChild(IDLabel);
    }

    void addHeader(String title) {
        //<text text-anchor="middle" font-family="Sans-serif" font-size="24" id="state" y="40" x="640">Colorado</text>
        Element header = SVGdoc.createElement("text");
        header.setAttribute("text-anchor", "middle");
        header.setAttribute("font-family", "Sans-serif");
        header.setAttribute("font-size", "24");
        header.setAttribute("id", "state");
        header.setAttribute("x", "532");
        header.setAttribute("y", "28");
        header.setTextContent(title);
        SVGdoc.getDocumentElement().appendChild(header);
    }

    void addFooter(int totalDistance) {
        Element footer = SVGdoc.createElement("text");
        footer.setAttribute("text-anchor", "middle");
        footer.setAttribute("font-family", "Sans-serif");
        footer.setAttribute("font-size", "24");
        footer.setAttribute("id", "distance");
        footer.setAttribute("x", "532");
        footer.setAttribute("y", "773");
        footer.setTextContent(totalDistance + " miles");
        SVGdoc.getDocumentElement().appendChild(footer);
    }

    void addBorders() {
        //North border
        Element northBorder = SVGdoc.createElement("line");
        northBorder.setAttribute("id", "north");
        northBorder.setAttribute("x1", "50");
        northBorder.setAttribute("y1", "174.8571429");
        northBorder.setAttribute("x2", "1230");
        northBorder.setAttribute("y2", "174.8571429");
        northBorder.setAttribute("stroke-width", "5");
        northBorder.setAttribute("stroke", "#666666");

        //East border
        Element eastBorder = SVGdoc.createElement("line");
        eastBorder.setAttribute("id", "east");
        eastBorder.setAttribute("x1", "1230");
        eastBorder.setAttribute("y1", "174.8571429");
        eastBorder.setAttribute("x2", "1230");
        eastBorder.setAttribute("y2", "849.1428572");
        eastBorder.setAttribute("stroke-width", "5");
        eastBorder.setAttribute("stroke", "#666666");

        //South border
        Element southBorder = SVGdoc.createElement("line");
        southBorder.setAttribute("id", "south");
        southBorder.setAttribute("x1", "1230");
        southBorder.setAttribute("y1", "849.1428572");
        southBorder.setAttribute("x2", "50");
        southBorder.setAttribute("y2", "849.1428572");
        southBorder.setAttribute("stroke-width", "5");
        southBorder.setAttribute("stroke", "#666666");

        //West border
        Element westBorder = SVGdoc.createElement("line");
        westBorder.setAttribute("id", "west");
        westBorder.setAttribute("x1", "50");
        westBorder.setAttribute("y1", "849.1428572");
        westBorder.setAttribute("x2", "50");
        westBorder.setAttribute("y2", "174.8571429");
        westBorder.setAttribute("stroke-width", "5");
        westBorder.setAttribute("stroke", "#666666");

        //Append all borders to document
        SVGdoc.getDocumentElement().appendChild(northBorder);
        SVGdoc.getDocumentElement().appendChild(eastBorder);
        SVGdoc.getDocumentElement().appendChild(southBorder);
        SVGdoc.getDocumentElement().appendChild(westBorder);
    }

    Document getSVGdoc() {
        return SVGdoc;
    }
}
