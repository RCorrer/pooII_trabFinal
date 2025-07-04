/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package telas;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import pizzaria.controller.SaborController;
import pizzaria.model.Sabor;

/**
 *
 * @author joaow
 */
public class telaCadastrarSabores extends javax.swing.JFrame {

    /**
     * Creates new form telaCadastrarSabores
     * 
     */
    
    
    
    //inicializar controller global
    SaborController saborController = new SaborController();
    
    public telaCadastrarSabores() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        boxTipos = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtSabor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textListaSabores = new javax.swing.JTextArea();
        btnMenu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        boxTipos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {" ---------"}));
        boxTipos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxTiposActionPerformed(evt);
            }
        });

        jLabel2.setText("Tipo do Sabor:");

        jLabel3.setText("Nome do Sabor");

        jButton1.setText("Adicionar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        textListaSabores.setEditable(false);
        textListaSabores.setColumns(20);
        textListaSabores.setRows(5);
        jScrollPane1.setViewportView(textListaSabores);

        btnMenu.setText("Voltar ao Menu");
        btnMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(btnMenu))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSabor)
                            .addComponent(boxTipos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(btnMenu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boxTipos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addGap(10, 10, 10)
                        .addComponent(txtSabor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(jButton1)))
                .addGap(0, 17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // fazer a inscrição do sabor digitado

        String nomeSabor = txtSabor.getText();
        if (existeSabor(nomeSabor)) {
            JOptionPane.showMessageDialog(null, "ESTE SABOR JÁ EXISTE!!.\n", "SABOR JÁ EXISTENTE", JOptionPane.INFORMATION_MESSAGE);
        } else {
            try {
            String tipo = boxTipos.getSelectedItem() + "";
            switch (tipo) {
                case "Simples":
                    Long id = Long.valueOf(SaborController.incrementarId());
                    Sabor saborTemp = new Sabor(id,nomeSabor,Long.valueOf(1));
                    saborController.adicionarSabor(saborTemp);
                    break;
                case "Especial":
                     Long id2 = Long.valueOf(SaborController.incrementarId());
                    Sabor saborTemp2 = new Sabor(id2,nomeSabor,Long.valueOf(2));
                    saborController.adicionarSabor(saborTemp2);
                    break;
                case "Premium":
                    Long id3 = Long.valueOf(SaborController.incrementarId());
                    Sabor saborTemp3 = new Sabor(id3,nomeSabor,Long.valueOf(3));
                    saborController.adicionarSabor(saborTemp3);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Selecione um tipo válido.\n", "SELECIONE TIPO VÁLIDO", JOptionPane.INFORMATION_MESSAGE);
            }

            String sabores = saborController.imprimeSabores();
            textListaSabores.setText(sabores);
             } catch (SQLException ex){
                System.out.println("ERRO ADICIONAR VALOR\n" + ex);
                }
        } 
       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        //carregar coisas qunaod logar
        
        String[] tipos = {"Simples", "Especial", "Premium"};
        String sabores = saborController.imprimeSabores();
        
        for (String c: tipos){
            boxTipos.addItem(c);
        }
        textListaSabores.setText(sabores);
        
        
    }//GEN-LAST:event_formComponentShown

    private void btnMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMenuActionPerformed
        //voltar ao menu
        new telaMenuInicial().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnMenuActionPerformed

    private void boxTiposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxTiposActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boxTiposActionPerformed

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
            java.util.logging.Logger.getLogger(telaCadastrarSabores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(telaCadastrarSabores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(telaCadastrarSabores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(telaCadastrarSabores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new telaCadastrarSabores().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> boxTipos;
    private javax.swing.JButton btnMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea textListaSabores;
    private javax.swing.JTextField txtSabor;
    // End of variables declaration//GEN-END:variables

    private boolean existeSabor(String nomeSabor){
    // Verificar se o sabor existe nas listas de sabores
    List<Sabor> listaSabores = null;
        try {
            listaSabores = saborController.listarSabores();
        } catch (SQLException ex) {
            Logger.getLogger(telaCadastrarSabores.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    for (Sabor s : listaSabores ){
        if (s.getNome().equals(nomeSabor)){
            return true;
        }
    }    
    return false;  // Nenhum sabor encontrado
    }
}

