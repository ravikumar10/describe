/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

/**
 *
 * @author Seb
 */
public class QCMChoice {
    private String text;
    private Boolean isOtherChoice;

    public QCMChoice(String t, Boolean iOC){
        text=t;
        isOtherChoice=iOC;
    }

    public String getText(){
        return text;
    }

    public Boolean getIsOtherChoice(){
        return isOtherChoice;
    }
}
