/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JTextField;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import org.json.JSONArray;
import org.json.JSONObject;
import police.configs.ConexaoDB;
import police.configs.Config;
import police.configs.Crimes;
import police.configs.Usuario;


/**
 *
 * @author John
 * 
 * 
 * CamposTexto
 * BtMais
 * BtMenos
 * BtZerar
 * QntCampo
 * ValorMulta
 * NomeCrime
 */
public class Procurado extends javax.swing.JFrame {

    int tamanhototal=59;
    /**
     * Creates new form Prender
     */
    
    JSONArray usuariosDBarray = new JSONArray();
    JSONArray discordDBarray = new JSONArray();
    JSONArray prisoesDBarray = new JSONArray();
    JSONArray blacklistDBarray = new JSONArray();
    JSONObject prenderDBarray = new JSONObject();
    JSONArray prisaorega = new JSONArray();
    
    int PassaPreso = 0;
    int meserpresor=0;
    int multapresor=0;
    boolean feitoprisao = false;
    String Protocolor = "Nenhum";
    boolean reuprimario=true;
    VerPrisoes verprisoes = new VerPrisoes();
    InicializadorMain policia = new InicializadorMain();
    JDA jda;
    
    public Procurado() {
        
        initComponents();
        //copiado.setVisible(false);
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagens/lspd-small.png")));
        
        AtualizarCrimes();
        id.requestFocus();
        //Container frame = this;
        getContentPane().setBackground(new java.awt.Color(13, 32, 64));
        
        
        jPanel1.setBackground(new java.awt.Color(13, 32, 64));
        jPanel2.setBackground(new java.awt.Color(13, 32, 64));
        jPanel3.setBackground(new java.awt.Color(13, 32, 64));
        jPanel4.setBackground(new java.awt.Color(13, 32, 64));
        jPanel6.setBackground(new java.awt.Color(13, 32, 64));
        jPanel7.setBackground(new java.awt.Color(13, 32, 64));
        jPanel9.setBackground(new java.awt.Color(13, 32, 64));
        jPanel10.setBackground(new java.awt.Color(13, 32, 64));
        
        
        jda = policia.getJDA();
        usuariosDBarray = policia.AttDBUsuarios();
        discordDBarray = policia.AttDBDiscord();
        
        Usuario usuario = new Usuario();
        JSONObject usua = new JSONObject(usuario.getDados());
        
        AttBancos();
        
        for (int i=1; i<=tamanhototal; i++) {
            BtMais(i).setBackground(new java.awt.Color(255, 255, 255));
            BtMenos(i).setBackground(new java.awt.Color(255, 255, 255));
            BtZerar(i).setBackground(new java.awt.Color(255, 255, 255));
        }
        pack();
    }
    
    public void AttBancos(){
        //AttDBUsuarios(); //Não há necessidade de atualização, pois está puxando direto da base
        AttDBPrisoes();
    }
    public void AttDBPrisoes(){
        Usuario usuarios = new Usuario();
        prisoesDBarray = usuarios.AttDBPrisoes();
        blacklistDBarray = usuarios.AttBlackList();
    }
    public void AttInfo(){
        feitoprisao=false;
        ResetarCampos(false);
        AtualizarCrimes();
        String tpassa = id.getText();
        if(tpassa.length() <= 0)return;
        boolean achou=false;
        for(int i = 0; i < usuariosDBarray.length(); i++){
            JSONObject o = usuariosDBarray.getJSONObject(i);
            int paser = Integer.parseInt(tpassa);

            if(paser==o.getInt("id_usuario")){
                //registrar.setEnabled(true);
                achou=true;
                int pass = o.getInt("id_usuario");
                PassaPreso=o.getInt("id_usuario");
                //String discord = o.getString("discord");
                String nome = o.getString("nome")+" "+o.getString("sobrenome");
                if(" ".equals(nome))nome="Sem Registro";
                String passaporte = o.getString("id_usuario");
                String identidade = o.getString("registration");
                //System.out.println("usuariosDBarray: "+o.toString()+" ?°/ ");

                prenderDBarray = new JSONObject();
                //prenderDBarray=usuariosDBarray;
                prenderDBarray.put("nome", nome);
                prenderDBarray.put("passaporte", passaporte);
                prenderDBarray.put("identidade", identidade);
                
                Usuario usuario = new Usuario();
                JSONObject usua = new JSONObject(usuario.getDados());
                prenderDBarray.put("id_prendeu", usua.getString("id_usuario"));
                //prenderDBarray.put("id_prendeu", "159");
                /*
                boolean checker=false;
                long dater=0;
                long datar=0;
                for(int i2 = 0; i2 < hierarquiaDBarray.length(); i2++){
                    JSONObject ohier = hierarquiaDBarray.getJSONObject(i2);
                    //System.out.println("hierarquiaDBarray: "+ohier.toString()+" // ");
                    if(pass==ohier.getInt("id_usuario")){
                        pter = ohier.getInt("cargo");
                        pter2= ohier.getInt("cargo_antigo");
                        datar= ohier.getLong("data");
                        if(!checker){
                            checker=true;
                            dater=ohier.getLong("data");
                        }
                    }
                }

                String patente = usuarios.Patentes(pter);
                String patenteo = usuarios.Patentes(pter2);
                PatenteP=pter;
                PatentePA=PatenteP;

                PrettyTime p = new PrettyTime();
                long ultimologin = o.getLong("ultimologin");
                String ErOq;
                if(ultimologin < 10000){
                    ErOq= "Nunca";
                }else{
                    ErOq= p.format(new Date(ultimologin));
                }

                jLabel22.setText(ErOq);
                */
                //usuarios.Patentes();
                int vzespreso = 0;
                int vzesmulta = 0;
                for(int i2 = 0; i2 < prisoesDBarray.length(); i2++){
                    JSONObject ohier = prisoesDBarray.getJSONObject(i2);
                    //System.out.println("hierarquiaDBarray: "+ohier.toString()+" // ");
                    int meseer = ohier.getInt("meses");
                    int multaar = ohier.getInt("multas");
                    if(pass==ohier.getInt("id_usuario")){
                        if("".equals(ohier.getString("limpo"))){
                            if(meseer > 0){
                                vzespreso++;
                            }
                            if(multaar > 0 && meseer <= 0){
                                vzesmulta++;
                            }
                        }
                    }
                }
                jLabel16.setText(vzespreso+"");
                jLabel22.setText(vzesmulta+"");
                if(vzespreso>0){
                    jLabel14.setText("NÃO");
                    jLabel14.setForeground(new java.awt.Color(255,51,51));
                    check1.setSelected(false);
                    reuprimario=false;
                    verpreso.setEnabled(true);
                }else{
                    jLabel14.setText("SIM");
                    jLabel14.setForeground(new java.awt.Color(51,153,0));
                    check1.setSelected(true);
                    reuprimario=true;
                    verpreso.setEnabled(false);
                }

                jLabel13.setText(nome);
                jLabel20.setText("");
                jLabel20.setForeground(new java.awt.Color(255,255,255));
                String fbir = "";
                if(o.getInt("lspd") == 1){
                    jLabel20.setText("LSPD");
                    jLabel20.setForeground(new java.awt.Color(90, 113, 216));
                    fbir="(FBI)";
                }else if(o.getInt("lspd") == 2){
                    jLabel20.setText("FBI");
                    jLabel20.setForeground(new java.awt.Color(56, 73, 148));
                }
            }

        }
        if(!achou){
            jLabel13.setText("Individuo não encontrado");
            PassaPreso=0;
            jLabel14.setText("NÃO");
            check1.setSelected(false);
            jLabel14.setForeground(new java.awt.Color(255,51,51));
            jLabel16.setText("0");
            registrar.setEnabled(false);
            verpreso.setEnabled(false);
        }

        /*
        ResultSet resulteSet = null;
        resulteSet = conexao.readDataBase2("user_identities", "id", Pasw);
        long datar=System.currentTimeMillis();
        boolean conte=false;
        boolean lspde=false;
        String nomw="Sujeito";
        String sobw="Indigente";

        PassCida=Integer.parseInt(txtPassaporte.getText());
        try {
            while (resulteSet.next()) {
                conte=true;
                nomw=resulteSet.getString("nome");
                sobw=resulteSet.getString("sobrenome");

                if(resulteSet.getInt("lspd") == 1)lspde=true;

            }
        } catch (SQLException ex) {
            Logger.getLogger(Corporacao.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!conte){
            int result = JOptionPane.showConfirmDialog(null, panel, "Adicionar Cidadão", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                //return new MyData(text0.getText(), text1.getText());
                System.out.print("text0: "+text0.getText()+" / text1: "+text1.getText());

                if(text1.getText().length() < 4){
                    JOptionPane.showMessageDialog(null, "Nome "+text1.getText()+" é muito pequeno!", "Erro no nome do cidadão", JOptionPane.PLAIN_MESSAGE);
                }else
                if(text2.getText().length() < 4){
                    JOptionPane.showMessageDialog(null, "Discord "+text2.getText()+" é muito pequeno!", "Erro no discord do cidadão", JOptionPane.PLAIN_MESSAGE);
                }else{
                    Bpatentes.setEnabled(true);
                    AddCorp.setEnabled(true);

                    PassaAddU=text0.getText();
                    nomw=text1.getText();
                    sobw=text2.getText();
                    NomeU=text1.getText()+" "+text2.getText();
                    DiscordAddU=text2.getText();
                }
            }
        }else{
            if(lspde){
                JOptionPane.showMessageDialog(null, nomw+" já está como LSPD!", "Opa, me parece que você já é da LSPD?", JOptionPane.PLAIN_MESSAGE);
            }else{
                Bpatentes.setEnabled(true);
                AddCorp.setEnabled(true);
            }
        }
        nomeid.setText(nomw+" "+sobw+" ("+PassCida+")");
        //jLabel3.setText("Adicionar: "+nomw);
        */
    }
    
    public JTextField CamposTexto(int valor){
        switch (valor) {
            case 1:
                return numero1;
            case 2:
                return numero2;
            case 3:
                return numero3;
            case 4:
                return numero4;
            case 5:
                return numero5;
            case 6:
                return numero6;
            case 7:
                return numero7;
            case 8:
                return numero8;
            case 9:
                return numero9;
            case 10:
                return numero10;
                
            // BÁSICO
            case 21:
                return numero21;
            case 22:
                return numero22;
            case 23:
                return numero23;
            case 24:
                return numero24;
            case 25:
                return numero25;
                
            //ASSALTOS
            case 26:
                return numero26;
            case 27:
                return numero27;
            case 28:
                return numero28;
            case 29:
                return numero29;
            case 30:
                return numero30;
                
            //CRIMES NIVEL 1
            case 31:
                return numero31;
            case 32:
                return numero32;
            case 33:
                return numero33;
            case 34:
                return numero34;
            case 35:
                return numero35;
            case 36:
                return numero36;
            case 37:
                return numero37;
            case 38:
                return numero38;
            case 39:
                return numero39;
            case 40:
                return numero40;
            case 41:
                return numero41;
            case 42:
                return numero42;
                
            //CRIMES NIVEL 2
            case 43:
                return numero43;
            case 44:
                return numero44;
            case 45:
                return numero45;
            case 46:
                return numero46;
            case 47:
                return numero47;
            case 48:
                return numero48;
            case 49:
                return numero49;
            case 50:
                return numero50;
            case 51:
                return numero51;
            case 52:
                return numero52;
                
            default:
                return null;
        }
    }
    
    public JButton BtMais(int valor){
        switch (valor) {
            case 1:
                return mais1;
            case 2:
                return mais2;
            case 3:
                return mais3;
            case 4:
                return mais4;
            case 5:
                return mais5;
            case 6:
                return mais6;
            case 7:
                return mais7;
            case 8:
                return mais8;
            case 9:
                return mais9;
            case 10:
                return mais10;
                
            /*case 11:
                return mais11;
            case 12:
                return mais12;
            case 13:
                return mais13;
            case 14:
                return mais14;
            case 15:
                return mais15;
            case 16:
                return mais16;
            case 17:
                return mais17;
            case 18:
                return mais18;
            case 19:
                return mais19;
            case 20:
                return mais20;*/ 
            case 21:
                return mais21;
            case 22:
                return mais22;
            case 23:
                return mais23;
            case 24:
                return mais24;
            case 25:
                return mais25;
            case 26:
                return mais26;
            case 27:
                return mais27;
            case 28:
                return mais28;
            case 29:
                return mais29;
            case 30:
                return mais30;    
            case 31:
                return mais31;
            case 32:
                return mais32;
            case 33:
                return mais33;
            case 34:
                return mais34;
            case 35:
                return mais32;
            case 36:
                return mais36;
            case 37:
                return mais37;
            case 38:
                return mais38;
            case 39:
                return mais39;
            case 40:
                return mais40;    
            /*case 41:
                return mais41;*/
            case 42:
                return mais42;
            case 43:
                return mais43;
            case 44:
                return mais44;
            case 45:
                return mais45;
            case 46:
                return mais46;
            case 47:
                return mais47;
            case 48:
                return mais48;
            case 49:
                return mais49;
            case 50:
                return mais50;    
            case 51:
                return mais51;
            case 52:
                return mais52;
            default:
                return mais1;
        }
    }
    
    public JButton BtMenos(int valor){
        switch (valor) {
            case 1:
                return menos1;
            case 2:
                return menos2;
            case 3:
                return menos3;
            case 4:
                return menos4;
            case 5:
                return menos5;
            case 6:
                return menos6;
            case 7:
                return menos7;
            case 8:
                return menos8;
            case 9:
                return menos9;
            case 10:
                return menos10;
                
            /*case 11:
                return menos11;
            case 12:
                return menos12;
            case 13:
                return menos13;
            case 14:
                return menos14;
            case 15:
                return menos15;
            case 16:
                return menos16;
            case 17:
                return menos17;
            case 18:
                return menos18;
            case 19:
                return menos19;
                
            case 20:
                return menos20;*/
            case 21:
                return menos21;
            case 22:
                return menos22;
            case 23:
                return menos23;
            case 24:
                return menos24;
            case 25:
                return menos25;
            case 26:
                return menos26;
            case 27:
                return menos27;
            case 28:
                return menos28;
            case 29:
                return menos29;
            case 30:
                return menos30;
            case 31:
                return menos31;
            case 32:
                return menos32;
            case 33:
                return menos33;
            case 34:
                return menos34;
            case 35:
                return menos35;
            case 36:
                return menos36;
            case 37:
                return menos37;
            case 38:
                return menos38;
            case 39:
                return menos39;
            case 40:
                return menos40;
            /*case 41:
                return menos41;*/
            case 42:
                return menos42;
            case 43:
                return menos43;
            case 44:
                return menos44;
            case 45:
                return menos45;
            case 46:
                return menos46;
            case 47:
                return menos47;
            case 48:
                return menos48;
            case 49:
                return menos49;
            case 50:
                return menos50;
            case 51:
                return menos51;
            case 52:
                return menos52;
            default:
                return menos1;
        }
    }
    
    public JButton BtZerar(int valor){
        switch (valor) {
            case 1:
                return zerar1;
            case 2:
                return zerar2;
            case 3:
                return zerar3;
            case 4:
                return zerar4;
            case 5:
                return zerar5;
            case 6:
                return zerar6;
            case 7:
                return zerar7;
            case 8:
                return zerar8;
            case 9:
                return zerar9;
            case 10:
                return zerar10;
                
            case 21:
                return zerar21;
            case 22:
                return zerar22;
            case 23:
                return zerar23;
            case 24:
                return zerar24;
            case 25:
                return zerar25;
            case 26:
                return zerar26;
            case 27:
                return zerar27;
            case 28:
                return zerar28;
            case 29:
                return zerar29;
            case 30:
                return zerar30;    
            case 31:
                return zerar31;
            case 32:
                return zerar32;
            case 33:
                return zerar33;
            case 34:
                return zerar34;
            case 35:
                return zerar35;
            case 36:
                return zerar36;
            case 37:
                return zerar37;
            case 38:
                return zerar38;
            case 39:
                return zerar39;
            case 40:
                return zerar40;
                    
            case 41:
                return zerar41;
            case 42:
                return zerar42;
            case 43:
                return zerar43;
            case 44:
                return zerar44;
            case 45:
                return zerar45;
            case 46:
                return zerar46;
            case 47:
                return zerar47;
            case 48:
                return zerar48;
            case 49:
                return zerar49;
            case 50:
                return zerar50;    
            case 51:
                return zerar51;
            case 52:
                return zerar52;
                
            default:
                return zerar1;
        }
    }
    
