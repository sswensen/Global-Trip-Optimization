package com.csu2017sp314.dtr07.View;

import java.io.File;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.DOMImplementation;

public class View {

	private Document XMLdoc;
	private Document SVGdoc;
	private int labelID = 1;

	public void initializeTrip() throws ParserConfigurationException{
	    //The document builders
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = factory.newDocumentBuilder();

		//Creating the SVG document
		String svgNS = "http://www.w3.org/2000/svg";
		DOMImplementation impl = docBuilder.getDOMImplementation();
		SVGdoc = impl.createDocument(svgNS, "svg", null);
		Element svgRoot = SVGdoc.getDocumentElement();
		svgRoot.setAttribute("width", "1280");
		svgRoot.setAttribute("height", "1024");
		svgRoot.setAttribute("xmlns", "http://www.w3.org/2000/svg");
		svgRoot.setAttribute("xmlns:svg", "http://www.w3.org/2000/svg");

		//Creating the XML document
		XMLdoc = docBuilder.newDocument();
		Element rootElement = XMLdoc.createElement("trip");
		XMLdoc.appendChild(rootElement);
	}

	private double convertLongitudeCoordinates(double x){
		double xPixels = 1180; //Width of colorado map
		double startX = -109;
		double endX = -102;
		//Convert to SVG 'x' coordinate
		double strideX = endX - startX;
		double relativeX = (x - startX);
		double realX = relativeX * (xPixels / strideX);
		return (realX + 50);
	}

	private double convertLatitudeCoordinates(double y){
		double yPixels = 674.2857143; //Height of colorado map
		double startY = 41;
		double endY = 37;
		//Convert to SVG 'y' coordinate
		double strideY = endY - startY;
		double relativeY = (y - startY);
		double realY = relativeY * (yPixels / strideY);
		return (realY + 174.8571429);
	}

	public void addLeg(String id, String start, String finish, int mileage){
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
	public void addFinalLeg(String id, String start, String finish, int totalMileage) {
		//Condenses original addFinalLeg
		addLeg(id, start, finish, totalMileage);
	}

	public void addLine(double x1, double y1, double x2, double y2, String id){
		Element line = SVGdoc.createElement("line");
		line.setAttribute( "id", ("leg" + id));
		line.setAttribute( "x1", Double.toString(convertLongitudeCoordinates(x1)));
		line.setAttribute( "y1", Double.toString(convertLatitudeCoordinates(y1)));
		line.setAttribute( "x2", Double.toString(convertLongitudeCoordinates(x2)));
		line.setAttribute( "y2", Double.toString(convertLatitudeCoordinates(y2)));
		line.setAttribute( "stroke-width", "3");
		line.setAttribute("stroke", "#999999");
		SVGdoc.getDocumentElement().appendChild(line);
	}

	public void addDistance(double x1, double y1, double x2, double y2, int distanceBetween, String id){
		Element distance = SVGdoc.createElement("text");
		distance.setAttribute("font-family", "Sans-serif");
		distance.setAttribute("font-size", "16");
		distance.setAttribute("id", ("leg" + id));
		distance.setAttribute("x", Double.toString((convertLongitudeCoordinates(x1) + convertLongitudeCoordinates(x2))/2));
		distance.setAttribute("y", Double.toString((convertLatitudeCoordinates(y1) + convertLatitudeCoordinates(y2))/2));
		distance.setTextContent(Integer.toString(distanceBetween));
		SVGdoc.getDocumentElement().appendChild(distance);
	}
	public void addCityNameLabel(double longitude, double latitude, String city){
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
	public void addIDLabel(double longitude, double latitude, String id){
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
	public void addHeader(String title){
		//<text text-anchor="middle" font-family="Sans-serif" font-size="24" id="state" y="40" x="640">Colorado</text>
		Element header = SVGdoc.createElement("text");
		header.setAttribute("text-anchor", "middle");
		header.setAttribute("font-family", "Sans-serif");
		header.setAttribute("font-size", "24");
		header.setAttribute("id", "state");
		header.setAttribute("x", "640");
		header.setAttribute("y", "161.8571429");
		header.setTextContent(title);
		SVGdoc.getDocumentElement().appendChild(header);
	}
	
	public void addFooter(int totalDistance){
		Element footer = SVGdoc.createElement("text");
		footer.setAttribute("text-anchor", "middle");
		footer.setAttribute("font-family", "Sans-serif");
		footer.setAttribute("font-size", "24");
		footer.setAttribute("id", "distance");
		footer.setAttribute("x", "640");
		footer.setAttribute("y", "889.1428572");
		footer.setTextContent(totalDistance + " miles");
		SVGdoc.getDocumentElement().appendChild(footer);
	}

	public void addBorders(){
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

	public void finalizeTrip(String filename) throws TransformerException{

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		String[] cut = filename.split("/");
		filename = cut[cut.length-1].substring(0, cut[cut.length-1].length()-4);
		//XML document
		DOMSource source = new DOMSource(XMLdoc);
		StreamResult result = new StreamResult(new File(filename + ".xml"));
		//System.out.println("xml file saved!");
		transformer.transform(source, result);

        //SVG document
		DOMSource source2 = new DOMSource(SVGdoc);
		StreamResult result2 = new StreamResult(new File(filename + ".svg"));
		//System.out.println("svg file saved!");
		transformer.transform(source2, result2);
	}

	public Document getXMLdoc() {
		return XMLdoc;
	}

	public void setXMLdoc(Document XMLdoc) {
		this.XMLdoc = XMLdoc;
	}

	public Document getSVGdoc() {
		return SVGdoc;
	}

	public void setSVGdoc(Document SVGdoc) {
		this.SVGdoc = SVGdoc;
	}

	public int getLabelID() {
		return labelID;
	}

	public void setLabelID(int labelID) {
		this.labelID = labelID;
	}

	public static void main(String argv[]) throws ParserConfigurationException, TransformerException {
		View map = new View();
        map.initializeTrip();
        map.addLeg("1", "Sandeep","Denver",9999);
        map.addLine(-109,41,-102,37,"1");
        System.out.println(map.getSVGdoc().getDocumentElement().getFirstChild().getNodeName());
		map.addBorders();
        //map.addLabel(-108.60,37.34, "Montezuma");
        map.finalizeTrip("./src/test/resources/Testing/ColoradoCountySeats.csv");
	}
}
