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

package api.dbc;

import model.Reponse;
import model.Session;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import api.utils.DateOutils;
import api.utils.getOs;
import java.util.StringTokenizer;
import model.Regle;

/**
 * Class DBConnexion.java
 * @description SQLite database management
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @author Bertrand Gros    <gros.bertrand@gmail.com>
  * @version 2011-01-28
 */
public class DBConnexion {

    /**
     * ATTRIBUTS
     */

    /**
     * instance du singleton
     */
    static protected DBConnexion instance = null;
    /**
     * élément de paramètres statiques
     */
    private static String nomDeLaBase = "jdbc:sqlite:des.db";


    private Connection conn;

    /**
     * CONSTRUCTEUR
     */
    public DBConnexion() {
        try {
            if (getOs.isWindows()) {
                nomDeLaBase = "jdbc:sqlite:data\\des.db";
            } else {
                nomDeLaBase = "jdbc:sqlite:data/des.db";
            }
            Class.forName("org.sqlite.JDBC");
            this.conn = DriverManager.getConnection(nomDeLaBase);
            Statement stat = conn.createStatement();
            // stat.executeUpdate("create table if not exists entries (question, reponse,instant);");
            stat.executeUpdate("create table if not exists entries (question,reponse,instant,session,screenshot,idreponse,regles);");
            stat.executeUpdate("create table if not exists session (idsession,datedebut,datefin,active,nom,dateexport,timetolive,questionsperhour);");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * METHODES PROPRES
     */

    /**
     * methode de récupération de singleton de connexion à la base
     * @return l'instance unique de connexion ou une erreurs
     */
    public static DBConnexion getConnexion() {
        if (instance == null) {
            instance = new DBConnexion();
        }
        return instance;
    }

    /**
     * METHODES A REFAIRE
     */
    public void resetBD() {
        try {
            Statement stat = conn.createStatement();
            stat.executeUpdate("drop table if exists entries;");
            //stat.executeUpdate("create table entries (question, reponse, instant);");
            stat.executeUpdate("drop table if exists session;");
            stat.executeUpdate("create table entries (question, reponse, instant,session,screenshot,idreponse,regles);");
            stat.executeUpdate("create table session (idsession,datedebut,datefin,active,nom,dateexport);"); //ajouter le nom
        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addEntry(String question, String reponse, String instant, String session) {
        try {
            PreparedStatement prep = conn.prepareStatement("insert into entries values (?, ?, ?, ?);");
            prep.setString(1, question);
            prep.setString(2, reponse);
            prep.setString(3, instant);
            prep.setString(4, session);
            prep.addBatch();
            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


        public ArrayList<Reponse> getEntriesNew() {
        ArrayList<Reponse> lesReponses = new ArrayList<Reponse>();

        try {
            Statement stat = null;
            try {
                stat = conn.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            }
            String s = "";
            ResultSet rs = stat.executeQuery("select * from entries;");

            while (rs.next()) {
                try {
                    lesReponses.add(new Reponse(Long.parseLong(rs.getString("idreponse")), rs.getString("question"), rs.getString("reponse"), DateOutils.stringToDate(rs.getString("instant")), getSessionById(Long.parseLong(rs.getString("session"))),rs.getString("screenshot")));
                } catch (ParseException ex) {
                    Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            rs.close();
            return lesReponses;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<Reponse> getEntries() {
        ArrayList<Reponse> lesReponses = new ArrayList<Reponse>();

        try {
            Statement stat = null;
            try {
                stat = conn.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            }
            String s = "";
            ResultSet rs = stat.executeQuery("select * from entries;");

            while (rs.next()) {
                try {
                    lesReponses.add(new Reponse(rs.getString("question"), rs.getString("reponse"), DateOutils.stringToDate(rs.getString("instant"))));
                } catch (ParseException ex) {
                    Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            rs.close();
            return lesReponses;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<Reponse> getEntriesBySession(Session se) {
        ArrayList<Reponse> lesReponses = new ArrayList<Reponse>();

        try {
            Statement stat = null;
            try {
                stat = conn.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            }
            String s = "";
            ResultSet rs = stat.executeQuery("select * from entries where session=\""+se.getId()+"\";");

            while (rs.next()) {
                try {
                    Reponse r1= new Reponse(Long.parseLong(rs.getString("idreponse")), rs.getString("question"), rs.getString("reponse"), DateOutils.stringToDate(rs.getString("instant")), rs.getString("screenshot"));
                    ArrayList<Regle> lesR=new ArrayList<Regle>();
                    String tokens[]=rs.getString("regles").split(", ");
                    for (String token : tokens)
                    {
                        String all=token;
                        String type=all.split(":")[0];
                        String event=all.split(":")[1];
                        lesR.add(new Regle(event, type));
                    }
                    r1.setReglesQuestion(lesR);
                    lesReponses.add(r1);
                } catch (ParseException ex) {
                    Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            rs.close();
            return lesReponses;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

        public ArrayList<Session> getSessions() {
        ArrayList<Session> lesSessions = new ArrayList<Session>();
        try {
            Statement stat = null;
            try {
                stat = conn.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            }
            ResultSet rs = stat.executeQuery("select * from session;");

            while (rs.next()) {
                try {
                    if(rs.getString("datefin").equals("null")){
                                            if(rs.getString("dateexport").equals("null")){
                                                Session s = new Session(Long.parseLong(rs.getString("idsession")), DateOutils.stringToDate(rs.getString("datedebut")), null, Boolean.parseBoolean(rs.getString("active")), rs.getString("nom"),null);
                                                s.setTimeToLive(Integer.parseInt(rs.getString("timetolive")));
                                                s.setQuestionsPerHour(Integer.parseInt(rs.getString("questionsperhour")));
                                                lesSessions.add(s);
                                            } else {
                                                Session s = new Session(Long.parseLong(rs.getString("idsession")), DateOutils.stringToDate(rs.getString("datedebut")), null, Boolean.parseBoolean(rs.getString("active")), rs.getString("nom"),DateOutils.stringToDate(rs.getString("dateexport")));
                                                s.setTimeToLive(Integer.parseInt(rs.getString("timetolive")));
                                                s.setQuestionsPerHour(Integer.parseInt(rs.getString("questionsperhour")));
                                                lesSessions.add(s);
                                            }
                    } else {
                                            if(rs.getString("dateexport").equals("null")){
                                                Session s = new Session(Long.parseLong(rs.getString("idsession")), DateOutils.stringToDate(rs.getString("datedebut")), DateOutils.stringToDate(rs.getString("datefin")), Boolean.parseBoolean(rs.getString("active")), rs.getString("nom"),null);
                                                s.setTimeToLive(Integer.parseInt(rs.getString("timetolive")));
                                                s.setQuestionsPerHour(Integer.parseInt(rs.getString("questionsperhour")));
                                                lesSessions.add(s);
                                            } else {
                                                Session s = new Session(Long.parseLong(rs.getString("idsession")), DateOutils.stringToDate(rs.getString("datedebut")), DateOutils.stringToDate(rs.getString("datefin")), Boolean.parseBoolean(rs.getString("active")), rs.getString("nom"),DateOutils.stringToDate(rs.getString("dateexport")));
                                                s.setTimeToLive(Integer.parseInt(rs.getString("timetolive")));
                                                s.setQuestionsPerHour(Integer.parseInt(rs.getString("questionsperhour")));
                                                lesSessions.add(s);
                                            }
                    }
                    } catch (ParseException ex) {
                    Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            rs.close();
            return lesSessions;

        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            return lesSessions;
        }
    }

   public Session getSessionById(long id) {
       Session s = null;
        try {
            Statement stat = null;
            try {
                stat = conn.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            }
            ResultSet rs = stat.executeQuery("select * from session where idsession=\""+id+"\";");

            while (rs.next()) {
                try {
                    if(rs.getString("datefin").equals("null")){
                        if(rs.getString("dateexport").equals("null")){
                                s = new Session(id, DateOutils.stringToDate(rs.getString("datedebut")), null, Boolean.parseBoolean(rs.getString("active")), rs.getString("nom"),null);
                                s.setTimeToLive(Integer.parseInt(rs.getString("timetolive")));
                                s.setQuestionsPerHour(Integer.parseInt(rs.getString("questionsperhour")));
                        } else {
                                s = new Session(id, DateOutils.stringToDate(rs.getString("datedebut")), null, Boolean.parseBoolean(rs.getString("active")), rs.getString("nom"),DateOutils.stringToDate(rs.getString("dateexport")));
                                s.setTimeToLive(Integer.parseInt(rs.getString("timetolive")));     
                                s.setQuestionsPerHour(Integer.parseInt(rs.getString("questionsperhour")));
                        }
                    } else {
                        if(rs.getString("dateexport").equals("null")){
                            s = new Session(id, DateOutils.stringToDate(rs.getString("datedebut")), DateOutils.stringToDate(rs.getString("datefin")), Boolean.parseBoolean(rs.getString("active")), rs.getString("nom"),null);
                            s.setTimeToLive(Integer.parseInt(rs.getString("timetolive")));
                            s.setQuestionsPerHour(Integer.parseInt(rs.getString("questionsperhour")));
                        } else {
                            s = new Session(id, DateOutils.stringToDate(rs.getString("datedebut")), DateOutils.stringToDate(rs.getString("datefin")), Boolean.parseBoolean(rs.getString("active")), rs.getString("nom"),DateOutils.stringToDate(rs.getString("dateexport")));
                            s.setTimeToLive(Integer.parseInt(rs.getString("timetolive")));
                            s.setQuestionsPerHour(Integer.parseInt(rs.getString("questionsperhour")));
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            rs.close();
            return s;

        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


   public String getEntriesString() {
        ArrayList<Reponse> lesReponses = new ArrayList<Reponse>();

        try {
            Statement stat = null;
            try {
                stat = conn.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            }
            String s = "";
            ResultSet rs = stat.executeQuery("select * from entries;");

            while (rs.next()) {
                    s=s+rs.getString("question")+" - "+rs.getString("reponse")+" - "+rs.getString("instant")+"\n";
            }
            rs.close();
            return s;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

      public String newgetEntriesString() {

        try {
            Statement stat = null;
            try {
                stat = conn.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            }
            String s = "";
            ResultSet rs = stat.executeQuery("select * from entries;");

            while (rs.next()) {
                    s=s+rs.getString("question")+" - "+rs.getString("reponse")+" - "+rs.getString("instant")+" - "+rs.getString("session")+"\n";
            }
            rs.close();
            return s;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

      public String getEntriesStringBySession(Session se) {

        try {
            Statement stat = null;
            try {
                stat = conn.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            }
            String s = "";
            ResultSet rs = stat.executeQuery("select * from entries where session=\""+se.getId()+"\";");

            while (rs.next()) {
                    //s=s+rs.getString("question")+" - "+rs.getString("reponse").replaceAll("\n", " ")+" - "+rs.getString("instant")+" - "+rs.getString("session")+"\n";
                    s=s+rs.getString("idreponse")+" - "+rs.getString("instant")+" - "+rs.getString("reponse").replaceAll("\n", " ")+"\n";
            }
            rs.close();
            return s;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

       public String getSessionsString() {

        try {
            Statement stat = null;
            try {
                stat = conn.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            }
            String s = "";
            ResultSet rs = stat.executeQuery("select * from session;");

            while (rs.next()) {
                    s=s+rs.getString("idsession")+" - "+rs.getString("nom")+" - "+rs.getString("datedebut")+" - "+rs.getString("datefin")+" - "+rs.getString("active")+" - "+rs.getString("dateexport")+"\n";
            }
            rs.close();
            return s;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }




    public void closeBDLink() {
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addEntry(Reponse rep) {
        try {
            PreparedStatement prep = conn.prepareStatement("insert into entries values (?, ?, ?);");
            prep.setString(1, rep.getIntituleQuestion());
            prep.setString(2, rep.getLaReponse());
            prep.setString(3, rep.getInstant().toString());
            //prep.setString(4, rep.getSession().toString());
            prep.addBatch();
            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void newAddEntry(Reponse rep) {
        try {
            PreparedStatement prep = conn.prepareStatement("insert into entries values (?, ?, ?, ?, ?, ?, ?);");
            prep.setString(1, rep.getIntituleQuestion());
            prep.setString(2, rep.getLaReponse());
            prep.setString(3, rep.getInstant().toString());
            prep.setString(4, ""+rep.getSession().getId());
            prep.setString(5, rep.getScreenshot());


            Long t = getMaxIdReponseBySession(rep.getSession())+1;
            prep.setString(6, ""+t);

            ArrayList<Regle> lesR=rep.getReglesQuestion();
            String strRegles="";
            for (int i=0; i<lesR.size();i++){
                if (i==0) {
                    strRegles+=lesR.get(i).getType()+":"+lesR.get(i).getEvent();
                } else if (i<=(lesR.size()-1)){
                    strRegles+=", "+lesR.get(i).getType()+":"+lesR.get(i).getEvent();
                }
            }
            prep.setString(7, strRegles);

            prep.addBatch();
            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addSession(Session s) {
        try {
            PreparedStatement prep = conn.prepareStatement("insert into session values (?, ?, ?, ?, ?, ?, ?, ?);");
            prep.setString(1, ""+s.getId());
            prep.setString(2, s.getDebut().toString());
            if (s.getFin()==null){
                prep.setString(3, "null");
            } else {
                prep.setString(3, s.getFin().toString());
            }
            prep.setString(4, s.getActive().toString());
            prep.setString(5, s.getNom());

            if (s.getLastExport()==null){
                prep.setString(6, "null");
            } else {
                prep.setString(6, s.getLastExport().toString());
            }

            prep.setString(7, ""+s.getTimeToLive());

            prep.setString(8, ""+s.getQuestionsPerHour());

            prep.addBatch();
            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Met a jour la session s dans la base
     * @param s la session
     */
    public void updateSession(Session s) {
        try {
            PreparedStatement prep = conn.prepareStatement("update session set datedebut=?, datefin=?, active=?, nom=?, dateexport=? where idsession =\""+s.getId()+"\";");

            prep.setString(1, s.getDebut().toString());
            if (s.getFin()==null){
                prep.setString(2, "null");
            } else {
                prep.setString(2, s.getFin().toString());
            }
            prep.setString(3, s.getActive().toString());
            prep.setString(4, s.getNom());
             if (s.getLastExport()==null){
                prep.setString(5, "null");
            } else {
                prep.setString(5, s.getLastExport().toString());
            }
            prep.addBatch();
            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Met a jour la session s dans la base
     * @param s la session
     */
    public void deleteOldSession() {
        try {
            PreparedStatement prep = conn.prepareStatement("delete from session where active=\"false\" ;");
            prep.addBatch();
            conn.setAutoCommit(false);
            prep.executeBatch();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Retourne le plus grand identifiant de session
     * @return
     */
    public Long getMaxIdSession() {
        Long idmax = Long.parseLong("0");

        try {
            Statement stat = null;
            try {
                stat = conn.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            }
            ResultSet rs0 = stat.executeQuery("select count(*) from session;");
            while (rs0.next()){
                if (Integer.parseInt(rs0.getString(1))==0){
                    return Long.parseLong("0");
                }
            }
            // Select max doesn't work apparently because it returns max in "string"
            ResultSet rs = stat.executeQuery("select distinct idsession from session;");

            while (rs.next()) {
                    if (Long.parseLong(rs.getString(1))>idmax) {
                        idmax=Long.parseLong(rs.getString(1));
                    }
            }
            rs.close();
            return idmax;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    /**
     * Retourne le plus grand identifiant de réponse pour une session
     * @return
     */
    public Long getMaxIdReponseBySession(Session s) {
        Long idmax = Long.parseLong("0");

        try {
            Statement stat = null;
            try {
                stat = conn.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            }
             ResultSet rs0 = stat.executeQuery("select count(*) from entries where session=\""+s.getId()+"\";");
            while (rs0.next()){
                if (Integer.parseInt(rs0.getString(1))==0){
                    return Long.parseLong("0");
                }
            }
            ResultSet rs = stat.executeQuery("select distinct idreponse from entries where session=\""+s.getId()+"\";");

            while (rs.next()) {
                    if (Long.parseLong(rs.getString(1))>idmax) {
                        idmax=Long.parseLong(rs.getString(1));
                    }
            }
            rs.close();
            return idmax;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }
     /**
     * Met a jour la session s dans la base
     * @param s la session
     */
    public void deleteSession(Session s) {
        try {
            PreparedStatement prep = conn.prepareStatement("delete from session where idsession =\""+s.getId()+"\";");
            conn.setAutoCommit(false);
            prep.executeUpdate();
            conn.setAutoCommit(true);

        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void deleteAnswersOfSession(Session s) {
        try {
            PreparedStatement prep = conn.prepareStatement("delete from entries where session =\""+s.getId()+"\";");
            prep.addBatch();
            conn.setAutoCommit(false);
            prep.executeUpdate();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void deleteAnwser(Reponse r) {
        try {
            PreparedStatement prep = conn.prepareStatement("delete from entries where session =\""+r.getSession().getId()+"\" and idreponse=\""+r.getId()+"\";");
            prep.addBatch();
            conn.setAutoCommit(false);
            prep.executeUpdate();
            conn.setAutoCommit(true);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Number of answers of a session
     * @return
     */
    public int getNbAnswers(Session s) {
        int nb=0;

        try {
            Statement stat = null;
            try {
                stat = conn.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            }
             ResultSet rs0 = stat.executeQuery("select count(*) from entries where session=\""+s.getId()+"\";");
            while (rs0.next()){
                nb=Integer.parseInt(rs0.getString(1));
            }
            rs0.close();
            return nb;
        } catch (SQLException ex) {
            Logger.getLogger(DBConnexion.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

}
