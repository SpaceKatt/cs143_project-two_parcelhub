/*
 * Copyright (C) 2016 Thomas
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

import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import static parcelhub.utilities.SortingAlgorithms.insertionSort;
import static parcelhub.utilities.SortingAlgorithms.insertionSortByName;
import static parcelhub.utilities.SortingAlgorithms.insertionSortByZip;

/**
 *
 * @author Thomas
 */
public class ParcelWindow extends javax.swing.JDialog {
    /** ArrayList of Parcels to display */
    private ArrayList<Parcel> displayParcels;
    /** String of the ParcelID we have selected */
    private String parcelID;
    
    /**
     * 
     * @param parcels The ArrayList of all extant Parcels
     * @param indices The indices in ArrayList parcels that we wish to display
     * @param titleWindow 
     */
    public ParcelWindow(ArrayList<Parcel> parcels, 
            ArrayList<Integer> indices, String titleWindow) {
        initComponents();
        this.setTitle(titleWindow);
        this.setModal(true);
        setLocationRelativeTo(null);
        this.getRootPane().setDefaultButton(this.selectButton);
        this.setIconImage(Toolkit.getDefaultToolkit().
                getImage("src/parcelhub/images/238be5e.png"));
        displayParcels = grabParcels(parcels, indices);
        sortParcels();
        pushToList();
    }
    
   /**
     * 
     * @param parcels The ArrayList of all extant Parcels
     * @param titleWindow 
     */
    public ParcelWindow(ArrayList<Parcel> parcels, String titleWindow) {
        initComponents();
        this.setTitle(titleWindow);
        this.setModal(true);
        setLocationRelativeTo(null);
        this.getRootPane().setDefaultButton(this.selectButton);
        this.setIconImage(Toolkit.getDefaultToolkit().
                getImage("src/parcelhub/images/238be5e.png"));
        displayParcels = new ArrayList(parcels);
        sortParcels();
        pushToList();
    }
    
    /**
     * Adds every parcel from displayParcels and creates a String to
     * add to the display parcelList.
     */
    private void pushToList() {
        DefaultListModel model = new DefaultListModel();
        for (int i = 0; i < displayParcels.size(); i++) {
            Parcel parcel = displayParcels.get(i);
            String displayString = parcel.getParcelID() + ", " 
                    + parcel.getNameReciever() + ", " + parcel.getZip();
            model.addElement(displayString);
        }
        this.parcelList.setModel(model);
        this.parcelList.setSelectedIndex(0);
    }
    
    private void sortParcels() {
        if (this.sortIDRadioButton.isSelected()) {
            insertionSort(displayParcels);
        } else if (this.sortNameRadioButton.isSelected()) {
            insertionSortByName(displayParcels);
        } else if (this.sortZipRadioButton.isSelected()) {
            insertionSortByZip(displayParcels);
        }
        pushToList();
    }
    
    /**
     * Returns an ArrayList with all of the Parcels specified by the
     * ArrayList indices.
     * @param parcels The ArrayList of all extant Parcels
     * @param indices The indices in ArrayList parcels that we wish to display
     * @return An ArrayList of all the Parcels we wish to display
     */
    private ArrayList<Parcel> grabParcels(ArrayList<Parcel> parcels, 
            ArrayList<Integer> indices) {
        ArrayList<Parcel> parcelsToGo = new ArrayList<>();
        for (int i = 0; i < indices.size(); i++) {
            parcelsToGo.add(parcels.get(indices.get(i)));
        }
        return parcelsToGo;
    }

    /** @return The ID of the selected Parcel. */
    public String getParcelID() {
        return this.parcelID;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sortButtonGroup = new javax.swing.ButtonGroup();
        displayPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        parcelList = new javax.swing.JList<>();
        controlPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        selectButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        sortPanel = new javax.swing.JPanel();
        sortIDRadioButton = new javax.swing.JRadioButton();
        sortNameRadioButton = new javax.swing.JRadioButton();
        sortZipRadioButton = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        parcelList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(parcelList);

        javax.swing.GroupLayout displayPanelLayout = new javax.swing.GroupLayout(displayPanel);
        displayPanel.setLayout(displayPanelLayout);
        displayPanelLayout.setHorizontalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displayPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
                .addContainerGap())
        );
        displayPanelLayout.setVerticalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, displayPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(displayPanel, java.awt.BorderLayout.CENTER);

        controlPanel.setLayout(new java.awt.GridLayout(1, 1));

        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        selectButton.setMnemonic('o');
        selectButton.setText("Select");
        selectButton.setToolTipText("Open selected database");
        selectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectButtonActionPerformed(evt);
            }
        });
        jPanel1.add(selectButton);

        exitButton.setMnemonic('c');
        exitButton.setText("Cancel");
        exitButton.setToolTipText("Cancel database opening");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
        jPanel1.add(exitButton);

        controlPanel.add(jPanel1);

        getContentPane().add(controlPanel, java.awt.BorderLayout.SOUTH);

        sortPanel.setLayout(new java.awt.GridLayout(3, 0));

        sortButtonGroup.add(sortIDRadioButton);
        sortIDRadioButton.setMnemonic('i');
        sortIDRadioButton.setSelected(true);
        sortIDRadioButton.setText("Sort by Parcel ID");
        sortIDRadioButton.setToolTipText("Sorts the Parcels by ID");
        sortIDRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortIDRadioButtonActionPerformed(evt);
            }
        });
        sortPanel.add(sortIDRadioButton);

        sortButtonGroup.add(sortNameRadioButton);
        sortNameRadioButton.setMnemonic('n');
        sortNameRadioButton.setText("Sort by Name");
        sortNameRadioButton.setToolTipText("Sorts the Parcels by Name");
        sortNameRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortNameRadioButtonActionPerformed(evt);
            }
        });
        sortPanel.add(sortNameRadioButton);

        sortButtonGroup.add(sortZipRadioButton);
        sortZipRadioButton.setMnemonic('z');
        sortZipRadioButton.setText("Sort by ZipCode");
        sortZipRadioButton.setToolTipText("Sorts the Parcels by Zip Code");
        sortZipRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortZipRadioButtonActionPerformed(evt);
            }
        });
        sortPanel.add(sortZipRadioButton);

        getContentPane().add(sortPanel, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectButtonActionPerformed
        parcelID = this.parcelList.getSelectedValue().substring(0, 32);
        this.dispose();
    }//GEN-LAST:event_selectButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        parcelID = null;
        this.dispose();
    }//GEN-LAST:event_exitButtonActionPerformed

    private void sortZipRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortZipRadioButtonActionPerformed
        sortParcels();
    }//GEN-LAST:event_sortZipRadioButtonActionPerformed

    private void sortIDRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortIDRadioButtonActionPerformed
        sortParcels();
    }//GEN-LAST:event_sortIDRadioButtonActionPerformed

    private void sortNameRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortNameRadioButtonActionPerformed
        sortParcels();
    }//GEN-LAST:event_sortNameRadioButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel controlPanel;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JButton exitButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> parcelList;
    private javax.swing.JButton selectButton;
    private javax.swing.ButtonGroup sortButtonGroup;
    private javax.swing.JRadioButton sortIDRadioButton;
    private javax.swing.JRadioButton sortNameRadioButton;
    private javax.swing.JPanel sortPanel;
    private javax.swing.JRadioButton sortZipRadioButton;
    // End of variables declaration//GEN-END:variables
}
