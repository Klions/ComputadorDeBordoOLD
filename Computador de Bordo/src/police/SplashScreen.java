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
import javax.swing.ImageIcon;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import police.configs.ConexaoDB;
import police.configs.Config;
import police.configs.DiscordMessage;
import police.configs.GetImages;
import police.configs.HttpDownloadUtility;
import police.configs.SNWindows;
import police.configs.Usuario;

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
    
    boolean PegarDados=false;
    
    JSONArray ServidoresRegistrados = new JSONArray();
    
    JSONArray vrp_users = new JSONArray();
    JSONArray cb_users = new JSONArray();
    
    public SplashScreen() {
        initComponents();
        EscolherCidadePainel.setVisible(false);
        Att.setVisible(false);
        pack();
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagens/CB2.png")));
        //this.setIconImage(new ImageIcon(GetImages.LogoCB).getImage());
        
        //BACKGROUND ICONS
        
        getContentPane().setBackground(new java.awt.Color(13, 32, 64));
        TituloPainel.setBackground(new java.awt.Color(13, 32, 64));
        ProgressoPainel.setBackground(new java.awt.Color(13, 32, 64));
        EscolherCidadePainel.setBackground(new java.awt.Color(13, 32, 64));
        //patt.setBackground(new java.awt.Color(30, 11, 67));
        IniciarSobre();
        this.setVisible(true);
        SNWindows.getSerialNumber();
        
        //setVisible(true);
        ValorProgresso=30;
        
        texto.setText("INICIANDO CONTATO COM A CENTRAL");
        // TODO code application logic here
        
        JDA jdab = null;
        try {
            jdab = new JDABuilder(HttpDownloadUtility.SetCon("TlRReE1USXhNekl3TmpjeE5URXdOVEk0LlhGVWtnQS5nY3V4TW1zNTdkYk5LZmQ5ZTdsZFBlVFVBQTA="))
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
            try {
                CarregarMysql();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            SNWindows.PegarIP();
            
            ProgressoAtual=ValorProgresso;
            ValorProgresso=65;
            texto.setText("VERIFICANDO INTEGRIDADE DOS REGISTROS");

            if(PegarInfoServidor()){
                VerAtt();
                ContandoFalhas = 0;
                ProgressoPainel.setVisible(false);
                EscolherCidadePainel.setVisible(true);
                VerificarEstaRodando();
                this.revalidate();
                this.repaint();
                this.pack();
            }else{

            }
        }else{
            ProgressoAtual=100;
            ValorProgresso=100;
            texto.setText("FALHA NA CONEXÃO");
        }
    }
    
    public void VerificarEstaRodando(){
        String Build = Config.getBuild()+"";
        String Porta = "9999";
        if(Build.length() >= 8){
            Porta = "99"+Build.substring(6, 8);
        }
        System.out.println("Porta: "+Porta);
        SNWindows.checkIfRunning(Integer.parseInt(Porta));
    }
    
    public void VerAtt(){
        if(Config.VerificarAtt()){
            Att.setVisible(true);
            boolean Neces = Config.getNeed();
            EntrarOffline.setEnabled(!Neces);
            CidadeEscolhaBt.setEnabled(!Neces);
            if(Neces){
                EntrarOffline.setToolTipText("NECESSÁRIO ATUALIZAR PARA PROSSEGUIR");
                CidadeEscolhaBt.setToolTipText("NECESSÁRIO ATUALIZAR PARA PROSSEGUIR");
                AttInfo.setText("ATUALIZAÇÃO NECESSÁRIA PARA CONTINUAR");
            }else{
                AttInfo.setText("VERSÃO DA ATUALIZAÇÃO: "+Config.getVersao());
            }
        }
    }
    
    public static void CarregarMysql() throws SQLException{
        ConexaoDB conexao = new ConexaoDB();
        conexao.ConfigCarregar();
    }
    public boolean IniciarSobre(){
        String versao = Config.versao;
        int build = Config.build_atual;
        
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
    Timer timer = new Timer();
    public void Timere(){
        int delay = 500;   // tempo de espera antes da 1ª execução da tarefa.
        int interval = 500;  // intervalo no qual a tarefa será executada.
        
        
        timer.scheduleAtFixedRate(new TimerTask() {
          public void run() {
            AttTimer();
          }
        }, delay, interval);
    }
    private void AttTimer(){
        Random gerador = new Random();
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
        if(PegarDados){
            PegarDados=false;
            ProgressoPainel.setVisible(true);
            EscolherCidadePainel.setVisible(false);
            this.revalidate();
            this.repaint();
            this.pack();
            ProgressoAtual=ValorProgresso;
            ValorProgresso=80;
            progresso.setValue(ProgressoAtual);
            ContandoFalhas=0;
            texto.setText("MONTANDO INTERFACE POLICIAL");
            if(TestarConexaoCidade() && PegarContas()){
                ProgressoAtual=ValorProgresso;
                ValorProgresso=90;
                progresso.setValue(ProgressoAtual);
                texto.setText("FAZENDO ÚLTIMOS AJUSTES");

                ProgressoAtual=ValorProgresso;
                progresso.setValue(ProgressoAtual);
                InicializadorMain.vrp_users = vrp_users;
                InicializadorMain.cb_users = cb_users;
                InicializadorMain.AttDbsStatic();
                ProgressoAtual=100;
                progresso.setValue(ProgressoAtual);
                texto.setText("CONCLUINDO");
                Fechar=true;
                Login logins = new Login();
                logins.setVisible(true);
                
                this.dispose();
            }else{
                ProgressoAtual=0;
                ValorProgresso=0;
                texto.setText("ERRO NO BANCO DE DADOS DA CIDADE");
                texto.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
                Fechar=true;
            }
        }
        if(Fechar || ContandoMargem > 10)timer.cancel();
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
                getTemporario2.put("url_logo", resulteSet.getString("url_logo"));
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
        resulteSet = conexao.GetPersonalizadoCidade("select * from vrp_users ORDER BY id");
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
    
    public boolean PegarContas(){
        ConexaoDB conexao = new ConexaoDB();
        ResultSet resulteSet = null;
        resulteSet = conexao.GetPersonalizadoCidade("select * from cb_users ORDER BY user_id");
        if(resulteSet == null) return false;
        try {
            while (resulteSet.next()) {
                JSONObject getTemporario2 = new JSONObject();
                getTemporario2.put("user_id", resulteSet.getInt("user_id"));
                getTemporario2.put("codigo", resulteSet.getString("codigo"));
                getTemporario2.put("permissao", resulteSet.getInt("permissao"));
                getTemporario2.put("ultimologin", resulteSet.getString("ultimologin"));
                cb_users.put(getTemporario2);
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
        EntrarOffline = new javax.swing.JButton();
        Att = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        AttBts = new javax.swing.JPanel();
        AttAgora = new javax.swing.JButton();
        AttSite = new javax.swing.JButton();
        AttInfo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("COMPUTADOR DE BORDO");
        setResizable(false);

        icone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/police/imagens/CB.png"))); // NOI18N

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
                .addGroup(TituloPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(atualizadot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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

        ProgressoPainel.setPreferredSize(new java.awt.Dimension(359, 122));

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
                .addGap(38, 38, 38)
                .addComponent(progresso, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(texto)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        CidadesEscolha.setMaximumRowCount(20);
        CidadesEscolha.setNextFocusableComponent(CidadeEscolhaBt);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ESCOLHA A CIDADE:");

        CidadeEscolhaBt.setBackground(new java.awt.Color(255, 255, 255));
        CidadeEscolhaBt.setText("ENTRAR");
        CidadeEscolhaBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CidadeEscolhaBtActionPerformed(evt);
            }
        });

        EntrarOffline.setBackground(new java.awt.Color(255, 255, 255));
        EntrarOffline.setText("ENTRAR NO MODO OFFLINE");
        EntrarOffline.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        EntrarOffline.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        EntrarOffline.setMargin(new java.awt.Insets(200, 14, 200, 14));
        EntrarOffline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EntrarOfflineActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EscolherCidadePainelLayout = new javax.swing.GroupLayout(EscolherCidadePainel);
        EscolherCidadePainel.setLayout(EscolherCidadePainelLayout);
        EscolherCidadePainelLayout.setHorizontalGroup(
            EscolherCidadePainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EscolherCidadePainelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EscolherCidadePainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(EntrarOffline, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(EntrarOffline, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Att.setBackground(new java.awt.Color(222, 82, 82));

        jLabel2.setFont(new java.awt.Font("Arial Narrow", 0, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("ATUALIZAÇÃO DISPONÍVEL");

        AttBts.setOpaque(false);

        AttAgora.setBackground(new java.awt.Color(0, 204, 255));
        AttAgora.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AttAgora.setText("ATUALIZAR AGORA");
        AttAgora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AttAgoraActionPerformed(evt);
            }
        });

        AttSite.setBackground(new java.awt.Color(255, 255, 204));
        AttSite.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AttSite.setText("BAIXAR PELO NAVEGADOR");
        AttSite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AttSiteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AttBtsLayout = new javax.swing.GroupLayout(AttBts);
        AttBts.setLayout(AttBtsLayout);
        AttBtsLayout.setHorizontalGroup(
            AttBtsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AttBtsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AttAgora, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 19, Short.MAX_VALUE)
                .addComponent(AttSite, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        AttBtsLayout.setVerticalGroup(
            AttBtsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AttBtsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AttBtsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AttAgora, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AttSite, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        AttInfo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        AttInfo.setForeground(new java.awt.Color(255, 255, 255));
        AttInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AttInfo.setText(" ");

        javax.swing.GroupLayout AttLayout = new javax.swing.GroupLayout(Att);
        Att.setLayout(AttLayout);
        AttLayout.setHorizontalGroup(
            AttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AttLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AttBts, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AttInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        AttLayout.setVerticalGroup(
            AttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AttLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AttBts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AttInfo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(icone, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(EscolherCidadePainel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ProgressoPainel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TituloPainel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(Att, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Att, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(icone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(TituloPainel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ProgressoPainel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(EscolherCidadePainel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CidadeEscolhaBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CidadeEscolhaBtActionPerformed
        int IndexSel = CidadesEscolha.getSelectedIndex();
        String IndexStr = CidadesEscolha.getSelectedItem()+"";
        if(IndexSel >= 0){
            String nomedacidade = "";
            for(int i = 0; i < ServidoresRegistrados.length(); i++){
                JSONObject obj = ServidoresRegistrados.getJSONObject(i);
                String FormatNome = obj.getString("nome_cidade")+" - "+obj.getString("nome_policia_abv");
                if(FormatNome.equals(IndexStr)){
                    /*HttpDownloadUtility.WebhookLog(
                        "https://discordapp.com/api/webhooks/754076621581058060/9Ek0Q-VumXWVyZhEzl_pFmvMmia9nrgOL05wqJ2ggyAguRZw19282ByKBpZfyY_fmTFX", 
                        "Novo Login (Cidade "+obj.getString("nome_cidade")+")", 
                        "Algum usuário entrou no Computador de Bordo da "+obj.getString("nome_policia")+".");*/
                    GetImages.PegarImagensCB(obj.getString("url_logo"));
                    SetarBancoServidor(obj.getString("db_host"), obj.getString("db_banco"), obj.getString("db_user"), obj.getString("db_senha"), obj.getInt("id"));
                    nomedacidade = obj.getString("nome_cidade");
                    
                    JSONObject getTemporario2 = new JSONObject();
                    getTemporario2.put("id", obj.getInt("id"));
                    getTemporario2.put("nome_cidade", obj.getString("nome_cidade"));
                    getTemporario2.put("nome_policia", obj.getString("nome_policia"));
                    getTemporario2.put("nome_policia_abv", obj.getString("nome_policia_abv"));
                    getTemporario2.put("url_logo", obj.getString("url_logo"));
                    InicializadorMain.info_cidade = getTemporario2;
                }
            }
            if(!"".equals(nomedacidade)){
                PegarDados=true;
            }else{
                PegarInfoServidor();
            }
        }else{
            
        }
    }//GEN-LAST:event_CidadeEscolhaBtActionPerformed

    private void EntrarOfflineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EntrarOfflineActionPerformed
        JSONObject getTemporario2 = new JSONObject();
        getTemporario2.put("id", 0);
        getTemporario2.put("nome_cidade", "Modo Offline");
        getTemporario2.put("nome_policia", "Polícia");
        getTemporario2.put("nome_policia_abv", "POL");
        InicializadorMain.info_cidade = getTemporario2;
        InicializadorMain.ModoOffline = true;
        
        JSONObject getTemporario = new JSONObject();
        getTemporario.put("id", 0);
        getTemporario.put("id_usuario", 0);
        getTemporario.put("nome", "Sem");
        getTemporario.put("sobrenome", "Registro");
        getTemporario.put("registration", "000AAA00");
        getTemporario.put("phone", "000-000");
        getTemporario.put("age", "18");
        getTemporario.put("permissao", 2);
        getTemporario.put("codigo", "000");
        getTemporario.put("ultimologin", 0);
        //Usuario usuarios = new Usuario();
        Usuario.setDados(getTemporario);
        
        Usuario.getContaPC();
        Fechar=true;
        ProgressoAtual=100;
        ContandoFalhas=0;
        
        /*HttpDownloadUtility.WebhookLog(
            "https://discordapp.com/api/webhooks/754076621581058060/9Ek0Q-VumXWVyZhEzl_pFmvMmia9nrgOL05wqJ2ggyAguRZw19282ByKBpZfyY_fmTFX", 
            "Novo Login (Modo Offline)", 
            "Algum usuário entrou no Computador de Bordo pelo Modo Offline");*/
        
        new Painel().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_EntrarOfflineActionPerformed

    private void AttAgoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AttAgoraActionPerformed
        AttInfo.setText(HttpDownloadUtility.DownloadArquivo(Config.getLink()));
    }//GEN-LAST:event_AttAgoraActionPerformed

    private void AttSiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AttSiteActionPerformed
        HttpDownloadUtility.openURL(Config.getLink());
    }//GEN-LAST:event_AttSiteActionPerformed

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
    public static void wait(int ms)
    {
        try{
            Thread.sleep(ms);
        }catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Att;
    private javax.swing.JButton AttAgora;
    private javax.swing.JPanel AttBts;
    private javax.swing.JLabel AttInfo;
    private javax.swing.JButton AttSite;
    private javax.swing.JButton CidadeEscolhaBt;
    private javax.swing.JComboBox<String> CidadesEscolha;
    private javax.swing.JButton EntrarOffline;
    private javax.swing.JPanel EscolherCidadePainel;
    private javax.swing.JPanel ProgressoPainel;
    private javax.swing.JPanel TituloPainel;
    private javax.swing.JLabel atualizadot;
    private javax.swing.JLabel icone;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    public javax.swing.JProgressBar progresso;
    public javax.swing.JLabel texto;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
