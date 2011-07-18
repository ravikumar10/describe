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

import api.gui.AskFrame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class CopyAndPasteHandler.java
 * @description Copy And Paste detection
 * @author Sébastien Faure  <sebastien.faure3@gmail.com>
 * @version 2011-07-18
 */
public class CopyAndPasteHandler {


    private Transferable oldContents=null;
    private String contentsType="";
    
    private static CopyAndPasteHandler instance;

    private Clipboard systemClipboard;
    public CopyAndPasteHandler(){
        // get the system clipboard
        systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
/*
 *      //Can't use it! Only detects types' changes and not contents changes (e.g. a text to another text not detected).
        systemClipboard.addFlavorListener(new FlavorListener() {

                public void flavorsChanged(FlavorEvent e) {
                            updateContents(systemClipboard.getContents(null));
                            System.out.println("Copy detected!");
                            if (oldContents.isDataFlavorSupported(DataFlavor.imageFlavor)) {
                                System.out.println("image");
                                //AskFrame.getTheFrame().showTheFrame("What did you want to do with this picture?");
                                AskFrame.getTheFrame().askQuestionWithRule("copyImage");
                            } else if (oldContents.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                                System.out.println("fichiers");
                                //AskFrame.getTheFrame().showTheFrame("What did you want to do with this(thise) file(s)?");
                                AskFrame.getTheFrame().askQuestionWithRule("copyFile");
                            } else if (oldContents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                                System.out.println("text");
                                //AskFrame.getTheFrame().showTheFrame("What did you want to do with this text?");
                                AskFrame.getTheFrame().askQuestionWithRule("copyText");
                            } else if (contentsType.equals("unsupported")) {
                                System.out.println("unsupported");
                                //AskFrame.getTheFrame().showTheFrame("What did you want to do with this text?");
                                AskFrame.getTheFrame().askQuestionWithRule("copy");
                            }

            }}
        );*/
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
                    DataFlavor t[]=systemClipboard.getAvailableDataFlavors();
                    if (systemClipboard.getAvailableDataFlavors().length>0){

                        // Ask a new question - Only for test. To be generalized with rules in form.xml
                        if (isImageTransferable(oldContents)){
                            //AskFrame.getTheFrame().showTheFrame("What did you want to do with this picture?");
                            AskFrame.getTheFrame().askQuestionWithRule("copyImage");
                        } else if (isFileTransferable(oldContents)){
                            //AskFrame.getTheFrame().showTheFrame("What did you want to do with this(thise) file(s)?");
                            AskFrame.getTheFrame().askQuestionWithRule("copyFile");
                        } else if (isTextTransferable(oldContents)){
                            //AskFrame.getTheFrame().showTheFrame("What did you want to do with this text?");
                            AskFrame.getTheFrame().askQuestionWithRule("copyText");
                        } else {
                            //AskFrame.getTheFrame().showTheFrame("What did you want to do with this text?");
                            AskFrame.getTheFrame().askQuestionWithRule("copy");
                        }
                    }

                    /*
                    // Ask a new question   1
                    if (oldContents.isDataFlavorSupported(DataFlavor.imageFlavor)){
                        System.out.println("image");
                        //AskFrame.getTheFrame().showTheFrame("What did you want to do with this picture?");
                        AskFrame.getTheFrame().askQuestionWithRule("copyImage");
                    } else if (oldContents.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
                        System.out.println("fichiers");
                        //AskFrame.getTheFrame().showTheFrame("What did you want to do with this(thise) file(s)?");
                        AskFrame.getTheFrame().askQuestionWithRule("copyFile");
                    } else if (oldContents.isDataFlavorSupported(DataFlavor.stringFlavor)){
                        System.out.println("text");
                        //AskFrame.getTheFrame().showTheFrame("What did you want to do with this text?");
                        AskFrame.getTheFrame().askQuestionWithRule("copyText");
                    } else if (contentsType.equals("unsupported")){
                        System.out.println("unsupported");
                        //AskFrame.getTheFrame().showTheFrame("What did you want to do with this text?");
                        AskFrame.getTheFrame().askQuestionWithRule("copy");
                    }*/
                }
                try {
                    Thread.sleep(800);
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
        /*    if (oldContents.isDataFlavorSupported(DataFlavor.imageFlavor)){
                // Image
                contentsType="image";
            } else if (oldContents.isDataFlavorSupported(DataFlavor.javaFileListFlavor)){
                // Fichier(s)
                contentsType="file";
            } else if (oldContents.isDataFlavorSupported(DataFlavor.stringFlavor)){
                // Texte
                contentsType="text";
            }*//* else {
                // Unsupported
                contentsType="unsupported";
            }*/
        /*} else {
            contentsType="void";
         */

            DataFlavor t[]=systemClipboard.getAvailableDataFlavors();
            if (systemClipboard.getAvailableDataFlavors().length>0){
                    // Ask a new question - Only for test. To be generalized with rules in form.xml
                    if (isImageTransferable(oldContents)){
                        contentsType="image";
                    } else if (isFileTransferable(oldContents)){
                        contentsType="file";
                    } else if (isTextTransferable(oldContents)){
                        contentsType="text";
                    } else {
                        // Unsupported
                        contentsType="unsupported";
                    }
            } else {
                    contentsType="void";
            }


        } else {
                contentsType="void";
        }

    }

    /* Check if the contents of clipBoard changed since last call, in other
       words, if a copy-paste action has been started
    */
    public Boolean isNewCopyDone() throws IOException {


        Transferable clipboardContents;

        try {
            clipboardContents = systemClipboard.getContents(null);
        } catch (Exception IllegalStateException){
            clipboardContents=null;
        }
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
       if ((tr1.getTransferDataFlavors().length>0) && (tr2.getTransferDataFlavors().length>0)){
            /*if (tr1.isDataFlavorSupported(DataFlavor.imageFlavor) && tr2.isDataFlavorSupported(DataFlavor.imageFlavor)){
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
            }*/

            if (isImageTransferable(tr1) && isImageTransferable(tr2)){
                if ((((Image) tr1.getTransferData(DataFlavor.imageFlavor)).getWidth(null)==((Image) tr2.getTransferData(DataFlavor.imageFlavor)).getWidth(null)) && (((Image) tr1.getTransferData(DataFlavor.imageFlavor)).getHeight(null)==((Image) tr2.getTransferData(DataFlavor.imageFlavor)).getHeight(null)))
                    return true;
                else
                    return false;
            } else if (isTextTransferable(tr1) && isTextTransferable(tr2)){
                if (tr1.getTransferData(DataFlavor.stringFlavor).toString().equals(tr2.getTransferData(DataFlavor.stringFlavor).toString()))
                    return true;
                else
                    return false;
            } else if (isFileTransferable(tr1) && isFileTransferable(tr2)){
                    if (tr1.getTransferData(DataFlavor.javaFileListFlavor).toString().equals(tr2.getTransferData(DataFlavor.javaFileListFlavor).toString()))
                        return true;
                    else
                        return false;

            } else {
                if (!isTextTransferable(tr1) && !isFileTransferable(tr1) && !isImageTransferable(tr1) && !isTextTransferable(tr2) && !isFileTransferable(tr2) && !isImageTransferable(tr2)){
                    return true;
                } else {
                    return false;
                }
            }

        } else if ((tr1.getTransferDataFlavors().length==0) && (tr2.getTransferDataFlavors().length==0)){
            return true;
        } else if (contentsType.equals("unsupported")){
            return true;
       } else {
           return false;
        }

    }

    public Boolean isImageTransferable(Transferable tr){
        DataFlavor t[]=tr.getTransferDataFlavors();
        if (t.length>0){
            if (api.utils.getOs.isWindows()){
                                return (t[0].getHumanPresentableName().contains("image"));
            }

            else
                return (t[0].getHumanPresentableName().contains("image"));
        }
        else
            return false;
    }

    public Boolean isTextTransferable(Transferable tr){
        DataFlavor t[]=tr.getTransferDataFlavors();
        if (t.length>0){
            if (api.utils.getOs.isWindows())
                return (t[0].getHumanPresentableName().equals("application/x-java-text-encoding") || (t[0].getHumanPresentableName().equals("application/x-java-url")));
            else {
                return ((t[0].getHumanPresentableName().equals("Unicode String")) || (t[0].getHumanPresentableName().equals("text/rtf")) || (t[0].getHumanPresentableName().equals("application/pdf")));
            }
                
        }
        else
            return false;
    }

    public Boolean isFileTransferable(Transferable tr){
        DataFlavor t[]=tr.getTransferDataFlavors();
        if (t.length>0){
            if (api.utils.getOs.isWindows())
                return (t[0].getHumanPresentableName().equals("application/x-java-file-list"));
            else
                return (t[0].getHumanPresentableName().equals("application/x-java-url"));
        }
        else
            return false;
    }

}
