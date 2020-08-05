/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.json.JSONArray;
import police.configs.ConexaoDB;
import police.configs.DiscordMessage;
import police.configs.Usuario;

/**
 *
 * @author John
 */
public class InicializadorMain {

    /**
     * @param args the command line arguments
     */
    static JDA jda = null;
    static JSONArray discordDBarray = new JSONArray();
    static JSONArray usuariosDBarray = new JSONArray();
    ConexaoDB conexao = new ConexaoDB();

    
    
    public static void main(String[] args) throws InterruptedException {
        SplashScreen splash = new SplashScreen();
        
        splash.setVisible(true);
        splash.ValorProgresso=40;
        
        splash.texto.setText("INICIANDO CONTATO COM A CENTRAL");
        // TODO code application logic here
        try {
            jda = new JDABuilder("Njc2MTI5MDkyMTkzNzQ2OTUy.XkmjLg.h4ovOM7x8gUa6P2hGmy5NskEkA4")         // The token of the account that is logging in.
                    .addEventListeners(new DiscordMessage())  // An instance of a class that will handle events.
                    .build();
        } catch (LoginException ex) {
            Logger.getLogger(DiscordMessage.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        

        jda.awaitReady(); // Blocking guarantees that JDA will be completely loaded.
        System.out.println("Building JDA finalizado!");
        splash.ValorProgresso=70;
        splash.ProgressoAtual=40;
        splash.texto.setText("VERIFICANDO INTEGRIDADE DOS REGISTROS");
        
        Usuario usuarios = new Usuario();
        usuariosDBarray = usuarios.AttDBUsuarios();
        splash.ValorProgresso=85;
        splash.ProgressoAtual=70;
        splash.ContandoFalhas=0;
        splash.texto.setText("MONTANDO INTERFACE POLICIAL");
        discordDBarray = usuarios.AttDBDiscord();
        splash.ValorProgresso=100;
        splash.ProgressoAtual=85;
        splash.texto.setText("FAZENDO ÚLTIMOS AJUSTES");
        Thread.sleep(300);
        splash.ProgressoAtual=100;
        splash.texto.setText("CONCLUINDO");
        Thread.sleep(500);
        Login logins = new Login();
        logins.setVisible(true);
        splash.Fechar=true;
        splash.dispose();
    }
    public JSONArray AttDBUsuarios(){
        return usuariosDBarray;
    }
    public JSONArray AttDBDiscord(){
        return discordDBarray;
    }
    public JDA getJDA(){
        return jda;
    }
    public void AttDBUsuariosSet(){
        Usuario usuarios = new Usuario();
        usuariosDBarray = usuarios.AttDBUsuarios();
    }
    public void AttDBUsuariosSetDiscord(){
        Usuario usuarios = new Usuario();
        usuariosDBarray = usuarios.AttDBUsuariosDiscord();
    }
}
