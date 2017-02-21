package View;


import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by SummitDrift on 2/13/17.
 */
public class TestView {
    @Before
    public void initalize() {

    }
    private View v = new View();

    @Test
    public void initializeTrip() throws Exception {
        v.initializeTrip();
        View one = new View();
        one.initializeTrip();
        assertEquals(one.getXMLdoc(), v.getXMLdoc());
    }

    @Test
    public void addLeg() throws Exception {
        v.initializeTrip();
        View one = new View();
        one.initializeTrip();
        assertEquals(one.getXMLdoc().getFirstChild(), v.getXMLdoc().getFirstChild());
    }

    @Test
    public void addFinalLeg() throws Exception {
        v.initializeTrip();
        View one = new View();
        one.initializeTrip();
        assertEquals(one.getXMLdoc().getFirstChild(), v.getXMLdoc().getFirstChild());
    }

    @Test
    public void addLine() throws Exception {
        v.initializeTrip();
        View one = new View();
        one.initializeTrip();
        assertEquals(one.getXMLdoc().getFirstChild(), v.getXMLdoc().getFirstChild());
    }

    @Test
    public void addDistance() throws Exception {
        v.initializeTrip();
        View one = new View();
        one.initializeTrip();
        assertEquals(one.getXMLdoc().getFirstChild(), v.getXMLdoc().getFirstChild());
    }

    @Test
    public void addCityNameLabel() throws Exception {
        v.initializeTrip();
        View one = new View();
        one.initializeTrip();
        assertEquals(one.getXMLdoc().getFirstChild(), v.getXMLdoc().getFirstChild());
    }

    @Test
    public void addIDLabel() throws Exception {
        v.initializeTrip();
        View one = new View();
        one.initializeTrip();
        assertEquals(one.getXMLdoc().getFirstChild(), v.getXMLdoc().getFirstChild());
    }

    @Test
    public void addHeader() throws Exception {
        v.initializeTrip();
        View one = new View();
        one.initializeTrip();
        assertEquals(one.getXMLdoc().getFirstChild(), v.getXMLdoc().getFirstChild());
    }

    @Test
    public void addFooter() throws Exception {
        v.initializeTrip();
        View one = new View();
        one.initializeTrip();
        assertEquals(one.getXMLdoc().getFirstChild(), v.getXMLdoc().getFirstChild());
    }

    @Test
    public void addBorders() throws Exception {
        v.initializeTrip();
        View one = new View();
        one.initializeTrip();
        assertEquals(one.getXMLdoc().getFirstChild(), v.getXMLdoc().getFirstChild());
    }

    @Test
    public void finalizeTrip() throws Exception {
        v.initializeTrip();
        View one = new View();
        one.initializeTrip();
        assertEquals(one.getXMLdoc().getFirstChild(), v.getXMLdoc().getFirstChild());
    }

}
