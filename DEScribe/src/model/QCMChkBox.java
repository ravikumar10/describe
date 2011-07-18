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

package model;

import java.util.ArrayList;

/**
 * Class QCMChkBox.java
 * @description Multiple choice question with possible multiple answers
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @version 2011-01-28
 */
public class QCMChkBox extends QCM{

    /*private ArrayList<String> choices;

    public QCMChkBox(String intitule, ArrayList<String> ch) {
        super(intitule);
        choices=new ArrayList<String>();
        choices=ch;
    }
    
    public ArrayList<String> getChoices(){
        return choices;
    }

     */

    public QCMChkBox(String intitule, ArrayList<QCMChoice> ch) {
        super(intitule);
        choices=new ArrayList<QCMChoice>();
        choices=ch;
    }

    public ArrayList<QCMChoice> getChoices(){
        return choices;
    }
}
