/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police;

import java.awt.Color;
import java.time.Instant;
import java.time.ZonedDateTime;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONObject;
import police.configs.ConexaoDB;
import police.configs.Config;
import police.configs.Usuario;

/**
 *
 * @author John
 */
public class SalvarDadosCorporacao extends javax.swing.JFrame {

    String novaptt;
    String antigaptt;
    
    String NomeAge="Desconhecido";
    String DiscordA="";
    String passaporte;
    int stars;
    int starsA;
    String PttInsi;
    String PttInsi2;
    
    JDA jda;
    
    /**
     * Creates new form VerCrimes
     */
    public SalvarDadosCorporacao() {
        initComponents();
        this.setLocationRelativeTo(null);
        //this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagens/lspd-small.png")));
        
        getContentPane().setBackground(new java.awt.Color(13, 32, 64));
        patt.setBackground(new java.awt.Color(15, 35, 67));
        IniciarSobre();
        DiscordBot();
    }
    public void DiscordBot(){
        InicializadorMain policia = new InicializadorMain();
        jda = policia.getJDA();
        /*try{

            jda = new JDABuilder("NTQxMTIxMzIwNjcxNTEwNTI4.Xfl2zQ.PBtKk452VMOzYWNsG9uvYbOu4OY")         // The token of the account that is logging in.

                    .addEventListeners(new DiscordMessage())  // An instance of a class that will handle events.

                    .build();

            jda.awaitReady(); // Blocking guarantees that JDA will be completely loaded.

            System.out.println("Finished Building JDA!");
            
            

        }catch (InterruptedException e)

        {

            //Due to the fact that awaitReady is a blocking method, one which waits until JDA is fully loaded,

            // the waiting can be interrupted. This is the exception that would fire in that situation.

            //As a note: in this extremely simplified example this will never occur. In fact, this will never occur unless

            // you use awaitReady in a thread that has the possibility of being interrupted (async thread usage and interrupts)

            e.printStackTrace();

        } catch (LoginException ex) {
            Logger.getLogger(Corporacao.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
    public boolean IniciarSobre(){
        Usuario usuario = new Usuario();
        JSONObject obj = new JSONObject(usuario.getDadosalvar());
        
        passaporte = obj.getString("ptt_passaporte");
        NomeAge = obj.getString("ptt_nome");
        novaptt = obj.getString("ptt_patente");
        antigaptt= obj.getString("ptt_patente_old");
        DiscordA = obj.getString("ptt_discord");
        stars = obj.getInt("ptt_stars");
        starsA = obj.getInt("ptt_stars_old");
        
        String patenten = usuario.Patentes(Integer.parseInt(novaptt));
        String patenteo = usuario.Patentes(Integer.parseInt(antigaptt));
        
        nomeof.setText(NomeAge+" ("+passaporte+")");
        
        switch (stars) {
            case 0:
                PttInsi= " [■■■]";
                break;
            case 1:
                PttInsi= " [*■■]";
                break;
            case 2:
                PttInsi= " [**■]";
                break;
            case 3:
                PttInsi= " [***️]";
                break;
            default:
                PttInsi= " [■■■]";
                break;
        }
        switch (starsA) {
            case 0:
                PttInsi2= " [■■■]";
                break;
            case 1:
                PttInsi2= " [*■■]";
                break;
            case 2:
                PttInsi2= " [**■]";
                break;
            case 3:
                PttInsi2= " [***️]";
                break;
            default:
                PttInsi2= " [■■■]";
                break;
        }
        
        txtnewptt.setText(patenten+" "+PttInsi);
        txtoldptt.setText(patenteo+" "+PttInsi2);
        
        
        
        
        return true;
    }
    public void MensagemHierarquia(JSONObject dados){
        
        //MUDAR ESSAS COISAS
        //String Titulo = dados.getString("oqfez");
        String Texto = dados.getString("texto");
        
        String id_usuario = dados.getString("id_usuario");
        String id_promoveu = dados.getString("id_promoveu");
        
        String discord_usuario = dados.getString("discord_usuario");
        String discord_promoveu = dados.getString("discord_promoveu");
        
        String nome_usuario = dados.getString("nome_usuario");
        String nome_promoveu = dados.getString("nome_promoveu");
        
        String oldpatente = dados.getString("oldpatente");
        String novapatente = dados.getString("novapatente");
        
        String motivo = dados.getString("motivo");
        String proreb = dados.getString("proreb");
        int Stars = dados.getInt("stars");
        int StarsA = dados.getInt("starsA");
        int cor = dados.getInt("cor");
        
        Config config = new Config();
        String canal="678697763394813954";
        TextChannel usuar = jda.getTextChannelById​(Long.parseLong(canal));
        
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Modificação na hierarquia", null);

        eb.setColor(new Color(cor));
        eb.setDescription(Texto);

        
        eb.addField("Policial "+proreb, nome_usuario+" ("+id_usuario+")", true);
        eb.addField("Oficial solicitante", nome_promoveu+" ("+id_promoveu+")", true);
        eb.addField("Motivo", motivo, false);
        eb.addField("Antiga Patente", oldpatente+" "+PttInsi2, true);
        eb.addField("Nova Patente", novapatente+" "+PttInsi, true);
        
        
        eb.setFooter("Computador de Bordo [ver. "+config.versao+"]", null);

        eb.setThumbnail(config.img_DiscordPolicia);
        Instant instant = Instant.from(ZonedDateTime.now());
        eb.setTimestamp(instant);
        
        usuar.sendMessage(eb.build()).queue();
    }
    
    public boolean SalvarDados(){
        ConexaoDB conexao = new ConexaoDB();
        String motivo = motivotxt.getText();
        
        Usuario usuario = new Usuario();
        JSONObject obj = new JSONObject(usuario.getDados());
        String nomep = obj.getString("nome")+" "+obj.getString("sobrenome");
        String passaportep = obj.getString("id_usuario");
        String discord = obj.getString("discord");
        
        String patenten = novaptt;
        String patenteo = antigaptt;
        
        String Nome = NomeAge;
        String Passw = passaporte;
        int Stars = stars;
        int StarsA = starsA;
        
        JSONObject my_obj = new JSONObject();
        my_obj.put("passaporte", passaportep);
        my_obj.put("nome", nomep);
        
        my_obj.put("patenten", patenten);
        my_obj.put("patenteo", patenteo);
        my_obj.put("nomen", Nome);
        my_obj.put("passaporten", Passw);
        my_obj.put("stars", Stars);
        
        my_obj.put("motivo", motivo);
        int ptet = Integer.parseInt(patenten);
        if(ptet==99){
            my_obj.put("lspd", 0);
        }else{
            my_obj.put("lspd", 1);
        }
        if(conexao.SalvarPtt("cb_hierarquia", my_obj)){
            String novapatente = usuario.Patentes(Integer.parseInt(patenten));
            String oldpatente = usuario.Patentes(Integer.parseInt(patenteo));
            //String oldpatente = usuario.Patentes(Integer.parseInt(antigaptt));
            System.out.print("deu certo!!");
            String oqfez;
            int cor;
            if(Integer.parseInt(patenten) < Integer.parseInt(patenteo) || ptet==99){
                oqfez="rebaixado";
                cor=0xCC3333;
            }else{
                oqfez="promovido";
                cor=0x339933;
            }
            String proreb = oqfez;
            String oqfez2="**"+Nome+"** foi **"+oqfez+"** de **"+oldpatente+"** para **"+novapatente+"** !";
            oqfez=Nome+" foi "+oqfez+" de "+oldpatente+" para "+novapatente+" !";
            
            //if(conexao.setLSPD(Integer.parseInt(passaportep), 0)){
                showMessageDialog(null,
                oqfez,
                "Salvo com sucesso!!",
                JOptionPane.PLAIN_MESSAGE);
            //}
            JSONObject dados = new JSONObject();
            dados.put("texto", oqfez2);
            dados.put("id_usuario", Passw);
            dados.put("id_promoveu", passaportep);
            
            dados.put("oldpatente", oldpatente);
            dados.put("novapatente", novapatente);
            
            dados.put("nome_usuario", Nome);
            dados.put("nome_promoveu", nomep);
            
            dados.put("discord_usuario", DiscordA);
            dados.put("discord_promoveu", discord);
            
            dados.put("motivo", motivo);
            dados.put("proreb", proreb);
            dados.put("cor", cor);
            
            dados.put("stars", Stars);
            dados.put("starsA", StarsA);
            
            MensagemHierarquia(dados);
            
        }
        new Corporacao().setVisible(true);
        this.dispose();
            
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

        titulo = new javax.swing.JLabel();
        patt = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nomeof = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtoldptt = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtnewptt = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        motivotxt = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SOBRE");
        setResizable(false);

        titulo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titulo.setForeground(new java.awt.Color(255, 255, 255));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("SALVAR PATENTE");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("NOME DO OFICIAL");

        nomeof.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        nomeof.setForeground(new java.awt.Color(255, 255, 255));
        nomeof.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeof.setText("CARREGANDO...");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("ANTIGA PATENTE");

        txtoldptt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtoldptt.setForeground(new java.awt.Color(255, 255, 255));
        txtoldptt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtoldptt.setText("CARREGANDO...");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("MOTIVO");

        txtnewptt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtnewptt.setForeground(new java.awt.Color(255, 255, 255));
        txtnewptt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtnewptt.setText("CARREGANDO...");

        motivotxt.setColumns(20);
        motivotxt.setRows(5);
        jScrollPane1.setViewportView(motivotxt);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("NOVA PATENTE");

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton1.setText("SALVAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton2.setText("DESFAZER");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pattLayout = new javax.swing.GroupLayout(patt);
        patt.setLayout(pattLayout);
        pattLayout.setHorizontalGroup(
            pattLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtnewptt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pattLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pattLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nomeof, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtoldptt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pattLayout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pattLayout.setVerticalGroup(
            pattLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pattLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nomeof)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtoldptt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtnewptt)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pattLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(patt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(patt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new Corporacao().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        SalvarDados();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(SalvarDadosCorporacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SalvarDadosCorporacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SalvarDadosCorporacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SalvarDadosCorporacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SalvarDadosCorporacao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea motivotxt;
    private javax.swing.JLabel nomeof;
    private javax.swing.JPanel patt;
    private javax.swing.JLabel titulo;
    private javax.swing.JLabel txtnewptt;
    private javax.swing.JLabel txtoldptt;
    // End of variables declaration//GEN-END:variables
}
