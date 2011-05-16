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
 * Class Reponse.java
 * @description Answer
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @author Bertrand Gros    <gros.bertrand@gmail.com>
 * @version 2011-01-28
 */
public class Reponse {

    String intituleQuestion;

    String laReponse;

    Date instant;

    Session session;

    String screenshot;


     public Reponse(String q, String r, Date d){
        this.intituleQuestion = q;
        this.laReponse = r;
        this.instant = d;
    }


    public Reponse(String q, String r, Date d, Session s){
        this.intituleQuestion = q;
        this.laReponse = r;
        this.instant = d;
        this.session = s;
        this.screenshot="";
    }

    public Reponse(String q, String r, Date d, Session s, String screenCapture){
        this.intituleQuestion = q;
        this.laReponse = r;
        this.instant = d;
        this.session = s;
        this.screenshot=screenCapture;
    }

    public String getIntituleQuestion(){
        return intituleQuestion;
    }

     public String getLaReponse(){
        return laReponse;
    }

    public Date getInstant(){
        return instant;
    }

    public Session getSession(){
        return session;
    }

    public String getScreenshot() {
        return screenshot;
    }

}
