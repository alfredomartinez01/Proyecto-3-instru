/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Animacion;

import Conexion.Conexion;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author Reina
 */
public class frmAnimacion extends javax.swing.JFrame {

    /*Variables para control de imagen con color (background) e imagen sin fondo (foreground)*/
    private BufferedImage backgroundTin;
    private BufferedImage foregroundTin;
    private BufferedImage backgroundTer;
    private BufferedImage foregroundTer;
    private BufferedImage backgroundLam;
    private BufferedImage foregroundLam;

    /*Declaracion de variables para puntos de localizacion de labels*/
    Point puntoTin = new Point();
    Point puntoTer = new Point();
    Point puntoLam = new Point();
    int x, y, x1, y1, x2, y2;

    int inicio = 0; //Variable que ayuda a que solo se haga el paint una vez

    /*Declaracion de alturas de llenado (En base a las imagenes)*/
    float llenoTin = 220; //Altura de lleno tinaco
    float llenoTer = 245; //Altura de lleno termometro
    float llenoLam = 248; //Altura de lleno lampara

    /*Declaracion de variables para conocer el valor de llenado segun el sensor (funciona para simulación sin conexion a proteus)*/
    int llenadoPCT = 100; //Esta vacio el tinaco
    int llenadoPCT1 = 0; //Esta vacio termometro
    int llenadoPCT2 = 0; //Esta vacio lampara

    /*Declaracion de variables de control para el llenado o vaciado (Llendo de uno en uno, posiblemente con la conexión no se necesite)*/
    boolean llenandoTin = false;
    boolean vaciandoTin = false;
    boolean llenandoTer = false;
    boolean vaciandoTer = false;
    boolean llenandoLam = false;
    boolean vaciandoLam = false;

    /*Variables booleanas que funcionen como indicadores de sensor activado (Se tiene que recibir la detección del mismo desde la conexion)*/
    boolean activeTin = true;
    boolean activeTer = true;
    boolean activeLam = true;

    int iTi, iTe, iL; //Variable que ayuda al pintado (viene siendo el valor que se reciba del sensor con un algun calculo de ajuste)
    Graphics imagen; //Variable auxiliar que ayuda a tomar los graficos del Jframe

    private Conexion p_dyt = null;
    private Conexion p_l = null;

    private int distancia = 0;
    private int temperatura = 0;
    private int luz = 0;

