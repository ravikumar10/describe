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

import java.util.Date;

/**
 * Class Session.java
 * @description A session is a set of questions and answer for a certain period
 * of time - once a session is closed, no more questions can be asked.
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @version 2011-07-18
 */
public class Session {

    /**
     * Session's id
     */
    long id;

    /**
     * Session's creation date
     */
    Date debut;

    /**
     * Session's closure date
     */
    Date fin;

    /**
     * Session's level of activity
     */
    Boolean active;

    /**
     * Session's name
     */
    String nom;

    /**
     * Session's state
     */
    boolean pause;

    /**
     * Session's last export's date
     */
    Date lastExport;

    /* Life time of the session in number of hours */
    /* For instance, 48 hours */
    int timeToLive;

    /**
     * Number of questions per hour
     */
    int questionsPerHour;


    /**
     * Constructor
     * @param id
     * @param d
     * @param f
     * @param a
     * @param n
     * @param e
     */
    public Session(Long id, Date d, Date f, Boolean a, String n, Date e){
        this.debut=d;
        this.fin=f;
        this.id=id;
        this.active=a;
        this.nom=n;
        this.lastExport=e;
    }

    /**
     * Gets session's id
     * @return
     */
    public long getId(){
        return id;
    }

    /**
     * Gets session's creation date
     * @return
     */
     public Date getDebut(){
        return debut;
    }

     /**
      * Gets session's closure date
      * @return
      */
     public Date getFin(){
        return fin;
    }

     /**
      * Gets session's name
      */
     public String getNom(){
         return nom;
     }

     /**
      * Gets session's level of activity
      * @return
      */
    public Boolean getActive() {
        return active;
    }

    /**
     * Sets session's level of activity
     * @param b true for active
     */
    public void setActive(boolean b) {
        active=b;
    }

    /**
     * Sets session's closure date
     * @param d
     */
    public void setfin(Date d) {
        fin=d;
    }

    /**
     *  Sets session's name
     * @param n
     */
    public void setnom(String n) {
        nom=n;
    }

    /**
     * Sets session in pause/resume session
     * @param b true for pause
     */
    public void setPause(boolean b) {
        pause=b;
    }

    /**
     * Checks if session is in pause
     * @return
     */
    public boolean getPause() {
        return pause;
    }

    /**
     * Sets last export date
     * @param d
     */
    public void setLastExport(Date d){
        lastExport=d;
    }

    /**
     * Gets last export date
     * @return
     */
    public Date getLastExport(){
        return lastExport;
    }

    /**
     * Sets session's time to live
     * @param ttl
     */
    public void setTimeToLive(int ttl){
        this.timeToLive=ttl;
    }

    /**
     * Gets session's time to live
     * @return
     */
    public int getTimeToLive(){
        return timeToLive;
    }

    /**
     * Sets number of questions per hour
     * @param nbQuestions
     */
    public void setQuestionsPerHour(int nbQuestions){
        this.questionsPerHour=nbQuestions;
    }

    /**
     * Gets number of questions per hour
     * @return
     */
    public int getQuestionsPerHour() {
        return questionsPerHour;
    }

    /**
     * Gets session's number of answers
     * @return
     */
    public int getNbReponses() {
        return 0;
    }
}
