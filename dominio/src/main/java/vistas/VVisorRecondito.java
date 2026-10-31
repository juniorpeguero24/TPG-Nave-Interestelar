package vistas;

import observer.ObservadorPlataforma;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import static vistas.Estilo.*;


public class VVisorRecondito extends JFrame implements IVista, ObservadorPlataforma {

	private JButton btnDetener;
	private JPanel contentPane;
	private JPanel panelBtn;
	private JPanel panelBtnNaves;
	private JButton btnNaveConsu;
	private JButton btnNaveGen;
	private JPanel panelBtnVolver;
	private JButton btnVolver;
	private JPanel panelCentral;
	private JScrollPane scrollConcurrencia;
	private JTextArea textAreaConcurrencia;
	private JPanel panelTitulo;
	private JLabel lblTitulo;

	private JTextField txtNombreNave;
	private JPanel panelBotonesNaves;
	private JPanel panelNombre;
	private JLabel lblNombreNave;
	private JPanel panelTiempo;
	private JLabel lblTiempo;
	private JTextField txtTiempo;

	public VVisorRecondito() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(250, 250, 700, 850);
		setResizable(false);
		setLocationRelativeTo(null);

		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.contentPane.setLayout(new BorderLayout(0, 0));
		this.contentPane.setBackground(fondo);
		setContentPane(this.contentPane);

		// Panel central
		this.panelCentral = new JPanel();
		this.contentPane.add(this.panelCentral, BorderLayout.CENTER);
		this.panelCentral.setLayout(new BorderLayout(0, 0));
		this.panelCentral.setBackground(fondo);

		this.scrollConcurrencia = new JScrollPane();
		this.panelCentral.add(this.scrollConcurrencia);
		this.scrollConcurrencia.getViewport().setBackground(fondoPanel);
		this.scrollConcurrencia.getVerticalScrollBar().setBackground(fondoPanel);
		this.scrollConcurrencia.getVerticalScrollBar().setForeground(celeste);

		this.textAreaConcurrencia = new JTextArea();
		this.textAreaConcurrencia.setEditable(false);
		this.scrollConcurrencia.setViewportView(this.textAreaConcurrencia);
		this.textAreaConcurrencia.setFont(fuentePlain);
		this.textAreaConcurrencia.setForeground(blanco);
		this.textAreaConcurrencia.setBackground(fondoPanel);

		// Panel inferior
		this.panelBtn = new JPanel();
		this.contentPane.add(this.panelBtn, BorderLayout.SOUTH);
		this.panelBtn.setLayout(new GridLayout(2, 1, 0, 0));
		this.panelBtn.setBackground(fondo);

		this.panelBtnNaves = new JPanel();
		this.panelBtn.add(this.panelBtnNaves);
		this.panelBtnNaves.setLayout(new GridLayout(3, 1, 0, 5));
		this.panelBtnNaves.setBackground(fondo);

		// Fila 1: Nombre de nave
		this.panelNombre = new JPanel();
		this.panelBtnNaves.add(this.panelNombre);
		this.panelNombre.setBackground(fondo);

		this.lblNombreNave = new JLabel("Nombre nave:");
		this.panelNombre.add(this.lblNombreNave);
		this.lblNombreNave.setFont(fuenteBold);
		this.lblNombreNave.setForeground(blanco);

		this.txtNombreNave = new JTextField(20);
		this.panelNombre.add(this.txtNombreNave);
		this.txtNombreNave.setFont(fuentePlain);
		this.txtNombreNave.setForeground(blanco);
		this.txtNombreNave.setBackground(fondoPanel);
		this.txtNombreNave.setCaretColor(blanco);

		// Fila 2: Tiempo de producción/consumo
		this.panelTiempo = new JPanel();
		this.panelBtnNaves.add(this.panelTiempo);
		this.panelTiempo.setBackground(fondo);

		this.lblTiempo = new JLabel("Tiempo Prod/Cons:");
		this.panelTiempo.add(this.lblTiempo);
		this.lblTiempo.setFont(fuenteBold);
		this.lblTiempo.setForeground(blanco);

		this.txtTiempo = new JTextField(10);
		this.panelTiempo.add(this.txtTiempo);
		this.txtTiempo.setFont(fuentePlain);
		this.txtTiempo.setForeground(blanco);
		this.txtTiempo.setBackground(fondoPanel);
		this.txtTiempo.setCaretColor(blanco);

		// Fila 3: Botones para agregar naves
		this.panelBotonesNaves = new JPanel();
		this.panelBtnNaves.add(this.panelBotonesNaves);
		this.panelBotonesNaves.setBackground(fondo);

		this.btnNaveConsu = new JButton("Agregar Nave Consumidora");
		this.panelBotonesNaves.add(this.btnNaveConsu);
		this.btnNaveConsu.setFont(fuenteBold);
		this.btnNaveConsu.setForeground(celeste);
		this.btnNaveConsu.setBackground(fondoPanel);
		this.btnNaveConsu.setActionCommand("NaveConsu");

		this.btnNaveGen = new JButton("Agregar Nave Generadora");
		this.panelBotonesNaves.add(this.btnNaveGen);
		this.btnNaveGen.setFont(fuenteBold);
		this.btnNaveGen.setForeground(celeste);
		this.btnNaveGen.setBackground(fondoPanel);
		this.btnNaveGen.setActionCommand("NaveGen");

		// btn detener
		this.btnDetener = new JButton("Detener");
		this.panelBotonesNaves.add(this.btnDetener);
		this.btnDetener.setFont(fuenteBold);
		this.btnDetener.setForeground(celeste);
		this.btnDetener.setBackground(fondoPanel);
		this.btnDetener.setActionCommand("Detener");

		// Panel y botón Volver
		this.panelBtnVolver = new JPanel();
		this.panelBtn.add(this.panelBtnVolver);
		this.panelBtnVolver.setBackground(fondo);

		this.btnVolver = new JButton("Volver");
		this.panelBtnVolver.add(this.btnVolver);
		this.btnVolver.setFont(fuenteBold);
		this.btnVolver.setForeground(blanco);
		this.btnVolver.setBackground(fondoPanel);

		// Quitar efecto de foco
		this.btnNaveConsu.setFocusPainted(false);
		this.btnNaveGen.setFocusPainted(false);
		this.btnVolver.setFocusPainted(false);

		// Panel de título
		this.panelTitulo = new JPanel();
		this.contentPane.add(this.panelTitulo, BorderLayout.NORTH);
		this.panelTitulo.setBackground(fondo);

		this.lblTitulo = new JLabel("Plataforma de Carga");
		this.panelTitulo.add(this.lblTitulo);
		this.lblTitulo.setFont(fuenteTitulo);
		this.lblTitulo.setForeground(celeste);
	}

	@Override
	public void setActionListener(ActionListener c){
		this.btnNaveConsu.addActionListener(c);
		this.btnNaveGen.addActionListener(c);
		this.btnVolver.addActionListener(c);
		this.btnDetener.addActionListener(c);
	}

	@Override
	public void actualizar(String msj) {
			this.textAreaConcurrencia.append(msj + "\n");
	}

	public String getNombreNave(){
		return this.txtNombreNave.getText();
	}

	public int getTiempo(){
		return Integer.parseInt(this.txtTiempo.getText());
	}
}