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
package des;

import api.time.TimerQuestion;
import api.utils.UniqueInstance;
import api.dbc.DBConnexion;
import api.gui.OptionFrame;
import api.gui.TaskTrayMenu;
import api.utils.CopyAndPasteHandler;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.SessionManager;

/**
 * Class Main.java
 * @description DEScribe's main class
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @author Bertrand Gros    <gros.bertrand@gmail.com>
 * @version 2011-01-28
 */
public class Main {

    public static String version = "1.0.9";
    public static String datemaj = "2011-07-05";
    public static String contacts = "Sébastien Faure<sebastien.faure3@gmail.com>\nBertrand Gros<gros.bertrand@gmail.com>\nYannick Prié<yannick.prie@univ-lyon1.fr>";
    public static String appName = "DEScribe";
    public static String projectPage = "http://describe.googlecode.com";
    public static String cpRight = "(C)2010-2011";

    public static void main(String[] args) throws SQLException {

        // Port à utiliser pour communiquer avec l'instance de l'application lancée.
        final int PORT = 32145;
        // Message à envoyer à l'application lancée lorsqu'une autre instance essaye de démarrer.
        final String MESSAGE = "DEScribe";
        // Actions à effectuer lorsqu'une autre instance essaye de démarrer.
        final Runnable RUN_ON_RECEIVE = new Runnable() {

            public void run() {
            }
        };
        try {
            Thread.sleep(1000);
            if (args.length>0){
                // If param = reboot => used for Mac : reboot from .jar to .app
                if (args[0].equals("reboot")){
                    //System.out.println(api.utils.appManagement.restartApplication(OptionFrame.getOptionFrame(), true));
                    api.utils.appManagement.restartApplication(OptionFrame.getOptionFrame(), true);
                }
            }
                /*Thread.sleep(20000);
                //System.out.println("Fullscreen :"+api.utils.FullScreenDetector.isFullScreenProgramRunning().toString());
                javax.swing.JOptionPane.showMessageDialog(null, "Fullscreen :"+api.utils.FullScreenDetector.isFullScreenProgramRunning().toString());*/
        } catch (InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        UniqueInstance uniqueInstance = new UniqueInstance(PORT, MESSAGE, RUN_ON_RECEIVE);
        // Si aucune autre instance n'est lancée...
        if (uniqueInstance.launch()) {
            // On démarre l'application.
            DBConnexion conn = DBConnexion.getConnexion();
            //conn.resetBD();
            TaskTrayMenu tt = new TaskTrayMenu();
            SessionManager sm = SessionManager.getSessionManager();

            OptionFrame.getOptionFrame();
            //TimerQuestion timr = new TimerQuestion();
            TimerQuestion timr=TimerQuestion.getTimerQuestion();
            /*try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            javax.swing.JOptionPane.showMessageDialog(null, timr.canIAskQuestionNow().toString());*/
            /*if (Utils.isPriorityProgramRunningOSWin("POWERP")){
            System.out.println("RUNNING");
            } else {
            System.out.println("NOT RUNNING");
            }*/
            
            /* Lancer le détecteur de Copier-Coller */
            CopyAndPasteHandler cPH =CopyAndPasteHandler.getInstance();

        }
    }

}
