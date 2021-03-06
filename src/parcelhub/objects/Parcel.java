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
package parcelhub.objects;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import parcelhub.ParcelHubGUI;

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
 * Revised on May 8, 2016, 1:51:11 PM
 * 
 * @author thomas.kercheval
 */
public class Parcel implements Comparable<Parcel> {
    /** The unique ID of the Parcel. */
    private final String parcelID;
    /** The Person the Parcel will be sent to. */
    private final Person person;
    /** The date the Parcel was scanned. */
    private final String date;
    
    /** 
     * Default constructor, generates ID and date scanner, 
     * sets all other fields to empty strings.
     */
    public Parcel() {
        this.date = generateDate();
        this.person = new Person("", "", "", "", "");
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
        this.person = new Person(name, address, city, state, zip);
        this.parcelID = generateID();
    }
    
    /**
     * Overloaded constructor, takes a Person object, 
     * generates ID and date scanner.
     * @param parcelReciever The person whom shall receive the Parcel.
     */
    public Parcel(Person parcelReciever) {
        this.date = generateDate();
        this.person = new Person(parcelReciever);
        this.parcelID = generateID();
    }
    
    /**
     * Overloaded constructor, takes a String array which is generated
     * while reading Parcels from the database. All fields are already
     * known so there is no need for generation of ID or Date.
     * @param parcelInfo All necessary fields needed to construct a Parcel
     * are stored in this array. Indices are stored as constants in 
     * ParcelHubGUI.
     */
    public Parcel(String[] parcelInfo) {
        this.date = parcelInfo[ParcelHubGUI.DATE_INDEX];
        this.person = new Person(parcelInfo[ParcelHubGUI.NAME_INDEX], 
                parcelInfo[ParcelHubGUI.ADDRESS_INDEX], 
                parcelInfo[ParcelHubGUI.CITY_INDEX], 
                parcelInfo[ParcelHubGUI.STATE_INDEX],
                parcelInfo[ParcelHubGUI.ZIP_INDEX]);
        this.parcelID = parcelInfo[ParcelHubGUI.ID_INDEX];
    }
    
    /**
     * Clone constructor, creates a clone of the Parcel supplied to the
     * constructor.
     * @param parcel The Parcel we wish to clone.
     */
    public Parcel(Parcel parcel) {
        this.date = parcel.date;
        this.person = new Person(parcel.person);
        this.parcelID = parcel.parcelID;
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
    public int compareTo(Parcel other) {
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMMM/yy HH:mm:ss");
        String dateNow = dateFormat.format(today);
        return dateNow;
    }

    /** @return String representation of Parcel object. */
    @Override
    public String toString() {
        return "Parcel{" + "parcelID=" + parcelID + ", nameReciever=" 
                + person.getNameReciever() + ", address=" + person.getAddress() 
                + ", city=" + person.getCity() + ", state=" + person.getState()
                + ", zip=" + person.getZip() + ", date=" + date + '}';
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
        return person.getNameReciever();
    }

    /** @param nameReciever The Parcels recipient's name. */
    public void setNameReciever(String nameReciever) {
        person.setNameReciever(nameReciever);
    }

    /** @return The Parcel's recipient's street address. */
    public String getAddress() {
        return person.getAddress();
    }

    /** @param address The Parcel's recipient's street address. */
    public void setAddress(String address) {
        person.setAddress(address);
    }

    /** @return The Parcel's recipient's city.  */
    public String getCity() {
        return person.getCity();
    }

    /** @param city The Parcel's recipient's city. */
    public void setCity(String city) {
        person.setCity(city);
    }

    /** @return The Parcel's recipient's State. */
    public String getState() {
        return person.getState();
    }

    /** @param state The Parcel's recipient's State. */
    public void setState(String state) {
        person.setState(state);
    }

    /** @return The Parcel's recipient's ZipCode. */
    public String getZip() {
        return person.getZip();
    }

    /** @param zip The Parcel's recipient's ZipCode. */
    public void setZip(String zip) {
        person.setZip(zip);
    }
    
    /** @return The Person who this Parcel will be shipped to. */
    public Person getPerson() {
        return this.person;
    }
}
