package Presenter;

import Model.*;
import View.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestPresenter
{
    @Before
    public void initialize()
    {

    }

    private Presenter presenter = new Presenter(new Model(), new View());

    @Test
    public void getDisplayMileage()
    {
        assertEquals(false, presenter.getDisplayMileage());
    }

    @Test
    public void getDisplayId()
    {
        assertEquals(false, presenter.getDisplayId());
    }

    @Test
    public void getDisplayName()
    {
        assertEquals(false, presenter.getDisplayName());
    }

    @Test
    public void setDisplayMileage()
    {
        presenter.setDisplayMileage(true);
        assertEquals(true, presenter.getDisplayMileage());
    }

    @Test
    public void setDisplayId()
    {
        presenter.setDisplayId(true);
        assertEquals(true, presenter.getDisplayId());
    }

    @Test
    public void setDisplayName()
    {
        presenter.setDisplayName(true);
        assertEquals(true, presenter.getDisplayName());
    }
}
