package Presenter;

import Model.*;
import View.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;

public class Presenter
{
    private Model model;
    private View view;

    public Presenter(Model model, View view)
    {
        this.model = model;
        this.view = view;
    }

    public View getView()
    {
        return view;
    }

    public void init(String filename) throws FileNotFoundException, ParserConfigurationException, TransformerException
    {
        view.initializeTrip();
        model.planTrip(filename);
        int numPairs = model.getNumPairs();
        view.addBorders();
        view.addHeader("Colorado");
        view.addFooter(model.getTripDistance());
        for(int i = 0; i < numPairs; i++)
        {
            double firstLon = model.getFirstLon(i);
            double firstLat = model.getFirstLat(i);
            double secondLon = model.getSecondLon(i);
            double secondLat = model.getSecondLat(i);
            int pairDistance = model.getPairDistance(i);
            int pairId = model.getPairId(i);
            String firstName = model.getFirstName(i);
            String secondName = model.getSecondName(i);
            view.addLeg(pairId, firstName, secondName, pairDistance);
            view.addLine(firstLat, firstLon, secondLat, secondLon, pairId);
            view.addLabel(firstLat, firstLon, firstName);
            view.addLabel(secondLat, secondLon, secondName);
            view.addDistance(firstLat, firstLon, secondLat, secondLon, pairDistance, pairId);
        }
        view.finalizeTrip();
    }

    public static void main(String[] args) throws FileNotFoundException, ParserConfigurationException, TransformerException
    {
    }
}
