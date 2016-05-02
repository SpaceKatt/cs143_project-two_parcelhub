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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents a parcel that will be arriving and scanned at
 * our shipping hub. It encapsulated the requisite fields that need to
 * be known about each parcel and accessors/mutators to the necessary
 * fields.
 * 
 * Project: Parcel Hub
 * Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
 * Course: CS 143
 * Created on Apr 21, 2016, 1:47:31 PM
 * Revised on Arp 21, 2016, 2:45:11 PM
 * 
 * @author thomas.kercheval
 */
public class Parcel implements Comparable {
    /** The number of Parcels scanned by this hub. */
    //private static int numberOfParcels;
    
    /** The unique ID of the Parcel. */
    private final String parcelID;
    /** The name of the Parcel's intended recipient. */
    private String nameReciever;
    /** The street address the Parcel will be shipped to. */
    private String address;
    /** The city the Parcel will be shipped to. */
    private String city;
    /** The state the Parcel will be shipped to. */
    private String state;
    /** The zipcode the Parcel will be shipped to. */
    private String zip;
    /** The date the Parcel was scanned. */
    private final String date;
    
    /** 
     * Default constructor, generates ID and date scanner, 
     * sets all other fields to empty strings.
     */
    public Parcel() {
        this.date = generateDate();
        this.nameReciever = "";
        this.address = "";
        this.city = "";
        this.state = "";
        this.zip = "";
        this.parcelID = generateID();
    }
    
    /**
     * Overloaded constructor, takes all String arguments, 
     * generates ID and date scanner.
     * @param name The Parcels recipient's name.
     * @param address The Parcel's recipient's street address.
     * @param city The Parcel's recipient's city.
     * @param state The Parcel's recipient's State.
     * @param zip The Parcel's recipient's ZipCode.
     */
    public Parcel(String name, String address, String city, String state,
            String zip) {
        this.date = generateDate();
        this.nameReciever = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.parcelID = generateID();
    }
    
    /**
     * Generates the ParcelID by generating an MD5 hash from Parcel's
     * toString method.
     * @return Parcel ID MD5
     */
    private String generateID() {
        try {
            MessageDigest messageDigester = MessageDigest.getInstance("MD5");
            messageDigester.update(this.toString().getBytes());
            byte[] digest = messageDigester.digest();
            StringBuilder bufferID = new StringBuilder();
            for (byte b : digest) {
                bufferID.append(String.format("%02x", b & 0xff));
            }
            return bufferID.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Parcel.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        return null;
    }
    
    /**
     * Compares one Parcel to another one, by alphanumeric ordering of their
     * ID. Copying the return statement from the Java String API because I'm
     * using the String.compareTo() method in my return statement.
     * @param other Parcel to be compared
     * @return the value 0 if the argument string is equal to this string; 
     * a value less than 0 if this string is lexicographically less than the 
     * string argument; and a value greater than 0 if this string is 
     * lexicographically greater than the string argument.
     */
    @Override
    public int compareTo(Object other) {
        if (!(other instanceof Parcel)) {
            System.out.println("NOT A PARCEL!!! Cannot compareTo...");
            System.exit(0);
        }
        Parcel otherParcel = (Parcel) other;
        return this.parcelID.compareTo(otherParcel.parcelID);
    }

    /** @return The Parcel ID, which is an MD5 Hash. */
    public String getParcelID() {
        return parcelID;
    }

    /** @return The date our Parcel was created. */
    public String getDate() {
        return date;
    }
    
    /**
     * Generates the date and time that the Parcel was scanned by our hub.
     * @return The current date as a string `dd-MM-yy_HH-mm-SS`
     */
    private String generateDate() {
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:SS");
        String dateNow = dateFormat.format(today);
        return dateNow;
    }

    /** @return String representation of Parcel object. */
    @Override
    public String toString() {
        return "Parcel{" + "parcelID=" + parcelID + ", nameReciever=" 
                + nameReciever + ", address=" + address + ", city=" + city 
                + ", state=" + state + ", zip=" + zip + ", date=" + date + '}';
    }

    /**
     * Determines if two Parcel objects are equivalent.
     * @param other The other Parcel we are comparing to.
     * @return true if this Parcel equals the other Parcel.
     */
    public boolean equals(Parcel other) {
        return this.parcelID.equals(other.parcelID)
                && this.date.equals(other.date);
    }

    /** @return The Parcels recipient's name. */
    public String getNameReciever() {
        return nameReciever;
    }

    /** @param nameReciever The Parcels recipient's name. */
    public void setNameReciever(String nameReciever) {
        this.nameReciever = nameReciever;
    }

    /** @return The Parcel's recipient's street address. */
    public String getAddress() {
        return address;
    }

    /** @param address The Parcel's recipient's street address. */
    public void setAddress(String address) {
        this.address = address;
    }

    /** @return The Parcel's recipient's city.  */
    public String getCity() {
        return city;
    }

    /** @param city The Parcel's recipient's city. */
    public void setCity(String city) {
        this.city = city;
    }

    /** @return The Parcel's recipient's State. */
    public String getState() {
        return state;
    }

    /** @param state The Parcel's recipient's State. */
    public void setState(String state) {
        this.state = state;
    }

    /** @return The Parcel's recipient's ZipCode. */
    public String getZip() {
        return zip;
    }

    /** @param zip The Parcel's recipient's ZipCode. */
    public void setZip(String zip) {
        this.zip = zip;
    }
    
}
