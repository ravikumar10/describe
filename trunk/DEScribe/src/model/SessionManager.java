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

import api.dbc.DBConnexion;
import api.utils.getOs;
import api.xml.Utils;
import exceptions.BadXMLFileException;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class SessionManager.java
 * @description Handle sessions
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @version 2011-07-18
 */
public class SessionManager {

    /**
     * SessionManager unique instance
     */
    static protected SessionManager sm = null;

    /**
     * Loaded sessions
     */
    ArrayList<Session> lesSessions;

    /**
     * Current session
     */
    Session courante;

    /**
     * Constructor
     * @throws SQLException
     */
    public SessionManager() throws SQLException {

        lesSessions = new ArrayList<Session>();

        DBConnexion conn = DBConnexion.getConnexion();
        lesSessions = conn.getSessions();
        load();
    }

    /**
     * Gets SessionManager instance
     * @return
     * @throws SQLException
     */
    public static SessionManager getSessionManager() throws SQLException {
        if (sm == null) {
            sm = new SessionManager();
        }
        return sm;
    }

    /**
     * Load session
     */
    public void load() {
        //Default sessions if none created yet
        if (lesSessions.isEmpty()) {
            try {
                // Init new session
                String nom = "default";
                lesSessions.add(new Session(Long.parseLong("1"), new Date(), null, true, nom, null));
                courante = lesSessions.get(0);
                try {
                    //Create Sessions folder if doesn't already exist
                    new File(api.xml.Utils.loadSessionsFolder()).mkdir();
                    // Create new session folder if doesn't already exist
                    if (getOs.isWindows()) {
                        new File(api.xml.Utils.loadSessionsFolder() + "\\session" + courante.getId()).mkdir();
                    } else {
                        new File(api.xml.Utils.loadSessionsFolder() + "/session" + courante.getId()).mkdir();
                    }
                } catch (BadXMLFileException ex) {
                    Logger.getLogger(SessionManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                DBConnexion conn = DBConnexion.getConnexion();
                courante.setTimeToLive(api.xml.Utils.loadSessionsTimeToLive());
                courante.setQuestionsPerHour(api.xml.Utils.loadSessionsNbQuestionsPerHour());
                conn.addSession(courante);
                try {
                    Utils.setDefaultFormFile();
                } catch (BadXMLFileException ex) {
                    javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            } catch (BadXMLFileException ex) {
                Logger.getLogger(SessionManager.class.getName()).log(Level.SEVERE,null, ex);
            }
           


        } else {
            /**
             * Latest session's loading
             */
            courante = lesSessions.get(lesSessions.size() - 1);
        }
    }

    /**
     * Gets current session
     * @return
     */
    public Session getSessionCourante() {
        return courante;
    }

    /**
     * Creates a new session : instanciate object and creates a new session
     * in database. This new session becomes current session/
     * @return
     */
    public void createSession(String name) {
        try {
            DBConnexion conn = DBConnexion.getConnexion();
            Long newId = conn.getMaxIdSession();
            lesSessions.add(new Session(newId + 1, new Date(), null, true, name, null));
            courante = lesSessions.get(lesSessions.size() - 1);
            courante.setTimeToLive(api.xml.Utils.loadSessionsTimeToLive());
            courante.setQuestionsPerHour(api.xml.Utils.loadSessionsNbQuestionsPerHour());
            conn.addSession(courante);
            try {
                //Create Sessions folder if doesn't already exist
                new File(api.xml.Utils.loadSessionsFolder()).mkdir();
                // Create new session folder if doesn't already exist
                if (getOs.isWindows()) {
                    new File(api.xml.Utils.loadSessionsFolder() + "\\session" + courante.getId()).mkdir();
                } else {
                    new File(api.xml.Utils.loadSessionsFolder() + "/session" + courante.getId()).mkdir();
                }
            } catch (BadXMLFileException ex) {
                Logger.getLogger(SessionManager.class.getName()).log(Level.SEVERE, null, ex);
            }


        } catch (BadXMLFileException ex) {
            Logger.getLogger(SessionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets the list of sessions
     * @return
     */
    public ArrayList<Session> getLesSessions() {
        return lesSessions;
    }


    /**
     * Closes a session
     */
    public void closeSession(Session s){
        s.setActive(false);
        if (s.getFin()==null){
            s.setfin(new Date());
        }
        DBConnexion conn = DBConnexion.getConnexion();
        conn.updateSession(s);
    }

    /**
     * Deletes all closed sessions and their answers
     */
    public void cleanOldSessions(){
       DBConnexion conn = DBConnexion.getConnexion();
       for (Iterator<Session> it = lesSessions.iterator(); it.hasNext(); ) {
                Session s = it.next();
                if (!s.getActive()){
                    conn.deleteAnswersOfSession(s);
                    conn.deleteSession(s);
                    it.remove();
                }

            }
        lesSessions = conn.getSessions();

        //TODO : Also delete answers screenshots!
        }

    /**
     * Deletes a closed session and its answers
     */
    public void deleteOldSession(Session s){
                if (!s.getActive()){
                    DBConnexion conn = DBConnexion.getConnexion();
                    ArrayList<Reponse> aR=conn.getEntriesBySession(s);
                    if (aR != null){
                       for (int i=0;i<aR.size();i++){
                          aR.get(i).deleteReponse();
                       }
                    }
                    //conn.deleteAnswersOfSession(s);
                    conn.deleteSession(s);
                    lesSessions.remove(s);
                    lesSessions = conn.getSessions();
                }
    }

    /**
     * Gets number of answers of a session
     */
    public int getNumberOfAnswers(Session s){
                    DBConnexion conn = DBConnexion.getConnexion();
                    int nb=conn.getNbAnswers(s);
                    return nb;
    }

}
