package Conexion;

import gnu.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.TooManyListenersException;

public class Conexion implements SerialPortEventListener {

    enum Medicion {
        DISTANCIA,
        TEMPERATURA,
        LUZ
    }

    HashMap<String, CommPortIdentifier> portMap = new HashMap<String, CommPortIdentifier>(); // Mapa con esos puertos para elegir el nuestro
    SerialPort serialPort; // Identificador final del puerto que servirá para manejar toda la conexión y obtener los flujos

    private boolean conectado; // Variable para guardar si se conectó o no exitosamente al puerto
    private InputStream input; // Entrada del puerto
    private OutputStream output; // Salida del puerto

    int sensor = -1; // Variable que almacena el valor
    boolean esperaByte = false; // Variable que controla si espera un byte de sensor o no
    Medicion medicion; // Variable que indica que tipo de dato de está midiendo

    private int distancia = 0;
    private int temperatura = 0;
    private int luz = 0;

    private boolean leyendo = true;
    
    public static void main(String[] args) {
        Conexion p_dyt = new Conexion("COM6");
        Conexion p_l = new Conexion("COM7");
        System.out.println("Temperatura: " + p_dyt.getTemperatura());
        System.out.println("Distancia: " + p_dyt.getDistancia());
        System.out.println("Luz: " + p_l.getLuz());        
    }

    public Conexion(String puerto) {
        buscarPuertos();
        conectar(puerto);
        if (conectado == true) { // Comprobamos que se haya conectado correctamente al puerto
            if (iniciarFlujos() == true) { // Comprobamos que se creen correctamente los flujos
                initListener(); // Iniciamos los listeners para tener las interrupciones
            }
        }
    }

    public int getDistancia() {
        return distancia;
    }

    public int getTemperatura() {
        return temperatura;
    }
    
    public int getLuz() {
        return luz;
    }

    public boolean getLeyendo() {
        return leyendo;
    }

   

    /**
     * @brief Función que obtiene la lista de puertos y lo guardar en portMap
     */
    public void buscarPuertos() {
        Enumeration ports; // Enumeración de todos los puertos encontrados
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
    public void conectar(String puerto) {
        CommPortIdentifier selectedPortIdentifier; // Idenfiticador común del puerto sobre el hasmap
        selectedPortIdentifier = (CommPortIdentifier) portMap.get(puerto);
        CommPort commPort; // Identificador del puerto para abrir la conexión
        commPort = null;

        // Intentamos abrir la conexión 
        try {
            commPort = selectedPortIdentifier.open("Nivel de tinaco", 1000);
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
     * @brief Función que establece los parámetros de la conexión que se
     * comparten con la
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

            return true;
        } catch (IOException e) {
            System.out.println("Error al abrir Stream.");
            return false;
        }
    }

    /**
     * @brief Función que agregar listeners para poder manejar interrupciones
     * por recepciónn de datos
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

                sensor = 0xFF & readBuffer[0];
                //System.out.println("\r"+ sensor + " "+ medicion + " " + esperaByte); // Imprimimos lo leído

                if (!esperaByte) {

                    switch (sensor) {
                        case 255:
                            esperaByte = true;
                            medicion = Medicion.DISTANCIA;
                            break;
                        case 254:
                            esperaByte = true;
                            medicion = Medicion.TEMPERATURA;
                            break;
                        case 253:
                            esperaByte = true;
                            medicion = Medicion.LUZ;
                            break;
                        default:
                            esperaByte = false;
                            break;
                    }
                } else if (sensor < 200) {

                    switch (medicion) {
                        case DISTANCIA:
                            distancia = sensor;
                            break;
                        case TEMPERATURA:
                            temperatura = sensor;
                            break;
                        default:
                            luz = sensor;
                            break;
                    }
                    
                    //System.out.println("\r" + sensor + " " + medicion); // Imprimimos lo leído

                    esperaByte = false;
                    enviar(sensor);
                }

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
