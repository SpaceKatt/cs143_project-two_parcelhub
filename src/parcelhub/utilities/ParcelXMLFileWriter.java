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
package parcelhub.utilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import parcelhub.objects.Parcel;


/**
 * ParcelCSVFileWriter.java
 * This class provides an abstraction to write our database one line at a time.
 * Using XML! For extra credit.
 * <pre>
    Project: Parcel Hub Database
    Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
    Course: CS 143
    Created on May 10, 2016, 2:09:33 PM
    Revised on May 10, 2016, PM
 </pre>
 * @author Thomas Kercheval
 */
public class ParcelXMLFileWriter {
    /** The file name of our database */
    private String fileName;
    /** ArrayList of Parcel Objects which we are writing to our database. */
    private ArrayList<Parcel> parcels;
    
    /**
     * Sets the list of Parcels we wish to save and the location at which we
     * will save our database.
     * @param parcelList The list of Parcels we will write.
     * @param filePath The path of the database we wish to write.
     */
    public ParcelXMLFileWriter(ArrayList<Parcel> parcelList, String filePath) {
        this.fileName = filePath;
        this.parcels = new ArrayList(parcelList);
    }
    
    /**
     * Creates the XML file which stores our parcel information.
     */
    public void createXMLFile() {
        Element root = new Element("Parcels");
        Document xmlDatabase = new Document();
        
        for (int i = 0; i < parcels.size(); i++) {
            Parcel parcelObj = parcels.get(i);
            Element parcelXML = new Element("Parcel");
            parcelXML.addContent(new Element("parcelID").addContent(
                    parcelObj.getParcelID()));
            parcelXML.addContent(new Element("name").addContent(
                    parcelObj.getNameReciever()));
            parcelXML.addContent(new Element("address").addContent(
                    parcelObj.getAddress()));
            parcelXML.addContent(new Element("date").addContent(
                    parcelObj.getDate()));
            parcelXML.addContent(new Element("city").addContent(
                    parcelObj.getCity()));
            parcelXML.addContent(new Element("state").addContent(
                    parcelObj.getState()));
            parcelXML.addContent(new Element("zip").addContent(
                    parcelObj.getZip()));
            root.addContent(parcelXML);
        }
        
        xmlDatabase.setRootElement(root);
        XMLOutputter outter = new XMLOutputter();
        outter.setFormat(Format.getPrettyFormat());
        try {
            outter.output(xmlDatabase, new FileWriter(new File(fileName)));
        } catch (IOException ex) {
            Logger.getLogger(ParcelXMLFileWriter.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }
}
