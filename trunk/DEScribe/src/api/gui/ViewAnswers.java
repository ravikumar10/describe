/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package api.gui;

import api.dbc.DBConnexion;
import api.i18n.Lang;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.List;
import java.awt.RenderingHints;
import java.awt.Toolkit;
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

    private JPanel jpImg;
    private JPanel jpControls;
    private JPanel jpInternal;
    JLabel imgContainer;

    private static ViewAnswers instance;

    private List listSessionID;
    private List listAnswerID;

    private Session selectedSession;
    private ArrayList<Reponse> selectedSessionAnswers;

    public static String labelAnswer = Lang.getLang().getValueFromRef("ViewAnswers.labelAnswer");
    public static String labelSession = Lang.getLang().getValueFromRef("ViewAnswers.labelSession");
    public static String btDelete = Lang.getLang().getValueFromRef("ViewAnswers.btDelete");
    public static String btNext = Lang.getLang().getValueFromRef("ViewAnswers.btNext");
    public static String btPrevious = Lang.getLang().getValueFromRef("ViewAnswers.btPrevious");
    public static String btClose = Lang.getLang().getValueFromRef("ViewAnswers.btClose");
    public static String title = Lang.getLang().getValueFromRef("ViewAnswers.title");

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
            BufferedImage src = ImageIO.read(new File("test2.jpg"));
            imgContainer = new JLabel(new ImageIcon(resize(src, width, height)));

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

            JLabel answer = new JLabel(labelAnswer);
            JLabel answerID = new JLabel("1");
            JLabel session = new JLabel(labelSession);

            listSessionID = new List();
      /*      listSessionID.add("1");
            listSessionID.add("2");
            listSessionID.add("3");*/

            SessionManager sm;
            try {
                sm = SessionManager.getSessionManager();
                for (Iterator<Session> it = sm.getLesSessions().iterator(); it.hasNext();) {
                    Session s = it.next();
                    if (!s.getActive()) {
                        listSessionID.add(""+s.getId());
                    }
                }
                selectedSession=sm.getLesSessions().get(0);
                DBConnexion conn = DBConnexion.getConnexion();
                selectedSessionAnswers=conn.getEntriesBySession(selectedSession);
                listAnswerID=new List();
                for (Iterator<Reponse> it = selectedSessionAnswers.iterator(); it.hasNext();) {
                    Reponse r = it.next();
                    listAnswerID.add(""+r.getId());
                }

            } catch (SQLException ex) {
                Logger.getLogger(SessionFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            //panelDownList.add(c);
            listSessionID.addItemListener(new ItemListener() {

                public void itemStateChanged(ItemEvent ie) {
                    if (ie.getStateChange() == ItemEvent.SELECTED) {
                        if (!listSessionID.getSelectedItem().equals("")){
                            refresh();
                        }
                    }
                }
            });

            JButton deleteButton = new JButton(btDelete);
            JButton previousButton = new JButton(btPrevious);
            JButton nextButton = new JButton(btNext);
            JButton closeButton = new JButton(btClose);
            
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


        } catch (IOException ex) {
            Logger.getLogger(ViewAnswers.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.getContentPane().add(jpInternal);

        this.setTitle(title);
        //this.setPreferredSize(new Dimension(800,600));

        this.setLocation((screen.width - this.getSize().width) / 2, (screen.height - this.getSize().height) / 2);
        this.pack();
        this.setVisible(false);
    }

    public static ViewAnswers getTheFrame() {
        if (instance == null) {
            instance = new ViewAnswers();
        }
        return instance;
    }

    private void refresh() {
        DBConnexion conn = DBConnexion.getConnexion();
        selectedSession=conn.getSessionById(Long.parseLong(listSessionID.getSelectedItem()));
        selectedSessionAnswers=conn.getEntriesBySession(selectedSession);
    }

    private void displayScreenshot(String path){
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screen.width;
        int height = (int)80*screen.height/100;
        BufferedImage src;
        try {

            Reponse r = null;
            for (Iterator<Reponse> it = selectedSessionAnswers.iterator(); it.hasNext();) {
                Reponse r2 = it.next();
                if (r2.getId()==Long.parseLong(listAnswerID.getSelectedItem())) {
                            r=r2;
                }
            }
        src = ImageIO.read(new File(r.getScreenshot()));
        imgContainer = new JLabel(new ImageIcon(resize(src, width, height)));
        } catch (IOException ex) {
            Logger.getLogger(ViewAnswers.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

}
