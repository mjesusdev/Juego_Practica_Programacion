package es.studium.Juego;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Duolingo extends JFrame implements WindowListener, ActionListener{

	private static final long serialVersionUID = 1L;

	// Agregar barra de Menú
	JMenuBar barraMenu = new JMenuBar();

	// Agregar menús
	JMenu menuNuevaPartida = new JMenu("Nueva Partida");
	JMenu menuTopTen = new JMenu("Top Ten"); 
	JMenu menuAyuda = new JMenu("Ayuda");

	// SubMenús Nueva Partida
	JMenuItem menuNuevaPartidaNP = new JMenuItem("Nueva Partida");

	// SubMenú TOPTEN
	JMenuItem menuTopTen10 = new JMenuItem("Top Ten");

	// SubMenú Ayuda
	JMenuItem menuAyudaVer = new JMenuItem("Ver Ayuda");

	Duolingo()
	{
		// Almacenamos en mipantalla el sistema nativo de pantallas, el tamaño por defecto de la pantalla
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		// Poner título a nuestra ventana
		setTitle("Duolingo");
		// Agregar la barra de Menú
		setJMenuBar(barraMenu);
		// Añadir los menús a la barra
		barraMenu.add(menuNuevaPartida);
		barraMenu.add(menuTopTen);
		barraMenu.add(menuAyuda);
		// Añadir a los menús los submenús
		menuNuevaPartida.add(menuNuevaPartidaNP);
		menuTopTen.add(menuTopTen10);
		menuAyuda.add(menuAyudaVer);
		// Añadir WindowListener
		addWindowListener(this);
		setSize(400,300);
		// Establecer el frame en el centro 
		setLocationRelativeTo(null);
		// Listener para abrir la clases
		menuNuevaPartidaNP.addActionListener(this);
		menuTopTen10.addActionListener(this);
		menuAyudaVer.addActionListener(this);

		// Listeners KEY
		EventoDeTeclado tecla = new EventoDeTeclado();
		addKeyListener(tecla);

		// Establecer un icono a la aplicación
		Image miIcono = mipantalla.getImage("src//duo.png");
		// Colocar icono
		setIconImage(miIcono);
		
		// Llamar a la clase que contiene la imagen
		LaminaConImagen lamina = new LaminaConImagen();
		// Añadir la imagen
		add(lamina);
		// No permitir maximizar
		setResizable(false);
		// Hacer visible el frame
		setVisible(true);
	}

	class LaminaConImagen extends JPanel{

		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
			super.paintComponents(g);

			// Especificar imagen
			File miImagen = new File("src//duolingo2.png") ;

			// Intentar leer la imagen
			try {
				imagen = ImageIO.read(miImagen);
			}
			// Si no la encuentra que salte el error y muestre por pantalla un mensaje
			catch(IOException e){
				System.out.println("La imagen no se encuentra");
			}

			// Dibujar la imagen con los siguientes tamaños
			g.drawImage(imagen, 80, 70, 250, 120, null);
		}

		private Image imagen;
	}

	public static void main(String[] args) {
		new Duolingo();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {}
	@Override
	public void windowClosed(WindowEvent arg0) {}
	@Override
	public void windowClosing(WindowEvent arg0) {
		// Método para cerrar la ventana al pulsar el botón cerrar
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}
	@Override
	public void windowDeiconified(WindowEvent arg0) {}
	@Override
	public void windowIconified(WindowEvent arg0) {}
	@Override
	public void windowOpened(WindowEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (menuNuevaPartidaNP.equals(arg0.getSource())) 
		{
			// Llamar a la clase NuevaPartida 
			new NuevaPartida();
			// Hacer invisible el frame o la ventana
			setVisible(false);
		}

		else if (menuAyudaVer.equals(arg0.getSource())) 
		{
			new Ayuda();
			setVisible(false);
		}
		
		else if (menuTopTen10.equals(arg0.getSource())) 
		{
			new Top_Ten();
			setVisible(false);
		}
	}

	class EventoDeTeclado implements KeyListener{
		@Override
		public void keyPressed(KeyEvent arg0) {
			int codigo = arg0.getKeyCode();

			// Si se ha pulsado el número 1 / tecla 1 que se abra el primer menú
			if (codigo==49) {
				menuNuevaPartida.doClick();
				// Si se ha pulsado ENTER que inicie partida
				if (codigo==10) {
					menuNuevaPartidaNP.doClick();
				}
			}

			// Si se ha pulsado el número 2 / tecla 2 que se abra el segundo menú
			else if (codigo==50) {
				menuTopTen.doClick();
				// Si se ha pulsado ENTER que abra el TopTen
				if (codigo==10) {
					menuTopTen10.doClick();
				}
			}

			else if (codigo==51) {
				menuAyuda.doClick();
				// Si se ha pulsado ENTER que Abra la Ayuda
				if (codigo==10) {
					menuAyudaVer.doClick();
				}
			}
			
			// Si se pulsa la tecla Escape
			else if (codigo==27) {
				// Cerrar el Programa
				dispose();
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {}
		@Override
		public void keyTyped(KeyEvent e) {}
	}
}