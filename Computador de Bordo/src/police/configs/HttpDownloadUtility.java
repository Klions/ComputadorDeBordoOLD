/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police.configs;

import java.awt.RenderingHints.Key;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author John
 */
public class HttpDownloadUtility {
    private static final int BUFFER_SIZE = 4096;
 
    /**
     * Downloads a file from a URL
     * @param fileURL HTTP URL of the file to be downloaded
     * @param saveDir path of the directory to save the file
     * @throws IOException
     */
    public static boolean downloadFile(String fileURL, String saveDir, String NovoNome) throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();
 
        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String fileName = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();
 
            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    fileName = disposition.substring(index + 10,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length());
            }
 
            System.out.println("Content-Type = " + contentType);
            System.out.println("Content-Disposition = " + disposition);
            System.out.println("Content-Length = " + contentLength);
            System.out.println("fileName = " + fileName);
 
            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + File.separator + NovoNome+".exe";
             
            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
 
            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
 
            outputStream.close();
            inputStream.close();
 
            System.out.println("Arquivo baixado!");
            httpConn.disconnect();
            return true;
        } else {
            System.out.println("Nenhum arquivo para baixar. Servidor retornando ao erro: " + responseCode);
            httpConn.disconnect();
            return false;
        }
    }
    
    public static String GetCon(String Conect){
        String ALGO = "AES";
        byte[] keyValue = 
        new byte[] { 'H', 't', 'v', 'b', 'a', 'w', 'e',
        'i', 'n', 'v', 'a','l', 't', 'k', 'y', 'e' };
        Base64.Encoder enc = Base64.getEncoder();
        Key pass = generateKey(Conect);
        // cipher class to provide the encryption and intialize
        Cipher c = null;
        try {
            c = Cipher.getInstance(ALGO);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(HttpDownloadUtility.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(HttpDownloadUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] encVal = null;
        String encryptedValue = Base64.getEncoder().encodeToString(new byte[] { 'i', 'n', 'v', 'a','l', 't', 'k', 'y', 'e',
            'H', 't', 'v', 'b', 'a', 'w', 'e',
        });
        return enc.encodeToString(Conect.getBytes());
    }
    public static String SetCon(String Conect){
        String alore = DecodeBase64(Conect);
        return alore;
    }
    public static String DecodeBase64(String StrDec){
        Base64.Decoder dec = Base64.getDecoder();
        byte[] bites = null;
        try {
            bites = dec.decode(StrDec);
        } catch (IllegalArgumentException e) { e.printStackTrace(); }     
        if(bites != null) return new String(bites);
        return "";
    }
    private static Key generateKey(String key) {
        Key pass=null;
        key = pass+"";
        return pass;
    }
}
