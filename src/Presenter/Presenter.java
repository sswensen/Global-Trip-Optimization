package Presenter;

import Model.*;
import View.*;

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

    public void planTrip()
    {

    }

    public static void main(String[] args) throws FileNotFoundException
    {
        String filename = args[0];
        String option = args[1];
        Model model = new Model();
        View view = new View();
        Presenter presenter = new Presenter(model, view);
        presenter.model.planTrip(filename);

    }
}
