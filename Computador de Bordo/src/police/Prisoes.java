/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police;

import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import org.json.JSONArray;
import org.json.JSONObject;

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
    
    /*JSONArray CategoriasCrimes = new JSONArray();
    JSONArray CrimesRegistro = new JSONArray();*/
    public Prisoes() {
        initComponents();
        SetarBotoes();
        AdicionarBotoes();
    }
    
    
    public void SetarBotoes(){
        
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
    }
    //private javax.swing.JLabel Textos[];
    //private javax.swing.JToggleButton Botoes[];
    
    JScrollPane[] ScrollPainel = new JScrollPane[10];
    JPanel[] PainelBase = new JPanel[10];
    JPanel[][] Painel = new JPanel[10][10];
    JLabel[] Textos = new JLabel[10];
    JToggleButton[][] Botoes = new JToggleButton[10][10];
    
    public void AdicionarBotoes(){
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
            jTabbedPane1.addTab(o2.getString("texto"), ScrollPainel[i2]);
            
            
            int PadraoX = 18;
            int EspacamentoX = 20;
            int EspacamentoY = 10;
            int LimiteQuadro = 4;
            int ContagemLimite = 0;

            int TamanhoPainel = (jPanel1.getWidth()/LimiteQuadro);
            int Linha = 0;
            
            Container container = PainelBase[i2];
            container.setLayout(null);
            
            for(int i = 0; i < CrimesRegistro.length(); i++){

                JSONObject o = CrimesRegistro.getJSONObject(i);
                if(o.getInt("categoria") == o2.getInt("id")){

                    JSONObject PegarDadosBt = o;
                    PegarDadosBt.put("i1", i2);
                    PegarDadosBt.put("i2", i);
                    RegistroBotoes.put(PegarDadosBt);
                    
                    
                    Painel[i2][i] = new JPanel();
                    Painel[i2][i].setBounds(PadraoX+(TamanhoPainel*ContagemLimite), PadraoX+(50*Linha), 200, 45);
                    //Painel[i].setBackground(new java.awt.Color(0, 0, 153));
                    Painel[i2][i].setBorder(javax.swing.BorderFactory.createEtchedBorder());
                    container.add( Painel[i2][i] );

                    Container container2 = Painel[i2][i];
                    container2.setLayout(null);

                    /*Textos[i] = new JLabel();//new javax.swing.JLabel();
                    Textos[i].setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
                    Textos[i].setText(o.getString("texto"));
                    Textos[i].setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

                    Textos[i].setBounds(PadraoX, 0, 150, 35);
                    container2.add( Textos[i] );*/

                    //Botoes[i] = new javax.swing.JToggleButton();
                    Botoes[i2][i] = new JToggleButton(o.getString("texto"));
                    Botoes[i2][i].setIcon(new javax.swing.ImageIcon(getClass().getResource("/police/imagens/falso.png"))); // NOI18N
                    Botoes[i2][i].setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                    Botoes[i2][i].setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
                    Botoes[i2][i].setRolloverEnabled(false);
                    Botoes[i2][i].setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/police/imagens/verdadeiro.png"))); // NOI18N

                    Botoes[i2][i].addItemListener(new ItemListener() {
                        public void itemStateChanged(ItemEvent ev) {
                            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAA");
                           AttCrimes();
                        }
                    });
                    //System.out.println("o.getString(\"texto\").length(): "+o.getString("texto").length());
                    /*if(o.getString("texto").length() > 33){
                        Botoes[i].setFont(new java.awt.Font("Tahoma", 0, 7));
                    }else*/ if(o.getString("texto").length() > 27){
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


                    //Botoes[i].setBounds(PadraoX+160, 0, 35, 35);
                    Botoes[i2][i].setBounds(3, 3, 194, 39);

                    container2.add( Botoes[i2][i] );

                    //System.out.println("o.toString: "+o.toString());
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
    
    public void AttCrimes(){
        int MultaTotal = 0;
        int MesesTotal = 0;
        for(int i = 0; i < RegistroBotoes.length(); i++){
            JSONObject obj = RegistroBotoes.getJSONObject(i);
            int ir1 = obj.getInt("i1");
            int ir2 = obj.getInt("i2");
            if(Botoes[ir1][ir2].isSelected()){
                System.out.println("Nome do crime: "+obj.getString("texto"));
                MultaTotal+=obj.getInt("multa");
                MesesTotal+=obj.getInt("meses");
            }
        }
        PenaTotal.setText("Multa: R$"+MultaTotal+" / Meses: "+MesesTotal);
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
        Texto1 = new javax.swing.JLabel();
        CrimesCometidos = new javax.swing.JTextField();
        Texto2 = new javax.swing.JLabel();
        PenaTotal = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("REGISTRO DE PRISÕES");

        jTabbedPane1.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 856, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 386, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel1);

        jTabbedPane1.addTab("Crimes 1", jScrollPane1);

        PainelDetalhes.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Texto1.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        Texto1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Texto1.setText("CRIMES COMETIDOS:");
        Texto1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        CrimesCometidos.setEditable(false);

        Texto2.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        Texto2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Texto2.setText("PENA TOTAL:");
        Texto2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        PenaTotal.setEditable(false);

        jButton1.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        jButton1.setText("REGISTRAR");

        jButton2.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        jButton2.setText("RESETAR");

        javax.swing.GroupLayout PainelDetalhesLayout = new javax.swing.GroupLayout(PainelDetalhes);
        PainelDetalhes.setLayout(PainelDetalhesLayout);
        PainelDetalhesLayout.setHorizontalGroup(
            PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PainelDetalhesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PainelDetalhesLayout.createSequentialGroup()
                        .addComponent(Texto1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CrimesCometidos))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PainelDetalhesLayout.createSequentialGroup()
                        .addComponent(Texto2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(PenaTotal))
                    .addGroup(PainelDetalhesLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        PainelDetalhesLayout.setVerticalGroup(
            PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelDetalhesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(CrimesCometidos, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(Texto1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PenaTotal)
                    .addComponent(Texto2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 880, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 70, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addComponent(PainelDetalhes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 880, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PainelDetalhes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
    private javax.swing.JTextField CrimesCometidos;
    private javax.swing.JPanel PainelDetalhes;
    private javax.swing.JTextField PenaTotal;
    private javax.swing.JLabel Texto1;
    private javax.swing.JLabel Texto2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
