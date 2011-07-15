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

package api.gui;

import api.i18n.Lang;
import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 * Class TaskTrayMenu.java
 * @description System Tray menu
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @author Bertrand Gros
 * @version 2011-01-28
 */
public class TaskTrayMenu {

    public static String ShowItemLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.ShowItemLabel");
    public static String ExitItemLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.ExitItemLabel");
    public static String ConfigItemLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.ConfigItemLabel");
    public static String SessionItemLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.SessionItemLabel");
    public static String SessionInPauseItemLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.SessionInPauseItemLabel");
    public static String AboutItemLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.AboutItemLabel");
    public static String ReportItemLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.ReportItemLabel");

    public static String PauseSession1HLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.PauseSession1HLabel");
    public static String PauseSession3HLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.PauseSession3HLabel");
    public static String PauseSession24HLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.PauseSession24HLabel");
    public static String PauseSessionCustomLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.PauseSessionCustomLabel");
    public static String ResumeSessionLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.ResumeSessionLabel");

    public static String PopupDelayLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.PopupDelayLabel");

    public static MenuItem showItem;
    public static MenuItem exitItem;
    public static MenuItem configItem;
    public static MenuItem sessionItem;
    public static MenuItem aboutItem;
    public static MenuItem reportItem;


    public static MenuItem pauseSession1HItem;
    public static MenuItem pauseSession3HItem;
    public static MenuItem pauseSession24HItem;
    public static MenuItem pauseSessionCustomItem;
    public static MenuItem resumeSessionItem;

    public static PopupMenu popupDelay;

    public TaskTrayMenu() {

        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image image = Toolkit.getDefaultToolkit().getImage("media/des.gif");

            PopupMenu popup = new PopupMenu();

            //For debug only
            showItem = new MenuItem(ShowItemLabel);
            showItem.addActionListener(Listeners.getListeners());
            //popup.add(showItem);
            
            exitItem = new MenuItem(ExitItemLabel);
            exitItem.addActionListener(Listeners.getListeners());

            configItem = new MenuItem(ConfigItemLabel);
            configItem.addActionListener(Listeners.getListeners());

            sessionItem = new MenuItem(SessionItemLabel);
            sessionItem.addActionListener(Listeners.getListeners());

            aboutItem = new MenuItem(AboutItemLabel);
            aboutItem.addActionListener(Listeners.getListeners());

            reportItem = new MenuItem(ReportItemLabel);
            reportItem.addActionListener(Listeners.getListeners());



            popup.add(configItem);
            popup.add(sessionItem);


            popupDelay = new PopupMenu(PopupDelayLabel);
            popup.add(popupDelay);

            pauseSession1HItem = new MenuItem(PauseSession1HLabel);
            pauseSession1HItem.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    SessionFrame.getFrame().setNbHoursOfDelay(1);
                }
            });
            popupDelay.add(pauseSession1HItem);

            pauseSession3HItem = new MenuItem(PauseSession3HLabel);
            pauseSession3HItem.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    SessionFrame.getFrame().setNbHoursOfDelay(3);
                }
            });
            popupDelay.add(pauseSession3HItem);

            pauseSession24HItem = new MenuItem(PauseSession24HLabel);
            pauseSession24HItem.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    SessionFrame.getFrame().setNbHoursOfDelay(24);
                }
            });
            popupDelay.add(pauseSession24HItem);

            pauseSessionCustomItem = new MenuItem(PauseSessionCustomLabel);
            pauseSessionCustomItem.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    String nom = JOptionPane.showInputDialog(null, Lang.getLang().getValueFromRef("TaskTrayMenu.DelayDialogContent"), Lang.getLang().getValueFromRef("TaskTrayMenu.DelayDialogTitle"), JOptionPane.QUESTION_MESSAGE);
                    if (!nom.equals("")){
                        try {
                        SessionFrame.getFrame().setNbHoursOfDelay(Integer.parseInt(nom));
                        } catch (Exception ex){
                            javax.swing.JOptionPane.showMessageDialog(null, Lang.getLang().getValueFromRef("TaskTrayMenu.NotPositiveIntegerValue"));
                        }
                    }
                }
            });
            popupDelay.add(pauseSessionCustomItem);


            resumeSessionItem = new MenuItem(ResumeSessionLabel);
            resumeSessionItem.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    SessionFrame.getFrame().leavePause();
                }
            });
            resumeSessionItem.setEnabled(false);
            popup.add(resumeSessionItem);

            popup.add(reportItem);
            popup.add(aboutItem);

            //DEBUG
            MenuItem displayAskFrame = new MenuItem("Display AskFrame (testing)");
            displayAskFrame.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    AskFrame.getTheFrame().showTheFrame(null);
                }
            });
            popup.add(displayAskFrame);
            // \DEBUG-ONLY\

            //DEBUG
            /*MenuItem restartDES = new MenuItem("Restart");
            restartDES.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    api.utils.appManagement.restartApplication(this, false);
                }
            });
            popup.add(restartDES);*/
            // \DEBUG-ONLY\



            popup.add(exitItem);



            final TrayIcon trayIcon = new TrayIcon(image, "DEScribe", popup);

            //listenner inutile ?
            ActionListener actionListener = new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    //AskFrame.getTheFrame().showTheFrame();
                }
            };

            trayIcon.setImageAutoSize(true);
            trayIcon.addActionListener(actionListener);

            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                System.err.println("TrayIcon could not be added.");
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, Lang.getLang().getValueFromRef("TaskTrayMenu.TrayError"));
        }
    }

    public static void refresh() {
        ShowItemLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.ShowItemLabel");
        ExitItemLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.ExitItemLabel");
        ConfigItemLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.ConfigItemLabel");
        SessionItemLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.SessionItemLabel");
        SessionInPauseItemLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.SessionInPauseItemLabel");
        AboutItemLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.AboutItemLabel");
        ReportItemLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.ReportItemLabel");
        //For debug only
         showItem.setLabel(Lang.getLang().getValueFromRef("TaskTrayMenu.ShowItemLabel"));
        
        exitItem.setLabel(Lang.getLang().getValueFromRef("TaskTrayMenu.ExitItemLabel"));
        configItem.setLabel(Lang.getLang().getValueFromRef("TaskTrayMenu.ConfigItemLabel"));
        sessionItem.setLabel(Lang.getLang().getValueFromRef("TaskTrayMenu.SessionItemLabel"));
        SessionInPauseItemLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.SessionInPauseItemLabel");
        aboutItem.setLabel(Lang.getLang().getValueFromRef("TaskTrayMenu.AboutItemLabel"));

        reportItem.setLabel(Lang.getLang().getValueFromRef("TaskTrayMenu.ReportItemLabel"));



        PauseSession1HLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.PauseSession1HLabel");
        PauseSession3HLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.PauseSession3HLabel");
        PauseSession24HLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.PauseSession24HLabel");
        PauseSessionCustomLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.PauseSessionCustomLabel");
        ResumeSessionLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.ResumeSessionLabel");
        PopupDelayLabel = Lang.getLang().getValueFromRef("TaskTrayMenu.PopupDelayLabel");

        pauseSession1HItem.setLabel(PauseSession1HLabel);
        pauseSession3HItem.setLabel(PauseSession3HLabel);
        pauseSession24HItem.setLabel(PauseSession24HLabel);
        pauseSessionCustomItem.setLabel(PauseSessionCustomLabel);
        resumeSessionItem.setLabel(ResumeSessionLabel);
        popupDelay.setLabel(PopupDelayLabel);
    }

}
