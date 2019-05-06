package es.studium.Juego;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Ayuda extends Frame implements WindowListener, ActionListener{

	private static final long serialVersionUID = 1L;

	TextArea txtAyuda = new TextArea("Duolingo tiene como finalidad llegar a aprender ingl�s jugando partidas," 
			+ "\n" + "ya sea traduciendo frases en espa�ol o en ingl�s."
			+ "\n" +
			// Espacio para dejar una l�nea vac�a
			"\t" +
			// Espacios 
			"\n" +
			"Se empieza una Nueva Partida pulsando sobre la opci�n de la Ventana Principal," + "\n" 
			+ "despu�s escribiendo tu nombre y as� entras a jugar partidas.");

	JButton btnVolver = new JButton("Volver");

	// Paneles para los componentes
	JPanel pnlTextArea = new JPanel();
	JPanel pnlVolver = new JPanel();

	Ayuda()
	{
		// Almacenamos en mipantalla el sistema nativo de pantallas, el tama�o por defecto de la pantalla
		Toolkit mipantalla = Toolkit.getDefaultToolkit();

		setTitle("Ayuda");
		pnlTextArea.add(txtAyuda);
		txtAyuda.setEditable(false);
		pnlVolver.add(btnVolver);

		// A�adir los paneles y colocarlos en su posici�n  
		add(pnlTextArea, BorderLayout.CENTER);
		add(pnlVolver, BorderLayout.SOUTH);

		// Establecer un icono a la aplicaci�n
		Image miIcono = mipantalla.getImage("src//duo.png");
		// Colocar icono
		setIconImage(miIcono);

		// Listeners a�adirlos
		addWindowListener(this);
		btnVolver.addActionListener(this);

		// Tama�o
		setSize(500,200);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
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

	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowOpened(WindowEvent e) {}
}