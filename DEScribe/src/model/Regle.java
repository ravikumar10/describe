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
    String event; //fullscreen, copy, copyImage, copyText, copyFile
    String type; //if, notif

    public Regle(String e, String t){
        event=e;
        type=t;
    }

    public String getEvent(){
        return event;
    }

    public String getType(){
        return type;
    }
}
