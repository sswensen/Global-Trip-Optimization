package com.csu2017sp314.dtr07.View;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by SummitDrift on 3/6/17.
 * @author Scott Swensen
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

    void addLeg(String sequence, String id, String name, String latitude, String longitude, String elevation, String municipality, String region, String country, String continent, String airportURL, String regionURL, String countryURL
    , String id2, String name2, String latitude2, String longitude2, String elevation2, String municipality2, String region2, String country2, String continent2, String airportURL2, String regionURL2, String countryURL2, int distanceBetween, String units) {
        //leg grouping
        Element leg = XMLdoc.createElement("leg");
        XMLdoc.getDocumentElement().appendChild(leg);

        //Sequence element
        Element sequenceElement = XMLdoc.createElement("sequence");
        sequenceElement.appendChild(XMLdoc.createTextNode(sequence));
        leg.appendChild(sequenceElement);

        //Start element
        Element startElement = XMLdoc.createElement("start");

            Element idElement = XMLdoc.createElement("id");
            idElement.appendChild(XMLdoc.createTextNode(id));
            startElement.appendChild(idElement);

            Element nameElement = XMLdoc.createElement("name");
            nameElement.appendChild(XMLdoc.createTextNode(name));
            startElement.appendChild(nameElement);

            Element latitudeElement = XMLdoc.createElement("latitude");
            latitudeElement.appendChild(XMLdoc.createTextNode(latitude));
            startElement.appendChild(latitudeElement);

            Element longitudeElement = XMLdoc.createElement("longitude");
            longitudeElement.appendChild(XMLdoc.createTextNode(longitude));
            startElement.appendChild(longitudeElement);

            Element elevationElement = XMLdoc.createElement("elevation");
            elevationElement.appendChild(XMLdoc.createTextNode(elevation));
            startElement.appendChild(elevationElement);

            Element municipalityElement = XMLdoc.createElement("municipality");
            municipalityElement.appendChild(XMLdoc.createTextNode(municipality));
            startElement.appendChild(municipalityElement);

            Element regionElement = XMLdoc.createElement("region");
            regionElement.appendChild(XMLdoc.createTextNode(region));
            startElement.appendChild(regionElement);

            Element countryElement = XMLdoc.createElement("country");
            countryElement.appendChild(XMLdoc.createTextNode(country));
            startElement.appendChild(countryElement);

            Element continentElement = XMLdoc.createElement("continent");
            continentElement.appendChild(XMLdoc.createTextNode(continent));
            startElement.appendChild(continentElement);

            Element airportURLElement = XMLdoc.createElement("airportURL");
            airportURLElement.appendChild(XMLdoc.createTextNode(airportURL));
            startElement.appendChild(airportURLElement);

            Element regionURLElement = XMLdoc.createElement("regionURL");
            regionURLElement.appendChild(XMLdoc.createTextNode(regionURL));
            startElement.appendChild(regionURLElement);

            Element countryURLElement = XMLdoc.createElement("countryURL");
            countryURLElement.appendChild(XMLdoc.createTextNode(countryURL));
            startElement.appendChild(countryURLElement);

        leg.appendChild(startElement);

        //Start element
        Element finishElement = XMLdoc.createElement("start");

            Element idElement2 = XMLdoc.createElement("id");
            idElement2.appendChild(XMLdoc.createTextNode(id2));
            finishElement.appendChild(idElement2);

            Element nameElement2 = XMLdoc.createElement("name");
            nameElement2.appendChild(XMLdoc.createTextNode(name2));
            finishElement.appendChild(nameElement2);

            Element latitudeElement2 = XMLdoc.createElement("latitude");
            latitudeElement2.appendChild(XMLdoc.createTextNode(latitude2));
            finishElement.appendChild(latitudeElement2);

            Element longitudeElement2 = XMLdoc.createElement("longitude");
            longitudeElement2.appendChild(XMLdoc.createTextNode(longitude2));
            finishElement.appendChild(longitudeElement2);

            Element elevationElement2 = XMLdoc.createElement("elevation");
            elevationElement2.appendChild(XMLdoc.createTextNode(elevation2));
            finishElement.appendChild(elevationElement2);

            Element municipalityElement2 = XMLdoc.createElement("municipality");
            municipalityElement2.appendChild(XMLdoc.createTextNode(municipality2));
            finishElement.appendChild(municipalityElement2);

            Element regionElement2 = XMLdoc.createElement("region");
            regionElement2.appendChild(XMLdoc.createTextNode(region2));
            finishElement.appendChild(regionElement2);

            Element countryElement2 = XMLdoc.createElement("country");
            countryElement2.appendChild(XMLdoc.createTextNode(country2));
            finishElement.appendChild(countryElement2);

            Element continentElement2 = XMLdoc.createElement("continent");
            continentElement2.appendChild(XMLdoc.createTextNode(continent2));
            finishElement.appendChild(continentElement2);

            Element airportURLElement2 = XMLdoc.createElement("airportURL");
            airportURLElement2.appendChild(XMLdoc.createTextNode(airportURL2));
            finishElement.appendChild(airportURLElement2);

            Element regionURLElement2 = XMLdoc.createElement("regionURL");
            regionURLElement2.appendChild(XMLdoc.createTextNode(regionURL2));
            finishElement.appendChild(regionURLElement2);

            Element countryURLElement2 = XMLdoc.createElement("countryURL");
            countryURLElement2.appendChild(XMLdoc.createTextNode(countryURL2));
            finishElement.appendChild(countryURLElement2);

        leg.appendChild(finishElement);

        //Distance element
        Element distance = XMLdoc.createElement("ditance");
        distance.appendChild(XMLdoc.createTextNode(Integer.toString(distanceBetween)));
        leg.appendChild(distance);

        //Unit Element
        Element unit = XMLdoc.createElement("units");
        unit.appendChild(XMLdoc.createTextNode(units));
        leg.appendChild(unit);


    }

    Document getXMLdoc() {
        return XMLdoc;
    }

    public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        transformer.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(out, "UTF-8")));
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException{
        XMLBuilder qqq = new XMLBuilder();

        printDocument(qqq.getXMLdoc(),System.out);

    }
}
