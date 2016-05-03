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

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import parcelhub.utilities.ParcelCSVFileReader;
import static parcelhub.utilities.SortingAlgorithms.insertionSort;

/**
 * This GUI runs our database for the Shipping Hub. It facilitates the
 * reading/writing to external files, display of Parcels received, and sorting
 * of Parcels by state, ID, zipcode, and search capacity by many different
 * means.
 *
 * Project: Parcel Hub Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
 * Course: CS 143 Created on May 2, 2016, 5:06:21 PM Revised on May 2, 2016,
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

    /** An array of all relevant state abbreviations. */
    public static final String[] STATE_ABBV = new String[]{"AK", "AL", "AR", 
        "AS", "AZ", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "GU", "HI", "IA", 
        "ID", "IL", "IN", "KS", "KY", "LA", "MA", "MD", "ME", "MH", "MI", "MN", 
        "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM", "NV", "NY", "OH", 
        "OK", "OR", "PA", "PR", "PW", "RI", "SC", "SD", "TN", "TX", "UT", "VA", 
        "VI", "VT", "WA", "WI", "WV", "WY"};
    /** The relative path of our Parcel database. */
    public static final String FILE_NAME = "src/parcelhub/data/Parcels.txt";
    public static final int ID_INDEX = 0;
    public static final int NAME_INDEX = 1;
    public static final int ADDRESS_INDEX = 2;
    public static final int CITY_INDEX = 3;
    public static final int STATE_INDEX = 4;
    public static final int ZIP_INDEX = 5;
    public static final int DATE_INDEX = 6;

    /**
     * Creates new form ParcelHubGUI
     */
    public ParcelHubGUI() {
        initComponents();
        setLocationRelativeTo(null);
        parcels = new ArrayList<>();
        stateMap = createStateMap();
        this.stateComboBox.setModel(new DefaultComboBoxModel(STATE_ABBV));
        readFromFile(FILE_NAME);
        pushToStateMap();

        displayParcels();
        showParcelInformation(parcelList.getSelectedIndex());
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
     * Writes the contents of ArrayList parcels to the Database File.
     * @param fileName 
     */
    private void writeToFile(String fileName) {
        
    }
    
    /**
     * This method distributes parcels from the ArrayList parcels
     * to the ArrayLists attached to States in the HashMap.
     */
    private void pushToStateMap() {
        for (Parcel parcel: parcels) {
            String state = parcel.getState();
            ArrayList<Parcel> list = (ArrayList<Parcel>) stateMap.get(state);
            list.add(parcel);
        }
        sortAllArrayLists();
    }
    
    /** 
     * Displays Parcels depending upon which state is currently selected.
     */
    private void displayParcels() {
        String state = (String) stateComboBox.getSelectedItem();
        ArrayList<Parcel> parcelArrayList = (ArrayList)stateMap.get(state);
        DefaultListModel model = new DefaultListModel();
        for (Parcel parcel: parcelArrayList) {
            model.addElement(parcel.getParcelID());
        }
        this.parcelList.setModel(model);
        parcelList.setSelectedIndex(0);
    }
    
    /**
     * Displays all Parcel Information for the selected Parcel.
     * @param index 
     */
    private void showParcelInformation(int index) {
        String state = (String) stateComboBox.getSelectedItem();
        if (index != -1) {
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

        titlePanel = new javax.swing.JPanel();
        arrivalLabel = new javax.swing.JLabel();
        arrivalTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        controlPanel = new javax.swing.JPanel();
        scanNewButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        seachButton = new javax.swing.JButton();
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
        actionMenu = new javax.swing.JMenu();
        helpMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Parcel Hub");

        arrivalLabel.setText("Arrived at:");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parcelhub/images/238be5e.png"))); // NOI18N

        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(arrivalLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(arrivalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 481, Short.MAX_VALUE)
                .addContainerGap())
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titlePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(arrivalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(arrivalLabel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(titlePanel, java.awt.BorderLayout.NORTH);

        controlPanel.setLayout(new java.awt.GridLayout(1, 6));

        scanNewButton.setMnemonic('s');
        scanNewButton.setText("Scan New");
        scanNewButton.setToolTipText("Scan New Parcel into System");
        controlPanel.add(scanNewButton);

        removeButton.setMnemonic('R');
        removeButton.setText("Remove");
        removeButton.setToolTipText("Remove Parcel from System");
        controlPanel.add(removeButton);

        editButton.setMnemonic('e');
        editButton.setText("Edit");
        editButton.setToolTipText("Edit existing Parcel in System");
        controlPanel.add(editButton);

        seachButton.setMnemonic('h');
        seachButton.setText("Search");
        seachButton.setToolTipText("Search for Parcel in System");
        controlPanel.add(seachButton);

        backButton.setMnemonic('B');
        backButton.setText("< Back");
        backButton.setToolTipText("View previous Parcel");
        controlPanel.add(backButton);

        nextButton.setMnemonic('N');
        nextButton.setText("Next >");
        nextButton.setToolTipText("View Next Parcel");
        controlPanel.add(nextButton);

        getContentPane().add(controlPanel, java.awt.BorderLayout.SOUTH);

        informationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Parcel Information"));

        parcelIDLabel.setText("Parcel ID:");

        nameLabel.setText("Name: ");

        addressLabel.setText("Address:");

        cityLabel.setText("City:");

        parcelIDTextField.setEditable(false);
        parcelIDTextField.setText("\n");
        parcelIDTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parcelIDTextFieldActionPerformed(evt);
            }
        });

        nameTextField.setEditable(false);
        nameTextField.setText("\n");
        nameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTextFieldActionPerformed(evt);
            }
        });

        addressTextField.setEditable(false);
        addressTextField.setText("\n");
        addressTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressTextFieldActionPerformed(evt);
            }
        });

        stateTextField.setEditable(false);
        stateTextField.setText("\n");
        stateTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stateTextFieldActionPerformed(evt);
            }
        });

        stateLabel.setText("State:");

        cityTextField.setEditable(false);
        cityTextField.setText("\n");
        cityTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cityTextFieldActionPerformed(evt);
            }
        });

        zipLabel.setText("Zip:");

        zipTextField.setEditable(false);
        zipTextField.setText("\n");
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
                            .addComponent(nameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                            .addComponent(addressTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 496, Short.MAX_VALUE)
                            .addGroup(informationPanelLayout.createSequentialGroup()
                                .addComponent(cityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(stateLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(zipLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(zipTextField)))))
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
                .addContainerGap(37, Short.MAX_VALUE))
        );

        byStatePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Parcels By State"));

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
                    .addComponent(stateComboBox, 0, 165, Short.MAX_VALUE))
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
                .addComponent(informationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(byStatePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        displayPanelLayout.setVerticalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displayPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(byStatePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(informationPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        getContentPane().add(displayPanel, java.awt.BorderLayout.CENTER);

        fileMenu.setText("File");
        parcelMenuBar.add(fileMenu);

        actionMenu.setText("Action");
        parcelMenuBar.add(actionMenu);

        helpMenu.setText("Help");
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

    private void parcelListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_parcelListValueChanged
        showParcelInformation(parcelList.getSelectedIndex());
    }//GEN-LAST:event_parcelListValueChanged

    private void stateComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_stateComboBoxItemStateChanged
        displayParcels();
    }//GEN-LAST:event_stateComboBoxItemStateChanged

    /**
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
            public void run() {
                new ParcelHubGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu actionMenu;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JTextField addressTextField;
    private javax.swing.JLabel arrivalLabel;
    private javax.swing.JTextField arrivalTextField;
    private javax.swing.JButton backButton;
    private javax.swing.JPanel byStatePanel;
    private javax.swing.JLabel cityLabel;
    private javax.swing.JTextField cityTextField;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JButton editButton;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JPanel informationPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JButton nextButton;
    private javax.swing.JLabel parcelIDLabel;
    private javax.swing.JTextField parcelIDTextField;
    private javax.swing.JList<String> parcelList;
    private javax.swing.JMenuBar parcelMenuBar;
    private javax.swing.JScrollPane parcelScrollPanel;
    private javax.swing.JButton removeButton;
    private javax.swing.JButton scanNewButton;
    private javax.swing.JButton seachButton;
    private javax.swing.JComboBox<String> stateComboBox;
    private javax.swing.JLabel stateLabel;
    private javax.swing.JTextField stateTextField;
    private javax.swing.JPanel titlePanel;
    private javax.swing.JLabel zipLabel;
    private javax.swing.JTextField zipTextField;
    // End of variables declaration//GEN-END:variables
}
