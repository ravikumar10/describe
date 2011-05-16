/**
 *
DEScribe - A Discrete Experience Sampling cross platform application
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

package model;

import api.utils.getOs;
import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ActionScreenshot extends Action {

    private String absoluteFileName;
    
    public ActionScreenshot(String resultFileName){
        name ="screenshot";
        takeCapture(resultFileName);
    }

    public ActionScreenshot() {
        name ="screenshot";
    }

    public void takeCapture(String resultFileName) {
        try {

            Robot robot = new Robot();
            // Capture the screen shot of the area of the screen defined by the rectangle
            BufferedImage bi=robot.createScreenCapture(new Rectangle((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()));

           
            if (getOs.isWindows()) {
                ImageIO.write(bi, "jpg", new File(".\\action_results\\screenshots\\"+resultFileName+".jpg"));
                absoluteFileName = ".\\action_results\\screenshots\\"+resultFileName+".jpg";
            } else {
                ImageIO.write(bi, "jpg", new File("./action_results/screenshots/"+resultFileName+".jpg"));
                absoluteFileName = "./action_results/screenshots/"+resultFileName+".jpg";
            }
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

