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

package api.gui;

import api.dbc.DBConnexion;
import api.i18n.Lang;
import java.awt.BorderLayout;
import java.awt.List;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;
import model.Session;
import model.SessionManager;

/**
 * Class SessionFrame.java
 * @description Session management frame
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @author Bertrand Gros    <gros.bertrand@gmail.com>
 * @version 2011-02-06
 */
public class SessionFrame extends JFrame {

    private static String title = Lang.getLang().getValueFromRef("SessionFrame.title");
    static protected SessionFrame sf = null;

    private List c;
    private JSpinner spinner;
    public static Label lbCurrentSession;
    public static Label lbCurrentSessionName;
    public static String labelCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelCurrentSession");

    public static Label lbSessionsList;
    public static Label lbSelectedSession;
    public static String labelSessionsList = Lang.getLang().getValueFromRef("SessionFrame.labelSessionsList");
    public static String labelSelectedSession = Lang.getLang().getValueFromRef("SessionFrame.labelSessionsList");
    public static String labelCurrentSessionName;
    private static JButton btExportCurrentSession = null;
    //public static JButton btPauseCurrentSession = null;
    private static JButton btNewSessionCurrentSession = null;
    private static JButton btCloseSessionCurrentSession = null;
    public static String labelBtConsultCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtConsultCurrentSession");
    public static String labelBtExportCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtExportCurrentSession");
    //public static String labelBtPauseCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtPauseCurrentSession");
    public static String labelBtRetourDePauseCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtRetourDePauseCurrentSession");
    public static String labelBtNewSessionCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtNewSessionCurrentSession");
    public static String labelBtCloseSessionCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtCloseSessionCurrentSession");
    public static String toolTipBtConsultCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtConsultCurrentSession");
    public static String toolTipBtExportCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtExportCurrentSession");
    //public static String toolTipBtPauseCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtPauseCurrentSession");
    public static String toolTipBtNewSessionCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtNewSessionCurrentSession");
    public static String toolTipBtCloseSessionCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtCloseSessionCurrentSession");
    private JTextField chrono;
    private Timer timer;
    private static String textInactiveTimer = Lang.getLang().getValueFromRef("SessionFrame.textInactiveTimer");
    public static String labelOldSessions = Lang.getLang().getValueFromRef("SessionFrame.labelOldSessions");
    private static JButton btExportOldSessions = null;
    public static JButton btDeleteOldSessions = null;

    public static Label lbOldSessions;
    public static String labelBtConsultOldSessions = Lang.getLang().getValueFromRef("SessionFrame.labelBtConsultOldSessions");
    public static String labelBtExportOldSessions = Lang.getLang().getValueFromRef("SessionFrame.labelBtExportOldSessions");
    public static String labelBtDeleteOldSessions = Lang.getLang().getValueFromRef("SessionFrame.labelBtDeleteOldSessions");
    public static String toolTipBtConsultOldSessions = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtConsultOldSessions");
    public static String toolTipBtExportOldSessions = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtExportOldSessions");
    public static String toolTipBtDeleteOldSessions = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtDeleteOldSessions");

    public static Label lbWarningUnexported;
    public static String lbUnexportedSessionWarning = Lang.getLang().getValueFromRef("SessionFrame.lbUnexportedSessionWarning");

    private static JButton btOk = null;
    public static String labelBtOk = Lang.getLang().getValueFromRef("SessionFrame.labelBtOk");
    public static String lbErrorNbHoursMessage = Lang.getLang().getValueFromRef("SessionFrame.lbErrorNbHoursMessage");


    private static JTextArea txtAreaCurrentSession = null;
    private static JTextArea txtAreaSelectedOldSession = null;

    public static Label lbName;
    public static String labelName = Lang.getLang().getValueFromRef("SessionFrame.labelName");
    public static Label lbStatus;
    public static String labelStatus = Lang.getLang().getValueFromRef("SessionFrame.labelStatus");
    public static Label lbStatusValue;
    public static String labelStatusActive = Lang.getLang().getValueFromRef("SessionFrame.labelStatusActive");
    public static Label lbStatusPaused;
    public static String labelStatusPaused = Lang.getLang().getValueFromRef("SessionFrame.labelStatusPaused");
    public static Label lbStatusClosed;
    public static String labelStatusClosed = Lang.getLang().getValueFromRef("SessionFrame.labelStatusClosed");

    private static JButton btVisualize = null;
    public static String labelBtVisualize = Lang.getLang().getValueFromRef("SessionFrame.labelBtVisualize");
    public static String toolTipBtVisualize = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtVisualize");

    public static Label lbCommandsCurrentSession;
    public static Label lbCommandsClosedSession;
    public static String labelCommandsCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelCommandsCurrentSession");
    public static String labelCommandsClosedSession = Lang.getLang().getValueFromRef("SessionFrame.labelCommandsClosedSession");

    public static long instantReprise;

