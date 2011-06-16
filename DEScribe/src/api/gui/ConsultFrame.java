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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Class ConsultFrame.java
 * @description Frame to browse a session's answers
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @author Bertrand Gros    <gros.bertrand@gmail.com>
 * @version 2011-01-28
 */
public class ConsultFrame extends GenericFrame {

    public static String lbAnswersPresentation = Lang.getLang().getValueFromRef("ConsultFrame.lbAnswersPresentation");
    public static String okButton = Lang.getLang().getValueFromRef("ConsultFrame.okButton");
    private static JTextArea jta = null;

    private ConsultFrame(String param) {
        super(lbAnswersPresentation);
        BorderLayout bl = new BorderLayout();
        JPanel jp = new JPanel(bl);

        jta = new JTextArea();
        jta.setEditable(false);

        JScrollPane jsp = new JScrollPane(jta);
        jsp.setBackground(Color.darkGray);
        jsp.setPreferredSize(new Dimension(500, 300));

        JButton b = new JButton(okButton);
        b.addActionListener(this);

        JTextField jtf = new JTextField(SessionFrame.getFrame().lbCurrentSessionName.getText());
        jtf.setEditable(false);
        jp.add(jtf, BorderLayout.NORTH);
        jp.add(jsp, BorderLayout.CENTER);
        jp.add(b, BorderLayout.SOUTH);

        getContentPane().add(jp);
        
        fill(param);
        this.setAlwaysOnTop(true);
        pack();
    }
    private static ConsultFrame instance;

    public static ConsultFrame getTheFrame(String param) {
        if (instance == null) {
            instance = new ConsultFrame(param);
        }
        fill(param);
        return instance;
    }

    private static void fill(String param) {
        ConsultFrame.jta.setText(param);
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.equals(okButton)) {
            this.setVisible(false);
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
