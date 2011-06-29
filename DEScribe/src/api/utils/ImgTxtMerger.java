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

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import model.Reponse;

public class ImgTxtMerger extends JPanel {

    private BufferedImage image;

    public ImgTxtMerger(){
        
    }

    public ImgTxtMerger(String file, String text) {
       // try {
            image.flush();
            //image = ImageIO.read(new File(file));
            image = api.utils.ImageBytes.FileToBufferedImage(file);
       // } catch (IOException e) {
       //     e.printStackTrace();
       // }
        this.setPreferredSize(new Dimension(
            image.getWidth(), image.getHeight()));
        image = process(image, text);
            File file2 = new File(file);
        try {
            // try {
            api.utils.ImageBytes.createFileFromBytes(api.utils.ImageBytes.bufferedImageToByteArray(image), file2);
        } catch (ImageFormatException ex) {
            Logger.getLogger(ImgTxtMerger.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ImgTxtMerger.class.getName()).log(Level.SEVERE, null, ex);
        }
            //javax.imageio.ImageIO.write(image, "gif", file2);
            image.flush();
        //} catch (IOException ex) {
        //    Logger.getLogger(ImgTxtMerger.class.getName()).log(Level.SEVERE, null, ex);
        //}
    }

    public BufferedImage process(BufferedImage old, String text) {
        int w = old.getWidth();
        int h = old.getHeight();
        BufferedImage img = new BufferedImage(
            w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(old, 0, 0, null);
        g2d.setPaint(Color.white);
        g2d.setBackground(Color.white);
        g2d.setFont(new Font("Serif", Font.PLAIN, 19));
        String s = text;
        FontMetrics fm = g2d.getFontMetrics();
        int x = img.getWidth() - fm.stringWidth(s) - 5;
        int y = fm.getHeight();
        g2d.fill(new Rectangle2D.Double(0, 0, img.getWidth(), 120));
        g2d.setPaint(Color.black);
        //g2d.drawString(s, x, y);
        drawString(g2d, text, 8, fm.getHeight(), img.getWidth());
        g2d.dispose();
        return img;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

    public static void merge(String file, String text) {
        //JFrame f = new JFrame();
        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //f.add(new ImgTxtMerger(".\\action_results\\screenshots\\session1_reponse8_screenshot.jpg", "Mon text"));
        new ImgTxtMerger(file, text);
       // f.pack();
        //f.setVisible(true);
    }

    public void drawString(Graphics g, String s, int x, int y, int width)
    {
        // FontMetrics gives us information about the width,
        // height, etc. of the current Graphics object's Font.
        FontMetrics fm = g.getFontMetrics();

        int lineHeight = fm.getHeight();

        int curX = x;
        int curY = y;

        String[] words = s.split(" ");

        for (String word : words)
        {
                // Find out thw width of the word.
                int wordWidth = fm.stringWidth(word + " ");

                // If text exceeds the width, then move to next line.
                if ((curX + wordWidth >= x + width) || (word.equals("\n")))
                {
                        curY += lineHeight;
                        curX = x;
                }
                if (!word.equals("\n")){
                    g.drawString(word, curX, curY);
                }

                // Move over to the right for next word.
                curX += wordWidth;
        }



    }

    public void fusion(Reponse r){
        try {

            BufferedImage src = ImageIO.read(new File(r.getScreenshot()));
            //BufferedImage src=api.utils.ImageBytes.FileToBufferedImage(r.getScreenshot());
            int newHeight= (int)80*src.getHeight()/100;
            int newWidth= src.getWidth();

            // Rectangle blanc au dessus
            int rectHeight = (int) newHeight*20/100;
            BufferedImage buf = new BufferedImage(newWidth, rectHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = buf.createGraphics();
            g2.drawRect(0, 0, newWidth, rectHeight);

            BufferedImage res=process(resize(append(buf, src), newWidth, newHeight), r.getIntituleQuestion() + " \n " + r.getLaReponse() + " \n " + r.getInstant().toString());
            File file2 = new File(r.getScreenshot());
            try {
                //api.utils.ImageBytes.createFileFromBytes(api.utils.ImageBytes.bufferedImageToByteArray(res), file2);
                javax.imageio.ImageIO.write(res, "gif", file2);
                res.flush();
                buf.flush();
                g2.dispose();
            } catch (IOException ex) {
                Logger.getLogger(ImgTxtMerger.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(ImgTxtMerger.class.getName()).log(Level.SEVERE, null, ex);
        }

    }




    // New version
    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
            int w = img.getWidth();
            int h = img.getHeight();
            BufferedImage dimg = dimg = new BufferedImage(newW, newH, img.getType());
            Graphics2D g = dimg.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
            g.dispose();
            return dimg;
        }



    // Pour fusionner le screenshot avec un blanc en haut:
    public static BufferedImage append(Image img1, Image img2) {
        BufferedImage buf = null;
        if(img1 != null && img2 != null) {
            int w1 = img1.getWidth(null);
            int h1 = img1.getHeight(null);
            int w2 = img2.getWidth(null);
            int h2 = img2.getHeight(null);
            int hMax = 0;
            int wMax = 0;

            hMax = h1 + h2;
            wMax = (w1 >= w2) ? w1 : w2;
            buf = new BufferedImage(wMax, hMax, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = buf.createGraphics();
            g2.drawImage(img1, 0, 0, null);
            g2.drawImage(img2, 0, h1, null);
        }
        return buf;
    }

}
