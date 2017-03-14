package com.csu2017sp314.dtr07.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
    private Consumer<String> callback;
    private Consumer<ArrayList<String>> callback2;
    private String filename;
    private JFrame map; //Map that displays locations
    private JTabbedPane options;
    private JFrame face; //User interface with locations
    private JFrame uOp;
    //private boolean tick = false;
    private int killmenow = 1;

    MapGUI() {

    }

    void setCallback(Consumer<String> callback) {
        this.callback = callback;
    }

    void setCallback2(Consumer<ArrayList<String>> callback2) {
        this.callback2 = callback2;
    }

    public void userAddLoc(String id) {
        callback.accept(id);
    }

    private void userAddLocList(ArrayList<String> ids) {
        callback2.accept(ids);
    }

    int init(String filename) throws Exception {
        this.filename = filename;
        new Convert(filename, -1);
        options = new JTabbedPane();
        //ImageIcon icon = new ImageIcon("png/favicon.ico", "HELP2");
        createMapGUI(filename);
        //createFaceGUI();

        /*JPanel jplInnerPanel1 = createInnerPanel("Tab 1 Contains Tooltip and Icon");
		options.addTab("One", icon, jplInnerPanel1, "Tab 1");
        options.setSelectedIndex(0);
		JPanel jplInnerPanel2 = createInnerPanel("Tab 2 Contains Icon only");
        options.addTab("Two", icon, jplInnerPanel2);*/


        createOptionsGUI();
        map.setVisible(true); //making the frame visible
        return 1;
    }

    int createMapGUI(String filename) {
        map = new JFrame("TripCo"); //creating instance of JFrame
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
        return 1;
    }

    int createFaceGUI() {
        face = new JFrame("User Options");
        face.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        face.setLocation(1063, 0);
        face.setSize(300, 802);
        face.setVisible(true);
        return 1;
    }

    int createOptionsGUI() {
        uOp = new JFrame("User Options");
        uOp.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //Closes app if window closes
        uOp.getContentPane().add(options, BorderLayout.CENTER);
        uOp.setSize(300, 802);
        uOp.setLocation(1063, 0);
        uOp.setVisible(true);
        return 1;
    }

    protected JPanel createInnerPanel(String text) {
        JPanel jplPanel = new JPanel();
        jplPanel.setLayout(new GridBagLayout());
        return jplPanel;
    }

    void displayXML(ArrayList<String> ids) {
        ArrayList<String> tempLoc = new ArrayList<>();
        JPanel fTemp = createInnerPanel("");
        /*System.out.println("Printing tempLoc");
        for(int i = 0; i < ids.size(); i++) {
            System.out.println("[GUI] ID at index " + i + " = "+ ids.get(i));
        }
        System.out.println("End printing tempLoc");*/

        //Adding button to load tempLocs
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy = 0;

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
        q.setBounds(5, 5, 90, 30);
        //face.add(q);
        //fTemp.add(q);
        fTemp.add(q, gbc);

        int index = 35;
        int i = 0;
        for (String id : ids) {
            JButton b = new JButton("   Add   ");
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
                    if(b.getText().equals("   Add   ")) { //Checks if button has already been pressed
                        tempLoc.add(id);
                        System.out.println("Added " + id + "to array");
                        //b.setBackground(Color.BLACK);
                        //b.setOpaque(true); //Doesn't work for some unknown reason
                        b.setText("Remove"); //If not pressed, toggle text and add
                    } else if(b.getText().equals("Remove")) {
                        tempLoc.remove(id);
                        System.out.println("Removed " + id + "to array");
                        b.setText("   Add   "); //If already pressed, toggle text and remove
                    }
                }
            });

            gbc.fill = GridBagConstraints.NONE;
            JButton t = new JButton(id);
            t.setEnabled(false);
            //t.setPreferredSize(new Dimension(150,t.getHeight()));

            //b.setBounds(5, index, 90, 3560);
            //t.setBounds(95, index, 200, 30);
            //face.add(b);
            fTemp.add(b);
            b.setVisible(true);
            //face.add(t);
            //fTemp.add(t);
            t.setVisible(true);
            index += 35;

            gbc.gridwidth = 1;
            gbc.gridx = 0;
            gbc.gridy = i+1;
            i++;
            fTemp.add(b, gbc);

            gbc.gridwidth = 2;
            gbc.gridx = 1;
            fTemp.add(t, gbc);
        }

        //face.setSize(299, 801);
        //face.setSize(300, 802);
        ImageIcon icon = new ImageIcon("png/favicon.ico", "HELP2");
        //fTemp.setLayout(new GridLayout(20, 2));
        options.addTab("Locations", icon, fTemp, "Locations");
        //options.addTab("Four", face.getContentPane());
        uOp.pack();
        //uOp.setVisible(true);
        //uOp.setMinimumSize(uOp.getSize());
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
        File temp = new File("png/" + filename + (killmenow-1) + "_User.png");
        if(!temp.delete()) {
            System.out.println("Error deleting " + temp.getPath());
        }
        background.setLayout(new BorderLayout());
        map.setContentPane(background);
        killmenow++;

        //map.setSize(w - 1, h - 1);
        //map.setSize(w, h);
        map.setSize(1063, 801); //Refreshes window, needed or image doesn't appear
        map.setSize(1064, 802); //Second part for refreshing the window

        map.setVisible(true); //making the frame visible
    }

    boolean cleanup() {
        boolean ret;
        /*for (int i = 0; i < killmenow; i++) {
            File temp = new File("png/" + filename + i + "_User.png");
            ret = temp.delete();
        }*/
        File t = new File("png/" + filename + (killmenow-1) + "_User.png");
        ret = t.delete();
        File temp = new File("png/" + filename + ".png");
        Boolean ret2 = temp.delete();
        killmenow = 0;
        return ret & ret2;
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
