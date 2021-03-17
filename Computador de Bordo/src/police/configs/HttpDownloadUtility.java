/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police.configs;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.RenderingHints.Key;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JOptionPane;
import net.dv8tion.jda.api.EmbedBuilder;
import police.Login;

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
    
    public static void openURL(String url) {
        Object[] options = { "Confirmar", "Cancelar" };
        int result = JOptionPane.showOptionDialog(null, "Deseja abrir o link '"+url+"' em seu navegador?", "Abrir link externo", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        if (result == JOptionPane.OK_OPTION) {
            URI uri = null;
            try {
                uri = new URI(url);
            } catch (URISyntaxException ex) {
                Logger.getLogger(HttpDownloadUtility.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(uri);
                } catch (IOException e) { /* TODO: error handling */ }
            } else { /* TODO: error handling */ }
            
        }
    }
    
    public static String DownloadArquivo(String Url){
        String fileURL = Url;
        String NovoNome = "CBordo";
        String path=null;
        try {
            path = new File(".").getCanonicalPath();
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.print(path);
        String saveDir = path;
        boolean down=false;
        try {
            down = downloadFile(fileURL, saveDir, NovoNome);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if(down){
            try {
                Process process = new ProcessBuilder(path+"/"+NovoNome+".exe").start();
                Thread.sleep(1000);
                System.exit(1);
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                return "OCORREU ALGUM ERRO AO TENTAR ABRIR O APP";
            } catch (InterruptedException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            return "OCORREU UMA FALHA NO DOWNLOAD";
        }
        return "INICIANDO APP ATUALIZADO";
    }
    
    public static boolean WebhookLog(String canal_id, String Titulo, String Description){ // 
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(Titulo, null);
        //eb.setAuthor(Titulo, "https://imgur.com/vBK8vRk.png");
        if(Description.toLowerCase().contains("online")){
            eb.setColor(new Color(254, 254, 254));
        }else if(Description.toLowerCase().contains("offline")){
            eb.setColor(new Color(41, 143, 202));
        }else{
            eb.setColor(new Color(5, 65, 99));
        }
        
        eb.setDescription(Description);

        eb.addField("IP", SNWindows.IP, true);
        eb.addField("PC", SNWindows.SerialNumber, true);
        eb.addField("VERSÃO CB", Config.versao, true);
        
        /*eb.addField("Policial "+proreb, nome_usuario+" ("+id_usuario+")", true);
        eb.addField("Oficial solicitante", nome_promoveu+" ("+id_promoveu+")", true);
        eb.addField("Motivo", motivo, false);
        eb.addField("Antiga Patente", oldpatente+" "+PttInsi2, true);
        eb.addField("Nova Patente", novapatente+" "+PttInsi, true);
        eb.setThumbnail(config.img_DiscordPolicia);*/
        if(!"7AC28570-51FC-0000-0000-000000000000".equals(SNWindows.SerialNumber)) DiscordWebhook2.EnviarMsg(canal_id, eb);
        return true;
    }
    
    public static boolean WebhookDiscord(String Url, String Titulo, String Description){ // 
        DiscordWebhook webhook = new DiscordWebhook(Url);
        webhook.setContent("");
        webhook.setAvatarUrl("https://i.imgur.com/3VY1Yjx.png");
        Color CorEmbed = new Color(5, 65, 99);
        if(Description.toLowerCase().contains("online")){
            webhook.setUsername("Modo Cidade");
            CorEmbed = new Color(254, 254, 254);
        }else if(Description.toLowerCase().contains("offline")){
            webhook.setUsername("Modo Offline");
            CorEmbed = new Color(41, 143, 202);
        }else{
            webhook.setUsername("Computador de Bordo");
        }
        
        //webhook.setTts(true);
        webhook.addEmbed(new DiscordWebhook.EmbedObject()
                //.setTitle("Title")
                .setDescription(Description)
                .setColor(CorEmbed)
                .addField("IP", SNWindows.IP, false)
                .addField("PC", SNWindows.SerialNumber, false)
                .addField("VERSÃO CB", Config.versao, false)
                //.setThumbnail("https://kryptongta.com/images/kryptonlogo.png")
                .setFooter("Computador de Bordo • "+Titulo, "https://i.imgur.com/x47Q1tn.png")
                //.setImage("https://kryptongta.com/images/kryptontitle2.png")
                .setAuthor(Titulo, "","https://i.imgur.com/x47Q1tn.png"));
                //.setUrl("https://kryptongta.com"));
        /*webhook.addEmbed(new DiscordWebhook.EmbedObject()
        .setDescription("Just another added embed object!"));*/
        
        try {
            webhook.execute(); //Handle exception
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}
