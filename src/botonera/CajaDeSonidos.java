package botonera;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javazoom.jl.player.Player;
import javazoom.jl.decoder.JavaLayerException;

@SuppressWarnings("serial")
public class CajaDeSonidos extends JFrame{
	
    //crea un objeto de una clase interna anonima que se agraga como escuhador a los botones, esta clase esta en este archivo	
    ManejadorBoton manejadorBotones = new ManejadorBoton();
    config con = new config();
    
    private JButton boton[]; // en este arreglo estan los botones que se ven en la cuadricula
    private String origenSonido[]; // en este arreglo esta la ruta al sonido asociado a cada boton
    
    private JButton botonModificar, botonAgregar, botonActualizar;
    
    private int botonesSonidos = 0, botonesMod = 2, totalBotones = 2;
    
    private GridLayout gridCentral;
    private JPanel panelMaster;
    private JPanel panelCentral;
    private JPanel panelTools;
    JMenu menu;  
    JMenuItem i1, i2; 


    public CajaDeSonidos() throws IOException{
        super("Botonera por @martinblancotl");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
        panelMaster = new JPanel();
        panelMaster.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelMaster);
		panelMaster.setLayout(new BorderLayout(0, 0));
		
		panelCentral = new JPanel();
		panelMaster.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(new GridLayout(9, 9, 0, 0));
		
		panelTools = new JPanel();
		panelMaster.add(panelTools, BorderLayout.SOUTH);
        
		botonModificar = new JButton("Modificar botones");
		botonModificar.setToolTipText("Modifica las propiedades de los botones");
        
	    botonAgregar = new JButton("Nuevo boton");
	    botonAgregar.setToolTipText("Agrega un nuevo boton a la cuadricula");
	    
	    botonActualizar = new JButton("Actualizar botones");
	    botonActualizar.setToolTipText("Actualiza los botones");
        
        botonesConfig();
        
        menuPrincipal();
     	
        //ponel los botones modificar y agregar a la ventana
        panelTools.add(botonAgregar);
        panelTools.add(botonModificar);
        panelTools.add(botonActualizar);
        botonModificar.setBackground(Color.GRAY);
        botonActualizar.setBackground(Color.GRAY);
        botonAgregar.setBackground(Color.GRAY);
        panelCentral.setBackground(Color.CYAN);
        panelTools.setBackground(Color.CYAN);
        botonModificar.addActionListener(manejadorBotones);
        botonActualizar.addActionListener(manejadorBotones);
        botonAgregar.addMouseListener( new ManejadorClick() );
        botonAgregar.addActionListener(manejadorBotones);
         
