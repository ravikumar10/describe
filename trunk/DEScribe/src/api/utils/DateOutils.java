/**
 *
    DEScribe - A Descriptive Experience Sampling cross platform application
    Copyright (C) 2011
    Sébastien Faure <sebastien.faure3@gmail.com>,
    Amaury Belin    <amaury.belin@gmail.com>,
    Yannick Prie    <yannick.prie@univ-lyon1.fr>.

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package api.utils;
import java.util.*;
import java.text.*;

/**
 * Class DateOutils.java
 * @description Date tools
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @version 2011-07-18
 */
public class DateOutils {
    /**
     * Converts string to Date format
     * @param d
     * @return
     * @throws ParseException
     */
   public static Date stringToDate(String d) throws ParseException{
        DateFormat formatter = new SimpleDateFormat( "EEE MMM d HH:mm:ss z yyyy", Locale.UK );
        Date date = (Date)formatter.parse(d);
        return date;
    }

   /**
    * Determinates the number of hours between two dates
    * @param d1
    * @param d2
    * @return nb of hours (int)
    */
   public static int nbHoursBetweenTwoDates(Date d1, Date d2){
       return (int) ((d2.getTime()-d1.getTime())/3600000);
   }

}