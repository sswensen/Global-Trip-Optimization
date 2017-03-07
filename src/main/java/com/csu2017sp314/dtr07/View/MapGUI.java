package com.csu2017sp314.dtr07.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

/*
 * Created by SummitDrift on 3/6/17.
 * File for displaying map with interactive stuff
 */
public class MapGUI {
    String filename;

    public MapGUI(String filename) {
        this.filename = filename;
    }

    public static void main(String[] args) throws Exception {
        Convert c = new Convert("ColoradoSkiResorts");
        JFrame f = new JFrame("TripCo"); //creating instance of JFrame
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Closes app if window closes
        JButton b = new JButton("click"); //creating instance of JButton
        b.setBounds(964, 0, 100, 40); //x axis, y axis, width, height
        f.add(b); //adding button in JFrame
        f.setTitle("Background Color for JFrame");
        f.setSize(400,400);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setLayout(new BorderLayout());
        f.setContentPane(new JLabel(new ImageIcon("Colorado14ers.png")));
        f.setLayout(new FlowLayout());
        f.setSize(1063,779); //Refreshes window, needed or image doesn't appear
        f.setSize(1064,780);
        //f.pack(); //Will make everything MASSIVE
        f.setLayout(null); //using no layout managers
        f.setVisible(true); //making the frame visible
    }
}