    /**
     * Creates new form frmAnimacion
     */
    public frmAnimacion(Conexion com6, Conexion com7) {
        p_dyt = com6;
        p_l = com7;

        initComponents();
        /*Estableciendo tamaño, color y posición de la ventana (JFrame)*/
        setSize(1111, 673);
        getContentPane().setBackground(new Color(204, 153, 255));
        setLocationRelativeTo(null);
        
        /*Mostramiento de mensaje de cargando puertos, si aun no se acaba la conexión con los mismos*/
        if (p_dyt == null && p_l == null) {
            lblCargando.setText("Cargando puertos...");
        } else {
            lblCargando.setText("");
        }

        /*Asignando valores (ruta de imagen) a los background y foreground*/
        try {
            foregroundTin = ImageIO.read(new File(".\\src\\imagenes\\tinaco4.png"));
            backgroundTin = ImageIO.read(new File(".\\src\\imagenes\\tinacoColor.png"));
            foregroundTer = ImageIO.read(new File(".\\src\\imagenes\\termometro3.png"));
            backgroundTer = ImageIO.read(new File(".\\src\\imagenes\\terColor.png"));
            foregroundLam = ImageIO.read(new File(".\\src\\imagenes\\lampara.png"));
            backgroundLam = ImageIO.read(new File(".\\src\\imagenes\\lamparaColor.png"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        /*Obtencion de puntos de localizacion de los label*/
        puntoTin = lblTinaco.getLocation();
        puntoTer = lblTermometro.getLocation();
        puntoLam = lblLampara.getLocation();

        /*Asignación de coordenadas x y y en entero de cada punto obtenido (Orden: Tinaco, Termometro, Lampara)*/
        x = (int) puntoTin.getX();
        y = (int) puntoTin.getY();

        x1 = (int) puntoTer.getX();
        y1 = (int) puntoTer.getY();

        x2 = (int) puntoLam.getX();
        y2 = (int) puntoLam.getY();

        /*Inicialización de i, realmente este es el valor que arrojara el sensor con un calculo de ajuste al pintado de la imagen*/
        iTi = 0;
        iTe = 0;
        iL = 110; //La i de lampara si o si empieza en 110 para que solo se pinte la parte de la lampara y no de la base
        
        /*Agregando el listener para detectar el cierre de la ventana*/
        addWindowListener (new WindowAdapter() {    
            public void windowClosing (WindowEvent e) { 
                p_dyt.desconectar();
                p_l.desconectar();
                System.exit(0);   
            }    
        });    

        /*Impresion de coordenadas de los label (solo por comprobacion)*/
        //System.out.println("Tinaco -> x: " + x + ", y: " + y);
        //System.out.println("Termometro -> x: " + x1 + ", y: " + y1);
        //System.out.println("Lampara -> x: " + x2 + ", y: " + y2);

        /*Creacion de hilos para realizar Simulación*/
        new Thread(() -> {
            sensor();
        }).start();
    }

    /*Sobreescritura del metodo paint, para realizar el pintado correspondiente*/
    @Override
    public void paint(Graphics g) {
        if (inicio == 0) {
            super.paint(g); //Hace que se pinte lo que ya esta en el Jframe
            /*Cuando el sensor Ultrasonico esta activado*/
            g.drawImage(backgroundTin, x, y, this);

            g.clearRect(x, y, 200, (int) llenoTin - iTi + 21);

            g.drawImage(foregroundTin, x, y, this);

            /*Cuando el sensor de Temperatura esta activado*/
            g.drawImage(backgroundTer, x1, y1, this);

            g.clearRect(x1, y1, 100, (int) llenoTer - iTe);

            g.drawImage(foregroundTer, x1, y1, this);

            llenadoPCT1 = (int) (iTe / llenoTer * 100);

            /*Cuando el sensor Fotoresistivo esta activado*/
            g.drawImage(backgroundLam, x2, y2, this);

            g.clearRect(x2, y2, 230, (int) llenoLam - iL);

            g.drawImage(foregroundLam, x2, y2, this);

            llenadoPCT2 = (int) (iL / llenoLam * 100);
        }
        inicio = (inicio + 1) % 2; //Calculo que devuelve un 1 o 0, para que solo se ejecute una vez la simulacion (es que antes se repetia 2 veces)
    }

    /*Funcion para crear las pausas en la animacion (Posiblemente no se necesite con la conexion)*/
    public static void pause(long timeInMilliSeconds) {
        try {
            Thread.sleep(timeInMilliSeconds);
        } catch (InterruptedException ex) {
            Logger.getLogger(frmTinaco.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*Funcion que ayuda a saber cuando y donde pintar el tinaco*/
    public void llenadoTin() {
        iTi = (int) (-2.268041 * distancia + 226.80412);
        repaint();
    }

    /*Funcion que ayuda a saber cuando y donde pintar el termometro*/
    public void llenadoTer() {
        iTe = (int) (2.45 * temperatura);
        repaint();
    }

    /*Funcion que ayuda a saber cuando y donde pintar la lampara*/
    public void llenadoLam() {
        iL = (int) (1.38 * luz + 110);
        repaint();
    }

    /*Funcion encargada de llevar el control de la animacion*/
    public void sensor() {
        try {
            //Thread.sleep(3000);
            llenandoTin = true;
            llenandoTer = true;
            llenandoLam = true;
            while (true) {
                Thread.sleep(500);

                /* Comprobacion que se estén leyendo, si no, muestra un mensaje en pantalla*/
                if (!p_dyt.getLeyendo()) {
                    lblMSGUltrasonico.setForeground(Color.red);
                    lblMSG_RTD.setForeground(Color.red);
                    lblMSGUltrasonico.setText("Puerto desconectado");
                    lblMSG_RTD.setText("Puerto desconectado");
                    lblUltrasonico.setText("");
                    lblRTD.setText("");
                } else {
                    lblMSGUltrasonico.setForeground(Color.black);
                    lblMSG_RTD.setForeground(Color.black);
                    lblMSGUltrasonico.setText("Valor recibido del sensor");
                    lblMSG_RTD.setText("Valor recibido del sensor");
                    lblUltrasonico.setText("" + distancia);
                    lblRTD.setText("" + temperatura);
                }

                if (!p_l.getLeyendo()) {
                    lblMSG_LDR.setForeground(Color.red);
                    lblMSG_LDR.setText("Puerto desconectado");
                    lblLDR.setText("");
                } else {
                    lblMSG_LDR.setForeground(Color.black);
                    lblMSG_LDR.setText("Valor recibido del sensor");
                    lblLDR.setText("" + luz);
                }
                                
                /*Control de pintado para cada sensor e imagen corresponidente*/
                /*Tinaco*/
                //System.out.println("PCT: " + p_dyt.getDistancia());
                activeTin = distancia != p_dyt.getDistancia();
                if (activeTin) {
                    distancia = p_dyt.getDistancia();
                    lblUltrasonico.setText("" + distancia);
                    llenadoTin();
                }

                if (p_dyt.getDistancia() <= 3) { //Apagar sensor ultrasonico
                    llenandoTin = false;
                    vaciandoTin = true;
                    //System.out.println("mensaje de tinaco lleno");
                }

                /*Termometro*/
                //System.out.println("PCT1: " + llenadoPCT1);
                activeTer = temperatura != p_dyt.getTemperatura();
                if (activeTer) {
                    temperatura = p_dyt.getTemperatura();
                    lblRTD.setText("" + temperatura);
                    llenadoTer();
                }

                System.out.println("PCT1: " + p_dyt.getTemperatura());
                if (p_dyt.getTemperatura() >= 100) { //Apagar sensor de temperatura
                    llenandoTer = false;
                    vaciandoTer = true;
                    //System.out.println("mensaje de termometro lleno");
                }
//
//                /*Lampara*/
//                //System.out.println("PCT2: " + llenadoPCT2);
                activeLam = luz != p_l.getLuz();
                if (activeLam) {
                    luz = p_l.getLuz();
                    lblLDR.setText("" + luz);
                    llenadoLam();
                }

                //System.out.println("PCT1: " + p_l.getLuz());
                if (p_l.getLuz() >= 100) { //Apagar sensor fotoresistivo
                    llenandoLam = false;
                    vaciandoLam = true;
                    //System.out.println("mensaje de lampara llena");
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        lblTinaco = new javax.swing.JLabel();
        lblTermometro = new javax.swing.JLabel();
        lblLampara = new javax.swing.JLabel();
        lblMSGUltrasonico = new javax.swing.JLabel();
        lblUltrasonico = new javax.swing.JLabel();
        lblCargando = new javax.swing.JLabel();
        lblRTD = new javax.swing.JLabel();
        lblMSG_LDR = new javax.swing.JLabel();
        lblLDR = new javax.swing.JLabel();
        lblMSG_RTD = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 153, 255));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Quicksand Medium", 1, 24)); // NOI18N
        jLabel1.setText("Sensor Fotoresistivo");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 110, -1, -1));

        jLabel2.setFont(new java.awt.Font("Quicksand Medium", 1, 24)); // NOI18N
        jLabel2.setText("LDR");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 150, -1, 30));

        jLabel3.setFont(new java.awt.Font("Quicksand Medium", 1, 24)); // NOI18N
        jLabel3.setText("Sensor Ultrasonico");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, -1));

        jLabel4.setFont(new java.awt.Font("Quicksand Medium", 1, 24)); // NOI18N
        jLabel4.setText("Sensor de Temperatura");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 110, -1, 30));

