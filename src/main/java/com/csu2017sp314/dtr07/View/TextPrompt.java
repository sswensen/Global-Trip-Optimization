package com.csu2017sp314.dtr07.View;

/**
 * Created by Sandeep on 4/8/2017.
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class TextPrompt extends JLabel implements FocusListener, DocumentListener
{
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

    public TextPrompt(String text, JTextComponent component)
    {
        this(text, component, Show.ALWAYS);
    }

    public TextPrompt(String text, JTextComponent component, Show show) {
        this.component = component;
        setShow( show );
        document = component.getDocument();

        setText( text );
        setFont( component.getFont() );
        setForeground( component.getForeground() );
        setBorder( new EmptyBorder(component.getInsets()) );
        setHorizontalAlignment(JLabel.LEADING);

        component.addFocusListener( this );
        document.addDocumentListener( this );

        component.setLayout( new BorderLayout() );
        component.add( this );
        checkForPrompt();
    }

    public void setShow(Show show)
    {
        this.show = show;
    }
    public Show getShow(){
        return show;
    }

    private void checkForPrompt() {
        //  Text has been entered, remove the prompt

        if (document.getLength() > 0)
        {
            setVisible( false );
            return;
        }

        //  Prompt has already been shown once, remove it

        if (showPromptOnce && focusLost > 0)
        {
            setVisible(false);
            return;
        }

        //  Check the Show property and component focus to determine if the
        //  prompt should be displayed.

        if (component.hasFocus())
        {
            if (show == Show.ALWAYS
                    ||  show ==	Show.FOCUS_GAINED)
                setVisible( true );
            else
                setVisible( false );
        }
        else
        {
            if (show == Show.ALWAYS
                    ||  show ==	Show.FOCUS_LOST)
                setVisible( true );
            else
                setVisible( false );
        }
    }

//  Implement FocusListener
    public int getFocusLost(){
        return focusLost;
    }
    public void addFocus(){
        focusLost++;
    }
    public void focusGained(FocusEvent e)
    {
        checkForPrompt();
    }

    public void focusLost(FocusEvent e) {
        focusLost++;
        checkForPrompt();
    }

//  Implement DocumentListener

    public void insertUpdate(DocumentEvent e)
    {
        checkForPrompt();
    }

    public void removeUpdate(DocumentEvent e)
    {
        checkForPrompt();
    }

    public void changedUpdate(DocumentEvent e) {}
}

