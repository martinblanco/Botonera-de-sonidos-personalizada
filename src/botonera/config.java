package botonera;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
//import javax.swing.JOptionPane;


public class config {
    
    public static Properties prop = new Properties();
    
    //usa la clase properties que usa el modelo clave - valor
    public void guardarConfig(String elemento, String valor){
    
        try{
            prop.setProperty(elemento, valor);
            prop.store(new FileOutputStream("config.config"), null);
            
        }catch(IOException e){
        
        }
    
    }
    
    //usa la clase properties que usa el modelo clave - valor aqui se obtiene el valor dependiendo de la clave o retorna NO_config.config
    public String getConfig(String elemento) throws IOException{
        String valor = null;
        
        try{
            
            prop.load( new FileInputStream("config.config") );
            valor = prop.getProperty( elemento );
            
            /*
            if (valor==null){
            JOptionPane.showMessageDialog( null, "El archivo config.config esta corrupto o no se encuentran las carpetas de imagenes y sonidos\nPuede repararlo o descargar uno nuevo\nNO MODIFIQUE EL ARCHIVO SI NO CONOCE SU ESTRUCTURA","ERROR FATAL", JOptionPane.ERROR_MESSAGE );
                return "CORRUPTO";
            }*/
            
        }catch(IOException ex){
            //JOptionPane.showMessageDialog( null, "Parece que hay un problema al leer el archivo config.config","AVISO", JOptionPane.WARNING_MESSAGE );
            
            return "NO_config.config";
        }
        
        
        return valor;
    }
    
    //copia el archivo excepto cuando es el mismo archivo a la misma carpeta
    public void copiarArchivo(File source, File dest) throws FileNotFoundException, IOException{
        InputStream is = null;
        OutputStream os = null;
        if (!(source.getCanonicalPath().equals(dest.getCanonicalPath()))){
            //if(source.exists()){ este if-else para verificar si existe el archivo se debe poner en las clases que agregan o modifican sonidos 
                try {
                    is = new FileInputStream(source);
                    os = new FileOutputStream(dest);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = is.read(buffer)) > 0) {
                        os.write(buffer, 0, length);
                    }
                } finally {
                    is.close();
                    os.close();
                }
            //}
            /*
            else{
                JOptionPane.showMessageDialog( null, "Parece que el archivo de sonido o imagen no existe","ERROR", JOptionPane.ERROR_MESSAGE );
            }*/
        }
    }
    
    //Retorna los botones con su nombre, icono y tooltip para sistemas unix
    public JButton[] getBotonesUnix() throws IOException{
        int botonesSonidos = Integer.parseInt(getConfig("Botones"));
        JButton botonSonido[] = new JButton[botonesSonidos];
        String nombreBoton[] = new String[botonesSonidos];
        String origenImagen[] = new String[botonesSonidos];
        String toolTip[] = new String[botonesSonidos];
        Icon icono [] = new Icon[botonesSonidos];
       
        //carga en los areglos el contenido del archivo config
        for (int i = 0; i < botonesSonidos; i++){

            nombreBoton[i] = getConfig("Nombre"+Integer.toString(i));
            origenImagen[i] = getConfig("ImagenU"+Integer.toString(i));
            toolTip[i] = getConfig("Tooltip"+Integer.toString(i));

        }
        
        //pone las propiedades en los botones
        for (int i =0; i<botonesSonidos; i++){
            
            //pone la imagen en el boton si la hay
            if(origenImagen[i].equals("NADA")){
                botonSonido[i] = new JButton( nombreBoton[i] );
            }
            else{
               /// icono[i] = new ImageIcon( getClass().getResource(origenImagen[i]) );
                File f = new File (origenImagen[i]);
                 icono[i] = new ImageIcon( f.getCanonicalPath() );
                botonSonido[i] = new JButton(nombreBoton[i], icono[i]);
            }
            //pone el tooltip si lo hay
            if(!toolTip[i].equals("NADA")){
                botonSonido[i].setToolTipText(toolTip[i]);
            }
        }
        
        return botonSonido;
    }
    //Retorna los botones con su nombre, icono y tooltip para sistemas windows
    public JButton[] getBotonesWindows() throws IOException{
        int botonesSonidos = Integer.parseInt(getConfig("Botones"));
        JButton botonSonido[] = new JButton[botonesSonidos];
        String nombreBoton[] = new String[botonesSonidos];
        String origenImagen[] = new String[botonesSonidos];
        String toolTip[] = new String[botonesSonidos];
        Icon icono [] = new Icon[botonesSonidos];
       
        //carga en los areglos el contenido del archivo config
        for (int i = 0; i < botonesSonidos; i++){

            nombreBoton[i] = getConfig("Nombre"+Integer.toString(i));
            origenImagen[i] = getConfig("ImagenW"+Integer.toString(i));
            toolTip[i] = getConfig("Tooltip"+Integer.toString(i));

        }
        
        //pone las propiedades en los botones
        for (int i =0; i<botonesSonidos; i++){
            
            //pone la imagen en el boton si la hay
            if(origenImagen[i].equals("NADA")){
                botonSonido[i] = new JButton( nombreBoton[i] );
            }
            else{
               /// icono[i] = new ImageIcon( getClass().getResource(origenImagen[i]) );
                File f = new File (origenImagen[i]);
                 icono[i] = new ImageIcon( f.getCanonicalPath() );
                botonSonido[i] = new JButton(nombreBoton[i], icono[i]);
            }
            //pone el tooltip si lo hay
            if(!toolTip[i].equals("NADA")){
                botonSonido[i].setToolTipText(toolTip[i]);
            }
        }
        
        return botonSonido;
    }
    
    //retorna un arreglo String con las ubucaciones de los sonidos para sistemas unix
    
    public String[] getOrigenSonidosUnix() throws IOException{
        int botonesSonidos = Integer.parseInt(getConfig("Botones"));
        String origenSonido[] = new String[botonesSonidos];
        
        for (int i = 0; i < botonesSonidos; i++){

            origenSonido[i] = getConfig("SonidoU"+Integer.toString(i));

        }
        
        return origenSonido;
    }
    //retorna un arreglo String con las ubucaciones de los sonidos para sistemas windows
    
    public String[] getOrigenSonidosWindows() throws IOException{
        int botonesSonidos = Integer.parseInt(getConfig("Botones"));
        String origenSonido[] = new String[botonesSonidos];
        
        for (int i = 0; i < botonesSonidos; i++){

            origenSonido[i] = getConfig("SonidoW"+Integer.toString(i));

        }
        
        return origenSonido;
    }
    //retorna un arreglo String con las ubucaciones de las imagenes para sistemas unix
    public String[] getOrigenImagenesUnix() throws IOException{
        int botonesSonidos = Integer.parseInt(getConfig("Botones"));
        String origenImagen[] = new String[botonesSonidos];
        
        for (int i = 0; i < botonesSonidos; i++){

            origenImagen[i] = getConfig("ImagenU"+Integer.toString(i));

        }
        
        return origenImagen;
    }
    //retorna un arreglo String con las ubucaciones de las imagenes para sistemas windows
    public String[] getOrigenImagenesWindows() throws IOException{
        int botonesSonidos = Integer.parseInt(getConfig("Botones"));
        String origenImagen[] = new String[botonesSonidos];
        
        for (int i = 0; i < botonesSonidos; i++){

            origenImagen[i] = getConfig("ImagenW"+Integer.toString(i));

        }
        
        return origenImagen;
    }
}