        jLabel5.setFont(new java.awt.Font("Quicksand Medium", 1, 24)); // NOI18N
        jLabel5.setText("RTD");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 150, -1, 30));

        jLabel6.setFont(new java.awt.Font("Quicksand", 1, 36)); // NOI18N
        jLabel6.setText("Simulación/Animación de sensores");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 10, -1, -1));
        getContentPane().add(lblTinaco, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, 270, 270));
        getContentPane().add(lblTermometro, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 230, 140, 250));
        getContentPane().add(lblLampara, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 230, 248, 271));

        lblMSGUltrasonico.setFont(new java.awt.Font("Quicksand Light", 1, 18)); // NOI18N
        lblMSGUltrasonico.setText("Valor Recibido del sensor");
        getContentPane().add(lblMSGUltrasonico, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 530, -1, -1));

        lblUltrasonico.setFont(new java.awt.Font("Quicksand SemiBold", 1, 18)); // NOI18N
        lblUltrasonico.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(lblUltrasonico, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 560, 70, 50));

        lblCargando.setFont(new java.awt.Font("Quicksand Light", 3, 18)); // NOI18N
        lblCargando.setForeground(new java.awt.Color(204, 0, 0));
        lblCargando.setText("Cargando puertos ...");
        getContentPane().add(lblCargando, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 70, -1, -1));

        lblRTD.setFont(new java.awt.Font("Quicksand SemiBold", 1, 18)); // NOI18N
        lblRTD.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(lblRTD, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 560, 60, 50));

        lblMSG_LDR.setFont(new java.awt.Font("Quicksand Light", 1, 18)); // NOI18N
        lblMSG_LDR.setText("Valor Recibido del sensor");
        getContentPane().add(lblMSG_LDR, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 530, -1, -1));

        lblLDR.setFont(new java.awt.Font("Quicksand SemiBold", 1, 18)); // NOI18N
        lblLDR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(lblLDR, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 560, 110, 50));

        lblMSG_RTD.setFont(new java.awt.Font("Quicksand Light", 1, 18)); // NOI18N
        lblMSG_RTD.setText("Valor Recibido del sensor");
        getContentPane().add(lblMSG_RTD, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 530, -1, -1));

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
            java.util.logging.Logger.getLogger(frmAnimacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmAnimacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmAnimacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmAnimacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmAnimacion(new Conexion("COM6"), new Conexion("COM7")).setVisible(true);
                
            } 
        });   
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel lblCargando;
    private javax.swing.JLabel lblLDR;
    private javax.swing.JLabel lblLampara;
    private javax.swing.JLabel lblMSGUltrasonico;
    private javax.swing.JLabel lblMSG_LDR;
    private javax.swing.JLabel lblMSG_RTD;
    private javax.swing.JLabel lblRTD;
    private javax.swing.JLabel lblTermometro;
    private javax.swing.JLabel lblTinaco;
    private javax.swing.JLabel lblUltrasonico;
    // End of variables declaration//GEN-END:variables
}
