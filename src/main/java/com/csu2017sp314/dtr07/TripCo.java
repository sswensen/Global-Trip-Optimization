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
            default:
                break;
        }
    }

    public static void main(String[] args) throws Exception {
        if(args.length == 0) {
            System.out.println("Usage: TripCo [options] file.csv");
        } else {

            for(int i = 0; i < args.length;i++){
                arguments.add(args[i]);
            }
            String filename = args[0];
            System.out.println(filename);
            String selectionXml = "";
            System.out.println(selectionXml.substring(selectionXml.lastIndexOf(".") + 1, selectionXml.length()));
            Model model = new Model();
            View view = new View();
            Presenter presenter = new Presenter(model, view);

            if(arguments.contains("-m")){
                displayOptions("-m", presenter);
            }
            if(arguments.contains("-n")){
                displayOptions("-n", presenter);
            }
            if(arguments.contains("-i")){
                displayOptions("-i", presenter);
            }

            for(int i = 0;i < arguments.size();i++){
                String file = arguments.get(i);
                try{
                    String xmlExtension = file.substring(file.lastIndexOf(".") + 1, file.length());
                    String xml = "xml";
                    if(!xml.equals(xmlExtension)){
                        continue;
                    }
                    else{
                        selectionXml = file;
                        System.out.println(selectionXml);
                    }
               } catch(IndexOutOfBoundsException e){
                   continue;
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
