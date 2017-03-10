package com.csu2017sp314.dtr07.View;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

/*
 * Created by SummitDrift on 3/6/17.
 * File for displaying map with interactive stuff
 */

public class MapGUI {
    private String filename;
    private JFrame map; //Map that displays locations
    private JFrame face; //User interface with locations

    MapGUI(String filename) {
        this.filename = filename;
    }

    int init() throws Exception {
        new Convert(filename, 0);
        map = new JFrame("TripCo"); //creating instance of JFrame


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


        JButton b = new JButton("click"); //creating instance of JButton
        b.setBounds(964, 0, 100, 40); //x axis, y axis, width, height
        map.add(b); //adding button in JFrame

        map.setLocation(0,0);
        map.setSize(1063,801); //Refreshes window, needed or image doesn't appear
        map.setSize(1064,802);

        //f.pack(); //Will make everything MASSIVE

        face = new JFrame("User Options");
        face.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        face.setLocation(1063,0);
        face.setSize(300, 802);

        map.setVisible(true); //making the frame visible
        face.setVisible(true);
        return 1;
    }

    void refresh() throws Exception {
        map.setVisible(false);
        new Convert(filename, 1);
        int w = map.getWidth();
        int h = map.getHeight();
        //TimeUnit.SECONDS.sleep(5);
        map.setContentPane(new JLabel(new ImageIcon("png/" + filename + "_User.png")));

        map.setSize(w-1, h-1);
        map.setSize(w,h);
        map.setSize(1063,801); //Refreshes window, needed or image doesn't appear
        map.setSize(1064,802); //Second part for refreshing the window

        map.setVisible(true); //making the frame visible
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
        f.setSize(1063,779); //Refreshes window, needed or image doesn't appear
        f.setSize(1064,780);
        //f.pack(); //Will make everything MASSIVE
        f.setLayout(null); //using no layout managers
        f.setVisible(true); //making the frame visible
    }
}
