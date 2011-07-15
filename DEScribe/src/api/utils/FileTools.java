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

package api.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Class FileTools.java
 * @description Tools for files
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @author Bertrand Gros
 * @version 2011-02-02
 */
public class FileTools {

    /** copie le fichier source dans le fichier resultat
     * retourne vrai si cela réussit
     */
    public static boolean copyFile(File source, File dest){
            try{
                    // Declaration et ouverture des flux
                    java.io.FileInputStream sourceFile = new java.io.FileInputStream(source);

                    try{
                            java.io.FileOutputStream destinationFile = null;

                            try{
                                    destinationFile = new FileOutputStream(dest);

                                    // Lecture par segment de 0.5Mo
                                    byte buffer[] = new byte[512 * 1024];
                                    int nbLecture;

                                    while ((nbLecture = sourceFile.read(buffer)) != -1){
                                            destinationFile.write(buffer, 0, nbLecture);
                                    }
                            } finally {
                                    destinationFile.close();
                            }
                    } finally {
                            sourceFile.close();
                    }
            } catch (IOException e){
                    e.printStackTrace();
                    return false; // Erreur
            }

            return true; // Résultat OK
    }


    /**
     * Déplace le fichier source dans le fichier résultat
     */
    public static boolean moveFile(File source,File destination) {
            if( !destination.exists() ) {
                    // On essaye avec renameTo
                    boolean result = source.renameTo(destination);
                    if( !result ) {
                            // On essaye de copier
                            result = true;
                            result &= copyFile(source,destination);
                            if(result) result &= source.delete();

                    } return(result);
            } else {
                    // Si le fichier destination existe, on annule ...
                    return(false);
            }
    }

}
