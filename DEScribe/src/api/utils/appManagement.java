/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package api.utils;

import java.io.File;

/**
 *
 * @author Seb
 */
public class appManagement {

    public static boolean  restartApplication( Object classInJarFile )
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

        System.out.println(jarFile.getName());
        /* is it a jar file? */
        if ( !jarFile.getName().endsWith(".jar") )
        return false;   //no, it's a .class probably


        String toExec[]=null;
        if (api.utils.getOs.isWindows()){
            toExec = new String[] { "\""+javaBin+"\"", "-jar", "\""+jarFile.getPath()+"\"" };
        } else {
            toExec = new String[] { javaBin, "-jar", jarFile.getPath()};
        }
        //String  toExec[] = new String[] { "java", "-jar", jarFile.getPath() };
        //System.out.println("\""+javaBin+"\""+ " -jar"+" "+ "\""+jarFile.getPath()+"\"" );
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
