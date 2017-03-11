package com.csu2017sp314.dtr07.View;

import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.io.File;

/*
 * Created by SummitDrift on 3/6/17.
 * File for displaying map with interactive stuff
 */

public class MapGUI {
    public Consumer<String> callback;
    public Consumer<ArrayList<String>> callback2;
    private String filename;
    private JFrame map; //Map that displays locations
    private JFrame face; //User interface with locations
    private boolean tick = false;
    private int killmenow = 1;

    MapGUI() {

    }

    public void setCallback(Consumer<String> callback) {
        this.callback = callback;
    }

    public void setCallback2(Consumer<ArrayList<String>> callback2) {
        this.callback2 = callback2;
    }

    public void userAddLoc(String id) {
        callback.accept(id);
    }

    public void userAddLocList(ArrayList<String> ids) {
        callback2.accept(ids);
    }

    int init(String filename) throws Exception {
        this.filename = filename;
        new Convert(filename, -1);
        map = new JFrame("TripCo"); //creating instance of JFrame
        /*map.addWindowListener(new WindowAdapter() { //This looks for the 'x' to be pressed on the window, better solution in TripCo.java
            public void windowClosing(WindowEvent e) {
                cleanup();
            }
        });*/

        //Code for aligning to left side of screen
        /* GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - f.getWidth();
        */
        /*map.setVisible(false);
        map.removeAll();
        map.revalidate();
        map.repaint();*/

        map.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //Closes app if window closes
        map.setLocationRelativeTo(null);
        map.setLayout(new BorderLayout());
        map.setContentPane(new JLabel(new ImageIcon("png/" + filename + ".png")));
        map.setLayout(new FlowLayout());
        /*JLabel background = new JLabel(new ImageIcon("png/" + filename + ".png"));
        background.setLayout( new BorderLayout() );
        map.setContentPane( background );*/

        map.setLocation(0, 0);
        map.setSize(1063, 801); //Refreshes window, needed or image doesn't appear
        map.setSize(1064, 802);

        //f.pack(); //Will make everything MASSIVE

        face = new JFrame("User Options");
        face.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        face.setLocation(1063, 0);
        face.setSize(300, 802);

        map.setVisible(true); //making the frame visible
        face.setVisible(true);
        return 1;
    }

    public void displayXML(ArrayList<String> ids) {
        ArrayList<String> tempLoc = new ArrayList<>();
        /*System.out.println("Printing tempLoc");
        for(int i = 0; i < ids.size(); i++) {
            System.out.println("[GUI] ID at index " + i + " = "+ ids.get(i));
        }
        System.out.println("End printing tempLoc");*/
        int index = 35;
        for (String id : ids) {
            JButton b = new JButton("[ ]");
            b.addActionListener(new ActionListener() { //This fires when button b is pressed, unique for each instance!
                @Override //Bish
                public void actionPerformed(ActionEvent e) {
                    /*
                        We can use the following method to run the call back each time a location is clicked (don't know about efficiency here)
                        Or we can do one where the callback is only initiated when the user clicks the button that loads the map after selecting locations
                        I'm going to implement the second method as the first one already is.
                        To use the first method, uncomment the line below.
                     */
                    //userAddLoc(id); //This is a callback to View
                    if (b.getText().equals("[ ]")) { //Checks if button has already been pressed
                        tempLoc.add(id);
                        System.out.println("Added " + id + "to array");
                        //b.setBackground(Color.BLACK);
                        //b.setOpaque(true); //Doesnt work for some unknown reason
                        b.setText("[X]"); //If not pressed, toggle text and add
                    } else if (b.getText().equals("[X]")) {
                        tempLoc.remove(id);
                        System.out.println("Removed " + id + "to array");
                        b.setText("[ ]"); //If already pressed, toggle text and remove
                    }
                }
            });
            JButton t = new JButton(id);
            t.setEnabled(false);
            b.setBounds(5, index, 30, 30);
            t.setBounds(35, index, 260, 30);
            face.add(b);
            b.setVisible(true);
            face.add(t);
            t.setVisible(true);
            index += 35;
        }
        //Adding button to load tempLocs
        JButton q = new JButton("Display");
        q.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*for(int i = 0; i < ids.size(); i++) {
                    System.out.println("[GUI] ID at index " + i + " = "+ ids.get(i));
                }*/
                userAddLocList(tempLoc);
            }
        });
        q.setBounds(5, 5, 290, 30);
        face.add(q);
        //--This does background stuff to attempt to get rid of the buttons forming incorrectly--
        JButton a = new JButton();
        a.setEnabled(false);
        face.add(a);
        a.setVisible(true);
        //---------------------------------------------------------------------------------------
        face.setSize(299, 801);
        face.setSize(300, 802);
    }

    void refresh() throws Exception {
        map.setVisible(false);
        //int w = map.getWidth();
        //int h = map.getHeight();
        //TimeUnit.SECONDS.sleep(5);
        /*if(tick) {
            new Convert(filename, 1);
            JLabel background = new JLabel(new ImageIcon("png/" + filename + "_User.png"));
            background.setLayout( new BorderLayout() );
            map.setContentPane( background );
            tick = false;
            try {
                File temp = new File("png/" + filename + "_User2.png");
                temp.delete();
            } catch (Exception e) {

            }
        } else {
            new Convert(filename, 2);
            JLabel background = new JLabel(new ImageIcon("png/" + filename + "_User2.png"));
            background.setLayout( new BorderLayout() );
            map.setContentPane( background );
            tick = true;
            try {
                File temp = new File("png/" + filename + "_User.png");
                temp.delete();
            } catch (Exception e) {

            }
        }*/
        new Convert(filename, killmenow);
        JLabel background = new JLabel(new ImageIcon("png/" + filename + killmenow + "_User.png"));
        background.setLayout(new BorderLayout());
        map.setContentPane(background);
        killmenow++;

        //map.setSize(w - 1, h - 1);
        //map.setSize(w, h);
        map.setSize(1063, 801); //Refreshes window, needed or image doesn't appear
        map.setSize(1064, 802); //Second part for refreshing the window

        map.setVisible(true); //making the frame visible
    }

    void cleanup() {
        for (int i = 0; i < killmenow; i++) {
            File temp = new File("png/" + filename + i + "_User.png");
            temp.delete();
        }
        File temp = new File("png/" + filename + ".png");
        temp.delete();
        killmenow = 0;
    }

    public static void main(String[] args) throws Exception {
        new Convert("Colorado14ers", 0);
        JFrame f = new JFrame("TripCo"); //creating instance of JFrame
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //Closes app if window closes
        JButton b = new JButton("click"); //creating instance of JButton
        b.setBounds(964, 0, 100, 40); //x axis, y axis, width, height
        f.add(b); //adding button in JFrame
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setLayout(new BorderLayout());
        f.setContentPane(new JLabel(new ImageIcon("png/Colorado14ers.png")));
        f.setLayout(new FlowLayout());
        f.setSize(1063, 779); //Refreshes window, needed or image doesn't appear
        f.setSize(1064, 780);
        //f.pack(); //Will make everything MASSIVE
        f.setLayout(null); //using no layout managers
        f.setVisible(true); //making the frame visible
    }
}
