/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police;

import police.configs.Usuario;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import org.json.JSONObject;
//import static police.Prisoes.FecharJanela;
import police.configs.Config;
import police.configs.GetImages;
import police.configs.HttpDownloadUtility;
import police.configs.SNWindows;

/**
 *
 * @author John
 */
public class Painel extends javax.swing.JFrame {

    /**
     * Creates new form Painel
     */
    Usuario usuario = new Usuario();
    Config config = new Config();
    
    int contageAtt = 10;
    public Painel() {
        initComponents();
        contageAtt = 10;
        Att.setVisible(false);
        corporacao.setVisible(false);
        gerenciar.setVisible(false);
        
        getContentPane().setBackground(new java.awt.Color(13, 32, 64));
        
        //pessoas.setBackground(new java.awt.Color(13, 32, 64));
        //corporacao.setBackground(new java.awt.Color(13, 32, 64));
        //veiculos.setBackground(new java.awt.Color(50, 31, 87));
        
        //this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(config.img_CBIcone)));
        if(InicializadorMain.ModoOffline){
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagens/CB2.png")));
        }else{
            this.setIconImage(new ImageIcon(GetImages.LogoCB).getImage());
            icone.setIcon(new ImageIcon(GetImages.LogoCB_branco));
            this.setTitle(InicializadorMain.info_cidade.getString("nome_cidade").toUpperCase()+" - COMPUTADOR DE BORDO v"+Config.versao);
        }
        VerAtt();
        
        
        JFrame EsteFrame = this;
        Timer timer = new Timer(); 
        TimerTask tt = new TimerTask() { 
  
            public void run(){
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String dataFormatada = simpleDateFormat.format(date);
                sobre1.setText(dataFormatada);
                if(!EsteFrame.isVisible()){
                    timer.cancel();
                }
                if(contageAtt>0){
                    contageAtt--;
                }else{
                    contageAtt=10;
                    VerAtt();
                }
            }
        }; 
        timer.schedule(tt, 500, 1000);
        Carregar();
        this.setLocationRelativeTo(null);
    }
    public void Carregar(){
        //System.err.println("getDados: "+ usuario.getDados()+"/ fechou");
        JSONObject obj = Usuario.getDados();
        
        Titulo1.setText(InicializadorMain.info_cidade.getString("nome_policia").toUpperCase());
        Titulo2.setText(InicializadorMain.info_cidade.getString("nome_cidade").toUpperCase());
        
        ContaBt.setBackground(new java.awt.Color(13, 32, 64));
        int perm = obj.getInt("permissao");
        
        if(InicializadorMain.ModoOffline){
            //ContaBt.setText("Sem Registro");
            DesativarBT(gerenciar1);
            DesativarBT(gerenciar2);
            //DesativarBT(boletim);
            
            gerenciar.setVisible(true);
            ContaBt.setText(obj.getString("nome").toUpperCase()+" "+obj.getString("sobrenome").toUpperCase());
        }else{
            if(perm >= 1) corporacao.setVisible(true);
            if(perm >= 2) gerenciar.setVisible(true);
            
            DesativarBT(gerenciar4);
            JSONObject cargo_usuario = SNWindows.getCargoObjUser();
            ContaBt.setText(cargo_usuario.getString("nome_cargo").toUpperCase()+" • "+obj.getString("nome").toUpperCase()+" "+obj.getString("sobrenome").toUpperCase());
        }
        
        sobre.setText("COMPUTADOR DE BORDO v"+Config.versao);
        pack();
    }
    boolean AntesAtt = false;
    public void VerAtt(){
        if(Config.VerificarAtt()){
            Att.setVisible(true);
            boolean Neces = Config.getNeed();
            if(Neces){
                AttSubTitulo.setText("ATUALIZAÇÃO NECESSÁRIA");
            }else{
                AttSubTitulo.setText("VERSÃO DA ATUALIZAÇÃO: "+Config.getVersao());
            }
            if(!AntesAtt) pack();
            AntesAtt = true;
        }else{
            Att.setVisible(false);
            if(AntesAtt) pack();
            AntesAtt = false;
        }
    }
    
