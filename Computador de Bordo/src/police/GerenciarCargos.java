/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;
import police.configs.ConexaoDB;
import police.configs.GetImages;
import police.configs.SNWindows;

/**
 *
 * @author John
 */
public class GerenciarCargos extends javax.swing.JFrame {

    /**
     * Creates new form GerenciarCargos
     */
    public GerenciarCargos() {
        initComponents();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        tbl_cargos.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        
        ((DefaultTableCellRenderer)tbl_cargos.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        
        getContentPane().setBackground(new java.awt.Color(13, 32, 64));
        this.setLocationRelativeTo(null);
        ResetarCampos();
        VerificarPanel();
        CarregarDB();
        /*if(isAdmin()){
            perm_admin.setVisible(true);
        }else{
            perm_admin.setVisible(false);
        }*/
        
        if(!InicializadorMain.ModoOffline){
            this.setIconImage(new ImageIcon(GetImages.LogoCB).getImage());
        }
        pack();
    }
    
    boolean EditandoInfo = false;
    public void EditandoInfo(boolean ativar){
        //i_salvar.setVisible(ativar);
        //salvar_tudo.setVisible(!ativar);
        EditandoInfo = ativar;
        if(EditandoInfo){
            i_salvar.setText("SALVAR CARGO");
        }else{
            i_salvar.setText("SALVAR ALTERAÇÕES");
        }
        pack();
    }

    public void MoverLinha(boolean cima){
        int index = tbl_cargos.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel)tbl_cargos.getModel();
        if(index >= 0){
            if(cima){
                if(index > 0){
                    model.moveRow(index, index, index - 1);
                    tbl_cargos.setRowSelectionInterval(index, index-1);
                }
            }else{
                if(index < model.getRowCount() - 1){
                    model.moveRow(index, index, index + 1);
                    tbl_cargos.setRowSelectionInterval(index, index+1);
                }
            }
        }
        getLinhaSelected();
    }
    
    public JSONArray tabela_cargos = new JSONArray();
    int contagadd = 0;
    public void AdicionarLinha(){
        //int index = jTable1.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel)tbl_cargos.getModel();
        contagadd++;
        //int Tot = model.getRowCount()+contagadd;
        String nome_cargo = "Cargo "+contagadd;
        model.addRow(new Object[]{nome_cargo});
        
        JSONObject getTemporario2 = new JSONObject();
        getTemporario2.put("nome_cargo", nome_cargo);
        //getTemporario2.put("perm_admin", 0);
        getTemporario2.put("perm_gerenciarcargos", 0);
        getTemporario2.put("perm_promover", 0);
        getTemporario2.put("perm_recrutar", 0);
        getTemporario2.put("perm_limparficha", 0);
        
        tabela_cargos.put(getTemporario2);
        
        if(model.getRowCount() > 0){
            tbl_cargos.setRowSelectionInterval(0, model.getRowCount()-1);
            AttInfo();
        }
    }
    
