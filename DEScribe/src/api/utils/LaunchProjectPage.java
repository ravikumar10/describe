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

package api.utils;

import des.Main;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class LaunchProjectPage.java
 * @description Open DEScribe's web page in user's browser
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @version 2011-07-18
 */
public class LaunchProjectPage {

    private static Properties sys = null;
    private static String os = null;
    private static Runtime r = null;

    /**
     * Launch DEScribe's project web page
     */
    public static void LaunchPage() {
        if (getOs.isWindows()) {
            try {
                Runtime.getRuntime().exec("cmd.exe /c start " + Main.projectPage);
            } catch (Exception e) {
            }
        } else if (getOs.isMac()) {
            try {
                Runtime.getRuntime().exec(new String[]{"open", Main.projectPage});
            } catch (IOException ex) {
                Logger.getLogger(LaunchReportPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Rendez-vous sur la page :\n"+Main.projectPage);
        }
    } //end LaunchPage$
} //end LaunchReportPage