    public int QntCampo(int valor){
        switch (valor) {
            case 1:
                return 99;
            case 4:
                return 99;
                
                
            case 11:
                return 9999999;    
            case 12:
                return 9999999;    
            case 13:
                return 9999999;    
            case 14:
                return 9999999;    
            case 15:
                return 9999999;    
            case 16:
                return 9999999;    
            case 17:
                return 9999999;    
            case 18:
                return 9999999;    
            case 19:
                return 9999999;   
            case 20:
                return 9999999;  
                
            /*case 24:
                return 99;   
            case 25:
                return 99;  
                
            case 26:
                return 9;  
            case 27:
                return 9;  
            case 28:
                return 9;  
            case 29:
                return 9;  
            case 30:
                return 9;*/
                
                
            case 41:
                return 9999999;
                
            case 43:
                return 9;
            case 44:
                return 9;  
            case 45:
                return 9;  
            case 46:
                return 9;  
            case 47:
                return 9;  
            case 48:
                return 9;  
            case 49:
                return 9; 
            case 50:
                return 9;  
            case 51:
                return 9;  
            case 52:
                return 9;     
            
            default:
                return 1;
        }
    }
    
    public int ValorVezes(int valor){
        switch (valor) {
            case 11:
                return 10;
            case 12:
                return 10;
            case 13:
                return 20;
            case 14:
                return 20;
            case 15:
                return 5;
            case 17:
                return 1;
            case 18:
                return 3;
            case 19:
                return 1;
            case 41:
                return 10;
                
            /*case 43:
                return 20;
            case 44:
                return 15;
            case 45:
                return 10;
            case 46:
                return 10;
            case 47:
                return 25;
            case 48:
                return 15;
            case 49:
                return 15;
            case 50:
                return 25;    
            case 51:
                return 15;
            case 52:
                return 20; */
                
            default:
                return 0;
        }
    }
    public int QntVezes(int valor){
        switch (valor) {
            case 17:
                return 2000;
            case 41:
                return 10000;
            default:
                return 1;
        }
    }
        
    
    public int BotaoGeral(String opcao, int campo){ //JTextField campo    
        String TextoDoCampo = CamposTexto(campo).getText();
        int valorc = Integer.parseInt(TextoDoCampo);
        int totalv = valorc;
        if(null == opcao){
            totalv=0;
        }else switch (opcao) {
            case "add":
                totalv++;
                break;
            case "rem":
                totalv--;
                break;
            default:
                totalv=0;
                break;
        }
        if(feitoprisao){
            ResetarCampos(true);
            feitoprisao=false;
        }
        
        //System.out.println("Teste Funcionou");
        CamposTexto(campo).setText(totalv+"");
        AtualizarBotoes(totalv, QntCampo(campo), campo);
        return 1;
    }
    
    
    public int AtualizarBotoes(int totalv, int total, int valorcampo){
        if(totalv >= total){
            BtMais(valorcampo).setEnabled(false);
            BtMenos(valorcampo).setEnabled(true);
            BtZerar(valorcampo).setEnabled(true);
        }else if(totalv <= 0){
            BtMais(valorcampo).setEnabled(true);
            BtMenos(valorcampo).setEnabled(false);
            BtZerar(valorcampo).setEnabled(false);
        }else{
            BtMais(valorcampo).setEnabled(true);
            BtMenos(valorcampo).setEnabled(true);
            BtZerar(valorcampo).setEnabled(true);
        }
        System.out.printf("valorcampo: "+valorcampo);
        AtualizarCrimes();
        //copiado.setVisible(false);
        return 1;
    }
    
    public boolean AtualizarCrimes(){
        int maxmeses = 50; //Máximo de meses preso
        
        String CrimesDB = "";
        String CrimesTxt = "";
        int crimestotais = 0;
        int valormulta = 0;
        int valormeses = 0;
        //CONFIGS
        Crimes crimesconfig;
        crimesconfig = new Crimes();
        //String NomeCrimes = crimesconfig.NomeCrime(i);
        
        
        copiar.setText("COPIAR");
        
        boolean desacato=false;
        boolean resistenciaprisao=false;
        
        
        prisaorega = new JSONArray();
        for (int i=1; i<=tamanhototal; i++) {
            //Crimes cometidos
            String TextoDoCampo = CamposTexto(i).getText();
            
            String NomeCrimes = crimesconfig.NomeCrime(i);
            int ValordaMulta  = crimesconfig.ValorMulta(i);
            int ValorMeses = crimesconfig.ValorMeses(i);
                    
            int valorc;
            
            if(TextoDoCampo.length() <= 0){
                valorc=0;
            }else if(TextoDoCampo.length() >= 8){
                valorc=9999999;
                CamposTexto(i).setText("9999999");
            }else{
                valorc = Integer.parseInt(TextoDoCampo);
            }
            String mesmulta = "";
            if(valorc > 0){
                if(crimestotais>0){CrimesTxt = CrimesTxt+" +"; CrimesDB += " //";}
                if(i==31)resistenciaprisao=true;
                if(i==43)desacato=true;
                
                int ValorVezes = ValorVezes(i);
                int QntVezes = QntVezes(i);
                
                int mesis = 0;
                int multar = 0;
                if(i == 16){ // SISTEMA DE DROGAS
                    if(valorc < 6){
                        NomeCrimes= "Usuário de Drogas"; 
                        //CrimesTxt = CrimesTxt+" Usuário de Drogas"; 
                        valormulta = valormulta+    500;
                        multar+=500;
                        
                    }else if(valorc >= 6 && valorc <= 10){
                        NomeCrimes = "Posse de Drogas"; 
                        valormeses += 10;
                        valormulta += 1000;
                        multar+=1000;
                        mesis+=10;
                    }else if(valorc > 10){
                        NomeCrimes = "Tráfico de Drogas"; 
                        valormeses += 15;
                        valormulta += 2500;
                        ValorVezes =    2;
                        QntVezes   =    10;
                        multar+=2500;
                        mesis+=15;
                    }
                }else if(i == 17){ // SISTEMA DE DROGAS
                    NomeCrimes = "Posse de Dinheiro Sujo"; 
                    if(valorc > 2000){
                       
                        int contage=valorc/1000;
                        
                        contage=contage/2;
                        //valorc
                        
                        valormeses += contage;
                        valormulta += 2500;
                        ValorVezes =    0;
                        QntVezes   =    0;
                        multar+=2500;
                        mesis+=contage;
                        
                        System.out.printf("contage: "+contage);
                    }
                }else if(i == 20){ // SISTEMA DE DROGAS
                    NomeCrimes = "Posse de Munições"; 
                    
                        int contage=valorc*50;
                        //valorc
                        valormulta += contage;
                        ValorVezes =    0;
                        QntVezes   =    0;
                        multar+=contage;
                        //System.out.printf("contage: "+contage);
                }else if(i == 41){ // SISTEMA DE DROGAS
                    if(valorc < 10000){
                        ValorVezes =    0;
                        QntVezes   =    0;
                    }
                }else{
                    valormulta += ValordaMulta*valorc; //*valorc);
                    valormeses += ValorMeses*valorc;//*valorc;
                    multar+=ValordaMulta*valorc;
                    mesis+=ValorMeses*valorc;
                    
                }
                CrimesTxt += " "+NomeCrimes;
                mesmulta= " "+NomeCrimes;
                crimestotais++;
                String qntiar="";
                if(i==17 || i==41){
                    qntiar = "($"+String.format("%,d", valorc)+")";
                }else{
                    if(valorc>1)qntiar = "(x"+valorc+")";
                }
                CrimesTxt+=qntiar;
                mesmulta+=" "+qntiar;
                int valorconta=0;
                if(ValorVezes > 0){
                    if(ValorVezes > QntVezes){
                        valorconta = ValorVezes*(valorc*QntVezes);
                    }else if(ValorVezes == QntVezes){
                        valorconta = valorc;
                    }else if(ValorVezes < QntVezes){
                        valorconta = (valorc*ValorVezes)/QntVezes;
                    }
                    valormeses += valorconta;
                    mesis+=valorconta;
                }
                
                mesmulta+=" [MESES: "+mesis+" & MULTA: $"+String.format("%,d", multar)+"]";
                CrimesDB+=mesmulta;
                JSONObject prisaoregis = new JSONObject();
                prisaoregis.put("crime", i);
                prisaoregis.put("meses", mesis);
                prisaoregis.put("multar", mesis);
                prisaoregis.put("quantidade", valorc);
                
                prisaorega.put(prisaoregis);
            }
            //CamposTexto(i).setText(valorc+"");
        }
        
        
        String Beneficios=" //";
        int descontor=0;
        if(desacato){
            int contadesa = valormeses/2;
            
            valormeses+=contadesa;
            check1.setSelected(false);
            Beneficios+=" // Desacato [REVOGADO TODAS AS REDUÇÕES +50%]";
            descontor=contadesa;
        }else{
            if(reuprimario)check1.setSelected(true);
        }
        
        //CONTA DOS BENEFICIOS
        int valorpmeses = -valormeses;
        
        if(check1.isSelected()){descontor+=-((valormeses*30)/100);Beneficios+=" // Réu Primario [-30%]";             }//-30%
        if(check2.isSelected()){descontor+=((valormeses*30)/100);Beneficios+=" // Réu Reincidente [+30%]";          }//+30%
        if(resistenciaprisao){  descontor+=((valormeses*15)/100);Beneficios+=" // Resistência à prisão [+15%]";     } //-10%
        if(descontor < valorpmeses)descontor=valorpmeses;
        String MesesFormat = (descontor == 1) ? "mês" : "meses";
        String Benefi = (descontor >= 0) ? "Aumento" : "Desconto";
        
        if(descontor!=0)Beneficios+=" // "+Benefi+" de "+descontor+" "+MesesFormat;
        
        valormeses+=descontor;
        if(valormeses < 0)valormeses=0;
        
        //desacato
        
        //resistencia a prisao
        if(valormeses > 0)CrimesDB+=Beneficios;
        //System.out.printf("Crimes: "+CrimesTxt);
        
        
        //LIMITE
        String maximor = (valormeses >= maxmeses) ? " ["+maxmeses+" Máx]" : "";
        maximor=valormeses+""+maximor;
        MesesFormat = (valormeses == 1) ? "mês" : "meses";
        String MultaFormat;
        if(valormeses>maxmeses)valormeses=maxmeses;
        String addmesese = "";
        if(valormeses > 0 && valormeses< 10){
            int contafazer = (valormeses*1000)/4;
            MultaFormat="$ "+String.format("%,d", contafazer);
            addmesese=" // [REVERTIDO: "+valormeses+" "+MesesFormat+" em "+MultaFormat+" de multas]";
            valormulta+=contafazer;
            
            valormeses=0;
        }
        
        MultaFormat="$ "+String.format("%,d", valormulta);
        multatotal.setText(MultaFormat);
        MesesFormat = (valormeses == 1) ? "mês" : "meses";
        MesesFormat = valormeses+" "+MesesFormat;
        mesestotal.setText(MesesFormat);
        
        registrar.setEnabled(false);
        
        CrimesDB+=" //// Meses Total: "+maximor+" // Multa Total: "+MultaFormat+addmesese;
        String Crimi = "Nenhuma";
        if(CrimesTxt.length() <= 0){
            crimes.setText("Nenhum crime cometido !");
            CrimesTxt="Nenhuma";
        }else{
            if(valormeses > 0 || valormulta > 0){
                Crimi=CrimesDB;
                if(PassaPreso!=0){
                    if(valormeses > 0){
                        registrar.setText("GUARDAR REGISTRO DE PRISÃO");
                    }else{
                        registrar.setText("GUARDAR REGISTRO DE MULTA");
                    }
                    registrar.setEnabled(true);
                }
            }
        }
        crimes.setText(Crimi);
        
        String Nome =   jLabel13.getText();
        String ID   =   PassaPreso+"";
        if(Nome.length() <= 0){ Nome="Não informado";}
        if(ID.length() <= 0){ ID="ID_JOGADOR";}
        
        String Fzeroq;
        String Comandos="Não se esqueça do /apreender e tirar uma foto !";
        boolean preso = false;
        boolean multa = false;
        if(valormeses>0)preso=true;
        if(valormulta>0)multa=true;
        
        meserpresor=valormeses;
        multapresor=valormulta;
        
        if(preso && multa){
            Fzeroq = "Prender por "+MesesFormat+" e Multar em "+MultaFormat;
            Comandos="/prender "+ID+" "+valormeses+" e /multar -> "+ID+" -> "+valormulta;
        }else if(preso){
            Fzeroq = "Somente Prender por "+MesesFormat;
            Comandos="/prender "+ID+" "+valormeses;
        }else if(multa){ //   ||
            Fzeroq = "Somente Multar em "+MultaFormat;
            Comandos="/multar -> "+ID+" -> "+valormulta;
        }else{
            Fzeroq = "Liberar o Cidadão";
        }
        fazeroq.setText(Fzeroq);
        comandos.setText(Comandos);
        
        String getMax = (valormeses >= maxmeses) ? "[Máx]" : "";
        String TextDiscord = "```\n"
                + "Nome: "+Nome+"\n"
                + "Passaporte: "+ID+"\n"
                + "Protocolo: "+Protocolor+"\n"
                + "Contravenções: "+CrimesTxt+"\n";
        if(multa) TextDiscord += "Multa Total: "+MultaFormat+"\n";
        if(preso) TextDiscord += "Pena Total: "+MesesFormat+" "+getMax+""+Beneficios+"\n";
        TextDiscord += "```";
        
        discord.setText(TextDiscord);
        contrav.setText(CrimesTxt);
        if("".equals(jLabel13.getText())){
            setTitle("CRIMES E PRISAO LSPD"); 
        }else{
            //setTitle("CRIMES E PRISAO DE "+nome.getText().toUpperCase());
        }
        
        return true;
    }
    
    public boolean ResetarCampos(boolean resettudo){
        for (int i=1; i<=tamanhototal; i++) {
            //Crimes cometidos
            CamposTexto(i).setText("0");
            BtMais(i).setEnabled(true);
            BtMenos(i).setEnabled(false);
            BtZerar(i).setEnabled(false);
        }
        jLabel13.setText("COLOQUE O ID");
        jLabel14.setText("S/ INFO");
        jLabel16.setText("S/ INFO");
        jLabel22.setText("S/ INFO");
        copiar.setEnabled(false);
        copiar1.setEnabled(false);
        check1.setSelected(false);
        
        if(resettudo){
            PassaPreso=0;
            id.setText("");
            Protocolor="";
        }
        
        //Protocolor="";
        return true;
    }
    
    public boolean SomenteNumeros(KeyEvent evt){
        char c = evt.getKeyChar();
        if (!((c >= '0') && (c <= '9') ||
            (c == KeyEvent.VK_BACK_SPACE) ||
            (c == KeyEvent.VK_DELETE) ||
            (c == KeyEvent.VK_LEFT) ||
            (c == KeyEvent.VK_RIGHT)) ) {
            //getToolkit().beep();
            evt.consume();
        }
        return true;
    }
    
