/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police;

import java.util.Timer;
import java.util.TimerTask;
import net.dv8tion.jda.api.JDA;
import org.json.JSONArray;
import org.json.JSONObject;
import police.configs.ConexaoDB;
import police.configs.GetImages;
import police.configs.SNWindows;
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
    
    public static JSONObject info_cidade = new JSONObject();
    
    public static JSONArray vrp_users = new JSONArray();
    public static JSONArray cb_users = new JSONArray();
    
    public static String host_server =  "";
    public static String banco_server = "";
    public static String user_server =  "";
    public static String pass_server =  "";
    
    public static boolean ModoOffline = false;
    
    public static int server_id =  0;
    
    public static Sobre sobre = new Sobre();
    
    public static String DestFile = SNWindows.DestPasta+"/cb-l.txt";
    public static String DestFile2 = SNWindows.DestPasta+"/cb-c.txt";
    public static String DestFile3 = SNWindows.DestPasta+"/cb-ar.txt";
    public static String DestFileUsers = SNWindows.DestPasta+"/cb-users.txt";
    
    //static ExportarImportar Export = new ExportarImportar();
    
    public static void main(String[] args) throws InterruptedException {
        SplashScreen splash = new SplashScreen();
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                if(server_id > 0){
                    Usuario usuarios = new Usuario();
                    usuariosDBarray = usuarios.AttDBUsuarios();
                    discordDBarray = usuarios.AttDBDiscord();
                    prisoesDBarray = usuarios.AttDBPrisoes();
                    procuradosDBarray = usuarios.AttDBProcurados();
                    hierarquiaDBarray = usuarios.AttDBHierarquia();
                    //System.out.println("Banco de dados ATUALIZADO.");
                }
                ConexaoDB conexao = new ConexaoDB();
                conexao.ConfigCarregar();
                SNWindows.getSerialALL();
            }
        },0,2*60000);
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
    
    public static void AttDBSHierarquia(){
        Usuario usuarios = new Usuario();
        usuariosDBarray = usuarios.AttDBUsuarios();
        hierarquiaDBarray = usuarios.AttDBHierarquia();
    }
    
    public void AttDBSTodas(){
        Usuario usuarios = new Usuario();
        usuariosDBarray = usuarios.AttDBUsuarios();
        discordDBarray = usuarios.AttDBDiscord();
        prisoesDBarray = usuarios.AttDBPrisoes();
        blacklistDBarray = usuarios.AttBlackList();
        procuradosDBarray = usuarios.AttDBProcurados();
        hierarquiaDBarray = usuarios.AttDBHierarquia();
        Usuario.AtualizarDiscords();
    }
    
    public static void AttDbsStatic(){
        Usuario usuarios = new Usuario();
        usuariosDBarray = usuarios.AttDBUsuarios();
        discordDBarray = usuarios.AttDBDiscord();
        prisoesDBarray = usuarios.AttDBPrisoes();
        blacklistDBarray = usuarios.AttBlackList();
        procuradosDBarray = usuarios.AttDBProcurados();
        hierarquiaDBarray = usuarios.AttDBHierarquia();
        Usuario.AtualizarDiscords();
    }
}
