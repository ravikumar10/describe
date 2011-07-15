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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

/**
 * Class PrefFrame.java
 * @description Settings' frame
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @author Bertrand Gros
 * @version 2011-01-28
 */
public class PrefFrame extends GenericFrame {

    private static interiorPanel thePanel = null;

    private static class interiorPanel extends JPanel {

        public String label01 = "Fréquence";
        public String label02 = "Normale";
        public String label03 = "Ralentie";
        public String label04 = "Sons";
        public String label05 = "Activer";
        public String label06 = "Choix du langage";
        public String label07 = "OK";
        public String label08 = "Annuler";
        public String label09 = "Rétablir";
        public String label10 = "English";
        PrefFrame listeners = null;
        JRadioButton c1 = null;
        JRadioButton c2 = null;
        JCheckBox jcb = null;
        JComboBox cb1 = null;
        JButton b1 = null;
        JButton b2 = null;
        JButton b3 = null;

        public interiorPanel(PrefFrame param) {
            super();
            this.listeners = param;
            this.setLayout(new BorderLayout(0, 10));
            this.add(this.theRows(), BorderLayout.CENTER);
            this.add(this.ButtonsRow(), BorderLayout.SOUTH);
            this.initThePanel();
        }

        private JPanel theRows(){
            JPanel res = new JPanel();
            res.setLayout(new GridLayout(3, 1));
            res.add(this.Row1());
            res.add(this.Row2());
            res.add(this.Row3());
            return res;
        }

        private JPanel oneRow() {
            JPanel res = new JPanel();
            res.setBorder(BorderFactory.createLineBorder(Color.darkGray));
            return res;
        }

        private JPanel Row1() {
            JPanel res = this.oneRow();
            res.setLayout(new GridLayout(3, 1));
            JLabel l = new JLabel(label01);
            c1 = new JRadioButton(label02);
            c2 = new JRadioButton(label03);
            ButtonGroup bg = new ButtonGroup();
            bg.add(c1);
            bg.add(c2);
            res.add(l);
            res.add(c1);
            res.add(c2);
            return res;
        }

        private JPanel Row2() {
            JPanel res = this.oneRow();
            res.setLayout(new GridLayout(2, 1));
            res.add(new JLabel(label04));
            jcb = new JCheckBox(label05);
            res.add(jcb);
            return res;
        }

        private JPanel Row3() {
            JPanel res = this.oneRow();
            res.setLayout(new GridLayout(2, 1));
            res.add(new JLabel(label06));
            String[] langChoice = {label10};
            cb1 = new JComboBox(langChoice);
            res.add(cb1);
            return res;
        }

        private JPanel ButtonsRow() {
            JPanel res = this.oneRow();
            b1 = new JButton(label07);
            b1.addActionListener(listeners);
            b2 = new JButton(label08);
            b2.addActionListener(listeners);
            b3 = new JButton(label09); 
            b3.addActionListener(listeners);
            res.add(b1);
            res.add(b2);
            res.add(b3);
            return res;
        }

        public void initThePanel() {
            c1.setSelected(true);
            jcb.setSelected(true);
            cb1.setSelectedIndex(0);
        }
    }

    private PrefFrame() {
        super("Préférences"); //i18n
        thePanel = new interiorPanel(this);
        getContentPane().add(thePanel);
        pack();
    }
    private static PrefFrame instance;

    public static PrefFrame getTheFrame() {
        if (instance == null) {
            instance = new PrefFrame();
        }
        return instance;
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.equals(thePanel.label08)) {
            PrefFrame.getTheFrame().hideTheFrame();

        } else if (action.equals(thePanel.label09)) {
            thePanel.initThePanel();
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
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

}
