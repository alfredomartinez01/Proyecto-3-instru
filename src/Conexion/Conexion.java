package Conexion;

import gnu.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.TooManyListenersException;

public class Conexion implements SerialPortEventListener {
    
    final String puerto = "COM6"; // Puerto virtual a conectarse
    
    Enumeration ports; // Enumeración de todos los puertos encontrados
    HashMap<String, CommPortIdentifier> portMap = new HashMap<String, CommPortIdentifier>(); // Mapa con esos puertos para elegir el nuestro
    CommPortIdentifier selectedPortIdentifier; // Idenfiticador común del puerto sobre el hasmap
    CommPort commPort; // Identificador del puerto para abrir la conexión
    
    SerialPort serialPort; // Identificador final del puerto que servirá para manejar toda la conexión y obtener los flujos
        
    int TIMEOUT = 1000; // Tiempo de espera para la creación de los flujos
    private boolean conectado; // Variable para guardar si se conectó o no exitosamente al puerto
    private InputStream input; // Entrada del puerto
    private OutputStream output; // Salida del puerto

    int distancia_sensor = -1;
    
    public static void main(String[] args) {
        Conexion pb = new Conexion();
        pb.buscarPuertos(); // buscamos los puertos
        pb.conectar(); // Conectamos al puerto establecido 

        if (pb.conectado == true) { // Comprobamos que se haya conectado correctamente al puerto
            if (pb.iniciarFlujos() == true) { // Comprobamos que se creen correctamente los flujos
                pb.initListener(); // Iniciamos los listeners para tener las interrupciones
            }
        }
    }
    
    /**
     * @brief Función que obtiene la lista de puertos y lo guardar en portMap 
     */
    public void buscarPuertos() {
        System.out.println("Puertos disponibles:");
        ports = CommPortIdentifier.getPortIdentifiers();
        
        // Recorremos todos los puertos y los vamos agregando al maapa solo si son seriales
        while (ports.hasMoreElements()) {
            CommPortIdentifier curPort = (CommPortIdentifier) ports.nextElement();

            if (curPort.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                System.out.println(curPort.getName());

                portMap.put(curPort.getName(), curPort);
            }
        }
        System.out.println("----------------------");
    }
    
    /**
     * @brief Función que conecta al puerto indicado en la variable puerto
     */
    public void conectar() {
        selectedPortIdentifier = (CommPortIdentifier) portMap.get(puerto);
        commPort = null;
        
        // Intentamos abrir la conexión 
        try {
            commPort = selectedPortIdentifier.open("Nivel de tinaco", TIMEOUT);
            serialPort = (SerialPort) commPort;
            configurarConexion();
            conectado = true;
            System.out.println("Conectado exitosamente a puerto " + puerto);
        } catch (PortInUseException e) {
            System.out.println("Puerto en uso.");
        } catch (Exception e) {
            System.out.println("Error al abrir puerto.");
        }
    }
    /**
     * @brief Función que establece los parámetros de la conexión que se comparten con la 
     */
    private void configurarConexion() throws IOException {
        int baudRate = 9600;
        try {
            serialPort.setSerialPortParams(baudRate,
                    SerialPort.DATABITS_8, // El tamaño de los datos, que será un byte
                    SerialPort.STOPBITS_1, // Byt de parada que va al final
                    SerialPort.PARITY_NONE); // Paridad nula
            serialPort.setFlowControlMode(
                    SerialPort.FLOWCONTROL_NONE); // Sin control de flujo
        } catch (UnsupportedCommOperationException ex) {
            throw new IOException("Unsupported serial port parameter");
        }
    }
    /**
     * @brief Función que inicia el flujo de entrada y de salida 
    */
    public boolean iniciarFlujos() {
        try {
            input = serialPort.getInputStream();
            output = serialPort.getOutputStream();
            //enviar("HELLO"); // Enviamos un saludo para comprobar conexión
            
            return true;
        } catch (IOException e) {
            System.out.println("Error al abrir Stream.");
            return false;
        }
    }
    
    /**
     * @brief Función que agregar listeners para poder manejar interrupciones por recepciónn de datos
     */
    public void initListener() {
        try {
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
            
            System.out.println("Listo..");
        } catch (TooManyListenersException e) {
            System.out.println("Demasiados escuchas.");
        }
    }
    
    /**
     * @brief Función que escrube una cadena sobre el puerto
     */
    public void enviar(int aenviar) {
        try {
            output.write(aenviar);
        } catch (IOException ex) {
            System.out.println("Error al enviar informacion.");
        }
    }

    /**
     * @brief Función para la interrupción del puerto
     */
    @Override
    public void serialEvent(SerialPortEvent spe) {
        if (spe.getEventType() == SerialPortEvent.DATA_AVAILABLE) { // En caso de que haya bytes por leer
            
            byte[] readBuffer = new byte[1];
            try {
                int numBytes = 0;
                while (input.available() > 0) { // Leemos cada uno de los datos del puerto
                    numBytes = input.read(readBuffer); // Los guardamos en el arreglo de bytes y obtenemos el número
                }
             
                distancia_sensor = 0xFF&readBuffer[0];
                System.out.println("\r"+distancia_sensor); // Imprimimos lo leído
                enviar(distancia_sensor);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
    
    /**
     * @brief Función para desconectar del puerto
     */
    public void desconectar() {
        try {
            //enviar("GOODBYE");
            serialPort.removeEventListener();
            serialPort.close();
            input.close();
            output.close();
            conectado = false;
            System.out.println("Desconectado.");
        } catch (Exception e) {
            System.out.println("Error al desconectar.");
        }
    }

}
