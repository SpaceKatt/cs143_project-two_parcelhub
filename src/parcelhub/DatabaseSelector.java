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
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author Thomas
 */
public class DatabaseSelector extends javax.swing.JDialog {
    ArrayList<String> databases;
    String fileName;
    /**
     * Creates new form DatabaseSelector
     */
    public DatabaseSelector() {
        initComponents();
        this.setModal(true);
        setLocationRelativeTo(null);
        this.getRootPane().setDefaultButton(this.openButton);
        this.setIconImage(Toolkit.getDefaultToolkit().
                getImage("src/parcelhub/images/238be5e.png"));
        databases = grabFileNames();
        DefaultListModel model = new DefaultListModel();
        insertionSort(databases);
        for (int i = 0; i < databases.size(); i++) {
            model.addElement(databases.get(i));
        }
        this.databaseList.setModel(model);
        this.databaseList.setSelectedIndex(0);
    }
    
    /** @return Filename of the Database to open. */
    public String getFileName() {
        return this.fileName;
    }
    
    /**
     * Implements a simple insertion sort to sort an ArrayList of Comparable
     * objects.
     * @param list The ArrayList we want to sort.
     */
    public static void insertionSort(ArrayList<String> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i; j > 0 && less(list.get(j), list.get(j - 1)); j--) {
                swap(list, j, j - 1);
            }
        }
    }
    
   /**
     * Compares object one to comparable object two and determines if 
     * object one is lesser in the order of the sort. 
     * @param one Object to compare to two, is this object less?
     * @param two Object to compare to one.
     * @return true if one is less than two in their ordering.
     */
    public static boolean less(String one, String two) {
        return one.compareTo(two) < 0;
    }
    
   /**
     * This method swaps two specified elements of an ArrayList.
     * @param list ArrayList of Comparable objects
     * @param indexOne Index of first item we want to swap
     * @param indexTwo Index of second item we want to swap
     */
    public static void swap(ArrayList<String> list, int indexOne, 
                            int indexTwo) {
        String temp = list.get(indexOne);
        list.set(indexOne, list.get(indexTwo));
        list.set(indexTwo, temp);
    }
    
    /**
     * Grabs all the file names in `src/parcelhub/data` so we can give
     * the user a list of databases to choose from.
     * @return An arraylist of database file paths.
     */
    public static ArrayList<String> grabFileNames() {
        File folder = new File("src/parcelhub/data");
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> list = new ArrayList<>();
        for (File file : listOfFiles) {
            list.add(file.toString());
        }
        return list;
    }

    /**
     * Generates a String so we can name our new Database uniquely.
     * @return String of todays date, made unique by integer addition.
     */
    public String generateFileName() {
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        String dateNow = "src/parcelhub/data/" + dateFormat.format(today);
        String canidateName = dateNow;
        int additionalPart = 0;
        while (fileAlreadyExists(canidateName + ".txt")) {
            additionalPart++;
            canidateName = dateNow + "-" + additionalPart;
        }
        return canidateName + ".txt";
    }
    
    /** 
     * @return true if the file name already exists
     * @param name Name of the file we are looking for
     */ 
    private boolean fileAlreadyExists(String name) {
        if (!databases.isEmpty() && databases.get(0).contains("\\")) {
            name = name.replace("/", "\\");
        }
        return this.databases.contains(name);     
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        displayPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        databaseList = new javax.swing.JList<>();
        controlPanel = new javax.swing.JPanel();
        newButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        openButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();
        titlePanel = new javax.swing.JPanel();
        selectLabel = new javax.swing.JLabel();
        parcelhubLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        databaseList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(databaseList);

        javax.swing.GroupLayout displayPanelLayout = new javax.swing.GroupLayout(displayPanel);
        displayPanel.setLayout(displayPanelLayout);
        displayPanelLayout.setHorizontalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(displayPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addContainerGap())
        );
        displayPanelLayout.setVerticalGroup(
            displayPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, displayPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(displayPanel, java.awt.BorderLayout.CENTER);

        controlPanel.setLayout(new java.awt.GridLayout(2, 1));

        newButton.setText("New");
        newButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newButtonActionPerformed(evt);
            }
        });
        controlPanel.add(newButton);

        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        openButton.setText("Open");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });
        jPanel1.add(openButton);

        exitButton.setText("Exit Application");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
        jPanel1.add(exitButton);

        controlPanel.add(jPanel1);

        getContentPane().add(controlPanel, java.awt.BorderLayout.SOUTH);

        selectLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        selectLabel.setText("Select Database");

        parcelhubLabel.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        parcelhubLabel.setText("Parcel Hub");

        javax.swing.GroupLayout titlePanelLayout = new javax.swing.GroupLayout(titlePanel);
        titlePanel.setLayout(titlePanelLayout);
        titlePanelLayout.setHorizontalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(parcelhubLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(titlePanelLayout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(selectLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        titlePanelLayout.setVerticalGroup(
            titlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, titlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(parcelhubLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(selectLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(titlePanel, java.awt.BorderLayout.NORTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        fileName = null;
        this.dispose();
    }//GEN-LAST:event_exitButtonActionPerformed

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        fileName = this.databaseList.getSelectedValue();
        this.dispose();
    }//GEN-LAST:event_openButtonActionPerformed

    private void newButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newButtonActionPerformed
        fileName = generateFileName();
        File f = new File(fileName);
        try {
            f.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(DatabaseSelector.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_newButtonActionPerformed

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
            java.util.logging.Logger.getLogger(DatabaseSelector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DatabaseSelector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DatabaseSelector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DatabaseSelector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DatabaseSelector().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel controlPanel;
    private javax.swing.JList<String> databaseList;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JButton exitButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton newButton;
    private javax.swing.JButton openButton;
    private javax.swing.JLabel parcelhubLabel;
    private javax.swing.JLabel selectLabel;
    private javax.swing.JPanel titlePanel;
    // End of variables declaration//GEN-END:variables
}
