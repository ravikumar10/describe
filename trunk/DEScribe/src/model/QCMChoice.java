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
 * Class Main.java
 * @description Multiple Choices Question's choice
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @version 2011-07-18
 */
public class QCMChoice {
    private String text;
    private Boolean isOtherChoice;

    /**
     * Constructor
     * @param t
     * @param iOC
     */
    public QCMChoice(String t, Boolean iOC){
        text=t;
        isOtherChoice=iOC;
    }

    /**
     * Gets choice to string
     * @return
     */
    public String getText(){
        return text;
    }

    /**
     * Checks if choice is of type "other" (will be followed by a textfield for
     * free answer)
     * @return
     */
    public Boolean getIsOtherChoice(){
        return isOtherChoice;
    }
}
