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

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * Class GenericFrame.java
 * @description Abstract frame for the application
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @author Bertrand Gros
 * @version 2011-01-28
 */
public abstract class GenericFrame extends JFrame implements ActionListener, WindowListener, KeyListener {

    protected int x = 0;
    protected int y = 0;

    protected GenericFrame() {
        this("Untitled");
    }
    protected GenericFrame(String title) {
        this.setIconImage(new ImageIcon("media/des.gif").getImage());
        this.setTitle(title);
        this.setResizable(false);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setUndecorated(true);
        this.setAlwaysOnTop(true);
        this.addWindowListener(this);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        x=screen.width;
        y=screen.height;
    }

    public void showTheFrame(String quest) {
        this.setLocation((x - this.getSize().width) / 2, (y - this.getSize().height) / 2);
        this.setVisible(true);
    }

    public void hideTheFrame() {
        this.setVisible(false);
    }
}
