/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author Seb
 */
public class Regle {
    String name; //fullscreen, copy, copyImage, copyText, copyFile
    String type; //if, notif

    public Regle(String n, String t){
        name=n;
        type=t;
    }

    public String getName(){
        return name;
    }

    public String getType(){
        return type;
    }
}
