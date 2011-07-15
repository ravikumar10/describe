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

package model;

import api.utils.getOs;
import exceptions.BadXMLFileException;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ActionScreenshot extends Action {

    private String absoluteFileName;

    public ActionScreenshot() {
        name ="screenshot";
    }

    public void takeCapture(String resultFileName) {
        try {

            Robot robot = new Robot();
            // Capture the screen shot of the area of the screen defined by the rectangle
            BufferedImage bi=robot.createScreenCapture(new Rectangle((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()));

           
            if (getOs.isWindows()) {
                //Create Sessions folder if doesn't already exist
                // Create new session folder if doesn't already exist
                try {
                    new File(api.xml.Utils.loadSessionsFolder()).mkdir();                            
                    new File(api.xml.Utils.loadSessionsFolder() + "\\session" + SessionManager.getSessionManager().getSessionCourante().getId()).mkdir();
                } catch (BadXMLFileException ex) {
                    Logger.getLogger(ActionScreenshot.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ActionScreenshot.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {
                    File tf = new File(api.xml.Utils.loadSessionsFolder() + "\\session" + SessionManager.getSessionManager().getSessionCourante().getId() + "\\" + resultFileName + ".jpg");
                    api.utils.ImageBytes.createFileFromBytes(api.utils.ImageBytes.bufferedImageToByteArray(bi), tf);
//                    ImageIO.write(bi, "jpg", tf);
                    absoluteFileName = api.xml.Utils.loadSessionsFolder() + "\\session" + SessionManager.getSessionManager().getSessionCourante().getId() + "\\" + resultFileName + ".jpg";
                } catch (SQLException ex) {
                    Logger.getLogger(ActionScreenshot.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BadXMLFileException ex) {
                Logger.getLogger(ActionScreenshot.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } else {
                try {
                    new File(api.xml.Utils.loadSessionsFolder()).mkdir();
                    new File(api.xml.Utils.loadSessionsFolder() + "/session" + SessionManager.getSessionManager().getSessionCourante().getId()).mkdir();
                } catch (BadXMLFileException ex) {
                    Logger.getLogger(ActionScreenshot.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ActionScreenshot.class.getName()).log(Level.SEVERE, null, ex);
                }

                try {

                    File tf = new File(api.xml.Utils.loadSessionsFolder() + "/session" + SessionManager.getSessionManager().getSessionCourante().getId() + "/" + resultFileName + ".jpg");
                    api.utils.ImageBytes.createFileFromBytes(api.utils.ImageBytes.bufferedImageToByteArray(bi), tf);
                    //ImageIO.write(bi, "jpg", tf);
                    absoluteFileName = api.xml.Utils.loadSessionsFolder() + "/session" + SessionManager.getSessionManager().getSessionCourante().getId() + "/" + resultFileName + ".jpg";
                } catch (SQLException ex) {
                    Logger.getLogger(ActionScreenshot.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BadXMLFileException ex) {
                Logger.getLogger(ActionScreenshot.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            bi.flush();
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getAbsFileName(){
        return absoluteFileName;
    }

}

