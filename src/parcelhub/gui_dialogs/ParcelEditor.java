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
package parcelhub.gui_dialogs;

import parcelhub.objects.Parcel;
import java.awt.Toolkit;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import static parcelhub.ParcelHubGUI.STATE_ABBV;
import parcelhub.utilities.Validation;

/**
 * Displays a Parcel and its information in editable text boxes, allows
 * for the main application to get the edited Parcel, but only after it 
 * verifies the changes the user has made. Parcel ID and Date scanned are not 
 * included because they are designed to not be editable.
 *
 * Project: Parcel Hub Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
 * Course: CS 143 
 * Created on May 6, 2016, 5:06:21 PM 
 * Revised on May 9, 2016, 5:07:52 PM
 *
 * @author thomas.kercheval
 */
public class ParcelEditor extends javax.swing.JDialog {
    /**
     * The Parcel we wish to edit. Saved as a new Parcel as to not 
     * change the state of the original object before the changes have passed
     * validation.
     */
    Parcel editParcel;
    
    /**
     * Creates new form ParcelEditer, copies the Parcel that is fed to this
     * constructor as an instance variable, and displays its information in
     * JTextFields.
     * @param parcel The parcel we are going to edit.
     */
    public ParcelEditor(Parcel parcel) {
        initComponents();
        this.setIconImage(Toolkit.getDefaultToolkit().
                getImage("src/parcelhub/images/238be5e.png"));
        this.setModal(true);
        this.stateComboBox.setModel(new DefaultComboBoxModel(STATE_ABBV));
        editParcel = new Parcel(parcel);
        nameTextField.setText(parcel.getNameReciever());
        addressTextField.setText(parcel.getAddress());
        stateComboBox.setSelectedItem(parcel.getState());
        zipTextField.setText(parcel.getZip());
        cityTextField.setText(parcel.getCity());
        this.getRootPane().setDefaultButton(this.saveButton);
    }

    /**
     * Creates new form ParcelEditer. This constructor is not used.
     */
    public ParcelEditor() {
        initComponents();
        this.setModal(true);
        this.stateComboBox.setModel(new DefaultComboBoxModel(STATE_ABBV));
    }
    
    /** @return The edited Parcel. */
    public Parcel getParcel() {
        return editParcel;
    }
    
    /** 
     * Performs Validation by using the methods given by the Validation class,
     * does nothing if the fields are valid, and sends the user an error 
     * message if anything is triggered. All Validation is done using RegEx.
     * @param name The Parcels recipient's name.
     * @param address The Parcel's recipient's street address.
     * @param city The Parcel's recipient's city.
     * @param zip The Parcel's recipient's ZipCode.
     * @return true if all fields are valid.
     */
    private boolean validateFields(String name, String address, String city, 
            String zip) {
        if (!Validation.isName(name)) {
            JOptionPane.showMessageDialog(this,
                                          "Must enter a valid name.\n"
                                        + "First and Last, middle optional.\n"
                                        + "\nDid you remember to Capitalize?",
                                          "Incomplete Form",
                                          JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!Validation.isAddress(address)) {
            JOptionPane.showMessageDialog(this,
                                          "Must enter a valid address.",
                                          "Incomplete Form",
                                          JOptionPane.ERROR_MESSAGE);
            
            return false;
        } else if (!Validation.isCity(city)) {
            JOptionPane.showMessageDialog(this,
                                          "Must enter a valid city.\n\n"
                                        + "Did you remember to Capatalize?",
                                          "Incomplete Form",
                                          JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (!Validation.isZip(zip)) {
            JOptionPane.showMessageDialog(this,
                                          "Must enter a valid ZipCode.\n\n"
                                        + "XXXXX or XXXXX-XXXX",
                                          "Incomplete Form",
                                          JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        informationPanel = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
        cityLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        addressTextField = new javax.swing.JTextField();
        stateLabel = new javax.swing.JLabel();
        cityTextField = new javax.swing.JTextField();
        zipLabel = new javax.swing.JLabel();
        zipTextField = new javax.swing.JTextField();
        stateComboBox = new javax.swing.JComboBox<>();
        controlPanel = new javax.swing.JPanel();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        titlePanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        logoLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Edit Parcel");
        setResizable(false);

        informationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Parcel Information"));

        nameLabel.setText("Name: ");

        addressLabel.setText("Address:");

        cityLabel.setText("City:");

        nameTextField.setToolTipText("The name of the recipient of the Parcel currently being Edited");
        nameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTextFieldActionPerformed(evt);
            }
        });

        addressTextField.setToolTipText("The street address of the recipient of the Parcel currently being Edited");
        addressTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addressTextFieldActionPerformed(evt);
            }
        });

