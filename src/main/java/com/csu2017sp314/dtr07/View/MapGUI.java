package com.csu2017sp314.dtr07.View;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.function.Consumer;



/**
 * Created by SummitDrift on 3/6/17.
 *
 * @author Scott Swensen
 *         File for displaying map with interactive stuff
 *         This file is going to be really complicated
 *         Prepare yourself
 *         Winter is coming...
 */

public class MapGUI {
    private Consumer<String> callback; //Used if other callback method is used
    private Consumer<ArrayList<String>> callback2;
    private Consumer<String> callback3; //Used if other callback method is used
    private Consumer<ArrayList<String>> callback4; //Used for searching database
    private String filename;
    private JFrame map; //Map that displays locations
    private JTabbedPane options;
    //private JTabbedPane itineraryTabs;
    private JFrame face; //User interface with locations
    private ArrayList<ArrayList<String>> trips = new ArrayList<>();
    private ArrayList<String> tripNames = new ArrayList<>();
    private ArrayList<String> tempLoc;
    private String workingDirectoryFilePath;
    private JFrame uOp;
    private JFrame itinerary;
    private GridBagConstraints gbc = new GridBagConstraints();
    private boolean tick = false;
    private boolean tick2 = false;
    private boolean rightTick = false;
    private int savedTrip = -1;
    private int filenameIncrementer = 1;
    private int z = -1; //Number of saved trips
    private int z2 = 1; //You'll figure it out
    private ArrayList<JButton> buttons = new ArrayList<>();
    private String tripName = "ERROR";
    private JPanel loadPanel;
    private Group root;
    private JPanel fTemp; //Add buttons
    private JPanel fTemp2; //Itinerary
    private JPanel databaseWindow; //GUI dropdowns
    private JFrame databaseFrame; //Frame that databaseWindow goes in
    private JLabel currentTrip;
    private ArrayList<String> lastTrip = new ArrayList<>();
    private int width;
    private int height;
    private String unit;
    private ArrayList<String> fiveThingsForDatabase = new ArrayList<>(); //Used for callback
    private int index = 0; //I inked...
    private ArrayList<GUILocation> guiLocations = new ArrayList<>();

    private DefaultTableModel model;
    private JTable table;
    private DefaultTableModel dm = new DefaultTableModel();
    private JTable table2 = new JTable(dm);
    private DefaultTableModel dm2 = new DefaultTableModel();
    private JTable table3 = new JTable(dm2);
    private ArrayList<String> databaseLocations = new ArrayList<>();

    private int databaseNumberFound = 0;

    MapGUI() {

    }

    void setCallback(Consumer<String> callback) {
        this.callback = callback;
    }

    void setCallback2(Consumer<ArrayList<String>> callback2) {
        this.callback2 = callback2;
    }

    void setCallback3(Consumer<String> callback3) {
        this.callback3 = callback3;
    }

    void setCallback4(Consumer<ArrayList<String>> callback4) {
        this.callback4 = callback4;
    }

    /*public void userAddLoc(String id) { //Used if other callback method is used
        callback.accept(id);
    }*/

    private void userAddLocList(ArrayList<String> ids) {
        callback2.accept(ids);
    }

    private void mapOptions(String option) {
        callback3.accept(option);
    }

    private void searchDatabase() {
        callback4.accept(fiveThingsForDatabase);
    }

    int init(String filename) throws Exception {
        this.filename = filename;
        this.workingDirectoryFilePath = System.getProperty("user.dir") + "/";
        //new Convert(filename, -1);
        options = new JTabbedPane();
        //itineraryTabs = new JTabbedPane();
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


        //createOptionsGUI();
        uOp = createJFrame("User Options", width + 1, 0, options);
        //createItineraryWindow();
        itinerary = createScrollingJFrame("Itinerary", 0, height + 42);

        map.setVisible(true); //making the frame visible
        return 1;
    }

    private void background(String filename) {
        map = new JFrame("TripCo");
        final JFXPanel fxPanel = new JFXPanel();
        map.add(fxPanel);
        map.setLocation(0, 0);
        map.setSize(width, height);
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
        browser.setPrefSize(width, height);
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
        browser.setPrefSize(width, height);
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
        map.setSize(width - 1, height - 1); //Refreshes window, needed or image doesn't appear
        map.setSize(width, height);
        return 1;
    }

