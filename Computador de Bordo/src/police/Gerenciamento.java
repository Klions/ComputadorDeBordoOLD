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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import police.configs.ConexaoDB;
import police.configs.ExportarOuImportar;
import police.configs.GetImages;
import police.configs.SNWindows;
import police.configs.Usuario;

/**
 *
 * @author John
 */
public class Gerenciamento extends javax.swing.JFrame {

    /**
     * Creates new form Prisoes
     */
    static JSONArray CrimesRegistro = new JSONArray();
    static JSONArray CategoriasCrimes = new JSONArray();
    
    static JSONArray RegistroTabelas = new JSONArray();
    
    
    static JSONArray novo_CrimesRegistro = new JSONArray();
    static JSONArray novo_CategoriasCrimes = new JSONArray();
    
    static JSONArray GetCrimes = new JSONArray();
    static int contageGetCrimes = 0;
    
    static int TotalIdsTab = 0;
    
    static int SalvarTime = 0;
    static String CrimesStore="";
    static String CategoriasStore="";
    
    static JSONObject ConfigGerais = new JSONObject();
    static JSONArray ReducaoRegistro = new JSONArray();
    static String CrimesStoreOpcoes="";
    static String OpcoesStore="";
    
    ExportarImportar Export = new ExportarImportar();
    
    
    
    public static int tCrimes = 0;
    public static int tCategorias = 0;
    
    private static Set<Integer> pressedKeys = new HashSet<>();
    
    static int Nivel_Ass = 0;
    /*JSONArray CategoriasCrimes = new JSONArray();
    JSONArray CrimesRegistro = new JSONArray();*/
    static JFrame EsteFrame = new JFrame();
    
    static boolean SalvarSairBool = false;
    public Gerenciamento() {
        initComponents();
        SalvarTime=10;
        EsteFrame = this;
        //Timere.cancel();
        
        if(InicializadorMain.ModoOffline){
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagens/CB2.png")));
        }else{
            this.setIconImage(new ImageIcon(GetImages.LogoCB).getImage());
        }
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        TabelaOpcoes.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        TabelaOpcoes.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        TabelaOpcoes.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        TabelaOpcoes.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );

        ((DefaultTableCellRenderer)TabelaOpcoes.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        TabelaOpcoes.getTableHeader().setReorderingAllowed(false);

        TabelaOpcoes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TabelaOpcoes.setShowGrid(true);
        TabelaOpcoes.setUpdateSelectionOnSort(false);
        
        if(InicializadorMain.ModoOffline){
            UPDATE();
            UPDATEOpcoes();
        }
        getContentPane().setBackground(new java.awt.Color(13, 32, 64));
        this.setLocationRelativeTo(null);
        PainelDetalhes.setBackground(new java.awt.Color(13, 32, 64));
        Export.setLocationRelativeTo(null);
        PegarDB();
        
        Timer timer = new Timer(); 
        TimerTask tt = new TimerTask() { 
  
            public void run() 
            { 
                if(SalvarTime > 0){
                    SalvarDados.setEnabled(false);
                    ResetarTudo.setEnabled(false);
                    
                    SalvarDados.setText("SALVAR ("+SalvarTime+")");
                    ResetarTudo.setText("RESETAR ("+SalvarTime+")");
                    SalvarTime--;
                }else{
                    SalvarDados.setEnabled(true);
                    ResetarTudo.setEnabled(true);
                    SalvarDados.setText("SALVAR");
                    ResetarTudo.setText("RESETAR");
                }
                if(!EsteFrame.isVisible()){
                    timer.cancel();
                }
            }; 
        }; 
        timer.schedule(tt, 1000, 1000); 
    }
    
    
    public static void PegarDB(){
        if(InicializadorMain.ModoOffline){
            CategoriasCrimes = new JSONArray();
            if(!"".equals(CategoriasStore) && CategoriasStore.length() > 10){
                CategoriasCrimes = new JSONArray(CategoriasStore);
            }else{
                JSONObject getTemporario2 = new JSONObject();
                getTemporario2.put("id", 1);
                getTemporario2.put("nome_categoria", "Categoria Exemplo");
                
                CategoriasCrimes.put(getTemporario2);
            }
            
            CrimesRegistro = new JSONArray();
            if(!"".equals(CrimesStore) && CrimesStore.length() > 10){
                CrimesRegistro = new JSONArray(CrimesStore);
            }else{
                JSONObject getTemporario2 = new JSONObject();
                getTemporario2.put("texto", "Crime Exemplo");
                getTemporario2.put("multa", 1000);
                getTemporario2.put("meses", 50);
                getTemporario2.put("tipo", "UNICO");
                getTemporario2.put("categoria", 1);
                
                CrimesRegistro.put(getTemporario2);
            }
            novo_CategoriasCrimes = CategoriasCrimes;
            novo_CrimesRegistro = CrimesRegistro;
        }else{
            Usuario usuarios = new Usuario();
            GetCrimes = usuarios.CrimesServerID();
            for(int i2 = 0; i2 < GetCrimes.length(); i2++){
                JSONObject o2 = GetCrimes.getJSONObject(i2);
                CategoriasCrimes = new JSONArray(o2.getString("categorias"));
                CrimesRegistro = new JSONArray(o2.getString("crimes"));
                contageGetCrimes = o2.getInt("id");
            }
            novo_CategoriasCrimes = CategoriasCrimes;
            novo_CrimesRegistro = CrimesRegistro;
        }
        PegarDBOpcoes();
        SalvarTime=10;
        AtualizarJanelas();
        PegarValoresTabela();
    }
    
    public static void PegarDBOpcoes(){
        if(InicializadorMain.ModoOffline){
            ReducaoRegistro = new JSONArray();
            if(!"".equals(CrimesStoreOpcoes) && CrimesStoreOpcoes.length() > 10){
                ReducaoRegistro = new JSONArray(CrimesStoreOpcoes);
            }else{
                JSONObject getTemporario2 = new JSONObject();
                getTemporario2.put("texto", "Reu Primario");
                getTemporario2.put("meses", 25);
                getTemporario2.put("calculo", "PORCENTAGEM");
                getTemporario2.put("tipo", "REDUCAO");
                ReducaoRegistro.put(getTemporario2);
                
                getTemporario2 = new JSONObject();
                getTemporario2.put("texto", "Reu Reincidente");
                getTemporario2.put("meses", 30);
                getTemporario2.put("calculo", "PORCENTAGEM");
                getTemporario2.put("tipo", "AUMENTO");
                ReducaoRegistro.put(getTemporario2);
            }
            
            ConfigGerais = new JSONObject();
            if(!"".equals(OpcoesStore) && OpcoesStore.length() > 10){
                ConfigGerais = new JSONObject(OpcoesStore);
            }else{
                ConfigGerais.put("pena_max", 0);
            }
        }else{
            /*
            Usuario usuarios = new Usuario();
            GetCrimes = usuarios.CrimesServerID();
            for(int i2 = 0; i2 < GetCrimes.length(); i2++){
                JSONObject o2 = GetCrimes.getJSONObject(i2);
                ReducaoRegistro = new JSONArray(o2.getString("crimes"));
                contageGetCrimes = o2.getInt("id");
            }
            OpcoesCrimes = CategoriasCrimes;*/
        }
        AdicionarBotoesOpcoes();
        PegarValoresTabelaOpcoes();
        RecarregarValoresTabelaOpcoes();
    }
    
    
    
    final static int QntCategorias = 50;
    final static int QntCrimes = 300;
    
    static JScrollPane[] ScrollPainel = new JScrollPane[QntCategorias];
    static JPanel[] PainelBase = new JPanel[QntCategorias];
    static JPanel[][] Painel = new JPanel[QntCategorias][QntCrimes];
    //JLabel[] Textos = new JLabel[10];
    public static JTable[] Tabelas = new JTable[QntCategorias];
    static JButton[] BotoesAdd = new JButton[QntCategorias];
    static JButton[] BotoesAdd2 = new JButton[QntCategorias];
    static JButton[] BotoesRem = new JButton[QntCategorias];
    
    static JTextField[] RenameTabc = new JTextField[QntCategorias];
    static JButton[] RenameTabBtc = new JButton[QntCategorias];
    
