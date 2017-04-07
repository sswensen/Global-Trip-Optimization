package com.csu2017sp314.dtr07;

import com.csu2017sp314.dtr07.Presenter.*;
import com.csu2017sp314.dtr07.Model.*;
import com.csu2017sp314.dtr07.View.*;

import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

import org.xml.sax.SAXException;


public class TripCo {
    private static ArrayList<String> arguments = new ArrayList<>();

    private static void displayOptions(String option, Presenter presenter) {
        switch(option)
        {
            case "-d":
                presenter.setDisplayMileage(true);
                break;
            case "-i":
                presenter.setDisplayId(true);
                break;
            case "-n":
                presenter.setDisplayName(true);
                break;
            case "-g":
                presenter.displayGui(true);
                break;
            case "-2":
                presenter.setTwoOpt(true);
                break;
            case "-3":
                presenter.setThreeOpt(true);
                break;
            case "-k":
                presenter.setKilometers(true);
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) throws Exception {
        String xmlFile = "";
        String csvFile = "";
        String svgFile = "";
        if(args.length == 0) {
            System.out.println("Usage: TripCo [options] file.csv [map.svg] [selection.xml]");
        } else {
            for(int i = 0; i < args.length; i++) {
                arguments.add(args[i]);
            }
            String filename = "";
            String selectionXml = "";
            String svgMap = "";
            Model model = new Model();
            View view = new View();
            Presenter presenter = new Presenter(model, view);

            for(int i = 0; i < arguments.size(); i++) {
                xmlFile = arguments.get(i);
                csvFile = arguments.get(i);
                svgFile = arguments.get(i);
                try {
                    String xmlExtension = xmlFile.substring(xmlFile.lastIndexOf(".") + 1, xmlFile.length());
                    String csvExtension = csvFile.substring(csvFile.lastIndexOf(".") + 1, csvFile.length());
                    String svgExtension = svgFile.substring(svgFile.lastIndexOf(".") + 1, svgFile.length());
                    String xml = "xml";
                    String csv = "csv";
                    String svg = "svg";
                    if(!xml.equals(xmlExtension) && !csv.equals(csvExtension) && !svg.equals(svgExtension)) { //both checks are false
                        continue;
                    } else { //one file matches the extension
                        if(xml.equals(xmlExtension)) {
                            selectionXml = xmlFile;
                            System.out.println("[TripCo] Found selectionXml: " + selectionXml);
                        }
                        if(csv.equals(csvExtension)) {
                            filename = csvFile;
                            System.out.println("[TripCo] Found csv file: " + filename);
                        }
                        if(svg.equals(svgExtension)) {
                            svgMap = svgFile;
                        }
                    }
                } catch(IndexOutOfBoundsException e) {
                    continue;
                }
            }
            if(arguments.contains("-g") && arguments.contains("-f")) {
                System.out.println("Error: can not have both -f and -g options");
            }
            if(arguments.contains("-g") && !arguments.contains("-f")) {
                displayOptions("-g", presenter);
                presenter.planTrip(filename, selectionXml, svgMap);
                Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                    public void run() {
                        //presenter.cleanup();
                    }
                }, "Shutdown-thread"));
            } else if(arguments.contains("-f") && !arguments.contains("-g")) {
                if(arguments.contains("-d")){
                    displayOptions("-d", presenter);
                }
                if(arguments.contains("-n")) {
                    displayOptions("-n", presenter);
                }
                if(arguments.contains("-i")) {
                    displayOptions("-i", presenter);
                }
                if(arguments.contains("-2")) {
                    displayOptions("-2", presenter);
                }
                if(arguments.contains("-3")) {
                    displayOptions("-3", presenter);
                }
                if(arguments.contains("-k")) {
                    displayOptions("-k", presenter);
                }
                presenter.planTrip(filename, selectionXml, svgMap);
                Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                    public void run() {
                        //presenter.cleanup();
                    }
                }, "Shutdown-thread"));
            }
        }
    }
}
