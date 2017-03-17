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
    private static void displayOptions(String option, Presenter presenter)
    {
        switch(option)
        {
            case "-m":
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
            default:
                break;
        }
    }

    public static void main(String[] args) throws Exception {
        if(args.length == 0) {
            System.out.println("Usage: TripCo [options] file.csv [map.svg] [selection.xml]");
        } else {
            for(int i = 0; i < args.length;i++){
                arguments.add(args[i]);
            }
            String filename = "";
            String selectionXml = "";
            Model model = new Model();
            View view = new View();
            Presenter presenter = new Presenter(model, view);

            for(int i = 0;i < arguments.size();i++){
                String xmlFile = arguments.get(i);
                String csvFile = arguments.get(i);
                try{
                    String xmlExtension = xmlFile.substring(xmlFile.lastIndexOf(".") + 1, xmlFile.length());
                    String csvExtension = csvFile.substring(csvFile.lastIndexOf(".") + 1, csvFile.length());
                    String xml = "xml";
                    String csv = "csv";
                    if(!xml.equals(xmlExtension) && !csv.equals(csvExtension)){ //both checks are false
                        continue;
                    }
                    else{ //one file matches the extension
                        if(xml.equals(xmlExtension)){
                            selectionXml = xmlFile;
                            System.out.println("Found selectionXml: " + selectionXml);
                        }
                        if(csv.equals(csvExtension)){
                            filename = csvFile;
                            System.out.println("Found csv file: " + filename);
                        }
                    }
                } catch(IndexOutOfBoundsException e){
                    continue;
                }
            }
            if(arguments.contains("-g")){
                displayOptions("-g", presenter);
            }
            else{
                if(arguments.contains("-m")){
                    displayOptions("-m", presenter);
                }
                if(arguments.contains("-n")){
                    displayOptions("-n", presenter);
                }
                if(arguments.contains("-i")){
                    displayOptions("-i", presenter);
                }
            }
            presenter.planTrip(filename, selectionXml);
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                public void run() {
                    //presenter.cleanup();
                }
            }, "Shutdown-thread"));
        }
    }
}