    static JButton[] TipoEscolha = new JButton[QntCategorias];
    
    //JComboBox[] TipoEscolha = new JComboBox[10];
    
    public static void AdicionarBotoes(){
        Nivel_Ass = SNWindows.getNivelSerialPC();
        final int nivel_ass = Nivel_Ass;
        RegistroTabelas = new JSONArray();
        System.out.println("AdicionarBotoes()");
        boolean Bloqueado = false;
        if(InicializadorMain.ModoOffline){
            if(novo_CategoriasCrimes.length() > SNWindows.CategAssinatura[Nivel_Ass][0] || novo_CrimesRegistro.length() > SNWindows.CategAssinatura[Nivel_Ass][1]){
                Bloqueado = true;
                //TxtCategoria.setEnabled(false);
                AddCategoria.setEnabled(false);
                showMessageDialog(null,"A tabela importada é de uma assinatura. Você não conseguirá edita-la, mas funcionará normalmente."
                        + "\nVocê pode adquirir uma assinatura em nosso discord. (Exibir -> Sobre)", "Tabela importada de uma assinatura",JOptionPane.PLAIN_MESSAGE);
            }
        }
        
        for(int i2 = 0; i2 < novo_CategoriasCrimes.length(); i2++){
            JSONObject o2 = novo_CategoriasCrimes.getJSONObject(i2);
            
            PainelBase[i2] = new JPanel();
            ScrollPainel[i2] = new JScrollPane();
            Tabelas[i2] = new JTable();
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
            
            PainelBase[i2].setBackground(new java.awt.Color(13, 32, 64));
            
            jTabbedPane1.addTab(o2.getString("nome_categoria"), PainelBase[i2]);
            JSONObject PegarDadosBt = o2;
            PegarDadosBt.put("i", i2);
            RegistroTabelas.put(PegarDadosBt);
            
            Container container = PainelBase[i2];
            container.setLayout(null);
            
            Tabelas[i2].setModel(new javax.swing.table.DefaultTableModel(
                null,
                new String [] {
                    "NOME DO CRIME", "MULTA", "MESES", "TIPO", "OBSERVAÇÃO"
                }
            ) {
                Class[] types = new Class [] {
                    java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
                };
                boolean[] canEdit = new boolean [] {
                    true, true, true, false, true
                };

                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });
            Tabelas[i2].putClientProperty("terminateEditOnFocusLost", true);
            
            DefaultTableModel modelTable = (DefaultTableModel)Tabelas[i2].getModel();
            for(int i = 0; i < novo_CrimesRegistro.length(); i++){
                JSONObject o = novo_CrimesRegistro.getJSONObject(i);
                if(o.getInt("categoria") == o2.getInt("id")){
                    String Obs = "";
                    if(o.has("obs")) Obs = o.getString("obs");
                    modelTable.addRow(new Object[]{o.getString("texto"), o.getInt("multa"), o.getInt("meses"), o.getString("tipo"), Obs});
                }
            }
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment( JLabel.CENTER );
            Tabelas[i2].getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
            Tabelas[i2].getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
            Tabelas[i2].getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
            Tabelas[i2].getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
            Tabelas[i2].getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
            
            Tabelas[i2].getColumnModel().getColumn(1).setPreferredWidth(2);
            Tabelas[i2].getColumnModel().getColumn(2).setPreferredWidth(2);
            Tabelas[i2].getColumnModel().getColumn(3).setPreferredWidth(2);
            
            ((DefaultTableCellRenderer)Tabelas[i2].getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
            
            Tabelas[i2].getTableHeader().setReorderingAllowed(false);

            Tabelas[i2].setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            Tabelas[i2].setShowGrid(true);
            Tabelas[i2].setUpdateSelectionOnSort(false);
            final int As = i2;
            DefaultTableModel model = (DefaultTableModel)Tabelas[As].getModel();
            
            Tabelas[i2].addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    //TableCellEditor tce = Tabelas[As].getCellEditor();
                    //if(tce == null) PegarValoresTabela();
                        //tce.stopCellEditing();
                }
            });
            
