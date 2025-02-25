/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.json.JSONArray;
import org.json.JSONObject;
import police.configs.ConexaoDB;
import police.configs.ExportarOuImportar;
import police.configs.GetImages;
import police.configs.SNWindows;

/**
 *
 * @author John
 */
public class GerenciamentoUsuarios extends javax.swing.JFrame {

    /**
     * Creates new form Prisoes
     */
    static JSONArray CrimesRegistro = new JSONArray();
    static JSONArray CategoriasCrimes = new JSONArray();
    
    static JSONArray RegistroTabelas = new JSONArray();
    
    static JSONObject ConfigGerais = new JSONObject();
    
    static JSONArray ReducaoRegistro = new JSONArray();
    
    static JSONArray GetCrimes = new JSONArray();
    static int contageGetCrimes = 0;
    
    static int TotalIdsTab = 0;
    
    static int SalvarTime = 0;
    static String CrimesStore="";
    static String OpcoesStore="";
    
    
    
    
    private static Set<Integer> pressedKeys = new HashSet<>();
    
    static int Nivel_Ass = 0;
    /*JSONArray CategoriasCrimes = new JSONArray();
    JSONArray CrimesRegistro = new JSONArray();*/
    static JFrame EsteFrame2 = new JFrame();
    public GerenciamentoUsuarios() {
        initComponents();
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        jTable1.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        jTable1.getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
        jTable1.getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
        jTable1.getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
        jTable1.getColumnModel().getColumn(4).setCellRenderer( centerRenderer );
        
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(7);
        
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(7);

        ((DefaultTableCellRenderer)jTable1.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        jTable1.getTableHeader().setReorderingAllowed(false);

        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.setShowGrid(true);
        jTable1.setUpdateSelectionOnSort(false);
        
        SalvarTime=10;
        EsteFrame2 = this;
        //Timere.cancel();
        
        if(InicializadorMain.ModoOffline){
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagens/CB2.png")));
        }else{
            this.setIconImage(new ImageIcon(GetImages.LogoCB).getImage());
        }
        
        if(InicializadorMain.ModoOffline) UPDATEUsers();
        getContentPane().setBackground(new java.awt.Color(13, 32, 64));
        this.setLocationRelativeTo(null);
        PainelDetalhes.setBackground(new java.awt.Color(13, 32, 64));
        
        jPanel1.setBackground(new java.awt.Color(13, 32, 64));
        jPanel2.setBackground(new java.awt.Color(13, 32, 64));
        //Export.setLocationRelativeTo(null);
        PegarDBOpcoes();
        RecarregarValoresTabela();
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
                if(!EsteFrame2.isVisible()){
                    timer.cancel();
                }
            }; 
        }; 
        timer.schedule(tt, 1000, 1000); 
    }
    
    
    public static void PegarDBOpcoes(){
        if(InicializadorMain.ModoOffline){
            ReducaoRegistro = new JSONArray();
            if(!"".equals(CrimesStore) && CrimesStore.length() > 10){
                ReducaoRegistro = new JSONArray(CrimesStore);
            }/*else{
                JSONObject getTemporario2 = new JSONObject();
                getTemporario2.put("user_id", "1");
                getTemporario2.put("nome", "Fulano");
                getTemporario2.put("registration", "N/A");
                getTemporario2.put("phone", "N/A");
                getTemporario2.put("preso", 0);
                ReducaoRegistro.put(getTemporario2);
            }*/
            
            ConfigGerais = new JSONObject();
            if(!"".equals(OpcoesStore) && OpcoesStore.length() > 10){
                ConfigGerais = new JSONObject(OpcoesStore);
            }else{
                ConfigGerais.put("save_automatico", 0);
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
        AdicionarBotoes();
        SalvarTime=5;
        PegarValoresTabela();
        RecarregarValoresTabela();
    }
    
    //JComboBox[] TipoEscolha = new JComboBox[10];
    
    static void AdicionarBotoes(){
        DefaultTableModel modelTable = (DefaultTableModel)jTable1.getModel();
        modelTable.setRowCount(0);
        
        int nivel_assinatura = SNWindows.getNivelSerialPC();
        boolean NaoAdd = false;
        if(InicializadorMain.ModoOffline){
            //if(nivel_assinatura > 0){
                if(modelTable.getRowCount() > SNWindows.UsuariosAssinatura[nivel_assinatura]){
                    jTable1.setEnabled(false);
                    showMessageDialog(null,"A tabela em uso possui "+modelTable.getRowCount()+" usuários e sua assinatura '"+SNWindows.TipoAssinatura[nivel_assinatura]+"' permite registrar até "+SNWindows.UsuariosAssinatura[nivel_assinatura]+" usuários."
                        + "\nFuncionará tudo normalmente, mas você não conseguirá editá-la", "Assinatura não compatível com tabela",JOptionPane.PLAIN_MESSAGE);
                }else{
                    jTable1.setEnabled(true);
                }
            /*}else{
                jTable1.setEnabled(false);
                showMessageDialog(null,"Lamento, mas a tabela é de uma assinatura. Para importar ou exportar tabelas é necessário possuir uma assinatura."
                    + "\nVocê pode adquirir uma assinatura em nosso discord. (Exibir -> Sobre)", "Assinatura",JOptionPane.PLAIN_MESSAGE);
                NaoAdd = true;
            }*/
        }
        if(!NaoAdd){
            for(int i = 0; i < ReducaoRegistro.length(); i++){
                JSONObject o = ReducaoRegistro.getJSONObject(i);
                //if(o.getInt("categoria") == o2.getInt("id")){
                    modelTable.addRow(new Object[]{o.getInt("user_id"), o.getString("nome"), o.getString("registration"), o.getString("phone"), o.getInt("age") });
                //}
            }
        }
        
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(jTable1.getModel());
        jTable1.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeys = new ArrayList<>();

        int columnIndexToSort = 0;
        sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));

        sorter.setSortKeys(sortKeys);
        sorter.sort();
        sorter.setSortable(1, true);
        sorter.setSortable(2, false);
        sorter.setSortable(3, false);
        sorter.setSortable(4, false);
        /*
        if(modelTable.getRowCount() <= 0){
            modelTable.addRow(new Object[]{"Reu Primario", 25, "PORCENTAGEM", "REDUCAO"});
            jTable1.setRowSelectionInterval(0, 0);
        }*/
    }
    
    static void PegarValoresTabela(){
        ReducaoRegistro = new JSONArray();
        int semnome = 0;
        int row = jTable1.getRowCount();
        int column = jTable1.getColumnCount();
        
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        for (int r = 0; r  < row; r++) {
            JSONObject getTemporario2 = new JSONObject();
            boolean naosalvar = false;
            for (int c = 0; c  < column; c++) {
                //System.out.println("Tabelas[ir].getValueAt(r, c): "+Tabelas[ir].getValueAt(r, c));
                switch(c){
                    case 0:
                        int id_user = Integer.parseInt(jTable1.getValueAt(r, c).toString());
                        if(id_user > 0){
                            getTemporario2.put("user_id", id_user);
                        }else{
                            naosalvar = true;
                        }
                    case 1:
                        String Nome_Player = jTable1.getValueAt(r, c)+"";
                        if("".equals(Nome_Player)){ Nome_Player = "Sem Nome";semnome++;}
                        if(!naosalvar) getTemporario2.put("nome", Nome_Player);
                    case 2:
                        String Registro = jTable1.getValueAt(r, c)+"";
                        if("".equals(Registro)) Registro = "N/A";
                        if(!naosalvar) getTemporario2.put("registration", Registro);
                    case 3:
                        String Phone = jTable1.getValueAt(r, c)+"";
                        if("".equals(Phone)) Phone = "N/A";
                        if(!naosalvar) getTemporario2.put("phone", Phone);
                    case 4:
                        if(!naosalvar) getTemporario2.put("age", jTable1.getValueAt(r, c));
                    //case 5:
                      //  if(!naosalvar) getTemporario2.put("vezes_preso", jTable1.getValueAt(r, c));
                }
            }
            if(!naosalvar) ReducaoRegistro.put(getTemporario2);
        }
        //RecarregarValoresTabela();
        PegarTotalTabela();
        if(semnome > 0)InfoDB.setText(semnome+" foram renomeados para 'Sem Nome'");
    }
    
    static void PegarTotalTabela(){
        InfoDB.setText("Total de "+jTable1.getRowCount()+" aumento/redução de pena registradas.");
    }
    void EditarDados(String Valor){
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
        RecarregarValoresTabela();
    }
    
    void EditarDadosBool(String Valor){
        int PegarValor;
        int ValorDados = 0;
        if(ConfigGerais.has(Valor)){
            PegarValor = ConfigGerais.getInt(Valor);
            if(PegarValor == 0){
                ValorDados = 1;
            }
        }
        
        ConfigGerais.put(Valor, ValorDados);
        RecarregarValoresTabela();
    }
    static String[] TipoTrueFalse = {
        "NÃO",
        "SIM"
    };
    static void RecarregarValoresTabela(){
        if(!ConfigGerais.has("save_automatico")) ConfigGerais.put("save_automatico", 0);
        ValorTxt.setText(TipoTrueFalse[ConfigGerais.getInt("save_automatico")]);
        //ValorTxt2.setText(PegarUser.getString("nome")+" "+PegarUser.getString("sobrenome"));
        //ValorTxt3.setText(PegarUser.getString("registration").toUpperCase());
    }
    
    public static void SAVEUsers(){      //Save the UserName and Password (for one user)
        try {
            File file = new File(InicializadorMain.DestFileUsers);
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
    
    public static void UPDATEUsers(){ //UPDATE ON OPENING THE APPLICATION
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
                    CrimesStore = DecoderStre;
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
    
    public static void SAVEUsersAdd(JSONArray novousuario, JSONObject OpcoesNovoUser){      //Save the UserName and Password (for one user)
        try {
            File file = new File(InicializadorMain.DestFileUsers);
            if(!file.exists()) file.createNewFile();  //if the file !exist create a new one

            BufferedWriter bw = new BufferedWriter(new FileWriter(file.getAbsolutePath()));
            Base64.Encoder enc = Base64.getEncoder();
            //System.out.println("enc.encodeToString(ReducaoRegistro.toString().getBytes()): "+enc.encodeToString(ReducaoRegistro.toString().getBytes()));
            bw.write(enc.encodeToString(novousuario.toString().getBytes())); //write the name
            
            bw.newLine(); //leave a new Line
            
            bw.write(enc.encodeToString(OpcoesNovoUser.toString().getBytes())); //getPassword()
            bw.close(); //close the BufferdWriter

        } catch (IOException e) { e.printStackTrace(); }        
    }//End Of Save
    
    void AtalhoVer(){
        showMessageDialog(null,
            "Inserir Usuário: Shift+Insert"+
            "\nRemover Usuário: Shift+Delete", 
            "Atalhos do Gerenciador de Usuários",JOptionPane.PLAIN_MESSAGE);
    }
    
    public void AddLinha(){
        //System.out.println("Tabelas[As].getRowCount(): "+Tabelas[As].getRowCount());

        //SNWindows.CorAssinatura[nivel_ass][0]
        
        int nivel_assinatura = SNWindows.getNivelSerialPC();
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        if(model.getRowCount() < SNWindows.UsuariosAssinatura[nivel_assinatura]){
            InfoDB1.setText("Nova linha foi adicionada");
            //model.addRow(new Object[]{"", 0, "PORCENTAGEM", "REDUCAO"});
            model.addRow(new Object[]{0, "", "", "", 0, 0 });
            jTable1.setRowSelectionInterval(model.getRowCount()-1, model.getRowCount()-1);
            PegarTotalTabela();
        }else{
            pressedKeys = new HashSet<>();
            showMessageDialog(null,"Lamento, mas sua assinatura '"+SNWindows.TipoAssinatura[nivel_assinatura]+"' permite registrar até "+SNWindows.UsuariosAssinatura[nivel_assinatura]+" usuários."
            + "\nVocê pode adquirir uma assinatura em nosso discord. (Exibir -> Sobre)", "Erro ao inserir",JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    public void RemLinha(){
        int row = jTable1.getSelectedRow();
        if(row >= 0){
            DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
            String NomeRow = jTable1.getValueAt(row, 0)+"";
            InfoDB1.setText(NomeRow+" foi removido");
            model.removeRow( row );
            if(model.getRowCount() > 0){
                if(row < model.getRowCount() - 1 || row == model.getRowCount() - 1){
                    jTable1.setRowSelectionInterval(row, row);
                }else if(row == 0){
                    jTable1.setRowSelectionInterval(row+1, row+1);
                }else{
                    jTable1.setRowSelectionInterval(row-1, row-1);
                }
            }
            PegarTotalTabela();
        }
    }
    
    public static boolean ImportarDadosOpcoes(String Valores){
        if(Valores.contains("\n")){
            String[] Valor = Valores.split("\n");
            String DecoderStre = Gerenciamento.DecodeBase64(Valor[0]);
            if(!"".equals(DecoderStre)){
                CrimesStore = DecoderStre;
            }else{
                return false;
            }
            
            DecoderStre = Gerenciamento.DecodeBase64(Valor[1]);
            if(!"".equals(DecoderStre)){
                OpcoesStore = DecoderStre;
            }else{
                return false;
            }
            
            PegarDBOpcoes();
            return true;
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

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        TabPainel = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        EditarBt1 = new javax.swing.JButton();
        NomeTxt = new javax.swing.JLabel();
        ValorTxt = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        EditarBt2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        ValorTxt2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        EditarBt3 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        ValorTxt3 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        EditarBt4 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        PainelDetalhes = new javax.swing.JPanel();
        SalvarDados = new javax.swing.JButton();
        ResetarTudo = new javax.swing.JButton();
        InfoDB = new javax.swing.JLabel();
        InfoDB1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
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
        setTitle("GERENCIAR USUÁRIOS");
        setBackground(new java.awt.Color(13, 32, 64));
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("GERENCIADOR DE USUÁRIOS");

        TabPainel.setBackground(new java.awt.Color(255, 255, 255));

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOME", "RG", "TELEFONE", "IDADE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
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
        jButton4.setText("ADICIONAR USUÁRIO");
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jButton5.setText("REMOVER USUÁRIO");
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        TabPainel.addTab("Aumento/Redução de Pena", jPanel1);

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        jPanel3.setOpaque(false);

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel4.setOpaque(false);

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
        NomeTxt.setText("SALVAR AUTO:");
        NomeTxt.setToolTipText("Salvar automaticamente ao registrar");

        ValorTxt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ValorTxt.setForeground(new java.awt.Color(255, 255, 255));
        ValorTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ValorTxt.setText("indefinido");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(NomeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ValorTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(EditarBt1, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditarBt1, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
            .addComponent(ValorTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(NomeTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel5.setOpaque(false);

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

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ValorTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(EditarBt2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditarBt2, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
            .addComponent(ValorTxt2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel6.setOpaque(false);

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

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ValorTxt3, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(EditarBt3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditarBt3, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
            .addComponent(ValorTxt3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel7.setOpaque(false);

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

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(EditarBt4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditarBt4, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("0 = Ilimitado");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        TabPainel.addTab("Configurações Gerais", jPanel2);

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
                        .addGap(0, 0, Short.MAX_VALUE)
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
                    .addComponent(ResetarTudo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(TabPainel)
                    .addComponent(PainelDetalhes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TabPainel, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PainelDetalhes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    

    private void jTable1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyPressed
        int index = jTable1.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        pressedKeys.add(evt.getKeyCode());
        if (!pressedKeys.isEmpty()) {
            if (pressedKeys.contains(KeyEvent.VK_SHIFT)) {
                int row = jTable1.getSelectedRow();
                if(row >= 0){
                    String NomeRow = jTable1.getValueAt(row, 0)+"";
                    if("".equals(NomeRow)) NomeRow = "Linha "+(row+1);
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
                    AddLinha();
                }
            }
        }
    }//GEN-LAST:event_jTable1KeyPressed

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        //System.out.println("REMOVIDO");
        pressedKeys.remove(evt.getKeyCode());
    }//GEN-LAST:event_jTable1KeyReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        AddLinha();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void ResetarTudoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetarTudoActionPerformed

        if(InicializadorMain.ModoOffline){
            UPDATEUsers();
            //if(ConfigGerais.length() > SNWindows.CategAssinatura[Nivel_Ass][0] || ReducaoRegistro.length() > SNWindows.CategAssinatura[Nivel_Ass][1]){
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
                    OpcoesStore="";
                    CrimesStore="";
                }
            //}
        }
        PegarDBOpcoes();
    }//GEN-LAST:event_ResetarTudoActionPerformed

    private void SalvarDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalvarDadosActionPerformed
        PegarValoresTabela();
        if(InicializadorMain.ModoOffline){
            System.out.println("ReducaoRegistro.toString(): "+ReducaoRegistro.toString());
            SAVEUsers();
            UPDATEUsers();
            PegarDBOpcoes();
        }else{
            //ConexaoDB conexao = new ConexaoDB();
            //conexao.SetarCrimesECategoria(ConfigGerais.toString(), ReducaoRegistro.toString(), contageGetCrimes);
            PegarDBOpcoes();
        }
        InfoDB1.setText("Salvo com sucesso!");
    }//GEN-LAST:event_SalvarDadosActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
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
        int nivel_assinatura = SNWindows.getNivelSerialPC();
        if(nivel_assinatura > 0){
            String ExportStr = ExportarOuImportar.textAreaDialog(null, "Importar Tabela", "", true, "Importar");
            if(ExportStr != null && ExportStr.length() > 0){
                ImportarDadosOpcoes(ExportStr);
            }
        }else{
            showMessageDialog(null,"Lamento, mas para Importar Usuários é necessário possuir uma assinatura."
                    + "\nVocê pode adquirir uma assinatura em nosso discord. (Exibir -> Sobre)", "Assine já!",JOptionPane.PLAIN_MESSAGE);
        }
    }//GEN-LAST:event_importarActionPerformed

    private void exportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportarActionPerformed
        int nivel_assinatura = SNWindows.getNivelSerialPC();
        if(nivel_assinatura > 0){
            Base64.Encoder enc = Base64.getEncoder();
            String ReducaoAumento = enc.encodeToString(ReducaoRegistro.toString().getBytes());
            String OpcoesDeCrimes = enc.encodeToString(ConfigGerais.toString().getBytes());

            String ExportStr = ExportarOuImportar.textAreaDialog(null, "Exportar Tabela", ReducaoAumento+"\n"+OpcoesDeCrimes, false, "Exportar");
            if(ExportStr != null && ExportStr.length() > 0){
                StringSelection stringSelection = new StringSelection(ExportStr);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        }else{
            showMessageDialog(null,"Lamento, mas para Exportar Usuários é necessário possuir uma assinatura."
                    + "\nVocê pode adquirir uma assinatura em nosso discord. (Exibir -> Sobre)", "Assine já!",JOptionPane.PLAIN_MESSAGE);
        }
        //JOptionPane.showMessageDialog(null, msg, title, JOptionPane.OK_CANCEL_OPTION);
        
        /*String obs = JOptionPane.showInputDialog(this, "Qual seria o novo valor?"+Numbr, "Modificar valor", JOptionPane.PLAIN_MESSAGE);
        if((obs.length() > 0 && obs.length() < 20 && "".equals(Numbr)) || (!"".equals(Numbr) && ViewUsuario.isNumeric(obs) && obs.length() < 6)){
            Usuario.setDadosParcial(Valor, obs);
            Usuario.setContaPC();
        }*/
        /*Export.setLocationRelativeTo(null);
        Export.Titulo.setText("EXPORTAR");
        Export.AreaTexto.setEditable(false);
        
        
        Export.Copiar.setText("COPIAR TABELA");
        Base64.Encoder enc = Base64.getEncoder();
            
        String Catego = enc.encodeToString(OpcoesCrimes.toString().getBytes());
        String Crimer = enc.encodeToString(ReducaoRegistro.toString().getBytes());
        
        Export.AreaTexto.setText(Catego+"\n"+Crimer);
        
        Export.TextoExportar = Catego+"\n"+Crimer;
        Export.ExportValor = 2;
        Export.setVisible(true);*/
    }//GEN-LAST:event_exportarActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        AtalhoVer();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        AtalhoVer();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void EditarBt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarBt1ActionPerformed
        EditarDadosBool("save_automatico");
    }//GEN-LAST:event_EditarBt1ActionPerformed

    private void EditarBt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarBt2ActionPerformed
        EditarDados("nome");
    }//GEN-LAST:event_EditarBt2ActionPerformed

    private void EditarBt3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarBt3ActionPerformed
        EditarDados("registration");
    }//GEN-LAST:event_EditarBt3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

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
            java.util.logging.Logger.getLogger(GerenciamentoUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GerenciamentoUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GerenciamentoUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GerenciamentoUsuarios.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new GerenciamentoUsuarios().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EditarBt1;
    private javax.swing.JButton EditarBt2;
    private javax.swing.JButton EditarBt3;
    private javax.swing.JButton EditarBt4;
    private static javax.swing.JLabel InfoDB;
    private static javax.swing.JLabel InfoDB1;
    private javax.swing.JLabel NomeTxt;
    private javax.swing.JPanel PainelDetalhes;
    private javax.swing.JButton ResetarTudo;
    private javax.swing.JButton SalvarDados;
    private javax.swing.JTabbedPane TabPainel;
    private static javax.swing.JLabel ValorTxt;
    private javax.swing.JLabel ValorTxt2;
    private javax.swing.JLabel ValorTxt3;
    private javax.swing.JMenuItem exportar;
    private javax.swing.JMenuItem importar;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
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
    private javax.swing.JScrollPane jScrollPane2;
    private static javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
