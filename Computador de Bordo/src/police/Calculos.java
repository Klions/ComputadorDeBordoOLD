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
import javax.swing.JButton;
import javax.swing.JTextField;


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
public class Calculos extends javax.swing.JFrame {

    int tamanhototal=8;
    /**
     * Creates new form Prender
     */
    public Calculos() {
        
        initComponents();
        //copiado.setVisible(false);
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagens/mclost_1.png")));
        
        AtualizarCrimes();
        inputnumero15.requestFocus();
        
        getContentPane().setBackground(new java.awt.Color(51, 51, 51));
 
        
        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel3.setBackground(new java.awt.Color(51, 51, 51));
        jPanel4.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        
        for (int i=1; i<=tamanhototal; i++) {
            BtMais(i).setBackground(new java.awt.Color(255, 255, 255));
            BtMenos(i).setBackground(new java.awt.Color(255, 255, 255));
            BtZerar(i).setBackground(new java.awt.Color(255, 255, 255));
        }
        
    }
    
    public JTextField CamposTexto(int valor){
        switch (valor) {
            case 1:
                return numero1;
            case 2:
                return numero2;
            case 3:
                return numero3;
            case 4:
                return numero4;
            case 5:
                return numero5;
            case 6:
                return numero6;
            case 7:
                return numero7;
            case 8:
                return numero8;
            default:
                return jTextField1;
        }
    }
    
    public JTextField PrecoTexto(int valor){
        switch (valor) {
            case 1:
                return inputnumero1;
            case 2:
                return inputnumero2;
            case 3:
                return inputnumero3;
            case 4:
                return inputnumero4;
            case 5:
                return inputnumero5;
            case 6:
                return inputnumero6;
            case 7:
                return inputnumero7;
            default:
                return jTextField1;
        }
    }
    
    public JButton BtMais(int valor){
        switch (valor) {
            case 1:
                return mais1;
            case 2:
                return mais2;
            case 3:
                return mais3;
            case 4:
                return mais4;
            case 5:
                return mais5;
            case 6:
                return mais6;
            case 7:
                return mais7;
            case 8:
                return mais8;
            default:
                return mais1;
        }
    }
    
    public JButton BtMenos(int valor){
        switch (valor) {
            case 1:
                return menos1;
            case 2:
                return menos2;
            case 3:
                return menos3;
            case 4:
                return menos4;
            case 5:
                return menos5;
            case 6:
                return menos6;
            case 7:
                return menos7;
            case 8:
                return menos8;
            default:
                return menos1;
        }
    }
    
    public JButton BtZerar(int valor){
        switch (valor) {
            case 1:
                return zerar1;
            case 2:
                return zerar2;
            case 3:
                return zerar3;
            case 4:
                return zerar4;
            case 5:
                return zerar5;
            case 6:
                return zerar6;
            case 7:
                return zerar7;
            case 8:
                return zerar8;
                
            default:
                return zerar1;
        }
    }
    
    public int QntCampo(int valor){
        switch (valor) {
            
            default:
                return 99;
        }
    }
    
    public int ValorVezes(int valor){
        switch (valor) {
            case 1:
                return 20000;
            case 2:
                return 25000;
            case 3:
                return 50000;
            case 4:
                return 65000;
            case 5:
                return 90000;
            case 6:
                return 150000;
            case 7:
                return 200000;
                
            default:
                return 0;
        }
    }
    
    public String NomeArmas(int valor){
        switch (valor) {
            case 1:
                return "HK P7M10";
            case 2:
                return "FIVE SEVEN";
            case 3:
                return "MAGNUM 44";
            case 4:
                return "UZI";
            case 5:
                return "THOMPSOM";
            case 6:
                return "AK-103";
            case 7:
                return "M4";
                
            default:
                return "NENHUMA";
        }
    }
    public int QntVezes(int valor){
        switch (valor) {
            default:
                return 99;
        }
    }
        
    
    public int BotaoGeral(String opcao, int campo){ //JTextField campo    
        String TextoDoCampo = CamposTexto(campo).getText();
        int valorc = Integer.parseInt(TextoDoCampo);
        int totalv = valorc;
        if(null == opcao){
            totalv=0;
        }else switch (opcao) {
            case "add":
                totalv++;
                break;
            case "rem":
                totalv--;
                break;
            default:
                totalv=0;
                break;
        }
        
        //System.out.println("Teste Funcionou");
        CamposTexto(campo).setText(totalv+"");
        AtualizarBotoes(totalv, QntCampo(campo), campo);
        return 1;
    }
    
    
    public int AtualizarBotoes(int totalv, int total, int valorcampo){
        if(totalv >= total){
            BtMais(valorcampo).setEnabled(false);
            BtMenos(valorcampo).setEnabled(true);
            BtZerar(valorcampo).setEnabled(true);
        }else if(totalv <= 0){
            BtMais(valorcampo).setEnabled(true);
            BtMenos(valorcampo).setEnabled(false);
            BtZerar(valorcampo).setEnabled(false);
        }else{
            BtMais(valorcampo).setEnabled(true);
            BtMenos(valorcampo).setEnabled(true);
            BtZerar(valorcampo).setEnabled(true);
        }
        System.out.printf("valorcampo: "+valorcampo);
        AtualizarCrimes();
        //copiado.setVisible(false);
        return 1;
    }
    
