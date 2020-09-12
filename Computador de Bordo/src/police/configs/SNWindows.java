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
        {4,25},
        {6,40},
        {7,60},
        {10,120},
        {30,200}
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
            while (resulteSet.next()) {
                JSONObject getTemporario2 = new JSONObject();
                getTemporario2.put("id", resulteSet.getInt("id"));
                getTemporario2.put("server_id", resulteSet.getInt("server_id"));
                getTemporario2.put("serial", resulteSet.getString("serial"));
                getTemporario2.put("nivel", resulteSet.getInt("nivel"));
                getTemporario2.put("data", resulteSet.getString("data"));
                getTemporario2.put("data_ativou", resulteSet.getString("data_ativou"));
                getTemporario2.put("comprovante_id", resulteSet.getInt("comprovante_id"));
                getTemporario2.put("expira", resulteSet.getInt("expira"));
                getTemporario2.put("discord_id", resulteSet.getString("discord_id"));
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
}
