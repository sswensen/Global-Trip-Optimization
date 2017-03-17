package com.csu2017sp314.dtr07.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
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
    //private JFrame face; //User interface with locations
    private ArrayList<ArrayList<String>> trips = new ArrayList<>();
    private ArrayList<String> tempLoc;
    private String workingDirectoryFilePath;
    private JFrame uOp;
    private GridBagConstraints gbc;
    private boolean tick = false;
    private int savedTrip = -1;
    private int filenameIncrementer = 1;
    private int z = 0; //Number of saved trips
    private ArrayList<JButton> buttons = new ArrayList<>();
    private String tripName = "ERROR";

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

    /*int createFaceGUI() {
        face = new JFrame("User Options");
        face.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        face.setLocation(1063, 0);
        face.setSize(300, 802);
        face.setVisible(true);
        return 1;
    }*/

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

    private void nameTrip() {
        /*JFrame nameThisTrip = new JFrame("Enter a name for this trip");
        JFrame holding = new JFrame("");
        JButton inputButton = new JButton("Send");
        JTextArea editTextArea = new JTextArea("Type Here!");
        JTextArea uneditTextArea = new JTextArea();
        holding.setLayout(new BorderLayout());
        uneditTextArea.setEditable(false);
        //INPUT TEXT AREA
        editTextArea.setBackground(Color.BLUE);
        editTextArea.setForeground(Color.WHITE);
        //SET CONTENT PANE
        Container c = holding.getContentPane();
        //ADD COMPONENTS TO CONTENT PANE
        c.add(uneditTextArea, BorderLayout.CENTER);
        c.add(editTextArea, BorderLayout.WEST);
        c.add(inputButton, BorderLayout.EAST);
        inputButton.addActionListener((ActionEvent e) -> {
            tripNames.add(editTextArea.getText());

            tempFname = editTextArea.getText();

            System.out.println("Adding " + editTextArea.getText() + " to array at " + tripNames.indexOf(editTextArea.getText()));
            editTextArea.setText("");
            holding.dispatchEvent(new WindowEvent(holding, WindowEvent.WINDOW_CLOSING));
        });*/


        JFrame holding = new JFrame("Enter name for trip");
        JTextField textField = new JTextField(20);
        JTextArea textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        holding.add(textField);
        textField.addActionListener((ActionEvent e) -> {
                String text = textField.getText();
                textArea.append(text + "\n");
                textField.selectAll();
                //Make sure the new text is visible, even if there
                //was a selection in the text area.
                textArea.setCaretPosition(textArea.getDocument().getLength());
                tripName = text;
                holding.dispatchEvent(new WindowEvent(holding, WindowEvent.WINDOW_CLOSING));
        });
        holding.setLocation(1063, 0);
        holding.setSize(200, 50);
        holding.setVisible(true);
    }

    void displayXML(ArrayList<String> ids) {
        tempLoc = new ArrayList<>();
        JPanel fTemp = createInnerPanel();
        JPanel loadPanel = createInnerPanel();
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        setGBC(0, 0, 2);

        JButton q = new JButton("  Display  ");
        q.addActionListener((ActionEvent e) -> {
            userAddLocList(tempLoc);
        });
        fTemp.add(q, gbc);

        setGBC(2, 0, 1);
        JButton s = new JButton(" Save Trip ");
        s.addActionListener((ActionEvent e) -> {
            ArrayList<String> trip = new ArrayList<>(tempLoc);
            if(savedTrip < 0 || trips.size() == 0) {
                //--------
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
                    trips.add(trip);
                    setGBC(0, z, 1);
                    JButton load = new JButton("Load Trip " + tripName);
                    loadPanel.add(load, gbc);
                    load.addActionListener((ActionEvent eee) -> {
                        tempLoc = trip;
                        userAddLocList(tempLoc);
                        for(JButton a : buttons) {
                            tick = true;
                            a.doClick();
                            a.doClick();
                        }
                        savedTrip = z;
                    });
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
                z++;
            } else {
                trips.add(z, trip);
            }
        });
        fTemp.add(s, gbc);

        setGBC(3, 0, 1);
        JButton sa = new JButton(" Save As ");
        sa.addActionListener((ActionEvent e) -> {
            ArrayList<String> trip = new ArrayList<>(tempLoc);
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
                trips.add(trip);
                setGBC(0, z, 1);
                JButton load = new JButton("Load Trip " + tripName);
                loadPanel.add(load, gbc);
                load.addActionListener((ActionEvent eee) -> {
                    tempLoc = trip;
                    userAddLocList(tempLoc);
                    for(JButton a : buttons) {
                        tick = true;
                        a.doClick();
                        a.doClick();
                    }
                    savedTrip = z;
                });
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
            z++;
        });
        fTemp.add(sa, gbc);

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
                        System.out.println("Added " + id + " to array");
                        b.setText("   Remove   "); //If not pressed, toggle text and add
                    }

                } else if(b.getText().equals("   Remove   ")) {
                    if(tempLoc.contains(id)) {
                        tempLoc.remove(id);
                        System.out.println("Removed " + id + " from array");
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
            setGBC(0, numButtons, 1);
            fTemp.add(b, gbc);
            setGBC(1, numButtons, 3);
            fTemp.add(t2, gbc);
        }

        ImageIcon icon = new ImageIcon(workingDirectoryFilePath + "/" + "favicon.ico", "HELP2");
        options.addTab("Locations", icon, fTemp, "Locations");
        options.addTab("Load Trips", icon, loadPanel, "Load saved trips");
        //options.addTab("Four", face.getContentPane());
        //uOp.setMinimumSize(new Dimension(500, 500));

        uOp.pack();
    }

    void refresh() throws Exception {
        map.setVisible(false);
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
        map.setVisible(true); //making the frame visible
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
