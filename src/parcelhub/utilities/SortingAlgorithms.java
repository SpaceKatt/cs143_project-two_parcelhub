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
import parcelhub.objects.Parcel;

/**
 * This class contains several algorithms that we will be using to sort
 * ArrayLists which contain Parcel Objects. We need to sort our objects in
 * several different ways, using similar code in each algorithm. So, 
 * implementing all of them in one class seems to be the best option at this
 * time. All methods here should be static in nature.
 * 
 * Project: Parcel Hub
 * Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
 * Course: CS 143
 * Created on Apr 28, 2016, 1:08:43 PM
 * Revised on Arp 28, 2016, 2:01:34 PM
 * 
 * @author thomas.kercheval
 */
public class SortingAlgorithms {
    /**
     * This method swaps two specified elements of an ArrayList.
     * @param list ArrayList of Comparable objects
     * @param indexOne Index of first item we want to swap
     * @param indexTwo Index of second item we want to swap
     */
    public static void swap(ArrayList<Parcel> list, int indexOne, 
                            int indexTwo) {
        Parcel temp = list.get(indexOne);
        list.set(indexOne, list.get(indexTwo));
        list.set(indexTwo, temp);
    }
    
    /**
     * Compares object one to comparable object two and determines if 
     * object one is lesser in the order of the sort. Compares Parcel
     * objects by ID only.
     * @param one Object to compare to two, is this object less?
     * @param two Object to compare to one.
     * @return true if one is less than two in their ordering.
     */
    public static boolean less(Parcel one, Parcel two) {
        return one.compareTo(two) < 0;
    }
    
    /**
     * Compares object one to comparable object two and determines if 
     * object one is lesser in the order of the sort. Compares Parcel
     * objects by Zipcode, and then by ID if ZipCodes are equal.
     * @param one Object to compare to two, is this object less?
     * @param two Object to compare to one.
     * @return true if one is less than two in their ordering.
     */
    public static boolean lessZip(Parcel one, Parcel two) {
        if (one instanceof Parcel && two instanceof Parcel) {
            Parcel oneParcel = (Parcel) one;
            Parcel twoParcel = (Parcel) two;
            if (!oneParcel.getZip().equals(twoParcel.getZip())) {
                return oneParcel.getZip().compareTo(twoParcel.getZip()) < 0;
            } else {
                return one.compareTo(two) < 0;
            }
        } else {
            System.out.println("Error sorting, objects not Parcel...");
            System.exit(0);
        }
        return false;
    }
    
   /**
     * Compares object one to comparable object two and determines if 
     * object one is lesser in the order of the sort. Compares Parcel
     * objects by Zipcode, and then by ID if ZipCodes are equal.
     * @param one Object to compare to two, is this object less?
     * @param two Object to compare to one.
     * @return true if one is less than two in their ordering.
     */
    public static boolean lessName(Parcel one, Parcel two) {
        if (one instanceof Parcel && two instanceof Parcel) {
            Parcel oneParcel = (Parcel) one;
            Parcel twoParcel = (Parcel) two;
            if (!oneParcel.getNameReciever().equals(twoParcel.getNameReciever())) {
                return oneParcel.getNameReciever().compareTo(twoParcel.getNameReciever()) < 0;
            } else {
                return one.compareTo(two) < 0;
            }
        } else {
            System.out.println("Error sorting, objects not Parcel...");
            System.exit(0);
        }
        return false;
    }
    
        /**
     * Compares object one to comparable object two and determines if 
     * object one is lesser in the order of the sort. Compares Parcel
     * objects by date, and then by ID if Dates are equal.
     * @param one Object to compare to two, is this object less?
     * @param two Object to compare to one.
     * @return true if one is less than two in their ordering.
     */
    public static boolean lessDate(Parcel one, Parcel two) {
        if (one instanceof Parcel && two instanceof Parcel) {
            Parcel oneParcel = (Parcel) one;
            Parcel twoParcel = (Parcel) two;
            if (!oneParcel.getDate().equals(oneParcel.getDate())) {
                return oneParcel.getDate().compareTo(oneParcel.getDate()) < 0;
            } else {
                return one.compareTo(two) < 0;
            }
        } else {
            System.out.println("Error sorting, objects not Parcel...");
            System.exit(0);
        }
        return false;
    }
    
    /**
     * Implements a simple insertion sort to sort an ArrayList of Comparable
     * objects. Sort by Parcel ID.
     * @param list The ArrayList we want to sort.
     */
    public static void insertionSort(ArrayList<Parcel> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i; j > 0 && less(list.get(j), list.get(j - 1)); j--) {
                swap(list, j, j - 1);
            }
        }
    }
    
    
    
    /**
     * Implements a simple insertion sort to sort an ArrayList of Comparable
     * objects. Sort by Parcel ZIP, then collisions by ID.
     * @param list The ArrayList we want to sort.
     */
    public static void insertionSortByZip(ArrayList<Parcel> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i; j > 0 && lessZip(list.get(j), 
                    list.get(j - 1)); j--) {
                swap(list, j, j - 1);
            }
        }
    }
    
    /**
     * Implements a simple insertion sort to sort an ArrayList of Comparable
     * objects. Sort by Parcel Name, then collisions by ID.
     * @param list The ArrayList we want to sort.
     */
    public static void insertionSortByName(ArrayList<Parcel> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i; j > 0 && lessName(list.get(j), 
                    list.get(j - 1)); j--) {
                swap(list, j, j - 1);
            }
        }
    }
    
        /**
     * Implements a simple insertion sort to sort an ArrayList of Comparable
     * objects. Sort by Parcel ZIP, then collisions by ID.
     * @param list The ArrayList we want to sort.
     */
    public static void insertionSortByDate(ArrayList<Parcel> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i; j > 0 && lessDate(list.get(j), 
                    list.get(j - 1)); j--) {
                swap(list, j, j - 1);
            }
        }
    }
    
    /**
     * Implements a simple selection sort to sort an ArrayList of Comparable
     * objects. Sort by Parcel ID.
     * @param list The ArrayList we want to sort.
     */
    public static void selectionSort(ArrayList<Parcel> list) {
        for (int i = 0; i < list.size(); i++) {
            int minIndex = i;
            for (int j = i; j < list.size(); j++) {
                if (less(list.get(j), list.get(i))) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                swap(list, i, minIndex);
            }
        }
    }
}
