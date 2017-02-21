import Presenter.*;
import Model.*;
import View.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileNotFoundException;

public class TripCo
{
    public static String printTest(String in)
    {
        return in;
    }

    public static void main(String[] args) throws FileNotFoundException, ParserConfigurationException, TransformerException
    {
        String filename = args[0];
        //String option = args[1];
        Model model = new Model();
        View view = new View();
        Presenter presenter = new Presenter(model, view);
        presenter.getView().initializeTrip();
        presenter.getView().addBorders();
        presenter.getView().addHeader("Colorado");
        presenter.getView().addFooter(9999);
        presenter.init(filename);
        presenter.getView().finalizeTrip();
    }
}
