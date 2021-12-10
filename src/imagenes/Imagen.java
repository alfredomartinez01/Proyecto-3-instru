/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package imagenes;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author kit_5
 */
public class Imagen {

    private ImageIcon imagen = null;
    private String nombre = "";
    private String location = "";

    public Imagen() {

    }

    public Imagen(String nombre) {
        this.nombre = nombre;
        this.location = toRelative(this.nombre);
        this.imagen = new ImageIcon(location);
    }

    public Imagen(ImageIcon imagen) {
        this.imagen = imagen;
    }

    public ImageIcon getImageIcon() {
        return imagen;
    }

    public ImageIcon getImageIcon(String nombre) {
        return imagen;
    }
    public String getLocation() {
        return location;
    }

    // Función correctora de location 1
    private String toRelative(String name) {
        URI p1 = null; // Variable de apoyo
        String directory = ".\\src\\main\\resources";
        File file = new File(directory);
        File[] archivos = file.listFiles();
        
        /*
        for (File fl : archivos) {
            System.out.println(fl.getName());
        }*/
        
        for(int i = 0; i<archivos.length; i++){
            if(archivos[i].getName().equals(name)){
                p1 = archivos[i].toURI(); // Cambia a URI primero
                URL p2 = null;
                
                try {
                    p2 = p1.toURL(); // Después cambia a URL
                } catch (MalformedURLException ex) {
                    System.out.println(ex);
                }        
                return archivos[i].getPath();
            }
        }
        return "";
    }

    public static void main(String args[]) {
        System.out.println(new Imagen("admin.png").getLocation());
        
        //Imagen img = new Imagen("fondo1.png");
        //System.out.println(img.getImageIcon().getIconWidth());
    }
}
