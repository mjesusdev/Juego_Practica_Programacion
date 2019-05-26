package es.studium.Juego;

import java.awt.BorderLayout;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Tablero2 extends WindowAdapter implements ActionListener{

	JFrame NuevaPartida2 = new JFrame("Tablero");

	// Componentes para la Ventana
	JLabel lblSeleccionar = new JLabel("Seleccione la traducción correcta de esta frase");
	JLabel lblFrase = new JLabel("");

	JButton btn1 = new JButton("Esta mañana no he desayunado");
	JButton btn2 = new JButton();
	JButton btnCalificar = new JButton("Calificar");

	// Paneles
	JPanel pnlFrase = new JPanel();
	JPanel pnlBoton = new JPanel();
	JPanel pnlBoton1 = new JPanel();
	JPanel pnlCalificar = new JPanel();

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
		colocarIcono();
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
		insertarRespuestas();

		// Añadir al panel BotonC
		pnlCalificar.add(btnCalificar);
		btnCalificar.setEnabled(false);

		// Ubicación de los paneles
		NuevaPartida2.add(pnlFrase, BorderLayout.NORTH);
		NuevaPartida2.add(pnlBoton, BorderLayout.CENTER);
		NuevaPartida2.add(pnlBoton1, BorderLayout.CENTER);
		NuevaPartida2.add(pnlCalificar, BorderLayout.SOUTH);

		// Añadir los listeners 
		NuevaPartida2.addWindowListener(this);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btnCalificar.addActionListener(this);

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
			rs = statement.executeQuery(sentencia);
			while (rs.next()) {
				String pregunta = rs.getString("preguntas");
				lblFrase.setText(pregunta);
			}
		}

		catch (ClassNotFoundException cnfe)
		{
			JOptionPane.showMessageDialog(null, "Error al cargar el driver", "Error", JOptionPane.ERROR_MESSAGE);
		}
		catch (SQLException sqle)
		{
			JOptionPane.showMessageDialog(null, "Se ha producido un error", "Error", JOptionPane.ERROR_MESSAGE);
		}
		desconectar();
	}

	public void insertarRespuestas() {
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();
			sentencia = "SELECT respuestas FROM respuestas WHERE idPreguntasFK = 2;";
			rs = statement.executeQuery(sentencia);
			while (rs.next()) {
				String respuestas = rs.getString("respuestas");
				btn2.setText(respuestas);
			}
		}

		catch (ClassNotFoundException cnfe)
		{
			JOptionPane.showMessageDialog(null, "Error al cargar el driver", "Error", JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null, "No se puede cerrar la conexión con la BD", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		Object ok = ae.getSource();
		
		if ((btn1.equals(ae.getSource())))
		{
			btnCalificar.setEnabled(true);
		}

		else if (btn2.equals(ae.getSource()))
		{
			btnCalificar.setEnabled(true);
		}

		else if (btnCalificar.equals(ae.getSource())) {
			if (ok.equals(btn1.equals(ae.getSource()))) {
				System.out.println("hola aja");
			}
			
			int seleccion = JOptionPane.showOptionDialog(
					NuevaPartida2,
					"Ha ganado la partida, ¡bien hecho!", 
					"Seleccione que desea realizar",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					null, new Object[] { "Iniciar de nuevo la primera partida", "Volver a la partida actual"},
					"opcion 1");

			if (seleccion==0) {
				new Tablero(nombrejugador);
				NuevaPartida2.setVisible(false);
			}	

			try
			{
				Class.forName(driver);
				connection = DriverManager.getConnection(url, login, password);
				statement = connection.createStatement();
				sentencia = "UPDATE jugador SET preguntas_Correctas= '2', puntos= '100' WHERE nombreJugador = '"+nombrejugador+"';";
				statement.executeUpdate(sentencia);
			}

			catch (ClassNotFoundException cnfe)
			{
				JOptionPane.showMessageDialog(null, "Error al cargar el driver", "Error", JOptionPane.ERROR_MESSAGE);
			}
			catch (SQLException sqle)
			{
				JOptionPane.showMessageDialog(null, "Se ha producido un error", "Error", JOptionPane.ERROR_MESSAGE);
			}
			desconectar();
		}

		else {
			int seleccion = JOptionPane.showOptionDialog(
					NuevaPartida2,
					"Ha perdido esta pregunta, no se decaiga hombre, juegue de nuevo", 
					"Seleccione que desea realizar",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.ERROR_MESSAGE,
					null, new Object[] { "Iniciar de nuevo la primera partida", "Volver a la partida actual"},
					"opcion 1");

			if (seleccion==0) {
				new Tablero3(nombrejugador);
				NuevaPartida2.setVisible(false);
			}	

			try
			{
				Class.forName(driver);
				connection = DriverManager.getConnection(url, login, password);
				statement = connection.createStatement();
				sentencia = "UPDATE jugador SET preguntas_Incorrectas= '2' WHERE nombreJugador = '"+nombrejugador+"';";
				statement.executeUpdate(sentencia);
			}

			catch (ClassNotFoundException cnfe)
			{
				JOptionPane.showMessageDialog(null, "Error al cargar el driver", "Error", JOptionPane.ERROR_MESSAGE);
			}
			catch (SQLException sqle)
			{
				JOptionPane.showMessageDialog(null, "Se ha producido un error", "Error", JOptionPane.ERROR_MESSAGE);
			}
			desconectar();
		}
	}

@Override
public void windowClosing(WindowEvent arg0) {	
	if(NuevaPartida2.isActive()){
		NuevaPartida2.setVisible(false);
		new Duolingo();
	}
}
}