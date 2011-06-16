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

import exceptions.BadXMLFileException;
import api.dbc.DBConnexion;
import api.i18n.Lang;
import api.xml.Utils;
import api.xml.Xmlfilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import api.utils.DirFileFilter;
import api.utils.LaunchProjectPage;
import api.utils.LaunchReportPage;
import api.utils.UrlHelper;
import api.utils.getOs;
import des.Main;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.JOptionPane;
import model.SessionManager;

/**
 * Class Listeners.java
 * @description Application's buttons' listeners
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @author Bertrand Gros    <gros.bertrand@gmail.com>
 * @version 2011-01-28
 */
public class Listeners implements ActionListener, WindowListener {

    static private Listeners li = null;

    public static Listeners getListeners() {
        if (li == null) {
            li = new Listeners();
        }
        return li;
    }

    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        /*if (s.equals(QuestionFrame.labelButtonValider)) {
        try {
        // CE QU'ON VEUT FAIRE AVEC LE BOUTON VALIDER
        DBConnexion conn = DBConnexion.getConnexion();
        SessionManager sm = SessionManager.getSessionManager();
        Date maDate = new Date();
        Reponse rep = new Reponse(QuestionFrame.GetText1(), QuestionFrame.GetText2(), maDate, sm.getSessionCourante());
        conn.newAddEntry(rep);
        QuestionFrame.SetText2("");
        QuestionFrame.getFrame().HideFrame();
        } catch (SQLException ex) {
        Logger.getLogger(Listeners.class.getName()).log(Level.SEVERE, null, ex);
        }
        } else */
        if (s.equals(SessionFrame.labelBtExportCurrentSession)) {
            // CE QU'ON VEUT FAIRE AVEC LE BOUTON EXPORTER
            SessionFrame.getFrame().setAlwaysOnTop(false);
            DBConnexion conn = DBConnexion.getConnexion();
            String cheminExportation;
            Xmlfilter filtre_xml = new Xmlfilter(Lang.getLang().getValueFromRef("SessionFrame.strXmlFile"), ".xml");

            /* UTILISER CA PLUTOT */
            if (!getOs.isWindows()){
                System.setProperty("apple.awt.fileDialogForDirectories", "true");
                FileDialog d = new FileDialog(SessionFrame.getFrame());
                d.setVisible(true);
                if (d.getFile()!=null){

                    try {
                        cheminExportation = "";
                        // un fichier a été choisi ( sortie par OK)
                        // nom du fichier  choisi
                        //choix.getSelectedFile().getName();
                        // chemin absolu du fichier choisi
                        cheminExportation = d.getDirectory()+d.getFile()+"/";

                        // A FAIRE : Mettre date debut et date fin au bon format
                        if (SessionManager.getSessionManager().getSessionCourante().getFin() != null) {
                            Utils.ExportReponsesToXML(conn.getEntriesBySession(SessionManager.getSessionManager().getSessionCourante()), cheminExportation + "session" + SessionManager.getSessionManager().getSessionCourante().getId() + "_" + SessionManager.getSessionManager().getSessionCourante().getDebut().toString().replaceAll(" ", "_").replaceAll(":", "-") + "_" + SessionManager.getSessionManager().getSessionCourante().getFin().toString().replaceAll(" ", "_").replaceAll(":", "-") + ".xml");
                            javax.swing.JOptionPane.showMessageDialog(null, cheminExportation + "session" + SessionManager.getSessionManager().getSessionCourante().getId() + "_" + SessionManager.getSessionManager().getSessionCourante().getDebut().toString().replaceAll(" ", "_").replaceAll(":", "-") + "_" + SessionManager.getSessionManager().getSessionCourante().getFin().toString().replaceAll(" ", "_").replaceAll(":", "-") + ".xml");
                        } else {
                            Utils.ExportReponsesToXML(conn.getEntriesBySession(SessionManager.getSessionManager().getSessionCourante()), cheminExportation + "session" + SessionManager.getSessionManager().getSessionCourante().getId() + "_" + SessionManager.getSessionManager().getSessionCourante().getDebut().toString().replaceAll(" ", "_").replaceAll(":", "-") + "_now.xml");
                            javax.swing.JOptionPane.showMessageDialog(null, cheminExportation + "session" + SessionManager.getSessionManager().getSessionCourante().getId() + "_" + SessionManager.getSessionManager().getSessionCourante().getDebut().toString().replaceAll(" ", "_").replaceAll(":", "-") + "_now.xml");
                        }
                        // On met à jour la nouvelle date d'export pour la session
                        SessionManager.getSessionManager().getSessionCourante().setLastExport(new Date());
                        conn.updateSession(SessionManager.getSessionManager().getSessionCourante());
                        SessionFrame.getFrame().RefreshFrame();
                    } catch (Exception ex) {
                        javax.swing.JOptionPane.showMessageDialog(null, Lang.getLang().getValueFromRef("SessionFrame.strXmlError") + ex.toString());
                        SessionFrame.getFrame().setAlwaysOnTop(true);
                    }

                }
            } else {
                /* Windows */
                JFileChooser choix = new JFileChooser();
                choix.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                choix.addChoosableFileFilter(new DirFileFilter());
                int retour = choix.showOpenDialog(null);

                try {
                    cheminExportation = "";
                    if (retour == JFileChooser.APPROVE_OPTION) {
                        // un fichier a été choisi ( sortie par OK)
                        // nom du fichier  choisi
                        //choix.getSelectedFile().getName();
                        // chemin absolu du fichier choisi
                        cheminExportation = choix.getSelectedFile().getAbsolutePath();

                        if (getOs.isWindows()) {
                            cheminExportation = cheminExportation + "\\";
                        } else {
                            cheminExportation = cheminExportation + "/";
                        }

                        // A FAIRE : Mettre date debut et date fin au bon format
                        if (SessionManager.getSessionManager().getSessionCourante().getFin() != null) {
                            Utils.ExportReponsesToXML(conn.getEntriesBySession(SessionManager.getSessionManager().getSessionCourante()), cheminExportation + "session" + SessionManager.getSessionManager().getSessionCourante().getId() + "_" + SessionManager.getSessionManager().getSessionCourante().getDebut().toString().replaceAll(" ", "_").replaceAll(":", "-") + "_" + SessionManager.getSessionManager().getSessionCourante().getFin().toString().replaceAll(" ", "_").replaceAll(":", "-") + ".xml");
                            javax.swing.JOptionPane.showMessageDialog(null, cheminExportation + "session" + SessionManager.getSessionManager().getSessionCourante().getId() + "_" + SessionManager.getSessionManager().getSessionCourante().getDebut().toString().replaceAll(" ", "_").replaceAll(":", "-") + "_" + SessionManager.getSessionManager().getSessionCourante().getFin().toString().replaceAll(" ", "_").replaceAll(":", "-") + ".xml");
                        } else {
                            Utils.ExportReponsesToXML(conn.getEntriesBySession(SessionManager.getSessionManager().getSessionCourante()), cheminExportation + "session" + SessionManager.getSessionManager().getSessionCourante().getId() + "_" + SessionManager.getSessionManager().getSessionCourante().getDebut().toString().replaceAll(" ", "_").replaceAll(":", "-") + "_now.xml");
                            javax.swing.JOptionPane.showMessageDialog(null, cheminExportation + "session" + SessionManager.getSessionManager().getSessionCourante().getId() + "_" + SessionManager.getSessionManager().getSessionCourante().getDebut().toString().replaceAll(" ", "_").replaceAll(":", "-") + "_now.xml");
                        }
                        // On met à jour la nouvelle date d'export pour la session
                        SessionManager.getSessionManager().getSessionCourante().setLastExport(new Date());
                        conn.updateSession(SessionManager.getSessionManager().getSessionCourante());
                        SessionFrame.getFrame().RefreshFrame();

                    }
                    /**
                     * Faire redirection ou message de succès
                     */
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(null, Lang.getLang().getValueFromRef("SessionFrame.strXmlError") + ex.toString());
                    SessionFrame.getFrame().setAlwaysOnTop(true);

                }


            }
            /* */

            SessionFrame.getFrame().setAlwaysOnTop(true);


        } else if (s.equals(SessionFrame.labelBtExportOldSessions)) {
            // CE QU'ON VEUT FAIRE AVEC LE BOUTON EXPORTER
            SessionFrame.getFrame().setAlwaysOnTop(false);
            DBConnexion conn = DBConnexion.getConnexion();
            String cheminExportation;

            if (!getOs.isWindows()){
                /* Mac / Linux */
                System.setProperty("apple.awt.fileDialogForDirectories", "true");
                FileDialog d = new FileDialog(SessionFrame.getFrame());
                d.setVisible(true);
                if (d.getFile()!=null){

                    try {
                        cheminExportation = "";
                        // un fichier a été choisi ( sortie par OK)
                        // nom du fichier  choisi
                        //choix.getSelectedFile().getName();
                        // chemin absolu du fichier choisi
                        cheminExportation = d.getDirectory()+d.getFile()+"/";

                        // A FAIRE : Mettre date debut et date fin au bon format
                        if (SessionFrame.getFrame().getSessionSelectionnee().getFin() != null) {
                            Utils.ExportReponsesToXML(conn.getEntriesBySession(SessionFrame.getFrame().getSessionSelectionnee()), cheminExportation + "session" + SessionFrame.getFrame().getSessionSelectionnee().getId() + "_" + SessionFrame.getFrame().getSessionSelectionnee().getDebut().toString().replaceAll(" ", "_").replaceAll(":", "-") + "_" + SessionFrame.getFrame().getSessionSelectionnee().getFin().toString().replaceAll(" ", "_").replaceAll(":", "-") + ".xml");
                            javax.swing.JOptionPane.showMessageDialog(null, cheminExportation + "session" + SessionFrame.getFrame().getSessionSelectionnee().getId() + "_" + SessionFrame.getFrame().getSessionSelectionnee().getDebut().toString().replaceAll(" ", "_").replaceAll(":", "-") + "_" + SessionFrame.getFrame().getSessionSelectionnee().getFin().toString().replaceAll(" ", "_").replaceAll(":", "-") + ".xml");
                        } else {
                            Utils.ExportReponsesToXML(conn.getEntriesBySession(SessionFrame.getFrame().getSessionSelectionnee()), cheminExportation + "session" + SessionFrame.getFrame().getSessionSelectionnee().getId() + "_" + SessionFrame.getFrame().getSessionSelectionnee().getDebut().toString().replaceAll(" ", "_").replaceAll(":", "-") + "_now.xml");
                            javax.swing.JOptionPane.showMessageDialog(null, cheminExportation + "session" + SessionFrame.getFrame().getSessionSelectionnee().getId() + "_" + SessionFrame.getFrame().getSessionSelectionnee().getDebut().toString().replaceAll(" ", "_").replaceAll(":", "-") + "_now.xml");
                        }
                        // On met à jour la nouvelle date d'export pour la session
                        SessionFrame.getFrame().getSessionSelectionnee().setLastExport(new Date());
                        conn.updateSession(SessionFrame.getFrame().getSessionSelectionnee());
                        SessionFrame.getFrame().RefreshFrame();
                    } catch (Exception ex) {
                        javax.swing.JOptionPane.showMessageDialog(null, Lang.getLang().getValueFromRef("SessionFrame.strXmlError") + ex.toString());
                        SessionFrame.getFrame().setAlwaysOnTop(true);
                    }

                }
            } else {
                /* Windows */
                Xmlfilter filtre_xml = new Xmlfilter(Lang.getLang().getValueFromRef("SessionFrame.strXmlFile"), ".xml");
                JFileChooser choix = new JFileChooser();
                choix.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                choix.addChoosableFileFilter(new DirFileFilter());
                int retour = choix.showOpenDialog(null);

                try {
                    cheminExportation = "";
                    if (retour == JFileChooser.APPROVE_OPTION) {
                        // un fichier a été choisi ( sortie par OK)
                        // nom du fichier  choisi
                        //choix.getSelectedFile().getName();
                        // chemin absolu du fichier choisi
                        cheminExportation = choix.getSelectedFile().getAbsolutePath();

                        if (getOs.isWindows()) {
                            cheminExportation = cheminExportation + "\\";
                        } else {
                            cheminExportation = cheminExportation + "/";
                        }

                        // A FAIRE : Mettre date debut et date fin au bon format
                        if (SessionFrame.getFrame().getSessionSelectionnee().getFin() != null) {
                            Utils.ExportReponsesToXML(conn.getEntriesBySession(SessionFrame.getFrame().getSessionSelectionnee()), cheminExportation + "session" + SessionFrame.getFrame().getSessionSelectionnee().getId() + "_" + SessionFrame.getFrame().getSessionSelectionnee().getDebut().toString().replaceAll(" ", "_").replaceAll(":", "-") + "_" + SessionFrame.getFrame().getSessionSelectionnee().getFin().toString().replaceAll(" ", "_").replaceAll(":", "-") + ".xml");
                            javax.swing.JOptionPane.showMessageDialog(null, cheminExportation + "session" + SessionFrame.getFrame().getSessionSelectionnee().getId() + "_" + SessionFrame.getFrame().getSessionSelectionnee().getDebut().toString().replaceAll(" ", "_").replaceAll(":", "-") + "_" + SessionFrame.getFrame().getSessionSelectionnee().getFin().toString().replaceAll(" ", "_").replaceAll(":", "-") + ".xml");
                        } else {
                            Utils.ExportReponsesToXML(conn.getEntriesBySession(SessionFrame.getFrame().getSessionSelectionnee()), cheminExportation + "session" + SessionFrame.getFrame().getSessionSelectionnee().getId() + "_" + SessionFrame.getFrame().getSessionSelectionnee().getDebut().toString().replaceAll(" ", "_").replaceAll(":", "-") + "_now.xml");
                            javax.swing.JOptionPane.showMessageDialog(null, cheminExportation + "session" + SessionFrame.getFrame().getSessionSelectionnee().getId() + "_" + SessionFrame.getFrame().getSessionSelectionnee().getDebut().toString().replaceAll(" ", "_").replaceAll(":", "-") + "_now.xml");
                        }
                        // On met à jour la nouvelle date d'export pour la session
                        SessionFrame.getFrame().getSessionSelectionnee().setLastExport(new Date());
                        conn.updateSession(SessionFrame.getFrame().getSessionSelectionnee());
                        SessionFrame.getFrame().RefreshFrame();
                    }
                        SessionFrame.getFrame().setAlwaysOnTop(true);
                    /**
                     * Faire redirection ou message de succès
                     */
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(null, Lang.getLang().getValueFromRef("SessionFrame.strXmlError") + ex.toString());
                    SessionFrame.getFrame().setAlwaysOnTop(true);

                }
            }


            SessionFrame.getFrame().setAlwaysOnTop(true);
        } else if (s.equals(TaskTrayMenu.ExitItemLabel)) {
            int retour = JOptionPane.showConfirmDialog(null, Lang.getLang().getValueFromRef("SessionFrame.strReallyQuit"), Lang.getLang().getValueFromRef("SessionFrame.strFrameTitleReallyQuit"), JOptionPane.OK_CANCEL_OPTION);
            if (retour == 0) {
                DBConnexion.getConnexion().closeBDLink();
                System.exit(0);
            }
        } else if (s.equals(TaskTrayMenu.ShowItemLabel)) {
            AskFrame.getTheFrame().showTheFrame(null);
        } else if (s.equals(TaskTrayMenu.ReportItemLabel)) {
            LaunchReportPage.LaunchPage();
        } else if (s.equals(SessionFrame.labelBtConsultCurrentSession)) {
            SessionFrame.getFrame().setAlwaysOnTop(false);
            DBConnexion conn = DBConnexion.getConnexion();
            try {
                ConsultFrame.getTheFrame(conn.getEntriesStringBySession(SessionManager.getSessionManager().getSessionCourante())).showTheFrame(null);
            } catch (SQLException ex) {
                Logger.getLogger(Listeners.class.getName()).log(Level.SEVERE, null, ex);
            }
            SessionFrame.getFrame().setAlwaysOnTop(true);
        } else if (s.equals(SessionFrame.labelBtConsultOldSessions)) {
            SessionFrame.getFrame().setAlwaysOnTop(false);
            DBConnexion conn = DBConnexion.getConnexion();
            javax.swing.JOptionPane.showMessageDialog(null, Lang.getLang().getValueFromRef("SessionFrame.strBeforeListAnwsers") + conn.getEntriesStringBySession(SessionFrame.getFrame().getSessionSelectionnee()));
            SessionFrame.getFrame().setAlwaysOnTop(true);
        } else if (s.equals(TaskTrayMenu.ConfigItemLabel)) {
            OptionFrame.getOptionFrame().ShowFrame();
            // dossier par défaut (d'export et pour les données)\n- ...");
        } else if ((s.equals(TaskTrayMenu.SessionItemLabel)) || (s.equals(TaskTrayMenu.SessionInPauseItemLabel))) {
            SessionFrame.getFrame().ShowFrame();
        } else if (s.equals(TaskTrayMenu.AboutItemLabel)) {
        
            JOptionPane d = new JOptionPane();

            // les textes figurant sur les boutons

            String lesTextes[]={ Lang.getLang().getValueFromRef("TaskTrayMenu.OKAbout"), Lang.getLang().getValueFromRef("TaskTrayMenu.AboutItemBtProjectPage") };

            int  retour  = // indice du bouton qui a été cliqué ou CLOSED_OPTION

                 d.showOptionDialog(null, Main.appName + "\n" + Lang.getLang().getValueFromRef("TaskTrayMenu.strVersion") + " " + Main.version + "\n" + Main.datemaj + "\n\n" + Lang.getLang().getValueFromRef("TaskTrayMenu.strProjectPage") + ": " + Main.projectPage + "\n\n" + Lang.getLang().getValueFromRef("TaskTrayMenu.strContacts") + ":\n" + Main.contacts + "\n\n" + Main.cpRight, Lang.getLang().getValueFromRef("TaskTrayMenu.AboutItemLabel"),

JOptionPane.OK_OPTION, JOptionPane.QUESTION_MESSAGE, null, lesTextes,lesTextes[1]);

                  if( retour== 1) {
                                LaunchProjectPage.LaunchPage();
                  }

            //Bouton nouvelle session
        } else if (s.equals(SessionFrame.labelBtNewSessionCurrentSession)) {
            SessionFrame.getFrame().setAlwaysOnTop(false);
            DBConnexion conn = DBConnexion.getConnexion();
            int retour = JOptionPane.showConfirmDialog(SessionFrame.getFrame(), Lang.getLang().getValueFromRef("SessionFrame.strWarningBeforeClosingSession"), Lang.getLang().getValueFromRef("SessionFrame.strFrameWarningBeforeClosingSession"), JOptionPane.OK_CANCEL_OPTION);
            if (retour == 0) {
                try {



                    int ret = JOptionPane.showConfirmDialog(SessionFrame.getFrame(), Lang.getLang().getValueFromRef("SessionFrame.strFormChoiceForNewSession"), Lang.getLang().getValueFromRef("SessionFrame.strFrameFormChoiceForNewSession"), JOptionPane.YES_NO_CANCEL_OPTION);
                    if (ret == 0) {
                        try {
                            // form.xml
                            Utils.setDefaultFormFile();
                        } catch (BadXMLFileException ex) {
                            javax.swing.JOptionPane.showMessageDialog(SessionFrame.getFrame(), ex.getMessage());
                        }
                        // Initialisation de la nouvelle session
                        String nom = JOptionPane.showInputDialog(SessionFrame.getFrame(), Lang.getLang().getValueFromRef("SessionFrame.strNameForNewSession"), Lang.getLang().getValueFromRef("SessionFrame.strFrameWarningBeforeClosingSession"), JOptionPane.QUESTION_MESSAGE);
                        // Cloture de la session courante
                        SessionFrame.getFrame().resetChrono();
                        SessionManager.getSessionManager().closeSession(SessionManager.getSessionManager().getSessionCourante()); 
                        if (!nom.equals("")) {
                            SessionManager.getSessionManager().createSession(nom);
                            SessionFrame.getFrame().RefreshFrame();
                        } else {
                            SessionManager.getSessionManager().createSession(Lang.getLang().getValueFromRef("SessionFrame.strDefaultSessionName"));
                            SessionFrame.getFrame().RefreshFrame();
                        }
                        // Création terminée
                        javax.swing.JOptionPane.showMessageDialog(SessionFrame.getFrame(), Lang.getLang().getValueFromRef("SessionFrame.strSessionCreationSuccess"));
                    } else if (ret == 1){
                        String[] liste = {Lang.getLang().getValueFromRef("SessionFrame.importQuestionsSetBtFromUrl"), Lang.getLang().getValueFromRef("SessionFrame.importQuestionsSetBtFromComputer"), Lang.getLang().getValueFromRef("SessionFrame.importQuestionsSetBtCancel")};
                        JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();

                        int rang = jop.showOptionDialog(null, Lang.getLang().getValueFromRef("SessionFrame.importQuestionsSetStr"), Lang.getLang().getValueFromRef("SessionFrame.importQuestionsSetTitle"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, liste, liste[2]);
                        if (rang == 0) {
                            // Choix de l'url
                            String url = JOptionPane.showInputDialog(null, Lang.getLang().getValueFromRef("SessionFrame.importQuestionsSetFromURLStr"), Lang.getLang().getValueFromRef("SessionFrame.importQuestionsSetFromURLTitle"), JOptionPane.QUESTION_MESSAGE);
                            if (!url.equals("")) {
                                try {

                                    if (getOs.isWindows()) {
                                        UrlHelper.downloadFile(url, new File(".\\xml\\", "importedForm.xml"));
                                        Utils.setNewFormFile("xml\\importedForm.xml");
                                    } else {
                                        UrlHelper.downloadFile(url, new File("./xml/", "importedForm.xml"));
                                        Utils.setNewFormFile("xml/importedForm.xml");
                                    }

                                    javax.swing.JOptionPane.showMessageDialog(SessionFrame.getFrame(), Lang.getLang().getValueFromRef("SessionFrame.strQuestionsSetLoaded"));
                                    // Initialisation de la nouvelle session
                                    String nom = JOptionPane.showInputDialog(SessionFrame.getFrame(), Lang.getLang().getValueFromRef("SessionFrame.strNameForNewSession"), Lang.getLang().getValueFromRef("SessionFrame.strFrameWarningBeforeClosingSession"), JOptionPane.QUESTION_MESSAGE);
                                    // Cloture de la session courante
                                    SessionFrame.getFrame().resetChrono();
                                    SessionManager.getSessionManager().closeSession(SessionManager.getSessionManager().getSessionCourante());
                                    if (!nom.equals("")) {
                                        SessionManager.getSessionManager().createSession(nom);
                                        SessionFrame.getFrame().RefreshFrame();
                                    } else {
                                        SessionManager.getSessionManager().createSession(Lang.getLang().getValueFromRef("SessionFrame.strDefaultSessionName"));
                                        SessionFrame.getFrame().RefreshFrame();
                                    }
                                    // Création terminée
                                    javax.swing.JOptionPane.showMessageDialog(SessionFrame.getFrame(), Lang.getLang().getValueFromRef("SessionFrame.strSessionCreationSuccess"));
                                } catch (Exception ex) {
                                    javax.swing.JOptionPane.showMessageDialog(SessionFrame.getFrame(), Lang.getLang().getValueFromRef("SessionFrame.importQuestionsSetFromURLErrorURLOrFile"));
                                }
                            } else {
                                javax.swing.JOptionPane.showMessageDialog(SessionFrame.getFrame(), Lang.getLang().getValueFromRef("SessionFrame.importQuestionsSetFromURLErrorURL"));
                            }
                        } else if (rang == 1) {

                            // Choix du fichier
                            String cheminFichierQuestions;
                            Xmlfilter filtre_xml = new Xmlfilter(Lang.getLang().getValueFromRef("SessionFrame.strXmlFile"), ".xml");
                            JFileChooser choix = new JFileChooser();
                            choix.setFileSelectionMode(JFileChooser.FILES_ONLY);
                            choix.addChoosableFileFilter(filtre_xml);
                            int r = choix.showOpenDialog(null);

                            try {
                                cheminFichierQuestions = "";
                                if (r == JFileChooser.APPROVE_OPTION) {
                                    // un fichier a été choisi ( sortie par OK)
                                    // nom du fichier  choisi
                                    //choix.getSelectedFile().getName();
                                    // chemin absolu du fichier choisi
                                    cheminFichierQuestions = choix.getSelectedFile().getAbsolutePath();
                                    // ICI parser le fichier d'options pour mettre le chemin du nouveau fichier a la place de "form.xml"
                                    Utils.setNewFormFile(cheminFichierQuestions);
                                    javax.swing.JOptionPane.showMessageDialog(null, Lang.getLang().getValueFromRef("SessionFrame.strQuestionsSetLoaded"));

                                    // Initialisation de la nouvelle session
                                    String nom = JOptionPane.showInputDialog(null, Lang.getLang().getValueFromRef("SessionFrame.strNameForNewSession"), Lang.getLang().getValueFromRef("SessionFrame.strFrameWarningBeforeClosingSession"), JOptionPane.QUESTION_MESSAGE);
                                    // Cloture de la session courante
                                    SessionFrame.getFrame().resetChrono();
                                    SessionManager.getSessionManager().closeSession(SessionManager.getSessionManager().getSessionCourante());
                                    if (!nom.equals("")) {
                                        SessionManager.getSessionManager().createSession(nom);
                                        SessionFrame.getFrame().RefreshFrame();
                                    } else {
                                        SessionManager.getSessionManager().createSession(Lang.getLang().getValueFromRef("SessionFrame.strDefaultSessionName"));
                                        SessionFrame.getFrame().RefreshFrame();
                                    }
                                    // Création terminée
                                    javax.swing.JOptionPane.showMessageDialog(null, Lang.getLang().getValueFromRef("SessionFrame.strSessionCreationSuccess"));
                                } else {
                                    javax.swing.JOptionPane.showMessageDialog(null, Lang.getLang().getValueFromRef("SessionFrame.strSessionCreationFailure"));
                                }
                                /**
                                 * Faire redirection ou message de succès
                                 */
                            } catch (BadXMLFileException ex) {
                                javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
                                javax.swing.JOptionPane.showMessageDialog(null, Lang.getLang().getValueFromRef("SessionFrame.strSessionCreationFailure"));
                            } catch (Exception ex) {
                                javax.swing.JOptionPane.showMessageDialog(null, Lang.getLang().getValueFromRef("SessionFrame.strXmlError") + ex.toString());
                                javax.swing.JOptionPane.showMessageDialog(null, Lang.getLang().getValueFromRef("SessionFrame.strSessionCreationFailure"));
                                /**
                                 * Faire redirection ou message d'erreur
                                 */
                            }
                        } else {
                            javax.swing.JOptionPane.showMessageDialog(null, Lang.getLang().getValueFromRef("SessionFrame.strSessionCreationFailure"));
                        }
                    }
                    SessionFrame.getFrame().RefreshFrame();

                } catch (SQLException ex) {
                    Logger.getLogger(Listeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            SessionFrame.getFrame().setAlwaysOnTop(true);
        } else if (s.equals(SessionFrame.labelBtCloseSessionCurrentSession)) {
            SessionFrame.getFrame().setAlwaysOnTop(false);
            int retour = JOptionPane.showConfirmDialog(SessionFrame.getFrame(), Lang.getLang().getValueFromRef("SessionFrame.strWarningClosingSession"), Lang.getLang().getValueFromRef("SessionFrame.strFrameWarningClosingSession"), JOptionPane.OK_CANCEL_OPTION);
            if (retour == 0) {
                try {
                    // Cloture de la session courante
                    SessionFrame.getFrame().resetChrono();
                    SessionManager.getSessionManager().closeSession(SessionManager.getSessionManager().getSessionCourante());
                    javax.swing.JOptionPane.showMessageDialog(null, Lang.getLang().getValueFromRef("SessionFrame.strSessionClosedSuccess"));
                    SessionFrame.getFrame().RefreshFrame();
                } catch (SQLException ex) {
                    Logger.getLogger(Listeners.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            SessionFrame.getFrame().setAlwaysOnTop(true);
        } else if (s.equals(SessionFrame.labelBtPauseCurrentSession)) {
            SessionFrame.getFrame().setAlwaysOnTop(false);
            SessionFrame.getFrame().launchChrono();
            SessionFrame.getFrame().setAlwaysOnTop(true);
        } else if (s.equals(SessionFrame.labelBtRetourDePauseCurrentSession)) {
            SessionFrame.getFrame().setAlwaysOnTop(false);
            //TimerQuestion.resetDateDelay();
            SessionFrame.getFrame().resetChrono();
            SessionFrame.btPauseCurrentSession.setText(SessionFrame.labelBtPauseCurrentSession);
            TaskTrayMenu.sessionItem.setLabel(TaskTrayMenu.SessionItemLabel);
            TaskTrayMenu.sessionItem.setFont(new Font("Verdana", Font.PLAIN, 12));
            SessionFrame.getFrame().RefreshFrame();
            SessionFrame.getFrame().setAlwaysOnTop(true);
        } else if (s.equals(SessionFrame.labelBtDeleteOldSessions)) {
            SessionFrame.getFrame().setAlwaysOnTop(false);
            try {
                SessionManager.getSessionManager().deleteOldSession(SessionFrame.getFrame().getSessionSelectionnee());
                SessionFrame.getFrame().RefreshFrame();
            } catch (SQLException ex) {
                Logger.getLogger(Listeners.class.getName()).log(Level.SEVERE, null, ex);
            }
            SessionFrame.getFrame().setAlwaysOnTop(true);
        } else if (s.equals(SessionFrame.labelBtOk)) {
            SessionFrame.getFrame().HideFrame();
        } else if (s.equals(SessionFrame.labelBtVisualize)) {
            ViewAnswers.getTheFrame().reset();
        }

    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
        AskFrame.getTheFrame().hideTheFrame();
    }

    public void windowIconified(WindowEvent e) {
        AskFrame.getTheFrame().hideTheFrame();
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }
}
