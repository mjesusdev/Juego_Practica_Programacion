package es.studium.Juego;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Top_Ten extends JFrame implements WindowListener, ActionListener{

	private static final long serialVersionUID = 1L;

	// Componentes para el frame
	JLabel lbl10mp = new JLabel("Las 10 mejores partidas");
	TextArea txtTop = new TextArea(
			"1. Michael Jackson - 150 pts | !Ganador!" + 
					"\n" +
					"2. Jesús Ojeda - 140 pts" +
					"\n" +
					"3. Silvia Sánchez - 130 pts" +
					"\n" +
					"4. Pepe Romero - 120 pts" +		
					"\n" +
					"5. Ana Mendéz - 110 pts" + 		
					"\n" +			
					"6. Manolo Rodríguez - 90 pts" +		
					"\n" +	
					"7. Pérez Pérez - 75 pts" +		
					"\n" +		
					"8. Lol Lolo - 60 pts" +		
					"\n" +		
					"9. Sulivan Rodríguez - 5 pts" +		
					"\n" +		
			"10. Jonas Brothers - 0 pts");
	JButton btnVolver = new JButton("Volver");

	// Paneles para los componentes
	JPanel pnlNorte = new JPanel();
	JPanel pnlText = new JPanel();
	JPanel pnlVolver = new JPanel();

	Top_Ten()
	{
		// Almacenamos en mipantalla el sistema nativo de pantallas, el tamaño por defecto de la pantalla
		Toolkit mipantalla = Toolkit.getDefaultToolkit();

		setTitle("Top Ten");
		pnlNorte.add(lbl10mp);
		pnlText.add(txtTop);
		// Hacer que el texto no se pueda editar 
		txtTop.setEditable(false);
		pnlVolver.add(btnVolver);

		// Añadir los paneles y colocarlos en su posición  
		add(pnlNorte, BorderLayout.NORTH);
		add(pnlText, BorderLayout.CENTER);
		add(pnlVolver, BorderLayout.SOUTH);

		// Establecer un icono a la aplicación
		Image miIcono = mipantalla.getImage("src//duo.png");
		// Colocar icono
		setIconImage(miIcono);

		// Listeners
		btnVolver.addActionListener(this);
		addWindowListener(this);

		setSize(500,260);
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

}
