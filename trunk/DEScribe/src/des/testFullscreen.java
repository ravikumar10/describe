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

package des;

import java.util.logging.Level;
import java.util.logging.Logger;

public class testFullscreen {

  public static void main(String[] args) {
            try {
                Thread.currentThread().sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(testFullscreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            Boolean b = api.utils.FullScreenDetector.isFullScreenProgramRunning();
            if (b) {
                System.out.println("Plein écran détecté");
            } else {
                System.out.println("Pas de programme en plein écran");
            }
  }
}