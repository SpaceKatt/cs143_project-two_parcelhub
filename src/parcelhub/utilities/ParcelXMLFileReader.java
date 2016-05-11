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

import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import parcelhub.objects.Parcel;


/**
 * ParcelCSVFileReader.java
 * This class provides an abstraction to read our database one line at a time.
 * Using XML! For extra credit.
 * <pre>
    Project: Parcel Hub Database
    Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
    Course: CS 143
    Created on May 11, 2016, 2:08:43 PM
    Revised on May 10, 2016, PM
 </pre>
 * @author Thomas Kercheval
 */
public class ParcelXMLFileReader {
    /** The ArrayList of String arrays which will host each Parcel's info. */
    ArrayList<String[]> parcelInfo;
    /** The file path of the database we will read from. */
    String filePath;
    
    /**
     * Initializes the ArrayList in which we will store the arrays with each
     * Parcel's information and save the file name of the database (passed to
     * this constructor).
     * @param fileName The name of the file we are reading from.
     */
    public ParcelXMLFileReader(String fileName) {
        filePath = fileName;
        parcelInfo = new ArrayList<>();
        readFile();
    }
    
    /**
     * Reads the XML file `this.filePath` and creates an ArrayList of String
     * arrays, each containing the complete information of a Parcel.
     */
    private void readFile() {
        try {
            File database = new File(this.filePath);
            
            SAXBuilder builder = new SAXBuilder();

            Document document = builder.build(database);
            Element parcelsElement = document.getRootElement();
            List<Element> parcelList = parcelsElement.getChildren();
            for (int i = 0; i < parcelList.size(); i++) {
                Element parcel = parcelList.get(i);
                String[] info = new String[]{
                    parcel.getChildText("parcelID"),
                    parcel.getChildText("name"),                
                    parcel.getChildText("address"),
                    parcel.getChildText("city"),
                    parcel.getChildText("state"),
                    parcel.getChildText("zip"),
                    parcel.getChildText("date")
                };
                this.parcelInfo.add(info);
            }
        } catch(JDOMException | IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * @return The ArrayList with all of our Parcels information.
     */
    public ArrayList<String[]> getParcelInformation() {
        return this.parcelInfo;
    }
}