package botonera;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;


@SuppressWarnings("serial")
public class AgregarSonido extends JFrame{
    
    private int largoText = 37, totalBotones = 0;
    private JLabel labelNombre, labelTip;
    private JButton botonImagen, botonSonido, botonGuardar;
    private JTextField textNombre, textImagen, textSonido,textTip;
    private JCheckBox checkImagen, checkTip;
    private GridBagLayout gridCentral;
    private GridBagConstraints restricciones;
    private JPanel panelCentral;
    private String nombreImagen, nombreSonido;
    private String tmpTextImg="NADA", tmpTextTip="";
    config con = new config();
    
    public AgregarSonido(){
        
        super("Nuevo sonido");
        setIconImage(Toolkit.getDefaultToolkit().getImage("res/icon.png"));
        // el contenido del constructor es la interfaz grafica, botones, campos de texto entre otros
        
        labelNombre = new JLabel("Nombre del boton");
        labelTip = new JLabel("Frase de ayuda");
        
        botonImagen = new JButton("Seleccionar imagen");
        botonImagen.setToolTipText("No es necesario una imagen");
        botonImagen.setEnabled(false);
        botonSonido = new JButton("Seleccionar sonido");
        botonGuardar = new JButton("Guardar");
        botonGuardar.setBackground(new Color( 182, 239, 226 ));
        
        textNombre = new JTextField(largoText);
        textNombre.setToolTipText("Si en el boton solo quieres la imagen deja este campo en blanco");
        textImagen = new JTextField(largoText);
        textImagen.setEnabled(false);
        textImagen.setText("NADA");
        textSonido = new JTextField(largoText);
        textSonido.setEnabled(false);
        textSonido.setText("NADA");
        textTip = new JTextField(largoText);
        textTip.setText("NADA");
        textTip.setEnabled(false);
        textTip.setToolTipText("Esta es una frase de ayuda");
        
        checkImagen = new JCheckBox ("Con imagen");
        checkTip = new JCheckBox ("Con frase de ayuda");
        
        gridCentral = new GridBagLayout();
        
        panelCentral = new JPanel(); 
        panelCentral.setLayout(gridCentral);
        restricciones = new GridBagConstraints();
        
        agregarComponente(labelNombre,0,0,1,1);
        agregarComponente(textNombre,0,1,3,1);
        
        agregarComponente(labelTip,1,0,1,1);
        agregarComponente(textTip,1,1,2,1);
        agregarComponente(checkTip,1,3,1,1);
        
        agregarComponente(botonSonido,2,0,1,1);
        agregarComponente(textSonido,2,1,3,1);
        
        agregarComponente(botonImagen,3,0,1,1);
        agregarComponente(textImagen,3,1,2,1);
        agregarComponente(checkImagen,3,3,1,1);
        
        
        
        agregarComponente(botonGuardar,4,0,1,1);
        
        add( panelCentral, BorderLayout.CENTER);
        
        ManejadorBoton manejadorBotones = new ManejadorBoton();
        botonImagen.addActionListener(manejadorBotones);
        botonSonido.addActionListener(manejadorBotones);
        botonGuardar.addActionListener(manejadorBotones);
        
        ManejadorCheckBox manejadorChecks = new ManejadorCheckBox();
        checkImagen.addItemListener(manejadorChecks);
        checkTip.addItemListener(manejadorChecks);
        
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );   
        setSize( 850, 300 );
        setVisible( true );
        //setResizable(false);
        setLocationRelativeTo(null);
    }
    
    //metodo para agregar componentes a un grid bag, se le pasa como parametro las restricciones de cada componente
    private void agregarComponente( Component componente, int fila, int columna, int anchura, int altura ){
        restricciones.gridx = columna; // establece gridx
        restricciones.gridy = fila; // establece gridy
        restricciones.gridwidth = anchura; // establece gridwidth
        restricciones.gridheight = altura; // establece gridheight
        gridCentral.setConstraints( componente, restricciones ); // establece restricciones
        panelCentral.add( componente ); // agrega el componente
    }
    
    //en esta clase estan las acciones de los checkbox, habilitar campos de texto y botones asi como rescatar los textos y mostrarlos nuevamente
    private class ManejadorCheckBox implements ItemListener{
        public void itemStateChanged( ItemEvent evento ){
            if(evento.getSource()==checkImagen){
                if(checkImagen.isSelected()){
                    textImagen.setText(tmpTextImg);
                    botonImagen.setEnabled(true);
                }

                else{
                    tmpTextImg = textImagen.getText();
                    textImagen.setText("NADA");
                    botonImagen.setEnabled(false);
                }
            }
            if(evento.getSource()==checkTip){
                if(checkTip.isSelected()){
                    textTip.setEnabled(true);
                    textTip.setText(tmpTextTip);
                }
                else{
                    tmpTextTip = textTip.getText();
                    textTip.setEnabled(false);
                    textTip.setText("NADA");
                }
            }
            
        }
    }
    
    private class ManejadorBoton implements ActionListener{
	
        public void actionPerformed( ActionEvent evento ){
            //al seleccionar el boton imagen se usa un filechooser para que el usuario la seleccione su imagen
            if (evento.getSource() == botonImagen){
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Seleccione su imagen");//titulo de ventana del JFileChooser

                /*Permite ver archivos con extencion especifica*/
                fileChooser.setFileFilter(new FileNameExtensionFilter("png (*.png)","png"));
                fileChooser.setFileFilter(new FileNameExtensionFilter("gif (*.gif)", "gif"));
                fileChooser.setFileFilter(new FileNameExtensionFilter("jpeg (*.jpeg)", "jpeg"));
                fileChooser.setFileFilter(new FileNameExtensionFilter("jpg (*.jpg)", "jpg"));

                int seleccion = fileChooser.showOpenDialog(null);

      
                if (seleccion == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        textImagen.setText("");//limpia el campo de texto antes de mostrar una nueva ruta al archivo de imagen
                        textImagen.setText(fileToSave.getAbsolutePath());
                        nombreImagen = fileToSave.getName();//obtiene el nombre del archivo, esta variable se usa al usar el boton guardar
                }
            }
            //las acciones para el boton seleccionar sonido son parecidas a la de seleccionar imagen
            else if (evento.getSource()==botonSonido){
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Seleccione su sonido");//titulo de ventana del JFileChooser

                /*Permite ver archivos con extencion especifica*/
                fileChooser.setFileFilter(new FileNameExtensionFilter("mp3 (*.mp3)", "mp3"));
                int seleccion = fileChooser.showOpenDialog(null);

      
                if (seleccion == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        textSonido.setText("");
                        textSonido.setText(fileToSave.getAbsolutePath());
                        nombreSonido = fileToSave.getName();
                }
            }
            // acciones para el boton guardar
            else if (evento.getSource()==botonGuardar){
                
                //Si hay un sonido seleccionado guardalo
                if(!(textSonido.getText().equals("NADA"))){
                    try {
                        // si hay un archivo config.config obtiene cuantos botones hay
                        if (!(con.getConfig("Botones").equals("NO_config.config"))){
                            totalBotones = Integer.parseInt(con.getConfig("Botones"));
                        }
                    } catch (IOException ex) {
                        
                        //Logger.getLogger(AgregarSonido.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    config con = new config();
                    
                    File origenSonido;
                    File destinoSonido;
                    
                    origenSonido = new File (textSonido.getText());
                    //dependiendo de sistema operativo prepara la ruta relativa para copiar el archivo de sonido
                    if(System.getProperty("os.name").toLowerCase().startsWith("windows")){
                        destinoSonido = new File ("Sonidos\\"+nombreSonido);
                    }
                    else{
                        destinoSonido = new File ("./Sonidos/"+nombreSonido);
                    }
                    try {
                        // copia el arhivo de sonido y guarda los parametros en el archivo config.config
                        con.copiarArchivo(origenSonido, destinoSonido);
                        con.guardarConfig("SonidoU"+Integer.toString(totalBotones), "./Sonidos/"+nombreSonido);
                        con.guardarConfig("SonidoW"+Integer.toString(totalBotones), "Sonidos\\"+nombreSonido);
                        con.guardarConfig("Nombre"+Integer.toString(totalBotones), textNombre.getText());
                        con.guardarConfig("Tooltip"+Integer.toString(totalBotones), textTip.getText());
                        
                    } catch (IOException ex) {
                        Logger.getLogger(AgregarSonido.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    //Si hay una imagen guardala
                    
                    if (!(textImagen.getText().equals("NADA"))){
                        File origenImagen;
                        File destinoImagen;
                        
                        origenImagen = new File(textImagen.getText());
                        //dependiendo del sistema operativo usa la ruta relativa para copiar el archivo
                        if(System.getProperty("os.name").toLowerCase().startsWith("windows")){
                            destinoImagen = new File ("Imagenes\\"+nombreImagen);
                        }
                        else{
                            destinoImagen = new File ("./Imagenes/"+nombreImagen);
                        }
                        
                        try {
                            //copia la imagen y guarda sus propiedades en el arhivo config.config
                            con.copiarArchivo(origenImagen, destinoImagen);
                            con.guardarConfig("ImagenU"+Integer.toString(totalBotones), "./Imagenes/"+nombreImagen);
                            con.guardarConfig("ImagenW"+Integer.toString(totalBotones), "./Imagenes/"+nombreImagen);
                        } catch (IOException ex) {
                            Logger.getLogger(AgregarSonido.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        
                    }
                    // si no hay una imagen se guarda NADA en el archivo config.config
                    else{
                            
                        con.guardarConfig("ImagenW"+Integer.toString(totalBotones), textImagen.getText());
                        con.guardarConfig("ImagenU"+Integer.toString(totalBotones), textImagen.getText());
                            
                    }
                    // al terminar de guardar el boton en el archivo config.config  copiar imagen y sonido aumento el numero de botones en uno
                    con.guardarConfig("Botones", Integer.toString(totalBotones+1));        
                    JOptionPane.showMessageDialog( null, "Actualiza la tabla para ver los cambios","AGREGADO", JOptionPane.INFORMATION_MESSAGE );
                    dispose();
                }
                //cuando no hay un sonido seleccionado muestra este mensaje
                else{
                    
                    JOptionPane.showMessageDialog( null, "Necesita seleccionar al menos el sonido","ERROR", JOptionPane.WARNING_MESSAGE );

                
                }
            }
        }
    }
}//Esta es la ventana con acciones para agregar un nuevo sonido al archivo config.config
