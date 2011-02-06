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

package api.gui;

import api.dbc.DBConnexion;
import api.i18n.Lang;
import java.awt.BorderLayout;
import java.awt.List;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import model.Session;
import model.SessionManager;

/**
 * Class SessionFrame.java
 * @description Session management frame
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @author Bertrand Gros    <gros.bertrand@gmail.com>
 * @version 2011-01-29
 */
public class SessionFrame extends JFrame {

    private static String title = Lang.getLang().getValueFromRef("SessionFrame.title");
    static protected SessionFrame sf = null;

    private List c;
    /*private Label lbSessions;
    private Label lbnbsession;
    private Label sessioncourante;
    private Label lbinvitChoixSession;
    private Label lbinvitChoixDateDelay;*/
    private JSpinner spinner;
    public static Label lbCurrentSession;
    public static Label lbCurrentSessionName;
    public static String labelCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelCurrentSession");
    public static String labelCurrentSessionName;
    private static JButton btConsultCurrentSession = null;
    private static JButton btExportCurrentSession = null;
    public static JButton btPauseCurrentSession = null;
    private static JButton btNewSessionCurrentSession = null;
    private static JButton btCloseSessionCurrentSession = null;
    public static String labelBtConsultCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtConsultCurrentSession");
    public static String labelBtExportCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtExportCurrentSession");
    public static String labelBtPauseCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtPauseCurrentSession");
    public static String labelBtRetourDePauseCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtRetourDePauseCurrentSession");
    public static String labelBtNewSessionCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtNewSessionCurrentSession");
    public static String labelBtCloseSessionCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtCloseSessionCurrentSession");
    public static String toolTipBtConsultCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtConsultCurrentSession");
    public static String toolTipBtExportCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtExportCurrentSession");
    public static String toolTipBtPauseCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtPauseCurrentSession");
    public static String toolTipBtNewSessionCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtNewSessionCurrentSession");
    public static String toolTipBtCloseSessionCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtCloseSessionCurrentSession");
    private JTextField chrono;
    private Timer timer;
    private static String textInactiveTimer = Lang.getLang().getValueFromRef("SessionFrame.textInactiveTimer");
    public static String labelOldSessions = Lang.getLang().getValueFromRef("SessionFrame.labelOldSessions");
    private static JButton btConsultOldSessions = null;
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


