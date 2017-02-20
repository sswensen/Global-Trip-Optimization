package Presenter;

import Model.*;
import View.*;
import com.sun.org.apache.xpath.internal.operations.Mod;

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

    public static void main(String[] args)
    {
        Model model = new Model();
        View view = new View();
        Presenter presenter = new Presenter(model, view);
    }
}
