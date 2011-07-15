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

/**
 *  Restarts the application
 * 
 */
public class appManagement {

    public static boolean  restartApplication( Object classInJarFile, Boolean isMacReboot )
    {
        String javaBin = System.getProperty("java.home");

        if (api.utils.getOs.isWindows()){
            javaBin+="\\bin\\java";
        } else {
            javaBin+="/bin/java";
        }
        File jarFile;
        
        try{
            jarFile = new File
            (classInJarFile.getClass().getProtectionDomain()
            .getCodeSource().getLocation().toURI());
        } catch(Exception e) {
            return false;
        }

        /* is it a jar file? */
        if ((!jarFile.getName().endsWith(".jar")) && (!jarFile.getName().endsWith(".exe")) ){
            return false;   //no, it's a .class probably
        }

        String toExec[]=null;
        if (api.utils.getOs.isWindows()){
            //toExec = new String[] { "\""+javaBin+"\"", "-jar", "\""+jarFile.getParent()+"\"" };
            toExec = new String[] { jarFile.getParent()+"\\" +"DEScribe.exe" };
        } else {
            //toExec = new String[] { javaBin, "-jar", jarFile.getPath()};
            if (api.utils.getOs.isMac()){
                //toExec = new String[] { "open","/Applications/DEScribe/DEScribe.app" };
                if (isMacReboot){
                    toExec = new String[] { "open","/Applications/DEScribe/DEScribe.app" };
                } else {
                toExec = new String[] { javaBin, "-jar", jarFile.getPath(), "reboot"};
                }
            } else {
                toExec = new String[] { javaBin, "-jar", jarFile.getPath()};
           }
        }
        //String  toExec[] = new String[] { "java", "-jar", jarFile.getPath() };
        //System.out.println(jarFile.getParent()+"\\" +"DEScribe.exe" );
        try{
            Process p = Runtime.getRuntime().exec( toExec );
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }

        System.exit(0);

        return true;
    }

}
