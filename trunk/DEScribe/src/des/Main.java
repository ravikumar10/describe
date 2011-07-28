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
package des;

import api.time.TimerQuestion;
import api.utils.UniqueInstance;
import api.dbc.DBConnexion;
import api.gui.AskFrame;
import api.gui.OptionFrame;
import api.gui.TaskTrayMenu;
import api.utils.CopyAndPasteHandler;
import com.google.gson.Gson;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Regle;
import model.SessionManager;

/**
 * Class Main.java
 * @description DEScribe's main class
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @version 2011-07-18
 */
public class Main {

    public static String version = "1.1.3";
    public static String datemaj = "2011-07-21";
    public static String contacts = "Sébastien Faure<sebastien.faure3@gmail.com>\nYannick Prié<yannick.prie@univ-lyon1.fr>\nAmaury Belin<amaury.belin@gmail.com>";
    public static String appName = "DEScribe";
    public static String projectPage = "http://describe.googlecode.com";
    public static String cpRight = "(C)2010-2011";
    public static String regleRead = null;

    public static void main(String[] args) throws SQLException {

        /**
         * Port used to communicate with the instance launched
         */
        final int PORT = 32145;

        /**
         * Message to send to the instance launched
         */
        final String MESSAGE = "Message envoyé : DEScribe is already running";
        
        /**
         * Actions to do when another instance is trying to start up
         */
        final Runnable RUN_ON_RECEIVE = new Runnable() {

            public void run() {
                /* Nothing for now */
                if (regleRead.equals("")){
                    javax.swing.JOptionPane.showMessageDialog(null, MESSAGE);
                } else {
                    //String json = "{'data1':100,'data2':'hello'}";
                    Gson gson = new Gson();

                    //convert JSON into java object
                    Regle r = gson.fromJson(regleRead, Regle.class);
                    //System.out.println(obj);
                    javax.swing.JOptionPane.showMessageDialog(null, "New rule : "+r.getType());
                    regleRead="";
                    AskFrame.getTheFrame().askQuestionWithRule(r.getType());
                }
                
            }
        };
        
        try {
            /**
             * 1 sec wait because of Mac version and its possible reboot
             * (see following lines)
             */
            Thread.sleep(1000);
            if (args.length>0){
                // If param = reboot => used for Mac : reboot from .jar to .app
                if (args[0].equals("reboot")){
                    api.utils.appManagement.restartApplication(OptionFrame.getOptionFrame(), true);
                }
            }
                /*Thread.sleep(20000);
                //System.out.println("Fullscreen :"+api.utils.FullScreenDetector.isFullScreenProgramRunning().toString());
                //javax.swing.JOptionPane.showMessageDialog(null, "Fullscreen :"+api.utils.FullScreenDetector.isFullScreenProgramRunning().toString());*/
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        UniqueInstance uniqueInstance = new UniqueInstance(PORT, MESSAGE, RUN_ON_RECEIVE);
        // If no oter instance is already launched...
        if (uniqueInstance.launch()) {
            // Let's start it up!

            /** Database connexion */
            DBConnexion conn = DBConnexion.getConnexion();

            /**
             * Tasktray menu
             */
            TaskTrayMenu tt = new TaskTrayMenu();

            /**
             * Sessions manager
             */
            SessionManager sm = SessionManager.getSessionManager();

            /**
             * Option frame
             */
            OptionFrame.getOptionFrame();
            
            /**
             * Question timer
             */
            TimerQuestion timr=TimerQuestion.getTimerQuestion();

            /*try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            //javax.swing.JOptionPane.showMessageDialog(null, timr.canIAskQuestionNow().toString());*/
            //javax.swing.JOptionPane.showMessageDialog(null, api.utils.FullScreenDetector.isFullScreenProgramRunning().toString());
            /*if (Utils.isPriorityProgramRunningOSWin("POWERP")){
            System.out.println("RUNNING");
            } else {
            System.out.println("NOT RUNNING");
            }*/
            
            /* Copy and Paste handler */
            CopyAndPasteHandler cPH =CopyAndPasteHandler.getInstance();

        }
    }
}