    public boolean AtualizarCrimes(){
        copiar.setText("COPIAR");
        int valortotal=0;
        int valorarmas=0;
        String Armas=""; 
        String ArmasF="";
        int contagemarmas=0;
        
        boolean temarmas=false;
        int contager = 0;
        for (int i=1; i<=tamanhototal; i++) {
            //Crimes cometidos
            String TextoDoCampo = CamposTexto(i).getText();
            int valorc = 0;
            int valorlocal = 0;
            
            
            if(TextoDoCampo.length() <= 0){
                valorc=0;
            }else if(TextoDoCampo.length() >= 3){
                valorc=99;
                
            }else{
                valorc = Integer.parseInt(TextoDoCampo);
            }
            CamposTexto(i).setText(valorc+"");
            
            
            if(valorc > 0){
                int ValorVezes = ValorVezes(i);
                if(ValorVezes > 0){
                    if(contager>0)Armas+=" & ";
                    if(contager==0)ArmasF+="ARMAS:\n";
                    valorarmas+=(ValorVezes*valorc);
                    valorlocal=ValorVezes*valorc;
                    contager++;
                    Armas+=valorc+" "+NomeArmas(i);
                    ArmasF+="- "+valorc+" "+NomeArmas(i)+"\n";
                    contagemarmas+=valorc;
                }
            }
            valortotal=valorarmas;
            PrecoTexto(i).setText(""+valorlocal);
            //System.out.printf("PrecoTexto: "+PrecoTexto(i).getText());
        }
        if("".equals(Armas)){
            Armas="Nenhuma selecionada.";
        }else{
            temarmas=true;
        }
        //ADICIONAL
        int vadicionar1=Integer.parseInt(inputnumero15.getText());
        int vadicionar2=Integer.parseInt(inputnumero16.getText());
        
        //DESCONTO
        int vdescontar1=Integer.parseInt(inputnumero17.getText());
        int vdescontar2=Integer.parseInt(inputnumero18.getText());
        
        //CONTA A FAZER
        int addporce=(vadicionar2*valortotal)/100;
        addporce=vadicionar1+addporce;
        valortotal+=addporce;
        int addporce2=(vdescontar2*valortotal)/100;
        addporce2=vdescontar1+addporce2;
        
        int descontconta=addporce2;
        
        valortotal-=addporce2;
        
        int vdividir=Integer.parseInt(numero8.getText());
        int contadivid=0;
        if(vdividir>1)contadivid=valortotal/vdividir;
        vdesconto.setText("VALOR DESCONTO: $ "+dinheiro(descontconta));
        if(vdividir>1){
            vrestante.setText("VALOR DIVIDO POR "+vdividir+": $ "+dinheiro(contadivid));
        }else{
            vrestante.setText("VALOR INTEIRO: $ "+dinheiro(valortotal));
        }
        
        varmas.setText("VALOR ARMAS: $ "+dinheiro(valorarmas));
        vtotal.setText("VALOR TOTAL: $ "+dinheiro(valortotal));
        
        jLabel11.setText(Armas+"");
        
        String TextDiscord = "```\n";
        if(contagemarmas>0)TextDiscord+="Qnt de armas: "+contagemarmas+"\n";
        if(vdividir>1)TextDiscord+="Valor divido por "+vdividir+": $"+dinheiro(contadivid)+"\n";
        if(descontconta>0){
            TextDiscord+="Desconto: $"+dinheiro(descontconta);
            if(vdescontar2!=0)TextDiscord+=" ("+vdescontar2+"%)";
            TextDiscord+="\n";
        }
        if(valortotal!=0){
            TextDiscord+="Valor Final: $"+dinheiro(valortotal)+"\n";
            copiar.setEnabled(true);
        }else{
            copiar.setEnabled(false);
        }
        if(temarmas)TextDiscord+=ArmasF;
        TextDiscord += "```";
        
        discord.setText(TextDiscord);
        
        return true;
    }
    
    public String dinheiro(int valor){
        return String.format("%,d", valor);
    }
    public boolean ResetarCampos(){
        for (int i=1; i<=tamanhototal; i++) {
            //Crimes cometidos
            CamposTexto(i).setText("0");
            BtMais(i).setEnabled(true);
            BtMenos(i).setEnabled(false);
            BtZerar(i).setEnabled(false);
            
            PrecoTexto(i).setText("0");
        }
        inputnumero15.setText("0");
        inputnumero16.setText("0");
        inputnumero17.setText("0");
        inputnumero18.setText("0");
        
        AtualizarCrimes();
        return true;
    }
    
