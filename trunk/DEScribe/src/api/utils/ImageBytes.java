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

import com.sun.image.codec.jpeg.ImageFormatException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

/**
 * Image / byte Array conversion
 * @author Seb
 */
public class ImageBytes {

    public static byte [] ImageToByte(File filePath) throws FileNotFoundException{

        FileInputStream fis = new FileInputStream(filePath);
        ByteArrayOutputStream bos =new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {

            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
        } catch (IOException ex) {
        }
        byte[] bytes =bos.toByteArray();

        return bytes;
    }

    public static void byteToImage(byte [] bytes,File imageFile) throws IOException{

        ByteArrayInputStream bis =new ByteArrayInputStream(bytes);
        Iterator<?> readers =ImageIO.getImageReadersByFormatName("jpeg");
        ImageReader reader =(ImageReader) readers.next();
        Object source = bis;
        ImageInputStream iis =ImageIO.createImageInputStream(source);
        reader.setInput(iis, true);
        ImageReadParam param =reader.getDefaultReadParam();
        Image image = reader.read(0,param);
        BufferedImage bufferedImage= new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 =bufferedImage.createGraphics();
        g2.drawImage(image, null,null);
        ImageIO.write(bufferedImage,"jpeg", imageFile);
    }

    public static byte[] bufferedImageToByteArray(BufferedImage img) throws ImageFormatException, IOException{
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
        encoder.encode(img);
        return os.toByteArray();
    }

    public static void createFileFromBytes(byte[] bA, File f){
            FileOutputStream fos;
        try {
            fos = new FileOutputStream(f);
            fos.write(bA);
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImageBytes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
                Logger.getLogger(ImageBytes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static byte[] getBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        if (length > Integer.MAX_VALUE) {
            // File is too large
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int)length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
               && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file "+file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }

    public static BufferedImage FileToBufferedImage(String file){

                ImageIcon icon = new ImageIcon(file);
                Image imageG = icon.getImage();

                // Create empty BufferedImage, sized to Image
                BufferedImage bimg =
                  new BufferedImage(
                      imageG.getWidth(null),
                      imageG.getHeight(null),
                      BufferedImage.TYPE_INT_ARGB);

                // Draw Image into BufferedImage
                Graphics g = bimg.getGraphics();
                g.drawImage(imageG, 0, 0, null);
                g.dispose();
                return bimg;
    }
}
