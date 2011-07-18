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

package api.xml;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * Class Xmlfilter.java
 * @description XML file filter
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @version 2011-07-18
 */
public class Xmlfilter extends FileFilter{

 //Description et extension acceptée par le filtre
   private String description;
   private String extension;
   //Constructeur à partir de la description et de l'extension acceptée
   public Xmlfilter(String description, String extension){
      if(description == null || extension ==null){
         throw new NullPointerException("La description (ou extension) ne peut être null.");
      }
      this.description = description;
      this.extension = extension;
   }
   //Implémentation de FileFilter
   public boolean accept(File file){
      if(file.isDirectory()) {
         return true;
      }
      String nomFichier = file.getName().toLowerCase();

      return nomFichier.endsWith(extension);
   }
      public String getDescription(){
      return description;
   }
}
