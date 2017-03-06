package com.csu2017sp314.dtr07.Presenter;


import com.csu2017sp314.dtr07.Model.*;
import com.csu2017sp314.dtr07.View.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Presenter
{
    private Model model;
    private View view;
    private boolean displayMileage;
    private boolean displayId;
    private boolean displayName;

    public Presenter(Model model, View view)
    {
        this.model = model;
        this.view = view;
        this.displayMileage = false;
        this.displayId = false;
        this.displayName = false;
    }

    public void setDisplayMileage(boolean x)
    {
        displayMileage = x;
    }

    public void setDisplayId(boolean x)
    {
        displayId = x;
    }

    public void setDisplayName(boolean x)
    {
        displayName = x;
    }

    public boolean getDisplayMileage()
    {
        return displayMileage;
    }

    public boolean getDisplayId()
    {
        return displayId;
    }

    public boolean getDisplayName()
    {
        return displayName;
    }

    public void planTrip(String filename) throws org.xml.sax.SAXException, IOException, ParserConfigurationException, TransformerException
    {
        view.initializeTrip();
        model.planTrip(filename);
        int numPairs = model.getNumPairs();
        //view.addBorders();
        //view.addHeader("Colorado");
        view.addFooter(model.getTripDistance());
        int finalPairID = 0;
        for(int i = 0; i < numPairs; i++)
        {
            double firstLon = model.getFirstLon(i);
            double firstLat = model.getFirstLat(i);
            double secondLon = model.getSecondLon(i);
            double secondLat = model.getSecondLat(i);
            int pairDistance = model.getPairDistance(i);
            String pairId = model.getPairId(i);
            String firstId = model.getFirstId(i);
            String secondId = model.getSecondId(i);
            String firstName = model.getFirstName(i);
            String secondName = model.getSecondName(i);
            view.addLeg(pairId, firstName, secondName, pairDistance);
            finalPairID++;
            view.addLine(firstLon, firstLat, secondLon, secondLat, pairId);
            if(displayName)
            {
                view.addCityNameLabel(firstLon, firstLat, firstName);
                view.addCityNameLabel(secondLon, secondLat, secondName);
            }
            if(displayMileage)
            {
                view.addDistance(firstLon, firstLat, secondLon, secondLat, pairDistance, pairId);
            }
            if(displayId)
            {
                view.addIDLabel(firstLon, firstLat, firstId);
                view.addIDLabel(secondLon, secondLat, secondId);
            }
        }
        view.addFinalLeg(Integer.toString(finalPairID), model.getLegStartLocation(), model.getLegFinishLocation(),model.getTripDistance());
        view.finalizeTrip(filename);
    }

    public static void main(String[] args) throws FileNotFoundException, ParserConfigurationException, TransformerException
    {
    }
}
