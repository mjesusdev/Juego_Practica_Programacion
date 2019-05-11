package es.studium.Juego;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Tablero2 extends WindowAdapter implements ActionListener{

	JFrame NuevaPartida2 = new JFrame("Tablero");

	// Componentes para la Ventana
	JLabel lblSeleccionar = new JLabel("Seleccione la traducción correcta de esta frase");
	JLabel lblFrase = new JLabel("");

	// Botones
	JButton btn1 = new JButton("Esta mañana no he desayunado");
	JButton btn2 = new JButton("Hoy no he desayunado");
	JButton btnCalificar = new JButton("Calificar");

	// Panel Frase
	JPanel pnlFrase = new JPanel();

	// Panel para la primera frase
	JPanel pnlBoton = new JPanel();

	// Panel para la segunda frase
	JPanel pnlBoton1 = new JPanel();

	// Panel para el botón CALIFICAR
	JPanel pnlCalificar = new JPanel();

	// Diálogo Pregunta Correcta
	JDialog DialogoCorrecto = new JDialog(NuevaPartida2, false);

	// Diálogo Pregunta Incorrecta
	JDialog DialogoIncorrecto = new JDialog(NuevaPartida2, false);

	// Componentes Diálogo Correcto
	JLabel lblPuntos = new JLabel("Has obtenido 50 puntos");
	JButton btnVolver = new JButton("Volver");
	JButton btnNuevaPartida = new JButton("Nueva Partida");

	// Componentes Diálogo Incorrecto
	JLabel lblError = new JLabel("Has fallado, inténtelo de nuevo");
	JButton btnNuevaPartida1 = new JButton("Nueva Partida");

	// BD
	static String driver = "com.mysql.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/duolingobd?autoReconnect=true&useSSL=false";
	static String login = "root";
	static String password = "Studium2018;";
	static String sentencia = null;
	static Connection connection = null;
	static Statement statement = null;
	static ResultSet rs = null;

	String nombrejugador;
	
	Tablero2(String jugador)
	{
		nombrejugador = jugador;
		// Aplicar Layout
		NuevaPartida2.setLayout(new GridLayout(5,2));

		// Añadir al Panel frase la frase
		pnlFrase.add(lblSeleccionar);
		pnlFrase.add(lblFrase);
		insertarPregunta();

		// Añadir la primera frase y la segunda a los paneles boton y boton2
		pnlBoton.add(btn1);
		pnlBoton1.add(btn2);

		// Añadir al panel BotonC
		pnlCalificar.add(btnCalificar);

		// Ubicación de los paneles
		NuevaPartida2.add(pnlFrase, BorderLayout.NORTH);
		NuevaPartida2.add(pnlBoton, BorderLayout.CENTER);
		NuevaPartida2.add(pnlBoton1, BorderLayout.CENTER);
		NuevaPartida2.add(pnlCalificar, BorderLayout.SOUTH);

		// Diálogo Correcto
		DialogoCorrecto.setTitle("Pregunta Correcta");
		DialogoCorrecto.setLayout(new FlowLayout());
		DialogoCorrecto.add(lblPuntos);
		DialogoCorrecto.add(btnVolver);
		DialogoCorrecto.add(btnNuevaPartida);
		DialogoCorrecto.setSize(210,100);
		DialogoCorrecto.setLocationRelativeTo(null);
		DialogoCorrecto.setResizable(false);
		DialogoCorrecto.setVisible(false);

		// Diálogo Incorrecto
		DialogoIncorrecto.setTitle("Pregunta Incorrecta");
		DialogoIncorrecto.setLayout(new FlowLayout());
		DialogoIncorrecto.add(lblError);
		DialogoIncorrecto.add(btnNuevaPartida1);
		DialogoIncorrecto.setSize(210,100);
		DialogoIncorrecto.setLocationRelativeTo(null);
		DialogoIncorrecto.setResizable(false);
		DialogoIncorrecto.setVisible(false);

		// Añadir los listeners 
		NuevaPartida2.addWindowListener(this);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btnCalificar.addActionListener(this);

		// Listeners del Diálogo Correcto
		btnVolver.addActionListener(this);
		btnNuevaPartida.addActionListener(this);
		DialogoCorrecto.addWindowListener(this);

		// Listeners del Diálogo Pregunta Incorrecta
		btnNuevaPartida1.addActionListener(this);
		DialogoIncorrecto.addWindowListener(this);

		colocarIcono();

		NuevaPartida2.setSize(400,250);
		NuevaPartida2.setLocationRelativeTo(null);
		NuevaPartida2.setResizable(false);
		NuevaPartida2.setVisible(true);
	}

	public void colocarIcono() {
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		Image miIcono = mipantalla.getImage("src//duo.png");
		NuevaPartida2.setIconImage(miIcono);
	}

	public void insertarPregunta() {
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();
			sentencia = "SELECT preguntas FROM preguntas WHERE idPreguntas = 2;";
			System.out.println(sentencia);
			rs = statement.executeQuery(sentencia);
			while (rs.next()) {
				String pregunta = rs.getString("preguntas");
				lblFrase.setText(pregunta);
			}
		}

		catch (ClassNotFoundException cnfe)
		{
			System.out.println("Error 1: "+cnfe.getMessage());
		}
		catch (SQLException sqle)
		{
			JOptionPane.showMessageDialog(null, "Se ha producido un error", "Error", JOptionPane.ERROR_MESSAGE);
		}
		desconectar();
	}

	public static void desconectar() {
		try
		{
			if(connection!=null)
			{
				connection.close();
			}
		}
		catch (SQLException se)
		{
			System.out.println("No se puede cerrar la conexión la Base De Datos");
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if ((btn1.equals(ae.getSource())))
		{
			btnCalificar.doClick(500);
		}
		
		else if (btnCalificar.equals(ae.getSource())) {
			
			String btn1text = btn1.getText();
			
			if (btn1text.equals(btn1text))
			{
				DialogoCorrecto.setVisible(true);
				
				try
				{
					Class.forName(driver);
					connection = DriverManager.getConnection(url, login, password);
					statement = connection.createStatement();
					sentencia = "UPDATE jugador SET preguntas_Correctas= '2', puntos= '100' WHERE nombreJugador = '"+nombrejugador+"';";
					System.out.println(sentencia);
					statement.executeUpdate(sentencia);
				}
				
				catch (ClassNotFoundException cnfe)
				{
					System.out.println("Error 1: "+cnfe.getMessage());
				}
				catch (SQLException sqle)
				{
					JOptionPane.showMessageDialog(null, "Se ha producido un error", "Error", JOptionPane.ERROR_MESSAGE);
				}
				desconectar();
			}
			
			else {
				DialogoIncorrecto.setVisible(true);
				
				try
				{
					Class.forName(driver);
					connection = DriverManager.getConnection(url, login, password);
					statement = connection.createStatement();
					sentencia = "UPDATE jugador SET preguntas_Incorrectas= '2' WHERE nombreJugador = '"+nombrejugador+"';";
					System.out.println(sentencia);
					statement.executeUpdate(sentencia);
				}
				
				catch (ClassNotFoundException cnfe)
				{
					System.out.println("Error 1: "+cnfe.getMessage());
				}
				catch (SQLException sqle)
				{
					JOptionPane.showMessageDialog(null, "Se ha producido un error", "Error", JOptionPane.ERROR_MESSAGE);
				}
				desconectar();
			}
		}

		else if (btnVolver.equals(ae.getSource())) {
			NuevaPartida2.setVisible(false);
			new Duolingo();
		}
	}

	@Override
	public void windowClosing(WindowEvent arg0) {	
		if (DialogoCorrecto.isActive()) {
			DialogoCorrecto.setVisible(false);
			new Tablero2(nombrejugador);
		}
		// Cuando se cierre el frame principal se abra el juego de nuevo
		else if(NuevaPartida2.isActive()){
			NuevaPartida2.setVisible(false);
			new Duolingo();
		}else if(DialogoIncorrecto.isActive()){
			NuevaPartida2.setVisible(true);
		}
	}
}