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
package api.utils;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class LaunchProjectPage.java
 * @description Open DEScribe's bug reporting web page in user's browser
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @author Bertrand Gros    <gros.bertrand@gmail.com>
 * @version 2011-02-06
 */
public class LaunchReportPage {

    private static Properties sys = null;
    private static String os = null;
    private static Runtime r = null;
    private static String ReportURL = "http://code.google.com/p/describe/issues/list";

    public static void LaunchPage() {
        if (getOs.isWindows()) {
            try {
                Runtime.getRuntime().exec("cmd.exe /c start " + ReportURL);
            } catch (Exception e) {
            }
        } else if (getOs.isMac()) {
            try {
                Runtime.getRuntime().exec(new String[]{"open", ReportURL});
            } catch (IOException ex) {
                Logger.getLogger(LaunchReportPage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(null, "Rendez-vous sur la page :\n"+ReportURL);
        }
    } //end LaunchPage$
} //end LaunchReportPage

