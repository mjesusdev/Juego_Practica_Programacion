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
	JLabel lblSeleccionar = new JLabel("Seleccione la traducci�n correcta de esta frase");
	JLabel lblFrase = new JLabel("");

	JButton btn1 = new JButton("Hoy no he desayunado");
	JButton btn2 = new JButton();
	JButton btnCalificar = new JButton("Calificar");

	// Paneles
	JPanel pnlFrase = new JPanel();
	JPanel pnlBoton = new JPanel();
	JPanel pnlBoton1 = new JPanel();
	JPanel pnlCalificar = new JPanel();

	// BD
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/duolingobd?autoReconnect=true&useSSL=false";
	String login = "root";
	String password = "Studium2018;";
	String sentencia = null;
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	String nombrejugador;
	int btn1click = 1;

	Tablero2(String jugador)
	{
		colocarIcono();
		nombrejugador = jugador;
		// Aplicar Layout
		NuevaPartida2.setLayout(new GridLayout(5,2));

		// A�adir al Panel frase la frase
		pnlFrase.add(lblSeleccionar);
		pnlFrase.add(lblFrase);
		insertarPregunta();

		// A�adir la primera frase y la segunda a los paneles boton y boton2
		pnlBoton.add(btn1);
		pnlBoton1.add(btn2);
		insertarRespuestas();

		// A�adir al panel BotonC
		pnlCalificar.add(btnCalificar);
		btnCalificar.setEnabled(false);

		// Ubicaci�n de los paneles
		NuevaPartida2.add(pnlFrase, BorderLayout.NORTH);
		NuevaPartida2.add(pnlBoton, BorderLayout.CENTER);
		NuevaPartida2.add(pnlBoton1, BorderLayout.CENTER);
		NuevaPartida2.add(pnlCalificar, BorderLayout.SOUTH);

		// A�adir los listeners 
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

	public int sacaridJugador() throws ClassNotFoundException, SQLException{
		Class.forName(driver);
		connection = DriverManager.getConnection(url, login, password);
		statement = connection.createStatement();
		sentencia = "SELECT idJugador FROM jugador WHERE nombreJugador = '"+nombrejugador+"';";
		rs = statement.executeQuery(sentencia);
		rs.next();
		int idJugador = rs.getInt("idJugador");
		desconectar();
		return idJugador;
	}
	
	public int preguntasCorrectas() throws ClassNotFoundException, SQLException{
		Class.forName(driver);
		connection = DriverManager.getConnection(url, login, password);
		statement = connection.createStatement();
		sentencia = "SELECT preguntas_Correctas FROM jugador WHERE nombreJugador = '"+nombrejugador+"';";
		rs = statement.executeQuery(sentencia);
		rs.next();
		int preguntasCorrectas = rs.getInt("preguntas_Correctas");
		desconectar();
		return preguntasCorrectas+1;
	}
	
	public int preguntasIncorrectas() throws ClassNotFoundException, SQLException{
		Class.forName(driver);
		connection = DriverManager.getConnection(url, login, password);
		statement = connection.createStatement();
		sentencia = "SELECT preguntas_Incorrectas FROM jugador WHERE nombreJugador = '"+nombrejugador+"';";
		rs = statement.executeQuery(sentencia);
		rs.next();
		int preguntasIncorrectas = rs.getInt("preguntas_Incorrectas");
		desconectar();
		return preguntasIncorrectas+1;
	}

	public void desconectar() {
		try
		{
			if(connection!=null)
			{
				connection.close();
			}
		}
		catch (SQLException se)
		{
			JOptionPane.showMessageDialog(null, "No se puede cerrar la conexi�n con la BD", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (btn1.equals(ae.getSource()))
		{	
			btnCalificar.setEnabled(true);
			btn1click++;
		}

		else if (btn2.equals(ae.getSource()))
		{
			btnCalificar.setEnabled(true);
		}

		else if(btnCalificar.equals(ae.getSource())) {
			if (btn1click==1) 
			{
				btn1click++;

				try
				{
					Class.forName(driver);
					connection = DriverManager.getConnection(url, login, password);
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
					statement.executeUpdate("UPDATE jugador SET preguntas_Correctas= "+preguntasCorrectas()+", puntos= '100' WHERE idJugador = "+sacaridJugador()+";");
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

				int seleccion = JOptionPane.showOptionDialog(
						NuevaPartida2,
						"Ha ganado la partida, �bien hecho!", 
						"Seleccione que desea realizar",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE,
						null, new Object[] { "Volver al men� principal del Juego", "Iniciar otra nueva partida"},
						"opcion 1");

				if (seleccion==0) {
					new Duolingo();
					NuevaPartida2.dispose();
				}else {
					new Tablero3(nombrejugador);
					NuevaPartida2.dispose();
				}
			}else if(btn1click!=1){				
				try
				{
					Class.forName(driver);
					connection = DriverManager.getConnection(url, login, password);
					statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
					statement.executeUpdate("UPDATE jugador SET preguntas_Incorrectas= '"+preguntasIncorrectas()+"' WHERE idJugador = '"+sacaridJugador()+"';");
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
				
				int seleccion = JOptionPane.showOptionDialog(
						NuevaPartida2,
						"Ha perdido esta pregunta, no se decaiga hombre, juegue de nuevo", 
						"Seleccione que desea realizar",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.ERROR_MESSAGE,
						null, new Object[] { "Volver al men� principal del Juego", "Iniciar otra nueva partida"},
						"opcion 1");

				if (seleccion==0) {
					new Duolingo();
					NuevaPartida2.dispose();
				}else {
					new Tablero3(nombrejugador);
					NuevaPartida2.dispose();
				}
			}
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