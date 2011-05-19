/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package api.gui;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author Seb
 */
public class ViewAnswers extends JFrame {

    private JPanel jpImg;
    private JPanel jpControls;
    private JPanel jpInternal;

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

    public ViewAnswers(){
        // 2 JPanels
        // One for pictures, other for controls
        jpInternal = new JPanel();
        jpInternal.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        jpImg = new JPanel();
        jpControls = new JPanel(new FlowLayout());

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

       
        try {
            /* BufferedImage image;
            image = ImageIO.read(new File("test.png"));
            JLabel imgContainer = new JLabel(new ImageIcon(image));

            jpImg.add(imgContainer);
            c.gridx = 1;
            c.gridy = 1;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.weightx = 100;
            c.weighty = 100;
            c.fill = GridBagConstraints.NONE;
            c.anchor = GridBagConstraints.CENTER;
            c.insets = new Insets(0,0,0,0);
            jpImg.add(imgContainer);
            jpControls.add(new JButton("BLABLA"));
            jpInternal.add(jpImg, c);


            c.gridx = 1;
            c.gridy = 2;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.weightx = 100;
            c.weighty = 100;
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.CENTER;
            c.insets = new Insets(0,10,0,10);
            jpInternal.add(jpControls,c);*/

            this.setSize(screen.width, screen.height);
            int width = screen.width;
            int height = (int)80*screen.height/100;
            BufferedImage src = ImageIO.read(new File("test2.jpg"));
            JLabel imgContainer = new JLabel(new ImageIcon(resize(src, width, height)));

            jpImg.add(imgContainer);
            c.gridx = 1;
            c.gridy = 1;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.weightx = 100;
            c.weighty = 100;
            c.fill = GridBagConstraints.NONE;
            c.anchor = GridBagConstraints.CENTER;
            c.insets = new Insets(0,0,0,0);
            jpImg.add(imgContainer);
            jpInternal.add(jpImg, c);

            JLabel answer = new JLabel("Anwser #");
            JLabel answerID = new JLabel("1");
            JLabel session = new JLabel("Session #");
            JLabel sessionID = new JLabel("1");
            Choice ch = new Choice();
            ch.add("1");
            ch.add("2");
            ch.add("3");
            JButton deleteButton = new JButton("Delete answer");
            JButton previousButton = new JButton("Previous");
            JButton nextButton = new JButton("Next");
            JButton closeButton = new JButton("Close");
            jpControls.add(answer);
            jpControls.add(answerID);
            jpControls.add(session);
            //jpControls.add(sessionID);
            jpControls.add(ch);
            jpControls.add(deleteButton);
            jpControls.add(previousButton);
            jpControls.add(nextButton);
            jpControls.add(closeButton);
 
            jpInternal.setBackground(Color.white);

            c.gridx = 1;
            c.gridy = 2;
            c.gridwidth = 1;
            c.gridheight = 1;
            c.weightx = 100;
            c.weighty = 100;
            c.fill = GridBagConstraints.BOTH;
            c.anchor = GridBagConstraints.CENTER;
            c.insets = new Insets(0,10,0,10);
            jpInternal.add(jpControls,c);


        } catch (IOException ex) {
            Logger.getLogger(ViewAnswers.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.getContentPane().add(jpInternal);

        this.setTitle("Answers visualization");
        //this.setPreferredSize(new Dimension(800,600));

        this.setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);
        this.pack();
        this.setVisible(false);


    }
}