    public SessionFrame() {

        // LA FENETRE PRINCIPALE

        // New version

        this.setTitle(title);
        this.setIconImage(new ImageIcon("media/des.gif").getImage());
        this.setResizable(true);
        this.addWindowListener(Listeners.getListeners());



        JPanel jpLeft = new JPanel(new GridBagLayout());
        jpLeft.setBorder(BorderFactory.createLineBorder(Color.darkGray,4));


        JPanel jpLeftUpFlowOne = new JPanel(new FlowLayout());
        jpLeftUpFlowOne.setBackground(Color.white);

        lbCurrentSession = new Label(labelCurrentSession);
        lbCurrentSession.setFont(new Font("Verdana", Font.BOLD, 30));
        lbCurrentSession.setForeground(new Color(178,34,34));
        lbCurrentSession.setBackground(Color.white);
        //jpLeftUpFlowOne.add(lbCurrentSession);

        lbSessionsList = new Label(labelSessionsList);
        lbSessionsList.setFont(new Font("Verdana", Font.BOLD, 30));
        lbSessionsList.setForeground(new Color(178,34,34));
        lbSessionsList.setBackground(Color.white);
        jpLeftUpFlowOne.add(lbSessionsList);


        /*c.gridy = 0;
        c.gridx = 1;
        c.gridheight = 2;
        container.add(Button3, c);*/

        /*jpLeft.setLayout(new GridLayout(3,1));
        JPanel jpLeftUp = new JPanel(new GridLayout(2,1));

        jpLeftUp.setBackground(new Color(178,34,34));
        jpLeftUp.setBorder(BorderFactory.createLineBorder(Color.lightGray,3));
*/

        JPanel jpLeftUpFlowTwo = new JPanel(new FlowLayout());
        jpLeftUpFlowTwo.setBackground(new Color(178,34,34));

        try {
            lbName = new Label (labelName);
            jpLeftUpFlowTwo.add(lbName);

            lbName.setForeground(Color.white);
            lbName.setBackground(new Color(178,34,34));
            lbName.setFont(new Font("Verdana", Font.PLAIN, 14));

            lbCurrentSessionName = new Label(SessionManager.getSessionManager().getSessionCourante().getNom());
            jpLeftUpFlowTwo.add(lbCurrentSessionName);
            jpLeftUpFlowTwo.setBackground(new Color(178,34,34));
            lbCurrentSessionName.setForeground(Color.white);
            lbCurrentSessionName.setBackground(new Color(178,34,34));
            lbCurrentSessionName.setFont(new Font("Verdana", Font.BOLD, 14));

            lbStatus = new Label (labelStatus);
            jpLeftUpFlowTwo.add(lbStatus);
            lbStatus.setForeground(Color.white);
            lbStatus.setBackground(new Color(178,34,34));
            lbStatus.setFont(new Font("Verdana", Font.PLAIN, 14));

            lbStatusValue = new Label (labelStatusActive);
            jpLeftUpFlowTwo.add(lbStatusValue);
            lbStatusValue.setForeground(Color.GREEN);
            lbStatusValue.setBackground(new Color(178,34,34));
            lbStatusValue.setFont(new Font("Verdana", Font.BOLD, 14));

        } catch (SQLException ex) {
            Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        //jpLeft.add(jpLeftUpFlowOne);
        //jpLeftUp.add(jpLeftUpFlowOne);
        //jpLeftUp.add(jpLeftUpFlowTwo);

        //jpLeft.add(jpLeftUp);
        c = new List();
        //c.setPreferredSize(new Dimension(280, 20));
        SessionManager sm;
        try {
            sm = SessionManager.getSessionManager();
            for (Iterator<Session> it = sm.getLesSessions().iterator(); it.hasNext();) {
                Session s = it.next();
                c.add("" + s.getId() + " - " + s.getNom() + " - " + s.getDebut());
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        //panelDownList.add(c);
        c.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent ie) {
                if (ie.getStateChange() == ItemEvent.SELECTED) {

                    if (c.getSelectedItem().equals(Lang.getLang().getValueFromRef("SessionFrame.noOldSessions"))) {
                        txtAreaSelectedOldSession.setText(Lang.getLang().getValueFromRef("SessionFrame.noAnswers"));
                        lbWarningUnexported.setVisible(false);
                    } else {
                    if (getSessionSelectionnee().getLastExport() == null) {
                        lbWarningUnexported.setVisible(true);
                        //SessionFrame.getFrame().pack();
                    } else {
                        lbWarningUnexported.setVisible(false);
                        //SessionFrame.getFrame().pack();
                    }
                    DBConnexion conn = DBConnexion.getConnexion();
                    txtAreaSelectedOldSession.setText(conn.getEntriesStringBySession(getSessionSelectionnee()));
                    if (txtAreaSelectedOldSession.getText().equals(""))
                        txtAreaSelectedOldSession.setText(Lang.getLang().getValueFromRef("SessionFrame.noAnswers"));
                    }

                     if (getSessionSelectionnee() != null) {
                        try {
                            if (getSessionSelectionnee().getLastExport() == null) {
                                lbWarningUnexported.setVisible(true);
                            } else {
                                lbWarningUnexported.setVisible(false);
                            }
                            if ((getSessionSelectionnee().getId() != SessionManager.getSessionManager().getSessionCourante().getId()) || (!getSessionSelectionnee().getActive())) {
                                // Already closed
                                btCloseSessionCurrentSession.setEnabled(false);
                            } else {
                                btCloseSessionCurrentSession.setEnabled(true);
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        // Pas de sessions passées
                        c.add(Lang.getLang().getValueFromRef("SessionFrame.noOldSessions"));
                        txtAreaSelectedOldSession.setText("SessionFrame.noAnswers");
                    }


                    lbCurrentSessionName.setText(getSessionSelectionnee().getNom());

                 /*   try {
                        if (SessionManager.getSessionManager().getSessionCourante().getPause()) {
                            btPauseCurrentSession.setText(labelBtRetourDePauseCurrentSession);
                        } else {
                            btPauseCurrentSession.setText(labelBtPauseCurrentSession);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }*/

                        if (!SessionFrame.getFrame().getSessionSelectionnee().getActive()) {
                            lbStatusValue.setText(labelStatusClosed);
                            lbStatusValue.setForeground(Color.YELLOW);
                        } else if (SessionFrame.getFrame().getSessionSelectionnee().getPause()) {
                            lbStatusValue.setText(labelStatusPaused);
                            lbStatusValue.setForeground(Color.YELLOW);
                        } else {
                            lbStatusValue.setText(labelStatusActive);
                            lbStatusValue.setForeground(Color.GREEN);
                        }

                }
            }
        });
        
        GridBagConstraints c1 = new  GridBagConstraints();
        c1.fill = GridBagConstraints.HORIZONTAL;
        //c1.ipady = 0;
        c1.weightx = 1;
        c1.gridwidth=3;
        c1.gridx = 1;
        c1.gridy = 0;
        jpLeft.add(jpLeftUpFlowOne, c1);


        JPanel jpLeftUpOneOne = new JPanel(new FlowLayout());
        jpLeftUpOneOne.setBackground(Color.white);
        c1.fill = GridBagConstraints.HORIZONTAL;
        c1.ipady = 38;
        c1.weightx = 1;
        c1.gridwidth=3;
        c1.gridx = 1;
        c1.gridy = 1;
        jpLeft.add(jpLeftUpOneOne, c1);
        JPanel jpLeftUpOneTwo = new JPanel(new GridLayout(2,1));
        jpLeftUpOneTwo.setBorder(BorderFactory.createLineBorder(Color.lightGray,3));
        jpLeftUpOneTwo.setBackground(new Color(178,34,34));
        c1.fill = GridBagConstraints.HORIZONTAL;
        c1.ipady = 22;
        c1.weightx = 1;
        c1.gridwidth=3;
        c1.gridx = 1;
        c1.gridy = 2;

        JPanel jpLeftUpOneTwoLbCommands = new JPanel(new FlowLayout());

        lbCommandsCurrentSession = new Label(labelCommandsCurrentSession);
        lbCommandsCurrentSession.setFont(new Font("Verdana", Font.BOLD, 16));
        lbCommandsCurrentSession.setForeground(Color.white);
        lbCommandsCurrentSession.setBackground(new Color(178,34,34));
        jpLeftUpOneTwoLbCommands.add(lbCommandsCurrentSession);
        JPanel jpLeftUpOneTwoCommands = new JPanel(new FlowLayout());
        jpLeftUpOneTwoLbCommands.setBackground(new Color(178,34,34));
        jpLeftUpOneTwoCommands.setBackground(new Color(178,34,34));

        btNewSessionCurrentSession = new JButton(labelBtNewSessionCurrentSession);
        btNewSessionCurrentSession.addActionListener(Listeners.getListeners());
        btNewSessionCurrentSession.setToolTipText(toolTipBtNewSessionCurrentSession);
        jpLeftUpOneTwoCommands.add(btNewSessionCurrentSession);

        btDeleteOldSessions = new JButton(labelBtDeleteOldSessions);
        btDeleteOldSessions.addActionListener(Listeners.getListeners());
        btDeleteOldSessions.setToolTipText(toolTipBtDeleteOldSessions);
        jpLeftUpOneTwoCommands.add(btDeleteOldSessions);

        jpLeftUpOneTwo.add(jpLeftUpOneTwoLbCommands);
        jpLeftUpOneTwo.add(jpLeftUpOneTwoCommands);

        jpLeft.add(jpLeftUpOneTwo, c1);
        c1.fill = GridBagConstraints.HORIZONTAL;
        if (api.utils.getOs.isWindows())
            c1.ipady = 343;      //make this component tall
        else
            c1.ipady = 383;
        c1.weightx = 1;
        c1.gridwidth = 3;
        c1.gridx = 1;
        c1.gridy = 3;
        jpLeft.add(c, c1);


        JPanel jpLeftMid = new JPanel(new GridLayout(3,1));
        jpLeftMid.setBorder(BorderFactory.createLineBorder(Color.lightGray,3));
        JPanel jpLeftMidFlowOne = new JPanel(new FlowLayout());
        JPanel jpLeftMidFlowTwo = new JPanel(new FlowLayout());
        JPanel jpLeftMidFlowThree = new JPanel(new FlowLayout());

        lbCommandsClosedSession = new Label(labelCommandsClosedSession);
        lbCommandsClosedSession.setFont(new Font("Verdana", Font.BOLD, 16));
        lbCommandsClosedSession.setForeground(Color.white);
        lbCommandsClosedSession.setBackground(new Color(178,34,34));
        jpLeftMidFlowOne.add(lbCommandsClosedSession);
        jpLeftMidFlowOne.setBackground(new Color(178,34,34));

        /*btPauseCurrentSession = new JButton(labelBtPauseCurrentSession);
        btPauseCurrentSession.addActionListener(Listeners.getListeners());
        jpLeftMidFlowTwo.add(btPauseCurrentSession);
        btPauseCurrentSession.setToolTipText(toolTipBtPauseCurrentSession);
        chrono = new JTextField(textInactiveTimer, 8);
        chrono.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                chrono.setSelectionStart(0);
                chrono.setSelectionEnd(chrono.getText().length());
            }
        });

        jpLeftMidFlowTwo.add(chrono);
         */
        
        btExportCurrentSession = new JButton(labelBtExportCurrentSession);
        btExportCurrentSession.addActionListener(Listeners.getListeners());
        //jpLeftMidFlowThree.add(btExportCurrentSession);
        btExportCurrentSession.setToolTipText(toolTipBtExportCurrentSession);

        //btNewSessionCurrentSession = new JButton(labelBtNewSessionCurrentSession);
        //btNewSessionCurrentSession.addActionListener(Listeners.getListeners());
        //jpLeftMidFlowThree.add(btNewSessionCurrentSession);
        //btNewSessionCurrentSession.setToolTipText(toolTipBtNewSessionCurrentSession);

        btCloseSessionCurrentSession = new JButton(labelBtCloseSessionCurrentSession);
        btCloseSessionCurrentSession.addActionListener(Listeners.getListeners());
        //jpLeftMidFlowThree.add(btCloseSessionCurrentSession);
        btCloseSessionCurrentSession.setToolTipText(toolTipBtCloseSessionCurrentSession);

        btVisualize = new JButton(labelBtVisualize);
        btVisualize.addActionListener(Listeners.getListeners());
        //jpLeftMidFlowThree.add(btVisualize);
        btVisualize.setToolTipText(toolTipBtVisualize);

        jpLeftMid.setBackground(new Color(178,34,34));
        jpLeftMidFlowOne.setBackground(new Color(178,34,34));
        jpLeftMidFlowTwo.setBackground(new Color(178,34,34));
        jpLeftMidFlowThree.setBackground(new Color(178,34,34));
        jpLeft.setBackground(new Color(178,34,34));
        jpLeftMid.add(jpLeftMidFlowOne);
        //jpLeftMid.add(jpLeftMidFlowTwo);
        jpLeftMid.add(jpLeftMidFlowThree);
        //jpLeft.add(jpLeftMid);


        txtAreaCurrentSession = new JTextArea();
        txtAreaCurrentSession.setEditable(false);

        JScrollPane jpLeftDown = new JScrollPane(txtAreaCurrentSession);
        jpLeftDown.setBackground(Color.LIGHT_GRAY);
        //jpLeftDown.setPreferredSize(new Dimension(500, 70));
        jpLeftDown.setBorder(BorderFactory.createLineBorder(Color.lightGray,3));

        //panelLeft.add(jpUpCurrentSessionInfo);
        //panelLeft.add(jpUpFlowBt);
        //jpLeft.add(jpLeftDown);

        jpLeft.setBackground(new Color(178,34,34));





        JPanel jpRight = new JPanel();
        jpRight.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,4));
        jpRight.setLayout(new GridLayout(3,1));


        JPanel jpRightUp = new JPanel(new GridLayout(2,1));
        JPanel jpRightUpFlowOne = new JPanel(new FlowLayout());
        jpRightUpFlowOne.setBackground(Color.white);

        lbOldSessions = new Label(labelOldSessions);
        lbOldSessions.setFont(new Font("Verdana", Font.BOLD, 30));
        lbOldSessions.setForeground(new Color(178,34,34));
        lbOldSessions.setBackground(Color.white);
        //jpRightUpFlowOne.add(lbOldSessions);

        lbSelectedSession = new Label(labelSelectedSession);
        lbSelectedSession.setFont(new Font("Verdana", Font.BOLD, 30));
        lbSelectedSession.setForeground(new Color(178,34,34));
        lbSelectedSession.setBackground(Color.white);
        jpRightUpFlowOne.add(lbSelectedSession);

        jpRightUp.add(jpRightUpFlowOne);
        JPanel jpRightUpFlowTwo = new JPanel(new FlowLayout());
        jpRightUpFlowTwo.setBackground(new Color(178,34,34));
        //jpRightUpFlowTwo.add(jpLeftUpFlowTwo);//-> add info
        //jpRightUp.add(jpRightUpFlowTwo);

        jpRightUp.setBorder(BorderFactory.createLineBorder(Color.lightGray,3));

        lbWarningUnexported = new Label(lbUnexportedSessionWarning);
        lbWarningUnexported.setFont(new Font("Verdana", Font.BOLD, 16));
        lbWarningUnexported.setForeground(Color.YELLOW);
        lbWarningUnexported.setBackground(new Color(178,34,34));

        /*c = new List();
        c.setPreferredSize(new Dimension(280, 20));
        SessionManager sm;
        try {
            sm = SessionManager.getSessionManager();
            for (Iterator<Session> it = sm.getLesSessions().iterator(); it.hasNext();) {
                Session s = it.next();
                if (!s.getActive()) {
                    c.add("" + s.getId() + " - " + s.getNom() + " - " + s.getDebut());
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        //panelDownList.add(c);
        c.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent ie) {
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    if (c.getSelectedItem().equals(Lang.getLang().getValueFromRef("SessionFrame.noOldSessions"))) {
                        txtAreaSelectedOldSession.setText(Lang.getLang().getValueFromRef("SessionFrame.noAnswers"));
                        lbWarningUnexported.setVisible(false);
                    } else {
                    if (getSessionSelectionnee().getLastExport() == null) {
                        lbWarningUnexported.setVisible(true);
                        //SessionFrame.getFrame().pack();
                    } else {
                        lbWarningUnexported.setVisible(false);
                        //SessionFrame.getFrame().pack();
                    }
                    DBConnexion conn = DBConnexion.getConnexion();
                    txtAreaSelectedOldSession.setText(conn.getEntriesStringBySession(getSessionSelectionnee()));
                    if (txtAreaSelectedOldSession.getText().equals(""))
                        txtAreaSelectedOldSession.setText(Lang.getLang().getValueFromRef("SessionFrame.noAnswers"));

                    }
                }
            }
        });
*/
        //jpRight.add(jpRightUp);

        JPanel jpRightMid = new JPanel(new GridLayout(5,1));
        jpRightMid.setBorder(BorderFactory.createLineBorder(Color.lightGray,3));

        JPanel jpRightMidFlowOne = new JPanel(new FlowLayout());
        jpRightMidFlowOne.setBackground(new Color(178,34,34));
        lbCommandsClosedSession = new Label(labelCommandsClosedSession);
        lbCommandsClosedSession.setFont(new Font("Verdana", Font.BOLD, 16));
        lbCommandsClosedSession.setForeground(Color.white);
        lbCommandsClosedSession.setBackground(new Color(178,34,34));
        jpRightMidFlowOne.add(lbCommandsClosedSession);
        //jpRightMid.add(jpRightMidFlowOne);

        //jpRightMid.add(c);
        JPanel jplbCommandsAndCmds = new JPanel(new GridLayout(2,1));
        jplbCommandsAndCmds.setBackground(new Color(178,34,34));
        jplbCommandsAndCmds.add(jpRightMidFlowOne); //label commands

        JPanel jpRightMidFlowTwo = new JPanel();
        jpRightMidFlowTwo.setLayout(new FlowLayout());
        jpRightMidFlowTwo.setBackground(new Color(178,34,34));
        btExportOldSessions = new JButton(labelBtExportOldSessions);
        btExportOldSessions.addActionListener(Listeners.getListeners());
        //jpRightMidFlowTwo.add(btExportOldSessions);
        jpRightMidFlowTwo.add(btExportOldSessions);
        btExportOldSessions.setToolTipText(toolTipBtExportOldSessions);

        jpRightMidFlowTwo.add(btCloseSessionCurrentSession);
        jpRightMidFlowTwo.add(btVisualize);
        //btDeleteOldSessions = new JButton(labelBtDeleteOldSessions);
        //btDeleteOldSessions.addActionListener(Listeners.getListeners());
        //jpRightMidFlowTwo.add(btDeleteOldSessions);
        //btDeleteOldSessions.setToolTipText(toolTipBtDeleteOldSessions);

        jpRightMidFlowTwo.setBackground(new Color(178,34,34));
        jplbCommandsAndCmds.add(jpRightMidFlowTwo);
        jpRightMid.setBackground(new Color(178,34,34));

        jpRightMid.add(jpLeftMidFlowTwo);
        //jpRightMid.add(jpLeftMidFlowThree);
        //jpRightMid.add(jpRightMidFlowTwo);
        jpRightMid.add(jpLeftUpFlowTwo);
        jpRightMid.add(lbWarningUnexported);

        //jpRightUpFlowTwo.add(jpLeftUpFlowTwo);//-> add info
       // jpRightUpFlowTwo.add(jpRightMidFlowTwo);
        jpRightUpFlowTwo.add(jplbCommandsAndCmds);
        jpRightUp.add(jpRightUpFlowTwo);
        jpRight.add(jpRightUp);

        jpRight.add(jpRightMid);

        txtAreaSelectedOldSession = new JTextArea();
        txtAreaSelectedOldSession.setEditable(false);
        JScrollPane jsp2 = new JScrollPane(txtAreaSelectedOldSession);
        jsp2.setBackground(Color.LIGHT_GRAY);
        jsp2.setBorder(BorderFactory.createLineBorder(Color.lightGray,3));
        //jsp2.setPreferredSize(new Dimension(500, 70));

        jpRight.add(jsp2);

        DBConnexion conn = DBConnexion.getConnexion();
        try {
            txtAreaCurrentSession.setText(conn.getEntriesStringBySession(SessionManager.getSessionManager().getSessionCourante()));
        } catch (SQLException ex) {
            Logger.getLogger(Listeners.class.getName()).log(Level.SEVERE, null, ex);
        }


            if (txtAreaSelectedOldSession.getText().equals(""))
                        txtAreaSelectedOldSession.setText(Lang.getLang().getValueFromRef("SessionFrame.noAnswers"));
            if (txtAreaCurrentSession.getText().equals(""))
                        txtAreaCurrentSession.setText(Lang.getLang().getValueFromRef("SessionFrame.noAnswers"));

        JPanel panelOK = new JPanel();
        btOk= new JButton(labelBtOk);
        btOk.addActionListener(Listeners.getListeners());
        panelOK.setBackground(new Color(178,34,34));
        panelOK.add(btOk);

        JPanel fullPanel = new JPanel(new GridBagLayout());


        GridBagConstraints c2 = new  GridBagConstraints();
        c2.fill = GridBagConstraints.HORIZONTAL;
        //c1.ipady = 0;
        c2.weightx = 0.5;
        c2.gridwidth=1;
        c2.gridx = 0;
        c2.gridy = 0;
        fullPanel.add(jpLeft,c2);

        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.ipady = 78;
        c2.weightx = 1;
        c2.gridwidth=10;
        c2.gridx = 1;
        c2.gridy = 0;
        fullPanel.add(jpRight,c2);

        getContentPane().add(fullPanel);



        //JPanel panel = new JPanel();
        //panel.setLayout(new GridLayout(1, 2));
        //getContentPane().add(panel);

        //panel.add(jpLeft);
        //panel.add(jpRight);
        
        this.setAlwaysOnTop(true);

        this.setPreferredSize(new Dimension(1280,665));

        this.pack();
        lbWarningUnexported.setVisible(false);
        this.setVisible(false);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);
    }

    public static SessionFrame getFrame() {
        if (SessionFrame.sf == null) {
            SessionFrame.sf = new SessionFrame();
        }
        return sf;
    }

    public void ShowFrame() {
        RefreshFrame();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);
                this.setAlwaysOnTop(true);
        setVisible(true);
    }

    public void HideFrame() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);
        setVisible(false);
    }

    public void RefreshFrame() {
        //try {
            repaint();
            //lbCurrentSessionName.setText(SessionManager.getSessionManager().getSessionCourante().getNom());
        //} catch (SQLException ex) {
        //    Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
        //}
        c.removeAll();
        disableOldSessionsButtons();
        try {
            SessionManager sm = SessionManager.getSessionManager();
            for (Iterator<Session> it = sm.getLesSessions().iterator(); it.hasNext();) {
                Session s = it.next();
                c.add("" + s.getId() + " - " + s.getNom() + " - " + s.getDebut());
                enableOldSessionsButtons();
            }

            c.select(c.getItemCount() - 1);
            if (getSessionSelectionnee() != null) {
                if (getSessionSelectionnee().getLastExport() == null) {
                    lbWarningUnexported.setVisible(true);
                } else {
                    lbWarningUnexported.setVisible(false);
                }

                if ((getSessionSelectionnee().getId()!=SessionManager.getSessionManager().getSessionCourante().getId()) || (!getSessionSelectionnee().getActive())){
                    // Already closed
                    btCloseSessionCurrentSession.setEnabled(false);
                } else {
                    btCloseSessionCurrentSession.setEnabled(true);
                }
            } else {
                // Pas de sessions passées
                c.add(Lang.getLang().getValueFromRef("SessionFrame.noOldSessions"));
                txtAreaSelectedOldSession.setText("SessionFrame.noAnswers");
            }

        } catch (SQLException ex) {
            Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
        }



        //Maj language ---
        title = Lang.getLang().getValueFromRef("SessionFrame.title");
        labelCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelCurrentSession");

        labelSessionsList = Lang.getLang().getValueFromRef("SessionFrame.labelSessionsList");
        labelSelectedSession = Lang.getLang().getValueFromRef("SessionFrame.labelSelectedSession");

        labelBtConsultCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtConsultCurrentSession");
        labelBtExportCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtExportCurrentSession");
        //labelBtPauseCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtPauseCurrentSession");
        labelBtRetourDePauseCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtRetourDePauseCurrentSession");
        labelBtNewSessionCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtNewSessionCurrentSession");
        labelBtCloseSessionCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtCloseSessionCurrentSession");
        toolTipBtConsultCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtConsultCurrentSession");
        toolTipBtExportCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtExportCurrentSession");
        //toolTipBtPauseCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtPauseCurrentSession");
        toolTipBtNewSessionCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtNewSessionCurrentSession");
        toolTipBtCloseSessionCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtCloseSessionCurrentSession");
        textInactiveTimer = Lang.getLang().getValueFromRef("SessionFrame.textInactiveTimer");
        labelOldSessions = Lang.getLang().getValueFromRef("SessionFrame.labelOldSessions");
        labelBtConsultOldSessions = Lang.getLang().getValueFromRef("SessionFrame.labelBtConsultOldSessions");
        labelBtExportOldSessions = Lang.getLang().getValueFromRef("SessionFrame.labelBtExportOldSessions");
        labelBtDeleteOldSessions = Lang.getLang().getValueFromRef("SessionFrame.labelBtDeleteOldSessions");
        toolTipBtConsultOldSessions = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtConsultOldSessions");
        toolTipBtExportOldSessions = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtExportOldSessions");
        toolTipBtDeleteOldSessions = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtDeleteOldSessions");
        lbUnexportedSessionWarning = Lang.getLang().getValueFromRef("SessionFrame.lbUnexportedSessionWarning");
        labelBtOk = Lang.getLang().getValueFromRef("SessionFrame.labelBtOk");
        lbErrorNbHoursMessage = Lang.getLang().getValueFromRef("SessionFrame.lbErrorNbHoursMessage");
        labelName = Lang.getLang().getValueFromRef("SessionFrame.labelName");
        labelStatus = Lang.getLang().getValueFromRef("SessionFrame.labelStatus");
        labelStatusActive = Lang.getLang().getValueFromRef("SessionFrame.labelStatusActive");
        labelStatusPaused = Lang.getLang().getValueFromRef("SessionFrame.labelStatusPaused");
        labelStatusClosed = Lang.getLang().getValueFromRef("SessionFrame.labelStatusClosed");

        labelBtVisualize = Lang.getLang().getValueFromRef("SessionFrame.labelBtVisualize");
        toolTipBtVisualize = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtVisualize");

        // ---

        DBConnexion conn = DBConnexion.getConnexion();

            if (getSessionSelectionnee()!=null){
                    txtAreaSelectedOldSession.setText(conn.getEntriesStringBySession(getSessionSelectionnee()));
                    if (txtAreaSelectedOldSession.getText().equals(""))
                        txtAreaSelectedOldSession.setText(Lang.getLang().getValueFromRef("SessionFrame.noAnswers"));
            } else {
                        txtAreaSelectedOldSession.setText("");
            }

            if (txtAreaSelectedOldSession.getText().equals(""))
                    txtAreaSelectedOldSession.setText(Lang.getLang().getValueFromRef("SessionFrame.noAnswers"));

        lbCurrentSession.setText(labelCurrentSession);

        lbSessionsList.setText(labelSessionsList);
        lbSelectedSession.setText(labelSelectedSession);

        lbCurrentSessionName.setText(getSessionSelectionnee().getNom());

        /*try {
            if (SessionManager.getSessionManager().getSessionCourante().getPause()) {
                btPauseCurrentSession.setText(labelBtRetourDePauseCurrentSession);
            } else {
                btPauseCurrentSession.setText(labelBtPauseCurrentSession);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        
        //btPauseCurrentSession.setToolTipText(toolTipBtPauseCurrentSession);
        btExportCurrentSession.setText(labelBtExportCurrentSession);
        btExportCurrentSession.setToolTipText(toolTipBtExportCurrentSession);
        btNewSessionCurrentSession.setText(labelBtNewSessionCurrentSession);
        btNewSessionCurrentSession.setToolTipText(toolTipBtNewSessionCurrentSession);
        btCloseSessionCurrentSession.setText(labelBtCloseSessionCurrentSession);
        btCloseSessionCurrentSession.setToolTipText(toolTipBtCloseSessionCurrentSession);
        lbOldSessions.setText(labelOldSessions);
        lbWarningUnexported.setText(lbUnexportedSessionWarning);
        btExportOldSessions.setText(labelBtExportOldSessions);
        btExportOldSessions.setToolTipText(toolTipBtExportOldSessions);
        btDeleteOldSessions.setText(labelBtDeleteOldSessions);
        btDeleteOldSessions.setToolTipText(toolTipBtDeleteOldSessions);

        btVisualize.setText(labelBtVisualize);
        btVisualize.setToolTipText(toolTipBtVisualize);

        lbName.setText(labelName);
        lbStatus.setText(labelStatus);
            if (!SessionFrame.getFrame().getSessionSelectionnee().getActive()) {
                lbStatusValue.setText(labelStatusClosed);
                lbStatusValue.setForeground(Color.YELLOW);
            } else if (SessionFrame.getFrame().getSessionSelectionnee().getPause()) {
                lbStatusValue.setText(labelStatusPaused);
                lbStatusValue.setForeground(Color.YELLOW);
            } else {
                lbStatusValue.setText(labelStatusActive);
                lbStatusValue.setForeground(Color.GREEN);
            }





    }

    public Session getSessionSelectionnee() {
        try {
            if (c.getSelectedIndex() > -1) {
                return SessionManager.getSessionManager().getLesSessions().get(c.getSelectedIndex());
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Date getDateDelaySelectionnee() {
        return (Date) spinner.getValue();
    }

    private void enableOldSessionsButtons() {
        btExportOldSessions.setEnabled(true);
        btDeleteOldSessions.setEnabled(true);
    }

    private void disableOldSessionsButtons() {
        btExportOldSessions.setEnabled(false);
        btDeleteOldSessions.setEnabled(false);
    }
/*
    public void launchChrono() {
      if (!chrono.getText().equals("")){
        try {

            btPauseCurrentSession.setText(SessionFrame.labelBtRetourDePauseCurrentSession);
            SessionManager.getSessionManager().getSessionCourante().setPause(true);
            TaskTrayMenu.sessionItem.setLabel(TaskTrayMenu.SessionInPauseItemLabel);
            TaskTrayMenu.sessionItem.setFont(new Font("Verdana", Font.BOLD, 12));
            RefreshFrame();
            TaskTrayMenu.pauseSession1HItem.setEnabled(false);
            TaskTrayMenu.pauseSession3HItem.setEnabled(false);
            TaskTrayMenu.pauseSession24HItem.setEnabled(false);
            TaskTrayMenu.resumeSessionItem.setEnabled(true);
        } catch (SQLException ex) {
            Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        chrono.setEditable(false);
        final long current = System.currentTimeMillis();
        try {
            final long limit = Integer.parseInt(chrono.getText().trim()) * 3600000; // X minutes

            ActionListener al = new ActionListener() {

                public void actionPerformed(ActionEvent event) {
                    long time = System.currentTimeMillis();
                    long passed = time - current;
                    long remaining = limit - passed;
                    if (remaining <= 0) {
                        try {
                            SessionManager.getSessionManager().getSessionCourante().setPause(false);
                            chrono.setEditable(true);
                            RefreshFrame();
                        } catch (SQLException ex) {
                            Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        chrono.setText(textInactiveTimer);
                        timer.stop();
                        btPauseCurrentSession.setText(labelBtPauseCurrentSession);
                        TaskTrayMenu.sessionItem.setLabel(TaskTrayMenu.SessionItemLabel);
                        TaskTrayMenu.sessionItem.setFont(new Font("Verdana", Font.PLAIN, 12));
                        TaskTrayMenu.pauseSession1HItem.setEnabled(true);
                        TaskTrayMenu.pauseSession3HItem.setEnabled(true);
                        TaskTrayMenu.pauseSession24HItem.setEnabled(true);
                        TaskTrayMenu.resumeSessionItem.setEnabled(false);
                        chrono.setEditable(false);
                    } else {
                        long seconds = remaining / 1000;
                        long minutes = seconds / 60;
                        long hours = minutes / 60;
                        chrono.setText(String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60));
                    }
                }
            };
            timer = new Timer(1000, al);
            timer.start();

        } catch (Exception e) {
            btPauseCurrentSession.setText(SessionFrame.labelBtPauseCurrentSession);
                try {
                    SessionManager.getSessionManager().getSessionCourante().setPause(false);
                } catch (SQLException ex) {
                    Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            javax.swing.JOptionPane.showMessageDialog(null, lbErrorNbHoursMessage);

            chrono.setEditable(true);
            chrono.setText("");
            chrono.requestFocus();

            TaskTrayMenu.sessionItem.setLabel(TaskTrayMenu.SessionItemLabel);
            TaskTrayMenu.sessionItem.setFont(new Font("Verdana", Font.PLAIN, 12));
            TaskTrayMenu.pauseSession1HItem.setEnabled(true);
            TaskTrayMenu.pauseSession3HItem.setEnabled(true);
            TaskTrayMenu.pauseSession24HItem.setEnabled(true);
            TaskTrayMenu.resumeSessionItem.setEnabled(false);
            RefreshFrame();
        }
        }
    }

    public void resetChrono() {
        try {
            SessionManager.getSessionManager().getSessionCourante().setPause(false);
            TaskTrayMenu.pauseSession1HItem.setEnabled(true);
            TaskTrayMenu.pauseSession3HItem.setEnabled(true);
            TaskTrayMenu.pauseSession24HItem.setEnabled(true);
            TaskTrayMenu.resumeSessionItem.setEnabled(false);
            RefreshFrame();
            if (timer != null) {
                timer.stop();
            }
            chrono.setText(textInactiveTimer);
            chrono.setEditable(true);
        } catch (SQLException ex) {
            Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
*/
    
    public void setNbHoursOfDelay(int nbH){
        /*chrono.setText(""+nbH);
        btPauseCurrentSession.doClick();*/
        instantReprise=new Date().getTime()+nbH*3600000;
        //System.out.println("Reprise à : "+new Date(instantReprise));
        try {
            SessionManager.getSessionManager().getSessionCourante().setPause(true);
            TaskTrayMenu.sessionItem.setLabel(TaskTrayMenu.SessionInPauseItemLabel);
            TaskTrayMenu.sessionItem.setFont(new Font("Verdana", Font.BOLD, 12));
            RefreshFrame();
            TaskTrayMenu.pauseSession1HItem.setEnabled(false);
            TaskTrayMenu.pauseSession3HItem.setEnabled(false);
            TaskTrayMenu.pauseSession24HItem.setEnabled(false);
            TaskTrayMenu.pauseSessionCustomItem.setEnabled(false);
            TaskTrayMenu.resumeSessionItem.setEnabled(true);            
        } catch (SQLException ex) {
            Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void leavePause() {
            //SessionFrame.btPauseCurrentSession.setText(SessionFrame.labelBtPauseCurrentSession);
            TaskTrayMenu.sessionItem.setLabel(TaskTrayMenu.SessionItemLabel);
            TaskTrayMenu.sessionItem.setFont(new Font("Verdana", Font.PLAIN, 12));
            TaskTrayMenu.pauseSession1HItem.setEnabled(true);
            TaskTrayMenu.pauseSession3HItem.setEnabled(true);
            TaskTrayMenu.pauseSession24HItem.setEnabled(true);
            TaskTrayMenu.pauseSessionCustomItem.setEnabled(true);
            TaskTrayMenu.resumeSessionItem.setEnabled(false);
            resetInstantReprise();
            RefreshFrame();
    }

    public void resetInstantReprise() {
        try {
            SessionManager.getSessionManager().getSessionCourante().setPause(false);
        } catch (SQLException ex) {
            Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        instantReprise=-1;
    }

    public Boolean isTimeToResumeSession(){
        if ((instantReprise==-1) || (new Date().getTime()>=instantReprise))
            return true;
        else
            return false;
    }

}