    public SessionFrame() {

        // LA FENETRE PRINCIPALE
        this.setTitle(title);
        this.setIconImage(new ImageIcon("media/des.gif").getImage());
        this.setResizable(true);
        this.addWindowListener(Listeners.getListeners());



        /**
         * New version
         */
        JPanel panelUp = new JPanel();
        panelUp.setBorder(BorderFactory.createLineBorder(Color.GRAY,4));
        panelUp.setLayout(new GridLayout(4,1));
        JPanel jpUpCurrentSession = new JPanel();
        jpUpCurrentSession.setLayout(new FlowLayout());

        jpUpCurrentSession.setBackground(new Color(178,34,34));
        lbCurrentSession = new Label(labelCurrentSession);
        lbCurrentSession.setFont(new Font("Verdana", Font.BOLD, 30));
        lbCurrentSession.setForeground(Color.white);
        lbCurrentSession.setBackground(new Color(178,34,34));
        jpUpCurrentSession.add(lbCurrentSession);
        JPanel jpUpCurrentSessionInfo = new JPanel();
        jpUpCurrentSessionInfo.setLayout(new FlowLayout());
        try {
            lbName = new Label (labelName);
            jpUpCurrentSessionInfo.add(lbName);

            lbName.setForeground(Color.white);
            lbName.setBackground(new Color(178,34,34));
            lbName.setFont(new Font("Verdana", Font.PLAIN, 14));

            lbCurrentSessionName = new Label(SessionManager.getSessionManager().getSessionCourante().getNom());
            jpUpCurrentSessionInfo.add(lbCurrentSessionName);
            jpUpCurrentSessionInfo.setBackground(new Color(178,34,34));
            lbCurrentSessionName.setForeground(Color.white);
            lbCurrentSessionName.setBackground(new Color(178,34,34));
            lbCurrentSessionName.setFont(new Font("Verdana", Font.BOLD, 14));

            lbStatus = new Label (labelStatus);
            jpUpCurrentSessionInfo.add(lbStatus);
            lbStatus.setForeground(Color.white);
            lbStatus.setBackground(new Color(178,34,34));
            lbStatus.setFont(new Font("Verdana", Font.PLAIN, 14));

            lbStatusValue = new Label (labelStatusActive);
            jpUpCurrentSessionInfo.add(lbStatusValue);
            lbStatusValue.setForeground(Color.GREEN);
            lbStatusValue.setBackground(new Color(178,34,34));
            lbStatusValue.setFont(new Font("Verdana", Font.BOLD, 14));
            
        } catch (SQLException ex) {
            Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        txtAreaCurrentSession = new JTextArea();
        txtAreaCurrentSession.setEditable(false);

        JScrollPane jsp = new JScrollPane(txtAreaCurrentSession);
        jsp.setBackground(Color.LIGHT_GRAY);
        jsp.setPreferredSize(new Dimension(500, 70));



        JPanel jpUpFlowBt = new JPanel();
        jpUpFlowBt.setLayout(new FlowLayout());
        btPauseCurrentSession = new JButton(labelBtPauseCurrentSession);
        btPauseCurrentSession.addActionListener(Listeners.getListeners());
        jpUpFlowBt.add(btPauseCurrentSession);
        btPauseCurrentSession.setToolTipText(toolTipBtPauseCurrentSession);
        chrono = new JTextField(textInactiveTimer, 8);
        chrono.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                //TODO : effacer le texte
                chrono.setSelectionStart(0);
                chrono.setSelectionEnd(chrono.getText().length());
            }
        });

        jpUpFlowBt.add(chrono);
        btExportCurrentSession = new JButton(labelBtExportCurrentSession);
        btExportCurrentSession.addActionListener(Listeners.getListeners());
        jpUpFlowBt.add(btExportCurrentSession);
        btExportCurrentSession.setToolTipText(toolTipBtExportCurrentSession);

        btNewSessionCurrentSession = new JButton(labelBtNewSessionCurrentSession);
        btNewSessionCurrentSession.addActionListener(Listeners.getListeners());
        jpUpFlowBt.add(btNewSessionCurrentSession);
        btNewSessionCurrentSession.setToolTipText(toolTipBtNewSessionCurrentSession);

        btCloseSessionCurrentSession = new JButton(labelBtCloseSessionCurrentSession);
        btCloseSessionCurrentSession.addActionListener(Listeners.getListeners());
        jpUpFlowBt.add(btCloseSessionCurrentSession);
        btCloseSessionCurrentSession.setToolTipText(toolTipBtCloseSessionCurrentSession);

        jpUpFlowBt.setBackground(new Color(178,34,34));

        panelUp.add(jpUpCurrentSession);
        panelUp.add(jpUpCurrentSessionInfo);
        panelUp.add(jpUpFlowBt);
        panelUp.add(jsp);
        panelUp.setBackground(new Color(178,34,34));

        JPanel panelDown = new JPanel();
        panelDown.setBorder(BorderFactory.createLineBorder(Color.GRAY,4));

        panelDown.setLayout(new GridLayout(5,1));
        JPanel jpDownOldSessions = new JPanel();
        jpDownOldSessions.setLayout(new FlowLayout());

        jpDownOldSessions.setBackground(new Color(178,34,34));
        lbOldSessions = new Label(labelOldSessions);
        lbOldSessions.setFont(new Font("Verdana", Font.BOLD, 30));
        lbOldSessions.setForeground(Color.white);
        lbOldSessions.setBackground(new Color(178,34,34));
        jpDownOldSessions.add(lbOldSessions);
        panelDown.add(jpDownOldSessions);

        lbWarningUnexported = new Label(lbUnexportedSessionWarning);
        lbWarningUnexported.setFont(new Font("Verdana", Font.BOLD, 20));
        lbWarningUnexported.setForeground(Color.YELLOW);
        lbWarningUnexported.setBackground(new Color(178,34,34));

        c = new List();
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
                    if (getSessionSelectionnee().getLastExport() == null) {
                        lbWarningUnexported.setVisible(true);
                        //SessionFrame.getFrame().pack();
                    } else {
                        lbWarningUnexported.setVisible(false);
                        //SessionFrame.getFrame().pack();
                    }
                    DBConnexion conn = DBConnexion.getConnexion();
                    txtAreaSelectedOldSession.setText(conn.getEntriesStringBySession(getSessionSelectionnee()));

                }
            }
        });

        panelDown.add(c);

        JPanel jpDownFlowBt = new JPanel();
        jpDownFlowBt.setLayout(new FlowLayout());
        btExportOldSessions = new JButton(labelBtExportOldSessions);
        btExportOldSessions.addActionListener(Listeners.getListeners());
        jpDownFlowBt.add(btExportOldSessions);
        btExportOldSessions.setToolTipText(toolTipBtExportOldSessions);

        btDeleteOldSessions = new JButton(labelBtDeleteOldSessions);
        btDeleteOldSessions.addActionListener(Listeners.getListeners());
        jpDownFlowBt.add(btDeleteOldSessions);
        btDeleteOldSessions.setToolTipText(toolTipBtDeleteOldSessions);

        jpDownFlowBt.setBackground(new Color(178,34,34));
        panelDown.add(jpDownFlowBt);
        panelDown.add(lbWarningUnexported);
        panelDown.setBackground(new Color(178,34,34));
        txtAreaSelectedOldSession = new JTextArea();
        txtAreaSelectedOldSession.setEditable(false);
        JScrollPane jsp2 = new JScrollPane(txtAreaSelectedOldSession);
        jsp2.setBackground(Color.LIGHT_GRAY);
        jsp2.setPreferredSize(new Dimension(500, 70));

        panelDown.add(jsp2);

        DBConnexion conn = DBConnexion.getConnexion();
        try {
            txtAreaCurrentSession.setText(conn.getEntriesStringBySession(SessionManager.getSessionManager().getSessionCourante()));
        } catch (SQLException ex) {
            Logger.getLogger(Listeners.class.getName()).log(Level.SEVERE, null, ex);
        }




        JPanel panelOK = new JPanel();
        btOk= new JButton(labelBtOk);
        btOk.addActionListener(Listeners.getListeners());
        panelOK.setBackground(new Color(178,34,34));
        panelOK.add(btOk);
 
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        getContentPane().add(panel);

        panel.add(panelUp, BorderLayout.NORTH);
        panel.add(panelDown, BorderLayout.CENTER);
        panel.add(panelOK, BorderLayout.SOUTH);

        this.setPreferredSize(new Dimension(1000,672));
       
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
        setVisible(true);
    }

    public void HideFrame() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);
        setVisible(false);
    }

    public void RefreshFrame() {
        try {
            repaint();
            lbCurrentSessionName.setText(SessionManager.getSessionManager().getSessionCourante().getNom());
        } catch (SQLException ex) {
            Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        c.removeAll();
        disableOldSessionsButtons();
        try {
            SessionManager sm = SessionManager.getSessionManager();
            for (Iterator<Session> it = sm.getLesSessions().iterator(); it.hasNext();) {
                Session s = it.next();
                if (!s.getActive()) {
                    c.add("" + s.getId() + " - " + s.getNom() + " - " + s.getDebut());
                    enableOldSessionsButtons();
                }

            }

            c.select(c.getItemCount() - 1);
            if (getSessionSelectionnee() != null) {
                if (getSessionSelectionnee().getLastExport() == null) {
                    lbWarningUnexported.setVisible(true);
                } else {
                    lbWarningUnexported.setVisible(false);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
        }



        //Maj language ---
        title = Lang.getLang().getValueFromRef("SessionFrame.title");
        labelCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelCurrentSession");
        labelBtConsultCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtConsultCurrentSession");
        labelBtExportCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtExportCurrentSession");
        labelBtPauseCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtPauseCurrentSession");
        labelBtRetourDePauseCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtRetourDePauseCurrentSession");
        labelBtNewSessionCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtNewSessionCurrentSession");
        labelBtCloseSessionCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.labelBtCloseSessionCurrentSession");
        toolTipBtConsultCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtConsultCurrentSession");
        toolTipBtExportCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtExportCurrentSession");
        toolTipBtPauseCurrentSession = Lang.getLang().getValueFromRef("SessionFrame.toolTipBtPauseCurrentSession");
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


        // ---

        DBConnexion conn = DBConnexion.getConnexion();
        try {
            txtAreaCurrentSession.setText(conn.getEntriesStringBySession(SessionManager.getSessionManager().getSessionCourante()));
            if (getSessionSelectionnee()!=null){
                    txtAreaSelectedOldSession.setText(conn.getEntriesStringBySession(getSessionSelectionnee()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Listeners.class.getName()).log(Level.SEVERE, null, ex);
        }
        lbCurrentSession.setText(labelCurrentSession);
        try {
            lbCurrentSessionName.setText(SessionManager.getSessionManager().getSessionCourante().getNom());
        } catch (SQLException ex) {
            Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (SessionManager.getSessionManager().getSessionCourante().getPause()) {
                btPauseCurrentSession.setText(labelBtRetourDePauseCurrentSession);
            } else {
                btPauseCurrentSession.setText(labelBtPauseCurrentSession);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        btPauseCurrentSession.setToolTipText(toolTipBtPauseCurrentSession);
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

        lbName.setText(labelName);
        lbStatus.setText(labelStatus);
        try {
            if (!SessionManager.getSessionManager().getSessionCourante().getActive()) {
                lbStatusValue.setText(labelStatusClosed);
                lbStatusValue.setForeground(Color.YELLOW);
            } else if (SessionManager.getSessionManager().getSessionCourante().getPause()) {
                lbStatusValue.setText(labelStatusPaused);
                lbStatusValue.setForeground(Color.YELLOW);
            } else {
                lbStatusValue.setText(labelStatusActive);
                lbStatusValue.setForeground(Color.GREEN);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
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

    public void launchChrono() {
      if (!chrono.getText().equals("")){
        try {

            btPauseCurrentSession.setText(SessionFrame.labelBtRetourDePauseCurrentSession);
            SessionManager.getSessionManager().getSessionCourante().setPause(true);
            TaskTrayMenu.sessionItem.setLabel(TaskTrayMenu.SessionInPauseItemLabel);
            TaskTrayMenu.sessionItem.setFont(new Font("Verdana", Font.BOLD, 12));
            RefreshFrame();
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
            RefreshFrame();
        }
        }
    }

    public void resetChrono() {
        try {
            SessionManager.getSessionManager().getSessionCourante().setPause(false);
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
}
