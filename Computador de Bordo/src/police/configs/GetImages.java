/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police.configs;

import java.awt.Image;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import police.SplashScreen;

/**
 *
 * @author John
 */
    //======= EXEMPLOS =======
        //this.setIconImage(new ImageIcon(image).getImage());
        //icone.setIcon(new ImageIcon(image)); // NOI18N
public class GetImages {
    public static Image LogoCB;
    public static Image LogoCB_branco;
    public static void PegarImagensCB(String Url){
        LogoCB = PegarImagemURL(Url); // https://computadordebordo.tk/wp-content/uploads/2020/09/150x150.png
        LogoCB_branco = PegarImagemURL(Url); // https://computadordebordo.tk/wp-content/uploads/2020/09/150x150-branca.png
    }
    public static Image PegarImagemURL(String URL){
        URL url = null;
        try {
            url = new URL(URL);
        } catch (MalformedURLException ex) {
            Logger.getLogger(SplashScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        Image image = null;
        try {
            image = ImageIO.read(url);
        } catch (IOException ex) {
            Logger.getLogger(SplashScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        return image;
    }
    
}
