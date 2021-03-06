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
package parcelhub.objects;

import parcelhub.objects.Parcel;

/**
 *
 * @author thomas.kercheval
 */
public class ParcelTest {
    public static void main(String[] args) {
        Parcel parcel = new Parcel("Steve", "1123 Oak Nut St", "Seattle", 
                "WA", "98125");
        System.out.println(parcel.toString());
    }
}
