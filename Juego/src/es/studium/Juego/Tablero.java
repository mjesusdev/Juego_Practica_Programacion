package es.studium.Juego;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class Tablero extends JFrame implements WindowListener, ActionListener{

	private static final long serialVersionUID = 1L;

	// Componentes para la Ventana
	JLabel lbl1 = new JLabel("Seleccione bloques para completar la frase en inglés");
	JLabel lblFrase = new JLabel("");
	JTextField txtFrase = new JTextField(25);

	// Botones
	JButton btn1 = new JButton("es");
	JButton btn2 = new JButton("un");
	JButton btn3 = new JButton("juego");
	JButton btn4 = new JButton("Esto");

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

	// Diálogo Pregunta Correcta
	JDialog DialogoCorrecto = new JDialog(this, true);

	// Diálogo Pregunta Incorrecta
	JDialog DialogoIncorrecto = new JDialog(this, true);

	// Componentes Diálogo Correcto
	JLabel lblPuntos = new JLabel("Has conseguido 50 puntos");
	JButton btnVolver = new JButton("Volver");
	JButton btnNuevaPartida = new JButton("Nueva Partida");

	// Componentes Diálogo Incorrecto
	JLabel lblError = new JLabel("Has fallado, inténtelo de nuevo!");
	JButton btnVolver1 = new JButton("Volver");
	JButton btnNuevaPartida1 = new JButton("Nueva Partida");

	// Paneles para Diálogo Incorrecto
	JPanel pnlFallar = new JPanel();
	JPanel pnlBotonesIn = new JPanel();

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
	
	Tablero(String jugador)
	{
		nombrejugador = jugador;
		// Almacenamos en mipantalla el sistema nativo de pantallas, el tamaño por defecto de la pantalla
		Toolkit mipantalla = Toolkit.getDefaultToolkit();

		// Título
		setTitle("Tablero");

		// Añadir al Panel frase la frase
		pnl1.add(lbl1);
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

		// Diálogo Correcto
		DialogoCorrecto.setTitle("Pregunta Correcta");
		DialogoCorrecto.setLayout(new FlowLayout());
		DialogoCorrecto.add(lblPuntos);
		DialogoCorrecto.add(btnVolver);
		DialogoCorrecto.add(btnNuevaPartida);
		DialogoCorrecto.setSize(200,100);
		DialogoCorrecto.setLocationRelativeTo(null);
		DialogoCorrecto.setResizable(false);
		DialogoCorrecto.setVisible(false);

		// Diálogo Incorrecto
		DialogoIncorrecto.setTitle("Pregunta Incorrecta");
		pnlFallar.add(lblError, "North");
		pnlBotonesIn.add(btnVolver1);
		pnlBotonesIn.add(btnNuevaPartida1);
		DialogoIncorrecto.setSize(250,100);
		DialogoIncorrecto.setLocationRelativeTo(null);
		DialogoIncorrecto.setResizable(false);
		DialogoIncorrecto.setVisible(false);

		// Ubicación Paneles Diálogo Incorrecto
		DialogoIncorrecto.add(pnlFallar, "North");
		DialogoIncorrecto.add(pnlBotonesIn, "Center");

		// Añadir los listeners 
		addWindowListener(this);
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btn4.addActionListener(this);
		btnCalificar.addActionListener(this);
		btnLimpiar.addActionListener(this);

		// Listeners del Diálogo Correcto
		btnVolver.addActionListener(this);
		btnNuevaPartida.addActionListener(this);
		DialogoCorrecto.addWindowListener(this);

		// Listeners del Diálogo Pregunta Incorrecta
		btnVolver1.addActionListener(this);
		btnNuevaPartida1.addActionListener(this);
		DialogoIncorrecto.addWindowListener(this);

		// Establecer un icono a la aplicación
		Image miIcono = mipantalla.getImage("src//duo.png");
		// Colocar icono
		setIconImage(miIcono);

		// Aplicar Layout
		setLayout(new GridLayout(6,2,5,5));
		setSize(400,350);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
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
	
	public static void insertarPregunta(JLabel lblFrase) {
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();
			sentencia = "SELECT preguntas FROM preguntas WHERE idPreguntas = 1;";
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
	
	public static String comprobarRespuesta() {
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();
			sentencia = "SELECT respuestas FROM respuestas WHERE idRespuestas = 1;";
			System.out.println(sentencia);
			rs = statement.executeQuery(sentencia);
			rs.next();
			
			String respuesta = rs.getString("respuestas");
			System.out.println(respuesta);
			return respuesta;
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
		
		return null;
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
				DialogoCorrecto.setVisible(true);
				
				try
				{
					Class.forName(driver);
					connection = DriverManager.getConnection(url, login, password);
					//Crear una sentencia
					statement = connection.createStatement();
					sentencia = "INSERT INTO jugador VALUES(NULL, '"+nombrejugador+"', 50, 1, 0);";
					System.out.println(sentencia);
					// Ejecutar la sentencia
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
			
			else{
				DialogoIncorrecto.setVisible(true);
				
				try
				{
					Class.forName(driver);
					connection = DriverManager.getConnection(url, login, password);
					//Crear una sentencia
					statement = connection.createStatement();
					sentencia = "INSERT INTO jugador VALUES(NULL, '"+nombrejugador+"', 0, 0, 1);";
					System.out.println(sentencia);
					// Ejecutar la sentencia
					statement.executeUpdate(sentencia);
				}
				
				catch (ClassNotFoundException cnfe)
				{
					System.out.println("Error 1: "+cnfe.getMessage());
				}
				catch (SQLException sqle)
				{
					JOptionPane.showMessageDialog(null, "Error, en el Alta", "Error", JOptionPane.ERROR_MESSAGE);
				}
				desconectar();
			}
		}

		else if (btnLimpiar.equals(ae.getSource())) {
			txtFrase.setText("");
		}

		else if (btnVolver.equals(ae.getSource())) {
			DialogoCorrecto.setVisible(false);
			setVisible(true);
		}

		else if (btnVolver1.equals(ae.getSource())) {
			DialogoIncorrecto.setVisible(false);
			setVisible(true);
		}

		else if (btnNuevaPartida.equals(ae.getSource())) {
			DialogoCorrecto.setVisible(false);
			setVisible(false);
			new Tablero2();
		}

		else if (btnNuevaPartida1.equals(ae.getSource())) {
			DialogoIncorrecto.setVisible(false);
			setVisible(false);
			new Tablero2();
		}
	}

	@Override
	public void windowClosing(WindowEvent arg0) {	
		if (DialogoCorrecto.isActive()) {
			DialogoCorrecto.setVisible(false);
			setVisible(true);
		}
		else if(DialogoIncorrecto.isActive()) {
			DialogoIncorrecto.setVisible(false);
			setVisible(true);
		}

		else if (this.isActive()){
			this.setVisible(false);
			new Duolingo();
		}
	}

	@Override
	public void windowActivated(WindowEvent arg0) {}
	@Override
	public void windowClosed(WindowEvent arg0) {}
	@Override
	public void windowDeactivated(WindowEvent arg0) {}
	@Override
	public void windowDeiconified(WindowEvent arg0) {}
	@Override
	public void windowIconified(WindowEvent arg0) {}
	@Override
	public void windowOpened(WindowEvent arg0) {}
}