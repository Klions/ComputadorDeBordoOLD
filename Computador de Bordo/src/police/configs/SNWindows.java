/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police.configs;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import org.json.JSONArray;
import org.json.JSONObject;
import police.Gerenciamento;
import police.InicializadorMain;
/**
 *
 * @author John
 */
public class SNWindows {
    public static String SerialNumber = "";
    public static String SerialNumber2 = "";
    public static String DestPasta = System.getProperty("user.home")+"/Documents/.cb";
    public static String DestFileSer = DestPasta+"/cb-ser.txt";
    public static JSONArray cb_serial = new JSONArray();
    public static JSONArray cb_serial_cidade = new JSONArray();
    public static String IP;
    
    public static boolean isNumeric(String str) { 
        try {  
            Double.parseDouble(str);  
            return true;
        } catch(NumberFormatException e){  
            return false;  
        }  
    }
    
    public static void PegarIP(){
        String systemipaddress = ""; 
        try { 
            URL url_name = new URL("http://bot.whatismyipaddress.com"); 
  
            BufferedReader sc = new BufferedReader( 
                new InputStreamReader(url_name.openStream())); 
  
            // reads system IPAddress 
            systemipaddress = sc.readLine().trim(); 
        } 
        catch (Exception e) { 
            systemipaddress = ""; 
        } 
        // Print IP address 
        System.out.println("IP Público: "
                           + systemipaddress + "\n"); 
        IP = systemipaddress;
    }
    
    public static String[] TipoAssinatura = {
        "Gratuita",
        "Bronze",
        "Prata",
        "Ouro",
        "Platina"
    };
    public static int[][] CorAssinatura = {
        {255,153,153},
        {235,199,158},
        {192,192,192},
        {255,215,0},
        {229,228,226}
    };
    public static int[][] CategAssinatura = {
        {2,20},
        {6,40},
        {7,60},
        {10,120},
        {30,200}
    };
    public static int[] BeneficiosAssinatura = { // LIMITE DE BENEFICIOS
        3,
        5,
        7,
        10,
        15
    };
    
