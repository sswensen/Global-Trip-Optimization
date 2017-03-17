package com.csu2017sp314.dtr07.View;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.io.File;

/*
 * Created by SummitDrift on 3/6/17.
 * File for displaying map with interactive stuff
 */

public class MapGUI {
    private Consumer<String> callback; //Used if other callback method is used
    private Consumer<ArrayList<String>> callback2;
    private String filename;
    private JFrame map; //Map that displays locations
    private JTabbedPane options;
    private JFrame face; //User interface with locations
    private ArrayList<ArrayList<String>> trips = new ArrayList<>();
    private ArrayList<String> tripNames = new ArrayList<>();
    private ArrayList<String> tempLoc;
    private String workingDirectoryFilePath;
    private JFrame uOp;
    private GridBagConstraints gbc;
    private boolean tick = false;
    private boolean rightTick = false;
    private int savedTrip = -1;
    private int filenameIncrementer = 1;
    private int z = -1; //Number of saved trips
    private int z2 = 0; //You'll figure it out
    private ArrayList<JButton> buttons = new ArrayList<>();
    private String tripName = "ERROR";
    private JPanel loadPanel;
    private Group root;
    private JPanel fTemp;
    private JLabel currentTrip;

    MapGUI() {

    }

    void setCallback(Consumer<String> callback) {
        this.callback = callback;
    }

    void setCallback2(Consumer<ArrayList<String>> callback2) {
        this.callback2 = callback2;
    }

    /*public void userAddLoc(String id) { //Used if other callback method is used
        callback.accept(id);
    }*/

    private void userAddLocList(ArrayList<String> ids) {
        callback2.accept(ids);
    }

