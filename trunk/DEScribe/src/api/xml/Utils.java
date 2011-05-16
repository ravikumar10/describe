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
package api.xml;

import api.gui.OptionFrame;
import api.utils.getOs;
import exceptions.BadXMLFileException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import model.QReponseLibre;
import model.Question;
import model.Reponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import model.Action;
import model.ActionScreenshot;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class Utils.java
 * @description Everything to handle XML in DEScribe
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @author Bertrand Gros    <gros.bertrand@gmail.com>
 * @version 2011-01-28
 */
public class Utils {

    private static String defaultFormFile = "form.xml";
    private static String defaultOptionFile = "options.xml";

    /**
     * Méthode de lecture d'un fichier XML pour
     * @param File String définissant le chemin du fichier XML
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SQLException
     * @throws ParseException
     */
    public static ArrayList<Question> importFormXML() throws BadXMLFileException {
        try {
            ArrayList<Question> lesQuestions = new ArrayList<Question>();
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc;
            if (getOs.isWindows()) {
                doc = db.parse("xml\\options.xml"); //fichier d'options a parser
            } else {
                doc = db.parse("xml/options.xml");
            }

            Element root = doc.getDocumentElement();
            NodeList form = null;
            form = root.getElementsByTagName("form");
            Element e = (Element) form.item(0);
            String url = e.getAttribute("url");
            /**
             * Récupération des questions du fichier "url"
             */
            DocumentBuilder db2 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc2 = db2.parse(url); //fichier d'options a parser

            Element rootQuestions = doc2.getDocumentElement();
            NodeList questionCourante = null;
            questionCourante = rootQuestions.getElementsByTagName("question");
            for (int k = 0; k < questionCourante.getLength(); k++) {
                Element q = (Element) questionCourante.item(k);
                String type = q.getAttribute("type");
                String intitule = q.getFirstChild().getNodeValue().trim();
                /**
                 * Ajout de la question
                 */
                if (type.equals("open")) {
                    QReponseLibre open = new QReponseLibre(intitule);
                    lesQuestions.add(open);
                }
            }
            return lesQuestions;
        } catch (SAXException ex) {
                    throw new BadXMLFileException(BadXMLFileException.BAD_FORM_FILE);
        } catch (IOException ex) {
                    throw new BadXMLFileException(BadXMLFileException.BAD_FORM_FILE);
        } catch (ParserConfigurationException ex) {
                    throw new BadXMLFileException(BadXMLFileException.BAD_FORM_FILE);
        }

    }

     /**
     * Méthode de lecture d'un fichier XML pour
     * @param File String définissant le chemin du fichier XML
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SQLException
     * @throws ParseException
     */
    public static ArrayList<Action> importFormActionsXML() throws BadXMLFileException {
        try {
        ArrayList<Action> lesActions = new ArrayList<Action>();
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc;
            if (getOs.isWindows()) {
                doc = db.parse("xml\\options.xml"); //fichier d'options a parser
            } else {
                doc = db.parse("xml/options.xml");
            }

            Element root = doc.getDocumentElement();
            NodeList form = null;
            form = root.getElementsByTagName("form");
            Element e = (Element) form.item(0);
            String url = e.getAttribute("url");
            /**
             * Récupération des questions du fichier "url"
             */
            DocumentBuilder db2 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc2 = db2.parse(url); //fichier d'options a parser

            Element rootQuestions = doc2.getDocumentElement();
            NodeList actionCourante = null;
            actionCourante = rootQuestions.getElementsByTagName("action");
            for (int k = 0; k < actionCourante.getLength(); k++) {
                Element q = (Element) actionCourante.item(k);
                String type = q.getAttribute("type");

                /**
                 * Adding new ActionScreenshot
                 */
                if (type.equals("screenshot")) {
                    ActionScreenshot screen = new ActionScreenshot();
                    lesActions.add(screen);
                }

                /* Add new actions here */
                /*if (type.equals("screenshot")) {
                    //...
                }*/

            }
            return lesActions;
        } catch (SAXException ex) {
                    throw new BadXMLFileException(BadXMLFileException.BAD_FORM_FILE);
        } catch (IOException ex) {
                    throw new BadXMLFileException(BadXMLFileException.BAD_FORM_FILE);
        } catch (ParserConfigurationException ex) {
                    throw new BadXMLFileException(BadXMLFileException.BAD_FORM_FILE);
        }

    }

