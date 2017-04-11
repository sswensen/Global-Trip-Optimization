package com.csu2017sp314.dtr07.View;

import org.junit.Test;
import org.w3c.dom.Document;

import static org.junit.Assert.*;

/**
 * Created by SummitDrift on 3/20/17.
 */
public class SVGBuilderTest {
    private Document SVGdoc;

    @Test
    public void addLine() throws Exception {
        SVGBuilder svg1 = new SVGBuilder("");
        SVGBuilder svg2 = new SVGBuilder("");
        svg1.addLine(1, 1, 1, 1, "HAY");
        svg2.addLine(1, 1, 1, 1, "HAY");
        assertEquals(svg1.getSVGdoc().getDocumentElement().getFirstChild().getNodeName(), svg2.getSVGdoc().getDocumentElement().getFirstChild().getNodeName());
    }

    @Test
    public void addDistance() throws Exception {
        SVGBuilder svg1 = new SVGBuilder("");
        SVGBuilder svg2 = new SVGBuilder("");
        svg1.addDistance(1, 1, 1, 1, 100, "HAY");
        svg2.addDistance(1, 1, 1, 1, 100, "HAY");
        assertEquals(svg1.getSVGdoc().getDocumentElement().getFirstChild().getNodeName(), svg2.getSVGdoc().getDocumentElement().getFirstChild().getNodeName());
    }

    @Test
    public void addCityNameLabel() throws Exception {
        SVGBuilder svg1 = new SVGBuilder("");
        SVGBuilder svg2 = new SVGBuilder("");
        svg1.addCityNameLabel(1, 1, "HAY");
        svg2.addCityNameLabel(1, 1, "HAY");
        assertEquals(svg1.getSVGdoc().getDocumentElement().getFirstChild().getNodeName(), svg2.getSVGdoc().getDocumentElement().getFirstChild().getNodeName());
    }

    @Test
    public void addIDLabel() throws Exception {
        SVGBuilder svg1 = new SVGBuilder("");
        SVGBuilder svg2 = new SVGBuilder("");
        svg1.addIDLabel(1, 1, "HAY");
        svg2.addIDLabel(1, 1, "HAY");
        assertEquals(svg1.getSVGdoc().getDocumentElement().getFirstChild().getNodeName(), svg2.getSVGdoc().getDocumentElement().getFirstChild().getNodeName());
    }

    @Test
    public void addHeader() throws Exception {
        SVGBuilder svg1 = new SVGBuilder("");
        SVGBuilder svg2 = new SVGBuilder("");
        svg1.addHeader("HAY");
        svg2.addHeader("HAY");
        assertEquals(svg1.getSVGdoc().getDocumentElement().getFirstChild().getNodeName(), svg2.getSVGdoc().getDocumentElement().getFirstChild().getNodeName());
    }

    @Test
    public void addFooter() throws Exception {
        SVGBuilder svg1 = new SVGBuilder("");
        SVGBuilder svg2 = new SVGBuilder("");
        svg1.addFooter(1000);
        svg2.addFooter(1000);
        assertEquals(svg1.getSVGdoc().getDocumentElement().getFirstChild().getNodeName(), svg2.getSVGdoc().getDocumentElement().getFirstChild().getNodeName());
    }

    @Test
    public void addBorders() throws Exception {
        SVGBuilder svg1 = new SVGBuilder("");
        SVGBuilder svg2 = new SVGBuilder("");
        svg1.addBorders();
        svg2.addBorders();
        assertEquals(svg1.getSVGdoc().getDocumentElement().getFirstChild().getNodeName(), svg2.getSVGdoc().getDocumentElement().getFirstChild().getNodeName());
    }

    @Test
    public void isKilometers() throws Exception {
        SVGBuilder svg = new SVGBuilder("");
        svg.setKilometers(true);
        assertEquals(true, svg.isKilometers());
    }
}