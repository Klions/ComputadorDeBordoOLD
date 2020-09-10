/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police.configs;

import java.util.prefs.Preferences;
import org.json.JSONArray;
import org.json.JSONObject;
import police.InicializadorMain;

/**
 *
 * @author John
 */
public class Config {
    public static int build_atual = 20200817;
    public static String versao = "1.0 [BETA]";
    public static String linkB = "https://rainbowcity.com.br/LSPD.exe";
    
    //CONFIGURAÇÃOS PERSONALIZADAS PARA CADA TIPO DE POLICIA
    public String NomePolicia           = "Polícia Militar Rainbow City";
    public String Abv_NomePolicia       = "PMERC";
    public String img_DiscordPolicia    = "https://i.imgur.com/WveHfXw.png";
    public String img_CBIcone           = "imagens/CB.png";
    
    public static JSONObject cb_config = new JSONObject();
    
    public static int getBuild() {
        if(cb_config.has("build")){
            return cb_config.getInt("build");
        }
        return 0;
    }
    
    public static String getVersao() {
        if(cb_config.has("versao")){
            return cb_config.getString("versao");
        }
        return "";
    }
    
    public static boolean getNeed() {
        if(cb_config.has("need")){
            int nidi = cb_config.getInt("need");
            return nidi >= 1;
        }
        return false;
    }
    
    public static String getLink() {
        if(cb_config.has("link")){
            return cb_config.getString("link");
        }
        return "";
    }
    
    public static String getMensagem() {
        if(cb_config.has("mensagem")){
            return cb_config.getString("mensagem");
        }
        return "";
    }
    
    public static boolean VerificarAtt(){
        int AttBuild1 = build_atual;
        int AttBuild2 = getBuild();//Integer.parseInt(prefs.get(buildB, "default"));
        return AttBuild1 < AttBuild2;
    }
}