    /**
     * Méthode de lecture d'un fichier XML de langue pour charger une hashmap de correspondance entre reference et traduction
     * @param File String définissant le chemin du fichier XML
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SQLException
     * @throws ParseException
     */
    public static HashMap importTranslationHashMapFromXML(String file) throws SAXException, IOException, ParserConfigurationException {

        HashMap hm = new HashMap();

        /**
         * Récupération des strings du fichier xml de lang
         */
        DocumentBuilder db2 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc2 = db2.parse(file);
        Element rootQuestions = doc2.getDocumentElement();
        NodeList stringCourante = null;
        stringCourante = rootQuestions.getElementsByTagName("string");

        for (int k = 0; k < stringCourante.getLength(); k++) {
            Element q = (Element) stringCourante.item(k);
            String ref = q.getElementsByTagName("ref").item(0).getTextContent();
            String value = q.getElementsByTagName("value").item(0).getTextContent();

            /**
             * Ajout de la chaine
             */
            hm.put(ref, value);
        }
        return hm;

    }

    public static void ExportReponsesToXML(ArrayList<Reponse> entries, String fichier) throws IOException {

        //FileWriter fw = new FileWriter(fichier);

        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(fichier), "ISO-8859-1");
        fw.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"no\"?>\n");
        fw.write("<reponses>\n");
        for (Iterator<Reponse> it = entries.iterator(); it.hasNext();) {
            Reponse r = it.next();

            /**
             * Pour chaque reponse on l'insere dans le document XML
             */
            fw.write("\t<reponse>\n");

            fw.write("\t\t<question>");
            fw.write(r.getIntituleQuestion());
            fw.write("</question>\n");

            fw.write("\t\t<content>");
            fw.write(r.getLaReponse());
            fw.write("</content>\n");

            fw.write("\t\t<timecode>");
            fw.write(r.getInstant().toString());
            fw.write("</timecode>\n");

            fw.write("\t</reponse>\n");


        }
        fw.write("</reponses>");
        fw.close();

    }

    /**
     * Modifie le fichier options.xml pour y mettre le nouveau chemin du fichier xml de questions
     * @param file
     */
    public static void setNewFormFile(String file) throws BadXMLFileException  {
        try{
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc;
                if (getOs.isWindows()) {
                    doc = docBuilder.parse("xml\\"+defaultOptionFile); //fichier d'options a parser
                } else {
                    doc = docBuilder.parse("xml/"+defaultOptionFile);
                }
                //Get the staff element by tag name directly
                Node form = doc.getElementsByTagName("form").item(0);

                if (form==null){
                    throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
                } else {
                    //update staff attribute
                    NamedNodeMap attr = form.getAttributes();
                    if (attr==null){
                        throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
                    } else {
                        Node nodeAttr = attr.getNamedItem("url");
                        if (nodeAttr==null){
                            throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
                        } else {
                            nodeAttr.setTextContent(file);
                            //ICI FAIRE LE TEST DE VALIDITE DU NOUVEAU FICHIER DE QUESTIONS, AVANT DE SAUVEGARDER LES OPTIONS
                            try{
                                testIsFormXML(file);
                                //write the content into xml file
                                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                                Transformer transformer = transformerFactory.newTransformer();
                                DOMSource source = new DOMSource(doc);
                                StreamResult result;
                                if (getOs.isWindows()) {
                                    result = new StreamResult(new File(".\\xml\\",defaultOptionFile));
                                } else {
                                    result = new StreamResult(new File("./xml/",defaultOptionFile));
                                }
                                transformer.transform(source, result);
                            } catch (BadXMLFileException ex0){
                                throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
                            }
                        }
                    }
                }
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                    throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
            } catch (SAXException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                    throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                    throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
            } catch (TransformerConfigurationException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                    throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
            } catch (TransformerException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                    throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
            }

    }

    /**
     * Modifie le fichier options.xml pour y mettre le chemin du fichier xml de questions par défaut
     * @param file
     */
    public static void setDefaultFormFile() throws BadXMLFileException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        Document doc;
        try {

                docBuilder = docFactory.newDocumentBuilder();
                if (getOs.isWindows()) {
                    doc = docBuilder.parse("xml\\"+defaultOptionFile); //fichier d'options a parser
                } else {
                    doc = docBuilder.parse("xml/"+defaultOptionFile);
                }
                Node form = doc.getElementsByTagName("form").item(0);
                if (form==null){
                    throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
                } else {
                    NamedNodeMap attr = form.getAttributes();
                    Node nodeAttr = attr.getNamedItem("url");
                    if (getOs.isWindows()) {
                        nodeAttr.setTextContent("xml\\"+defaultFormFile);
                    } else {
                        nodeAttr.setTextContent("xml/"+defaultFormFile);
                    }
                    DOMSource source = new DOMSource(doc);
                    //write the content into xml file
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer;
                    transformer = transformerFactory.newTransformer();
                    StreamResult result;
                    if (getOs.isWindows()) {
                        result = new StreamResult(new File(".\\xml\\",defaultOptionFile));
                    } else {
                        result = new StreamResult(new File("./xml/",defaultOptionFile));
                    }
                    transformer.transform(source, result);
                }
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                    throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
            } catch (SAXException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                    throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                    throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
            } catch (TransformerConfigurationException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                    throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
            } catch (TransformerException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                    throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
            }

    }

    public static String loadLanguage() throws BadXMLFileException {
        String s = "english";
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc;
            if (getOs.isWindows()) {
                doc = db.parse("xml\\"+defaultOptionFile); //fichier d'options a parser
            } else {
                doc = db.parse("xml/"+defaultOptionFile);
            }
            Element options = doc.getDocumentElement();
            NodeList currentOption = options.getElementsByTagName("lang");
            Element e = (Element) currentOption.item(0);
            s = e.getAttribute("value");
        } catch (SAXException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
        }
        return s;
    }

    public static void loadSettings() throws BadXMLFileException {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc;
            if (getOs.isWindows()) {
                doc = db.parse("xml\\"+defaultOptionFile); //fichier d'options a parser
            } else {
                doc = db.parse("xml/"+defaultOptionFile);
            }
            Element options = doc.getDocumentElement();
            NodeList currentOption = null;
            Element e = null;

            currentOption = options.getElementsByTagName("freq");
            e = (Element) currentOption.item(0);
            if (e.getAttribute("value").equals("0")) {
                OptionFrame.c1.setSelected(true);
            } else if (e.getAttribute("value").equals("1")) {
                OptionFrame.c2.setSelected(true);
            }

            currentOption = options.getElementsByTagName("sound");
            e = (Element) currentOption.item(0);
            if (e.getAttribute("on").equals("true")) {
                OptionFrame.jrb.setSelected(true);
            }

            currentOption = options.getElementsByTagName("lang");
            e = (Element) currentOption.item(0);
            if (e.getAttribute("value").equals("english")) {
                OptionFrame.c3.setSelected(true);
            } else if (e.getAttribute("value").equals("francais")) {
                OptionFrame.c4.setSelected(true);
            }

        } catch (SAXException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
        }

    }

    public static void saveSettings() throws BadXMLFileException {
        try{
                String oldFormURL;
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc;
                if (getOs.isWindows()) {
                    doc = docBuilder.parse("xml\\"+defaultOptionFile); //fichier d'options a parser
                } else {
                    doc = docBuilder.parse("xml/"+defaultOptionFile);
                }
                //Get the staff element by tag name directly
                Node form = doc.getElementsByTagName("form").item(0);

                if (form==null){
                    throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
                } else {
                    //update staff attribute
                    NamedNodeMap attr = form.getAttributes();
                    if (attr==null){
                        throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
                    } else {
                        Node nodeAttr = attr.getNamedItem("url");
                        if (nodeAttr==null){
                            throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
                        } else {
                            oldFormURL=nodeAttr.getTextContent();
                           OutputStreamWriter osw = null;
                            try {
                                if (getOs.isWindows()) {
                                    osw = new OutputStreamWriter(new FileOutputStream("xml\\"+defaultOptionFile), "ISO-8859-1");
                                } else {
                                    osw = new OutputStreamWriter(new FileOutputStream("xml/"+defaultOptionFile), "ISO-8859-1");
                                }
                                osw.write("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" standalone=\"no\"?>\n");
                                osw.write("<options>\n");

                                // le fichier de formulaire
                                osw.write("\t<form url=\""+oldFormURL+"\"/>\n");

                                // la valeur de la freq
                                if (OptionFrame.c1.isSelected()) {
                                    osw.write("\t<freq value=\"0\"/>\n");
                                } else {
                                    osw.write("\t<freq value=\"1\"/>\n");
                                }

                                // sons activés ?
                                if (OptionFrame.jrb.isSelected()) {
                                    osw.write("\t<sound on=\"true\"/>\n");
                                } else {
                                    osw.write("\t<sound on=\"false\"/>\n");
                                }

                                // language selectionné
                                if (OptionFrame.c3.isSelected()) {
                                    osw.write("\t<lang value=\"english\"/>\n");
                                } else if (OptionFrame.c4.isSelected()) {
                                    osw.write("\t<lang value=\"francais\"/>\n");
                                }

                                osw.write("</options>");
                                osw.close();
                            } catch (IOException ex) {
                                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                            } finally {
                                try {
                                    osw.close();
                                } catch (IOException ex) {
                                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                        }
                    }
                }
            } catch (ParserConfigurationException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                    throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
            } catch (SAXException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                    throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                    throw new BadXMLFileException(BadXMLFileException.BAD_OPTION_FILE);
            }


    }

     /**
     * Méthode de lecture d'un fichier XML pour
     * @param File String définissant le chemin du fichier XML
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SQLException
     * @throws ParseException
     */
    public static void testIsFormXML(String file) throws BadXMLFileException {
        try {
            ArrayList<Question> lesQuestions = new ArrayList<Question>();
            /**
             * Récupération des questions du fichier "url"
             */

            DocumentBuilder db2 = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc2 = db2.parse(file); //fichier d'options a parser
            if (doc2==null){
                throw new BadXMLFileException(BadXMLFileException.BAD_FORM_FILE);
            } else {
                Element rootQuestions = doc2.getDocumentElement();
                if (rootQuestions==null){
                throw new BadXMLFileException(BadXMLFileException.BAD_FORM_FILE);
                } else {
                        NodeList questionCourante = null;
                        questionCourante = rootQuestions.getElementsByTagName("question");
                        if (questionCourante==null){
                            throw new BadXMLFileException(BadXMLFileException.BAD_FORM_FILE);
                        } else {
                            for (int k = 0; k < questionCourante.getLength(); k++) {
                                Element q = (Element) questionCourante.item(k);
                                String type = q.getAttribute("type");
                                String intitule = q.getFirstChild().getNodeValue().trim();
                                /**
                                 * Ajout de la question
                                 */
                                if (type.equals("open")) {
                                    QReponseLibre open = new QReponseLibre(intitule);
                                    lesQuestions.add(open);
                                }
                            }
                        }
                }
            }
            if (lesQuestions.isEmpty()){
                throw new BadXMLFileException(BadXMLFileException.BAD_FORM_FILE);

            }

        } catch (SAXException ex) {
                    throw new BadXMLFileException(BadXMLFileException.BAD_FORM_FILE);
        } catch (IOException ex) {
                    throw new BadXMLFileException(BadXMLFileException.BAD_FORM_FILE);
        } catch (ParserConfigurationException ex) {
                    throw new BadXMLFileException(BadXMLFileException.BAD_FORM_FILE);
        }

    }


}
