/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police;

import java.awt.Container;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Calendar;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import org.json.JSONArray;
import org.json.JSONObject;
import police.configs.ConexaoDB;
import police.configs.GetImages;
import police.configs.HttpDownloadUtility;
import police.configs.SNWindows;
import police.configs.Usuario;

/**
 *
 * @author John
 */
public class Prisoes extends javax.swing.JFrame {
    VerPrisoes verprisoes = new VerPrisoes();
    
    JSONArray CrimesRegistro = new JSONArray();
    JSONArray CategoriasCrimes = new JSONArray();
    
    JSONArray BeneficiosCrimes = new JSONArray();
    JSONObject OpcoesDeCrimes = new JSONObject();
    
    JSONArray UsuariosOfflineTbl = new JSONArray();
    JSONObject OpcoesUsuariosTbl = new JSONObject();
    
    JSONArray RegistroBotoes = new JSONArray();
    JSONArray RegistroInputs = new JSONArray();
    
    JSONArray GetCrimes = new JSONArray();
    
    JSONArray usuariosDBarray = new JSONArray();
    JSONArray discordDBarray = new JSONArray();
    JSONArray prisoesDBarray = new JSONArray();
    JSONArray blacklistDBarray = new JSONArray();
    JSONArray procuradosDBarray = new JSONArray();
    
    String CrimesDiscordFormat = "";
    String CrimesDBFormat = "";
    
    static String CrimesStore2="";
    static String CategoriasStore2="";
    
    static String AumentoEReducao="";
    static String OpcoesCrimes="";
    
    static String UsuariosOffline="";
    static String OpcoesUsuarios="";
    
    static String Protocolor = "";
    
    static int s_Meses = 0;
    static int s_Multa = 0;
    
    JSONObject UsuarioPegar = new JSONObject();
    
    static boolean FecharJanela = false;
    
    static boolean NaoAbrirDnvAss = false;
    
