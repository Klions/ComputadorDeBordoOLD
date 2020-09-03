/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police;

import java.awt.Container;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import org.json.JSONArray;
import org.json.JSONObject;
import police.configs.ConexaoDB;
import police.configs.Usuario;

/**
 *
 * @author John
 */
public class Gerenciamento extends javax.swing.JFrame {

    /**
     * Creates new form Prisoes
     */
    JSONArray CrimesRegistro = new JSONArray();
    JSONArray CategoriasCrimes = new JSONArray();
    
    JSONArray RegistroTabelas = new JSONArray();
    
    
    JSONArray novo_CrimesRegistro = new JSONArray();
    JSONArray novo_CategoriasCrimes = new JSONArray();
    
    JSONArray GetCrimes = new JSONArray();
    int contageGetCrimes = 0;
    
    int TotalIdsTab = 0;
    
    int SalvarTime = 0;
    
    /*JSONArray CategoriasCrimes = new JSONArray();
    JSONArray CrimesRegistro = new JSONArray();*/
    public Gerenciamento() {
        initComponents();
        PegarDB();
        //AdicionarBotoes();
        //jTable1.setTransferHandler(new TableRowTransferHandler(jTable1)); 
        
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
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
            }
        },0,1000);
    }
    
    
    public void PegarDB(){
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
        
        AtualizarJanelas();
        SalvarTime=20;
        /*
        JSONObject getTemporario10 = new JSONObject();
        getTemporario10.put("id", 1);
        getTemporario10.put("texto", "Crimes Leves");
        
        CategoriasCrimes.put(getTemporario10);
        
        getTemporario10 = new JSONObject();
        getTemporario10.put("id", 2);
        getTemporario10.put("texto", "Crimes Pesados");
        CategoriasCrimes.put(getTemporario10);
        
        JSONObject getTemporario2 = new JSONObject();
        getTemporario2.put("categoria", 2);
        getTemporario2.put("id", 2);
        getTemporario2.put("texto", "Tentativa de Homicídio À Autoridade");
        getTemporario2.put("tipo", 1);
        getTemporario2.put("multa", 1000);
        getTemporario2.put("meses", 5);
        CrimesRegistro.put(getTemporario2);
        
        getTemporario2 = new JSONObject();
        getTemporario2.put("categoria", 1);
        getTemporario2.put("id", 5);
        getTemporario2.put("texto", "EITA PORA 2");
        getTemporario2.put("tipo", 1);
        getTemporario2.put("multa", 1000);
        getTemporario2.put("meses", 5);
        CrimesRegistro.put(getTemporario2);
        
        getTemporario2 = new JSONObject();
        getTemporario2.put("categoria", 1);
        getTemporario2.put("id", 4);
        getTemporario2.put("texto", "EITA PORA 3");
        getTemporario2.put("tipo", 1);
        getTemporario2.put("multa", 1000);
        getTemporario2.put("meses", 5);
        CrimesRegistro.put(getTemporario2);
        
        getTemporario2 = new JSONObject();
        getTemporario2.put("categoria", 1);
        getTemporario2.put("id", 5);
        getTemporario2.put("texto", "EITA PORA 4");
        getTemporario2.put("tipo", 1);
        getTemporario2.put("multa", 1000);
        getTemporario2.put("meses", 5);
        CrimesRegistro.put(getTemporario2);
        
        getTemporario2 = new JSONObject();
        getTemporario2.put("categoria", 2);
        getTemporario2.put("id", 5);
        getTemporario2.put("texto", "PESADAAAAAAO");
        getTemporario2.put("tipo", 1);
        getTemporario2.put("multa", 1000);
        getTemporario2.put("meses", 5);
        CrimesRegistro.put(getTemporario2);
        */
    }
    //private javax.swing.JLabel Textos[];
    //private javax.swing.JToggleButton Botoes[];
    
    JScrollPane[] ScrollPainel = new JScrollPane[10];
    JPanel[] PainelBase = new JPanel[10];
    JPanel[][] Painel = new JPanel[10][10];
    JLabel[] Textos = new JLabel[10];
    public JTable[] Tabelas = new JTable[10];
    JButton[] BotoesAdd = new JButton[10];
    JButton[] BotoesRem = new JButton[10];
    
    JTextField[] RenameTabc = new JTextField[10];
    JButton[] RenameTabBtc = new JButton[10];
    
    JButton[] TipoEscolha = new JButton[10];
    
    //JComboBox[] TipoEscolha = new JComboBox[10];
    
    public void AdicionarBotoes(){
        RegistroTabelas = new JSONArray();
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
            
            jTabbedPane1.addTab(o2.getString("nome_categoria"), PainelBase[i2]);
            JSONObject PegarDadosBt = o2;
            PegarDadosBt.put("i", i2);
            RegistroTabelas.put(PegarDadosBt);
            
            Container container = PainelBase[i2];
            container.setLayout(null);
            
            Tabelas[i2].setModel(new javax.swing.table.DefaultTableModel(
                null,
                new String [] {
                    "NOME DO CRIME", "VALOR DA MULTA", "PENA EM MESES", "TIPO"
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
            Tabelas[i2].putClientProperty("terminateEditOnFocusLost", true);
            
            DefaultTableModel modelTable = (DefaultTableModel)Tabelas[i2].getModel();
            for(int i = 0; i < novo_CrimesRegistro.length(); i++){
                JSONObject o = novo_CrimesRegistro.getJSONObject(i);
                if(o.getInt("categoria") == o2.getInt("id")){
                    modelTable.addRow(new Object[]{o.getString("texto"), o.getInt("multa"), o.getInt("meses"), o.getString("tipo")});
                }
            }
            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment( JLabel.CENTER );
            Tabelas[i2].getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
            Tabelas[i2].getColumnModel().getColumn(1).setCellRenderer( centerRenderer );
            Tabelas[i2].getColumnModel().getColumn(2).setCellRenderer( centerRenderer );
            Tabelas[i2].getColumnModel().getColumn(3).setCellRenderer( centerRenderer );
            
            ((DefaultTableCellRenderer)Tabelas[i2].getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
            
            Tabelas[i2].getTableHeader().setReorderingAllowed(false);

            Tabelas[i2].setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            Tabelas[i2].setShowGrid(true);
            Tabelas[i2].setUpdateSelectionOnSort(false);
            final int As = i2;
            DefaultTableModel model = (DefaultTableModel)Tabelas[As].getModel();
            
            Tabelas[i2].addFocusListener(new FocusAdapter() {
                public void focusLost(FocusEvent e) {
                    TableCellEditor tce = Tabelas[As].getCellEditor();
                    if(tce == null) PegarValoresTabela();
                        //tce.stopCellEditing();
                }
            });
            TipoEscolha[i2] = new JButton();
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
                                }
                            }
                            if (pressedKeys.contains(KeyEvent.VK_INSERT)) {
                                //System.out.println("Tabelas[As].getRowCount(): "+Tabelas[As].getRowCount());
                                if(Tabelas[As].getRowCount() < 30){
                                    InfoDB1.setText("Nova linha foi adicionada");
                                    model.addRow(new Object[]{"", 0, 0,"UNICO"});
                                    Tabelas[As].setRowSelectionInterval(model.getRowCount()-1, model.getRowCount()-1);
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
            BotoesRem[i2] = new JButton("REMOVER CATEGORIA");
            
            BotoesAdd[i2].setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
            BotoesRem[i2].setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
            
            BotoesAdd[i2].setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            BotoesRem[i2].setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

            BotoesAdd[i2].addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    model.addRow(new Object[]{"", 0, 0, "UNICO"});
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
                        PegarValoresTabela();
                        AtualizarJanelas();
                    }
                }
            });
            
            RenameTabc[i2] = new JTextField(o2.getString("nome_categoria"));
            RenameTabBtc[i2] = new JButton("ADICIONAR CRIME");
            
            RenameTabBtc[i2].setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
            RenameTabBtc[i2].setText("RENOMEAR CATEGORIA");
            RenameTabBtc[i2].setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            
            RenameTabBtc[i2].addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    String NCategoria = RenameTabc[As].getText();
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
                    }
                }
            });
        
            int index3 = Tabelas[i2].getSelectedRow();
            if(model.getRowCount() > 0){
                if(index3 < 0) Tabelas[i2].setRowSelectionInterval(0, 0);
            }else{
                model.addRow(new Object[]{"", 0, 0, "UNICO"});
                Tabelas[i2].setRowSelectionInterval(0, 0);
            }
            //Tabelas[i2].requestFocus();
            
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
                            .addGap(75, 75, 75)
                            .addComponent(RenameTabc[i2], javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(RenameTabBtc[i2])
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TipoEscolha[i2], javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(BotoesAdd[i2], javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                        .addComponent(BotoesRem[i2], javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            
                        .addComponent(RenameTabBtc[i2], javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(RenameTabc[i2])
                        .addComponent(TipoEscolha[i2], javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            
            if(TotalIdsTab < o2.getInt("id")){
                TotalIdsTab = o2.getInt("id");
            }
        }
    }
    
    public void AtualizarJanelas(){
        int AtualTab = jTabbedPane1.getSelectedIndex();
        while (jTabbedPane1.getTabCount() > 0)
            jTabbedPane1.remove(0);
        AdicionarBotoes();
        if(AtualTab > 0 && AtualTab < jTabbedPane1.getTabCount()) jTabbedPane1.setSelectedIndex(AtualTab);
        
    }
    
    public void PegarValoresTabela(){
        novo_CrimesRegistro = new JSONArray();
        int tCrimes = 0;
        int tCategorias = 0;
        for(int i = 0; i < RegistroTabelas.length(); i++){
            JSONObject obj = RegistroTabelas.getJSONObject(i);
            int ir = obj.getInt("i");
            int row = Tabelas[ir].getRowCount();
            int column = Tabelas[ir].getColumnCount();
            tCategorias++;
            for (int r = 0; r  < row; r++) {
                tCrimes++;
                JSONObject getTemporario2 = new JSONObject();
                for (int c = 0; c  < column; c++) {
                    //System.out.println("Tabelas[ir].getValueAt(r, c): "+Tabelas[ir].getValueAt(r, c));
                    switch(c){
                        case 0:
                            getTemporario2.put("texto", Tabelas[ir].getValueAt(r, c));
                        case 1:
                            getTemporario2.put("multa", Tabelas[ir].getValueAt(r, c));
                        case 2:
                            getTemporario2.put("meses", Tabelas[ir].getValueAt(r, c));
                        case 3:
                            String TipoValor = Tabelas[ir].getValueAt(r, c)+"";
                            if("".equals(TipoValor)) TipoValor = "UNICO";
                            getTemporario2.put("tipo", TipoValor);
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
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        RenameTab = new javax.swing.JTextField();
        RenameTabBt = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        PainelDetalhes = new javax.swing.JPanel();
        SalvarDados = new javax.swing.JButton();
        ResetarTudo = new javax.swing.JButton();
        LabelCategoria = new javax.swing.JLabel();
        AddCategoria = new javax.swing.JButton();
        TxtCategoria = new javax.swing.JTextField();
        InfoDB = new javax.swing.JLabel();
        InfoDB1 = new javax.swing.JLabel();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("GERENCIADOR DE CRIMES");

        jTabbedPane1.setToolTipText("");
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"CRIME 1",  new Integer(1500),  new Integer(11111), null},
                {"CRIME 2",  new Integer(2500),  new Integer(2222222), null},
                {"CRIME 3",  new Integer(5000),  new Integer(333333333), null},
                {"CRIME 4",  new Integer(10000),  new Integer(444444444), null}
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

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jButton4.setText("ADICIONAR CRIME");
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jButton5.setText("DELETAR CATEGORIA");
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

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
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 749, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton5)
                        .addGap(75, 75, 75)
                        .addComponent(RenameTab, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(RenameTab)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", jPanel1);

        PainelDetalhes.setBorder(javax.swing.BorderFactory.createTitledBorder("ADICIONAR CATEGORIA"));

        SalvarDados.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        SalvarDados.setText("SALVAR");
        SalvarDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SalvarDadosActionPerformed(evt);
            }
        });

        ResetarTudo.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        ResetarTudo.setText("RESETAR");
        ResetarTudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetarTudoActionPerformed(evt);
            }
        });

        LabelCategoria.setFont(new java.awt.Font("Arial Unicode MS", 0, 15)); // NOI18N
        LabelCategoria.setText("CATEGORIA:");

        AddCategoria.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        AddCategoria.setText("ADICIONAR");
        AddCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddCategoriaActionPerformed(evt);
            }
        });

        InfoDB1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout PainelDetalhesLayout = new javax.swing.GroupLayout(PainelDetalhes);
        PainelDetalhes.setLayout(PainelDetalhesLayout);
        PainelDetalhesLayout.setHorizontalGroup(
            PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelDetalhesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelDetalhesLayout.createSequentialGroup()
                        .addComponent(LabelCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AddCategoria)
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
                .addGroup(PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(SalvarDados, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ResetarTudo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PainelDetalhesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LabelCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(AddCategoria, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(InfoDB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(InfoDB1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PainelDetalhes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PainelDetalhes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private final Set<Integer> pressedKeys = new HashSet<>();

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
        String NomeCategoria = TxtCategoria.getText();
        if(NomeCategoria.length() > 2 && NomeCategoria.length() < 25){
            PegarValoresTabela();
            JSONObject getTemporario10 = new JSONObject();
            TotalIdsTab++;
            getTemporario10.put("id", TotalIdsTab);
            getTemporario10.put("nome_categoria", NomeCategoria);

            novo_CategoriasCrimes.put(getTemporario10);
            TxtCategoria.setText(null);
            AtualizarJanelas();
        }
    }//GEN-LAST:event_AddCategoriaActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void ResetarTudoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetarTudoActionPerformed
        // VINCULAR COM O BANCO DE DADOS
        //AtualizarJanelas();
        PegarDB();
    }//GEN-LAST:event_ResetarTudoActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void SalvarDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SalvarDadosActionPerformed
        PegarValoresTabela();
        ConexaoDB conexao = new ConexaoDB();
        conexao.SetarCrimesECategoria(novo_CategoriasCrimes.toString(), novo_CrimesRegistro.toString(), contageGetCrimes);
        PegarDB();
        InfoDB1.setText("Salvo com sucesso!");
    }//GEN-LAST:event_SalvarDadosActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

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
    private javax.swing.JButton AddCategoria;
    private javax.swing.JLabel InfoDB;
    private javax.swing.JLabel InfoDB1;
    private javax.swing.JLabel LabelCategoria;
    private javax.swing.JPanel PainelDetalhes;
    private javax.swing.JTextField RenameTab;
    private javax.swing.JButton RenameTabBt;
    private javax.swing.JButton ResetarTudo;
    private javax.swing.JButton SalvarDados;
    private javax.swing.JTextField TxtCategoria;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