    private int createFaceGUI() {
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

    private int createItineraryWindow() {
        JFrame itin = new JFrame("Itinerary");
        itin.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        itin.getContentPane().add(options, BorderLayout.CENTER);
        itin.setLocation(1360, 0);
        itin.setVisible(true);
        return 1;
    }

    private JButton createXMLBrowser() {
        JButton ret = new JButton("Load From XML");
        ret.addActionListener((ActionEvent e) -> {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setCurrentDirectory(new File(workingDirectoryFilePath));
            int result = jFileChooser.showOpenDialog(new JFrame());
            if(result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jFileChooser.getSelectedFile();
                try {
                    readXML(selectedFile.getAbsolutePath());
                } catch(Exception ee) {
                    System.err.println("Error in createXMLBrowser");
                }
            }
        });
        return ret;
    }

    private JFrame createJFrame(String name, int x, int y, JTabbedPane tabs) {
        JFrame ret = new JFrame(name);
        ret.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ret.getContentPane().add(tabs, BorderLayout.CENTER);
        ret.setLocation(x, y);
        ret.setVisible(true);
        return ret;
    }

    private JFrame createScrollingJFrame(String name, int x, int y) {
        JFrame ret = new JFrame(name);
        ret.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //ret.getContentPane().add(new BorderLayout.CENTER);
        ret.setLocation(x, y);
        ret.setVisible(true);
        return ret;
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

    private int copySVG(String name) {
        //If ids array is changed, need to modify call in addSaveButton
        Path FROM = Paths.get(workingDirectoryFilePath + filename + ".svg");
        Path TO = Paths.get(workingDirectoryFilePath + "png/" + name + ".svg");
        CopyOption[] options = new CopyOption[]{
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
        };
        try {
            Files.copy(FROM, TO, options);
        } catch(Exception e) {
            System.err.println(e);
            System.err.println("Error copying saved trip file");
            return -1;
        }
        return 1;
    }

    private void readXML(String selectionXml) throws SAXException, IOException, ParserConfigurationException {
        Document readXml;
        File xmlFile = new File(selectionXml);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        readXml = dBuilder.parse(xmlFile);
        readXml.getDocumentElement().normalize();
        //System.out.println("*Testing*   Root element :" + readXml.getDocumentElement().getNodeName());
        ArrayList<String> tempTrip = new ArrayList<>();
        NodeList nList = readXml.getElementsByTagName("destinations");
        NodeList nList2 = readXml.getElementsByTagName("title");
        System.out.println("nnList2 size = " + nList2.getLength());
        for(int i = 0; i < nList2.getLength(); i++) {
            Node a = nList2.item(i);
            tripName = a.getTextContent();
        }
        for(int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            //System.out.println("\nCurrent Element :" + nNode.getNodeName());
            if(nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                int i = 0;
                while(eElement.getElementsByTagName("id").item(i) != null) {
                    tempTrip.add(eElement.getElementsByTagName("id").item(i).getTextContent());
                    i++;
                }
            }
        }
        trips.add(tempTrip);
        tripNames.add(tripName);
        addLoadButton(tripName);
        System.out.println(selectionXml);
        for(int i = 0; i < tripNames.size(); i++) {
            System.out.println("id at index " + i + " = " + tripNames.get(i));
        }
        System.out.println("trips size = " + trips.size());
    }

    private int saveTripToXML(String name, ArrayList ids) throws ParserConfigurationException, TransformerException {
        Document saveXml;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = factory.newDocumentBuilder();
        saveXml = docBuilder.newDocument();
        //root element
        Element rootElement = saveXml.createElement("xml");
        saveXml.appendChild(rootElement);

        //selection
        Element selection = saveXml.createElement("selection");
        rootElement.appendChild(selection);

        //<title>name</title>
        Element tripName = saveXml.createElement("title");
        tripName.appendChild(saveXml.createTextNode(name));
        selection.appendChild(tripName);

        //<filename>file.csv</filename>
        Element csvFilename = saveXml.createElement("filename");
        csvFilename.appendChild(saveXml.createTextNode(filename + ".svg"));
        selection.appendChild(csvFilename);


        Element destinations = saveXml.createElement("destinations");
        selection.appendChild(destinations);


        for(int i = 0; i < ids.size(); i++) {
            Element id = saveXml.createElement("id");
            id.appendChild(saveXml.createTextNode((String) ids.get(i)));
            destinations.appendChild(id);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        //String[] cut = filename.split("/");
        //String f = cut[cut.length - 1].substring(0, cut[cut.length - 1].length() - 4);

        //XML document
        DOMSource source = new DOMSource(saveXml);
        StreamResult result = new StreamResult(new File(workingDirectoryFilePath + "png/" + name + ".xml"));
        transformer.transform(source, result);
        copySVG(name);
        return 1;
    }

    private void addLoadButton(String name) {
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
            lastTrip = new ArrayList<>(tempLoc);
            updateTripLabel(load.getText().substring(10));
            for(JButton a : buttons) {
                tick = true;
                a.doClick();
                a.doClick();
            }
            for(int i = 0; i < dm.getRowCount(); i++) {
                System.out.println(dm.getValueAt(i, 0) + " " + dm.getValueAt(i, 1));
                for(int j = 0; j < tempLoc.size(); j++) {
                    if(tempLoc.contains(dm.getValueAt(i, 1)) && dm.getValueAt(i, 0).equals("Add")) {
                        dm.setValueAt("Remove", i, 0);
                    } else if(!tempLoc.contains(dm.getValueAt(i, 1)) && dm.getValueAt(i, 0).equals("Remove")) {
                        dm.setValueAt("Add", i, 0);
                    }
                }
            }
            for(int i = 0; i < dm.getRowCount(); i++) {
                System.out.println(dm.getValueAt(i, 0) + " " + dm.getValueAt(i, 1));
                for(int j = 0; j < tempLoc.size(); j++) {
                    if(tempLoc.contains(dm.getValueAt(i, 1)) && dm.getValueAt(i, 0).equals("Add")) {
                        dm.setValueAt("Remove", i, 0);
                    } else if(!tempLoc.contains(dm.getValueAt(i, 1)) && dm.getValueAt(i, 0).equals("Remove")) {
                        dm.setValueAt("Add", i, 0);
                    }
                }
            }
            savedTrip = tripNames.indexOf(load.getText().substring(10));
        });
        updateTripLabel(load.getText().substring(10));
        savedTrip = tripNames.indexOf(load.getText().substring(10));
        System.out.println("Setting savedTrip to " + tripNames.indexOf(load.getText().substring(10)));
    }

    private JButton addSaveButton(String name) throws ParserConfigurationException, TransformerException {
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
                    lastTrip = new ArrayList<>(trip);
                    userAddLocList(trip);
                    System.out.println("Adding " + trip + " to trips at index " + (trips.size() - 1));
                    tripNames.add(tripName);
                    try {
                        saveTripToXML(tripName, trip);
                    } catch(ParserConfigurationException parseException) {

                    } catch(TransformerException transException) {

                    }
                    addLoadButton(name);
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
                userAddLocList(trip); //Update svg
                try {
                    saveTripToXML(tripNames.get(savedTrip), trip); //Save xml and copy svg
                } catch(ParserConfigurationException parseException) {

                } catch(TransformerException transException) {

                }
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

    private JButton mapDisplayButtons(String name) {
        JButton b = new JButton("Add " + name);
        b.addActionListener((ActionEvent e) -> {
            if(b.getText().equals("Add " + name)) { //Checks if button has already been pressed
                b.setText("Remove " + name); //If not pressed, toggle text and add
            } else if(b.getText().equals("Remove " + name)) {
                b.setText("Add " + name);
            }
            mapOptions(name);
            userAddLocList(tempLoc); //Use to be lastTrip
        });
        return b;
    }

    private JButton mapToggleUnits() {
        JButton b = new JButton("Switch to KM");
        b.addActionListener((ActionEvent e) -> {
            if(b.getText().equals("Switch to KM")) { //Checks if button has already been pressed
                b.setText("Switch to Miles"); //If not pressed, toggle text and add
                unit = "M";
            } else if(b.getText().equals("Switch to Miles")) {
                b.setText("Switch to KM");
                unit = "K";
            }
            mapOptions(unit);
            userAddLocList(lastTrip);
        });
        return b;
    }

    private JPanel generateMapDisplayOptions() {
        JPanel panel = createInnerPanel();

        setGBC(0, 0, 1);
        panel.add(mapDisplayButtons("Names"), gbc);
        setGBC(1, 0, 1);
        panel.add(mapDisplayButtons("IDs"), gbc);
        setGBC(0, 1, 1);
        panel.add(mapDisplayButtons("Distance"), gbc);
        setGBC(0, 2, 1);
        panel.add(mapDisplayButtons("2-opt"), gbc);
        setGBC(1, 2, 1);
        panel.add(mapDisplayButtons("3-opt"), gbc);
        setGBC(1, 1, 1);
        panel.add(mapToggleUnits(), gbc);
        return panel;
    }

    JComboBox makeDropdowns(ArrayList<String> options) {
        String whatIsThis = options.get(0);
        JComboBox drop = new JComboBox(options.toArray(new String[0])); //This converts options to a String[]
        index = 0;
        switch(whatIsThis) {
            case "Select an airport type filter":
                index = 0;
                break;
            case "Select a continent filter":
                index = 1;
                break;
            case "Select a country filter":
                index = 2;
                break;
            case "Select a region filter":
                index = 3;
                break;

        }
        drop.setEditable(false);
        /*drop.addActionListener((ActionEvent e) -> {
            JComboBox<String> combo = (JComboBox<String>) e.getSource();
            String selected = (String) combo.getSelectedItem();
            //TODOo set the returning arraylist after this method is called multiple times
            fiveThingsForDatabase.add(index, selected);
        });*/
        return drop;
    }

    ArrayList<String> sshImCheatingDontTell(String whatYouWant, String table, String wheres) {
        if(!wheres.equals("")) {
            String temp = wheres;
            wheres = "WHERE " + temp;
        }
        ArrayList<String> ret = new ArrayList<>();
        if(table.equalsIgnoreCase("airports"))
            ret.add("All airports");
        if(table.equalsIgnoreCase("continents"))
            ret.add("All continents");
        if(table.equalsIgnoreCase("countries"))
            ret.add("All countries");
        if(table.equalsIgnoreCase("regions"))
            ret.add("All regions");
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cs314", "sswensen", "830534566");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT COUNT(1) " + whatYouWant + " FROM " + table + wheres + " ORDER BY " + whatYouWant);
            rs.next();
            databaseNumberFound = rs.getInt(1);
            rs = st.executeQuery("SELECT DISTINCT " + whatYouWant + " FROM " + table + wheres + " ORDER BY " + whatYouWant);

            while(rs.next()) {
                ret.add(rs.getString(1));
            }
        } catch(Exception e) {
            System.err.println("Problem in MapGUI with database");
        }
        return ret;
    }

