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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This GUI runs our database for the Shipping Hub. It facilitates the
 * reading/writing to external files, display of Parcels received, and sorting
 * of Parcels by state, ID, zipcode, and search capacity by many different
 * means.
 *
 * Project: Parcel Hub Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
 * Course: CS 143 
 * Created on May 5, 2016, 1:08:21 PM 
 * Revised on May 2, 2016,
 *
 * @author thomas.kercheval
 */
public class Validation {
    /** Regex expression for email validation. */
    public final static String EMAIL_PATTERN = 
            "^\\s*[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+\\s*$";
    /** Regex expression for phone number validation. */
    public final static String PHONE_PATTERN = 
            "\\D{0,1}([0-9]\\d{2})(\\D*)([0-9]\\d{2})(\\D*)(\\d{4})";
    /** Regex expression for name validation. */
    public final static String NAME_PATTERN = "^\\s*([A-Z][a-z]+\\s*){2,3}$";
        /** Regex expression for name validation. */
    public final static String CITY_PATTERN = "^\\s*([A-Z][a-z]+\\s*){1,3}$";
    /** Regex expression for fileName validation */
    public final static String FILE_PATTERN = "^[^*&%\\s/;\'\"<>]+$";
    /** Regex expression for street address validation. */
    public final static String ADDRESS_PATTERN = 
            "\\w+(\\s\\w+){2,}";
    /** Regex expression for Zip Code validation. */
    public final static String ZIP_PATTERN = "^\\d{5}(?:[-\\s]\\d{4})?$";
    
    /**
     * Check to see if input is a double type.
     * @param field The item we are validating as a double.
     * @return true if input is a double
     */
    public static boolean isDouble(String field) {
        Pattern pat = Pattern.compile("\\d+(\\.\\d+)?");
        Matcher mat = pat.matcher(field);
        return mat.matches();
    }
    
    /**
     * Check to see if input is a valid phone number.
     * @param field The item we are validating as a phone number.
     * @return true if input is a valid phone number
     */
    public static boolean isPhone(String field) {
        String noLetters = "[^A-Za-z]+";
        return field.matches(PHONE_PATTERN) && field.matches(noLetters);
    }
    
    /**
     * Check to see if input is a valid Name.
     * @param field The item we are validating as a Name.
     * @return true if input is a valid Name.
     */
    public static boolean isName(String field) {
        return field.matches(NAME_PATTERN);
    }
    
    /**
     * Check to see if input is a valid ZipCode.
     * @param field The item we are validating as a ZipCode.
     * @return true if input is a valid ZipCode.
     */
    public static boolean isZip(String field) {
        return field.matches(ZIP_PATTERN);
    }
    
    /**
     * Check to see if input is a valid City.
     * @param field The item we are validating as a City.
     * @return true if input is a valid City.
     */
    public static boolean isCity(String field) {
        return field.matches(CITY_PATTERN);
    }

    /**
     * Check to see if input is a valid File Name.
     * @param field The item we are validating as a fileName.
     * @return true if input is a valid fileName.
     */
    public static boolean isFileName(String field) {
        return field.matches(FILE_PATTERN);
    }
    
    /**
     * Check to see if input is a valid Email Address.
     * @param field The item we are validating as a Email Address.
     * @return true if input is a valid Email Address.
     */
    public static boolean isEmail(String field) {
        return field.matches(EMAIL_PATTERN);
    }
    
    /**
     * Check to see if input is a valid Address.
     * @param field The item we are validating as a Address.
     * @return true if input is a valid Address.
     */
    public static boolean isAddress(String field) {
        return field.matches(ADDRESS_PATTERN);
    }
}