    int init(String filename) throws Exception {
        this.filename = filename;
        this.workingDirectoryFilePath = System.getProperty("user.dir") + "/";
        //new Convert(filename, -1);
        options = new JTabbedPane();
        //ImageIcon icon = new ImageIcon("png/favicon.ico", "HELP2");
        //createMapGUI(filename);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                background(filename);
            }
        });
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

    private void background(String filename) {
        map = new JFrame("TripCo");
        final JFXPanel fxPanel = new JFXPanel();
        map.add(fxPanel);
        map.setLocation(0, 0);
        map.setSize(1064, 802);
        map.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                fxPanel.setScene(createBGScene(filename));
            }
        });
    }

    private Scene createBGScene(String filename) {
        root = new Group();
        Scene scene = new Scene(root);
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();
        browser.setPrefSize(1064, 802);
        try {
            URL bgURL = new File(workingDirectoryFilePath + filename + ".svg").toURI().toURL();
            webEngine.load(bgURL.toString());
        } catch(Exception e) {
            System.err.println("Error reading URL of background image");
        }
        //webEngine.load("file://" + workingDirectoryFilePath + filename + ".svg");
        System.out.println("Attempting to display \"" + workingDirectoryFilePath + filename + ".svg\"");
        root.getChildren().add(browser);
        return (scene);
    }

    private void updateBG() {
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();
        browser.setPrefSize(1064, 802);
        try {
            URL bgURL = new File(workingDirectoryFilePath + filename + ".svg").toURI().toURL();
            webEngine.load(bgURL.toString());
        } catch(Exception e) {
            System.err.println("Error reading URL of background image [2]");
        }
        root.getChildren().add(browser);
    }

    private int createMapGUI(String filename) {
        map = new JFrame("TripCo"); //creating instance of JFrame
        map.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //Closes app if window closes
        map.setLocationRelativeTo(null);
        map.setLayout(new BorderLayout());
        map.setContentPane(new JLabel(new ImageIcon(workingDirectoryFilePath + "png/" + filename + ".png"))); //Creates png background
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

    private int createOptionsGUI() {
        uOp = new JFrame("User Options");
        uOp.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //Closes app if window closes
        uOp.getContentPane().add(options, BorderLayout.CENTER);
        //uOp.setSize(300, 802);
        uOp.setLocation(1063, 0);
        uOp.setVisible(true);
        return 1;
    }

    private JPanel createInnerPanel() {
        JPanel jplPanel = new JPanel();
        jplPanel.setLayout(new GridBagLayout());
        return jplPanel;
    }

    private void setGBC(int gridX, int gridY, int gridWidth) {
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.gridwidth = gridWidth;
    }

    private JButton addSaveButton(String name) {
        JButton sa = new JButton(name);
        sa.addActionListener((ActionEvent e) -> {
            ArrayList<String> trip = new ArrayList<>(tempLoc);
            if((savedTrip < 0 || trips.size() == 0 || name.equals(" Save As "))) {
                z++;
                //-----
                JFrame holding = new JFrame("Enter name for trip");
                JTextField textField = new JTextField(20);
                JTextArea textArea = new JTextArea(5, 20);
                textArea.setEditable(false);
                holding.add(textField);
                textField.addActionListener((ActionEvent ee) -> {
                    String text = textField.getText();
                    textArea.append(text + "\n");
                    textField.selectAll();
                    textArea.setCaretPosition(textArea.getDocument().getLength());
                    tripName = text;
                    holding.dispatchEvent(new WindowEvent(holding, WindowEvent.WINDOW_CLOSING));
                    trips.add(new ArrayList<>(trip));
                    System.out.println("Adding " + trip + " to trips at index " + (trips.size()-1));
                    tripNames.add(tripName);
                    if(rightTick) {
                        setGBC(1, z2, 1);
                        rightTick = false;
                        z2++;
                    } else {
                        setGBC(0, z2, 1);
                        rightTick = true;
                    }
                    if(trips.size() == 0 || name.equals(" Save Trip "))
                        savedTrip = z;
                    System.out.println("Trip name is " + tripName);
                    JButton load = new JButton("Load Trip " + tripName);
                    loadPanel.add(load, gbc);
                    System.out.println("Added button " + load.getText());
                    load.addActionListener((ActionEvent eee) -> {
                        System.out.println("Attempting to load trip " + load.getText().substring(10) + " containing " + trips.get(tripNames.indexOf(load.getText().substring(10))));
                        tempLoc = trips.get(tripNames.indexOf(load.getText().substring(10)));
                        userAddLocList(tempLoc);
                        updateTripLabel(load.getText().substring(10));
                        for(JButton a : buttons) {
                            tick = true;
                            a.doClick();
                            a.doClick();
                        }

                        savedTrip = tripNames.indexOf(load.getText().substring(10));

                    });
                    updateTripLabel(load.getText().substring(10));
                    savedTrip = tripNames.indexOf(load.getText().substring(10));
                    System.out.println("Setting savedTrip to " + tripNames.indexOf(load.getText().substring(10)));
                });
                holding.setLocation(1063, 0);
                holding.setSize(200, 50);
                holding.setVisible(true);
                //--------
            /*setGBC(1, z, 1);
            JButton deleteTrip = new JButton("Delete");
            loadPanel.add(deleteTrip, gbc);
            deleteTrip.addActionListener((ActionEvent ee) -> {
                trips.remove(z - 1);
                loadPanel.remove(load);
                loadPanel.remove(deleteTrip);
                uOp.pack();
            });*/
            } else {
                /*if(trips.size() == 1) {
                    trips.remove(0);
                    trips.add(0, trip);
                    System.out.println("Adding " + trip + " to trips at index 0");
                } else{*/
                    trips.remove(savedTrip);
                    trips.add(savedTrip, new ArrayList<>(trip));
                    System.out.println("Adding " + trip + " to trips at index " + savedTrip);
                //}
            }
            System.out.println("savedTrip is " + savedTrip);
            System.out.println("Z is " + z);
            printAll();
            System.out.println("--------------------------------");
        });
        return sa;
    }

    private void updateTripLabel(String name) {
        fTemp.remove(currentTrip);
        setGBC(0, 0, 4);
        currentTrip = new JLabel("Editing \"" + name + "\"", SwingConstants.CENTER);
        fTemp.add(currentTrip);
    }

    private JPanel generateMapDisplayOptions() {
        JPanel panel = createInnerPanel();
        JButton names = new JButton("Toggle Names");
        JButton ids = new JButton("Toggle IDs");
        JButton mileage = new JButton("Toggle Mileage");
        JButton twoOpt = new JButton("Toggle 2-opt");
        JButton threeOpt = new JButton("Toggle 3-opt");


        setGBC(0, 0, 1);
        panel.add(names, gbc);
        setGBC(1, 0, 1);
        panel.add(ids, gbc);
        setGBC(0, 1, 2);
        panel.add(mileage, gbc);
        setGBC(0, 2, 1);
        panel.add(twoOpt, gbc);
        setGBC(1, 2, 1);
        panel.add(threeOpt, gbc);
        return panel;
    }

    void displayXML(ArrayList<String> ids) {
        tempLoc = new ArrayList<>();
        fTemp = createInnerPanel();
        loadPanel = createInnerPanel();
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        setGBC(0, 0, 4);
        currentTrip = new JLabel("Untitled trip", SwingConstants.CENTER);
        currentTrip.setPreferredSize(new Dimension(30,30));
        fTemp.add(currentTrip, gbc);

        setGBC(0, 1, 2);

        JButton q = new JButton("  Display  ");
        q.addActionListener((ActionEvent e) -> {
            userAddLocList(tempLoc);
        });
        fTemp.add(q, gbc);

        setGBC(2, 1, 1);
        fTemp.add(addSaveButton(" Save Trip "), gbc);

        setGBC(3, 1, 1);
        fTemp.add(addSaveButton(" Save As "), gbc);

        int numButtons = 0;
        for(String id : ids) {
            JButton b = new JButton("      Add      ");
            buttons.add(b);
            b.addActionListener((ActionEvent e) -> { //This fires when button b is pressed, unique for each instance!
                    /*
                        We can use the following method to run the call back each time a location is clicked (don't know about efficiency here)
                        Or we can do one where the callback is only initiated when the user clicks the button that loads the map after selecting locations
                        I'm going to implement the second method as the first one already is.
                        To use the first method, uncomment the line below.
                     */
                //userAddLoc(id); //This is a callback to View
                if(tick) {
                    for(int i = 0; i < tempLoc.size(); i++) {
                        if(tempLoc.contains(id) && b.getText().equals("      Add      ")) {
                            b.setText("   Remove   ");
                        } else if(!tempLoc.contains(id) && b.getText().equals("   Remove   ")) {
                            b.setText("      Add      ");
                        }
                    }
                }
                tick = false;

                if(b.getText().equals("      Add      ")) { //Checks if button has already been pressed
                    if(!tempLoc.contains(id)) {
                        tempLoc.add(id);
                        //System.out.println("Added " + id + " to array");
                        b.setText("   Remove   "); //If not pressed, toggle text and add
                    }

                } else if(b.getText().equals("   Remove   ")) {
                    if(tempLoc.contains(id)) {
                        tempLoc.remove(id);
                        //System.out.println("Removed " + id + " from array");
                        b.setText("      Add      ");
                    }
                }
            });

            gbc.fill = GridBagConstraints.NONE;
            JButton t = new JButton(id);
            JLabel t2 = new JLabel(id);
            t.setEnabled(false);

            fTemp.add(b);
            b.setVisible(true);
            t.setVisible(true);

            numButtons++;
            setGBC(0, numButtons+1, 1);
            fTemp.add(b, gbc);
            setGBC(1, numButtons+1, 3);
            fTemp.add(t2, gbc);
        }

        ImageIcon icon = new ImageIcon(workingDirectoryFilePath + "/" + "favicon.ico", "HELP2");
        options.addTab("Locations", icon, fTemp, "Locations");
        options.addTab("Load Trips", icon, loadPanel, "Load saved trips");

        options.addTab("Map Options", icon, generateMapDisplayOptions(), "Pane for map options");
        //options.addTab("Four", face.getContentPane());
        //uOp.setMinimumSize(new Dimension(500, 500));

        uOp.pack();
    }

    void refresh() throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        updateBG();
                    }
                });
            }
        });
        /*map.setVisible(false);
        new Convert(filename, filenameIncrementer);
        JLabel background = new JLabel(new ImageIcon(workingDirectoryFilePath + "png/" + filename + filenameIncrementer + "_User.png"));
        File temp = new File(workingDirectoryFilePath + "png/" + filename + (filenameIncrementer - 1) + "_User.png");
        if(!temp.delete() && filenameIncrementer != 1) {
            System.out.println("Error deleting " + temp.getPath());
        }
        background.setLayout(new BorderLayout());
        map.setContentPane(background);
        filenameIncrementer++;

        map.setSize(1063, 801); //Refreshes window, needed or image doesn't appear
        map.setSize(1064, 802); //Second part for refreshing the window
        map.setVisible(true); //making the frame visible*/
    }

    boolean cleanup() {
        boolean ret;
        File t = new File(workingDirectoryFilePath + "png/" + filename + (filenameIncrementer - 1) + "_User.png");
        ret = t.delete();
        File temp = new File(workingDirectoryFilePath + "png/" + filename + ".png");
        Boolean ret2 = temp.delete();
        filenameIncrementer = 0;
        return ret & ret2;
    }

    private void printAll() {
        for(int i = 0; i < trips.size(); i++) {
            System.out.println("Trips at " + i + " is " + trips.get(i).toString());
        }
        for(int i = 0; i < tripNames.size(); i++) {
            System.out.println("Trip Names at " + i + " is " + tripNames.get(i));
        }
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
