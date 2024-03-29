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

package api.time;

import api.gui.AskFrame;
import api.gui.OptionFrame;
import api.gui.SessionFrame;
import api.i18n.Lang;
import api.utils.FullScreenDetector;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.SessionManager;

/**
 * Class TimerQuestion.java
 * @description Timer asking questions to user aperiodicly
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @version 2011-07-18
 */
public class TimerQuestion {

    int heureDerniereQuestion = -1;
    long instantDerniereQuestion=-1;
    static int randomNum;
    static Timer timer;
    static Timer timer2;
    static long temps = 10000;
    long startTime = 2000;
    //int min = 0, max = 36000;
    // Coefficient pour la frequence des questions. 1.0 en mode normal,  et par exemple 2.0 en mode "ralenti".
    int coeffSlow = 1;
    int coeffSpeed = 3;
    int coeffCurrent = coeffSlow;
    // Time before next question range : random int will be choosen between min and max. It's the time in seconds before asking next question.
    //int min = 0, max = 3600000;
    int min = 0, max = 3600000;
    TimerTask tache;
    static Date dateDeReprise;

    /**
     * Unique instance of TimerQuestion (singleton design pattern)
     */
    private static TimerQuestion instance;

    /**
     * Contructor
     */
    public TimerQuestion(){
        chrono();
    }

    /**
     * Get unique instance
     * @return
     */
    public static TimerQuestion getTimerQuestion(){
        if (instance == null) {
            instance = new TimerQuestion();
        }
        return instance;
    }