    void displayDatabaseWindow() throws Exception {
        fiveThingsForDatabase.add("");
        fiveThingsForDatabase.add("");
        fiveThingsForDatabase.add("");
        fiveThingsForDatabase.add("");
        fiveThingsForDatabase.add("");
        fiveThingsForDatabase.add("");
        fiveThingsForDatabase.add("");
        databaseFrame = new JFrame("Testing dropdowns");
        databaseFrame.setVisible(true);
        databaseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenHeight = screenSize.getHeight();
        databaseFrame.setLocation(1025, ((int) screenHeight - height) + 35);
        databaseWindow = createInnerPanel();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        setGBC(0, 0, 4);
        JComboBox airports = makeDropdowns(sshImCheatingDontTell("type", "airports", ""));
        databaseWindow.add(airports, gbc);

        setGBC(0, 1, 4);
        JComboBox continents = makeDropdowns(sshImCheatingDontTell("name", "continents", ""));
        databaseWindow.add(continents, gbc);

        setGBC(0, 2, 4);
        JComboBox countries = makeDropdowns(sshImCheatingDontTell("name", "countries", ""));
        databaseWindow.add(countries, gbc);

        setGBC(0, 3, 4);
        JComboBox regions = makeDropdowns(sshImCheatingDontTell("name", "regions", ""));
        databaseWindow.add(regions, gbc);

        //Search Municipality textField
        JPanel searchPanel = new JPanel();
        JTextField findTextField;
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        //JLabel findLabel = new JLabel("Search:");
        //searchPanel.add(findLabel);
        //searchPanel.add(Box.createRigidArea(new Dimension(6, 0)));
        findTextField = new JTextField(30);
        TextPrompt tp = new TextPrompt("Municipality Search", findTextField);
        findTextField.setForeground(Color.red);
        searchPanel.add(findTextField);
        //searchPanel.add(Box.createRigidArea(new Dimension(6, 0)));

        JButton findButton = new JButton("Search");
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String s = findTextField.getText().toUpperCase().trim();
                if (!s.equals("")) {
                    findTextField.setText(s);

                }
            }
        });
        //searchPanel.add(findButton);
        setGBC(0,6,4);
        databaseWindow.add(searchPanel,gbc);
        //Search Municipality textField

        //Search Airport textField
        JPanel searchPanel2 = new JPanel();
        JTextField findTextField2;
        searchPanel2.setLayout(new BoxLayout(searchPanel2, BoxLayout.X_AXIS));
        searchPanel2.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        //JLabel findLabel = new JLabel("Search:");
        //searchPanel.add(findLabel);
        //searchPanel.add(Box.createRigidArea(new Dimension(6, 0)));
        findTextField2 = new JTextField(30);
        TextPrompt tp2 = new TextPrompt("Airport Name Search", findTextField2);
        findTextField2.setForeground(Color.red);
        searchPanel2.add(findTextField2);
        //searchPanel.add(Box.createRigidArea(new Dimension(6, 0)));

        JButton findButton2 = new JButton("Search");
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String s = findTextField2.getText().toUpperCase().trim();
                if (!s.equals("")) {
                    findTextField2.setText(s);

                }
            }
        });
        //searchPanel.add(findButton);
        setGBC(0,7,4);
        databaseWindow.add(searchPanel2,gbc);
        //Search Airport textField

        setGBC(0, 8, 4);
        JButton searchDatabasePlease = new JButton("Search");
        searchDatabasePlease.addActionListener((ActionEvent e) -> {
            fiveThingsForDatabase.remove(0);
            fiveThingsForDatabase.add(0, (String) airports.getSelectedItem());
            fiveThingsForDatabase.remove(1);
            fiveThingsForDatabase.add(1, (String) continents.getSelectedItem());
            fiveThingsForDatabase.remove(2);
            fiveThingsForDatabase.add(2, (String) countries.getSelectedItem());
            fiveThingsForDatabase.remove(3);
            fiveThingsForDatabase.add(3, (String) regions.getSelectedItem());
            //fiveThingsForDatabase.remove(4);
            guiLocations.clear();
            searchDatabase();
            updateAddButtonsDatabase(); //Update database selection scroll window
        });
        databaseWindow.add(searchDatabasePlease, gbc);

        setGBC(0, 9, 1);
        JButton testingSearching = new JButton("Search for hardcoded 1");
        testingSearching.addActionListener((ActionEvent e) -> {
            ArrayList<String> testingNames = new ArrayList<>();
            testingNames.add("Berlin-SchÃ¶nefeld International Airport");
            testingNames.add("Denver International Airport");
            userAddLocList(testingNames);
        });
        databaseWindow.add(testingSearching, gbc);
        setGBC(1, 9, 1);
        JButton testingSearching2 = new JButton("Search for hardcoded 2");
        testingSearching2.addActionListener((ActionEvent e) -> {
            ArrayList<String> testingNames = new ArrayList<>();
            testingNames.add("Denver International Airport");
            testingNames.add("London Heathrow Airport");
            userAddLocList(testingNames);
        });
        databaseWindow.add(testingSearching2, gbc);
        setGBC(3, 9, 1);
        JButton selectAll = new JButton("Select all");
        selectAll.addActionListener((ActionEvent e) -> {
            databaseLocations.clear();
            for(GUILocation loc : guiLocations) {
                databaseLocations.add(loc.getName());
            }

        });
        databaseWindow.add(selectAll, gbc);


        updateAddButtonsDatabase();

        JScrollPane scroll = new JScrollPane(table3);
        setGBC(0, 10, 4);
        table3.setPreferredScrollableViewportSize(new Dimension(470, 260));
        databaseWindow.add(scroll, gbc);

        setGBC(0, 11, 4);
        JButton transferToFirstWindow = new JButton("Select");
        transferToFirstWindow.addActionListener((ActionEvent e) -> {
            //TODO instead of replacing the existing tempLoc/locationNames, maybe just add them to the list and add a clear button to the first window
            ArrayList<String> locationNames = searchDBLocationNames();
            updateTripLabel("Untitled trip");
            userAddLocList(locationNames);
            tempLoc = locationNames;
            updateAddButtonsAddRemove(locationNames);
        });
        databaseWindow.add(transferToFirstWindow, gbc);

        databaseFrame.add(databaseWindow);
        databaseFrame.pack();
    }

    void updateAddButtonsDatabase() {
        dm2.setRowCount(0);
        Vector<String> columnNames = new Vector<>();
        Vector<Vector<String>> addButtons = new Vector<>();
        columnNames.addElement("Click to add Destination");
        columnNames.addElement("Location");
        for(int i = 0; i < guiLocations.size(); i++) {
            Vector<String> temp = new Vector<>();
            temp.addElement("  ");
            temp.addElement(guiLocations.get(i).getName());
            addButtons.add(temp);
        }
        dm2.setDataVector(addButtons, columnNames);
        Action test = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable temp = (JTable) e.getSource();
                DefaultTableModel model = (DefaultTableModel) temp.getModel();
                int index = Integer.parseInt(e.getActionCommand());
                //private ArrayList<String> databaseLocations = new ArrayList<>();
                if(tick) {
                    for(int i = 0; i < guiLocations.size(); i++) {
                        if(databaseLocations.contains(model.getValueAt(index, 1)) && model.getValueAt(index, 0).equals("  ")) {
                            model.setValueAt("X", index, 0);
                        } else if(!databaseLocations.contains(model.getValueAt(index, 1)) && model.getValueAt(index, 0).equals("X")) {
                            model.setValueAt("  ", index, 0);
                        }
                    }
                }
                tick = false;
                if(model.getValueAt(index, 0).equals("  ")) { //Checks if button has already been pressed
                    if(!databaseLocations.contains(model.getValueAt(index, 1))) {
                        databaseLocations.add((String) model.getValueAt(index, 1));
                        System.out.println("Added " + model.getValueAt(index, 1).toString() + " to array");
                        System.out.println("databaseLocations size = " + databaseLocations.size());
                        model.setValueAt("X", index, 0);
                    }

                } else if(model.getValueAt(index, 0).equals("X")) {
                    if(databaseLocations.contains(model.getValueAt(index, 1))) {
                        databaseLocations.remove(model.getValueAt(index, 1));
                        System.out.println("Removed " + model.getValueAt(index, 1).toString() + " from array");
                        System.out.println("databaseLocations size = " + databaseLocations.size());
                        model.setValueAt("  ", index, 0);
                    }
                }
            }
        };

        ButtonColumn buttonColumn = new ButtonColumn(table3, test, 0);
    }

    int displayXML(ArrayList<String> ids) throws Exception {
        displayDatabaseWindow();
        int ret = -1;
        tempLoc = new ArrayList<>();
        fTemp = createInnerPanel();
        loadPanel = createInnerPanel();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        setGBC(0, 0, 4);
        currentTrip = new JLabel("Untitled trip", SwingConstants.CENTER);
        currentTrip.setPreferredSize(new Dimension(30, 30));
        fTemp.add(currentTrip, gbc);

        JButton loadFromXML = createXMLBrowser();
        loadPanel.add(loadFromXML, gbc);

        setGBC(0, 1, 2);

        JButton q = new JButton("  Display  ");
        q.addActionListener((ActionEvent e) -> {
            userAddLocList(tempLoc);
            lastTrip = new ArrayList<>(tempLoc);
        });
        fTemp.add(q, gbc);

        setGBC(2, 1, 1);
        fTemp.add(addSaveButton(" Save Trip "), gbc);

        setGBC(3, 1, 1);
        fTemp.add(addSaveButton(" Save As "), gbc);

        updateAddButtonsAddRemove(ids);

        /*
        for(int i = 0; i < dm.getRowCount();i++){
            JButton temp = (JButton) buttonColumn.getTableCellEditorComponent(table2,"Add",true,i, 0);
            buttons.add(temp);
        }
        System.out.println(buttonColumn.getTableCellEditorComponent(table2,"Add",true,0,0).getClass());
        JButton temp = (JButton) buttonColumn.getTableCellEditorComponent(table2,"Add",true,0,0);
        System.out.println(temp.getText());
        */
        JScrollPane scroll = new JScrollPane(table2);
        setGBC(0, 2, 4);
        fTemp.add(scroll, gbc);
        ImageIcon icon = new ImageIcon(workingDirectoryFilePath + "/" + "favicon.ico", "HELP2");
        options.addTab("Locations", icon, fTemp, "Locations");
        options.addTab("Load Trips", icon, loadPanel, "Load saved trips");
        options.addTab("Map Options", icon, generateMapDisplayOptions(), "Pane for map options");
        uOp.pack();
        table.getTableHeader().setBackground(Color.BLACK);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Impact", Font.BOLD, 12));
        JScrollPane scrollPane = new JScrollPane(table);
        itinerary.getContentPane().add(scrollPane);
        itinerary.pack();
        ret = 1;
        return ret;
    }

    private void updateAddButtonsAddRemove(ArrayList<String> ids) {
        dm.setRowCount(0);
        Vector<String> columnNames = new Vector<>();
        Vector<Vector<String>> addButtons = new Vector<>();
        columnNames.addElement("Click to add Destination");
        columnNames.addElement("Location");

        for(String id : ids) {
            Vector<String> temp = new Vector<>();
            temp.addElement("Add");
            temp.addElement(id);
            addButtons.add(temp);
        }
        dm.setDataVector(addButtons, columnNames);
        Action test = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable temp = (JTable) e.getSource();
                DefaultTableModel model = (DefaultTableModel) temp.getModel();
                int index = Integer.parseInt(e.getActionCommand());
                if(tick) {
                    for(int i = 0; i < tempLoc.size(); i++) {
                        if(tempLoc.contains(ids.get(index)) && model.getValueAt(index, 0).equals("Add")) {
                            model.setValueAt("Remove", index, 0);
                        } else if(!tempLoc.contains(ids.get(index)) && model.getValueAt(index, 0).equals("Remove")) {
                            model.setValueAt("Add", index, 0);
                        }
                    }
                }
                tick = false;
                if(model.getValueAt(index, 0).equals("Add")) { //Checks if button has already been pressed
                    if(!tempLoc.contains(ids.get(index))) {
                        tempLoc.add(ids.get(index));
                        System.out.println("Added " + ids.get(index) + " to array");
                        model.setValueAt("Remove", index, 0);
                    }

                } else if(model.getValueAt(index, 0).equals("Remove")) {
                    if(tempLoc.contains(ids.get(index))) {
                        tempLoc.remove(ids.get(index));
                        System.out.println("Removed " + ids.get(index) + " from array");
                        model.setValueAt("Add", index, 0);
                    }
                }
            }
        };
        ButtonColumn buttonColumn = new ButtonColumn(table2, test, 0);
    }

    public class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener {
        private JTable table;
        private Action action;
        private int mnemonic;
        private Border originalBorder;
        private Border focusBorder;

        private JButton renderButton;
        private JButton editButton;
        private Object editorValue;
        private boolean isButtonColumnEditor;

        /**
         * Create the ButtonColumn to be used as a renderer and editor. The
         * renderer and editor will automatically be installed on the TableColumn
         * of the specified column.
         *
         * @param table  the table containing the button renderer/editor
         * @param action the Action to be invoked when the button is invoked
         * @param column the column to which the button renderer/editor is added
         */
        public ButtonColumn(JTable table, Action action, int column) {
            this.table = table;
            this.action = action;

            renderButton = new JButton();
            editButton = new JButton();
            editButton.setFocusPainted(false);
            editButton.addActionListener(this);
            originalBorder = editButton.getBorder();
            setFocusBorder(new LineBorder(Color.BLUE));

            TableColumnModel columnModel = table.getColumnModel();
            columnModel.getColumn(column).setCellRenderer(this);
            columnModel.getColumn(column).setCellEditor(this);
            table.addMouseListener(this);
        }


        /**
         * Get foreground color of the button when the cell has focus
         *
         * @return the foreground color
         */
        public Border getFocusBorder() {
            return focusBorder;
        }

        /**
         * The foreground color of the button when the cell has focus
         *
         * @param focusBorder the foreground color
         */
        public void setFocusBorder(Border focusBorder) {
            this.focusBorder = focusBorder;
            editButton.setBorder(focusBorder);
        }

        public int getMnemonic() {
            return mnemonic;
        }

        /**
         * The mnemonic to activate the button when the cell has focus
         *
         * @param mnemonic the mnemonic
         */
        public void setMnemonic(int mnemonic) {
            this.mnemonic = mnemonic;
            renderButton.setMnemonic(mnemonic);
            editButton.setMnemonic(mnemonic);
        }

        @Override
        public Component getTableCellEditorComponent(
                JTable table, Object value, boolean isSelected, int row, int column) {
            if(value == null) {
                editButton.setText("");
                editButton.setIcon(null);
            } else if(value instanceof Icon) {
                editButton.setText("");
                editButton.setIcon((Icon) value);
            } else {
                editButton.setText(value.toString());
                editButton.setIcon(null);
            }

            this.editorValue = value;
            return editButton;
        }

        @Override
        public Object getCellEditorValue() {
            return editorValue;
        }

        //
//  Implement TableCellRenderer interface
//
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if(isSelected) {
                renderButton.setForeground(table.getSelectionForeground());
                renderButton.setBackground(table.getSelectionBackground());
            } else {
                renderButton.setForeground(table.getForeground());
                renderButton.setBackground(UIManager.getColor("Button.background"));
            }

            if(hasFocus) {
                renderButton.setBorder(focusBorder);
            } else {
                renderButton.setBorder(originalBorder);
            }

//		renderButton.setText( (value == null) ? "" : value.toString() );
            if(value == null) {
                renderButton.setText("");
                renderButton.setIcon(null);
            } else if(value instanceof Icon) {
                renderButton.setText("");
                renderButton.setIcon((Icon) value);
            } else {
                renderButton.setText(value.toString());
                renderButton.setIcon(null);
            }

            return renderButton;
        }

        //
