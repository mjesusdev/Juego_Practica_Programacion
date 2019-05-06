package es.studium.Juego;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NuevaPartida extends JFrame implements WindowListener, ActionListener, KeyListener{

	private static final long serialVersionUID = 1L;

	// Componentes para la Nueva Partida
	JLabel lblNombre = new JLabel("Introduzca el nombre del jugador");
	JLabel lblJugador = new JLabel("Jugador:");
	// Campo de Texto y tamaño al campo
	JTextField txtJugador = new JTextField(15);
	JButton btnComenzar = new JButton("Comenzar Partida");

	// Paneles
	JPanel pnlSuperior = new JPanel();
	JPanel pnlCentral = new JPanel();
	JPanel pnlInferior = new JPanel();

	// BD
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/duolingobd?autoReconnect=true&useSSL=false";
	String login = "root";
	String password = "Studium2018;";
	String sentencia = null;
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	
	NuevaPartida(){
		// Almacenamos en mipantalla el sistema nativo de pantallas, el tamaño por defecto de la pantalla
		Toolkit mipantalla = Toolkit.getDefaultToolkit();

		setTitle("Nueva Partida");

		// Añadir los componentes a los distintos paneles
		pnlSuperior.add(lblNombre);
		pnlCentral.add(lblJugador);
		pnlCentral.add(txtJugador);
		pnlInferior.add(btnComenzar);

		// Añadir los paneles
		add(pnlSuperior, "North");
		add(pnlCentral, "Center");
		add(pnlInferior, "South");

		// Añadir los listeners 
		addWindowListener(this);
		btnComenzar.addActionListener(this);
		txtJugador.addKeyListener(this);

		// Establecer un icono a la aplicación
		Image miIcono = mipantalla.getImage("src//duo.png");
		// Colocar icono
		setIconImage(miIcono);	

		// Tamaño a la ventana
		setSize(300,150);
		// Ventana al centro
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String nombreJugador = txtJugador.getText();

		if (btnComenzar.equals(arg0.getSource())) {
			if (nombreJugador.equals("")) {
				JOptionPane.showMessageDialog(null, "Tiene que proporcionar un nombre...", "Error", JOptionPane.ERROR_MESSAGE);
			}else {
				this.setVisible(false);
				new Tablero();
			}
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {}

	@Override
	public void windowClosing(WindowEvent arg0) {
		if (this.isActive()) {
			this.setVisible(false);
			new Duolingo();
		}
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
	public void keyPressed(KeyEvent arg0) {
		int codigo = arg0.getKeyCode();
		if (codigo==10) {
			btnComenzar.doClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}