    private boolean isAdmin(){
        return false;
    }
    
    
    int LinhaSelecionado = -1;
    public void RemoverLinha(){
        int index = tbl_cargos.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel)tbl_cargos.getModel();
        if(model.getRowCount() > 0)model.removeRow( index );
        if(index < model.getRowCount() - 1 || index == model.getRowCount() - 1){
            tbl_cargos.setRowSelectionInterval(index, index);
        }else if(index == 0){
            //tbl_cargos.setRowSelectionInterval(index+1, index+1);
        }else{
            tbl_cargos.setRowSelectionInterval(index-1, index-1);
        }
        LinhaSelecionado = -1;
        AttInfo();
    }
    public void VerificarPanel(){
        DefaultTableModel model = (DefaultTableModel)tbl_cargos.getModel();
        
        int index = tbl_cargos.getSelectedRow();
        if(index <= 0){
            bt_up.setEnabled(false);
        }else{
            bt_up.setEnabled(true);
        }
        if(index == model.getRowCount() - 1){
            bt_down.setEnabled(false);
        }else{
            bt_down.setEnabled(true);
        }
        
        if(model.getRowCount() <= 0){
            bt_rem.setEnabled(false);
        }else{
            bt_rem.setEnabled(true);
        }
        msgSucess(false, "");
    }
    
    public void CarregarDados(){
        tabela_cargos = new JSONArray();
        
        //JSONObject getTemporario2 = new JSONObject();
        //getTemporario2.put("user_id", id_user);
        
        //tabela_cargos.put(getTemporario2);
    }
    
    public void getLinhaSelected(){
        VerificarPanel();
        if(tbl_cargos.getSelectedRow() == LinhaSelecionado)return;
        LinhaSelecionado = tbl_cargos.getSelectedRow();
    }
    
    boolean Editado = false;
    
    private boolean SalvarSair(){
        Object[] options = { "Salvar", "Cancelar" }; 
        int Escolha=JOptionPane.showOptionDialog(this,
                "Você efetuou alterações, deseja salvar antes de sair?", 
                "Salvar antes de sair", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.WARNING_MESSAGE, 
                null, 
                options, 
                options[0]);
        return Escolha==JOptionPane.YES_OPTION;
    }
    public void AttInfo(){
        getLinhaSelected();
        if(tbl_cargos.getSelectedRow() >= 0){
            String nome_do_cargo = tbl_cargos.getValueAt(tbl_cargos.getSelectedRow(), 0).toString();
            boolean achou = false;
            for(int i = 0; i < tabela_cargos.length(); i++){
                JSONObject obj = tabela_cargos.getJSONObject(i);
                if(nome_do_cargo.equals(obj.getString("nome_cargo"))){
                    EditandoInfo(true);
                    achou = true;
                    i_nome.setText(nome_do_cargo);
                    /*
                    if(obj.getInt("perm_admin") == 1){
                        perm_admin.setSelected(true);
                    }else{
                        perm_admin.setSelected(false);
                    }*/
                    if(obj.getInt("perm_gerenciarcargos") == 1){
                        perm_gerenciarcargos.setSelected(true);
                    }else{
                        perm_gerenciarcargos.setSelected(false);
                    }
                    if(obj.getInt("perm_promover") == 1){
                        perm_promover.setSelected(true);
                    }else{
                        perm_promover.setSelected(false);
                    }
                    if(obj.getInt("perm_recrutar") == 1){
                        perm_recrutar.setSelected(true);
                    }else{
                        perm_recrutar.setSelected(false);
                    }
                    if(obj.getInt("perm_limparficha") == 1){
                        perm_limparficha.setSelected(true);
                    }else{
                        perm_limparficha.setSelected(false);
                    }
                }
            }
            AtivarCampos(achou);
        }else{
            ResetarCampos();
        }
    }
    
    public void CarregarDB(){
        DefaultTableModel model = (DefaultTableModel) tbl_cargos.getModel();
        model.setRowCount(0);
        tabela_cargos = SNWindows.getConfig("hierarquia");
        for(int i = 0; i < tabela_cargos.length(); i++){
            JSONObject obj = tabela_cargos.getJSONObject(i);
            model.addRow(new Object[]{obj.getString("nome_cargo")});
        }
    }
    void SalvarEdInfo(){
        int index = tbl_cargos.getSelectedRow();
        if(index >= 0){
            String nome_do_cargo = tbl_cargos.getValueAt(index, 0).toString();

            for(int i = 0; i < tabela_cargos.length(); i++){
                JSONObject obj = tabela_cargos.getJSONObject(i);
                if(nome_do_cargo.equals(obj.getString("nome_cargo"))){
                    String TextoCampo = i_nome.getText();
                    String SucessTxt = "DIGITE O NOME DO CARGO!";
                    if(!"".equals(TextoCampo) && TextoCampo.length() > 0){
                        if(TextoCampo.toLowerCase().equals(obj.getString("nome_cargo").toLowerCase())){
                            SucessTxt = "";
                        }else if(VerificarNomeCargo(TextoCampo)){
                            SucessTxt = "POSSUI UM CARGO COM ESTE NOME!";
                        }else{
                            obj.put("nome_cargo", TextoCampo);
                            tbl_cargos.setValueAt(TextoCampo, index, 0);
                            SucessTxt = "";
                        }
                    }

                    int itrue = 0;
                    /*if(perm_admin.isSelected()) {
                        itrue = 1;
                    }
                    obj.put("perm_admin", itrue);*/

                    itrue = 0;
                    if(perm_gerenciarcargos.isSelected()) {
                        itrue = 1;
                    }
                    obj.put("perm_gerenciarcargos", itrue);

                    itrue = 0;
                    if(perm_promover.isSelected()) {
                        itrue = 1;
                    }
                    obj.put("perm_promover", itrue);

                    itrue = 0;
                    if(perm_recrutar.isSelected()) {
                        itrue = 1;
                    }
                    obj.put("perm_recrutar", itrue);

                    itrue = 0;
                    if(perm_limparficha.isSelected()) {
                        itrue = 1;
                    }
                    obj.put("perm_limparficha", itrue);
                    if("".equals(SucessTxt)){
                        msgSucess(true, "");
                    }else{
                        msgSucess(false, SucessTxt);
                        i_nome.requestFocus();
                    }
                    EditandoInfo(false);
                }
            }
        }else{
            ResetarCampos();
        }
    }
    public void SalvarDado(){
        if(EditandoInfo){
            SalvarEdInfo();
        }else{
            PegarValoresTabela();
            CarregarDB();
        }
    }
    
    private boolean VerificarNomeCargo(String Nome){
        int rows = tbl_cargos.getRowCount();
        for (int r = 0; r  < rows; r++) {
            String nome_do_cargo = tbl_cargos.getValueAt(r, 0)+"";
            if(nome_do_cargo.toLowerCase().equals(Nome.toLowerCase())) return true;
        }
        return false;
    }
    
    public void msgSucess(boolean sucesso, String Txt){
        if(sucesso){
            txt_sucess.setText("SALVO COM SUCESSO!");
            txt_sucess.setForeground(new java.awt.Color(0,153,51));
        }else{
            txt_sucess.setText(Txt);
            txt_sucess.setForeground(new java.awt.Color(255,51,51));
        }
    }
    
    public void AtivarCampos(boolean ativar){
        i_nome.setEnabled(ativar);
        //perm_admin.setEnabled(ativar);
        perm_gerenciarcargos.setEnabled(ativar);
        perm_promover.setEnabled(ativar);
        perm_recrutar.setEnabled(ativar);
        perm_limparficha.setEnabled(ativar);
        i_salvar.setEnabled(ativar);
        i_nome.requestFocus();
    }
    
    public void ResetarCampos(){
        AtivarCampos(false);
        i_nome.setText("SELECIONE ALGUM CARGO");
        //perm_admin.setSelected(false);
        perm_gerenciarcargos.setSelected(false);
        perm_promover.setSelected(false);
        perm_recrutar.setSelected(false);
        perm_limparficha.setSelected(false);
        EditandoInfo(false);
    }
    
    public void PegarValoresTabela(){
        JSONArray tabela_cargos_temp = new JSONArray();
        int rows = tbl_cargos.getRowCount();
        for (int r = 0; r  < rows; r++) {
            String nome_do_cargo = tbl_cargos.getValueAt(r, 0)+"";
            for(int i = 0; i < tabela_cargos.length(); i++){
                JSONObject obj = tabela_cargos.getJSONObject(i);
                if(nome_do_cargo.equals(obj.getString("nome_cargo"))){
                    obj.put("id", r+1);
                    tabela_cargos_temp.put(obj);
                }
            }
        }
        tabela_cargos = tabela_cargos_temp;
        System.out.println(tabela_cargos.toString());
        
        ConexaoDB conexao = new ConexaoDB();
        conexao.SetarConfig("hierarquia", tabela_cargos.toString());
        InicializadorMain.AttDBSConfigs();
        //SNWindows.getCargoUser();
    }
    
    void editou(boolean editar){
        Editado = editar;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_cargos = new javax.swing.JTable();
        info = new javax.swing.JPanel();
        info_texto = new javax.swing.JLabel();
        i_nome = new javax.swing.JTextField();
        i_perms = new javax.swing.JPanel();
        perm_gerenciarcargos = new javax.swing.JCheckBox();
        perm_promover = new javax.swing.JCheckBox();
        perm_recrutar = new javax.swing.JCheckBox();
        perm_limparficha = new javax.swing.JCheckBox();
        txt_sucess = new javax.swing.JLabel();
        bt_up = new javax.swing.JButton();
        bt_down = new javax.swing.JButton();
        bt_rem = new javax.swing.JButton();
        bt_add = new javax.swing.JButton();
        i_salvar = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GERENCIAR CARGOS");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("GERENCIAR CARGOS");

        tbl_cargos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbl_cargos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NOME DO CARGO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_cargos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbl_cargos.setShowGrid(true);
        tbl_cargos.setUpdateSelectionOnSort(false);
        tbl_cargos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbl_cargosMouseReleased(evt);
            }
        });
        tbl_cargos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_cargosKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_cargos);

        info.setOpaque(false);

        info_texto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        info_texto.setForeground(new java.awt.Color(255, 255, 255));
        info_texto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        info_texto.setText("INFORMAÇÕES DO CARGO");

        i_nome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        i_nome.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        i_nome.setText("NOME DO CARGO");
        i_nome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                i_nomeKeyReleased(evt);
            }
        });

        i_perms.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true), "PERMISSÕES", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        i_perms.setOpaque(false);

        perm_gerenciarcargos.setForeground(new java.awt.Color(255, 255, 255));
        perm_gerenciarcargos.setText("GERENCIAR CARGOS");
        perm_gerenciarcargos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        perm_gerenciarcargos.setOpaque(false);
        perm_gerenciarcargos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                perm_promoverMouseClicked(evt);
            }
        });

        perm_promover.setForeground(new java.awt.Color(255, 255, 255));
        perm_promover.setText("PROMOVER / REBAIXAR");
        perm_promover.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        perm_promover.setOpaque(false);
        perm_promover.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                perm_promoverMouseClicked(evt);
            }
        });

        perm_recrutar.setForeground(new java.awt.Color(255, 255, 255));
        perm_recrutar.setText("RECRUTAR");
        perm_recrutar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        perm_recrutar.setOpaque(false);
        perm_recrutar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                perm_promoverMouseClicked(evt);
            }
        });

        perm_limparficha.setForeground(new java.awt.Color(255, 255, 255));
        perm_limparficha.setText("LIMPAR FICHA");
        perm_limparficha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        perm_limparficha.setOpaque(false);
        perm_limparficha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                perm_promoverMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout i_permsLayout = new javax.swing.GroupLayout(i_perms);
        i_perms.setLayout(i_permsLayout);
        i_permsLayout.setHorizontalGroup(
            i_permsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(i_permsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(i_permsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(perm_promover, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(perm_recrutar, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(perm_limparficha, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                    .addComponent(perm_gerenciarcargos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        i_permsLayout.setVerticalGroup(
            i_permsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(i_permsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(perm_gerenciarcargos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(perm_promover)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(perm_recrutar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(perm_limparficha, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txt_sucess.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txt_sucess.setForeground(new java.awt.Color(255, 51, 51));
        txt_sucess.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_sucess.setText("SALVO COM SUCESSO!");

        javax.swing.GroupLayout infoLayout = new javax.swing.GroupLayout(info);
        info.setLayout(infoLayout);
        infoLayout.setHorizontalGroup(
            infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(i_nome)
                    .addComponent(info_texto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(i_perms, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_sucess, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        infoLayout.setVerticalGroup(
            infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(info_texto, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(i_nome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(i_perms, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_sucess, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bt_up.setBackground(new java.awt.Color(255, 255, 255));
        bt_up.setText("▲");
        bt_up.setToolTipText("Mover linha para cima");
        bt_up.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_upActionPerformed(evt);
            }
        });

        bt_down.setBackground(new java.awt.Color(255, 255, 255));
        bt_down.setText("▼");
        bt_down.setToolTipText("Mover linha para baixo");
        bt_down.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_downActionPerformed(evt);
            }
        });

        bt_rem.setBackground(new java.awt.Color(255, 255, 255));
        bt_rem.setText("╳");
        bt_rem.setToolTipText("Remover linha");
        bt_rem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_remActionPerformed(evt);
            }
        });

        bt_add.setBackground(new java.awt.Color(255, 255, 255));
        bt_add.setText("✚");
        bt_add.setToolTipText("Adicionar nova linha");
        bt_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_addActionPerformed(evt);
            }
        });

        i_salvar.setBackground(new java.awt.Color(255, 255, 255));
        i_salvar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        i_salvar.setText("SALVAR");
        i_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                i_salvarActionPerformed(evt);
            }
        });

        jMenu2.setText("FECHAR");

        jMenuItem2.setText("FECHAR JANELA");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

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
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(bt_add)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt_rem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt_up)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bt_down))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(info, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(i_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(info, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_up)
                    .addComponent(bt_down)
                    .addComponent(bt_rem)
                    .addComponent(bt_add)
                    .addComponent(i_salvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_upActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_upActionPerformed
        MoverLinha(true);
        tbl_cargos.requestFocus();
    }//GEN-LAST:event_bt_upActionPerformed

    private void bt_remActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_remActionPerformed
        RemoverLinha();
        tbl_cargos.requestFocus();
    }//GEN-LAST:event_bt_remActionPerformed

    private void bt_downActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_downActionPerformed
        MoverLinha(false);
        tbl_cargos.requestFocus();
    }//GEN-LAST:event_bt_downActionPerformed

    private void bt_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_addActionPerformed
        AdicionarLinha();
        tbl_cargos.requestFocus();
    }//GEN-LAST:event_bt_addActionPerformed

    private void tbl_cargosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_cargosKeyReleased
        AttInfo();
        System.out.println("tbl_cargosKeyReleased");
    }//GEN-LAST:event_tbl_cargosKeyReleased

    private void tbl_cargosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_cargosMouseReleased
        AttInfo();
        System.out.println("tbl_cargosMouseReleased");
    }//GEN-LAST:event_tbl_cargosMouseReleased

    private void i_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_i_salvarActionPerformed
        SalvarDado();
    }//GEN-LAST:event_i_salvarActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if(Editado){
            if(SalvarSair()){
                SalvarEdInfo();
                PegarValoresTabela();
                new GerenciarServer().setVisible(true);
                this.dispose();
            }
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        new Sobre().setVisible(true);
        //this.dispose();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void i_nomeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_i_nomeKeyReleased
        editou(true);
    }//GEN-LAST:event_i_nomeKeyReleased

    private void perm_promoverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_perm_promoverMouseClicked
        editou(true);
    }//GEN-LAST:event_perm_promoverMouseClicked

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
            java.util.logging.Logger.getLogger(GerenciarCargos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GerenciarCargos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GerenciarCargos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GerenciarCargos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GerenciarCargos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_add;
    private javax.swing.JButton bt_down;
    private javax.swing.JButton bt_rem;
    private javax.swing.JButton bt_up;
    private javax.swing.JTextField i_nome;
    private javax.swing.JPanel i_perms;
    private javax.swing.JButton i_salvar;
    private javax.swing.JPanel info;
    private javax.swing.JLabel info_texto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JCheckBox perm_gerenciarcargos;
    private javax.swing.JCheckBox perm_limparficha;
    private javax.swing.JCheckBox perm_promover;
    private javax.swing.JCheckBox perm_recrutar;
    private javax.swing.JTable tbl_cargos;
    private javax.swing.JLabel txt_sucess;
    // End of variables declaration//GEN-END:variables
}