        setIconImage(Toolkit.getDefaultToolkit().getImage("res/icon.png"));
   
    }
    
    public void botonesConfig() throws IOException{
    	//si se encuentra el archivo config.config carga los botones segun el sistema operativo
    	if (!(con.getConfig("Botones").equals("NO_config.config"))){
            botonesSonidos = Integer.parseInt(con.getConfig("Botones"));
            
            if(System.getProperty("os.name").toLowerCase().startsWith("windows")){
                boton = con.getBotonesWindows();
                origenSonido = con.getOrigenSonidosWindows();
            }
            else{
                boton = con.getBotonesUnix();
                origenSonido = con.getOrigenSonidosUnix();
            }
            
            totalBotones = botonesMod + botonesSonidos;
        }
        else{
            JOptionPane.showMessageDialog( null, "Parece que hay un problema al leer el archivo config.config o no se encuentran las carpetas Imagenes y Sonidos","AVISO", JOptionPane.WARNING_MESSAGE );
        }
        
        //los parametros del GridLayout es la manera de como se redimenciona automaticamente la cuadricula al quitar o poner botones
        gridCentral = new GridLayout( (int) Math.round(Math.sqrt(totalBotones)), (int) Math.ceil(Math.sqrt(totalBotones)),2,2 );
        
        //panelCentral = new JPanel();
        panelCentral.setLayout(gridCentral);
        //panelMaster.add(panelCentral,BorderLayout.CENTER);
        
        // si se encuentra el archivo config.config pone los botones en la ventana con su escuchador
        if (!(con.getConfig("Botones").equals("NO_config.config"))){
            for(int i=0; i < botonesSonidos; i++){
                if (!(origenSonido[i].equals("NADA"))){
                    panelCentral.add (boton[i]);
                    boton[i].addActionListener(manejadorBotones);
                    boton[i].setBackground(Color.WHITE);
                }
            }
        }
    }
    
    public void menuPrincipal(){
        JMenuBar mb=new JMenuBar();  
      	menu=new JMenu("Menu");  
      	i1=new JMenuItem("Item 1");  
      	i2=new JMenuItem("Item 2");   
      	menu.add(botonModificar); 
      	menu.addSeparator();
      	menu.add(botonAgregar);  
      	mb.add(menu); 
      	setJMenuBar(mb);
    }
    
    public void actualizar() throws IOException{
    	 for(int i=0; i < botonesSonidos; i++){
    		 panelCentral.remove(boton[i]);
    	 }
    	 panelCentral.setVisible(false);
    	 botonesConfig();
    	 panelCentral.setVisible(true);
    }
 
    // estos dos metodos estan hechos para desactivar los botones cuando se reproduce un sonido, es una posible mejora a futuro
    public void desactivarBotones(){
    	for (int i=0;i<botonesSonidos;i++){
    		boton[i].setEnabled(false);
    	}
    }
    
    public void activarBotones(){
    	for (int i=0;i<botonesSonidos;i++){
    		boton[i].setEnabled(true);
    	}
    }

    public static void main(String[] args) throws IOException {
    	/*Este try-catch es para poner a la interfaz grafica una "skin" de "nimbus" (hace que se vea "bonita" la interfaz).
        Esta manera de hacerlo se encuentra en la pagina de oracle
        */
    	try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
            }
    	} 
    	catch (Exception e) {
    		// If Nimbus is not available, you can set the GUI to another look and feel.
    	}

    	CajaDeSonidos sistema = new CajaDeSonidos();
    	sistema.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    	sistema.setSize( 900, 700 );
    	sistema.setVisible( true );
    	sistema.setLocationRelativeTo(null);

    }
    
    private class ManejadorClick extends MouseAdapter{
        public void mouseClicked( MouseEvent evento ){
            
            //SwingUtilities.isRightMouseButton(evento) 
            //SwingUtilities.isLeftMouseButton(evento)
            //Scroll click
            if (evento.isAltDown()/*SwingUtilities.isMiddleMouseButton(evento)*/ ){             
                JOptionPane.showMessageDialog( null, "Al menos la primer version de esta caja de sonidos su ubica en:\n\ngithub.com/JuanMX/caja-de-sonidos\n\nCon una licencia GPL-3.0","Soy un mensaje escondido !", JOptionPane.WARNING_MESSAGE );
            }
            //else if  ( evento.isMetaDown() ){}
            
            /*
            else{
                AgregarSonido nuevoSonido = new AgregarSonido();
            }*/
        }
    }


    private class ManejadorBoton implements ActionListener{

        public void actionPerformed( ActionEvent evento ){
            
            //recorre el arreglo de botones buscando cual se presiono para reproducir el sonido, no es tan tardado como se pensaria
            for(int i=0; i < botonesSonidos; i++){
                desactivarBotones();
                if (evento.getSource() == boton[i]){
                    
                    try{
                        /*
                        // esta es una manera de reproducir un sonido que al final no se uso
                        InputStream ubicacionSonido;
                        ubicacionSonido = getClass().getResourceAsStream(origenSonido[i] );
                        Player player;
                        BufferedInputStream bufferEntrada = new BufferedInputStream(ubicacionSonido);
                        player = new Player(bufferEntrada);
                        player.play();
                        */
                        File file = new File (origenSonido[i]);
                        FileInputStream fileInput = new FileInputStream(file.getCanonicalPath());
                        BufferedInputStream bufferInput = new BufferedInputStream(fileInput);
                        
                        Player player = new Player (bufferInput);
                        player.play();
                        //player.stop();
                            
                    }
                    catch(JavaLayerException e){
                        e.printStackTrace();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(CajaDeSonidos.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(CajaDeSonidos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                activarBotones();
            }
            //evento para los botones modificar y agregar botones
            
            if (evento.getSource()==botonAgregar){
                desactivarBotones();
                new AgregarSonido();
                activarBotones();
            }
            
            if (evento.getSource()==botonModificar){
                //String nada[] = null;
            	//dispose();
                desactivarBotones();
                try {
                    new GridSonidos();
                } catch (IOException ex) {
                    Logger.getLogger(CajaDeSonidos.class.getName()).log(Level.SEVERE, null, ex);
                }
                activarBotones();
            }
            if (evento.getSource()==botonActualizar){
                try {
					actualizar();
				} catch (IOException e) {
					Logger.getLogger(CajaDeSonidos.class.getName()).log(Level.SEVERE, null, e);
				}
            }
        }
    }
}

