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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FullScreenDetector {
            private static Boolean isFullScreen;
  public static Boolean isFullScreenProgramRunning() {
      isFullScreen=false;
      //System.out.println("Testing fullscreen mode...");
      if (api.utils.getOs.isWindows()){
        try {
            Runtime runtime = Runtime.getRuntime();
            final Process process = runtime.exec(".\\tools\\testFS.exe");
            // Consommation de la sortie standard de l'application externe dans un Thread separe


            Thread t=new Thread() {

                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String line = "";
                        try {
                            while ((line = reader.readLine()) != null) {
                                // Traitement du flux de sortie de l'application si besoin est
                               //System.out.println(line);
                                if (line.equals("fullscreen")){
                                    isFullScreen=true;
                                } else {
                                    isFullScreen=false;
                                }
                            }
                        } finally {
                            reader.close();
                            return;
                        }
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            };
            t.start();
                try {
                    t.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FullScreenDetector.class.getName()).log(Level.SEVERE, null, ex);
                }
        } catch (IOException ex) {
            Logger.getLogger(FullScreenDetector.class.getName()).log(Level.SEVERE, null, ex);
        }

        return isFullScreen;
      }
      else
      {
          if (api.utils.getOs.isMac()){
                try {
                    final Process process=Runtime.getRuntime().exec(new String[]{"tools/testFS"});
                    Thread t=new Thread() {

                    public void run() {
                        try {
                            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String line = "";
                            try {

                                while ((line = reader.readLine()) != null) {
                                    // Traitement du flux de sortie de l'application si besoin est
                                   //System.out.println("ligne lue : "+line);
                                   //javax.swing.JOptionPane.showMessageDialog(null, line);
                                    if (line.equals("fullscreen")){
                                        isFullScreen=true;
                                    } else {
                                        isFullScreen=false;
                                    }
                                }
                            } finally {
                                reader.close();
                                return;
                            }
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    }
                };
                t.start();
                    try {
                        t.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FullScreenDetector.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    } catch (IOException ex) {
                        Logger.getLogger(FullScreenDetector.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return isFullScreen;
          }
        return false;
      }
  }
}
