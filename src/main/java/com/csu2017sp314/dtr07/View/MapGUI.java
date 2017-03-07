package com.csu2017sp314.dtr07.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
        File file = new File("./Colorado14ers.svg");
        BufferedImage image = ImageIO.read(file);
        JFrame f = new JFrame(); //creating instance of JFrame
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Closes app if window closes
        JLabel label = new JLabel(new ImageIcon(image));
        f.getContentPane().add(label);
        JButton b = new JButton("click"); //creating instance of JButton
        b.setBounds(964, 0, 100, 40); //x axis, y axis, width, height
        f.add(b); //adding button in JFrame
        f.pack();
        f.setSize(1064, 780); //400 width and 500 height
        f.setLayout(null); //using no layout managers
        f.setVisible(true); //making the frame visible
    }
}