            TipoEscolha[i2] = new JButton();
            TipoEscolha[i2].setBackground(new java.awt.Color(255, 255, 255));
            TipoEscolha[i2].setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
            TipoEscolha[i2].setText("MUDAR TIPO");
            TipoEscolha[i2].setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            
            TipoEscolha[i2].addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    int index = Tabelas[As].getSelectedRow();
                    if(index >= 0){
                        String ValorAtualTabela = Tabelas[As].getValueAt(index, 3)+"";
                        if(null != ValorAtualTabela)switch (ValorAtualTabela) {
                            case "UNICO":
                                ValorAtualTabela = "MULTIPLO";
                                break;
                            case "MULTIPLO":
                                ValorAtualTabela = "UNICO";
                                break;
                            default:
                                ValorAtualTabela = "UNICO";
                                break;
                        }
                        Tabelas[As].setValueAt(ValorAtualTabela, index, 3);
                    }
                }
            });
            /*
            TipoEscolha[i2] = new JComboBox();
            TipoEscolha[i2].setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UNICO", "QUANTIDADE" }));
            
            TipoEscolha[i2].addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    //TipoEscolha[As].getSelectedIndex();
                    int index = Tabelas[As].getSelectedRow();
                    if(index >= 0){
                        String ValorAtualTabela = Tabelas[As].getValueAt(index, 3)+"";
                        if(TipoEscolha[As].getSelectedItem() != ValorAtualTabela){
                            //Tabelas[As].setValueAt(TipoEscolha[As].getSelectedItem(), index, 3);
                            System.out.println("addActionListener");
                        }
                    }
                    
                    
                }
            });*/
            
            Tabelas[i2].addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    int index = Tabelas[As].getSelectedRow();
                    
                    if(index >= 0){
                        //TipoEscolha[As].setSelectedItem(Tabelas[As].getValueAt(index, 3));
                        //System.out.println("index: "+index+" / Tabelas[As].getValueAt(index, 3): "+Tabelas[As].getValueAt(index, 3));
                    }
                    pressedKeys.add(evt.getKeyCode());
                    if (!pressedKeys.isEmpty()) {
                        if (pressedKeys.contains(KeyEvent.VK_SHIFT)) {
                            int row = Tabelas[As].getSelectedRow();
                            if(row >= 0){
                                String NomeRow = Tabelas[As].getValueAt(row, 0)+"";
                                if("".equals(NomeRow)) NomeRow = "Linha "+(row+1);
                                if (pressedKeys.contains(KeyEvent.VK_UP)) {
                                    //System.out.println("SHIFT + UP");
                                    if(index > 0){
                                        InfoDB1.setText(NomeRow+" movido para cima");
                                        model.moveRow(index, index, index - 1);
                                        PegarValoresTabela();
                                    }
                                    //jTable1.setRowSelectionInterval(index - 1, index - 1);
                                }
                                if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
                                    //System.out.println("SHIFT + DOWN");
                                    if(index < model.getRowCount() - 1){
                                        InfoDB1.setText(NomeRow+" movido para baixo");
                                        model.moveRow(index, index, index + 1);
                                        PegarValoresTabela();
                                    }
                                }
                                if (pressedKeys.contains(KeyEvent.VK_DELETE)) {
                                    InfoDB1.setText(NomeRow+" foi removido");
                                    model.removeRow( row );
                                    if(model.getRowCount() > 0){
                                        if(index < model.getRowCount() - 1 || index == model.getRowCount() - 1){
                                            Tabelas[As].setRowSelectionInterval(index, index);
                                        }else if(index == 0){
                                            Tabelas[As].setRowSelectionInterval(index+1, index+1);
                                        }else{
                                            Tabelas[As].setRowSelectionInterval(index-1, index-1);
                                        }
                                    }
                                    PegarTotalTabela();
                                }
                                SalvarSairBool=true;
                            }
                            if (pressedKeys.contains(KeyEvent.VK_INSERT)) {
                                //System.out.println("Tabelas[As].getRowCount(): "+Tabelas[As].getRowCount());
                                
                                //SNWindows.CorAssinatura[nivel_ass][0]
                                if(tCrimes < SNWindows.CategAssinatura[nivel_ass][1]){
                                    InfoDB1.setText("Nova linha foi adicionada");
                                    model.addRow(new Object[]{"", 0, 0,"UNICO"});
                                    Tabelas[As].setRowSelectionInterval(model.getRowCount()-1, model.getRowCount()-1);
                                    PegarTotalTabela();
                                    SalvarSairBool=true;
                                }else{
                                    pressedKeys = new HashSet<>();
                                    showMessageDialog(null,"Lamento, mas sua assinatura '"+SNWindows.TipoAssinatura[nivel_ass]+"' permite adicionar até "+SNWindows.CategAssinatura[nivel_ass][1]+" crimes."
                                            + "\nVocê pode adquirir uma assinatura em nosso discord. (Exibir -> Sobre)", "Erro ao inserir novo crime",JOptionPane.PLAIN_MESSAGE);
                                }
                            }
                        }
                    }
                }
                public void keyReleased(java.awt.event.KeyEvent evt) {
                    pressedKeys.remove(evt.getKeyCode());
                }
            });
            
            Tabelas[i2].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int index = Tabelas[As].getSelectedRow();
                    
                    if(index >= 0){
                        //TipoEscolha[As].setSelectedItem(Tabelas[As].getValueAt(index, 3));
                        //System.out.println("index: "+index+" / Tabelas[As].getValueAt(index, 3): "+Tabelas[As].getValueAt(index, 3));
                    }
                }
            });
            
            ScrollPainel[i2].setViewportView(Tabelas[i2]);
            
            BotoesAdd[i2] = new JButton("ADICIONAR CRIME");
            BotoesAdd2[i2] = new JButton("REMOVER CRIME");
            BotoesRem[i2] = new JButton("REMOVER CATEGORIA");
            
            BotoesAdd[i2].setBackground(new java.awt.Color(255, 255, 255));
            BotoesAdd2[i2].setBackground(new java.awt.Color(255, 255, 255));
            BotoesRem[i2].setBackground(new java.awt.Color(255, 255, 255));
            
            BotoesAdd[i2].setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
            BotoesAdd2[i2].setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
            BotoesRem[i2].setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
            
            BotoesAdd[i2].setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            BotoesAdd2[i2].setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            BotoesRem[i2].setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

            BotoesAdd[i2].addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    //Tabelas[As].getRowCount()
                    if(tCrimes < SNWindows.CategAssinatura[nivel_ass][1]){
                        InfoDB1.setText("Nova linha foi adicionada");
                        model.addRow(new Object[]{"", 0, 0,"UNICO"});
                        Tabelas[As].setRowSelectionInterval(model.getRowCount()-1, model.getRowCount()-1);
                        PegarTotalTabela();
                        SalvarSairBool=true;
                    }else{
                        pressedKeys = new HashSet<>();
                        showMessageDialog(null,"Lamento, mas sua assinatura '"+SNWindows.TipoAssinatura[nivel_ass]+"' permite adicionar até "+SNWindows.CategAssinatura[nivel_ass][1]+" crimes."
                                + "\nVocê pode adquirir uma assinatura em nosso discord. (Exibir -> Sobre)", "Erro ao inserir novo crime",JOptionPane.PLAIN_MESSAGE);
                    }
                }
            });
            
            BotoesAdd2[i2].addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    int row = Tabelas[As].getSelectedRow();
                    if(row >= 0){
                        String NomeRow = Tabelas[As].getValueAt(row, 0)+"";
                        InfoDB1.setText(NomeRow+" foi removido");
                        model.removeRow( row );
                        if(model.getRowCount() > 0){
                            if(row < model.getRowCount() - 1 || row == model.getRowCount() - 1){
                                Tabelas[As].setRowSelectionInterval(row, row);
                            }else if(row == 0){
                                Tabelas[As].setRowSelectionInterval(row+1, row+1);
                            }else{
                                Tabelas[As].setRowSelectionInterval(row-1, row-1);
                            }
                        }
                        PegarTotalTabela();
                    }
                }
            });
            
            
            
            BotoesRem[i2].addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    String NomeCategoria = o2.getString("nome_categoria");
                    Object[] options = { "Sim, excluir", "Não, cancelar" }; 
                    int Escolha=JOptionPane.showOptionDialog(null,
                    "Tem certeza que deseja excluir a categoria "+NomeCategoria+"?", // \n
                    "Excluir Categoria", 
                    JOptionPane.DEFAULT_OPTION, 
                    JOptionPane.WARNING_MESSAGE, 
                    null, 
                    options, 
                    options[0]);
                    if(Escolha==JOptionPane.YES_OPTION){
                        //jTabbedPane1.remove(2);
                        InfoDB1.setText("Categoria "+NomeCategoria+" removida");
                        novo_CategoriasCrimes.remove(As);
                        
                        if(novo_CategoriasCrimes.length() <= 0){
                            novo_CategoriasCrimes = new JSONArray();
                            JSONObject getTemporario2 = new JSONObject();
                            getTemporario2.put("id", 1);
                            getTemporario2.put("nome_categoria", "Categoria Exemplo");
                            novo_CategoriasCrimes.put(getTemporario2);
                            
                            CrimesRegistro = new JSONArray();
                            novo_CrimesRegistro = new JSONArray();
                            getTemporario2 = new JSONObject();
                            getTemporario2.put("texto", "Crime Exemplo");
                            getTemporario2.put("multa", 1000);
                            getTemporario2.put("meses", 50);
                            getTemporario2.put("tipo", "UNICO");
                            getTemporario2.put("categoria", 1);

                            novo_CrimesRegistro.put(getTemporario2);
                            
                            showMessageDialog(null,"Foi adicionada uma categoria padrão, pois você removeu a última restante","Tabela retornou ao padrão",JOptionPane.PLAIN_MESSAGE);
                        }else{
                            PegarValoresTabela();
                        }
                        AtualizarJanelas();
                        PegarTotalTabela();
                    }
                }
            });
            
            RenameTabc[i2] = new JTextField(o2.getString("nome_categoria"));
            RenameTabBtc[i2] = new JButton("ADICIONAR CRIME");
            
            RenameTabBtc[i2].setBackground(new java.awt.Color(255, 255, 255));
            RenameTabBtc[i2].setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
            RenameTabBtc[i2].setText("RENOMEAR CATEGORIA");
            RenameTabBtc[i2].setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            
            RenameTabBtc[i2].addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    String NCategoria = RenameTabc[As].getText();
                    if(jTabbedPane1.getTabCount() < SNWindows.CategAssinatura[Nivel_Ass][0]){
                        String NomeCategoria = (String)JOptionPane.showInputDialog(EsteFrame, "Escolha o nome para a categoria. Entre 3 e 30 caracteres.",
                            "Renomear Categoria", JOptionPane.QUESTION_MESSAGE,null,null,NCategoria);
                        //String NomeCategoria = JOptionPane.showInputDialog(EsteFrame, "Escolha o nome para a categoria. Entre 3 e 30 caracteres.", "Nome da Categoria", JOptionPane.PLAIN_MESSAGE);
                        if(NomeCategoria != null && !"".equals(NomeCategoria) && NomeCategoria.length() >= 3 && NomeCategoria.length() <= 30){
                            String CatNome = "Desconhecido";
                            jTabbedPane1.setTitleAt(As, NomeCategoria);
                            JSONArray ArrayDescartavel = new JSONArray();
                            for(int i3 = 0; i3 < novo_CategoriasCrimes.length(); i3++){
                                JSONObject Obj = novo_CategoriasCrimes.getJSONObject(i3);

                                JSONObject getTemporario2 = new JSONObject();
                                if(i3 == As){
                                    CatNome = Obj.getString("nome_categoria");
                                    getTemporario2.put("id", Obj.getInt("id"));
                                    getTemporario2.put("nome_categoria", NomeCategoria);
                                }else{
                                    getTemporario2 = Obj;
                                }
                                ArrayDescartavel.put(getTemporario2);
                            }
                            novo_CategoriasCrimes = ArrayDescartavel;
                            InfoDB1.setText("Categoria "+CatNome+" renomeada para "+NomeCategoria);
                            SalvarSairBool=true;
                        }else{
                            showMessageDialog(null,"O nome da categoria deve estar entre 3 e 30 caracteres.", "Erro ao renomear categoria",JOptionPane.PLAIN_MESSAGE);
                        }
                    }else{
                        showMessageDialog(null,"Lamento, mas sua assinatura '"+SNWindows.TipoAssinatura[Nivel_Ass]+"' permite adicionar até "+SNWindows.CategAssinatura[Nivel_Ass][0]+" categorias."
                                + "\nVocê pode adquirir uma assinatura em nosso discord. (Exibir -> Sobre)", "Erro ao inserir nova categoria",JOptionPane.PLAIN_MESSAGE);
                    }
                    
                    /*
                    if(NCategoria.length() > 3 && NCategoria.length() < 20){
                        String CatNome = "Desconhecido";
                        jTabbedPane1.setTitleAt(As, NCategoria);
                        JSONArray ArrayDescartavel = new JSONArray();
                        for(int i3 = 0; i3 < novo_CategoriasCrimes.length(); i3++){
                            JSONObject Obj = novo_CategoriasCrimes.getJSONObject(i3);
                            
                            JSONObject getTemporario2 = new JSONObject();
                            if(i3 == As){
                                CatNome = Obj.getString("nome_categoria");
                                getTemporario2.put("id", Obj.getInt("id"));
                                getTemporario2.put("nome_categoria", NCategoria);
                            }else{
                                getTemporario2 = Obj;
                            }
                            ArrayDescartavel.put(getTemporario2);
                        }
                        novo_CategoriasCrimes = ArrayDescartavel;
                        InfoDB1.setText("Categoria "+CatNome+" renomeada para "+NCategoria);
                    }*/
                }
            });
        
            int index3 = Tabelas[i2].getSelectedRow();
            if(model.getRowCount() > 0){
                if(index3 < 0) Tabelas[i2].setRowSelectionInterval(0, 0);
            }else{
                if(tCrimes < SNWindows.CategAssinatura[nivel_ass][1]){
                    model.addRow(new Object[]{"", 0, 0, "UNICO"});
                    Tabelas[i2].setRowSelectionInterval(0, 0);
                }
            }
            //Tabelas[i2].requestFocus();
            //Tabelas[i2].changeSelection(0, 0, false, false);
            //Tabelas[i2].editCellAt(0,0);
            
            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(PainelBase[i2]);
            PainelBase[i2].setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(ScrollPainel[i2], javax.swing.GroupLayout.DEFAULT_SIZE, 749, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        
                        
                            .addComponent(BotoesRem[i2])
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                
                            .addComponent(RenameTabBtc[i2])
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TipoEscolha[i2], javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(BotoesAdd[i2], javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(BotoesAdd2[i2], javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(ScrollPainel[i2], javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BotoesAdd[i2], javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BotoesAdd2[i2], javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BotoesRem[i2], javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            
                        .addComponent(RenameTabBtc[i2], javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TipoEscolha[i2], javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            
        
            if(Bloqueado){
                Tabelas[i2].setEnabled(false);
                RenameTabBtc[i2].setEnabled(false);
                RenameTabc[i2].setEnabled(false);
                TipoEscolha[i2].setEnabled(false);
                BotoesRem[i2].setEnabled(false);
                BotoesAdd[i2].setEnabled(false);
            }
            if(TotalIdsTab < o2.getInt("id")){
                TotalIdsTab = o2.getInt("id");
            }
        }
    }
    
    public static void AtualizarJanelas(){
        PegarTotalTabela();
        int AtualTab = jTabbedPane1.getSelectedIndex();
        while (jTabbedPane1.getTabCount() > 0)
            jTabbedPane1.remove(0);
        AdicionarBotoes();
        if(AtualTab > 0 && AtualTab < jTabbedPane1.getTabCount()) jTabbedPane1.setSelectedIndex(AtualTab);
    }
    
    public static void PegarValoresTabela(){
        novo_CrimesRegistro = new JSONArray();
        int semnome = 0;
        for(int i = 0; i < RegistroTabelas.length(); i++){
            JSONObject obj = RegistroTabelas.getJSONObject(i);
            int ir = obj.getInt("i");
            int row = Tabelas[ir].getRowCount();
            int column = Tabelas[ir].getColumnCount();
            for (int r = 0; r  < row; r++) {
                JSONObject getTemporario2 = new JSONObject();
                for (int c = 0; c  < column; c++) {
                    //System.out.println("Tabelas[ir].getValueAt(r, c): "+Tabelas[ir].getValueAt(r, c));
                    switch(c){
                        case 0:
                            String Nome_Crime = Tabelas[ir].getValueAt(r, c)+"";
                            if("".equals(Nome_Crime)){ Nome_Crime = "Sem Nome";semnome++;}
                            getTemporario2.put("texto", Nome_Crime);
                        case 1:
                            getTemporario2.put("multa", Tabelas[ir].getValueAt(r, c));
                        case 2:
                            getTemporario2.put("meses", Tabelas[ir].getValueAt(r, c));
                        case 3:
                            String TipoValor = Tabelas[ir].getValueAt(r, c)+"";
                            if("".equals(TipoValor)) TipoValor = "UNICO";
                            getTemporario2.put("tipo", TipoValor);
                        case 4:
                            getTemporario2.put("obs", Tabelas[ir].getValueAt(r, c));
                    }
                }
                getTemporario2.put("categoria", obj.getInt("id"));
                novo_CrimesRegistro.put(getTemporario2);
            }
            //PAREI AQUI NO CODIGO DE PEGAR DADOS DA TABELA PARA SALVAR NO BANCO DE DADOS
            //novo_CrimesRegistro = new JSONArray();
            //novo_CategoriasCrimes = new JSONArray();
        }
        //System.out.println("novo_CrimesRegistro: "+novo_CrimesRegistro.toString());
        PegarTotalTabela();
        if(semnome > 0)InfoDB.setText(semnome+" crimes foram renomeados para 'Sem Nome'");
    }
    
    public static void PegarTotalTabela(){
        tCategorias = 0;
        tCrimes = 0;
        for(int i = 0; i < RegistroTabelas.length(); i++){
            JSONObject obj = RegistroTabelas.getJSONObject(i);
            int ir = obj.getInt("i");
            int row = Tabelas[ir].getRowCount();
            tCategorias++;
            for (int r = 0; r  < row; r++) {
                tCrimes++;
            }
        }
        InfoDB.setText("Total de "+tCrimes+" crimes em "+tCategorias+" categorias registradas.");
    }

    public void AddKey(java.awt.event.KeyEvent evt, JTable tabela) {
        int index = tabela.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel)tabela.getModel();
        
        
        pressedKeys.add(evt.getKeyCode());
        if (!pressedKeys.isEmpty()) {
            if (pressedKeys.contains(KeyEvent.VK_SHIFT)) {
                if (pressedKeys.contains(KeyEvent.VK_UP)) {
                    //System.out.println("SHIFT + UP");
                    if(index > 0) model.moveRow(index, index, index - 1);
                    //jTable1.setRowSelectionInterval(index - 1, index - 1);
                }
                if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
                    //System.out.println("SHIFT + DOWN");
                    if(index < model.getRowCount() - 1) model.moveRow(index, index, index + 1);
                }
            }
        }
    }
    
    public void SAVE(){      //Save the UserName and Password (for one user)
        try {
            File file = new File(InicializadorMain.DestFile2);
            if(!file.exists()) file.createNewFile();  //if the file !exist create a new one

            BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
            Base64.Encoder enc = Base64.getEncoder();
            
            bw.write(enc.encodeToString(novo_CategoriasCrimes.toString().getBytes())); //write the name
            bw.newLine(); //leave a new Line
            bw.write(enc.encodeToString(novo_CrimesRegistro.toString().getBytes())); //getPassword()
            bw.close(); //close the BufferdWriter

        } catch (IOException e) { e.printStackTrace(); }        
    }//End Of Save
    
    public static void UPDATE(){ //UPDATE ON OPENING THE APPLICATION
        try {
            File file = new File(InicializadorMain.DestFile2);
            if(file.exists()){    //if this file exists
                Scanner scan = new Scanner(file);   //Use Scanner to read the File
                /*while (scan.hasNext()) {
                    System.out.println(scan.next());
                }*/
                Base64.Decoder dec = Base64.getDecoder();
                String DecoderStre = DecodeBase64(scan.nextLine());
                if(!"".equals(DecoderStre)){
                    CategoriasStore = DecoderStre;
                }else{
                    showMessageDialog(null,"Ocorreu um erro ao pegar as categorias salvas. Os dados foram resetados.","Erro nos dados salvos",JOptionPane.PLAIN_MESSAGE);
                }
                
                DecoderStre = DecodeBase64(scan.nextLine());
                if(!"".equals(DecoderStre)){
                    CrimesStore = DecoderStre;
                }else{
                    showMessageDialog(null,"Ocorreu um erro ao pegar os crimes salvos. Os dados foram resetados.","Erro nos dados salvos",JOptionPane.PLAIN_MESSAGE);
                }
                scan.close();
            }

        } catch (FileNotFoundException e) {         
            e.printStackTrace();
        }
    }
    
    public static String DecodeBase64(String StrDec){
        Base64.Decoder dec = Base64.getDecoder();
        byte[] bites = null;
        try {
            bites = dec.decode(StrDec);
        } catch (IllegalArgumentException e) { e.printStackTrace(); }     
        if(bites != null) try {
            return new String(bites,"UTF8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Gerenciamento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public void AtalhoVer(){
        showMessageDialog(null,
            "Inserir Crime: Shift+Insert"+
            "\nRemover Crime: Shift+Delete"+
            "\nMover Crime Cima: Shift+Page_UP"+
            "\nMover Crime Baixo: Shift+Page_DOWN", 
            "Atalhos do Gerenciador de Crimes",JOptionPane.PLAIN_MESSAGE);
    }
    
    public static boolean ImportarDados(String Valores){
        if(Valores.contains("\n")){
            String[] Valor = Valores.split("\n");
            String DecoderStre = DecodeBase64(Valor[0]);
            if(!"".equals(DecoderStre)){
                CategoriasStore = DecoderStre;
            }else{
                return false;
            }
            
            DecoderStre = DecodeBase64(Valor[1]);
            if(!"".equals(DecoderStre)){
                CrimesStore = DecoderStre;
            }else{
                return false;
            }
            
            DecoderStre = DecodeBase64(Valor[2]);
            if(!"".equals(DecoderStre)){
                CrimesStoreOpcoes = DecoderStre;
            }else{
                return false;
            }
            
            DecoderStre = Gerenciamento.DecodeBase64(Valor[3]);
            if(!"".equals(DecoderStre)){
                OpcoesStore = DecoderStre;
            }else{
                return false;
            }
            
            PegarDB();
            return true;
        }
        return false;
    }
    
    boolean SalvarSair(){
        Object[] options = { "Sim", "Não" };
        int ret = JOptionPane.showOptionDialog(null, "Deseja sair sem salvar as alterações?", "Há alterações não salvas", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        //JOptionPane.showConfirmDialog((Component) obj, new JScrollPane(textArea), title, JOptionPane.OK_OPTION);
        if (ret == 0) {
            return false;
        }
        return true;
    }
    
     public static void AdicionarBotoesOpcoes(){
        DefaultTableModel modelTable = (DefaultTableModel)TabelaOpcoes.getModel();
        modelTable.setRowCount(0);
        int nivel_assinatura = SNWindows.getNivelSerialPC();
        if(InicializadorMain.ModoOffline){
            if(ReducaoRegistro.length() > SNWindows.BeneficiosAssinatura[nivel_assinatura]){
                TabelaOpcoes.setEnabled(false);
                MudarCalculoBt.setEnabled(false);
                MudarTipoBt.setEnabled(false);
                showMessageDialog(null,"A tabela importada é de uma assinatura. Você não conseguirá edita-la, mas funcionará normalmente."
                    + "\nVocê pode adquirir uma assinatura em nosso discord. (Exibir -> Sobre)", "Tabela importada de uma assinatura",JOptionPane.PLAIN_MESSAGE);
            }else{
                TabelaOpcoes.setEnabled(true);
                MudarCalculoBt.setEnabled(true);
                MudarTipoBt.setEnabled(true);
            }
        }
        
        for(int i = 0; i < ReducaoRegistro.length(); i++){
            JSONObject o = ReducaoRegistro.getJSONObject(i);
            //if(o.getInt("categoria") == o2.getInt("id")){
                modelTable.addRow(new Object[]{o.getString("texto"), o.getInt("meses"), o.getString("calculo"), o.getString("tipo")});
            //}
        }
        if(modelTable.getRowCount() <= 0){
            modelTable.addRow(new Object[]{"Reu Primario", 25, "PORCENTAGEM", "REDUCAO"});
            TabelaOpcoes.setRowSelectionInterval(0, 0);
        }
    }
    
    public static void PegarValoresTabelaOpcoes(){
        ReducaoRegistro = new JSONArray();
        int semnome = 0;
        int row = TabelaOpcoes.getRowCount();
        int column = TabelaOpcoes.getColumnCount();
        for (int r = 0; r  < row; r++) {
            JSONObject getTemporario2 = new JSONObject();
            for (int c = 0; c  < column; c++) {
                //System.out.println("Tabelas[ir].getValueAt(r, c): "+Tabelas[ir].getValueAt(r, c));
                switch(c){
                    case 0:
                        String Nome_Crime = TabelaOpcoes.getValueAt(r, c)+"";
                        if("".equals(Nome_Crime)){ Nome_Crime = "Sem Nome";semnome++;}
                        getTemporario2.put("texto", Nome_Crime);
                    case 1:
                        getTemporario2.put("meses", TabelaOpcoes.getValueAt(r, c));
                    case 2:
                        String TipoValor = TabelaOpcoes.getValueAt(r, c)+"";
                        if("".equals(TipoValor)) TipoValor = "PORCENTAGEM";
                        getTemporario2.put("calculo", TipoValor);
                    case 3:
                        String TipoValor2 = TabelaOpcoes.getValueAt(r, c)+"";
                        if("".equals(TipoValor2)) TipoValor2 = "REDUCAO";
                        getTemporario2.put("tipo", TipoValor2);
                }
            }
            ReducaoRegistro.put(getTemporario2);
        }
        //RecarregarValoresTabelaOpcoes();
        PegarTotalTabelaOpcoes();
        if(semnome > 0)InfoDB.setText(semnome+" foram renomeados para 'Sem Nome'");
    }
    
    public static void PegarTotalTabelaOpcoes(){
        InfoDB.setText("Total de "+TabelaOpcoes.getRowCount()+" aumento/redução de pena registradas.");
    }

    public void EditarDados(String Valor){
        String Numbr = "";
        String PegarValor;
        
        if("pena_max".equals(Valor)){
            Numbr = " Coloque somente números.";
            if(ConfigGerais.has(Valor)){
                if(ConfigGerais.getInt(Valor) > 0){ 
                    PegarValor = ConfigGerais.getInt(Valor)+"";
                }else{
                    PegarValor = "0";
                }
            }else{
                PegarValor = "0";
            }
        }else{
            if(ConfigGerais.has(Valor)){
                PegarValor = ConfigGerais.getString(Valor);
            }else{
                PegarValor = "";
            }
        }// ||
        
        
        //String obs = JOptionPane.showInputDialog(this, "Qual seria o novo valor?"+Numbr, "Modificar valor", JOptionPane.PLAIN_MESSAGE);
        String ValorDados = (String)JOptionPane.showInputDialog(this, "Digite o novo valor."+Numbr,
            "Alterando valor", JOptionPane.QUESTION_MESSAGE,null,null,PegarValor);
        
        if(ValorDados != null && (ValorDados.length() > 0 && ValorDados.length() < 20 && "".equals(Numbr)) || (!"".equals(Numbr) && SNWindows.isNumeric(ValorDados) && Integer.parseInt(ValorDados) >= 0 && ValorDados.length() < 6)){
            //ConfigGerais.setDadosParcial(Valor, ValorDados);
            ConfigGerais.put(Valor, ValorDados);
        }
        RecarregarValoresTabelaOpcoes();
    }
    
    private static void RecarregarValoresTabelaOpcoes(){
        if(!ConfigGerais.has("pena_max")) ConfigGerais.put("pena_max", 0);
        ValorTxt.setText(ConfigGerais.getInt("pena_max")+"");
        //ValorTxt2.setText(PegarUser.getString("nome")+" "+PegarUser.getString("sobrenome"));
        //ValorTxt3.setText(PegarUser.getString("registration").toUpperCase());
    }
    
    public void SAVEOpcoes(){      //Save the UserName and Password (for one user)
        try {
            File file = new File(InicializadorMain.DestFile3);
            if(!file.exists()) file.createNewFile();  //if the file !exist create a new one

            BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
            Base64.Encoder enc = Base64.getEncoder();
            //System.out.println("enc.encodeToString(ReducaoRegistro.toString().getBytes()): "+enc.encodeToString(ReducaoRegistro.toString().getBytes()));
            bw.write(enc.encodeToString(ReducaoRegistro.toString().getBytes())); //write the name
            
            bw.newLine(); //leave a new Line
            
            bw.write(enc.encodeToString(ConfigGerais.toString().getBytes())); //getPassword()
            bw.close(); //close the BufferdWriter

        } catch (IOException e) { e.printStackTrace(); }        
    }//End Of Save
    
    public static void UPDATEOpcoes(){ //UPDATE ON OPENING THE APPLICATION
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
                    CrimesStoreOpcoes = DecoderStre;
                }else{
                    showMessageDialog(null,"Ocorreu um erro ao pegar aumento/redução salvos. Os dados foram resetados.","Erro nos dados salvos",JOptionPane.PLAIN_MESSAGE);
                }
                
                DecoderStre = Gerenciamento.DecodeBase64(scan.nextLine());
                if(!"".equals(DecoderStre)){
                    OpcoesStore = DecoderStre;
                }else{
                    showMessageDialog(null,"Ocorreu um erro ao pegar as opções salvas. Os dados foram resetados.","Erro nos dados salvos",JOptionPane.PLAIN_MESSAGE);
                }
                scan.close();
            }

        } catch (FileNotFoundException e) {         
            e.printStackTrace();
        }
    }
    
    public void AddLinha(){
        //System.out.println("Tabelas[As].getRowCount(): "+Tabelas[As].getRowCount());

        //SNWindows.CorAssinatura[nivel_ass][0]
        
        int nivel_assinatura = SNWindows.getNivelSerialPC();
        DefaultTableModel model = (DefaultTableModel)TabelaOpcoes.getModel();
        if(model.getRowCount() < SNWindows.BeneficiosAssinatura[nivel_assinatura]){
            InfoDB1.setText("Nova linha foi adicionada");
            model.addRow(new Object[]{"", 0, "PORCENTAGEM", "REDUCAO"});
            TabelaOpcoes.setRowSelectionInterval(model.getRowCount()-1, model.getRowCount()-1);
            PegarTotalTabelaOpcoes();
        }else{
            pressedKeys = new HashSet<>();
            showMessageDialog(null,"Lamento, mas sua assinatura '"+SNWindows.TipoAssinatura[nivel_assinatura]+"' permite adicionar até "+SNWindows.BeneficiosAssinatura[nivel_assinatura]+" aumento/redução de pena."
            + "\nVocê pode adquirir uma assinatura em nosso discord. (Exibir -> Sobre)", "Erro ao inserir",JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    public void RemLinha(){
        int row = TabelaOpcoes.getSelectedRow();
        if(row >= 0){
            DefaultTableModel model = (DefaultTableModel)TabelaOpcoes.getModel();
            String NomeRow = TabelaOpcoes.getValueAt(row, 0)+"";
            InfoDB1.setText(NomeRow+" foi removido");
            model.removeRow( row );
            if(model.getRowCount() > 0){
                if(row < model.getRowCount() - 1 || row == model.getRowCount() - 1){
                    TabelaOpcoes.setRowSelectionInterval(row, row);
                }else if(row == 0){
                    TabelaOpcoes.setRowSelectionInterval(row+1, row+1);
                }else{
                    TabelaOpcoes.setRowSelectionInterval(row-1, row-1);
                }
            }
            PegarTotalTabela();
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

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        PainelDetalhes = new javax.swing.JPanel();
        SalvarDados = new javax.swing.JButton();
        ResetarTudo = new javax.swing.JButton();
        AddCategoria = new javax.swing.JButton();
        InfoDB = new javax.swing.JLabel();
        InfoDB1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        RenameTabBt = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        TabPainel = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        TabelaOpcoes = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        MudarTipoBt = new javax.swing.JButton();
        MudarCalculoBt = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        EditarBt1 = new javax.swing.JButton();
        NomeTxt = new javax.swing.JLabel();
        ValorTxt = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        EditarBt2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        ValorTxt2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        EditarBt3 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        ValorTxt3 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        EditarBt4 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        importar = new javax.swing.JMenuItem();
        exportar = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GERENCIAR CRIMES");
        setBackground(new java.awt.Color(13, 32, 64));
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("GERENCIADOR DE CRIMES");

        PainelDetalhes.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        SalvarDados.setBackground(new java.awt.Color(255, 255, 255));
        SalvarDados.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        SalvarDados.setText("SALVAR");
        SalvarDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalvarDadosActionPerformed(evt);
            }
        });

        ResetarTudo.setBackground(new java.awt.Color(255, 255, 255));
        ResetarTudo.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        ResetarTudo.setText("RESETAR");
        ResetarTudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetarTudoActionPerformed(evt);
            }
        });

        AddCategoria.setBackground(new java.awt.Color(255, 255, 255));
        AddCategoria.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        AddCategoria.setText("ADICIONAR CATEGORIA");
        AddCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddCategoriaActionPerformed(evt);
            }
        });

        InfoDB.setForeground(new java.awt.Color(255, 255, 255));
        InfoDB.setText(" ");

        InfoDB1.setForeground(new java.awt.Color(255, 255, 255));
        InfoDB1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        InfoDB1.setText(" ");

        javax.swing.GroupLayout PainelDetalhesLayout = new javax.swing.GroupLayout(PainelDetalhes);
        PainelDetalhes.setLayout(PainelDetalhesLayout);
        PainelDetalhesLayout.setHorizontalGroup(
            PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelDetalhesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelDetalhesLayout.createSequentialGroup()
                        .addComponent(AddCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ResetarTudo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SalvarDados, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PainelDetalhesLayout.createSequentialGroup()
                        .addComponent(InfoDB, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(InfoDB1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PainelDetalhesLayout.setVerticalGroup(
            PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelDetalhesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SalvarDados, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ResetarTudo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(InfoDB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(InfoDB1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel2.setForeground(new java.awt.Color(255, 255, 204));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("VER ATALHOS (SELECIONE ALGUMA LINHA PARA UTILIZA-LOS)");
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        jTabbedPane2.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N

        jTabbedPane1.setToolTipText("");
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NOME DO CRIME", "MULTA", "PENA EM MESES", "PREENCHIMENTO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.setShowGrid(true);
        jTable1.setUpdateSelectionOnSort(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jButton4.setText("ADICIONAR CRIME");
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jButton5.setText("DELETAR CATEGORIA");
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        RenameTabBt.setBackground(new java.awt.Color(255, 255, 255));
        RenameTabBt.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        RenameTabBt.setText("RENOMEAR CATEGORIA");
        RenameTabBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "UNICO", "QUANTIDADE" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RenameTabBt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RenameTabBt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", jPanel1);

        jTabbedPane2.addTab("GERENCIAR CRIMES", jTabbedPane1);

        TabPainel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(13, 32, 64));

        TabelaOpcoes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        TabelaOpcoes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NOME", "MESES", "CALCULO", "TIPO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TabelaOpcoes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TabelaOpcoes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        TabelaOpcoes.setShowGrid(true);
        TabelaOpcoes.setUpdateSelectionOnSort(false);
        TabelaOpcoes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelaOpcoesMouseClicked(evt);
            }
        });
        TabelaOpcoes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TabelaOpcoesKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                TabelaOpcoesKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(TabelaOpcoes);

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jButton6.setText("ADICIONAR LINHA");
        jButton6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        MudarTipoBt.setBackground(new java.awt.Color(255, 255, 255));
        MudarTipoBt.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        MudarTipoBt.setText("MUDAR TIPO");
        MudarTipoBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        MudarTipoBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MudarTipoBtActionPerformed(evt);
            }
        });

        MudarCalculoBt.setBackground(new java.awt.Color(255, 255, 255));
        MudarCalculoBt.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        MudarCalculoBt.setText("MUDAR CALCULO");
        MudarCalculoBt.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        MudarCalculoBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MudarCalculoBtActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(255, 255, 255));
        jButton7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jButton7.setText("REMOVER LINHA");
        jButton7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 850, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(MudarCalculoBt, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MudarTipoBt, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MudarTipoBt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MudarCalculoBt, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        TabPainel.addTab("Aumento/Redução de Pena", jPanel2);

        jPanel3.setBackground(new java.awt.Color(13, 32, 64));

        jPanel4.setOpaque(false);

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel5.setOpaque(false);

        EditarBt1.setBackground(new java.awt.Color(255, 255, 255));
        EditarBt1.setText("EDITAR");
        EditarBt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditarBt1ActionPerformed(evt);
            }
        });

        NomeTxt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        NomeTxt.setForeground(new java.awt.Color(255, 255, 255));
        NomeTxt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        NomeTxt.setText("PENA MÁXIMA:");
        NomeTxt.setToolTipText("Limite máximo de pena, não ultrapassará este valor.");

        ValorTxt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ValorTxt.setForeground(new java.awt.Color(255, 255, 255));
        ValorTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ValorTxt.setText("indefinido");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(NomeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ValorTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(EditarBt1, javax.swing.GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditarBt1, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
            .addComponent(ValorTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(NomeTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel6.setOpaque(false);

        EditarBt2.setBackground(new java.awt.Color(255, 255, 255));
        EditarBt2.setText("EDITAR");
        EditarBt2.setEnabled(false);
        EditarBt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditarBt2ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("EM BREVE:");
        jLabel5.setEnabled(false);

        ValorTxt2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ValorTxt2.setForeground(new java.awt.Color(255, 255, 255));
        ValorTxt2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ValorTxt2.setText("indefinido");
        ValorTxt2.setEnabled(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ValorTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(EditarBt2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditarBt2, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
            .addComponent(ValorTxt2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel7.setOpaque(false);

        EditarBt3.setBackground(new java.awt.Color(255, 255, 255));
        EditarBt3.setText("EDITAR");
        EditarBt3.setEnabled(false);
        EditarBt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditarBt3ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("EM BREVE:");
        jLabel9.setEnabled(false);

        ValorTxt3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ValorTxt3.setForeground(new java.awt.Color(255, 255, 255));
        ValorTxt3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ValorTxt3.setText("indefinido");
        ValorTxt3.setEnabled(false);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ValorTxt3, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(EditarBt3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditarBt3, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
            .addComponent(ValorTxt3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel8.setOpaque(false);

        EditarBt4.setBackground(new java.awt.Color(255, 255, 255));
        EditarBt4.setText("EDITAR");
        EditarBt4.setEnabled(false);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("EM BREVE:");
        jLabel11.setEnabled(false);

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("indefinido");
        jLabel12.setEnabled(false);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(EditarBt4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditarBt4, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("0 = Ilimitado");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        TabPainel.addTab("Configurações Gerais", jPanel3);

        jTabbedPane2.addTab("OPÇÕES", TabPainel);

        jMenu3.setText("VOLTAR");

        jMenuItem2.setText("VOLTAR PARA O PAINEL");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        jMenuBar2.add(jMenu3);

        jMenu5.setText("EXPORTAR/IMPORTAR");

        importar.setText("IMPORTAR");
        importar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importarActionPerformed(evt);
            }
        });
        jMenu5.add(importar);

        exportar.setText("EXPORTAR");
        exportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportarActionPerformed(evt);
            }
        });
        jMenu5.add(exportar);

        jMenuBar2.add(jMenu5);

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

        jMenuItem4.setText("ATALHOS");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem4);

        jMenuBar2.add(jMenu4);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane2)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PainelDetalhes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PainelDetalhes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    void SalvarFunction(){
        if(SalvarSairBool){
            if (SalvarSair()){
                SalvarDadosBT();
            }
        }
    }
    
    void SalvarDadosBT(){
        PegarValoresTabela();
        PegarValoresTabelaOpcoes();
        if(InicializadorMain.ModoOffline){
            SAVE();
            UPDATE();
            
            SAVEOpcoes();
            UPDATEOpcoes();
            
            PegarDB();
        }else{
            ConexaoDB conexao = new ConexaoDB();
            conexao.SetarCrimesECategoria(novo_CategoriasCrimes.toString(), novo_CrimesRegistro.toString(), contageGetCrimes);
            PegarDB();
        }
    }
    

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        int index = jTable1.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        pressedKeys.add(evt.getKeyCode());
        if (!pressedKeys.isEmpty()) {
            if (pressedKeys.contains(KeyEvent.VK_SHIFT)) {
                if (pressedKeys.contains(KeyEvent.VK_UP)) {
                    //System.out.println("SHIFT + UP");
                    if(index > 0) model.moveRow(index, index, index - 1);
                    //jTable1.setRowSelectionInterval(index - 1, index - 1);
                }
                if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
                    //System.out.println("SHIFT + DOWN");
                    if(index < model.getRowCount() - 1) model.moveRow(index, index, index + 1);
                }
            }
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        //System.out.println("REMOVIDO");
        pressedKeys.remove(evt.getKeyCode());
    }//GEN-LAST:event_jTable1KeyReleased

    private void AddCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddCategoriaActionPerformed
        //String NomeCategoria = TxtCategoria.getText();
        
        if(jTabbedPane1.getTabCount() < SNWindows.CategAssinatura[Nivel_Ass][0]){
            String NomeCategoria = JOptionPane.showInputDialog(this, "Escolha o nome para a categoria. Entre 3 e 30 caracteres.", "Nome da Categoria", JOptionPane.PLAIN_MESSAGE);
            if(NomeCategoria != null && !"".equals(NomeCategoria) && NomeCategoria.length() >= 3 && NomeCategoria.length() <= 30){
                PegarValoresTabela();
                JSONObject getTemporario10 = new JSONObject();
                TotalIdsTab++;
                getTemporario10.put("id", TotalIdsTab);
                getTemporario10.put("nome_categoria", NomeCategoria);

                novo_CategoriasCrimes.put(getTemporario10);
                //TxtCategoria.setText(null);
                AtualizarJanelas();
                SalvarSairBool=true;
            }else{
                showMessageDialog(null,"O nome da categoria deve estar entre 3 e 30 caracteres.", "Erro ao adicionar categoria",JOptionPane.PLAIN_MESSAGE);
            }
        }else{
            showMessageDialog(null,"Lamento, mas sua assinatura '"+SNWindows.TipoAssinatura[Nivel_Ass]+"' permite adicionar até "+SNWindows.CategAssinatura[Nivel_Ass][0]+" categorias."
                    + "\nVocê pode adquirir uma assinatura em nosso discord. (Exibir -> Sobre)", "Erro ao inserir nova categoria",JOptionPane.PLAIN_MESSAGE);
        }
    }//GEN-LAST:event_AddCategoriaActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void ResetarTudoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetarTudoActionPerformed

        if(InicializadorMain.ModoOffline){
            UPDATE();
            UPDATEOpcoes();
            if(novo_CategoriasCrimes.length() > SNWindows.CategAssinatura[Nivel_Ass][0] || novo_CrimesRegistro.length() > SNWindows.CategAssinatura[Nivel_Ass][1]){
                Object[] options = { "Resetar TUDO", "Resetar Alterações" }; 
                int Escolha=JOptionPane.showOptionDialog(this,
                        "Você deseja resetar tudo ou resetar as alterações?\nAo resetar tudo será zerada toda a tabela.", 
                        "Vai um Master Reset?", 
                        JOptionPane.DEFAULT_OPTION, 
                        JOptionPane.WARNING_MESSAGE, 
                        null, 
                        options, 
                        options[0]);
                if(Escolha==JOptionPane.YES_OPTION){
                    CategoriasStore="";
                    CrimesStore="";
                }
            }
        }
        PegarDB();
    }//GEN-LAST:event_ResetarTudoActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void SalvarDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalvarDadosActionPerformed
        PegarValoresTabela();
        SalvarSairBool=false;
        SalvarDadosBT();
        InfoDB1.setText("Salvo com sucesso!");
    }//GEN-LAST:event_SalvarDadosActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        SalvarFunction();
        //Timere.cancel();
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

    private void importarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importarActionPerformed
        /*Export.setLocationRelativeTo(null);
        Export.Titulo.setText("IMPORTAR");
        Export.AreaTexto.setEditable(true);
        Export.AreaTexto.setText("");
        Export.Copiar.setText("IMPORTAR CRIMES");
        Export.ExportValor = 1;
        Export.AreaTexto.grabFocus();
        Export.setVisible(true);*/
        String ExportStr = ExportarOuImportar.textAreaDialog(null, "Importar Tabela", "", true, "Importar");
        if(ExportStr != null && ExportStr.length() > 0){
            ImportarDados(ExportStr);
        }
    }//GEN-LAST:event_importarActionPerformed

    private void exportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportarActionPerformed
        /*Export.setLocationRelativeTo(null);
        Export.Titulo.setText("EXPORTAR");
        Export.AreaTexto.setEditable(false);
        
        
        Export.Copiar.setText("COPIAR TABELA");
        Base64.Encoder enc = Base64.getEncoder();
            
        String Catego = enc.encodeToString(novo_CategoriasCrimes.toString().getBytes());
        String Crimer = enc.encodeToString(novo_CrimesRegistro.toString().getBytes());
        
        Export.AreaTexto.setText(Catego+"\n"+Crimer);
        
        Export.TextoExportar = Catego+"\n"+Crimer;
        Export.ExportValor = 2;
        Export.setVisible(true);*/
        Base64.Encoder enc = Base64.getEncoder();
        String ReducaoAumento = enc.encodeToString(novo_CategoriasCrimes.toString().getBytes());
        String OpcoesDeCrimes = enc.encodeToString(novo_CrimesRegistro.toString().getBytes());
        
        String AumentoReducao1 = enc.encodeToString(ReducaoRegistro.toString().getBytes());
        String AumentoReducao2 = enc.encodeToString(ConfigGerais.toString().getBytes());
        
        String ExportStr = ExportarOuImportar.textAreaDialog(null, "Exportar Tabela", ReducaoAumento+"\n"+OpcoesDeCrimes+"\n"+AumentoReducao1+"\n"+AumentoReducao2, false, "Exportar");
        if(ExportStr != null && ExportStr.length() > 0){
            StringSelection stringSelection = new StringSelection(ExportStr);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        }
    }//GEN-LAST:event_exportarActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        AtalhoVer();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        AtalhoVer();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void TabelaOpcoesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelaOpcoesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TabelaOpcoesMouseClicked

    private void TabelaOpcoesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TabelaOpcoesKeyPressed
        int index = jTable1.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        pressedKeys.add(evt.getKeyCode());
        if (!pressedKeys.isEmpty()) {
            if (pressedKeys.contains(KeyEvent.VK_SHIFT)) {
                int row = jTable1.getSelectedRow();
                if(row >= 0){
                    String NomeRow = jTable1.getValueAt(row, 0)+"";
                    if("".equals(NomeRow)) NomeRow = "Linha "+(row+1);
                    if (pressedKeys.contains(KeyEvent.VK_UP)) {
                        //System.out.println("SHIFT + UP");
                        if(index > 0){
                            InfoDB1.setText(NomeRow+" movido para cima");
                            model.moveRow(index, index, index - 1);
                            PegarValoresTabela();
                        }
                        //jTable1.setRowSelectionInterval(index - 1, index - 1);
                    }
                    if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
                        //System.out.println("SHIFT + DOWN");
                        if(index < model.getRowCount() - 1){
                            InfoDB1.setText(NomeRow+" movido para baixo");
                            model.moveRow(index, index, index + 1);
                            PegarValoresTabela();
                        }
                    }
                    if (pressedKeys.contains(KeyEvent.VK_DELETE)) {
                        InfoDB1.setText(NomeRow+" foi removido");
                        model.removeRow( row );
                        if(model.getRowCount() > 0){
                            if(index < model.getRowCount() - 1 || index == model.getRowCount() - 1){
                                jTable1.setRowSelectionInterval(index, index);
                            }else if(index == 0){
                                jTable1.setRowSelectionInterval(index+1, index+1);
                            }else{
                                jTable1.setRowSelectionInterval(index-1, index-1);
                            }
                        }
                        PegarTotalTabela();
                    }
                }
                if (pressedKeys.contains(KeyEvent.VK_INSERT)) {
                    //AddLinha();
                }
            }
        }
    }//GEN-LAST:event_TabelaOpcoesKeyPressed

    private void TabelaOpcoesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TabelaOpcoesKeyReleased
        //System.out.println("REMOVIDO");
        pressedKeys.remove(evt.getKeyCode());
    }//GEN-LAST:event_TabelaOpcoesKeyReleased

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        AddLinha();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void MudarTipoBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MudarTipoBtActionPerformed
        int index = jTable1.getSelectedRow();
        if(index >= 0){
            String ValorAtualTabela = jTable1.getValueAt(index, 3)+"";
            if(null != ValorAtualTabela)switch (ValorAtualTabela) {
                case "REDUCAO":
                ValorAtualTabela = "AUMENTO";
                break;
                case "AUMENTO":
                ValorAtualTabela = "REDUCAO";
                break;
                default:
                ValorAtualTabela = "REDUCAO";
                break;
            }
            jTable1.setValueAt(ValorAtualTabela, index, 3);
        }
    }//GEN-LAST:event_MudarTipoBtActionPerformed

    private void MudarCalculoBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MudarCalculoBtActionPerformed
        int index = jTable1.getSelectedRow();
        if(index >= 0){
            String ValorAtualTabela = jTable1.getValueAt(index, 2)+"";
            if(null != ValorAtualTabela)switch (ValorAtualTabela) {
                case "PORCENTAGEM":
                ValorAtualTabela = "FIXO";
                break;
                case "FIXO":
                ValorAtualTabela = "PORCENTAGEM";
                break;
                default:
                ValorAtualTabela = "PORCENTAGEM";
                break;
            }
            jTable1.setValueAt(ValorAtualTabela, index, 2);
        }
    }//GEN-LAST:event_MudarCalculoBtActionPerformed

    private void EditarBt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarBt1ActionPerformed
        EditarDados("pena_max");
    }//GEN-LAST:event_EditarBt1ActionPerformed

    private void EditarBt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarBt2ActionPerformed
        EditarDados("nome");
    }//GEN-LAST:event_EditarBt2ActionPerformed

    private void EditarBt3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarBt3ActionPerformed
        EditarDados("registration");
    }//GEN-LAST:event_EditarBt3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        RemLinha();
    }//GEN-LAST:event_jButton7ActionPerformed

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
            java.util.logging.Logger.getLogger(Gerenciamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gerenciamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gerenciamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gerenciamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gerenciamento().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private static javax.swing.JButton AddCategoria;
    private javax.swing.JButton EditarBt1;
    private javax.swing.JButton EditarBt2;
    private javax.swing.JButton EditarBt3;
    private javax.swing.JButton EditarBt4;
    private static javax.swing.JLabel InfoDB;
    private static javax.swing.JLabel InfoDB1;
    private static javax.swing.JButton MudarCalculoBt;
    private static javax.swing.JButton MudarTipoBt;
    private javax.swing.JLabel NomeTxt;
    private javax.swing.JPanel PainelDetalhes;
    private javax.swing.JButton RenameTabBt;
    private javax.swing.JButton ResetarTudo;
    private javax.swing.JButton SalvarDados;
    private javax.swing.JTabbedPane TabPainel;
    private static javax.swing.JTable TabelaOpcoes;
    private static javax.swing.JLabel ValorTxt;
    private javax.swing.JLabel ValorTxt2;
    private javax.swing.JLabel ValorTxt3;
    private javax.swing.JMenuItem exportar;
    private javax.swing.JMenuItem importar;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private static javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
