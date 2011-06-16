/**
 *
    DEScribe - A Descriptive Experience Sampling cross platform application
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

import api.dbc.DBConnexion;
import api.utils.getOs;
import api.xml.Utils;
import exceptions.BadXMLFileException;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 * Class SessionManager.java
 * @description Handle sessions
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @author Bertrand Gros    <gros.bertrand@gmail.com>
 * @version 2011-01-28
 */
public class SessionManager {

    /**
     * Instance unique du SessionManager
     */
    static protected SessionManager sm = null;
    /**
     * Les sessions chargées depuis la base
     */
    ArrayList<Session> lesSessions;
    /**
     * La session courante
     */
    Session courante;

    /**
     * Constructeur
     * @throws SQLException
     */
    public SessionManager() throws SQLException {

        lesSessions = new ArrayList<Session>();

        DBConnexion conn = DBConnexion.getConnexion();
        lesSessions = conn.getSessions();
        load();
    }

    /**
     * Permet d'instancier ou d'obtenir l'unique instance existante du SessionManager selon le cas
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
     * Chargement d'une session par défaut (la dernière) :
     * à utiliser au lancement auto de l'application
     */
    public void load() {
        //Lancement de la session par défaut
        if (lesSessions.isEmpty()) {
            try {
                // Initialisation de la nouvelle session
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
             * Chargement de la session la plus récente.
             */
            courante = lesSessions.get(lesSessions.size() - 1);
        }
    }

    /**
     * Pour charger une session en particulier
     * @param idSession
     */
    public void load(long idSession) {
        //Lancement de la session
    }

    /**
     * Pour obtenir la session courante
     * @return
     */
    public Session getSessionCourante() {
        return courante;
    }

    /**
     * Pour créer une nouvelle session : objet puis enregistrement dans la base
     * Cette session devient la session courante
     * A coder
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
     * Pour obtenir la liste des sessions manipulées par le SessionManager
     * @return
     */
    public ArrayList<Session> getLesSessions() {
        return lesSessions;
    }


    /**
     * Pour clore une session
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
     * Pour supprimer les sessions closes et leurs réponses
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
        }

    /**
     * Pour supprimer une session close
     */
    public void deleteOldSession(Session s){
                if (!s.getActive()){
                    DBConnexion conn = DBConnexion.getConnexion();
                    conn.deleteAnswersOfSession(s);
                    conn.deleteSession(s);
                    lesSessions.remove(s);
                    lesSessions = conn.getSessions();
                }
        }


    //SEssion manager charge liste sessions
}
