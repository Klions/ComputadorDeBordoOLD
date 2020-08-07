/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import static java.lang.Integer.parseInt;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import org.json.JSONArray;
import org.json.JSONObject;
import police.configs.Config;
import police.configs.Usuario;


/**
 *
 * @author John
 * 
 * 
 * CamposTexto
 * BtMais
 * BtMenos
 * BtZerar
 * QntCampo
 * ValorMulta
 * NomeCrime
 */
public class VerPrisoes extends javax.swing.JFrame {

    /**
     * Creates new form Prender
     */
    public JSONArray usuariosDBarray = new JSONArray();
    public JSONArray prisoesDBarray = new JSONArray();
    public JSONArray hierarquiaDBarray = new JSONArray();
    public JSONArray procuradosDBarray = new JSONArray();
    int LinhaSelecionado;
    Usuario usuarios = new Usuario();
    InicializadorMain policia = new InicializadorMain();
    Config config = new Config();
    public int verpresoes;
    
    public VerPrisoes() {
        
        initComponents();
        usuariosDBarray = InicializadorMain.usuariosDBarray;
        prisoesDBarray = InicializadorMain.prisoesDBarray;
        hierarquiaDBarray = InicializadorMain.hierarquiaDBarray;
        procuradosDBarray = InicializadorMain.procuradosDBarray;
        
        /*
        usuariosDBarray = usuarios.AttDBUsuarios();
        prisoesDBarray = usuarios.AttDBPrisoes();
        hierarquiaDBarray = usuarios.AttDBHierarquia();
        procuradosDBarray = usuarios.AttDBProcurados();
        */
        
        //copiado.setVisible(false);
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(config.img_CBIcone)));

        id.requestFocus();

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        
        for(int i = 0; i < 5; i++){
            tabela.getColumnModel().getColumn(i).setPreferredWidth(20);
            tabela.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
            tabela.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
        }
        crimes.getColumnModel().getColumn(0).setPreferredWidth(20);
        crimes.getColumnModel().getColumn(0).setHeaderRenderer(centerRenderer);
        crimes.getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
        getContentPane().setBackground(new java.awt.Color(13, 32, 64));

        jPanel1.setBackground(new java.awt.Color(13, 32, 64));
        jPanel2.setBackground(new java.awt.Color(13, 32, 64));
        jPanel3.setBackground(new java.awt.Color(13, 32, 64));
        jPanel4.setBackground(new java.awt.Color(13, 32, 64)); //50, 82, 114
        jPanel6.setBackground(new java.awt.Color(13, 32, 64));  //30, 62, 94
        jPanel7.setBackground(new java.awt.Color(13, 32, 64));
        jPanel2.setVisible(false);
        pack();
        TabelaAtt();
    }
    public void PesquisarT(){
        DefaultTableModel table = (DefaultTableModel)tabela.getModel();
        String search2 = pesquisa.getText().toLowerCase();
        String search1 = id.getText().toLowerCase();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(table);
        tabela.setRowSorter(tr);
        
        List<RowFilter<Object,Object>> filters = new ArrayList<RowFilter<Object,Object>>(2);
        String regex = String.format("^%s$", search1);
        if(search1.length() <= 0){
            filters.add(RowFilter.regexFilter("", 0));
            nomes.setText("S/ INFO");
            rgs.setText("S/ INFO");
            tels.setText("S/ INFO");
        }else{
            filters.add(RowFilter.regexFilter(regex, 0));
        }
        
        filters.add(RowFilter.regexFilter(search2, 1));
        tr.setRowFilter(RowFilter.andFilter(filters));
        /*tr.setRowFilter(RowFilter.regexFilter("(?i)" +search, 1));
        tr.setRowFilter(RowFilter.regexFilter("(?i)" +search2, 0));*/
        
        
        boolean contare=false;
        for(int i = 0; i < usuariosDBarray.length(); i++){
            if(search1.length() <= 0)return;
            JSONObject o = usuariosDBarray.getJSONObject(i);
            int paser = Integer.parseInt(search1);
            if(paser == o.getInt("id_usuario")){
                contare=true;
                String identidade = o.getString("registration");
                String telefone = o.getString("phone");
                String nomer="Sem";
                String sobrenomer="Registro";
                if(o.getString("nome").length() > 0)nomer=o.getString("nome");
                if(o.getString("sobrenome").length() > 0)sobrenomer=o.getString("sobrenome");
                String nome = nomer+" "+sobrenomer;
                
                nomes.setText(nome);
                rgs.setText(identidade);
                tels.setText(telefone);
                //copiar.setEnabled(true);
            }
        }
        if(!contare){
            nomes.setText("S/ INFO");
            rgs.setText("S/ INFO");
            tels.setText("S/ INFO");
        }
    }
    
    public boolean ResetarCampos(){
        LinhaSelecionado=-1;
        id.setText("");
        pesquisa.setText("");
        copiar.setText("COPIAR");
        PesquisarT();
        AttInfo();
        return true;
    }
    
    public void AttInfo(){
        if(tabela.getSelectedRow() >= 0){
            if(tabela.getSelectedRow() == LinhaSelecionado)return;
            LinhaSelecionado = tabela.getSelectedRow();
            jPanel2.setVisible(true);
            int protocol = Integer.parseInt(tabela.getValueAt(tabela.getSelectedRow(), 1).toString());
            String preso_nome = "NADA";
            String preso_identidade = "NADA";
            String preso_telefone = "NADA";
            String polic_nome = "NADA";
            String polic_identidade = "NADA";
            String polic_telefone = "NADA";
            int policial_id = 0;
            int preso_id = 0;
            String justificado = "NADA";
            int multa=0;
            int meses=0;
            String limpo = "";
            fichalimpa.setText(" ");
            String pagou = "";
            for(int i = 0; i < prisoesDBarray.length(); i++){
                JSONObject o = prisoesDBarray.getJSONObject(i);
                //System.out.println("protocol: "+protocol+" // o.getInt(protocolo): "+o.getInt("protocolo"));
                if(protocol == o.getInt("protocolo")){
                    //System.out.println("FOIIIIIIIIIIII");
                    policial_id = o.getInt("id_prendeu");
                    preso_id = o.getInt("id_usuario"); 
                    justificado = o.getString("justificado"); 
                    multa = o.getInt("multas");
                    meses = o.getInt("meses"); 
                    limpo = o.getString("limpo");
                    pagou = o.getString("pagou");
                    //copiar.setEnabled(true);
                }
            }
            
            int nivel_procurado = 0;
            if(preso_id==0){
                for(int i = 0; i < procuradosDBarray.length(); i++){
                    JSONObject o = procuradosDBarray.getJSONObject(i);
                    if(protocol == o.getInt("protocolo")){
                        policial_id = o.getInt("procurou_id");
                        preso_id = o.getInt("id_usuario"); 
                        justificado = o.getString("justificado"); 
                        multa = o.getInt("multas");
                        meses = o.getInt("meses"); 
                        limpo = "";
                        pagou = o.getString("pagou");
                        //copiar.setEnabled(true);
                        if(o.getInt("nivel_procurado") > nivel_procurado) nivel_procurado = o.getInt("nivel_procurado");
                    }
                }
            }
            
            for(int us = 0; us < usuariosDBarray.length(); us++){
                JSONObject user = usuariosDBarray.getJSONObject(us);
                if(preso_id==user.getInt("id_usuario")){

                    preso_nome = user.getString("nome")+" "+user.getString("sobrenome");
                    preso_identidade = user.getString("registration");
                    preso_telefone = user.getString("phone");
                    //System.out.println("preso_nome: "+preso_nome+" // preso_identidade: "+preso_identidade);
                }
                if(policial_id==user.getInt("id_usuario")){
                    polic_nome = user.getString("nome")+" "+user.getString("sobrenome");
                    polic_identidade = user.getString("registration");
                    polic_telefone = user.getString("phone");
                }
            }
            
            int pter=99;
            for(int i2 = 0; i2 < hierarquiaDBarray.length(); i2++){
                JSONObject ohier = hierarquiaDBarray.getJSONObject(i2);
                //System.out.println("hierarquiaDBarray: "+ohier.toString()+" // ");
                if(policial_id==ohier.getInt("id_usuario")){
                    pter = ohier.getInt("cargo");
                }
            }
            if(limpo.length()>0)fichalimpa.setText("FICHA LIMPA NO PROTOCOLO: "+limpo);
            if(pagou.length()>1){
                if(limpo.length()>1)fichalimpa.setText(fichalimpa.getText()+" / ");
                fichalimpa.setText("EXECUTOU DIA: "+pagou);
            }
            titulo_protocol.setText("DETALHES DO CRIME DE PROTOCOLO: "+protocol);
            nome_preso.setText(preso_nome);
            rg_preso.setText("ID: "+preso_id+" / RG: "+preso_identidade);
            
            jLabel23.setText(usuarios.Patentes(pter).toUpperCase());
            nome_pol.setText(polic_nome);
            rg_pol.setText("ID: "+policial_id+" / RG: "+polic_identidade);
            
            jLabel14.setText("Meses: "+meses+" / Multa: $"+String.format("%,d", multa));
            String[] arrOfStr = justificado.split("//"); 

            /*for (String a : arrOfStr) 
                System.out.println(a); */
            DefaultTableModel model = (DefaultTableModel) crimes.getModel();
            model.setRowCount(0);
            /*String[] stringss = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            //jList1.setModel(new javax.swing.AbstractListModel<String>() {
            
            public int getSize() { return arrOfStr.length; }
            public String getElementAt(int i) { return arrOfStr[i]; }
            });*/
            for(int i3 = 0; i3 < arrOfStr.length; i3++){
                model.addRow(new Object[]{arrOfStr[i3]});
            }
            
            String NivelPr = "Sem Informação";
            switch(nivel_procurado) {
                case 1:
                    NivelPr = "Desarmado";
                    break;
                case 2:
                    NivelPr = "Portando Arma Branca";
                    break;
                case 3:
                    NivelPr = "Portando Arma Leve";
                    break;
                case 4:
                    NivelPr = "Portando Arma Pesada";
                    break;
            }
            if(nivel_procurado > 0){
                model.addRow(new Object[]{""});
                model.addRow(new Object[]{NivelPr});
                NivelPr = "";
                switch(nivel_procurado) {
                    case 1:
                        NivelPr = "de Baixo Risco";
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
                nivel_procuradoTxt.setText("ESTE INDIVÍDUO É "+NivelPr.toUpperCase());
            }else{
                nivel_procuradoTxt.setText("");
            }
            //jLabel15.setText(justificado);
        }else{
            jPanel2.setVisible(false);
            LinhaSelecionado=-1;
        }
        pack();
    }
    
     public void TabelaAtt(){
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        model.setRowCount(0);
        pesquisa.setText("");
        nivel_procuradoTxt.setText("");
        //System.out.println("usuariosDBarray: "+usuariosDBarray.toString()+" ?°/ ");
        switch(verpresoes) {
            case 0:
                TituloJanela.setText("REGISTRO DE PRISÕES");
                break;
            case 1:
                TituloJanela.setText("REGISTRO DE MULTAS");
                break;
            case 2:
                TituloJanela.setText("REGISTRO DE PROCURADOS");
                break;
        }
        if(verpresoes >= 0 && verpresoes <= 1){
            for(int i2 = 0; i2 < prisoesDBarray.length(); i2++){
                JSONObject ohier = prisoesDBarray.getJSONObject(i2);
                //System.out.println("hierarquiaDBarray: "+ohier.toString()+" // ");
                int ide = ohier.getInt("id");
                int tornz = ohier.getInt("tornz");
                int meses = ohier.getInt("meses");
                int multas = ohier.getInt("multas");
                int id_usuario = ohier.getInt("id_usuario");
                int id_prendeu = ohier.getInt("id_prendeu");
                int protocolo = ohier.getInt("protocolo");
                long data = ohier.getLong("data");
                String pagou = ohier.getString("pagou");
                String motivo = ohier.getString("motivo");
                Timestamp timestamp = new Timestamp(data);
                String date = new SimpleDateFormat("dd/MM/yy hh:mm").format(timestamp.getTime());
                String TornzTEXT="inválido";
                if(meses > 0 && tornz > 0){
                    TornzTEXT=meses+" preso / "+tornz+" tornozeleira";
                }else if(meses > 0){
                    TornzTEXT=meses+" preso";
                }else if(tornz > 0){
                    TornzTEXT=tornz+" tornozeleira";
                }else if(multas > 0){
                    TornzTEXT="somente multa";
                }
                if(verpresoes == 0){
                    if((meses > 0 || tornz > 0) && parseInt(pagou) > 0){
                        model.addRow(new Object[]{id_usuario, protocolo, TornzTEXT, "$ "+String.format("%,d", multas), date});
                    }
                }else if(verpresoes == 1){
                    if((meses <= 0 && tornz <= 0) && multas > 0 && parseInt(pagou) > 0){
                         model.addRow(new Object[]{id_usuario, protocolo, TornzTEXT, "$ "+String.format("%,d", multas), date});
                    }
                }
            }
        }else if(verpresoes == 2){
            int nivel_procurado = 0;
            for(int i2 = 0; i2 < procuradosDBarray.length(); i2++){
                JSONObject ohier = procuradosDBarray.getJSONObject(i2);
                //System.out.println("hierarquiaDBarray: "+ohier.toString()+" // ");
                int ide = ohier.getInt("id");
                int tornz = ohier.getInt("tornz");
                int meses = ohier.getInt("meses");
                int multas = ohier.getInt("multas");
                int id_usuario = ohier.getInt("id_usuario");
                int id_prendeu = ohier.getInt("procurou_id");
                int protocolo = ohier.getInt("protocolo");
                long data = ohier.getLong("data");
                String pagou = ohier.getString("pagou");
                String motivo = ohier.getString("motivo");
                Timestamp timestamp = new Timestamp(data);
                String date = new SimpleDateFormat("dd/MM/yy hh:mm").format(timestamp.getTime());
                String TornzTEXT="inválido";
                if(meses > 0 && tornz > 0){
                    TornzTEXT=meses+" preso / "+tornz+" tornozeleira";
                }else if(meses > 0){
                    TornzTEXT=meses+" preso";
                }else if(tornz > 0){
                    TornzTEXT=tornz+" tornozeleira";
                }else if(multas > 0){
                    TornzTEXT="somente multa";
                }
                if((meses > 0 || tornz > 0 || multas > 0) && parseInt(pagou) == 0){
                    model.addRow(new Object[]{id_usuario, protocolo, TornzTEXT, "$ "+String.format("%,d", multas), date});
                    
                    if(ohier.getInt("nivel_procurado") > nivel_procurado) nivel_procurado = ohier.getInt("nivel_procurado");
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

        TituloJanela = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        id = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        rgs = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tels = new javax.swing.JLabel();
        nomes = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        resetar = new javax.swing.JButton();
        copiar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        pesquisa = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        titulo_protocol = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        nome_preso = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        rg_preso = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel7 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        crimes = new javax.swing.JTable();
        fichalimpa = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        nome_pol = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        rg_pol = new javax.swing.JLabel();
        nivel_procuradoTxt = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("VER PRISÕES");
        setName("PRISAO"); // NOI18N
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);

        TituloJanela.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        TituloJanela.setForeground(new java.awt.Color(255, 255, 255));
        TituloJanela.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TituloJanela.setText("REGISTROS DE PRISÕES");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PESQUISAR REGISTRO PELO PASSAPORTE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        id.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        id.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                idKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                idKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                idKeyTyped(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("REGISTRO");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("ID:");

        rgs.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rgs.setForeground(new java.awt.Color(255, 255, 255));
        rgs.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rgs.setText("S/ INFO");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("TELEFONE");

        tels.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tels.setForeground(new java.awt.Color(255, 255, 255));
        tels.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tels.setText("S/ INFO");

        nomes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        nomes.setForeground(new java.awt.Color(255, 255, 255));
        nomes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomes.setText("S/ INFO");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("NOME");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(id)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nomes, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(rgs, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(tels, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nomes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tels, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 4, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rgs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        resetar.setBackground(new java.awt.Color(255, 255, 255));
        resetar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        resetar.setText("RESETAR TUDO");
        resetar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetarActionPerformed(evt);
            }
        });

        copiar.setBackground(new java.awt.Color(255, 255, 255));
        copiar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        copiar.setText("COPIAR");
        copiar.setEnabled(false);
        copiar.setNextFocusableComponent(resetar);
        copiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(resetar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(copiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(copiar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resetar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("PRISÕES E MULTAS");

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Passaporte", "Protocolo", "Pena", "Multa", "Data"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabela.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabela.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tabelaFocusGained(evt);
            }
        });
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tabelaMousePressed(evt);
            }
        });
        tabela.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tabelaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabelaKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tabela);

        pesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pesquisaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pesquisaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pesquisaKeyTyped(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("PROTOCOLO:");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        titulo_protocol.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        titulo_protocol.setForeground(new java.awt.Color(255, 255, 255));
        titulo_protocol.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titulo_protocol.setText("DETALHES DO CRIME DE PROTOCOLO:");

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        nome_preso.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nome_preso.setForeground(new java.awt.Color(255, 255, 255));
        nome_preso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nome_preso.setText("CARREGANDO...");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("INDIVÍDUO");

        rg_preso.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rg_preso.setForeground(new java.awt.Color(255, 255, 255));
        rg_preso.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rg_preso.setText("9999");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(nome_preso, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(125, 125, 125)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE))
                            .addComponent(jSeparator4)
                            .addComponent(rg_preso, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nome_preso)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rg_preso)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("CRIMES COMETIDOS");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("MESES: 999 meses");

        crimes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DESCRIÇÃO DE CRIMES"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(crimes);

        fichalimpa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        fichalimpa.setForeground(new java.awt.Color(153, 153, 0));
        fichalimpa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fichalimpa.setText(" ");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(fichalimpa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fichalimpa)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        nome_pol.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        nome_pol.setForeground(new java.awt.Color(255, 255, 255));
        nome_pol.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nome_pol.setText("CARREGANDO...");

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("POLICIAL");

        rg_pol.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rg_pol.setForeground(new java.awt.Color(255, 255, 255));
        rg_pol.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        rg_pol.setText("9999");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator3)
                    .addComponent(rg_pol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nome_pol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nome_pol)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rg_pol)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(titulo_protocol, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titulo_protocol)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        nivel_procuradoTxt.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        nivel_procuradoTxt.setForeground(new java.awt.Color(255, 51, 51));
        nivel_procuradoTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

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
                    .addComponent(nivel_procuradoTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TituloJanela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TituloJanela)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(nivel_procuradoTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void idKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idKeyTyped
        //AtualizarCrimes();
    }//GEN-LAST:event_idKeyTyped

    private void idKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idKeyReleased
       //AtualizarCrimes();
    }//GEN-LAST:event_idKeyReleased

    private void copiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copiarActionPerformed
        String ide = id.getText();
        if(ide.length() < 1){
            showMessageDialog(null, "Está faltando Nome ou ID do indivíduo!!");
            id.requestFocus();
        }else{
        
            //String myString = discord.getText();
            /*StringSelection stringSelection = new StringSelection(myString);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);*/
            copiar.setText("COPIADO!!!");
            //copiado.setVisible(true);
        }
    }//GEN-LAST:event_copiarActionPerformed

    private void resetarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetarActionPerformed
       ResetarCampos();
    }//GEN-LAST:event_resetarActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        //new Painel().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        new Sobre().setVisible(true);
        //this.dispose();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void idKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            PesquisarT();
        }
    }//GEN-LAST:event_idKeyPressed

    private void tabelaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaKeyReleased
        AttInfo();
    }//GEN-LAST:event_tabelaKeyReleased

    private void tabelaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabelaKeyPressed
        AttInfo();
    }//GEN-LAST:event_tabelaKeyPressed

    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        AttInfo();
    }//GEN-LAST:event_tabelaMouseClicked

    private void tabelaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMousePressed
        AttInfo();
    }//GEN-LAST:event_tabelaMousePressed

    private void pesquisaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pesquisaKeyTyped
        PesquisarT();
    }//GEN-LAST:event_pesquisaKeyTyped

    private void pesquisaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pesquisaKeyPressed
        PesquisarT();
    }//GEN-LAST:event_pesquisaKeyPressed

    private void pesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pesquisaKeyReleased
        PesquisarT();
    }//GEN-LAST:event_pesquisaKeyReleased

    private void tabelaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tabelaFocusGained
        AttInfo();
    }//GEN-LAST:event_tabelaFocusGained

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
            java.util.logging.Logger.getLogger(VerPrisoes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VerPrisoes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VerPrisoes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VerPrisoes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VerPrisoes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel TituloJanela;
    private javax.swing.JButton copiar;
    private javax.swing.JTable crimes;
    private javax.swing.JLabel fichalimpa;
    public javax.swing.JTextField id;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel nivel_procuradoTxt;
    private javax.swing.JLabel nome_pol;
    private javax.swing.JLabel nome_preso;
    private javax.swing.JLabel nomes;
    private javax.swing.JTextField pesquisa;
    public javax.swing.JButton resetar;
    private javax.swing.JLabel rg_pol;
    private javax.swing.JLabel rg_preso;
    private javax.swing.JLabel rgs;
    private javax.swing.JTable tabela;
    private javax.swing.JLabel tels;
    private javax.swing.JLabel titulo_protocol;
    // End of variables declaration//GEN-END:variables
}
