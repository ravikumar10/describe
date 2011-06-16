/**
 *
    DEScribe - A Discrete Experience Sampling cross platform application
    Copyright (C) 2011
    Sébastien Faure <sebastien.faure3@gmail.com>,
    Bertrand Gros   <gros.bertrand@gmail.com>,
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
 * @author Bertrand Gros    <gros.bertrand@gmail.com>
 * @version 2011-01-28
 */
public class Session {
    long id;
    Date debut;
    Date fin;
    Boolean active;
    String nom;
    boolean pause;
    Date lastExport;

    /* Life time of the session in number of hours */
    /* For instance, 48 hours */
    int timeToLive;


    public Session(Long id, Date d, Date f, Boolean a, String n, Date e){
        this.debut=d;
        this.fin=f;
        this.id=id;
        this.active=a;
        this.nom=n;
        this.lastExport=e;
    }

    public long getId(){
        return id;
    }

     public Date getDebut(){
        return debut;
    }

     public Date getFin(){
        return fin;
    }

     public String getNom(){
         return nom;
     }

    public Boolean getActive() {
        return active;
    }

    public void setActive(boolean b) {
        active=b;
    }

    public void setfin(Date d) {
        fin=d;
    }

    public void setnom(String n) {
        nom=n;
    }

    public void setPause(boolean b) {
        pause=b;
    }

    public boolean getPause() {
        return pause;
    }

    public void setLastExport(Date d){
        lastExport=d;
    }

    public Date getLastExport(){
        return lastExport;
    }

    public void setTimeToLive(int ttl){
        this.timeToLive=ttl;
    }

    public int getTimeToLive(){
        return timeToLive;
    }
}
