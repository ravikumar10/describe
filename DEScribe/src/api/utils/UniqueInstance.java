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

import des.Main;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Class UniqueInstance.java
 * @description Limit the use of only one instance of DEScribe at a time and receive event from port
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @version 2011-07-18
 */
public class UniqueInstance {

    /** listening port */
    private int port;
    /** Message to send to the application if already launched */
    private String message;
    /** Actions to do when when another instance is trying to launch */
    private Runnable runOnReceive;

    private static String request = "<?xml version=\"1.0\"?><cross-domain-policy><allow-access-from domain=\"*\" to-ports=\"*\" /></cross-domain-policy>" ;


    private static String policyAsk ="<policy-file-request/>";


    /**
     * Java port listening server
     * Send and receive messages on a choosen port
     * Used to detect already running instance of DEScribe
     * Also receive events notifications from external application.
     * 
     * @param port
     * Port
     * @param message
     * Message
     * @param runOnReceive
     * Actions to do when when another instance is trying to launch,
     * {@code null} for no action.
     */
    public UniqueInstance(int port, String message, Runnable runOnReceive) {
        assert port > 0 && port < 1 << 16 : "Port must be between 1 and 65535";
        assert message != null || runOnReceive == null : "There are actions to do => Message can't be null!.";
        this.port = port;
        this.message = message;
        this.runOnReceive = runOnReceive;
    }

    /**
     * Creates a unique instance manager for DEScribe. This constructor disables
     * communication between instance already launched and the new one
     *
     * @param port
     * listening port
     */
    public UniqueInstance(int port) {
        this(port, null, null);
    }

    /**
     * Tries to launch the unique instance manager. If works, it means instance 
     * is unique. Else, there's already one running : application is warned that
     * another instance is trying to connect
     *
     * @return {@code true} if DEScribe's instance is unique
     */
    public boolean launch() {
        /* Result */
        boolean unique;

        try {
            /* Socket creation on the listening port */
            final ServerSocket server = new ServerSocket(port);

            /* If socket creation worked, it means instance is unique */
            unique = true;

            /* If there are actions to do... */
            if (runOnReceive != null) {

                /* Creation of a listening thread on this port. */
                Thread portListenerThread = new Thread() {

                    @Override
                    public void run() {
                        /* While DEScribe is running... */
                        while (true) {
                            try {
                                /* Waiting for a socket to connect to the server */
                                final Socket socket = server.accept();

                                /* If a socket is connected, then listen the message send in a new thread */
                                new Thread() {

                                    @Override
                                    public void run() {
                                        receive(socket);
                                    }
                                }.start();
                            } catch (IOException e) {
                                Logger.getLogger("UniqueInstance").warning("Attente de connexion de socket échouée.");
                            }
                        }
                    }
                };

                /* Listening port thread is a daemon */
                portListenerThread.setDaemon(true);

                /* Strating the thread */
                portListenerThread.start();
            }
        } catch (IOException e) {
            /**
             * If socket's creation fails, it means that DEScribe's new instance
             * is not unique
             */
            unique = false;

            /* If actions are planed by the already running instance.... */
            if (runOnReceive != null) {
                /*
                 * Send the other instance the message
                 */
                send();
            }
        }
        return unique;
    }

    /**
     * Sends a mesage to the already running instance of DEScribe
     */
    private void send() {
        PrintWriter pw = null;
        try {
            /* Connexion to local machine */
            Socket socket = new Socket("localhost", port);

            /* Use of PrintWriterOn to write on output of the socket */
            pw = new PrintWriter(socket.getOutputStream());

            /* Writing the message on the socket */
            pw.write(message);
            //pw.write("copyText");
        } catch (IOException e) {
            Logger.getLogger("UniqueInstance").warning("Socket output flow writing failed.");
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * Sends a policy file to flash server
     */
    private void sendRequest() {
        PrintWriter pw = null;
        try {
            /* Connexion to local machine */
            Socket socket = new Socket("localhost", port);
            /* Use of PrintWriterOn to write on output of the socket */
            pw = new PrintWriter(socket.getOutputStream());
            /* Writing the message on the socket */

            pw.write(request);
            //pw.flush();
            System.out.println("Policy renvoyé");
            //pw.write("copyText");
        } catch (IOException e) {
            Logger.getLogger("UniqueInstance").warning("Socket output flow writing failed.");
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
    }

    /**
     * Receives a message from a socket connected to the listening server
     * If this is the message of unique instance then actions are done
     *
     * @param socket
     * Socket connected to the server
     */
    private void receive(Socket socket) {
        Scanner sc = null;

        try {
            /* Only listens for 5 secondes... */
            socket.setSoTimeout(5000);

            /* Use of a Scanner to read on socket's input */
            sc = new Scanner(socket.getInputStream());

            /* We only read one line */
             //= sc.nextLine();
             String s="";
             String temp="";
            try {
                temp=sc.nextLine();
                s=temp;
                while (temp!=null){
                    temp=sc.nextLine();
                    s=s+"\n"+temp;
                }
               
            } catch (Exception ex){
                s=s.trim();
                System.out.println("Message: "+s);
                if (s.equalsIgnoreCase(policyAsk)){
                    sendRequest();
                } else {
                    if (s.equalsIgnoreCase(request)){
                        //nothing. Let it to flash client
                    }
                }
            }
            if (!message.equals(s)){
                javax.swing.JOptionPane.showMessageDialog(null, s);
            }

            if (message.equals(s)) {
            /**
             * If this is DEScribe already running instance message
             */
                Main.regleRead="";
                runOnReceive.run();
            /**
             * If this is an external application's event notification
             */
            } else if ((s.length()>0) && (!s.equalsIgnoreCase(policyAsk)) && (!s.equalsIgnoreCase(request))){
                Main.regleRead=s;
                runOnReceive.run();
            }
        } catch (IOException e) {
            Logger.getLogger("UniqueInstance").warning("Socket input flow reading failed.");
        } finally {
            if (sc != null) {
                sc.close();
            }
        }
    }
}
