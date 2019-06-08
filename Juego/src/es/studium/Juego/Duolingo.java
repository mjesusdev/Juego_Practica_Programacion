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
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Duolingo extends JFrame implements WindowListener, ActionListener{

	private static final long serialVersionUID = 1L;

	// Agregar barra de Menú
	JMenuBar barraMenu = new JMenuBar();

	// Agregar menús
	JMenu menuNuevaPartida = new JMenu("Nueva Partida");
	JMenu menuTopTen = new JMenu("Top Ten"); 
	JMenu menuAyuda = new JMenu("Ayuda");

	// Agregar MenuItem
	JMenuItem menuNuevaPartidaNP = new JMenuItem("Iniciar Nueva Partida");
	JMenuItem menuTopTen10 = new JMenuItem("Ver Top Ten");
	JMenuItem menuAyudaVer = new JMenuItem("Ver Ayuda");

	Duolingo()
	{
		// Elemento ToolKit
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
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
		menuNuevaPartidaNP.addActionListener(this);
		menuTopTen10.addActionListener(this);
		menuAyudaVer.addActionListener(this);

		// Listeners KEY
		EventoDeTeclado tecla = new EventoDeTeclado();
		addKeyListener(tecla);

		// Establecer un icono a la aplicación
		Image miIcono = mipantalla.getImage("duo.png");
		// Colocar icono
		setIconImage(miIcono);

		// Llamar a la clase que contiene la imagen
		LaminaConImagen lamina = new LaminaConImagen();
		// Añadir la imagen
		add(lamina);
		setSize(400,300);
		// Añadir WindowListener
		addWindowListener(this);
		// Establecer el frame en el centro 
		setLocationRelativeTo(null);
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
			File miImagen = new File("duolingo2.png") ;

			try {
				imagen = ImageIO.read(miImagen);
			}
			catch(IOException e){
				JOptionPane.showMessageDialog(null, "Error", "La imagen no se encuentra", JOptionPane.ERROR_MESSAGE);
			}

			// Dibujar la imagen con los siguientes tamaños
			g.drawImage(imagen, 65, 55, 270, 140, null);
		}

		private Image imagen;
	}

	public static void main(String[] args) {
		new Duolingo();
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// Método para cerrar la ventana al pulsar el botón cerrar
		System.exit(0);
	}

	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (menuNuevaPartidaNP.equals(arg0.getSource())) 
		{
			// Llamar a la clase NuevaPartida 
			new NuevaPartida(null);
			// Hacer invisible el frame o la ventana
			setVisible(false);
		}

		else if (menuAyudaVer.equals(arg0.getSource())) 
		{
			try 
			{
				Runtime.getRuntime().exec("hh.exe ayuda_duolingo.chm");
			}
			catch (IOException e) 
			{
				JOptionPane.showMessageDialog(this, "No se puede abrir la ayuda", "Fallo al abrir la ayuda", JOptionPane.ERROR_MESSAGE);
			}
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

		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	}
}