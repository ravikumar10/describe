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
package api.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImgTxtMerger extends JPanel {

    private BufferedImage image;

    public ImgTxtMerger(String file, String text) {
        try {
            image = ImageIO.read(new File(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setPreferredSize(new Dimension(
            image.getWidth(), image.getHeight()));
        image = process(image, text);
            File file2 = new File(file);
        try {
            javax.imageio.ImageIO.write(image, "png", file2);
        } catch (IOException ex) {
            Logger.getLogger(ImgTxtMerger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private BufferedImage process(BufferedImage old, String text) {
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

}
