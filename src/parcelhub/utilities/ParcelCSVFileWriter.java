/*
 * Copyright (C) 2016 thomas.kercheval
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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import parcelhub.Parcel;
import static parcelhub.utilities.SortingAlgorithms.insertionSort;

/**
 * ParcelCSVFileWriter.java
 * This class provides an abstraction to write our database one line at a time.
 * <pre>
    Project: CafeDansa Database
    Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
    Course: CS 143
    Created on May 5, 2016, 2:11:46 PM
    Revised on May 5, 2016, 7:13:53 PM
 </pre>
 * @author Thomas Kercheval
 */
public class ParcelCSVFileWriter {
    /** File path (relative) to our database. */
    private final String filePath;

    /** List of lines to be written. */
    private final ArrayList<String> lines;

    /**
     * Constructor which stores the relative path of our database and
     * creates the lines to be written in our filePath.
     * @param file Relative filePath path of Parcel database
     * @param parcels ArrayList of parcels to be written
     */
    public ParcelCSVFileWriter(String file, ArrayList<Parcel> parcels) {
        this.filePath = file;
        this.lines = createLines(parcels);
    }

    /**
     * Creates the lines to be written by this class.
     * Takes each parcel and creates a line to be written in our database.
     * @param parcels ArrayList of our Parcel objects that need to be written
     * @return ArrayList populated by the parcels and their information.
     */
    private ArrayList<String> createLines(ArrayList<Parcel> parcels) {
        ArrayList<Parcel> sortParcel = new ArrayList<>(parcels);
        insertionSort(sortParcel);
        ArrayList<String> newLines = new ArrayList<>();
        for (int i = 0; i < parcels.size(); i++) {
            Parcel parcel = sortParcel.get(i);
            String line = parcel.getParcelID() + ",";
            line += parcel.getNameReciever()+ ",";
            line += parcel.getAddress() + ",";
            line += parcel.getCity() + ",";
            line += parcel.getState() + ",";
            line += parcel.getZip() + ",";
            line += parcel.getDate();
            newLines.add(line);
        }
        return newLines;
    }

    /**
     * Writes all the lines created by createLines() into a filePath specified
     * by this.filePath
     * @see createLines
     * @see java.nio.file.Files
     * @see java.nio.file.Path
     * @see java.nio.file.Paths
     */
    public void writeTheFile() {
        Path filePath = Paths.get(this.filePath);
        try {
            Files.write(filePath, lines, Charset.forName("UTF-8"));
        } catch (IOException ex) {
            Logger.getLogger(ParcelCSVFileWriter.class.getName()).log(Level.SEVERE,
                                                                 null, ex);
        }
    }
}
