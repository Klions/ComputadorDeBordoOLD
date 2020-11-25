/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import org.json.JSONObject;
import police.configs.GetImages;
import police.configs.HttpDownloadUtility;
import police.configs.SNWindows;
import police.configs.Usuario;

/**
 *
 * @author John
 */
public class ViewUsuario extends javax.swing.JFrame {

    /**
     * Creates new form ViewUsuario
     */
    public ViewUsuario() {
        initComponents();
        getContentPane().setBackground(new java.awt.Color(13, 32, 64));
        /*jPanel1.setBackground(new java.awt.Color(10, 28, 60));
        jPanel2.setBackground(new java.awt.Color(10, 28, 60));
        jPanel3.setBackground(new java.awt.Color(10, 28, 60));
        jPanel4.setBackground(new java.awt.Color(10, 28, 60));
        jPanel5.setBackground(new java.awt.Color(10, 28, 60));*/
        SubTitulo.setText(InicializadorMain.info_cidade.getString("nome_cidade").toUpperCase());
        if(InicializadorMain.ModoOffline){
            this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("imagens/CB2.png")));
            EditarBt4.setEnabled(false);
        }else{
            jLabel12.setText("•••••••••");
            EditarBt1.setEnabled(false);
            EditarBt2.setEnabled(false);
            EditarBt3.setEnabled(false);
            this.setIconImage(new ImageIcon(GetImages.LogoCB).getImage());
            icone.setIcon(new ImageIcon(GetImages.LogoCB_branco));
        }
        this.setLocationRelativeTo(null);
        AttAssinatura();
        RecarregarValoresTabela();
    }

    public void AttAssinatura(){
        int nivel_ass = SNWindows.getNivelSerialPC();
        
        if(nivel_ass != 0){
            SerialPainel.setVisible(false);
            AssinaturaTxt2.setText("Muito obrigado por contribuir no desenvolvimento <3");
        }else{
            SerialPainel.setVisible(true);
            AssinaturaTxt2.setText("Você pode pegar uma assinatura para ajudar no desenvolvimento <3");
        }
        if(!InicializadorMain.ModoOffline){
            AssinaturaTxt2.setText("A cidade nos ajuda no desenvolvimento <3");
            SerialPainel.setVisible(false);
            PainelAssinatura.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "ASSINATURA PARA CIDADE", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        }
        pack();
        AssinaturaTxt.setText(SNWindows.TipoAssinatura[nivel_ass].toUpperCase());
        PainelAssinatura.setBackground(new java.awt.Color(SNWindows.CorAssinatura[nivel_ass][0], SNWindows.CorAssinatura[nivel_ass][1], SNWindows.CorAssinatura[nivel_ass][2]));
    }
    
    public void AtivarAssinatura(){
        String Serial = SerialTxt.getText();
        if(Serial.length()<10){ showMessageDialog(null,"Lamento, mas o serial informado é inválido.", "Erro ao ativar o serial",JOptionPane.PLAIN_MESSAGE); return; }
        if(SNWindows.setSerialOnPC(Serial)){
            String serial_nome = SNWindows.TipoAssinatura[SNWindows.getNivelSerialPorSerial(Serial)].toUpperCase();
            HttpDownloadUtility.WebhookDiscord("https://discordapp.com/api/webhooks/781234838271164466/Bji5tJ0pzsNvhtCZMN2wpqFyoAg-UjSN6kUHeXR-YXa9u-BxIQ61FHD-jIHAZD2agFYM",
                        "Serial Ativado", 
                        "Um usuário ativou um serial de nível "+serial_nome+"!!");
            AttAssinatura();
            RecarregarValoresTabela();
            this.setLocationRelativeTo(null);
            showMessageDialog(null,"Você ativou com sucesso um serial de nível "+serial_nome+".\nAproveite os benefícios! <3", "Serial ativado com sucesso!!",JOptionPane.PLAIN_MESSAGE);
        }else{
            showMessageDialog(null,"Lamento, mas o serial informado é inválido ou já foi ativado.", "Erro ao ativar o serial",JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    public void EditarDados(String Valor){
        String Numbr = "";
        if("id".equals(Valor) || "id_usuario".equals(Valor) || "age".equals(Valor)) Numbr = " Coloque somente números.";
        
        String obs = JOptionPane.showInputDialog(this, "Qual seria o novo valor?"+Numbr, "Modificar valor", JOptionPane.PLAIN_MESSAGE);
        if(obs != null){
            if((obs.length() > 0 && obs.length() < 20 && "".equals(Numbr)) || (!"".equals(Numbr) && isNumeric(obs) && obs.length() < 6)){
                Usuario.setDadosParcial(Valor, obs);
                Usuario.setContaPC();
            }
        }
        RecarregarValoresTabela();
    }
    
    public static boolean isNumeric(String str) { 
        try {  
            Double.parseDouble(str);  
            return true;
        } catch(NumberFormatException e){  
            return false;  
        }  
    }
    
    public void RecarregarValoresTabela(){
        JSONObject PegarUser = Usuario.getDados();
        ValorTxt.setText(PegarUser.getInt("id_usuario")+"");
        ValorTxt2.setText(PegarUser.getString("nome")+" "+PegarUser.getString("sobrenome"));
        ValorTxt3.setText(PegarUser.getString("registration").toUpperCase());
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        icone = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        Titulo = new javax.swing.JLabel();
        SubTitulo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        EditarBt1 = new javax.swing.JButton();
        NomeTxt = new javax.swing.JLabel();
        ValorTxt = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        EditarBt2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        ValorTxt2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        EditarBt3 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        ValorTxt3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        EditarBt4 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        PainelAssinatura = new javax.swing.JPanel();
        SerialPainel = new javax.swing.JPanel();
        SerialTxt = new javax.swing.JTextField();
        SoInfoMsm = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        AssinaturaTxt = new javax.swing.JLabel();
        AssinaturaTxt2 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("INFORMAÇÕES DO USUÁRIO");
        setResizable(false);

        icone.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        icone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/police/imagens/CB.png"))); // NOI18N
        icone.setToolTipText("");

        jPanel1.setOpaque(false);

        Titulo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Titulo.setForeground(new java.awt.Color(255, 255, 255));
        Titulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Titulo.setText("SUAS INFORMAÇÕES");

        SubTitulo.setForeground(new java.awt.Color(255, 255, 255));
        SubTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SubTitulo.setText("NOME DA CIDADE");

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel2.setOpaque(false);

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
        NomeTxt.setText("PASSAPORTE:");

        ValorTxt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ValorTxt.setForeground(new java.awt.Color(255, 255, 255));
        ValorTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ValorTxt.setText("indefinido");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(NomeTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ValorTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(EditarBt1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditarBt1, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
            .addComponent(ValorTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(NomeTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel3.setOpaque(false);

        EditarBt2.setBackground(new java.awt.Color(255, 255, 255));
        EditarBt2.setText("EDITAR");
        EditarBt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditarBt2ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("NOME:");

        ValorTxt2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ValorTxt2.setForeground(new java.awt.Color(255, 255, 255));
        ValorTxt2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ValorTxt2.setText("indefinido");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ValorTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(EditarBt2, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditarBt2, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
            .addComponent(ValorTxt2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel4.setOpaque(false);

        EditarBt3.setBackground(new java.awt.Color(255, 255, 255));
        EditarBt3.setText("EDITAR");
        EditarBt3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditarBt3ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("REGISTRO:");

        ValorTxt3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ValorTxt3.setForeground(new java.awt.Color(255, 255, 255));
        ValorTxt3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ValorTxt3.setText("indefinido");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ValorTxt3, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(EditarBt3, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditarBt3, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
            .addComponent(ValorTxt3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jPanel5.setOpaque(false);

        EditarBt4.setBackground(new java.awt.Color(255, 255, 255));
        EditarBt4.setText("EDITAR");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("SENHA:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Somente no Online");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(EditarBt4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(EditarBt4, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Titulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SubTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Titulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SubTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PainelAssinatura.setBackground(new java.awt.Color(255, 153, 153));
        PainelAssinatura.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "ASSINATURA", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N

        SerialPainel.setOpaque(false);

        SerialTxt.setBackground(new java.awt.Color(255, 153, 153));
        SerialTxt.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        SerialTxt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        SerialTxt.setToolTipText("Cole o SERIAL e pressione ENTER");
        SerialTxt.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "SERIAL ASSINATURA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));
        SerialTxt.setDisabledTextColor(new java.awt.Color(255, 153, 153));
        SerialTxt.setOpaque(false);
        SerialTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                SerialTxtKeyPressed(evt);
            }
        });

        SoInfoMsm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SoInfoMsm.setText("PRESSIONE ENTER PARA VALIDAR");

        javax.swing.GroupLayout SerialPainelLayout = new javax.swing.GroupLayout(SerialPainel);
        SerialPainel.setLayout(SerialPainelLayout);
        SerialPainelLayout.setHorizontalGroup(
            SerialPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SerialPainelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(SerialPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SerialTxt)
                    .addComponent(SoInfoMsm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        SerialPainelLayout.setVerticalGroup(
            SerialPainelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SerialPainelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SerialTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SoInfoMsm)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setOpaque(false);

        AssinaturaTxt.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        AssinaturaTxt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AssinaturaTxt.setText(" ");

        AssinaturaTxt2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        AssinaturaTxt2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        AssinaturaTxt2.setText(" ");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(AssinaturaTxt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(AssinaturaTxt2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AssinaturaTxt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AssinaturaTxt2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PainelAssinaturaLayout = new javax.swing.GroupLayout(PainelAssinatura);
        PainelAssinatura.setLayout(PainelAssinaturaLayout);
        PainelAssinaturaLayout.setHorizontalGroup(
            PainelAssinaturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SerialPainel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelAssinaturaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        PainelAssinaturaLayout.setVerticalGroup(
            PainelAssinaturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelAssinaturaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(SerialPainel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu2.setText("FECHAR");

        jMenuItem2.setText("VOLTAR PARA O PAINEL");
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
            .addComponent(PainelAssinatura, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(icone)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(icone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PainelAssinatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SerialTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SerialTxtKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
            AtivarAssinatura();
        }
    }//GEN-LAST:event_SerialTxtKeyPressed

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

    private void EditarBt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarBt1ActionPerformed
        EditarDados("id_usuario");
    }//GEN-LAST:event_EditarBt1ActionPerformed

    private void EditarBt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarBt2ActionPerformed
        EditarDados("nome");
    }//GEN-LAST:event_EditarBt2ActionPerformed

    private void EditarBt3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarBt3ActionPerformed
        EditarDados("registration");
    }//GEN-LAST:event_EditarBt3ActionPerformed

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
            java.util.logging.Logger.getLogger(ViewUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewUsuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AssinaturaTxt;
    private javax.swing.JLabel AssinaturaTxt2;
    private javax.swing.JButton EditarBt1;
    private javax.swing.JButton EditarBt2;
    private javax.swing.JButton EditarBt3;
    private javax.swing.JButton EditarBt4;
    private javax.swing.JLabel NomeTxt;
    private javax.swing.JPanel PainelAssinatura;
    private javax.swing.JPanel SerialPainel;
    private javax.swing.JTextField SerialTxt;
    private javax.swing.JLabel SoInfoMsm;
    private javax.swing.JLabel SubTitulo;
    private javax.swing.JLabel Titulo;
    private javax.swing.JLabel ValorTxt;
    private javax.swing.JLabel ValorTxt2;
    private javax.swing.JLabel ValorTxt3;
    private javax.swing.JLabel icone;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    // End of variables declaration//GEN-END:variables
}
