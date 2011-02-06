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
import api.xml.Utils;
import exceptions.BadXMLFileException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.xml.parsers.ParserConfigurationException;
import model.Question;
import model.Reponse;
import model.SessionManager;
import org.xml.sax.SAXException;

/**
 * Class AskFrame.java
 * @description Frame to ask questions and get user's answers
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @author Bertrand Gros    <gros.bertrand@gmail.com>
 * @version 2011-01-28
 */
public class AskFrame extends GenericFrame {

    private static interiorPanel thePanel = null;
    Timer hideCD = null;
    public static String labelButtonValider = Lang.getLang().getValueFromRef("QuestionFrame.labelButtonValider");
    public static String helpCtrlMaj = Lang.getLang().getValueFromRef("QuestionFrame.helpCtrlMaj");
    public static String appTitle = Lang.getLang().getValueFromRef("QuestionFrame.appTitle");

    private static class interiorPanel extends JPanel {


        AskFrame listeners = null;
        public JTextField jta1 = null;
        public JTextArea jta2 = null;
        public static JButton b1 = null;
        public static JLabel lbCtrlMaj = null;

        public interiorPanel(AskFrame param) {
            super();
            this.listeners = param;
            this.setLayout(new BorderLayout(0, 10));
            this.setBackground(new Color(178,34,34));
            JLabel logo= new JLabel(new ImageIcon("media/describe-title.jpg"));
            JPanel jpUp = new JPanel();
            jpUp.setLayout(new GridLayout(2,1));
            //logo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            jpUp.add(logo);
            jpUp.add(this.firstTextField());
            jpUp.setBackground(Color.white);
           
            this.add(jpUp, BorderLayout.NORTH);
            this.add(this.secondTextField(), BorderLayout.CENTER);
            this.add(this.ButtonsRow(), BorderLayout.SOUTH);
            this.initThePanel();
        }

        private JPanel oneRow() {
            JPanel res = new JPanel();
            res.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            return res;
        }

        private JPanel firstTextField() {
            //Proposition : if "one line" question use JTextfield, if "multiple lines" question use JTextarea
            JPanel res = this.oneRow();
            jta1 = new JTextField();
            jta1.setEditable(false);
            //jta1.setLineWrap(false);
            jta1.setBackground(Color.lightGray);
            jta1.setFont(new Font("Verdana", Font.BOLD, 16));
            jta1.setForeground(Color.BLUE);
            jta1.setHorizontalAlignment(JTextField.CENTER);
            JScrollPane jsp = new JScrollPane(jta1);
            jsp.setPreferredSize(new Dimension(400, 150));
            res.add(jsp);
            res.setBackground(new Color(178,34,34));
            return res;
        }

        private JPanel secondTextField() {
            JPanel res = this.oneRow();
            jta2 = new JTextArea();
            jta2.setLineWrap(true);
            jta2.addKeyListener(listeners);
            JScrollPane jsp = new JScrollPane(jta2);
            jsp.setPreferredSize(new Dimension(400, 150));
            res.add(jsp);
            res.setBackground(new Color(178,34,34));
            return res;
        }

        private JPanel ButtonsRow() {
            JPanel res = this.oneRow();
            res.setLayout(new GridLayout(2, 1));
            lbCtrlMaj = new JLabel(helpCtrlMaj);
            lbCtrlMaj.setFont(new Font("Verdana", Font.PLAIN, 10));
            lbCtrlMaj.setForeground(Color.white);
            b1 = new JButton(labelButtonValider); //i18n
            b1.addActionListener(listeners);
            res.add(lbCtrlMaj);
            res.add(b1);
            res.setBackground(new Color(178,34,34));
            return res;
        }

        public void initThePanel() {
        }
    }

    private AskFrame() {
        super("DEScribe"); //i18n
        thePanel = new interiorPanel(this);
        getContentPane().add(thePanel);
        thePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        pack();
    }
    private static AskFrame instance;

    public static AskFrame getTheFrame() {
        if (instance == null) {
            instance = new AskFrame();
        }
        return instance;
    }

    public static void setText1(String param) {
        thePanel.jta1.setText(param);
    }

    public static String getText1() {
        return thePanel.jta1.getText();
    }

    public static void setText2(String param) {
        thePanel.jta2.setText(param);
    }

    public static String getText2() {
        return thePanel.jta2.getText();
    }

    @Override
    public void showTheFrame() {
        this.refresh();

        hideCD = new Timer();
        TimerTask taskCD = new TimerTask() {

            @Override
            public void run() {
                AskFrame.getTheFrame().hideTheFrame();
            }
        };
        hideCD.schedule(taskCD, 1200000);
        thePanel.jta2.requestFocus();
        this.setLocation((x - this.getSize().width) / 2, (y - this.getSize().height) / 2);
        if (OptionFrame.isSoundEnabled()) {
            Toolkit.getDefaultToolkit().beep();
        }
        this.setVisible(true);
    }

    private void refresh() {
        try {
            ArrayList<Question> lesQuestions = new ArrayList<Question>();
            lesQuestions = Utils.importFormXML();
            setText1(lesQuestions.get(0).intitule);
            setText2("");
            labelButtonValider = Lang.getLang().getValueFromRef("QuestionFrame.labelButtonValider");
            interiorPanel.b1.setText(labelButtonValider);

            helpCtrlMaj = Lang.getLang().getValueFromRef("QuestionFrame.helpCtrlMaj");
            interiorPanel.lbCtrlMaj.setText(helpCtrlMaj);

            appTitle = Lang.getLang().getValueFromRef("QuestionFrame.appTitle");
            this.setTitle(appTitle);
            
        } catch (BadXMLFileException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if (s.equals(labelButtonValider)) {
            try {
                DBConnexion conn = DBConnexion.getConnexion();
                SessionManager sm = SessionManager.getSessionManager();
                Date maDate = new Date();
                Reponse rep = new Reponse(AskFrame.getText1(), AskFrame.getText2(), maDate, sm.getSessionCourante());
                conn.newAddEntry(rep);
                AskFrame.setText2("");
                this.hideTheFrame();
            } catch (Exception ex) {
                javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void keyTyped(KeyEvent e) {
        hideCD.cancel();
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        if (e.getModifiers() == KeyEvent.CTRL_MASK && e.getKeyCode() == KeyEvent.VK_ENTER) {
            thePanel.b1.doClick();
        }
    }
}