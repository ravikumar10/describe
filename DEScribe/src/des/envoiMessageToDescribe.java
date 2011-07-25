/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package des;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

/**
 *
 * @author Seb
 */
public class envoiMessageToDescribe {
	public static void main(String[] args) {
            PrintWriter pw = null;
            try {
                /* Connexion to local machine */
                Socket socket = new Socket("localhost", 32145);

                /* Use of PrintWriterOn to write on output of the socket */
                pw = new PrintWriter(socket.getOutputStream());

                /* Writing the message on the socket */
                //pw.write("{'data1':100,'data2':'hello'}");
                pw.write("{'event':'copyText','data2':'hello'}");
            } catch (IOException e) {
                Logger.getLogger("UniqueInstance").warning("Socket output flow writing failed.");
            } finally {
                if (pw != null) {
                    pw.close();
                }
            }
	}
}
