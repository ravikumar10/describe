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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Makes a zip archive of a folder
 * 
 */
public class Zip {

    private static int BUFFER = 1024;

    public static void zipFolder(String folderSrc, String archiveDest) throws FileNotFoundException, IOException, Exception
    {
        byte data[] = new byte[BUFFER];
        FileOutputStream dest = new FileOutputStream(archiveDest);
        BufferedOutputStream buff = new BufferedOutputStream(dest);
        ZipOutputStream out = new ZipOutputStream(buff);

        out.setMethod(ZipOutputStream.DEFLATED);
        out.setLevel(9);

        File f = new File(folderSrc);

        if(f.isDirectory()){
            String files[] = f.list();
            if(files != null){

                for(int i=0; i<files.length; i++) {
                    FileInputStream fi;
                    if (api.utils.getOs.isWindows()){
                         fi = new FileInputStream(folderSrc+"\\"+files[i]);
                    } else {
                        fi = new FileInputStream(folderSrc+"/"+files[i]);
                    }
                    BufferedInputStream buffi = new BufferedInputStream(fi, BUFFER);
                    if (api.utils.getOs.isWindows()){
                        ZipEntry entry = new ZipEntry(folderSrc.substring(folderSrc.lastIndexOf("\\")+1)+"\\"+files[i]);
                        out.putNextEntry(entry);
                    } else {
                        ZipEntry entry = new ZipEntry(folderSrc.substring(folderSrc.lastIndexOf("/")+1)+"/"+files[i]);
                        out.putNextEntry(entry);
                    }
                    int count;
                    while((count = buffi.read(data, 0, BUFFER)) != -1) {
                        out.write(data, 0, count);
                    }
                    out.closeEntry();
                    buffi.close();
                }
            }
        }
        out.flush();
        out.close();
      }

}
