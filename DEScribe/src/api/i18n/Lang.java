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

package api.i18n;

import api.utils.getOs;
import api.xml.Utils;
import exceptions.BadXMLFileException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * Class Lang.java
 * @description Internationalization. Load language from XML file to hashmap
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @author Bertrand Gros    <gros.bertrand@gmail.com>
 * @version 2011-01-28
 */
public class Lang {

    private static HashMap hm;
    private static Lang inst = null;

    public String applicationTitle = "english default version";

    private Lang() {
        try {
            load(Utils.loadLanguage());
        } catch (BadXMLFileException ex) {
            javax.swing.JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public static Lang getLang(){
        if (inst==null)
            inst=new Lang();
        return inst;
    }

    public void load(String language) {
        try {

                    if (getOs.isWindows()) {
                        hm = Utils.importTranslationHashMapFromXML("language\\"+language + ".xml");
                    } else {
                        hm = Utils.importTranslationHashMapFromXML("language/"+language + ".xml");
                    }
            Set set = hm.entrySet();
        } catch (SAXException ex) {
            Logger.getLogger(Lang.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Lang.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Lang.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    public String getValueFromRef(String ref) {
        return (String) hm.get(ref);
     }
    


}