    /**
     * Launch the timer
     */
    public void chrono() {

        // délai avant la mise en route (0 demarre immediatement)
        timer = new Timer();           // création du timer
        tache = new TimerTask() {      // création et spécification de la tache à effectuer

            @Override
            public void run() {
                try {
                    Random rand = new Random();
                    max = (int) 3600000 / SessionManager.getSessionManager().getSessionCourante().getQuestionsPerHour();
                    //System.out.println("Max ="+max);
                    int nb = (rand.nextInt(max - min + 1) + min) * coeffCurrent;
                    setRandomNum(nb);
                    setTemps(nb);
                    setNewTimer();
                    timer.cancel();
                    Date madate = new Date();
                    //System.out.println(madate.toString() + " - Premiere question dans : " + randomNum / (1000 * 60) + " minutes, soit " + randomNum / 1000 + " secondes.");
                } catch (SQLException ex) {
                    Logger.getLogger(TimerQuestion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        timer.schedule(tache, temps);  // ici on lance la mecanique
    }

    /**
     * Get the current timer
     * @return
     */
    public static Timer getTimer() {
        return timer;
    }

    /**
     * Set a new timer
     */
    public void setNewTimer() {
        if (timer2 != null) {
            timer2.cancel();
        }

        timer2 = new Timer();
        tache = new TimerTask() {      // création et spécification de la tache à effectuer

            @Override
            public void run() {
                if (OptionFrame.getOptionFrame().isNormalSpeed())
                    coeffCurrent = coeffSlow;
                else
                    coeffCurrent = coeffSpeed;

                GregorianCalendar gc = new GregorianCalendar();
                try {
                    //System.out.println("Durée de vie : "+SessionManager.getSessionManager().getSessionCourante().getTimeToLive());
                    //System.out.println("Age : "+api.utils.DateOutils.nbHoursBetweenTwoDates(SessionManager.getSessionManager().getSessionCourante().getDebut(), new Date()));
                    if ((SessionManager.getSessionManager().getSessionCourante().getActive()) && (SessionManager.getSessionManager().getSessionCourante().getTimeToLive()<=(api.utils.DateOutils.nbHoursBetweenTwoDates(SessionManager.getSessionManager().getSessionCourante().getDebut(), new Date())))){
                        /*SessionManager.getSessionManager().getSessionCourante().setActive(false);
                        SessionManager.getSessionManager().getSessionCourante().setfin(new Date());*/
                        SessionManager.getSessionManager().closeSession(SessionManager.getSessionManager().getSessionCourante());
                        javax.swing.JOptionPane.showMessageDialog(null, Lang.getLang().getValueFromRef("TimerQuestion.endOfSession"));

                    }

                    if (SessionManager.getSessionManager().getSessionCourante().getPause()){
                        //System.out.println("Nb ms remaining : "+(SessionFrame.getFrame().instantReprise-new Date().getTime()));
                        if (SessionFrame.getFrame().isTimeToResumeSession()){
                            SessionFrame.getFrame().leavePause();
                        }
                    }

//                    if (((heureDerniereQuestion == -1) || (gc.get(Calendar.HOUR_OF_DAY) != heureDerniereQuestion)) && (!AskFrame.getTheFrame().isVisible()) && (SessionManager.getSessionManager().getSessionCourante().getActive()) && (!SessionManager.getSessionManager().getSessionCourante().getPause())) {
                    if (((instantDerniereQuestion == -1) || ((new Date().getTime()-instantDerniereQuestion)>(3600000/SessionManager.getSessionManager().getSessionCourante().getQuestionsPerHour()))) && (!AskFrame.getTheFrame().isVisible()) && (SessionManager.getSessionManager().getSessionCourante().getActive()) && (!SessionManager.getSessionManager().getSessionCourante().getPause()) && (!FullScreenDetector.isFullScreenProgramRunning()) && (!SessionFrame.getFrame().isVisible()) ) {
                        heureDerniereQuestion = gc.get(Calendar.HOUR_OF_DAY);
                        instantDerniereQuestion=new Date().getTime();
                        AskFrame.getTheFrame().showTheFrame(null);
                        Random rand = new Random();
                        max = (int) 3600000 / SessionManager.getSessionManager().getSessionCourante().getQuestionsPerHour();
                        int nb = (rand.nextInt(max - min + 1) + min) * coeffCurrent;
                        setRandomNum(nb);
                        setTemps(nb);
                        //Date madate = new Date();
                        //System.out.println(madate.toString() + "Prochaine question dans : " + randomNum / (1000 * 60) + " minutes, soit " + randomNum / 1000 + " secondes.");
                        setNewTimer();
                    } else {
                        Date madate = new Date();
                        if (!(SessionManager.getSessionManager().getSessionCourante().getActive())) {
                            //System.out.println(madate.toString() + "Session courante close... Nouveau calcul pour la prochaine question...");
                        } else if ((dateDeReprise != null) && dateDeReprise.after(new Date())) {
                            //System.out.println(madate.toString() + "Session en pause jusqu'à : "+dateDeReprise.toString());
                        } else {
                            //System.out.println(madate.toString() + "Déjà questionné dans l'heure. Nouveau calcul pour la prochaine question...");
                        }
                        Random rand = new Random();
                        max = (int) 3600000 / SessionManager.getSessionManager().getSessionCourante().getQuestionsPerHour();
                        int nb = rand.nextInt(max - min + 1) + min;
                        setRandomNum(nb);
                        setTemps(nb);
                        //System.out.println("PPP: fullscreen="+FullScreenDetector.isFullScreenProgramRunning().toString()+" - "+new Date().toString()+" Prochaine question dans : " + randomNum / (1000 * 60) + " minutes, soit " + randomNum / 1000 + " secondes.");
                        setNewTimer();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(TimerQuestion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        timer2.schedule(tache, temps);  // ici on lance la mecanique
    }

    /**
     * Generate new random int value in rd range for randomNum
     * @param rd
     */
    public void setRandomNum(int rd) {
        randomNum = rd;
    }

    /**
     * Change time value
     * @param tps
     */
    public void setTemps(int tps) {
        temps = tps;
    }

    /**
     * Set instant of next question
     * @param d
     */
    public static void setDateDelay(Date d) {
        dateDeReprise = d;
    }

    /**
     * Reset instant of next question
     */
    public static void resetDateDelay() {
        dateDeReprise = null;
    }

    /**
     * Check if a question can be ask at the currnet moment
     * @return true if a question can be asked, else fase
     */
    public Boolean canIAskQuestionNow(){
        GregorianCalendar gc = new GregorianCalendar();
        try {
//            if (((heureDerniereQuestion == -1) || (gc.get(Calendar.HOUR_OF_DAY) != heureDerniereQuestion)) && (!AskFrame.getTheFrame().isVisible()) && (SessionManager.getSessionManager().getSessionCourante().getActive()) && (!SessionManager.getSessionManager().getSessionCourante().getPause())) {
//        if (((instantDerniereQuestion == -1) || ((new Date().getTime()-instantDerniereQuestion)>1800000)) && (!AskFrame.getTheFrame().isVisible()) && (SessionManager.getSessionManager().getSessionCourante().getActive()) && (!SessionManager.getSessionManager().getSessionCourante().getPause())) {
        if (((instantDerniereQuestion == -1) || ((new Date().getTime()-instantDerniereQuestion)>(3600000/SessionManager.getSessionManager().getSessionCourante().getQuestionsPerHour()))) && (!AskFrame.getTheFrame().isVisible()) && (SessionManager.getSessionManager().getSessionCourante().getActive()) && (!SessionManager.getSessionManager().getSessionCourante().getPause()) && (!FullScreenDetector.isFullScreenProgramRunning())  && (!SessionFrame.getFrame().isVisible()) ) {
            return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TimerQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }


    /**
     * Call it when a new question has been asked : not needed
     */
    public void resetTimerAfterQuestion(){
        try {
            GregorianCalendar gc = new GregorianCalendar();
            heureDerniereQuestion = gc.get(Calendar.HOUR_OF_DAY);
            instantDerniereQuestion = new Date().getTime();
            if (OptionFrame.getOptionFrame().isNormalSpeed()) {
                coeffCurrent = coeffSlow;
            } else {
                coeffCurrent = coeffSpeed;
            }
            Random rand = new Random();
            max = (int) 3600000 / SessionManager.getSessionManager().getSessionCourante().getQuestionsPerHour();
            int nb = (rand.nextInt(max - min + 1) + min) * coeffCurrent;
            setRandomNum(nb);
            setTemps(nb);
            setNewTimer();
        } catch (SQLException ex) {
            Logger.getLogger(TimerQuestion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
