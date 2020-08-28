/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package police;

import java.awt.Container;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.table.DefaultTableModel;
import org.json.JSONArray;
import org.json.JSONObject;

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
    
    JSONArray RegistroBotoes = new JSONArray();
    
    /*JSONArray CategoriasCrimes = new JSONArray();
    JSONArray CrimesRegistro = new JSONArray();*/
    public Gerenciamento() {
        initComponents();
        SetarBotoes();
        AdicionarBotoes();
        jTable1.setDragEnabled(true);
        jTable1.setDropMode(DropMode.INSERT_ROWS);
        //jTable1.setTransferHandler(new TableRowTransferHandler(jTable1)); 
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
    public JTable[] Tabelas = new JTable[10];
    JButton[] BotoesAdd = new JButton[10];
    JButton[] BotoesRem = new JButton[10];
    
    public void AdicionarBotoes(){
        for(int i2 = 0; i2 < CategoriasCrimes.length(); i2++){
            JSONObject o2 = CategoriasCrimes.getJSONObject(i2);
            
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

            jTabbedPane1.addTab(o2.getString("texto"), PainelBase[i2]);
            
            
            Container container = PainelBase[i2];
            container.setLayout(null);
            
            Tabelas[i2].setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                    {"CRIME 1",  new Integer(1500),  new Integer(11111)},
                    {"CRIME 2",  new Integer(2500),  new Integer(2222222)},
                    {"CRIME 3",  new Integer(5000),  new Integer(333333333)},
                    {"CRIME 4",  new Integer(10000),  new Integer(444444444)}
                },
                new String [] {
                    "NOME DO CRIME", "MULTA", "PENA EM MESES"
                }
            ) {
                Class[] types = new Class [] {
                    java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
                };

                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }
            });
            Tabelas[i2].setDragEnabled(true);
            Tabelas[i2].setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            Tabelas[i2].setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            Tabelas[i2].setShowGrid(true);
            Tabelas[i2].setUpdateSelectionOnSort(false);
            final int As = i2;
            Tabelas[i2].addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent evt) {
                    int index = Tabelas[As].getSelectedRow();
                    DefaultTableModel model = (DefaultTableModel)Tabelas[As].getModel();
                    pressedKeys.add(evt.getKeyCode());
                    if (!pressedKeys.isEmpty()) {
                        if (pressedKeys.contains(KeyEvent.VK_SHIFT)) {
                            int row = Tabelas[As].getSelectedRow();
                            if(row >= 0){
                                if (pressedKeys.contains(KeyEvent.VK_UP)) {
                                    //System.out.println("SHIFT + UP");
                                    if(index > 0) model.moveRow(index, index, index - 1);
                                    //jTable1.setRowSelectionInterval(index - 1, index - 1);
                                }
                                if (pressedKeys.contains(KeyEvent.VK_DOWN)) {
                                    //System.out.println("SHIFT + DOWN");
                                    if(index < model.getRowCount() - 1) model.moveRow(index, index, index + 1);
                                }
                                if (pressedKeys.contains(KeyEvent.VK_DELETE)) {
                                    model.removeRow( row );
                                }
                            }
                            if (pressedKeys.contains(KeyEvent.VK_INSERT)) {
                                //System.out.println("Tabelas[As].getRowCount(): "+Tabelas[As].getRowCount());
                                if(Tabelas[As].getRowCount() < 30) model.addRow(new Object[]{"", 0, 0});
                            }
                        }
                    }
                }
                public void keyReleased(java.awt.event.KeyEvent evt) {
                    pressedKeys.remove(evt.getKeyCode());
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
                    DefaultTableModel model = (DefaultTableModel) Tabelas[As].getModel();
                    model.addRow(new Object[]{"", 0, 0});
                }
            });
            
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
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(BotoesAdd[i2])))
                    .addContainerGap())
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(ScrollPainel[i2], javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BotoesAdd[i2], javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BotoesRem[i2], javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
            for(int i = 0; i < CrimesRegistro.length(); i++){

                JSONObject o = CrimesRegistro.getJSONObject(i);
                
            }
        }
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
        PainelDetalhes = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("REGISTRO DE PRISÕES");

        jTabbedPane1.setToolTipText("");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"CRIME 1",  new Integer(1500),  new Integer(11111)},
                {"CRIME 2",  new Integer(2500),  new Integer(2222222)},
                {"CRIME 3",  new Integer(5000),  new Integer(333333333)},
                {"CRIME 4",  new Integer(10000),  new Integer(444444444)}
            },
            new String [] {
                "NOME DO CRIME", "MULTA", "PENA EM MESES"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.setDragEnabled(true);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.setShowGrid(true);
        jTable1.setUpdateSelectionOnSort(false);
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
        jButton5.setText("- DELETAR CATEGORIA");
        jButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", jPanel1);

        PainelDetalhes.setBorder(javax.swing.BorderFactory.createTitledBorder("ADICIONAR CATEGORIA"));

        jButton1.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        jButton1.setText("SALVAR");

        jButton2.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        jButton2.setText("RESETAR");

        jLabel2.setFont(new java.awt.Font("Arial Unicode MS", 0, 15)); // NOI18N
        jLabel2.setText("CATEGORIA:");

        jButton3.setFont(new java.awt.Font("Arial Unicode MS", 0, 12)); // NOI18N
        jButton3.setText("ADICIONAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PainelDetalhesLayout = new javax.swing.GroupLayout(PainelDetalhes);
        PainelDetalhes.setLayout(PainelDetalhesLayout);
        PainelDetalhesLayout.setHorizontalGroup(
            PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelDetalhesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        PainelDetalhesLayout.setVerticalGroup(
            PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelDetalhesLayout.createSequentialGroup()
                .addGroup(PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelDetalhesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PainelDetalhesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PainelDetalhesLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PainelDetalhesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PainelDetalhes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

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
    private javax.swing.JPanel PainelDetalhes;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
