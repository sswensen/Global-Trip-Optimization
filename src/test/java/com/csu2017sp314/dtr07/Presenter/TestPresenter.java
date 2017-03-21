package com.csu2017sp314.dtr07.Presenter;

import com.csu2017sp314.dtr07.Model.*;
import com.csu2017sp314.dtr07.View.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPresenter {
    @Before
    public void initialize() {
    }

    private Presenter presenter = new Presenter(new Model(), new View());

    @Test
    public void getTwoOpt(){
        assertEquals(false, presenter.getTwoOpt());
    }

    @Test
    public void setTwoOpt(){
        presenter.setTwoOpt(true);
        assertEquals(true, presenter.getTwoOpt());
    }

    @Test
    public void getThreeOpt(){
        assertEquals(false, presenter.getThreeOpt());
    }

    @Test
    public void setThreeOpt(){
        presenter.setThreeOpt(true);
        assertEquals(true, presenter.getThreeOpt());
    }

    @Test
    public void setDisplayMileage() {
        presenter.setDisplayMileage(true);
        assertEquals(true, presenter.getDisplayMileage());
    }

    @Test
    public void setDisplayId() {
        presenter.setDisplayId(true);
        assertEquals(true, presenter.getDisplayId());
    }

    @Test
    public void setDisplayName() {
        presenter.setDisplayName(true);
        assertEquals(true, presenter.getDisplayName());
    }

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
    public void displayGui(){
        presenter.displayGui(true);
        assertEquals(true, presenter.getDisplayGui());
    }

    @Test
    public void getDisplayGui(){
        assertEquals(false, presenter.getDisplayGui());
    }

    @Test
    public void planTrip(){
        try{
            presenter.planTrip("exampleFile", "src/test/resources/Testing/selectionXml.xml", "src/test/resources/coloradoMap.svg");
            assertEquals("exampleFile", presenter.getFname());
        }catch(Exception e){}
    }







}
