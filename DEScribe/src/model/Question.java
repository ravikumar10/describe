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

import java.util.ArrayList;

/**
 * Class Question.java
 * @description Question
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @version 2011-07-18
 */
public class Question {

    /**
     * Question id
     */
    long id;

    /**
     * Question's text
     */
    public String intitule;

    /**
     * Rules of the question
     */
    private ArrayList<Regle> lesRegles;

    /**
     * Constructeur
     * @param enonce
     */
    public Question(String enonce){
        this.intitule=enonce;
        lesRegles=new ArrayList<Regle>();
    }

    /**
     * Adds a rule to the question
     * @param r
     */
    public void addRegle(Regle r){
        lesRegles.add(r);
    }

    /**
     * Sets the lists of rules of the question
     * @param lesR
     */
    public void setRegles(ArrayList<Regle> lesR){
        lesRegles=lesR;
    }

    /**
     * Gets the list of rules of the question
     * @return
     */
    public ArrayList<Regle> getRegles(){
        return lesRegles;
    }

}
