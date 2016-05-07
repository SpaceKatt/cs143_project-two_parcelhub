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

import java.util.ArrayList;
import parcelhub.Parcel;


/**
 * This class provides methods by which we will search through ArrayLists
 * of Parcel objects. All methods in this class should be static.
 * 
 * Project: Parcel Hub
 * Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
 * Course: CS 143
 * Created on Apr 28, 2016, 2:03:54 PM
 * Revised on Arp 21, 2016,  PM
 * 
 * @author thomas.kercheval
 */
public class SearchingAlgorithms {
    
    /**
     * This method implements Binary Search to look for an exact match to
     * a parcel ID supplied by the user.
     * @param list The list of Parcel Objects we wish to look through
     * @param key The ID we are looking for
     * @return The index where our key is found, or -1 if it is not.
     */
    public static int binarySearch(ArrayList<Parcel> list, String key) {
        int low = 0;
        int high = list.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (list.get(mid).getParcelID().equals(key)) {
                return mid;
            } else if (list.get(mid).getParcelID().compareTo(key) > 0) {
                high = mid - 1;
            } else if (list.get(mid).getParcelID().compareTo(key) < 0) {
                low = mid + 1;
            }
        }
        return -1;
    }
    
    /**
     * This method implements Linear Search to look for a match which contains
     * a parcel ID supplied by the user.
     * @param list The list of Parcel Objects we wish to look through
     * @param key The ID we are looking for
     * @return The index where our key is found, or -1 if it is not.
     */
    public static int linearSearch(ArrayList<Parcel> list, String key) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getParcelID().contains(key)) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Returns the index of every instance where a ZipCode is found.
     * @param list The list we wish to search through.
     * @param zip The zipcode we are searching for
     * @return the index of every instance where a ZipCode is found.
     */
    public static ArrayList<Integer> linearZipSearch(ArrayList<Parcel> list, 
            String zip) {
        ArrayList<Integer> results = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getZip().equalsIgnoreCase(zip)) {
                results.add(i);
            } else if (list.get(i).getZip().
                    toLowerCase().contains(zip.toLowerCase())) {
                results.add(i);
            }
        }
        return results;
    }
    
    /**
     * Returns the index of every instance where a Name is found.
     * @param list The list we wish to search through.
     * @param name The Name we are searching for
     * @return the index of every instance where the Name is found.
     */
    public static ArrayList<Integer> linearNameSearch(ArrayList<Parcel> list, 
            String name) {
        ArrayList<Integer> results = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getNameReciever().equalsIgnoreCase(name)) {
                results.add(i);
            } else if (list.get(i).getNameReciever().
                    toLowerCase().contains(name.toLowerCase())) {
                results.add(i);
            }
        }
        return results;
    }
}
