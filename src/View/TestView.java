package View;


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
    public void initalize() throws ParserConfigurationException {
        v.initializeTrip();
        v.addLeg(1,"denver","fort collins", 9999);
        /*
        v.addFinalLeg(2,"denver","fort collins", 9999);
        v.addLine(-109,41,-102,37,1);
        v.addDistance(-109,41,-102,37,9999,1);
        v.addCityNameLabel(-109,41,"denver");
        v.addIDLabel();
        v.addBorders();
        v.addHeader("Colorado");
        v.
        */
    }



    @Test
    public void initializeTrip() throws Exception {
        //v.initializeTrip();
        View one = new View();
        one.initializeTrip();
        one.addLeg(1,"denver","fort collins", 9999);
        assertEquals(one.getXMLdoc(), v.getXMLdoc());
    }

    @Test
    public void addLeg() throws Exception {
        //v.initializeTrip();
        View one = new View();
        one.initializeTrip();
        one.addLeg(1,"denver","fort collins", 9999);
        assertEquals(one.getXMLdoc().getFirstChild(), v.getXMLdoc().getFirstChild());
    }

    @Test
    public void addFinalLeg() throws Exception {
        //v.initializeTrip();
        View one = new View();
        one.initializeTrip();
        one.addLeg(1,"denver","fort collins", 9999);
        assertEquals(one.getXMLdoc().getFirstChild(), v.getXMLdoc().getFirstChild());
    }

    @Test
    public void addLine() throws Exception {
        //v.initializeTrip();
        View one = new View();
        one.initializeTrip();

        one.addLeg(1,"denver","fort collins", 9999);
        assertEquals(one.getXMLdoc().getFirstChild(), v.getXMLdoc().getFirstChild());
    }

    @Test
    public void addDistance() throws Exception {
        //v.initializeTrip();
        View one = new View();
        one.initializeTrip();
        one.addLeg(1,"denver","fort collins", 9999);
        assertEquals(one.getXMLdoc().getFirstChild(), v.getXMLdoc().getFirstChild());
    }

    @Test
    public void addCityNameLabel() throws Exception {
        //v.initializeTrip();
        View one = new View();
        one.initializeTrip();
        one.addLeg(1,"denver","fort collins", 9999);
        assertEquals(one.getXMLdoc().getFirstChild(), v.getXMLdoc().getFirstChild());
    }

    @Test
    public void addIDLabel() throws Exception {
        //v.initializeTrip();
        View one = new View();
        one.initializeTrip();
        one.addLeg(1,"denver","fort collins", 9999);
        assertEquals(one.getXMLdoc().getFirstChild(), v.getXMLdoc().getFirstChild());
    }

    @Test
    public void addHeader() throws Exception {
        //v.initializeTrip();
        View one = new View();
        one.initializeTrip();
        one.addLeg(1,"denver","fort collins", 9999);
        assertEquals(one.getXMLdoc().getFirstChild(), v.getXMLdoc().getFirstChild());
    }

    @Test
    public void addFooter() throws Exception {
        //v.initializeTrip();
        View one = new View();
        one.initializeTrip();
        one.addLeg(1,"denver","fort collins", 9999);
        assertEquals(one.getXMLdoc().getFirstChild(), v.getXMLdoc().getFirstChild());
    }

    @Test
    public void addBorders() throws Exception {
        //v.initializeTrip();
        View one = new View();
        one.initializeTrip();
        one.addLeg(1,"denver","fort collins", 9999);
        assertEquals(one.getXMLdoc().getFirstChild(), v.getXMLdoc().getFirstChild());
    }

    @Test
    public void finalizeTrip() throws Exception {
        //v.initializeTrip();
        View one = new View();
        one.initializeTrip();
        one.addLeg(1,"denver","fort collins", 9999);
        assertEquals(one.getXMLdoc().getFirstChild(), v.getXMLdoc().getFirstChild());
    }

}
