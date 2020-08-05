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
public class CursoCorporacao extends javax.swing.JFrame {

    String novaptt;
    String antigaptt;
    
    String NomeAge="Desconhecido";
    String DiscordA="";
    String passaporte;
    
    JDA jda;
    
    /**
     * Creates new form VerCrimes
     */
    public CursoCorporacao() {
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
        
        
        
        String patenten = usuario.Patentes(Integer.parseInt(novaptt));
        String patenteo = usuario.Patentes(Integer.parseInt(antigaptt));
        
        nomeof.setText(NomeAge+" ("+passaporte+")");
        
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
        int cor = dados.getInt("cor");
        
        Config config = new Config();
        String canal="658497570900934662";
        TextChannel usuar = jda.getTextChannelById​(Long.parseLong(canal));
        
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Modificação na hierarquia", null);

        /*
            Set the color
         */
        //eb.setColor(Color.green);
        eb.setColor(new Color(cor));
        //eb.setColor(new Color(255, 0, 54));

        /*
            Set the text of the Embed:
            Arg: text as string
         */
        eb.setDescription(Texto);

        /*
            Add fields to embed:
            1. Arg: title as string
            2. Arg: text as string
            3. Arg: inline mode true / false
         */
        eb.addField("Policial "+proreb, nome_usuario+" ("+id_usuario+")", true);
        eb.addField("Oficial solicitante", nome_promoveu+" ("+id_promoveu+")", true);
        eb.addField("Motivo", motivo, false);
        eb.addField("Antiga Patente", oldpatente, true);
        eb.addField("Nova Patente", novapatente, true);
        
        
        /*
            Add spacer like field
            Arg: inline mode true / false
         */
        //eb.addBlankField(false);

        /*
            Add embed author:
            1. Arg: name as string
            2. Arg: url as string (can be null)
            3. Arg: icon url as string (can be null)
         */
        //eb.setAuthor("name", null, "https://i.imgur.com/3RuZCiB.png");

        /*
            Set footer:
            1. Arg: text as string
            2. icon url as string (can be null)
         */
        
        eb.setFooter("Computador de Bordo [ver. "+config.versao+"]", null);

        /*
            Set image:
            Arg: image url as string
         */
        //eb.setImage("https://i.imgur.com/3RuZCiB.png");

        /*
            Set thumbnail image:
            Arg: image url as string
         */
        eb.setThumbnail("https://i.imgur.com/3RuZCiB.png");
        Instant instant = Instant.from(ZonedDateTime.now());
        eb.setTimestamp(instant);
        
        usuar.sendMessage(eb.build()).queue();
    }
    
    /*
    public boolean SalvarDados(){
        ConexaoDB conexao = new ConexaoDB();
        String motivo = motivotxt.getText();
        
        Usuario usuario = new Usuario();
        JSONObject obj = new JSONObject(usuario.getDados());
        String nomep = obj.getString("nome");
        String passaportep = obj.getString("passaporte");
        String discord = obj.getString("discord");
        
        String patenten = novaptt;
        String patenteo = antigaptt;
        
        String Nome = NomeAge;
        String Passw = passaporte;
        
        JSONObject my_obj = new JSONObject();
        my_obj.put("passaporte", passaportep);
        my_obj.put("nome", nomep);
        
        my_obj.put("patenten", patenten);
        my_obj.put("patenteo", patenteo);
        my_obj.put("nomen", Nome);
        my_obj.put("passaporten", Passw);
        
        my_obj.put("motivo", motivo);
        
        int ptet = Integer.parseInt(patenten);
        if(ptet==99){
            my_obj.put("lspd", 0);
        }else{
            my_obj.put("lspd", 1);
        }
        if(conexao.SalvarPtt("hierarquia", my_obj)){
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
            
            MensagemHierarquia(dados);
            
        }
        new Corporacao().setVisible(true);
        this.dispose();
            
        return false;
    }*/

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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        nomeof1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("SOBRE");
        setResizable(false);

        titulo.setFont(new java.awt.Font("DialogInput", 1, 36)); // NOI18N
        titulo.setForeground(new java.awt.Color(255, 255, 255));
        titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo.setText("CURSOS");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("NOME DO OFICIAL");

        nomeof.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        nomeof.setForeground(new java.awt.Color(255, 255, 255));
        nomeof.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeof.setText("CARREGANDO...");

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

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("PASSAPORTE");

        nomeof1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        nomeof1.setForeground(new java.awt.Color(255, 255, 255));
        nomeof1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeof1.setText("CARREGANDO...");

        jLabel4.setFont(new java.awt.Font("DialogInput", 0, 20)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("CURSOS DISPONÍVEIS");

        jCheckBox1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jCheckBox1.setText("MOTO");
        jCheckBox1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jCheckBox2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jCheckBox2.setText("CHEFE MOTO");
        jCheckBox2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jCheckBox3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jCheckBox3.setText("SPEED");
        jCheckBox3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jCheckBox4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jCheckBox4.setText("CHEFE SPEED");
        jCheckBox4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jCheckBox5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jCheckBox5.setText("FBI");
        jCheckBox5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jCheckBox6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jCheckBox6.setText("PRISÃO");
        jCheckBox6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jCheckBox7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jCheckBox7.setText("EAGLE");
        jCheckBox7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jCheckBox8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jCheckBox8.setText("CHEFE EAGLE");
        jCheckBox8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBox6, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jCheckBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCheckBox8, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox2))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox3)
                    .addComponent(jCheckBox4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox7)
                    .addComponent(jCheckBox8))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox6)
                    .addComponent(jCheckBox5))
                .addContainerGap())
        );

        javax.swing.GroupLayout pattLayout = new javax.swing.GroupLayout(patt);
        patt.setLayout(pattLayout);
        pattLayout.setHorizontalGroup(
            pattLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pattLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pattLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pattLayout.createSequentialGroup()
                        .addGroup(pattLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(nomeof1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addGroup(pattLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nomeof, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pattLayout.createSequentialGroup()
                        .addGroup(pattLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pattLayout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        pattLayout.setVerticalGroup(
            pattLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pattLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pattLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pattLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomeof)
                    .addComponent(nomeof1))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
        //SalvarDados();
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
            java.util.logging.Logger.getLogger(CursoCorporacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CursoCorporacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CursoCorporacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CursoCorporacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CursoCorporacao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel nomeof;
    private javax.swing.JLabel nomeof1;
    private javax.swing.JPanel patt;
    private javax.swing.JLabel titulo;
    // End of variables declaration//GEN-END:variables
}
