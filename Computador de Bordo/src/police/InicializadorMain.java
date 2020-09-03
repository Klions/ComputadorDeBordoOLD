/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.json.JSONArray;
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
    public static JDA jda = null;
    public static JSONArray discordDBarray = new JSONArray();
    public static JSONArray usuariosDBarray = new JSONArray();
    
    public static JSONArray prisoesDBarray = new JSONArray();
    public static JSONArray blacklistDBarray = new JSONArray();
    public static JSONArray procuradosDBarray = new JSONArray();
    public static JSONArray hierarquiaDBarray = new JSONArray();
    
    public static JSONArray usuarioMyDBarray = new JSONArray();
    
    public static JSONArray vrp_users = new JSONArray();
    
    public static String host_server =  ""; //"158.69.22.55";
    public static String banco_server = "";//"vrp";
    public static String user_server =  "";//"ferrazgado";
    public static String pass_server =  "";//"gadoferraz";
    
    public static int server_id =  0;
    
    public static String BuildCB = "0";
    
    public static Sobre sobre = new Sobre();
    
    public static void main(String[] args) throws InterruptedException {
        SplashScreen splash = new SplashScreen();
        
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                Usuario usuarios = new Usuario();
                usuariosDBarray = usuarios.AttDBUsuarios();
                discordDBarray = usuarios.AttDBDiscord();
                prisoesDBarray = usuarios.AttDBPrisoes();
                procuradosDBarray = usuarios.AttDBProcurados();
                hierarquiaDBarray = usuarios.AttDBHierarquia();
                usuarioMyDBarray = usuarios.AttDBUsuarioMy();
                System.out.println("Banco de dados ATUALIZADO.");
            }
        },0,60000);
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
    
    public void AttDBSTodas(){
        Usuario usuarios = new Usuario();
        usuariosDBarray = usuarios.AttDBUsuarios();
        discordDBarray = usuarios.AttDBDiscord();
        prisoesDBarray = usuarios.AttDBPrisoes();
        blacklistDBarray = usuarios.AttBlackList();
        procuradosDBarray = usuarios.AttDBProcurados();
        hierarquiaDBarray = usuarios.AttDBHierarquia();
        usuarioMyDBarray = usuarios.AttDBUsuarioMy();
    }
}
