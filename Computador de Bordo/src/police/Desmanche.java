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
public class Desmanche extends javax.swing.JFrame {

    int tamanhototal=1;
    /**
     * Creates new form Prender
     */
    public Desmanche() {
        
        initComponents();
        //copiado.setVisible(false);
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagens/mclost_1.png")));
        
        AtualizarCrimes();
        inputnumero15.requestFocus();
        
        getContentPane().setBackground(new java.awt.Color(51, 51, 51));
 
        
        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel3.setBackground(new java.awt.Color(51, 51, 51));
        jPanel4.setBackground(new java.awt.Color(51, 51, 51));
        jPanel5.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        
        for (int i=1; i<=tamanhototal; i++) {
            BtMais(i).setBackground(new java.awt.Color(255, 255, 255));
            BtMenos(i).setBackground(new java.awt.Color(255, 255, 255));
            BtZerar(i).setBackground(new java.awt.Color(255, 255, 255));
        }
        
    }
    

    
 
    
    public JButton BtMais(int valor){
        switch (valor) {
            case 1:
                return mais8;
            default:
                return mais8;
        }
    }
    
    public JButton BtMenos(int valor){
        switch (valor) {
            case 1:
                return menos8;
            default:
                return menos8;
        }
    }
    
    public JButton BtZerar(int valor){
        switch (valor) {
            case 1:
                return zerar8;
                
            default:
                return zerar8;
        }
    }
    
    public int QntCampo(int valor){
        switch (valor) {
            
            default:
                return 99;
        }
    }
    
