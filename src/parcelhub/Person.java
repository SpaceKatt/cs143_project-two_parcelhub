/*
 * Copyright (C) 2016 Thomas Kercehval
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

/**
 * This class represents a Person that a Parcel will be shipped to.
 * 
 * Project: Parcel Hub
 * Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
 * Course: CS 143
 * Created on Apr 21, 2016, 1:47:31 PM
 * Revised on Arp 21, 2016, 2:45:11 PM
 * 
 * @author thomas.kercheval
 */
public class Person {
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

    /** Constructor which takes all String arguments
     * 
     * @param nameReciever The Parcels recipient's name.
     * @param address The Parcel's recipient's street address.
     * @param city The Parcel's recipient's city.
     * @param state The Parcel's recipient's State.
     * @param zip The Parcel's recipient's ZipCode.
     */
    public Person(String nameReciever, String address, String city, 
            String state, String zip) {
        this.nameReciever = nameReciever;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
    
    /** Copy Constructor which copies the fields from another Person.
     * 
     * @param other The Person we intend to clone.
     */
    public Person(Person other) {
        this.nameReciever = other.nameReciever;
        this.address = other.address;
        this.city = other.city;
        this.state = other.state;
        this.zip = other.zip;
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
