package es.studium.Juego;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Top_Ten extends JFrame implements WindowListener, ActionListener{

	private static final long serialVersionUID = 1L;

	// Componentes para el frame
	JLabel label10mejorespartidas = new JLabel("Las 10 mejores partidas");
	
	JTable tablaTop10 = new JTable();

	String [] titulos= {};

	DefaultTableModel modelo = new DefaultTableModel();
	
	JButton btnVolver = new JButton("Volver");

	// Paneles para los componentes
	JPanel pnlNorte = new JPanel();
	JPanel pnlTabla = new JPanel();
	JPanel pnlVolver = new JPanel();

	// Base de Datos
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/duolingobd?autoReconnect=true&useSSL=false";
	String login = "admin";
	String password = "Studium2018;";
	String sentencia = "SELECT idJugador AS 'Nº Jugador', nombreJugador AS 'Jugador', puntos AS 'Puntos', "
			+ "preguntas_Correctas AS 'Correctas', "
			+ "preguntas_Incorrectas AS 'Incorrectas' "
			+ "FROM jugador ORDER BY 3 DESC, 4 DESC;";
	Connection connection = null;
	Statement statement = null;
	ResultSet rs = null;
	
	Top_Ten()
	{
		setTitle("Top Ten");
		colocarIcono();
		pnlNorte.add(label10mejorespartidas);
		label10mejorespartidas.setFont(new java.awt.Font("Calibri", 1, 18));
		
		tablaTop10 = new JTable(rellenarTabla(),titulos);
		tablaTop10.setModel(modelo);
		// Aplicar un color de fondo a la tabla
		tablaTop10.setBackground(Color.decode("#e9ffdb"));
		// No editar la tabla
		tablaTop10.setEnabled(false);
		
		pnlTabla.add(new JScrollPane(tablaTop10), BorderLayout.CENTER);
		pnlVolver.add(btnVolver);
		btnVolver.setFont(new java.awt.Font("Calibri", 0, 15));
		
		// Añadir los paneles y colocarlos en su posición  
		add(pnlNorte, BorderLayout.NORTH);
		add(pnlTabla, BorderLayout.CENTER);
		add(pnlVolver, BorderLayout.SOUTH);

		// Listeners
		btnVolver.addActionListener(this);
		addWindowListener(this);

		setSize(500,550);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
	
	public void colocarIcono() {
	    ImageIcon ImageIcon = new ImageIcon("duo.png");
        Image image = ImageIcon.getImage();
        this.setIconImage(image);
	}
	
	public Object[][] rellenarTabla() {
		try 
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
			statement = connection.createStatement();						
			rs = statement.executeQuery(sentencia);

			ResultSetMetaData rsMd = rs.getMetaData();
			// Guardar en una variable las columnas que hay
			int cantidadColumnas = rsMd.getColumnCount();

			// Bucle para ir de 1 hasta las columnas que existen
			for (int i=1;i<=cantidadColumnas;i++) {
				// Añadir los títulos de las columnas
				modelo.addColumn(rsMd.getColumnLabel(i));
			}

			while (rs.next()) {
				Object [] fila = new Object[cantidadColumnas];
				for (int i=0;i<cantidadColumnas;i++) {
					// Coger los objetos de la bd	
					fila[i] = rs.getObject(i+1);
				}
				// Añadir las columnas
				modelo.addRow(fila);
			}
		}

		catch (ClassNotFoundException cnfe)
		{
			JOptionPane.showMessageDialog(null, "Error", "No se puede cargar el Driver", JOptionPane.ERROR_MESSAGE);
		}

		catch (SQLException sqle)
		{
			JOptionPane.showMessageDialog(null, "Error", "Error, por favor corrija los fallos", JOptionPane.ERROR_MESSAGE);
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
		catch (SQLException e)
		{
			JOptionPane.showMessageDialog(null, "Error", "No se puede cerrar la conexión con la BD", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (btnVolver.equals(arg0.getSource())) {
			this.setVisible(false);
			new Duolingo();
		}
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		if (this.isActive()) {
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