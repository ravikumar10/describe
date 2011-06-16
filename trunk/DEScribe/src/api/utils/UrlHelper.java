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
 *
 *
 * Source : http://www.javafr.com/codes/TELECHARGER-FICHIER-PARTIR-URL_42988.aspx
 * Author : Jaoued Zahraoui for Javafr.
 *
 *
 */

package api.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


/**
 * Class UrlHelper.java
 * @description Tool to download file from url
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @author Bertrand Gros    <gros.bertrand@gmail.com>
 * @version 2011-02-01
 */
public class UrlHelper {

    public static void downloadFile(String adresse) {

        downloadFile(adresse, null);
    }

    public static void downloadFile(String adresse, File dest) {
        BufferedReader reader = null;
        FileOutputStream fos = null;
        InputStream in = null;
        try {

            // création de la connection
            URL url = new URL(adresse);
            URLConnection conn = url.openConnection();
            String FileType = conn.getContentType();

            int FileLenght = conn.getContentLength();
            if (FileLenght == -1) {
                throw new IOException("Fichier non valide.");
            }

            // lecture de la réponse
            in = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            if (dest == null) {
                String FileName = url.getFile();
                FileName = FileName.substring(FileName.lastIndexOf('/') + 1);
                dest = new File(FileName);
            }
            fos = new FileOutputStream(dest);
            byte[] buff = new byte[1024];
            int l = in.read(buff);
            while (l > 0) {
                fos.write(buff, 0, l);
                l = in.read(buff);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
