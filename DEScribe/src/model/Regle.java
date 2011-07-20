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


package model;

/**
 * Class Regle.java
 * @description Rule (condition of asking question)
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @version 2011-07-18
 */
public class Regle {

    /**
     * Name of event : fullscreen, copy, copyImage, copyText, copyFile...
     */
    String event;

    /**
     * Type of rule : if, notif
     */
    String type;

    /**
     * Constructor
     * @param e
     * @param t
     */
    public Regle(String e, String t){
        event=e;
        type=t;
    }

    /**
     * Gets event
     * @return
     */
    public String getEvent(){
        return event;
    }

    /**
     * Gets type
     * @return
     */
    public String getType(){
        return type;
    }
}
