/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police;

import java.awt.Container;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import org.json.JSONArray;
import org.json.JSONObject;
import police.configs.Usuario;

/**
 *
 * @author John
 */
public class Prisoes extends javax.swing.JFrame {

    /**
     * Creates new form Prisoes
     */
    JSONArray CrimesRegistro = new JSONArray();
    JSONArray CategoriasCrimes = new JSONArray();
    
    JSONArray RegistroBotoes = new JSONArray();
    JSONArray RegistroInputs = new JSONArray();
    
    JSONArray GetCrimes = new JSONArray();
    
    JSONArray usuariosDBarray = new JSONArray();
    JSONArray discordDBarray = new JSONArray();
    JSONArray prisoesDBarray = new JSONArray();
    JSONArray blacklistDBarray = new JSONArray();
    JSONArray procuradosDBarray = new JSONArray();
    
    String CrimesDiscordFormat = "";
    
    /*JSONArray CategoriasCrimes = new JSONArray();
    JSONArray CrimesRegistro = new JSONArray();*/
    public Prisoes() {
        initComponents();
        DetalhesPainel.setVisible(false);
        this.revalidate();
        this.repaint();
        this.pack();
        SetarBotoes();
        
        usuariosDBarray = InicializadorMain.usuariosDBarray;
        discordDBarray = InicializadorMain.discordDBarray;
        
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                Date date = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String dataFormatada = simpleDateFormat.format(date);
                TimeAgora.setText(dataFormatada);
            }
        },0,1000);
    }
    
    
    public void SetarBotoes(){
        Usuario usuarios = new Usuario();
        GetCrimes = usuarios.CrimesServerID();
        for(int i2 = 0; i2 < GetCrimes.length(); i2++){
            
            JSONObject o2 = GetCrimes.getJSONObject(i2);
            CategoriasCrimes = new JSONArray(o2.getString("categorias"));
            CrimesRegistro = new JSONArray(o2.getString("crimes"));
        }
        AtualizarJanelas();
    }
    //private javax.swing.JLabel Textos[];
    //private javax.swing.JToggleButton Botoes[];
    int QntCategorias = 10;
    int QntCrimes = 100;
    
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
            ScrollPainel[i2] = new JScrollPane();
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

            ScrollPainel[i2].setViewportView(PainelBase[i2]);
            jTabbedPane1.addTab(o2.getString("nome_categoria"), ScrollPainel[i2]);
            
            
            int PadraoX = 15;
            int EspacamentoX = 20;
            int EspacamentoY = 10;
            int LimiteQuadro = 4;
            int ContagemLimite = 0;

            int TamanhoPainel = (jPanel1.getWidth()/LimiteQuadro);
            int Linha = 0;
            
            Container container = PainelBase[i2];
            container.setLayout(null);
            
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

                        Botoes[i2][i].addItemListener(new ItemListener() {
                            public void itemStateChanged(ItemEvent ev) {
                               AttCrimes();
                            }
                        });
                        if(o.getString("texto").length() > 27){
                            Botoes[i2][i].setFont(new java.awt.Font("Tahoma", 0, 8));
                        }else if(o.getString("texto").length() > 24){
                            Botoes[i2][i].setFont(new java.awt.Font("Tahoma", 0, 9));
                        }else if(o.getString("texto").length() > 20){
                            Botoes[i2][i].setFont(new java.awt.Font("Tahoma", 0, 10));
                        }else if(o.getString("texto").length() > 15){
                            Botoes[i2][i].setFont(new java.awt.Font("Tahoma", 0, 11));
                        }else if(o.getString("texto").length() > 9){
                            Botoes[i2][i].setFont(new java.awt.Font("Tahoma", 0, 12));
                        }
                        Botoes[i2][i].setBounds(3, 3, 214, 39);
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
                        
                        if(o.getString("texto").length() > 27){
                            InputText[i2][i].setFont(new java.awt.Font("Tahoma", 0, 8));
                        }else if(o.getString("texto").length() > 24){
                            InputText[i2][i].setFont(new java.awt.Font("Tahoma", 0, 9));
                        }else if(o.getString("texto").length() > 20){
                            InputText[i2][i].setFont(new java.awt.Font("Tahoma", 0, 10));
                        }else if(o.getString("texto").length() > 15){
                            InputText[i2][i].setFont(new java.awt.Font("Tahoma", 0, 11));
                        }else{
                            InputText[i2][i].setFont(new java.awt.Font("Tahoma", 0, 12));
                        }
                        
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
                                    
                                    if(o.getString("texto").length() > 27){
                                        InputText[As2][As].setFont(new java.awt.Font("Tahoma", 0, 8));
                                    }else if(o.getString("texto").length() > 24){
                                        InputText[As2][As].setFont(new java.awt.Font("Tahoma", 0, 9));
                                    }else if(o.getString("texto").length() > 20){
                                        InputText[As2][As].setFont(new java.awt.Font("Tahoma", 0, 10));
                                    }else if(o.getString("texto").length() > 15){
                                        InputText[As2][As].setFont(new java.awt.Font("Tahoma", 0, 11));
                                    }else{
                                        InputText[As2][As].setFont(new java.awt.Font("Tahoma", 0, 12));
                                    }
                                }
                            }
                        });
                        container2.add( InputText[i2][i] );
                    }
                    
                    
                    
                    ContagemLimite++;
                    if(ContagemLimite>=LimiteQuadro){
                        ContagemLimite=0;
                        Linha++;
                    }
                       
                }
            }
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
        CrimesDiscordFormat = "";
        for(int i = 0; i < RegistroBotoes.length(); i++){
            JSONObject obj = RegistroBotoes.getJSONObject(i);
            int ir1 = obj.getInt("i1");
            int ir2 = obj.getInt("i2");
            if(Botoes[ir1][ir2].isSelected()){
                //System.out.println("Nome do crime: "+obj.getString("texto"));
                MultaTotal+=obj.getInt("multa");
                MesesTotal+=obj.getInt("meses");
                if(ContageCrimes == 0){
                    CrimesExtenso = obj.getString("texto");
                }else{
                    CrimesExtenso += " + "+obj.getString("texto");
                }
                CrimesDiscordFormat="\n* "+obj.getString("texto");
                ContageCrimes++;
            }
        }
        for(int i = 0; i < RegistroInputs.length(); i++){
            JSONObject obj = RegistroInputs.getJSONObject(i);
            int ir1 = obj.getInt("i1");
            int ir2 = obj.getInt("i2");
            String TextoInput = InputText[ir1][ir2].getText();
            if(!TextoInput.isEmpty() && !TextoInput.equals(obj.getString("texto"))){
                int ValorInput = Integer.parseInt(TextoInput);
                //System.out.println("TextoInput: "+Integer.parseInt(TextoInput));
                if(ValorInput > 0){
                    MultaTotal+=(obj.getInt("multa")*ValorInput);
                    MesesTotal+=(obj.getInt("meses")*ValorInput);
                    if(ContageCrimes == 0){
                        CrimesExtenso = obj.getString("texto")+" (x"+ValorInput+")";
                    }else{
                        CrimesExtenso += " + "+obj.getString("texto")+" (x"+ValorInput+")";
                    }
                    CrimesDiscordFormat="\n* "+obj.getString("texto")+" (x"+ValorInput+")";
                    ContageCrimes++;
                }
            }
        }
        if(MultaTotal>0) StrMeses = "R$"+String.format("%,d", MultaTotal);
        if(MesesTotal>0) StrMultas = MesesTotal+" meses";
        info_Pena1S.setText(StrMeses);
        info_Pena2S.setText(StrMultas);
        
        info_CrimesS.setText(CrimesExtenso);
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
    
    public int ProcuradoPorID(int user_id){
        int nivel_procurado = 0;
        for(int i3 = 0; i3 < procuradosDBarray.length(); i3++){
            JSONObject oproc = procuradosDBarray.getJSONObject(i3);
            int meseer = oproc.getInt("meses");
            int multaar = oproc.getInt("multas");
            int pagou = oproc.getInt("pagou");
            int nv_procurado = oproc.getInt("nivel_procurado");
            if(user_id==oproc.getInt("id_usuario")){
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
        switch(Campo) {
            case 0:
                txtID.setText(null);
                txtNome.setText(null);
                txtPlaca.setText(null);
                break;
            case 1:
                txtNome.setText(null);
                txtPlaca.setText(null);
                break;
            case 2:
                txtID.setText(null);
                txtPlaca.setText(null);
                break;
            case 3:
                txtID.setText(null);
                txtNome.setText(null);
                break;
        }
    }
    
    public void Alerta(String Titulo, String Mensagem){
        showMessageDialog(null,Titulo, Mensagem,JOptionPane.PLAIN_MESSAGE);
    }
    
    public void PegarUsuario(){
        ResetarCrimes();
        JSONObject Usuario = new JSONObject();
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
                    Alerta("O nome '"+TxtPegarBusca+"' é muito pequeno para procurarmos! Coloque mais de 3 caracteres", "Ocorreu algum erro");
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
                        Alerta("O registro '"+TxtPegarBusca+"' é muito pequeno para procurarmos! Coloque mais de 5 caracteres", "Ocorreu algum erro");
                        return;
                    }
                    Usuario = UsuarioPorRegistration(TxtPegarBusca);
                    if(!Usuario.has("id_usuario")){
                        Alerta("A placa '"+TxtPegarBusca+"' não foi encontrado no banco da cidade!", "Ocorreu algum erro");
                        return;
                    }
                }else{
                    Alerta("Necessário digitar algo para buscarmos!", "Ocorreu algum erro");
                    return;
                }
            }
        }
        PesquisarPainel.setVisible(false);
        DetalhesPainel.setVisible(true);
        this.revalidate();
        this.repaint();
        this.pack();
        
        int pass = Usuario.getInt("id_usuario");
        //PassaPreso=Usuario.getInt("id_usuario");
        //String discord = o.getString("discord");
        String nome = Usuario.getString("nome")+" "+Usuario.getString("sobrenome");
        if(" ".equals(nome))nome="Sem Registro";
        String passaporte = Usuario.getString("id_usuario");
        String identidade = Usuario.getString("registration");
        
        //SETAGENS DE TEXTOS INFO
        des_NomeS.setText(nome);
        des_IdadeS.setText(Usuario.getString("age")+" anos");
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

        int vzespreso = 0;
        int vzesmulta = 0;
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
        if(PlayerProcurado > 0){
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
    public void ResetarCrimes(){
        ResetarBotoes();
    }
    
    public void ResetarTudo(){
        ResetarCrimes();
        DigitandoCampo(0);
        
        PesquisarPainel.setVisible(true);
        DetalhesPainel.setVisible(false);
        this.revalidate();
        this.repaint();
        this.pack();
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
        jPanel1 = new javax.swing.JPanel();
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
        PesquisarPainel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtPlaca = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
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
        SalvarBt1 = new javax.swing.JButton();
        CopiarDiscordBt = new javax.swing.JButton();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("REGISTRO DE PRISÕES");

        jTabbedPane1.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 923, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 386, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel1);

        jTabbedPane1.addTab("Crimes 1", jScrollPane1);

        PainelDetalhes.setBorder(javax.swing.BorderFactory.createTitledBorder("INFORMAÇÕES PENAL"));

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

        PesquisarPainel.setBorder(javax.swing.BorderFactory.createTitledBorder("PESQUISAR INDIVÍDUO"));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("PASSAPORTE:");

        txtID.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIDKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIDKeyTyped(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("OU");

        txtNome.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtNome.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomeKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("NOME:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("PLACA DO VEÍCULO:");

        txtPlaca.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        txtPlaca.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPlaca.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtPlacaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPlacaKeyTyped(evt);
            }
        });

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("OU");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/police/imagens/searchbt.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PesquisarPainelLayout = new javax.swing.GroupLayout(PesquisarPainel);
        PesquisarPainel.setLayout(PesquisarPainelLayout);
        PesquisarPainelLayout.setHorizontalGroup(
            PesquisarPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PesquisarPainelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addContainerGap())
        );
        PesquisarPainelLayout.setVerticalGroup(
            PesquisarPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PesquisarPainelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PesquisarPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PesquisarPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPlaca, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(des_Passagem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(des_Procurado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(des_ProcuradoS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(des_PassagemS, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(verpreso)
                    .addComponent(procuradoBt))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(des_Multa, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(des_MultaS, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(vermulta)
                        .addGap(0, 0, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        SalvarBt.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        SalvarBt.setText("REGISTRAR PRISÃO");

        ResetarBt.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        ResetarBt.setText("RESETAR");
        ResetarBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetarBtActionPerformed(evt);
            }
        });

        SalvarBt1.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        SalvarBt1.setText("REGISTRAR PROCURADO");

        CopiarDiscordBt.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        CopiarDiscordBt.setText("COPIAR DISCORD");
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
                .addComponent(SalvarBt1, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(SalvarBt1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CopiarDiscordBt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 947, Short.MAX_VALUE)
                    .addComponent(DetalhesPainel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PesquisarPainel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PainelDetalhes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PesquisarPainel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DetalhesPainel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PainelDetalhes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void vermultaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vermultaActionPerformed
        //AbrirMenuVerPrisoes(1);
    }//GEN-LAST:event_vermultaActionPerformed

    private void verpresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verpresoActionPerformed
        //AbrirMenuVerPrisoes(0);
    }//GEN-LAST:event_verpresoActionPerformed

    private void procuradoBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_procuradoBtActionPerformed
        //AbrirMenuVerPrisoes(2);
    }//GEN-LAST:event_procuradoBtActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        PegarUsuario();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void ResetarBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetarBtActionPerformed
        ResetarTudo();
    }//GEN-LAST:event_ResetarBtActionPerformed

    private void txtIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDKeyTyped
        DigitandoCampo(1);
    }//GEN-LAST:event_txtIDKeyTyped

    private void txtNomeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyTyped
        DigitandoCampo(2);
    }//GEN-LAST:event_txtNomeKeyTyped

    private void txtPlacaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPlacaKeyTyped
        DigitandoCampo(3);
    }//GEN-LAST:event_txtPlacaKeyTyped

    private void txtIDKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIDKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            DigitandoCampo(1);
            PegarUsuario();
        }
    }//GEN-LAST:event_txtIDKeyPressed

    private void txtNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            DigitandoCampo(2);
            PegarUsuario();
        }
    }//GEN-LAST:event_txtNomeKeyPressed

    private void txtPlacaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPlacaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            DigitandoCampo(3);
            PegarUsuario();
        }
    }//GEN-LAST:event_txtPlacaKeyPressed

    private void CopiarDiscordBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CopiarDiscordBtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CopiarDiscordBtActionPerformed

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
    private javax.swing.JPanel PesquisarPainel;
    private javax.swing.JButton ResetarBt;
    private javax.swing.JButton SalvarBt;
    private javax.swing.JButton SalvarBt1;
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
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JButton procuradoBt;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPlaca;
    private javax.swing.JButton vermulta;
    private javax.swing.JButton verpreso;
    // End of variables declaration//GEN-END:variables
}
