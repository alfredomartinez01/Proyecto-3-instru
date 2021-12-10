package Test;

import java.io.File;

import java.io.*;
import javax.swing.ImageIcon;

public class Test {
    
    public Test(){
        File img = new File(".\\src\\imagenes\\terColor.png");
        ImageIcon ic = new ImageIcon(img.getAbsolutePath());
        System.out.println(ic.getIconWidth());
    }
    
    public static void main(String args[]) {
        Test test = new Test();
    }
    
}
