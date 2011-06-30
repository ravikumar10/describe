package api.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Seb
 */
public class FullScreenDetector {
            private static Boolean isFullScreen;
  public static Boolean isFullScreenProgramRunning() {
      if (api.utils.getOs.isWindows()){
        isFullScreen=false;
        try {
            Runtime runtime = Runtime.getRuntime();
            final Process process = runtime.exec("testDEScribe.exe");
            // Consommation de la sortie standard de l'application externe dans un Thread separe


            Thread t=new Thread() {

                public void run() {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                        String line = "";
                        try {
                            while ((line = reader.readLine()) != null) {
                                // Traitement du flux de sortie de l'application si besoin est
                               System.out.println(line);
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
                    Runtime.getRuntime().exec(new String[]{"open", "testDEScribe.app"});
                } catch (IOException ex) {
                    Logger.getLogger(FullScreenDetector.class.getName()).log(Level.SEVERE, null, ex);
                }
          }
        return false;
      }
  }
}