package com.csu2017sp314.dtr07.View;

import org.junit.Test;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import static org.junit.Assert.*;

/**
 * Created by Sandeep on 4/10/2017.
 */
public class TestTextPrompt {
    public enum Show
    {
        ALWAYS,
        FOCUS_GAINED,
        FOCUS_LOST;
    }

    private JTextComponent component;
    private Document document;

    private Show show;
    private boolean showPromptOnce;
    private int focusLost;
    JTextField findTextField = new JTextField(30);
    TextPrompt tp = new TextPrompt("Municipality Search", findTextField);

    public JTextField getFindTextField() {
        return findTextField;
    }

    @Test
    public void initialize(){

    }

    @Test
    public void setShow() throws Exception {
        assertEquals(TextPrompt.Show.ALWAYS, tp.getShow());
    }

    @Test
    public void checkForPrompt() throws Exception{
        JTextField test = getFindTextField();
        test.setText("test");
        TextPrompt testing = new TextPrompt("test", test);
        assertEquals("test", test.getText());
    }
}