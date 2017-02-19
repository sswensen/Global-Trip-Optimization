package View;

import java.io.File;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.DOMImplementation;

public class View {
	
	//Document builders
	public DocumentBuilderFactory factory;
	public DocumentBuilder docBuilder;
	public Document XMLdoc;
	public Document SVGdoc;
	public DOMImplementation impl;
	public Element locations;
	public String svgNS = "http://www.w3.org/2000/svg";
	public int labelID = 1;
	public int legID = 1;

	public void initializeTrip() throws ParserConfigurationException{
	    //The document builders
		factory = DocumentBuilderFactory.newInstance(); 
		docBuilder = factory.newDocumentBuilder();

		//Creating the SVG document
		impl = docBuilder.getDOMImplementation();
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
	
	public static void convertCoordinates(double x, double y){
		double startX = 30;
		double endX = 60;
		double xPixels = 1066.6073;
		double yPixels = 783.0824;
		double stride = endX - startX;
		double relativeX = (x - startX);
		double realX = relativeX * (xPixels / stride);
		
	}
	public void addLocation(String name, double lat, double lng) {
		Element location = XMLdoc.createElement("location");
		location.setAttribute("name", name);
		location.setAttribute("latitude", "" + lat);
		location.setAttribute("longitude", "" + lng);
		locations.appendChild(location);
	}

	public void addLeg(String sequence,String start, String finish, int mileage){
		//leg grouping
		Element leg = XMLdoc.createElement("leg");
		XMLdoc.getDocumentElement().appendChild(leg);
		
		//Sequence element
		Element sequenceElement = XMLdoc.createElement("sequence");
		sequenceElement.appendChild(XMLdoc.createTextNode(sequence));
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
	
	public void addLine(double x1, double y1, double x2, double y2){
		Element line = SVGdoc.createElementNS(svgNS, "line");
		line.setAttribute( "id", ("leg" + legID));
		legID++;
		line.setAttribute( "x1", Double.toString(x1));
		line.setAttribute( "y1", Double.toString(y1));
		line.setAttribute( "x2", Double.toString(x2));
		line.setAttribute( "y2", Double.toString(y2));
		line.setAttribute( "stroke-width", "3");
		line.setAttribute("stroke", "#999999");
		SVGdoc.getDocumentElement().appendChild(line);
	}
	
	public void addLabel(double x, double y, String city){
		Element label = SVGdoc.createElementNS(svgNS, "text");
		label.setAttribute("font-family", "Sans-serif");
		label.setAttribute("font-size", "16");
		label.setAttribute("id", "id" + labelID);
		label.setAttribute("x", Double.toString(x));
		label.setAttribute("y", Double.toString(y));
		label.setTextContent(city);
		SVGdoc.getDocumentElement().appendChild(label);
	}
	
	public void addHeader(String title){
		//<text text-anchor="middle" font-family="Sans-serif" font-size="24" id="state" y="40" x="640">Colorado</text>
		Element header = SVGdoc.createElement("text");
		header.setAttribute("text-anchor", "middle");
		header.setAttribute("font-family", "Sans-serif");
		header.setAttribute("font-size", "24");
		header.setAttribute("id", "state");
		header.setAttribute("x", "640");
		header.setAttribute("y", "40");
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
		footer.setAttribute("y", "1014");
		footer.setTextContent(totalDistance + " miles");
		SVGdoc.getDocumentElement().appendChild(footer);
	}

	public void addBorders(){
		//North border
		Element northBorder = SVGdoc.createElement("line");
		northBorder.setAttribute("id", "north");
		northBorder.setAttribute("x1", "50");
		northBorder.setAttribute("y1", "50");
		northBorder.setAttribute("x2", "1230");
		northBorder.setAttribute("y2", "50");
		northBorder.setAttribute("stroke-width", "5");
		northBorder.setAttribute("stroke", "#666666");

		//East border
		Element eastBorder = SVGdoc.createElement("line");
		eastBorder.setAttribute("id", "east");
		eastBorder.setAttribute("x1", "1230");
		eastBorder.setAttribute("y1", "50");
		eastBorder.setAttribute("x2", "1230");
		eastBorder.setAttribute("y2", "974");
		eastBorder.setAttribute("stroke-width", "5");
		eastBorder.setAttribute("stroke", "#666666");

		//South border
		Element southBorder = SVGdoc.createElement("line");
		southBorder.setAttribute("id", "south");
		southBorder.setAttribute("x1", "1230");
		southBorder.setAttribute("y1", "974");
		southBorder.setAttribute("x2", "50");
		southBorder.setAttribute("y2", "974");
		southBorder.setAttribute("stroke-width", "5");
		southBorder.setAttribute("stroke", "#666666");

		//West border
		Element westBorder = SVGdoc.createElement("line");
		westBorder.setAttribute("id", "west");
		westBorder.setAttribute("x1", "50");
		westBorder.setAttribute("y1", "974");
		westBorder.setAttribute("x2", "50");
		westBorder.setAttribute("y2", "50");
		westBorder.setAttribute("stroke-width", "5");
		westBorder.setAttribute("stroke", "#666666");

		SVGdoc.getDocumentElement().appendChild(northBorder);
		SVGdoc.getDocumentElement().appendChild(eastBorder);
		SVGdoc.getDocumentElement().appendChild(southBorder);
		SVGdoc.getDocumentElement().appendChild(westBorder);
	}
	public void addGrouping(){

    }

    public void addTitle(){

    }

	public void finalizeTrip() throws TransformerException{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();

		//XML document
		DOMSource source = new DOMSource(XMLdoc);
		StreamResult result = new StreamResult(new File("XMLfile.xml"));
		transformer.transform(source, result);
		System.out.println("File saved!");

        //SVG document
		DOMSource source2 = new DOMSource(SVGdoc);
		StreamResult result2 = new StreamResult(new File("SVGfile.svg"));
		transformer.transform(source2, result2);
		System.out.println("File saved!");
	}
	
	public static void main(String argv[]) throws ParserConfigurationException {
		View map = new View();

		try {
			map.initializeTrip();
			map.addLine(100,100,500,110);
			map.addLine(500,110,1140,900);
			map.addLine(1140,900,120,700);
			map.addLine(120,700,200,400);
			map.addLine(200,400,100,100);
			map.addBorders();
			map.addHeader("Colorado");
			map.addFooter(9999);
			map.addLabel(100,100, "cityA");
			map.addLabel(500,110, "cityB");
			map.addLabel(1140,900, "cityC");
			map.addLabel(120,700, "cityD");
			map.addLabel(200,400, "cityE");
			map.finalizeTrip();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}