        stateLabel.setText("State:");

        cityTextField.setToolTipText("The City of the recipient of the Parcel currently being Edited");
        cityTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cityTextFieldActionPerformed(evt);
            }
        });

        zipLabel.setText("Zip:");

        zipTextField.setToolTipText("The ZipCode of the recipient of the Parcel currently being Edited");
        zipTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zipTextFieldActionPerformed(evt);
            }
        });

        stateComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        stateComboBox.setToolTipText("The state of the recipient of the Parcel currently being Edited");

        javax.swing.GroupLayout informationPanelLayout = new javax.swing.GroupLayout(informationPanel);
        informationPanel.setLayout(informationPanelLayout);
        informationPanelLayout.setHorizontalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addContainerGap()
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
                        .addComponent(stateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zipLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(zipTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)))
                .addContainerGap())
        );
        informationPanelLayout.setVerticalGroup(
            informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informationPanelLayout.createSequentialGroup()
                .addContainerGap()
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
                    .addComponent(stateLabel)
                    .addComponent(cityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zipLabel)
                    .addComponent(zipTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        getContentPane().add(informationPanel, java.awt.BorderLayout.CENTER);

        controlPanel.setLayout(new java.awt.GridLayout(1, 2));

        saveButton.setMnemonic('s');
        saveButton.setText("Save Parcel");
        saveButton.setToolTipText("Save what you have edited");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        controlPanel.add(saveButton);

        cancelButton.setMnemonic('c');
        cancelButton.setText("Cancel");
        cancelButton.setToolTipText("Canel Editing");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        controlPanel.add(cancelButton);

        getContentPane().add(controlPanel, java.awt.BorderLayout.SOUTH);

        titleLabel.setFont(new java.awt.Font("AR JULIAN", 0, 24)); // NOI18N
        titleLabel.setText("Edit a Parcel");

        logoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parcelhub/images/238be5e.png"))); // NOI18N
        logoLabel.setText("jLabel2");

        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                    .addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(titlePanel, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /** 
     * Try to save if enter is pressed in this text field. 
     * @param evt The event which triggers this listener.
     */
    private void nameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameTextFieldActionPerformed
        saveButtonActionPerformed(evt);
    }//GEN-LAST:event_nameTextFieldActionPerformed

    /** 
     * Try to save if enter is pressed in this text field. 
     * @param evt The event which triggers this listener.
     */
    private void addressTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressTextFieldActionPerformed
        saveButtonActionPerformed(evt);
    }//GEN-LAST:event_addressTextFieldActionPerformed

    /** 
     * Try to save if enter is pressed in this text field. 
     * @param evt The event which triggers this listener.
     */
    private void cityTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cityTextFieldActionPerformed
        saveButtonActionPerformed(evt);
    }//GEN-LAST:event_cityTextFieldActionPerformed

    /** 
     * Try to save if enter is pressed in this text field. 
     * @param evt The event which triggers this listener.
     */
    private void zipTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zipTextFieldActionPerformed
        saveButtonActionPerformed(evt);
    }//GEN-LAST:event_zipTextFieldActionPerformed

    /** 
     * Save if all fields are Valid. If not all fields are valid, do nothing.
     * @param evt The event which triggers this listener.
     */
    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        String name = this.nameTextField.getText();
        String address = this.addressTextField.getText();
        String zip = this.zipTextField.getText();
        String state = (String) this.stateComboBox.getSelectedItem();
        String city = this.cityTextField.getText();
        if (validateFields(name, address, city, zip)) {
            editParcel.setNameReciever(name);
            editParcel.setCity(city);
            editParcel.setAddress(address);
            editParcel.setZip(zip);
            editParcel.setState(state);
            this.setVisible(false);
        } else {
            // Do nothing
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    /**
     * Cancels the edit by setting editParcel to be null and closing the
     * window.
     * @param evt The event which triggers this listener.
     */
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.editParcel = null;
        this.setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel addressLabel;
    private javax.swing.JTextField addressTextField;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel cityLabel;
    private javax.swing.JTextField cityTextField;
    private javax.swing.JPanel controlPanel;
    private javax.swing.JPanel informationPanel;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JButton saveButton;
    private javax.swing.JComboBox<String> stateComboBox;
    private javax.swing.JLabel stateLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel titlePanel;
    private javax.swing.JLabel zipLabel;
    private javax.swing.JTextField zipTextField;
    // End of variables declaration//GEN-END:variables
}