    public static int[] UsuariosAssinatura = {
        5,
        15,
        40,
        100,
        500
    };
    public static final void getSerialNumber(){
        String command = "wmic csproduct get UUID";
        StringBuilder output = new StringBuilder();

        Process SerNumProcess = null;
        try {
            SerNumProcess = Runtime.getRuntime().exec(command);
        } catch (IOException ex) {
            Logger.getLogger(SNWindows.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedReader sNumReader = new BufferedReader(new InputStreamReader(SerNumProcess.getInputStream()));

        String line = "";
        try {
            while ((line = sNumReader.readLine()) != null) {
                output.append(line).append("\n");
            }
        } catch (IOException ex) {
            Logger.getLogger(SNWindows.class.getName()).log(Level.SEVERE, null, ex);
        }
        String MachineID=output.toString().substring(output.indexOf("\n"), output.length()).trim();
        //System.out.println(MachineID);
        SerialNumber = MachineID;
    }
    public static void getSerialOnPC(){
        File pasta = new File(DestPasta);
        if(!pasta.exists()) pasta.mkdirs();
            
        try {
            File file = new File(DestFileSer);
            if(file.exists()){
                try ( //if this file exists
                        Scanner scan = new Scanner(file) //Use Scanner to read the File
                ) {
                    Base64.Decoder dec = Base64.getDecoder();
                    String DecoderStre = Gerenciamento.DecodeBase64(scan.nextLine());
                    if(!"".equals(DecoderStre)){
                        SerialNumber2 = DecoderStre;
                    }
                }
            }

        } catch (FileNotFoundException e) {         
            e.printStackTrace();
        }
    }
    public static boolean setSerialOnPC(String Serial){
        getSerialNumber();
        if(getSerialOn(Serial)){
            if(SetSerialALL(Serial)){
                SerialNumber2=Serial;
                try {
                    File file = new File(DestFileSer);
                    if(!file.exists()) file.createNewFile();
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsolutePath()))) {
                        Base64.Encoder enc = Base64.getEncoder();
                        bw.write(enc.encodeToString(Serial.getBytes()));
                    }
                } catch (IOException e) {}
                getSerialALL();
                return true;
            }
        }
        return false;
    }
    
    public static void getSerialALL(){
        getSerialOnPC();
        cb_serial = new JSONArray();
        ConexaoDB conexao = new ConexaoDB();
        ResultSet resulteSet = conexao.GetPersonalizado("select * from cb_serial ORDER BY id DESC");
        try {
            while (resulteSet.next()) {
                JSONObject getTemporario2 = new JSONObject();
                getTemporario2.put("id", resulteSet.getInt("id"));
                getTemporario2.put("serial", resulteSet.getString("serial"));
                getTemporario2.put("nivel", resulteSet.getInt("nivel"));
                getTemporario2.put("pc_ativou", resulteSet.getString("pc_ativou"));
                getTemporario2.put("data_ativou", resulteSet.getString("data_ativou"));
                cb_serial.put(getTemporario2);
                //System.out.println("getSerialALL(): "+resulteSet.getString("serial"));
            }
        } catch (SQLException ex) {
        }
        
        ResultSet resulteSet2 = conexao.GetPersonalizado("select * from cb_serial_cidade ORDER BY id DESC");
        try {
            while (resulteSet2.next()) {
                JSONObject getTemporario2 = new JSONObject();
                getTemporario2.put("id", resulteSet2.getInt("id"));
                getTemporario2.put("server_id", resulteSet2.getInt("server_id"));
                getTemporario2.put("serial", resulteSet2.getString("serial"));
                getTemporario2.put("nivel", resulteSet2.getInt("nivel"));
                getTemporario2.put("data", resulteSet2.getString("data"));
                getTemporario2.put("data_ativou", resulteSet2.getString("data_ativou"));
                getTemporario2.put("comprovante_id", resulteSet2.getInt("comprovante_id"));
                getTemporario2.put("expira", resulteSet2.getInt("expira"));
                getTemporario2.put("discord_id", resulteSet2.getString("discord_id"));
                cb_serial_cidade.put(getTemporario2);
                //System.out.println("getSerialALL(): "+resulteSet.getString("serial"));
            }
        } catch (SQLException ex) {
        }
        
    }
    
    public static boolean getSerialOn(String Serial){
        if(InicializadorMain.ModoOffline){
            for(int i = 0; i < cb_serial.length(); i++){
                JSONObject obj = cb_serial.getJSONObject(i);
                if(Serial.equals(obj.getString("serial"))){
                    if(obj.getInt("data_ativou") <= 0) return true;
                }
            }
        }else{
            for(int i = 0; i < cb_serial_cidade.length(); i++){
                JSONObject obj = cb_serial_cidade.getJSONObject(i);
                if(Serial.equals(obj.getString("serial"))){
                    if(obj.getInt("data_ativou") <= 0) return true;
                }
            }
        }
        return false;
    }
    
    public static int getNivelSerialPC(){
        if(InicializadorMain.ModoOffline){
            for(int i = 0; i < cb_serial.length(); i++){
                JSONObject obj = cb_serial.getJSONObject(i);
                if(SerialNumber.equals(obj.getString("pc_ativou"))){
                    if(!"0".equals(obj.getString("data_ativou"))) return obj.getInt("nivel");
                }
            }
        }else{
            for(int i = 0; i < cb_serial_cidade.length(); i++){
                JSONObject obj = cb_serial_cidade.getJSONObject(i);
                if(InicializadorMain.server_id == obj.getInt("server_id")){
                    if(!"0".equals(obj.getString("data_ativou"))) return obj.getInt("nivel");
                }
            }
        }
        return 0;
    }
    
    public static int getNivelSerialPorSerial(String Serial){
        if(InicializadorMain.ModoOffline){
            for(int i = 0; i < cb_serial.length(); i++){
                JSONObject obj = cb_serial.getJSONObject(i);
                if(Serial.equals(obj.getString("serial"))){
                    return obj.getInt("nivel");
                }
            }
        }
        return 0;
    }
    
    public static boolean SetSerialALL(String Serial){
        ConexaoDB conexao = new ConexaoDB();
        return conexao.UpdatePersonalizado("update cb_serial set pc_ativou='"+SerialNumber+"', data_ativou='"+System.currentTimeMillis()+"' where serial='"+Serial+"' AND data_ativou = '0'");
    }
    
    public static ServerSocket socket; 
    public static void checkIfRunning(int Porta) {
        try {
          //Bind to localhost adapter with a zero connection queue 
          socket = new ServerSocket(Porta,0,InetAddress.getByAddress(new byte[] {127,0,0,1}));
        }
        catch (BindException e) {
          showMessageDialog(null,
            "Feche o programa antes de abrir outra janela!",
            "Programa já está sendo executado",
            JOptionPane.ERROR_MESSAGE);
          System.err.println("Already running.");
          System.exit(1);
        }
        catch (IOException e) {
          System.err.println("Unexpected error.");
          e.printStackTrace();
          System.exit(2);
        }
    }
    
    public static boolean possuiConfig(String nome){
        if(!InicializadorMain.ModoOffline){
            for(int i = 0; i < InicializadorMain.configsDBarray.length(); i++){
                JSONObject obj = InicializadorMain.configsDBarray.getJSONObject(i);
                if(nome.equals(obj.getString("nome"))){
                    return true;
                }
            }
        }
        return false;
    }
    
    public static boolean isAdemir(){
        JSONObject obj = Usuario.getDados();
        if(!InicializadorMain.ModoOffline){
            int admin = obj.getInt("admin");
            if(admin > 0) return true;
        }
        return false;
    }
    
    public static JSONArray getCargos(){
        JSONArray cargos_disponiveis = new JSONArray();
        if(!InicializadorMain.ModoOffline){
            for(int i = 0; i < InicializadorMain.configsDBarray.length(); i++){
                JSONObject obj = InicializadorMain.configsDBarray.getJSONObject(i);
                if("hierarquia".equals(obj.getString("nome"))){
                    return new JSONArray(obj.getString("valor"));
                }
            }
        }
        return cargos_disponiveis;
    }
    
    public static JSONObject getCargoObjUser(){
        JSONObject cargos_disponiveis = new JSONObject();
        //JSONObject obj_user = Usuario.getDados();
        JSONArray cargos_hierarquia = getCargos();
        for(int i2 = 0; i2 < cargos_hierarquia.length(); i2++){
            cargos_disponiveis = cargos_hierarquia.getJSONObject(i2);
            //if(obj_user.getInt("permissao") == cargos_disponiveis.getInt("id")){
            //System.out.println("getHierarquiaUserValueInt: "+getHierarquiaUserValueInt("cargo")+" / cargos_disponiveis.getInt(\"id\"): "+cargos_disponiveis.getInt("id"));
            if(getHierarquiaUserValueInt("cargo") == cargos_disponiveis.getInt("id")){
                return cargos_disponiveis;
            }
        }
        return cargos_disponiveis;
    }
    
    public static JSONObject getCargoObjUserID(int USER_ID){
        JSONObject cargos_disponiveis = new JSONObject();
        JSONArray cargos_hierarquia = getCargos();
        for(int i2 = 0; i2 < cargos_hierarquia.length(); i2++){
            cargos_disponiveis = cargos_hierarquia.getJSONObject(i2);
            if(getHierarquiaPorUserIDValueInt(USER_ID, "cargo") == cargos_disponiveis.getInt("id")){
                return cargos_disponiveis;
            }
        }
        return cargos_disponiveis;
    }
    
    public static JSONObject getCargoPorID(int ID_CARGO){
        JSONObject cargos_disponiveis = new JSONObject();
        JSONArray cargos_hierarquia = getCargos();
        for(int i2 = 0; i2 < cargos_hierarquia.length(); i2++){
            cargos_disponiveis = cargos_hierarquia.getJSONObject(i2);
            if(ID_CARGO == cargos_disponiveis.getInt("id")){
                //System.out.println("obj_cargo(): "+cargos_disponiveis.toString());
                return cargos_disponiveis;
            }
        }
        return cargos_disponiveis;
    }
    
    public static JSONArray getHierarquiaPorID(int ID_USER){
        JSONArray cargos_disponiveis = new JSONArray();
        if(!InicializadorMain.ModoOffline){
            for(int i = 0; i < InicializadorMain.hierarquiaDBarray.length(); i++){
                JSONObject obj = InicializadorMain.hierarquiaDBarray.getJSONObject(i);
                if(ID_USER == obj.getInt("id_usuario")){
                    cargos_disponiveis.put(obj);
                }
            }
        }
        return cargos_disponiveis;
    }
    
    public static JSONObject getLastHierarquiaPorID(int ID_USER){
        JSONObject cargos_disponiveis = new JSONObject();
        if(!InicializadorMain.ModoOffline){
            for(int i = 0; i < InicializadorMain.hierarquiaDBarray.length(); i++){
                JSONObject obj = InicializadorMain.hierarquiaDBarray.getJSONObject(i);
                if(ID_USER == obj.getInt("id_usuario")){
                    cargos_disponiveis = obj;
                }
            }
        }
        return cargos_disponiveis;
    }
    
    public static JSONArray getHierarquiaUser(){
        JSONArray cargos_disponiveis = new JSONArray();
        JSONObject obj_user = Usuario.getDados();
        if(!InicializadorMain.ModoOffline){
            cargos_disponiveis = getHierarquiaPorID(obj_user.getInt("id_usuario"));
        }
        return cargos_disponiveis;
    }
    
    public static String getHierarquiaUserValueString(String VALOR){
        JSONArray cargos_disponiveis = getHierarquiaUser();
        String UltimoValor = "";
        for(int i = 0; i < cargos_disponiveis.length(); i++){
            JSONObject obj = cargos_disponiveis.getJSONObject(i);
            UltimoValor = obj.getString(VALOR);
        }
        return UltimoValor;
    }
    
    public static int getHierarquiaUserValueInt(String VALOR){
        JSONArray cargos_disponiveis = getHierarquiaUser();
        int UltimoValor = 0;
        for(int i = 0; i < cargos_disponiveis.length(); i++){
            JSONObject obj = cargos_disponiveis.getJSONObject(i);
            UltimoValor = obj.getInt(VALOR);
        }
        return UltimoValor;
    }
    
    
    public static JSONArray getHierarquiaPorUserID(int USERID){
        JSONArray cargos_disponiveis = new JSONArray();
        if(!InicializadorMain.ModoOffline){
            cargos_disponiveis = getHierarquiaPorID(USERID);
        }
        return cargos_disponiveis;
    }
    
    public static String getHierarquiaPorUserIDValueString(int USERID, String VALOR){
        JSONArray cargos_disponiveis = getHierarquiaPorUserID(USERID);
        String UltimoValor = "";
        for(int i = 0; i < cargos_disponiveis.length(); i++){
            JSONObject obj = cargos_disponiveis.getJSONObject(i);
            UltimoValor = obj.getString(VALOR);
        }
        return UltimoValor;
    }
    
    public static int getHierarquiaPorUserIDValueInt(int USERID, String VALOR){
        JSONArray cargos_disponiveis = getHierarquiaPorUserID(USERID);
        int UltimoValor = 0;
        for(int i = 0; i < cargos_disponiveis.length(); i++){
            JSONObject obj = cargos_disponiveis.getJSONObject(i);
            UltimoValor = obj.getInt(VALOR);
        }
        return UltimoValor;
    }
    
    public static boolean getPermUser(String perm){
        JSONObject obj_cargo = getCargoObjUser();
        return obj_cargo.getInt(perm) == 1;
    }
    
    public static JSONArray getConfig(String ConfigName){
        JSONArray cargos_disponiveis = new JSONArray();
        if(!InicializadorMain.ModoOffline){
            for(int i = 0; i < InicializadorMain.configsDBarray.length(); i++){
                JSONObject obj = InicializadorMain.configsDBarray.getJSONObject(i);
                if(ConfigName.equals(obj.getString("nome"))){
                    return new JSONArray(obj.getString("valor"));
                }
            }
        }
        return cargos_disponiveis;
    }
}
