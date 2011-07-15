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

package api.gui;

import api.dbc.DBConnexion;
import api.i18n.Lang;
import api.utils.ImgTxtMerger;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.List;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import model.Reponse;
import model.Session;
import model.SessionManager;

/**
 *
 * @author Seb
 */
public class ViewAnswers extends JFrame {

    static Object getFrame() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private JPanel jpImg;
    private JPanel jpControls;
    private JPanel jpInternal;
    JLabel imgContainer;

    private static ViewAnswers instance;

    private List listSessionID;
    private List listAnswerID;

    private Session selectedSession;
    private ArrayList<Reponse> selectedSessionAnswers;

    public static String lbAnswer = Lang.getLang().getValueFromRef("ViewAnswers.lbAnswer");
    public static String lbSession = Lang.getLang().getValueFromRef("ViewAnswers.lbSession");
    public static String btDelete = Lang.getLang().getValueFromRef("ViewAnswers.btDelete");
    public static String btNext = Lang.getLang().getValueFromRef("ViewAnswers.btNext");
    public static String btPrevious = Lang.getLang().getValueFromRef("ViewAnswers.btPrevious");
    public static String btClose = Lang.getLang().getValueFromRef("ViewAnswers.btClose");
    public static String title = Lang.getLang().getValueFromRef("ViewAnswers.title");

    private ViewAnswers() {
        // 2 JPanels
        // One for pictures, other for controls
        jpInternal = new JPanel();
        jpInternal.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        jpImg = new JPanel();
        jpControls = new JPanel(new FlowLayout());

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();


        try {

            this.setSize(screen.width, screen.height);
            int width = screen.width;
            int height = (int)80*screen.height/100;
            //BufferedImage src = ImageIO.read(new File("test2.jpg"));
            //imgContainer = new JLabel(new ImageIcon(resize(src, width, height)));
            imgContainer=new JLabel();
            imgContainer.setSize(width, height);
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

            JLabel answer = new JLabel(lbAnswer);
            JLabel session = new JLabel(lbSession);

            listSessionID = new List();
      /*      listSessionID.add("1");
            listSessionID.add("2");
            listSessionID.add("3");*/

            SessionManager sm;
            try {
                sm = SessionManager.getSessionManager();
                for (Iterator<Session> it = sm.getLesSessions().iterator(); it.hasNext();) {
                    Session s = it.next();
                    listSessionID.add(""+s.getId());
                }
                //selectedSession=sm.getLesSessions().get(0);
                
        selectedSession=SessionFrame.getFrame().getSessionSelectionnee();
        DBConnexion conn = DBConnexion.getConnexion();
        selectedSessionAnswers=conn.getEntriesBySession(selectedSession);
        int ind=0;
        while (!listSessionID.getItem(ind).equals(Long.toString(selectedSession.getId()))){
            ind++;
            listSessionID.select(ind);
        }
             
                //listSessionID.select(0);
                //DBConnexion conn = DBConnexion.getConnexion();
                //selectedSessionAnswers=conn.getEntriesBySession(selectedSession);
                listAnswerID=new List();
                for (Iterator<Reponse> it = selectedSessionAnswers.iterator(); it.hasNext();) {
                    Reponse r = it.next();
                    listAnswerID.add(""+r.getId());
                }
                for (Iterator<Reponse> it = selectedSessionAnswers.iterator(); it.hasNext();) {
                    it.next().setSession(selectedSession);
                }
                if (selectedSessionAnswers.size()>0){
                    listAnswerID.select(0);
                    //refresh();
                }

            } catch (SQLException ex) {
                Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            //panelDownList.add(c);
            listSessionID.addItemListener(new ItemListener() {

                public void itemStateChanged(ItemEvent ie) {
                    if (ie.getStateChange() == ItemEvent.SELECTED) {
                        if (!listSessionID.getSelectedItem().equals("")){
                            DBConnexion conn = DBConnexion.getConnexion();
                            selectedSession=conn.getSessionById(Long.parseLong(listSessionID.getSelectedItem()));
                            selectedSessionAnswers=conn.getEntriesBySession(selectedSession);
                            if (selectedSessionAnswers.size()>0){
                                listAnswerID.removeAll();
                                for (Iterator<Reponse> it = selectedSessionAnswers.iterator(); it.hasNext();) {
                                    Reponse r = it.next();
                                    listAnswerID.add(""+r.getId());
                                }
                                listAnswerID.select(0);
                            }
                            refresh();
                        }
                    }
                }
            });
         
            listAnswerID.addItemListener(new ItemListener() {

                public void itemStateChanged(ItemEvent ie) {
                    if (ie.getStateChange() == ItemEvent.SELECTED) {
                        if (!listAnswerID.getSelectedItem().equals("")){
                            refresh();
                        }
                    }
                }
            });

            JButton deleteButton = new JButton(btDelete);
            JButton previousButton = new JButton(btPrevious);
            JButton nextButton = new JButton(btNext);
            nextButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (selectedSessionAnswers.size()>0){
                        if(listAnswerID.getSelectedIndex()==selectedSessionAnswers.size()-1){
                            listAnswerID.select(0);
                        } else {
                            listAnswerID.select(listAnswerID.getSelectedIndex()+1);
                        }
                    }
                    refresh();
                }
            });

            previousButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (selectedSessionAnswers.size()>0){
                        if(listAnswerID.getSelectedIndex()== 0){
                            listAnswerID.select(selectedSessionAnswers.size()-1);
                        } else {
                            listAnswerID.select(listAnswerID.getSelectedIndex()-1);
                        }
                        refresh();
                    }
                }
            });

            deleteButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (selectedSessionAnswers.size()>0)
                        selectedSessionAnswers.get(listAnswerID.getSelectedIndex()).deleteReponse();
                    reset();
                }
            });

            JButton closeButton = new JButton(btClose);
            closeButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

            jpControls.add(answer);
            jpControls.add(listAnswerID);
            jpControls.add(session);
            jpControls.add(listSessionID);
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
            //  refresh();

        } catch (Exception ex) {
            Logger.getLogger(ViewAnswers.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.getContentPane().add(jpInternal);
        this.setVisible(false);
        this.setTitle(title);
        //this.setPreferredSize(new Dimension(800,600));

        this.setAlwaysOnTop(true);

        this.setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);


        this.pack();
    }

    public static ViewAnswers getTheFrame() {
        if (instance == null) {
            instance = new ViewAnswers();
        }
        return instance;
    }

    public void refresh() {
        pack();
        this.setLocation(0, 0);
        validate();
        //selectedSession=SessionFrame.getFrame().getSessionSelectionnee();
        //DBConnexion conn = DBConnexion.getConnexion();
        //selectedSessionAnswers=conn.getEntriesBySession(selectedSession);
        if (selectedSessionAnswers.size()>0){
            displayScreenshot();
        } else {
            imgContainer.setIcon(null);
        }
        setVisible(true);
    }

    private void displayScreenshot(){
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screen.width;
        int height = (int)80*screen.height/100;
        BufferedImage src;
        String screens="";
        try {

            Reponse r = null;
            for (Iterator<Reponse> it = selectedSessionAnswers.iterator(); it.hasNext();) {
                Reponse r2 = it.next();
                if (r2.getId()==Long.parseLong(listAnswerID.getSelectedItem())) {
                            r=r2;
                }
            }
            screens=r.getScreenshot();

                ImageIcon icon = new ImageIcon(screens);
                Image imageG = icon.getImage();

                // Create empty BufferedImage, sized to Image
                src =
                  new BufferedImage(
                      imageG.getWidth(null),
                      imageG.getHeight(null),
                      BufferedImage.TYPE_INT_ARGB);

                // Draw Image into BufferedImage
                Graphics g = src.getGraphics();
                g.drawImage(imageG, 0, 0, null);

       //     src = ImageIO.read(new File(screens));
/*
            int rectHeight = (int) height*20/100;
            BufferedImage buf = new BufferedImage(width, rectHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = buf.createGraphics();
            g2.drawRect(0, 0, width, rectHeight);

            //imgContainer.setIcon(new ImageIcon(resize(src, width, height)));
            ImgTxtMerger iTM = new ImgTxtMerger();*/
            if (selectedSessionAnswers.size()>0){
                Reponse currentAnswer = selectedSessionAnswers.get(listAnswerID.getSelectedIndex());
                imgContainer.setIcon(new ImageIcon(src));
            }
        } catch (Exception ex) {
           String message = Lang.getLang().getValueFromRef("ViewAnswers.strFileError") + " "+screens;
           javax.swing.JOptionPane.showMessageDialog(this, message);
        }
        //this.setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);

    }

    public void reset(){
        listAnswerID.removeAll();
        listSessionID.removeAll();
        //imgContainer.setEnabled(false);


            SessionManager sm;
            try {
                sm = SessionManager.getSessionManager();
                for (Iterator<Session> it = sm.getLesSessions().iterator(); it.hasNext();) {
                    Session s = it.next();
                    listSessionID.add(""+s.getId());

                }

        selectedSession=SessionFrame.getFrame().getSessionSelectionnee();
        DBConnexion conn = DBConnexion.getConnexion();
        selectedSessionAnswers=conn.getEntriesBySession(selectedSession);
        int ind=0;
        while (!listSessionID.getItem(ind).equals(Long.toString(selectedSession.getId()))){
            ind++;
            listSessionID.select(ind);
        }
                //selectedSession=sm.getLesSessions().get(0);
                //listSessionID.select(0);
                //DBConnexion conn = DBConnexion.getConnexion();
                //selectedSessionAnswers=conn.getEntriesBySession(selectedSession);
                for (Iterator<Reponse> it = selectedSessionAnswers.iterator(); it.hasNext();) {
                    Reponse r = it.next();
                    listAnswerID.add(""+r.getId());
                }
                for (Iterator<Reponse> it = selectedSessionAnswers.iterator(); it.hasNext();) {
                    it.next().setSession(selectedSession);
                }
                if (selectedSessionAnswers.size()>0){
                    listAnswerID.select(0);
                    ////refresh();
                }
                refresh();

            } catch (SQLException ex) {
                Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        pack();
        validate();

        jpImg.revalidate();
        jpControls.revalidate();
        jpInternal.revalidate();
        imgContainer.revalidate();
        this.repaint();    

    }

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

  /*
   public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(3);
        f.setSize(800, 600);
        f.setLocationRelativeTo(null);


        ImageIcon img1 = new ImageIcon("img1.png");
        ImageIcon img2 = new ImageIcon("img2.png");
        ImageIcon image = new ImageIcon(append(img1.getImage(), img2.getImage()));
        JLabel label = new JLabel();
        label.setIcon(image);


        f.setContentPane(label);
        f.setVisible(true);
    }
   */


    //Puis mettre le texte
    //ImgTxtMerger.merge(absoluteScreenshotFilePath, rep.getIntituleQuestion()+ " \n "+rep.getLaReponse()+" \n "+rep.getInstant().toString());
}
