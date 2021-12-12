/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Animacion;

import Conexion.Conexion;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import static java.lang.Thread.sleep;
import javax.swing.ImageIcon;

/**
 *
 * @author Reina
 */
public class Portada extends javax.swing.JFrame {
    
    private ImageIcon poli;
    private ImageIcon escom;
    private ImageIcon play;
    
    private Conexion p_dyt = null;
    private Conexion p_l = null;
    /**
     * Creates new form Portada
     */
    public Portada(){
        poli = obtenerImageIcon("logoPoli.png");
        escom = obtenerImageIcon("logoEscom.png");
        play = obtenerImageIcon("comenzar.png");
        
        initComponents();
        
        /*Estableciendo tamaño, color y posición de la ventana (JFrame)*/
        setSize(1111, 700);
        getContentPane().setBackground(new Color(204,153,255));
        setLocationRelativeTo(null);
        
        /*Agregando el listener para detectar el cierre de la ventana*/
        addWindowListener (new WindowAdapter() {    
            public void windowClosing (WindowEvent e) { 
                p_dyt.desconectar();
                p_l.desconectar();
                System.exit(0);   
            }    
        }); 
        
        /*Hilo que realiza la conexión con los puertos*/
        new Thread(() -> {
            p_dyt = new Conexion("COM6");
            p_l = new Conexion("COM7");
        }).start();        
    }

    public ImageIcon obtenerImageIcon(String nombre_imagen){
        File img = new File(".\\src\\imagenes\\" + nombre_imagen);
        return new ImageIcon(img.getAbsolutePath());
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        btnComenzar = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(escom);
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 30, 140, 130));

        jLabel2.setIcon(poli);
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 20, 100, 160));

        jLabel3.setFont(new java.awt.Font("Quicksand", 1, 20)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 173, 248));
        jLabel3.setText("ESCUELA SUPERIOR DE CÓMPUTO");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 70, -1, -1));

        jLabel4.setFont(new java.awt.Font("Quicksand", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(109, 12, 46));
        jLabel4.setText("INSTITUTO POLITÉCNICO NACIONAL");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 20, -1, -1));

        jLabel5.setFont(new java.awt.Font("Quicksand", 1, 22)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(15, 111, 198));
        jLabel5.setText("Proyecto Final");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 200, -1, -1));

        jLabel6.setFont(new java.awt.Font("Quicksand", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 32, 96));
        jLabel6.setText("Instrumentación");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 140, -1, -1));

        jLabel7.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 32, 96));
        jLabel7.setText("Equipo 5");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 290, -1, -1));

        jLabel8.setFont(new java.awt.Font("Quicksand", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 32, 96));
        jLabel8.setText("•      Méndez Castañeda Aurora ");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 450, -1, -1));

        jLabel9.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 32, 96));
        jLabel9.setText("Integrantes:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 350, -1, -1));

        jLabel10.setFont(new java.awt.Font("Quicksand", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 32, 96));
        jLabel10.setText("•     Juárez Leonel Reina Beatriz ");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 390, -1, -1));

        jLabel11.setFont(new java.awt.Font("Quicksand", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 32, 96));
        jLabel11.setText("•     Martínez Ruiz Alfredo");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 420, -1, -1));

        jLabel12.setFont(new java.awt.Font("Quicksand", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 32, 96));
        jLabel12.setText("                   Rosario Rocha Bernabé");
        getContentPane().add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 610, 270, -1));

        jLabel13.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 32, 96));
        jLabel13.setText("Grupo:");
        getContentPane().add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 510, -1, -1));

        jLabel14.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 32, 96));
        jLabel14.setText("3CV13");
        getContentPane().add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 550, -1, -1));

        btnComenzar.setIcon(play);
        btnComenzar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnComenzarMouseClicked(evt);
            }
        });
        getContentPane().add(btnComenzar, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 550, 90, 100));

        jLabel15.setFont(new java.awt.Font("Quicksand", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 32, 96));
        jLabel15.setText("Profesora: ");
        getContentPane().add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 610, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnComenzarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnComenzarMouseClicked
        this.setVisible(false);
        new frmAnimacion(p_dyt, p_l).setVisible(true);
    }//GEN-LAST:event_btnComenzarMouseClicked

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
            java.util.logging.Logger.getLogger(Portada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Portada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Portada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Portada.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Portada().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnComenzar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    // End of variables declaration//GEN-END:variables
}
