/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Animacion;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Reina
 */
public class frmLampara extends javax.swing.JFrame {
    Point puntoLamp = new Point();
    int x, y;
    private int inicio = 0;
    boolean llenandoLamp = false;
    boolean vaciandoLamp = false;
    float lleno1 = 248; //Altura de lleno lampara
    int llenadoPCT2 = 0; //Esta vacio
    
    
    int fila;
    int i;
    Graphics imagen;
    private BufferedImage background;
    private BufferedImage foreground;
    /**
     * Creates new form Lampara
     */
    public frmLampara() {
    
    try {
                foreground = ImageIO.read(getClass().getResource("/imagenes/lampara.png"));
                background = ImageIO.read(getClass().getResource("/imagenes/lamparaColor.png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        initComponents();
        setSize(720, 500);
        setLocationRelativeTo(null);
        puntoLamp = lblLampara.getLocation();
        //puntoLam = lblLmpara.getLocation();
        //punto2 = lblMedida.getLocation();
        x = (int) puntoLamp.getX();
        y = (int) puntoLamp.getY();

        i = 110;
        //x1 = (int) punto2.getX();
        //y1 = (int) punto2.getY();
        //System.out.println("Tinaco -> x: " + x + ", y: " + y);
        //System.out.println("Termometro -> x: " + x + ", y: " + y);
        System.out.println("Lampara -> x: " + x + ", y: " + y);
        //System.out.println("x1: " + x1 + ", y1: " + y1);
        imagen = getGraphics();
        new Thread(() -> {
            llenadoLamp();
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
        double c;
            if (inicio == 0) {
                super.paint(g);
                g.drawImage(background, x, y, this);
                
//                g.setColor(new Color(255, 255 , 255, 255));
                g.clearRect(x, y, 230, (int)lleno1-i);
                
                
                
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
    
    public void llenadoLamp() {
        while (true) {
            try {
                Thread.sleep(30);
                if (llenandoLamp) {
                    i++;
                    if(i>lleno1)
                        i=(int)lleno1;
                    paint(imagen);                   
                }
                if(vaciandoLamp){
                    i--;
                    if(i<110)
                        i=110;
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
            llenandoLamp = true;
            
            while (true) {
                Thread.sleep(30);
                System.out.println("PCT: " + llenadoPCT2);
                if (llenadoPCT2 >= 100) { //Apagar sensor
                    llenandoLamp = false;
                    vaciandoLamp = true;
                    System.out.println("mensaje de lampara llena");
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

        lblLampara = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(lblLampara, new org.netbeans.lib.awtextra.AbsoluteConstraints(332, 120, 248, 271));

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
            java.util.logging.Logger.getLogger(frmLampara.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmLampara.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmLampara.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmLampara.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmLampara().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblLampara;
    // End of variables declaration//GEN-END:variables
}
