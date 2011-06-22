/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package des;

import java.util.logging.Level;
import java.util.logging.Logger;

public class testFullscreen {

  public static void main(String[] args) {
            /*try {
                Thread.currentThread().sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(testFullscreen.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            Boolean b = api.utils.FullScreenDetector.isFullScreenProgramRunning();
            if (b) {
                System.out.println("Plein écran détecté");
            } else {
                System.out.println("Pas de programme en plein écran");
            }
  }
}