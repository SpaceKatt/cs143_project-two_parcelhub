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

import java.awt.Toolkit;

/**
 * The About form for this project.
 *
 * Project: Parcel Hub Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
 * Course: CS 143 
 * Created on May 8, 2016, 4:06:51 PM 
 * Revised on May 9, 2016, 5:03:12 PM
 *
 * @author thomas.kercheval
 */
public class AboutForm extends javax.swing.JDialog {

    /**
     * Creates new form AboutForm. Sets modal, sets icon, and sets caret
     * position to start at beginning of the text box.
     */
    public AboutForm() {
        initComponents();
        this.setIconImage(Toolkit.getDefaultToolkit().
                getImage("src/parcelhub/images/238be5e.png"));
        this.getRootPane().setDefaultButton(closeJButton);
        this.setModal(true);
        setLocationRelativeTo(null);
        this.jTextArea1.setCaretPosition(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleJPanel = new javax.swing.JPanel();
        logoJLabel = new javax.swing.JLabel();
        titleJLabel = new javax.swing.JLabel();
        aboutJPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        copyrightJLabel = new javax.swing.JLabel();
        closeJButton = new javax.swing.JButton();
        warningJLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("About Parcel Hub");
        setResizable(false);

        logoJLabel.setFont(new java.awt.Font("Tahoma", 2, 24)); // NOI18N
        logoJLabel.setForeground(new java.awt.Color(51, 0, 0));
        logoJLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/parcelhub/images/238be5e.png"))); // NOI18N

        titleJLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        titleJLabel.setForeground(new java.awt.Color(51, 0, 0));
        titleJLabel.setText("Parcel Hub Database");

        javax.swing.GroupLayout titleJPanelLayout = new javax.swing.GroupLayout(titleJPanel);
        titleJPanel.setLayout(titleJPanelLayout);
        titleJPanelLayout.setHorizontalGroup(
            titleJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titleJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(logoJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(titleJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );
        titleJPanelLayout.setVerticalGroup(
            titleJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titleJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(titleJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleJLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(titleJPanelLayout.createSequentialGroup()
                        .addComponent(logoJLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 7, Short.MAX_VALUE)))
                .addContainerGap())
        );

        getContentPane().add(titleJPanel, java.awt.BorderLayout.NORTH);

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 14)); // NOI18N
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("# Parcel Hub Project\n\nA simple application emulating the receiving of Parcels and storing\ntheir information, for my Java class (CS143/Spr16).\n\n#### Project Objective:\n\nTo create a GUI that allows a user to scan, edit, and store Parcels in\nmultiple databases. This application must also provide basic functionalities\nthat are requisite for a Shipping Hub.\n\n#### Features:\n\n  - Users may scan, edit, remove, search for, and view the information of\n    Parcels which make their way into a database.\n  - Multiple databases may be used to give users the chance to view Parcel\n    listings from different days.\n  - Databases may be renamed.\n  - Users may search for Parcels by ID, Customer Name, and ZipCode.\n  - Search by name and ZipCode spawns a new window which lists all results\n    found. Upon closing this window, the selected Parcel will be found and\n    highlighted in the main GUI Window.\n  - Individual Parcel information, or the entire GUI, may be printed from the\n    File menu.\n  - Parcels are listed by state, and only states which contain Parcels are\n    listed.\n  - All Parcels may be viewed at once from the Action menu.\n  - When viewing all Parcels, or search by name/zip results, Parcels may be\n    sorted by ID, Customer Name, or ZipCode.\n  - ToolTip Text is given for all buttons and all menus/menuItems.\n  - Mnemonics are given for all buttons and all menus/menuItems.\n  - Buttons are enabled/disabled based on whether their actions are valid\n    within the current context of the application.\n  - Splash screen is displayed on startup.\n  - Parcels are sorted by ID in the State listings.\n  - Changes to the database are dynamic in nature, e.g. Parcels that are\n    deleted do not reappear when the application is restarted.\n  - Fields from editing and scanning Parcels are validated with regular\n    expressions.\n  - Dancer deletion is confirmed before performed.\n  - Parcel ID is generated using an MD5 digest, hence all IDs are unique\n    between all databases. Even Parcels with the exact same information will\n    produce unique IDs since the time of the Parcel being scanned is being fed\n    into the cryptographic hash.\n\n##### Contact:\n\n    Thomas Kercheval -> spacekattpoispin@gmail.com\n\n##### Notes:\n\n  - This project is backed up on GitHub.\n  - This project is licensed under GPL v3.0\n  - Press Enter to close this window\n");
        jTextArea1.setToolTipText("About this project");
        jTextArea1.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout aboutJPanelLayout = new javax.swing.GroupLayout(aboutJPanel);
        aboutJPanel.setLayout(aboutJPanelLayout);
        aboutJPanelLayout.setHorizontalGroup(
            aboutJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aboutJPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
                .addContainerGap())
        );
        aboutJPanelLayout.setVerticalGroup(
            aboutJPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(aboutJPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 319, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 9, Short.MAX_VALUE))
        );

        getContentPane().add(aboutJPanel, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.GridLayout(1, 0));

        copyrightJLabel.setText("Copyright 2016; Thomas Kercheval");
        copyrightJLabel.setToolTipText("It's my name :D");
        jPanel1.add(copyrightJLabel);

        closeJButton.setMnemonic('c');
        closeJButton.setText("Close");
        closeJButton.setToolTipText("Close this about window");
        closeJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeJButtonActionPerformed(evt);
            }
        });
        jPanel1.add(closeJButton);

        warningJLabel.setText("If: Steal Code -> Then: Suffer Curse");
        warningJLabel.setToolTipText("@see Death");
        jPanel1.add(warningJLabel);

        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Closes the About form and returns to main GUI.
     * @param evt 
     */
    private void closeJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeJButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closeJButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel aboutJPanel;
    private javax.swing.JButton closeJButton;
    private javax.swing.JLabel copyrightJLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel logoJLabel;
    private javax.swing.JLabel titleJLabel;
    private javax.swing.JPanel titleJPanel;
    private javax.swing.JLabel warningJLabel;
    // End of variables declaration//GEN-END:variables
}
