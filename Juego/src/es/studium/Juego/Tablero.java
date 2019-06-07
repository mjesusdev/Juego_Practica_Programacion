package es.studium.Juego;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import javax.swing.JTextField;

public class Tablero extends JFrame implements WindowListener, ActionListener{

	private static final long serialVersionUID = 1L;

	// Componentes para la Ventana
	JLabel lblInstrucciones = new JLabel("Seleccione bloques para completar la frase en inglés");
	JLabel lblFrase = new JLabel("");
	JTextField txtFrase = new JTextField(25);

	// Botones
	JButton btn1 = new JButton("");
	JButton btn2 = new JButton("");
	JButton btn3 = new JButton("");
	JButton btn4 = new JButton("");
	JButton btnCalificar = new JButton("Calificar");
	JButton btnLimpiar = new JButton("Limpiar");

	// Panel Frase
	JPanel pnl1 = new JPanel();

	// Panel Frase
	JPanel pnlFrase = new JPanel();

	// Panel Texto
	JPanel pnlTexto = new JPanel();

	// Paneles para los dos primeros botones 
	JPanel pnlBotones1 = new JPanel();

	// Paneles para los otros dos botones
	JPanel pnlBotones2 = new JPanel();

	// Panel para el botón Calificar
	JPanel pnlBotonC = new JPanel();

	// BD
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/duolingobd?autoReconnect=true&useSSL=false";
	String login = "root";
	String password = "Studium2018;";
	String sentencia = null;
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;

	// Variable que servirá para guardar el nombre del jugador
	String nombrejugador;

	Tablero(String jugador)
	{
		nombrejugador = jugador;
		// Título
		setTitle("Tablero");
		insertarIcono();

		// Añadir al Panel frase la frase
		pnl1.add(lblInstrucciones);
		pnlFrase.add(lblFrase);
		pnlTexto.add(txtFrase);
		txtFrase.setEditable(false);
		insertarPregunta(lblFrase);

		// Tamaño al Panel Botones 1 y 2
		pnlBotones1.setLayout(new GridLayout(0,2,5,5));
		pnlBotones2.setLayout(new GridLayout(0,2,5,5));

		// Añadir los botones al panel BOTONES
		pnlBotones1.add(btn1);
		pnlBotones1.add(btn2);
		pnlBotones2.add(btn3);
		pnlBotones2.add(btn4);
		insertarRespuestasBotones();

		// Añadir al panel BotonC
		pnlBotonC.add(btnCalificar);
		pnlBotonC.add(btnLimpiar);

		// Ubicación de los paneles
		add(pnl1, "North");
		add(pnlFrase, "North");
		add(pnlTexto, "North");
		add(pnlBotones1, "Center");
		add(pnlBotones2, "Center");
		add(pnlBotonC, "South");

		// Añadir los listeners 
		addWindowListener(this);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btn4.addActionListener(this);
		btnCalificar.addActionListener(this);
		btnLimpiar.addActionListener(this);

		// Aplicar Layout
		setLayout(new GridLayout(6,2,5,5));
		setSize(400,350);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	public void insertarIcono() {
		Toolkit mipantalla = Toolkit.getDefaultToolkit();
		Image miIcono = mipantalla.getImage("src//duo.png");
		setIconImage(miIcono);
	}

	public void insertarPregunta(JLabel lblFrase) {
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();
			sentencia = "SELECT preguntas FROM preguntas WHERE idPreguntas = 1;";
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

	public void insertarRespuestasBotones() {
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();
			sentencia = "SELECT respuestas FROM respuestas WHERE idRespuestas = 1;";
			rs = statement.executeQuery(sentencia);
			while (rs.next()) {
				String respuesta = rs.getString("respuestas");
				String [] dividirrespuesta = respuesta.split(" ");
				String palabra1 = dividirrespuesta[0];
				String palabra2 = dividirrespuesta[1];
				String palabra3 = dividirrespuesta[2];
				String palabra4 = dividirrespuesta[3];
				btn1.setText(palabra2);
				btn2.setText(palabra3);
				btn3.setText(palabra4);
				btn4.setText(palabra1);
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

	public String comprobarRespuesta() {
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();
			sentencia = "SELECT respuestas FROM respuestas WHERE idRespuestas = 1;";
			rs = statement.executeQuery(sentencia);
			rs.next();
			String respuesta = rs.getString("respuestas");
			return respuesta;
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
		return null;
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
			JOptionPane.showMessageDialog(null, "No se puede cerrar la conexión con la Base de Datos", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String textofrase = btn1.getText();
		String textofrase1 = btn2.getText();
		String textofrase2 = btn3.getText();
		String textofrase3 = btn4.getText();	

		if ((btn1.equals(ae.getSource()))){
			txtFrase.setText(txtFrase.getText()+" "+textofrase);
		}

		else if ((btn2.equals(ae.getSource()))) {
			txtFrase.setText(txtFrase.getText()+" "+textofrase1);
		}

		else if ((btn3.equals(ae.getSource()))) {
			txtFrase.setText(txtFrase.getText()+" "+textofrase2);
		}

		else if ((btn4.equals(ae.getSource()))) {
			txtFrase.setText(txtFrase.getText()+" "+textofrase3);
		}

		String textoFinal = txtFrase.getText();

		if (btnCalificar.equals(ae.getSource())) {
			if (textoFinal.equals(" " +comprobarRespuesta())) {
				int seleccion = JOptionPane.showOptionDialog(
						this,
						"Ha ganado 50 puntos por acertar la pregunta, pulse que desea hacer", 
						"Seleccione que desea realizar",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE,
						null, new Object[] { "Nueva Partida", "Volver a la partida"},
						"opcion 1");

				if (seleccion==0) {
					new Tablero2(nombrejugador);
					this.setVisible(false);

					try
					{
						Class.forName(driver);
						connection = DriverManager.getConnection(url, login, password);
						statement = connection.createStatement();
						sentencia = "INSERT INTO jugador VALUES(NULL, '"+nombrejugador+"', 50, 1, 0);";
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

			else{
				int seleccion = JOptionPane.showOptionDialog(
						this,
						"Ha perdido esta pregunta, no se decaiga hombre", 
						"Seleccione que desea realizar",
						JOptionPane.YES_NO_CANCEL_OPTION,
						JOptionPane.ERROR_MESSAGE,
						null, new Object[] { "Nueva Partida", "Volver a la partida"},
						"opcion 1");

				if (seleccion==0) {
					new Tablero2(nombrejugador);
					this.setVisible(false);

					try
					{
						Class.forName(driver);
						connection = DriverManager.getConnection(url, login, password);
						statement = connection.createStatement();
						sentencia = "INSERT INTO jugador VALUES(NULL, '"+nombrejugador+"', 0, 0, 1);";
						statement.executeUpdate(sentencia);
					}

					catch (ClassNotFoundException cnfe)
					{
						JOptionPane.showMessageDialog(null, "Error al cargar el driver", "Error", JOptionPane.ERROR_MESSAGE);
					}
					catch (SQLException sqle)
					{
						JOptionPane.showMessageDialog(null, "Error", "Error, por favor corrija los fallos", JOptionPane.ERROR_MESSAGE);
					}
					desconectar();
				}	
			}
		}

		else if (btnLimpiar.equals(ae.getSource())) {
			txtFrase.setText("");
		}
	}

	@Override
	public void windowClosing(WindowEvent arg0) {	
		if (this.isActive()){
			this.setVisible(false);
			new Duolingo();
		}
	}

	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
}