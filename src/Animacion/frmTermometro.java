/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Animacion;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Reina
 */
public class frmTermometro extends javax.swing.JFrame {
    Point puntoTer = new Point();
    int x, y;
    private int inicio = 0;
    boolean llenandoTer = false;
    boolean vaciandoTer = false;
    float lleno1 = 245; //Altura de lleno termometro
    int llenadoPCT2 = 0; //Esta vacio
    
    
    int fila;
    int i;
    Graphics imagen;
    private BufferedImage background;
    private BufferedImage foreground;
    /**
     * Creates new form frmTermometro
     */
    public frmTermometro() {
        try {
                foreground = ImageIO.read(getClass().getResource("/imagenes/termometro3.png"));
                background = ImageIO.read(getClass().getResource("/imagenes/terColor.png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        initComponents();
        setSize(720, 500);
        setLocationRelativeTo(null);
        puntoTer = lblTermometro.getLocation();
        //puntoLam = lblLmpara.getLocation();
        //punto2 = lblMedida.getLocation();
        x = (int) puntoTer.getX();
        y = (int) puntoTer.getY();

        i = 0;
        //x1 = (int) punto2.getX();
        //y1 = (int) punto2.getY();
        //System.out.println("Tinaco -> x: " + x + ", y: " + y);
        System.out.println("Termometro -> x: " + x + ", y: " + y);
        //System.out.println("Lampara -> x: " + x + ", y: " + y);
        //System.out.println("x1: " + x1 + ", y1: " + y1);
        imagen = getGraphics();
        new Thread(() -> {
            llenadoTer();
        }).start();
//        new Thread(() -> {
//            vaciado();
//        }).start();
        new Thread(() -> {
            sensor();
        }).start();
    }
    
    @Override
    public void paint(Graphics g) {
        
            if (inicio == 0) {
                super.paint(g);
                g.drawImage(background, x, y, this);
                
//                g.setColor(new Color(255, 255 , 255, 255));
                g.clearRect(x, y, 100, (int)lleno1-i);
                
                
                
                g.drawImage(foreground, x, y, this);
                
                llenadoPCT2 = (int) (i/lleno1*100);
            }
            //super.paint(g2);
            inicio = (inicio + 1) % 2;
            
//            Color col = new Color(255, 153, 153, 90);
//            g.setColor(col);
//            g.fillOval (x+20, y+185, 80, 80); //a lograr
//            g.fillOval (x+20, y+220, 80, 80 //medio
//            g.fillOval (x+20, y+264, 80, 80); //principio
//            //g.fillOval (x1+40, y1+40, 40, 150);
//            g.fillRoundRect(x1+43, y1+43, 35, 145, 35, 35);
        
    }

    public static void pause(long timeInMilliSeconds) {
        try {
            Thread.sleep(timeInMilliSeconds);

//        long timestamp = System.currentTimeMillis();
//
//        do {
//
//        } while (System.currentTimeMillis() < timestamp + timeInMilliSeconds);
        } catch (InterruptedException ex) {
            Logger.getLogger(frmTinaco.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void llenadoTer() {
        while (true) {
            try {
                Thread.sleep(30);
                if (llenandoTer) {
                    i++;
                    if(i>lleno1)
                        i=(int)lleno1;
                    paint(imagen);                   
                }
                if(vaciandoTer){
                    i--;
                    if(i<0)
                        i=0;
                    paint(imagen);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(frmTinaco.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void sensor() {
        try {
            Thread.sleep(3000);
            llenandoTer = true;
            
            while (true) {
                Thread.sleep(30);
                System.out.println("PCT: " + llenadoPCT2);
                if (llenadoPCT2 >= 100) { //Apagar sensor
                    llenandoTer = false;
                    vaciandoTer = true;
                    System.out.println("mensaje de termometro lleno");
                    return;
                }
                
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(frmTinaco.class.getName()).log(Level.SEVERE, null, ex);
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

        lblTermometro = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(lblTermometro, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 150, 140, 250));

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
            java.util.logging.Logger.getLogger(frmTermometro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmTermometro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmTermometro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmTermometro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmTermometro().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblTermometro;
    // End of variables declaration//GEN-END:variables
}
