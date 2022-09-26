package botonera;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class GridSonidos extends JFrame{
    
	//crea un objeto de una clase interna anonima que se agraga como escuhador a los botones, esta clase esta en este archivo	
    CajaDeSonidos botonera = new CajaDeSonidos();
	ManejadorBoton manejadorBotones = new ManejadorBoton();
    config con = new config();
    private JButton boton[];
    private String origenSonido[];
    private String origenImagen[];
    private JButton volver;
    private String nombre="", tip="";
    
    private int totalBotones = 2, botonesSonidos = 0, botonesMod = 2;
            
    private GridLayout gridCentral;
    
    private JPanel panelCentral;
    private JPanel panelMaster;
    private JPanel panelTools;

    // es la interfaz grafica parecida a la caja de sonidos solo que se usa un arreglo con las rutas a la imagen
    // ademas el escuchador de los botones envia los paramateros de los botones a otra clase para ser modificados
    public GridSonidos() throws IOException{
        super("Modificar Botones");
        
        setIconImage(Toolkit.getDefaultToolkit().getImage("res/icon.png"));
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
        
		volver = new JButton("Volver");
		volver.setToolTipText("Volver a la famosa botonera");
        
        //si se encuentra el archivo config.config carga los botones segun el sistema operativo
        if (!(con.getConfig("Botones").equals("NO_config.config"))){
            botonesSonidos = Integer.parseInt(con.getConfig("Botones"));
            
            if(System.getProperty("os.name").toLowerCase().startsWith("windows")){
                boton = con.getBotonesWindows();
                origenSonido = con.getOrigenSonidosWindows();
                origenImagen = con.getOrigenImagenesWindows();
            }
            else{
                boton = con.getBotonesUnix();
                origenSonido = con.getOrigenSonidosUnix();
                origenImagen = con.getOrigenImagenesUnix();
            }
            
            totalBotones = botonesMod + botonesSonidos;
        }
        
        gridCentral = new GridLayout( (int) Math.round(Math.sqrt(totalBotones)), (int) Math.ceil(Math.sqrt(totalBotones)),2,2 );
        
        panelCentral.setLayout(gridCentral);
        
        if (!(con.getConfig("Botones").equals("NO_config.config"))){
            for(int i=0; i < botonesSonidos; i++){
                if (!(origenSonido[i].equals("NADA"))){
                    panelCentral.add (boton[i]);
                    boton[i].addActionListener(manejadorBotones);
                }
            }
        }
        
        JLabel lblNewLabel = new JLabel("MODIFICAR BOTONES");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 15));
		panelMaster.add(lblNewLabel,BorderLayout.NORTH);
		
		volver.addActionListener(manejadorBotones);
        panelTools.add(volver);
        panelCentral.setBackground(Color.GRAY);
        panelTools.setBackground(Color.GRAY);
        panelMaster.setBackground(Color.GRAY);
        
        setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE );   
        setSize(600, 500);
        setVisible( true );
        setLocationRelativeTo(null);
    }
        
    private class ManejadorBoton implements ActionListener{

        public void actionPerformed( ActionEvent evento ){
            //recorre el arreglo de botones buscando cual se presiono para mandar sus propiedades a otra clase, no es tan tardado como se pensaria
        	for(int i = 0; i < botonesSonidos; i++){
                if (evento.getSource() == boton[i]){
                    try {
                        nombre = con.getConfig("Nombre"+Integer.toString(i));
                        tip = con.getConfig("Tooltip"+Integer.toString(i));
                        
                        @SuppressWarnings("unused")
						ModificaPropiedades newprops = new ModificaPropiedades(origenSonido[i], nombre, origenImagen[i], tip, Integer.toString(i));
                    } catch (IOException ex) {
                        Logger.getLogger(GridSonidos.class.getName()).log(Level.SEVERE, null, ex);
                    	}
               }
            }
            if (evento.getSource() == volver){
            	dispose();
            }
        }  
    }
    
}
