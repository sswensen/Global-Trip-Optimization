package com.csu2017sp314.dtr07.View;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * Created by SummitDrift on 3/6/17.
 * @author Scott Swensen
 */

public interface MapView {
    void initializeTrip() throws SAXException, IOException, ParserConfigurationException;

    void addLeg(String id, String start, String finish, int mileage);

    void addFinalLeg(String id, String start, String finish, int totalMileage);

    void addLine(double x1, double y1, double x2, double y2, String id);

    void addDistance(double x1, double y1, double x2, double y2, int distanceBetween, String id);

    void addCityNameLabel(double lon, double lat, String city);

    void addIDLabel(double lon, double lat, String id);

    void addHeader(String title);

    void addFooter(int totalDistance);

    void addBorders();

    void finalizeTrip(String filename) throws TransformerException;
}
