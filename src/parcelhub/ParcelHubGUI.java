/*
 * Copyright (C) 2016 Thomas Kercheval
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package parcelhub;

import parcelhub.gui_dialogs.ParcelEditor;
import parcelhub.gui_dialogs.DatabaseSelector;
import parcelhub.gui_dialogs.ParcelWindow;
import parcelhub.gui_dialogs.ParcelScanner;
import parcelhub.objects.Parcel;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import parcelhub.gui_dialogs.AboutForm;
import parcelhub.utilities.ParcelCSVFileReader;
import parcelhub.utilities.ParcelCSVFileWriter;
import parcelhub.utilities.PrintUtilities;
import static parcelhub.utilities.SearchingAlgorithms.binarySearch;
import static parcelhub.utilities.SearchingAlgorithms.linearNameSearch;
import static parcelhub.utilities.SearchingAlgorithms.linearSearch;
import static parcelhub.utilities.SearchingAlgorithms.linearZipSearch;
import static parcelhub.utilities.SortingAlgorithms.insertionSort;
import parcelhub.utilities.Validation;

/**
 * This GUI runs our database for the Shipping Hub. It facilitates the
 * reading/writing to external files, display of Parcels received, and sorting
 * of Parcels by state, ID, zipcode, and search capacity by many different
 * means.
 *
 * Project: Parcel Hub Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
 * Course: CS 143 
 * Created on May 2, 2016, 5:06:21 PM 
 * Revised on May 9, 2016, 4:59:24 PM
 *
 * @author thomas.kercheval
 */
public class ParcelHubGUI extends javax.swing.JFrame {
    /** 
     * A HashMap which has keys of each state abbreviation listed in 
     * STATE_ABBV and values which are ArrayLists containing the Parcel
     * objects which are being shipped to the corresponding state.
     */
    HashMap stateMap;
    /** Contains our Parcel objects. */
    ArrayList<Parcel> parcels;
    /** The name of our current database. */
    String fileName;

    /** An array of all relevant state abbreviations. */
    public static final String[] STATE_ABBV = new String[]{"AK", "AL", "AR", 
        "AS", "AZ", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "GU", "HI", "IA", 
        "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MH", "MI", "MN", 
        "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", 
        "OK", "OR", "PA", "PR", "PW", "RI", "SC", "SD", "TN", "TX", "UT", "VA", 
        "VI", "VT", "WA", "WI", "WV", "WY"};
    /** The relative path of our Parcel database. */
    public static final String FILE_NAME = "src/parcelhub/data/Parcels.txt";
    /** The index of the Parcel ID while reading from a CSV file. */
    public static final int ID_INDEX = 0;
    /** The index of the Customer Name while reading from a CSV file. */
    public static final int NAME_INDEX = 1;
    /** The index of the Customer's Address while reading from a CSV file. */
    public static final int ADDRESS_INDEX = 2;
    /** The index of the Customer's City while reading from a CSV file. */
    public static final int CITY_INDEX = 3;
    /** The index of the Customer's State while reading from a CSV file. */
    public static final int STATE_INDEX = 4;
    /** The index of the Customer's ZipCode while reading from a CSV file. */
    public static final int ZIP_INDEX = 5;
    /** The index of the Parcel scanning Date while reading from a CSV file. */
    public static final int DATE_INDEX = 6;

