/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police.configs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import org.json.JSONArray;
import org.json.JSONObject;
import police.Corporacao;
import police.InicializadorMain;

/**
 *
 * @author John
 */
// não está funcionando
public class Usuario {
    private static final String nome = "Desconhecido";
    
    private static final String passaporte = "-1";
    private static final String codigo = "";
    
    //JSONObject dadoss = new JSONObject();
    private static final String dadoss = "";
    
    private static final String dadosalvar = "";
    
    private String usuariosDB = "";
    private String hierarquiaDB = "";

    Preferences prefs = Preferences.userNodeForPackage(Example.class);
    
    /*public String Patentes(int valor){
        switch (valor) {
            case 0:
                return "Recruit";
            case 1:
                return "Officer I";
            case 2:
                return "Officer II";
            case 3:
                return "Officer III";
            case 4:
                return "Supervisor Officer";
            case 5:
                return "Senior L. Officer";
            case 6:
                return "Sargeant I";
            case 7:
                return "Sargeant II";
            case 8:
                return "Sargent Major";
            case 9:
                return "Liutenant I";
            case 10:
                return "Liutenant II";
            case 11:
                return "Captain";
            case 12:
                return "Major";
            case 13:
                return "Command Officer";
            case 14:
                return "Deputy Chief";
            case 15:
                return "Chefe de Polícia";
            case 16:
                return "Programador";
                
            case 99:
                return "Exonerado";

            default:
                return "Não é LSPD";
        }
    }*/
    public String Patentes(int valor){
        switch (valor) {
            case 0:
                return "Recruta";
            case 1:
                return "Soldado";
            case 2:
                return "Cabo";
            case 3:
                return "3º Sargento";
            case 4:
                return "2º Sargento";
            case 5:
                return "1º Sargento";
            case 6:
                return "SubTenente";
            case 7:
                return "2º Tenente";
            case 8:
                return "1º Tenente";
            case 9:
                return "Capitão";
            case 10:
                return "Major";
            case 11:
                return "Ten. Coronel";
            case 12:
                return "Coronel";
            case 13:
                return "Programador";
                
            case 99:
                return "Exonerado";

            default:
                return "Não é Policial";
        }
    }
    public int Qntptt = 12;
    
    public void setNome(String Nome) {
        prefs.put(nome, Nome);
    }

    public String getNome() {
        return prefs.get(nome, "padrao");
    }
    
    public void setCodigo (String pass){
        prefs.put(codigo, pass);
    }
    public String getCodigo (){
        return prefs.get(codigo, "padrao");
    }
    
    public void setPassaporte (int pass){
        prefs.put(passaporte, pass+"");
    }
    public int getPassaporte (){
        return Integer.parseInt(prefs.get(passaporte, "padrao"));
    }
    
    public void setDados(JSONObject DaDos) {
        prefs.put(dadoss, DaDos.toString());
    }

    public String getDados() {
        String prefss = prefs.get(dadoss, "padrao");
        if(prefss !=null){
            return prefs.get(dadoss, "padrao");
        }
        return null;  
        
        
    }
    
    public void setDadosalvar(JSONObject DaDos) {
        prefs.put(dadosalvar, DaDos.toString());
    }
    public String getDadosalvar() {
        String prefss = prefs.get(dadosalvar, "padrao");
        if(prefss !=null){
            return prefs.get(dadosalvar, "padrao");
        }
        return null;  
    }
    
