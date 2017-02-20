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
import javax.xml.transform.OutputKeys;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.DOMImplementation;

public class View {

	private Document XMLdoc;
	private Document SVGdoc;
	private int labelID = 1;
	private int legID = 1;

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
	private double convertXCoordinates(double x){
		double xPixels = 1180;
		double startX = 41;
		double endX = 37;
		//Convert to SVG 'x' coordinate
		double strideX = endX - startX;
		double relativeX = (x - startX);
		double realX = relativeX * (xPixels / strideX);
		return (realX + 50);
	}

	private double convertYCoordinates(double y){
		double yPixels = 758;
		double startY = -109;
		double endY = -102;
		//Convert to SVG 'y' coordinate
		double strideY = endY - startY;
		double relativeY = (y - startY);
		double realY = relativeY * (yPixels / strideY);
		return (realY + 133);
	}

	private static void convertCoordinates(double x, double y){
        double xPixels = 1180;
        double yPixels = 758;
		double startX = 41;
		double endX = 37;
		double startY = -109.0;
		double endY = -102.0;

		//Convert to SVG 'x' coordinate
		double strideX = endX - startX;
		double relativeX = (x - startX);
		double realX = relativeX * (xPixels / strideX);

		//Convert to SVG 'y' coordinate
		double strideY = endY - startY;
		double relativeY = (y - startY);
		double realY = relativeY * (yPixels / strideY);
		System.out.println((realX + 50) + " " + (realY + 133));
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
		Element line = SVGdoc.createElement("line");
		line.setAttribute( "id", ("leg" + legID));
		legID++;
		line.setAttribute( "x1", Double.toString(convertXCoordinates(x1)));
		line.setAttribute( "y1", Double.toString(convertYCoordinates(y1)));
		line.setAttribute( "x2", Double.toString(convertXCoordinates(x2)));
		line.setAttribute( "y2", Double.toString(convertYCoordinates(y2)));
		line.setAttribute( "stroke-width", "3");
		line.setAttribute("stroke", "#999999");
		SVGdoc.getDocumentElement().appendChild(line);
	}

	public void addDistance(double x1, double y1, double x2, double y2, int distanceBetween){
		Element distance = SVGdoc.createElement("text");
		distance.setAttribute("font-family", "Sans-serif");
		distance.setAttribute("font-size", "16");
		distance.setAttribute("id", ("leg" + legID));
		distance.setAttribute("x", Double.toString((convertXCoordinates(x1) + convertXCoordinates(x2))/2));
		distance.setAttribute("y", Double.toString((convertYCoordinates(y1) + convertYCoordinates(y2))/2));
		distance.setTextContent(Integer.toString(distanceBetween));
		SVGdoc.getDocumentElement().appendChild(distance);
	}
	public void addLabel(double x, double y, String city){
		Element label = SVGdoc.createElement("text");
		label.setAttribute("font-family", "Sans-serif");
		label.setAttribute("font-size", "16");
		label.setAttribute("id", "id" + labelID);
		labelID++;
		label.setAttribute("x", Double.toString(convertXCoordinates(x)));
		label.setAttribute("y", Double.toString(convertYCoordinates(y)));
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
		header.setAttribute("y", "120");
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
		footer.setAttribute("y", "931");
		footer.setTextContent(totalDistance + " miles");
		SVGdoc.getDocumentElement().appendChild(footer);
	}

	public void addBorders(){
		//North border
		Element northBorder = SVGdoc.createElement("line");
		northBorder.setAttribute("id", "north");
		northBorder.setAttribute("x1", "50");
		northBorder.setAttribute("y1", "133");
		northBorder.setAttribute("x2", "1230");
		northBorder.setAttribute("y2", "133");
		northBorder.setAttribute("stroke-width", "5");
		northBorder.setAttribute("stroke", "#666666");

		//East border
		Element eastBorder = SVGdoc.createElement("line");
		eastBorder.setAttribute("id", "east");
		eastBorder.setAttribute("x1", "1230");
		eastBorder.setAttribute("y1", "133");
		eastBorder.setAttribute("x2", "1230");
		eastBorder.setAttribute("y2", "891");
		eastBorder.setAttribute("stroke-width", "5");
		eastBorder.setAttribute("stroke", "#666666");

		//South border
		Element southBorder = SVGdoc.createElement("line");
		southBorder.setAttribute("id", "south");
		southBorder.setAttribute("x1", "1230");
		southBorder.setAttribute("y1", "891");
		southBorder.setAttribute("x2", "50");
		southBorder.setAttribute("y2", "891");
		southBorder.setAttribute("stroke-width", "5");
		southBorder.setAttribute("stroke", "#666666");

		//West border
		Element westBorder = SVGdoc.createElement("line");
		westBorder.setAttribute("id", "west");
		westBorder.setAttribute("x1", "50");
		westBorder.setAttribute("y1", "891");
		westBorder.setAttribute("x2", "50");
		westBorder.setAttribute("y2", "133");
		westBorder.setAttribute("stroke-width", "5");
		westBorder.setAttribute("stroke", "#666666");

		//Append all borders to document
		SVGdoc.getDocumentElement().appendChild(northBorder);
		SVGdoc.getDocumentElement().appendChild(eastBorder);
		SVGdoc.getDocumentElement().appendChild(southBorder);
		SVGdoc.getDocumentElement().appendChild(westBorder);
	}

	public void finalizeTrip() throws TransformerException{

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();

		//XML document
		DOMSource source = new DOMSource(XMLdoc);
		StreamResult result = new StreamResult(new File("trip.xml"));
		System.out.println("xml file saved!");
		transformer.transform(source, result);

        //SVG document
		DOMSource source2 = new DOMSource(SVGdoc);
		StreamResult result2 = new StreamResult(new File("trip.svg"));
		System.out.println("svg file saved!");
		transformer.transform(source2, result2);
	}
	
	public static void main(String argv[]) throws ParserConfigurationException {
		View map = new View();
		try {
			map.initializeTrip();
			map.addLeg("sequence 1", "Denver", "Fort Collins", 9999);
			map.addLine(38.9243,-106.3208,37.5774,-105.4857);
			map.addDistance(37.5774,-105.4857,38.9243,-106.3208, 500);
			map.addBorders();
			map.addHeader("Colorado");
			map.addFooter(9999);
			map.addLabel(37,-102, "1");
			map.addLabel(39.1875,-106.4756, "2");
			map.addLabel(38.9243,-106.3208, "3");
			map.addLabel(37.5774,-105.4857, "4");
			map.addLabel(39.0294,-106.4729, "5");
			map.finalizeTrip();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}
