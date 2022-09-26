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
public class ModificaPropiedades extends JFrame{

	private int largoTextos = 35;
    private JCheckBox checkImagen, checkTip;
    private String id, tmpTextImg, tmpTextTip;
    private JLabel labelSonido, labelNombre, labelImagen, labelTip, labelEliminar;
    private JTextField textSonido, textNombre, textImagen, textTip;
    private JButton botonImagen, botonSonido, botonGuardar, botonEliminar;
    private GridBagLayout gridCentral, gridSur;
    private GridBagConstraints restricciones;
    private JPanel panelCentral, panelSur;
    
    //el contenido del constructor es la interfaz grafica, botones, campos de texto entre otros
    public ModificaPropiedades(String sonido, String nombre, String imagen, String toolTip, String idBoton){
        
        super("Cambio de propiedades");
        setIconImage(Toolkit.getDefaultToolkit().getImage("res/icon.png"));
        id = idBoton;
        labelSonido = new JLabel("Origen del sonido");
        labelNombre = new JLabel("Nombre del boton");
        labelImagen = new JLabel("Origen de la Imagen");
        labelTip = new JLabel("Frase de ayuda");
        labelEliminar = new JLabel("Tambien puedes:     ");
        
        botonImagen = new JButton("Nueva imagen");
        botonSonido = new JButton("Nuevo sonido");
        botonGuardar = new JButton("Guardar");
        botonGuardar.setBackground(new Color( 182, 239, 226 ));
        botonEliminar = new JButton("Reiniciar todos los valores");
        botonEliminar.setBackground(new Color( 246, 81, 81 ));
        
        textSonido = new JTextField(largoTextos);
        textSonido.setEnabled(false);
        textNombre = new JTextField(largoTextos);
        textNombre.setToolTipText("Si en el boton solo quieres la imagen deja este campo en blanco");
        textImagen = new JTextField(largoTextos);
        textImagen.setEnabled(false);
        textTip = new JTextField(largoTextos);
        textTip.setToolTipText("Esta es una frase de ayuda");
        
        checkImagen = new JCheckBox ("Sin imagen");
        checkTip = new JCheckBox ("Sin frase de ayuda");
                
        gridCentral = new GridBagLayout();
        gridSur = new GridBagLayout();
        
        panelCentral = new JPanel(); 
        panelCentral.setLayout(gridCentral);
        restricciones = new GridBagConstraints();
        
        panelSur = new JPanel();
        panelSur.setLayout(gridSur);
        
        
        textSonido.setText(sonido);
        textNombre.setText(nombre);
        textImagen.setText(imagen);
        textTip.setText(toolTip);
        
        agregarComponente(labelNombre, 0, 0, 1, 1, gridCentral, panelCentral);
        agregarComponente(textNombre, 0, 1, 3, 1, gridCentral, panelCentral);
        
        agregarComponente(labelTip, 1, 0, 1, 1, gridCentral, panelCentral);
        agregarComponente(textTip, 1, 1, 3, 1, gridCentral, panelCentral);
        agregarComponente(checkTip, 1 , 4, 1, 1,gridCentral, panelCentral);
        
        agregarComponente(labelSonido, 2, 0, 1, 1, gridCentral, panelCentral);
        agregarComponente(textSonido, 2, 1, 3, 1, gridCentral, panelCentral);
        agregarComponente(botonSonido, 2, 4, 1, 1, gridCentral, panelCentral);
        
        agregarComponente(labelImagen, 3, 0, 1, 1, gridCentral, panelCentral);
        agregarComponente(textImagen, 3, 1, 3, 1, gridCentral, panelCentral);
        agregarComponente(botonImagen, 3, 4, 1, 1, gridCentral, panelCentral);
        agregarComponente(checkImagen, 3, 5 , 1, 1, gridCentral, panelCentral);
        
        agregarComponente(botonGuardar, 4, 0, 1, 1, gridCentral, panelCentral);
        
        
        
        agregarComponente(labelEliminar, 0, 0, 2, 1, gridSur, panelSur);
        agregarComponente(botonEliminar, 0, 3, 2, 1, gridSur, panelSur);
        
        add( panelCentral, BorderLayout.CENTER);
        add ( panelSur, BorderLayout.SOUTH);
        
        ManejadorBoton manejadorBotones = new ManejadorBoton();
        botonImagen.addActionListener(manejadorBotones);
        botonSonido.addActionListener(manejadorBotones);
        botonEliminar.addActionListener(manejadorBotones);
        botonGuardar.addActionListener(manejadorBotones);
        
        ManejadorCheckBox manejadorChecks = new ManejadorCheckBox();
        checkImagen.addItemListener(manejadorChecks);
        checkTip.addItemListener(manejadorChecks);
        
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );   
        setSize( 850, 350 );
        setVisible( true );
        //setResizable(false);
        setLocationRelativeTo(null);
    }
    
    //metodo para agregar componentes a un grid bag, se le pasa como parametro las restricciones de cada componente, el GridBag y el panel donde se colocaran
    private void agregarComponente( Component componente, int fila, int columna, int anchura, int altura, GridBagLayout elGrid, JPanel elPanel){
        restricciones.gridx = columna; // establece gridx
        restricciones.gridy = fila; // establece gridy
        restricciones.gridwidth = anchura; // establece gridwidth
        restricciones.gridheight = altura; // establece gridheight
        
        elGrid.setConstraints( componente, restricciones ); // establece restricciones
        elPanel.add( componente ); // agrega el componente
             
    }
    //en esta clase estan las acciones de los checkbox, habilitar campos de texto y botones asi como rescatar los textos y mostrarlos nuevamente
    private class ManejadorCheckBox implements ItemListener{
        public void itemStateChanged( ItemEvent evento ){
            if(evento.getSource()==checkImagen){
                if(checkImagen.isSelected()){
                    tmpTextImg = textImagen.getText();
                    botonImagen.setEnabled(false);
                    textImagen.setText("NADA");
                }

                else{
                    
                    botonImagen.setEnabled(true);
                    textImagen.setText(tmpTextImg);
                }
            }
            if(evento.getSource()==checkTip){
                if(checkTip.isSelected()){
                    tmpTextTip = textTip.getText();
                    textTip.setEnabled(false);
                    textTip.setText("NADA");
                }
                else{
                    textTip.setEnabled(true);
                    textTip.setText(tmpTextTip);
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
                        
                }
            }
            //las acciones para el boton seleccionar sonido son parecidas a la de seleccionar imagen se usa un filechooser
            else if (evento.getSource()==botonSonido){
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Seleccione su sonido");//titulo de ventana del JFileChooser

                /*Permite ver archivos con extencion especifica*/
                fileChooser.setFileFilter(new FileNameExtensionFilter("mp3 (*.mp3)", "mp3"));
                int seleccion = fileChooser.showOpenDialog(null);

      
                if (seleccion == JFileChooser.APPROVE_OPTION) {
                        File fileToSave = fileChooser.getSelectedFile();
                        textSonido.setText("");//limpia el campo de texto antes de poenr una ruta al archivo nueva
                        textSonido.setText(fileToSave.getAbsolutePath());
                        
                }
            }
            
            //el boton eliminar (o el boton reinicio de valores pone en los campos de texto NADA y muestra el mensaje)
            else if (evento.getSource() == botonEliminar){
                textSonido.setText("NADA");
                textNombre.setText("NADA");
                textImagen.setText("NADA");
                textTip.setText("NADA");
                
                JOptionPane.showMessageDialog( null, "Al reiniciar los valores puedes:\n"
                		+ "- Guardar con el boton con valores vacios para eliminarlo\n"
                		+ "- Modificar los valores para obtener un boton nuevo","Reinicio de valores", JOptionPane.INFORMATION_MESSAGE );
            }
            
            else if (evento.getSource() == botonGuardar){
                // si hay un sonido guardalo
                if(!(textSonido.getText().equals("NADA"))){
                    
                    
                    config con = new config();
                    
                    File origenSonido;
                    File destinoSonido;
                    
                    origenSonido = new File (textSonido.getText());
                    //dependiendo de sistema operativo prepara la ruta relativa para copiar el archivo de sonido
                    if(System.getProperty("os.name").toLowerCase().startsWith("windows")){
                        destinoSonido = new File ("Sonidos\\"+origenSonido.getName());
                    }
                    else{
                        destinoSonido = new File ("./Sonidos/"+origenSonido.getName());
                    }
                    try {
                        // copia el arhivo de sonido y guarda los parametros en el archivo config.config
                        con.copiarArchivo(origenSonido, destinoSonido);
                        con.guardarConfig("SonidoU"+id, "./Sonidos/"+origenSonido.getName());
                        con.guardarConfig("SonidoW"+id, "Sonidos\\"+origenSonido.getName());
                        con.guardarConfig("Nombre"+id, textNombre.getText());
                        con.guardarConfig("Tooltip"+id, textTip.getText());
                        
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
                            destinoImagen = new File ("Imagenes\\"+origenImagen.getName());
                        }
                        else{
                            destinoImagen = new File ("./Imagenes/"+origenImagen.getName());
                        }
                        System.out.println(">>>>>"+origenImagen.getName()+"<<<<<<<");
                        
                        try {
                            //copia la imagen y guarda sus propiedades en el arhivo config.config
                            con.copiarArchivo(origenImagen, destinoImagen);
                            con.guardarConfig("ImagenU"+id, "./Imagenes/"+origenImagen.getName());
                            con.guardarConfig("ImagenW"+id, "./Imagenes/"+origenImagen.getName());
                        } catch (IOException ex) {
                            Logger.getLogger(AgregarSonido.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                    else{
                        // si no hay una imagen se guarda NADA en el archivo config.config
                        con.guardarConfig("ImagenU"+id, textImagen.getText());
                        con.guardarConfig("ImagenW"+id, textImagen.getText());
                            
                    }
                    JOptionPane.showMessageDialog( null, "Se ha modificado un boton\nActualiza la botonera para ver los cambios","AGREGADO", JOptionPane.INFORMATION_MESSAGE );
                    dispose();
                }
                //si no hay un archivo de sonido seleccionado pone NADA en el archivo config.config y correr el programa no lo pone en la ventana
                else{
                    config con = new config();
                    int totalBotones=0;
                    try {
                        //si esta el archivo config.config obtiene la cantidad de botones
                        if (!(con.getConfig("Botones").equals("NO_config.config"))){
                            totalBotones = Integer.parseInt(con.getConfig("Botones"));
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ModificaPropiedades.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //pone NADA para que asi no se muestre el boton la siguiente vez que se corra el programa
                    con.guardarConfig("SonidoU"+id, "NADA");
                    con.guardarConfig("SonidoW"+id, "NADA");
                    con.guardarConfig("Botones", Integer.toString(totalBotones-1) );//resta uno al total de botones
                    JOptionPane.showMessageDialog( null, "Se ha eliminado este boton\nActualiza la botonera para ver los cambios","ELIMINADO", JOptionPane.INFORMATION_MESSAGE );
                    dispose();
                }
            }
        }
    }
}