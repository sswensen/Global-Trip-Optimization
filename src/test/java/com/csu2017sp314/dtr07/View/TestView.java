package com.csu2017sp314.dtr07.View;


import static org.junit.Assert.*;

import jdk.nashorn.internal.runtime.ECMAException;
import org.junit.Before;
import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;

/**
 * Created by SummitDrift on 2/13/17.
 */
public class TestView {

    private View v = new View();

    @Before
    public void initialize() throws ParserConfigurationException {

    }

    @Test
    public void setOptions() throws Exception{
        ArrayList<String> temp = v.getOptions();
        temp.add("hello");
        assertEquals("hello", temp.get(0));
    }

    @Test
    public void getOptions() throws Exception{
        ArrayList<String> temp = v.getOptions();
        assertEquals(temp.size(), v.getOptions().size());
    }

    @Test
    public void setKilometers() throws Exception{
        v.setKilometers(true);
        assertEquals(true, v.getKilometers());
    }

    @Test
    public void getKilometers() throws Exception{
        assertEquals(false, v.getKilometers());
    }

    @Test
    public void initializeTrip() throws Exception {
        v.initializeTrip("S3/world2.svg");
        v.addLeg("1","Denver International Airport",Double.toString(39.861698150635), Double.toString(-104.672996521),"","","","","","","","","","","","","","","","","","","","","",5000,"");
        View one = new View();
        one.initializeTrip("S3/world2.svg");
        one.addLeg("1","Denver International Airport",Double.toString(39.861698150635), Double.toString(-104.672996521),"","","","","","","","","","","","","","","","","","","","","",5000,"");
        assertEquals(one.getXMLdoc().getDocumentElement().getFirstChild().getFirstChild().getNextSibling().getFirstChild().getNodeValue(), v.getXMLdoc().getDocumentElement().getFirstChild().getFirstChild().getNextSibling().getFirstChild().getNodeValue());
    }

    @Test
    public void addLeg() throws Exception {
        v.initializeTrip("S3/world2.svg");
        v.addLeg("1","Denver International Airport",Double.toString(39.861698150635), Double.toString(-104.672996521),"","","","","","","","","","","","","","","","","","","","","",5000,"");
        View one = new View();
        one.initializeTrip("S3/world2.svg");
        one.addLeg("1","Denver International Airport",Double.toString(39.861698150635), Double.toString(-104.672996521),"","","","","","","","","","","","","","","","","","","","","",5000,"");
        assertEquals(one.getXMLdoc().getDocumentElement().getFirstChild().getFirstChild().getNextSibling().getFirstChild().getNodeValue(), v.getXMLdoc().getDocumentElement().getFirstChild().getFirstChild().getNextSibling().getFirstChild().getNodeValue());
    }

    @Test
    public void addFinalLeg() throws Exception {
        v.initializeTrip("S3/world2.svg");
        v.addLeg("1","Denver International Airport",Double.toString(39.861698150635), Double.toString(-104.672996521),"","","","","","","","","","","","","","","","","","","","","",5000,"");
        View one = new View();
        one.initializeTrip("S3/world2.svg");
        one.addLeg("1","Denver International Airport",Double.toString(39.861698150635), Double.toString(-104.672996521),"","","","","","","","","","","","","","","","","","","","","",5000,"");
        assertEquals(one.getXMLdoc().getDocumentElement().getFirstChild().getFirstChild().getNextSibling().getFirstChild().getNodeValue(), v.getXMLdoc().getDocumentElement().getFirstChild().getFirstChild().getNextSibling().getFirstChild().getNodeValue());
    }

    @Test
    public void addLine() throws Exception {
        v.initializeTrip("S3/world2.svg");
        v.addLine(-109,41,-102,37,"1", false);
        View one = new View();
        one.initializeTrip("S3/world2.svg");
        one.addLine(-109,41,-102,37,"1", false);
        assertEquals(one.getSVGdoc().getDocumentElement().getFirstChild().getNodeName(), v.getSVGdoc().getDocumentElement().getFirstChild().getNodeName());
    }

    @Test
    public void addDistance() throws Exception {
        v.initializeTrip("S3/world2.svg");
        v.addDistance(-109,41,-102,37,1, "2", true);
        View one = new View();
        one.initializeTrip("S3/world2.svg");
        one.addDistance(-109,41,-102,37,1, "2", true);
        assertEquals(one.getSVGdoc().getDocumentElement().getFirstChild().getNodeName(), v.getSVGdoc().getDocumentElement().getFirstChild().getNodeName());
    }

    @Test
    public void addCityNameLabel() throws Exception {
        v.initializeTrip("S3/world2.svg");
        v.addCityNameLabel(-109,41,"Denver");
        View one = new View();
        one.initializeTrip("S3/world2.svg");
        one.addCityNameLabel(-109,41,"Denver");
        assertEquals(one.getSVGdoc().getDocumentElement().getFirstChild().getNodeName(), v.getSVGdoc().getDocumentElement().getFirstChild().getNodeName());
    }

    @Test
    public void addIDLabel() throws Exception {
        v.initializeTrip("S3/world2.svg");
        v.addCityNameLabel(-109, 41, "Denver");
        View one = new View();
        one.initializeTrip("S3/world2.svg");
        one.addCityNameLabel(-109, 41, "Denver");
        assertEquals(one.getSVGdoc().getDocumentElement().getFirstChild().getNodeName(), v.getSVGdoc().getDocumentElement().getFirstChild().getNodeName());
    }

    @Test
    public void addHeader() throws Exception {
        v.initializeTrip("S3/world2.svg");
        v.addHeader("Denver");
        View one = new View();
        one.initializeTrip("S3/world2.svg");
        one.addHeader("Denver");
        assertEquals(one.getSVGdoc().getDocumentElement().getFirstChild().getNodeName(), v.getSVGdoc().getDocumentElement().getFirstChild().getNodeName());
    }

    @Test
    public void addFooter() throws Exception {
        v.initializeTrip("S3/world2.svg");
        v.addFooter(9999);
        View one = new View();
        one.initializeTrip("S3/world2.svg");
        one.addFooter(9999);
        assertEquals(one.getSVGdoc().getDocumentElement().getFirstChild().getNodeName(), v.getSVGdoc().getDocumentElement().getFirstChild().getNodeName());
    }

    @Test
    public void addBorders() throws Exception {
        v.initializeTrip("S3/world2.svg");
        v.addBorders();
        View one = new View();
        one.initializeTrip("S3/world2.svg");
        one.addBorders();
        assertEquals(one.getSVGdoc().getDocumentElement().getFirstChild().getNodeName(), v.getSVGdoc().getDocumentElement().getFirstChild().getNodeName());
    }

    @Test
    public void finalizeTrip() throws Exception {
        v.initializeTrip("S3/world2.svg");
        v.addLeg("1", "Denver International Airport", Double.toString(39.861698150635), Double.toString(-104.672996521), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 5000, "");
        View one = new View();
        one.initializeTrip("S3/world2.svg");
        one.addLeg("1", "Denver International Airport", Double.toString(39.861698150635), Double.toString(-104.672996521), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 5000, "");
        assertEquals(one.getXMLdoc().getDocumentElement().getFirstChild().getFirstChild().getNextSibling().getFirstChild().getNodeValue(), v.getXMLdoc().getDocumentElement().getFirstChild().getFirstChild().getNextSibling().getFirstChild().getNodeValue());
    }

}
