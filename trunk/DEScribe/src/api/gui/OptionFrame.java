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

import api.i18n.Lang;
import api.utils.DirFileFilter;
import api.utils.getOs;
import api.xml.Utils;
import api.xml.Xmlfilter;
import exceptions.BadXMLFileException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * Class OptionFrame.java
 * @description Old settings' frame
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @author Bertrand Gros    <gros.bertrand@gmail.com>
 * @version 2011-01-28
 */
public class OptionFrame extends JFrame {

    static protected OptionFrame of = null;
    public static JRadioButton c1 = null;
    public static JRadioButton c2 = null;
    public static JRadioButton c3 = null;
    public static JRadioButton c4 = null;
    public static JCheckBox jrb = null;
    public static JButton okButton = null;
    public static JButton cancelButton = null;
    public static JButton defaultButton = null;
    public static JLabel jl = null;
    public static JLabel jl2 = null;
    public static JLabel jl3 = null;
    public static String currentLang = null;


    private String opt1 = "English";
    private String opt2 = "Francais";

    public static JLabel jl4 = null;
    public JTextField sessionFolder=null;
    public static JButton browseButton = null;

    public static String defaultSessionFolder="sessions";


    private OptionFrame() {
        // LA FENETRE PRINCIPALE
        this.setTitle(Lang.getLang().getValueFromRef("OptionFrame.title"));
        this.setIconImage(new ImageIcon("media/des.gif").getImage());
        this.setResizable(false);

        // LA FENETRE INTERIEURE
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        panel.setPreferredSize(new Dimension(300, 300));
        panel.setBackground(new Color(178, 34, 34));
        this.getContentPane().add(panel);

        JPanel Option1 = new JPanel(new GridLayout(3, 1));
        Option1.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        Option1.setBackground(new Color(178, 34, 34));
        Option1.setPreferredSize(new Dimension(300, 0));
        c1 = new JRadioButton(Lang.getLang().getValueFromRef("OptionFrame.normalFrequency"));
        c1.setBackground(new Color(178, 34, 34));
        c1.setForeground(Color.white);
        c2 = new JRadioButton(Lang.getLang().getValueFromRef("OptionFrame.slowFrequency"));
        c2.setBackground(new Color(178, 34, 34));
        c2.setForeground(Color.white);
        ButtonGroup bg = new ButtonGroup();
        bg.add(c1);
        bg.add(c2);
        jl = new JLabel(Lang.getLang().getValueFromRef("OptionFrame.frequencies"));
        jl.setForeground(Color.white);
        jl.setFont(new Font("Verdana", Font.BOLD, 14));
        Option1.add(jl);
        Option1.add(c1);
        Option1.add(c2);

        JPanel Option2 = new JPanel(new GridLayout(2, 1));
        Option2.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        Option2.setBackground(new Color(178, 34, 34));
        Option2.setPreferredSize(new Dimension(300, 0));
        jrb = new JCheckBox(Lang.getLang().getValueFromRef("OptionFrame.soundEnable"));
        jrb.setBackground(new Color(178, 34, 34));
        jrb.setForeground(Color.white);
        jl2 = new JLabel(Lang.getLang().getValueFromRef("OptionFrame.sound"));
        jl2.setForeground(Color.white);
        jl2.setFont(new Font("Verdana", Font.BOLD, 14));
        Option2.add(jl2);
        Option2.add(jrb);

        JPanel Option3 = new JPanel(new GridLayout(3, 1));
        Option3.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        Option3.setBackground(new Color(178, 34, 34));
        Option3.setPreferredSize(new Dimension(300, 0));
        c3 = new JRadioButton(opt1);
        c3.setBackground(new Color(178, 34, 34));
        c3.setForeground(Color.white);
        c4 = new JRadioButton(opt2);
        c4.setBackground(new Color(178, 34, 34));
        c4.setForeground(Color.white);
        ButtonGroup bg2 = new ButtonGroup();
        bg2.add(c3);
        bg2.add(c4);
        jl3 = new JLabel(Lang.getLang().getValueFromRef("OptionFrame.language"));
        jl3.setForeground(Color.white);
        jl3.setFont(new Font("Verdana", Font.BOLD, 14));
        Option3.add(jl3);
        Option3.add(c3);
        Option3.add(c4);

        JPanel Option4 = new JPanel(new GridLayout(3, 1));
        Option4.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        Option4.setBackground(new Color(178, 34, 34));
        Option4.setPreferredSize(new Dimension(300, 0));
        sessionFolder = new JTextField("");
        sessionFolder.setSize(new Dimension(240, 30));
        //jrb.setBackground(new Color(178, 34, 34));
        //jrb.setForeground(Color.white);
        sessionFolder.setEditable(false);
        jl4 = new JLabel(Lang.getLang().getValueFromRef("OptionFrame.sessions"));
        jl4.setForeground(Color.white);
        jl4.setFont(new Font("Verdana", Font.BOLD, 14));
        browseButton = new JButton(Lang.getLang().getValueFromRef("OptionFrame.browseButton"));
        browseButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                OptionFrame.getOptionFrame().setAlwaysOnTop(false);
                String path;
                if (!getOs.isWindows()){
                    /* Mac / Linux */
                    System.setProperty("apple.awt.fileDialogForDirectories", "true");
                    FileDialog d = new FileDialog(SessionFrame.getFrame());
                    d.setVisible(true);
                    if (d.getFile()!=null){
                            path = "";
                            // un fichier a été choisi ( sortie par OK)
                            // nom du fichier  choisi
                            //choix.getSelectedFile().getName();
                            // chemin absolu du fichier choisi
                            path = d.getDirectory()+d.getFile();
                            sessionFolder.setEditable(true);
                            sessionFolder.setText(path);
                            sessionFolder.setEditable(false);
                    }
                }
                else {
                    JFileChooser choix = new JFileChooser();
                    choix.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    choix.addChoosableFileFilter(new DirFileFilter());
                    int retour = choix.showOpenDialog(null);

                    path = "";
                    if (retour == JFileChooser.APPROVE_OPTION) {
                        path = choix.getSelectedFile().getAbsolutePath();
                        sessionFolder.setEditable(true);
                        sessionFolder.setText(path);
                        sessionFolder.setEditable(false);
                    }
                }

                OptionFrame.refresh();
                OptionFrame.getOptionFrame().setAlwaysOnTop(true);
            }
        });
        Option4.add(jl4);
        Option4.add(sessionFolder);
        Option4.add(browseButton);



        //LA LIGNE DES BOUTTONS
        JPanel buttonline = new JPanel();
        buttonline.setLayout(new FlowLayout());
        buttonline.setBackground(new Color(178, 34, 34));
        okButton = new JButton(Lang.getLang().getValueFromRef("OptionFrame.okButton"));
        cancelButton = new JButton(Lang.getLang().getValueFromRef("OptionFrame.cancelButton"));
        defaultButton = new JButton(Lang.getLang().getValueFromRef("OptionFrame.resetButton"));
        buttonline.add(okButton);
        buttonline.add(cancelButton);
        buttonline.add(defaultButton);

        okButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    OptionFrame.saveSettings();
                } catch (BadXMLFileException ex) {
                    Logger.getLogger(OptionFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (c3.isSelected()) {
                    Lang.getLang().load(opt1);
                }
                if (c4.isSelected()) {
                    Lang.getLang().load(opt2);
                }

                TaskTrayMenu.refresh();
                OptionFrame.refresh();
                //javax.swing.JOptionPane.showMessageDialog(null, Lang.getLang().getValueFromRef("OptionFrame.reConfigSuccess"));
            }
        });

        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                OptionFrame.getOptionFrame().HideFrame();
            }
        });

        defaultButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                c1.setSelected(true);
                jrb.setSelected(true);
                c3.setSelected(true);
                sessionFolder.setText(defaultSessionFolder);
            }
        });

        panel.add(Option1);
        panel.add(Option2);
        panel.add(Option3);
        panel.add(Option4);
        panel.add(buttonline);

        this.setAlwaysOnTop(true);
        
        this.pack();

    }

    public static OptionFrame getOptionFrame() {
        if (of == null) {
            of = new OptionFrame();
        }
        return of;
    }

    public void ShowFrame() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);
        OptionFrame.loadSettings();
        setVisible(true);
    }

    public void HideFrame() {
        setVisible(false);
    }

    public boolean isNormalSpeed() {
        return c1.isSelected();
    }

    public static boolean isSoundEnabled() {
        return jrb.isSelected();
    }

    private static void saveSettings() throws BadXMLFileException {
        Utils.saveSettings();
        OptionFrame.getOptionFrame().HideFrame();
    }

    private static void loadSettings() {
        try {
            Utils.loadSettings();
        } catch (BadXMLFileException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void setSessionFolder(String outputDir) {
        sessionFolder.setText(outputDir);
    }

    public static void refresh() {
        jl.setText(Lang.getLang().getValueFromRef("OptionFrame.frequencies"));
        c1.setText(Lang.getLang().getValueFromRef("OptionFrame.normalFrequency"));
        c2.setText(Lang.getLang().getValueFromRef("OptionFrame.slowFrequency"));
        jl2.setText(Lang.getLang().getValueFromRef("OptionFrame.sound"));
        jrb.setText(Lang.getLang().getValueFromRef("OptionFrame.soundEnable"));
        jl3.setText(Lang.getLang().getValueFromRef("OptionFrame.language"));
        okButton.setText(Lang.getLang().getValueFromRef("OptionFrame.okButton"));
        cancelButton.setText(Lang.getLang().getValueFromRef("OptionFrame.cancelButton"));
        defaultButton.setText(Lang.getLang().getValueFromRef("OptionFrame.resetButton"));
    }
}
