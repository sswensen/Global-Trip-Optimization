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

    public static void displayOptions(String option, Presenter presenter)
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

    public static void main(String[] args) throws FileNotFoundException, ParserConfigurationException, TransformerException
    {
        String filename = args[0];

        Model model = new Model();
        View view = new View();
        Presenter presenter = new Presenter(model, view);

        if(args.length>1)
        {
            String option1 = args[1];
            displayOptions(option1, presenter);
            if(args.length>2)
            {
                String option2 = args[2];
                displayOptions(option2, presenter);
                if(args.length>3)
                {
                    String option3 = args[3];
                    displayOptions(option3, presenter);
                }
            }
        }

        presenter.planTrip(filename);
    }
}
