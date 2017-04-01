package com.csu2017sp314.dtr07.View;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.swing.border.Border;
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
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Vector;
import java.util.function.Consumer;
import java.io.File;

import java.nio.file.CopyOption;
import java.nio.file.StandardCopyOption;


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
    private boolean rightTick = false;
    private int savedTrip = -1;
    private int filenameIncrementer = 1;
    private int z = -1; //Number of saved trips
    private int z2 = 1; //You'll figure it out
    private ArrayList<JButton> buttons = new ArrayList<>();
    private String tripName = "ERROR";
    private JPanel loadPanel;
    private Group root;
    private JPanel fTemp;
    private JPanel fTemp2;
    private JLabel currentTrip;
    private ArrayList<String> lastTrip = new ArrayList<>();
    private int width;
    private int height;
    private ArrayList<String> tempArray = new ArrayList<>();
    private ArrayList<String> tempIdArray = new ArrayList<>();
    private ArrayList<String> tempDistanceArray = new ArrayList<>();


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

    /*public void userAddLoc(String id) { //Used if other callback method is used
        callback.accept(id);
    }*/

    private void userAddLocList(ArrayList<String> ids) {
        callback2.accept(ids);
    }

    private void mapOptions(String option) {
        callback3.accept(option);
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
        uOp = createJFrame("User Options", width+1, 0, options);
        //createItineraryWindow();
        itinerary = createScrollingJFrame("Itinerary", 0, 0);

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
        map.setSize(width-1, height-1); //Refreshes window, needed or image doesn't appear
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
        setGBC(0, 1, 2);
        panel.add(mapDisplayButtons("Mileage"), gbc);
        setGBC(0, 2, 1);
        panel.add(mapDisplayButtons("2-opt"), gbc);
        setGBC(1, 2, 1);
        panel.add(mapDisplayButtons("3-opt"), gbc);
        return panel;
    }

    int displayXML(ArrayList<String> ids) throws ParserConfigurationException, TransformerException {
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

        int numButtons = 0;
        for(String id : ids) {
            ret = 1;
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
            setGBC(0, numButtons + 1, 1);
            fTemp.add(b, gbc);
            setGBC(1, numButtons + 1, 3);
            fTemp.add(t2, gbc);
        }

        ImageIcon icon = new ImageIcon(workingDirectoryFilePath + "/" + "favicon.ico", "HELP2");
        options.addTab("Locations", icon, fTemp, "Locations");
        options.addTab("Load Trips", icon, loadPanel, "Load saved trips");

        options.addTab("Map Options", icon, generateMapDisplayOptions(), "Pane for map options");
        //options.addTab("Four", face.getContentPane());
        //uOp.setMinimumSize(new Dimension(500, 500));
        uOp.pack();

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        model.addColumn("ID");
        model.addColumn("Trip");
        model.addColumn("Distance");
        for(int i = 0; i < fTemp2.getComponentCount();i++){
            Vector row = new Vector();
            row.add(tempIdArray.get(i));
            row.add(tempArray.get(i));
            row.add(tempDistanceArray.get(i));
            model.addRow(row);
        }
        table.getTableHeader().setBackground(Color.BLACK);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        /*
        for (int column = 0; column < table.getColumnCount(); column++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component c = table.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);
                if (preferredWidth >= maxWidth) {
                    preferredWidth = maxWidth;
                    break;
                }
            }
            System.out.println(tableColumn.getHeaderValue() + " " + preferredWidth);
            tableColumn.setPreferredWidth( preferredWidth );
        }
        */
        for (int column = 0; column < table.getColumnCount(); column++){
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = 0;
            TableCellRenderer rend = table.getTableHeader().getDefaultRenderer();
            TableCellRenderer rendCol = tableColumn.getHeaderRenderer();
            if (rendCol == null) rendCol = rend;
            Component header = rendCol.getTableCellRendererComponent(table, tableColumn.getHeaderValue(), false, false, 0, column);
            maxWidth = header.getPreferredSize().width;
            //System.out.println("maxWidth :"+maxWidth);

            for (int row = 0; row < table.getRowCount(); row++){
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component c = table.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);
                //System.out.println("preferredWidth :"+preferredWidth);
                //System.out.println("Width :"+width);

                //  We've exceeded the maximum width, no need to check other rows

                if (preferredWidth <= maxWidth){
                    preferredWidth = maxWidth;
                    break;
                }
            }
            tableColumn.setPreferredWidth(preferredWidth);
        }
        //double[] percents = {10,70,20};
        //setJTableColumnsWidth(table,600,percents);
        Dimension d = table.getPreferredSize();
        System.out.println(d.getWidth() + " " + d.getHeight());
        table.setPreferredScrollableViewportSize(new Dimension((int)d.getWidth(), 500));
        JScrollPane scrollPane = new JScrollPane(table);
        //scrollPane.setPreferredSize(new Dimension(600,500));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        itinerary.add(scrollPane, BorderLayout.CENTER);
        itinerary.pack();

        ret = 1;
        return ret;
    }
    public  void setJTableColumnsWidth(JTable table, int tablePreferredWidth, double[] percentages) {
        double total = 0;
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            total += percentages[i];
        }

        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth((int)
                    (tablePreferredWidth * (percentages[i] / total)));
        }
    }

    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 15; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            if(width > 300)
                width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }

    int addLegToItinerary(String seqId, String name1, String name2, int mileage) {
        int ret = -1;
        if(fTemp2 == null) {
            fTemp2 = createInnerPanel();
            ret = 1;
        }
        if(seqId.equals("0")) {
            fTemp2.removeAll();
            fTemp2.repaint();
            //fTemp2.requestFocus(true);
        }
        setGBC(0, Integer.parseInt(seqId), 4);
        JLabel lab = new JLabel("ID: " + seqId + "\t" + name1 + " to " + name2 + "\t" + mileage + " miles");
        String temp = (name1 + " to " + name2);
        tempDistanceArray.add(Integer.toString(mileage));
        tempIdArray.add(seqId);
        tempArray.add(temp);
        lab.setHorizontalAlignment(2);
        fTemp2.add(lab, gbc);

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

    boolean cleanup() {
        boolean ret;
        File t = new File(workingDirectoryFilePath + "output/" + filename + (filenameIncrementer - 1) + "_User.png");
        ret = t.delete();
        File temp = new File(workingDirectoryFilePath + "output/" + filename + ".png");
        Boolean ret2 = temp.delete();
        filenameIncrementer = 0;
        return ret & ret2;
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
        /*
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
        */
        MapGUI g = new MapGUI();
        g.readXML("src/test/resources/Testing/selectionXml.xml");
        g.readXML("testing.xml");
        g.readXML("testing3.xml");
    }
}