    public void MensagemDiscord(JSONObject DiscordTag){
        
        int passaporte=DiscordTag.getInt("passaporte");
        String motivo=DiscordTag.getString("motivo");
        int id_prendeu=DiscordTag.getInt("id_prendeu");
        String protocolo=DiscordTag.getString("protocolo");
        
        int meses=DiscordTag.getInt("meses");
        int multas=DiscordTag.getInt("multas");
        
        String justificado=DiscordTag.getString("justificado");
        String contravencoes=DiscordTag.getString("contravencoes");
        String discord=DiscordTag.getString("discord");
        
        
        String nome_preso="Sem Registro";
        String registro="Sem Registro";
        int idade=0;
        for(int i = 0; i < usuariosDBarray.length(); i++){
            JSONObject oPL = usuariosDBarray.getJSONObject(i);
            if(passaporte==oPL.getInt("id_usuario")){
                nome_preso = oPL.getString("nome")+" "+oPL.getString("sobrenome");
                registro = oPL.getString("registration");
                idade = oPL.getInt("age");
            }
        }
        String ApreendiT="";
        String Apree="";
        String ApreeTXT="";
        int OqH = 0;
        if(meses > 0){
            ApreendiT="foi preso pela";
            Apree="pena";
            ApreeTXT=":regional_indicator_p: :regional_indicator_r: :regional_indicator_i: :regional_indicator_s: :regional_indicator_a: :regional_indicator_o:";
            OqH=1;
        }else if(multas > 0){
            ApreendiT="recebeu uma multa da";
            Apree="multa";
            ApreeTXT=":regional_indicator_m: :regional_indicator_u: :regional_indicator_l: :regional_indicator_t: :regional_indicator_a:";
            OqH=2;
        }
        if(OqH != 0){
            String multars = String.format("%,d", multas);
            User usuar = jda.getUserById(discord);
            Config config = new Config();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Newlands LSPD - Registro "+protocolo, null);
            eb.setColor(new Color(0x0D2040));
            eb.setDescription(":rotating_light: Você "+ApreendiT+" **polícia de NewLands**!");
            

            eb.addField(":regional_indicator_i: :regional_indicator_n: :regional_indicator_f: :regional_indicator_o:   :regional_indicator_c: :regional_indicator_i: :regional_indicator_d: :regional_indicator_a: :regional_indicator_d: :regional_indicator_a: :regional_indicator_o:", "Você pode recursar a "+Apree+" com um advogado pelo número do procotolo.", false);
            eb.addField("Nome Completo", ":page_facing_up: "+nome_preso, true);
            eb.addField("Registro (RG)", ":bookmark_tabs: "+registro, true);
            eb.addField("Idade", ":calendar_spiral: "+idade+" anos", true);
            eb.addField(" ", " ", false);
            
            eb.addField(ApreeTXT, contravencoes, false);
            //eb.addField("Motivo", contravencoes, false);
            eb.addField("Protocolo", ":clipboard: "+protocolo, true);
            if(meses > 0)eb.addField("Pena", ":hourglass: "+meses+" meses", true);
            eb.addField("Multa", ":dollar: $ "+multars, true);
            //eb.addField("", "", false);
            //eb.addField("\nFoi preso injustamente?", "Você pode recursar a pena com um advogado pelo número do procotolo.", false);
            //eb.addField("", "", false);
            eb.addField("Deseja parar de receber notificação?", ":speech_balloon: Mande '!notificar' para alternar as notificações.", false);
            
            eb.setFooter("Computador de Bordo [ver. "+config.versao+"]", null);
            Instant instant = Instant.from(ZonedDateTime.now());
            eb.setTimestamp(instant);
            eb.setThumbnail("https://i.imgur.com/3RuZCiB.png");
            usuar.openPrivateChannel().queue((channel) ->
            {
                channel.sendMessage(eb.build()).queue();
            });
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
      @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        discord = new java.awt.TextArea();
        check1 = new javax.swing.JCheckBox();
        check2 = new javax.swing.JCheckBox();
        contrav = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        id = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtA = new javax.swing.JLabel();
        numero1 = new javax.swing.JTextField();
        menos1 = new javax.swing.JButton();
        mais1 = new javax.swing.JButton();
        zerar1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        txtA1 = new javax.swing.JLabel();
        menos2 = new javax.swing.JButton();
        numero2 = new javax.swing.JTextField();
        mais2 = new javax.swing.JButton();
        zerar2 = new javax.swing.JButton();
        txtA2 = new javax.swing.JLabel();
        menos6 = new javax.swing.JButton();
        numero6 = new javax.swing.JTextField();
        mais6 = new javax.swing.JButton();
        zerar6 = new javax.swing.JButton();
        txtA3 = new javax.swing.JLabel();
        menos7 = new javax.swing.JButton();
        numero7 = new javax.swing.JTextField();
        mais7 = new javax.swing.JButton();
        zerar7 = new javax.swing.JButton();
        txtA4 = new javax.swing.JLabel();
        txtA5 = new javax.swing.JLabel();
        menos8 = new javax.swing.JButton();
        menos3 = new javax.swing.JButton();
        numero8 = new javax.swing.JTextField();
        numero3 = new javax.swing.JTextField();
        mais8 = new javax.swing.JButton();
        mais3 = new javax.swing.JButton();
        zerar8 = new javax.swing.JButton();
        zerar3 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        menos4 = new javax.swing.JButton();
        numero9 = new javax.swing.JTextField();
        numero4 = new javax.swing.JTextField();
        mais9 = new javax.swing.JButton();
        mais4 = new javax.swing.JButton();
        zerar9 = new javax.swing.JButton();
        zerar4 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        txtA6 = new javax.swing.JLabel();
        txtA7 = new javax.swing.JLabel();
        menos9 = new javax.swing.JButton();
        menos5 = new javax.swing.JButton();
        numero10 = new javax.swing.JTextField();
        numero5 = new javax.swing.JTextField();
        mais10 = new javax.swing.JButton();
        mais5 = new javax.swing.JButton();
        zerar10 = new javax.swing.JButton();
        zerar5 = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        txtA8 = new javax.swing.JLabel();
        txtA9 = new javax.swing.JLabel();
        menos10 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        crimes = new javax.swing.JTextField();
        multatotal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        mesestotal = new javax.swing.JTextField();
        resetar = new javax.swing.JButton();
        copiar = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        fazeroq = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        comandos = new javax.swing.JTextField();
        registrar = new javax.swing.JButton();
        copiar1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        txtA10 = new javax.swing.JLabel();
        numero21 = new javax.swing.JTextField();
        menos21 = new javax.swing.JButton();
        mais21 = new javax.swing.JButton();
        zerar21 = new javax.swing.JButton();
        jSeparator5 = new javax.swing.JSeparator();
        txtA11 = new javax.swing.JLabel();
        menos22 = new javax.swing.JButton();
        numero22 = new javax.swing.JTextField();
        mais22 = new javax.swing.JButton();
        zerar22 = new javax.swing.JButton();
        txtA15 = new javax.swing.JLabel();
        menos23 = new javax.swing.JButton();
        numero23 = new javax.swing.JTextField();
        mais23 = new javax.swing.JButton();
        zerar23 = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        numero24 = new javax.swing.JTextField();
        zerar25 = new javax.swing.JButton();
        numero25 = new javax.swing.JTextField();
        jSeparator16 = new javax.swing.JSeparator();
        zerar24 = new javax.swing.JButton();
        txtA30 = new javax.swing.JLabel();
        txtA31 = new javax.swing.JLabel();
        mais25 = new javax.swing.JButton();
        menos24 = new javax.swing.JButton();
        jSeparator17 = new javax.swing.JSeparator();
        mais24 = new javax.swing.JButton();
        menos25 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        txtA12 = new javax.swing.JLabel();
        numero26 = new javax.swing.JTextField();
        menos26 = new javax.swing.JButton();
        mais26 = new javax.swing.JButton();
        zerar26 = new javax.swing.JButton();
        jSeparator9 = new javax.swing.JSeparator();
        txtA13 = new javax.swing.JLabel();
        menos27 = new javax.swing.JButton();
        numero27 = new javax.swing.JTextField();
        mais27 = new javax.swing.JButton();
        zerar27 = new javax.swing.JButton();
        txtA16 = new javax.swing.JLabel();
        menos28 = new javax.swing.JButton();
        numero28 = new javax.swing.JTextField();
        mais28 = new javax.swing.JButton();
        zerar28 = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator12 = new javax.swing.JSeparator();
        txtA17 = new javax.swing.JLabel();
        menos29 = new javax.swing.JButton();
        numero29 = new javax.swing.JTextField();
        mais29 = new javax.swing.JButton();
        zerar29 = new javax.swing.JButton();
        txtA18 = new javax.swing.JLabel();
        menos30 = new javax.swing.JButton();
        numero30 = new javax.swing.JTextField();
        mais30 = new javax.swing.JButton();
        zerar30 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        txtA28 = new javax.swing.JLabel();
        numero31 = new javax.swing.JTextField();
        menos31 = new javax.swing.JButton();
        mais31 = new javax.swing.JButton();
        zerar31 = new javax.swing.JButton();
        jSeparator15 = new javax.swing.JSeparator();
        txtA29 = new javax.swing.JLabel();
        menos32 = new javax.swing.JButton();
        numero32 = new javax.swing.JTextField();
        mais32 = new javax.swing.JButton();
        zerar32 = new javax.swing.JButton();
        txtA32 = new javax.swing.JLabel();
        menos37 = new javax.swing.JButton();
        numero37 = new javax.swing.JTextField();
        mais37 = new javax.swing.JButton();
        zerar37 = new javax.swing.JButton();
        txtA33 = new javax.swing.JLabel();
        menos38 = new javax.swing.JButton();
        numero38 = new javax.swing.JTextField();
        mais38 = new javax.swing.JButton();
        zerar38 = new javax.swing.JButton();
        txtA34 = new javax.swing.JLabel();
        txtA35 = new javax.swing.JLabel();
        menos39 = new javax.swing.JButton();
        menos33 = new javax.swing.JButton();
        numero39 = new javax.swing.JTextField();
        numero33 = new javax.swing.JTextField();
        mais39 = new javax.swing.JButton();
        mais33 = new javax.swing.JButton();
        zerar39 = new javax.swing.JButton();
        zerar33 = new javax.swing.JButton();
        jSeparator18 = new javax.swing.JSeparator();
        menos34 = new javax.swing.JButton();
        numero40 = new javax.swing.JTextField();
        numero34 = new javax.swing.JTextField();
        mais40 = new javax.swing.JButton();
        mais34 = new javax.swing.JButton();
        zerar40 = new javax.swing.JButton();
        zerar34 = new javax.swing.JButton();
        jSeparator19 = new javax.swing.JSeparator();
        txtA36 = new javax.swing.JLabel();
        txtA37 = new javax.swing.JLabel();
        menos40 = new javax.swing.JButton();
        menos35 = new javax.swing.JButton();
        numero41 = new javax.swing.JTextField();
        numero35 = new javax.swing.JTextField();
        mais35 = new javax.swing.JButton();
        zerar41 = new javax.swing.JButton();
        zerar35 = new javax.swing.JButton();
        jSeparator20 = new javax.swing.JSeparator();
        txtA38 = new javax.swing.JLabel();
        txtA39 = new javax.swing.JLabel();
        menos36 = new javax.swing.JButton();
        numero36 = new javax.swing.JTextField();
        mais36 = new javax.swing.JButton();
        zerar36 = new javax.swing.JButton();
        jSeparator25 = new javax.swing.JSeparator();
        txtA50 = new javax.swing.JLabel();
        txtA51 = new javax.swing.JLabel();
        menos42 = new javax.swing.JButton();
        numero42 = new javax.swing.JTextField();
        mais42 = new javax.swing.JButton();
        zerar42 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        txtA40 = new javax.swing.JLabel();
        numero43 = new javax.swing.JTextField();
        menos43 = new javax.swing.JButton();
        mais43 = new javax.swing.JButton();
        zerar43 = new javax.swing.JButton();
        jSeparator21 = new javax.swing.JSeparator();
        txtA41 = new javax.swing.JLabel();
        menos44 = new javax.swing.JButton();
        numero44 = new javax.swing.JTextField();
        mais44 = new javax.swing.JButton();
        zerar44 = new javax.swing.JButton();
        txt48 = new javax.swing.JLabel();
        menos48 = new javax.swing.JButton();
        numero48 = new javax.swing.JTextField();
        mais48 = new javax.swing.JButton();
        zerar48 = new javax.swing.JButton();
        txtA43 = new javax.swing.JLabel();
        menos49 = new javax.swing.JButton();
        numero49 = new javax.swing.JTextField();
        mais49 = new javax.swing.JButton();
        zerar49 = new javax.swing.JButton();
        txtA44 = new javax.swing.JLabel();
        txtA45 = new javax.swing.JLabel();
        menos50 = new javax.swing.JButton();
        menos45 = new javax.swing.JButton();
        numero50 = new javax.swing.JTextField();
        numero45 = new javax.swing.JTextField();
        mais50 = new javax.swing.JButton();
        mais45 = new javax.swing.JButton();
        zerar50 = new javax.swing.JButton();
        zerar45 = new javax.swing.JButton();
        jSeparator22 = new javax.swing.JSeparator();
        menos46 = new javax.swing.JButton();
        numero51 = new javax.swing.JTextField();
        numero46 = new javax.swing.JTextField();
        mais51 = new javax.swing.JButton();
        mais46 = new javax.swing.JButton();
        zerar51 = new javax.swing.JButton();
        zerar46 = new javax.swing.JButton();
        jSeparator23 = new javax.swing.JSeparator();
        txtA46 = new javax.swing.JLabel();
        txtA47 = new javax.swing.JLabel();
        menos51 = new javax.swing.JButton();
        menos47 = new javax.swing.JButton();
        numero52 = new javax.swing.JTextField();
        numero47 = new javax.swing.JTextField();
        mais52 = new javax.swing.JButton();
        mais47 = new javax.swing.JButton();
        zerar52 = new javax.swing.JButton();
        zerar47 = new javax.swing.JButton();
        jSeparator24 = new javax.swing.JSeparator();
        txtA48 = new javax.swing.JLabel();
        txtA49 = new javax.swing.JLabel();
        menos52 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        verpreso = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        discord.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        discord.setEditable(false);
        discord.setText("AGUARDANDO INFORMAÇÕES");

        check1.setBackground(new java.awt.Color(50, 31, 87));
        check1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        check1.setForeground(new java.awt.Color(255, 255, 255));
        check1.setText("RÉU PRIMÁRIO");
        check1.setEnabled(false);
        check1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                check1MouseClicked(evt);
            }
        });
        check1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check1ActionPerformed(evt);
            }
        });

        check2.setBackground(new java.awt.Color(50, 31, 87));
        check2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        check2.setForeground(new java.awt.Color(255, 255, 255));
        check2.setText("RÉU REINCIDENTE");
        check2.setEnabled(false);
        check2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                check2MouseClicked(evt);
            }
        });

        contrav.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("CRIMES E PRISAO LSPD");
        setName("PRISAO LSPD ORIGIN"); // NOI18N
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CRIMES E PRISÃO LSPD");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "INDIVÍDUO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        id.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        id.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        id.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                idKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                idKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ID:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(id)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(id)
                .addGap(10, 10, 10))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtA.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA.setForeground(new java.awt.Color(255, 255, 255));
        txtA.setText("ATROPELAMENTO:");

        numero1.setEditable(false);
        numero1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero1.setText("0");
        numero1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero1ActionPerformed(evt);
            }
        });

        menos1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos1.setText("<");
        menos1.setEnabled(false);
        menos1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos1.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos1.setMaximumSize(new java.awt.Dimension(21, 21));
        menos1.setMinimumSize(new java.awt.Dimension(21, 21));
        menos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos1ActionPerformed(evt);
            }
        });

        mais1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais1.setText(">");
        mais1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais1.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais1.setMaximumSize(new java.awt.Dimension(21, 21));
        mais1.setMinimumSize(new java.awt.Dimension(21, 21));
        mais1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais1ActionPerformed(evt);
            }
        });

        zerar1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar1.setText("0");
        zerar1.setEnabled(false);
        zerar1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar1.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar1.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar1.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar1ActionPerformed(evt);
            }
        });

        txtA1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA1.setForeground(new java.awt.Color(255, 255, 255));
        txtA1.setText("ALTA VELOCIDADE:");

        menos2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos2.setText("<");
        menos2.setEnabled(false);
        menos2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos2.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos2.setMaximumSize(new java.awt.Dimension(21, 21));
        menos2.setMinimumSize(new java.awt.Dimension(21, 21));
        menos2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos2ActionPerformed(evt);
            }
        });

        numero2.setEditable(false);
        numero2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero2.setText("0");
        numero2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero2ActionPerformed(evt);
            }
        });

        mais2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais2.setText(">");
        mais2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais2.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais2.setMaximumSize(new java.awt.Dimension(21, 21));
        mais2.setMinimumSize(new java.awt.Dimension(21, 21));
        mais2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais2ActionPerformed(evt);
            }
        });

        zerar2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar2.setText("0");
        zerar2.setEnabled(false);
        zerar2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar2.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar2.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar2.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar2ActionPerformed(evt);
            }
        });

        txtA2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA2.setForeground(new java.awt.Color(255, 255, 255));
        txtA2.setText("DIRIGIR S/ CARTEIRA:");

        menos6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos6.setText("<");
        menos6.setEnabled(false);
        menos6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos6.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos6.setMaximumSize(new java.awt.Dimension(21, 21));
        menos6.setMinimumSize(new java.awt.Dimension(21, 21));
        menos6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos6ActionPerformed(evt);
            }
        });

        numero6.setEditable(false);
        numero6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero6.setText("0");
        numero6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero6ActionPerformed(evt);
            }
        });

        mais6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais6.setText(">");
        mais6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais6.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais6.setMaximumSize(new java.awt.Dimension(21, 21));
        mais6.setMinimumSize(new java.awt.Dimension(21, 21));
        mais6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais6ActionPerformed(evt);
            }
        });

        zerar6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar6.setText("0");
        zerar6.setEnabled(false);
        zerar6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar6.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar6.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar6.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar6ActionPerformed(evt);
            }
        });

        txtA3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtA3.setForeground(new java.awt.Color(255, 255, 255));
        txtA3.setText("VEICULO DANIFICADO:");

        menos7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos7.setText("<");
        menos7.setEnabled(false);
        menos7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos7.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos7.setMaximumSize(new java.awt.Dimension(21, 21));
        menos7.setMinimumSize(new java.awt.Dimension(21, 21));
        menos7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos7ActionPerformed(evt);
            }
        });

        numero7.setEditable(false);
        numero7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero7.setText("0");
        numero7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero7ActionPerformed(evt);
            }
        });

        mais7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais7.setText(">");
        mais7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais7.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais7.setMaximumSize(new java.awt.Dimension(21, 21));
        mais7.setMinimumSize(new java.awt.Dimension(21, 21));
        mais7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais7ActionPerformed(evt);
            }
        });

        zerar7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar7.setText("0");
        zerar7.setEnabled(false);
        zerar7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar7.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar7.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar7.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar7ActionPerformed(evt);
            }
        });

        txtA4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtA4.setForeground(new java.awt.Color(255, 255, 255));
        txtA4.setText("ESTAC. LOCAL PROIB.:");

        txtA5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA5.setForeground(new java.awt.Color(255, 255, 255));
        txtA5.setText("DIREÇÃO PERIGOSA:");

        menos8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos8.setText("<");
        menos8.setEnabled(false);
        menos8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos8.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos8.setMaximumSize(new java.awt.Dimension(21, 21));
        menos8.setMinimumSize(new java.awt.Dimension(21, 21));
        menos8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos8ActionPerformed(evt);
            }
        });

        menos3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos3.setText("<");
        menos3.setEnabled(false);
        menos3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos3.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos3.setMaximumSize(new java.awt.Dimension(21, 21));
        menos3.setMinimumSize(new java.awt.Dimension(21, 21));
        menos3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos3ActionPerformed(evt);
            }
        });

        numero8.setEditable(false);
        numero8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero8.setText("0");
        numero8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero8ActionPerformed(evt);
            }
        });

        numero3.setEditable(false);
        numero3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero3.setText("0");
        numero3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero3ActionPerformed(evt);
            }
        });

        mais8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais8.setText(">");
        mais8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais8.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais8.setMaximumSize(new java.awt.Dimension(21, 21));
        mais8.setMinimumSize(new java.awt.Dimension(21, 21));
        mais8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais8ActionPerformed(evt);
            }
        });

        mais3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais3.setText(">");
        mais3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais3.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais3.setMaximumSize(new java.awt.Dimension(21, 21));
        mais3.setMinimumSize(new java.awt.Dimension(21, 21));
        mais3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais3ActionPerformed(evt);
            }
        });

        zerar8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar8.setText("0");
        zerar8.setEnabled(false);
        zerar8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar8.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar8.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar8.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar8ActionPerformed(evt);
            }
        });

        zerar3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar3.setText("0");
        zerar3.setEnabled(false);
        zerar3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar3.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar3.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar3.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar3ActionPerformed(evt);
            }
        });

        menos4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos4.setText("<");
        menos4.setEnabled(false);
        menos4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos4.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos4.setMaximumSize(new java.awt.Dimension(21, 21));
        menos4.setMinimumSize(new java.awt.Dimension(21, 21));
        menos4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos4ActionPerformed(evt);
            }
        });

        numero9.setEditable(false);
        numero9.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero9.setText("0");
        numero9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero9ActionPerformed(evt);
            }
        });

        numero4.setEditable(false);
        numero4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero4.setText("0");
        numero4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero4ActionPerformed(evt);
            }
        });

        mais9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais9.setText(">");
        mais9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais9.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais9.setMaximumSize(new java.awt.Dimension(21, 21));
        mais9.setMinimumSize(new java.awt.Dimension(21, 21));
        mais9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais9ActionPerformed(evt);
            }
        });

        mais4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais4.setText(">");
        mais4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais4.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais4.setMaximumSize(new java.awt.Dimension(21, 21));
        mais4.setMinimumSize(new java.awt.Dimension(21, 21));
        mais4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais4ActionPerformed(evt);
            }
        });

        zerar9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar9.setText("0");
        zerar9.setEnabled(false);
        zerar9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar9.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar9.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar9.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar9ActionPerformed(evt);
            }
        });

        zerar4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar4.setText("0");
        zerar4.setEnabled(false);
        zerar4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar4.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar4.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar4.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar4ActionPerformed(evt);
            }
        });

        txtA6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA6.setForeground(new java.awt.Color(255, 255, 255));
        txtA6.setText("DIRIGIR EMBRIAGADO:");

        txtA7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA7.setForeground(new java.awt.Color(255, 255, 255));
        txtA7.setText("ULTRAPASSAR SINAL:");

        menos9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos9.setText("<");
        menos9.setEnabled(false);
        menos9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos9.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos9.setMaximumSize(new java.awt.Dimension(21, 21));
        menos9.setMinimumSize(new java.awt.Dimension(21, 21));
        menos9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos9ActionPerformed(evt);
            }
        });

        menos5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos5.setText("<");
        menos5.setEnabled(false);
        menos5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos5.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos5.setMaximumSize(new java.awt.Dimension(21, 21));
        menos5.setMinimumSize(new java.awt.Dimension(21, 21));
        menos5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos5ActionPerformed(evt);
            }
        });

        numero10.setEditable(false);
        numero10.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero10.setText("0");
        numero10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero10ActionPerformed(evt);
            }
        });

        numero5.setEditable(false);
        numero5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero5.setText("0");
        numero5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero5ActionPerformed(evt);
            }
        });

        mais10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais10.setText(">");
        mais10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais10.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais10.setMaximumSize(new java.awt.Dimension(21, 21));
        mais10.setMinimumSize(new java.awt.Dimension(21, 21));
        mais10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais10ActionPerformed(evt);
            }
        });

        mais5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais5.setText(">");
        mais5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais5.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais5.setMaximumSize(new java.awt.Dimension(21, 21));
        mais5.setMinimumSize(new java.awt.Dimension(21, 21));
        mais5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais5ActionPerformed(evt);
            }
        });

        zerar10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar10.setText("0");
        zerar10.setEnabled(false);
        zerar10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar10.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar10.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar10.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar10ActionPerformed(evt);
            }
        });

        zerar5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar5.setText("0");
        zerar5.setEnabled(false);
        zerar5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar5.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar5.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar5.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar5ActionPerformed(evt);
            }
        });

        txtA8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtA8.setForeground(new java.awt.Color(255, 255, 255));
        txtA8.setText("ABANDONO DE VEICULO:");

        txtA9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtA9.setForeground(new java.awt.Color(255, 255, 255));
        txtA9.setText("S/ CAPACETE RODOVIA:");

        menos10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos10.setText("<");
        menos10.setEnabled(false);
        menos10.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos10.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos10.setMaximumSize(new java.awt.Dimension(21, 21));
        menos10.setMinimumSize(new java.awt.Dimension(21, 21));
        menos10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtA1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtA3, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtA, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtA2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtA5, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtA4, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator3)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtA7, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtA6, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtA9, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtA8, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA)
                    .addComponent(numero1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtA2)
                    .addComponent(numero6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA1)
                    .addComponent(numero2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtA3)
                    .addComponent(numero7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA5)
                    .addComponent(numero3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtA4)
                    .addComponent(numero8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA7)
                    .addComponent(numero4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtA6)
                    .addComponent(numero9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA9)
                    .addComponent(numero5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtA8)
                    .addComponent(numero10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("CRIMES COMETIDOS");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("CRIMES COMETIDOS:");

        crimes.setEditable(false);
        crimes.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        multatotal.setEditable(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("MULTA TOTAL:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("TEMPO PRESO:");

        mesestotal.setEditable(false);

        resetar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        resetar.setText("RESETAR TUDO");
        resetar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetarActionPerformed(evt);
            }
        });

        copiar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        copiar.setText("COPIAR");
        copiar.setEnabled(false);
        copiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copiarActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("O QUE FAZER:");

        fazeroq.setEditable(false);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("COMANDOS:");

        comandos.setEditable(false);

        registrar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        registrar.setText("GUARDAR REGISTRO DE PRISÃO");
        registrar.setEnabled(false);
        registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registrarActionPerformed(evt);
            }
        });

        copiar1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        copiar1.setText("COPIAR PROTOCOLO");
        copiar1.setEnabled(false);
        copiar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copiar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(registrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mesestotal, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(multatotal, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fazeroq))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(crimes))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comandos))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(copiar, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(copiar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(resetar, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(crimes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(mesestotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(multatotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8))
                    .addComponent(fazeroq, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(comandos)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(copiar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(resetar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(copiar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtA10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA10.setForeground(new java.awt.Color(255, 255, 255));
        txtA10.setText("EQUIP. BALÍSTICO:");

        numero21.setEditable(false);
        numero21.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero21.setText("0");
        numero21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero21ActionPerformed(evt);
            }
        });

        menos21.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos21.setText("<");
        menos21.setEnabled(false);
        menos21.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos21.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos21.setMaximumSize(new java.awt.Dimension(21, 21));
        menos21.setMinimumSize(new java.awt.Dimension(21, 21));
        menos21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos21ActionPerformed(evt);
            }
        });

        mais21.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais21.setText(">");
        mais21.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais21.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais21.setMaximumSize(new java.awt.Dimension(21, 21));
        mais21.setMinimumSize(new java.awt.Dimension(21, 21));
        mais21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais21ActionPerformed(evt);
            }
        });

        zerar21.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar21.setText("0");
        zerar21.setEnabled(false);
        zerar21.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar21.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar21.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar21.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar21ActionPerformed(evt);
            }
        });

        txtA11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA11.setForeground(new java.awt.Color(255, 255, 255));
        txtA11.setText("EQUIP. POLICIAL:");

        menos22.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos22.setText("<");
        menos22.setEnabled(false);
        menos22.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos22.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos22.setMaximumSize(new java.awt.Dimension(21, 21));
        menos22.setMinimumSize(new java.awt.Dimension(21, 21));
        menos22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos22ActionPerformed(evt);
            }
        });

        numero22.setEditable(false);
        numero22.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero22.setText("0");
        numero22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero22ActionPerformed(evt);
            }
        });

        mais22.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais22.setText(">");
        mais22.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais22.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais22.setMaximumSize(new java.awt.Dimension(21, 21));
        mais22.setMinimumSize(new java.awt.Dimension(21, 21));
        mais22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais22ActionPerformed(evt);
            }
        });

        zerar22.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar22.setText("0");
        zerar22.setEnabled(false);
        zerar22.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar22.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar22.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar22.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar22ActionPerformed(evt);
            }
        });

        txtA15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA15.setForeground(new java.awt.Color(255, 255, 255));
        txtA15.setText("MÁSCARA:");

        menos23.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos23.setText("<");
        menos23.setEnabled(false);
        menos23.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos23.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos23.setMaximumSize(new java.awt.Dimension(21, 21));
        menos23.setMinimumSize(new java.awt.Dimension(21, 21));
        menos23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos23ActionPerformed(evt);
            }
        });

        numero23.setEditable(false);
        numero23.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero23.setText("0");
        numero23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero23ActionPerformed(evt);
            }
        });

        mais23.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais23.setText(">");
        mais23.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais23.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais23.setMaximumSize(new java.awt.Dimension(21, 21));
        mais23.setMinimumSize(new java.awt.Dimension(21, 21));
        mais23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais23ActionPerformed(evt);
            }
        });

        zerar23.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar23.setText("0");
        zerar23.setEnabled(false);
        zerar23.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar23.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar23.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar23.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar23ActionPerformed(evt);
            }
        });

        numero24.setEditable(false);
        numero24.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero24.setText("0");
        numero24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero24ActionPerformed(evt);
            }
        });

        zerar25.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar25.setText("0");
        zerar25.setEnabled(false);
        zerar25.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar25.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar25.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar25.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar25ActionPerformed(evt);
            }
        });

        numero25.setEditable(false);
        numero25.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero25.setText("0");
        numero25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero25ActionPerformed(evt);
            }
        });

        zerar24.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar24.setText("0");
        zerar24.setEnabled(false);
        zerar24.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar24.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar24.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar24.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar24ActionPerformed(evt);
            }
        });

        txtA30.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA30.setForeground(new java.awt.Color(255, 255, 255));
        txtA30.setText("ARMA LEVE:");

        txtA31.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA31.setForeground(new java.awt.Color(255, 255, 255));
        txtA31.setText("ARMA PESADA:");

        mais25.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais25.setText(">");
        mais25.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais25.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais25.setMaximumSize(new java.awt.Dimension(21, 21));
        mais25.setMinimumSize(new java.awt.Dimension(21, 21));
        mais25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais25ActionPerformed(evt);
            }
        });

        menos24.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos24.setText("<");
        menos24.setEnabled(false);
        menos24.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos24.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos24.setMaximumSize(new java.awt.Dimension(21, 21));
        menos24.setMinimumSize(new java.awt.Dimension(21, 21));
        menos24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos24ActionPerformed(evt);
            }
        });

        mais24.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais24.setText(">");
        mais24.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais24.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais24.setMaximumSize(new java.awt.Dimension(21, 21));
        mais24.setMinimumSize(new java.awt.Dimension(21, 21));
        mais24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais24ActionPerformed(evt);
            }
        });

        menos25.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos25.setText("<");
        menos25.setEnabled(false);
        menos25.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos25.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos25.setMaximumSize(new java.awt.Dimension(21, 21));
        menos25.setMinimumSize(new java.awt.Dimension(21, 21));
        menos25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos25ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtA10, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(menos21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero21, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator5)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtA11, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(menos22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero22, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator6)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtA15, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(menos23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero23, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator16)
                    .addComponent(jSeparator17)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(txtA30, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(menos24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero24, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(txtA31, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(menos25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero25, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA10)
                    .addComponent(numero21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA11)
                    .addComponent(numero22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA15)
                    .addComponent(numero23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA30)
                    .addComponent(numero24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA31)
                    .addComponent(numero25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtA12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA12.setForeground(new java.awt.Color(255, 255, 255));
        txtA12.setText("BANCO/JOELHERIA:");

        numero26.setEditable(false);
        numero26.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero26.setText("0");
        numero26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero26ActionPerformed(evt);
            }
        });

        menos26.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos26.setText("<");
        menos26.setEnabled(false);
        menos26.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos26.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos26.setMaximumSize(new java.awt.Dimension(21, 21));
        menos26.setMinimumSize(new java.awt.Dimension(21, 21));
        menos26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos26ActionPerformed(evt);
            }
        });

        mais26.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais26.setText(">");
        mais26.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais26.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais26.setMaximumSize(new java.awt.Dimension(21, 21));
        mais26.setMinimumSize(new java.awt.Dimension(21, 21));
        mais26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais26ActionPerformed(evt);
            }
        });

        zerar26.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar26.setText("0");
        zerar26.setEnabled(false);
        zerar26.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar26.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar26.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar26.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar26ActionPerformed(evt);
            }
        });

        txtA13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA13.setForeground(new java.awt.Color(255, 255, 255));
        txtA13.setText("LOJAS:");

        menos27.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos27.setText("<");
        menos27.setEnabled(false);
        menos27.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos27.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos27.setMaximumSize(new java.awt.Dimension(21, 21));
        menos27.setMinimumSize(new java.awt.Dimension(21, 21));
        menos27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos27ActionPerformed(evt);
            }
        });

        numero27.setEditable(false);
        numero27.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero27.setText("0");
        numero27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero27ActionPerformed(evt);
            }
        });

        mais27.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais27.setText(">");
        mais27.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais27.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais27.setMaximumSize(new java.awt.Dimension(21, 21));
        mais27.setMinimumSize(new java.awt.Dimension(21, 21));
        mais27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais27ActionPerformed(evt);
            }
        });

        zerar27.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar27.setText("0");
        zerar27.setEnabled(false);
        zerar27.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar27.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar27.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar27.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar27ActionPerformed(evt);
            }
        });

        txtA16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA16.setForeground(new java.awt.Color(255, 255, 255));
        txtA16.setText("CAIXA ELETRÔNICO:");

        menos28.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos28.setText("<");
        menos28.setEnabled(false);
        menos28.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos28.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos28.setMaximumSize(new java.awt.Dimension(21, 21));
        menos28.setMinimumSize(new java.awt.Dimension(21, 21));
        menos28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos28ActionPerformed(evt);
            }
        });

        numero28.setEditable(false);
        numero28.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero28.setText("0");
        numero28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero28ActionPerformed(evt);
            }
        });

        mais28.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais28.setText(">");
        mais28.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais28.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais28.setMaximumSize(new java.awt.Dimension(21, 21));
        mais28.setMinimumSize(new java.awt.Dimension(21, 21));
        mais28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais28ActionPerformed(evt);
            }
        });

        zerar28.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar28.setText("0");
        zerar28.setEnabled(false);
        zerar28.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar28.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar28.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar28.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar28ActionPerformed(evt);
            }
        });

        txtA17.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtA17.setForeground(new java.awt.Color(255, 255, 255));
        txtA17.setText("CAIXA REGISTRADORA:");

        menos29.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos29.setText("<");
        menos29.setEnabled(false);
        menos29.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos29.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos29.setMaximumSize(new java.awt.Dimension(21, 21));
        menos29.setMinimumSize(new java.awt.Dimension(21, 21));
        menos29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos29ActionPerformed(evt);
            }
        });

        numero29.setEditable(false);
        numero29.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero29.setText("0");
        numero29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero29ActionPerformed(evt);
            }
        });

        mais29.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais29.setText(">");
        mais29.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais29.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais29.setMaximumSize(new java.awt.Dimension(21, 21));
        mais29.setMinimumSize(new java.awt.Dimension(21, 21));
        mais29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais29ActionPerformed(evt);
            }
        });

        zerar29.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar29.setText("0");
        zerar29.setEnabled(false);
        zerar29.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar29.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar29.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar29.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar29ActionPerformed(evt);
            }
        });

        txtA18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA18.setForeground(new java.awt.Color(255, 255, 255));
        txtA18.setText("CARRO FORTE:");

        menos30.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos30.setText("<");
        menos30.setEnabled(false);
        menos30.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos30.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos30.setMaximumSize(new java.awt.Dimension(21, 21));
        menos30.setMinimumSize(new java.awt.Dimension(21, 21));
        menos30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos30ActionPerformed(evt);
            }
        });

        numero30.setEditable(false);
        numero30.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero30.setText("0");
        numero30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero30ActionPerformed(evt);
            }
        });

        mais30.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais30.setText(">");
        mais30.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais30.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais30.setMaximumSize(new java.awt.Dimension(21, 21));
        mais30.setMinimumSize(new java.awt.Dimension(21, 21));
        mais30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais30ActionPerformed(evt);
            }
        });

        zerar30.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar30.setText("0");
        zerar30.setEnabled(false);
        zerar30.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar30.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar30.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar30.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar30ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtA16, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(menos28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero28, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtA18, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(menos30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero30, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator12)
                    .addComponent(jSeparator11)
                    .addComponent(jSeparator10)
                    .addComponent(jSeparator9)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtA12, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(menos26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero26, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtA13, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(menos27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero27, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(txtA17, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(menos29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero29, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA12)
                    .addComponent(numero26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA13)
                    .addComponent(numero27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA16)
                    .addComponent(numero28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA17)
                    .addComponent(numero29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA18)
                    .addComponent(numero30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtA28.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtA28.setForeground(new java.awt.Color(255, 255, 255));
        txtA28.setText("RESISTÊNCIA À PRISÃO:");

        numero31.setEditable(false);
        numero31.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero31.setText("0");
        numero31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero31ActionPerformed(evt);
            }
        });

        menos31.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos31.setText("<");
        menos31.setEnabled(false);
        menos31.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos31.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos31.setMaximumSize(new java.awt.Dimension(21, 21));
        menos31.setMinimumSize(new java.awt.Dimension(21, 21));
        menos31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos31ActionPerformed(evt);
            }
        });

        mais31.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais31.setText(">");
        mais31.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais31.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais31.setMaximumSize(new java.awt.Dimension(21, 21));
        mais31.setMinimumSize(new java.awt.Dimension(21, 21));
        mais31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais31ActionPerformed(evt);
            }
        });

        zerar31.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar31.setText("0");
        zerar31.setEnabled(false);
        zerar31.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar31.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar31.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar31.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar31ActionPerformed(evt);
            }
        });

        txtA29.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtA29.setForeground(new java.awt.Color(255, 255, 255));
        txtA29.setText("OMISSÃO DE SOCORRO:");

        menos32.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos32.setText("<");
        menos32.setEnabled(false);
        menos32.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos32.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos32.setMaximumSize(new java.awt.Dimension(21, 21));
        menos32.setMinimumSize(new java.awt.Dimension(21, 21));
        menos32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos32ActionPerformed(evt);
            }
        });

        numero32.setEditable(false);
        numero32.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero32.setText("0");
        numero32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero32ActionPerformed(evt);
            }
        });

        mais32.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais32.setText(">");
        mais32.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais32.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais32.setMaximumSize(new java.awt.Dimension(21, 21));
        mais32.setMinimumSize(new java.awt.Dimension(21, 21));
        mais32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais32ActionPerformed(evt);
            }
        });

        zerar32.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar32.setText("0");
        zerar32.setEnabled(false);
        zerar32.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar32.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar32.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar32.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar32ActionPerformed(evt);
            }
        });

        txtA32.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtA32.setForeground(new java.awt.Color(255, 255, 255));
        txtA32.setText("TENTATIVA DE ROUBO:");

        menos37.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos37.setText("<");
        menos37.setEnabled(false);
        menos37.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos37.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos37.setMaximumSize(new java.awt.Dimension(21, 21));
        menos37.setMinimumSize(new java.awt.Dimension(21, 21));
        menos37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos37ActionPerformed(evt);
            }
        });

        numero37.setEditable(false);
        numero37.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero37.setText("0");
        numero37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero37ActionPerformed(evt);
            }
        });

        mais37.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais37.setText(">");
        mais37.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais37.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais37.setMaximumSize(new java.awt.Dimension(21, 21));
        mais37.setMinimumSize(new java.awt.Dimension(21, 21));
        mais37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais37ActionPerformed(evt);
            }
        });

        zerar37.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar37.setText("0");
        zerar37.setEnabled(false);
        zerar37.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar37.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar37.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar37.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar37ActionPerformed(evt);
            }
        });

        txtA33.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA33.setForeground(new java.awt.Color(255, 255, 255));
        txtA33.setText("DIFAMAÇÃO:");

        menos38.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos38.setText("<");
        menos38.setEnabled(false);
        menos38.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos38.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos38.setMaximumSize(new java.awt.Dimension(21, 21));
        menos38.setMinimumSize(new java.awt.Dimension(21, 21));
        menos38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos38ActionPerformed(evt);
            }
        });

        numero38.setEditable(false);
        numero38.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero38.setText("0");
        numero38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero38ActionPerformed(evt);
            }
        });

        mais38.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais38.setText(">");
        mais38.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais38.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais38.setMaximumSize(new java.awt.Dimension(21, 21));
        mais38.setMinimumSize(new java.awt.Dimension(21, 21));
        mais38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais38ActionPerformed(evt);
            }
        });

        zerar38.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar38.setText("0");
        zerar38.setEnabled(false);
        zerar38.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar38.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar38.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar38.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar38ActionPerformed(evt);
            }
        });

        txtA34.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtA34.setForeground(new java.awt.Color(255, 255, 255));
        txtA34.setText("OBSTRUÇÃO DE JUSTIÇA:");

        txtA35.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtA35.setForeground(new java.awt.Color(255, 255, 255));
        txtA35.setText("DANO AO PATRIMÔNIO:");

        menos39.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos39.setText("<");
        menos39.setEnabled(false);
        menos39.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos39.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos39.setMaximumSize(new java.awt.Dimension(21, 21));
        menos39.setMinimumSize(new java.awt.Dimension(21, 21));
        menos39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos39ActionPerformed(evt);
            }
        });

        menos33.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos33.setText("<");
        menos33.setEnabled(false);
        menos33.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos33.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos33.setMaximumSize(new java.awt.Dimension(21, 21));
        menos33.setMinimumSize(new java.awt.Dimension(21, 21));
        menos33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos33ActionPerformed(evt);
            }
        });

        numero39.setEditable(false);
        numero39.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero39.setText("0");
        numero39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero39ActionPerformed(evt);
            }
        });

        numero33.setEditable(false);
        numero33.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero33.setText("0");
        numero33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero33ActionPerformed(evt);
            }
        });

        mais39.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais39.setText(">");
        mais39.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais39.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais39.setMaximumSize(new java.awt.Dimension(21, 21));
        mais39.setMinimumSize(new java.awt.Dimension(21, 21));
        mais39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais39ActionPerformed(evt);
            }
        });

        mais33.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais33.setText(">");
        mais33.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais33.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais33.setMaximumSize(new java.awt.Dimension(21, 21));
        mais33.setMinimumSize(new java.awt.Dimension(21, 21));
        mais33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais33ActionPerformed(evt);
            }
        });

        zerar39.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar39.setText("0");
        zerar39.setEnabled(false);
        zerar39.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar39.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar39.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar39.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar39ActionPerformed(evt);
            }
        });

        zerar33.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar33.setText("0");
        zerar33.setEnabled(false);
        zerar33.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar33.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar33.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar33.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar33ActionPerformed(evt);
            }
        });

        menos34.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos34.setText("<");
        menos34.setEnabled(false);
        menos34.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos34.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos34.setMaximumSize(new java.awt.Dimension(21, 21));
        menos34.setMinimumSize(new java.awt.Dimension(21, 21));
        menos34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos34ActionPerformed(evt);
            }
        });

        numero40.setEditable(false);
        numero40.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero40.setText("0");
        numero40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero40ActionPerformed(evt);
            }
        });

        numero34.setEditable(false);
        numero34.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero34.setText("0");
        numero34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero34ActionPerformed(evt);
            }
        });

        mais40.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais40.setText(">");
        mais40.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais40.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais40.setMaximumSize(new java.awt.Dimension(21, 21));
        mais40.setMinimumSize(new java.awt.Dimension(21, 21));
        mais40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais40ActionPerformed(evt);
            }
        });

        mais34.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais34.setText(">");
        mais34.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais34.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais34.setMaximumSize(new java.awt.Dimension(21, 21));
        mais34.setMinimumSize(new java.awt.Dimension(21, 21));
        mais34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais34ActionPerformed(evt);
            }
        });

        zerar40.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar40.setText("0");
        zerar40.setEnabled(false);
        zerar40.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar40.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar40.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar40.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar40ActionPerformed(evt);
            }
        });

        zerar34.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar34.setText("0");
        zerar34.setEnabled(false);
        zerar34.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar34.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar34.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar34.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar34ActionPerformed(evt);
            }
        });

        txtA36.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtA36.setForeground(new java.awt.Color(255, 255, 255));
        txtA36.setText("ROUBO (C/ AMEAÇA):");

        txtA37.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtA37.setForeground(new java.awt.Color(255, 255, 255));
        txtA37.setText("FURTO:");

        menos40.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos40.setText("<");
        menos40.setEnabled(false);
        menos40.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos40.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos40.setMaximumSize(new java.awt.Dimension(21, 21));
        menos40.setMinimumSize(new java.awt.Dimension(21, 21));
        menos40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos40ActionPerformed(evt);
            }
        });

        menos35.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos35.setText("<");
        menos35.setEnabled(false);
        menos35.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos35.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos35.setMaximumSize(new java.awt.Dimension(21, 21));
        menos35.setMinimumSize(new java.awt.Dimension(21, 21));
        menos35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos35ActionPerformed(evt);
            }
        });

        numero41.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero41.setText("0");
        numero41.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                numero41KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                numero41KeyTyped(evt);
            }
        });

        numero35.setEditable(false);
        numero35.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero35.setText("0");
        numero35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero35ActionPerformed(evt);
            }
        });

        mais35.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais35.setText(">");
        mais35.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais35.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais35.setMaximumSize(new java.awt.Dimension(21, 21));
        mais35.setMinimumSize(new java.awt.Dimension(21, 21));
        mais35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais35ActionPerformed(evt);
            }
        });

        zerar41.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar41.setText("0");
        zerar41.setEnabled(false);
        zerar41.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar41.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar41.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar41.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar41ActionPerformed(evt);
            }
        });

        zerar35.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar35.setText("0");
        zerar35.setEnabled(false);
        zerar35.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar35.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar35.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar35.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar35ActionPerformed(evt);
            }
        });

        txtA38.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtA38.setForeground(new java.awt.Color(255, 255, 255));
        txtA38.setText("MULTAS PENDENTES:");

        txtA39.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtA39.setForeground(new java.awt.Color(255, 255, 255));
        txtA39.setText("FUGA DA ABORDAGEM:");

        menos36.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos36.setText("<");
        menos36.setEnabled(false);
        menos36.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos36.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos36.setMaximumSize(new java.awt.Dimension(21, 21));
        menos36.setMinimumSize(new java.awt.Dimension(21, 21));
        menos36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos36ActionPerformed(evt);
            }
        });

        numero36.setEditable(false);
        numero36.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero36.setText("0");
        numero36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero36ActionPerformed(evt);
            }
        });

        mais36.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais36.setText(">");
        mais36.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais36.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais36.setMaximumSize(new java.awt.Dimension(21, 21));
        mais36.setMinimumSize(new java.awt.Dimension(21, 21));
        mais36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais36ActionPerformed(evt);
            }
        });

        zerar36.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar36.setText("0");
        zerar36.setEnabled(false);
        zerar36.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar36.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar36.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar36.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar36ActionPerformed(evt);
            }
        });

        txtA50.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA50.setForeground(new java.awt.Color(255, 255, 255));
        txtA50.setText("CORRIDA ILEGAL:");

        txtA51.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA51.setForeground(new java.awt.Color(255, 255, 255));
        txtA51.setText("ATO OBSCENO:");

        menos42.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos42.setText("<");
        menos42.setEnabled(false);
        menos42.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos42.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos42.setMaximumSize(new java.awt.Dimension(21, 21));
        menos42.setMinimumSize(new java.awt.Dimension(21, 21));
        menos42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos42ActionPerformed(evt);
            }
        });

        numero42.setEditable(false);
        numero42.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero42.setText("0");
        numero42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero42ActionPerformed(evt);
            }
        });

        mais42.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais42.setText(">");
        mais42.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais42.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais42.setMaximumSize(new java.awt.Dimension(21, 21));
        mais42.setMinimumSize(new java.awt.Dimension(21, 21));
        mais42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais42ActionPerformed(evt);
            }
        });

        zerar42.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar42.setText("0");
        zerar42.setEnabled(false);
        zerar42.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar42.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar42.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar42.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar42ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator15)
                    .addComponent(jSeparator18)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(txtA35, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero33, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtA34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero39, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator19)
                    .addComponent(jSeparator20)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(txtA39, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero35, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtA38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero41, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator25)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(txtA37, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(menos34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numero34, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mais34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(zerar34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtA36, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(menos40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numero40, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mais40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(zerar40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(txtA29, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(menos32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numero32, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mais32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(zerar32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtA33, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(menos38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numero38, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mais38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(zerar38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(txtA28, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(menos31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numero31, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mais31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(zerar31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtA32, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(menos37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numero37, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mais37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(zerar37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(txtA50, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(menos36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numero36, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mais36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(zerar36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtA51, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(menos42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numero42, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mais42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(zerar42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA28)
                    .addComponent(numero31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtA32)
                    .addComponent(numero37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA29)
                    .addComponent(numero32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtA33)
                    .addComponent(numero38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA35)
                    .addComponent(numero33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtA34)
                    .addComponent(numero39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA37)
                    .addComponent(numero34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtA36)
                    .addComponent(numero40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar40, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA39)
                    .addComponent(numero35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtA38)
                    .addComponent(numero41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtA51)
                        .addComponent(numero42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(menos42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mais42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(zerar42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtA50)
                        .addComponent(numero36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(menos36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mais36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(zerar36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        txtA40.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA40.setForeground(new java.awt.Color(255, 255, 255));
        txtA40.setText("DESACATO:");

        numero43.setEditable(false);
        numero43.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero43.setText("0");
        numero43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero43ActionPerformed(evt);
            }
        });

        menos43.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos43.setText("<");
        menos43.setEnabled(false);
        menos43.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos43.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos43.setMaximumSize(new java.awt.Dimension(21, 21));
        menos43.setMinimumSize(new java.awt.Dimension(21, 21));
        menos43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos43ActionPerformed(evt);
            }
        });

        mais43.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais43.setText(">");
        mais43.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais43.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais43.setMaximumSize(new java.awt.Dimension(21, 21));
        mais43.setMinimumSize(new java.awt.Dimension(21, 21));
        mais43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais43ActionPerformed(evt);
            }
        });

        zerar43.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar43.setText("0");
        zerar43.setEnabled(false);
        zerar43.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar43.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar43.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar43.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar43ActionPerformed(evt);
            }
        });

        txtA41.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA41.setForeground(new java.awt.Color(255, 255, 255));
        txtA41.setText("EXTORSÃO:");

        menos44.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos44.setText("<");
        menos44.setEnabled(false);
        menos44.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos44.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos44.setMaximumSize(new java.awt.Dimension(21, 21));
        menos44.setMinimumSize(new java.awt.Dimension(21, 21));
        menos44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos44ActionPerformed(evt);
            }
        });

        numero44.setEditable(false);
        numero44.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero44.setText("0");
        numero44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero44ActionPerformed(evt);
            }
        });

        mais44.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais44.setText(">");
        mais44.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais44.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais44.setMaximumSize(new java.awt.Dimension(21, 21));
        mais44.setMinimumSize(new java.awt.Dimension(21, 21));
        mais44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais44ActionPerformed(evt);
            }
        });

        zerar44.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar44.setText("0");
        zerar44.setEnabled(false);
        zerar44.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar44.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar44.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar44.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar44ActionPerformed(evt);
            }
        });

        txt48.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txt48.setForeground(new java.awt.Color(255, 255, 255));
        txt48.setText("CORRUPÇÃO:");

        menos48.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos48.setText("<");
        menos48.setEnabled(false);
        menos48.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos48.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos48.setMaximumSize(new java.awt.Dimension(21, 21));
        menos48.setMinimumSize(new java.awt.Dimension(21, 21));
        menos48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos48ActionPerformed(evt);
            }
        });

        numero48.setEditable(false);
        numero48.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero48.setText("0");
        numero48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero48ActionPerformed(evt);
            }
        });

        mais48.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais48.setText(">");
        mais48.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais48.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais48.setMaximumSize(new java.awt.Dimension(21, 21));
        mais48.setMinimumSize(new java.awt.Dimension(21, 21));
        mais48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais48ActionPerformed(evt);
            }
        });

        zerar48.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar48.setText("0");
        zerar48.setEnabled(false);
        zerar48.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar48.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar48.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar48.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar48.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar48ActionPerformed(evt);
            }
        });

        txtA43.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtA43.setForeground(new java.awt.Color(255, 255, 255));
        txtA43.setText("POLUIÇÃO SONORA:");

        menos49.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos49.setText("<");
        menos49.setEnabled(false);
        menos49.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos49.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos49.setMaximumSize(new java.awt.Dimension(21, 21));
        menos49.setMinimumSize(new java.awt.Dimension(21, 21));
        menos49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos49ActionPerformed(evt);
            }
        });

        numero49.setEditable(false);
        numero49.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero49.setText("0");
        numero49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero49ActionPerformed(evt);
            }
        });

        mais49.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais49.setText(">");
        mais49.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais49.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais49.setMaximumSize(new java.awt.Dimension(21, 21));
        mais49.setMinimumSize(new java.awt.Dimension(21, 21));
        mais49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais49ActionPerformed(evt);
            }
        });

        zerar49.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar49.setText("0");
        zerar49.setEnabled(false);
        zerar49.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar49.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar49.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar49.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar49ActionPerformed(evt);
            }
        });

        txtA44.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtA44.setForeground(new java.awt.Color(255, 255, 255));
        txtA44.setText("ROUBO DE VEÍCULO:");

        txtA45.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtA45.setForeground(new java.awt.Color(255, 255, 255));
        txtA45.setText("FALSI. IDEOLÓG.:");

        menos50.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos50.setText("<");
        menos50.setEnabled(false);
        menos50.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos50.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos50.setMaximumSize(new java.awt.Dimension(21, 21));
        menos50.setMinimumSize(new java.awt.Dimension(21, 21));
        menos50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos50ActionPerformed(evt);
            }
        });

        menos45.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos45.setText("<");
        menos45.setEnabled(false);
        menos45.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos45.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos45.setMaximumSize(new java.awt.Dimension(21, 21));
        menos45.setMinimumSize(new java.awt.Dimension(21, 21));
        menos45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos45ActionPerformed(evt);
            }
        });

        numero50.setEditable(false);
        numero50.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero50.setText("0");
        numero50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero50ActionPerformed(evt);
            }
        });

        numero45.setEditable(false);
        numero45.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero45.setText("0");
        numero45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero45ActionPerformed(evt);
            }
        });

        mais50.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais50.setText(">");
        mais50.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais50.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais50.setMaximumSize(new java.awt.Dimension(21, 21));
        mais50.setMinimumSize(new java.awt.Dimension(21, 21));
        mais50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais50ActionPerformed(evt);
            }
        });

        mais45.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais45.setText(">");
        mais45.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais45.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais45.setMaximumSize(new java.awt.Dimension(21, 21));
        mais45.setMinimumSize(new java.awt.Dimension(21, 21));
        mais45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais45ActionPerformed(evt);
            }
        });

        zerar50.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar50.setText("0");
        zerar50.setEnabled(false);
        zerar50.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar50.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar50.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar50.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar50ActionPerformed(evt);
            }
        });

        zerar45.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar45.setText("0");
        zerar45.setEnabled(false);
        zerar45.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar45.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar45.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar45.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar45ActionPerformed(evt);
            }
        });

        menos46.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos46.setText("<");
        menos46.setEnabled(false);
        menos46.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos46.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos46.setMaximumSize(new java.awt.Dimension(21, 21));
        menos46.setMinimumSize(new java.awt.Dimension(21, 21));
        menos46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos46ActionPerformed(evt);
            }
        });

        numero51.setEditable(false);
        numero51.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero51.setText("0");
        numero51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero51ActionPerformed(evt);
            }
        });

        numero46.setEditable(false);
        numero46.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero46.setText("0");
        numero46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero46ActionPerformed(evt);
            }
        });

        mais51.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais51.setText(">");
        mais51.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais51.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais51.setMaximumSize(new java.awt.Dimension(21, 21));
        mais51.setMinimumSize(new java.awt.Dimension(21, 21));
        mais51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais51ActionPerformed(evt);
            }
        });

        mais46.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais46.setText(">");
        mais46.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais46.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais46.setMaximumSize(new java.awt.Dimension(21, 21));
        mais46.setMinimumSize(new java.awt.Dimension(21, 21));
        mais46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais46ActionPerformed(evt);
            }
        });

        zerar51.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar51.setText("0");
        zerar51.setEnabled(false);
        zerar51.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar51.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar51.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar51.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar51ActionPerformed(evt);
            }
        });

        zerar46.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar46.setText("0");
        zerar46.setEnabled(false);
        zerar46.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar46.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar46.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar46.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar46ActionPerformed(evt);
            }
        });

        txtA46.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        txtA46.setForeground(new java.awt.Color(255, 255, 255));
        txtA46.setText("LESÃO CORPORAL:");

        txtA47.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA47.setForeground(new java.awt.Color(255, 255, 255));
        txtA47.setText("CALÚNIA:");

        menos51.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos51.setText("<");
        menos51.setEnabled(false);
        menos51.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos51.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos51.setMaximumSize(new java.awt.Dimension(21, 21));
        menos51.setMinimumSize(new java.awt.Dimension(21, 21));
        menos51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos51ActionPerformed(evt);
            }
        });

        menos47.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos47.setText("<");
        menos47.setEnabled(false);
        menos47.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos47.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos47.setMaximumSize(new java.awt.Dimension(21, 21));
        menos47.setMinimumSize(new java.awt.Dimension(21, 21));
        menos47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos47ActionPerformed(evt);
            }
        });

        numero52.setEditable(false);
        numero52.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero52.setText("0");
        numero52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero52ActionPerformed(evt);
            }
        });

        numero47.setEditable(false);
        numero47.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero47.setText("0");
        numero47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero47ActionPerformed(evt);
            }
        });

        mais52.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais52.setText(">");
        mais52.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais52.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais52.setMaximumSize(new java.awt.Dimension(21, 21));
        mais52.setMinimumSize(new java.awt.Dimension(21, 21));
        mais52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais52ActionPerformed(evt);
            }
        });

        mais47.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais47.setText(">");
        mais47.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais47.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais47.setMaximumSize(new java.awt.Dimension(21, 21));
        mais47.setMinimumSize(new java.awt.Dimension(21, 21));
        mais47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais47ActionPerformed(evt);
            }
        });

        zerar52.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar52.setText("0");
        zerar52.setEnabled(false);
        zerar52.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar52.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar52.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar52.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar52ActionPerformed(evt);
            }
        });

        zerar47.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar47.setText("0");
        zerar47.setEnabled(false);
        zerar47.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar47.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar47.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar47.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar47.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar47ActionPerformed(evt);
            }
        });

        txtA48.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA48.setForeground(new java.awt.Color(255, 255, 255));
        txtA48.setText("SEQUESTRO:");

        txtA49.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtA49.setForeground(new java.awt.Color(255, 255, 255));
        txtA49.setText("AMEAÇA:");

        menos52.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos52.setText("<");
        menos52.setEnabled(false);
        menos52.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos52.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos52.setMaximumSize(new java.awt.Dimension(21, 21));
        menos52.setMinimumSize(new java.awt.Dimension(21, 21));
        menos52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos52ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator21)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(txtA41, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero44, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtA43, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero49, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(txtA40, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero43, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 79, Short.MAX_VALUE)
                        .addComponent(txt48, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero48, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator22)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(txtA45, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero45, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtA44, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero50, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator23)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(txtA47, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero46, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtA46, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero51, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator24)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(txtA49, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero47, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtA48, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(menos52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero52, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA40)
                    .addComponent(numero43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar43, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt48)
                    .addComponent(numero48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar48, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA41)
                    .addComponent(numero44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtA43)
                    .addComponent(numero49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA45)
                    .addComponent(numero45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtA44)
                    .addComponent(numero50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA47)
                    .addComponent(numero46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar46, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtA46)
                    .addComponent(numero51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtA49)
                    .addComponent(numero47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar47, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtA48)
                    .addComponent(numero52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BANCO DA POLÍCIA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("NOME:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("RÉU PRIMÁRIO:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(153, 153, 255));
        jLabel13.setText("COLOQUE O ID");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(153, 153, 255));
        jLabel14.setText("S/ INFO");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("VEZES PRESO:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(153, 153, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("S/ INFO");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("PROCURADO:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(153, 153, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("NÃO");

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setText("VER");
        jButton1.setEnabled(false);

        verpreso.setBackground(new java.awt.Color(255, 255, 255));
        verpreso.setText("VER");
        verpreso.setEnabled(false);
        verpreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verpresoActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("MULTAS:");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(153, 153, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("S/ INFO");

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setText("VER");
        jButton3.setEnabled(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(verpreso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton3))
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(8, 8, 8)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10)
                                .addComponent(jLabel13))
                            .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(verpreso, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
        );

        jMenu2.setText("FECHAR");

        jMenuItem2.setText("VOLTAR PARA O PAINEL");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("EXIBIR");
        jMenu3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMenu3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jMenuItem1.setText("CRIMES E MULTAS");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuItem3.setText("SOBRE");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void numero1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero1ActionPerformed

    private void zerar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar1ActionPerformed
        BotaoGeral("zerar",1);
    }//GEN-LAST:event_zerar1ActionPerformed

    private void numero2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero2ActionPerformed

    private void zerar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar2ActionPerformed
        BotaoGeral("zer",2);
    }//GEN-LAST:event_zerar2ActionPerformed

    private void numero6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero6ActionPerformed

    private void zerar6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar6ActionPerformed
        BotaoGeral("zer",6);
    }//GEN-LAST:event_zerar6ActionPerformed

    private void numero7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero7ActionPerformed

    private void zerar7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar7ActionPerformed
        BotaoGeral("zer",7);
    }//GEN-LAST:event_zerar7ActionPerformed

    private void numero8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero8ActionPerformed

    private void numero3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero3ActionPerformed

    private void zerar8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar8ActionPerformed
        BotaoGeral("zer",8);
    }//GEN-LAST:event_zerar8ActionPerformed

    private void zerar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar3ActionPerformed
        BotaoGeral("zer",3);
    }//GEN-LAST:event_zerar3ActionPerformed

    private void numero9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero9ActionPerformed

    private void numero4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero4ActionPerformed

    private void zerar9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar9ActionPerformed
        BotaoGeral("zer",9);
    }//GEN-LAST:event_zerar9ActionPerformed

    private void zerar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar4ActionPerformed
        BotaoGeral("zer",4);
    }//GEN-LAST:event_zerar4ActionPerformed

    private void numero10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero10ActionPerformed

    private void numero5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero5ActionPerformed

    private void zerar10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar10ActionPerformed
        BotaoGeral("zer",10);
    }//GEN-LAST:event_zerar10ActionPerformed

    private void zerar5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar5ActionPerformed
        BotaoGeral("zer",5);
    }//GEN-LAST:event_zerar5ActionPerformed

    private void menos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos1ActionPerformed
        BotaoGeral("rem",1);
    }//GEN-LAST:event_menos1ActionPerformed

    private void mais1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais1ActionPerformed
        BotaoGeral("add",1);
    }//GEN-LAST:event_mais1ActionPerformed

    private void mais2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais2ActionPerformed
        BotaoGeral("add",2);
    }//GEN-LAST:event_mais2ActionPerformed

    private void menos2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos2ActionPerformed
        BotaoGeral("rem",2);
    }//GEN-LAST:event_menos2ActionPerformed

    private void menos3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos3ActionPerformed
        BotaoGeral("rem",3);
    }//GEN-LAST:event_menos3ActionPerformed

    private void mais3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais3ActionPerformed
        BotaoGeral("add",3);
    }//GEN-LAST:event_mais3ActionPerformed

    private void menos4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos4ActionPerformed
        BotaoGeral("rem",4);
    }//GEN-LAST:event_menos4ActionPerformed

    private void mais4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais4ActionPerformed
        BotaoGeral("add",4);
    }//GEN-LAST:event_mais4ActionPerformed

    private void menos5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos5ActionPerformed
        BotaoGeral("rem",5);
    }//GEN-LAST:event_menos5ActionPerformed

    private void mais5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais5ActionPerformed
        BotaoGeral("add",5);
    }//GEN-LAST:event_mais5ActionPerformed

    private void menos6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos6ActionPerformed
        BotaoGeral("rem",6);
    }//GEN-LAST:event_menos6ActionPerformed

    private void mais6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais6ActionPerformed
        BotaoGeral("add",6);
    }//GEN-LAST:event_mais6ActionPerformed

    private void menos7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos7ActionPerformed
        BotaoGeral("rem",7);
    }//GEN-LAST:event_menos7ActionPerformed

    private void mais7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais7ActionPerformed
        BotaoGeral("add",7);
    }//GEN-LAST:event_mais7ActionPerformed

    private void menos8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos8ActionPerformed
        BotaoGeral("rem",8);
    }//GEN-LAST:event_menos8ActionPerformed

    private void mais8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais8ActionPerformed
        BotaoGeral("add",8);
    }//GEN-LAST:event_mais8ActionPerformed

    private void menos9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos9ActionPerformed
        BotaoGeral("rem",9);
    }//GEN-LAST:event_menos9ActionPerformed

    private void mais9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais9ActionPerformed
        BotaoGeral("add",9);
    }//GEN-LAST:event_mais9ActionPerformed

    private void menos10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos10ActionPerformed
        BotaoGeral("rem", 10);
    }//GEN-LAST:event_menos10ActionPerformed

    private void mais10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais10ActionPerformed
        BotaoGeral("add",10);
    }//GEN-LAST:event_mais10ActionPerformed

    private void idKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idKeyTyped
        AtualizarCrimes();
    }//GEN-LAST:event_idKeyTyped

    private void idKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idKeyReleased
       AtualizarCrimes();
    }//GEN-LAST:event_idKeyReleased

    private void copiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copiarActionPerformed
        String ide = id.getText();
        /*if(ide.length() < 1){
            showMessageDialog(null, "Está faltando Nome ou ID do indivíduo!!");
            id.requestFocus();
        }else{*/
        
            String myString = discord.getText();
            StringSelection stringSelection = new StringSelection(myString);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
            copiar.setText("COPIADO!!!");
            //copiado.setVisible(true);
        //}
        id.requestFocus();
    }//GEN-LAST:event_copiarActionPerformed

    private void numero21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero21ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero21ActionPerformed

    private void menos21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos21ActionPerformed
         BotaoGeral("rem", 21);       // TODO add your handling code here:
    }//GEN-LAST:event_menos21ActionPerformed

    private void mais21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais21ActionPerformed
       BotaoGeral("add",21);
    }//GEN-LAST:event_mais21ActionPerformed

    private void zerar21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar21ActionPerformed
        BotaoGeral("zer", 21);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar21ActionPerformed

    private void menos22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos22ActionPerformed
         BotaoGeral("rem", 22);       // TODO add your handling code here:
    }//GEN-LAST:event_menos22ActionPerformed

    private void numero22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero22ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero22ActionPerformed

    private void mais22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais22ActionPerformed
        BotaoGeral("add",22);
    }//GEN-LAST:event_mais22ActionPerformed

    private void zerar22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar22ActionPerformed
        BotaoGeral("zer", 22);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar22ActionPerformed

    private void menos23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos23ActionPerformed
        BotaoGeral("rem", 23);        // TODO add your handling code here:
    }//GEN-LAST:event_menos23ActionPerformed

    private void numero23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero23ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero23ActionPerformed

    private void mais23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais23ActionPerformed
        BotaoGeral("add",23);        // TODO add your handling code here:
    }//GEN-LAST:event_mais23ActionPerformed

    private void zerar23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar23ActionPerformed
        BotaoGeral("zer", 23);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar23ActionPerformed

    private void numero26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero26ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero26ActionPerformed

    private void menos26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos26ActionPerformed
         BotaoGeral("rem", 26);       // TODO add your handling code here:
    }//GEN-LAST:event_menos26ActionPerformed

    private void mais26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais26ActionPerformed
        BotaoGeral("add",26);        // TODO add your handling code here:
    }//GEN-LAST:event_mais26ActionPerformed

    private void zerar26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar26ActionPerformed
         BotaoGeral("zer", 26);       // TODO add your handling code here:
    }//GEN-LAST:event_zerar26ActionPerformed

    private void menos27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos27ActionPerformed
        BotaoGeral("rem", 27);        // TODO add your handling code here:
    }//GEN-LAST:event_menos27ActionPerformed

    private void numero27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero27ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero27ActionPerformed

    private void mais27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais27ActionPerformed
        BotaoGeral("add",27);        // TODO add your handling code here:
    }//GEN-LAST:event_mais27ActionPerformed

    private void zerar27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar27ActionPerformed
        BotaoGeral("zer", 27);       // TODO add your handling code here:
    }//GEN-LAST:event_zerar27ActionPerformed

    private void menos28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos28ActionPerformed
        BotaoGeral("rem", 28);        // TODO add your handling code here:
    }//GEN-LAST:event_menos28ActionPerformed

    private void numero28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero28ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero28ActionPerformed

    private void mais28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais28ActionPerformed
         BotaoGeral("add",28);       // TODO add your handling code here:
    }//GEN-LAST:event_mais28ActionPerformed

    private void zerar28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar28ActionPerformed
        BotaoGeral("zer", 28);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar28ActionPerformed

    private void menos29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos29ActionPerformed
         BotaoGeral("rem", 29);       // TODO add your handling code here:
    }//GEN-LAST:event_menos29ActionPerformed

    private void numero29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero29ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero29ActionPerformed

    private void mais29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais29ActionPerformed
        BotaoGeral("add",29);        // TODO add your handling code here:
    }//GEN-LAST:event_mais29ActionPerformed

    private void zerar29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar29ActionPerformed
        BotaoGeral("zer", 29);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar29ActionPerformed

    private void menos30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos30ActionPerformed
        BotaoGeral("rem", 30);        // TODO add your handling code here:
    }//GEN-LAST:event_menos30ActionPerformed

    private void numero30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero30ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero30ActionPerformed

    private void mais30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais30ActionPerformed
        BotaoGeral("add",30);        // TODO add your handling code here:
    }//GEN-LAST:event_mais30ActionPerformed

    private void zerar30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar30ActionPerformed
        BotaoGeral("zer", 30);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar30ActionPerformed

    private void numero24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero24ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero24ActionPerformed

    private void menos24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos24ActionPerformed
         BotaoGeral("rem", 24);       // TODO add your handling code here:
    }//GEN-LAST:event_menos24ActionPerformed

    private void menos25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos25ActionPerformed
         BotaoGeral("rem", 25);       // TODO add your handling code here:
    }//GEN-LAST:event_menos25ActionPerformed

    private void numero25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero25ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero25ActionPerformed

    private void mais25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais25ActionPerformed
        BotaoGeral("add",25);        // TODO add your handling code here:
    }//GEN-LAST:event_mais25ActionPerformed

    private void zerar25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar25ActionPerformed
        BotaoGeral("zer", 25);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar25ActionPerformed

    private void zerar24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar24ActionPerformed
         BotaoGeral("zer", 24);       // TODO add your handling code here:
    }//GEN-LAST:event_zerar24ActionPerformed

    private void mais24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais24ActionPerformed
        BotaoGeral("add",24);        // TODO add your handling code here:
    }//GEN-LAST:event_mais24ActionPerformed

    private void numero31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero31ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero31ActionPerformed

    private void menos31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos31ActionPerformed
        BotaoGeral("rem", 31);        // TODO add your handling code here:
    }//GEN-LAST:event_menos31ActionPerformed

    private void mais31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais31ActionPerformed
        BotaoGeral("add",31);        // TODO add your handling code here:
    }//GEN-LAST:event_mais31ActionPerformed

    private void zerar31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar31ActionPerformed
        BotaoGeral("zer", 31);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar31ActionPerformed

    private void menos32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos32ActionPerformed
        BotaoGeral("rem", 32);        // TODO add your handling code here:
    }//GEN-LAST:event_menos32ActionPerformed

    private void numero32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero32ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero32ActionPerformed

    private void mais32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais32ActionPerformed
        BotaoGeral("add",32);        // TODO add your handling code here:
    }//GEN-LAST:event_mais32ActionPerformed

    private void zerar32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar32ActionPerformed
        BotaoGeral("zer", 32);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar32ActionPerformed

    private void menos37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos37ActionPerformed
        BotaoGeral("rem", 37);        // TODO add your handling code here:
    }//GEN-LAST:event_menos37ActionPerformed

    private void numero37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero37ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero37ActionPerformed

    private void mais37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais37ActionPerformed
         BotaoGeral("add",37);       // TODO add your handling code here:
    }//GEN-LAST:event_mais37ActionPerformed

    private void zerar37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar37ActionPerformed
        BotaoGeral("zer", 37);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar37ActionPerformed

    private void menos38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos38ActionPerformed
        BotaoGeral("rem", 38);        // TODO add your handling code here:
    }//GEN-LAST:event_menos38ActionPerformed

    private void numero38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero38ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero38ActionPerformed

    private void mais38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais38ActionPerformed
         BotaoGeral("add",38);       // TODO add your handling code here:
    }//GEN-LAST:event_mais38ActionPerformed

    private void zerar38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar38ActionPerformed
        BotaoGeral("zer", 38);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar38ActionPerformed

    private void menos39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos39ActionPerformed
        BotaoGeral("rem", 39);        // TODO add your handling code here:
    }//GEN-LAST:event_menos39ActionPerformed

    private void menos33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos33ActionPerformed
         BotaoGeral("rem", 33);       // TODO add your handling code here:
    }//GEN-LAST:event_menos33ActionPerformed

    private void numero39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero39ActionPerformed
                // TODO add your handling code here:
    }//GEN-LAST:event_numero39ActionPerformed

    private void numero33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero33ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero33ActionPerformed

    private void mais39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais39ActionPerformed
        BotaoGeral("add",39);        // TODO add your handling code here:
    }//GEN-LAST:event_mais39ActionPerformed

    private void mais33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais33ActionPerformed
         BotaoGeral("add", 33);        // TODO add your handling code here:
    }//GEN-LAST:event_mais33ActionPerformed

    private void zerar39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar39ActionPerformed
        BotaoGeral("zer", 39);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar39ActionPerformed

    private void zerar33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar33ActionPerformed
        BotaoGeral("zer", 33);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar33ActionPerformed

    private void menos34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos34ActionPerformed
        BotaoGeral("rem", 34);        // TODO add your handling code here:
    }//GEN-LAST:event_menos34ActionPerformed

    private void numero40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero40ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero40ActionPerformed

    private void numero34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero34ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero34ActionPerformed

    private void mais40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais40ActionPerformed
        BotaoGeral("add",40);        // TODO add your handling code here:
    }//GEN-LAST:event_mais40ActionPerformed

    private void mais34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais34ActionPerformed
         BotaoGeral("add",34);       // TODO add your handling code here:
    }//GEN-LAST:event_mais34ActionPerformed

    private void zerar40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar40ActionPerformed
        BotaoGeral("zer", 40);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar40ActionPerformed

    private void zerar34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar34ActionPerformed
        BotaoGeral("zer", 34);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar34ActionPerformed

    private void menos40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos40ActionPerformed
        BotaoGeral("rem", 40);        // TODO add your handling code here:
    }//GEN-LAST:event_menos40ActionPerformed

    private void menos35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos35ActionPerformed
        BotaoGeral("rem", 35);        // TODO add your handling code here:
    }//GEN-LAST:event_menos35ActionPerformed

    private void numero35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero35ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero35ActionPerformed

    private void mais35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais35ActionPerformed
        BotaoGeral("add",35);        // TODO add your handling code here:
    }//GEN-LAST:event_mais35ActionPerformed

    private void zerar41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar41ActionPerformed
        BotaoGeral("zer", 41);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar41ActionPerformed

    private void zerar35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar35ActionPerformed
        BotaoGeral("zer", 35);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar35ActionPerformed

    private void numero43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero43ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero43ActionPerformed

    private void menos43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos43ActionPerformed
        BotaoGeral("rem", 43);        // TODO add your handling code here:
    }//GEN-LAST:event_menos43ActionPerformed

    private void mais43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais43ActionPerformed
        BotaoGeral("add",43);        // TODO add your handling code here:
    }//GEN-LAST:event_mais43ActionPerformed

    private void zerar43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar43ActionPerformed
        BotaoGeral("zer", 43);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar43ActionPerformed

    private void menos44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos44ActionPerformed
        BotaoGeral("rem", 44);        // TODO add your handling code here:
    }//GEN-LAST:event_menos44ActionPerformed

    private void numero44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero44ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero44ActionPerformed

    private void mais44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais44ActionPerformed
        BotaoGeral("add",44);        // TODO add your handling code here:
    }//GEN-LAST:event_mais44ActionPerformed

    private void zerar44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar44ActionPerformed
        BotaoGeral("zer", 44);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar44ActionPerformed

    private void menos48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos48ActionPerformed
        BotaoGeral("rem", 48);        // TODO add your handling code here:
    }//GEN-LAST:event_menos48ActionPerformed

    private void numero48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero48ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero48ActionPerformed

    private void mais48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais48ActionPerformed
         BotaoGeral("add",48);       // TODO add your handling code here:
    }//GEN-LAST:event_mais48ActionPerformed

    private void zerar48ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar48ActionPerformed
        BotaoGeral("zer", 48);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar48ActionPerformed

    private void menos49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos49ActionPerformed
        BotaoGeral("rem", 49);        // TODO add your handling code here:
    }//GEN-LAST:event_menos49ActionPerformed

    private void numero49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero49ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero49ActionPerformed

    private void mais49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais49ActionPerformed
         BotaoGeral("add",49);       // TODO add your handling code here:
    }//GEN-LAST:event_mais49ActionPerformed

    private void zerar49ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar49ActionPerformed
        BotaoGeral("zer", 49);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar49ActionPerformed

    private void menos50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos50ActionPerformed
        BotaoGeral("rem", 50);        // TODO add your handling code here:
    }//GEN-LAST:event_menos50ActionPerformed

    private void menos45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos45ActionPerformed
        BotaoGeral("rem", 45);        // TODO add your handling code here:
    }//GEN-LAST:event_menos45ActionPerformed

    private void numero50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero50ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero50ActionPerformed

    private void numero45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero45ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero45ActionPerformed

    private void mais50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais50ActionPerformed
         BotaoGeral("add",50);       // TODO add your handling code here:
    }//GEN-LAST:event_mais50ActionPerformed

    private void mais45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais45ActionPerformed
        BotaoGeral("add",45);        // TODO add your handling code here:
    }//GEN-LAST:event_mais45ActionPerformed

    private void zerar50ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar50ActionPerformed
        BotaoGeral("zer", 50);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar50ActionPerformed

    private void zerar45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar45ActionPerformed
        BotaoGeral("zer", 45);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar45ActionPerformed

    private void menos46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos46ActionPerformed
        BotaoGeral("rem", 46);        // TODO add your handling code here:
    }//GEN-LAST:event_menos46ActionPerformed

    private void numero51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero51ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero51ActionPerformed

    private void numero46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero46ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero46ActionPerformed

    private void mais51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais51ActionPerformed
         BotaoGeral("add",51);       // TODO add your handling code here:
    }//GEN-LAST:event_mais51ActionPerformed

    private void mais46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais46ActionPerformed
         BotaoGeral("add",46);       // TODO add your handling code here:
    }//GEN-LAST:event_mais46ActionPerformed

    private void zerar51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar51ActionPerformed
         BotaoGeral("zer", 51);       // TODO add your handling code here:
    }//GEN-LAST:event_zerar51ActionPerformed

    private void zerar46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar46ActionPerformed
        BotaoGeral("zer", 46);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar46ActionPerformed

    private void menos51ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos51ActionPerformed
         BotaoGeral("rem", 51);       // TODO add your handling code here:
    }//GEN-LAST:event_menos51ActionPerformed

    private void menos47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos47ActionPerformed
        BotaoGeral("rem", 47);        // TODO add your handling code here:
    }//GEN-LAST:event_menos47ActionPerformed

    private void numero52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero52ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero52ActionPerformed

    private void numero47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero47ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero47ActionPerformed

    private void mais52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais52ActionPerformed
         BotaoGeral("add", 52);        // TODO add your handling code here:
    }//GEN-LAST:event_mais52ActionPerformed

    private void mais47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais47ActionPerformed
         BotaoGeral("add", 47);        // TODO add your handling code here:
    }//GEN-LAST:event_mais47ActionPerformed

    private void zerar52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar52ActionPerformed
        BotaoGeral("zer", 52);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar52ActionPerformed

    private void zerar47ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar47ActionPerformed
        BotaoGeral("zer", 47);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar47ActionPerformed

    private void menos52ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos52ActionPerformed
        BotaoGeral("rem", 52);        // TODO add your handling code here:
    }//GEN-LAST:event_menos52ActionPerformed

    private void menos36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos36ActionPerformed
        BotaoGeral("rem", 36);        // TODO add your handling code here:
    }//GEN-LAST:event_menos36ActionPerformed

    private void numero36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero36ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero36ActionPerformed

    private void mais36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais36ActionPerformed
          BotaoGeral("add", 36);       // TODO add your handling code here:
    }//GEN-LAST:event_mais36ActionPerformed

    private void zerar36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar36ActionPerformed
        BotaoGeral("zer", 36);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar36ActionPerformed

    private void menos42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos42ActionPerformed
        BotaoGeral("rem", 42);        // TODO add your handling code here:
    }//GEN-LAST:event_menos42ActionPerformed

    private void numero42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero42ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero42ActionPerformed

    private void mais42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais42ActionPerformed
         BotaoGeral("add", 42);        // TODO add your handling code here:
    }//GEN-LAST:event_mais42ActionPerformed

    private void zerar42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar42ActionPerformed
        BotaoGeral("zer", 42);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar42ActionPerformed

    private void check1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_check1ActionPerformed

    private void numero41KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numero41KeyTyped
        SomenteNumeros(evt);
        AtualizarCrimes();        // TODO add your handling code here:
    }//GEN-LAST:event_numero41KeyTyped

    private void numero41KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_numero41KeyReleased
        SomenteNumeros(evt);
        AtualizarCrimes();        // TODO add your handling code here:
    }//GEN-LAST:event_numero41KeyReleased

    private void check1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_check1MouseClicked
        //System.out.println("Teste Funcionou");        // TODO add your handling code here:
        
        /*if(check2.isSelected()==true && check1.isSelected()==true){
            check2.setSelected(false);
        }*/
        AtualizarCrimes();
    }//GEN-LAST:event_check1MouseClicked

    private void check2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_check2MouseClicked
        /*if(check2.isSelected()==true && check1.isSelected()==true){
            check1.setSelected(false);
        }*/
        AtualizarCrimes();        // TODO add your handling code here:
    }//GEN-LAST:event_check2MouseClicked

    private void resetarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetarActionPerformed
       ResetarCampos(true);
       AttBancos();
       AtualizarCrimes();
       id.requestFocus();
    }//GEN-LAST:event_resetarActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        new VerCrimes().setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        new Painel().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        new Sobre().setVisible(true);
        /*if(!policia.sobre.isVisible()){
            policia.sobre.setVisible(true);
        }else{
            policia.sobre.requestFocus();
        }*/
        //this.dispose();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void registrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registrarActionPerformed
        ConexaoDB conexao = new ConexaoDB();
        
        JSONObject my_obj = new JSONObject();
        Random rand = new Random(); 
        String Procol = (rand.nextInt(8999)+1000)+""; 
        Calendar cal = Calendar.getInstance(); 
        cal.getTime(); 
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        System.out.println(" / "+sdf.format(cal.getTime()));
        Procol=sdf.format(cal.getTime())+Procol;
        
        Protocolor=Procol;
        System.out.println("Procol: "+Procol+" / Protocolor: "+Protocolor);
        
        my_obj.put("passaporte", prenderDBarray.getString("passaporte"));
        
        my_obj.put("motivo", prisaorega.toString());
        my_obj.put("id_prendeu", prenderDBarray.getString("id_prendeu"));
        my_obj.put("protocolo", Procol);
        my_obj.put("meses", meserpresor+"");
        my_obj.put("multas", multapresor+"");
        my_obj.put("justificado", crimes.getText());
        my_obj.put("contravencoes", contrav.getText());
        boolean discorde=false;
        int bloqueado=0;
        String discordt;
        for(int i = 0; i < discordDBarray.length(); i++){
            JSONObject o = discordDBarray.getJSONObject(i);
            int paser = prenderDBarray.getInt("passaporte"); //Integer.parseInt(tpassa)
            if(paser==o.getInt("user_id")){
                for(int i2 = 0; i2 < blacklistDBarray.length(); i2++){
                    JSONObject o2 = blacklistDBarray.getJSONObject(i2);
                    if(o2.getInt("user_id") == paser){
                        bloqueado=o2.getInt("bloqueado");
                    }
                }
                discordt = o.getString("identifier");
                my_obj.put("discord", discordt);
                discorde=true;
            }
        }
        
        if(conexao.SalvarPrisao(my_obj)){
            showMessageDialog(null,
                prenderDBarray.getString("nome")+" foi registrado no banco da LSPD com o protocolo: "+Procol+" !",
                "Salvo com sucesso!!",
                JOptionPane.PLAIN_MESSAGE);
            
        }
        //PassaPreso=0;
        //ResetarCampos();
        //AtualizarCrimes();
        //AttBancos();
        feitoprisao=true;
        AttBancos();
        AtualizarCrimes();
        copiar.setEnabled(true);
        copiar1.setEnabled(true);
        registrar.setEnabled(false);
        
        id.requestFocus();
        
        
        /*
        for(int i = 0; i < usuariosDBarray.length(); i++){
            JSONObject o = usuariosDBarray.getJSONObject(i);
            if(PassaPreso == o.getInt("id")){
                int pass = o.getInt("id");
                String discord = o.getString("discord");
                String nome = o.getString("nome")+" "+o.getString("sobrenome");
                //System.out.println("usuariosDBarray: "+o.toString()+" ?°/ ");
                my_obj.put("passaporte", id);
                my_obj.put("registration", o.getString("registration"));

                my_obj.put("nome", o.getString("discord"));
                my_obj.put("sobrenome", o.getString("discord"));
                
            }
        }*/
        
        
        
        
        /*int ptet = Integer.parseInt(novaptt);
        if(ptet==99){
            my_obj.put("lspd", 0);
        }else{
            my_obj.put("lspd", 1);
        }
        if(conexao.SalvarPtt("hierarquia", my_obj)){
            
        }*/
        
    }//GEN-LAST:event_registrarActionPerformed

    private void idKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            AttInfo();
        }
    }//GEN-LAST:event_idKeyPressed

    private void copiar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copiar1ActionPerformed
        String myString = "```Protocolo: "+Protocolor+"```";
        StringSelection stringSelection = new StringSelection(myString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }//GEN-LAST:event_copiar1ActionPerformed

    private void verpresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verpresoActionPerformed
        if(!verprisoes.isVisible()){
            verprisoes.setVisible(true);
            verprisoes.ResetarCampos();
            verprisoes.id.setText(prenderDBarray.getString("passaporte"));
            verprisoes.id.setEnabled(false);
            verprisoes.resetar.setEnabled(false);
            verprisoes.PesquisarT();
        }else{
            verprisoes.requestFocus();
        }
    }//GEN-LAST:event_verpresoActionPerformed

    /**
     * @param args the command line arguments
     */
    
    
    
    
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Procurado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Procurado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Procurado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Procurado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Procurado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox check1;
    private javax.swing.JCheckBox check2;
    private javax.swing.JTextField comandos;
    private javax.swing.JLabel contrav;
    private javax.swing.JButton copiar;
    private javax.swing.JButton copiar1;
    private javax.swing.JTextField crimes;
    private java.awt.TextArea discord;
    private javax.swing.JTextField fazeroq;
    private javax.swing.JTextField id;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator20;
    private javax.swing.JSeparator jSeparator21;
    private javax.swing.JSeparator jSeparator22;
    private javax.swing.JSeparator jSeparator23;
    private javax.swing.JSeparator jSeparator24;
    private javax.swing.JSeparator jSeparator25;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JButton mais1;
    private javax.swing.JButton mais10;
    private javax.swing.JButton mais2;
    private javax.swing.JButton mais21;
    private javax.swing.JButton mais22;
    private javax.swing.JButton mais23;
    private javax.swing.JButton mais24;
    private javax.swing.JButton mais25;
    private javax.swing.JButton mais26;
    private javax.swing.JButton mais27;
    private javax.swing.JButton mais28;
    private javax.swing.JButton mais29;
    private javax.swing.JButton mais3;
    private javax.swing.JButton mais30;
    private javax.swing.JButton mais31;
    private javax.swing.JButton mais32;
    private javax.swing.JButton mais33;
    private javax.swing.JButton mais34;
    private javax.swing.JButton mais35;
    private javax.swing.JButton mais36;
    private javax.swing.JButton mais37;
    private javax.swing.JButton mais38;
    private javax.swing.JButton mais39;
    private javax.swing.JButton mais4;
    private javax.swing.JButton mais40;
    private javax.swing.JButton mais42;
    private javax.swing.JButton mais43;
    private javax.swing.JButton mais44;
    private javax.swing.JButton mais45;
    private javax.swing.JButton mais46;
    private javax.swing.JButton mais47;
    private javax.swing.JButton mais48;
    private javax.swing.JButton mais49;
    private javax.swing.JButton mais5;
    private javax.swing.JButton mais50;
    private javax.swing.JButton mais51;
    private javax.swing.JButton mais52;
    private javax.swing.JButton mais6;
    private javax.swing.JButton mais7;
    private javax.swing.JButton mais8;
    private javax.swing.JButton mais9;
    private javax.swing.JButton menos1;
    private javax.swing.JButton menos10;
    private javax.swing.JButton menos2;
    private javax.swing.JButton menos21;
    private javax.swing.JButton menos22;
    private javax.swing.JButton menos23;
    private javax.swing.JButton menos24;
    private javax.swing.JButton menos25;
    private javax.swing.JButton menos26;
    private javax.swing.JButton menos27;
    private javax.swing.JButton menos28;
    private javax.swing.JButton menos29;
    private javax.swing.JButton menos3;
    private javax.swing.JButton menos30;
    private javax.swing.JButton menos31;
    private javax.swing.JButton menos32;
    private javax.swing.JButton menos33;
    private javax.swing.JButton menos34;
    private javax.swing.JButton menos35;
    private javax.swing.JButton menos36;
    private javax.swing.JButton menos37;
    private javax.swing.JButton menos38;
    private javax.swing.JButton menos39;
    private javax.swing.JButton menos4;
    private javax.swing.JButton menos40;
    private javax.swing.JButton menos42;
    private javax.swing.JButton menos43;
    private javax.swing.JButton menos44;
    private javax.swing.JButton menos45;
    private javax.swing.JButton menos46;
    private javax.swing.JButton menos47;
    private javax.swing.JButton menos48;
    private javax.swing.JButton menos49;
    private javax.swing.JButton menos5;
    private javax.swing.JButton menos50;
    private javax.swing.JButton menos51;
    private javax.swing.JButton menos52;
    private javax.swing.JButton menos6;
    private javax.swing.JButton menos7;
    private javax.swing.JButton menos8;
    private javax.swing.JButton menos9;
    private javax.swing.JTextField mesestotal;
    private javax.swing.JTextField multatotal;
    private javax.swing.JTextField numero1;
    private javax.swing.JTextField numero10;
    private javax.swing.JTextField numero2;
    private javax.swing.JTextField numero21;
    private javax.swing.JTextField numero22;
    private javax.swing.JTextField numero23;
    private javax.swing.JTextField numero24;
    private javax.swing.JTextField numero25;
    private javax.swing.JTextField numero26;
    private javax.swing.JTextField numero27;
    private javax.swing.JTextField numero28;
    private javax.swing.JTextField numero29;
    private javax.swing.JTextField numero3;
    private javax.swing.JTextField numero30;
    private javax.swing.JTextField numero31;
    private javax.swing.JTextField numero32;
    private javax.swing.JTextField numero33;
    private javax.swing.JTextField numero34;
    private javax.swing.JTextField numero35;
    private javax.swing.JTextField numero36;
    private javax.swing.JTextField numero37;
    private javax.swing.JTextField numero38;
    private javax.swing.JTextField numero39;
    private javax.swing.JTextField numero4;
    private javax.swing.JTextField numero40;
    private javax.swing.JTextField numero41;
    private javax.swing.JTextField numero42;
    private javax.swing.JTextField numero43;
    private javax.swing.JTextField numero44;
    private javax.swing.JTextField numero45;
    private javax.swing.JTextField numero46;
    private javax.swing.JTextField numero47;
    private javax.swing.JTextField numero48;
    private javax.swing.JTextField numero49;
    private javax.swing.JTextField numero5;
    private javax.swing.JTextField numero50;
    private javax.swing.JTextField numero51;
    private javax.swing.JTextField numero52;
    private javax.swing.JTextField numero6;
    private javax.swing.JTextField numero7;
    private javax.swing.JTextField numero8;
    private javax.swing.JTextField numero9;
    private javax.swing.JButton registrar;
    private javax.swing.JButton resetar;
    private javax.swing.JLabel txt48;
    private javax.swing.JLabel txtA;
    private javax.swing.JLabel txtA1;
    private javax.swing.JLabel txtA10;
    private javax.swing.JLabel txtA11;
    private javax.swing.JLabel txtA12;
    private javax.swing.JLabel txtA13;
    private javax.swing.JLabel txtA15;
    private javax.swing.JLabel txtA16;
    private javax.swing.JLabel txtA17;
    private javax.swing.JLabel txtA18;
    private javax.swing.JLabel txtA2;
    private javax.swing.JLabel txtA28;
    private javax.swing.JLabel txtA29;
    private javax.swing.JLabel txtA3;
    private javax.swing.JLabel txtA30;
    private javax.swing.JLabel txtA31;
    private javax.swing.JLabel txtA32;
    private javax.swing.JLabel txtA33;
    private javax.swing.JLabel txtA34;
    private javax.swing.JLabel txtA35;
    private javax.swing.JLabel txtA36;
    private javax.swing.JLabel txtA37;
    private javax.swing.JLabel txtA38;
    private javax.swing.JLabel txtA39;
    private javax.swing.JLabel txtA4;
    private javax.swing.JLabel txtA40;
    private javax.swing.JLabel txtA41;
    private javax.swing.JLabel txtA43;
    private javax.swing.JLabel txtA44;
    private javax.swing.JLabel txtA45;
    private javax.swing.JLabel txtA46;
    private javax.swing.JLabel txtA47;
    private javax.swing.JLabel txtA48;
    private javax.swing.JLabel txtA49;
    private javax.swing.JLabel txtA5;
    private javax.swing.JLabel txtA50;
    private javax.swing.JLabel txtA51;
    private javax.swing.JLabel txtA6;
    private javax.swing.JLabel txtA7;
    private javax.swing.JLabel txtA8;
    private javax.swing.JLabel txtA9;
    private javax.swing.JButton verpreso;
    private javax.swing.JButton zerar1;
    private javax.swing.JButton zerar10;
    private javax.swing.JButton zerar2;
    private javax.swing.JButton zerar21;
    private javax.swing.JButton zerar22;
    private javax.swing.JButton zerar23;
    private javax.swing.JButton zerar24;
    private javax.swing.JButton zerar25;
    private javax.swing.JButton zerar26;
    private javax.swing.JButton zerar27;
    private javax.swing.JButton zerar28;
    private javax.swing.JButton zerar29;
    private javax.swing.JButton zerar3;
    private javax.swing.JButton zerar30;
    private javax.swing.JButton zerar31;
    private javax.swing.JButton zerar32;
    private javax.swing.JButton zerar33;
    private javax.swing.JButton zerar34;
    private javax.swing.JButton zerar35;
    private javax.swing.JButton zerar36;
    private javax.swing.JButton zerar37;
    private javax.swing.JButton zerar38;
    private javax.swing.JButton zerar39;
    private javax.swing.JButton zerar4;
    private javax.swing.JButton zerar40;
    private javax.swing.JButton zerar41;
    private javax.swing.JButton zerar42;
    private javax.swing.JButton zerar43;
    private javax.swing.JButton zerar44;
    private javax.swing.JButton zerar45;
    private javax.swing.JButton zerar46;
    private javax.swing.JButton zerar47;
    private javax.swing.JButton zerar48;
    private javax.swing.JButton zerar49;
    private javax.swing.JButton zerar5;
    private javax.swing.JButton zerar50;
    private javax.swing.JButton zerar51;
    private javax.swing.JButton zerar52;
    private javax.swing.JButton zerar6;
    private javax.swing.JButton zerar7;
    private javax.swing.JButton zerar8;
    private javax.swing.JButton zerar9;
    // End of variables declaration//GEN-END:variables
}
