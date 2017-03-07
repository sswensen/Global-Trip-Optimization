package com.csu2017sp314.dtr07.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;


/*
 * Created by SummitDrift on 3/6/17.
 * File for displaying map with interactive stuff
 */
public class MapGUI {
    String filename;

    public MapGUI(String filename) {
        this.filename = filename;
    }

    public int init() throws Exception {
        new Convert(filename);
        JFrame f = new JFrame("TripCo"); //creating instance of JFrame
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //Closes app if window closes
        JButton b = new JButton("click"); //creating instance of JButton
        b.setBounds(964, 0, 100, 40); //x axis, y axis, width, height
        f.add(b); //adding button in JFrame
        f.setLocationRelativeTo(null);
        f.setLayout(new BorderLayout());
        f.setContentPane(new JLabel(new ImageIcon("png/" + filename + ".png")));
        f.setLayout(new FlowLayout());

        //Code for aligning to left side of screen
        /* GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - f.getWidth();
        */

        f.setLocation(0,0);
        f.setSize(1063,801); //Refreshes window, needed or image doesn't appear
        f.setSize(1064,802);
        //f.pack(); //Will make everything MASSIVE
        f.setVisible(true); //making the frame visible
        return 1;
    }

    public static void main(String[] args) throws Exception {
        new Convert("Colorado14ers");
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
