/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police;

import java.awt.Toolkit;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import police.configs.ConexaoDB;
import police.configs.Config;
import police.configs.DiscordMessage;

/**
 *
 * @author John
 */
public class SplashScreen extends javax.swing.JFrame {

    /**
     * Creates new form VerCrimes
     */
    public int ValorProgresso=0;
    public int ProgressoAtual=0;
    public int ContandoFalhas=0;
    public boolean Fechar=false;
    public boolean Valendo=false;
    public int ContandoMargem=0;
    
    JSONArray ServidoresRegistrados = new JSONArray();
    
    JSONArray vrp_users = new JSONArray();
    
    public SplashScreen() {
        initComponents();
        EscolherCidadePainel.setVisible(false);
        pack();
        this.setLocationRelativeTo(null);
        //this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagens/mclost_1.png")));
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagens/RC.png")));
        //BACKGROUND ICONS
        
        getContentPane().setBackground(new java.awt.Color(13, 32, 64));
        TituloPainel.setBackground(new java.awt.Color(13, 32, 64));
        ProgressoPainel.setBackground(new java.awt.Color(13, 32, 64));
        EscolherCidadePainel.setBackground(new java.awt.Color(13, 32, 64));
        //patt.setBackground(new java.awt.Color(30, 11, 67));
        IniciarSobre();
        this.setVisible(true);
        
        //setVisible(true);
        ValorProgresso=30;
        
        texto.setText("INICIANDO CONTATO COM A CENTRAL");
        // TODO code application logic here
        
        JDA jdab = null;
        try {
            jdab = new JDABuilder("Njc2MTI5MDkyMTkzNzQ2OTUy.XkmjLg.h4ovOM7x8gUa6P2hGmy5NskEkA4")         // The token of the account that is logging in.
                    .addEventListeners(new DiscordMessage())  // An instance of a class that will handle events.
                    .build();
        } catch (LoginException ex) {
            Logger.getLogger(DiscordMessage.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }
        try {
            jdab.awaitReady(); // Blocking guarantees that JDA will be completely loaded.
        } catch (InterruptedException ex) {
            Logger.getLogger(SplashScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        InicializadorMain.jda = jdab;
        //System.out.println("Building JDA finalizado!");
        
        ProgressoAtual=ValorProgresso;
        ValorProgresso=50;
        texto.setText("TESTANDO CONEXÃO");
        if(netIsAvailable()){
            ProgressoAtual=ValorProgresso;
            ValorProgresso=65;
            texto.setText("VERIFICANDO INTEGRIDADE DOS REGISTROS");

            if(PegarInfoServidor()){
                ProgressoPainel.setVisible(false);
                EscolherCidadePainel.setVisible(true);
                this.revalidate();
                this.repaint();
                this.pack();
            }else{

            }
        }else{
            ProgressoAtual=100;
            ValorProgresso=100;
            texto.setText("CONEXÃO FALHOU");
        }
    }
    public boolean IniciarSobre(){
        Config config;
        config = new Config();
        String versao = config.versao;
        int build = config.build;
        
        int ano = build / 10000;
        int mes = (build / 100)%100;
        int dia = build % 100;
        String De=dia+"";
        String Me=mes+"";
        String An=ano+"";
        if(dia<10)De="0"+De; if(mes<10)Me="0"+Me; if(ano<10)An="0"+An;
        String Att = De+"/"+Me+"/"+An;
        atualizadot.setText("(ATUALIZADO EM: "+Att+")");
        Timere();
        
        //if(config.VerificarAtt()) txtAtt.setText("ATUALIZAÇÃO DISPONÍVEL PARA: "+config.getVersao());
        return true;
    }
    public void Timere (){
        int delay = 500;   // tempo de espera antes da 1ª execução da tarefa.
        int interval = 500;  // intervalo no qual a tarefa será executada.
        Timer timer = new Timer();
        Random gerador = new Random();
        timer.scheduleAtFixedRate(new TimerTask() {
          public void run() {
            //System.out.print("CONTANDO: "+ValorProgresso+ " /////////// ");
            
            if(ValorProgresso>0){
                if(ValorProgresso > ProgressoAtual){
                    ProgressoAtual+=2+gerador.nextInt(10);
                }else{
                    ContandoFalhas++;
                    //System.out.print("ContandoFalhas: "+ContandoFalhas);
                }
            }else{
                ContandoMargem++;
            }
            if(ProgressoAtual>=100){
                ProgressoAtual=100;
                ContandoFalhas=0;
                timer.cancel();
                //progresso.setIndeterminate(true);
            }
            progresso.setValue(ProgressoAtual);
            if(ContandoFalhas > 36){
                texto.setText("CONEXÃO EXTREMAMENTE LENTA");
                if(ContandoFalhas > 60) texto.setText("FINALIZANDO PROGRAMA POR FALTA DE CONEXÃO");
                if(ContandoFalhas > 70) System.exit(0);
            }
            if(Fechar || ContandoMargem > 10)timer.cancel();
          }
        }, delay, interval);
    }
    private static boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }
    
    public boolean PegarInfoServidor(){
        ConexaoDB conexao = new ConexaoDB();
        ResultSet resulteSet = null;
        resulteSet = conexao.GetPersonalizado("select * from cb_servers ORDER BY id DESC");
        
        try {
            while (resulteSet.next()) {
                JSONObject getTemporario2 = new JSONObject();
                getTemporario2.put("id", resulteSet.getInt("id"));
                getTemporario2.put("nome_cidade", resulteSet.getString("nome_cidade"));
                getTemporario2.put("nome_policia", resulteSet.getString("nome_policia"));
                getTemporario2.put("nome_policia_abv", resulteSet.getString("nome_policia_abv"));
                getTemporario2.put("db_host", resulteSet.getString("db_host"));
                getTemporario2.put("db_banco", resulteSet.getString("db_banco"));
                getTemporario2.put("db_user", resulteSet.getString("db_user"));
                getTemporario2.put("db_senha", resulteSet.getString("db_senha"));
                ServidoresRegistrados.put(getTemporario2);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        AtualizarCidadesJCombo();
        return true;
    }
    public void AtualizarCidadesJCombo(){
        CidadesEscolha.removeAllItems();
        for(int i = 0; i < ServidoresRegistrados.length(); i++){
            JSONObject obj = ServidoresRegistrados.getJSONObject(i);
            CidadesEscolha.addItem(obj.getString("nome_cidade")+" - "+obj.getString("nome_policia_abv"));
        }
    }
    
    public boolean TestarConexaoCidade(){
        ConexaoDB conexao = new ConexaoDB();
        ResultSet resulteSet = null;
        resulteSet = conexao.GetPersonalizadoCidade("select * from vrp_users ORDER BY id DESC");
        if(resulteSet == null) return false;
        try {
            while (resulteSet.next()) {
                JSONObject getTemporario2 = new JSONObject();
                getTemporario2.put("id", resulteSet.getInt("id"));
                getTemporario2.put("last_login", resulteSet.getString("last_login"));
                getTemporario2.put("ip", resulteSet.getString("ip"));
                getTemporario2.put("whitelisted", resulteSet.getInt("whitelisted"));
                getTemporario2.put("banned", resulteSet.getInt("banned"));
                vrp_users.put(getTemporario2);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        icone = new javax.swing.JLabel();
        TituloPainel = new javax.swing.JPanel();
        titulo = new javax.swing.JLabel();
        atualizadot = new javax.swing.JLabel();
        ProgressoPainel = new javax.swing.JPanel();
        progresso = new javax.swing.JProgressBar();
        texto = new javax.swing.JLabel();
        EscolherCidadePainel = new javax.swing.JPanel();
        CidadesEscolha = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        CidadeEscolhaBt = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("COMPUTADOR DE BORDO");
        setResizable(false);

        icone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/police/imagens/RC.png"))); // NOI18N

        titulo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titulo.setForeground(new java.awt.Color(255, 255, 255));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("COMPUTADOR DE BORDO");

        atualizadot.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        atualizadot.setForeground(new java.awt.Color(255, 255, 255));
        atualizadot.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        atualizadot.setText("(ATUALIZADO EM 25/10/19)");

        javax.swing.GroupLayout TituloPainelLayout = new javax.swing.GroupLayout(TituloPainel);
        TituloPainel.setLayout(TituloPainelLayout);
        TituloPainelLayout.setHorizontalGroup(
            TituloPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TituloPainelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TituloPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(atualizadot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(titulo, javax.swing.GroupLayout.DEFAULT_SIZE, 326, Short.MAX_VALUE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        TituloPainelLayout.setVerticalGroup(
            TituloPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TituloPainelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(atualizadot)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        texto.setForeground(new java.awt.Color(255, 255, 255));
        texto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        texto.setText("...");

        javax.swing.GroupLayout ProgressoPainelLayout = new javax.swing.GroupLayout(ProgressoPainel);
        ProgressoPainel.setLayout(ProgressoPainelLayout);
        ProgressoPainelLayout.setHorizontalGroup(
            ProgressoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProgressoPainelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ProgressoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(texto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(progresso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        ProgressoPainelLayout.setVerticalGroup(
            ProgressoPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ProgressoPainelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(progresso, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(texto)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        CidadesEscolha.setMaximumRowCount(20);
        CidadesEscolha.setNextFocusableComponent(CidadeEscolhaBt);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel1.setText("ESCOLHA A CIDADE:");

        CidadeEscolhaBt.setText("ENTRAR");
        CidadeEscolhaBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CidadeEscolhaBtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EscolherCidadePainelLayout = new javax.swing.GroupLayout(EscolherCidadePainel);
        EscolherCidadePainel.setLayout(EscolherCidadePainelLayout);
        EscolherCidadePainelLayout.setHorizontalGroup(
            EscolherCidadePainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EscolherCidadePainelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EscolherCidadePainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(EscolherCidadePainelLayout.createSequentialGroup()
                        .addComponent(CidadesEscolha, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CidadeEscolhaBt, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)))
                .addContainerGap())
        );
        EscolherCidadePainelLayout.setVerticalGroup(
            EscolherCidadePainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EscolherCidadePainelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(EscolherCidadePainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CidadesEscolha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CidadeEscolhaBt))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(icone)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(EscolherCidadePainel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(ProgressoPainel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(TituloPainel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(TituloPainel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ProgressoPainel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(EscolherCidadePainel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(icone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CidadeEscolhaBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CidadeEscolhaBtActionPerformed
        int IndexSel = CidadesEscolha.getSelectedIndex();
        String IndexStr = CidadesEscolha.getSelectedItem()+"";
        if(IndexSel >= 0){
            boolean AchouCity = false;
            String nomedacidade = "";
            for(int i = 0; i < ServidoresRegistrados.length(); i++){
                JSONObject obj = ServidoresRegistrados.getJSONObject(i);
                String FormatNome = obj.getString("nome_cidade")+" - "+obj.getString("nome_policia_abv");
                if(FormatNome.equals(IndexStr)){
                    SetarBancoServidor(obj.getString("db_host"), obj.getString("db_banco"), obj.getString("db_user"), obj.getString("db_senha"), obj.getInt("id"));
                    AchouCity = true;
                    nomedacidade = obj.getString("nome_cidade");
                }
            }
            
            if(AchouCity){
                ProgressoPainel.setVisible(true);
                EscolherCidadePainel.setVisible(false);
                this.revalidate();
                this.repaint();
                this.pack();
                ProgressoAtual=ValorProgresso;
                ValorProgresso=80;
                ContandoFalhas=0;
                texto.setText("MONTANDO INTERFACE POLICIAL");
                if(TestarConexaoCidade()){
                    ProgressoAtual=ValorProgresso;
                    ValorProgresso=100;
                    texto.setText("FAZENDO ÚLTIMOS AJUSTES");
                    ProgressoAtual=100;
                    texto.setText("CONCLUINDO");
                    Login logins = new Login();
                    logins.setVisible(true);
                    Fechar=true;
                    InicializadorMain.vrp_users = vrp_users;
                    this.dispose();
                }else{
                    ProgressoAtual=0;
                    ValorProgresso=0;
                    texto.setText("ERRO NO BANCO DE DADOS DA CIDADE: "+nomedacidade.toUpperCase());
                    texto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
                    Fechar=true;
                }
            }else{
                PegarInfoServidor();
            }
        }else{
            
        }
    }//GEN-LAST:event_CidadeEscolhaBtActionPerformed

    public boolean SetarBancoServidor(String s_host, String s_banco, String s_user, String s_senha, int server_ide){
        InicializadorMain.host_server = s_host;
        InicializadorMain.banco_server = s_banco;
        InicializadorMain.user_server = s_user;
        InicializadorMain.pass_server = s_senha;
        InicializadorMain.server_id = server_ide;
        return true;
    }
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
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SplashScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SplashScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CidadeEscolhaBt;
    private javax.swing.JComboBox<String> CidadesEscolha;
    private javax.swing.JPanel EscolherCidadePainel;
    private javax.swing.JPanel ProgressoPainel;
    private javax.swing.JPanel TituloPainel;
    private javax.swing.JLabel atualizadot;
    private javax.swing.JLabel icone;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JProgressBar progresso;
    private javax.swing.JLabel texto;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
