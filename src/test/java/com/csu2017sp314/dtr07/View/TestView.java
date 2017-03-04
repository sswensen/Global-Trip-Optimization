package com.csu2017sp314.dtr07.View;


import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by SummitDrift on 2/13/17.
 */
public class TestView {
    private View v = new View();
    @Before
    public void initialize() throws ParserConfigurationException {

    }

    @Test
    public void initializeTrip() throws Exception {
        v.initializeTrip();
        v.addLeg("1","denver","fort collins", 9999);
        View one = new View();
        one.initializeTrip();
        one.addLeg("1","denver","fort collins", 9999);
        assertEquals(one.getXMLdoc().getDocumentElement().getFirstChild().getFirstChild().getNextSibling().getFirstChild().getNodeValue(), v.getXMLdoc().getDocumentElement().getFirstChild().getFirstChild().getNextSibling().getFirstChild().getNodeValue());
    }

    @Test
    public void addLeg() throws Exception {
        v.initializeTrip();
        v.addLeg("1","denver","fort collins", 9999);
        View one = new View();
        one.initializeTrip();
        one.addLeg("1","denver","fort collins", 9999);
        assertEquals(one.getXMLdoc().getDocumentElement().getFirstChild().getFirstChild().getNextSibling().getFirstChild().getNodeValue(), v.getXMLdoc().getDocumentElement().getFirstChild().getFirstChild().getNextSibling().getFirstChild().getNodeValue());
    }

    @Test
    public void addFinalLeg() throws Exception {
        v.initializeTrip();
        v.addLeg("1","denver","fort collins", 9999);
        View one = new View();
        one.initializeTrip();
        one.addLeg("1","denver","fort collins", 9999);
        assertEquals(one.getXMLdoc().getDocumentElement().getFirstChild().getFirstChild().getNextSibling().getFirstChild().getNodeValue(), v.getXMLdoc().getDocumentElement().getFirstChild().getFirstChild().getNextSibling().getFirstChild().getNodeValue());
    }

    @Test
    public void addLine() throws Exception {
        v.initializeTrip();
        v.addLine(-109,41,-102,37,"1");
        View one = new View();
        one.initializeTrip();
        one.addLine(-109,41,-102,37,"1");
        assertEquals(one.getSVGdoc().getDocumentElement().getFirstChild().getNodeName(), v.getSVGdoc().getDocumentElement().getFirstChild().getNodeName());
    }

    @Test
    public void addDistance() throws Exception {
        v.initializeTrip();
        v.addDistance(-109,41,-102,37,1, "2");
        View one = new View();
        one.initializeTrip();
        one.addDistance(-109,41,-102,37,1, "2");
        assertEquals(one.getSVGdoc().getDocumentElement().getFirstChild().getNodeName(), v.getSVGdoc().getDocumentElement().getFirstChild().getNodeName());
    }

    @Test
    public void addCityNameLabel() throws Exception {
        v.initializeTrip();
        v.addCityNameLabel(-109,41,"Denver");
        View one = new View();
        one.initializeTrip();
        one.addCityNameLabel(-109,41,"Denver");
        assertEquals(one.getSVGdoc().getDocumentElement().getFirstChild().getNodeName(), v.getSVGdoc().getDocumentElement().getFirstChild().getNodeName());
    }

    @Test
    public void addIDLabel() throws Exception {
        v.initializeTrip();
        v.addCityNameLabel(-109, 41, "Denver");
        View one = new View();
        one.initializeTrip();
        one.addCityNameLabel(-109, 41, "Denver");
        assertEquals(one.getSVGdoc().getDocumentElement().getFirstChild().getNodeName(), v.getSVGdoc().getDocumentElement().getFirstChild().getNodeName());
    }

    @Test
    public void addHeader() throws Exception {
        v.initializeTrip();
        v.addHeader("Denver");
        View one = new View();
        one.initializeTrip();
        one.addHeader("Denver");
        assertEquals(one.getSVGdoc().getDocumentElement().getFirstChild().getNodeName(), v.getSVGdoc().getDocumentElement().getFirstChild().getNodeName());
    }

    @Test
    public void addFooter() throws Exception {
        v.initializeTrip();
        v.addFooter(9999);
        View one = new View();
        one.initializeTrip();
        one.addFooter(9999);
        assertEquals(one.getSVGdoc().getDocumentElement().getFirstChild().getNodeName(), v.getSVGdoc().getDocumentElement().getFirstChild().getNodeName());
    }

    @Test
    public void addBorders() throws Exception {
        v.initializeTrip();
        v.addBorders();
        View one = new View();
        one.initializeTrip();
        one.addBorders();
        assertEquals(one.getSVGdoc().getDocumentElement().getFirstChild().getNodeName(), v.getSVGdoc().getDocumentElement().getFirstChild().getNodeName());
    }

    @Test
    public void finalizeTrip() throws Exception {
        v.initializeTrip();
        v.addLeg("1","denver","fort collins", 9999);
        View one = new View();
        one.initializeTrip();
        one.addLeg("1","denver","fort collins", 9999);
        assertEquals(one.getXMLdoc().getDocumentElement().getFirstChild().getFirstChild().getNextSibling().getFirstChild().getNodeValue(), v.getXMLdoc().getDocumentElement().getFirstChild().getFirstChild().getNextSibling().getFirstChild().getNodeValue());
    }

}