    /*JSONArray CategoriasCrimes = new JSONArray();
    JSONArray CrimesRegistro = new JSONArray();*/
    public Prisoes() {
        initComponents();
        FecharJanela = false;
        JFrame EsteFrame = this;
        DetalhesPainel.setVisible(false);
        
        getContentPane().setBackground(new java.awt.Color(13, 32, 64));
        PesquisarPainel.setBackground(new java.awt.Color(13, 32, 64));
        DetalhesPainel.setBackground(new java.awt.Color(13, 32, 64));
        PainelDetalhes.setBackground(new java.awt.Color(13, 32, 64));
        PesquisarPainel.setBackground(new java.awt.Color(13, 32, 64));
        jPanel2.setBackground(new java.awt.Color(13, 32, 64));
        jPanel3.setBackground(new java.awt.Color(13, 32, 64));
        
        PegarValoresOffline();
        PegarValoresOfflineOpcoes();
        PegarValoresOfflineUsuarios();
        
        if(InicializadorMain.ModoOffline){
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagens/CB2.png")));
            PesquisarPainel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CADASTRAR INDIVÍDUO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
            PesquisarBt.setText("CADASTRAR");
            
            
            JSONObject PegarUser = Usuario.getDados();
            if(PegarUser.getInt("id_usuario") == 0){
                Object[] options = { "Configurar Agora", "Depois" };
                int result = JOptionPane.showOptionDialog(null, "Estou vendo que você não configurou sua conta, deseja configurar agora?\nIsso afeta o código gerado dos crimes.", "Configure sua conta, vai ser rapidinho", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                if (result == JOptionPane.OK_OPTION) {
                    FecharJanela = true;
                }
            }
        }else{
            this.setIconImage(new ImageIcon(GetImages.LogoCB).getImage());
        }
        
        this.revalidate();
        this.repaint();
        this.pack();
        SetarBotoes();
        this.setLocationRelativeTo(null);
        PegarDBMain();
        Timer timer = new Timer(); 
        TimerTask tt = new TimerTask() { 
  
            public void run(){
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String dataFormatada = simpleDateFormat.format(date);
                TimeAgora.setText(dataFormatada);
                if(FecharJanela){
                    EsteFrame.dispose();
                    new ViewUsuario().setVisible(true);
                    FecharJanela = false;
                }
                if(!EsteFrame.isVisible()){
                    timer.cancel();
                }
            }
        }; 
        timer.schedule(tt, 500, 1000);
    }
    
    void PegarDBMain(){
        usuariosDBarray = InicializadorMain.usuariosDBarray;
        discordDBarray = InicializadorMain.discordDBarray;
        procuradosDBarray = InicializadorMain.procuradosDBarray;
        prisoesDBarray = InicializadorMain.prisoesDBarray;
    }
    
    public void PegarValoresOffline(){ //UPDATE ON OPENING THE APPLICATION
        if(InicializadorMain.ModoOffline){
            try {
                File file = new File(InicializadorMain.DestFile2);
                if(file.exists()){    //if this file exists
                    Scanner scan = new Scanner(file);   //Use Scanner to read the File
                    /*while (scan.hasNext()) {
                        System.out.println(scan.next());
                    }*/
                    Base64.Decoder dec = Base64.getDecoder();
                    String DecoderStre = Gerenciamento.DecodeBase64(scan.nextLine());
                    if(!"".equals(DecoderStre)){
                        CategoriasStore2 = DecoderStre;
                    }

                    DecoderStre = Gerenciamento.DecodeBase64(scan.nextLine());
                    if(!"".equals(DecoderStre)){
                        CrimesStore2 = DecoderStre;
                    }
                    scan.close();
                }

            } catch (FileNotFoundException e) {         
                e.printStackTrace();
            }
        }else{
            Usuario usuarios = new Usuario();
            GetCrimes = usuarios.CrimesServerID();
            for(int i2 = 0; i2 < GetCrimes.length(); i2++){
                JSONObject o2 = GetCrimes.getJSONObject(i2);
                CategoriasStore2 = o2.getString("categorias");
                CrimesStore2 = o2.getString("crimes");
                AumentoEReducao = o2.getString("aumento_reducao");
                OpcoesCrimes = o2.getString("opcoes");
            }
        }
    }
    
    public void PegarValoresOfflineOpcoes(){ //UPDATE ON OPENING THE APPLICATION
        if(InicializadorMain.ModoOffline){
            try {
                File file = new File(InicializadorMain.DestFile3);
                if(file.exists()){    //if this file exists
                    Scanner scan = new Scanner(file);   //Use Scanner to read the File
                    /*while (scan.hasNext()) {
                        System.out.println(scan.next());
                    }*/
                    Base64.Decoder dec = Base64.getDecoder();
                    String DecoderStre = Gerenciamento.DecodeBase64(scan.nextLine());
                    if(!"".equals(DecoderStre)){
                        AumentoEReducao = DecoderStre;
                    }

                    DecoderStre = Gerenciamento.DecodeBase64(scan.nextLine());
                    if(!"".equals(DecoderStre)){
                        OpcoesCrimes = DecoderStre;
                    }
                    scan.close();
                }

            } catch (FileNotFoundException e) {         
                e.printStackTrace();
            }
        }
    }
    
    public void PegarValoresOfflineUsuarios(){ //UPDATE ON OPENING THE APPLICATION
        if(InicializadorMain.ModoOffline){
            try {
                File file = new File(InicializadorMain.DestFileUsers);
                if(file.exists()){    //if this file exists
                    Scanner scan = new Scanner(file);   //Use Scanner to read the File
                    /*while (scan.hasNext()) {
                        System.out.println(scan.next());
                    }*/
                    Base64.Decoder dec = Base64.getDecoder();
                    String DecoderStre = Gerenciamento.DecodeBase64(scan.nextLine());
                    if(!"".equals(DecoderStre)){
                        UsuariosOffline = DecoderStre;
                    }

                    DecoderStre = Gerenciamento.DecodeBase64(scan.nextLine());
                    if(!"".equals(DecoderStre)){
                        OpcoesUsuarios = DecoderStre;
                    }
                    scan.close();
                }

            } catch (FileNotFoundException e) {         
                e.printStackTrace();
            }
        }
    }
    
    public void SetarBotoes(){
        //if(InicializadorMain.ModoOffline){
            CategoriasCrimes = new JSONArray();
            if(!"".equals(CategoriasStore2) && CategoriasStore2.length() > 10){
                CategoriasCrimes = new JSONArray(CategoriasStore2);
            }else{
                JSONObject getTemporario2 = new JSONObject();
                getTemporario2.put("id", 1);
                getTemporario2.put("nome_categoria", "Categoria Exemplo");
                
                CategoriasCrimes.put(getTemporario2);
            }
            
            CrimesRegistro = new JSONArray();
            if(!"".equals(CrimesStore2) && CrimesStore2.length() > 10){
                CrimesRegistro = new JSONArray(CrimesStore2);
            }else{
                JSONObject getTemporario2 = new JSONObject();
                getTemporario2.put("texto", "Crime Exemplo");
                getTemporario2.put("multa", 1000);
                getTemporario2.put("meses", 50);
                getTemporario2.put("tipo", "UNICO");
                getTemporario2.put("categoria", 1);
                
                CrimesRegistro.put(getTemporario2);
            }
            
            BeneficiosCrimes = new JSONArray();
            if(!"".equals(AumentoEReducao) && AumentoEReducao.length() > 10){
                BeneficiosCrimes = new JSONArray(AumentoEReducao);
            }else{
                JSONObject getTemporario2 = new JSONObject();
                getTemporario2.put("texto", "Reu Primario");
                getTemporario2.put("meses", 25);
                getTemporario2.put("calculo", "PORCENTAGEM");
                getTemporario2.put("tipo", "REDUCAO");
                BeneficiosCrimes.put(getTemporario2);
                
                getTemporario2 = new JSONObject();
                getTemporario2.put("texto", "Reu Reincidente");
                getTemporario2.put("meses", 30);
                getTemporario2.put("calculo", "PORCENTAGEM");
                getTemporario2.put("tipo", "AUMENTO");
                BeneficiosCrimes.put(getTemporario2);
            }
            
            OpcoesDeCrimes = new JSONObject();
            if(!"".equals(OpcoesCrimes) && OpcoesCrimes.length() > 10){
                OpcoesDeCrimes = new JSONObject(OpcoesCrimes);
            }else{
                OpcoesDeCrimes.put("pena_max", 0);
            }
            
            
            if(InicializadorMain.ModoOffline){
                UsuariosOfflineTbl = new JSONArray();
                if(!"".equals(UsuariosOffline) && UsuariosOffline.length() > 10){
                    UsuariosOfflineTbl = new JSONArray(UsuariosOffline);
                }

                OpcoesUsuariosTbl = new JSONObject();
                if(!"".equals(OpcoesUsuarios) && OpcoesUsuarios.length() > 10){
                    OpcoesUsuariosTbl = new JSONObject(OpcoesUsuarios);
                }else{
                    OpcoesUsuariosTbl.put("save_automatico", 0);
                }
            }
        /*}else{
            Usuario usuarios = new Usuario();
            GetCrimes = usuarios.CrimesServerID();
            for(int i2 = 0; i2 < GetCrimes.length(); i2++){
                JSONObject o2 = GetCrimes.getJSONObject(i2);
                CategoriasCrimes = new JSONArray(o2.getString("categorias"));
                CrimesRegistro = new JSONArray(o2.getString("crimes"));
            }
        }*/
        AtualizarJanelas();
        /*
        Usuario usuarios = new Usuario();
        GetCrimes = usuarios.CrimesServerID();
        for(int i2 = 0; i2 < GetCrimes.length(); i2++){
            
            JSONObject o2 = GetCrimes.getJSONObject(i2);
            CategoriasCrimes = new JSONArray(o2.getString("categorias"));
            CrimesRegistro = new JSONArray(o2.getString("crimes"));
        }
        AtualizarJanelas();
        */
    }
    //private javax.swing.JLabel Textos[];
    //private javax.swing.JToggleButton Botoes[];
    final int QntCategorias = 50;
    final int QntCrimes = 300;
    
    JScrollPane[] ScrollPainel = new JScrollPane[QntCategorias];
    JPanel[] PainelBase = new JPanel[QntCategorias];
    JPanel[][] Painel = new JPanel[QntCategorias][QntCrimes];
    JLabel[][] Textos = new JLabel[QntCategorias][QntCrimes];
    JToggleButton[][] Botoes = new JToggleButton[QntCategorias][QntCrimes];
    JTextField[][] InputText = new JTextField[QntCategorias][QntCrimes];
    
    public void AdicionarBotoes(){
        AttCrimes();
        for(int i2 = 0; i2 < CategoriasCrimes.length(); i2++){
            JSONObject o2 = CategoriasCrimes.getJSONObject(i2);
            
            PainelBase[i2] = new JPanel();
            
            javax.swing.GroupLayout jPanel1Layout2 = new javax.swing.GroupLayout(PainelBase[i2]);
            PainelBase[i2].setLayout(jPanel1Layout2);
            jPanel1Layout2.setHorizontalGroup(
                jPanel1Layout2.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 856, Short.MAX_VALUE)
            );
            jPanel1Layout2.setVerticalGroup(
                jPanel1Layout2.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 386, Short.MAX_VALUE)
            );
            ScrollPainel[i2] = new JScrollPane(PainelBase[i2], JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            ScrollPainel[i2].setViewportView(PainelBase[i2]);
            jTabbedPane1.addTab(o2.getString("nome_categoria"), ScrollPainel[i2]);
            
            ScrollPainel[i2].getVerticalScrollBar().setUnitIncrement(30);
            int PadraoX = 15;
            int EspacamentoX = 20;
            int EspacamentoY = 10;
            int LimiteQuadro = 4;
            int ContagemLimite = 0;

            int TamanhoPainel = (jPanel4.getWidth()/LimiteQuadro)-5;
            int Linha = 0;
            
            int NLinha = (CrimesRegistro.length()/3);
            int TamanhoTabela = 200+(50*NLinha)+45;
            
            Container container = PainelBase[i2];
            
            javax.swing.GroupLayout jPanel1Layout3 = new javax.swing.GroupLayout(PainelBase[i2]);
            container.setLayout(jPanel1Layout3);
            jPanel1Layout3.setHorizontalGroup(
                jPanel1Layout3.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 200, Short.MAX_VALUE)
            );
            
            
            for(int i = 0; i < CrimesRegistro.length(); i++){
                final int As = i;
                final int As2 = i2;
                
                JSONObject o = CrimesRegistro.getJSONObject(i);
                if(o.getInt("categoria") == o2.getInt("id")){
                    Painel[i2][i] = new JPanel();
                    Painel[i2][i].setBounds(PadraoX+(TamanhoPainel*ContagemLimite), 20+(50*Linha), 220, 45);
                    
                    //Painel[i].setBackground(new java.awt.Color(0, 0, 153));
                    Painel[i2][i].setBorder(javax.swing.BorderFactory.createEtchedBorder());
                    container.add( Painel[i2][i] );

                    Container container2 = Painel[i2][i];
                    container2.setLayout(null);

                    //Botoes[i] = new javax.swing.JToggleButton();
                    
                    //================ BOTOES ================
                    if("UNICO".equals(o.getString("tipo"))){
                        JSONObject PegarDadosBt = o;
                        PegarDadosBt.put("i1", i2);
                        PegarDadosBt.put("i2", i);
                        RegistroBotoes.put(PegarDadosBt);
                        
                        Botoes[i2][i] = new JToggleButton(o.getString("texto"));
                        Botoes[i2][i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/police/imagens/falso.png"))); // NOI18N
                        Botoes[i2][i].setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                        Botoes[i2][i].setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
                        Botoes[i2][i].setRolloverEnabled(false);
                        Botoes[i2][i].setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/police/imagens/verdadeiro.png"))); // NOI18N
                        Botoes[i2][i].setBackground(new java.awt.Color(255, 255, 255));

                        Botoes[i2][i].addItemListener(new ItemListener() {
                            public void itemStateChanged(ItemEvent ev) {
                               AttCrimes();
                            }
                        });
                        
                        Botoes[i2][i].setFont(new java.awt.Font("Tahoma", 0, FontPorTamanho(o.getString("texto").length())));
                        
                        Botoes[i2][i].setBounds(3, 3, 214, 39);
                        
                        if(o.has("obs") && !"".equals(o.getString("obs"))) Botoes[i2][i].setToolTipText(o.getString("obs"));
                        
                        container2.add( Botoes[i2][i] );
                    //=========================================
                    }else{
                        JSONObject PegarDadosBt = o;
                        PegarDadosBt.put("i1", i2);
                        PegarDadosBt.put("i2", i);
                        RegistroInputs.put(PegarDadosBt);
                        /*Textos[i2][i] = new JLabel(o.getString("texto"));//new javax.swing.JLabel();
                        Textos[i2][i].setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                        Textos[i2][i].setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

                        Textos[i2][i].setBounds(3, 3, 214, 39);
                        container2.add( Textos[i2][i] );*/
                        
                        InputText[i2][i] = new JTextField(o.getString("texto"));
                        //InputText[i2][i].setFont(new java.awt.Font("Tahoma", 0, 16));
                        InputText[i2][i].setHorizontalAlignment(javax.swing.JTextField.CENTER);
                        
                        InputText[i2][i].setBounds(3, 3, 214, 39);
                        
                        InputText[i2][i].setFont(new java.awt.Font("Tahoma", 0, FontPorTamanho(o.getString("texto").length())));
                        
                        InputText[i2][i].addKeyListener(new KeyAdapter() {
                            public void keyTyped(KeyEvent e) {
                                char c = e.getKeyChar();
                                String TextoGet = InputText[As2][As].getText();
                                if (!((c >= '0') && (c <= '9') ||
                                    (c == KeyEvent.VK_BACK_SPACE) ||
                                    (c == KeyEvent.VK_DELETE)) ||
                                    (TextoGet.length() > 8)) {
                                  getToolkit().beep();
                                  e.consume();
                                }
                                //System.out.println("TextoGet.length(): "+TextoGet.length());
                            }
                            public void keyReleased(java.awt.event.KeyEvent evt) {
                                AttCrimes();
                            }
                         });
                        
                        InputText[i2][i].addFocusListener(new FocusListener() {
                            @Override
                            public void focusGained(FocusEvent e) {
                                if (InputText[As2][As].getText().equals(o.getString("texto"))) {
                                    InputText[As2][As].setText("");
                                    InputText[As2][As].setFont(new java.awt.Font("Tahoma", 0, 16));
                                    //InputText[As2][As].setForeground(Color.BLACK);
                                }
                            }
                            @Override
                            public void focusLost(FocusEvent e) {
                                if (InputText[As2][As].getText().isEmpty()) {
                                    //InputText[As2][As].setForeground(Color.GRAY);
                                    InputText[As2][As].setText(o.getString("texto"));
                                    
                                    InputText[As2][As].setFont(new java.awt.Font("Tahoma", 0, FontPorTamanho(o.getString("texto").length())));
                                    
                                }
                            }
                        });
                        
                        if(o.has("obs") && !"".equals(o.getString("obs"))) InputText[i2][i].setToolTipText(o.getString("obs"));
                        
                        container2.add( InputText[i2][i] );
                    }
                    
                    
                    
                    ContagemLimite++;
                    if(ContagemLimite>=LimiteQuadro){
                        ContagemLimite=0;
                        Linha++;
                    }
                       
                }
            }
            if(ContagemLimite == 0)Linha--;
            jPanel1Layout3.setVerticalGroup(
                jPanel1Layout3.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 40+(50*Linha)+45, Short.MAX_VALUE) //386
            );
            this.pack();
        }
         /*   
        
        Texto1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Texto1.setText("Tentativa de Roubo:");

        Botao1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/police/imagens/falso.png"))); // NOI18N
        Botao1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Botao1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Botao1.setRolloverEnabled(false);
        Botao1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/police/imagens/verdadeiro.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Texto1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Botao1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(642, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Texto1, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(Botao1, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                .addContainerGap(343, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel1);

        jTabbedPane1.addTab("Crimes 1", jScrollPane1);
        */
        AdicionarBotoesBeneficios();
    }
    
    public void AdicionarBotoesBeneficios(){
        //for(int i2 = 0; i2 < BeneficiosCrimes.length(); i2++){
            //JSONObject o2 = BeneficiosCrimes.getJSONObject(i2);
            int i2 = 40;
            PainelBase[i2] = new JPanel();
            
            javax.swing.GroupLayout jPanel1Layout2 = new javax.swing.GroupLayout(PainelBase[i2]);
            PainelBase[i2].setLayout(jPanel1Layout2);
            jPanel1Layout2.setHorizontalGroup(
                jPanel1Layout2.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 856, Short.MAX_VALUE)
            );
            jPanel1Layout2.setVerticalGroup(
                jPanel1Layout2.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 386, Short.MAX_VALUE)
            );
            ScrollPainel[i2] = new JScrollPane(PainelBase[i2], JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            ScrollPainel[i2].setViewportView(PainelBase[i2]);
            jTabbedPane1.addTab("Aumento/Redução de Pena", ScrollPainel[i2]);
            
            ScrollPainel[i2].getVerticalScrollBar().setUnitIncrement(30);
            int PadraoX = 15;
            int LimiteQuadro = 4;
            int ContagemLimite = 0;

            int TamanhoPainel = (jPanel4.getWidth()/LimiteQuadro)-5;
            int Linha = 0;
            
            int NLinha = (CrimesRegistro.length()/3);
            
            Container container = PainelBase[i2];
            
            javax.swing.GroupLayout jPanel1Layout3 = new javax.swing.GroupLayout(PainelBase[i2]);
            container.setLayout(jPanel1Layout3);
            jPanel1Layout3.setHorizontalGroup(
                jPanel1Layout3.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 200, Short.MAX_VALUE)
            );
            
            
            for(int i = 0; i < BeneficiosCrimes.length(); i++){
                final int As = i;
                final int As2 = i2;
                
                JSONObject o = BeneficiosCrimes.getJSONObject(i);
               
                Painel[i2][i] = new JPanel();
                Painel[i2][i].setBounds(PadraoX+(TamanhoPainel*ContagemLimite), 20+(50*Linha), 220, 45);

                //Painel[i].setBackground(new java.awt.Color(0, 0, 153));
                Painel[i2][i].setBorder(javax.swing.BorderFactory.createEtchedBorder());
                container.add( Painel[i2][i] );

                Container container2 = Painel[i2][i];
                container2.setLayout(null);

                //Botoes[i] = new javax.swing.JToggleButton();

                //================ BOTOES ================
                JSONObject PegarDadosBt = o;
                PegarDadosBt.put("i1", i2);
                PegarDadosBt.put("i2", i);
                RegistroBotoes.put(PegarDadosBt);

                Botoes[i2][i] = new JToggleButton(o.getString("texto"));
                Botoes[i2][i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/police/imagens/falso.png"))); // NOI18N
                Botoes[i2][i].setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                Botoes[i2][i].setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
                Botoes[i2][i].setRolloverEnabled(false);
                Botoes[i2][i].setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/police/imagens/verdadeiro.png"))); // NOI18N
                Botoes[i2][i].setBackground(new java.awt.Color(255, 255, 255));

                Botoes[i2][i].addItemListener(new ItemListener() {
                    public void itemStateChanged(ItemEvent ev) {
                       AttCrimes();
                    }
                });

                Botoes[i2][i].setFont(new java.awt.Font("Tahoma", 0, FontPorTamanho(o.getString("texto").length())));

                Botoes[i2][i].setBounds(3, 3, 214, 39);
                container2.add( Botoes[i2][i] );
                //=========================================
                



                ContagemLimite++;
                if(ContagemLimite>=LimiteQuadro){
                    ContagemLimite=0;
                    Linha++;
                }
            }
            if(ContagemLimite == 0)Linha--;
            jPanel1Layout3.setVerticalGroup(
                jPanel1Layout3.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 40+(50*Linha)+45, Short.MAX_VALUE) //386
            );
            this.pack();
        //}
    }
    
    public void AtualizarJanelas(){
        int AtualTab = jTabbedPane1.getSelectedIndex();
        while (jTabbedPane1.getTabCount() > 0)
            jTabbedPane1.remove(0);
        AdicionarBotoes();
        if(AtualTab > 0 && AtualTab < jTabbedPane1.getTabCount()) jTabbedPane1.setSelectedIndex(AtualTab);
        
    }
    
    public void AttCrimes(){
        int MultaTotal = 0;
        int MesesTotal = 0;
        String StrMeses = "N/A";
        String StrMultas = "N/A";
        
        String CrimesExtenso = "N/A";
        int ContageCrimes = 0;
        
        String ReducaoDis = "";
        String ReducaoDB = "";
        
        CrimesDiscordFormat = "";
        CrimesDBFormat = "";
        
        String CrimesDis = "";
        String CrimesDB = "";
        
        for(int i = 0; i < RegistroInputs.length(); i++){
            JSONObject obj = RegistroInputs.getJSONObject(i);
            int ir1 = obj.getInt("i1");
            int ir2 = obj.getInt("i2");
            String TextoInput = InputText[ir1][ir2].getText();
            if(!TextoInput.isEmpty() && !TextoInput.equals(obj.getString("texto"))){
                int ValorInput = Integer.parseInt(TextoInput);
                //System.out.println("TextoInput: "+Integer.parseInt(TextoInput));
                if(ValorInput > 0){
                    int ValorMulta = (obj.getInt("multa")*ValorInput);
                    int ValorMeses = (obj.getInt("meses")*ValorInput);
                    //System.out.println("TextoInput: "+Integer.parseInt(TextoInput)+" / ValorInput: "+ValorInput+" / obj.getInt(\"meses\"): "+obj.getInt("meses")+" / MesesTotal: "+MesesTotal);
                    MultaTotal+=ValorMulta;
                    MesesTotal+=ValorMeses;
                    
                    if(ContageCrimes == 0){
                        CrimesExtenso = obj.getString("texto")+" (x"+ValorInput+")";
                    }else{
                        CrimesExtenso += " + "+obj.getString("texto")+" (x"+ValorInput+")";
                        CrimesDB+=" // ";
                    }
                    CrimesDis+="\n• "+obj.getString("texto")+" (x"+ValorInput+") [MESES: "+ValorMeses+" / MULTA: $"+String.format("%,d", ValorMulta)+"]";
                    CrimesDB+=obj.getString("texto")+" (x"+ValorInput+") [MESES: "+ValorMeses+" / MULTA: $"+String.format("%,d", ValorMulta)+"]";
                    ContageCrimes++;
                }
            }
        }
        
        for(int i = 0; i < RegistroBotoes.length(); i++){
            JSONObject obj = RegistroBotoes.getJSONObject(i);
            int ir1 = obj.getInt("i1");
            int ir2 = obj.getInt("i2");
            if(Botoes[ir1][ir2].isSelected()){
                //System.out.println("Nome do crime: "+obj.getString("texto"));
                if(ir1 != 40){
                    MultaTotal+=obj.getInt("multa");
                    MesesTotal+=obj.getInt("meses");
                    if(ContageCrimes == 0){
                        CrimesExtenso = obj.getString("texto");
                    }else{
                        CrimesExtenso += " + "+obj.getString("texto");
                        CrimesDB+=" // ";
                    }
                    CrimesDis+="\n• "+obj.getString("texto")+" [MESES: "+obj.getInt("meses")+" / MULTA: $"+String.format("%,d", obj.getInt("multa"))+"]";
                    CrimesDB+=obj.getString("texto")+" [MESES: "+obj.getInt("meses")+" / MULTA: $"+String.format("%,d", obj.getInt("multa"))+"]";
                    ContageCrimes++;
                }else{
                    int ValorConta = 0;
                    if("FIXO".equals(obj.getString("calculo"))){
                        ValorConta = obj.getInt("meses");
                        //System.out.println("obj.getInt(\"meses\"): "+obj.getInt("meses")+" / MesesTotal: "+MesesTotal+" / ValorConta: "+ValorConta);
                        if("REDUCAO".equals(obj.getString("tipo"))){
                            ReducaoDis+="\n• "+obj.getString("texto")+" (-"+obj.getInt("meses")+")[REDUÇÃO DE "+ValorConta+" MESES]";
                            MesesTotal-=ValorConta;
                            ReducaoDB+=" // "+obj.getString("texto")+" (-"+obj.getInt("meses")+")[REDUÇÃO DE "+ValorConta+" MESES]";
                        }else{
                            ReducaoDis+="\n• "+obj.getString("texto")+" (+"+obj.getInt("meses")+")[AUMENTO DE "+ValorConta+" MESES]";
                            MesesTotal+=ValorConta;
                            ReducaoDB+=" // "+obj.getString("texto")+" (-"+obj.getInt("meses")+")[REDUÇÃO DE "+ValorConta+" MESES]";
                        }
                    }else{
                        ValorConta = (obj.getInt("meses")*MesesTotal)/100;
                        if("REDUCAO".equals(obj.getString("tipo"))){
                            ReducaoDis+="\n• "+obj.getString("texto")+" (-"+obj.getInt("meses")+"%)[REDUÇÃO DE "+ValorConta+" MESES]";
                            MesesTotal-=ValorConta;
                            ReducaoDB+=" // "+obj.getString("texto")+" (-"+obj.getInt("meses")+"%)[REDUÇÃO DE "+ValorConta+" MESES]";
                        }else{
                            ReducaoDis+="\n• "+obj.getString("texto")+" (+"+obj.getInt("meses")+"%)[AUMENTO DE "+ValorConta+" MESES]";
                            MesesTotal+=ValorConta;
                            ReducaoDB+=" // "+obj.getString("texto")+" (+"+obj.getInt("meses")+"%)[AUMENTO DE "+ValorConta+" MESES]";
                        }
                    }
                }
                
            }
        }
        boolean Ativado = false;
        boolean AtivadoOn = false;
        if(UsuarioPegar.has("id_usuario")){
            if(!"N/A".equals(CrimesExtenso)){
                info_CrimesS.setText(CrimesExtenso);
                Ativado = true;
                if(!InicializadorMain.ModoOffline){
                    AtivadoOn=true;
                }else{
                    ProcuradoBt.setToolTipText("DESATIVADO NO MODO OFFLINE");
                    SalvarBt.setToolTipText("DESATIVADO NO MODO OFFLINE");
                }
                
                
                String nome = UsuarioPegar.getString("nome")+" "+UsuarioPegar.getString("sobrenome");
                if(UsuarioPegar.has("nome_completo")) nome = UsuarioPegar.getString("nome_completo");
                if(" ".equals(nome))nome="Sem Registro";
                String RGi = "";
                if(!"N/A".equals(UsuarioPegar.getString("registration"))) RGi = " <"+UsuarioPegar.getString("registration")+">".toUpperCase();
                
                        
                JSONObject PolicialUser = Usuario.getDados();
                String nome_policial = PolicialUser.getString("nome")+" "+PolicialUser.getString("sobrenome");
                if(PolicialUser.has("nome_completo")) nome_policial = PolicialUser.getString("nome_completo");
                if(" ".equals(nome_policial))nome_policial="Sem Registro";
                
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String dataFormatada = simpleDateFormat.format(date);
                
                //System.out.println("MesesTotal: "+MesesTotal+" / OpcoesDeCrimes.getInt(\"pena_max\"): "+OpcoesDeCrimes.getInt("pena_max"));
                String StrPenaTotal = MesesTotal+" meses";
                if(OpcoesDeCrimes.getInt("pena_max") > 0 && MesesTotal >= OpcoesDeCrimes.getInt("pena_max")){
                    MesesTotal = OpcoesDeCrimes.getInt("pena_max");
                    StrPenaTotal = MesesTotal+" meses [PENA MÁXIMA]";
                }
                //System.out.println("MesesTotal: "+MesesTotal+" / OpcoesDeCrimes.getInt(\"pena_max\"): "+OpcoesDeCrimes.getInt("pena_max"));
                
                String FormatDiscord = "[INDIVÍDUO]("+nome+" ["+UsuarioPegar.getInt("id_usuario")+"])"+RGi+
                    "\n[POLICIAL]("+nome_policial+" ["+PolicialUser.getInt("id_usuario")+"])"+
                    "\n\n"+
                    "# CRIMES COMETIDOS:"+
                    CrimesDis+
                    "\n\n";
                
                if(!"".equals(ReducaoDis)) FormatDiscord+="# AUMENTO/REDUÇÃO DE PENA:"+ReducaoDis+"\n\n";
                
                FormatDiscord+= "* MULTA TOTAL: $"+String.format("%,d", MultaTotal)+
                    "\n* PENA TOTAL: "+StrPenaTotal+
                    "\n\n"+
                    "* DATA: "+dataFormatada;
                CrimesDiscordFormat = FormatDiscord;
                
                
                String FormatParaDB = "[INDIVÍDUO]("+nome+" ["+UsuarioPegar.getInt("id_usuario")+"])"+RGi+
                    " // [POLICIAL]("+nome_policial+" ["+PolicialUser.getInt("id_usuario")+"])"+
                    " //// "+
                    "# CRIMES COMETIDOS: //"+
                    CrimesDB+
                    " //// ";
                
                if(!"".equals(ReducaoDB)) FormatParaDB+="# AUMENTO/REDUÇÃO DE PENA:"+ReducaoDis+" //// ";
                
                FormatParaDB+= "* MULTA TOTAL: $"+String.format("%,d", MultaTotal)+
                    " // * PENA TOTAL: "+StrPenaTotal+
                    " //// "+
                    "* DATA: "+dataFormatada;
                CrimesDBFormat = FormatParaDB;
            }else{
                info_CrimesS.setText("Necessário escolher os crimes do indivíduo");
            }
        }else{
            info_CrimesS.setText("Necessário pesquisar ou cadastrar indivíduo");
        }
        CopiarDiscordBt.setEnabled(Ativado);
        
        ProcuradoBt.setEnabled(AtivadoOn);
        SalvarBt.setEnabled(AtivadoOn);
        
        if(MultaTotal>0) StrMultas = "$"+String.format("%,d", MultaTotal);
        if(MesesTotal>0) StrMeses = MesesTotal+" meses";
        info_Pena1S.setText(StrMultas);
        info_Pena2S.setText(StrMeses);
        CopiarDiscordBt.setText("COPIAR P/ DISCORD");
        
        s_Meses = MesesTotal;
        s_Multa = MultaTotal;
    }
    
    public static String FormatarParaDiscord(String Texto){
        return "```md\n"+Texto+"\n```";
    }
    public static String DesformatarDiscord(String texto){
        String TextoDesformatado = texto;
        TextoDesformatado = TextoDesformatado.replace("```md","");
        TextoDesformatado = TextoDesformatado.replace("```","");
        return TextoDesformatado;
    }
    
    public void ResetarBotoes(){
        for(int i = 0; i < RegistroBotoes.length(); i++){
            JSONObject obj = RegistroBotoes.getJSONObject(i);
            int ir1 = obj.getInt("i1");
            int ir2 = obj.getInt("i2");
            if(Botoes[ir1][ir2].isSelected()){
                Botoes[ir1][ir2].setSelected(false);
            }
        }
    }
    
    public void ResetarCampoTexto(){
        for(int i = 0; i < RegistroInputs.length(); i++){
            JSONObject obj = RegistroInputs.getJSONObject(i);
            int ir1 = obj.getInt("i1");
            int ir2 = obj.getInt("i2");
            String TextoInput = InputText[ir1][ir2].getText();
            if(!TextoInput.isEmpty() && !TextoInput.equals(obj.getString("texto"))){
                InputText[ir1][ir2].setText(obj.getString("texto"));
                InputText[ir1][ir2].setFont(new java.awt.Font("Tahoma", 0, FontPorTamanho(obj.getString("texto").length())));
            }
        }
    }
    
    public JSONObject UsuarioPorID(int user_id){
        for(int i = 0; i < usuariosDBarray.length(); i++){
            JSONObject obj = usuariosDBarray.getJSONObject(i);
            if(user_id==obj.getInt("id_usuario")){
                return obj;
            }
        }
        return new JSONObject();
    }
    public JSONObject UsuarioPorNome(String nome_user){
        nome_user = nome_user.toLowerCase();
        for(int i = 0; i < usuariosDBarray.length(); i++){
            JSONObject obj = usuariosDBarray.getJSONObject(i);
            String NomeUsuario1 = obj.getString("nome").toLowerCase();
            String NomeUsuario2 = obj.getString("sobrenome").toLowerCase();
            if(NomeUsuario1.contains(nome_user) || NomeUsuario2.contains(nome_user)){
                return obj;
            }
        }
        return new JSONObject();
    }
    public JSONObject UsuarioPorRegistration(String registro){
        registro = registro.toLowerCase();
        for(int i = 0; i < usuariosDBarray.length(); i++){
            JSONObject obj = usuariosDBarray.getJSONObject(i);
            String RegistrationUsuario = obj.getString("registration").toLowerCase();
            if(RegistrationUsuario.contains(registro)){
                return obj;
            }
        }
        return new JSONObject();
    }
    public JSONObject UsuarioPorPhone(String registro){
        registro = registro.toLowerCase();
        for(int i = 0; i < usuariosDBarray.length(); i++){
            JSONObject obj = usuariosDBarray.getJSONObject(i);
            String RegistrationUsuario = obj.getString("phone").toLowerCase();
            if(RegistrationUsuario.contains(registro)){
                return obj;
            }
        }
        return new JSONObject();
    }
    
    public int ProcuradoPorID(int user_id){
        int nivel_procurado = -1;
        for(int i3 = 0; i3 < procuradosDBarray.length(); i3++){
            JSONObject oproc = procuradosDBarray.getJSONObject(i3);
            int meseer = oproc.getInt("meses");
            int multaar = oproc.getInt("multas");
            int pagou = oproc.getInt("pagou");
            
            if(user_id==oproc.getInt("id_usuario")){
                int nv_procurado = oproc.getInt("nivel_procurado");
                if(pagou <= 0){
                    if(meseer > 0 || multaar > 0){
                        if(nv_procurado > nivel_procurado) nivel_procurado = nv_procurado;
                    }
                }
            }
        }
        return nivel_procurado;
    }
    
    public void DigitandoCampo(int Campo){
        if(!InicializadorMain.ModoOffline || Campo == 0){
            switch(Campo) {
                case 0:
                    txtID.setText(null);
                    txtNome.setText(null);
                    txtPlaca.setText(null);
                    txtTelefone.setText(null);
                    break;
                case 1:
                    txtNome.setText(null);
                    txtPlaca.setText(null);
                    txtTelefone.setText(null);
                    break;
                case 2:
                    txtID.setText(null);
                    txtPlaca.setText(null);
                    txtTelefone.setText(null);
                    break;
                case 3:
                    txtID.setText(null);
                    txtNome.setText(null);
                    txtTelefone.setText(null);
                    break;
                case 4:
                    txtID.setText(null);
                    txtNome.setText(null);
                    txtPlaca.setText(null);
                    break;
            }
        }
    }
    
    public void Alerta(String Titulo, String Mensagem){
        showMessageDialog(null,Titulo, Mensagem,JOptionPane.PLAIN_MESSAGE);
    }
    
    public JSONObject GetUserOffline(int user_id){
        for(int i2 = 0; i2 < UsuariosOfflineTbl.length(); i2++){
            JSONObject obj = UsuariosOfflineTbl.getJSONObject(i2);
            //System.out.println("hierarquiaDBarray: "+ohier.toString()+" // ");
            if(obj.getInt("user_id") == user_id){
                return obj;
            }
        }
        return null;
    }
    
    public void PegarUsuario(){
        int nivel_assinatura = SNWindows.getNivelSerialPC();
        ResetarCrimes();
        JSONObject Usuario = new JSONObject();
        int vzespreso = 0;
        int vzesmulta = 0;
        
        if(InicializadorMain.ModoOffline){
            String Cad1 = txtID.getText();
            String Cad2 = txtNome.getText();
            String Cad3 = txtPlaca.getText();
            String Cad4 = txtTelefone.getText();
            if(Cad1.length() <= 0){Alerta("Necessário digitar o passaporte!", "Ocorreu algum erro");txtID.grabFocus(); return ;}
            
            
            int PassaporteDigitado = Integer.parseInt(Cad1);
            JSONObject getUser = GetUserOffline(PassaporteDigitado);
            if(nivel_assinatura <= 0 && UsuariosOfflineTbl.length() > SNWindows.UsuariosAssinatura[nivel_assinatura]){
                getUser = null;
            }
            if(getUser != null){
                Usuario.put("id_usuario", getUser.getInt("user_id"));
                Usuario.put("nome_completo", getUser.getString("nome"));
                Usuario.put("nome", getUser.getString("nome"));
                Usuario.put("sobrenome", getUser.getString("nome"));
                Usuario.put("registration", getUser.getString("registration"));
                Usuario.put("phone", getUser.getString("phone"));
                
                if(getUser.getInt("age") > 0){
                    Usuario.put("age", getUser.getInt("age")+"");
                }else{
                    Usuario.put("age", "0");
                }
                //vzespreso = getUser.getInt("vezes_preso");
            }else{
                if(Cad2.length() <= 5){Alerta("Necessário digitar o nome! Está muito curto.", "Ocorreu algum erro");txtNome.grabFocus(); return ;}
                if(Cad3.length() <= 0)Cad3 = "N/A";
                if(Cad4.length() <= 0)Cad4 = "N/A";
                Usuario.put("id_usuario", Cad1);
                Usuario.put("nome_completo", Cad2);
                Usuario.put("nome", Cad2);
                Usuario.put("sobrenome", Cad2);
                Usuario.put("registration", Cad3);
                Usuario.put("phone", Cad4);
                Usuario.put("age", "0");
                
                
                if(UsuariosOfflineTbl.length() < SNWindows.UsuariosAssinatura[nivel_assinatura]){
                    JSONObject Novo_user = new JSONObject();
                    Novo_user.put("user_id", PassaporteDigitado);
                    Novo_user.put("nome", Cad2);
                    Novo_user.put("registration", Cad3);
                    Novo_user.put("phone", Cad4);
                    Novo_user.put("age", 0);

                    UsuariosOfflineTbl.put(Novo_user);
                    if(OpcoesUsuariosTbl.has("save_automatico") && OpcoesUsuariosTbl.getInt("save_automatico") == 1){
                        GerenciamentoUsuarios.SAVEUsersAdd(UsuariosOfflineTbl, OpcoesUsuariosTbl);
                    }else{
                        Object[] options = { "Registrar", "Não registrar" };
                        int result = JOptionPane.showOptionDialog(null, "Me parece que este usuário não está registrado no seu banco de dados, deseja registrar agora?\nAo registrar não precisará cadastrar novamente.", "Cadastro de Usuário", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                        if (result == JOptionPane.OK_OPTION) {
                            GerenciamentoUsuarios.SAVEUsersAdd(UsuariosOfflineTbl, OpcoesUsuariosTbl);
                        }
                    }
                }else{
                    if(!NaoAbrirDnvAss){
                        Object[] options = { "Quero uma Assinatura", "Não mostrar novamente" };
                        int result = JOptionPane.showOptionDialog(null, "Você chegou no seu limite de registro de usuários que é de "+SNWindows.UsuariosAssinatura[nivel_assinatura]+"."
                                + "\nVocê pode adquirir uma assinatura em nosso discord. (Exibir -> Sobre)", "Erro no Cadastro de Usuário", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                        if (result == JOptionPane.OK_OPTION) {
                            HttpDownloadUtility.openURL("https://discord.gg/nFNqvDs");
                        }else{
                            NaoAbrirDnvAss = true;
                        }
                    }
                }
                
            }
            
        }else{
            String TxtPegarBusca = txtID.getText();
            if(TxtPegarBusca.length() > 0){
                Usuario = UsuarioPorID(Integer.parseInt(TxtPegarBusca));
                if(!Usuario.has("id_usuario")){
                    Alerta("O passaporte '"+TxtPegarBusca+"' não foi encontrado no banco da cidade!", "Ocorreu algum erro");
                    return;
                }
            }else{
                TxtPegarBusca = txtNome.getText();
                if(TxtPegarBusca.length() > 0){
                    if(TxtPegarBusca.length() < 4){
                        Alerta("O nome '"+TxtPegarBusca+"' é muito pequeno para procurarmos! Coloque mais de 3 caracteres.", "Ocorreu algum erro");
                        return;
                    }
                    Usuario = UsuarioPorNome(TxtPegarBusca);
                    if(!Usuario.has("id_usuario")){
                        Alerta("O nome '"+TxtPegarBusca+"' não foi encontrado no banco da cidade!", "Ocorreu algum erro");
                        return;
                    }
                }else{
                    TxtPegarBusca = txtPlaca.getText();
                    if(TxtPegarBusca.length() > 0){
                        if(TxtPegarBusca.length() < 6){
                            Alerta("O registro '"+TxtPegarBusca+"' é muito pequeno para procurarmos! Coloque mais de 5 caracteres.", "Ocorreu algum erro");
                            return;
                        }
                        Usuario = UsuarioPorRegistration(TxtPegarBusca);
                        if(!Usuario.has("id_usuario")){
                            Alerta("A placa '"+TxtPegarBusca+"' não foi encontrado no banco da cidade!", "Ocorreu algum erro");
                            return;
                        }
                    }else{
                        TxtPegarBusca = txtTelefone.getText();
                        if(TxtPegarBusca.length() > 0){
                            if(TxtPegarBusca.length() < 4){
                                Alerta("O telefone '"+TxtPegarBusca+"' é muito pequeno para procurarmos! Siga o exemplo: 000-000 ou 0000-0000.", "Ocorreu algum erro");
                                return;
                            }
                            Usuario = UsuarioPorPhone(TxtPegarBusca);
                            if(!Usuario.has("id_usuario")){
                                Alerta("O telefone '"+TxtPegarBusca+"' não foi encontrado no banco da cidade!", "Ocorreu algum erro");
                                return;
                            }
                        }else{
                            Alerta("Necessário digitar algo para buscarmos!", "Ocorreu algum erro");
                            return;
                        }
                    }
                }
            }
        }
        PesquisarPainel.setVisible(false);
        DetalhesPainel.setVisible(true);
        this.revalidate();
        this.repaint();
        this.pack();
        
        UsuarioPegar = Usuario;
        int pass = Usuario.getInt("id_usuario");
        //PassaPreso=Usuario.getInt("id_usuario");
        //String discord = o.getString("discord");
        String nome = Usuario.getString("nome")+" "+Usuario.getString("sobrenome");
        if(Usuario.has("nome_completo")) nome = Usuario.getString("nome_completo");
        
        if(" ".equals(nome))nome="Sem Registro";
        String identidade = Usuario.getString("registration");
        
        //SETAGENS DE TEXTOS INFO
        des_NomeS.setText(nome+" ("+pass+")");
        String Idade = Usuario.getString("age");
        if("0".equals(Idade)){
            Idade="N/A";
        }else{
            Idade+=" anos";
        }
        des_IdadeS.setText(Idade);
        des_RegistroS.setText(identidade);
        
        
        
        //System.out.println("usuariosDBarray: "+o.toString()+" ?°/ ");

        /*prenderDBarray = new JSONObject();
        //prenderDBarray=usuariosDBarray;
        prenderDBarray.put("nome", nome);
        prenderDBarray.put("passaporte", passaporte);
        prenderDBarray.put("identidade", identidade);*/
        /*
        Usuario usuario = new Usuario();
        JSONObject usua = new JSONObject(usuario.getDados());
        prenderDBarray.put("id_prendeu", usua.getString("id_usuario"));*/

        
        for(int i2 = 0; i2 < prisoesDBarray.length(); i2++){
            JSONObject ohier = prisoesDBarray.getJSONObject(i2);
            //System.out.println("hierarquiaDBarray: "+ohier.toString()+" // ");
            int meseer = ohier.getInt("meses");
            int multaar = ohier.getInt("multas");
            int pagou = ohier.getInt("pagou");
            if(pass==ohier.getInt("id_usuario")){
                if("".equals(ohier.getString("limpo"))){
                    if((meseer > 0) && pagou > 0){
                        vzespreso++;
                    }
                    if((meseer <= 0) && multaar > 0 && pagou > 0){
                        vzesmulta++;
                    }
                }
            }
        }

        
        des_PassagemS.setText(vzespreso+"");
        des_MultaS.setText(vzesmulta+"");
        
        if(vzespreso>0){
            des_ReuS.setText("NÃO");
            des_ReuS.setForeground(new java.awt.Color(255,51,51));
            //check1.setSelected(false);
            verpreso.setEnabled(true);
        }else{
            des_ReuS.setText("SIM");
            des_ReuS.setForeground(new java.awt.Color(51,153,0));
            //check1.setSelected(true);
            verpreso.setEnabled(false);
        }

        if(vzesmulta>0){
            vermulta.setEnabled(true);
        }else{
            vermulta.setEnabled(false);
        }

        int PlayerProcurado = ProcuradoPorID(pass);
        if(PlayerProcurado >= 0){
            des_ProcuradoS.setText("SIM");
            des_ProcuradoS.setForeground(new java.awt.Color(255,51,51));
            procuradoBt.setEnabled(true);
            
            
            String NivelPr = "Sem Informação";
            switch(PlayerProcurado) {
                case 1:
                    NivelPr = "Risco Baixo";
                    break;
                case 2:
                    NivelPr = "Perigoso";
                    break;
                case 3:
                    NivelPr = "Muito Perigoso";
                    break;
                case 4:
                    NivelPr = "Extremamente Perigoso";
                    break;
            }
            des_Info.setText(NivelPr.toUpperCase());
            des_Info.setForeground(new java.awt.Color(255,51,51));
        }else{
            des_ProcuradoS.setText("NÃO");
            des_ProcuradoS.setForeground(new java.awt.Color(51,153,0));
            procuradoBt.setEnabled(false);
            
            des_Info.setText("");
            des_Info.setForeground(new java.awt.Color(90, 113, 216));
        }
        AttCrimes();
        /*
        if(!achou){
            jLabel13.setText("Individuo não encontrado");
            PassaPreso=0;
            jLabel14.setText("NÃO");
            check1.setSelected(false);
            jLabel14.setForeground(new java.awt.Color(255,51,51));
            jLabel16.setText("0");
            procuradoTxt.setText("NÃO");
            registrar.setEnabled(false);
            verpreso.setEnabled(false);
            guardar_procurado.setEnabled(false);
        }*/
    }
    public int FontPorTamanho(int length){
        if(length > 27){
            return 8;
        }else if(length > 24){
            return 9;
        }else if(length > 20){
            return 10;
        }else if(length > 15){
            return 11;
        }else{
            return 12;
        }
    }
    
    public void ResetarCrimes(){
        ResetarBotoes();
        ResetarCampoTexto();
        PegarDBMain();
    }
    
    public void ResetarTudo(){
        UsuarioPegar = new JSONObject();
        ResetarCrimes();
        DigitandoCampo(0);
        
        PesquisarPainel.setVisible(true);
        DetalhesPainel.setVisible(false);
        this.revalidate();
        this.repaint();
        this.pack();
        AttCrimes();
        txtID.requestFocus();
    }
    
    public boolean AbrirMenuVerPrisoes(int ModeloBusca){
        int pass = 0;
        if(UsuarioPegar.has("id_usuario")){
            pass = UsuarioPegar.getInt("id_usuario");
        }
        if(pass > 0){
            if(!verprisoes.isVisible()){
                verprisoes.setVisible(true);
                verprisoes.ResetarCampos();
                verprisoes.verpresoes=ModeloBusca;
                verprisoes.TabelaAtt();
                verprisoes.id.setText(pass+"");
                verprisoes.id.setEnabled(false);
                verprisoes.resetar.setEnabled(false);
                verprisoes.PesquisarT();
                verprisoes.setLocationRelativeTo(null);
            }else{
                verprisoes.verpresoes=ModeloBusca;
                verprisoes.TabelaAtt();
                verprisoes.id.setText(pass+"");
                verprisoes.requestFocus();
                verprisoes.PesquisarT();
            }
            return true;
        }
        return false;
    }
    
    void CopiarClipboard(String texto){
        StringSelection stringSelection = new StringSelection(texto);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        PainelDetalhes = new javax.swing.JPanel();
        info_CrimesS = new javax.swing.JTextField();
        info_Pena1S = new javax.swing.JLabel();
        info_Crimes = new javax.swing.JLabel();
        info_Comando = new javax.swing.JLabel();
        info_ComandoS = new javax.swing.JTextField();
        TimeAgora = new javax.swing.JLabel();
        info_Pena1 = new javax.swing.JLabel();
        info_Pena2 = new javax.swing.JLabel();
        info_Pena2S = new javax.swing.JLabel();
        DetalhesPainel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        des_Nome = new javax.swing.JLabel();
        des_NomeS = new javax.swing.JLabel();
        des_Idade = new javax.swing.JLabel();
        des_IdadeS = new javax.swing.JLabel();
        des_PassagemS = new javax.swing.JLabel();
        des_Passagem = new javax.swing.JLabel();
        verpreso = new javax.swing.JButton();
        des_Multa = new javax.swing.JLabel();
        des_MultaS = new javax.swing.JLabel();
        vermulta = new javax.swing.JButton();
        des_Reu = new javax.swing.JLabel();
        des_ReuS = new javax.swing.JLabel();
        des_Registro = new javax.swing.JLabel();
        des_RegistroS = new javax.swing.JLabel();
        des_Procurado = new javax.swing.JLabel();
        des_ProcuradoS = new javax.swing.JLabel();
        procuradoBt = new javax.swing.JButton();
        des_Info = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        SalvarBt = new javax.swing.JButton();
        ResetarBt = new javax.swing.JButton();
        ProcuradoBt = new javax.swing.JButton();
        CopiarDiscordBt = new javax.swing.JButton();
        PesquisarPainel = new javax.swing.JPanel();
        txtID = new javax.swing.JTextField();
        PesquisarBt = new javax.swing.JButton();
        txtNome = new javax.swing.JTextField();
        txtPlaca = new javax.swing.JTextField();
        txtTelefone = new javax.swing.JTextField();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("REGISTRO DE PRISÕES");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("REGISTRO DE PRISÕES");

        jTabbedPane1.setToolTipText("");
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 946, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 334, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel4);

        jTabbedPane1.addTab("Crimes 1", jScrollPane1);

        PainelDetalhes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "INFORMAÇÕES PENAL", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        info_CrimesS.setEditable(false);
        info_CrimesS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        info_CrimesS.setForeground(new java.awt.Color(153, 153, 255));

        info_Pena1S.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        info_Pena1S.setForeground(new java.awt.Color(153, 153, 255));
        info_Pena1S.setText("CARREGANDO");

        info_Crimes.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        info_Crimes.setForeground(new java.awt.Color(255, 255, 255));
        info_Crimes.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        info_Crimes.setText("CRIMES COMETIDOS:");

        info_Comando.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        info_Comando.setForeground(new java.awt.Color(255, 255, 255));
        info_Comando.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        info_Comando.setText("COMANDOS:");

        info_ComandoS.setEditable(false);
        info_ComandoS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        info_ComandoS.setForeground(new java.awt.Color(153, 153, 255));
        info_ComandoS.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        TimeAgora.setForeground(new java.awt.Color(255, 255, 255));
        TimeAgora.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TimeAgora.setText(" ");

        info_Pena1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        info_Pena1.setForeground(new java.awt.Color(255, 255, 255));
        info_Pena1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        info_Pena1.setText("MULTA TOTAL:");

        info_Pena2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        info_Pena2.setForeground(new java.awt.Color(255, 255, 255));
        info_Pena2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        info_Pena2.setText("PENA TOTAL:");

        info_Pena2S.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        info_Pena2S.setForeground(new java.awt.Color(153, 153, 255));
        info_Pena2S.setText("CARREGANDO");

        javax.swing.GroupLayout PainelDetalhesLayout = new javax.swing.GroupLayout(PainelDetalhes);
        PainelDetalhes.setLayout(PainelDetalhesLayout);
        PainelDetalhesLayout.setHorizontalGroup(
            PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelDetalhesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TimeAgora, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(PainelDetalhesLayout.createSequentialGroup()
                        .addGroup(PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(info_Crimes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(info_Pena1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PainelDetalhesLayout.createSequentialGroup()
                                .addComponent(info_Pena1S, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(info_Pena2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(info_Pena2S, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(info_Comando, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(info_ComandoS))
                            .addComponent(info_CrimesS))))
                .addContainerGap())
        );
        PainelDetalhesLayout.setVerticalGroup(
            PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelDetalhesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(info_CrimesS, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(info_Crimes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(info_Pena1S, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(info_ComandoS, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(info_Comando, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(info_Pena1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(info_Pena2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(info_Pena2S, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TimeAgora)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        DetalhesPainel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BANCO DA POLÍCIA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        des_Nome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        des_Nome.setForeground(new java.awt.Color(255, 255, 255));
        des_Nome.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        des_Nome.setText("NOME:");

        des_NomeS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        des_NomeS.setForeground(new java.awt.Color(153, 153, 255));
        des_NomeS.setText("CARREGANDO...");

        des_Idade.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        des_Idade.setForeground(new java.awt.Color(255, 255, 255));
        des_Idade.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        des_Idade.setText("IDADE:");

        des_IdadeS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        des_IdadeS.setForeground(new java.awt.Color(153, 153, 255));
        des_IdadeS.setText("CARREGANDO...");

        des_PassagemS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        des_PassagemS.setForeground(new java.awt.Color(153, 153, 255));
        des_PassagemS.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        des_PassagemS.setText("S/ INFO");

        des_Passagem.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        des_Passagem.setForeground(new java.awt.Color(255, 255, 255));
        des_Passagem.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        des_Passagem.setText("PASSAGENS:");

        verpreso.setBackground(new java.awt.Color(255, 255, 255));
        verpreso.setText("VER");
        verpreso.setEnabled(false);
        verpreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verpresoActionPerformed(evt);
            }
        });

        des_Multa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        des_Multa.setForeground(new java.awt.Color(255, 255, 255));
        des_Multa.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        des_Multa.setText("MULTAS:");

        des_MultaS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        des_MultaS.setForeground(new java.awt.Color(153, 153, 255));
        des_MultaS.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        des_MultaS.setText("S/ INFO");

        vermulta.setBackground(new java.awt.Color(255, 255, 255));
        vermulta.setText("VER");
        vermulta.setEnabled(false);
        vermulta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vermultaActionPerformed(evt);
            }
        });

        des_Reu.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        des_Reu.setForeground(new java.awt.Color(255, 255, 255));
        des_Reu.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        des_Reu.setText("RÉU PRIMÁRIO:");

        des_ReuS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        des_ReuS.setForeground(new java.awt.Color(153, 153, 255));
        des_ReuS.setText("CARREGANDO...");

        des_Registro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        des_Registro.setForeground(new java.awt.Color(255, 255, 255));
        des_Registro.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        des_Registro.setText("REGISTRO:");

        des_RegistroS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        des_RegistroS.setForeground(new java.awt.Color(153, 153, 255));
        des_RegistroS.setText("CARREGANDO...");

        des_Procurado.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        des_Procurado.setForeground(new java.awt.Color(255, 255, 255));
        des_Procurado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        des_Procurado.setText("PROCURADO:");

        des_ProcuradoS.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        des_ProcuradoS.setForeground(new java.awt.Color(153, 153, 255));
        des_ProcuradoS.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        des_ProcuradoS.setText("NÃO");

        procuradoBt.setBackground(new java.awt.Color(255, 255, 255));
        procuradoBt.setText("VER");
        procuradoBt.setEnabled(false);
        procuradoBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                procuradoBtActionPerformed(evt);
            }
        });

        des_Info.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        des_Info.setForeground(new java.awt.Color(255, 255, 255));
        des_Info.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(des_Reu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(des_Nome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(des_NomeS, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                    .addComponent(des_ReuS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(des_Idade, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(des_IdadeS, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(des_Registro, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(des_RegistroS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(des_Procurado)
                    .addComponent(des_Passagem, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(des_ProcuradoS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(des_PassagemS, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(verpreso)
                    .addComponent(procuradoBt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(des_Multa, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(des_MultaS, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(vermulta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(des_Info, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(des_MultaS, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(vermulta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(des_Nome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(des_Idade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(des_IdadeS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(des_NomeS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(des_Passagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(des_PassagemS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(verpreso, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(des_Multa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(des_RegistroS, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(des_ReuS, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(des_Reu, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(des_Registro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(des_ProcuradoS, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                            .addComponent(procuradoBt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(des_Procurado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(des_Info, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout DetalhesPainelLayout = new javax.swing.GroupLayout(DetalhesPainel);
        DetalhesPainel.setLayout(DetalhesPainelLayout);
        DetalhesPainelLayout.setHorizontalGroup(
            DetalhesPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DetalhesPainelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        DetalhesPainelLayout.setVerticalGroup(
            DetalhesPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DetalhesPainelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        SalvarBt.setBackground(new java.awt.Color(255, 255, 255));
        SalvarBt.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        SalvarBt.setText("REGISTRAR PRISÃO");
        SalvarBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalvarBtActionPerformed(evt);
            }
        });

        ResetarBt.setBackground(new java.awt.Color(255, 255, 255));
        ResetarBt.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        ResetarBt.setText("RESETAR");
        ResetarBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetarBtActionPerformed(evt);
            }
        });

        ProcuradoBt.setBackground(new java.awt.Color(255, 255, 255));
        ProcuradoBt.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        ProcuradoBt.setText("REGISTRAR PROCURADO");
        ProcuradoBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProcuradoBtActionPerformed(evt);
            }
        });

        CopiarDiscordBt.setBackground(new java.awt.Color(255, 255, 255));
        CopiarDiscordBt.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        CopiarDiscordBt.setText("COPIAR P/ DISCORD");
        CopiarDiscordBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CopiarDiscordBtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ResetarBt, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(CopiarDiscordBt, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ProcuradoBt, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(SalvarBt, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SalvarBt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ResetarBt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ProcuradoBt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CopiarDiscordBt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PesquisarPainel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PESQUISAR INDIVÍDUO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        txtID.setBackground(new java.awt.Color(13, 32, 64));
        txtID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtID.setForeground(new java.awt.Color(255, 255, 255));
        txtID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtID.setToolTipText("Passaporte/ID do indivíduo");
        txtID.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "PASSAPORTE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        txtID.setCaretColor(null);
        txtID.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtID.setOpaque(false);
        txtID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIDKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIDKeyTyped(evt);
            }
        });

        PesquisarBt.setBackground(new java.awt.Color(13, 32, 64));
        PesquisarBt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        PesquisarBt.setForeground(new java.awt.Color(255, 255, 255));
        PesquisarBt.setText("PESQUISAR");
        PesquisarBt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        PesquisarBt.setContentAreaFilled(false);
        PesquisarBt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PesquisarBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PesquisarBtActionPerformed(evt);
            }
        });

        txtNome.setBackground(new java.awt.Color(13, 32, 64));
        txtNome.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtNome.setForeground(new java.awt.Color(255, 255, 255));
        txtNome.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNome.setToolTipText("Nome do indivíduo");
        txtNome.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "NOME", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        txtNome.setCaretColor(null);
        txtNome.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtNome.setOpaque(false);
        txtNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomeKeyTyped(evt);
            }
        });

        txtPlaca.setBackground(new java.awt.Color(13, 32, 64));
        txtPlaca.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtPlaca.setForeground(new java.awt.Color(255, 255, 255));
        txtPlaca.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPlaca.setToolTipText("Placa/Registro (RG) do Indivíduo");
        txtPlaca.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "REGISTRO OU PLACA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        txtPlaca.setCaretColor(null);
        txtPlaca.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtPlaca.setOpaque(false);
        txtPlaca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPlacaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPlacaKeyTyped(evt);
            }
        });

        txtTelefone.setBackground(new java.awt.Color(13, 32, 64));
        txtTelefone.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTelefone.setForeground(new java.awt.Color(255, 255, 255));
        txtTelefone.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTelefone.setToolTipText("Telefone/Celular do Indivíduo");
        txtTelefone.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "TELEFONE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        txtTelefone.setCaretColor(null);
        txtTelefone.setDisabledTextColor(new java.awt.Color(255, 255, 255));
        txtTelefone.setOpaque(false);
        txtTelefone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTelefoneKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefoneKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout PesquisarPainelLayout = new javax.swing.GroupLayout(PesquisarPainel);
        PesquisarPainel.setLayout(PesquisarPainelLayout);
        PesquisarPainelLayout.setHorizontalGroup(
            PesquisarPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PesquisarPainelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(PesquisarBt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PesquisarPainelLayout.setVerticalGroup(
            PesquisarPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PesquisarPainelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PesquisarPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PesquisarPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(PesquisarBt, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu3.setText("VOLTAR");

        jMenuItem2.setText("VOLTAR PARA O PAINEL");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        jMenuBar2.add(jMenu3);

        jMenu4.setText("EXIBIR");
        jMenu4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMenu4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jMenuItem3.setText("SOBRE");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem3);

        jMenuBar2.add(jMenu4);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DetalhesPainel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PainelDetalhes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PesquisarPainel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PesquisarPainel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DetalhesPainel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PainelDetalhes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void vermultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vermultaActionPerformed
        AbrirMenuVerPrisoes(1);
    }//GEN-LAST:event_vermultaActionPerformed

    private void verpresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verpresoActionPerformed
        AbrirMenuVerPrisoes(0);
    }//GEN-LAST:event_verpresoActionPerformed

    private void procuradoBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_procuradoBtActionPerformed
        AbrirMenuVerPrisoes(2);
    }//GEN-LAST:event_procuradoBtActionPerformed

    private void ResetarBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetarBtActionPerformed
        ResetarTudo();
    }//GEN-LAST:event_ResetarBtActionPerformed

    private void CopiarDiscordBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CopiarDiscordBtActionPerformed
        CopiarClipboard(FormatarParaDiscord(CrimesDiscordFormat));
        CopiarDiscordBt.setText("COPIADO!!");
    }//GEN-LAST:event_CopiarDiscordBtActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        new Painel().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        InicializadorMain.sobre.setVisible(true);
        /*if(!policia.sobre.isVisible()){
            policia.sobre.setVisible(true);
        }else{
            policia.sobre.requestFocus();
        }*/
        //this.dispose();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void txtIDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            DigitandoCampo(1);
            PegarUsuario();
        }
    }//GEN-LAST:event_txtIDKeyPressed

    private void txtIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDKeyTyped
        DigitandoCampo(1);
        SomenteNumeros(evt);
    }//GEN-LAST:event_txtIDKeyTyped

    private void txtNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            DigitandoCampo(2);
            PegarUsuario();
        }
    }//GEN-LAST:event_txtNomeKeyPressed

    private void txtNomeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyTyped
        DigitandoCampo(2);
    }//GEN-LAST:event_txtNomeKeyTyped

    private void txtPlacaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPlacaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            DigitandoCampo(3);
            PegarUsuario();
        }
    }//GEN-LAST:event_txtPlacaKeyPressed

    private void txtPlacaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPlacaKeyTyped
        DigitandoCampo(3);
    }//GEN-LAST:event_txtPlacaKeyTyped

    private void PesquisarBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PesquisarBtActionPerformed
        PegarUsuario();
    }//GEN-LAST:event_PesquisarBtActionPerformed

    private void txtTelefoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefoneKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            DigitandoCampo(4);
            PegarUsuario();
        }
    }//GEN-LAST:event_txtTelefoneKeyPressed

    private void txtTelefoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefoneKeyTyped
        DigitandoCampo(4);
    }//GEN-LAST:event_txtTelefoneKeyTyped

    private void SalvarBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalvarBtActionPerformed
        ConexaoDB conexao = new ConexaoDB();
        
        JSONObject my_obj = new JSONObject();
        Random rand = new Random(); 
        String Procol = (rand.nextInt(8999)+1000)+""; 
        Calendar cal = Calendar.getInstance(); 
        cal.getTime(); 
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        //System.out.println(" / "+sdf.format(cal.getTime()));
        Procol=sdf.format(cal.getTime())+Procol;
        
        Protocolor=Procol;
        
        info_ComandoS.setText("Nada para digitar");
        if(UsuarioPegar.has("id_usuario")){
            int pass = UsuarioPegar.getInt("id_usuario");
            my_obj.put("passaporte", pass+"");

            JSONObject user_local = Usuario.UsuarioMain;
            my_obj.put("id_prendeu", user_local.getString("id_usuario"));

            String temp_contravencoes = "Prisão ("+Procol+"): "+info_CrimesS.getText();
            // VERIFICAR SE O JOGADOR ESTA PROCURADO
            boolean player_procurado = false;
            int proc_meses = 0;
            int proc_multas = 0;
            String temp_CrimesDBFormat = "";
            String temp_CrimesDiscordFormat = "";
            String temp_contravencoes_proc = "";
            for(int i3 = 0; i3 < procuradosDBarray.length(); i3++){
                JSONObject oproc = procuradosDBarray.getJSONObject(i3);
                int meseer = oproc.getInt("meses");
                int multaar = oproc.getInt("multas");
                int pagou = oproc.getInt("pagou");
                if(pass==oproc.getInt("id_usuario")){
                    if(pagou <= 0){
                        if(meseer > 0 || multaar > 0 ){
                            proc_meses+=meseer;
                            proc_multas+=multaar;
                            player_procurado = true;
                            temp_CrimesDBFormat+=" ////// "+oproc.getString("justificado");
                            if(!"".equals(temp_CrimesDiscordFormat))temp_CrimesDiscordFormat+="\n\n";
                            temp_CrimesDiscordFormat+=DesformatarDiscord(oproc.getString("justificado_discord"));
                            temp_contravencoes_proc+=" / "+oproc.getString("contravencoes");
                        }
                    }
                }
            }
            String CrimesFinal = "";
            if(proc_meses > 0) CrimesFinal+="Meses: "+proc_meses;
            if(proc_multas > 0){
                if(!"".equals(CrimesFinal)) CrimesFinal+=" e ";
                CrimesFinal+="Multas: $"+proc_multas;
            }
            boolean AddProcurado = false;
            if(player_procurado){
                Object[] options = { "Adicionar", "Cancelar" }; 
                int Escolha=JOptionPane.showOptionDialog(this,
                        "Este indivíduo está procurado por alguns crimes.\n"+CrimesFinal+"\nDeseja adicionar na pena total?", 
                        "Indivíduo procurado", 
                        JOptionPane.DEFAULT_OPTION, 
                        JOptionPane.WARNING_MESSAGE, 
                        null, 
                        options, 
                        options[0]);
                if(Escolha==JOptionPane.YES_OPTION){
                    AddProcurado = true;
                    if(proc_meses > 0) s_Meses+=proc_meses;
                    if(proc_multas > 0) s_Multa+=proc_multas;
                    CrimesDiscordFormat+="\n\n\n"+temp_CrimesDiscordFormat;
                    CrimesDBFormat+=" ////// "+temp_CrimesDBFormat;
                    temp_contravencoes+=temp_contravencoes_proc;
                }  
            }
            //========================================
        
            my_obj.put("protocolo", Procol);
            my_obj.put("meses", s_Meses+"");
            my_obj.put("multas", s_Multa+"");
            
            my_obj.put("contravencoes", temp_contravencoes);

            Calendar cal2 = Calendar.getInstance(); 
            cal2.getTime(); 
            //SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy HH:mm");

            //String ProcStr = "Relatório de Prisão "+sdf2.format(cal2.getTime());
            my_obj.put("justificado", CrimesDBFormat);
            my_obj.put("justificado_discord", FormatarParaDiscord(CrimesDiscordFormat));
            SalvarBt.setEnabled(false);
            ProcuradoBt.setEnabled(false);
            
            String StrMeses = "N/A";
            String StrMultas = "N/A";
            if(s_Multa>0) StrMultas = "$"+String.format("%,d", s_Multa);
            if(s_Meses>0) StrMeses = s_Meses+" meses";
            info_Pena1S.setText(StrMultas);
            info_Pena2S.setText(StrMeses);
            info_CrimesS.setText(temp_contravencoes);
            info_ComandoS.setText("/protocolo "+Procol);
            if(conexao.SalvarPrisao(my_obj)){
                String nome = UsuarioPegar.getString("nome")+" "+UsuarioPegar.getString("sobrenome");
                if(UsuarioPegar.has("nome_completo")) nome = UsuarioPegar.getString("nome_completo");
                if(" ".equals(nome))nome="Sem Registro";
                
                if(AddProcurado){
                    if(conexao.AttProcurados(pass, Procol)){
                        System.out.println("Procurado do ID "+pass+" setado no protocolo "+Procol);
                    }  
                }
                
                showMessageDialog(null,
                    nome+" foi registrado como detento no banco da "+InicializadorMain.info_cidade.getString("nome_policia").toUpperCase()+" com o protocolo: "+Procol+" !"+
                    "\nFoi copiado o comando para ser digitado dentro do jogo (pressione T e depois pressione CTRL+V).",
                    "Salvo com sucesso!!",
                    JOptionPane.PLAIN_MESSAGE);
                CopiarClipboard("/protocolo "+Procol);
                
                InicializadorMain.AttDbsStatic();
            }
        }
    }//GEN-LAST:event_SalvarBtActionPerformed

    private void ProcuradoBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProcuradoBtActionPerformed
        ConexaoDB conexao = new ConexaoDB();
        
        JSONObject my_obj = new JSONObject();
        Random rand = new Random(); 
        String Procol = (rand.nextInt(8999)+1000)+""; 
        Calendar cal = Calendar.getInstance(); 
        cal.getTime(); 
        //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        //System.out.println(" / "+sdf.format(cal.getTime()));
        Procol=sdf.format(cal.getTime())+Procol;
        
        Protocolor=Procol;
        
        info_ComandoS.setText("Nada para digitar");
        if(UsuarioPegar.has("id_usuario")){
            int pass = UsuarioPegar.getInt("id_usuario");
            my_obj.put("passaporte", pass+"");

            JSONObject user_local = Usuario.UsuarioMain;
            my_obj.put("id_prendeu", user_local.getString("id_usuario"));

            my_obj.put("protocolo", Procol);
            my_obj.put("meses", s_Meses+"");
            my_obj.put("multas", s_Multa+"");


            my_obj.put("contravencoes", "Procurado ("+Procol+"): "+info_CrimesS.getText());

            Calendar cal2 = Calendar.getInstance(); 
            cal2.getTime(); 
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy HH:mm");

            String ProcStr = "## Relatório de Procurado "+sdf2.format(cal2.getTime());
            my_obj.put("justificado", ProcStr+" //// "+CrimesDBFormat);

            my_obj.put("justificado_discord", FormatarParaDiscord(ProcStr+"\n\n"+CrimesDiscordFormat));

            String obs = JOptionPane.showInputDialog(this, "Alguma observação à acrescentar? Qualquer coisa que seja útil.\nVocê pode descrever características ou o modelo do veículo do indivíduo.", "Observações sobre o procurado", JOptionPane.PLAIN_MESSAGE);
            my_obj.put("observacoes", obs);

            ImageIcon iconer = new ImageIcon("src/police/imagens/gunimg.png");
            Object[] possibilidades = {"Sem Informação", "Desarmado", "Portando Arma Branca", "Portando Arma Leve", "Portando Arma Pesada"};
            String NivPro = (String)JOptionPane.showInputDialog(this,
                "O indivíduo portava algum tipo de arma?\n"
                + "Nos ajude a definir o risco deste indivíduo.",
                "Nível de Caráter Perigoso do Procurado",
                JOptionPane.QUESTION_MESSAGE, 
                iconer,
                possibilidades,
                "Sem Informação");
            int NivelProc = 0;
            if(NivPro.contains("Desarmado")){
                NivelProc = 1;
            }else if(NivPro.contains("Portando Arma Branca")){
                NivelProc = 2;
            }else if(NivPro.contains("Portando Arma Leve")){
                NivelProc = 3;
            }else if(NivPro.contains("Portando Arma Pesada")){
                NivelProc = 4;
            }
            my_obj.put("nivel_procurado", NivelProc+"");
            //System.out.println("NivPro: "+NivPro+" / NivelProc: "+NivelProc);

            if(conexao.SalvarProcurado(my_obj)){
                String nome = UsuarioPegar.getString("nome")+" "+UsuarioPegar.getString("sobrenome");
                if(UsuarioPegar.has("nome_completo")) nome = UsuarioPegar.getString("nome_completo");
                if(" ".equals(nome))nome="Sem Registro";
                
                showMessageDialog(null,
                    nome+" foi registrado como procurado no banco da "+InicializadorMain.info_cidade.getString("nome_policia").toUpperCase()+" com o protocolo: "+Procol+" !",
                    "Salvo com sucesso!!",
                    JOptionPane.PLAIN_MESSAGE);
                InicializadorMain.AttDbsStatic();
            }
        }
    }//GEN-LAST:event_ProcuradoBtActionPerformed

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
            java.util.logging.Logger.getLogger(Prisoes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Prisoes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Prisoes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Prisoes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Prisoes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CopiarDiscordBt;
    private javax.swing.JPanel DetalhesPainel;
    private javax.swing.JPanel PainelDetalhes;
    private javax.swing.JButton PesquisarBt;
    private javax.swing.JPanel PesquisarPainel;
    private javax.swing.JButton ProcuradoBt;
    private javax.swing.JButton ResetarBt;
    private javax.swing.JButton SalvarBt;
    private javax.swing.JLabel TimeAgora;
    private javax.swing.JLabel des_Idade;
    private javax.swing.JLabel des_IdadeS;
    private javax.swing.JLabel des_Info;
    private javax.swing.JLabel des_Multa;
    private javax.swing.JLabel des_MultaS;
    private javax.swing.JLabel des_Nome;
    private javax.swing.JLabel des_NomeS;
    private javax.swing.JLabel des_Passagem;
    private javax.swing.JLabel des_PassagemS;
    private javax.swing.JLabel des_Procurado;
    private javax.swing.JLabel des_ProcuradoS;
    private javax.swing.JLabel des_Registro;
    private javax.swing.JLabel des_RegistroS;
    private javax.swing.JLabel des_Reu;
    private javax.swing.JLabel des_ReuS;
    private javax.swing.JLabel info_Comando;
    private javax.swing.JTextField info_ComandoS;
    private javax.swing.JLabel info_Crimes;
    private javax.swing.JTextField info_CrimesS;
    private javax.swing.JLabel info_Pena1;
    private javax.swing.JLabel info_Pena1S;
    private javax.swing.JLabel info_Pena2;
    private javax.swing.JLabel info_Pena2S;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton procuradoBt;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPlaca;
    private javax.swing.JTextField txtTelefone;
    private javax.swing.JButton vermulta;
    private javax.swing.JButton verpreso;
    // End of variables declaration//GEN-END:variables
}