    /**
     * Creates new form ParcelHubGUI. Selects the Database to read from,
     * initializes the ArrayLists, one to hold all of the Parcels, and one
     * for every state to hold the Parcels that are being sent there. It finds
     * all states where there are Parcels headed and sets the stateComboBox 
     * model to list those states. If there are no Parcels in the database,
     * we alert the user and display an empty Washington State.
     */
    public ParcelHubGUI() {
        DatabaseSelector selector = new DatabaseSelector();
        selector.setVisible(true);
        fileName = selector.getFileName();
        if (fileName == null) {
            JOptionPane.showMessageDialog(null, "No database was selected...",
                    "Database Not Selected",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        initComponents();
        setLocationRelativeTo(null);
        this.getRootPane().setDefaultButton(this.scanNewButton);
        this.setIconImage(Toolkit.getDefaultToolkit().
                getImage("src/parcelhub/images/238be5e.png"));
        parcels = new ArrayList<>();
        stateMap = createStateMap();
        readFromFile(fileName);
        pushToStateMap();
        
        setModelForStateCombo();
        displayFirstNonEmptyState();
        displayParcels();
        showParcelInformation(parcelList.getSelectedIndex());
    }
    
    /**
     * Cycles through every possible state and adds the ones which have Parcels
     * affiliated with them to the stateComboBox to be displayed. If there are
     * no Parcels we display Washington State and alert the User.
     */
    private void setModelForStateCombo() {
        ArrayList<String> filledStates = new ArrayList<>();
        for (String state : STATE_ABBV) {
            ArrayList<Parcel> parcelArrayList = (ArrayList)stateMap.get(state);
            if (!parcelArrayList.isEmpty()) {
                filledStates.add(state);
            }
        }
        if (filledStates.isEmpty()) {
            filledStates.add("WA");
            JOptionPane.showMessageDialog(null, "No Parcels Exist yet...\n"
                    + "States will appear in the ComboBox as you add Parcels."
                    + "\n\nSo have Washington State appear as a default!",
                    "No Extant Parcels",
                    JOptionPane.ERROR_MESSAGE);
        }
        String[] filledStatez = filledStates.toArray(new String[filledStates.size()]);
        this.stateComboBox.setModel(new DefaultComboBoxModel(filledStatez));
    }
    
    /**
     * This method finds the first state, alphabetically, that has Parcels
     * being delivered to it. 
     * 
     * # NOTE: This method may not be necessary due to the after added 
     * setModelForStateCombo() method. This is so because to non-empty states
     * are displayed to begin with.
     */
    private void displayFirstNonEmptyState() {
        String state = (String) stateComboBox.getSelectedItem();
        ArrayList<Parcel> parcelArrayList = (ArrayList)stateMap.get(state);
        if (stateComboBox.getSelectedIndex() == stateComboBox.getItemCount() - 1) {
            stateComboBox.setSelectedIndex(0);
        } else if (parcelArrayList.isEmpty()) {
            int index = stateComboBox.getSelectedIndex() + 1;
            stateComboBox.setSelectedIndex(index);
            displayFirstNonEmptyState();
        }
    }

    /**
     * Reads Parcel objects from a file and populates ArrayList parcels.
     * @param fileName The fileName of our database.
     */
    private void readFromFile(String fileName) {
        parcels.clear();
        ParcelCSVFileReader reader = new ParcelCSVFileReader(fileName);
        String line;
        while ((line = reader.readRecord()) != null) {
            String[] parcelInfo = line.split(","); // Create Array of info
            Parcel parcel = new Parcel(parcelInfo); // Use Array Constructor
            parcels.add(parcel);
        }
        reader.close();
    }
    
    /**
     * This method distributes parcels from the ArrayList parcels
     * to the ArrayLists attached to States in the HashMap.
     */
    private void pushToStateMap() {
        for (String state: ParcelHubGUI.STATE_ABBV) {
            ArrayList<Parcel> list = (ArrayList<Parcel>) stateMap.get(state);
            list.clear();
        }
        for (Parcel parcel: parcels) {
            String state = parcel.getState();
            ArrayList<Parcel> list = (ArrayList<Parcel>) stateMap.get(state);
            list.add(parcel);
        }
        sortAllArrayLists();
    }
    
    /** 
     * Displays Parcels in the parcelList display component depending upon 
     * which state is currently selected. This is also where buttons are
     * enabled/disabled depending on how many Parcels are currently being
     * Displayed.
     */
    private void displayParcels() {
        String state = (String) stateComboBox.getSelectedItem();
        ArrayList<Parcel> parcelArrayList = (ArrayList)stateMap.get(state);
        DefaultListModel model = new DefaultListModel();
        insertionSort(parcelArrayList);
        for (int i = 0; i < parcelArrayList.size(); i++) {
            model.addElement((i + 1) + ": " 
                    + parcelArrayList.get(i).getParcelID());
        }
        this.parcelList.setModel(model);
        if (parcelArrayList.size() <= 1) {
            this.backButton.setEnabled(false);
            this.nextButton.setEnabled(false);
            this.backMenuItem.setEnabled(false);
            this.nextMenuItem.setEnabled(false);
        } else {
            this.backButton.setEnabled(true);
            this.nextButton.setEnabled(true);
            this.backMenuItem.setEnabled(true);
            this.nextMenuItem.setEnabled(true);
        }
        if (parcelArrayList.isEmpty()) {
            clearParcelDisplay();
            this.removeButton.setEnabled(false);
            this.editButton.setEnabled(false);
            this.removeMenuItem.setEnabled(false);
            this.editMenuItem.setEnabled(false);
        } else {
            this.removeButton.setEnabled(true);
            this.editButton.setEnabled(true);
            this.removeMenuItem.setEnabled(true);
            this.editMenuItem.setEnabled(true);
            parcelList.setSelectedIndex(0);
        }
        if (parcels.isEmpty()) {
            this.searchButton.setEnabled(false);
            this.searchMenuItem.setEnabled(false);
            this.searchZipMenuItem.setEnabled(false);
        } else {
            this.searchButton.setEnabled(true);
            this.searchMenuItem.setEnabled(true);
            this.searchZipMenuItem.setEnabled(true);
        }
    }
    
    /** This method clears the displayed Parcel information TextFields. */
    private void clearParcelDisplay() {
        nameTextField.setText("");
        addressTextField.setText("");
        parcelIDTextField.setText("");
        arrivalTextField.setText("");
        stateTextField.setText("");
        zipTextField.setText("");
        cityTextField.setText("");
    }
    
    /**
     * Displays all Parcel Information for the selected Parcel.
     * @param index The index of the Parcel in the parcelList display.
     */
    private void showParcelInformation(int index) {
        String state = (String) stateComboBox.getSelectedItem();
        if (index != -1) { // Double cast is necessary because the ArrayList
                           // is stored as an Object in the HashMap stateMap
                           // and then the Casted ArrayList can't infer the
                           // type of its contents.
            Parcel parcel = (Parcel)((ArrayList)stateMap.get(state)).get(index);
            nameTextField.setText(parcel.getNameReciever());
            addressTextField.setText(parcel.getAddress());
            parcelIDTextField.setText(parcel.getParcelID());
            arrivalTextField.setText(parcel.getDate());
            stateTextField.setText(parcel.getState());
            zipTextField.setText(parcel.getZip());
            cityTextField.setText(parcel.getCity());
        }
        
    }
    
    /**
     * This method returns a HashMap whose keys are State Abbreviations and
     * whose values are HashMaps.
     * @return A HashMap with the State Abbreviations as keys and ArrayLists
     * of Parcels as values.
     */
    private HashMap createStateMap() {
        HashMap stateMapper = new HashMap();
        for (String state : ParcelHubGUI.STATE_ABBV) {
            stateMapper.put(state, new ArrayList<Parcel>());
        }
        return stateMapper;
    }

    /**
     * This method sorts every ArrayList in this application by ID.
     * @see parcelhub.utilities.SortingAlgorithms.insertionSort
     */
    private void sortAllArrayLists() {
        insertionSort(parcels);
        for (String state : ParcelHubGUI.STATE_ABBV) {
            insertionSort((ArrayList)stateMap.get(state));
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        titlePanel = new javax.swing.JPanel();
        arrivalLabel = new javax.swing.JLabel();
        arrivalTextField = new javax.swing.JTextField();
        logoLabel = new javax.swing.JLabel();
        titleLabel = new javax.swing.JLabel();
        controlPanel = new javax.swing.JPanel();
        scanNewButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        searchButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        displayPanel = new javax.swing.JPanel();
        informationPanel = new javax.swing.JPanel();
        parcelIDLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
        cityLabel = new javax.swing.JLabel();
        parcelIDTextField = new javax.swing.JTextField();
        nameTextField = new javax.swing.JTextField();
        addressTextField = new javax.swing.JTextField();
        stateTextField = new javax.swing.JTextField();
        stateLabel = new javax.swing.JLabel();
        cityTextField = new javax.swing.JTextField();
        zipLabel = new javax.swing.JLabel();
        zipTextField = new javax.swing.JTextField();
        byStatePanel = new javax.swing.JPanel();
        stateComboBox = new javax.swing.JComboBox<>();
        parcelScrollPanel = new javax.swing.JScrollPane();
        parcelList = new javax.swing.JList<>();
        parcelMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        clearMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        printMenuItem = new javax.swing.JMenuItem();
        printParcelMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        exitMenuItem = new javax.swing.JMenuItem();
        actionMenu = new javax.swing.JMenu();
        viewAllParcelsMenuItem = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JPopupMenu.Separator();
        scanMenuItem = new javax.swing.JMenuItem();
        removeMenuItem = new javax.swing.JMenuItem();
        editMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        searchMenuItem = new javax.swing.JMenuItem();
        searchNameMenuItem = new javax.swing.JMenuItem();
        searchZipMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        backMenuItem = new javax.swing.JMenuItem();
        nextMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Parcel Hub");
        setResizable(false);

        arrivalLabel.setText("Arrived at:");

        arrivalTextField.setEditable(false);
        arrivalTextField.setToolTipText("The date at which this Parcel was Scanned");

        logoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parcelhub/images/238be5e.png"))); // NOI18N

        titleLabel.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        titleLabel.setText("Parcel Hub");

        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(arrivalLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(arrivalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addGroup(titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(titlePanelLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(titlePanelLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(arrivalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(arrivalLabel))
                            .addComponent(titleLabel))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(titlePanel, java.awt.BorderLayout.NORTH);

        controlPanel.setLayout(new java.awt.GridLayout(1, 6));

        scanNewButton.setMnemonic('s');
        scanNewButton.setText("Scan New");
        scanNewButton.setToolTipText("Scan New Parcel into System");
        scanNewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scanNewButtonActionPerformed(evt);
            }
        });
        controlPanel.add(scanNewButton);

        removeButton.setMnemonic('R');
        removeButton.setText("Remove");
        removeButton.setToolTipText("Remove Parcel from System");
        removeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtonActionPerformed(evt);
            }
        });
        controlPanel.add(removeButton);

        editButton.setMnemonic('e');
        editButton.setText("Edit");
        editButton.setToolTipText("Edit existing Parcel in System");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });
        controlPanel.add(editButton);

