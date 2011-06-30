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
import api.time.TimerQuestion;
import api.utils.ImgTxtMerger;
import api.xml.Utils;
import com.sun.management.OperatingSystemMXBean;
import exceptions.BadXMLFileException;
import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.Action;
import model.ActionScreenshot;
import model.QCMChkBox;
import model.QCMChoice;
import model.QCMRadio;
import model.QReponseLibre;
import model.Question;
import model.Regle;
import model.Reponse;
import model.SessionManager;
import sun.management.ManagementFactory;

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

    public static String labelButtonSkip = Lang.getLang().getValueFromRef("QuestionFrame.labelButtonSkip");
    public static String toolTipButtonSkip = Lang.getLang().getValueFromRef("QuestionFrame.toolTipButtonSkip");

    public static String toolTipLogo = Lang.getLang().getValueFromRef("QuestionFrame.toolTipLogo");

    /** Full screenshot file name **/
    private String absoluteScreenshotFilePath="";

    private Question currentQuestion=null;

    private static int EVENT_CPY_PROBA = 20;

    private Regle currentRegle=null;

    private static class interiorPanel extends JPanel {

        AskFrame listeners = null;
        public JTextField jta1 = null;
        public JTextArea jta2 = null;
        public static JButton b1 = null;
        public static JLabel lbCtrlMaj = null;

        public ButtonGroup bg2 = null;
        public JPanel jpMiddle = null;
        
        public JScrollPane jspMid = null;

        public AnswerTextField jtaOther = null;

        public static JButton buttonSkip = null;
        public JLabel logo = null;
        public JPanel jpUp= null;
        public interiorPanel(AskFrame param) {
            super();
            this.listeners = param;
            this.setLayout(new BorderLayout(0, 10));
            this.setBackground(new Color(178,34,34));
            logo= new JLabel(new ImageIcon("media/describe-title.jpg"));
            logo.setToolTipText(toolTipLogo);
            logo.addMouseMotionListener(new MouseMotionListener() {

                public void mouseDragged(MouseEvent e) {
                    //AskFrame.getTheFrame().setLocation(AskFrame.getTheFrame().getLocation().x+e.getX()-(logo.getIcon().getIconWidth()/2), AskFrame.getTheFrame().getLocation().y+e.getY()-(logo.getIcon().getIconHeight()/2));
                    AskFrame.getTheFrame().setLocation(AskFrame.getTheFrame().getLocation().x+e.getX()-(jpUp.getWidth()/2), AskFrame.getTheFrame().getLocation().y+e.getY()-(jpUp.getHeight()/2));
                }

                public void mouseMoved(MouseEvent e) {
                }
            });
            logo.addMouseListener(new MouseListener() {

                public void mouseClicked(MouseEvent e) {

                }

                public void mousePressed(MouseEvent e) {

                }

                public void mouseReleased(MouseEvent e) {

                }

                public void mouseEntered(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR) );;
                }

                public void mouseExited(MouseEvent e) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR) );
                }
            });
            jpUp = new JPanel();
            jpUp.setLayout(new GridLayout(1,1));
            //logo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            jpUp.add(logo);
            //jpUp.add(this.firstTextField());
            jpUp.setBackground(Color.white);

            this.add(jpUp, BorderLayout.NORTH);
            //this.add(this.secondTextField(), BorderLayout.CENTER);
            this.add(this.ButtonsRow(), BorderLayout.SOUTH);
            this.initThePanel();
        }

        private JPanel oneRow() {
            JPanel res = new JPanel();
            res.setBorder(BorderFactory.createLineBorder(Color.WHITE));
            return res;
        }

        private JPanel firstTextField() {
            JPanel res = this.oneRow();
            jta1 = new JTextField();
            jta1.setEditable(false);
            //jta1.setLineWrap(false);
            jta1.setBackground(Color.lightGray);
            jta1.setFont(new Font("Verdana", Font.BOLD, 14));
            jta1.setForeground(Color.BLUE);
            jta1.setHorizontalAlignment(JTextField.CENTER);
            JScrollPane jsp = new JScrollPane(jta1);
            jsp.setPreferredSize(new Dimension(600, 150));
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
            jsp.setPreferredSize(new Dimension(600, 150));
            res.add(jsp);
            res.setBackground(new Color(178,34,34));
            return res;
        }

        private JPanel choicesGroup(ArrayList<QCMChoice> choices, String style) {
            Boolean other=false;
            JPanel res = new JPanel(new GridLayout(0,1));
            bg2 = new ButtonGroup();
            for (Iterator<QCMChoice> it = choices.iterator(); it.hasNext();) {
                QCMChoice qcmC = it.next();
                if (style.equals("radio")){
                    bg2.add(new JRadioButton(qcmC.getText()));
                    Enumeration<AbstractButton> en = thePanel.bg2.getElements();
                    while (en.hasMoreElements()) {
                        AbstractButton ab = en.nextElement();
                        ab.addKeyListener(listeners);
                        res.add(ab);
                    }
                } else if (style.equals("checkbox")){
                    JCheckBox jcb = new JCheckBox(qcmC.getText());
                    jcb.addKeyListener(listeners);
                    res.add(jcb);
                }
                if (qcmC.getIsOtherChoice()){
                    other=true;
                }
            }
            if (other){
                jtaOther = new AnswerTextField();
                jtaOther.addKeyListener(listeners);
                res.add(jtaOther);
            }
            //res.add(this);
            res.setPreferredSize(new Dimension(600, 150));
            res.setBackground(new Color(178,34,34));
            jpMiddle=res;
            return res;
        }

        private JPanel ButtonsRow() {
            JPanel res = this.oneRow();
            res.setLayout(new GridLayout(2, 1));
            JPanel jpBt = new JPanel(new FlowLayout());
            lbCtrlMaj = new JLabel(helpCtrlMaj);
            lbCtrlMaj.setFont(new Font("Verdana", Font.PLAIN, 10));
            lbCtrlMaj.setForeground(Color.white);
            b1 = new JButton(labelButtonValider); //i18n
            b1.addActionListener(listeners);

            buttonSkip = new JButton(labelButtonSkip);
            buttonSkip.addActionListener(listeners);
            buttonSkip.setToolTipText(toolTipButtonSkip);

            res.add(lbCtrlMaj);
            jpBt.add(b1);
            jpBt.add(new JLabel("                       "));
            jpBt.add(buttonSkip);
            res.add(jpBt);
            jpBt.setBackground(new Color(178,34,34));
            /*res.add(b1);
            res.add(buttonSkip);*/
            res.setBackground(new Color(178,34,34));
            return res;
        }

        public void initThePanel() {
        }

 public void addQuestion(Question q){
            JPanel panQuestion = new JPanel(new GridLayout(2, 1));
            panQuestion.setBackground(Color.lightGray);
            QuestionLabel jlQuest= new QuestionLabel(q.intitule);
            panQuestion.add(jlQuest);
            jlQuest.setFont(new Font("Verdana", Font.BOLD, 12));
            jlQuest.setForeground(Color.BLUE);
            jlQuest.setBackground(Color.lightGray);
            if (q instanceof QReponseLibre){
                panQuestion.add(new AnswerTextArea());
            } else if ((q instanceof  QCMChkBox) || (q instanceof QCMRadio)){
                ArrayList<QCMChoice> choices = new ArrayList<QCMChoice>();
                if (q instanceof QCMChkBox)
                    choices=((QCMChkBox)q).getChoices();
                if (q instanceof QCMRadio)
                    choices=((QCMRadio)q).getChoices();
                Boolean other=false;
                JPanel res = new JPanel(new GridLayout(0,1));
                bg2 = new ButtonGroup();
                for (Iterator<QCMChoice> it = choices.iterator(); it.hasNext();) {
                    QCMChoice qcmC = it.next();
                    if (q instanceof QCMRadio){
                        bg2.add(new JRadioButton(qcmC.getText()));
                        Enumeration<AbstractButton> en = thePanel.bg2.getElements();
                        while (en.hasMoreElements()) {
                            AbstractButton ab = en.nextElement();
                            ab.addKeyListener(listeners);
                            res.add(ab);
                        }
                    } else if (q instanceof QCMChkBox){
                        JCheckBox jcb = new JCheckBox(qcmC.getText());
                        jcb.addKeyListener(listeners);
                        res.add(jcb);
                    }
                    if (qcmC.getIsOtherChoice()){
                        other=true;
                    }
                }
                if (other){
                    jtaOther = new AnswerTextField();
                    jtaOther.addKeyListener(listeners);
                    res.add(jtaOther);
                }
                //res.add(this);
                //res.setPreferredSize(new Dimension(400, 150));
                res.setBackground(new Color(178,34,34));
                panQuestion.add(res);
            }
            panQuestion.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            jpMiddle.add(panQuestion);
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
    public void showTheFrame(String quest) {
        if (quest==null){
            currentRegle=null;
        }
        absoluteScreenshotFilePath="";
        try{
            if (thePanel.getComponent(2)!=null){
                thePanel.remove(thePanel.getComponent(2));
            }
        }
        catch (Exception ex){
            
        }
        if (quest==null)
            currentQuestion=selectQuestionFromForm();
        else if(!quest.equals("rule")){
            currentQuestion=selectQuestionFromForm();
        }
        this.refresh();



        hideCD = new Timer();
        TimerTask taskCD = new TimerTask() {

            @Override
            public void run() {
                AskFrame.getTheFrame().hideTheFrame();
            }
        };
        // After 2 minutes of inactivity, the frame disappears by itself
        hideCD.schedule(taskCD, 100000);

            ArrayList<Question> lesQuestions = new ArrayList<Question>();
        try {
            lesQuestions = Utils.importFormXML();
        } catch (BadXMLFileException ex) {
            Logger.getLogger(AskFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

            
            // If there is a question to ask
            /*if (currentQuestion!=null){
                setText1(currentQuestion.intitule);
                if (quest!=null){
                    if (!quest.equals("rule")){
                        setText1(quest);
                    }
                }
                if (currentQuestion instanceof QReponseLibre){
                    thePanel.jta2.requestFocus();
                } else if (thePanel.jtaOther!=null){
                    thePanel.jtaOther.requestFocus();
                }

                //thePanel.jta2.requestFocus();*/
                this.setLocation((x - this.getSize().width) / 2, (y - this.getSize().height) / 2);
                if (OptionFrame.isSoundEnabled()) {
                    Toolkit.getDefaultToolkit().beep();
                }
                this.pack();
                this.setVisible(true);
/*
                thePanel.jta1.requestFocus();

                //thePanel.j
                if (currentQuestion instanceof QReponseLibre){
                    thePanel.jta2.requestFocus();
                } else if (thePanel.jtaOther!=null){
                    thePanel.jpMiddle.requestFocus();
                    thePanel.jtaOther.requestFocus();
                }
            }*/

    }

    private void refresh() {
        try {

            ArrayList<Question> lesQuestions = new ArrayList<Question>();
            lesQuestions = Utils.importFormXML();
            //setText1(lesQuestions.get(0).intitule);
            /* removed! setText1(currentQuestion.intitule);*/
            //thePanel.remove(thePanel.getComponent(0));
            //Component xt[]= ((JPanel)thePanel.getComponent(2)).getComponents();
            thePanel.jpMiddle=new JPanel(new GridLayout(getAskableQuestionsFromForm().size(), 1));
            thePanel.jspMid=new JScrollPane();

            // Listen for value changes in the scroll pane's scrollbars
           // AdjustmentListener listener = new MyAdjustmentListener();
            //thePanel.jspMid.getHorizontalScrollBar().addAdjustmentListener(listener);
            //thePanel.jspMid.getVerticalScrollBar().addAdjustmentListener(listener);
            thePanel.jspMid.setPreferredSize(new Dimension(600,600));
            thePanel.jspMid.setViewportView(thePanel.jpMiddle);
            thePanel.add(thePanel.jspMid, BorderLayout.CENTER);
            //thePanel.add(thePanel.jpMiddle,BorderLayout.CENTER);
            //this.add(thePanel.jpMiddle, BorderLayout.SOUTH);
            if (thePanel.jtaOther!=null){
                thePanel.remove(thePanel.jtaOther);
            }
            /*removed! if (currentQuestion instanceof QReponseLibre){
                // Add textfield to panel
                //this.add(this.secondTextField(), BorderLayout.CENTER);
                thePanel.add(thePanel.secondTextField(), BorderLayout.CENTER);
                setText2("");
                pack();
            }
            if (currentQuestion instanceof QCMRadio){
                // Add radio buttons
                thePanel.add(thePanel.choicesGroup(((QCMRadio) currentQuestion).getChoices(), "radio"), BorderLayout.CENTER);

                pack();
            }

            if (currentQuestion instanceof QCMChkBox){
                // Add radio buttons
                thePanel.add(thePanel.choicesGroup(((QCMChkBox) currentQuestion).getChoices(), "checkbox"), BorderLayout.CENTER);
                pack();
            }*/

            loadForm();
            pack();
            labelButtonValider = Lang.getLang().getValueFromRef("QuestionFrame.labelButtonValider");
            interiorPanel.b1.setText(labelButtonValider);

            labelButtonSkip = Lang.getLang().getValueFromRef("QuestionFrame.labelButtonSkip");
            interiorPanel.buttonSkip.setText(labelButtonSkip);

            toolTipButtonSkip = Lang.getLang().getValueFromRef("QuestionFrame.toolTipButtonSkip");
            interiorPanel.buttonSkip.setToolTipText(toolTipButtonSkip);

            helpCtrlMaj = Lang.getLang().getValueFromRef("QuestionFrame.helpCtrlMaj");
            interiorPanel.lbCtrlMaj.setText(helpCtrlMaj);

            appTitle = Lang.getLang().getValueFromRef("QuestionFrame.appTitle");

            toolTipLogo=Lang.getLang().getValueFromRef("QuestionFrame.toolTipLogo");
            thePanel.logo.setToolTipText(toolTipLogo);
            this.setTitle(appTitle);

            ArrayList<Action> lesActions = new ArrayList<Action>();
            lesActions = Utils.importFormActionsXML();
            // For each action execute it

            try{
                DBConnexion conn = DBConnexion.getConnexion();
                SessionManager sm = SessionManager.getSessionManager();
                for (Iterator<Action> it = lesActions.iterator(); it.hasNext();) {
                    Action s = it.next();
                    if (s instanceof ActionScreenshot) {
                        Long t =conn.getMaxIdReponseBySession(sm.getSessionCourante())+1;
                       ((ActionScreenshot) s).takeCapture("session"+sm.getSessionCourante().getId()+"_reponse"+t+"_screenshot");
                        absoluteScreenshotFilePath = ((ActionScreenshot) s).getAbsFileName();
                    }
                }
            } catch (Exception ex) {
                javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
            }



        } catch (BadXMLFileException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void clearFrame() {
                ArrayList<Question> lesQuestions = new ArrayList<Question>();
                //Netoyage : à faire
            /*try{
                lesQuestions = Utils.importFormXML();
                if (currentQuestion instanceof QReponseLibre) {
                    AskFrame.setText2("");
                }


                if (currentQuestion instanceof QCMRadio)
                    thePanel.bg2.clearSelection();
                if (currentQuestion instanceof QCMChkBox){
                    Component t[]= ((JPanel)thePanel.getComponent(2)).getComponents();
                    for (int i =0; i<t.length; i++){
                        if (t[i] instanceof JCheckBox)
                            ((JCheckBox) ((JPanel)thePanel.getComponent(2)).getComponent(i)).setSelected(false);
                        if (t[i] instanceof JTextField){
                            ((JTextField) ((JPanel)thePanel.getComponent(2)).getComponent(i)).setText("");
                            //((JTextField) ((JPanel)thePanel.getComponent(2)).getComponent(i)).setVisible(false);
                        }
                    }
                }
            } catch (BadXMLFileException ex) {
                Logger.getLogger(AskFrame.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            hideCD.cancel();
    }


    public Question selectQuestionFromForm(){
        // Mettre les regles dans la classe Question et initialisé dans importForm.xml
//            ArrayList<Question> lesQuestions = new ArrayList<Question>();
//            Question selectedQuestion = null;
//            try{
//                lesQuestions = Utils.importFormXML();
//            } catch (BadXMLFileException ex) {
//                Logger.getLogger(AskFrame.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            //Faire des while avec deux booleens plutot pour la validité.
//
//            Boolean foundQuestion = false;
//            int j=0;
//            while ((j<lesQuestions.size()) && (!foundQuestion)){
//
//                ArrayList<Regle> lesRegles = lesQuestions.get(j).getRegles();
//
//                // Pour chaque regle on vérifie si elle est valide.
//                //Si une seule n'est pas valide la question ne l'est pas
//                // Si elles le sont toutes alors on a trouvé la question à poser
//
//                Boolean foundRuleFalse = false;
//                int k=0;
//                while ((k< lesRegles.size()) &&(!foundRuleFalse)){
//
//                    Regle r = lesRegles.get(k);
//                    if (r.getEvent().equals("fullscreen")){
//                        if (r.getType().equals("if")){
//                            //TODO
//                          /*  if (!checkEvent("fullscreen")){
//                                foundRuleFalse=true;
//                            } */
//                        } else if (r.getType().equals("notif")){
//                            //TODO
//                          /*  if (checkEvent("fullscreen")){
//                                foundRuleFalse=true;
//                            } */
//                        }
//                    } else if (r.getEvent().equals("copyImage")){
//                            //Traité dans CopyAndPasteHandler
//                            foundRuleFalse=true;
//
//                    } else if (r.getEvent().equals("copyText")){
//                            //Traité dans CopyAndPasteHandler
//                            foundRuleFalse=true;
//
//
//                    } else if (r.getEvent().equals("copyFile")){
//                            //Traité dans CopyAndPasteHandler
//                            foundRuleFalse=true;
//                    } else if (r.getEvent().equals("copyFile")){
//                            //Traité dans CopyAndPasteHandler
//                            foundRuleFalse=true;
//                    } else if (r.getEvent().equals("copy")){
//                            foundRuleFalse=true;
//                    }
//
//                    k++;
//                }
//                if (!foundRuleFalse){
//                    foundQuestion=true;
//                    selectedQuestion=lesQuestions.get(j);
//                }
//
//                j++;
//            }
//
//        return selectedQuestion;
            ArrayList<Question> lesQuestions = new ArrayList<Question>();
            ArrayList<Question> askableQuestions = new ArrayList<Question>();
            Question selectedQuestion = null;
            try{
                lesQuestions = Utils.importFormXML();
            } catch (BadXMLFileException ex) {
                Logger.getLogger(AskFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Faire des while avec deux booleens plutot pour la validité.

            Boolean foundQuestion = false;
            int j=0;
            while ((j<lesQuestions.size())){

                ArrayList<Regle> lesRegles = lesQuestions.get(j).getRegles();

                // Pour chaque regle on vérifie si elle est valide.
                //Si une seule n'est pas valide la question ne l'est pas
                // Si elles le sont toutes alors on a trouvé la question à poser

                Boolean foundRuleFalse = false;
                int k=0;
                while ((k< lesRegles.size()) &&(!foundRuleFalse)){

                    Regle r = lesRegles.get(k);
                    if (r.getEvent().equals("fullscreen")){
                        if (r.getType().equals("if")){
                            //TODO
                          /*  if (!checkEvent("fullscreen")){
                                foundRuleFalse=true;
                            } */
                        } else if (r.getType().equals("notif")){
                            //TODO
                          /*  if (checkEvent("fullscreen")){
                                foundRuleFalse=true;
                            } */
                        }
                    } else if (r.getEvent().equals("copyImage")){
                            //Traité dans CopyAndPasteHandler
                            foundRuleFalse=true;

                    } else if (r.getEvent().equals("copyText")){
                            //Traité dans CopyAndPasteHandler
                            foundRuleFalse=true;


                    } else if (r.getEvent().equals("copyFile")){
                            //Traité dans CopyAndPasteHandler
                            foundRuleFalse=true;
                    } else if (r.getEvent().equals("copyFile")){
                            //Traité dans CopyAndPasteHandler
                            foundRuleFalse=true;
                    } else if (r.getEvent().equals("copy")){
                            foundRuleFalse=true;
                    }

                    k++;
                }
                if (!foundRuleFalse){
                    foundQuestion=true;
//                    selectedQuestion=lesQuestions.get(j);
                    askableQuestions.add(lesQuestions.get(j));

                }

                j++;
            }
            //System.out.println("Nombre de questions posables_ : "+askableQuestions.size());
            if (askableQuestions.size()>0){
                int rang = (new Random().nextInt(askableQuestions.size()));
                //System.out.println("Num question choisie : "+rang);
                selectedQuestion=askableQuestions.get(rang);
            } else {
                selectedQuestion=null;
            }

        return selectedQuestion;

    }

    public Question selectQuestionFromFormWithRule(Regle reg){
        // Mettre les regles dans la classe Question et initialisé dans importForm.xml
//            ArrayList<Question> lesQuestions = new ArrayList<Question>();
//            ArrayList<Question> askableQuestions = new ArrayList<Question>();
//            Question selectedQuestion = null;
//            try{
//                lesQuestions = Utils.importFormXML();
//            } catch (BadXMLFileException ex) {
//                Logger.getLogger(AskFrame.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            //Faire des while avec deux booleens plutot pour la validité.
//
//            Boolean foundQuestion = false;
//            int j=0;
//            while ((j<lesQuestions.size()) && (!foundQuestion)){
//                ArrayList<Regle> lesRegles = lesQuestions.get(j).getRegles();
//
//                // Pour chaque regle on vérifie si elle est valide.
//                //Si une seule n'est pas valide la question ne l'est pas
//                // Si elles le sont toutes alors on a trouvé la question à poser
//
//                Boolean foundRuleFalse = false;
//                Boolean foundThisRule = false;
//                int k=0;
//                while ((k< lesRegles.size()) && ((foundRuleFalse) || (!foundThisRule))){
//
//                    Regle r = lesRegles.get(k);
//                    if ((r.getEvent().equals(reg.getEvent())) && (r.getType().equals(reg.getType()))){
//                        foundThisRule=true;
//                    }
//                    if (r.getEvent().equals("fullscreen")){
//                        if (r.getType().equals("if")){
//                            //TODO
//                          /*  if (!checkEvent("fullscreen")){
//                                foundRuleFalse=true;
//                            } */
//                        } else if (r.getType().equals("notif")){
//                            //TODO
//                            //TODO
//                          /*  if (checkEvent("fullscreen")){
//                                foundRuleFalse=true;
//                            } */
//                        }
//                    } else if (r.getEvent().equals("copyImage")){
//                        if (r.getType().equals("if")){
//                            //Traité dans CopyAndPasteHandler
//                        }
//                        else if (r.getType().equals("notif")){
//                            // rien
//                        }
//                    } else if (r.getEvent().equals("copyText")){
//                        if (r.getType().equals("if")){
//                            //Traité dans CopyAndPasteHandler
//                        }
//                        else if (r.getType().equals("notif")){
//                            // rien
//                        }
//                    } else if (r.getEvent().equals("copyFile")){
//                        if (r.getType().equals("if")){
//                            //Traité dans CopyAndPasteHandler
//                        }
//                        else if (r.getType().equals("notif")){
//                            // rien
//                        }
//                    } else if (r.getEvent().equals("copy")){
//                        if (r.getType().equals("if")){
//                            //Traité dans CopyAndPasteHandler
//                        }
//                        else if (r.getType().equals("notif")){
//                            // rien
//                        }
//                    }
//
//                    k++;
//                }
//                if ((!foundRuleFalse) && (foundThisRule)){
//                    foundQuestion=true;
//                    selectedQuestion=lesQuestions.get(j);
//                }
//
//                j++;
//            }
//
//        return selectedQuestion;
        
        
            // New version : select a random question from a list of validated
            //               questions
            ArrayList<Question> lesQuestions = new ArrayList<Question>();
            ArrayList<Question> askableQuestions = new ArrayList<Question>();
            Question selectedQuestion = null;
            try{
                lesQuestions = Utils.importFormXML();
            } catch (BadXMLFileException ex) {
                Logger.getLogger(AskFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Faire des while avec deux booleens plutot pour la validité.

            Boolean foundQuestion = false;
            int j=0;
            while (j<lesQuestions.size()){
                ArrayList<Regle> lesRegles = lesQuestions.get(j).getRegles();

                // Pour chaque regle on vérifie si elle est valide.
                //Si une seule n'est pas valide la question ne l'est pas
                // Si elles le sont toutes alors on a trouvé la question à poser

                Boolean foundRuleFalse = false;
                Boolean foundThisRule = false;
                int k=0;
                while ((k< lesRegles.size()) && ((foundRuleFalse) || (!foundThisRule))){

                    Regle r = lesRegles.get(k);
                    if ((r.getEvent().equals(reg.getEvent())) && (r.getType().equals(reg.getType()))){
                        foundThisRule=true;
                    }
                    if (r.getEvent().equals("fullscreen")){
                        if (r.getType().equals("if")){
                            //TODO
                          /*  if (!checkEvent("fullscreen")){
                                foundRuleFalse=true;
                            } */
                        } else if (r.getType().equals("notif")){
                            //TODO
                            //TODO
                          /*  if (checkEvent("fullscreen")){
                                foundRuleFalse=true;
                            } */
                        }
                    } else if (r.getEvent().equals("copyImage")){
                        if (r.getType().equals("if")){
                            //Traité dans CopyAndPasteHandler
                        }
                        else if (r.getType().equals("notif")){
                            // rien
                        }
                    } else if (r.getEvent().equals("copyText")){
                        if (r.getType().equals("if")){
                            //Traité dans CopyAndPasteHandler
                        }
                        else if (r.getType().equals("notif")){
                            // rien
                        }
                    } else if (r.getEvent().equals("copyFile")){
                        if (r.getType().equals("if")){
                            //Traité dans CopyAndPasteHandler
                        }
                        else if (r.getType().equals("notif")){
                            // rien
                        }
                    } else if (r.getEvent().equals("copy")){
                        if (r.getType().equals("if")){
                            //Traité dans CopyAndPasteHandler
                        }
                        else if (r.getType().equals("notif")){
                            // rien
                        }
                    }

                    k++;
                }
                if ((!foundRuleFalse) && (foundThisRule)){
                    foundQuestion=true;
                    askableQuestions.add(lesQuestions.get(j));
                    //selectedQuestion=lesQuestions.get(j);
                }

                j++;
            }
            //System.out.println("Nombre de questions posables : "+askableQuestions.size());
            if (askableQuestions.size()>0){
                int rang = (new Random().nextInt(askableQuestions.size()));
                //System.out.println("Num question choisie : "+rang);
                selectedQuestion=askableQuestions.get(rang);
            } else {
                selectedQuestion=null;
            }
        return selectedQuestion;

    }

    public void askQuestionWithRule(String event) {
        Question q;
        // Check if it's not to early to ask a question
        if (TimerQuestion.getTimerQuestion().canIAskQuestionNow()){
            // Then check if there is an "askable" question with the rule "event" and type "if"
            // If so, ask the first
            // Else do nothing
            q=selectQuestionFromFormWithRule(new Regle(event, "if"));

            if (q!=null){
                currentQuestion=q;
                Random rand = new Random();

                int nb = rand.nextInt(100);
                if (nb<EVENT_CPY_PROBA){
                    currentRegle=new Regle(event, "if");
                    showTheFrame("rule");
                    TimerQuestion.getTimerQuestion().resetTimerAfterQuestion();
                }
            }
        }

    }




    public ArrayList<Question> getAskableQuestionsFromForm(){
        // Mettre les regles dans la classe Question et initialisé dans importForm.xml
//            ArrayList<Question> lesQuestions = new ArrayList<Question>();
//            Question selectedQuestion = null;
//            try{
//                lesQuestions = Utils.importFormXML();
//            } catch (BadXMLFileException ex) {
//                Logger.getLogger(AskFrame.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            //Faire des while avec deux booleens plutot pour la validité.
//
//            Boolean foundQuestion = false;
//            int j=0;
//            while ((j<lesQuestions.size()) && (!foundQuestion)){
//
//                ArrayList<Regle> lesRegles = lesQuestions.get(j).getRegles();
//
//                // Pour chaque regle on vérifie si elle est valide.
//                //Si une seule n'est pas valide la question ne l'est pas
//                // Si elles le sont toutes alors on a trouvé la question à poser
//
//                Boolean foundRuleFalse = false;
//                int k=0;
//                while ((k< lesRegles.size()) &&(!foundRuleFalse)){
//
//                    Regle r = lesRegles.get(k);
//                    if (r.getEvent().equals("fullscreen")){
//                        if (r.getType().equals("if")){
//                            //TODO
//                          /*  if (!checkEvent("fullscreen")){
//                                foundRuleFalse=true;
//                            } */
//                        } else if (r.getType().equals("notif")){
//                            //TODO
//                          /*  if (checkEvent("fullscreen")){
//                                foundRuleFalse=true;
//                            } */
//                        }
//                    } else if (r.getEvent().equals("copyImage")){
//                            //Traité dans CopyAndPasteHandler
//                            foundRuleFalse=true;
//
//                    } else if (r.getEvent().equals("copyText")){
//                            //Traité dans CopyAndPasteHandler
//                            foundRuleFalse=true;
//
//
//                    } else if (r.getEvent().equals("copyFile")){
//                            //Traité dans CopyAndPasteHandler
//                            foundRuleFalse=true;
//                    } else if (r.getEvent().equals("copyFile")){
//                            //Traité dans CopyAndPasteHandler
//                            foundRuleFalse=true;
//                    } else if (r.getEvent().equals("copy")){
//                            foundRuleFalse=true;
//                    }
//
//                    k++;
//                }
//                if (!foundRuleFalse){
//                    foundQuestion=true;
//                    selectedQuestion=lesQuestions.get(j);
//                }
//
//                j++;
//            }
//
//        return selectedQuestion;
            ArrayList<Question> lesQuestions = new ArrayList<Question>();
            ArrayList<Question> askableQuestions = new ArrayList<Question>();
            Question selectedQuestion = null;
            try{
                lesQuestions = Utils.importFormXML();
            } catch (BadXMLFileException ex) {
                Logger.getLogger(AskFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Faire des while avec deux booleens plutot pour la validité.

            Boolean foundQuestion = false;
            int j=0;
            while ((j<lesQuestions.size())){

                ArrayList<Regle> lesRegles = lesQuestions.get(j).getRegles();

                // Pour chaque regle on vérifie si elle est valide.
                //Si une seule n'est pas valide la question ne l'est pas
                // Si elles le sont toutes alors on a trouvé la question à poser

                Boolean foundRuleFalse = false;
                int k=0;
                while ((k< lesRegles.size()) &&(!foundRuleFalse)){

                    Regle r = lesRegles.get(k);
                    if (r.getEvent().equals("fullscreen")){
                        if (r.getType().equals("if")){
                            //TODO
                          /*  if (!checkEvent("fullscreen")){
                                foundRuleFalse=true;
                            } */
                        } else if (r.getType().equals("notif")){
                            //TODO
                          /*  if (checkEvent("fullscreen")){
                                foundRuleFalse=true;
                            } */
                        }
                    } else if (r.getEvent().equals("copyImage")){
                            //Traité dans CopyAndPasteHandler
                            foundRuleFalse=true;

                    } else if (r.getEvent().equals("copyText")){
                            //Traité dans CopyAndPasteHandler
                            foundRuleFalse=true;


                    } else if (r.getEvent().equals("copyFile")){
                            //Traité dans CopyAndPasteHandler
                            foundRuleFalse=true;
                    } else if (r.getEvent().equals("copyFile")){
                            //Traité dans CopyAndPasteHandler
                            foundRuleFalse=true;
                    } else if (r.getEvent().equals("copy")){
                            foundRuleFalse=true;
                    }

                    k++;
                }
                if (!foundRuleFalse){
                    foundQuestion=true;
//                    selectedQuestion=lesQuestions.get(j);
                    askableQuestions.add(lesQuestions.get(j));

                }

                j++;
            }
            //System.out.println("Nombre de questions posables_ : "+askableQuestions.size());
            if (askableQuestions.size()>0){
                int rang = (new Random().nextInt(askableQuestions.size()));
                //System.out.println("Num question choisie : "+rang);
                selectedQuestion=askableQuestions.get(rang);
            } else {
                selectedQuestion=null;
            }

        return askableQuestions;

    }

    public ArrayList<Question> getAskableQuestionsFromFormWithRule(Regle reg){
        // Mettre les regles dans la classe Question et initialisé dans importForm.xml
//            ArrayList<Question> lesQuestions = new ArrayList<Question>();
//            ArrayList<Question> askableQuestions = new ArrayList<Question>();
//            Question selectedQuestion = null;
//            try{
//                lesQuestions = Utils.importFormXML();
//            } catch (BadXMLFileException ex) {
//                Logger.getLogger(AskFrame.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//            //Faire des while avec deux booleens plutot pour la validité.
//
//            Boolean foundQuestion = false;
//            int j=0;
//            while ((j<lesQuestions.size()) && (!foundQuestion)){
//                ArrayList<Regle> lesRegles = lesQuestions.get(j).getRegles();
//
//                // Pour chaque regle on vérifie si elle est valide.
//                //Si une seule n'est pas valide la question ne l'est pas
//                // Si elles le sont toutes alors on a trouvé la question à poser
//
//                Boolean foundRuleFalse = false;
//                Boolean foundThisRule = false;
//                int k=0;
//                while ((k< lesRegles.size()) && ((foundRuleFalse) || (!foundThisRule))){
//
//                    Regle r = lesRegles.get(k);
//                    if ((r.getEvent().equals(reg.getEvent())) && (r.getType().equals(reg.getType()))){
//                        foundThisRule=true;
//                    }
//                    if (r.getEvent().equals("fullscreen")){
//                        if (r.getType().equals("if")){
//                            //TODO
//                          /*  if (!checkEvent("fullscreen")){
//                                foundRuleFalse=true;
//                            } */
//                        } else if (r.getType().equals("notif")){
//                            //TODO
//                            //TODO
//                          /*  if (checkEvent("fullscreen")){
//                                foundRuleFalse=true;
//                            } */
//                        }
//                    } else if (r.getEvent().equals("copyImage")){
//                        if (r.getType().equals("if")){
//                            //Traité dans CopyAndPasteHandler
//                        }
//                        else if (r.getType().equals("notif")){
//                            // rien
//                        }
//                    } else if (r.getEvent().equals("copyText")){
//                        if (r.getType().equals("if")){
//                            //Traité dans CopyAndPasteHandler
//                        }
//                        else if (r.getType().equals("notif")){
//                            // rien
//                        }
//                    } else if (r.getEvent().equals("copyFile")){
//                        if (r.getType().equals("if")){
//                            //Traité dans CopyAndPasteHandler
//                        }
//                        else if (r.getType().equals("notif")){
//                            // rien
//                        }
//                    } else if (r.getEvent().equals("copy")){
//                        if (r.getType().equals("if")){
//                            //Traité dans CopyAndPasteHandler
//                        }
//                        else if (r.getType().equals("notif")){
//                            // rien
//                        }
//                    }
//
//                    k++;
//                }
//                if ((!foundRuleFalse) && (foundThisRule)){
//                    foundQuestion=true;
//                    selectedQuestion=lesQuestions.get(j);
//                }
//
//                j++;
//            }
//
//        return selectedQuestion;


            // New version : select a random question from a list of validated
            //               questions
            ArrayList<Question> lesQuestions = new ArrayList<Question>();
            ArrayList<Question> askableQuestions = new ArrayList<Question>();
            Question selectedQuestion = null;
            try{
                lesQuestions = Utils.importFormXML();
            } catch (BadXMLFileException ex) {
                Logger.getLogger(AskFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Faire des while avec deux booleens plutot pour la validité.

            Boolean foundQuestion = false;
            int j=0;
            while (j<lesQuestions.size()){
                ArrayList<Regle> lesRegles = lesQuestions.get(j).getRegles();

                // Pour chaque regle on vérifie si elle est valide.
                //Si une seule n'est pas valide la question ne l'est pas
                // Si elles le sont toutes alors on a trouvé la question à poser

                Boolean foundRuleFalse = false;
                Boolean foundThisRule = false;
                int k=0;
                while ((k< lesRegles.size()) && ((foundRuleFalse) || (!foundThisRule))){

                    Regle r = lesRegles.get(k);
                    if ((r.getEvent().equals(reg.getEvent())) && (r.getType().equals(reg.getType()))){
                        foundThisRule=true;
                    }
                    if (r.getEvent().equals("fullscreen")){
                        if (r.getType().equals("if")){
                            //TODO
                          /*  if (!checkEvent("fullscreen")){
                                foundRuleFalse=true;
                            } */
                        } else if (r.getType().equals("notif")){
                            //TODO
                            //TODO
                          /*  if (checkEvent("fullscreen")){
                                foundRuleFalse=true;
                            } */
                        }
                    } else if (r.getEvent().equals("copyImage")){
                        if (r.getType().equals("if")){
                            //Traité dans CopyAndPasteHandler
                        }
                        else if (r.getType().equals("notif")){
                            // rien
                        }
                    } else if (r.getEvent().equals("copyText")){
                        if (r.getType().equals("if")){
                            //Traité dans CopyAndPasteHandler
                        }
                        else if (r.getType().equals("notif")){
                            // rien
                        }
                    } else if (r.getEvent().equals("copyFile")){
                        if (r.getType().equals("if")){
                            //Traité dans CopyAndPasteHandler
                        }
                        else if (r.getType().equals("notif")){
                            // rien
                        }
                    } else if (r.getEvent().equals("copy")){
                        if (r.getType().equals("if")){
                            //Traité dans CopyAndPasteHandler
                        }
                        else if (r.getType().equals("notif")){
                            // rien
                        }
                    }

                    k++;
                }
                if ((!foundRuleFalse) && (foundThisRule)){
                    foundQuestion=true;
                    askableQuestions.add(lesQuestions.get(j));
                    //selectedQuestion=lesQuestions.get(j);
                }

                j++;
            }
            //System.out.println("Nombre de questions posables : "+askableQuestions.size());
            if (askableQuestions.size()>0){
                int rang = (new Random().nextInt(askableQuestions.size()));
                //System.out.println("Num question choisie : "+rang);
                selectedQuestion=askableQuestions.get(rang);
            } else {
                selectedQuestion=null;
            }
        return askableQuestions;

    }

    public void loadForm(){
        // On parcourt les questions posables et pour chacune :
        //      - On crée un sous-panel dans interiorPanel et on y ajoute :
        //          - un Label de l'intitulé
        //          - Puis le choiceGroup en appelant la methode choicesGroupe(notre Panel).

        ArrayList<Question> questions = new ArrayList<Question>();
        if (currentRegle!=null){
            questions=getAskableQuestionsFromFormWithRule(currentRegle);
        } else {
            questions=getAskableQuestionsFromForm();
        }
        System.out.println("Nombre de questions trouvées : "+questions.size());
        //thePanel.addQuestion(questions.get(1));
        for (int i=0;i<questions.size();i++){
            currentQuestion=questions.get(i);
            thePanel.addQuestion(questions.get(i));
        }
    }


    public void loadFormFromRule(Regle reg){
        // On parcourt les questions posables et pour chacune :
        //      - On crée un sous-panel dans interiorPanel et on y ajoute :
        //          - un Label de l'intitulé
        //          - Puis le choiceGroup en appelant la methode choicesGroupe(notre Panel).

        ArrayList<Question> questions = new ArrayList<Question>();
        questions=getAskableQuestionsFromFormWithRule(reg);
    }


    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals(labelButtonValider)) {
            try {
                DBConnexion conn = DBConnexion.getConnexion();
                SessionManager sm = SessionManager.getSessionManager();
                Date maDate = new Date();
                Reponse rep=null;
/*
                ArrayList<Question> lesQuestions = new ArrayList<Question>();
                lesQuestions = Utils.importFormXML();
                //setText1(lesQuestions.get(0).intitule);
                Reponse rep = null;
                if (currentQuestion instanceof QReponseLibre){
                    rep = new Reponse(conn.getMaxIdReponseBySession(sm.getSessionCourante())+1,AskFrame.getText1(), AskFrame.getText2(), maDate, sm.getSessionCourante(),absoluteScreenshotFilePath);
                    rep.setReglesQuestion(currentQuestion.getRegles());
                }
                if (currentQuestion instanceof QCMRadio) {
                    String res="";
                    Enumeration<AbstractButton> en = thePanel.bg2.getElements();
                    while (en.hasMoreElements()) {

                        AbstractButton ab = en.nextElement();
                        if (ab.isSelected()){
                            if (!res.equals("")){
                                res=res+" \n ";
                            }
                            res+=ab.getText();
                        }
                    }
                    
                    Component ti[]= ((JPanel)thePanel.getComponent(2)).getComponents();
                    for (int i=0; i<ti.length;i++){
                        if (ti[i] instanceof JTextField){
                            if (!((JTextField) ti[i]).getText().equals("")){
                                res+=" \n "+((JTextField) ti[i]).getText();
                            }
                        }                        
                    }

                    rep = new Reponse(conn.getMaxIdReponseBySession(sm.getSessionCourante())+1,AskFrame.getText1(), res, maDate, sm.getSessionCourante(),absoluteScreenshotFilePath);
                    rep.setReglesQuestion(currentQuestion.getRegles());
                    thePanel.bg2.clearSelection();
                }
                
                if (currentQuestion instanceof QCMChkBox){
                    String res="";
                    Component t[]= ((JPanel)thePanel.getComponent(2)).getComponents();

                    for (int i=0; i<t.length;i++){

                        if (t[i] instanceof AbstractButton){
                            AbstractButton ab=(AbstractButton) t[i];
                            if (ab.isSelected()){
                                if (!res.equals("")){
                                    res=res+" \n ";
                                }
                                res+=ab.getText();
                            }
                        } else if (!((JTextField) t[i]).getText().equals("")){
                            res+=" \n "+((JTextField) t[i]).getText();
                        }
                    }
 */
                    String questions="";
                    String answers="";
                    int idQuestion=1;
                    Component panQuests[] = thePanel.jpMiddle.getComponents();
                    for (int i=0; i<panQuests.length;i++){
                        Component tsub[] = ((JPanel) panQuests[i]).getComponents();
                        for (int j=0; j<tsub.length;j++){
                            if (tsub[j] instanceof QuestionLabel){
                                QuestionLabel ql=(QuestionLabel) tsub[j];
                                questions+=idQuestion+". "+ql.getText()+" \n";
                                if (idQuestion==1){
                                    answers+=" "+idQuestion+". "+ql.getText()+" : ";
                                } else {
                                    answers+=idQuestion+". "+ql.getText()+" : ";
                                }
                                idQuestion++;
                            }  else {
                                // Subpanel "res" of panel PanQuestion contents
                                Component tsub2[] = ((JPanel) tsub[j]).getComponents();
                                for (int k=0; k<tsub2.length;k++){
                                    if (tsub2[k] instanceof AnswerTextField){
                                        AnswerTextField atf=(AnswerTextField) tsub2[k];
                                        if (!atf.equals("")){
                                            if (k==tsub2.length-1){
                                                answers+=atf.getText()+" \n ";
                                            } else {
                                                answers+=atf.getText()+" --- ";
                                            }
                                        }
                                    } else if (tsub2[k] instanceof AbstractButton){
                                        AbstractButton ab=(AbstractButton) tsub2[k];
                                        if (ab.isSelected()){
                                            if (k==tsub2.length-1){
                                                answers+=ab.getText()+" \n ";
                                            } else {
                                                answers+=ab.getText()+" --- ";
                                            }
                                        }
                                    } else if (tsub2[k] instanceof AnswerTextArea){
                                        AnswerTextArea ata=(AnswerTextArea) tsub2[k];
                                        if (!ata.equals("")){
                                            if (k==tsub2.length-1){
                                                answers+=ata.getText()+" /n ";
                                            } else {
                                                answers+=ata.getText()+" --- ";
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    //System.out.println(questions+" : \n "+answers);
                    rep = new Reponse(conn.getMaxIdReponseBySession(sm.getSessionCourante())+1, questions, answers, maDate, sm.getSessionCourante(),absoluteScreenshotFilePath);
                    rep.setReglesQuestion(currentQuestion.getRegles());

/*
                    // Reinit
                    
                    for (int i =0; i<t.length; i++){
                        if (t[i] instanceof JCheckBox)
                            ((JCheckBox) ((JPanel)thePanel.getComponent(2)).getComponent(i)).setSelected(false);
                        if (t[i] instanceof JTextField){
                            ((JTextField) ((JPanel)thePanel.getComponent(2)).getComponent(i)).setText("");
                            //((JTextField) ((JPanel)thePanel.getComponent(2)).getComponent(i)).setVisible(false);
                        }
                    }

                }
  */
                ArrayList<Regle> lesR=rep.getReglesQuestion();
                String strRegles="";
                for (int i=0; i<lesR.size();i++){
                    if (i==0) {
                        strRegles+=lesR.get(i).getType()+":"+lesR.get(i).getEvent();
                    } else if (i<=(lesR.size()-1)){
                        strRegles+=", "+lesR.get(i).getType()+":"+lesR.get(i).getEvent();
                    }
                }
                conn.newAddEntry(rep);


                if (!absoluteScreenshotFilePath.equals("")){
                    // Réouvrir l'image et la resize et ajouter réponse comme dans ViewAnswers
                    ImgTxtMerger imTxtM  = new ImgTxtMerger();
                    imTxtM.fusion(rep);
                }
                //ImgTxtMerger.merge(absoluteScreenshotFilePath, rep.getIntituleQuestion()+ " \n "+rep.getLaReponse()+" \n "+rep.getInstant().toString());
                /*if (currentQuestion instanceof QReponseLibre){
                    AskFrame.setText2("");
                }*/

                hideCD.cancel();
                this.hideTheFrame();




                // Restart application to avoid huge memory consumption because of images
                //if (Runtime.getRuntime().freeMemory()// and Runtime.totalMemory()
                //double cons = ((double)Runtime.getRuntime().freeMemory()/(double)Runtime.getRuntime().totalMemory())*100.0;
                Runtime.getRuntime().gc();
                OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
                Long usedMemory = operatingSystemMXBean.getTotalPhysicalMemorySize()-operatingSystemMXBean.getFreePhysicalMemorySize();
                //long memory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                double cons = ((double)usedMemory/(double)operatingSystemMXBean.getTotalPhysicalMemorySize())*100.0;
                //double cons = ((double)memory/(double)operatingSystemMXBean.getTotalPhysicalMemorySize())*100.0;
                //System.out.println("Consumption : "+cons);
                if (cons>80){
                    //System.out.println("High memory consumption...Restarting...");
                    api.utils.appManagement.restartApplication(this, false);
                }
                //System.out.println(operatingSystemMXBean.getFreePhysicalMemorySize());
                //System.out.println(operatingSystemMXBean.getTotalPhysicalMemorySize());
            } catch (Exception ex) {
                javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else if (s.equals(labelButtonSkip)) {
                // Hide frame & delete screenshot
                if (absoluteScreenshotFilePath!=null){
                    if (!absoluteScreenshotFilePath.equals("")) {
                        File f = new File(absoluteScreenshotFilePath);
                        if (f.exists()) {
                            f.delete();
                        }
                    }
                }
                clearFrame();
                this.hideTheFrame();

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


    /*class MyAdjustmentListener implements AdjustmentListener {
      public void adjustmentValueChanged(AdjustmentEvent evt) {
         AskFrame.getTheFrame().repaint();
        Adjustable source = evt.getAdjustable();
        if (evt.getValueIsAdjusting()) {
          return;
        }
        int orient = source.getOrientation();
        if (orient == Adjustable.HORIZONTAL) {
          System.out.println("from horizontal scrollbar");
        } else {
          System.out.println("from vertical scrollbar");
        }
        int type = evt.getAdjustmentType();
        switch (type) {
        case AdjustmentEvent.UNIT_INCREMENT:
          System.out.println("Scrollbar was increased by one unit");
          break;
        case AdjustmentEvent.UNIT_DECREMENT:
          System.out.println("Scrollbar was decreased by one unit");
          break;
        case AdjustmentEvent.BLOCK_INCREMENT:
          System.out.println("Scrollbar was increased by one block");
          break;
        case AdjustmentEvent.BLOCK_DECREMENT:
          System.out.println("Scrollbar was decreased by one block");
          break;
        case AdjustmentEvent.TRACK:
          System.out.println("The knob on the scrollbar was dragged");
          break;
        }
        int value = evt.getValue();
      }
    }*/

}