    private void DesativarBT(JButton Botao){
        Botao.setEnabled(false);
        Botao.setBackground(new java.awt.Color(0, 48, 73));
        if(InicializadorMain.ModoOffline){
            Botao.setToolTipText("Desativado no Modo Offline");
        }else{
            Botao.setToolTipText("Desativado no Modo Online");
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

        Titulo2 = new javax.swing.JLabel();
        pessoas = new javax.swing.JPanel();
        crimes = new javax.swing.JButton();
        boletim = new javax.swing.JButton();
        Titulo1 = new javax.swing.JLabel();
        icone = new javax.swing.JLabel();
        corporacao = new javax.swing.JPanel();
        gerenciar1 = new javax.swing.JButton();
        gerenciar2 = new javax.swing.JButton();
        sobre = new javax.swing.JLabel();
        ContaBt = new javax.swing.JButton();
        gerenciar = new javax.swing.JPanel();
        gerenciar3 = new javax.swing.JButton();
        gerenciar4 = new javax.swing.JButton();
        Att = new javax.swing.JPanel();
        AttTitulo = new javax.swing.JLabel();
        AttSite = new javax.swing.JButton();
        AttAgora = new javax.swing.JButton();
        AttSubTitulo = new javax.swing.JLabel();
        sobre1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("COMPUTADOR DE BORDO");
        setResizable(false);

        Titulo2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        Titulo2.setForeground(new java.awt.Color(255, 255, 255));
        Titulo2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Titulo2.setText("AO COMPUTADOR DE BORDO");
        Titulo2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        pessoas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "REGISTROS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        pessoas.setOpaque(false);

        crimes.setBackground(new java.awt.Color(153, 153, 255));
        crimes.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        crimes.setText("PRISÃO / MULTA");
        crimes.setToolTipText("Registrar Multas e Crimes");
        crimes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crimesActionPerformed(evt);
            }
        });

        boletim.setBackground(new java.awt.Color(102, 102, 255));
        boletim.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        boletim.setText("BOLETIM DE OCORRÊNCIA");
        boletim.setToolTipText("Escrever um Boletim de Ocorrência");
        boletim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boletimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pessoasLayout = new javax.swing.GroupLayout(pessoas);
        pessoas.setLayout(pessoasLayout);
        pessoasLayout.setHorizontalGroup(
            pessoasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pessoasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crimes, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(boletim, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pessoasLayout.setVerticalGroup(
            pessoasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pessoasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pessoasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(crimes, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boletim, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        Titulo1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        Titulo1.setForeground(new java.awt.Color(255, 255, 255));
        Titulo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Titulo1.setText("BEM VINDO NOME,");
        Titulo1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        icone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/police/imagens/CB.png"))); // NOI18N

        corporacao.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CORPORAÇÃO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        corporacao.setOpaque(false);

        gerenciar1.setBackground(new java.awt.Color(204, 204, 0));
        gerenciar1.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        gerenciar1.setText("GERENCIAR CORPORAÇÃO");
        gerenciar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gerenciar1ActionPerformed(evt);
            }
        });

        gerenciar2.setBackground(new java.awt.Color(102, 204, 0));
        gerenciar2.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        gerenciar2.setText("LIMPAR FICHA");
        gerenciar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gerenciar2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout corporacaoLayout = new javax.swing.GroupLayout(corporacao);
        corporacao.setLayout(corporacaoLayout);
        corporacaoLayout.setHorizontalGroup(
            corporacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(corporacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gerenciar1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(gerenciar2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        corporacaoLayout.setVerticalGroup(
            corporacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(corporacaoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(corporacaoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gerenciar1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gerenciar2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sobre.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        sobre.setForeground(new java.awt.Color(255, 255, 255));
        sobre.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        sobre.setText(" ");

        ContaBt.setBackground(new java.awt.Color(204, 204, 204));
        ContaBt.setForeground(new java.awt.Color(255, 255, 255));
        ContaBt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/police/imagens/user.png"))); // NOI18N
        ContaBt.setText("CARREGANDO...");
        ContaBt.setBorder(null);
        ContaBt.setFocusPainted(false);
        ContaBt.setFocusable(false);
        ContaBt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ContaBt.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        ContaBt.setIconTextGap(5);
        ContaBt.setOpaque(false);
        ContaBt.setRequestFocusEnabled(false);
        ContaBt.setRolloverEnabled(false);
        ContaBt.setVerifyInputWhenFocusTarget(false);
        ContaBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ContaBtActionPerformed(evt);
            }
        });

        gerenciar.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "GERENCIAR", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        gerenciar.setOpaque(false);

        gerenciar3.setBackground(new java.awt.Color(0, 153, 102));
        gerenciar3.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        gerenciar3.setText("GERENCIAR CRIMES");
        gerenciar3.setToolTipText("Adicionar ou Remover Crimes");
        gerenciar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gerenciar3ActionPerformed(evt);
            }
        });

        gerenciar4.setBackground(new java.awt.Color(0, 153, 102));
        gerenciar4.setFont(new java.awt.Font("Arial Black", 0, 24)); // NOI18N
        gerenciar4.setText("GERENCIAR USUÁRIOS");
        gerenciar4.setToolTipText("Adicionar ou Remover Crimes");
        gerenciar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gerenciar4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout gerenciarLayout = new javax.swing.GroupLayout(gerenciar);
        gerenciar.setLayout(gerenciarLayout);
        gerenciarLayout.setHorizontalGroup(
            gerenciarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gerenciarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(gerenciar3, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(gerenciar4, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        gerenciarLayout.setVerticalGroup(
            gerenciarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gerenciarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(gerenciarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gerenciar3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gerenciar4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Att.setBackground(new java.awt.Color(222, 82, 82));

        AttTitulo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        AttTitulo.setForeground(new java.awt.Color(255, 255, 255));
        AttTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AttTitulo.setText("ATUALIZAÇÃO DISPONÍVEL");

        AttSite.setBackground(new java.awt.Color(255, 255, 204));
        AttSite.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AttSite.setText("BAIXAR PELO SITE");
        AttSite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AttSiteActionPerformed(evt);
            }
        });

        AttAgora.setBackground(new java.awt.Color(0, 204, 255));
        AttAgora.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        AttAgora.setText("ATUALIZAR AGORA");
        AttAgora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AttAgoraActionPerformed(evt);
            }
        });

        AttSubTitulo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        AttSubTitulo.setForeground(new java.awt.Color(255, 255, 255));
        AttSubTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AttSubTitulo.setText(" ");

        javax.swing.GroupLayout AttLayout = new javax.swing.GroupLayout(Att);
        Att.setLayout(AttLayout);
        AttLayout.setHorizontalGroup(
            AttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AttLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AttTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AttSubTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(AttAgora, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(AttSite, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        AttLayout.setVerticalGroup(
            AttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AttLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(AttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AttLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(AttSite, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(AttAgora, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(AttLayout.createSequentialGroup()
                        .addComponent(AttTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AttSubTitulo)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        sobre1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        sobre1.setForeground(new java.awt.Color(255, 255, 255));
        sobre1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sobre1.setText(" ");

        jMenu3.setText("EXIBIR");
        jMenu3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMenu3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

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
                    .addComponent(pessoas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(icone, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Titulo2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Titulo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(ContaBt))))
                    .addComponent(corporacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(gerenciar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(sobre1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(sobre, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(Att, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Att, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ContaBt)
                        .addGap(18, 18, 18)
                        .addComponent(Titulo1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Titulo2))
                    .addComponent(icone, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(pessoas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(corporacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gerenciar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sobre)
                    .addComponent(sobre1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void crimesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crimesActionPerformed
        //new Prender().setVisible(true);
        new Prisoes().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_crimesActionPerformed

    private void boletimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boletimActionPerformed
        new Boletim().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_boletimActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        InicializadorMain.sobre.setVisible(true);
        //this.dispose();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void gerenciar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gerenciar1ActionPerformed
        //new Corporacao().setVisible(true);
        new GerenciarServer().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_gerenciar1ActionPerformed

    private void gerenciar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gerenciar2ActionPerformed
        new LimpezaFicha().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_gerenciar2ActionPerformed

    private void gerenciar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gerenciar3ActionPerformed
        new Gerenciamento().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_gerenciar3ActionPerformed

    private void ContaBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ContaBtActionPerformed
        new ViewUsuario().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ContaBtActionPerformed

    private void AttAgoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AttAgoraActionPerformed
        AttSubTitulo.setText(HttpDownloadUtility.DownloadArquivo(Config.getLink()));
        HttpDownloadUtility.WebhookLog(
            "755986573031637073", 
            "Usuário atualizando Computador de Bordo", 
            "Algum usuário está atualizando o Computador de Bordo para a versão: "+Config.getVersao());
    }//GEN-LAST:event_AttAgoraActionPerformed

    private void AttSiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AttSiteActionPerformed
        HttpDownloadUtility.openURL(Config.getLink());
    }//GEN-LAST:event_AttSiteActionPerformed

    private void gerenciar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gerenciar4ActionPerformed
        new GerenciamentoUsuarios().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_gerenciar4ActionPerformed

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
            java.util.logging.Logger.getLogger(Painel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Painel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Painel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Painel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Painel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Att;
    private javax.swing.JButton AttAgora;
    private javax.swing.JButton AttSite;
    private javax.swing.JLabel AttSubTitulo;
    private javax.swing.JLabel AttTitulo;
    private javax.swing.JButton ContaBt;
    private javax.swing.JLabel Titulo1;
    private javax.swing.JLabel Titulo2;
    private javax.swing.JButton boletim;
    private javax.swing.JPanel corporacao;
    private javax.swing.JButton crimes;
    private javax.swing.JPanel gerenciar;
    private javax.swing.JButton gerenciar1;
    private javax.swing.JButton gerenciar2;
    private javax.swing.JButton gerenciar3;
    private javax.swing.JButton gerenciar4;
    private javax.swing.JLabel icone;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel pessoas;
    private javax.swing.JLabel sobre;
    private javax.swing.JLabel sobre1;
    // End of variables declaration//GEN-END:variables
}
