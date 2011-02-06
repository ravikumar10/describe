/**
 *
    DEScribe - A Discrete Experience Sampling cross platform application
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
 */

package exceptions;

import api.i18n.Lang;

/**
 * Class BadXMFileException.java
 * @description Exception to throw when problem with Options or Form XML files
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @author Bertrand Gros    <gros.bertrand@gmail.com>
 * @version 2011-02-01
 */
public class BadXMLFileException extends Exception {
    public static String BAD_OPTION_FILE=Lang.getLang().getValueFromRef("BadXMLFileException.BAD_OPTION_FILE");
    public static String BAD_FORM_FILE = Lang.getLang().getValueFromRef("BadXMLFileException.BAD_FORM_FILE");
                    public String message;

                    public BadXMLFileException() {
                            super();
                            BAD_OPTION_FILE = Lang.getLang().getValueFromRef("BadXMLFileException.BAD_OPTION_FILE");
                            BAD_FORM_FILE = Lang.getLang().getValueFromRef("BadXMLFileException.BAD_FORM_FILE");
                            this.message = BAD_FORM_FILE;
                    }

                    public BadXMLFileException(String message) {
                            super(message);
                            BAD_OPTION_FILE = Lang.getLang().getValueFromRef("BadXMLFileException.BAD_OPTION_FILE");
                            BAD_FORM_FILE = Lang.getLang().getValueFromRef("BadXMLFileException.BAD_FORM_FILE");
                            this.message = message;
                    }

                    @Override
                    public String getMessage() {
                            return message;
                    }

}
