/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Animacion;

import imagenes.Imagen;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JFrame;

/**
 *
 * @author Reina
 */
public class frmTinaco extends JFrame {

    //public final static ImageIcon gracias = new Imagen("tinaco.png").getImageIcon();
    Point puntoTin = new Point();
    Point puntoTer = new Point();
    Point puntoLam = new Point();
    int x, y, x1, y1, x2, y2;
    private int inicio = 0;
    float lleno = 220; //Altura de lleno tinaco
    int llenadoPCT = 100; //Esta vacio
    boolean llenando = false;
    boolean vaciando = false;
    boolean llenandoTer = false;
    float lleno1 = 145; //Altura de lleno termometro
    int llenadoPCT2 = 0; //Esta vacio

    /*Indicadores de sensor*/
    boolean activeTin = true;
    boolean activeTer = false;
    boolean activeLam = false;

    int fila;
    int i;
    Graphics imagen;
    private BufferedImage background;
    private BufferedImage foreground;

    /**
     * Creates new form frmAnimacion
     */
    public frmTinaco() {
        try {
                foreground = ImageIO.read(getClass().getResource("/imagenes/tinaco4.png"));
                background = ImageIO.read(getClass().getResource("/imagenes/tinacoColor.png"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        initComponents();
        setSize(720, 500);
        setLocationRelativeTo(null);
        puntoTin = lblTinaco.getLocation();
        puntoTer = lblTermometro.getLocation();
        //puntoLam = lblLmpara.getLocation();
        //punto2 = lblMedida.getLocation();
        x = (int) puntoTin.getX();
        y = (int) puntoTin.getY();
        
        x1 = (int) puntoTer.getX();
        y1 = (int) puntoTer.getY();
        
        x2 = (int) puntoLam.getX();
        y2 = (int) puntoLam.getY();

        i = 0;
        //x1 = (int) punto2.getX();
        //y1 = (int) punto2.getY();
        System.out.println("Tinaco -> x: " + x + ", y: " + y);
        System.out.println("Termometro -> x: " + x1 + ", y: " + y1);
        //System.out.println("Lampara -> x: " + x + ", y: " + y);
        //System.out.println("x1: " + x1 + ", y1: " + y1);
        imagen = getGraphics();
        new Thread(() -> {
//            if(activeTin)
                llenado();
//            if(activeTer)
//                llenadoTer();
//            if(activeLam)
//                llenadoLam();
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
        if(!activeTin && !activeTer && !activeLam)
            super.paint(g);
        if (activeTin) {
            if (inicio == 0) {

                /*super.paint(g);
                fila = y + 100 + 182;
                for (int j = 0; j < i; j++) {
                    Color col = new Color(0, j / 3 + 9, j, 90);
                    g.setColor(col);
                    if (j < 182) {
                        g.drawLine(x + 20, fila, x + 205, fila);
                    } else if (j < lleno) {
                        g.drawLine(x + 20 + j - 182, fila, x + 387 - j, fila);
                    } else {
                        System.out.println("Mal funcionamiento, se paso del limite");
                    }
                    fila--;
                }*/
                super.paint(g);
                g.drawImage(background, x, y, this);
                
//                g.setColor(new Color(255, 255 , 255, 255));
                g.clearRect(x, y, 200, (int)lleno-i+21);
                
                
                
                g.drawImage(foreground, x, y, this);

                llenadoPCT = (int) ((lleno - i) / lleno * 100);
            }

            inicio = (inicio + 1) % 2;

            //g.fillRoundRect(x+20, y+100, 185, 182, 35, 35); //Cubre
            //g.fillRoundRect(460, 362, 206, 250, 30, 30);  
        }
        
        if(activeTer){
            if (inicio == 0) {
                super.paint(g);
                g.drawImage(background, x, y, this);
                
//                g.setColor(new Color(255, 255 , 255, 255));
                g.clearRect(x, y, 100, (int)lleno1-i);
                
                
                
                g.drawImage(foreground, x, y, this);
                
                llenadoPCT2 = (int) ((lleno1 + i) / lleno1 * 100);
            }

            inicio = (inicio + 1) % 2;
            
//            Color col = new Color(255, 153, 153, 90);
//            g.setColor(col);
//            g.fillOval (x1+20, y1+185, 80, 80);
//            //g.fillOval (x1+40, y1+40, 40, 150);
//            g.fillRoundRect(x1+43, y1+43, 35, 145, 35, 35);
        }
        
        if(activeLam){
            
        }
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

    public void llenado() {
        while (true) {
            try {
                Thread.sleep(20);
                if (llenando) {
                    i++;
                    paint(imagen);
                    
                }
                if (vaciando) {
                    i--;
                    paint(imagen);
                    
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(frmTinaco.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
    public void llenadoTer() {
        while (true) {
            try {
                Thread.sleep(20);
                if (llenandoTer) {
                    paint(imagen);
                    i++;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(frmTinaco.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void sensor() {
        try {
            Thread.sleep(3000);
            llenando = true;
            while (true) {
                Thread.sleep(20);
                System.out.println("PCT: " + llenadoPCT);
                if (llenadoPCT <= 3) { //Apagar sensor
                    llenando = false;
                    vaciando = true;
                    System.out.println("mensaje de tinaco lleno");
                    return;
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(frmTinaco.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void sensor2() {
        try {
            Thread.sleep(3000);
            llenandoTer = true;
            while (true) {
                Thread.sleep(20);
                System.out.println("PCT: " + llenadoPCT2);
                if (llenadoPCT2 <= 100) { //Apagar sensor
                    llenandoTer = false;
                    System.out.println("mensaje de termometro lleno");
                    return;
                }
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(frmTinaco.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /*public class label extends JLabel {

        public label(ImageIcon icono) {
            super(icono);
        }

        public void paint(Graphics g) {
            super.paint(g);
            int fila = 0;
            for (int rojo = 0; rojo <= 255; rojo++) {
                Color col = new Color(rojo, 0, 0);
                g.setColor(col);
                g.drawLine(0, fila, 800, fila);
                fila++;
            }
        }
    }*/
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTinaco = new javax.swing.JLabel();
        lblTermometro = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(lblTinaco, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 100, 270, 270));

        lblTermometro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/termometro3.png"))); // NOI18N
        getContentPane().add(lblTermometro, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 110, -1, 250));

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
            java.util.logging.Logger.getLogger(frmTinaco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmTinaco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmTinaco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmTinaco.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmTinaco().setVisible(true);
            }
        });
//        new Thread(() -> {
//            frm = new frmTinaco();
//            
//        }).start();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblTermometro;
    private javax.swing.JLabel lblTinaco;
    // End of variables declaration//GEN-END:variables
}