        searchButton.setMnemonic('h');
        searchButton.setText("Search");
        searchButton.setToolTipText("Search for Parcel in System");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        controlPanel.add(searchButton);

        backButton.setMnemonic('B');
        backButton.setText("< Back");
        backButton.setToolTipText("View previous Parcel");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
        controlPanel.add(backButton);

        nextButton.setMnemonic('N');
        nextButton.setText("Next >");
        nextButton.setToolTipText("View Next Parcel");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });
        controlPanel.add(nextButton);

        getContentPane().add(controlPanel, java.awt.BorderLayout.SOUTH);

        informationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Parcel Information"));

        parcelIDLabel.setText("Parcel ID:");

        nameLabel.setText("Name: ");

        addressLabel.setText("Address:");

        cityLabel.setText("City:");

        parcelIDTextField.setEditable(false);
        parcelIDTextField.setText("\n");
        parcelIDTextField.setToolTipText("The generated ID of the Parcel being displayed");
        parcelIDTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parcelIDTextFieldActionPerformed(evt);
            }
        });

        nameTextField.setEditable(false);
        nameTextField.setText("\n");
        nameTextField.setToolTipText("The name of the recipient of the Parcel being displayed");
        nameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTextFieldActionPerformed(evt);
            }
        });

        addressTextField.setEditable(false);
        addressTextField.setText("\n");
        addressTextField.setToolTipText("The address of the recipient of the Parcel being displayed");
        addressTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressTextFieldActionPerformed(evt);
            }
        });

        stateTextField.setEditable(false);
        stateTextField.setText("\n");
        stateTextField.setToolTipText("The state of the recipient of the Parcel being displayed");
        stateTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateTextFieldActionPerformed(evt);
            }
        });

        stateLabel.setText("State:");

        cityTextField.setEditable(false);
        cityTextField.setText("\n");
        cityTextField.setToolTipText("The City of the recipient of the Parcel being displayed");
        cityTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cityTextFieldActionPerformed(evt);
            }
        });

        zipLabel.setText("Zip:");

        zipTextField.setEditable(false);
        zipTextField.setText("\n");
        zipTextField.setToolTipText("The ZipCode of the recipient of the Parcel being displayed");
        zipTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zipTextFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout informationPanelLayout = new javax.swing.GroupLayout(informationPanel);
        informationPanel.setLayout(informationPanelLayout);
        informationPanelLayout.setHorizontalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addComponent(parcelIDLabel)
                        .addGap(18, 18, 18)
                        .addComponent(parcelIDTextField))
                    .addGroup(informationPanelLayout.createSequentialGroup()
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameLabel)
                            .addComponent(cityLabel)
                            .addComponent(addressLabel))
                        .addGap(22, 22, 22)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameTextField)
                            .addComponent(addressTextField)
                            .addGroup(informationPanelLayout.createSequentialGroup()
                                .addComponent(cityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(stateLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(zipLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(zipTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        informationPanelLayout.setVerticalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(parcelIDLabel)
                    .addComponent(parcelIDTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addressLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cityLabel)
                    .addComponent(stateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stateLabel)
                    .addComponent(cityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zipLabel)
                    .addComponent(zipTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        byStatePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Parcels By State"));

        stateComboBox.setToolTipText("The abbreviation of the current State");
        stateComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                stateComboBoxItemStateChanged(evt);
            }
        });

        parcelList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { " " };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        parcelList.setToolTipText("Parcels that belong to the state given by the ComboBox above");
        parcelList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                parcelListValueChanged(evt);
            }
        });
        parcelScrollPanel.setViewportView(parcelList);

        javax.swing.GroupLayout byStatePanelLayout = new javax.swing.GroupLayout(byStatePanel);
        byStatePanel.setLayout(byStatePanelLayout);
        byStatePanelLayout.setHorizontalGroup(
            byStatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, byStatePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(byStatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(parcelScrollPanel)
                    .addComponent(stateComboBox, 0, 269, Short.MAX_VALUE))
                .addContainerGap())
        );
        byStatePanelLayout.setVerticalGroup(
            byStatePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(byStatePanelLayout.createSequentialGroup()
                .addComponent(stateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(parcelScrollPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout displayPanelLayout = new javax.swing.GroupLayout(displayPanel);
        displayPanel.setLayout(displayPanelLayout);
        displayPanelLayout.setHorizontalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displayPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(informationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(byStatePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        displayPanelLayout.setVerticalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displayPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(byStatePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(informationPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        getContentPane().add(displayPanel, java.awt.BorderLayout.CENTER);

        fileMenu.setMnemonic('F');
        fileMenu.setText("File");
        fileMenu.setToolTipText("File menu lets you save databases, open databases, and exit the program");

        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Open Database");
        openMenuItem.setToolTipText("Open a different database");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);

        saveAsMenuItem.setMnemonic('v');
        saveAsMenuItem.setText("Save As...");
        saveAsMenuItem.setToolTipText("Save Database under a new name");
        saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveAsMenuItem);

        clearMenuItem.setMnemonic('c');
        clearMenuItem.setText("Clear");
        clearMenuItem.setToolTipText("Clears the Parcel Information display component");
        clearMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(clearMenuItem);
        fileMenu.add(jSeparator2);

        printMenuItem.setMnemonic('i');
        printMenuItem.setText("Print");
        printMenuItem.setToolTipText("Print the entire GUI");
        printMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(printMenuItem);

        printParcelMenuItem.setMnemonic('l');
        printParcelMenuItem.setText("Print Parcel");
        printParcelMenuItem.setToolTipText("Print the information of a single Parcel");
        printParcelMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printParcelMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(printParcelMenuItem);
        fileMenu.add(jSeparator1);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.setToolTipText("Exit the Application");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        parcelMenuBar.add(fileMenu);

        actionMenu.setMnemonic('A');
        actionMenu.setText("Action");
        actionMenu.setToolTipText("Sort, search, scan, edit, remove, and do various things to Parcels");

        viewAllParcelsMenuItem.setMnemonic('V');
        viewAllParcelsMenuItem.setText("View All Parcels");
        viewAllParcelsMenuItem.setToolTipText("Opens a new Window with all Parcels listed for selection");
        viewAllParcelsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewAllParcelsMenuItemActionPerformed(evt);
            }
        });
        actionMenu.add(viewAllParcelsMenuItem);
        actionMenu.add(jSeparator5);

        scanMenuItem.setMnemonic('w');
        scanMenuItem.setText("Scan New");
        scanMenuItem.setToolTipText("Scan a new Parcel");
        scanMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scanMenuItemActionPerformed(evt);
            }
        });
        actionMenu.add(scanMenuItem);

        removeMenuItem.setMnemonic('r');
        removeMenuItem.setText("Remove");
        removeMenuItem.setToolTipText("Remove a Parcel");
        removeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeMenuItemActionPerformed(evt);
            }
        });
        actionMenu.add(removeMenuItem);

        editMenuItem.setMnemonic('e');
        editMenuItem.setText("Edit a Parcel");
        editMenuItem.setToolTipText("Edit the current selected Parcel");
        editMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMenuItemActionPerformed(evt);
            }
        });
        actionMenu.add(editMenuItem);
        actionMenu.add(jSeparator3);

        searchMenuItem.setMnemonic('I');
        searchMenuItem.setText("Search by ID");
        searchMenuItem.setToolTipText("Search for a Parcel by ID");
        searchMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchMenuItemActionPerformed(evt);
            }
        });
        actionMenu.add(searchMenuItem);

        searchNameMenuItem.setMnemonic('a');
        searchNameMenuItem.setText("Search by Name");
        searchNameMenuItem.setToolTipText("Search for a Parcel by customer name");
        searchNameMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchNameMenuItemActionPerformed(evt);
            }
        });
        actionMenu.add(searchNameMenuItem);

        searchZipMenuItem.setMnemonic('z');
        searchZipMenuItem.setText("Search by Zip");
        searchZipMenuItem.setToolTipText("Search for a Parcel by ZipCode");
        searchZipMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchZipMenuItemActionPerformed(evt);
            }
        });
        actionMenu.add(searchZipMenuItem);
        actionMenu.add(jSeparator4);

        backMenuItem.setMnemonic('b');
        backMenuItem.setText("Back");
        backMenuItem.setToolTipText("View previous Parcel");
        backMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backMenuItemActionPerformed(evt);
            }
        });
        actionMenu.add(backMenuItem);

        nextMenuItem.setMnemonic('n');
        nextMenuItem.setText("Next");
        nextMenuItem.setToolTipText("View next Parcel");
        nextMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextMenuItemActionPerformed(evt);
            }
        });
        actionMenu.add(nextMenuItem);

        parcelMenuBar.add(actionMenu);

        helpMenu.setMnemonic('p');
        helpMenu.setText("Help");
        helpMenu.setToolTipText("We are here to help");

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        aboutMenuItem.setToolTipText("About this project");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        parcelMenuBar.add(helpMenu);

        setJMenuBar(parcelMenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void parcelIDTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_parcelIDTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_parcelIDTextFieldActionPerformed

    private void nameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameTextFieldActionPerformed

    private void addressTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addressTextFieldActionPerformed

    private void stateTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stateTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stateTextFieldActionPerformed

    private void cityTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cityTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cityTextFieldActionPerformed

    private void zipTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zipTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_zipTextFieldActionPerformed

    /**
     * This method Listens to see if the value in parcelList has changed, and
     * upon hearing an event it updates which Parcel has its information 
     * displayed.
     * @param evt 
     */
    private void parcelListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_parcelListValueChanged
        showParcelInformation(parcelList.getSelectedIndex());
    }//GEN-LAST:event_parcelListValueChanged

    /** 
     * Displays the Parcels for the State which was selected in stateComboBox. 
     */
    private void stateComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_stateComboBoxItemStateChanged
        displayParcels();
    }//GEN-LAST:event_stateComboBoxItemStateChanged
    /**
     * Scans a new Parcel and adds it to our list. The database is rewritten
     * after every successful new Parcel addition.
     * @param evt 
     */
    private void scanNewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scanNewButtonActionPerformed
        int location = this.parcelList.getSelectedIndex();
        try {    
            ParcelScanner scanner = new ParcelScanner();
            scanner.setLocationRelativeTo(this);
            scanner.setVisible(true);
            Parcel newParcel = scanner.getParcel();
            if (newParcel != null) {
                parcels.add(newParcel);
                pushToStateMap();
                displayParcels();
                saveParcels();
                searchParcel(newParcel.getParcelID());
            } else {
                JOptionPane.showMessageDialog(null, "Parcel not Scanned",
                    "No Parcel Scanned",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NullPointerException nullex) {
            JOptionPane.showMessageDialog(null, "Parcel not Scanned",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE);
            parcelList.setVisible(true);
            parcelList.setSelectedIndex(location);
        }
    }//GEN-LAST:event_scanNewButtonActionPerformed

    /**
     * Initiates search for a Parcel by ID, takes input from user.
     * @param evt 
     */
    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        String parcelID = JOptionPane.showInputDialog(this, "Search for:",
                "Search for Parcel ID",
                JOptionPane.PLAIN_MESSAGE);
        searchParcel(parcelID);
    }//GEN-LAST:event_searchButtonActionPerformed
    
    /**
     * Removes a Parcel from our database.
     * @param evt 
     */
    private void removeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtonActionPerformed
        int reply = JOptionPane.showConfirmDialog(this, "Are you sure?",
                "Confirm Parcel deletion...",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if (reply == JOptionPane.YES_OPTION) {
            String parcelID = this.parcelList.getSelectedValue();
            parcelID = parcelID.substring(parcelID.lastIndexOf(" ")+1);
            int index = linearSearch(parcels, parcelID);
            
            parcels.remove(index);

            pushToStateMap();
            displayFirstNonEmptyState();
            displayParcels();
            showParcelInformation(parcelList.getSelectedIndex());
            saveParcels();
        } else {
            // Do nothing
        }
    }//GEN-LAST:event_removeButtonActionPerformed

    /** Displays the next Parcel in parcelList. */
    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        int currentIndex = this.parcelList.getSelectedIndex();
        int listSize = this.parcelList.getModel().getSize();
        int nextIndex = (currentIndex + 1) % listSize;
        this.parcelList.setSelectedIndex(nextIndex);
    }//GEN-LAST:event_nextButtonActionPerformed

    /** Displays the previous Parcel in parcelList. */
    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        int currentIndex = this.parcelList.getSelectedIndex();
        int listSize = this.parcelList.getModel().getSize();
        int nextIndex = currentIndex - 1 >= 0 ? currentIndex - 1 : listSize - 1;
        this.parcelList.setSelectedIndex(nextIndex);
    }//GEN-LAST:event_backButtonActionPerformed

    /** 
     * Launches the form in which the User may edit the currently selected 
     * Parcel. 
     */
    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        int location = this.parcelList.getSelectedIndex();
        String parcelID = this.parcelList.getSelectedValue();
        parcelID = parcelID.substring(parcelID.lastIndexOf(" ")+1);
        int index = linearSearch(parcels, parcelID);
        try {    
            ParcelEditor editor = new ParcelEditor(parcels.get(index));
            editor.setLocationRelativeTo(this);
            editor.setVisible(true);
            Parcel newParcel = editor.getParcel();
            if (newParcel != null) {
                parcels.set(index, newParcel);
                pushToStateMap();
                displayParcels();
                saveParcels();
                searchParcel(newParcel.getParcelID());
            } else {
                JOptionPane.showMessageDialog(null, "Parcel not Edited",
                    "No Parcel Edited",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NullPointerException nullex) {
            JOptionPane.showMessageDialog(null, "Parcel not Scanned",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE);
            parcelList.setVisible(true);
            parcelList.setSelectedIndex(location);
        }
    }//GEN-LAST:event_editButtonActionPerformed

    /** 
     * Allows the User to open a different database and work with a different
     * set of Parcels.
     * @param evt 
     */
    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        String oldFileName = this.fileName;
        DatabaseSelector selector = new DatabaseSelector();
        selector.setVisible(true);
        fileName = selector.getFileName();
        if (fileName == null) {
            JOptionPane.showMessageDialog(null, "No database was selected...",
                    "Database Not Selected",
                    JOptionPane.INFORMATION_MESSAGE);
            this.fileName = oldFileName;
        } else {
            readFromFile(fileName);
            pushToStateMap();
            setModelForStateCombo();
            displayFirstNonEmptyState();
            displayParcels();
            showParcelInformation(parcelList.getSelectedIndex());
        }
    }//GEN-LAST:event_openMenuItemActionPerformed

    /**
     * Allows the user to save the current database under a new name. Does
     * not delete the old file, and will only save under the new name.
     * @param evt 
     */
    private void saveAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsMenuItemActionPerformed
        String newFileName = JOptionPane.showInputDialog(this, "Save as:",
                "Enter a Valid File name",
                JOptionPane.PLAIN_MESSAGE);
        if (Validation.isFileName(newFileName)) {
            this.fileName = "src/parcelhub/data/" + newFileName + ".txt";
            saveParcels();
        } else {
            JOptionPane.showMessageDialog(null, "File name is invalid.",
                    "Database Not Saved",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_saveAsMenuItemActionPerformed

    /**
     * Prints the entire GUI.
     * @param evt 
     */
    private void printMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printMenuItemActionPerformed
        PrintUtilities.printComponent(this);
    }//GEN-LAST:event_printMenuItemActionPerformed

    /**
     * Prints a single Parcel.
     * @param evt 
     */
    private void printParcelMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printParcelMenuItemActionPerformed
        PrintUtilities.printComponent(this.informationPanel);
    }//GEN-LAST:event_printParcelMenuItemActionPerformed

    /**
     * Exits the application.
     * @param evt 
     */
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    /** Scans a Parcel by calling the corresponding button listener. */
    private void scanMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scanMenuItemActionPerformed
        scanNewButtonActionPerformed(evt);
    }//GEN-LAST:event_scanMenuItemActionPerformed

    /** Removes a Parcel by calling the corresponding button listener. */
    private void removeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeMenuItemActionPerformed
        removeButtonActionPerformed(evt);
    }//GEN-LAST:event_removeMenuItemActionPerformed

    /** Edits a Parcel by calling the corresponding button listener. */
    private void editMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMenuItemActionPerformed
        editButtonActionPerformed(evt);
    }//GEN-LAST:event_editMenuItemActionPerformed

    /** Searches for a Parcel by calling the corresponding button listener. */
    private void searchMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchMenuItemActionPerformed
        searchButtonActionPerformed(evt);
    }//GEN-LAST:event_searchMenuItemActionPerformed

    /** Selects previous Parcel by calling corresponding button listener. */
    private void backMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backMenuItemActionPerformed
        backButtonActionPerformed(evt);
    }//GEN-LAST:event_backMenuItemActionPerformed

    /** Selects next Parcel by calling corresponding button listener. */
    private void nextMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextMenuItemActionPerformed
        nextButtonActionPerformed(evt);
    }//GEN-LAST:event_nextMenuItemActionPerformed

    /**
     * Searches for all instances of where a Parcel Customer name contains
     * our search key, then spawns a new Dialog where the user may select
     * a Parcel out of all search return results.
     * @param evt 
     */
    private void searchNameMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchNameMenuItemActionPerformed
        String customerName = JOptionPane.showInputDialog(this, "Search for:",
                "Search for Name",
                JOptionPane.PLAIN_MESSAGE);
        if (customerName == null || customerName.length() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No Name given.",
                    "Search Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        ArrayList<Integer> indices = linearNameSearch(parcels, customerName);
        if (!indices.isEmpty()) {
            displayParcelWindow(parcels, indices, "Search by Name Results");
        } else {
            JOptionPane.showMessageDialog(this,
                    customerName + " not found.",
                    "Search Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_searchNameMenuItemActionPerformed

    /**
     * Spawns a new Dialog which lists all the Parcels in parcels ArrayList.
     * @param evt 
     */
    private void viewAllParcelsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewAllParcelsMenuItemActionPerformed
        ParcelWindow window = new ParcelWindow(parcels, "All Parcels");
        window.setVisible(true);
        String parcelID = window.getParcelID();
        if (parcelID == null || parcelID.length() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No Parcel ID given.",
                    "Search Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            searchParcel(parcelID);
        }
    }//GEN-LAST:event_viewAllParcelsMenuItemActionPerformed

    /**
     * Searches for all instances of where a Parcel Customer ZipCode contains
     * our search key, then spawns a new Dialog where the user may select
     * a Parcel out of all search return results.
     * @param evt 
     */
    private void searchZipMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchZipMenuItemActionPerformed
        String zipCode = JOptionPane.showInputDialog(this, "Search for:",
                "Search for ZipCode",
                JOptionPane.PLAIN_MESSAGE);
        if (zipCode == null || zipCode.length() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No ZipCode given.",
                    "Search Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        ArrayList<Integer> indices = linearZipSearch(parcels, zipCode);
        if (!indices.isEmpty()) {
            displayParcelWindow(parcels, indices, "Search by ZipCode Results");
        } else {
            JOptionPane.showMessageDialog(this,
                    zipCode + " not found.",
                    "Search Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_searchZipMenuItemActionPerformed

    /** Spawns the About Form. */
    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        AboutForm about = new AboutForm();
        about.setVisible(true);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    /** 
     * Clears the displayed Parcel information. If this wasn't included
     * in the project rubric I wouldn't have included it in this application.
     */
    private void clearMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearMenuItemActionPerformed
        clearParcelDisplay();
    }//GEN-LAST:event_clearMenuItemActionPerformed

    /**
     * Starts our GUI.
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ParcelHubGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ParcelHubGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ParcelHubGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ParcelHubGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ParcelHubGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenu actionMenu;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JTextField addressTextField;
    private javax.swing.JLabel arrivalLabel;
    private javax.swing.JTextField arrivalTextField;
    private javax.swing.JButton backButton;
    private javax.swing.JMenuItem backMenuItem;
    private javax.swing.JPanel byStatePanel;
    private javax.swing.JLabel cityLabel;
    private javax.swing.JTextField cityTextField;
    private javax.swing.JMenuItem clearMenuItem;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JButton editButton;
    private javax.swing.JMenuItem editMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JPanel informationPanel;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JPopupMenu.Separator jSeparator5;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JButton nextButton;
    private javax.swing.JMenuItem nextMenuItem;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JLabel parcelIDLabel;
    private javax.swing.JTextField parcelIDTextField;
    private javax.swing.JList<String> parcelList;
    private javax.swing.JMenuBar parcelMenuBar;
    private javax.swing.JScrollPane parcelScrollPanel;
    private javax.swing.JMenuItem printMenuItem;
    private javax.swing.JMenuItem printParcelMenuItem;
    private javax.swing.JButton removeButton;
    private javax.swing.JMenuItem removeMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem scanMenuItem;
    private javax.swing.JButton scanNewButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JMenuItem searchMenuItem;
    private javax.swing.JMenuItem searchNameMenuItem;
    private javax.swing.JMenuItem searchZipMenuItem;
    private javax.swing.JComboBox<String> stateComboBox;
    private javax.swing.JLabel stateLabel;
    private javax.swing.JTextField stateTextField;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel titlePanel;
    private javax.swing.JMenuItem viewAllParcelsMenuItem;
    private javax.swing.JLabel zipLabel;
    private javax.swing.JTextField zipTextField;
    // End of variables declaration//GEN-END:variables

    /**
     * Searches for a Parcel in our database by its ID. If the Parcel is
     * found it will be selected in its state listing. If not then the user
     * will be notified.
     * @param parcelID 
     */
    private void searchParcel(String parcelID) {
        if (parcelID == null || parcelID.length() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No Parcel ID given.",
                    "Search Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        int index = binarySearch(parcels, parcelID);
        if (index != -1) {
            Parcel parcel = parcels.get(index);
            this.stateComboBox.setSelectedItem(parcel.getState());
            String state = (String) stateComboBox.getSelectedItem();
            ArrayList<Parcel> parcelArrayList = (ArrayList)stateMap.get(state);
            int parcelIndex = binarySearch(parcelArrayList, parcelID);
            this.parcelList.setSelectedIndex(parcelIndex);
        } else {
            int indexTwo = linearSearch(parcels, parcelID);
            if (indexTwo != -1) {
                Parcel parcel = parcels.get(indexTwo);
                this.stateComboBox.setSelectedItem(parcel.getState());
                String state = (String) stateComboBox.getSelectedItem();
                ArrayList<Parcel> parcelArrayList = (ArrayList)stateMap.get(state);
                int parcelIndex = linearSearch(parcelArrayList, parcelID);
                this.parcelList.setSelectedIndex(parcelIndex);
            } else {
                JOptionPane.showMessageDialog(this,
                        parcelID + " not found.",
                        "Search Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Saves our Parcels to a text file that is named based on the 
     * current date. The file it is saved in is selected by the User.
     */
    private void saveParcels() {
        setModelForStateCombo();
        displayParcels();
        showParcelInformation(parcelList.getSelectedIndex());
        try {
            writeToFile(this.fileName);
        } catch (NullPointerException nullex) {
            JOptionPane.showMessageDialog(null, "DATABASE NOT SAVED",
                    "Input Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Write Parcels to a text file that is comma delimited.
     *
     * @param file The file path of our database to be written pre-condition: a
     * valid file name, post-condition: a new text file
     * is created with the current Parcels in the database
     * @see ParcelCSVFileWriter
     * @see Parcel
     */
    public void writeToFile(String file) {
        ParcelCSVFileWriter writer = new ParcelCSVFileWriter(file, parcels);
        writer.writeTheFile();
    }

    /**
     * Creates a new JDialog which displays Parcels in the parcels ArrayList 
     * at the Indices given by the indices  ArrayList.
     * @param parcels ArrayList of our Parcel Objects
     * @param indices Every index of a Parcel we wish to display
     * @param titleWindow Title of the window to be spawned
     */
    private void displayParcelWindow(ArrayList<Parcel> parcels, 
            ArrayList<Integer> indices, String titleWindow) {
        ParcelWindow window = new ParcelWindow(parcels, indices, titleWindow);
        window.setVisible(true);
        String parcelID = window.getParcelID();
        if (parcelID == null || parcelID.length() == 0) {
            JOptionPane.showMessageDialog(this,
                    "No Parcel ID given.",
                    "Search Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            searchParcel(parcelID);
        }
    }
}
