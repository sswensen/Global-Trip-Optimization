package com.csu2017sp314.dtr07.View;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/*
 * Created by SummitDrift on 3/6/17.
 * All code for creating XML doc
 */
class XMLBuilder {
    private Document XMLdoc;

    XMLBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();

        XMLdoc = docBuilder.newDocument();
        Element rootElement = XMLdoc.createElement("trip");
        XMLdoc.appendChild(rootElement);
    }

    void addLeg(String id, String start, String finish, int mileage) {
        //leg grouping
        Element leg = XMLdoc.createElement("leg");
        XMLdoc.getDocumentElement().appendChild(leg);

        //Sequence element
        Element sequenceElement = XMLdoc.createElement("sequence");
        sequenceElement.appendChild(XMLdoc.createTextNode(id));
        leg.appendChild(sequenceElement);

        //Start element
        Element startElement = XMLdoc.createElement("start");
        startElement.appendChild(XMLdoc.createTextNode(start));
        leg.appendChild(startElement);

        //finish element
        Element finishElement = XMLdoc.createElement("finish");
        finishElement.appendChild(XMLdoc.createTextNode(finish));
        leg.appendChild(finishElement);

        //mileage element
        Element mileageElement = XMLdoc.createElement("mileage");
        mileageElement.appendChild(XMLdoc.createTextNode(Integer.toString(mileage)));
        leg.appendChild(mileageElement);
    }

    Document getXMLdoc() {
        return XMLdoc;
    }
}