    public JTextField CamposTexto(int valor){
        switch (valor) {
            case 1:
                return numero8;
            default:
                return jTextField1;
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
                totalv=1;
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
        }else if(totalv <= 1){
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
        for (int i=1; i<=tamanhototal; i++) {
            //Crimes cometidos
            String TextoDoCampo = CamposTexto(i).getText();
            int valorc;
            if(TextoDoCampo.length() <= 0){
                valorc=0;
            }else if(TextoDoCampo.length() >= 3){
                valorc=99;
                
            }else{
                valorc = Integer.parseInt(TextoDoCampo);
            }
            CamposTexto(i).setText(valorc+"");
        }
        //ADICIONAL
        int vadicionar1=Integer.parseInt(inputnumero15.getText());
        
        //DESCONTO
        int vdescontar1=Integer.parseInt(inputnumero17.getText());
        int vdescontar2=Integer.parseInt(inputnumero18.getText());
        
        //CONTA A FAZER
        valortotal+=vadicionar1;
        int valorlocal=valortotal;
        
        int addporce2=(vdescontar2*valortotal)/100;
        addporce2=vdescontar1+addporce2;
        
        int descontconta=addporce2;
        
        valortotal-=addporce2;
        
        int vdividir=Integer.parseInt(numero8.getText());
        int contadivid=0;
        if(vdividir>1)contadivid=descontconta/vdividir;
        
        //vdesconto.setText("VALOR DESCONTO: $ "+dinheiro(descontconta));
        if(vdividir>1 && contadivid>0){
            vrestante1.setText("$ "+dinheiro(contadivid)+" ÷"+vdividir);
        }else{
            vrestante1.setText("$ "+dinheiro(descontconta));
        }
                
        vdesconto1.setText("$ "+dinheiro(valortotal));
        
        vtotal1.setText("$ "+dinheiro(valorlocal));
        
        String TextDiscord = "```\n";
        if(vdividir>1)TextDiscord+="Valor da MC divido por "+vdividir+": $"+dinheiro(contadivid)+"\n";
        if(descontconta>0){
            TextDiscord+="Valor da MC: $"+dinheiro(descontconta);
            if(vdescontar2!=0)TextDiscord+=" ("+vdescontar2+"%)";
            TextDiscord+="\n";
        }
        if(valortotal!=0){
            TextDiscord+="Valor Total: $"+dinheiro(valortotal)+"\n";
            copiar.setEnabled(true);
        }else{
            copiar.setEnabled(false);
        }
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
        }
        inputnumero15.setText("0");
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
        registrar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        inputnumero15 = new javax.swing.JTextField();
        cifrao16 = new javax.swing.JLabel();
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
        inputnumero18 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        discord = new java.awt.TextArea();
        jPanel5 = new javax.swing.JPanel();
        vdesconto = new javax.swing.JLabel();
        vrestante = new javax.swing.JLabel();
        vtotal = new javax.swing.JLabel();
        vdesconto1 = new javax.swing.JLabel();
        vrestante1 = new javax.swing.JLabel();
        vtotal1 = new javax.swing.JLabel();
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
        jLabel1.setText("DESMANCHE THE LOST MC");

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

        registrar.setBackground(new java.awt.Color(255, 255, 255));
        registrar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        registrar.setText("GUARDAR REGISTRO");
        registrar.setEnabled(false);
        registrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(registrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(copiar, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(resetar, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(copiar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registrar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 102, 102));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("PREÇO MC");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 0));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("VALOR DO DESMANCHE");

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

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("DIVISÃO EM");

        zerar8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        zerar8.setText("1");
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
        numero8.setText("1");
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

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jButton1.setText("0");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
                                .addComponent(inputnumero15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(cifrao19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputnumero17, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
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
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        vdesconto.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        vdesconto.setForeground(new java.awt.Color(255, 102, 51));
        vdesconto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vdesconto.setText("VALOR DO CLIENTE:");

        vrestante.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        vrestante.setForeground(new java.awt.Color(204, 204, 204));
        vrestante.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vrestante.setText("VALOR DA MC:");

        vtotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        vtotal.setForeground(new java.awt.Color(255, 204, 102));
        vtotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vtotal.setText("VALOR TOTAL:");

        vdesconto1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        vdesconto1.setForeground(new java.awt.Color(255, 102, 51));
        vdesconto1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vdesconto1.setText("CARREGANDO...");

        vrestante1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        vrestante1.setForeground(new java.awt.Color(204, 204, 204));
        vrestante1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vrestante1.setText("CARREGANDO...");

        vtotal1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        vtotal1.setForeground(new java.awt.Color(255, 204, 102));
        vtotal1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vtotal1.setText("CARREGANDO...");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(vtotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(vdesconto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(vrestante, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(vdesconto1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(vrestante1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(vtotal1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(vdesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vdesconto1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(vrestante, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vrestante1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(vtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(vtotal1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
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
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void menos8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menos8ActionPerformed
        BotaoGeral("rem",1);
    }//GEN-LAST:event_menos8ActionPerformed

    private void numero8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numero8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numero8ActionPerformed

    private void mais8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mais8ActionPerformed
        BotaoGeral("add",1);
    }//GEN-LAST:event_mais8ActionPerformed

    private void zerar8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zerar8ActionPerformed
        BotaoGeral("zer",1);
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

    private void inputnumero18KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputnumero18KeyReleased
        Numberos(3, inputnumero18);
        SomenteNumeros(evt);
    }//GEN-LAST:event_inputnumero18KeyReleased

    private void inputnumero18KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputnumero18KeyTyped
        Numberos(3, inputnumero18);
        SomenteNumeros(evt);
    }//GEN-LAST:event_inputnumero18KeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        inputnumero15.setText("0");
        AtualizarCrimes();
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
            java.util.logging.Logger.getLogger(Desmanche.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Desmanche.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Desmanche.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Desmanche.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Desmanche().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel cifrao16;
    private javax.swing.JLabel cifrao19;
    private javax.swing.JLabel cifrao20;
    private javax.swing.JLabel cifrao21;
    private javax.swing.JButton copiar;
    private java.awt.TextArea discord;
    private javax.swing.JTextField inputnumero15;
    private javax.swing.JTextField inputnumero17;
    private javax.swing.JTextField inputnumero18;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JButton mais8;
    private javax.swing.JButton menos8;
    private javax.swing.JTextField numero8;
    private javax.swing.JButton registrar;
    private javax.swing.JButton resetar;
    private javax.swing.JLabel vdesconto;
    private javax.swing.JLabel vdesconto1;
    private javax.swing.JLabel vrestante;
    private javax.swing.JLabel vrestante1;
    private javax.swing.JLabel vtotal;
    private javax.swing.JLabel vtotal1;
    private javax.swing.JButton zerar8;
    // End of variables declaration//GEN-END:variables
}
