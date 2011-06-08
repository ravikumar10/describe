/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package api.utils;

import api.gui.AskFrame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Seb
 */
public class CopyAndPasteHandler {


    private Transferable oldContents=null;
    private String contentsType="";

    private static CopyAndPasteHandler instance;

    public CopyAndPasteHandler(){
        // get the system clipboard
        Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        updateContents(systemClipboard.getContents(null));
        scrutation();
    }


    public static CopyAndPasteHandler getInstance(){

        if (instance == null) {
            instance = new CopyAndPasteHandler();
        }
        return instance;
    }

    public void scrutation(){
        while (true){
            try {
                if (isNewCopyDone()) {
                    // Ask a new question - Only for test. To be generalized with rules in form.xml
                    if (oldContents.isDataFlavorSupported(DataFlavor.imageFlavor)){
                        //AskFrame.getTheFrame().showTheFrame("What did you want to do with this picture?");
                        AskFrame.getTheFrame().askQuestionWithRule("copyImage");
                    } else if (oldContents.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
                        //AskFrame.getTheFrame().showTheFrame("What did you want to do with this(thise) file(s)?");
                        AskFrame.getTheFrame().askQuestionWithRule("copyFile");
                    } else if (oldContents.isDataFlavorSupported(DataFlavor.stringFlavor)){
                        //AskFrame.getTheFrame().showTheFrame("What did you want to do with this text?");
                        AskFrame.getTheFrame().askQuestionWithRule("copyText");
                    } else if (contentsType.equals("unsupported")){
                        //AskFrame.getTheFrame().showTheFrame("What did you want to do with this text?");
                        AskFrame.getTheFrame().askQuestionWithRule("copy");
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CopyAndPasteHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(CopyAndPasteHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void updateContents(Transferable newContents){
        oldContents=newContents;
        if (oldContents != null){
            if (oldContents.isDataFlavorSupported(DataFlavor.imageFlavor)){
                // Image
                contentsType="image";
            } else if (oldContents.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
                // Fichier(s)
                contentsType="file";
            } else if (oldContents.isDataFlavorSupported(DataFlavor.stringFlavor)){
                // Texte
                contentsType="text";
            }/* else {
                // Unsupported
                contentsType="unsupported";
            }*/
        } else {
            contentsType="void";
        }
    }

    /* Check if the contents of clipBoard changed since last call, in other
       words, if a copy-paste action has been started
    */
    public Boolean isNewCopyDone() throws IOException {

      // get the system clipboard
      Clipboard systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

      // get the contents on the clipboard in a
      // Transferable object
      Transferable clipboardContents = systemClipboard.getContents(null);
        try {
            // check if contents are empty, if so, return null
            if ((clipboardContents == null) || (isEqual(clipboardContents, oldContents))) {
                return false;
            } else {
                // New contents for clipboard
                updateContents(clipboardContents);
                return true;
            }
        } catch (UnsupportedFlavorException ex) {
            Logger.getLogger(CopyAndPasteHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
     }

    public Boolean isEqual(Transferable tr1, Transferable tr2) throws UnsupportedFlavorException, IOException{
        if (tr1.isDataFlavorSupported(DataFlavor.imageFlavor) && tr2.isDataFlavorSupported(DataFlavor.imageFlavor)){
            if ((((Image) tr1.getTransferData(DataFlavor.imageFlavor)).getWidth(null)==((Image) tr2.getTransferData(DataFlavor.imageFlavor)).getWidth(null)) && (((Image) tr1.getTransferData(DataFlavor.imageFlavor)).getHeight(null)==((Image) tr2.getTransferData(DataFlavor.imageFlavor)).getHeight(null)))
                return true;
            else
                return false;
        } else if (tr1.isDataFlavorSupported(DataFlavor.stringFlavor) && tr2.isDataFlavorSupported(DataFlavor.stringFlavor)){
            if (tr1.getTransferData(DataFlavor.stringFlavor).toString().equals(tr2.getTransferData(DataFlavor.stringFlavor).toString()))
                return true;
            else
                return false;
        } else if (tr1.isDataFlavorSupported(DataFlavor.javaFileListFlavor) && tr2.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
            if (tr1.getTransferData(DataFlavor.javaFileListFlavor).toString().equals(tr2.getTransferData(DataFlavor.javaFileListFlavor).toString()))
                return true;
            else
                return false;
        } else {
            return false;
        }
    }

}