    public boolean Numberos(int total, JTextField textfielde){
        if(textfielde.getText().length() <= 0){
            textfielde.setText("0");
        }else if(textfielde.getText().length() >= total){
            textfielde.setText(removeLastCharacter(textfielde.getText()));
        }else{
            textfielde.setText(Integer.parseInt(textfielde.getText())+"");
        }
        
        
        return true;
    }
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
        AtualizarCrimes();
        return true;
    }
    
    public static String removeLastCharacter(String str) {
       String strNew = str.substring(0, str.length()-1);
       return strNew;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
      @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        resetar = new javax.swing.JButton();
        copiar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        registrar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        vdesconto = new javax.swing.JLabel();
        varmas = new javax.swing.JLabel();
        vtotal = new javax.swing.JLabel();
        vrestante = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        txtA1 = new javax.swing.JLabel();
        menos1 = new javax.swing.JButton();
        numero1 = new javax.swing.JTextField();
        mais1 = new javax.swing.JButton();
        zerar1 = new javax.swing.JButton();
        cifrao1 = new javax.swing.JLabel();
        inputnumero1 = new javax.swing.JTextField();
        cifrao2 = new javax.swing.JLabel();
        inputnumero2 = new javax.swing.JTextField();
        zerar2 = new javax.swing.JButton();
        mais2 = new javax.swing.JButton();
        menos2 = new javax.swing.JButton();
        numero2 = new javax.swing.JTextField();
        txtA2 = new javax.swing.JLabel();
        cifrao3 = new javax.swing.JLabel();
        inputnumero3 = new javax.swing.JTextField();
        zerar3 = new javax.swing.JButton();
        mais3 = new javax.swing.JButton();
        menos3 = new javax.swing.JButton();
        numero3 = new javax.swing.JTextField();
        txtA3 = new javax.swing.JLabel();
        cifrao4 = new javax.swing.JLabel();
        inputnumero4 = new javax.swing.JTextField();
        zerar4 = new javax.swing.JButton();
        mais4 = new javax.swing.JButton();
        menos4 = new javax.swing.JButton();
        numero4 = new javax.swing.JTextField();
        txtA4 = new javax.swing.JLabel();
        txtA5 = new javax.swing.JLabel();
        menos5 = new javax.swing.JButton();
        numero5 = new javax.swing.JTextField();
        mais5 = new javax.swing.JButton();
        zerar5 = new javax.swing.JButton();
        cifrao5 = new javax.swing.JLabel();
        inputnumero5 = new javax.swing.JTextField();
        txtA6 = new javax.swing.JLabel();
        menos6 = new javax.swing.JButton();
        numero6 = new javax.swing.JTextField();
        mais6 = new javax.swing.JButton();
        zerar6 = new javax.swing.JButton();
        cifrao6 = new javax.swing.JLabel();
        inputnumero6 = new javax.swing.JTextField();
        txtA7 = new javax.swing.JLabel();
        menos7 = new javax.swing.JButton();
        numero7 = new javax.swing.JTextField();
        mais7 = new javax.swing.JButton();
        zerar7 = new javax.swing.JButton();
        cifrao7 = new javax.swing.JLabel();
        inputnumero7 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        inputnumero15 = new javax.swing.JTextField();
        cifrao16 = new javax.swing.JLabel();
        cifrao17 = new javax.swing.JLabel();
        cifrao18 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        zerar8 = new javax.swing.JButton();
        menos8 = new javax.swing.JButton();
        mais8 = new javax.swing.JButton();
        numero8 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        inputnumero17 = new javax.swing.JTextField();
        cifrao19 = new javax.swing.JLabel();
        cifrao20 = new javax.swing.JLabel();
        cifrao21 = new javax.swing.JLabel();
        inputnumero16 = new javax.swing.JTextField();
        inputnumero18 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        discord = new java.awt.TextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CALCULOS THE LOST MC");
        setName("PRISAO LSPD ORIGIN"); // NOI18N
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CALCULOS THE LOST MC");

        resetar.setBackground(new java.awt.Color(255, 255, 255));
        resetar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        resetar.setText("RESETAR TUDO");
        resetar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetarActionPerformed(evt);
            }
        });

        copiar.setBackground(new java.awt.Color(255, 255, 255));
        copiar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        copiar.setText("COPIAR");
        copiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copiarActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("ARMAS:");

        registrar.setBackground(new java.awt.Color(255, 255, 255));
        registrar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        registrar.setText("GUARDAR REGISTRO");
        registrar.setEnabled(false);
        registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registrarActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel11.setText("CARREGANDO...");

        vdesconto.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        vdesconto.setForeground(new java.awt.Color(255, 102, 51));
        vdesconto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vdesconto.setText("VALOR DESCONTO: $0");

        varmas.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        varmas.setForeground(new java.awt.Color(204, 153, 255));
        varmas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        varmas.setText("VALOR ARMAS: $0");

        vtotal.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        vtotal.setForeground(new java.awt.Color(255, 204, 102));
        vtotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vtotal.setText("VALOR TOTAL: $0");

        vrestante.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        vrestante.setForeground(new java.awt.Color(204, 204, 204));
        vrestante.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vrestante.setText("VALOR RESTANTE: $0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator4)
                    .addComponent(registrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(vdesconto, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                            .addComponent(vrestante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(copiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(vtotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(varmas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(resetar, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vdesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(varmas, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(vrestante, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(vtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(copiar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        txtA1.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtA1.setForeground(new java.awt.Color(255, 255, 255));
        txtA1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtA1.setText("HK P7M10");
        txtA1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        menos1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos1.setText("<");
        menos1.setEnabled(false);
        menos1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos1.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos1.setMaximumSize(new java.awt.Dimension(21, 21));
        menos1.setMinimumSize(new java.awt.Dimension(21, 21));
        menos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos1ActionPerformed(evt);
            }
        });

        numero1.setEditable(false);
        numero1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero1.setText("0");
        numero1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero1ActionPerformed(evt);
            }
        });

        mais1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais1.setText(">");
        mais1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais1.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais1.setMaximumSize(new java.awt.Dimension(21, 21));
        mais1.setMinimumSize(new java.awt.Dimension(21, 21));
        mais1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais1ActionPerformed(evt);
            }
        });

        zerar1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar1.setText("0");
        zerar1.setEnabled(false);
        zerar1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar1.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar1.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar1.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar1ActionPerformed(evt);
            }
        });

        cifrao1.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cifrao1.setForeground(new java.awt.Color(255, 255, 255));
        cifrao1.setText("$");

        cifrao2.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cifrao2.setForeground(new java.awt.Color(255, 255, 255));
        cifrao2.setText("$");

        zerar2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar2.setText("0");
        zerar2.setEnabled(false);
        zerar2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar2.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar2.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar2.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar2ActionPerformed(evt);
            }
        });

        mais2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais2.setText(">");
        mais2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais2.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais2.setMaximumSize(new java.awt.Dimension(21, 21));
        mais2.setMinimumSize(new java.awt.Dimension(21, 21));
        mais2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais2ActionPerformed(evt);
            }
        });

        menos2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos2.setText("<");
        menos2.setEnabled(false);
        menos2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos2.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos2.setMaximumSize(new java.awt.Dimension(21, 21));
        menos2.setMinimumSize(new java.awt.Dimension(21, 21));
        menos2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos2ActionPerformed(evt);
            }
        });

        numero2.setEditable(false);
        numero2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero2.setText("0");
        numero2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero2ActionPerformed(evt);
            }
        });

        txtA2.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtA2.setForeground(new java.awt.Color(255, 255, 255));
        txtA2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtA2.setText("FIVE SEVEN");
        txtA2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        cifrao3.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cifrao3.setForeground(new java.awt.Color(255, 255, 255));
        cifrao3.setText("$");

        zerar3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar3.setText("0");
        zerar3.setEnabled(false);
        zerar3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar3.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar3.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar3.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar3ActionPerformed(evt);
            }
        });

        mais3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais3.setText(">");
        mais3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais3.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais3.setMaximumSize(new java.awt.Dimension(21, 21));
        mais3.setMinimumSize(new java.awt.Dimension(21, 21));
        mais3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais3ActionPerformed(evt);
            }
        });

        menos3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos3.setText("<");
        menos3.setEnabled(false);
        menos3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos3.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos3.setMaximumSize(new java.awt.Dimension(21, 21));
        menos3.setMinimumSize(new java.awt.Dimension(21, 21));
        menos3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos3ActionPerformed(evt);
            }
        });

        numero3.setEditable(false);
        numero3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero3.setText("0");
        numero3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero3ActionPerformed(evt);
            }
        });

        txtA3.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtA3.setForeground(new java.awt.Color(255, 255, 255));
        txtA3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtA3.setText("MAGNUM 44");
        txtA3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        cifrao4.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cifrao4.setForeground(new java.awt.Color(255, 255, 255));
        cifrao4.setText("$");

        zerar4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar4.setText("0");
        zerar4.setEnabled(false);
        zerar4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar4.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar4.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar4.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar4ActionPerformed(evt);
            }
        });

        mais4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais4.setText(">");
        mais4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais4.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais4.setMaximumSize(new java.awt.Dimension(21, 21));
        mais4.setMinimumSize(new java.awt.Dimension(21, 21));
        mais4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais4ActionPerformed(evt);
            }
        });

        menos4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos4.setText("<");
        menos4.setEnabled(false);
        menos4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos4.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos4.setMaximumSize(new java.awt.Dimension(21, 21));
        menos4.setMinimumSize(new java.awt.Dimension(21, 21));
        menos4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos4ActionPerformed(evt);
            }
        });

        numero4.setEditable(false);
        numero4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero4.setText("0");
        numero4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero4ActionPerformed(evt);
            }
        });

        txtA4.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtA4.setForeground(new java.awt.Color(255, 255, 255));
        txtA4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtA4.setText("UZI");
        txtA4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        txtA5.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtA5.setForeground(new java.awt.Color(255, 255, 255));
        txtA5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtA5.setText("THOMPSOM");
        txtA5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        menos5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos5.setText("<");
        menos5.setEnabled(false);
        menos5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos5.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos5.setMaximumSize(new java.awt.Dimension(21, 21));
        menos5.setMinimumSize(new java.awt.Dimension(21, 21));
        menos5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos5ActionPerformed(evt);
            }
        });

        numero5.setEditable(false);
        numero5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero5.setText("0");
        numero5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero5ActionPerformed(evt);
            }
        });

        mais5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais5.setText(">");
        mais5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais5.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais5.setMaximumSize(new java.awt.Dimension(21, 21));
        mais5.setMinimumSize(new java.awt.Dimension(21, 21));
        mais5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais5ActionPerformed(evt);
            }
        });

        zerar5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar5.setText("0");
        zerar5.setEnabled(false);
        zerar5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar5.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar5.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar5.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar5ActionPerformed(evt);
            }
        });

        cifrao5.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cifrao5.setForeground(new java.awt.Color(255, 255, 255));
        cifrao5.setText("$");

        txtA6.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtA6.setForeground(new java.awt.Color(255, 255, 255));
        txtA6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtA6.setText("AK-103");
        txtA6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        menos6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos6.setText("<");
        menos6.setEnabled(false);
        menos6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos6.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos6.setMaximumSize(new java.awt.Dimension(21, 21));
        menos6.setMinimumSize(new java.awt.Dimension(21, 21));
        menos6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos6ActionPerformed(evt);
            }
        });

        numero6.setEditable(false);
        numero6.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero6.setText("0");
        numero6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero6ActionPerformed(evt);
            }
        });

        mais6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais6.setText(">");
        mais6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais6.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais6.setMaximumSize(new java.awt.Dimension(21, 21));
        mais6.setMinimumSize(new java.awt.Dimension(21, 21));
        mais6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais6ActionPerformed(evt);
            }
        });

        zerar6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar6.setText("0");
        zerar6.setEnabled(false);
        zerar6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar6.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar6.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar6.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar6ActionPerformed(evt);
            }
        });

        cifrao6.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cifrao6.setForeground(new java.awt.Color(255, 255, 255));
        cifrao6.setText("$");

        txtA7.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        txtA7.setForeground(new java.awt.Color(255, 255, 255));
        txtA7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtA7.setText("M4");
        txtA7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        menos7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos7.setText("<");
        menos7.setEnabled(false);
        menos7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos7.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos7.setMaximumSize(new java.awt.Dimension(21, 21));
        menos7.setMinimumSize(new java.awt.Dimension(21, 21));
        menos7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos7ActionPerformed(evt);
            }
        });

        numero7.setEditable(false);
        numero7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero7.setText("0");
        numero7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero7ActionPerformed(evt);
            }
        });

        mais7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais7.setText(">");
        mais7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais7.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais7.setMaximumSize(new java.awt.Dimension(21, 21));
        mais7.setMinimumSize(new java.awt.Dimension(21, 21));
        mais7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais7ActionPerformed(evt);
            }
        });

        zerar7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar7.setText("0");
        zerar7.setEnabled(false);
        zerar7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar7.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar7.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar7.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar7ActionPerformed(evt);
            }
        });

        cifrao7.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cifrao7.setForeground(new java.awt.Color(255, 255, 255));
        cifrao7.setText("$");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtA3, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                        .addComponent(txtA6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtA5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtA4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtA7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtA2, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                        .addComponent(txtA1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(menos7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(numero7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mais7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(zerar7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cifrao7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(inputnumero7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(menos1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(numero1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(mais1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(zerar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cifrao1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(inputnumero1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(menos2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(numero2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(mais2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(zerar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cifrao2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(inputnumero2, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(menos4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(numero4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(mais4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(zerar4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cifrao4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(inputnumero4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(menos5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(numero5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(mais5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(zerar5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cifrao5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(inputnumero5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(menos6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(numero6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(mais6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(zerar6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cifrao6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(inputnumero6, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(menos3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(numero3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(mais3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(zerar3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(cifrao3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(inputnumero3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cifrao1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtA1)
                        .addComponent(numero1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(menos1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mais1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(zerar1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(inputnumero1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cifrao2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtA2)
                        .addComponent(numero2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(menos2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mais2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(zerar2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(inputnumero2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cifrao3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtA3)
                        .addComponent(numero3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(menos3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mais3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(zerar3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(inputnumero3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cifrao4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtA4)
                        .addComponent(numero4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(menos4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mais4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(zerar4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(inputnumero4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cifrao5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtA5)
                        .addComponent(numero5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(menos5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mais5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(zerar5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(inputnumero5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cifrao6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtA6)
                        .addComponent(numero6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(menos6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mais6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(zerar6, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(inputnumero6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cifrao7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtA7)
                        .addComponent(numero7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(menos7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mais7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(zerar7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(inputnumero7)))
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 102, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("DESCONTOS");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("ADICIONAL");

        inputnumero15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        inputnumero15.setForeground(new java.awt.Color(0, 153, 0));
        inputnumero15.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inputnumero15.setText("0");
        inputnumero15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inputnumero15KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inputnumero15KeyTyped(evt);
            }
        });

        cifrao16.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cifrao16.setForeground(new java.awt.Color(0, 153, 0));
        cifrao16.setText("$");

        cifrao17.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cifrao17.setForeground(new java.awt.Color(0, 153, 0));
        cifrao17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cifrao17.setText("$");

        cifrao18.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cifrao18.setForeground(new java.awt.Color(0, 153, 0));
        cifrao18.setText("%");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("DIVISÃO EM");

        zerar8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar8.setText("0");
        zerar8.setEnabled(false);
        zerar8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zerar8.setMargin(new java.awt.Insets(1, 2, 1, 2));
        zerar8.setMaximumSize(new java.awt.Dimension(21, 21));
        zerar8.setMinimumSize(new java.awt.Dimension(21, 21));
        zerar8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zerar8ActionPerformed(evt);
            }
        });

        menos8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        menos8.setText("<");
        menos8.setEnabled(false);
        menos8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        menos8.setMargin(new java.awt.Insets(1, 2, 1, 2));
        menos8.setMaximumSize(new java.awt.Dimension(21, 21));
        menos8.setMinimumSize(new java.awt.Dimension(21, 21));
        menos8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menos8ActionPerformed(evt);
            }
        });

        mais8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        mais8.setText(">");
        mais8.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        mais8.setMargin(new java.awt.Insets(1, 2, 1, 2));
        mais8.setMaximumSize(new java.awt.Dimension(21, 21));
        mais8.setMinimumSize(new java.awt.Dimension(21, 21));
        mais8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mais8ActionPerformed(evt);
            }
        });

        numero8.setEditable(false);
        numero8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        numero8.setText("0");
        numero8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numero8ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("MEMBROS");

        inputnumero17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        inputnumero17.setForeground(new java.awt.Color(255, 102, 102));
        inputnumero17.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inputnumero17.setText("0");
        inputnumero17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inputnumero17KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inputnumero17KeyTyped(evt);
            }
        });

        cifrao19.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cifrao19.setForeground(new java.awt.Color(255, 102, 102));
        cifrao19.setText("$");

        cifrao20.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cifrao20.setForeground(new java.awt.Color(255, 102, 102));
        cifrao20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cifrao20.setText("$");

        cifrao21.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        cifrao21.setForeground(new java.awt.Color(255, 102, 102));
        cifrao21.setText("%");

        inputnumero16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        inputnumero16.setForeground(new java.awt.Color(0, 153, 0));
        inputnumero16.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inputnumero16.setText("0");
        inputnumero16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inputnumero16KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inputnumero16KeyTyped(evt);
            }
        });

        inputnumero18.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        inputnumero18.setForeground(new java.awt.Color(255, 102, 102));
        inputnumero18.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inputnumero18.setText("0");
        inputnumero18.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                inputnumero18KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inputnumero18KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator3)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(menos8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(numero8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mais8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(zerar8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(cifrao16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputnumero15, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cifrao17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputnumero16, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cifrao18))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(cifrao19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputnumero17, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cifrao20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputnumero18, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cifrao21)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cifrao16, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(inputnumero15, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(cifrao18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cifrao17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputnumero16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cifrao19, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(inputnumero17, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(cifrao21, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cifrao20, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputnumero18, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(numero8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(menos8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mais8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zerar8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap())
        );

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DISCORD", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N

        discord.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        discord.setEditable(false);
        discord.setText("AGUARDANDO INFORMAÇÕES");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(discord, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(discord, javax.swing.GroupLayout.PREFERRED_SIZE, 50, Short.MAX_VALUE)
                .addContainerGap())
        );

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
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void copiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copiarActionPerformed
        String myString = discord.getText();
        StringSelection stringSelection = new StringSelection(myString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        copiar.setText("COPIADO!!!");
        //copiado.setVisible(true);
    }//GEN-LAST:event_copiarActionPerformed

    private void numero1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero1ActionPerformed

    private void menos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos1ActionPerformed
        BotaoGeral("rem", 1);        // TODO add your handling code here:
    }//GEN-LAST:event_menos1ActionPerformed

    private void mais1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais1ActionPerformed
        BotaoGeral("add",1);        // TODO add your handling code here:
    }//GEN-LAST:event_mais1ActionPerformed

    private void zerar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar1ActionPerformed
        BotaoGeral("zer", 1);        // TODO add your handling code here:
    }//GEN-LAST:event_zerar1ActionPerformed

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

    private void registrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registrarActionPerformed
        
    }//GEN-LAST:event_registrarActionPerformed

    private void zerar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar2ActionPerformed
        BotaoGeral("zer",2);// TODO add your handling code here:
    }//GEN-LAST:event_zerar2ActionPerformed

    private void mais2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais2ActionPerformed
        BotaoGeral("add",2);
    }//GEN-LAST:event_mais2ActionPerformed

    private void menos2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos2ActionPerformed
        BotaoGeral("rem",2);// TODO add your handling code here:
    }//GEN-LAST:event_menos2ActionPerformed

    private void numero2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero2ActionPerformed

    private void zerar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar3ActionPerformed
        BotaoGeral("zer",3);
    }//GEN-LAST:event_zerar3ActionPerformed

    private void mais3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais3ActionPerformed
        BotaoGeral("add",3);
    }//GEN-LAST:event_mais3ActionPerformed

    private void menos3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos3ActionPerformed
        BotaoGeral("rem",3);
    }//GEN-LAST:event_menos3ActionPerformed

    private void numero3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero3ActionPerformed

    private void zerar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar4ActionPerformed
        BotaoGeral("zer",4);
    }//GEN-LAST:event_zerar4ActionPerformed

    private void mais4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais4ActionPerformed
        BotaoGeral("add",4);
    }//GEN-LAST:event_mais4ActionPerformed

    private void menos4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos4ActionPerformed
        BotaoGeral("rem",4);
    }//GEN-LAST:event_menos4ActionPerformed

    private void numero4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero4ActionPerformed

    private void menos5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos5ActionPerformed
        BotaoGeral("rem",5);
    }//GEN-LAST:event_menos5ActionPerformed

    private void numero5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero5ActionPerformed

    private void mais5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais5ActionPerformed
        BotaoGeral("add",5);
    }//GEN-LAST:event_mais5ActionPerformed

    private void zerar5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar5ActionPerformed
        BotaoGeral("zer",5);// TODO add your handling code here:
    }//GEN-LAST:event_zerar5ActionPerformed

    private void menos6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos6ActionPerformed
        BotaoGeral("rem",6);
    }//GEN-LAST:event_menos6ActionPerformed

    private void numero6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero6ActionPerformed

    private void mais6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais6ActionPerformed
        BotaoGeral("add",6);
    }//GEN-LAST:event_mais6ActionPerformed

    private void zerar6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar6ActionPerformed
        BotaoGeral("zer",6);
    }//GEN-LAST:event_zerar6ActionPerformed

    private void menos7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos7ActionPerformed
        BotaoGeral("rem",7);
    }//GEN-LAST:event_menos7ActionPerformed

    private void numero7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero7ActionPerformed

    private void mais7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais7ActionPerformed
        BotaoGeral("add",7);
    }//GEN-LAST:event_mais7ActionPerformed

    private void zerar7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar7ActionPerformed
        BotaoGeral("zer",7);
    }//GEN-LAST:event_zerar7ActionPerformed

    private void menos8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos8ActionPerformed
        BotaoGeral("rem",8);
    }//GEN-LAST:event_menos8ActionPerformed

    private void numero8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero8ActionPerformed

    private void mais8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais8ActionPerformed
        BotaoGeral("add",8);
    }//GEN-LAST:event_mais8ActionPerformed

    private void zerar8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar8ActionPerformed
        BotaoGeral("zer",8);
    }//GEN-LAST:event_zerar8ActionPerformed

    private void inputnumero15KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputnumero15KeyReleased
        Numberos(10, inputnumero15);
        SomenteNumeros(evt);// TODO add your handling code here:
        
    }//GEN-LAST:event_inputnumero15KeyReleased

    private void inputnumero15KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputnumero15KeyTyped
        Numberos(10, inputnumero15);
        SomenteNumeros(evt);// TODO add your handling code here:
        
    }//GEN-LAST:event_inputnumero15KeyTyped

    private void inputnumero17KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputnumero17KeyReleased
        Numberos(10, inputnumero17);
        SomenteNumeros(evt);// TODO add your handling code here:
        
    }//GEN-LAST:event_inputnumero17KeyReleased

    private void inputnumero17KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputnumero17KeyTyped
        Numberos(10, inputnumero17);
        SomenteNumeros(evt);// TODO add your handling code here:
    }//GEN-LAST:event_inputnumero17KeyTyped

    private void inputnumero16KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputnumero16KeyReleased
        Numberos(3, inputnumero16);// TODO add your handling code here:
        SomenteNumeros(evt);
    }//GEN-LAST:event_inputnumero16KeyReleased

    private void inputnumero16KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputnumero16KeyTyped
        Numberos(3, inputnumero16);
        SomenteNumeros(evt);
    }//GEN-LAST:event_inputnumero16KeyTyped

    private void inputnumero18KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputnumero18KeyReleased
        Numberos(3, inputnumero18);
        SomenteNumeros(evt);
    }//GEN-LAST:event_inputnumero18KeyReleased

    private void inputnumero18KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputnumero18KeyTyped
        Numberos(3, inputnumero18);
        SomenteNumeros(evt);
    }//GEN-LAST:event_inputnumero18KeyTyped

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
            java.util.logging.Logger.getLogger(Calculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Calculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Calculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Calculos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Calculos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cifrao1;
    private javax.swing.JLabel cifrao16;
    private javax.swing.JLabel cifrao17;
    private javax.swing.JLabel cifrao18;
    private javax.swing.JLabel cifrao19;
    private javax.swing.JLabel cifrao2;
    private javax.swing.JLabel cifrao20;
    private javax.swing.JLabel cifrao21;
    private javax.swing.JLabel cifrao3;
    private javax.swing.JLabel cifrao4;
    private javax.swing.JLabel cifrao5;
    private javax.swing.JLabel cifrao6;
    private javax.swing.JLabel cifrao7;
    private javax.swing.JButton copiar;
    private java.awt.TextArea discord;
    private javax.swing.JTextField inputnumero1;
    private javax.swing.JTextField inputnumero15;
    private javax.swing.JTextField inputnumero16;
    private javax.swing.JTextField inputnumero17;
    private javax.swing.JTextField inputnumero18;
    private javax.swing.JTextField inputnumero2;
    private javax.swing.JTextField inputnumero3;
    private javax.swing.JTextField inputnumero4;
    private javax.swing.JTextField inputnumero5;
    private javax.swing.JTextField inputnumero6;
    private javax.swing.JTextField inputnumero7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton mais1;
    private javax.swing.JButton mais2;
    private javax.swing.JButton mais3;
    private javax.swing.JButton mais4;
    private javax.swing.JButton mais5;
    private javax.swing.JButton mais6;
    private javax.swing.JButton mais7;
    private javax.swing.JButton mais8;
    private javax.swing.JButton menos1;
    private javax.swing.JButton menos2;
    private javax.swing.JButton menos3;
    private javax.swing.JButton menos4;
    private javax.swing.JButton menos5;
    private javax.swing.JButton menos6;
    private javax.swing.JButton menos7;
    private javax.swing.JButton menos8;
    private javax.swing.JTextField numero1;
    private javax.swing.JTextField numero2;
    private javax.swing.JTextField numero3;
    private javax.swing.JTextField numero4;
    private javax.swing.JTextField numero5;
    private javax.swing.JTextField numero6;
    private javax.swing.JTextField numero7;
    private javax.swing.JTextField numero8;
    private javax.swing.JButton registrar;
    private javax.swing.JButton resetar;
    private javax.swing.JLabel txtA1;
    private javax.swing.JLabel txtA2;
    private javax.swing.JLabel txtA3;
    private javax.swing.JLabel txtA4;
    private javax.swing.JLabel txtA5;
    private javax.swing.JLabel txtA6;
    private javax.swing.JLabel txtA7;
    private javax.swing.JLabel varmas;
    private javax.swing.JLabel vdesconto;
    private javax.swing.JLabel vrestante;
    private javax.swing.JLabel vtotal;
    private javax.swing.JButton zerar1;
    private javax.swing.JButton zerar2;
    private javax.swing.JButton zerar3;
    private javax.swing.JButton zerar4;
    private javax.swing.JButton zerar5;
    private javax.swing.JButton zerar6;
    private javax.swing.JButton zerar7;
    private javax.swing.JButton zerar8;
    // End of variables declaration//GEN-END:variables
}
