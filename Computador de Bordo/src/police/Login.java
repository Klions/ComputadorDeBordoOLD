/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police;

import police.configs.Usuario;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import org.json.JSONArray;
import org.json.JSONObject;
import police.configs.ConexaoDB;
import police.configs.Config;
import police.configs.DiscordWebhook;
import police.configs.GetImages;
import police.configs.HttpDownloadUtility;


/**
 *
 * @author John
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    
   
    JSONArray usuariosDBarray = new JSONArray();
    JSONArray hierarquiaDBarray = new JSONArray();
    
    JSONArray vrp_users = new JSONArray();
    JSONArray cb_users = new JSONArray();
    
    Usuario usuarios = new Usuario();
    InicializadorMain police = new InicializadorMain();
    
    int tentativas=1;
    public Login() {
        initComponents();
        this.setLocationRelativeTo(null);
        Nome.requestFocus();
        txtErro.setText(InicializadorMain.info_cidade.getString("nome_policia").toUpperCase());
        getContentPane().setBackground(new java.awt.Color(13, 32, 64));
        PainelLogin.setBackground(new java.awt.Color(13, 32, 64));
        jPanel1.setBackground(new java.awt.Color(13, 32, 64));
        
        versaoatual.setText("Versão: "+Config.versao);
        //att.setBackground(new java.awt.Color(222, 82, 82));
        AttUsers();
        //AttDBUsuarios();
        //AttDBHierarquia();
        
        if(InicializadorMain.ModoOffline){
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagens/CB2png")));
        }else{
            this.setIconImage(new ImageIcon(GetImages.LogoCB).getImage());
            icone.setIcon(new ImageIcon(GetImages.LogoCB_branco));
        }
        
        if(netIsAvailable()){
            
        }else{
            txtErro.setText("Desculpe, mas sua conexão está ruim ou nula.");
            Entrar.setEnabled(false);
        }
        pack();
        UPDATE();
    }
    public void AttUsers(){
        vrp_users = InicializadorMain.vrp_users;
        cb_users = InicializadorMain.cb_users;
        usuariosDBarray = InicializadorMain.usuariosDBarray;
        hierarquiaDBarray = InicializadorMain.hierarquiaDBarray;
    }
    
    public void AttDBUsuarios(){
        usuariosDBarray = usuarios.AttDBUsuarios();
    }
    
    public void AttDBHierarquia(){
        hierarquiaDBarray = usuarios.AttDBHierarquia();
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
        String senha = Codigo.getText();
        
        if("".equals(passaporteI) || "".equals(senha)){
            TextoErro("Campos vazios, digite os dados");
            return false;
        }
        int passaporte = Integer.parseInt(passaporteI);
        JSONObject cbuser = cb_userPorID(passaporte);
        if(cbuser != null){
            if(passaporte == cbuser.getInt("user_id") && senha.equals(cbuser.getString("codigo"))){
                JSONObject sal_info = PegarUsuario(passaporte);
                if(sal_info != null){
                    Usuario.setDados(sal_info);
                    if(lembrar.isSelected()) SAVE();
                    new Painel().setVisible(true);
                    this.dispose();
                }else{
                    TextoErro("Erro ao pegar sua conta no Banco de Dados.");
                    AttDBHierarquia();
                    return false;
                }
            }else{
                tentativas++;
                TextoErro("Senha ou usuário inválido.");
                return false;
            }
        }
        
        //String FormatNome = obj.getString("nome_cidade")+" - "+obj.getString("nome_policia_abv");

        /*int servidor_id = VerificarConta(Integer.parseInt(passaporteI), codigoI);
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
                            InicializadorMain.server_id = servidor_id;
                            
                            police.AttDBSTodas();
                        }else{
                            TextoErro("Erro ao pegar sua conta no Banco de Dados.");
                            AttDBHierarquia();
                            return false;
                        }
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
        }*/
        
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
    
    public JSONObject cb_userPorID(int user_id){
        System.out.print("cb_userPorID("+user_id+")");
        for(int i = 0; i < cb_users.length(); i++){
            JSONObject obj = cb_users.getJSONObject(i);
            if(user_id == obj.getInt("user_id"))return obj;
        }
        return null;
    }
    public JSONObject vrp_userPorID(int user_id){
        for(int i = 0; i < vrp_users.length(); i++){
            JSONObject obj = vrp_users.getJSONObject(i);
            if(user_id == obj.getInt("id"))return obj;
        }
        return null;
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
            //System.out.println("user_id: "+user_id+" / data.getInt(id_usuario): "+data.getInt("id_usuario"));
            if(user_id==data.getInt("id_usuario")){
                return data;
            }
        }
        return null;
    }
    
    public boolean PegarInfoServidor(int Server_ID){
        ConexaoDB conexao = new ConexaoDB();
        ResultSet resulteSet = null;
        resulteSet = conexao.GetPersonalizado("select * from cb_servers WHERE id="+Server_ID+" ORDER BY id DESC");
        
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
    
    public void SAVE(){      //Save the UserName and Password (for one user)
        try {
            File file = new File(InicializadorMain.DestFile);
            if(!file.exists()) file.createNewFile();  //if the file !exist create a new one

            BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
            
            bw.write(Nome.getText()); //write the name
            bw.newLine(); //leave a new Line
            bw.write(Codigo.getText()); //getPassword()
            bw.close(); //close the BufferdWriter

        } catch (IOException e) { e.printStackTrace(); }        
    }//End Of Save
    
    public void UPDATE(){ //UPDATE ON OPENING THE APPLICATION
        try {
            File file = new File(InicializadorMain.DestFile);
            if(file.exists()){    //if this file exists

                Scanner scan = new Scanner(file);   //Use Scanner to read the File
                /*while (scan.hasNext()) {
                    System.out.println(scan.next());
                }*/
                Nome.setText(scan.nextLine());  //append the text to name field
                Codigo.setText(scan.nextLine()); //append the text to password field
                scan.close();
                Entrar.requestFocus();
            }

        } catch (FileNotFoundException e) {         
            e.printStackTrace();
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

        info = new javax.swing.JLabel();
        versaoatual = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        icone = new javax.swing.JLabel();
        txtErro = new javax.swing.JLabel();
        PainelLogin = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        Nome = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        Codigo = new javax.swing.JTextField();
        lembrar = new javax.swing.JCheckBox();
        Entrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ENTRAR");
        setBackground(new java.awt.Color(51, 51, 51));
        setResizable(false);

        info.setForeground(new java.awt.Color(255, 255, 255));
        info.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        info.setText("Clique aqui para entrar no nosso Discord");
        info.setEnabled(false);
        info.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                infoMouseClicked(evt);
            }
        });

        versaoatual.setForeground(new java.awt.Color(255, 255, 255));
        versaoatual.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        versaoatual.setText("Carregando...");
        versaoatual.setEnabled(false);

        icone.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/police/imagens/CB.png"))); // NOI18N

        txtErro.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        txtErro.setForeground(new java.awt.Color(255, 255, 255));
        txtErro.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtErro.setText("POLÍCIA MILITAR");

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

        Codigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CodigoKeyPressed(evt);
            }
        });

        lembrar.setForeground(new java.awt.Color(255, 255, 255));
        lembrar.setSelected(true);
        lembrar.setText("LEMBRAR-ME");
        lembrar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lembrar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        lembrar.setOpaque(false);

        javax.swing.GroupLayout PainelLoginLayout = new javax.swing.GroupLayout(PainelLogin);
        PainelLogin.setLayout(PainelLoginLayout);
        PainelLoginLayout.setHorizontalGroup(
            PainelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelLoginLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lembrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PainelLoginLayout.createSequentialGroup()
                        .addGroup(PainelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(PainelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Nome, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                            .addComponent(Codigo))))
                .addContainerGap())
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lembrar)
                .addGap(14, 14, 14))
        );

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(icone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtErro, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(PainelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(Entrar, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(icone, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtErro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PainelLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Entrar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(info, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(versaoatual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(info)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(versaoatual)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void EntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EntrarActionPerformed
        LogarConta();
    }//GEN-LAST:event_EntrarActionPerformed

    private void NomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NomeKeyReleased
        SomenteNumeros(evt);
    }//GEN-LAST:event_NomeKeyReleased

    private void NomeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NomeKeyTyped
        SomenteNumeros(evt);
    }//GEN-LAST:event_NomeKeyTyped

    private void CodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CodigoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            LogarConta();
        }
    }//GEN-LAST:event_CodigoKeyPressed

    private void NomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NomeKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Codigo.requestFocus();
        }
    }//GEN-LAST:event_NomeKeyPressed

    private void infoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoMouseClicked
        HttpDownloadUtility.openURL("https://discord.gg/nFNqvDs");
    }//GEN-LAST:event_infoMouseClicked

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
    private javax.swing.JLabel icone;
    private javax.swing.JLabel info;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JCheckBox lembrar;
    private javax.swing.JLabel txtErro;
    private javax.swing.JLabel versaoatual;
    // End of variables declaration//GEN-END:variables
}