    public JSONArray AttDBUsuarios(){
        ConexaoDB conexao = new ConexaoDB();
        JSONArray usuariosDBarray = new JSONArray();
         //for(int i = 0; i < usuariosDBarray.length(); i++){
        
        JSONArray usuariosInfo = new JSONArray();
        
        ResultSet resulteSet2 = conexao.getDadosBanco2("cb_users", "user_id");
        try {
            while (resulteSet2.next()) {
                JSONObject getTempoInfo = new JSONObject();
                getTempoInfo.put("id_usuario", resulteSet2.getInt("user_id"));
                getTempoInfo.put("codigo", resulteSet2.getString("codigo"));
                getTempoInfo.put("permissao", resulteSet2.getInt("permissao"));
                getTempoInfo.put("ultimologin", resulteSet2.getString("ultimologin"));
                usuariosInfo.put(getTempoInfo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("conexao.GetConexaoServer(): "+conexao.GetConexaoServer()+" <--");
        
        //InicializadorMain main = new InicializadorMain();
        if(!"".equals(InicializadorMain.host_server)){
            ResultSet resulteSet = conexao.PegarContas();
            int contage=0;
            try {
                while (resulteSet.next()) {
                    JSONObject getTemporario = new JSONObject();
                    boolean setador=false;
                    for(int i = 0; i < usuariosInfo.length(); i++){
                        JSONObject o = usuariosInfo.getJSONObject(i);
                        if(o.getInt("id_usuario") == resulteSet.getInt("user_id")){
                            getTemporario.put("permissao", o.getInt("permissao"));
                            getTemporario.put("codigo", o.getString("codigo"));
                            getTemporario.put("ultimologin", o.getString("ultimologin"));
                            setador=true;
                        }
                    }
                    if(!setador){
                        getTemporario.put("permissao", 0);
                        getTemporario.put("codigo", "");
                        getTemporario.put("ultimologin", 0);
                    }
                    contage++;

                    getTemporario.put("id", contage);
                    getTemporario.put("id_usuario", resulteSet.getString("user_id"));
                    getTemporario.put("nome", resulteSet.getString("name"));
                    getTemporario.put("sobrenome", resulteSet.getString("firstname"));
                    getTemporario.put("registration", resulteSet.getString("registration"));
                    getTemporario.put("phone", resulteSet.getString("phone"));
                    getTemporario.put("age", resulteSet.getString("age"));

                    usuariosDBarray.put(getTemporario);

                    //System.out.println("+1 / ");
                    //getTemporario2.setJSONArray("animals", values);

                    //System.out.print("deu certo!!");

                }
            } catch (SQLException ex) {
                Logger.getLogger(Corporacao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            System.out.println("Não foi conectado ao PegarContas( / InicializadorMain.host_server: "+InicializadorMain.host_server);
        }
        /*for(int ir = 0; ir < usuariosDBarray.length(); ir++){
            JSONObject o = usuariosDBarray.getJSONObject(ir);
            System.out.println(o.toString());
            
        }*/
        return usuariosDBarray;
    }
    
    public JSONArray AttDBUsuariosDiscord(){
        ConexaoDB conexao = new ConexaoDB();
        InicializadorMain policia = new InicializadorMain();
        JSONArray usuariosDBarray = new JSONArray();
        JSONArray usuariosInfo = new JSONArray();
        JSONArray usuariosDBarrayPol = policia.AttDBUsuarios();
        ResultSet resulteSet2 = conexao.getDadosBanco2("cb_identities", "id");
        try {
            while (resulteSet2.next()) {
                JSONObject getTempoInfo = new JSONObject();
                getTempoInfo.put("id_usuario", resulteSet2.getString("id"));
                getTempoInfo.put("lspd", resulteSet2.getInt("lspd"));
                getTempoInfo.put("discord", resulteSet2.getString("discord"));
                getTempoInfo.put("permissao", resulteSet2.getInt("permissao"));
                getTempoInfo.put("codigo", resulteSet2.getString("codigo"));
                getTempoInfo.put("ultimologin", resulteSet2.getString("ultimologin"));
                for(int i = 0; i < usuariosDBarrayPol.length(); i++){
                    JSONObject o = usuariosDBarrayPol.getJSONObject(i);
                    if(resulteSet2.getInt("id") == o.getInt("id_usuario")){
                        getTempoInfo.put("id", o.getInt("id"));
                        //getTempoInfo.put("id_usuario", o.getString("id_usuario"));
                        getTempoInfo.put("nome", o.getString("nome"));
                        getTempoInfo.put("sobrenome", o.getString("sobrenome"));
                        getTempoInfo.put("registration", o.getString("registration"));
                        getTempoInfo.put("phone", o.getString("phone"));
                        getTempoInfo.put("age", o.getString("age"));
                    }
                }
                usuariosInfo.put(getTempoInfo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuariosDBarray;
    }
    
    public JSONArray AttDBDiscord(){
        ConexaoDB conexao = new ConexaoDB();
        JSONArray usuariosDBarray = new JSONArray();
        if(conexao.Servidor_Config()){
            ResultSet resulteSet = conexao.PegarDiscord();
            int contage=0;
            try {
                while (resulteSet.next()) {
                    JSONObject getTemporario = new JSONObject();
                    String path = resulteSet.getString("identifier");
                    // Split path into segments
                    String PegarDiscord="discord:";
                    if(path.startsWith(PegarDiscord)){
                        contage++;
                        String segments[] = path.split(PegarDiscord);
                        String document = segments[segments.length - 1];
                        //System.out.println("id: "+resulteSet.getString("user_id")+" - "+document);
                        getTemporario.put("id", contage);
                        getTemporario.put("user_id", resulteSet.getString("user_id"));
                        getTemporario.put("identifier", document);
                        usuariosDBarray.put(getTemporario);
                    }

                }
            } catch (SQLException ex) {
                Logger.getLogger(Corporacao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //System.out.print(usuariosDBarray.toString());
        return usuariosDBarray;
    }
    
    public JSONArray AttBlackList(){
        ConexaoDB conexao = new ConexaoDB();
        JSONArray usuariosDBarray = new JSONArray();
        ResultSet resulteSet = conexao.PegarBlackList();
        int contage=0;
        try {
            while (resulteSet.next()) {
                JSONObject getTemporario = new JSONObject();
                getTemporario.put("id", contage);
                getTemporario.put("user_id", resulteSet.getInt("user_id"));
                getTemporario.put("bloqueado", resulteSet.getInt("bloqueado"));
                usuariosDBarray.put(getTemporario);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Corporacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.print(usuariosDBarray.toString());
        return usuariosDBarray;
    }
    
    public JSONArray AttDBHierarquia(){
        //PassaAddU
        ConexaoDB conexao = new ConexaoDB();
        JSONArray hierarquiaDBarray = new JSONArray();
        ResultSet resulteSet = conexao.getDadosBanco2("cb_hierarquia", "id");
        int contage=0;
        try {
            while (resulteSet.next()) {
                contage++;

                JSONObject getTemporario = new JSONObject();

                getTemporario.put("id", contage);
                getTemporario.put("id_usuario", resulteSet.getInt("id_usuario"));
                getTemporario.put("cargo", resulteSet.getInt("cargo"));
                getTemporario.put("cargo_antigo", resulteSet.getInt("cargo_antigo"));
                getTemporario.put("motivo", resulteSet.getString("motivo"));
                getTemporario.put("id_promoveu", resulteSet.getInt("id_promoveu"));
                getTemporario.put("data", resulteSet.getString("data"));
                getTemporario.put("stars", resulteSet.getInt("stars"));
                getTemporario.put("unidade", resulteSet.getInt("unidade"));
                //getTemporario.put("lspd", resulteSet.getInt("lspd"));

                hierarquiaDBarray.put(getTemporario);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Corporacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hierarquiaDBarray;
    }
    
    public JSONArray AttDBPrisoes(){
        //PassaAddU
        ConexaoDB conexao = new ConexaoDB();
        JSONArray prisaoDBarray = new JSONArray();
        ResultSet resulteSet = conexao.getDadosBanco2("cb_prisoes", "id");
        int contage=0;
        try {
            while (resulteSet.next()) {
                contage++;

                JSONObject getTemporario = new JSONObject();

                getTemporario.put("id", contage);
                getTemporario.put("id_usuario", resulteSet.getInt("id_usuario"));
                getTemporario.put("data", resulteSet.getString("data"));
                getTemporario.put("motivo", resulteSet.getString("motivo"));
                getTemporario.put("id_prendeu", resulteSet.getInt("id_prendeu"));
                getTemporario.put("protocolo", resulteSet.getString("protocolo"));
                getTemporario.put("tornz", resulteSet.getInt("tornz"));
                getTemporario.put("meses", resulteSet.getInt("meses"));
                getTemporario.put("multas", resulteSet.getInt("multas"));
                getTemporario.put("justificado", resulteSet.getString("justificado"));
                
                if(resulteSet.getString("limpo")==null){
                    getTemporario.put("limpo", "");
                }else{
                    getTemporario.put("limpo", resulteSet.getString("limpo"));
                }
                if(resulteSet.getString("pagou")==null){
                    getTemporario.put("pagou", "");
                }else{
                    getTemporario.put("pagou", resulteSet.getString("pagou"));
                }

                prisaoDBarray.put(getTemporario);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Corporacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prisaoDBarray;
    }
    
    
    public JSONArray AttDBLogItens(){
        ConexaoDB conexao = new ConexaoDB();
        JSONArray usuariosDBarray = new JSONArray();
        ResultSet resulteSet2 = conexao.getDadosBanco2("vrp_itenstransfer", "id");
        int contage2=0;
        try {
            while (resulteSet2.next()) {
                contage2++;
                JSONObject getTempoInfo = new JSONObject();
                //getTempoInfo.put("id", contage2);
                getTempoInfo.put("id", contage2);
                getTempoInfo.put("id_user", resulteSet2.getInt("id_user"));
                getTempoInfo.put("id_rece", resulteSet2.getInt("id_rece"));
                getTempoInfo.put("tipo", resulteSet2.getString("tipo"));
                getTempoInfo.put("profissao_user", resulteSet2.getString("profissao_user"));
                getTempoInfo.put("profissao_recebeu", resulteSet2.getString("profissao_recebeu"));
                getTempoInfo.put("item", resulteSet2.getString("item"));
                getTempoInfo.put("data", resulteSet2.getString("data"));
                getTempoInfo.put("posicao", resulteSet2.getString("posicao"));
                usuariosDBarray.put(getTempoInfo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        ResultSet resulteSet = conexao.getDadosBanco2("vrp_ammu", "id");
        int contage=0;
        try {
            while (resulteSet.next()) {
                JSONObject getTemporario = new JSONObject();
                contage++;
                
                getTemporario.put("id", contage);
                getTemporario.put("id_user", resulteSet2.getInt("id_user"));
                getTemporario.put("id_rece", 0);
                getTemporario.put("tipo", "ammunation");
                getTemporario.put("profissao_user", resulteSet2.getString("profissao"));
                getTemporario.put("profissao_recebeu", "Nenhuma");
                getTemporario.put("item", resulteSet2.getString("inventario"));
                getTemporario.put("data", resulteSet2.getString("data"));
                getTemporario.put("posicao", "0, 0, 0");
                
                usuariosDBarray.put(getTemporario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Corporacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return usuariosDBarray;
    }
    
    
    public JSONArray AttDBProcurados(){
        //PassaAddU
        ConexaoDB conexao = new ConexaoDB();
        JSONArray prisaoDBarray = new JSONArray();
        ResultSet resulteSet = conexao.getDadosBanco2("cb_procurados", "id");
        int contage=0;
        try {
            while (resulteSet.next()) {
                contage++;

                JSONObject getTemporario = new JSONObject();

                getTemporario.put("id", contage);
                getTemporario.put("id_usuario", resulteSet.getInt("id_usuario"));
                getTemporario.put("data", resulteSet.getString("data"));
                getTemporario.put("motivo", resulteSet.getString("motivo"));
                getTemporario.put("procurou_id", resulteSet.getInt("procurou_id"));
                getTemporario.put("protocolo", resulteSet.getString("protocolo"));
                getTemporario.put("meses", resulteSet.getInt("meses"));
                getTemporario.put("multas", resulteSet.getInt("multas"));
                getTemporario.put("tornz", resulteSet.getInt("tornz"));
                getTemporario.put("justificado", resulteSet.getString("justificado"));
                getTemporario.put("nivel_procurado", resulteSet.getInt("nivel_procurado"));
                getTemporario.put("observacao", resulteSet.getString("observacao"));
                getTemporario.put("contravencoes", resulteSet.getString("contravencoes"));
                if(resulteSet.getString("pagou")==null){
                    getTemporario.put("pagou", "0");
                }else{
                    getTemporario.put("pagou", resulteSet.getString("pagou"));
                }

                prisaoDBarray.put(getTemporario);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Corporacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prisaoDBarray;
    }
    
    public JSONArray AttDBCrimes(){
        //PassaAddU
        ConexaoDB conexao = new ConexaoDB();
        JSONArray prisaoDBarray = new JSONArray();
        ResultSet resulteSet = conexao.getDadosBanco2("cb_procurados", "id");
        int contage=0;
        try {
            while (resulteSet.next()) {
                contage++;

                JSONObject getTemporario = new JSONObject();

                getTemporario.put("id", contage);
                getTemporario.put("id_usuario", resulteSet.getInt("id_usuario"));
                getTemporario.put("data", resulteSet.getString("data"));
                getTemporario.put("motivo", resulteSet.getString("motivo"));
                getTemporario.put("procurou_id", resulteSet.getInt("procurou_id"));
                getTemporario.put("protocolo", resulteSet.getString("protocolo"));
                getTemporario.put("meses", resulteSet.getInt("meses"));
                getTemporario.put("multas", resulteSet.getInt("multas"));
                getTemporario.put("tornz", resulteSet.getInt("tornz"));
                getTemporario.put("justificado", resulteSet.getString("justificado"));
                getTemporario.put("nivel_procurado", resulteSet.getInt("nivel_procurado"));
                getTemporario.put("observacao", resulteSet.getString("observacao"));
                getTemporario.put("contravencoes", resulteSet.getString("contravencoes"));
                if(resulteSet.getString("pagou")==null){
                    getTemporario.put("pagou", "0");
                }else{
                    getTemporario.put("pagou", resulteSet.getString("pagou"));
                }

                prisaoDBarray.put(getTemporario);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Corporacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prisaoDBarray;
    }
    
    public JSONArray CrimesServerID(){
        //PassaAddU
        ConexaoDB conexao = new ConexaoDB();
        JSONArray crimesserverDBarray = new JSONArray();
        ResultSet resulteSet = conexao.PegarValoresPorServerID("cb_crimes");
        try {
            while (resulteSet.next()) {
                JSONObject getTemporario = new JSONObject();
                getTemporario.put("id", resulteSet.getInt("id"));
                getTemporario.put("categorias", resulteSet.getString("categorias"));
                getTemporario.put("crimes", resulteSet.getString("crimes"));
                crimesserverDBarray.put(getTemporario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Corporacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return crimesserverDBarray;
    }
}
