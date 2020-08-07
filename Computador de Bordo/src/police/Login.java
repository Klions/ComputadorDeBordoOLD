/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police;

import police.configs.Usuario;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import org.json.JSONArray;
import org.json.JSONObject;
import police.configs.ConexaoDB;
import police.configs.Config;
import police.configs.HttpDownloadUtility;


/**
 *
 * @author John
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    private static final int PORT = 9996;
    private static ServerSocket socket; 
   
    JSONArray usuariosDBarray = new JSONArray();
    JSONArray hierarquiaDBarray = new JSONArray();
    Usuario usuarios = new Usuario();
    Config config = new Config();
    InicializadorMain police = new InicializadorMain();
    
    int tentativas=1;
    public Login() {
        
        checkIfRunning();
        initComponents();
        this.setLocationRelativeTo(null);
        Nome.requestFocus();
        txtErro.setText(config.NomePolicia.toUpperCase());
        getContentPane().setBackground(new java.awt.Color(13, 32, 64));
        PainelLogin.setBackground(new java.awt.Color(13, 32, 64));
        att.setBackground(new java.awt.Color(222, 82, 82));
        //AttDBUsuarios();
        AttDBHierarquia();
        
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(config.img_CBIcone)));
        if(netIsAvailable()){
            try {
                CarregarMysql();
            } catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
            att.setVisible(config.VerificarAtt());
            AtualizarAtt();
        }else{
            txtAtt.setText("Sem conexão com a internet!");
            versao.setText("Versão: "+config.versao);
            build.setText("Build: "+config.build);
            download.setEnabled(false);
            mensagem.setText("Desculpe, mas sua conexão está ruim ou está nula.");
            Entrar.setEnabled(false);
        }
        pack();
    }
    
    public void AttDBUsuarios(){
        usuariosDBarray = usuarios.AttDBUsuarios();
    }
    
    public void AttDBHierarquia(){
        hierarquiaDBarray = usuarios.AttDBHierarquia();
    }
    private static void checkIfRunning() {
        try {
          //Bind to localhost adapter with a zero connection queue 
          socket = new ServerSocket(PORT,0,InetAddress.getByAddress(new byte[] {127,0,0,1}));
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
    public static void CarregarMysql() throws SQLException{
        ConexaoDB conexao = new ConexaoDB();
        conexao.ConfigCarregar();
    }
    public void AtualizarAtt(){
        versao.setText("VERSÃO: "+config.getVersao());
        build.setText("BUILD: "+config.getBuild());
        mensagem.setText(config.getMensagem());
        versaoatual.setText("Versão Atual: "+config.versao+" / Build: "+config.build);
        
        if(config.VerificarAtt()){
            Entrar.setEnabled(!config.getNeed());
        }else{
            Entrar.setEnabled(true);
        }
        if(config.getNeed()) txtAtt.setText("ATUALIZAÇÃO NECESSÁRIA");
    }
    public void TextoErro(String Texto){
        txtErro.setText(Texto);
        txtErro.setForeground(new java.awt.Color(255, 60, 60));
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
    public boolean LogarConta(){
        if(tentativas > 4){
            System.exit(0);
            return false;
        }
        if(tentativas > 3){
            tentativas++;
            TextoErro("Número de tentativas excedido.");
            //Entrar.setEnabled(false);
            Entrar.setText("FECHAR");
            return false;
        }
        //att.setVisible(true);
        //pack();
        
        
        String passaporteI = Nome.getText();
        String codigoI = Codigo.getText();
        
        if("".equals(passaporteI)  || "".equals(codigoI)){
            TextoErro("Campos vazios, digite os dados");
            return false;
        }
        int servidor_id = VerificarConta(Integer.parseInt(passaporteI), codigoI);
        System.out.println("servidor_id: "+servidor_id);
        if(servidor_id > 0){
            if(servidor_id > 1){
                if(VerificarEhPolicia(Integer.parseInt(passaporteI))){
                    PegarInfoServidor(servidor_id);
                    //System.out.println("PegarInfo: "+PegarInfo);
                    //if(PegarInfo == 1){
                        AttDBUsuarios();
                        JSONObject sal_info = PegarUsuario(Integer.parseInt(passaporteI));
                        if(sal_info != null){
                            usuarios.setDados(sal_info);
                            //System.out.println("Resultado do JSON: "+sal_info);
                            new Painel().setVisible(true);
                            this.dispose();
                            
                            police.AttDBSTodas();
                        }else{
                            TextoErro("Erro ao pegar sua conta no Banco de Dados.");
                            AttDBHierarquia();
                            return false;
                        }
                    /*}else{
                        TextoErro("Erro ao consultar DB da Cidade.");
                        System.out.println("Erro ao setar servidor da DB: "+PegarInfo);
                        return false;
                    }*/
                }else{
                    TextoErro("Desculpe, me parece que você não possui mais acesso.");
                    AttDBHierarquia();
                    return false;
                }
            }else{
                TextoErro("Desculpe, passaporte ou código incorretos.");
                tentativas++;
                return false;
            }
        }else{
            TextoErro("Desculpe, ocorreu um erro na DB.");
            tentativas++;
            //AttDBHierarquia();
            //police.AttDBUsuariosSetDiscord();
            //AttDBUsuarios();
            return false;
        }
        
        /*
        ResultSet resulteSet = null;
        resulteSet = conexao.GetPersonalizado("select * from cb_identities WHERE id='"+passaporteI+"' AND codigo='"+codigoI+"' ORDER BY id DESC");
        
        try {
            while (resulteSet.next()) {
                set_info.put("passaporte", resulteSet.getString("id"));
                set_info.put("nome", resulteSet.getString("sobrenome"));
                set_info.put("codigo", resulteSet.getString("codigo"));
                set_info.put("discord", resulteSet.getString("discord"));
                set_info.put("age", resulteSet.getInt("age"));
                set_info.put("lspd", resulteSet.getInt("lspd"));
                set_info.put("permissao", resulteSet.getInt("permissao"));
                
                conexao.AttDataLogin(Integer.parseInt(passaporteI));
                Contar++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("Resultado do JSON: "+set_info.toString());
        usuario.setDados(set_info);
        //System.out.print("getDados: "+usuario.getDados());
        
        // PEGAR UNICO DADO DO USUÁRIO
        /*
        JSONObject obj = new JSONObject(usuario.getDados());
        String pageName = obj.getString("passaporte");
        */

        //System.out.println(pageName);
        
        //System.out.print(" / getDados: "+pageName);
        //usuario.savePreference("aaaaa");
        return true;
    }
    
    public int VerificarConta(int Login, String Senha){
        ConexaoDB conexao = new ConexaoDB();
        ResultSet resulteSet = null;
        resulteSet = conexao.GetPersonalizado("select * from cb_identities WHERE user_id="+Login+" AND codigo='"+Senha+"' ORDER BY id DESC");
        
        try {
            while (resulteSet.next()) {
                int Passaporte_Login = resulteSet.getInt("user_id");
                
                if(Login == Passaporte_Login){
                    System.out.print("Login: "+Login+" / Passaporte_Login: "+Passaporte_Login);
                    conexao.AttDataLogin(Passaporte_Login);
                    return resulteSet.getInt("server_id");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public boolean VerificarEhPolicia(Integer user_id){
        for(int i2 = 0; i2 < hierarquiaDBarray.length(); i2++){
            JSONObject ohier = hierarquiaDBarray.getJSONObject(i2);
            if(user_id==ohier.getInt("id_usuario")){
                if(ohier.getInt("cargo")!=99){
                    return true;
                }else{
                    return false;
                }
            }
        }
        return false;
    }
    
    public JSONObject PegarUsuario(Integer user_id){
        for(int i = 0; i < usuariosDBarray.length(); i++){
            JSONObject data = usuariosDBarray.getJSONObject(i);
            System.out.println("user_id: "+user_id+" / data.getInt(id_usuario): "+data.getInt("id_usuario"));
            if(user_id==data.getInt("id_usuario")){
                return data;
            }
        }
        return null;
    }
    
    public boolean PegarInfoServidor(int Server_ID){
        ConexaoDB conexao = new ConexaoDB();
        ResultSet resulteSet = null;
        resulteSet = conexao.GetPersonalizado("select * from cb_servers WHERE id='"+Server_ID+"' ORDER BY id DESC");
        
        try {
            while (resulteSet.next()) {
                System.out.println("Server_ID: "+Server_ID);
                return conexao.SetarBancoServidor(resulteSet.getString("db_host"), resulteSet.getString("db_banco"), resulteSet.getString("db_user"), resulteSet.getString("db_senha"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtErro = new javax.swing.JLabel();
        PainelLogin = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        Nome = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        Entrar = new javax.swing.JButton();
        Codigo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        att = new javax.swing.JPanel();
        txtAtt = new javax.swing.JLabel();
        download = new javax.swing.JButton();
        build = new javax.swing.JLabel();
        versao = new javax.swing.JLabel();
        mensagem = new javax.swing.JLabel();
        versaoatual = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ENTRAR");
        setBackground(new java.awt.Color(51, 51, 51));
        setResizable(false);

        txtErro.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        txtErro.setForeground(new java.awt.Color(255, 255, 255));
        txtErro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtErro.setText("POLÍCIA MILITAR RAINBOW CITY");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("PASSAPORTE:");

        Nome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Nome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NomeKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                NomeKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                NomeKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("CÓDIGO:");

        Entrar.setBackground(new java.awt.Color(255, 255, 255));
        Entrar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Entrar.setForeground(new java.awt.Color(51, 51, 51));
        Entrar.setText("ENTRAR");
        Entrar.setToolTipText("");
        Entrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EntrarActionPerformed(evt);
            }
        });

        Codigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CodigoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout PainelLoginLayout = new javax.swing.GroupLayout(PainelLogin);
        PainelLogin.setLayout(PainelLoginLayout);
        PainelLoginLayout.setHorizontalGroup(
            PainelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelLoginLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(PainelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(PainelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Nome)
                    .addComponent(Codigo))
                .addGap(70, 70, 70))
            .addGroup(PainelLoginLayout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addComponent(Entrar, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(94, Short.MAX_VALUE))
        );
        PainelLoginLayout.setVerticalGroup(
            PainelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Nome, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Codigo, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE))
                .addGap(9, 9, 9)
                .addComponent(Entrar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/police/imagens/RC.png"))); // NOI18N

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Desenvolvido por kli0ns#3092 vulgo Ferraz");
        jLabel5.setEnabled(false);

        txtAtt.setFont(new java.awt.Font("Arial Narrow", 1, 24)); // NOI18N
        txtAtt.setForeground(new java.awt.Color(255, 255, 255));
        txtAtt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtAtt.setText("ATUALIZAÇÃO DISPONÍVEL");

        download.setBackground(new java.awt.Color(255, 255, 255));
        download.setFont(new java.awt.Font("Arial Narrow", 1, 12)); // NOI18N
        download.setText("DOWNLOAD NOVA VERSÃO");
        download.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadActionPerformed(evt);
            }
        });

        build.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        build.setForeground(new java.awt.Color(245, 245, 245));
        build.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        build.setText("VERSÃO: 20191026");

        versao.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        versao.setForeground(new java.awt.Color(245, 245, 245));
        versao.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        versao.setText("VERSÃO: 20191026");

        mensagem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        mensagem.setForeground(new java.awt.Color(245, 245, 245));
        mensagem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        mensagem.setText("MENSAGEM");

        javax.swing.GroupLayout attLayout = new javax.swing.GroupLayout(att);
        att.setLayout(attLayout);
        attLayout.setHorizontalGroup(
            attLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, attLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(attLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mensagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, attLayout.createSequentialGroup()
                        .addGroup(attLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(versao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(build, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(download, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(txtAtt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        attLayout.setVerticalGroup(
            attLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(attLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtAtt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(attLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(attLayout.createSequentialGroup()
                        .addComponent(versao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(build))
                    .addComponent(download, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mensagem)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        versaoatual.setForeground(new java.awt.Color(255, 255, 255));
        versaoatual.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        versaoatual.setText("Versão atual:");
        versaoatual.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(att, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(versaoatual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 24, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(PainelLogin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtErro, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(att, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtErro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PainelLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(2, 2, 2)
                .addComponent(versaoatual)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void EntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EntrarActionPerformed
        LogarConta();
    }//GEN-LAST:event_EntrarActionPerformed

    private void downloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadActionPerformed
        String fileURL = config.linkB;//"https://newlandsrp.tk/LSPD.exe";
        String NovoNome = "LSPD";
        String path=null;
        try {
            path = new File(".").getCanonicalPath();
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.print(path);
        String saveDir = path;
        boolean down=false;
        try {
            down = HttpDownloadUtility.downloadFile(fileURL, saveDir, NovoNome);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if(down){
            try {
                Process process = new ProcessBuilder(path+"/"+NovoNome+".exe").start();
                Thread.sleep(1000);
                this.dispose();
                
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                mensagem.setText("Algum erro aconteceu ao tentar abrir o programa!");
            } catch (InterruptedException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            mensagem.setText("Algum erro aconteceu ao tentar fazer download!");
        }
    }//GEN-LAST:event_downloadActionPerformed

    private void NomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NomeKeyReleased
        SomenteNumeros(evt);
    }//GEN-LAST:event_NomeKeyReleased

    private void NomeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NomeKeyTyped
        SomenteNumeros(evt);
    }//GEN-LAST:event_NomeKeyTyped

    private void CodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CodigoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if(config.VerificarAtt()){
                if(config.getNeed()){
                    txtAtt.setText("ATUALIZAÇÃO NECESSÁRIA!!!!");
                }else{
                    LogarConta();
                }
            }else{
                LogarConta();
            }
        }
    }//GEN-LAST:event_CodigoKeyPressed

    private void NomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NomeKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Codigo.requestFocus();
        }
    }//GEN-LAST:event_NomeKeyPressed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Codigo;
    private javax.swing.JButton Entrar;
    private javax.swing.JTextField Nome;
    private javax.swing.JPanel PainelLogin;
    private javax.swing.JPanel att;
    private javax.swing.JLabel build;
    private javax.swing.JButton download;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel mensagem;
    private javax.swing.JLabel txtAtt;
    private javax.swing.JLabel txtErro;
    private javax.swing.JLabel versao;
    private javax.swing.JLabel versaoatual;
    // End of variables declaration//GEN-END:variables
}