//  Implement ActionListener interface
//
    /*
     *	The button has been pressed. Stop editing and invoke the custom Action
	 */
        public void actionPerformed(ActionEvent e) {
            int row = table.convertRowIndexToModel(table.getEditingRow());
            fireEditingStopped();

            //  Invoke the Action

            ActionEvent event = new ActionEvent(
                    table,
                    ActionEvent.ACTION_PERFORMED,
                    "" + row);
            action.actionPerformed(event);
        }

        //
//  Implement MouseListener interface
//
    /*
     *  When the mouse is pressed the editor is invoked. If you then then drag
	 *  the mouse to another cell before releasing it, the editor is still
	 *  active. Make sure editing is stopped when the mouse is released.
	 */
        public void mousePressed(MouseEvent e) {
            if(table.isEditing()
                    && table.getCellEditor() == this)
                isButtonColumnEditor = true;
        }

        public void mouseReleased(MouseEvent e) {
            if(isButtonColumnEditor
                    && table.isEditing())
                table.getCellEditor().stopCellEditing();

            isButtonColumnEditor = false;
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    }

    void resizeTable(JTable table) {
        for(int column = 0; column < table.getColumnCount(); column++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = 0;
            TableCellRenderer rend = table.getTableHeader().getDefaultRenderer();
            TableCellRenderer rendCol = tableColumn.getHeaderRenderer();
            if(rendCol == null) rendCol = rend;
            Component header = rendCol.getTableCellRendererComponent(table, tableColumn.getHeaderValue(), false, false, 0, column);
            maxWidth = header.getPreferredSize().width;

            for(int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component c = table.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);

                if(preferredWidth <= maxWidth) {
                    preferredWidth = maxWidth;
                    break;
                }
            }
            tableColumn.setPreferredWidth(preferredWidth);
        }
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double screenHeight = screenSize.getHeight();
        table.setPreferredScrollableViewportSize(new Dimension(width - 19, ((int) screenHeight - height) - 42));
    }

    int addLegToItinerary(String seqId, String name1, String name2, int mileage) {
        int ret = -1;
        if(fTemp2 == null) {
            fTemp2 = createInnerPanel();
            ret = 1;
        }

        if(model == null) {
            model = new DefaultTableModel();
            table = new JTable(model);
            /*
                private String id;
                private String name;
                private double lat;
                private double lon;
                private String municipality;
                private String region;
                private String country;
                private String continent;
                private String airportUrl;
                private String regionUrl;
                private String countryUrl;
            */
            model.addColumn("ID");
            model.addColumn("From");
            model.addColumn("To");
            model.addColumn("Distance");
            table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            ret = 1;
        }

        if(seqId.equals("0")) {
            fTemp2.removeAll();
            fTemp2.repaint();
            model.setRowCount(0);
        }

        setGBC(0, Integer.parseInt(seqId), 4);
        JLabel lab = new JLabel("ID: " + seqId + "   " + name1 + " to " + name2 + "   " + mileage + " miles");
        lab.setHorizontalAlignment(2);
        fTemp2.add(lab, gbc);
        GUILocation temp = searchGuiLocationsWithName(name1);
        GUILocation temp2 = searchGuiLocationsWithName(name2);
        if(lab.getText() != null) {
            model.addRow(new Object[]{seqId, temp.getName(), temp2.getName(), mileage});
            resizeTable(table);
        }
        Action testColumn1 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable temp7 = (JTable) e.getSource();
                DefaultTableModel model = (DefaultTableModel) temp7.getModel();
                int index = Integer.parseInt(e.getActionCommand());
                System.out.println("value at this cell is = " + model.getValueAt(index,1));
                GUILocation temp3 = searchGuiLocationsWithName((String) model.getValueAt(index,1));
                JPopupMenu popupMenu = new JPopupMenu();
                popupMenu.add(new JMenuItem(new AbstractAction("Click here to learn more about " + temp3.getName()) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (Desktop.isDesktopSupported()) {
                            Desktop desktop = Desktop.getDesktop();
                            try {
                                if(temp3.getAirportUrl().equals("")){
                                    JOptionPane.showMessageDialog(popupMenu.getComponent(),"Information not Available");
                                }
                                else{
                                    URI uri = new URI(temp3.getAirportUrl());
                                    desktop.browse(uri);
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            } catch (URISyntaxException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }));
                popupMenu.add(new JMenuItem("Continent: " + temp3.getContinent()));
                popupMenu.add(new JMenuItem("Country: " + temp3.getCountry()));
                popupMenu.add(new JMenuItem("Municipality: " + temp3.getMunicipality()));
               System.out.println(temp3.getContinent());
               System.out.println(temp3.getCountry());
               System.out.println(temp3.getMunicipality());
               temp7.addMouseListener(new MouseAdapter() {
                   public void mouseClicked(MouseEvent evt) {
                        popupMenu.show(evt.getComponent(),evt.getX(),evt.getY());
                   }
                });
            }
        };
        Action testColumn2 = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable temp7 = (JTable) e.getSource();
                DefaultTableModel model = (DefaultTableModel) temp7.getModel();
                int index = Integer.parseInt(e.getActionCommand());
                System.out.println("value at this cell is = " + model.getValueAt(index,2));
                GUILocation temp3 = searchGuiLocationsWithName((String) model.getValueAt(index,2));
                JPopupMenu popupMenu = new JPopupMenu();

                popupMenu.add(new JMenuItem(new AbstractAction("Click here to learn more about " + temp3.getName()) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (Desktop.isDesktopSupported()) {
                            Desktop desktop = Desktop.getDesktop();
                            try {
                                if(temp3.getAirportUrl().equals("")){
                                    JOptionPane.showMessageDialog(popupMenu.getComponent(),"Information not Available");
                                }
                                else{
                                    URI uri = new URI(temp3.getAirportUrl());
                                    desktop.browse(uri);
                                }
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            } catch (URISyntaxException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }));
                popupMenu.add(new JMenuItem("Continent: " + temp3.getContinent()));
                popupMenu.add(new JMenuItem("Country: " + temp3.getCountry()));
                popupMenu.add(new JMenuItem("Municipality: " + temp3.getMunicipality()));
                System.out.println(temp3.getContinent());
                System.out.println(temp3.getCountry());
                System.out.println(temp3.getMunicipality());
                temp7.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent evt) {
                        popupMenu.show(evt.getComponent(),evt.getX(),evt.getY());
                    }
                });
            }
        };

        ButtonColumn buttonColumn = new ButtonColumn(table, testColumn1, 1);
        ButtonColumn buttonColumn2 = new ButtonColumn(table, testColumn2, 2);
        return ret;
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

    void makeGUILocations(ArrayList<Object> locs) {
        //System.out.println("[MapGUI] Making GUILocations");
        guiLocations.add(new GUILocation(locs));
    }

    ArrayList<String> searchDBLocationNames() {
        ArrayList<String> ret = new ArrayList<>();
        for(int i = 0; i < guiLocations.size(); i++) {
            for(int j = 0; j < databaseLocations.size(); j++) {
                if(guiLocations.get(i).getName().equals(databaseLocations.get(j))) {
                    ret.add(guiLocations.get(i).getName());
                }
            }
        }
        return ret;
    }

    boolean cleanup() {
        boolean ret;
        File t = new File(workingDirectoryFilePath + "output/" + filename + (filenameIncrementer - 1) + "_User.png");
        ret = t.delete();
        File temp = new File(workingDirectoryFilePath + "output/" + filename + ".png");
        Boolean ret2 = temp.delete();
        filenameIncrementer = 0;
        return ret & ret2;
    }

    GUILocation searchGuiLocationsWithName(String name) {
        for(GUILocation loc : guiLocations) {
            if(loc.getName().equals(name)) {
                return loc;
            }
        }
        return null;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
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
        MapGUI g = new MapGUI();
        g.readXML("src/test/resources/Testing/selectionXml.xml");
        g.readXML("testing.xml");
        g.readXML("testing3.xml");
    }
}
