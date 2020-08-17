/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police.configs;

import java.util.prefs.Preferences;
import police.InicializadorMain;

/**
 *
 * @author John
 */
public class Config {
    public int build = 20200816;
    public String versao = "1.0 [BETA]";
    public String linkB = "https://rainbowcity.com.br/LSPD.exe";
    Preferences prefs = Preferences.userNodeForPackage(Example.class);
    
    //CONFIGURAÇÃOS PERSONALIZADAS PARA CADA TIPO DE POLICIA
    public String NomePolicia           = "Polícia Militar Rainbow City";
    public String Abv_NomePolicia       = "PMERC";
    public String img_DiscordPolicia    = "https://i.imgur.com/WveHfXw.png";
    public String img_CBIcone           = "imagens/RC.png";
    
    private static final String buildB = "20200816";
    private static final String versaoB = "1.0 [BETA]";
    private static final String needB = "0";
    private static final String link = "";
    private static final String mensagem = "";
    
    public void setBuild(String Valor) {
        InicializadorMain.BuildCB = Valor; //prefs.put(buildB, Valor);
    }
    public String getBuild() {
        return InicializadorMain.BuildCB;
    }
    
    public void setVersao(String Valor) {
        prefs.put(versaoB, Valor);
    }
    public String getVersao() {
        return prefs.get(versaoB, "default");
    }
    
    public void setNeed(String Valor) {
        prefs.put(needB, Valor);
    }
    public boolean getNeed() {
        int nidi = Integer.parseInt(prefs.get(needB, "default"));
        return nidi >= 1;
    }
    
    public void setLink(String Valor) {
        prefs.put(link, Valor);
    }
    public String getLink() {
        return prefs.get(link, "default");
    }
    
    public void setMensagem(String Valor) {
        prefs.put(mensagem, Valor);
    }
    public String getMensagem() {
        return prefs.get(mensagem, "default");
    }
    
    public boolean VerificarAtt(){
        int AttBuild1 = build;
        int AttBuild2 = Integer.parseInt(InicializadorMain.BuildCB);//Integer.parseInt(prefs.get(buildB, "default"));
        return AttBuild1 < AttBuild2;
    }
}